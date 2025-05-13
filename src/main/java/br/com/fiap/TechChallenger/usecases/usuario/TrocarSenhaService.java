package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.domains.entity.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.exception.SenhaInvalidaException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TrocarSenhaService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> execute(final TrocaSenhaDto trocaSenhaDto) throws SenhaInvalidaException {

        final Usuario usuario = usuarioRepository.findByLogin(trocaSenhaDto.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException(trocaSenhaDto.getLogin()));

        if(passwordEncoder.matches(trocaSenhaDto.getSenhaAtual(),usuario.getSenha())) {
            alteraSenha(usuario, trocaSenhaDto);
        } else throw new SenhaInvalidaException("A senha atual digitada não confere com a senha atual cadastrada!");

        return ResponseEntity.status(HttpStatus.OK).body("Troca de senha efetuada com sucesso!");
    }

    private void alteraSenha(final Usuario usuario, final TrocaSenhaDto trocaSenhaDto) {
        final Usuario usuarioEditado = Usuario.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .login(usuario.getLogin())
                .senha(passwordEncoder.encode(trocaSenhaDto.getNovaSenha()))
                .dataUltimaAlteracao(LocalDateTime.now())
                .endereco(usuario.getEndereco())
                .tipoUsuario(usuario.getTipoUsuario())
                .build();
        usuarioRepository.save(usuarioEditado);
    }

}