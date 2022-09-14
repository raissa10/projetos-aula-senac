package ModelCadastro;

/**
 *
 * @author Gelvazio Camargo Tabela banco dados CREATE TABLE GRUPO ( CD_GRUPO
 * INTEGER NOT NULL, DS_GRUPO VARCHAR(50) NOT NULL, CD_USUARIO SMALLINT NOT
 * NULL, DT_ALT DATE NOT NULL, HR_ALT TIME NOT NULL, DT_CAD DATE NOT NULL,
 * HR_CAD TIME NOT NULL ); );
 *
 */
public class Grupo {

    int cd_grupo;
    String ds_grupo;
    int cd_usuario;

    public Grupo(int cd_grupo, String ds_grupo, int cd_usuario) {
        this.cd_grupo = cd_grupo;
        this.ds_grupo = ds_grupo;
        this.cd_usuario = cd_usuario;
    }

    public int getCd_grupo() {
        return cd_grupo;
    }

    public void setCd_grupo(int cd_grupo) {
        this.cd_grupo = cd_grupo;
    }

    public String getDs_grupo() {
        return ds_grupo;
    }

    public void setDs_grupo(String ds_grupo) {
        this.ds_grupo = ds_grupo;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

}
