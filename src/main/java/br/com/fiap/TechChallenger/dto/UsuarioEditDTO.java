package br.com.fiap.TechChallenger.dto;

import br.com.fiap.TechChallenger.entity.Endereco;
import br.com.fiap.TechChallenger.model.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioEditDTO {

    private String nome;
    private String email;
    private String login;
    private Endereco endereco;
    private TipoUsuario tipoUsuario;

}
