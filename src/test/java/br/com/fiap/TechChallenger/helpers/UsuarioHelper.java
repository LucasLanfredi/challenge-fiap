package br.com.fiap.TechChallenger.helpers;

import br.com.fiap.TechChallenger.domains.TipoUsuario;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.UsuarioDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UsuarioHelper {

    public static Usuario gerarUsuario() {

        return new Usuario(1L, "João", "joao@email.com", "user", "12345678", false, LocalDateTime.now(), new ArrayList<>(), null);
    }

    public static Usuario gerarUsuario(Long id) {
        return new Usuario(id, "João", "joao@email.com", "user", "12345678", false, LocalDateTime.now(), new ArrayList<>(), TipoUsuario.CLIENTE);
    }

    public static Usuario gerarUsuario(UsuarioDTO usuarioDTO) {
        return new Usuario(
                1L,
                usuarioDTO.getNome(),
                usuarioDTO.getEmail(),
                usuarioDTO.getLogin(),
                usuarioDTO.getSenha(),
                false,
                LocalDateTime.now(),
                new ArrayList<>(),
                TipoUsuario.CLIENTE
        );
    }


}
