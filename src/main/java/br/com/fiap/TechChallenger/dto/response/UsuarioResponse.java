package br.com.fiap.TechChallenger.dto.response;

import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.model.TipoUsuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String login,
        TipoUsuario tipoUsuario
) {
    public static UsuarioResponse fromEntity(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipoUsuario()
        );
    }
}
