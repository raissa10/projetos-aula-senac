package ModelCadastro;

/**
 *
 * @author Gelvazio Camargo
 *
 */
/*
 Tabela Banco Dados 
 cd_unidade  smallint not null,
 ds_unidade  varchar(40),
 dt_alt      date not null,
 dt_cad      date not null,
 hr_cad      time not null,
 hr_alt      time not null,
 cd_usuario  smallint not null,
 cd_filial   integer not null,
 ds_sigla    varchar(20)
 */
public class ModelUnidadeMedida {

    private int cd_unidade;
    private String ds_unidade;
    private String ds_sigla;
    private int cd_usuario;
    private int cd_filial;

    public ModelUnidadeMedida(int cd_unidade, String ds_unidade, int cd_usuario, int cd_filial, String ds_sigla) {
        this.cd_unidade = cd_unidade;
        this.ds_unidade = ds_unidade;
        this.ds_sigla = ds_sigla;
        this.cd_usuario = cd_usuario;
        this.cd_filial = cd_filial;
    }

    public int getCd_unidade() {
        return cd_unidade;
    }

    public void setCd_unidade(int cd_unidade) {
        this.cd_unidade = cd_unidade;
    }

    public String getDs_unidade() {
        return ds_unidade;
    }

    public void setDs_unidade(String ds_unidade) {
        this.ds_unidade = ds_unidade;
    }

    public String getDs_sigla() {
        return ds_sigla;
    }

    public void setDs_sigla(String ds_sigla) {
        this.ds_sigla = ds_sigla;
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
