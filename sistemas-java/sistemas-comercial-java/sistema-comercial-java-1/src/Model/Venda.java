/*
 * Copyright (C) 2014 Gelvazio Camargo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Model;

//import java.util.Date;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Gelvazio Camargo
 */
/*
 Campos da Tabela no banco de dados
 CD_FILIAL              SMALLINT NOT NULL,
 CD_MOVIMENTO           INTEGER NOT NULL,
 CD_VENDE               INTEGER,
 CD_PAGTO               INTEGER,
 CD_PESSOA              INTEGER NOT NULL,
 DT_EMI_DOC             DATE,
 DT_SAI_DOC             DATE,
 VL_TOT_CUS_DOC         NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_TOT_PRO_DOC         NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_ACRESCIMO           NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_DESCONTO            NUMERIC(16,4) DEFAULT 0 NOT NULL,
 CD_TIPO_DOC            INTEGER NOT NULL,
 FG_SITUACAO            SMALLINT,
 FG_MOVIMENTOU_ESTOQUE  SMALLINT NOT NULL,
 CD_USUARIO             SMALLINT NOT NULL,
 DT_ALT                 DATE NOT NULL,
 HR_ALT                 TIME NOT NULL,
 DT_CAD                 DATE NOT NULL,
 HR_CAD                 TIME NOT NULL,
 CD_CFOP                INTEGER NOT NULL,
 VL_BASE_ICM_TOTAL      NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_ICM_TOTAL           NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_BASE_ICM_SUB_TOTAL  NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_ICM_SUB_TOTAL       NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_BASE_PIS_TOTAL      NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_PIS_TOTAL           NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_BASE_COFINS_TOTAL   NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_COFINS_TOTAL        NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_BASE_IPI_TOTAL      NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_IPI_TOTAL           NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_BASE_SERVICO_TOTAL  NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_SERVICO_TOTAL       NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_BASE_ISSQN_TOTAL    NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_ISSQN_TOTAL         NUMERIC(16,4) DEFAULT 0 NOT NULL,
 CD_TRANSPORTADORA      INTEGER NOT NULL,
 NR_PLACA_VEICULO       VARCHAR(200),
 QTD_VOLUME             INTEGER NOT NULL,
 FG_EMITENTE            INTEGER NOT NULL,
 VL_TOT_FRETE           NUMERIC(16,4) DEFAULT 0 NOT NULL,
 NM_ESPECIE             VARCHAR(200),
 NR_NOTA_NFE            INTEGER NOT NULL,
 NR_CHAVE_NFE           INTEGER NOT NULL,
 NR_PROT_AUTORIZACAO    NUMERIC(16,4) DEFAULT 0 NOT NULL,
 DS_INF_ADICIONAIS      VARCHAR(400),
 VL_TOT_SEGURO          NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_TOT_PEDIDO_NOTA     NUMERIC(16,4) DEFAULT 0 NOT NULL,
 DS_MARCA               VARCHAR(200),
 NR_NUMERACAO           NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_PESO_LIQUIDO        NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_PESO_BRUTO          NUMERIC(16,4) DEFAULT 0 NOT NULL,
 VL_TOT_IMPOSTOS        NUMERIC(16,4) DEFAULT 0 NOT NULL
 */
public class Venda {

    private int cd_filial;
    private int cd_movimento;
    private int cd_vende;
    private int cd_pagto;
    private int cd_pessoa;
    private Date dt_emi_doc;
    private Date dt_sai_doc;
    private double vl_tot_cus_doc;
    private double vl_tot_pro_doc;
    private double vl_acrescimo;
    private double vl_desconto;
    private int cd_tipo_doc;
    private int fg_situacao;
    private int fg_movimentou_estoque;
    private int cd_usuario;
    private Date dt_alt;
    private Time hr_alt;
    private Date dt_cad;
    private Time hr_cad;
    private int cfop;
    private double vl_base_icm_total;
    private double vl_icm_total;
    private double vl_base_icm_sub_total;
    private double vl_icm_sub_total;
    private double vl_base_pis_total;
    private double vl_pis_total;
    private double vl_base_cofins_total;
    private double vl_cofins_total;
    private double vl_base_ipi_total;
    private double vl_ipi_total;
    private double vl_base_servico_total;
    private double vl_servico_total;
    private double vl_base_issqn_total;
    private double vl_issqn_total;
    private int cd_transportadora;
    private String nr_placa_veiculo;
    private int qtd_volume;
    private int fg_emitente;
    private double vl_tot_frete;
    private String nm_especie;
    private String nr_nota_nfe;
    private String nr_chave_nfe;
    private String nr_prot_autorizacao;
    private String ds_inf_adicionais;
    private double vl_tot_seguro;
    private double vl_tot_pedido_nota;
    private String ds_marca;
    private int nr_numeracao;
    private double vl_peso_liquido;
    private double vl_peso_bruto;
    private double vl_tot_impostos;

