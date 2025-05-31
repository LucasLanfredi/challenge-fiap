package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.dto.response.UsuarioResponse;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarUsuario {

    private final Autenticacao autenticacao;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<UsuarioResponse> buscar(HttpServletRequest request) {
        try {
            final UsuarioLogado usuarioLogado = autenticacao.getUsuarioLogado(request);
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

    public ResponseEntity<List<UsuarioResponse>> buscarTodosUsuarios() {
        List<UsuarioResponse> usuariosList = usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(usuariosList);
    }

}