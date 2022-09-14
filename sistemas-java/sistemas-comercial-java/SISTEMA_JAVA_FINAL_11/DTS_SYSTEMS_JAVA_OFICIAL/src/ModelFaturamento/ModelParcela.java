/*
 * Copyright (C) 2015 Gelvazio Camargo
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
package ModelFaturamento;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Gelvazio Camargo
 */

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Gelvazio
 */
/*
 CREATE TABLE PARCELAS (
 CD_FILIAL              SMALLINT NOT NULL,
 CD_MOVIMENTO           INTEGER NOT NULL,
 CD_DOCUMENTO           INTEGER NOT NULL,
 NR_PARCELA             INTEGER NOT NULL,
 FG_SITUACAO            INTEGER NOT NULL,
 VL_PARCELA             NUMERIC(16,4),
 VL_JUROS               NUMERIC(16,4),
 CD_CONDICAO_PAGAMENTO  INTEGER NOT NULL,
 SEQUENCIA_PARCELA      SMALLINT NOT NULL,
 VL_ACRESCIMO           NUMERIC(16,4),
 VL_DESCONTO            NUMERIC(16,4),
 DT_VENCTO_PAR          DATE NOT NULL,
 CD_TIPO_COBRANCA       NUMERIC(3,0),
 CD_USUARIO             SMALLINT NOT NULL,
 DT_ALT                 DATE NOT NULL,
 HR_ALT                 TIME NOT NULL,
 DT_CAD                 DATE NOT NULL,
 HR_CAD                 TIME NOT NULL,
 DT_PAG_PAR             DATE,
 VL_PAGO                NUMERIC(16,4),
 cd_pessoa bigint not null
 );
 */
public class ModelParcela {
    private int cd_filial;
    private long cd_movimento;
    private long cd_documento;
    private int nr_parcela;
    private int fg_situacao;
    private double vl_parcela;
    private double vl_juros;
    private int cd_condicao_pagamento;
    private int sequencia_parcela;
    private double vl_acrescimo;
    private double vl_desconto;
    private Date dt_vencto_par;
    private int cd_tipo_cobranca;
    private int cd_usuario;
    private Date dt_alt;
    private Time hr_alt;
    private Date dt_cad;
    private Time hr_cad;
    private Date dt_pag_par;
    private double vl_pago;
    private int cd_pessoa;

    public ModelParcela(int cd_filial, long cd_movimento, long cd_documento, int nr_parcela, int fg_situacao, double vl_parcela, double vl_juros, int cd_condicao_pagamento, int sequencia_parcela, double vl_acrescimo, double vl_desconto, Date dt_vencto_par, int cd_tipo_cobranca, int cd_usuario, Date dt_alt, Time hr_alt, Date dt_cad, Time hr_cad, Date dt_pag_par, double vl_pago, int cd_pessoa) {
        this.cd_filial = cd_filial;
        this.cd_movimento = cd_movimento;
        this.cd_documento = cd_documento;
        this.nr_parcela = nr_parcela;
        this.fg_situacao = fg_situacao;
        this.vl_parcela = vl_parcela;
        this.vl_juros = vl_juros;
        this.cd_condicao_pagamento = cd_condicao_pagamento;
        this.sequencia_parcela = sequencia_parcela;
        this.vl_acrescimo = vl_acrescimo;
        this.vl_desconto = vl_desconto;
        this.dt_vencto_par = dt_vencto_par;
        this.cd_tipo_cobranca = cd_tipo_cobranca;
        this.cd_usuario = cd_usuario;
        this.dt_alt = dt_alt;
        this.hr_alt = hr_alt;
        this.dt_cad = dt_cad;
        this.hr_cad = hr_cad;
        this.dt_pag_par = dt_pag_par;
        this.vl_pago = vl_pago;
        this.cd_pessoa = cd_pessoa;
    }

    public int getCd_filial() {
        return cd_filial;
    }

    public void setCd_filial(int cd_filial) {
        this.cd_filial = cd_filial;
    }

    public long getCd_movimento() {
        return cd_movimento;
    }

    public void setCd_movimento(long cd_movimento) {
        this.cd_movimento = cd_movimento;
    }

    public long getCd_documento() {
        return cd_documento;
    }

    public void setCd_documento(long cd_documento) {
        this.cd_documento = cd_documento;
    }

    public int getNr_parcela() {
        return nr_parcela;
    }

    public void setNr_parcela(int nr_parcela) {
        this.nr_parcela = nr_parcela;
    }

    public int getFg_situacao() {
        return fg_situacao;
    }

    public void setFg_situacao(int fg_situacao) {
        this.fg_situacao = fg_situacao;
    }

    public double getVl_parcela() {
        return vl_parcela;
    }

    public void setVl_parcela(double vl_parcela) {
        this.vl_parcela = vl_parcela;
    }

    public double getVl_juros() {
        return vl_juros;
    }

    public void setVl_juros(double vl_juros) {
        this.vl_juros = vl_juros;
    }

    public int getCd_condicao_pagamento() {
        return cd_condicao_pagamento;
    }

    public void setCd_condicao_pagamento(int cd_condicao_pagamento) {
        this.cd_condicao_pagamento = cd_condicao_pagamento;
    }

    public int getSequencia_parcela() {
        return sequencia_parcela;
    }

    public void setSequencia_parcela(int sequencia_parcela) {
        this.sequencia_parcela = sequencia_parcela;
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

    public Date getDt_vencto_par() {
        return dt_vencto_par;
    }

    public void setDt_vencto_par(Date dt_vencto_par) {
        this.dt_vencto_par = dt_vencto_par;
    }

    public int getCd_tipo_cobranca() {
        return cd_tipo_cobranca;
    }

    public void setCd_tipo_cobranca(int cd_tipo_cobranca) {
        this.cd_tipo_cobranca = cd_tipo_cobranca;
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

    public Date getDt_pag_par() {
        return dt_pag_par;
    }

    public void setDt_pag_par(Date dt_pag_par) {
        this.dt_pag_par = dt_pag_par;
    }

    public double getVl_pago() {
        return vl_pago;
    }

    public void setVl_pago(double vl_pago) {
        this.vl_pago = vl_pago;
    }

    public int getCd_pessoa() {
        return cd_pessoa;
    }

    public void setCd_pessoa(int cd_pessoa) {
        this.cd_pessoa = cd_pessoa;
    }
    
}
