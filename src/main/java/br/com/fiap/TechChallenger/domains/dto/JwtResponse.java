package br.com.fiap.TechChallenger.domains.dto;

import br.com.fiap.TechChallenger.domains.enums.TipoUsuario;

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
