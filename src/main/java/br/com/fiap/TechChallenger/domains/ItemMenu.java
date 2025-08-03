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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "ITEM_MENU_INGREDIENTES",
            joinColumns = @JoinColumn(name = "item_menu_id")
    )
    @Column(name = "ingredientes")
    @Setter
    private Set<String> ingredientes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "ITEM_MENU_ALERGENOS",
            joinColumns = @JoinColumn(name = "item_menu_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "alergenos")
    @Setter
    private Set<Alergeno> alergenos;

    @ManyToOne()
    @JoinColumn(name = "id_restaurante", nullable = false)
    private Restaurante restaurante;

    private boolean disponivel = true;

    //private Restaurante restaurante;

    public void marcarComoIndisponivel(){
        this.disponivel = false;
    }

    public void marcarComoDisponivel() {
        this.disponivel = true;
    }
}
