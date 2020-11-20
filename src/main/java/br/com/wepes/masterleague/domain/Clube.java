package br.com.wepes.masterleague.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "clube")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Clube implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private String nome;
	private Integer vitoria = 0;
	private Integer empate = 0;
	private Integer derrota = 0;
	private Integer golPro = 0;
	private Integer golSofrido = 0;
	private BigDecimal caixa = BigDecimal.ZERO;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario")
	private Usuario treinador;

	@OneToMany(mappedBy = "clube")
	private List<Jogador> jogadores = new ArrayList<>();

}
