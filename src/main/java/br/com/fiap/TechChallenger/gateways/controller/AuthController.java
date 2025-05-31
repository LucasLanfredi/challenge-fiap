package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.gateways.controller.api.AuthApi;
import br.com.fiap.TechChallenger.domains.dto.LoginRequest;
import br.com.fiap.TechChallenger.usecases.usuario.AutenticacaoUsuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AutenticacaoUsuario autenticacaoUsuario;

    @Override
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return autenticacaoUsuario.autenticar(loginRequest);
    }

}