    public Venda(int cd_filial, int cd_movimento, int cd_vende, int cd_pagto, int cd_pessoa, Date dt_emi_doc, Date dt_sai_doc, double vl_tot_cus_doc, double vl_tot_pro_doc, double vl_acrescimo, double vl_desconto, int cd_tipo_doc, int fg_situacao, int fg_movimentou_estoque, int cd_usuario, Date dt_alt, Time hr_alt, Date dt_cad, Time hr_cad, int cfop, double vl_base_icm_total, double vl_icm_total, double vl_base_icm_sub_total, double vl_icm_sub_total, double vl_base_pis_total, double vl_pis_total, double vl_base_cofins_total, double vl_cofins_total, double vl_base_ipi_total, double vl_ipi_total, double vl_base_servico_total, double vl_servico_total, double vl_base_issqn_total, double vl_issqn_total, int cd_transportadora, String nr_placa_veiculo, int qtd_volume, int fg_emitente, double vl_tot_frete, String nm_especie, String nr_nota_nfe, String nr_chave_nfe, String nr_prot_autorizacao, String ds_inf_adicionais, double vl_tot_seguro, double vl_tot_pedido_nota, String ds_marca, int nr_numeracao, double vl_peso_liquido, double vl_peso_bruto, double vl_tot_impostos) {
        this.cd_filial = cd_filial;
        this.cd_movimento = cd_movimento;
        this.cd_vende = cd_vende;
        this.cd_pagto = cd_pagto;
        this.cd_pessoa = cd_pessoa;
        this.dt_emi_doc = dt_emi_doc;
        this.dt_sai_doc = dt_sai_doc;
        this.vl_tot_cus_doc = vl_tot_cus_doc;
        this.vl_tot_pro_doc = vl_tot_pro_doc;
        this.vl_acrescimo = vl_acrescimo;
        this.vl_desconto = vl_desconto;
        this.cd_tipo_doc = cd_tipo_doc;
        this.fg_situacao = fg_situacao;
        this.fg_movimentou_estoque = fg_movimentou_estoque;
        this.cd_usuario = cd_usuario;
        this.dt_alt = dt_alt;
        this.hr_alt = hr_alt;
        this.dt_cad = dt_cad;
        this.hr_cad = hr_cad;
        this.cfop = cfop;
        this.vl_base_icm_total = vl_base_icm_total;
        this.vl_icm_total = vl_icm_total;
        this.vl_base_icm_sub_total = vl_base_icm_sub_total;
        this.vl_icm_sub_total = vl_icm_sub_total;
        this.vl_base_pis_total = vl_base_pis_total;
        this.vl_pis_total = vl_pis_total;
        this.vl_base_cofins_total = vl_base_cofins_total;
        this.vl_cofins_total = vl_cofins_total;
        this.vl_base_ipi_total = vl_base_ipi_total;
        this.vl_ipi_total = vl_ipi_total;
        this.vl_base_servico_total = vl_base_servico_total;
        this.vl_servico_total = vl_servico_total;
        this.vl_base_issqn_total = vl_base_issqn_total;
        this.vl_issqn_total = vl_issqn_total;
        this.cd_transportadora = cd_transportadora;
        this.nr_placa_veiculo = nr_placa_veiculo;
        this.qtd_volume = qtd_volume;
        this.fg_emitente = fg_emitente;
        this.vl_tot_frete = vl_tot_frete;
        this.nm_especie = nm_especie;
        this.nr_nota_nfe = nr_nota_nfe;
        this.nr_chave_nfe = nr_chave_nfe;
        this.nr_prot_autorizacao = nr_prot_autorizacao;
        this.ds_inf_adicionais = ds_inf_adicionais;
        this.vl_tot_seguro = vl_tot_seguro;
        this.vl_tot_pedido_nota = vl_tot_pedido_nota;
        this.ds_marca = ds_marca;
        this.nr_numeracao = nr_numeracao;
        this.vl_peso_liquido = vl_peso_liquido;
        this.vl_peso_bruto = vl_peso_bruto;
        this.vl_tot_impostos = vl_tot_impostos;
    }

    public int getCd_filial() {
        return cd_filial;
    }

    public void setCd_filial(int cd_filial) {
        this.cd_filial = cd_filial;
    }

