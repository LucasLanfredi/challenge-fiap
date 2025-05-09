package br.com.fiap.TechChallenger.dto;

import br.com.fiap.TechChallenger.model.TipoUsuario;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioEditDTO {

    private String nome;
    private String email;
    private String login;
    private List<EnderecoDTO> endereco;
    private TipoUsuario tipoUsuario;

}
