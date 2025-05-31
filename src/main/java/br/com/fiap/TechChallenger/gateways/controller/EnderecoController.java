package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.gateways.controller.api.EnderecoApi;
import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.dto.EnderecoEditDTO;
import br.com.fiap.TechChallenger.domains.dto.response.EnderecoResponse;
import br.com.fiap.TechChallenger.usecases.endereco.BuscarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.CriarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.DeletarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.EditarEndereco;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario/endereco")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EnderecoController implements EnderecoApi {

    private final CriarEndereco criarEndereco;
    private final EditarEndereco editarEndereco;
    private final DeletarEndereco deletarEndereco;
    private final BuscarEndereco buscarEndereco;

    @PostMapping("/userId/{userId}")
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> criarEndereco(
            @Valid @RequestBody EnderecoDTO criarEnderecoRequest,
            @PathVariable Long userId) {
        return criarEndereco.criarEnderecosByUserId(criarEnderecoRequest, userId);
    }

    @PutMapping
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> editarEndereco(@Valid @RequestBody EnderecoEditDTO editarEnderecoRequest) {
        return editarEndereco.editarEnderecosByEnderecoId(editarEnderecoRequest);
    }

    @DeleteMapping("/enderecoId/{enderecoId}")
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> deleteEndereco(@PathVariable Long enderecoId) {
        return deletarEndereco.deletarEnderecoById(enderecoId);
    }

    @GetMapping("/userId/{userId}")
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<EnderecoResponse>> buscarEnderecosByUserId(@PathVariable Long userId) {
        return buscarEndereco.buscarEnderecosByUserId(userId);
    }

    @GetMapping("/todos")
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<EnderecoResponse>> buscarTodosEnderecos() {
        return buscarEndereco.buscarTodosEnderecos();
    }
}
