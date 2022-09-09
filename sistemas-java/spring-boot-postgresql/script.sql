create table tutorial (
	id serial not null,
	title varchar(250) not null,
	description varchar(250) not null,
	published boolean not null default false
);


-- BASE DE DADOS LOCAL

-- BASE DE DADOS SUPABASE


#BANCO DE DADOS LOCAL
#spring.datasource.url= jdbc:postgresql://localhost:5432/postgres
#spring.datasource.username= postgres
#spring.datasource.password= postgres

# BANCO DE DADOS ONLINE
spring.datasource.url= jdbc:postgresql://localhost:5432/postgres
spring.datasource.username= postgres
spring.datasource.password= postgres