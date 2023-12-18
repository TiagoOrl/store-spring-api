package com.asm.estore.service;

import com.asm.estore.dto.address.AddressDTO;
import com.asm.estore.dto.client.AllClientsDTO;
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

    public AllClientsDTO getById(Long clientId) {
        var optClient = clientRepository.findById(clientId);
        if (optClient.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");

        return mapper.map(optClient.get(), AllClientsDTO.class);
    }

    public AllClientsDTO createClient(AllClientsDTO dto) {
        mainValidator.validateObject(dto);


        return new AllClientsDTO();
    }
}
