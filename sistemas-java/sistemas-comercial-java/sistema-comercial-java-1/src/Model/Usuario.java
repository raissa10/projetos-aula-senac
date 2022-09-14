/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Gelvazio Camargo
 */
/*
 * Tabela do banco de dados
 create table usuario (
 cd_usuario  smallint not null,
 ds_login    varchar(20) not null,
 ds_usuario  varchar(35) not null,
 ds_senha    varchar(10),
 cd_grupo    integer not null,
 dt_alt      date not null,
 hr_alt      time not null,
 dt_cad      date not null,
 hr_cad      time not null,
 cd_filial   integer not null,
 fg_ativo    smallint default 1 not null
 );

 */
public class Usuario {

    private int cd_usuario;
    private String ds_login;
    private String ds_usuario;
    private String ds_senha;
    private int cd_grupo;
    private int cd_filial;
    private int fg_ativo;

    public Usuario(int cd_usuario, String ds_login, String ds_usuario, String ds_senha, int cd_grupo, int cd_filial, int fg_ativo) {
        this.cd_usuario = cd_usuario;
        this.ds_login = ds_login;
        this.ds_usuario = ds_usuario;
        this.ds_senha = ds_senha;
        this.cd_grupo = cd_grupo;
        this.cd_filial = cd_filial;
        this.fg_ativo = fg_ativo;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

    public String getDs_login() {
        return ds_login;
    }

    public void setDs_login(String ds_login) {
        this.ds_login = ds_login;
    }

    public String getDs_usuario() {
        return ds_usuario;
    }

    public void setDs_usuario(String ds_usuario) {
        this.ds_usuario = ds_usuario;
    }

    public String getDs_senha() {
        return ds_senha;
    }

    public void setDs_senha(String ds_senha) {
        this.ds_senha = ds_senha;
    }

    public int getCd_grupo() {
        return cd_grupo;
    }

    public void setCd_grupo(int cd_grupo) {
        this.cd_grupo = cd_grupo;
    }

    public int getCd_filial() {
        return cd_filial;
    }

    public void setCd_filial(int cd_filial) {
        this.cd_filial = cd_filial;
    }

    public int getFg_ativo() {
        return fg_ativo;
    }

    public void setFg_ativo(int fg_ativo) {
        this.fg_ativo = fg_ativo;
    }

}
