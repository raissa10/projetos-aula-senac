create table tutorial (
	id serial not null,
	title varchar(250) not null,
	description varchar(250) not null,
	published boolean not null default false
);


-- BASE DE DADOS LOCAL

-- BASE DE DADOS SUPABASE