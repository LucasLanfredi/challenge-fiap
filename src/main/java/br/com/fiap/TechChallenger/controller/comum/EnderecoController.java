package br.com.fiap.TechChallenger.controller.comum;

import br.com.fiap.TechChallenger.api.EnderecoApi;
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
public class EnderecoController implements EnderecoApi {

    private final CriarEnderecoService criarEnderecoService;
    private final EditarEnderecoService editarEnderecoService;
    private final DeletarEnderecoService deletarEnderecoService;
    private final BuscarEnderecoService buscarEnderecoService;

    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> criarEndereco(
            @Valid @RequestBody EnderecoDTO criarEnderecoRequest,
            @PathVariable Long userId) {
        return criarEnderecoService.criarEnderecosByUserId(criarEnderecoRequest, userId);
    }

    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> editarEndereco(@Valid @RequestBody EnderecoEditDTO editarEnderecoRequest) {
        return editarEnderecoService.editarEnderecosByEnderecoId(editarEnderecoRequest);
    }

    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> deleteEndereco(@PathVariable Long enderecoId) {
        return deletarEnderecoService.deletarEnderecoById(enderecoId);
    }

    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<EnderecoResponse>> buscarEnderecos(@PathVariable Long userId) {
        return buscarEnderecoService.buscarEnderecosByUserId(userId);
    }

    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<EnderecoResponse>> buscarTodosEnderecos() {
        return buscarEnderecoService.buscarTodosEnderecos();
    }
}
