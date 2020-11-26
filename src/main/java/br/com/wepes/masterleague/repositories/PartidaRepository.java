package br.com.wepes.masterleague.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wepes.masterleague.domain.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
}
