package br.com.fiap.TechChallenger.dto;

import br.com.fiap.TechChallenger.model.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

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

    @Schema(description = "Login de acesso do usuário", example = "joaosilva")
    @NotBlank(message = "O login é obrigatório.")
    private String login;

    @Schema(description = "Lista de endereços associados ao usuário")
    @Valid
    @NotNull(message = "O endereço é obrigatório.")
    private List<EnderecoDTO> endereco;

    @Schema(description = "Tipo do usuário (ex: ADMIN, CLIENTE)", example = "CLIENTE")
    @NotNull(message = "O tipo de usuário é obrigatório.")
    private TipoUsuario tipoUsuario;

}
