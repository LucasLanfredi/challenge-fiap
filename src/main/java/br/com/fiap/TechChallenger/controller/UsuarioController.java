package br.com.fiap.TechChallenger.controller;

import br.com.fiap.TechChallenger.dto.MessageResponse;
import br.com.fiap.TechChallenger.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.security.AutenticacaoService;
import br.com.fiap.TechChallenger.service.UsuarioService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final AutenticacaoService autenticacaoService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody UsuarioDTO criarUsuarioRequest) {
        if (usuarioService.existsByLogin(criarUsuarioRequest.getLogin())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erro: Login já existente!"));
        }
        if (usuarioService.existsByEmail(criarUsuarioRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erro: Email já existente!"));
        }
        final Long idUsuario = usuarioService.registrar(criarUsuarioRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario cadastrado com sucesso! ID: " + idUsuario.toString());
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody UsuarioEditDTO editarUsuarioRequest, final HttpServletRequest request) throws AuthException {
        final Long idUsuario = usuarioService.editarUsuario(editarUsuarioRequest, request);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario editado com sucesso! ID: " + idUsuario.toString());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUsuario(final HttpServletRequest request) throws AuthException {
        usuarioService.deleteUsuario(request);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso!");
    }

    @GetMapping("/logado")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") <- Configurar para começar a usar TODO
    public ResponseEntity<UsuarioLogado> buscarUsuarioLogado(final HttpServletRequest request) {
        try {
            UsuarioLogado usuarioLogado = autenticacaoService.getUsuarioLogado(request);
            return ResponseEntity.ok(usuarioLogado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
