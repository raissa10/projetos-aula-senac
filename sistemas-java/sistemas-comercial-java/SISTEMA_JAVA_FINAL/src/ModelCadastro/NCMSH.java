package ModelCadastro;

/**
 *
 * @author Gelvazio Camargo Tabela banco dados CREATE TABLE NCMSH ( CD_CODIGO
 * INTEGER NOT NULL, CD_NCMSH VARCHAR(10), DS_TITULO_1 BLOB SUB_TYPE 1 SEGMENT
 * SIZE 80, DS_TITULO_2 BLOB SUB_TYPE 1 SEGMENT SIZE 80, DS_NCMSH BLOB SUB_TYPE
 * 1 SEGMENT SIZE 80,
 *
 * CD_USUARIO SMALLINT NOT NULL, DT_ALT DATE NOT NULL, HR_ALT TIME NOT NULL,
 * DT_CAD DATE NOT NULL, HR_CAD TIME NOT NULL, CD_FILIAL INTEGER NOT NULL ); );
 *
 */
public class NCMSH {

    private int cd_codigo;
    private String cd_ncmsh;
    private String ds_titulo_1;
    private String ds_titulo_2;
    private String ds_ncmsh;
    private int vl_mva;

    public NCMSH(int cd_codigo, String cd_ncmsh, String ds_titulo_1, String ds_titulo_2, String ds_ncmsh, int vl_mva) {
        this.cd_codigo = cd_codigo;
        this.cd_ncmsh = cd_ncmsh;
        this.ds_titulo_1 = ds_titulo_1;
        this.ds_titulo_2 = ds_titulo_2;
        this.ds_ncmsh = ds_ncmsh;
        this.vl_mva = vl_mva;
    }

    public int getCd_codigo() {
        return cd_codigo;
    }

    public void setCd_codigo(int cd_codigo) {
        this.cd_codigo = cd_codigo;
    }

    public String getCd_ncmsh() {
        return cd_ncmsh;
    }

    public void setCd_ncmsh(String cd_ncmsh) {
        this.cd_ncmsh = cd_ncmsh;
    }

    public String getDs_titulo_1() {
        return ds_titulo_1;
    }

    public void setDs_titulo_1(String ds_titulo_1) {
        this.ds_titulo_1 = ds_titulo_1;
    }

    public String getDs_titulo_2() {
        return ds_titulo_2;
    }

    public void setDs_titulo_2(String ds_titulo_2) {
        this.ds_titulo_2 = ds_titulo_2;
    }

    public String getDs_ncmsh() {
        return ds_ncmsh;
    }

    public void setDs_ncmsh(String ds_ncmsh) {
        this.ds_ncmsh = ds_ncmsh;
    }

    public int getVl_mva() {
        return vl_mva;
    }

    public void setVl_mva(int vl_mva) {
        this.vl_mva = vl_mva;
    }

}
