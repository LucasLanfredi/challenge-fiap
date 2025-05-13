package br.com.fiap.TechChallenger.usecases.endereco;

import br.com.fiap.TechChallenger.domains.dto.EnderecoEditDTO;
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
public class EditarEnderecoService {

    private final AutenticacaoService autenticacaoService;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<?> editarEnderecos(@Valid EnderecoEditDTO editarEnderecoRequest, HttpServletRequest request) {
        try {
            final UsuarioLogado usuariologado = autenticacaoService.getUsuarioLogado(request);
            final Usuario usuarioEntity = usuarioRepository.findById(usuariologado.getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            final Endereco enderecoEntityAlterado = Endereco.builder()
                    .id(editarEnderecoRequest.getId())
                    .numero(editarEnderecoRequest.getNumero())
                    .rua(editarEnderecoRequest.getRua())
                    .cidade(editarEnderecoRequest.getCidade())
                    .estado(editarEnderecoRequest.getEstado())
                    .cep(editarEnderecoRequest.getCep())
                    .usuario(usuarioEntity)
                    .build();

            final Endereco endereco = enderecoRepository.save(enderecoEntityAlterado);
            return ResponseEntity.status(HttpStatus.OK).body(endereco);
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}