package br.com.fiap.TechChallenger.controller.logado;

import br.com.fiap.TechChallenger.api.EnderecoLogadoApi;
import br.com.fiap.TechChallenger.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.dto.EnderecoEditDTO;
import br.com.fiap.TechChallenger.dto.response.EnderecoResponse;
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
@RequestMapping("/usuarioLogado/endereco")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EnderecoLogadoController implements EnderecoLogadoApi {

    private final CriarEnderecoService criarEnderecoService;
    private final EditarEnderecoService editarenderecoService;
    private final DeletarEnderecoService deletarEnderecoService;
    private final BuscarEnderecoService buscarEnderecoService;

    @Override
    public ResponseEntity<?> criarEndereco(@Valid @RequestBody final EnderecoDTO criarEnderecoRequest, final HttpServletRequest request) {
        return criarEnderecoService.criarEnderecos(criarEnderecoRequest, request);
    }

    @Override
    public ResponseEntity<?> editarEndereco(@Valid @RequestBody final EnderecoEditDTO editarEnderecoRequest, final HttpServletRequest request) {
        return editarenderecoService.editarEnderecos(editarEnderecoRequest, request);
    }

    @Override
    public ResponseEntity<?> deleteEndereco(final Long enderecoId) {
        return deletarEnderecoService.deletarEnderecoById(enderecoId);
    }

    @Override
    public ResponseEntity<List<EnderecoResponse>> buscarEnderecos(HttpServletRequest request) {
        return buscarEnderecoService.buscarEnderecos(request);
    }

}
