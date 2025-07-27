package br.com.fiap.TechChallenger.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Builder
@Getter
@AllArgsConstructor
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRestaurante;

    private String nome;

    @OneToOne
    private Endereco endereco;

    private String tipoDeCozinha;

    @OneToOne
    private HorariosDeFuncionamento horarioDeFuncionamento;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario DonoDoRestaurante;
}
