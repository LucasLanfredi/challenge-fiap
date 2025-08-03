package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.dto.response.RestauranteResponse;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarRestaurante {

    private final Autenticacao autenticacao;
    private final RestauranteRepository restauranteRepository;

    public ResponseEntity<RestauranteResponse> buscaPorId(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        return ResponseEntity.ok(RestauranteResponse.converte(restaurante));

    }

    public ResponseEntity<List<RestauranteResponse>> execute() {
        List<RestauranteResponse> todosRestaurantes =
                restauranteRepository.findAll()
                        .stream()
                        .map(RestauranteResponse::converte)
                        .toList();
        return ResponseEntity.ok(todosRestaurantes);
    }

}
