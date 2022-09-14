
package Model;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Gelvazio Camargo
 */
public class ItemTeste {
    
    private int cd_filial;
    private int cd_movimento;
    private int cd_seq_ite_pro;
    private long cd_refer_pro;
    private double qt_ite_pro;
    private double vl_cus_ite_pro;
    private double vl_ven_ite_pro;
    private double vl_real_ite_pro;
    private double tx_icms;
    private double vl_base_icm;
    private double vl_icm;
    private double tx_icms_substituicao;
    private double vl_mva;
    private double vl_base_icm_substituicao;
    private double vl_icm_substituicao;
    private double tx_iss;
    private double vl_base_iss;
    private double vl_iss;
    private int cst_ipi;
    private double vl_base_ipi;
    private double tx_ipi;
    private double vl_ipi;
    private int cst_pis;
    private double vl_base_pis;
    private double tx_pis;
    private double vl_pis;
    private int cst_cofins;
    private double vl_base_cofins;
    private double tx_cofins;
    private double vl_cofins;
    private Date dt_emi_doc;
    private int cd_tipo_doc;
    private String ab_unidade;
    private int cd_vendedor;
    private int cd_usuario;
    private Date dt_alt;
    private Time hr_alt;
    private Date dt_cad;
    private Time hr_cad;
    private int cfop;
    private double vl_desconto;
    private double vl_acrescimo;
    private int cd_grupo_fiscal;
    private int cd_cst;
    private double vl_peso_liquido;
    private double vl_peso_bruto;
    private double vl_volume;
    private int fg_situacao;
    private double vl_rateado;
    private double vl_frete;
    private double vl_impostos;

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

    public int getCd_seq_ite_pro() {
        return cd_seq_ite_pro;
    }

    public void setCd_seq_ite_pro(int cd_seq_ite_pro) {
        this.cd_seq_ite_pro = cd_seq_ite_pro;
    }

    public long getCd_refer_pro() {
        return cd_refer_pro;
    }

    public void setCd_refer_pro(long cd_refer_pro) {
        this.cd_refer_pro = cd_refer_pro;
    }

    public double getQt_ite_pro() {
        return qt_ite_pro;
    }

