package br.com.fiap.TechChallenger.controller;

import br.com.fiap.TechChallenger.dto.LoginRequest;
import br.com.fiap.TechChallenger.service.usuario.AutenticacaoUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AutenticacaoUsuarioService autenticacaoUsuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return autenticacaoUsuarioService.autenticar(loginRequest);
    }

}