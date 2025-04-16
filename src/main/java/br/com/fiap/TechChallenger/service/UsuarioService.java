package br.com.fiap.TechChallenger.service;

import br.com.fiap.TechChallenger.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario registrar(UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .login(dto.getLogin())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .endereco(dto.getEndereco())
                .tipoUsuario(dto.getTipoUsuario())
                .dataUltimaAlteracao(LocalDateTime.now())
                .build();
        return usuarioRepository.save(usuario);
    }

    public boolean existsByLogin(String login) {
        return usuarioRepository.findByLogin(login).isPresent();
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}