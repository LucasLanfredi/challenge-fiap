package br.com.fiap.TechChallenger.usecases.itemMenu;

import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import br.com.fiap.TechChallenger.gateways.repository.ItemMenuRepository;
import br.com.fiap.TechChallenger.usecases.exception.RecursoNaoEncontradoException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuscarItemMenu {

    private final ItemMenuRepository itemMenuRepository;

    public ResponseEntity<List<ItemMenuResponse>> buscarTodos() {

        List<ItemMenu> itensMenu = itemMenuRepository.findAll();

        List<ItemMenuResponse> respostaItensMenu = itensMenu.stream()
                .map(ItemMenuResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(respostaItensMenu);
    }


    public ResponseEntity<ItemMenuResponse> buscaPorId(Long id) {

        ItemMenu itemMenu = itemMenuRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Id " + id + " não foi encontrado."
                ));

        return ResponseEntity.ok(ItemMenuResponse.fromEntity(itemMenu));
    }

}
