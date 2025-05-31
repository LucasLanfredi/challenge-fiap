package br.com.fiap.TechChallenger.domains.dto.response;

import br.com.fiap.TechChallenger.domains.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Resposta com token JWT e dados do usuário autenticado")
public record JwtResponse(
        @Schema(description = "Token JWT gerado") String token,
        @Schema(description = "Tipo do token, geralmente 'Bearer'") String type,
        @Schema(description = "ID do usuário") Long id,
        @Schema(description = "Nome de usuário") String username,
        @Schema(description = "Email do usuário") String email,
        @Schema(description = "Tipo de usuário (ex: ADMIN, CLIENTE)") TipoUsuario tipoUsuario
) {
    public JwtResponse(String token, Long id, String username, String email, TipoUsuario tipoUsuario) {
        this(token, "Bearer", id, username, email, tipoUsuario);
    }
}
