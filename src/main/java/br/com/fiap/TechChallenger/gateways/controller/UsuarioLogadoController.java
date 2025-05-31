package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.gateways.controller.api.UsuarioLogadoApi;
import br.com.fiap.TechChallenger.domains.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.domains.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.domains.dto.response.UsuarioResponse;
import br.com.fiap.TechChallenger.usecases.exception.SenhaInvalidaException;
import br.com.fiap.TechChallenger.usecases.usuario.*;
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

    private final EditarUsuario editarUsuario;
    private final DeletarUsuario deletarUsuario;
    private final BuscarUsuario buscarUsuario;
    private final TrocarSenha trocarSenha;

    @PutMapping()
    @Override
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody final UsuarioEditDTO editarUsuarioRequest, final HttpServletRequest request) throws AuthException {
         return editarUsuario.editar(editarUsuarioRequest, request);
    }

    @DeleteMapping
    @Override
    public ResponseEntity<?> deleteUsuario(final HttpServletRequest request) throws AuthException {
        return deletarUsuario.deletar(request);
    }

    @GetMapping()
    @Override
    public ResponseEntity<UsuarioResponse> buscarUsuarioLogado(final HttpServletRequest request) {
        return buscarUsuario.buscar(request);
    }

    @PutMapping("/senha")
    @Override
    public ResponseEntity<?> trocarSenha(@Valid @RequestBody final TrocaSenhaDto trocaSenhaDto) throws SenhaInvalidaException {
        return trocarSenha.execute(trocaSenhaDto);
    }

}
