package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.*;
import br.com.fiap.TechChallenger.domains.dto.RestauranteRequestEditDto;
import br.com.fiap.TechChallenger.domains.dto.response.RestauranteResponse;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.exception.TipoUsuarioInvalidoException;
import br.com.fiap.TechChallenger.usecases.exception.UsuarioNaoEncontradoException;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EditarRestaurante {

    private final Autenticacao autenticacao;
    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<?> execute(final RestauranteRequestEditDto restauranteDto,
                                     final HttpServletRequest request,
                                     final Long idRestaurante) {
        try {
            autenticacao.getUsuarioLogado(request);

            Restaurante restauranteAtualizado = restauranteRepository.findById(idRestaurante)
                    .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

            final Usuario dono = usuarioRepository.findById(restauranteDto.getDonoRestauranteId())
                    .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário dono não encontrado"));

            if (!dono.getTipoUsuario().equals(TipoUsuario.DONO_RESTAURANTE)) {
                throw new TipoUsuarioInvalidoException("Tipo de usuário inválido para alteração de cadastro");
            }

            modelMapper.typeMap(RestauranteRequestEditDto.class, Restaurante.class)
                    .addMappings(mapper -> {
                        mapper.skip(Restaurante::setIdRestaurante);
                        mapper.skip(Restaurante::setDonoDoRestaurante);
                        mapper.when(notNull()).map(RestauranteRequestEditDto::getHorariosDeFuncionamentoDTO,
                                Restaurante::setHorarioDeFuncionamento);
                    });

            modelMapper.map(restauranteDto, restauranteAtualizado);
            restauranteAtualizado.setDonoDoRestaurante(dono);

            Restaurante restauranteSalvo = restauranteRepository.save(restauranteAtualizado);
            return ResponseEntity.ok(RestauranteResponse.converte(restauranteSalvo));

        } catch (RuntimeException | TipoUsuarioInvalidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocorreu um erro inesperado ao atualizar o restaurante");
        }
    }

    private Condition<?, ?> notNull() {
        return ctx -> ctx.getSource() != null;
    }

}
