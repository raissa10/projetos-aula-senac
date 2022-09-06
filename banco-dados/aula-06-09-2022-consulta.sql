CREATE TABLE public.tbconsulta (
	medcodigo int4 NOT NULL,
	paccodigo int4 NOT NULL,
	dataconsulta date not null,
	horaconsulta time not null,	
	CONSTRAINT pk_tbconsulta 
	PRIMARY KEY (medcodigo, paccodigo)
);

delete from tbconsulta; 

--FK MEDICO
alter table public.tbconsulta add CONSTRAINT 
"FK_TBCONSULTA=>TBMEDICO" 
FOREIGN KEY (medcodigo) 
REFERENCES public.tbmedico(medcodigo);


--FK PACIENTE
alter table public.tbconsulta add CONSTRAINT 
"FK_TBCONSULTA=>TBPACIENTE" 
FOREIGN KEY (paccodigo) 
REFERENCES public.tbpaciente(paccodigo);

select * from tbmedico 

select * from tbpaciente 

select * from tbconsulta  

insert into tbconsulta 
	(medcodigo,
	 paccodigo, 
	 dataconsulta, 
	 horaconsulta)
values ( 
1,
1,
'2022-09-06', 
'16:30:00'
); 


select * from tbconsulta 