package br.com.fiap.TechChallenger.usecases.endereco;

import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.entity.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.entity.Endereco;
import br.com.fiap.TechChallenger.domains.entity.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.AutenticacaoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarEnderecoService {

    private final UsuarioRepository usuarioRepository;
    private final AutenticacaoService autenticacaoService;
    private final EnderecoRepository enderecoRepository;

    public ResponseEntity<?> criarEnderecos(@Valid EnderecoDTO criarEnderecoRequest, HttpServletRequest request) {
        try {
            final UsuarioLogado usuariologado = autenticacaoService.getUsuarioLogado(request);
            final Usuario usuarioEntity = usuarioRepository.findById(usuariologado.getId())
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

            return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}