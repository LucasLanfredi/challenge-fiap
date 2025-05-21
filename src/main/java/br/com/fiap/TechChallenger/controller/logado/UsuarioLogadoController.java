package br.com.fiap.TechChallenger.controller.logado;

import br.com.fiap.TechChallenger.api.UsuarioLogadoApi;
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
public class UsuarioLogadoController implements UsuarioLogadoApi {

    private final EditarUsuarioService editarUsuarioService;
    private final DeletarUsuarioService deletarUsuarioService;
    private final BuscarUsuarioService buscarUsuarioService;
    private final TrocarSenhaService trocarSenhaService;

    @Override
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody final UsuarioEditDTO editarUsuarioRequest, final HttpServletRequest request) throws AuthException {
         return editarUsuarioService.editar(editarUsuarioRequest, request);
    }

    @Override
    public ResponseEntity<?> deleteUsuario(final HttpServletRequest request) throws AuthException {
        return deletarUsuarioService.deletar(request);
    }

    @Override
    public ResponseEntity<UsuarioResponse> buscarUsuarioLogado(final HttpServletRequest request) {
        return buscarUsuarioService.buscar(request);
    }

    @Override
    public ResponseEntity<?> trocarSenha(@Valid @RequestBody final TrocaSenhaDto trocaSenhaDto) throws SenhaInvalidaException {
        return trocarSenhaService.execute(trocaSenhaDto);
    }

}
