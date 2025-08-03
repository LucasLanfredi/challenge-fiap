package br.com.fiap.TechChallenger.domains.dto.response;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.HorariosDeFuncionamento;
import br.com.fiap.TechChallenger.domains.Restaurante;

public record RestauranteResponse(Long id,
                                  String nome,
                                  EnderecoResponse endereco,
                                  String tipoDeCozinha,
                                  HorariosDeFuncionamento horarioDeFuncionamento,
                                  UsuarioResponse donoDoRestaurante)

{
    public static RestauranteResponse converte(Restaurante restaurante) {
        final UsuarioResponse donoDoRestaurante = UsuarioResponse.fromEntity(restaurante.getDonoDoRestaurante());
        final EnderecoResponse enderecoResponse = EnderecoResponse.fromEntity(restaurante.getEndereco());
        return new RestauranteResponse(
                restaurante.getIdRestaurante(),
                restaurante.getNome(),
                enderecoResponse,
                restaurante.getTipoDeCozinha(),
                restaurante.getHorarioDeFuncionamento(),
                donoDoRestaurante
        );
    }
}
