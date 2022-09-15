package model;

import java.util.Vector;

public class Item {

    private int codigo;
    private String descricao;

    ManipTXT manip = new ManipTXT("Produtos");

    Vector<String> v = manip.ler();

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo > 0) {
            this.codigo = codigo;
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao.length() > 0) {
            this.descricao = descricao;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(codigo);
        builder.append(";");
        builder.append(descricao);
        return builder.toString();
    }
}
