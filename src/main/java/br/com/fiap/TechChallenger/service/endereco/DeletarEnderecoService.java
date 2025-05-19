package br.com.fiap.TechChallenger.service.endereco;

import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.entity.Endereco;
import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.repository.EnderecoRepository;
import br.com.fiap.TechChallenger.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.security.AutenticacaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarEnderecoService {

    private final AutenticacaoService autenticacaoService;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<?> deletarEnderecos(Long enderecoId, HttpServletRequest request) {
        try {
            final UsuarioLogado usuariologado = autenticacaoService.getUsuarioLogado(request);
            final Usuario usuarioEntity = usuarioRepository.findById(usuariologado.getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            Endereco endereco = enderecoRepository.findById(enderecoId)
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

            if (!endereco.getUsuario().getId().equals(usuarioEntity.getId())) {
                throw new RuntimeException("Este endereço não pertence ao usuário logado");
            }

            return deletarEnderecoById(enderecoId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<String> deletarEnderecoById(Long enderecoId) {
        enderecoRepository.deleteById(enderecoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}