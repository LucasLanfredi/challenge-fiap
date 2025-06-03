package br.com.fiap.TechChallenger.gateways.controller.api;

import br.com.fiap.TechChallenger.domains.dto.LoginRequest;
import br.com.fiap.TechChallenger.domains.dto.response.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Autenticação", description = "Endpoints para login e geração de tokens JWT")
@RequestMapping("/auth")
@RestController
public interface AuthApi {
    @Operation(
            summary = "Autenticar usuário",
            description = "Realiza a autenticação do usuário e retorna o token JWT em caso de sucesso."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticado com sucesso",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "400", description = "Requisição malformada")
    })
    @PostMapping("/login")
    ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest);
}
