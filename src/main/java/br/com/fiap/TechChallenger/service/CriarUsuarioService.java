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

    public ResponseEntity<?> criar(final UsuarioDTO dto) {

        if (existsByLogin(dto.getLogin())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erro: Login já existente!"));
        }
        if (existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erro: Email já existente!"));
        } //TODO Setar regras em Class e instanciar e passar com o foreach como vimos na aula de SOLID

        final Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .login(dto.getLogin())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .dataUltimaAlteracao(LocalDateTime.now())
                .endereco(dto.getEndereco())
                .tipoUsuario(dto.getTipoUsuario())
                .build();

        final Long idUsuario = usuarioRepository.save(usuario).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario cadastrado com sucesso! ID: " + idUsuario.toString());
    }

    public boolean existsByLogin(String login) {
        return usuarioRepository.findByLogin(login).isPresent();
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

}