package br.com.fiap.TechChallenger.helpers;

import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;

public class UsuarioLogadoHelper {

    public static UsuarioLogado gerarUsuarioLogado() {
        return UsuarioLogado.builder()
                .id(1L)
                .email("usuario@email.com")
                .nome("Usuario")
                .build();
    }

    public static UsuarioLogado gerarUsuarioLogado(Usuario usuario) {
        return UsuarioLogado.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nome(usuario.getNome())
                .build();
    }

}
