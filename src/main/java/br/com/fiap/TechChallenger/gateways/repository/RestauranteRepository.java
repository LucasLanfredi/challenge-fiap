package br.com.fiap.TechChallenger.gateways.repository;

import br.com.fiap.TechChallenger.domains.dto.RestauranteDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<RestauranteDto, Long> {

}
