package br.com.fiap.TechChallenger.repository;

import br.com.fiap.TechChallenger.entity.Endereco;
import br.com.fiap.TechChallenger.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> getEnderecosByUsuario(Usuario usuarioEntity);
}
