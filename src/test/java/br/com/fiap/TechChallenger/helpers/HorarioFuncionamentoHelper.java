package br.com.fiap.TechChallenger.helpers;

import br.com.fiap.TechChallenger.domains.dto.HorariosDeFuncionamentoDTO;
import br.com.fiap.TechChallenger.domains.dto.HorariosDto;

public class HorarioFuncionamentoHelper {

    public static HorariosDeFuncionamentoDTO gerarHorariosDeFuncionamentoDto() {
        HorariosDeFuncionamentoDTO horariosDeFuncionamentoDTO = new HorariosDeFuncionamentoDTO();
        HorariosDto diasUteis = HorariosDto.builder()
                .abertura("00:00")
                .fechamento("23:59")
                .build();
        horariosDeFuncionamentoDTO.setDiasUteis(diasUteis);
        horariosDeFuncionamentoDTO.setSabado(diasUteis);
        horariosDeFuncionamentoDTO.setDomingoEFeriado(diasUteis);
        return horariosDeFuncionamentoDTO;
    }

}
