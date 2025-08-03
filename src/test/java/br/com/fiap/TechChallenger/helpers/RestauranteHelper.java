package br.com.fiap.TechChallenger.helpers;

import br.com.fiap.TechChallenger.domains.*;
import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.dto.RestauranteDto;
import br.com.fiap.TechChallenger.domains.dto.RestauranteRequestEditDto;

import java.util.List;

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEnderecoDTO;
import static br.com.fiap.TechChallenger.helpers.HorarioFuncionamentoHelper.gerarHorariosDeFuncionamento;
import static br.com.fiap.TechChallenger.helpers.HorarioFuncionamentoHelper.gerarHorariosDeFuncionamentoDto;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.*;

public class RestauranteHelper {

    public static Restaurante gerarRestauranteTipo(Endereco endereco,
                                               Usuario usuario,
                                               String tipoDeCozinha,
                                               List<ItemMenu> itemMenu){
        return new Restaurante(
                1L,
                "Restaurante Teste",
                endereco,
                tipoDeCozinha,
                HorariosDeFuncionamento.builder().build(),
                usuario,
                itemMenu
        );
    }

    public static Restaurante gerarRestaurante(Endereco endereco,
                                               Usuario usuario,
                                               List<ItemMenu> itemMenu) {
        return new Restaurante(
                1L,
                "Restaurante Teste",
                endereco,
                "Italiana",
                HorariosDeFuncionamento.builder().build(),
                usuario,
                itemMenu
        );
    }

    public static RestauranteDto gerarRestauranteDto() {
        RestauranteDto restaurante = new RestauranteDto();
        restaurante.setNome("Restaurante Teste");
        restaurante.setEnderecoDTO(EnderecoDTO.builder().build());
        restaurante.setTipoDeCozinha("Italiana");
        restaurante.setHorariosDeFuncionamentoDTO(gerarHorariosDeFuncionamentoDto());
        restaurante.setDonoRestauranteId(gerarUsuarioDonoDeRestaurante().getId());
        return restaurante;
    }

    public static RestauranteRequestEditDto gerarRestauranteRequestEditDto() {
        RestauranteRequestEditDto restaurante = new RestauranteRequestEditDto();
        restaurante.setNome("Restaurante Teste");
        restaurante.setTipoDeCozinha("Italiana");
        restaurante.setHorariosDeFuncionamentoDTO(gerarHorariosDeFuncionamento());
        restaurante.setDonoRestauranteId(gerarUsuarioDonoDeRestaurante().getId());
        return restaurante;
    }

    public static RestauranteRequestEditDto gerarRestauranteRequestEditDto(Usuario donoDoRestaurante) {
        RestauranteRequestEditDto restaurante = new RestauranteRequestEditDto();
        restaurante.setNome("Restaurante Teste");
        restaurante.setTipoDeCozinha("Italiana");
        restaurante.setHorariosDeFuncionamentoDTO(gerarHorariosDeFuncionamento());
        restaurante.setDonoRestauranteId(donoDoRestaurante.getId());
        return restaurante;
    }



    public static RestauranteDto gerarRestauranteDto(Restaurante restaurante) {
        RestauranteDto restauranteDto = new RestauranteDto();
        restauranteDto.setNome(restaurante.getNome());
        restauranteDto.setEnderecoDTO(gerarEnderecoDTO(restaurante.getEndereco()));
        restauranteDto.setTipoDeCozinha(restaurante.getTipoDeCozinha());
        restauranteDto.setHorariosDeFuncionamentoDTO(gerarHorariosDeFuncionamentoDto());
        restauranteDto.setDonoRestauranteId(gerarUsuarioDonoDeRestaurante().getId());
        return restauranteDto;
    }
    
    public static RestauranteDto gerarRestauranteDto(Usuario donoDoRestaurante) {
        RestauranteDto restaurante = new RestauranteDto();
        restaurante.setNome("Restaurante Teste");
        restaurante.setEnderecoDTO(EnderecoDTO.builder().build());
        restaurante.setTipoDeCozinha("Italiana");
        restaurante.setHorariosDeFuncionamentoDTO(gerarHorariosDeFuncionamentoDto());
        restaurante.setDonoRestauranteId(donoDoRestaurante.getId());
        return restaurante;
    }

}
