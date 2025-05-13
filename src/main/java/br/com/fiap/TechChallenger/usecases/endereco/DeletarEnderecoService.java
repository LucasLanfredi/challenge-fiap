package br.com.fiap.TechChallenger.usecases.endereco;

import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarEnderecoService {

    private final EnderecoRepository enderecoRepository;

    public ResponseEntity<?> deletarEnderecos(Long enderecoId) {
        try {
            enderecoRepository.deleteEnderecoById(enderecoId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}