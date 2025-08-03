package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.*;
import br.com.fiap.TechChallenger.domains.dto.*;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.exception.TipoUsuarioInvalidoException;
import br.com.fiap.TechChallenger.usecases.exception.UsuarioNaoEncontradoException;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarRestaurante {

    private final Autenticacao autenticacao;
    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<?> execute(final RestauranteDto restauranteDto, HttpServletRequest request) {

        try {
            autenticacao.getUsuarioLogado(request);
            return cadastrarRestaurante(restauranteDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> cadastrarRestaurante(final RestauranteDto restauranteDto) throws TipoUsuarioInvalidoException {
        Usuario dono = usuarioRepository.findById(restauranteDto.getDonoRestauranteId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário dono não encontrado"));

        if (!dono.getTipoUsuario().equals(TipoUsuario.DONO_RESTAURANTE)) {
            throw new TipoUsuarioInvalidoException("Tipo de usuário inválido para cadastro de restaurante");
        }

        final Restaurante entidadeRestaurante = Restaurante.builder()
                .nome(restauranteDto.getNome())
                .endereco(converteEndereco(restauranteDto.getEnderecoDTO()))
                .tipoDeCozinha(restauranteDto.getTipoDeCozinha())
                .horarioDeFuncionamento(converteDias(restauranteDto.getHorariosDeFuncionamentoDTO()))
                .donoDoRestaurante(dono)
                .build();
        try {
            if (!entidadeRestaurante.getDonoDoRestaurante().getTipoUsuario().equals(TipoUsuario.DONO_RESTAURANTE))
                throw new TipoUsuarioInvalidoException("Tipo de usuário inválido para cadastro de restaurante");

            Restaurante save = restauranteRepository.save(entidadeRestaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(save.getIdRestaurante());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    private Usuario converteUsuario(final UsuarioDTO donoDoRestaurante) {
        return Usuario.builder()
                .nome(donoDoRestaurante.getNome())
                .email(donoDoRestaurante.getEmail())
                .tipoUsuario(donoDoRestaurante.getTipoUsuario())
                .build();
    }

    private HorariosDeFuncionamento converteDias(final HorariosDeFuncionamentoDTO horariosDeFuncionamentoDTO) {
        return HorariosDeFuncionamento.builder()
                .diasUteis(converteHorarios(horariosDeFuncionamentoDTO.getDiasUteis()))
                .sabado(converteHorarios(horariosDeFuncionamentoDTO.getSabado()))
                .domingoEFeriado(converteHorarios(horariosDeFuncionamentoDTO.getDomingoEFeriado()))
                .build();
    }

    private Horarios converteHorarios(final HorariosDto horariosDto) {
        return Horarios.builder()
                .abertura(horariosDto.getAbertura())
                .fechamento(horariosDto.getFechamento())
                .build();
    }

    private Endereco converteEndereco(final EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }
}
