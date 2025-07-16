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
    private Set<String> ingredientes = new HashSet<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Alergeno> alergenos = new HashSet<>();

    private boolean disponivel;

    //private Restaurante restaurante;

    public void adicionarIngrediente(String ingrediente){
        ingredientes.add(ingrediente);
    }

    public void removerIngrediente(String ingrediente){
        ingredientes.remove(ingrediente);
    }

    public void adicionarAlergeno(Alergeno alergeno){
        alergenos.add(alergeno);
    }

    public void removerAlergeno(Alergeno alergeno){
        alergenos.remove(alergeno);
    }

    public void marcarComoIndisponivel(){
        this.disponivel = false;
    }
}
