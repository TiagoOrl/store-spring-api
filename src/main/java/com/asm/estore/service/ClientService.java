package com.asm.estore.service;

import com.asm.estore.dto.address.AddressDTO;
import com.asm.estore.dto.client.AllClientsDTO;
import com.asm.estore.dto.client.CreateClientDTO;
import com.asm.estore.dto.client.SingleClientDTO;
import com.asm.estore.entity.Client;
import com.asm.estore.repository.AddressRepository;
import com.asm.estore.repository.ClientRepository;
import com.asm.estore.validation.MainValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class ClientService {
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private MainValidator mainValidator;

    @Autowired
    public ClientService(ClientRepository repository, AddressRepository addressRepository) {
        this.clientRepository = repository;
        this.addressRepository = addressRepository;
    }

    public List<AllClientsDTO> getAllClients() {
        return clientRepository.findAll().stream().map(
                client -> mapper.map(client, AllClientsDTO.class)
        ).toList();
    }

    public List<AddressDTO> getAllAddresses() {
        return addressRepository.findAll().stream().map(
                address -> {
                    AddressDTO dto = mapper.map(address, AddressDTO.class);
                    dto.setClientsName(address.getClient().getFirstName() + " " + address.getClient().getSecondName());
                    return dto;
                }
        ).toList();
    }

    public SingleClientDTO getById(Long clientId) {
        var optClient = clientRepository.findById(clientId);
        if (optClient.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");

        return mapper.map(optClient.get(), SingleClientDTO.class);
    }

    public CreateClientDTO createClient(CreateClientDTO dto) {
        mainValidator.validateObject(dto);

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
        try {
            clientRepository.save(client);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        dto.setId(client.getId());
        return dto;
    }
}
