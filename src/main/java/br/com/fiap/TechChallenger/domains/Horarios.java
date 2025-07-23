package br.com.fiap.TechChallenger.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Entity
@AllArgsConstructor
public class Horarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHorarios;

    private String abertura;

    private String fechamento;
}
