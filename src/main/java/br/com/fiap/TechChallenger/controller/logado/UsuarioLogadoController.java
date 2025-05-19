package br.com.fiap.TechChallenger.controller.logado;

import br.com.fiap.TechChallenger.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.dto.response.UsuarioResponse;
import br.com.fiap.TechChallenger.service.exception.SenhaInvalidaException;
import br.com.fiap.TechChallenger.service.usuario.*;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarioLogado")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioLogadoController {

    private final EditarUsuarioService editarUsuarioService;
    private final DeletarUsuarioService deletarUsuarioService;
    private final BuscarUsuarioService buscarUsuarioService;
    private final TrocarSenhaService trocarSenhaService;

    @PutMapping()
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody final UsuarioEditDTO editarUsuarioRequest, final HttpServletRequest request) throws AuthException {
         return editarUsuarioService.editar(editarUsuarioRequest, request);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUsuario(final HttpServletRequest request) throws AuthException {
        return deletarUsuarioService.deletar(request);
    }

    @GetMapping()
    public ResponseEntity<UsuarioResponse> buscarUsuarioLogado(final HttpServletRequest request) {
        return buscarUsuarioService.buscar(request);
    }

    @PutMapping("/senha")
    public ResponseEntity<?> trocarSenha(@Valid @RequestBody final TrocaSenhaDto trocaSenhaDto) throws SenhaInvalidaException {
        return trocarSenhaService.execute(trocaSenhaDto);
    }

}
