package br.com.fiap.TechChallenger.domains.dto;

import br.com.fiap.TechChallenger.domains.enums.TipoUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioEditDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    private String email;

    @NotBlank(message = "O login é obrigatório.")
    private String login;

    @Valid
    @NotNull(message = "O endereço é obrigatório.")
    private List<EnderecoDTO> endereco;

    @NotNull(message = "O tipo de usuário é obrigatório.")
    private TipoUsuario tipoUsuario;

}
