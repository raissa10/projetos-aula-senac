package principal;

import java.util.Vector;

public class VendaProduto {

    private int quantidade;
    private double preco;
    protected int produto;
    private Vector<String> listaProd;

    ManipTXT manip = new ManipTXT("ProdutosDaVenda");

    Vector<String> v = manip.ler();

    public VendaProduto() {

    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade > 0) {
            this.quantidade = quantidade;
        }
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco >= 0) {
            this.preco = preco;
        }
    }

    public int getProduto() {
        return produto;
    }

    public void setProduto(int produto) {
        this.produto = produto;
    }

    public Vector<String> getListaProd() {
        return listaProd;
    }

    public void setListaProd(Vector<String> linhaProd) {
        this.listaProd = linhaProd;
    }

    public boolean gravarProdutoDaVenda(int codVenda) {
        try {
            StringBuilder texto = new StringBuilder();

            texto.append(codVenda);
            texto.append(";");
            texto.append(produto);
            texto.append(";");
            texto.append(quantidade);
            texto.append(";");
            texto.append(preco);

            v.add(texto.toString());

            manip.abrirArquivo();

            for (int x = 0; x < v.size(); x++) {
                manip.escrever(v.elementAt(x));
            }

            manip.fecharArquivo();

            return true;
        } catch (Exception e) {
            System.out.println("ERRO (gravar Produtos da Venda): " + e.toString());
        }

        return false;
    }

    public String listarVenda(int codigo) {

        try {

            StringBuilder texto = new StringBuilder();

            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {

                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[0].equals(String.valueOf(codigo))) {

                    texto.append("Produto: ");
                    texto.append(linhaResul[1]);
                    texto.append("Quantidade: ");
                    texto.append(linhaResul[2]);
                    texto.append("Preço: ");
                    texto.append(linhaResul[3]);
                    texto.append("\n");

                }

            }

            return texto.toString();

        } catch (Exception e) {
            System.out.println("ERRO (Listar Venda Produto): " + e.toString());
        }

        return "";
    }

    public Vector<String> setaProdutosVenda() {
        try {
            v = manip.ler();
            return v;
        } catch (Exception e) {
            System.out.println("ERRO (setaProdutosVenda): " + e.toString());
        }
        return null;

    }

    public boolean procurarProdutoNaVenda(int codigo) {
        try {

            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[1].equals(String.valueOf(codigo))) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO (procurarProdutoNaVenda): " + e.toString());
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VendaProduto [quantidade=");
        builder.append(quantidade);
        builder.append(", preco=");
        builder.append(preco);
        builder.append(", produto=");
        builder.append(produto);
        builder.append("]");
        return builder.toString();
    }
}
