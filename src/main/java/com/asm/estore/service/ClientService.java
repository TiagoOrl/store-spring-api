package com.asm.estore.service;

import com.asm.estore.dto.client.ClientDTO;
import com.asm.estore.entity.Address;
import com.asm.estore.entity.Client;
import com.asm.estore.repository.AddressRepository;
import com.asm.estore.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientService {
    private final ClientRepository repository;
    private final AddressRepository addressRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    public ClientService(ClientRepository repository, AddressRepository addressRepository) {
        this.repository = repository;
        this.addressRepository = addressRepository;
    }

    public List<ClientDTO> getAllClients() {
        return repository.findAll().stream().map(
                client -> mapper.map(client, ClientDTO.class)
        ).toList();
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
}
