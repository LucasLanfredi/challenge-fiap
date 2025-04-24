package br.com.fiap.TechChallenger.service;

import br.com.fiap.TechChallenger.dto.MessageResponse;
import br.com.fiap.TechChallenger.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CriarUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> criar(UsuarioDTO dto) {

        if (existsByLogin(dto.getLogin())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erro: Login já existente!"));
        }
        if (existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erro: Email já existente!"));
        }

        final var usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setLogin(dto.getLogin());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setDataUltimaAlteracao(LocalDateTime.now());
        usuario.setEndereco(dto.getEndereco());
        usuario.setTipoUsuario(dto.getTipoUsuario());

        final var idUsuario = usuarioRepository.save(usuario).getId();

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario cadastrado com sucesso! ID: " + idUsuario.toString());
    }

    public boolean existsByLogin(String login) {
        return usuarioRepository.findByLogin(login).isPresent();
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

}