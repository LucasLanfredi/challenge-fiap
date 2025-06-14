package br.com.fiap.TechChallenger.gateways.controller.api;

import br.com.fiap.TechChallenger.domains.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.domains.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.domains.dto.response.UsuarioResponse;
import br.com.fiap.TechChallenger.usecases.exception.SenhaInvalidaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuário", description = "Endpoints para gerenciar usuários")
public interface UsuarioApi {

    @Operation(summary = "Criar usuário", description = "Cria um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    })
    ResponseEntity<?> criarUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo usuário",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
            )
            @org.springframework.web.bind.annotation.RequestBody @Valid UsuarioDTO criarUsuarioRequest);

    @Operation(summary = "Editar usuário", description = "Edita os dados de um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário editado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<?> editarUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do usuário",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UsuarioEditDTO.class))
            )
            @org.springframework.web.bind.annotation.RequestBody @Valid UsuarioEditDTO editarUsuarioRequest,
            @PathVariable("userId") Long userId);

    @Operation(summary = "Excluir usuário", description = "Remove um usuário do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    @DeleteMapping("/userId/{userId}")
    ResponseEntity<?> deleteUsuario(@PathVariable Long userId);

    @Operation(summary = "Buscar usuário", description = "Obtém os dados de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário retornados"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    ResponseEntity<UsuarioResponse> buscarUsuarioById(@PathVariable("userId") Long userId);

    @Operation(summary = "Buscar todos usuários", description = "Obtém os dados de todos os usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário retornados")
    })
    ResponseEntity<List<UsuarioResponse>> buscarTodosUsuarios();

    @Operation(summary = "Trocar senha", description = "Altera a senha do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Senha atual inválida")
    })
    ResponseEntity<?> trocarSenha(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para alteração de senha",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TrocaSenhaDto.class)))
            @org.springframework.web.bind.annotation.RequestBody @Valid TrocaSenhaDto trocaSenhaDto) throws SenhaInvalidaException;
}