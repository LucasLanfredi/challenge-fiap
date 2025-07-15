package br.com.fiap.TechChallenger.domains;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String nomePrato;

    @Setter
    private String descricaoPrato;

    @Setter
    private BigDecimal preco;

    @Setter
    private String urlImagem;

    @ElementCollection
    private List<String> ingrdientes;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Alergenos> alergenos;

    private boolean disponivel;

    //private Restaurante restaurante;
}
