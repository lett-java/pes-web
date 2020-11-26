package br.com.wepes.masterleague.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "partida")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Partida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_clube_mandante", referencedColumnName = "id")
	private Clube clubeMandante;

	@OneToOne
	@JoinColumn(name = "id_clube_visitante", referencedColumnName = "id")
	private Clube clubeVisitante;

	@OneToMany(mappedBy = "partida")
	private List<Gol> gols = new ArrayList<>();

	@OneToMany(mappedBy = "partida")
	private List<Assistencia> assistencias = new ArrayList<>();

	private Integer publico;

	private BigDecimal renda = BigDecimal.ZERO;
}
