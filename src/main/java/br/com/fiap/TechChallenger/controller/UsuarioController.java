package br.com.fiap.TechChallenger.controller;

import br.com.fiap.TechChallenger.api.UsuarioApi;
import br.com.fiap.TechChallenger.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.service.exception.SenhaInvalidaException;
import br.com.fiap.TechChallenger.service.usuario.*;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioController implements UsuarioApi {

    private final CriarUsuarioService criarUsuarioService;
    private final EditarUsuarioService editarUsuarioService;
    private final DeletarUsuarioService deletarUsuarioService;
    private final BuscarUsuarioService buscarUsuarioService;
    private final TrocarSenhaService trocarSenhaService;

    public ResponseEntity<?> criarUsuario(@Valid @RequestBody final UsuarioDTO criarUsuarioRequest) {
        return criarUsuarioService.criar(criarUsuarioRequest);
    }

    public ResponseEntity<?> editarUsuario(@Valid @RequestBody final UsuarioEditDTO editarUsuarioRequest, final HttpServletRequest request) throws AuthException {
         return editarUsuarioService.editar(editarUsuarioRequest, request);
    }

    public ResponseEntity<?> deleteUsuario(final HttpServletRequest request) throws AuthException {
        return deletarUsuarioService.deletar(request);
    }

    public ResponseEntity<UsuarioLogado> buscarUsuarioLogado(final HttpServletRequest request) {
        return buscarUsuarioService.buscar(request);
    }

    public ResponseEntity<?> trocarSenha(@Valid @RequestBody final TrocaSenhaDto trocaSenhaDto) throws SenhaInvalidaException {
        return trocarSenhaService.execute(trocaSenhaDto);
    }

}
