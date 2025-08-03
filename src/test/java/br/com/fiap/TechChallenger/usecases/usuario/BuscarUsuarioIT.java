package br.com.fiap.TechChallenger.usecases.usuario;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Profile("teste")
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BuscarUsuarioIT {
    @Autowired
    private BuscarUsuario buscarUsuario;

    @Test
    void deveBuscarUsuarioPorId() {
        var response = buscarUsuario.buscarTodosUsuarios();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .hasSize(2)
                .extracting("id", "nome", "email")
                .containsExactlyInAnyOrder(
                tuple(1L, "Nome do Usuário", "usuario@email.com"),
                tuple(2L, "Nome do Admin", "admin@email.com")
        );
    }
}
