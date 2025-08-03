package br.com.fiap.TechChallenger.domains.dto;

import br.com.fiap.TechChallenger.domains.HorariosDeFuncionamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Dados do restaurante")
@Data
public class RestauranteRequestEditDto {

    @Schema(description = "Nome do restaurante", example = "Sabor da Montanha")
    @NotBlank(message = "O nome do restaurante é obrigatório.")
    private String nome;

    @Schema(description = "Tipo de Cozinha", example = "Cozinha Mineira")
    @NotBlank(message = "O tipo de cozinha do restaurante é obrigatório")
    private String tipoDeCozinha;

    @Schema(description = "Horário de funcionamento do restaurante")
    private HorariosDeFuncionamento horariosDeFuncionamentoDTO;

    @NotNull(message = "Os dados do dono do restaurante são obrigatórios")
    private Long donoRestauranteId;

}
