package br.com.fiap.TechChallenger.domains.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Corpo para troca de senha")
@Data
public class TrocaSenhaDto {
    @Schema(description = "Login do usuário")
    private String login;

    @Schema(description = "Senha atual do usuário")
    private String senhaAtual;

    @Schema(description = "Nova senha do usuário")
    private String NovaSenha;
}
