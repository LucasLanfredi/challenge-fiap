package br.com.fiap.TechChallenger.controller.comum;

import br.com.fiap.TechChallenger.api.UsuarioApi;
import br.com.fiap.TechChallenger.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.dto.response.UsuarioResponse;
import br.com.fiap.TechChallenger.service.exception.SenhaInvalidaException;
import br.com.fiap.TechChallenger.service.usuario.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioController implements UsuarioApi {

    private final CriarUsuarioService criarUsuarioService;
    private final EditarUsuarioService editarUsuarioService;
    private final DeletarUsuarioService deletarUsuarioService;
    private final BuscarUsuarioService buscarUsuarioService;
    private final TrocarSenhaService trocarSenhaService;

    @PostMapping
    @Override
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody final UsuarioDTO criarUsuarioRequest) {
        return criarUsuarioService.criar(criarUsuarioRequest);
    }

    @PutMapping("/userId/{userId}")
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody final UsuarioEditDTO editarUsuarioRequest, @PathVariable("userId") Long userId) {
         return editarUsuarioService.editarUsuarioByUserId(editarUsuarioRequest, userId);
    }

    @DeleteMapping("/userId/{userId}")
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long userId) {
        return deletarUsuarioService.deleteUsuarioById(userId);
    }

    @GetMapping("/userId/{userId}")
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponse> buscarUsuarioById(@PathVariable("userId") Long userId) {
        return buscarUsuarioService.getUsuarioResponseById(userId);
    }

    @PutMapping("/senha")
    @Override
    public ResponseEntity<?> trocarSenha(@Valid @RequestBody final TrocaSenhaDto trocaSenhaDto) throws SenhaInvalidaException {
        return trocarSenhaService.execute(trocaSenhaDto);
    }

}
