select * from tbmedico 

-- paciente
CREATE TABLE public.tbpaciente (
	paccodigo int4 NOT NULL,
	pacnome varchar(200) NOT NULL,
	constraint pk_tbpaciente primary key (paccodigo) 	
);

--medico
CREATE TABLE public.tbmedico (
	medcodigo int4 NOT NULL,
	mednome varchar(200) NOT NULL,
	medespecialidade varchar(100) NOT null,
	-- primary_key
	constraint pk_tbmedico primary key (medcodigo) 	
);

--opcao 02 para criar pk 
alter table tbmedico add 
constraint pk_tbmedico primary key (medcodigo);

delete from tbmedico;

--copiar e executar 
insert into 
tbmedico(medcodigo,mednome,medespecialidade)
values (1, 'mauricio de nassau', 'Cardiologia');

insert into 
tbmedico(medcodigo,mednome,medespecialidade)
values (2, 'jorge amado', 'Dermatologista');

insert into 
tbmedico(medcodigo,mednome,medespecialidade)
values (3, 'paulo coelho', 'Cardiologia');

insert into 
tbmedico(medcodigo,mednome,medespecialidade)
values (4, 'William Bonner', 'Neurologista');

select * from tbmedico 












