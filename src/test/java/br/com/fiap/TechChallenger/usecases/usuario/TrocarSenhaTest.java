package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.exception.SenhaInvalidaException;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Profile("teste")
public class TrocarSenhaTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    private TrocarSenha usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new TrocarSenha(usuarioRepository, passwordEncoder);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class TrocarSenhaUsuario {

        @Test
        void devePermitirTrocarSenhaDoUsuario() throws SenhaInvalidaException {

            Usuario usuario = gerarUsuario();
            String novaSenha = "novaSenha123";
            String senhaAtual = "senhaAtual123";

            TrocaSenhaDto trocaSenhaDto = new TrocaSenhaDto();
            trocaSenhaDto.setSenhaAtual(senhaAtual);
            trocaSenhaDto.setNovaSenha(novaSenha);
            trocaSenhaDto.setLogin(usuario.getLogin());

            when(usuarioRepository.findByLogin(usuario.getLogin()))
                    .thenReturn(java.util.Optional.of(usuario));

            when(passwordEncoder.matches(senhaAtual, usuario.getSenha())).thenReturn(true);
            when(passwordEncoder.encode(novaSenha)).thenReturn("senhaCodificada");

            var response = usecase.execute(trocaSenhaDto);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody()).isEqualTo("Troca de senha efetuada com sucesso!");

            verify(usuarioRepository, times(1)).findByLogin(usuario.getLogin());
            verify(usuarioRepository, times(1)).save(any(Usuario.class));
            verify(passwordEncoder, times(1)).matches(senhaAtual, usuario.getSenha());
            verify(passwordEncoder, times(1)).encode(novaSenha);

        }

        @Test
        void deveLancarExcecaoQuandoSenhaAtualInvalida() {
            Usuario usuario = gerarUsuario();
            String senhaAtual = "senhaAtualErrada";
            String novaSenha = "novaSenha123";

            TrocaSenhaDto trocaSenhaDto = new TrocaSenhaDto();
            trocaSenhaDto.setSenhaAtual(senhaAtual);
            trocaSenhaDto.setNovaSenha(novaSenha);
            trocaSenhaDto.setLogin(usuario.getLogin());

            when(usuarioRepository.findByLogin(usuario.getLogin()))
                    .thenReturn(java.util.Optional.of(usuario));

            when(passwordEncoder.matches(senhaAtual, usuario.getSenha())).thenReturn(false);

            assertThatThrownBy(() -> usecase.execute(trocaSenhaDto))
                    .isInstanceOf(SenhaInvalidaException.class)
                    .hasMessage("A senha atual digitada não confere com a senha atual cadastrada!");

            verify(usuarioRepository, times(1)).findByLogin(usuario.getLogin());
            verify(usuarioRepository, never()).save(any(Usuario.class));
            verify(passwordEncoder, times(1)).matches(senhaAtual, usuario.getSenha());
            verify(passwordEncoder, never()).encode(novaSenha);
        }

        @Test
        void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
            TrocaSenhaDto trocaSenhaDto = new TrocaSenhaDto();
            trocaSenhaDto.setLogin("usuarioInexistente");
            trocaSenhaDto.setSenhaAtual("senhaAtual123");
            trocaSenhaDto.setNovaSenha("novaSenha123");

            when(usuarioRepository.findByLogin(trocaSenhaDto.getLogin()))
                    .thenReturn(java.util.Optional.empty());

            assertThatThrownBy(() -> usecase.execute(trocaSenhaDto))
                    .isInstanceOf(UsernameNotFoundException.class)
                    .hasMessage(trocaSenhaDto.getLogin());

            verify(usuarioRepository, times(1)).findByLogin(trocaSenhaDto.getLogin());
            verify(usuarioRepository, never()).save(any(Usuario.class));
            verify(passwordEncoder, never()).matches(anyString(), anyString());
            verify(passwordEncoder, never()).encode(anyString());
        }

    }

}
