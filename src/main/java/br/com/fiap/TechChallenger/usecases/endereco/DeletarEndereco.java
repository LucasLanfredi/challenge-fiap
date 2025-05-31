package br.com.fiap.TechChallenger.usecases.endereco;

import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarEndereco {

    private final Autenticacao autenticacao;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<?> deletarEnderecos(Long enderecoId, HttpServletRequest request) {
        try {
            final UsuarioLogado usuariologado = autenticacao.getUsuarioLogado(request);
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