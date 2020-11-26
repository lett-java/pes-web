CREATE TABLE titulo (
	id bigint NOT NULL AUTO_INCREMENT, 
	ano_mes date NOT NULL, 
	tipo_competicao varchar(255), 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE assistencia (
	id bigint NOT NULL AUTO_INCREMENT, 
	partida bigint, 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE assistencia_jogador (
	id_jogador bigint NOT NULL, 
	id_assistencia bigint NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE gol (
	id bigint NOT NULL AUTO_INCREMENT, 
	partida bigint, 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE gol_jogador (
	id_jogador bigint NOT NULL, 
	id_gol bigint NOT NULL
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE partida (
	id bigint NOT NULL AUTO_INCREMENT, 
	publico integer, 
	renda decimal(19,2), 
	id_clube_mandante bigint, 
	id_clube_visitante bigint, 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE assistencia add constraint FKh7fd2iq084q168n4y5fvwtoj foreign key (partida) references partida (id);
ALTER TABLE assistencia_jogador add constraint FK5sat59drem76t8jm86ypsn7dh foreign key (id_assistencia) references jogador (id);
ALTER TABLE assistencia_jogador add constraint FKbq4mprua09q43oyixuqe5si5m foreign key (id_jogador) references assistencia (id);
ALTER TABLE gol add constraint FKd2fqgybpehin93vsp7ugt8rve foreign key (partida) references partida (id);
ALTER TABLE gol_jogador add constraint FKhmjl4o5gpnt50p3d4av4arjwt foreign key (id_gol) references jogador (id);
ALTER TABLE gol_jogador add constraint FKfs650i4268m26pqj9ga331kph foreign key (id_jogador) references gol (id);
ALTER TABLE partida add constraint FKftq2r314dg0676juxjov4fmv7 foreign key (id_clube_mandante) references clube (id);
ALTER TABLE partida add constraint FKi3rwaar5v3iu5cbwk7ww6fcwy foreign key (id_clube_visitante) references clube (id);