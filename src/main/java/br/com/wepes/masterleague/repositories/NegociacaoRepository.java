package br.com.wepes.masterleague.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wepes.masterleague.domain.Negociacao;

@Repository
public interface NegociacaoRepository extends JpaRepository<Negociacao, Long> {

}
