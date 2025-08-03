package br.com.fiap.TechChallenger.gateways.controller.api;

import br.com.fiap.TechChallenger.domains.dto.RestauranteDto;
import br.com.fiap.TechChallenger.domains.dto.RestauranteRequestEditDto;
import br.com.fiap.TechChallenger.domains.dto.response.RestauranteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.List;

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
            @Valid RestauranteDto restauranteDto, HttpServletRequest request);

    @Operation(summary = "Listar todos os restaurantes cadastrados", description = "Retorna todos os restaurantes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    ResponseEntity<List<RestauranteResponse>> buscarTodosRestaurantes();

    @Operation(summary = "Buscar restaurante por id", description = "Exibe os dados de um restaurante cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<RestauranteResponse> buscarRestaurantePorId(
            @PathVariable @Parameter(description = "id do restaurante") Long id);

    @Operation(summary = "Editar restaurante", description = "Atualiza os dados de um restaurante cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    ResponseEntity<?> editarRestaurante(
            @RequestBody(description = "Dados para edição do restaurante", required = true,
                    content = @Content(schema = @Schema(implementation = RestauranteDto.class)))
            @Valid RestauranteRequestEditDto restauranteDto, HttpServletRequest request,
            @PathVariable Long id);

    @Operation(summary = "Excluir restaurante cadastrado", description = "Exclui um restaurante cadastrado através do seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    ResponseEntity<?> excluirRestaurante(@PathVariable Long id, HttpServletRequest request);

}
