package sistema.sistema_java_ok.src.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Venda {

    private int codigo;
    private String data;
    private int cliente;
    private int vendedor;
    private List<VendaProduto> produtosDaVenda;
    private Vector<String> listaVend;
    private int condicaoPagamento;

    ManipTXT manip = new ManipTXT("Vendas");

    Vector<String> v = manip.ler();

    public Venda() {
        produtosDaVenda = new ArrayList<VendaProduto>();
    }

    public Venda(int codigo) {
        setCodigo(codigo);
        produtosDaVenda = new ArrayList<VendaProduto>();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo > 0) {
            this.codigo = codigo;
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        if (data != null) {
            this.data = data;
        }
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }

    public void setListaVend(Vector<String> listaVend) {
        this.listaVend = listaVend;
    }

    public Vector<String> getListaVend() {
        return listaVend;
    }

    public boolean gravarVenda() {
        try {
            StringBuilder texto = new StringBuilder();

            texto.append(codigo);
            texto.append(";");
            texto.append(cliente);
            texto.append(";");
            texto.append(vendedor);
            texto.append(";");
            texto.append(data);
            texto.append(";");
            texto.append(condicaoPagamento);

            v.add(texto.toString());

            manip.abrirArquivo();

            for (int x = 0; x < v.size(); x++) {
                manip.escrever(v.elementAt(x));
            }

            manip.fecharArquivo();

            return true;
        } catch (Exception e) {
            System.out.println("ERRO (gravar Vendas): " + e.toString());
        }

        return false;
    }

    public String listarVenda() {

        try {

            StringBuilder texto = new StringBuilder();

            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {

                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[0].equals(String.valueOf(codigo))) {

                    texto.append("Código da Venda: ");
                    texto.append(linhaResul[0]);

                    texto.append("Data da Venda: ");
                    texto.append(linhaResul[3]);

                    texto.append("Cliente da Venda: ");
                    texto.append(linhaResul[1]);

                    texto.append("Vendedor da Venda: ");
                    texto.append(linhaResul[2]);

                    texto.append("\n");
                    texto.append("Produtos:");
                    texto.append("\n");
                }
            }

            return texto.toString();

        } catch (Exception e) {
            System.out.println("ERRO (Listar Venda): " + e.toString());
        }

        return "";
    }

    public boolean procurarVenda(int codVenda) {
        try {

            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[0].equals(String.valueOf(getCodigo()))) {
                    setCliente(Integer.parseInt(linhaResul[1]));
                    setVendedor(Integer.parseInt(linhaResul[2]));
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("ERRO (ProcurarVenda): " + e.toString());
        }

        return false;
    }

    public boolean procurarClienteNaVenda(int codigo) {
        try {

            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[1].equals(String.valueOf(codigo))) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO (procurarClienteNaVenda): " + e.toString());
        }

        return false;
    }

    public boolean procurarVendedorNaVenda(int codigo) {
        try {

            v = manip.ler();

            for (int x = 0; x < v.size(); x++) {
                String[] linhaResul = v.get(x).split(";");

                if (linhaResul[2].equals(String.valueOf(codigo))) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO (procurarVendedorNaVenda): " + e.toString());
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

    public Vector<String> retornaVendas() {
        try {
            v = manip.ler();

            return v;
        } catch (Exception e) {
            System.out.println("ERRO (retornaVendas): " + e.toString());
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Venda [codigo=");
        builder.append(codigo);
        builder.append(", data=");
        builder.append(data);
        builder.append(", cliente=");
        builder.append(cliente);
        builder.append(", vendedor=");
        builder.append(vendedor);
        builder.append(", condicaoPagamento=");
        builder.append(condicaoPagamento);

        return builder.toString();
    }

    public int getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public void setCondicaoPagamento(int condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public ArrayList<String> getListaCondicaoPagamento(){

        ArrayList<String> listaCondicao = new ArrayList<String>();
        listaCondicao.add("1;A VISTA");
        listaCondicao.add("2;A PRAZO");
        listaCondicao.add("3;CARTAO DEBITO");
        listaCondicao.add("4;CARTAO CREDITO");

        return listaCondicao;
    }

}
