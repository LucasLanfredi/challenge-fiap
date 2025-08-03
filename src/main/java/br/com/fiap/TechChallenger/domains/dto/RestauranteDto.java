package br.com.fiap.TechChallenger.domains.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "Dados do restaurante")
@Data
public class RestauranteDto {

    @Schema(description = "Nome do restaurante", example = "Sabor da Montanha")
    @NotBlank(message = "O nome do restaurante é obrigatório.")
    private String nome;

    private EnderecoDTO enderecoDTO;

    @Schema(description = "Tipo de Cozinha", example = "Cozinha Mineira")
    @NotBlank(message = "O tipo de cozinha do restaurante é obrigatório")
    private String tipoDeCozinha;

    @Schema(description = "Horário de funcionamento do restaurante")
    private HorariosDeFuncionamentoDTO horariosDeFuncionamentoDTO;

    @NotBlank(message = "Os dados do dono do restaurante são obrigatórios")
    private UsuarioDTO DonoDoRestaurante;

}
