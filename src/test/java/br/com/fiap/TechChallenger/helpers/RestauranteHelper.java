package br.com.fiap.TechChallenger.helpers;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.HorariosDeFuncionamento;
import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.dto.RestauranteDto;
import br.com.fiap.TechChallenger.domains.dto.UsuarioDTO;

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEnderecoDTO;
import static br.com.fiap.TechChallenger.helpers.HorarioFuncionamentoHelper.gerarHorariosDeFuncionamentoDto;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.*;

public class RestauranteHelper {

    public static Restaurante gerarRestaurante(Endereco endereco, Usuario usuario) {
        return gerarRestaurante(endereco, usuario, "Italiana");
    }

    public static Restaurante gerarRestaurante(Endereco endereco, Usuario usuario, String tipoDeCozinha) {
        return new Restaurante(
                1L,
                "Restaurante Teste",
                endereco,
                tipoDeCozinha,
                HorariosDeFuncionamento.builder().build(),
                usuario
        );
    }

    public static RestauranteDto gerarRestauranteDto() {
        RestauranteDto restaurante = new RestauranteDto();
        restaurante.setNome("Restaurante Teste");
        restaurante.setEnderecoDTO(EnderecoDTO.builder().build());
        restaurante.setTipoDeCozinha("Italiana");
        restaurante.setHorariosDeFuncionamentoDTO(gerarHorariosDeFuncionamentoDto());
        restaurante.setDonoDoRestaurante(gerarUsuarioDTODonoDeRestaurante());
        return restaurante;
    }

    public static RestauranteDto gerarRestauranteDto(Restaurante restaurante) {
        RestauranteDto restauranteDto = new RestauranteDto();
        restauranteDto.setNome(restaurante.getNome());
        restauranteDto.setEnderecoDTO(gerarEnderecoDTO(restaurante.getEndereco()));
        restauranteDto.setTipoDeCozinha(restaurante.getTipoDeCozinha());
        restauranteDto.setHorariosDeFuncionamentoDTO(gerarHorariosDeFuncionamentoDto());
        restauranteDto.setDonoDoRestaurante(gerarUsuarioDTO(restaurante.getDonoDoRestaurante()));
        return restauranteDto;
    }
    
    public static RestauranteDto gerarRestauranteDto(UsuarioDTO donoDoRestaurante) {
        RestauranteDto restaurante = new RestauranteDto();
        restaurante.setNome("Restaurante Teste");
        restaurante.setEnderecoDTO(EnderecoDTO.builder().build());
        restaurante.setTipoDeCozinha("Italiana");
        restaurante.setHorariosDeFuncionamentoDTO(gerarHorariosDeFuncionamentoDto());
        restaurante.setDonoDoRestaurante(donoDoRestaurante);
        return restaurante;
    }

}
