package br.com.fiap.TechChallenger.usecases.endereco;

import br.com.fiap.TechChallenger.domains.dto.EnderecoEditDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.dto.response.EnderecoResponse;
import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditarEndereco {

    private final Autenticacao autenticacao;
    private final EnderecoRepository enderecoRepository;

    public ResponseEntity<?> editarEnderecos(@Valid EnderecoEditDTO editarEnderecoRequest, HttpServletRequest request) {
        try {
            final UsuarioLogado usuariologado = autenticacao.getUsuarioLogado(request);

            Endereco endereco = enderecoRepository.findById(editarEnderecoRequest.getEnderecoId())
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

            if (!endereco.getUsuario().getId().equals(usuariologado.getId())) {
                throw new RuntimeException("Este endereço não pertence ao usuário logado");
            }

            return editarEnderecosByEnderecoId(editarEnderecoRequest);
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<EnderecoResponse> editarEnderecosByEnderecoId(EnderecoEditDTO editarEnderecoRequest) {

        Endereco enderecoEntidade = enderecoRepository.findById(editarEnderecoRequest.getEnderecoId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        updateEnderecoFromDto(editarEnderecoRequest, enderecoEntidade);

        final Endereco endereco = enderecoRepository.save(enderecoEntidade);

        return ResponseEntity.status(HttpStatus.OK).body(EnderecoResponse.fromEntity(endereco));
    }

    public void updateEnderecoFromDto(EnderecoEditDTO dto, Endereco entity) {
        if (dto.getRua() != null) {
            entity.setRua(dto.getRua());
        }
        if (dto.getNumero() != null) {
            entity.setNumero(dto.getNumero());
        }
        if (dto.getCidade() != null) {
            entity.setCidade(dto.getCidade());
        }
        if (dto.getEstado() != null) {
            entity.setEstado(dto.getEstado());
        }
        if (dto.getCep() != null) {
            entity.setCep(dto.getCep());
        }
    }

}