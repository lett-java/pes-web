package br.com.wepes.masterleague.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.wepes.masterleague.domain.enums.PosicaoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Jogador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String nome;
	private Integer idade;
	private Integer contrato;
	private Integer overall;
	private BigDecimal valorDeMercado = BigDecimal.ZERO;
	private BigDecimal salario = BigDecimal.ZERO;

	@Enumerated(EnumType.STRING)
	@Column(name = "posicao")
	private PosicaoEnum posicao;

	@ManyToOne
	@JoinColumn(name = "id_clube")
	private Clube clube;
}
