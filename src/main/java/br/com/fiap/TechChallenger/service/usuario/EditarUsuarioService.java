package br.com.fiap.TechChallenger.service.usuario;

import br.com.fiap.TechChallenger.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.entity.Endereco;
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

    public ResponseEntity<?> editar(@Valid final UsuarioEditDTO editarUsuarioRequest, final HttpServletRequest request) throws AuthException {

        final UsuarioLogado usuarioLogado = autenticacaoService.getUsuarioLogado(request);
        final Usuario usuario = usuarioRepository.findById(usuarioLogado.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        final Usuario usuarioEditado = Usuario.builder()
                .id(usuario.getId())
                .nome(editarUsuarioRequest.getNome())
                .email(editarUsuarioRequest.getEmail())
                .login(editarUsuarioRequest.getLogin())
                .dataUltimaAlteracao(LocalDateTime.now())
                .endereco(editarUsuarioRequest.getEndereco().stream()
                        .map(enderecoDTO -> Endereco.builder()
                                .rua(enderecoDTO.getRua())
                                .numero(enderecoDTO.getNumero())
                                .cidade(enderecoDTO.getCidade())
                                .estado(enderecoDTO.getEstado())
                                .cep(enderecoDTO.getCep())
                                .usuario(usuario)
                                .build())
                        .toList())
                .tipoUsuario(editarUsuarioRequest.getTipoUsuario())
                .build();

        final var idUsuario = usuarioRepository.save(usuarioEditado).getId();

        return ResponseEntity.status(HttpStatus.OK).body("Usuario editado com sucesso! ID: " + idUsuario.toString());
    }

}