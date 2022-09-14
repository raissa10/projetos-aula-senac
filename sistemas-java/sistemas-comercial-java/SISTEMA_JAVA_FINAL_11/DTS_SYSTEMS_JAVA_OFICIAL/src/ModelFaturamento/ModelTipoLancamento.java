package ModelFaturamento;

/**
 *
 * @author gelvazio
 */


public class ModelTipoLancamento {
    
    private int cd_tipo_lancamento;
    private String ds_tipo_lancamento;

    public ModelTipoLancamento(int cd_tipo_lancamento, String ds_tipo_lancamento) {
        this.cd_tipo_lancamento = cd_tipo_lancamento;
        this.ds_tipo_lancamento = ds_tipo_lancamento;
    }

    public int getCd_tipo_lancamento() {
        return cd_tipo_lancamento;
    }

    public void setCd_tipo_lancamento(int cd_tipo_lancamento) {
        this.cd_tipo_lancamento = cd_tipo_lancamento;
    }

    public String getDs_tipo_lancamento() {
        return ds_tipo_lancamento;
    }

    public void setDs_tipo_lancamento(String ds_tipo_lancamento) {
        this.ds_tipo_lancamento = ds_tipo_lancamento;
    }

    
}
