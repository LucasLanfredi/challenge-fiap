package br.com.fiap.TechChallenger.gateways.repository;

import br.com.fiap.TechChallenger.domains.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}
