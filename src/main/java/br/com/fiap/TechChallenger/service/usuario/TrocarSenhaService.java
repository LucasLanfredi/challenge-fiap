package br.com.fiap.TechChallenger.service.usuario;

import br.com.fiap.TechChallenger.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.entity.Usuario;
import br.com.fiap.TechChallenger.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.service.exception.SenhaInvalidaException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TrocarSenhaService {

    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<?> execute(final TrocaSenhaDto trocaSenhaDto) throws SenhaInvalidaException {

        final Usuario usuario = usuarioRepository.findByLogin(trocaSenhaDto.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException(trocaSenhaDto.getLogin()));

        if(trocaSenhaDto.getSenhaAtual().equals(usuario.getSenha())) {
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
                .senha(trocaSenhaDto.getNovaSenha())
                .dataUltimaAlteracao(LocalDateTime.now())
                .endereco(usuario.getEndereco())
                .tipoUsuario(usuario.getTipoUsuario())
                .build();
        usuarioRepository.save(usuarioEditado);
    }

}