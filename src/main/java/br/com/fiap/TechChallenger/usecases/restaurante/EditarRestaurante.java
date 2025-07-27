package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.*;
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

           Restaurante restauranteAtualizado = updateRestauranteFromDto(restauranteDto);
           restauranteRepository.save(restauranteAtualizado);
           return ResponseEntity.status(HttpStatus.OK).body(RestauranteResponse.converte(restauranteAtualizado));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private Restaurante updateRestauranteFromDto(final RestauranteDto restauranteDto) {
        return Restaurante.builder()
                .nome(restauranteDto.getNome() != null ? restauranteDto.getNome() : null)
                .endereco(Endereco.builder()
                        .rua(restauranteDto.getEnderecoDTO().getRua() != null ? restauranteDto.getEnderecoDTO().getRua() : null)
                        .numero(restauranteDto.getEnderecoDTO().getNumero() != null ? restauranteDto.getEnderecoDTO().getNumero() : null)
                        .cidade(restauranteDto.getEnderecoDTO().getCidade() != null ? restauranteDto.getEnderecoDTO().getCidade() : null)
                        .estado(restauranteDto.getEnderecoDTO().getEstado() != null ? restauranteDto.getEnderecoDTO().getEstado() : null)
                        .cep(restauranteDto.getEnderecoDTO().getCep() != null ? restauranteDto.getEnderecoDTO().getCep() : null)
                        .build())
                .tipoDeCozinha(restauranteDto.getTipoDeCozinha() != null ? restauranteDto.getTipoDeCozinha() : null)
                .horarioDeFuncionamento(HorariosDeFuncionamento.builder()
                        .diasUteis(Horarios.builder()
                                .abertura(restauranteDto.getHorariosDeFuncionamentoDTO().getDiasUteis().getAbertura() != null ?
                                        restauranteDto.getHorariosDeFuncionamentoDTO().getDiasUteis().getAbertura() : null)
                                .fechamento(restauranteDto.getHorariosDeFuncionamentoDTO().getDiasUteis().getFechamento() != null ?
                                        restauranteDto.getHorariosDeFuncionamentoDTO().getDiasUteis().getFechamento() : null)
                                .build())
                        .sabado(Horarios.builder()
                                .abertura(restauranteDto.getHorariosDeFuncionamentoDTO().getSabado().getAbertura() != null ?
                                        restauranteDto.getHorariosDeFuncionamentoDTO().getSabado().getAbertura() : null)
                                .fechamento(restauranteDto.getHorariosDeFuncionamentoDTO().getSabado().getFechamento() != null ?
                                        restauranteDto.getHorariosDeFuncionamentoDTO().getSabado().getFechamento() : null)
                                .build())
                        .domingoEFeriado(Horarios.builder()
                                .abertura(restauranteDto.getHorariosDeFuncionamentoDTO().getDomingoEFeriado().getAbertura() != null ?
                                        restauranteDto.getHorariosDeFuncionamentoDTO().getDomingoEFeriado().getAbertura() : null)
                                .fechamento(restauranteDto.getHorariosDeFuncionamentoDTO().getDomingoEFeriado().getFechamento() != null ?
                                        restauranteDto.getHorariosDeFuncionamentoDTO().getDomingoEFeriado().getFechamento(): null)
                                .build())
                        .build())
                .DonoDoRestaurante(Usuario.builder()
                        .nome(restauranteDto.getDonoDoRestaurante().getNome() != null ? restauranteDto.getDonoDoRestaurante().getNome() : null)
                        .email(restauranteDto.getDonoDoRestaurante().getEmail() != null ? restauranteDto.getDonoDoRestaurante().getEmail(): null)
                        .tipoUsuario(restauranteDto.getDonoDoRestaurante().getTipoUsuario() != null ? restauranteDto.getDonoDoRestaurante().getTipoUsuario() : null)
                        .build())
                .build();

    }
}
