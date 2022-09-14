package Model;

/**
 *
 * @author Gelvazio Camargo Tabela banco dados CREATE TABLE GRUPO ( CD_GRUPO
 * INTEGER NOT NULL, DS_GRUPO VARCHAR(50) NOT NULL, CD_USUARIO SMALLINT NOT
 * NULL, DT_ALT DATE NOT NULL, HR_ALT TIME NOT NULL, DT_CAD DATE NOT NULL,
 * HR_CAD TIME NOT NULL ); );
 *
 */
public class Grupo {

    int codigo;
    String descricao;
    int codigoUsuario;

    public Grupo(int codigo, String descricao, int codigoUsuario) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.codigoUsuario = codigoUsuario;
    }

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
