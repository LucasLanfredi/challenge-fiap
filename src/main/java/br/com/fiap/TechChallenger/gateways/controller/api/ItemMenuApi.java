package br.com.fiap.TechChallenger.gateways.controller.api;

import br.com.fiap.TechChallenger.domains.dto.ErroCustomizado;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Item Menu", description = "Endpoints para visualizar e gerenciar itens do menu")
public interface ItemMenuApi {


    @Operation(
            summary = "Listar todos os itens de menu (público)",
            description = "Retorna todos os itens disponíveis no menu"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Itens encontrados com sucesso",
                    content = @Content(schema = @Schema(implementation = ItemMenuResponse.class))
            )
    })
    @GetMapping
    ResponseEntity<List<ItemMenuResponse>> buscarTodos();


    @Operation(
            summary = "Buscar item de menu por ID (público)",
            description = "Retorna os detalhes de um item específico do menu"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = ItemMenuResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroCustomizado.class))
            )
    })
    @GetMapping("/{id}")
    ResponseEntity<ItemMenuResponse> buscarPorId(@PathVariable Long id);

}
