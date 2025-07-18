package br.com.fiap.TechChallenger.gateways.controller.api;

import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.dto.EnderecoEditDTO;
import br.com.fiap.TechChallenger.domains.dto.response.EnderecoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Endereço", description = "Endpoints para gerenciar endereços de usuários")
public interface EnderecoLogadoApi {
    @Operation(summary = "Criar endereço", description = "Cria um novo endereço para o usuário autenticado.")
    @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso")
    ResponseEntity<?> criarEndereco(@RequestBody(description = "Dados para criação do endereço", required = true,
            content = @Content(schema = @Schema(implementation = EnderecoDTO.class)))final EnderecoDTO criarEnderecoRequest, final HttpServletRequest request);

    @Operation(summary = "Editar endereço", description = "Edita um endereço existente do usuário autenticado.")
    @ApiResponse(responseCode = "200", description = "Endereço editado com sucesso")
    ResponseEntity<?> editarEndereco(@RequestBody(description = "Dados para edição do endereço", required = true,
            content = @Content(schema = @Schema(implementation = EnderecoEditDTO.class))) final EnderecoEditDTO editarEnderecoRequest, final HttpServletRequest request);

    @Operation(summary = "Excluir endereço", description = "Exclui um endereço pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    ResponseEntity<?> deleteEndereco(@PathVariable Long enderecoId, HttpServletRequest request);

    @Operation(summary = "Buscar endereços", description = "Retorna todos os endereços do usuário autenticado.")
    @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso")
    ResponseEntity<List<EnderecoResponse>> buscarEnderecos(HttpServletRequest request);
}
