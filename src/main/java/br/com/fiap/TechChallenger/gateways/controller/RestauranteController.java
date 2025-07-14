package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.domains.dto.RestauranteDto;
import br.com.fiap.TechChallenger.gateways.controller.api.RestauranteApi;
import br.com.fiap.TechChallenger.usecases.restaurante.CadastrarRestaurante;
import br.com.fiap.TechChallenger.usecases.restaurante.ExcluirRestaurante;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurante")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RestauranteController implements RestauranteApi {

    private final CadastrarRestaurante cadastrarRestaurante;
    private final ExcluirRestaurante excluirRestaurante;

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> cadastrarRestaurante(@Valid @RequestBody RestauranteDto restauranteDto,
                                                  final HttpServletRequest request) {
        return cadastrarRestaurante.execute(restauranteDto, request);
    }

    @DeleteMapping
    @Override
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<?> excluirRestaurante(@PathVariable Long id, final HttpServletRequest request) {
        return excluirRestaurante.execute(id, request);
    }

}
