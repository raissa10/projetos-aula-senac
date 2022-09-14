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
package ModelCadastro;

/**
 *
 * @author Gelvazio Camargo
 */
public class Tributacao {

    private double vl_cus_ite_pro;
    private double vl_ven_ite_pro;
    
    private double tx_icms_interno_destino;
    private double tx_icms_interestadual_destino;
    private String cd_estado_destino;
    private double tx_icms_interno_origem;
    private double tx_icms_interestadual_origem;
    private String cd_estado_origem;
    
    private double tx_icms;
    private double tx_icms_substituicao;
    private double vl_mva;
    private double tx_iss;
    private int cst_ipi;
    private double tx_ipi;
    private int cst_pis;
    private double tx_pis;
    private int cst_cofins;
    private double tx_cofins;
    private String ab_unidade;
    private int cfop_estadual;
    private int cfop_inter_estadual;
    private int cd_grupo_fiscal;
    private int cd_cst;

    public Tributacao(double vl_cus_ite_pro, double vl_ven_ite_pro, double tx_icms_interno_destino, double tx_icms_interestadual_destino, String cd_estado_destino, double tx_icms_interno_origem, double tx_icms_interestadual_origem, String cd_estado_origem, double tx_icms, double tx_icms_substituicao, double vl_mva, double tx_iss, int cst_ipi, double tx_ipi, int cst_pis, double tx_pis, int cst_cofins, double tx_cofins, String ab_unidade, int cfop_estadual, int cfop_inter_estadual, int cd_grupo_fiscal, int cd_cst) {
        this.vl_cus_ite_pro = vl_cus_ite_pro;
        this.vl_ven_ite_pro = vl_ven_ite_pro;
        this.tx_icms_interno_destino = tx_icms_interno_destino;
        this.tx_icms_interestadual_destino = tx_icms_interestadual_destino;
        this.cd_estado_destino = cd_estado_destino;
        this.tx_icms_interno_origem = tx_icms_interno_origem;
        this.tx_icms_interestadual_origem = tx_icms_interestadual_origem;
        this.cd_estado_origem = cd_estado_origem;
        this.tx_icms = tx_icms;
        this.tx_icms_substituicao = tx_icms_substituicao;
        this.vl_mva = vl_mva;
        this.tx_iss = tx_iss;
        this.cst_ipi = cst_ipi;
        this.tx_ipi = tx_ipi;
        this.cst_pis = cst_pis;
        this.tx_pis = tx_pis;
        this.cst_cofins = cst_cofins;
        this.tx_cofins = tx_cofins;
        this.ab_unidade = ab_unidade;
        this.cfop_estadual = cfop_estadual;
        this.cfop_inter_estadual = cfop_inter_estadual;
        this.cd_grupo_fiscal = cd_grupo_fiscal;
        this.cd_cst = cd_cst;
    }

    public double getVl_cus_ite_pro() {
        return vl_cus_ite_pro;
    }

    public void setVl_cus_ite_pro(double vl_cus_ite_pro) {
        this.vl_cus_ite_pro = vl_cus_ite_pro;
    }

    public double getVl_ven_ite_pro() {
        return vl_ven_ite_pro;
    }

    public void setVl_ven_ite_pro(double vl_ven_ite_pro) {
        this.vl_ven_ite_pro = vl_ven_ite_pro;
    }

    public double getTx_icms_interno_destino() {
        return tx_icms_interno_destino;
    }

    public void setTx_icms_interno_destino(double tx_icms_interno_destino) {
        this.tx_icms_interno_destino = tx_icms_interno_destino;
    }

    public double getTx_icms_interestadual_destino() {
        return tx_icms_interestadual_destino;
    }

    public void setTx_icms_interestadual_destino(double tx_icms_interestadual_destino) {
        this.tx_icms_interestadual_destino = tx_icms_interestadual_destino;
    }

    public String getCd_estado_destino() {
        return cd_estado_destino;
    }

    public void setCd_estado_destino(String cd_estado_destino) {
        this.cd_estado_destino = cd_estado_destino;
    }

    public double getTx_icms_interno_origem() {
        return tx_icms_interno_origem;
    }

    public void setTx_icms_interno_origem(double tx_icms_interno_origem) {
        this.tx_icms_interno_origem = tx_icms_interno_origem;
    }

    public double getTx_icms_interestadual_origem() {
        return tx_icms_interestadual_origem;
    }

    public void setTx_icms_interestadual_origem(double tx_icms_interestadual_origem) {
        this.tx_icms_interestadual_origem = tx_icms_interestadual_origem;
    }

    public String getCd_estado_origem() {
        return cd_estado_origem;
    }

    public void setCd_estado_origem(String cd_estado_origem) {
        this.cd_estado_origem = cd_estado_origem;
    }

    public double getTx_icms() {
        return tx_icms;
    }

    public void setTx_icms(double tx_icms) {
        this.tx_icms = tx_icms;
    }

    public double getTx_icms_substituicao() {
        return tx_icms_substituicao;
    }

    public void setTx_icms_substituicao(double tx_icms_substituicao) {
        this.tx_icms_substituicao = tx_icms_substituicao;
    }

    public double getVl_mva() {
        return vl_mva;
    }

    public void setVl_mva(double vl_mva) {
        this.vl_mva = vl_mva;
    }

    public double getTx_iss() {
        return tx_iss;
    }

    public void setTx_iss(double tx_iss) {
        this.tx_iss = tx_iss;
    }

    public int getCst_ipi() {
        return cst_ipi;
    }

    public void setCst_ipi(int cst_ipi) {
        this.cst_ipi = cst_ipi;
    }

    public double getTx_ipi() {
        return tx_ipi;
    }

    public void setTx_ipi(double tx_ipi) {
        this.tx_ipi = tx_ipi;
    }

    public int getCst_pis() {
        return cst_pis;
    }

    public void setCst_pis(int cst_pis) {
        this.cst_pis = cst_pis;
    }

    public double getTx_pis() {
        return tx_pis;
    }

    public void setTx_pis(double tx_pis) {
        this.tx_pis = tx_pis;
    }

    public int getCst_cofins() {
        return cst_cofins;
    }

    public void setCst_cofins(int cst_cofins) {
        this.cst_cofins = cst_cofins;
    }

    public double getTx_cofins() {
        return tx_cofins;
    }

    public void setTx_cofins(double tx_cofins) {
        this.tx_cofins = tx_cofins;
    }

    public String getAb_unidade() {
        return ab_unidade;
    }

    public void setAb_unidade(String ab_unidade) {
        this.ab_unidade = ab_unidade;
    }

    public int getCfop_estadual() {
        return cfop_estadual;
    }

    public void setCfop_estadual(int cfop_estadual) {
        this.cfop_estadual = cfop_estadual;
    }

    public int getCfop_inter_estadual() {
        return cfop_inter_estadual;
    }

    public void setCfop_inter_estadual(int cfop_inter_estadual) {
        this.cfop_inter_estadual = cfop_inter_estadual;
    }

    public int getCd_grupo_fiscal() {
        return cd_grupo_fiscal;
    }

    public void setCd_grupo_fiscal(int cd_grupo_fiscal) {
        this.cd_grupo_fiscal = cd_grupo_fiscal;
    }

    public int getCd_cst() {
        return cd_cst;
    }

    public void setCd_cst(int cd_cst) {
        this.cd_cst = cd_cst;
    }
    
}
