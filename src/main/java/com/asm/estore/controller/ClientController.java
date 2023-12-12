package com.asm.estore.controller;


import com.asm.estore.dto.client.ClientDTO;
import com.asm.estore.entity.Address;
import com.asm.estore.entity.Client;
import com.asm.estore.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/client")
public class ClientController {
    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClientDTO> getAllClients() {
        return service.getAllClients();
    }

    @GetMapping("address") List<Address> getAllAddresses() {
        return service.getAllAddresses();
    }
}
