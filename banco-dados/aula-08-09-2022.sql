
-- mostrar tipo de campo serial e sequence
insert into tbmedico(
	medcodigo,
	mednome,
	medespecialidade)
values (
	8, 
	'pedro', 
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

-- comando exclusao delete n registros
delete from tbmedico where 
medcodigo in (5,6,7,8)







