package br.com.fiap.TechChallenger.gateways.controller.api;

import br.com.fiap.TechChallenger.domains.dto.ErroCustomizado;
import br.com.fiap.TechChallenger.domains.dto.ItemMenuDTO;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Item Menu", description = "Endpoints para visualizar e gerenciar itens do menu")
public interface ItemMenuLogadoApi {


    @Operation(summary = "Criar item (restrito)", description = "Cria um novo item de menu")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Item criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ItemMenuResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroCustomizado.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroCustomizado.class))
            )
    })
    @PostMapping
    ResponseEntity<ItemMenuResponse> criar(@RequestBody(
            description = "Corpo da requisição contendo os dados do novo prato",
            required = true,
            content = @Content(schema = @Schema(implementation = ItemMenuDTO.class))
    )
            @Valid ItemMenuDTO dto);



    @Operation(summary = "Editar item de menu (restrito)", description = "Edita um item de menu existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item editado com sucesso",
                    content = @Content(schema = @Schema(implementation = ItemMenuResponse.class))
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErroCustomizado.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroCustomizado.class))
            )
    })
    @PutMapping("/{id}")
    ResponseEntity<ItemMenuResponse> editar(
            @PathVariable Long id,
            @RequestBody(
                    description = "Corpo da requisição com os dados atualizados do item de menu",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ItemMenuDTO.class))
            )
            @Valid ItemMenuDTO dto
    );



    @Operation(summary = "Excluir item de menu (restrito)", description = "Remove um item de menu pelo ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Item excluído com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Item não encontrado",
                    content = @Content(schema = @Schema(implementation = ErroCustomizado.class))
            )
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id);

}
