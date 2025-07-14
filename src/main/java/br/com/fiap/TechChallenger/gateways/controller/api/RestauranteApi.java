package br.com.fiap.TechChallenger.gateways.controller.api;

import br.com.fiap.TechChallenger.domains.dto.RestauranteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Restaurante", description = "Endpoints para gerenciar cadastro de restaurantes")
public interface RestauranteApi {

    @Operation(summary = "Cadastrar restaurante", description = "Cadastra um novo restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    ResponseEntity<?> cadastrarRestaurante(@RequestBody(description = "Dados para cadastro do restaurante", required = true,
                content = @Content(schema = @Schema(implementation = RestauranteDto.class)))
            @Valid RestauranteDto restauranteDto, final HttpServletRequest request);

    @Operation(summary = "Excluir restaurante cadastrado", description = "Exclui um restaurante através do id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    ResponseEntity<?> excluirRestaurante(@PathVariable Long id, HttpServletRequest request);

}
