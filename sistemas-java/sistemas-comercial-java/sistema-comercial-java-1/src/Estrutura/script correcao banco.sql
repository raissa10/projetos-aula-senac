alter table PESSOA  add NR_INSCRICAO_ESTADUAL varchar (100);
alter table PESSOA  add  TIPO_CONSUMO smallint;
alter table PESSOA  add  REGIME_TRIBUTACAO smallint;
alter table PESSOA  add  FG_TRANSPORTADOR smallint;
alter table PESSOA  add  FG_ATIVO smallint;


CREATE SEQUENCE CD_CONDICAO_PAGAMENTO;
ALTER SEQUENCE CD_CONDICAO_PAGAMENTO RESTART WITH 0;
CREATE SEQUENCE CD_TIPO_COBRANCA;
ALTER SEQUENCE CD_TIPO_COBRANCA RESTART WITH 0;
		
CREATE TABLE TIPO_COBRANCA (
    CD_COBRANCA                    INTEGER NOT NULL,
    DS_COBRANCA                    VARCHAR(30) NOT NULL,
    FG_IMEDIATO                    SMALLINT DEFAULT 0,
    FG_CHEQUE                  SMALLINT DEFAULT 0 NOT NULL,
    FG_CARTAO                  SMALLINT DEFAULT 0 NOT NULL,    
    CD_FILIAL                  INTEGER NOT NULL,
    CD_USUARIO                 SMALLINT NOT NULL,
    DT_ALT                     DATE NOT NULL,
    HR_ALT                     TIME NOT NULL,
    DT_CAD                     DATE NOT NULL,
    HR_CAD                     TIME NOT NULL,	
	FG_CREDIARIO               SMALLINT DEFAULT 0 NOT NULL,
    FG_BOLETO             SMALLINT,
    FG_QUITA_QUANDO_GERA             SMALLINT,    
	FG_ATIVO                 SMALLINT    
);