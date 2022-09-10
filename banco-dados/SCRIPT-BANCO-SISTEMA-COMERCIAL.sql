create schema sistemacomercial;

select * from sistemacomercial.tbcliente

create table public.tbproduto(
	procodigo serial not null,
	prodescricao varchar(200) not null,
	propreco numeric(10,2) not null,
	constraint pk_tbproduto primary key (procodigo)
);

create table public.tbcliente(
	clicodigo serial not null,
	clinome varchar(200) not null,
	clicpf varchar(20) not null,
	cliendereco varchar(200) not null,
	constraint pk_tbcliente primary key (clicodigo)	
);


create table public.tbvendedor(
	clicodigo int not null,
	ventaxacomissao numeric(10,2) not null,
    constraint pk_tbvendedor primary key (clicodigo)	
);

create table public.tbvenda(
	vencodigo serial not null,
	clicodigo int2 not null,
	vendedor int2 not null,
	ventotalcusto numeric(10,2) not null,	
	ventotalvenda numeric(10,2) not null,	
	ventotalcomissao numeric(10,2) not null,
	vendata date not null,
	constraint pk_tbvenda primary key (vencodigo)
);

create table public.tbitem(
	itecodigo serial not null,
	vencodigo int2 not null,
	procodigo int2 not null,
	quantidade int2 not null,
	precocusto numeric(10,2) not null,
	precovenda numeric(10,2) not null,
	constraint pk_tbitem primary key (vencodigo, itecodigo)
);

-- inserts 
INSERT INTO public.tbcliente
(clicodigo, clinome, clicpf, cliendereco)
VALUES(1, 'gelvazio camargo', '061.023.124-87', 'Estrada São José, 540, Santana');

INSERT INTO public.tbcliente
(clicodigo, clinome, clicpf, cliendereco)
VALUES(2, 'Jessica Moratelli', '080.214.146-75', 'Estrada São Jose, 540, Casa Rosa');


--script rh senac 
create table public.tbfolha(
	focodigo serial not null,
	clicodigo int 2 not null,
	tipo varchar(100) not null,
	competencia date not null,
	provento numeric(10,2) not null,
	desconto numeric(10,2) not null,
	liquido numeric(10,2) not null,	
	constraint pk_tbfolha primary key (focodigo)
);

--inserts folha 

insert into tbfolha(clicodigo, tipo,competencia,provento,desconto,liquido)
	values(1, 'Folha Mensal','01/08/2022', 1954.78, 154.56, 1654.78);