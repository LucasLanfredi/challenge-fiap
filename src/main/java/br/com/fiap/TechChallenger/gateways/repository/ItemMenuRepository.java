package br.com.fiap.TechChallenger.gateways.repository;

import br.com.fiap.TechChallenger.domains.ItemMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemMenuRepository extends JpaRepository<ItemMenu, Long> {
}
