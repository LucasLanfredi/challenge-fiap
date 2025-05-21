package br.com.fiap.TechChallenger.api;

import br.com.fiap.TechChallenger.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.dto.EnderecoEditDTO;
import br.com.fiap.TechChallenger.dto.response.EnderecoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Endereço", description = "Endpoints para gerenciar endereços de usuários")
public interface EnderecoApi {

    @Operation(summary = "Criar endereço", description = "Cria um novo endereço para um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    ResponseEntity<?> criarEndereco(
            @RequestBody(description = "Dados para criação do endereço", required = true,
                    content = @Content(schema = @Schema(implementation = EnderecoDTO.class)))
            @Valid EnderecoDTO criarEnderecoRequest,
            @PathVariable @Parameter(description = "ID do usuário") Long userId);

    @Operation(summary = "Editar endereço", description = "Atualiza os dados de um endereço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    ResponseEntity<?> editarEndereco(
            @RequestBody(description = "Dados para edição do endereço", required = true,
                    content = @Content(schema = @Schema(implementation = EnderecoEditDTO.class)))
            @Valid EnderecoEditDTO editarEnderecoRequest);

    @Operation(summary = "Excluir endereço", description = "Remove um endereço pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço excluído com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    ResponseEntity<?> deleteEndereco(
            @PathVariable @Parameter(description = "ID do endereço") Long enderecoId);

    @Operation(summary = "Buscar endereços por usuário", description = "Lista todos os endereços de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereços encontrados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<List<EnderecoResponse>> buscarEnderecosByUserId(
            @PathVariable @Parameter(description = "ID do usuário") Long userId);

    @Operation(summary = "Listar todos os endereços", description = "Retorna todos os endereços cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    })
    ResponseEntity<List<EnderecoResponse>> buscarTodosEnderecos();
}