package br.com.fiap.TechChallenger.domains;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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
    private Set<String> ingredientes;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Alergeno> alergenos;

    private boolean disponivel = true;

    //private Restaurante restaurante;

    public void marcarComoIndisponivel(){
        this.disponivel = false;
    }

    public void marcarComoDisponivel() {
        this.disponivel = true;
    }
}
