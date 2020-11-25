package br.com.wepes.masterleague.domain;

import java.io.Serializable;
import java.time.YearMonth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.wepes.masterleague.domain.enums.TipoCompeticaoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "titulo")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Titulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_competicao")
	private TipoCompeticaoEnum tipoCompeticao;
	
	private YearMonth anoMes;
}
