create  TABLE public.tbpaciente (
                                    paccodigo int4 NOT NULL,
                                    pacnome varchar(200) NOT NULL,
                                    constraint pk_tbpaciente primary key (paccodigo)
);

CREATE TABLE public.tbmedico (
                                 medcodigo int4 NOT NULL,
                                 mednome varchar(200) NULL,
                                 medespecialidade varchar(100) NULL,
                                 CONSTRAINT pk_tbmedico PRIMARY KEY (medcodigo)
);


CREATE TABLE public.tbagenda (
                                 medcodigo int not null,
                                 paccodigo int not null,
                                 agedata date not null,
                                 agehora time without time zone,
                                 CONSTRAINT pk_tbagenda PRIMARY KEY (medcodigo,paccodigo)
);

-- exercicios 2

CREATE TABLE public.tblogradouro (
                                     logcodigo int not null,
                                     logtipo varchar (10) not null,
                                     logdescricao varchar (100) not null,
                                     CONSTRAINT pk_tblogradouro PRIMARY KEY (logcodigo)
);



CREATE TABLE public.tbpessoa (
                                 pescodigo int not null,
                                 pesnome varchar (100) not null,
                                 pessexo smallint  not null,
                                 pestipo smallint  not null,
                                 logcodigo integer not null,
                                 CONSTRAINT pk_tbpessoa PRIMARY KEY (pescodigo),
                                 CONSTRAINT "FK_TBPESSOA=>TBLOGRADOURO"
                                     FOREIGN KEY (LOGCODIGO)
                                         REFERENCES public.tbLOGRADOURO(LOGCODIGO)
);

CREATE TABLE public.tbPESSOACONTATO (
                                        pescodigo integer not null,
                                        CTPNUMERO varchar (18) not null,
                                        CTPDESCRICAO VARCHAR (100) null,
                                        CTPRAMAL integer null,
                                        CONSTRAINT pk_tbpessoacontato PRIMARY KEY (pescodigo),
                                        CONSTRAINT "FK_TBPESSOACONTATO=>TBPESSOA"
                                            FOREIGN KEY (pesCODIGO)
                                                REFERENCES public.tbpessoa(pesCODIGO)
);


CREATE TABLE public.tbIMOVEL (
                                 pescodigo integer not null,
                                 LOGCODIGO INTEGER  not null,
                                 IMVDESCRICAO VARCHAR (500) null,
                                 IMVLARGURA numeric (10,2) null,
                                 IMVCOMPRIMENTO numeric (10,2),
                                 CONSTRAINT pk_tbimovel PRIMARY KEY (pescodigo),
                                 CONSTRAINT "FK_TBIMOVEL=>TBLOGRADOURO"
                                     FOREIGN KEY (LOGCODIGO)
                                         REFERENCES public.tbLOGRADOURO(LOGCODIGO)
);



--insert pessoas
INSERT INTO public.tbpessoa
(pescodigo, pessexo, pestipo, logcodigo, pesnome)
VALUES(1, 1, 1, 2, 'Marcos');
INSERT INTO public.tbpessoa
(pescodigo, pessexo, pestipo, logcodigo, pesnome)
VALUES(2, 1, 1, 5, 'Pedro');
INSERT INTO public.tbpessoa
(pescodigo, pessexo, pestipo, logcodigo, pesnome)
VALUES(3, 1, 2, 1, 'Jos� S/A');
INSERT INTO public.tbpessoa
(pescodigo, pessexo, pestipo, logcodigo, pesnome)
VALUES(4, 2, 1, 3, 'Maria');
INSERT INTO public.tbpessoa
(pescodigo, pessexo, pestipo, logcodigo, pesnome)
VALUES(5, 2, 1, 4, 'Marta');
INSERT INTO public.tbpessoa
(pescodigo, pessexo, pestipo, logcodigo, pesnome)
VALUES(6, 1, 2, 5, 'Papel & CIA');
INSERT INTO public.tbpessoa
(pescodigo, pessexo, pestipo, logcodigo, pesnome)
VALUES(7, 1, 1, 3, 'Roberto');
INSERT INTO public.tbpessoa
(pescodigo, pessexo, pestipo, logcodigo, pesnome)
VALUES(8, 2, 1, 2, 'Tais');
INSERT INTO public.tbpessoa
(pescodigo, pessexo, pestipo, logcodigo, pesnome)
VALUES(9, 1, 2, 4, 'M�nica Presentes');
INSERT INTO public.tbpessoa
(pescodigo, pessexo, pestipo, logcodigo, pesnome)
VALUES(10, 1, 1, 5, 'Rivaldo');


