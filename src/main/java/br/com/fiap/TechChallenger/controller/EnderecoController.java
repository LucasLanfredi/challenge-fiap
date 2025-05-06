package br.com.fiap.TechChallenger.controller;

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
