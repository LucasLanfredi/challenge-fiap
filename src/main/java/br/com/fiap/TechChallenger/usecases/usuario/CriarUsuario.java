package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.Usuario;
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
public class CriarUsuario {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EnderecoRepository enderecoRepository;

    public ResponseEntity<?> criar(final UsuarioDTO dto) {

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

}