create table clube (
	id bigint not null auto_increment, 
	caixa decimal(19,2), 
	derrota integer, 
	empate integer, 
	gol_pro integer, 
	gol_sofrido integer, 
	nome varchar(255),
	vitoria integer, 
	id_usuario bigint, 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table jogador (
	id integer not null auto_increment, 
	contrato integer, 
	nome varchar(255), 
	overall integer, 
	posicao varchar(255), 
	id_clube bigint, 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table titulo (
	id bigint not null auto_increment, 
	tipo_competicao integer, 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table usuario (
	id bigint not null auto_increment, 
	nome varchar(255), 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table clube add constraint FKol5h49fo06226mk8q7veix9qv foreign key (id_usuario) references usuario (id);
alter table jogador add constraint FKolc28dr5d9w2xlswigavku60h foreign key (id_clube) references clube (id);





















