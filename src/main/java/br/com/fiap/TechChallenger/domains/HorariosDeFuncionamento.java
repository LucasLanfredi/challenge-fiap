package br.com.fiap.TechChallenger.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Entity
@AllArgsConstructor
public class HorariosDeFuncionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHorariosDeFuncionamento;

    @OneToOne
    private Horarios diasUteis;

    @OneToOne
    private Horarios sabado;

    @OneToOne
    private Horarios domingoEFeriado;
}
