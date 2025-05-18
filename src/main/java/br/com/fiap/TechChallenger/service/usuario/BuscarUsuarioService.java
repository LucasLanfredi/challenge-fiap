package br.com.fiap.TechChallenger.service.usuario;

import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.dto.response.UsuarioResponse;
import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.security.AutenticacaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarUsuarioService {

    private final AutenticacaoService autenticacaoService;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<UsuarioResponse> buscar(HttpServletRequest request) {
        try {
            final UsuarioLogado usuarioLogado = autenticacaoService.getUsuarioLogado(request);
            return getUsuarioResponseById(usuarioLogado.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<UsuarioResponse> getUsuarioResponseById(Long userId) {
        final Usuario usuarioExistente = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return ResponseEntity.ok(UsuarioResponse.fromEntity(usuarioExistente));
    }
}