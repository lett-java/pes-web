CREATE TABLE clube (
	id bigint NOT NULL AUTO_INCREMENT, 
	caixa decimal(19,2), 
	derrota integer, 
	empate integer, 
	gol_pro integer, 
	gol_sofrido integer,
	torcedor integer, 
	nome varchar(255), 
	vitoria integer, 
	id_usuario bigint, 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE jogador (
	id bigint NOT NULL AUTO_INCREMENT, 
	contrato integer, 
	idade integer, 
	nome varchar(255), 
	overall integer, 
	posicao varchar(255), 
	valor_de_mercado decimal(19,2), 
	salario decimal(19,2), 
	id_clube bigint, 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE negociacao (
	id bigint NOT NULL AUTO_INCREMENT, 
	tipo varchar(255), 
	valor_transacionado decimal(19,2), 
	id_clube_destino bigint NOT NULL, 
	id_clube_origem bigint NOT NULL, 
	id_jogador bigint, 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario (
	id bigint NOT NULL AUTO_INCREMENT, 
	nome varchar(255), 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE clube add constraint FKol5h49fo06226mk8q7veix9qv foreign key (id_usuario) references usuario (id);
ALTER TABLE jogador add constraint FKitetspsx8388j82flyldu7a1k foreign key (id_clube) references clube (id);
ALTER TABLE negociacao add constraint FKalaocu04nv1tprhme000ue2i1 foreign key (id_clube_destino) references clube (id);
ALTER TABLE negociacao add constraint FKptfnfq3usa0j54mgle01n886e foreign key (id_clube_origem) references clube (id);
ALTER TABLE negociacao add constraint FKa2m8mwebfukuc636m2k4bx8uh foreign key (id_jogador) references jogador (id);