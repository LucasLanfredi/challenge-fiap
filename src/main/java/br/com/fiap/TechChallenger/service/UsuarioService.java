package br.com.fiap.TechChallenger.service;

import br.com.fiap.TechChallenger.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.security.AutenticacaoService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AutenticacaoService autenticacaoService;
    private final PasswordEncoder passwordEncoder;

    public Long registrar(UsuarioDTO dto) {
        return usuarioRepository.save(Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .login(dto.getLogin())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .endereco(dto.getEndereco())
                .tipoUsuario(dto.getTipoUsuario())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build()).getId();
    }

    public boolean existsByLogin(String login) {
        return usuarioRepository.findByLogin(login).isPresent();
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Long editarUsuario(@Valid UsuarioEditDTO editarUsuarioRequest, HttpServletRequest request) throws AuthException {
        UsuarioLogado usuarioLogado = autenticacaoService.getUsuarioLogado(request);
        Usuario usuario = usuarioRepository.findById(usuarioLogado.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setNome(editarUsuarioRequest.getNome());
        usuario.setEmail(editarUsuarioRequest.getEmail());
        usuario.setSenha(editarUsuarioRequest.getLogin());
        usuario.setEndereco(editarUsuarioRequest.getEndereco());
        usuario.setDataUltimaAlteracao(LocalDateTime.now());

        return usuarioRepository.save(usuario).getId();
    }

    public void deleteUsuario(HttpServletRequest request) throws AuthException {
        UsuarioLogado usuarioLogado = autenticacaoService.getUsuarioLogado(request);
        Usuario usuario = usuarioRepository.findById(usuarioLogado.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
    }
}