package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.domains.dto.RestauranteDto;
import br.com.fiap.TechChallenger.domains.dto.RestauranteRequestEditDto;
import br.com.fiap.TechChallenger.domains.dto.response.RestauranteResponse;
import br.com.fiap.TechChallenger.gateways.controller.api.RestauranteApi;
import br.com.fiap.TechChallenger.usecases.restaurante.BuscarRestaurante;
import br.com.fiap.TechChallenger.usecases.restaurante.CadastrarRestaurante;
import br.com.fiap.TechChallenger.usecases.restaurante.EditarRestaurante;
import br.com.fiap.TechChallenger.usecases.restaurante.ExcluirRestaurante;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RestauranteController implements RestauranteApi {

    private final CadastrarRestaurante cadastrarRestaurante;
    private final BuscarRestaurante buscarRestaurante;
    private final EditarRestaurante editarRestaurante;
    private final ExcluirRestaurante excluirRestaurante;

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> cadastrarRestaurante(@Valid @RequestBody RestauranteDto restauranteDto,
                                                  final HttpServletRequest request) {
        return cadastrarRestaurante.execute(restauranteDto, request);
    }

    @Override
    @GetMapping("/todos")
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<List<RestauranteResponse>> buscarTodosRestaurantes() {
        return buscarRestaurante.execute();
    }

    @Override
    @GetMapping("/idRestaurante/{id}")
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<RestauranteResponse> buscarRestaurantePorId(Long id) {
        return buscarRestaurante.buscaPorId(id);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> editarRestaurante(@Valid @RequestBody RestauranteRequestEditDto restauranteDto,
                                               final HttpServletRequest request,
                                               @PathVariable Long id) {
        return editarRestaurante.execute(restauranteDto, request, id);
    }

    @DeleteMapping("/{id}")
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> excluirRestaurante(@PathVariable Long id, final HttpServletRequest request) {
        return excluirRestaurante.execute(id, request);
    }

}