    public void setQt_ite_pro(double qt_ite_pro) {
        this.qt_ite_pro = qt_ite_pro;
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

    public double getVl_real_ite_pro() {
        return vl_real_ite_pro;
    }

    public void setVl_real_ite_pro(double vl_real_ite_pro) {
        this.vl_real_ite_pro = vl_real_ite_pro;
    }

    public double getTx_icms() {
        return tx_icms;
    }

    public void setTx_icms(double tx_icms) {
        this.tx_icms = tx_icms;
    }

    public double getVl_base_icm() {
        return vl_base_icm;
    }

    public void setVl_base_icm(double vl_base_icm) {
        this.vl_base_icm = vl_base_icm;
    }

    public double getVl_icm() {
        return vl_icm;
    }

    public void setVl_icm(double vl_icm) {
        this.vl_icm = vl_icm;
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

    public double getVl_base_icm_substituicao() {
        return vl_base_icm_substituicao;
    }

    public void setVl_base_icm_substituicao(double vl_base_icm_substituicao) {
        this.vl_base_icm_substituicao = vl_base_icm_substituicao;
    }

    public double getVl_icm_substituicao() {
        return vl_icm_substituicao;
    }

    public void setVl_icm_substituicao(double vl_icm_substituicao) {
        this.vl_icm_substituicao = vl_icm_substituicao;
    }

    public double getTx_iss() {
        return tx_iss;
    }

    public void setTx_iss(double tx_iss) {
        this.tx_iss = tx_iss;
    }

    public double getVl_base_iss() {
        return vl_base_iss;
    }

    public void setVl_base_iss(double vl_base_iss) {
        this.vl_base_iss = vl_base_iss;
    }

    public double getVl_iss() {
        return vl_iss;
    }

    public void setVl_iss(double vl_iss) {
        this.vl_iss = vl_iss;
    }

    public int getCst_ipi() {
        return cst_ipi;
    }

    public void setCst_ipi(int cst_ipi) {
        this.cst_ipi = cst_ipi;
    }

    public double getVl_base_ipi() {
        return vl_base_ipi;
    }

    public void setVl_base_ipi(double vl_base_ipi) {
        this.vl_base_ipi = vl_base_ipi;
    }

    public double getTx_ipi() {
        return tx_ipi;
    }

    public void setTx_ipi(double tx_ipi) {
        this.tx_ipi = tx_ipi;
    }

    public double getVl_ipi() {
        return vl_ipi;
    }

    public void setVl_ipi(double vl_ipi) {
        this.vl_ipi = vl_ipi;
    }

    public int getCst_pis() {
        return cst_pis;
    }

    public void setCst_pis(int cst_pis) {
        this.cst_pis = cst_pis;
    }

    public double getVl_base_pis() {
        return vl_base_pis;
    }

    public void setVl_base_pis(double vl_base_pis) {
        this.vl_base_pis = vl_base_pis;
    }

    public double getTx_pis() {
        return tx_pis;
    }

    public void setTx_pis(double tx_pis) {
        this.tx_pis = tx_pis;
    }

    public double getVl_pis() {
        return vl_pis;
    }

    public void setVl_pis(double vl_pis) {
        this.vl_pis = vl_pis;
    }

    public int getCst_cofins() {
        return cst_cofins;
    }

    public void setCst_cofins(int cst_cofins) {
        this.cst_cofins = cst_cofins;
    }

    public double getVl_base_cofins() {
        return vl_base_cofins;
    }

    public void setVl_base_cofins(double vl_base_cofins) {
        this.vl_base_cofins = vl_base_cofins;
    }

    public double getTx_cofins() {
        return tx_cofins;
    }

    public void setTx_cofins(double tx_cofins) {
        this.tx_cofins = tx_cofins;
    }

    public double getVl_cofins() {
        return vl_cofins;
    }

    public void setVl_cofins(double vl_cofins) {
        this.vl_cofins = vl_cofins;
    }

    public Date getDt_emi_doc() {
        return dt_emi_doc;
    }

    public void setDt_emi_doc(Date dt_emi_doc) {
        this.dt_emi_doc = dt_emi_doc;
    }

    public int getCd_tipo_doc() {
        return cd_tipo_doc;
    }

    public void setCd_tipo_doc(int cd_tipo_doc) {
        this.cd_tipo_doc = cd_tipo_doc;
    }

    public String getAb_unidade() {
        return ab_unidade;
    }

    public void setAb_unidade(String ab_unidade) {
        this.ab_unidade = ab_unidade;
    }

    public int getCd_vendedor() {
        return cd_vendedor;
    }

    public void setCd_vendedor(int cd_vendedor) {
        this.cd_vendedor = cd_vendedor;
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

    public double getVl_desconto() {
        return vl_desconto;
    }

    public void setVl_desconto(double vl_desconto) {
        this.vl_desconto = vl_desconto;
    }

    public double getVl_acrescimo() {
        return vl_acrescimo;
    }

    public void setVl_acrescimo(double vl_acrescimo) {
        this.vl_acrescimo = vl_acrescimo;
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

    public double getVl_volume() {
        return vl_volume;
    }

    public void setVl_volume(double vl_volume) {
        this.vl_volume = vl_volume;
    }

    public int getFg_situacao() {
        return fg_situacao;
    }

    public void setFg_situacao(int fg_situacao) {
        this.fg_situacao = fg_situacao;
    }

    public double getVl_rateado() {
        return vl_rateado;
    }

    public void setVl_rateado(double vl_rateado) {
        this.vl_rateado = vl_rateado;
    }

    public double getVl_frete() {
        return vl_frete;
    }

    public void setVl_frete(double vl_frete) {
        this.vl_frete = vl_frete;
    }

    public double getVl_impostos() {
        return vl_impostos;
    }

    public void setVl_impostos(double vl_impostos) {
        this.vl_impostos = vl_impostos;
    }
    
}