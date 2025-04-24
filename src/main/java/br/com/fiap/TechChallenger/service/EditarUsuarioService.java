package br.com.fiap.TechChallenger.service;

import br.com.fiap.TechChallenger.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.security.AutenticacaoService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EditarUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AutenticacaoService autenticacaoService;

    public ResponseEntity<?> editar(@Valid UsuarioEditDTO editarUsuarioRequest, HttpServletRequest request) throws AuthException {

        UsuarioLogado usuarioLogado = autenticacaoService.getUsuarioLogado(request);

        Usuario usuario = usuarioRepository.findById(usuarioLogado.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(editarUsuarioRequest.getNome());
        usuario.setEmail(editarUsuarioRequest.getEmail());
        usuario.setLogin(editarUsuarioRequest.getLogin());
        usuario.setEndereco(editarUsuarioRequest.getEndereco());
        usuario.setDataUltimaAlteracao(LocalDateTime.now());

        final var idUsuario = usuarioRepository.save(usuario).getId();

        return ResponseEntity.status(HttpStatus.OK).body("Usuario editado com sucesso! ID: " + idUsuario.toString());
    }

}