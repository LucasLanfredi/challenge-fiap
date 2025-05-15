package br.com.fiap.TechChallenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Informações do usuário autenticado")
@Getter
@AllArgsConstructor
@Builder
public class UsuarioLogado {

    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    private String nome;

    @Schema(description = "E-mail do usuário", example = "joao@email.com")
    private String email;

    @Schema(description = "Identificador único do usuário", example = "123")
    private Long id;

    @Schema(description = "Perfil do usuário (ex: ADMIN, CLIENTE)", example = "CLIENTE")
    private String perfil;

}
