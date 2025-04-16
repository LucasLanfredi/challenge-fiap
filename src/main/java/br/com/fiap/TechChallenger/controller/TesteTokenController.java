package br.com.fiap.TechChallenger.controller;

import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.security.PortalService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuarioLogado")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TesteTokenController {

    private final PortalService portalService;

    @GetMapping
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") <- Configurar para começar a usar TODO
    public ResponseEntity<UsuarioLogado> buscarUsuarioLogado(HttpServletRequest request) {
        try {
            UsuarioLogado usuarioLogado = portalService.getUsuarioLogado(request);
            return ResponseEntity.ok(usuarioLogado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
