package br.com.fiap.TechChallenger.repository;

import br.com.fiap.TechChallenger.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
    boolean existsByEmail(String email);
    Usuario getUsuarioById(Long id);
}
