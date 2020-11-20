package br.com.wepes.masterleague.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wepes.masterleague.domain.Jogador;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {

	Optional<Jogador> findByNomeIgnoreCase(final String nome);

}
