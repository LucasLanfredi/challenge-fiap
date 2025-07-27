package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.domains.dto.ItemMenuDTO;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import br.com.fiap.TechChallenger.gateways.controller.api.ItemMenuLogadoApi;
import br.com.fiap.TechChallenger.usecases.itemMenu.CriarItemMenu;
import br.com.fiap.TechChallenger.usecases.itemMenu.DeletarItemMenu;
import br.com.fiap.TechChallenger.usecases.itemMenu.EditarItemMenu;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item-menu")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ItemMenuLogadoController implements ItemMenuLogadoApi {

    private final CriarItemMenu criarItemMenu;
    private final EditarItemMenu editarItemMenu;
    private final DeletarItemMenu deletarItemMenu;

    @Override
    @PostMapping
    public ResponseEntity<ItemMenuResponse> criar(@Valid @RequestBody ItemMenuDTO dto) {
        return criarItemMenu.criar(dto);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ItemMenuResponse> editar(@PathVariable Long id, @Valid @RequestBody ItemMenuDTO dto) {
        return editarItemMenu.editar(id, dto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return deletarItemMenu.deletar(id);
    }
}
