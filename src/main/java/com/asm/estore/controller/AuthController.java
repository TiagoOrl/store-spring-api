package com.asm.estore.controller;

import com.asm.estore.config.security.TokenService;
import com.asm.estore.dto.LoginResponseDTO;
import com.asm.estore.dto.client.CreateClientDTO;
import com.asm.estore.dto.client.LoginDTO;
import com.asm.estore.entity.Client;
import com.asm.estore.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final ClientService clientService;

    @Autowired
    public AuthController(
            AuthenticationManager authManager,
            TokenService tokenService,
            ClientService clientService
            ) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.clientService = clientService;
    }

    @PostMapping("create")
    public CreateClientDTO createClient(
            @Valid @RequestBody CreateClientDTO dto
    ) {
        return clientService.createClient(dto);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        var auth = authManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Client)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
