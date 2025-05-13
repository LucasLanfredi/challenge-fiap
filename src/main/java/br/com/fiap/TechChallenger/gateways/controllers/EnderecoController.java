package br.com.fiap.TechChallenger.gateways.controllers;

import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.dto.EnderecoEditDTO;
import br.com.fiap.TechChallenger.domains.entity.Endereco;
import br.com.fiap.TechChallenger.usecases.endereco.BuscarEnderecoService;
import br.com.fiap.TechChallenger.usecases.endereco.CriarEnderecoService;
import br.com.fiap.TechChallenger.usecases.endereco.DeletarEnderecoService;
import br.com.fiap.TechChallenger.usecases.endereco.EditarEnderecoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EnderecoController {

    private final CriarEnderecoService criarEnderecoService;
    private final EditarEnderecoService editarenderecoService;
    private final DeletarEnderecoService deletarEnderecoService;
    private final BuscarEnderecoService buscarEnderecoService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarEndereco(@Valid @RequestBody final EnderecoDTO criarEnderecoRequest, final HttpServletRequest request) {
        return criarEnderecoService.criarEnderecos(criarEnderecoRequest, request);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarEndereco(@Valid @RequestBody final EnderecoEditDTO editarEnderecoRequest, final HttpServletRequest request) {
        return editarenderecoService.editarEnderecos(editarEnderecoRequest, request);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEndereco(final Long enderecoId) {
        return deletarEnderecoService.deletarEnderecos(enderecoId);
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> buscarEnderecos(HttpServletRequest request) {
        return buscarEnderecoService.buscarEnderecos(request);
    }

}
