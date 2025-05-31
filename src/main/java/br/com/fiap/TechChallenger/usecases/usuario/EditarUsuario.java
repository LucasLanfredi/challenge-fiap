package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EditarUsuario {

    private final UsuarioRepository usuarioRepository;
    private final Autenticacao autenticacao;

    public ResponseEntity<?> editar(@Valid final UsuarioEditDTO editarUsuarioRequest,
                                    final HttpServletRequest request) throws AuthException {

        final UsuarioLogado usuarioLogado = autenticacao.getUsuarioLogado(request);

        return editarUsuarioByUserId(editarUsuarioRequest, usuarioLogado.getId());
    }

    public ResponseEntity<Map<String, ? extends Serializable>> editarUsuarioByUserId(UsuarioEditDTO editarUsuarioRequest, Long userId) {
        final Usuario usuarioExistente = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setNome(editarUsuarioRequest.getNome());
        usuarioExistente.setEmail(editarUsuarioRequest.getEmail());
        usuarioExistente.setDataUltimaAlteracao(LocalDateTime.now());

        final Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);

        return ResponseEntity.ok()
                .body(Map.of(
                        "mensagem", "Usuário editado com sucesso!",
                        "id", usuarioAtualizado.getId()
                ));
    }

}