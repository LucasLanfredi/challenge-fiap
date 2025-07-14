package br.com.fiap.TechChallenger.domains;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class HorariosDeFuncionamento {
    private final Horarios diasUteis;
    private final Horarios sabado;
    private final Horarios domingoEFeriado;
}
