package br.com.fiap.TechChallenger.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa um endereço do usuário")
public class EnderecoEditDTO {
    @Schema(description = "Id do endereço")
    private Long enderecoId;

    @Schema(description = "Rua", example = "Rua das rosas")
    private String rua;

    @Schema(description = "Número", example = "123")
    private String numero;

    @Schema(description = "Cidade", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado", example = "SP")
    private String estado;

    @Schema(description = "CEP", example = "01001-000")
    private String cep;

}

