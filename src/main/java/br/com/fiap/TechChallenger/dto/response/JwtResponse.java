package br.com.fiap.TechChallenger.dto.response;

import br.com.fiap.TechChallenger.model.TipoUsuario;

public record JwtResponse(
        String token,
        String type,
        Long id,
        String username,
        String email,
        TipoUsuario tipoUsuario
) {
    public JwtResponse(String token, Long id, String username, String email, TipoUsuario tipoUsuario) {
        this(token, "Bearer", id, username, email, tipoUsuario);
    }
}
