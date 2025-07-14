package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.dto.*;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExcluirRestaurante {

    private final Autenticacao autenticacao;
    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<?> execute(final Long id, HttpServletRequest request) {

        try {
            final UsuarioLogado usuariologado = autenticacao.getUsuarioLogado(request);
            restauranteRepository.findById(id).orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
            restauranteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
