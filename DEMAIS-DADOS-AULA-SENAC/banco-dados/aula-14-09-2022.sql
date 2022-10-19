/*
1 -  conferir listas exercicios

Leandro - ok - nao fez a lista 1 e a lista 2 e a lista 3

Carlos e Eduardo - ok nao fizeram tudo a lista 2

Amanda - Nao fez nenhum exercicio ainda

2 - Lista Exercicios 03
todos fizeram a pergunta 01.

3 - Explicacao de assunto pendente
seguir apostila treina web

assunto novo:
DQL - EXPLICAR TUDO DO ZERO

SELECT - ok
WHERE - ok
AND, OR, NOT - ok
COUNT - ok
SUM - ok
AVG - media
MIN - ok
MAX - ok
GROUP BY - ok
ORDER BY - OK
LIMIT - OK
INNER JOIN - OK
LEFT JOIN - ok
--nao sera usado nos fontes
RIGHT JOIN - ok
HAVING - ok

*/


-- COMENTARIO DE 1 LINHA 

/*
 * COMENTARIO DE N LINHAS
 * 
 */
select COLUNA from TABELA 

select tbmedico.medcodigo as codigo, 
	   mednome as nome, 
	   medespecialidade as especialidade 
  from tbmedico
  where 1 = 1
  -- clausula and
    -- and mednome ilike '%mau%'
    --and medespecialidade ilike '%cardio%'
  	-- clausula or 
   --and medespecialidade ilike '%cardio%' 
  -- or medespecialidade ilike '%derma%'
    --clausula not in - nao vai listar 1 e 3 
  -- and medcodigo not in (1,3)
  --clausula in-vai listar apenas 1 e 3 
  --and medcodigo in (1,3)
  
 -- EXEMPLO COUNT
 -- Contar quantos medicos
 -- existem na tabela medico? 
 select count(*) as total from tbmedico
 
 -- exemplo sum 
 alter table tbconsulta 
 	add column valor numeric(10,2)
 
update tbconsulta set valor = 250 

-- soma do valor de todas as consultas 
 select sum(valor) as totalsoma 
 from tbconsulta
 
-- avg e a media  
select avg(valor) as media from tbconsulta 

-- min - retorna o menor valor da coluna
select min(medcodigo) as menor from tbmedico 

-- max - retorna o maior valor da coluna
select max(medcodigo) as maior from tbmedico

-- nome do medico com o maior codigo
-- group by - usado para agrupar 

  select count(*) as total, 
	     mednome 
	from tbmedico
group by mednome

select * from tbmedico 



-- agrupa as especialidades de medicos 
  select count(*) as total, 
	     medespecialidade  
	from tbmedico
group by medespecialidade

-- order by - ordena por coluna x 
-- em ordem crescente ou descrescente   
  select count(*) as total, 
	     medespecialidade  
	from tbmedico
group by medespecialidade
order by total desc, 
	     medespecialidade asc

  select * 
	from tbmedico 
order by medespecialidade, 
		 mednome 

 -- LIMIT 
   select * 
     from tbmedico 
 order by medcodigo desc 
    limit 2
 
select * from tbconsulta 


-- inner join 
-- quantas consultas 
-- tem um medico x(nome do medico)? 
select count(*) as totalconsulta,
	   tbmedico.medcodigo,
	   tbmedico.mednome
  from tbconsulta
  -- junta as chaves primarias 
inner join tbmedico 
	 on(tbmedico.medcodigo = tbconsulta.medcodigo)
group by tbmedico.medcodigo,
	     tbmedico.mednome
order by totalconsulta 	   

-- quais medicos um paciente ja se consultou?
    select tbmedico.mednome as medico,
           tbpaciente.pacnome as paciente
      from tbconsulta
  -- junta as chaves primarias 
inner join tbmedico 
	    on (tbmedico.medcodigo = tbconsulta.medcodigo)
inner join tbpaciente 
        on (tbpaciente.paccodigo = tbconsulta.paccodigo)	 
  order by tbpaciente.pacnome   

 -- quais medicos um paciente ja se consultou-contador?
    select count(*) as consultas,
    	   tbmedico.mednome as medico,
           tbpaciente.pacnome as paciente
      from tbconsulta
  -- junta as chaves primarias 
inner join tbmedico 
	    on (tbmedico.medcodigo = tbconsulta.medcodigo)
inner join tbpaciente 
        on (tbpaciente.paccodigo = tbconsulta.paccodigo)	 
  group by tbmedico.mednome,
           tbpaciente.pacnome
  order by tbpaciente.pacnome   

  -- ALTERANDO A CHAVE DA TABELA CONSULTA 
alter table tbconsulta 
drop CONSTRAINT pk_tbconsulta;

alter table tbconsulta add 
constraint pk_tbconsulta
PRIMARY KEY (medcodigo, paccodigo,dataconsulta);

-- LISTAR TODOS OS PACIENTES 
-- QUE NAO TIVERAM NENHUMA 
-- CONSULTA...
   select tbpaciente.* 
     from tbpaciente 
right join tbconsulta -- traz somente da tbconsulta
	   on (tbpaciente.paccodigo = tbconsulta.paccodigo)
 
   select tbpaciente.* 
     from tbpaciente 
left join tbconsulta -- traz todos
	   on (tbpaciente.paccodigo = tbconsulta.paccodigo)       
	where tbconsulta.paccodigo is null    

--HAVING - USADO EM CLAUSULAS AGRUPADORAS
    select count(*) as totalconsulta,
           mednome 
      from tbconsulta
inner join tbmedico 
	 on (tbmedico.medcodigo = TBCONSULTA.medcodigo)
group by mednome
-- buscando apenas quem teve quantidade x 
-- de consulta
-- so traz quem trouxe mais que 2 consultas 
having (count(*) < 11)




	   
	   
 
		 
		 




  
  
  
  
