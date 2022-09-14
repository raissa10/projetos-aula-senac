package Model;

import Estrutura.ModelPadrao;

/**
 *
 * @author Gelvazio
 * @since 01/01/2014
*/
public class ModelCondicaoPagamento extends ModelPadrao {

    private int codigo;
    private String descricao;
    private int codigoUsuario;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

}
