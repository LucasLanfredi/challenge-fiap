package br.com.fiap.TechChallenger.usecases.itemMenu;

import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.gateways.repository.ItemMenuRepository;
import br.com.fiap.TechChallenger.usecases.exception.RecursoNaoEncontradoException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeletarItemMenu {

    private final ItemMenuRepository itemMenuRepository;

    public ResponseEntity<Void> deletar(Long id) {
        ItemMenu itemMenu = itemMenuRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Id " + id + " não foi encontrado."
                ));

        itemMenuRepository.delete(itemMenu);

        return ResponseEntity.noContent().build();
    }
}
