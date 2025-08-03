package br.com.fiap.TechChallenger.domains.dto.response;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.HorariosDeFuncionamento;
import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.Usuario;

public record RestauranteResponse(Long id,
                                  String nome,
                                  Endereco endereco,
                                  String tipoDeCozinha,
                                  HorariosDeFuncionamento horarioDeFuncionamento,
                                  Usuario DonoDoRestaurante)

{
    public static RestauranteResponse converte(Restaurante restaurante) {
        return new RestauranteResponse(
                restaurante.getIdRestaurante(),
                restaurante.getNome(),
                restaurante.getEndereco(),
                restaurante.getTipoDeCozinha(),
                restaurante.getHorarioDeFuncionamento(),
                restaurante.getDonoDoRestaurante()
        );
    }
}
