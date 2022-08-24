package principal;

import java.util.Vector;
import javax.swing.JOptionPane;

public class Produto extends Item {

    private double preco;
    private boolean alteracao;
    private Vector<String> listaProd;

    public Produto() {

    }

    public Produto(double preco) {
        super();
        setPreco(preco);
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco >= 0) {
            this.preco = preco;
        }
    }

    public void setAlteracao(boolean alteracao) {
        this.alteracao = alteracao;
    }

    public boolean getAlteracao() {

        if (alteracao) {
            return true;
        }

        return false;
    }

    public void setListaProd(Vector<String> listaProd) {
        this.listaProd = listaProd;
    }

    public Vector<String> getListaProd() {
        return listaProd;
    }

    public boolean gravarProduto() {
        try {
            StringBuilder texto = new StringBuilder();

            texto.append(super.toString());
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
            System.out.println("ERRO (gravarProduto): " + e.toString());
        }

        return false;
    }

    public boolean GravarAlterar(int codigo, String descricao, double preco) {

        try {
            setCodigo(codigo);
            setDescricao(descricao);
            setPreco(preco);
            if (alteracao) {
                if (alterarProduto()) {
                    return true;
                }
            } else if (gravarProduto()) {
                return true;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: (GravarAlterar) - " + e.toString());
        }

        return false;
    }

    public boolean alterarProduto() {
        try {

            StringBuilder texto = new StringBuilder();
            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[0].equals(String.valueOf(getCodigo()))) {
                    texto.append(super.toString());
                    texto.append(";");
                    texto.append(preco);

                    manip.abrirArquivo();

                    v.set(x, texto.toString());

                    for (int f = 0; f < v.size(); f++) {
                        manip.escrever(v.elementAt(f));
                    }

                    manip.fecharArquivo();
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("ERRO (AlterarProduto): " + e.toString());
        }

        return false;
    }

    public boolean deletarProduto() {

        try {
            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[0].equals(String.valueOf(getCodigo()))) {
                    manip.abrirArquivo();

                    v.remove(x);

                    for (int f = 0; f < v.size(); f++) {
                        manip.escrever(v.elementAt(f));
                    }

                    manip.fecharArquivo();

                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO (Deletar Produto): " + e.toString());
        }

        return false;
    }

    public boolean procurarProduto() {
        try {
            v = manip.ler();
            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");
                if (linhaResul[0].equals(String.valueOf(getCodigo()))) {
                    setDescricao(linhaResul[1]);
                    setPreco(Double.parseDouble(linhaResul[2]));
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO (ProcurarProduto): " + e.toString());
        }
        return false;
    }

    public int retornaUltimo() {
        int codigo = 0;

        try {
            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (Integer.parseInt(linhaResul[0]) > codigo) {
                    codigo = Integer.parseInt(linhaResul[0]);
                }
            }
        } catch (Exception e) {
            codigo = 0;
        }

        return codigo + 1;
    }

    public Vector<String> retornaProdutos() {
        try {
            v = manip.ler();

            return v;
        } catch (Exception e) {
            System.out.println("ERRO (Lista Produtos): " + e.toString());
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Produto [preco=");
        builder.append(preco);
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }
}
