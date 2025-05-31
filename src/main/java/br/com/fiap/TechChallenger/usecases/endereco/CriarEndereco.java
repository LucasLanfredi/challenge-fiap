package br.com.fiap.TechChallenger.usecases.endereco;

import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.dto.response.EnderecoResponse;
import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarEndereco {

    private final UsuarioRepository usuarioRepository;
    private final Autenticacao autenticacao;
    private final EnderecoRepository enderecoRepository;

    public ResponseEntity<?> criarEnderecos(@Valid EnderecoDTO criarEnderecoRequest, HttpServletRequest request) {
        try {
            final UsuarioLogado usuariologado = autenticacao.getUsuarioLogado(request);
            return criarEnderecosByUserId(criarEnderecoRequest, usuariologado.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> criarEnderecosByUserId(@Valid EnderecoDTO criarEnderecoRequest, Long userId) {
        try {
            final Usuario usuarioEntity = usuarioRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            final Endereco enderecoEntity = Endereco.builder()
                    .numero(criarEnderecoRequest.getNumero())
                    .rua(criarEnderecoRequest.getRua())
                    .cidade(criarEnderecoRequest.getCidade())
                    .estado(criarEnderecoRequest.getEstado())
                    .cep(criarEnderecoRequest.getCep())
                    .usuario(usuarioEntity)
                    .build();

            final Endereco endereco = enderecoRepository.save(enderecoEntity);

            return ResponseEntity.status(HttpStatus.CREATED).body(EnderecoResponse.fromEntity(endereco));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}