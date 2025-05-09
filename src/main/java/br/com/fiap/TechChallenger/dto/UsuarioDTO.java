package br.com.fiap.TechChallenger.dto;

import br.com.fiap.TechChallenger.model.TipoUsuario;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioDTO {
    private String nome;
    private String email;
    private String login;
    private String senha;
    private List<EnderecoDTO> endereco;
    private TipoUsuario tipoUsuario;
}