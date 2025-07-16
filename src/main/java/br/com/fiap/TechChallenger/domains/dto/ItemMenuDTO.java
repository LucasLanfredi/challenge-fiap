package br.com.fiap.TechChallenger.domains.dto;

import br.com.fiap.TechChallenger.domains.Alergeno;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ItemMenuDTO {

    private String nomePrato;

    private String descricaoPrato;

    private BigDecimal preco;

    private String urlImagem;

    private Set<String> ingredientes;

    private Set<Alergeno> alergenos;

    private boolean disponivel;

    //private RestauranteDto restaurante;
}
