package ModelFaturamento;

/**
 *
 * @author Gelvazio Camargo
 */
public class ModelVendaClasse {

    private int codigoFilial;
    private int cd_movimento;
    private int cd_usuario;
    private int cd_vende;
    private int cd_pagto;
    private int cd_pessoa;
    private double vl_tot_cus_doc;
    private double vl_tot_pro_doc;
    private int fg_situacao;
    private int cd_tipo_nota;

    public ModelVendaClasse(int cd_filial, int cd_movimento, int cd_usuario, int cd_vende, int cd_pagto, int cd_pessoa, double vl_tot_cus_doc, double vl_tot_pro_doc, int fg_situacao, int cd_tipo_nota) {
        this.codigoFilial = cd_filial;
        this.cd_movimento = cd_movimento;
        this.cd_usuario = cd_usuario;
        this.cd_vende = cd_vende;
        this.cd_pagto = cd_pagto;
        this.cd_pessoa = cd_pessoa;
        this.vl_tot_cus_doc = vl_tot_cus_doc;
        this.vl_tot_pro_doc = vl_tot_pro_doc;
        this.fg_situacao = fg_situacao;
        this.cd_tipo_nota = cd_tipo_nota;
    }

    public int getCodigoFilial() {
        return codigoFilial;
    }

    public void setCodigoFilial(int codigoFilial) {
        this.codigoFilial = codigoFilial;
    }

    public int getCd_movimento() {
        return cd_movimento;
    }

    public void setCd_movimento(int cd_movimento) {
        this.cd_movimento = cd_movimento;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
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

    public int getFg_situacao() {
        return fg_situacao;
    }

    public void setFg_situacao(int fg_situacao) {
        this.fg_situacao = fg_situacao;
    }

    public int getCd_tipo_nota() {
        return cd_tipo_nota;
    }

    public void setCd_tipo_nota(int cd_tipo_nota) {
        this.cd_tipo_nota = cd_tipo_nota;
    }

}
