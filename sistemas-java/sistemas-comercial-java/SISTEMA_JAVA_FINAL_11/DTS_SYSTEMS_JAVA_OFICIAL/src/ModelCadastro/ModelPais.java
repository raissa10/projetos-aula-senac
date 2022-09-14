/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelCadastro;

/**
 *
 * @author Gelvazio Camargo
 */
/*
 * Tabela ModelPais
 CREATE TABLE PAIS (
 CD_PAIS     INTEGER NOT NULL,
 NM_PAIS     VARCHAR(100),
 CD_USUARIO  SMALLINT NOT NULL,
 DT_ALT      DATE NOT NULL,
 HR_ALT      TIME NOT NULL,
 DT_CAD      DATE NOT NULL,
 HR_CAD      TIME NOT NULL,
 CD_FILIAL   INTEGER NOT NULL
 );

 */
public class ModelPais {

    private int cd_pais;
    private String nm_pais;
    private int cd_usuario;
    private int cd_filial;

    public ModelPais(int cd_pais, String nm_pais, int cd_usuario, int cd_filial) {
        this.cd_pais = cd_pais;
        this.nm_pais = nm_pais;
        this.cd_usuario = cd_usuario;
        this.cd_filial = cd_filial;
    }

    public int getCd_pais() {
        return cd_pais;
    }

    public void setCd_pais(int cd_pais) {
        this.cd_pais = cd_pais;
    }

    public String getNm_pais() {
        return nm_pais;
    }

    public void setNm_pais(String nm_pais) {
        this.nm_pais = nm_pais;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

    public int getCd_filial() {
        return cd_filial;
    }

    public void setCd_filial(int cd_filial) {
        this.cd_filial = cd_filial;
    }

}
