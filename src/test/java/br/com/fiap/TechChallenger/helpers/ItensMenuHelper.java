package br.com.fiap.TechChallenger.helpers;

import br.com.fiap.TechChallenger.domains.TipoUsuario;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.UsuarioDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ItensMenuHelper {

    public static Usuario gerarUsuario() {
        return new Usuario();
//        return new Usuario(1L, "João", "joao@email.com", "user", "12345678", false, LocalDateTime.now(), new ArrayList<>(), TipoUsuario.CLIENTE);
    }

    public static Usuario gerarUsuario(Long id) {
        return new Usuario(id,
                "João",
                "joao@email.com",
                "user",
                "12345678",
                false,
                LocalDateTime.now(),
                new ArrayList<>(),
                new ArrayList<>(),
                TipoUsuario.CLIENTE);
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
                new ArrayList<>(),
                TipoUsuario.CLIENTE
        );
    }

    public static Usuario gerarUsuarioDonoDeRestaurante() {
        return new Usuario(1L,
                "João",
                "joao@email.com",
                "user",
                "12345678",
                false,
                LocalDateTime.now(),
                new ArrayList<>(),
                new ArrayList<>(),
                TipoUsuario.DONO_RESTAURANTE);
    }

    public static UsuarioDTO gerarUsuarioDTO() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setEmail("joao@email.com");
        usuarioDTO.setLogin("joao");
        usuarioDTO.setSenha("12345678");
        usuarioDTO.setTipoUsuario(TipoUsuario.CLIENTE);
        return usuarioDTO;
    }

    public static UsuarioDTO gerarUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setLogin(usuario.getLogin());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setTipoUsuario(usuario.getTipoUsuario());
        return usuarioDTO;
    }

    public static UsuarioDTO gerarUsuarioDTODonoDeRestaurante() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setEmail("joao@email.com");
        usuarioDTO.setLogin("joao");
        usuarioDTO.setSenha("12345678");
        usuarioDTO.setTipoUsuario(TipoUsuario.DONO_RESTAURANTE);
        return usuarioDTO;

    }
}
