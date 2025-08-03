package br.com.fiap.TechChallenger.domains.dto;

import lombok.Data;

@Data
public class HorariosDeFuncionamentoDTO {
    private HorariosDto diasUteis;
    private HorariosDto sabado;
    private HorariosDto domingoEFeriado;
}
