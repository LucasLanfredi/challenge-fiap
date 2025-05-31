package br.com.fiap.TechChallenger.usecases.endereco;

import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.dto.response.EnderecoResponse;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
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
public class BuscarEndereco {

    private final EnderecoRepository enderecoRepository;
    private final Autenticacao autenticacao;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<List<EnderecoResponse>> buscarEnderecos(HttpServletRequest request) {
        try {
            final UsuarioLogado usuariologado = autenticacao.getUsuarioLogado(request);
            return buscarEnderecosByUserId(usuariologado.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<List<EnderecoResponse>> buscarEnderecosByUserId(Long userId) {
        final Usuario usuarioEntity = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return ResponseEntity.ok(enderecoRepository.getEnderecosByUsuario(usuarioEntity).stream().map(EnderecoResponse::fromEntity).toList());
    }

    public ResponseEntity<List<EnderecoResponse>> buscarTodosEnderecos() {
        List<EnderecoResponse> enderecos = enderecoRepository.findAll()
                .stream()
                .map(EnderecoResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(enderecos);
    }
}