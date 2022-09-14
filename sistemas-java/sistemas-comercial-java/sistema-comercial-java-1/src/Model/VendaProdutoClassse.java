package Model;

/**
 *
 * @author Gelvazio /
 */
public class VendaProdutoClassse {

    private int quantidade;
    private double preco;
    protected int produto;
    private int cd_filial;
    private int cd_movimento;
    private int cd_seq_ite_pro;
    int cd_usuario;
    private double vl_custo;

    public VendaProdutoClassse(int quantidade, double preco, int produto, int cd_filial, int cd_movimento, int cd_seq_ite_pro, int cd_usuario, double vl_custo) {
        this.quantidade = quantidade;
        this.preco = preco;
        this.produto = produto;
        this.cd_filial = cd_filial;
        this.cd_movimento = cd_movimento;
        this.cd_seq_ite_pro = cd_seq_ite_pro;
        this.cd_usuario = cd_usuario;
        this.vl_custo = vl_custo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getProduto() {
        return produto;
    }

    public void setProduto(int produto) {
        this.produto = produto;
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

    public int getCd_seq_ite_pro() {
        return cd_seq_ite_pro;
    }

    public void setCd_seq_ite_pro(int cd_seq_ite_pro) {
        this.cd_seq_ite_pro = cd_seq_ite_pro;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

    public double getVl_custo() {
        return vl_custo;
    }

    public void setVl_custo(double vl_custo) {
        this.vl_custo = vl_custo;
    }

}
