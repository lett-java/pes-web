CREATE TABLE titulo (
	id bigint not null auto_increment, 
	ano_mes date NOT NULL, 
	tipo_competicao varchar(255), 
	
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;