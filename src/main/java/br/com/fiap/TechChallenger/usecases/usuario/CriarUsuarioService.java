package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.dto.MessageResponse;
import br.com.fiap.TechChallenger.domains.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.domains.entity.Endereco;
import br.com.fiap.TechChallenger.domains.entity.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CriarUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EnderecoRepository enderecoRepository;

    public ResponseEntity<?> criar(final UsuarioDTO dto) {

        if (existsByLogin(dto.getLogin())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erro: Login já existente!"));
        }
        if (existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erro: Email já existente!"));
        }

        final Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .login(dto.getLogin())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .dataUltimaAlteracao(LocalDateTime.now())
                .tipoUsuario(dto.getTipoUsuario())
                .build();

        final Usuario usuarioSalvo = usuarioRepository.save(usuario);

        List<Endereco> enderecos = dto.getEndereco().stream()
                .map(enderecoDTO -> Endereco.builder()
                        .rua(enderecoDTO.getRua())
                        .numero(enderecoDTO.getNumero())
                        .cidade(enderecoDTO.getCidade())
                        .estado(enderecoDTO.getEstado())
                        .cep(enderecoDTO.getCep())
                        .usuario(usuarioSalvo)
                        .build())
                .toList();

        enderecoRepository.saveAll(enderecos);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Usuário cadastrado com sucesso! ID: " + usuarioSalvo.getId());
    }

    public boolean existsByLogin(String login) {
        return usuarioRepository.findByLogin(login).isPresent();
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

}