package br.com.fiap.TechChallenger.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class HorariosDeFuncionamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHorariosDeFuncionamento;

    @OneToOne(cascade = CascadeType.ALL)
    private Horarios diasUteis;

    @OneToOne(cascade = CascadeType.ALL)
    private Horarios sabado;

    @OneToOne(cascade = CascadeType.ALL)
    private Horarios domingoEFeriado;
}
