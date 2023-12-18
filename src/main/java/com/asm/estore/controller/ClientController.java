package com.asm.estore.controller;


import com.asm.estore.dto.address.AddressDTO;
import com.asm.estore.dto.client.ClientDTO;
import com.asm.estore.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{clientId}")
    public ClientDTO getClientById(@PathVariable("clientId") Long clientId) {
        return service.getById(clientId);
    }

    @GetMapping("address")
    public List<AddressDTO> getAllAddresses() {
        return service.getAllAddresses();
    }

    @PostMapping("create")
    public ClientDTO createClient(@RequestBody ClientDTO dto) {
        return service.createClient(dto);
    }
}
