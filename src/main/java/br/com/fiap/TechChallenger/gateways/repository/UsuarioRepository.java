package br.com.fiap.TechChallenger.gateways.repository;

import br.com.fiap.TechChallenger.domains.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
    boolean existsByEmail(String email);
    Optional<Usuario> findById(Long id);

    @Override
    List<Usuario> findAll();
}
