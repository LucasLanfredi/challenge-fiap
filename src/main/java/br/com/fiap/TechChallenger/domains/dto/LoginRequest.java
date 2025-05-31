package br.com.fiap.TechChallenger.domains.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Corpo de entrada para autenticação")
public class LoginRequest {
    @Schema(description = "Login do usuário") private String login;
    @Schema(description = "Senha do usuário") private String senha;
}
