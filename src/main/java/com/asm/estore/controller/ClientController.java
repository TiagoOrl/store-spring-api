package com.asm.estore.controller;


import com.asm.estore.dto.address.AddressDTO;
import com.asm.estore.dto.address.CreateAddressDTO;
import com.asm.estore.dto.address.UpdateAddressDTO;
import com.asm.estore.dto.client.AllClientsDTO;
import com.asm.estore.dto.client.CreateClientDTO;
import com.asm.estore.dto.client.SingleClientDTO;
import com.asm.estore.dto.client.UpdateClientDTO;
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
    public List<AllClientsDTO> getAllClients() {
        return service.getAllClients();
    }

    @GetMapping("{clientId}")
    public SingleClientDTO getClientById(@PathVariable("clientId") Long clientId) {
        return service.getById(clientId);
    }

    @PostMapping("create")
    public CreateClientDTO createClient(
            @Valid @RequestBody CreateClientDTO dto
    ) {
        return service.createClient(dto);
    }

    @PutMapping("{clientId}")
    public UpdateClientDTO updateClientData(
            @PathVariable("clientId") Long clientId,
            @Valid @RequestBody UpdateClientDTO dto
    ) {
        return service.updateClient(clientId, dto);
    }

    @GetMapping("address")
    public List<AddressDTO> getAllAddresses() {
        return service.getAllAddresses();
    }

    @PostMapping("address/create")
    public CreateAddressDTO createAddressForClient(
            @Valid @RequestBody CreateAddressDTO dto
    ) {
        return service.addAddressForClient(dto);
    }

    @PutMapping("address/{clientId}")
    public UpdateAddressDTO updateAddress(
            @PathVariable("clientId") Long clientId,
            @Valid @RequestBody UpdateAddressDTO dto) {
        return service.updateAddressByClientId(clientId, dto);
    }
}
