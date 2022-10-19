-- resolucao problema alex 

drop table tbpessoa; 

-- bkp de tabela 
create table tbpessoa_bkp as 
	select * from tbpessoa

	
--tbpessoa
create table tbpessoa(
	pescodigo integer,
	pesnome varchar (100) not null,
	pessexo integer not null,
	pestipo integer not null,
	logcodigo integer,
	CONSTRAINT pk_tbpessoa PRIMARY KEY (pescodigo)
);

-- tbpessoasexo
create table tbpessoasexo(
	pessexo integer,	
	descricaosexo varchar (100) not null,
	CONSTRAINT pk_tbpessoasexo 
		PRIMARY KEY (pessexo)
);

create table tbpessoatipo(
	pestipo integer,	
	descricaotipo varchar (100) not null,
	CONSTRAINT pk_tbpessoatipo 
		PRIMARY KEY (pestipo)
);


select * from tbpessoasexo



-- busca de dados de sexo da pessoa
-- e tipo -juridica ou fisica

-- explicacao da fk 
-- on delete cascade 
-- on update e cascade


--