package br.com.fiap.TechChallenger.gateways.controller;

import br.com.fiap.TechChallenger.domains.dto.ItemMenuDTO;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import br.com.fiap.TechChallenger.gateways.controller.api.ItemMenuLogadoApi;
import br.com.fiap.TechChallenger.usecases.itemMenu.CriarItemMenu;
import br.com.fiap.TechChallenger.usecases.itemMenu.DeletarItemMenu;
import br.com.fiap.TechChallenger.usecases.itemMenu.EditarItemMenu;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ItemMenuResponse> criar(@Valid @RequestBody ItemMenuDTO dto,  HttpServletRequest request) {
        return criarItemMenu.criar(dto);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ItemMenuResponse> editar(@PathVariable Long id, @Valid @RequestBody ItemMenuDTO dto, HttpServletRequest request) {
        return editarItemMenu.editar(id, dto);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DONO_RESTAURANTE') or hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long id,  HttpServletRequest request) {
        return deletarItemMenu.deletar(id);
    }
}
