package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.domains.dto.ItemMenuDTO;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import br.com.fiap.TechChallenger.usecases.itemMenu.BuscarItemMenu;
import br.com.fiap.TechChallenger.usecases.itemMenu.CriarItemMenu;
import br.com.fiap.TechChallenger.usecases.itemMenu.DeletarItemMenu;
import br.com.fiap.TechChallenger.usecases.itemMenu.EditarItemMenu;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-menu")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemMenuController {

    private final CriarItemMenu criarItemMenu;
    private final EditarItemMenu editarItemMenu;
    private final DeletarItemMenu deletarItemMenu;
    private final BuscarItemMenu buscarItemMenu;

    @GetMapping
    public ResponseEntity<List<ItemMenuResponse>> buscarTodos() {
        return buscarItemMenu.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemMenuResponse> buscarPorId(@PathVariable Long id) {
        return buscarItemMenu.buscaPorId(id);
    }

    @PostMapping
    public ResponseEntity<ItemMenuResponse> criar(@Valid @RequestBody ItemMenuDTO dto) {
        return criarItemMenu.criar(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemMenuResponse> editar(@PathVariable Long id,  @Valid @RequestBody ItemMenuDTO dto) {
        return editarItemMenu.editar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return deletarItemMenu.deletar(id);
    }
}
