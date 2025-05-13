package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.entity.UsuarioLogado;
import br.com.fiap.TechChallenger.usecases.security.AutenticacaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarUsuarioService {

    private final AutenticacaoService autenticacaoService;

    public ResponseEntity<UsuarioLogado> buscar(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(autenticacaoService.getUsuarioLogado(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}