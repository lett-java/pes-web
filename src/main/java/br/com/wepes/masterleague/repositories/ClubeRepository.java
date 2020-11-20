package br.com.wepes.masterleague.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wepes.masterleague.domain.Clube;

@Repository
public interface ClubeRepository extends JpaRepository<Clube, Long> {

	Optional<Clube> findByNomeIgnoreCase(final String nome);

}
