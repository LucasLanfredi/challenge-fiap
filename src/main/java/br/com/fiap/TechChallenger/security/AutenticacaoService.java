package br.com.fiap.TechChallenger.security;

import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.repository.UsuarioRepository;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final JwtUtils jwtUtils;
    private final UsuarioRepository usuarioRepository;

    public UsuarioLogado getUsuarioLogado(HttpServletRequest request) throws AuthException {
        String token = parseJwt(request);
        if (token != null && jwtUtils.validateJwtToken(token)) {
            String username = jwtUtils.getUserNameFromJwtToken(token);
            return getUsuarioLogadoByUsername(username);
        }
        throw new AuthException("Nenhum usuário logado encontrado.");
    }

    private UsuarioLogado getUsuarioLogadoByUsername(String username) {
        final Usuario usuarioEntidade = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return UsuarioLogado.builder()
                .id(usuarioEntidade.getId())
                .nome(usuarioEntidade.getNome())
                .email(usuarioEntidade.getEmail())
                .perfil(usuarioEntidade.getTipoUsuario().toString())
                .build();
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}