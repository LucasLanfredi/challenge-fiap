package br.com.fiap.TechChallenger.controller;

import br.com.fiap.TechChallenger.api.EnderecoApi;
import br.com.fiap.TechChallenger.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.dto.EnderecoEditDTO;
import br.com.fiap.TechChallenger.entity.Endereco;
import br.com.fiap.TechChallenger.service.endereco.BuscarEnderecoService;
import br.com.fiap.TechChallenger.service.endereco.CriarEnderecoService;
import br.com.fiap.TechChallenger.service.endereco.DeletarEnderecoService;
import br.com.fiap.TechChallenger.service.endereco.EditarEnderecoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EnderecoController implements EnderecoApi {

    private final CriarEnderecoService criarEnderecoService;
    private final EditarEnderecoService editarenderecoService;
    private final DeletarEnderecoService deletarEnderecoService;
    private final BuscarEnderecoService buscarEnderecoService;

    public ResponseEntity<?> criarEndereco(@Valid @RequestBody final EnderecoDTO criarEnderecoRequest, final HttpServletRequest request) {
        return criarEnderecoService.criarEnderecos(criarEnderecoRequest, request);
    }

    public ResponseEntity<?> editarEndereco(@Valid @RequestBody final EnderecoEditDTO editarEnderecoRequest, final HttpServletRequest request) {
        return editarenderecoService.editarEnderecos(editarEnderecoRequest, request);
    }

    public ResponseEntity<?> deleteEndereco(final Long enderecoId) {
        return deletarEnderecoService.deletarEnderecos(enderecoId);
    }

    public ResponseEntity<List<Endereco>> buscarEnderecos(HttpServletRequest request) {
        return buscarEnderecoService.buscarEnderecos(request);
    }

}
