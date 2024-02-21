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
@RequestMapping("api/user/client")
public class ClientController {
    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
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
