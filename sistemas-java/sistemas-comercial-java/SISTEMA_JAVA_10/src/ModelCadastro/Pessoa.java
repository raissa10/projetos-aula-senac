package ModelCadastro;

import java.sql.Time;
import java.sql.Date;

/**
 *
 * @author Gelvazio Camargo *
 */
/*
 Tabela Pessoa
 CREATE TABLE PESSOA (
 CD_PESSOA              INTEGER NOT NULL,
 NM_PESSOA              VARCHAR(400),
 FG_CLIENTE             SMALLINT NOT NULL,
 FG_VENDEDOR            SMALLINT NOT NULL,
 FG_FORNECEDOR          SMALLINT NOT NULL,
 DS_ENDERECO            VARCHAR(400),
 NR_ENDERECO            VARCHAR(50),
 DS_BAIRRO              VARCHAR(200),
 CD_ESTADO              VARCHAR(2),
 CD_CIDADE              INTEGER,
 CD_PAIS                INTEGER DEFAULT 1058,
 CD_CEP                 VARCHAR(8),
 DS_EMAIL               VARCHAR(200),
 NR_TELEFONE            VARCHAR(200),
 CD_CGCCPF              VARCHAR(25),
 CD_USUARIO             SMALLINT NOT NULL,
 DT_ALT                 DATE NOT NULL,
 HR_ALT                 TIME NOT NULL,
 DT_CAD                 DATE NOT NULL,
 HR_CAD                 TIME NOT NULL,
 CD_FILIAL              INTEGER NOT NULL,
 NR_INSCRICAO_ESTADUAL  VARCHAR(200),
 TIPO_CONSUMO           INTEGER,
 REGIME_TRIBUTACAO      INTEGER,
 FG_TRANSPORTADOR       SMALLINT
 );

 */
public class Pessoa {

    private int cd_pessoa;
    private String nm_pessoa;
    private int fg_cliente;
    private int fg_vendedor;
    private int fg_fornecedor;
    private String ds_endereco;
    private String nr_endereco;
    private String ds_bairro;
    private String cd_estado;
    private int cd_cidade;
    private int cd_pais;
    private String cd_cep;
    private String ds_email;
    private String nr_telefone;
    private String cd_cgccpf;
    private int cd_usuario;
    private Date dt_alt;
    private Time hr_alt;
    private Date dt_cad;
    private Time hr_cad;
    private int cd_filial;
    private String nr_inscricao_estadual;
    private int tipo_consumo;
    private int regime_tributacao;
    private int fg_transportador;
    private int fg_ativo;

    public Pessoa(int cd_pessoa, String nm_pessoa, int fg_cliente, int fg_vendedor, int fg_fornecedor, String ds_endereco, String nr_endereco, String ds_bairro, String cd_estado, int cd_cidade, int cd_pais, String cd_cep, String ds_email, String nr_telefone, String cd_cgccpf, int cd_usuario, Date dt_alt, Time hr_alt, Date dt_cad, Time hr_cad, int cd_filial, String nr_inscricao_estadual, int tipo_consumo, int regime_tributacao, int fg_transportador, int fg_ativo) {
        this.cd_pessoa = cd_pessoa;
        this.nm_pessoa = nm_pessoa;
        this.fg_cliente = fg_cliente;
        this.fg_vendedor = fg_vendedor;
        this.fg_fornecedor = fg_fornecedor;
        this.ds_endereco = ds_endereco;
        this.nr_endereco = nr_endereco;
        this.ds_bairro = ds_bairro;
        this.cd_estado = cd_estado;
        this.cd_cidade = cd_cidade;
        this.cd_pais = cd_pais;
        this.cd_cep = cd_cep;
        this.ds_email = ds_email;
        this.nr_telefone = nr_telefone;
        this.cd_cgccpf = cd_cgccpf;
        this.cd_usuario = cd_usuario;
        this.dt_alt = dt_alt;
        this.hr_alt = hr_alt;
        this.dt_cad = dt_cad;
        this.hr_cad = hr_cad;
        this.cd_filial = cd_filial;
        this.nr_inscricao_estadual = nr_inscricao_estadual;
        this.tipo_consumo = tipo_consumo;
        this.regime_tributacao = regime_tributacao;
        this.fg_transportador = fg_transportador;
        this.fg_ativo = fg_ativo;
    }

