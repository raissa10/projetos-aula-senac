package ModelCadastro;

/**
 *
 * @author Gelvazio Camargo
 *
 *
 */
/*
 * Tabela do banco de dados 

 CREATE TABLE PRODUTO_SIMPLES (
 CD_PROD            INTEGER NOT NULL,
 DS_PROD            VARCHAR(100) NOT NULL,
 CD_GRUPO           INTEGER NOT NULL,
 CD_SUB_GRUPO       INTEGER NOT NULL,
 FG_ATIVO           SMALLINT DEFAULT 1,
 CD_COR             INTEGER,
 CD_FABRICA         VARCHAR(40),
 CD_MARCA           INTEGER,
 DT_ALT             DATE NOT NULL,
 HR_ALT             TIME NOT NULL,
 DT_CAD             DATE NOT NULL,
 HR_CAD             TIME NOT NULL,
 CD_GP_FISCAL       SMALLINT NOT NULL,
 CD_NCM_SH          VARCHAR(11),
 CD_REF             BIGINT NOT NULL,
 CD_USUARIO         SMALLINT NOT NULL,
 CD_FILIAL          INTEGER NOT NULL,
 CD_UNIDADE_MEDIDA  INTEGER,
 QT_ESTOQUE         INTEGER NOT NULL,
 TX_IPI             INTEGER NOT NULL,
 TX_ISS             INTEGER NOT NULL
 );




 /******************************************************************************/
/**
 * * Unique Constraints **
 */
/**
 * ***************************************************************************
 */
//ALTER TABLE PRODUTO_SIMPLES ADD CONSTRAINT UNQ1_PRODUTO_SIMPLES UNIQUE (CD_REF);
/**
 * ***************************************************************************
 */
/**
 * * Primary Keys **
 */
/**
 * ***************************************************************************
 */
//ALTER TABLE PRODUTO_SIMPLES ADD CONSTRAINT PK_PRODUTO_SIMPLES PRIMARY KEY (CD_PROD);
/**
 * ***************************************************************************
 */
/**
 * * Privileges **
 */
/**
 * ***************************************************************************
 */
 //*/
public class ModelProduto {

    private int cd_prod;
    private String ds_prod;
    private int cd_grupo;
    private int cd_sub_grupo;
    private int fg_ativo;
    private int cd_cor;
    private String cd_fabrica;
    private int cd_marca;

    private int cd_gp_fiscal;
    private String cd_ncm_sh;
    private long cd_ref;
    private int cd_usuario;
    private int cd_filial;
    private int cd_unidade_medida;
    private int qt_estoque;
    private int tx_ipi;
    private int tx_iss;

    public ModelProduto(int cd_prod, String ds_prod, int cd_grupo, int cd_sub_grupo, int fg_ativo, int cd_cor, String cd_fabrica, int cd_marca, int cd_gp_fiscal, String cd_ncm_sh, long cd_ref, int cd_usuario, int cd_filial, int cd_unidade_medida, int qt_estoque, int tx_ipi, int tx_iss) {
        this.cd_prod = cd_prod;
        this.ds_prod = ds_prod;
        this.cd_grupo = cd_grupo;
        this.cd_sub_grupo = cd_sub_grupo;
        this.fg_ativo = fg_ativo;
        this.cd_cor = cd_cor;
        this.cd_fabrica = cd_fabrica;
        this.cd_marca = cd_marca;
        this.cd_gp_fiscal = cd_gp_fiscal;
        this.cd_ncm_sh = cd_ncm_sh;
        this.cd_ref = cd_ref;
        this.cd_usuario = cd_usuario;
        this.cd_filial = cd_filial;
        this.cd_unidade_medida = cd_unidade_medida;
        this.qt_estoque = qt_estoque;
        this.tx_ipi = tx_ipi;
        this.tx_iss = tx_iss;
    }

    public int getCd_prod() {
        return cd_prod;
    }

    public void setCd_prod(int cd_prod) {
        this.cd_prod = cd_prod;
    }

    public String getDs_prod() {
        return ds_prod;
    }

    public void setDs_prod(String ds_prod) {
        this.ds_prod = ds_prod;
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

    public int getFg_ativo() {
        return fg_ativo;
    }

    public void setFg_ativo(int fg_ativo) {
        this.fg_ativo = fg_ativo;
    }

    public int getCd_cor() {
        return cd_cor;
    }

    public void setCd_cor(int cd_cor) {
        this.cd_cor = cd_cor;
    }

    public String getCd_fabrica() {
        return cd_fabrica;
    }

    public void setCd_fabrica(String cd_fabrica) {
        this.cd_fabrica = cd_fabrica;
    }

    public int getCd_marca() {
        return cd_marca;
    }

    public void setCd_marca(int cd_marca) {
        this.cd_marca = cd_marca;
    }

    public int getCd_gp_fiscal() {
        return cd_gp_fiscal;
    }

    public void setCd_gp_fiscal(int cd_gp_fiscal) {
        this.cd_gp_fiscal = cd_gp_fiscal;
    }

    public String getCd_ncm_sh() {
        return cd_ncm_sh;
    }

    public void setCd_ncm_sh(String cd_ncm_sh) {
        this.cd_ncm_sh = cd_ncm_sh;
    }

    public long getCd_ref() {
        return cd_ref;
    }

    public void setCd_ref(long cd_ref) {
        this.cd_ref = cd_ref;
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

    public int getCd_unidade_medida() {
        return cd_unidade_medida;
    }

    public void setCd_unidade_medida(int cd_unidade_medida) {
        this.cd_unidade_medida = cd_unidade_medida;
    }

    public int getQt_estoque() {
        return qt_estoque;
    }

    public void setQt_estoque(int qt_estoque) {
        this.qt_estoque = qt_estoque;
    }

    public int getTx_ipi() {
        return tx_ipi;
    }

    public void setTx_ipi(int tx_ipi) {
        this.tx_ipi = tx_ipi;
    }

    public int getTx_iss() {
        return tx_iss;
    }

    public void setTx_iss(int tx_iss) {
        this.tx_iss = tx_iss;
    }

}
