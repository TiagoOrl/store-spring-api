package com.asm.estore.controller;

import com.asm.estore.dto.address.AddressDTO;
import com.asm.estore.dto.client.SingleClientDTO;
import com.asm.estore.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/admin/client")
public class ClientAdminController {
    private final ClientService service;

    @Autowired
    public ClientAdminController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<SingleClientDTO> getAllClients(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        return service.getAllClients(page, size);
    }

    @GetMapping("get-by-name")
    public List<SingleClientDTO> getAllByName(
            @RequestParam String name,
            Optional<Integer> page,
            Optional<Integer> size
    ) {
        return service.getAllByNameMatch(name, page, size);
    }

    @GetMapping("{clientId}")
    public SingleClientDTO getClientById(@PathVariable("clientId") Long clientId) {
        return service.getById(clientId);
    }

    @GetMapping("address")
    public List<AddressDTO> getAllAddresses(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        return service.getAllAddresses(page, size);
    }
}
