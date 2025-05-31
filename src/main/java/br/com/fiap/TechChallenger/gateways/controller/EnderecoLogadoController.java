package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.gateways.controller.api.EnderecoLogadoApi;
import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.dto.EnderecoEditDTO;
import br.com.fiap.TechChallenger.domains.dto.response.EnderecoResponse;
import br.com.fiap.TechChallenger.usecases.endereco.BuscarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.CriarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.DeletarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.EditarEndereco;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarioLogado/endereco")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EnderecoLogadoController implements EnderecoLogadoApi {

    private final CriarEndereco criarEndereco;
    private final EditarEndereco editarendereco;
    private final DeletarEndereco deletarEndereco;
    private final BuscarEndereco buscarEndereco;

    @PostMapping()
    @Override
    public ResponseEntity<?> criarEndereco(@Valid @RequestBody final EnderecoDTO criarEnderecoRequest, final HttpServletRequest request) {
        return criarEndereco.criarEnderecos(criarEnderecoRequest, request);
    }

    @PutMapping()
    @Override
    public ResponseEntity<?> editarEndereco(@Valid @RequestBody final EnderecoEditDTO editarEnderecoRequest, final HttpServletRequest request) {
        return editarendereco.editarEnderecos(editarEnderecoRequest, request);
    }

    @DeleteMapping("/enderecoId/{enderecoId}")
    @Override
    public ResponseEntity<?> deleteEndereco(@PathVariable final Long enderecoId, final HttpServletRequest request) {
        return deletarEndereco.deletarEnderecos(enderecoId, request);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<EnderecoResponse>> buscarEnderecos(HttpServletRequest request) {
        return buscarEndereco.buscarEnderecos(request);
    }

}
