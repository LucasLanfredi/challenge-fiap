package br.com.fiap.TechChallenger.controller;

import br.com.fiap.TechChallenger.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.service.usuario.BuscarUsuarioService;
import br.com.fiap.TechChallenger.service.usuario.CriarUsuarioService;
import br.com.fiap.TechChallenger.service.usuario.DeletarUsuarioService;
import br.com.fiap.TechChallenger.service.usuario.EditarUsuarioService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioController {

    private final CriarUsuarioService criarUsuarioService;
    private final EditarUsuarioService editarUsuarioService;
    private final DeletarUsuarioService deletarUsuarioService;
    private final BuscarUsuarioService buscarUsuarioService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody final UsuarioDTO criarUsuarioRequest) {
        return criarUsuarioService.criar(criarUsuarioRequest);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody final UsuarioEditDTO editarUsuarioRequest, final HttpServletRequest request) throws AuthException {
         return editarUsuarioService.editar(editarUsuarioRequest, request);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUsuario(final HttpServletRequest request) throws AuthException {
        return deletarUsuarioService.deletar(request);
    }

    @GetMapping("/logado")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") <- Configurar para começar a usar TODO
    public ResponseEntity<UsuarioLogado> buscarUsuarioLogado(final HttpServletRequest request) {
        return buscarUsuarioService.buscar(request);
    }

}