    public int getCd_pessoa() {
        return cd_pessoa;
    }

    public void setCd_pessoa(int cd_pessoa) {
        this.cd_pessoa = cd_pessoa;
    }

    public String getNm_pessoa() {
        return nm_pessoa;
    }

    public void setNm_pessoa(String nm_pessoa) {
        this.nm_pessoa = nm_pessoa;
    }

    public int getFg_cliente() {
        return fg_cliente;
    }

    public void setFg_cliente(int fg_cliente) {
        this.fg_cliente = fg_cliente;
    }

    public int getFg_vendedor() {
        return fg_vendedor;
    }

    public void setFg_vendedor(int fg_vendedor) {
        this.fg_vendedor = fg_vendedor;
    }

    public int getFg_fornecedor() {
        return fg_fornecedor;
    }

    public void setFg_fornecedor(int fg_fornecedor) {
        this.fg_fornecedor = fg_fornecedor;
    }

    public String getDs_endereco() {
        return ds_endereco;
    }

    public void setDs_endereco(String ds_endereco) {
        this.ds_endereco = ds_endereco;
    }

    public String getNr_endereco() {
        return nr_endereco;
    }

    public void setNr_endereco(String nr_endereco) {
        this.nr_endereco = nr_endereco;
    }

    public String getDs_bairro() {
        return ds_bairro;
    }

    public void setDs_bairro(String ds_bairro) {
        this.ds_bairro = ds_bairro;
    }

    public String getCd_estado() {
        return cd_estado;
    }

    public void setCd_estado(String cd_estado) {
        this.cd_estado = cd_estado;
    }

    public int getCd_cidade() {
        return cd_cidade;
    }

    public void setCd_cidade(int cd_cidade) {
        this.cd_cidade = cd_cidade;
    }

    public int getCd_pais() {
        return cd_pais;
    }

    public void setCd_pais(int cd_pais) {
        this.cd_pais = cd_pais;
    }

    public String getCd_cep() {
        return cd_cep;
    }

    public void setCd_cep(String cd_cep) {
        this.cd_cep = cd_cep;
    }

    public String getDs_email() {
        return ds_email;
    }

    public void setDs_email(String ds_email) {
        this.ds_email = ds_email;
    }

    public String getNr_telefone() {
        return nr_telefone;
    }

    public void setNr_telefone(String nr_telefone) {
        this.nr_telefone = nr_telefone;
    }

    public String getCd_cgccpf() {
        return cd_cgccpf;
    }

    public void setCd_cgccpf(String cd_cgccpf) {
        this.cd_cgccpf = cd_cgccpf;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

    public Date getDt_alt() {
        return dt_alt;
    }

    public void setDt_alt(Date dt_alt) {
        this.dt_alt = dt_alt;
    }

    public Time getHr_alt() {
        return hr_alt;
    }

    public void setHr_alt(Time hr_alt) {
        this.hr_alt = hr_alt;
    }

    public Date getDt_cad() {
        return dt_cad;
    }

    public void setDt_cad(Date dt_cad) {
        this.dt_cad = dt_cad;
    }

    public Time getHr_cad() {
        return hr_cad;
    }

    public void setHr_cad(Time hr_cad) {
        this.hr_cad = hr_cad;
    }

    public int getCd_filial() {
        return cd_filial;
    }

    public void setCd_filial(int cd_filial) {
        this.cd_filial = cd_filial;
    }

    public String getNr_inscricao_estadual() {
        return nr_inscricao_estadual;
    }

    public void setNr_inscricao_estadual(String nr_inscricao_estadual) {
        this.nr_inscricao_estadual = nr_inscricao_estadual;
    }

    public int getTipo_consumo() {
        return tipo_consumo;
    }

    public void setTipo_consumo(int tipo_consumo) {
        this.tipo_consumo = tipo_consumo;
    }

    public int getRegime_tributacao() {
        return regime_tributacao;
    }

    public void setRegime_tributacao(int regime_tributacao) {
        this.regime_tributacao = regime_tributacao;
    }

    public int getFg_transportador() {
        return fg_transportador;
    }

    public void setFg_transportador(int fg_transportador) {
        this.fg_transportador = fg_transportador;
    }

    public int getFg_ativo() {
        return fg_ativo;
    }

    public void setFg_ativo(int fg_ativo) {
        this.fg_ativo = fg_ativo;
    }

}
