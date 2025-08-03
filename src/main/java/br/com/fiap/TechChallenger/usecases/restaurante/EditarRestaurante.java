package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.*;
import br.com.fiap.TechChallenger.domains.dto.HorariosDeFuncionamentoDTO;
import br.com.fiap.TechChallenger.domains.dto.HorariosDto;
import br.com.fiap.TechChallenger.domains.dto.RestauranteDto;
import br.com.fiap.TechChallenger.domains.dto.response.RestauranteResponse;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
import br.com.fiap.TechChallenger.usecases.exception.TipoUsuarioInvalidoException;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditarRestaurante {

    private final Autenticacao autenticacao;
    private final RestauranteRepository restauranteRepository;

    public ResponseEntity<?> execute(final RestauranteDto restauranteDto,
                                     final HttpServletRequest request, final Long id) {

        try {
            autenticacao.getUsuarioLogado(request);
            restauranteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

            if (!restauranteDto.getDonoDoRestaurante().getTipoUsuario().equals(TipoUsuario.DONO_RESTAURANTE))
                throw new TipoUsuarioInvalidoException("Tipo de usuário inválido para alteração de cadastro");

           Restaurante restauranteAtualizado = mapearAtualizacao(restauranteDto);
           restauranteRepository.save(restauranteAtualizado);
           return ResponseEntity.status(HttpStatus.OK).body(RestauranteResponse.converte(restauranteAtualizado));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private Restaurante mapearAtualizacao(final RestauranteDto dto) {
        return Restaurante.builder()
                .nome(dto.getNome())
                .tipoDeCozinha(dto.getTipoDeCozinha())
                .horarioDeFuncionamento(mapearHorarios(dto.getHorariosDeFuncionamentoDTO()))
                .build();
    }

    private HorariosDeFuncionamento mapearHorarios(HorariosDeFuncionamentoDTO dto) {
        return HorariosDeFuncionamento.builder()
                .diasUteis(mapearHorario(dto.getDiasUteis()))
                .sabado(mapearHorario(dto.getSabado()))
                .domingoEFeriado(mapearHorario(dto.getDomingoEFeriado()))
                .build();
    }

    private Horarios mapearHorario(HorariosDto dto) {
        if (dto == null)
            return null;
        return Horarios.builder()
                .abertura(dto.getAbertura())
                .fechamento(dto.getFechamento())
                .build();
    }
}
