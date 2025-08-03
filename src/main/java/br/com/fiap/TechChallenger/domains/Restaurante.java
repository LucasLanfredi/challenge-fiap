package br.com.fiap.TechChallenger.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurante_id")
    private Long idRestaurante;

    private String nome;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER, orphanRemoval = false)
    private Endereco endereco;

    private String tipoDeCozinha;

    @OneToOne(cascade = CascadeType.ALL)
    private HorariosDeFuncionamento horarioDeFuncionamento;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario donoDoRestaurante;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ItemMenu> itensMenu;

}
