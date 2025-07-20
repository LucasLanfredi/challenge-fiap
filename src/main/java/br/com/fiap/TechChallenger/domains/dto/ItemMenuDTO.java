package br.com.fiap.TechChallenger.domains.dto;

import br.com.fiap.TechChallenger.domains.Alergeno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ItemMenuDTO {

    @NotBlank(message = "O nome do prato é obrigatório")
    private String nomePrato;

    private String descricaoPrato;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    private String urlImagem;

    @NotEmpty(message = "A lista de ingredientes não pode estar vazia")
    private Set<@NotBlank(message = "Ingrediente não pode ser vazio") String> ingredientes;

    private Set<Alergeno> alergenos;

    private boolean disponivel;

    //private RestauranteDto restaurante;
}
