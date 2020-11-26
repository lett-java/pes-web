package br.com.wepes.masterleague.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Gol implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "partida", referencedColumnName = "id")
	private Partida partida;

	@ManyToMany
	@JoinTable(name = "gol_jogador", 
		joinColumns = @JoinColumn(name = "id_jogador"), 
		inverseJoinColumns = @JoinColumn(name = "id_gol"))
	private List<Jogador> jogadores = new ArrayList<>();

}
