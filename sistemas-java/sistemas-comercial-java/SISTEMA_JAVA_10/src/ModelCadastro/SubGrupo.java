package ModelCadastro;

/**
 *
 * @author Gelvazio Camargo tabela banco dados CREATE TABLE SUB_GRUPO ( CD_GRUPO
 * INTEGER NOT NULL, CD_SUB_GRUPO INTEGER NOT NULL, DS_SUB_GRUPO VARCHAR(35) NOT
 * NULL, CD_USUARIO SMALLINT NOT NULL, DT_ALT DATE NOT NULL, HR_ALT TIME NOT
 * NULL, DT_CAD DATE NOT NULL, HR_CAD TIME NOT NULL );
 */
public class SubGrupo {

    int cd_grupo;
    int cd_sub_grupo;
    int cd_usuario;
    String ds_sub_grupo;

    public SubGrupo(int cd_grupo, int cd_sub_grupo, int cd_usuario, String ds_sub_grupo) {
        this.cd_grupo = cd_grupo;
        this.cd_sub_grupo = cd_sub_grupo;
        this.cd_usuario = cd_usuario;
        this.ds_sub_grupo = ds_sub_grupo;
    }

    public int getCd_grupo() {
        return cd_grupo;
    }

    public void setCd_grupo(int cd_grupo) {
        this.cd_grupo = cd_grupo;
    }

    public int getCd_sub_grupo() {
        return cd_sub_grupo;
    }

    public void setCd_sub_grupo(int cd_sub_grupo) {
        this.cd_sub_grupo = cd_sub_grupo;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

    public String getDs_sub_grupo() {
        return ds_sub_grupo;
    }

    public void setDs_sub_grupo(String ds_sub_grupo) {
        this.ds_sub_grupo = ds_sub_grupo;
    }

}
