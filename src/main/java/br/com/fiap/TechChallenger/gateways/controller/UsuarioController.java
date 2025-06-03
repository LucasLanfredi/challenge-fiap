package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.gateways.controller.api.UsuarioApi;
import br.com.fiap.TechChallenger.domains.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.domains.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.domains.dto.response.UsuarioResponse;
import br.com.fiap.TechChallenger.usecases.exception.SenhaInvalidaException;
import br.com.fiap.TechChallenger.usecases.usuario.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioController implements UsuarioApi {

    private final CriarUsuario criarUsuario;
    private final EditarUsuario editarUsuario;
    private final DeletarUsuario deletarUsuario;
    private final BuscarUsuario buscarUsuario;
    private final TrocarSenha trocarSenha;

    @PostMapping
    @Override
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody final UsuarioDTO criarUsuarioRequest) {
        return criarUsuario.criar(criarUsuarioRequest);
    }

    @PutMapping("/userId/{userId}")
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody final UsuarioEditDTO editarUsuarioRequest, @PathVariable("userId") Long userId) {
         return editarUsuario.editarUsuarioByUserId(editarUsuarioRequest, userId);
    }

    @DeleteMapping("/userId/{userId}")
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long userId) {
        return deletarUsuario.deleteUsuarioById(userId);
    }

    @GetMapping("/userId/{userId}")
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<UsuarioResponse> buscarUsuarioById(@PathVariable("userId") Long userId) {
        return buscarUsuario.getUsuarioResponseById(userId);
    }

    @GetMapping()
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioResponse>> buscarTodosUsuarios() {
        return buscarUsuario.buscarTodosUsuarios();
    }

    @PutMapping("/senha")
    @Override
    public ResponseEntity<?> trocarSenha(@Valid @RequestBody final TrocaSenhaDto trocaSenhaDto) throws SenhaInvalidaException {
        return trocarSenha.execute(trocaSenhaDto);
    }

}
