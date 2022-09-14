/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelCadastro;

/**
 *
 * @author Gelvazio
 */
public class ModelMunicipio {
    /*
     CREATE TABLE MUNICIPIO (
     CD_ESTADO     VARCHAR(2) NOT NULL,
     CD_MUNICIPIO  INTEGER NOT NULL,
     DS_MUNICIPIO  VARCHAR(50),
     CD_USUARIO    SMALLINT NOT NULL,
     DT_ALT        DATE NOT NULL,
     HR_ALT        TIME NOT NULL,
     DT_CAD        DATE NOT NULL,
     HR_CAD        TIME NOT NULL,
    
     CD_IBGE       INTEGER,
     CD_CEP        VARCHAR(20),
     CD_FILIAL     INTEGER NOT NULL
     );

     */

    private String cd_estado;
    private int cd_municipio;
    private String ds_municipio;
    private int cd_ibge;
    private String cd_cep;
    private int cd_usuario, cd_filial;

    public ModelMunicipio(String cd_estado, int cd_municipio, String ds_municipio, int cd_ibge, String cd_cep, int cd_usuario, int cd_filial) {
        this.cd_estado = cd_estado;
        this.cd_municipio = cd_municipio;
        this.ds_municipio = ds_municipio;
        this.cd_ibge = cd_ibge;
        this.cd_cep = cd_cep;
        this.cd_usuario = cd_usuario;
        this.cd_filial = cd_filial;
    }

    public String getCd_estado() {
        return cd_estado;
    }

    public void setCd_estado(String cd_estado) {
        this.cd_estado = cd_estado;
    }

    public int getCd_municipio() {
        return cd_municipio;
    }

    public void setCd_municipio(int cd_municipio) {
        this.cd_municipio = cd_municipio;
    }

    public String getDs_municipio() {
        return ds_municipio;
    }

    public void setDs_municipio(String ds_municipio) {
        this.ds_municipio = ds_municipio;
    }

    public int getCd_ibge() {
        return cd_ibge;
    }

    public void setCd_ibge(int cd_ibge) {
        this.cd_ibge = cd_ibge;
    }

    public String getCd_cep() {
        return cd_cep;
    }

    public void setCd_cep(String cd_cep) {
        this.cd_cep = cd_cep;
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
