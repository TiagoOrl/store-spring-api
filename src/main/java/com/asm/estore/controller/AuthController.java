package com.asm.estore.controller;

import com.asm.estore.config.security.TokenService;
import com.asm.estore.dto.LoginResponseDTO;
import com.asm.estore.dto.client.LoginDTO;
import com.asm.estore.entity.Client;
import jakarta.validation.Valid;
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

    public AuthController(
            AuthenticationManager authManager,
            TokenService tokenService
            ) {
        this.authManager = authManager;
        this.tokenService = tokenService;
    }
    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        var auth = authManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Client)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
