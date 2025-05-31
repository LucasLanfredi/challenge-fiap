package br.com.fiap.TechChallenger.gateways.controller.api;

import br.com.fiap.TechChallenger.domains.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.domains.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.domains.dto.response.UsuarioResponse;
import br.com.fiap.TechChallenger.usecases.exception.SenhaInvalidaException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuário", description = "Endpoints para gerenciar usuários")
public interface UsuarioLogadoApi {

    @Operation(summary = "Editar usuário", description = "Edita os dados do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário editado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    ResponseEntity<?> editarUsuario(@RequestBody(
            description = "Dados para edição do usuário", required = true,
            content = @Content(schema = @Schema(implementation = UsuarioEditDTO.class)
    )) UsuarioEditDTO editarUsuarioRequest, HttpServletRequest request) throws AuthException;

    @Operation(summary = "Excluir usuário", description = "Exclui o usuário autenticado do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    ResponseEntity<?> deleteUsuario(HttpServletRequest request) throws AuthException;

    @Operation(summary = "Buscar usuário logado", description = "Retorna os dados do usuário atualmente autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário retornados com sucesso")
    })
    ResponseEntity<UsuarioResponse> buscarUsuarioLogado(HttpServletRequest request);

    @Operation(summary = "Trocar senha", description = "Permite ao usuário logado alterar sua senha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Senha atual inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    ResponseEntity<?> trocarSenha(TrocaSenhaDto trocaSenhaDto) throws SenhaInvalidaException;
}
