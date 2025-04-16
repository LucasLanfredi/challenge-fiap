package br.com.fiap.TechChallenger.security;

import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortalService {

    private final JwtUtils jwtUtils;
    private final UsuarioRepository usuarioRepository;

    public UsuarioLogado getUsuarioLogado(HttpServletRequest request) {
        String token = parseJwt(request);

        if (token != null && jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUserNameFromJwtToken(token);

            final Usuario usuarioLogado = usuarioRepository.findByLogin(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));

            UsuarioLogado usuario = new UsuarioLogado();
            usuario.setId(usuarioLogado.getId());
            usuario.setNome(usuarioLogado.getNome());
            usuario.setEmail(username);

            return usuario;
        }

        return null;
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // remove "Bearer "
        }

        return null;
    }
}