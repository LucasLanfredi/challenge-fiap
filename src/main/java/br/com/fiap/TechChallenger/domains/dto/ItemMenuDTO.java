package br.com.fiap.TechChallenger.domains.dto;

import br.com.fiap.TechChallenger.domains.Alergeno;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Schema(description = "DTO para criação e edição de itens do menu")
@Builder
public class ItemMenuDTO {

    @Schema(description = "Nome do prato", example = "Spaghetti ao molho de tomate e manjericão")
    @NotBlank(message = "O nome do prato é obrigatório")
    private String nomePrato;

    @Schema(description = "Descrição detalhada do prato", example = "Massa italiana com molho caseiro de tomate, manjericão fresco e azeite de oliva extra virgem")
    private String descricaoPrato;

    @Schema(description = "Preço do prato", example = "39.90")
    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @Schema(description = "URL da imagem do prato", example = "https://restaurante.com.br/imagens/spaghetti.jpg")
    private String urlImagem;

    @Schema(description = "Lista de ingredientes utilizados no prato", example = "[\"massa de trigo\", \"molho de tomate\", \"manjericão fresco\"]")
    @NotEmpty(message = "A lista de ingredientes não pode estar vazia")
    private Set<@NotBlank(message = "Ingrediente não pode ser vazio") String> ingredientes;

    @Schema(description = "Lista de alérgenos presentes no prato", example = "[\"GLÚTEN\"]")
    private Set<Alergeno> alergenos;

    @Schema(description = "Indica se o prato está disponível para pedidos", example = "true")
    private boolean disponivel;

    @NotNull(message = "Restaurante é obrigatorio")
    @Schema(description = "Restaurante que apresentará esse item", example = "1")
    private Long idRestaurante;

}
