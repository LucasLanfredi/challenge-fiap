package br.com.fiap.TechChallenger.domains.dto;

import br.com.fiap.TechChallenger.domains.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "Entrada para edição de usuário")
@Data
public class UsuarioEditDTO {

    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Schema(description = "E-mail do usuário", example = "joao@email.com")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    private String email;

    @Schema(description = "Tipo do usuário (ex: ADMIN, CLIENTE)", example = "CLIENTE")
    private TipoUsuario tipoUsuario;

}
