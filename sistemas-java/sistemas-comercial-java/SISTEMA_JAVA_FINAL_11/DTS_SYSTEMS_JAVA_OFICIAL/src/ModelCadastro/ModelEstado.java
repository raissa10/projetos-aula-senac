package ModelCadastro;

/**
 *
 * @author Gelvazio
 */
public class ModelEstado {
    /*
     * TABELAS DO BANCO DE DADOS DA TABELA ESTADO
     CD_ESTADO   VARCHAR(2) NOT NULL,
     DS_ESTADO   VARCHAR(40) NOT NULL,
     CD_IBGE     INTEGER,
     CD_FILIAL   INTEGER NOT NULL,
     CD_USUARIO  SMALLINT NOT NULL,
    
     //abaixo n precisa ter na classe
     DT_ALT      DATE NOT NULL,
     HR_ALT      TIME NOT NULL,
     DT_CAD      DATE NOT NULL,
     HR_CAD      TIME NOT NULL
     */

    private String cd_estado, ds_estado;
    private int cd_ibge, cd_filial, cd_usuario;

    public ModelEstado(String cd_estado, String ds_estado, int cd_ibge, int cd_filial, int cd_usuario) {
        this.cd_estado = cd_estado;
        this.ds_estado = ds_estado;
        this.cd_ibge = cd_ibge;
        this.cd_filial = cd_filial;
        this.cd_usuario = cd_usuario;
    }

    public String getCd_estado() {
        return cd_estado;
    }

    public void setCd_estado(String cd_estado) {
        this.cd_estado = cd_estado;
    }

    public String getDs_estado() {
        return ds_estado;
    }

    public void setDs_estado(String ds_estado) {
        this.ds_estado = ds_estado;
    }

    public int getCd_ibge() {
        return cd_ibge;
    }

    public void setCd_ibge(int cd_ibge) {
        this.cd_ibge = cd_ibge;
    }

    public int getCd_filial() {
        return cd_filial;
    }

    public void setCd_filial(int cd_filial) {
        this.cd_filial = cd_filial;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

}
