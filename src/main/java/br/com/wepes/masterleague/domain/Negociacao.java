package br.com.wepes.masterleague.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.wepes.masterleague.domain.enums.TipoTransferenciaEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "negociacao")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Negociacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_jogador")
	private Jogador jogador;
	
	private BigDecimal valorTransacionado;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoTransferenciaEnum tipo;
	
	@OneToOne(optional = false)
    @JoinColumn(name = "id_clube_origem", referencedColumnName = "id")
    private Clube clubeOrigem;
	
	@OneToOne(optional = false)
    @JoinColumn(name = "id_clube_destino", referencedColumnName = "id")
    private Clube clubeDestino;

}
