package br.com.fiap.TechChallenger.controller.comum;

import br.com.fiap.TechChallenger.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.dto.EnderecoEditDTO;
import br.com.fiap.TechChallenger.dto.response.EnderecoResponse;
import br.com.fiap.TechChallenger.service.endereco.BuscarEnderecoService;
import br.com.fiap.TechChallenger.service.endereco.CriarEnderecoService;
import br.com.fiap.TechChallenger.service.endereco.DeletarEnderecoService;
import br.com.fiap.TechChallenger.service.endereco.EditarEnderecoService;
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
public class EnderecoController {

    private final CriarEnderecoService criarEnderecoService;
    private final EditarEnderecoService editarenderecoService;
    private final DeletarEnderecoService deletarEnderecoService;
    private final BuscarEnderecoService buscarEnderecoService;

    @PostMapping("/userId/{userId}")
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> criarEndereco(@Valid @RequestBody final EnderecoDTO criarEnderecoRequest, @PathVariable final Long userId) {
        return criarEnderecoService.criarEnderecosByUserId(criarEnderecoRequest, userId);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> editarEndereco(@Valid @RequestBody final EnderecoEditDTO editarEnderecoRequest) {
        return editarenderecoService.editarEnderecosByEnderecoId(editarEnderecoRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> deleteEndereco(final Long enderecoId) {
        return deletarEnderecoService.deletarEnderecoById(enderecoId);
    }

    @GetMapping("/userId/{userId}")
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<EnderecoResponse>> buscarEnderecos(@PathVariable final Long userId) {
        return buscarEnderecoService.buscarEnderecosByUserId(userId);
    }

    @GetMapping("/todos")
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<EnderecoResponse>> buscarTodosEnderecos() {
        return buscarEnderecoService.buscarTodosEnderecos();
    }

}
