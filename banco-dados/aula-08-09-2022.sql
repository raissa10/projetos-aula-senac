
-- mostrar tipo de campo serial e sequence
insert into tbmedico(
	medcodigo,
	mednome,
	medespecialidade)
values (
	5, 
	'Bruno Coelho', 
	'Cardiologia'
);

--exemplo update 
update tbmedico set 
	mednome = 'Gelvazio Camargo',
	medespecialidade = 'Dermatologista'
where 
-- alterar somente o medico de codigo 5
medcodigo = 5

-- comando exclusao delete 1 registro
delete from tbmedico where medcodigo = 5

-- comando exclusao delete (N) registros
delete from tbmedico where 
medcodigo in (5,6,7,8)

-- EXEMPLO SELECT 
		select  --* -- ASTERISCO SELECIONA TODAS COLUNAS
		      tbmedico.medcodigo,
		      tbmedico.mednome,
		      tbmedico.medespecialidade
		 from tbmedico
	    where --medcodigo in (2,3)
	          --medcodigo not in(1,2,3)
--	          medcodigo > 2
--	      and medcodigo < 4
--	      medcodigo between 2 and 4 
	    -- usado para busca textual
-- busca todos os nomes que contem "coelho"
--	    mednome ilike '%coelho%'
-- busca todos os nomes que comecam com "bru"
--	    mednome ilike 'bru%'	    
-- busca todos os nomes que terminam com "o"
	    mednome ilike '%o'
	    
select * from tbmedico     	  
  
--exemplo comando delete 
delete from tbmedico where mednome = 'joao'

--clausula join - juntar 2 tabelas ou mais
-- Qual o nome do medico de codigo = 1
 select tbconsulta.medcodigo,
   	    tbmedico.mednome,
		tbconsulta.paccodigo,
		tbpaciente.pacnome,
		tbconsulta.dataconsulta,
		tbconsulta.horaconsulta			   
 from tbconsulta
-- traz tudo com a condicao selecionada
	               --condicao
inner join tbmedico 
    on(tbmedico.medcodigo = tbconsulta.medcodigo) 
inner join tbpaciente 
    on(tbpaciente.paccodigo = tbconsulta.paccodigo)


select * from tbmedico where medcodigo = 1 





	          







