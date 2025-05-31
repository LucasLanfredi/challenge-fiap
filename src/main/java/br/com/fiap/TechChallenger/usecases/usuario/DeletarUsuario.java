package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarUsuario {

    private final UsuarioRepository usuarioRepository;
    private final Autenticacao autenticacao;

    public ResponseEntity<?> deletar(HttpServletRequest request) throws AuthException {
        final UsuarioLogado usuarioLogado = autenticacao.getUsuarioLogado(request);
        return deleteUsuarioById(usuarioLogado.getId());
    }

    public ResponseEntity<String> deleteUsuarioById(Long id) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
    }
}