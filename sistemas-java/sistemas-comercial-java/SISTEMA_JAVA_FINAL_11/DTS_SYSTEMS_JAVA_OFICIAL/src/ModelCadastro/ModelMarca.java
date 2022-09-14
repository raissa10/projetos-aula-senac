package ModelCadastro;

/**
 *
 * @author Gelvazio Camargo tabela banco dados CREATE TABLE MARCA ( CD_MARCA
 * INTEGER NOT NULL,
 *
 *
 * CD_USUARIO SMALLINT NOT NULL, DT_ALT DATE NOT NULL, HR_ALT TIME NOT NULL,
 * DT_CAD DATE NOT NULL, HR_CAD TIME NOT NULL,
 *
 * DS_MARCA VARCHAR(50), CD_FILIAL INTEGER NOT NULL );
 */
public class ModelMarca {

    int cd_marca;
    String ds_marca;
    int cd_usuario;

    public ModelMarca(int cd_marca, String ds_marca, int cd_usuario) {
        this.cd_marca = cd_marca;
        this.ds_marca = ds_marca;
    }

    public int getCd_marca() {
        return cd_marca;
    }

    public void setCd_marca(int cd_marca) {
        this.cd_marca = cd_marca;
    }

    public String getDs_marca() {
        return ds_marca;
    }

    public void setDs_marca(String ds_marca) {
        this.ds_marca = ds_marca;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

}
