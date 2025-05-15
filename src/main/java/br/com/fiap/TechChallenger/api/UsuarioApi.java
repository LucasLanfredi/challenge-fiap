package br.com.fiap.TechChallenger.api;

import br.com.fiap.TechChallenger.dto.*;
import br.com.fiap.TechChallenger.service.exception.SenhaInvalidaException;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "Usuário", description = "Endpoints para gerenciar usuários")
@RequestMapping("/usuario")
@RestController
public interface UsuarioApi {
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário no sistema.")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso" )
    @PostMapping("/criar")
    ResponseEntity<?> criarUsuario(@RequestBody(
            description = "Dados para criação do usuário", required = true,
            content = @Content(schema = @Schema(implementation = UsuarioDTO.class)
            ))  final UsuarioDTO criarUsuarioRequest);

    @Operation(summary = "Editar usuário", description = "Edita os dados do usuário logado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário editado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @PutMapping("/editar")
    ResponseEntity<?> editarUsuario(@RequestBody(
            description = "Dados para edição do usuário", required = true,
            content = @Content(schema = @Schema(implementation = UsuarioEditDTO.class)
    )) final UsuarioEditDTO editarUsuarioRequest, final HttpServletRequest request) throws AuthException;

    @Operation(summary = "Excluir usuário", description = "Exclui o usuário autenticado do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @DeleteMapping
    ResponseEntity<?> deleteUsuario(final HttpServletRequest request) throws AuthException;

    @Operation(summary = "Buscar usuário logado", description = "Retorna os dados do usuário atualmente autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário retornados com sucesso")
    })
    @GetMapping("/logado")
    ResponseEntity<UsuarioLogado> buscarUsuarioLogado(final HttpServletRequest request);

    @Operation(summary = "Trocar senha", description = "Permite ao usuário logado alterar sua senha.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Senha atual inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @PutMapping("/troca/senha")
    ResponseEntity<?> trocarSenha(final TrocaSenhaDto trocaSenhaDto) throws SenhaInvalidaException;
}
