package br.com.fiap.TechChallenger.domains.dto.response;

import br.com.fiap.TechChallenger.domains.Alergeno;
import br.com.fiap.TechChallenger.domains.ItemMenu;

import java.math.BigDecimal;
import java.util.Set;

public record ItemMenuResponse(
        Long id,
        String nomePrato,
        String descricaoPrato,
        BigDecimal preco,
        String urlImagem,
        Set<String> ingredientes,
        Set<Alergeno> alergenos,
        boolean disponivel
) {
    public static ItemMenuResponse fromEntity(ItemMenu itemMenu){
        return new ItemMenuResponse(
                itemMenu.getId(),
                itemMenu.getNomePrato(),
                itemMenu.getDescricaoPrato(),
                itemMenu.getPreco(),
                itemMenu.getUrlImagem(),
                itemMenu.getIngredientes(),
                itemMenu.getAlergenos(),
                itemMenu.isDisponivel()
        );
    }
}