    public int getCd_movimento() {
        return cd_movimento;
    }

    public void setCd_movimento(int cd_movimento) {
        this.cd_movimento = cd_movimento;
    }

    public int getCd_vende() {
        return cd_vende;
    }

    public void setCd_vende(int cd_vende) {
        this.cd_vende = cd_vende;
    }

    public int getCd_pagto() {
        return cd_pagto;
    }

    public void setCd_pagto(int cd_pagto) {
        this.cd_pagto = cd_pagto;
    }

    public int getCd_pessoa() {
        return cd_pessoa;
    }

    public void setCd_pessoa(int cd_pessoa) {
        this.cd_pessoa = cd_pessoa;
    }

    public Date getDt_emi_doc() {
        return dt_emi_doc;
    }

    public void setDt_emi_doc(Date dt_emi_doc) {
        this.dt_emi_doc = dt_emi_doc;
    }

    public Date getDt_sai_doc() {
        return dt_sai_doc;
    }

    public void setDt_sai_doc(Date dt_sai_doc) {
        this.dt_sai_doc = dt_sai_doc;
    }

    public double getVl_tot_cus_doc() {
        return vl_tot_cus_doc;
    }

    public void setVl_tot_cus_doc(double vl_tot_cus_doc) {
        this.vl_tot_cus_doc = vl_tot_cus_doc;
    }

    public double getVl_tot_pro_doc() {
        return vl_tot_pro_doc;
    }

    public void setVl_tot_pro_doc(double vl_tot_pro_doc) {
        this.vl_tot_pro_doc = vl_tot_pro_doc;
    }

    public double getVl_acrescimo() {
        return vl_acrescimo;
    }

    public void setVl_acrescimo(double vl_acrescimo) {
        this.vl_acrescimo = vl_acrescimo;
    }

    public double getVl_desconto() {
        return vl_desconto;
    }

    public void setVl_desconto(double vl_desconto) {
        this.vl_desconto = vl_desconto;
    }

    public int getCd_tipo_doc() {
        return cd_tipo_doc;
    }

    public void setCd_tipo_doc(int cd_tipo_doc) {
        this.cd_tipo_doc = cd_tipo_doc;
    }

    public int getFg_situacao() {
        return fg_situacao;
    }

    public void setFg_situacao(int fg_situacao) {
        this.fg_situacao = fg_situacao;
    }

    public int getFg_movimentou_estoque() {
        return fg_movimentou_estoque;
    }

