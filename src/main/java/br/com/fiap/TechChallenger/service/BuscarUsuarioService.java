package br.com.fiap.TechChallenger.service;

import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.security.AutenticacaoService;
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
            UsuarioLogado usuarioLogado = autenticacaoService.getUsuarioLogado(request);
            return ResponseEntity.ok(usuarioLogado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}