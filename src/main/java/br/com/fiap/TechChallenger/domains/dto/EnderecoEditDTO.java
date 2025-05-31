package br.com.fiap.TechChallenger.domains.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "A rua é obrigatória.")
    private String rua;

    @Schema(description = "Número", example = "123")
    @NotBlank(message = "O número é obrigatório.")
    private String numero;

    @Schema(description = "Cidade", example = "São Paulo")
    @NotBlank(message = "O número é obrigatório.")
    private String cidade;

    @Schema(description = "Estado", example = "SP")
    @NotBlank(message = "O estado é obrigatório.")
    @Size(min = 2, max = 2, message = "O estado deve conter exatamente 2 letras (ex: SP, RJ).")
    private String estado;

    @Schema(description = "CEP", example = "01001-000")
    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter exatamente 8 dígitos numéricos.")
    private String cep;

}