    public void setFg_movimentou_estoque(int fg_movimentou_estoque) {
        this.fg_movimentou_estoque = fg_movimentou_estoque;
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

    public int getCfop() {
        return cfop;
    }

    public void setCfop(int cfop) {
        this.cfop = cfop;
    }

    public double getVl_base_icm_total() {
        return vl_base_icm_total;
    }

    public void setVl_base_icm_total(double vl_base_icm_total) {
        this.vl_base_icm_total = vl_base_icm_total;
    }

    public double getVl_icm_total() {
        return vl_icm_total;
    }

    public void setVl_icm_total(double vl_icm_total) {
        this.vl_icm_total = vl_icm_total;
    }

    public double getVl_base_icm_sub_total() {
        return vl_base_icm_sub_total;
    }

    public void setVl_base_icm_sub_total(double vl_base_icm_sub_total) {
        this.vl_base_icm_sub_total = vl_base_icm_sub_total;
    }

    public double getVl_icm_sub_total() {
        return vl_icm_sub_total;
    }

    public void setVl_icm_sub_total(double vl_icm_sub_total) {
        this.vl_icm_sub_total = vl_icm_sub_total;
    }

    public double getVl_base_pis_total() {
        return vl_base_pis_total;
    }

    public void setVl_base_pis_total(double vl_base_pis_total) {
        this.vl_base_pis_total = vl_base_pis_total;
    }

    public double getVl_pis_total() {
        return vl_pis_total;
    }

    public void setVl_pis_total(double vl_pis_total) {
        this.vl_pis_total = vl_pis_total;
    }

    public double getVl_base_cofins_total() {
        return vl_base_cofins_total;
    }

    public void setVl_base_cofins_total(double vl_base_cofins_total) {
        this.vl_base_cofins_total = vl_base_cofins_total;
    }

    public double getVl_cofins_total() {
        return vl_cofins_total;
    }

    public void setVl_cofins_total(double vl_cofins_total) {
        this.vl_cofins_total = vl_cofins_total;
    }

    public double getVl_base_ipi_total() {
        return vl_base_ipi_total;
    }

    public void setVl_base_ipi_total(double vl_base_ipi_total) {
        this.vl_base_ipi_total = vl_base_ipi_total;
    }

    public double getVl_ipi_total() {
        return vl_ipi_total;
    }

    public void setVl_ipi_total(double vl_ipi_total) {
        this.vl_ipi_total = vl_ipi_total;
    }

    public double getVl_base_servico_total() {
        return vl_base_servico_total;
    }

    public void setVl_base_servico_total(double vl_base_servico_total) {
        this.vl_base_servico_total = vl_base_servico_total;
    }

    public double getVl_servico_total() {
        return vl_servico_total;
    }

    public void setVl_servico_total(double vl_servico_total) {
        this.vl_servico_total = vl_servico_total;
    }

    public double getVl_base_issqn_total() {
        return vl_base_issqn_total;
    }

    public void setVl_base_issqn_total(double vl_base_issqn_total) {
        this.vl_base_issqn_total = vl_base_issqn_total;
    }

    public double getVl_issqn_total() {
        return vl_issqn_total;
    }

    public void setVl_issqn_total(double vl_issqn_total) {
        this.vl_issqn_total = vl_issqn_total;
    }

    public int getCd_transportadora() {
        return cd_transportadora;
    }

    public void setCd_transportadora(int cd_transportadora) {
        this.cd_transportadora = cd_transportadora;
    }

    public String getNr_placa_veiculo() {
        return nr_placa_veiculo;
    }

    public void setNr_placa_veiculo(String nr_placa_veiculo) {
        this.nr_placa_veiculo = nr_placa_veiculo;
    }

    public int getQtd_volume() {
        return qtd_volume;
    }

    public void setQtd_volume(int qtd_volume) {
        this.qtd_volume = qtd_volume;
    }

    public int getFg_emitente() {
        return fg_emitente;
    }

    public void setFg_emitente(int fg_emitente) {
        this.fg_emitente = fg_emitente;
    }

    public double getVl_tot_frete() {
        return vl_tot_frete;
    }

    public void setVl_tot_frete(double vl_tot_frete) {
        this.vl_tot_frete = vl_tot_frete;
    }

    public String getNm_especie() {
        return nm_especie;
    }

    public void setNm_especie(String nm_especie) {
        this.nm_especie = nm_especie;
    }

    public String getNr_nota_nfe() {
        return nr_nota_nfe;
    }

    public void setNr_nota_nfe(String nr_nota_nfe) {
        this.nr_nota_nfe = nr_nota_nfe;
    }

    public String getNr_chave_nfe() {
        return nr_chave_nfe;
    }

    public void setNr_chave_nfe(String nr_chave_nfe) {
        this.nr_chave_nfe = nr_chave_nfe;
    }

    public String getNr_prot_autorizacao() {
        return nr_prot_autorizacao;
    }

    public void setNr_prot_autorizacao(String nr_prot_autorizacao) {
        this.nr_prot_autorizacao = nr_prot_autorizacao;
    }

    public String getDs_inf_adicionais() {
        return ds_inf_adicionais;
    }

    public void setDs_inf_adicionais(String ds_inf_adicionais) {
        this.ds_inf_adicionais = ds_inf_adicionais;
    }

    public double getVl_tot_seguro() {
        return vl_tot_seguro;
    }

    public void setVl_tot_seguro(double vl_tot_seguro) {
        this.vl_tot_seguro = vl_tot_seguro;
    }

    public double getVl_tot_pedido_nota() {
        return vl_tot_pedido_nota;
    }

    public void setVl_tot_pedido_nota(double vl_tot_pedido_nota) {
        this.vl_tot_pedido_nota = vl_tot_pedido_nota;
    }

    public String getDs_marca() {
        return ds_marca;
    }

    public void setDs_marca(String ds_marca) {
        this.ds_marca = ds_marca;
    }

    public int getNr_numeracao() {
        return nr_numeracao;
    }

    public void setNr_numeracao(int nr_numeracao) {
        this.nr_numeracao = nr_numeracao;
    }

    public double getVl_peso_liquido() {
        return vl_peso_liquido;
    }

    public void setVl_peso_liquido(double vl_peso_liquido) {
        this.vl_peso_liquido = vl_peso_liquido;
    }

    public double getVl_peso_bruto() {
        return vl_peso_bruto;
    }

    public void setVl_peso_bruto(double vl_peso_bruto) {
        this.vl_peso_bruto = vl_peso_bruto;
    }

    public double getVl_tot_impostos() {
        return vl_tot_impostos;
    }

    public void setVl_tot_impostos(double vl_tot_impostos) {
        this.vl_tot_impostos = vl_tot_impostos;
    }

}
