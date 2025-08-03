package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import br.com.fiap.TechChallenger.gateways.controller.api.ItemMenuApi;
import br.com.fiap.TechChallenger.usecases.itemMenu.BuscarItemMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-menu")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemMenuController implements ItemMenuApi {

    private final BuscarItemMenu buscarItemMenu;

    @Override
    @GetMapping
    public ResponseEntity<List<ItemMenuResponse>> buscarTodos() {
        return buscarItemMenu.buscarTodos();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ItemMenuResponse> buscarPorId(@PathVariable Long id) {
        return buscarItemMenu.buscaPorId(id);
    }

}
