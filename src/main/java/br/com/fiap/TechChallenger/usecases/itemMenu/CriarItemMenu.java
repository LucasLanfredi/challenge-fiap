package br.com.fiap.TechChallenger.usecases.itemMenu;

import br.com.fiap.TechChallenger.domains.Alergeno;
import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.domains.dto.ItemMenuDTO;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import br.com.fiap.TechChallenger.gateways.repository.ItemMenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CriarItemMenu {

    private final ItemMenuRepository itemMenuRepository;

    public ResponseEntity<ItemMenuResponse> criar(final ItemMenuDTO dto){



        final ItemMenu item = ItemMenu.builder()
                .nomePrato(dto.getNomePrato())
                .descricaoPrato(dto.getDescricaoPrato())
                .preco(dto.getPreco())
                .urlImagem(dto.getUrlImagem())
                .ingredientes(new HashSet<>(dto.getIngredientes()))
                .alergenos(new HashSet<>(Optional.ofNullable(dto.getAlergenos()).orElse(Set.of())))
                .disponivel(dto.isDisponivel())
                .build();

        itemMenuRepository.save(item);

        ItemMenuResponse response = ItemMenuResponse.fromEntity(item);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
