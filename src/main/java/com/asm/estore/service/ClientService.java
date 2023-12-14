package com.asm.estore.service;

import com.asm.estore.dto.address.AddressDTO;
import com.asm.estore.dto.client.ClientDTO;
import com.asm.estore.dto.order.OrderDTO;
import com.asm.estore.entity.Address;
import com.asm.estore.repository.AddressRepository;
import com.asm.estore.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientService {
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    public ClientService(ClientRepository repository, AddressRepository addressRepository) {
        this.clientRepository = repository;
        this.addressRepository = addressRepository;
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(
                client -> {
                    ClientDTO clientDTO = mapper.map(client, ClientDTO.class);

                    clientDTO.setOrders(client.getOrders().stream().map(
                            order -> mapper.map(order, OrderDTO.class)
                    ).toList());

                    return clientDTO;
                }
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
}
