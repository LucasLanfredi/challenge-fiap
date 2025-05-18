package br.com.fiap.TechChallenger.dto;

import br.com.fiap.TechChallenger.model.TipoUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    private String email;

    @NotBlank(message = "O login é obrigatório.")
    private String login;

    @NotBlank(message = "A senha é obrigatória.")
//    @Pattern(
//            regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6}$",
//            message = "A senha deve ter 6 caracteres com letra e número, sem símbolos."
//    )
    private String senha;

    @Valid
    @NotNull(message = "O endereço é obrigatório.")
    private List<EnderecoDTO> endereco;


    @NotNull(message = "O tipo de usuário é obrigatório.")
    private TipoUsuario tipoUsuario;
}