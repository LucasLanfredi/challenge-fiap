package br.com.fiap.TechChallenger.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String rua;

    @Setter
    private String numero;

    @Setter
    private String cidade;

    @Setter
    private String estado;

    @Setter
    private String cep;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
