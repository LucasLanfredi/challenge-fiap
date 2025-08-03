package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.TipoUsuario;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Profile("teste")
public class CriarUsuarioTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    private CriarUsuario usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new CriarUsuario(usuarioRepository, passwordEncoder, enderecoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Test
    void devePermitirCriarUsuario() {
        // Implementar o teste para criar usuário
        // Exemplo: verificar se o usuário é salvo corretamente no repositório
        UsuarioDTO usuarioDto = new UsuarioDTO();
        usuarioDto.setNome("João da Silva");
        usuarioDto.setEmail("joao@email.com");
        usuarioDto.setLogin("joao.silva");
        usuarioDto.setSenha("senha123");
        usuarioDto.setTipoUsuario(TipoUsuario.CLIENTE);
        usuarioDto.setEndereco(List.of(
                new EnderecoDTO("Rua A", "123", "Cidade A", "Estado A", "12345-678")
        ));
        Usuario usuario = gerarUsuario();

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        var response = usecase.criar(usuarioDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Usuário cadastrado com sucesso! ID: 1");

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

}
