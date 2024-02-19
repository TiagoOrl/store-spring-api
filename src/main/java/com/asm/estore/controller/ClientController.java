package com.asm.estore.controller;


import com.asm.estore.dto.address.AddressDTO;
import com.asm.estore.dto.address.CreateAddressDTO;
import com.asm.estore.dto.address.UpdateAddressDTO;
import com.asm.estore.dto.client.CreateClientDTO;
import com.asm.estore.dto.client.SingleClientDTO;
import com.asm.estore.dto.client.UpdateClientDTO;
import com.asm.estore.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/client")
public class ClientController {
    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("admin")
    public List<SingleClientDTO> getAllClients(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        return service.getAllClients(page, size);
    }

    @GetMapping("admin/get-by-name")
    public List<SingleClientDTO> getAllByName(
            @RequestParam String name,
            Optional<Integer> page,
            Optional<Integer> size
    ) {
        return service.getAllByNameMatch(name, page, size);
    }

    @GetMapping("admin/{clientId}")
    public SingleClientDTO getClientById(@PathVariable("clientId") Long clientId) {
        return service.getById(clientId);
    }

    @PostMapping("create")
    public CreateClientDTO createClient(
            @Valid @RequestBody CreateClientDTO dto
    ) {
        return service.createClient(dto);
    }

    @PutMapping("user/{clientId}")
    public UpdateClientDTO updateClientData(
            @PathVariable("clientId") Long clientId,
            @Valid @RequestBody UpdateClientDTO dto
    ) {
        return service.updateClient(clientId, dto);
    }

    @GetMapping("admin/address")
    public List<AddressDTO> getAllAddresses(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        return service.getAllAddresses(page, size);
    }

    @PostMapping("user/address/create")
    public CreateAddressDTO createAddressForClient(
            @Valid @RequestBody CreateAddressDTO dto
    ) {
        return service.addAddressForClient(dto);
    }

    @PutMapping("user/address/{clientId}")
    public UpdateAddressDTO updateAddress(
            @PathVariable("clientId") Long clientId,
            @Valid @RequestBody UpdateAddressDTO dto) {
        return service.updateAddressByClientId(clientId, dto);
    }
}
