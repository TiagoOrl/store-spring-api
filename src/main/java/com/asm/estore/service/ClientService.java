package com.asm.estore.service;

import com.asm.estore.dto.address.AddressDTO;
import com.asm.estore.dto.address.CreateAddressDTO;
import com.asm.estore.dto.address.UpdateAddressDTO;
import com.asm.estore.dto.client.CreateClientDTO;
import com.asm.estore.dto.client.SingleClientDTO;
import com.asm.estore.dto.client.UpdateClientDTO;
import com.asm.estore.entity.Address;
import com.asm.estore.entity.Client;
import com.asm.estore.entity.EmailModel;
import com.asm.estore.repository.AddressRepository;
import com.asm.estore.repository.ClientRepository;
import com.asm.estore.utils.PaginationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ClientService {
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper mapper;
    private final RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.queue}")
    private String queue;

    @Value("${email.username}")
    private String emailUsername;

    @Autowired
    public ClientService(
            ClientRepository repository,
            AddressRepository addressRepository,
            ModelMapper mapper,
            RabbitTemplate rabbitTemplate
    ) {
        this.clientRepository = repository;
        this.addressRepository = addressRepository;
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
    }

//    @Cacheable("clients1")
    public List<SingleClientDTO> getAllClients(
            Optional<Integer> page,
            Optional<Integer> size
    ) {
        var pagUtils = new PaginationUtil(page, size);

        return clientRepository.findAll(pagUtils.getPageRequest()).stream().map(
                client -> mapper.map(client, SingleClientDTO.class)
        ).toList();
    }

    public List<SingleClientDTO> getAllByNameMatch(
            String name,
            Optional<Integer> page,
            Optional<Integer> size
    ) {
        var pagUtils = new PaginationUtil(page, size);

        return clientRepository.getAllByName(name.trim(), pagUtils.getPageRequest()).stream().map(
                client -> mapper.map(client, SingleClientDTO.class)
        ).toList();
    }

    public List<AddressDTO> getAllAddresses(
            Optional<Integer> page,
            Optional<Integer> size
    ) {
        var pagUtils = new PaginationUtil(page, size);

        return addressRepository.findAll(pagUtils.getPageRequest()).stream().map(
                address -> {
                    AddressDTO dto = mapper.map(address, AddressDTO.class);
                    dto.setClientsName(address.getClient().getFirstName() + " " + address.getClient().getSecondName());
                    return dto;
                }
        ).toList();
    }

//    @Cacheable("clients1")
    public SingleClientDTO getById(Long clientId) {
        var optClient = clientRepository.findById(clientId);
        if (optClient.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");

        return mapper.map(optClient.get(), SingleClientDTO.class);
    }

    public CreateClientDTO createClient(CreateClientDTO dto) {
        clientRepository.findByCountryId(dto.getCountryId()).ifPresent(
                i -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Client with this ID already exists: " + dto.getCountryId());
                }
        );
        clientRepository.findByEmail(dto.getEmail()).ifPresent(
                i -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Client with this email already exists: " + dto.getEmail());
                }
        );

        var client = mapper.map(dto, Client.class);
        client.setFullname(dto.getFirstName() + " " + dto.getSecondName());
        client.setDeleted(false);

        String hashedPass = new BCryptPasswordEncoder().encode(dto.getPassword());
        client.setPassword(hashedPass);

        try {
            clientRepository.save(client);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        try {
            var email = new EmailModel(
                    emailUsername,
                    client.getEmail(),
                    "Conta criada na ESTORE",
                    "Olá " + client.getFirstName() + ", sua conta foi criada com sucesso em nossa loja, " +
                            "em breve você receberá um email com um código de ativação da sua conta."
            );

            ObjectMapper mapper = new ObjectMapper();
            var serialized = mapper.writeValueAsBytes(email);
            rabbitTemplate.send(queue, new Message(serialized));
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        dto.setId(client.getId());
        return dto;
    }

    public CreateAddressDTO addAddressForClient(CreateAddressDTO dto) {
        addressRepository.getAddressByClientId(dto.getClientId()).ifPresent(
                i -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "This client already has an Address registered");
                }
        );
        clientRepository.findById(dto.getClientId()).ifPresentOrElse(
                i -> {
                    var address = mapper.map(dto, Address.class);
                    address.setClient(i);
                    addressRepository.save(address);
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Client with this Id: "+ dto.getClientId() + " does not exists");
                }
        );
        return dto;
    }

    @Transactional
    public UpdateClientDTO updateClient(Long clientId, UpdateClientDTO dto) {
        clientRepository.findById(clientId).ifPresentOrElse(
                client -> {
                    if (dto.getFirstName() != null)
                        client.setFirstName(dto.getFirstName());
                    if (dto.getSecondName() != null)
                        client.setSecondName(dto.getSecondName());
                    if (dto.getEmail() != null)
                        client.setEmail(dto.getEmail());
                    if (dto.getCountryId() != null)
                        client.setCountryId(dto.getCountryId());
                    if (dto.getDob() != null)
                        client.setDob(dto.getDob());
                    if (dto.getPassword() != null)
                        client.setPassword(dto.getPassword());

                    client.setUpdatedAt(new Date());
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with this Id not found");
                }
        );


        return dto;
    }

    @Transactional
    public UpdateAddressDTO updateAddressByClientId(Long clientId, UpdateAddressDTO dto) {
        addressRepository.getAddressByClientId(clientId).ifPresentOrElse(
                address -> {
                    if (dto.getStreet() != null)
                        address.setStreet(dto.getStreet());
                    if (dto.getNeighborhood() != null)
                        address.setNeighborhood(dto.getNeighborhood());
                    if (dto.getNumber() != null)
                        address.setNumber(dto.getNumber());
                    if (dto.getCity() != null)
                        address.setCity(dto.getCity());
                    if (dto.getStateOrCounty() != null)
                        address.setStateOrCounty(dto.getStateOrCounty());
                    if (dto.getCountry() != null)
                        address.setCountry(dto.getCountry());

                    address.setUpdatedAt(new Date());
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address for this Client Id not found");
                }
        );

        return dto;
    }
}
