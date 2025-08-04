package br.com.fiap.TechChallenger.helpers;

import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.dto.ItemMenuDTO;

import java.math.BigDecimal;
import java.util.HashSet;


public class ItensMenuHelper {

    public static ItemMenu gerarItemMenu(Restaurante restaurante) {
        return ItemMenu.builder()
                .id(1L)
                .nomePrato("Item de Menu")
                .descricaoPrato("Descrição do item de menu")
                .preco(BigDecimal.valueOf(19.99))
                .urlImagem("https://example.com/imagem.jpg")
                .alergenos(new HashSet<>())
                .disponivel(true)
                .ingredientes(new HashSet<>())
                .restaurante(restaurante)
                .build();
    }

    public static ItemMenuDTO gerarItemMenuDTO(ItemMenu itemMenu) {
        return ItemMenuDTO.builder()
                .nomePrato(itemMenu.getNomePrato())
                .descricaoPrato(itemMenu.getDescricaoPrato())
                .preco(itemMenu.getPreco())
                .urlImagem(itemMenu.getUrlImagem())
                .alergenos(itemMenu.getAlergenos())
                .disponivel(itemMenu.isDisponivel())
                .ingredientes(itemMenu.getIngredientes())
                .idRestaurante(1L)
                .build();
    }
}
