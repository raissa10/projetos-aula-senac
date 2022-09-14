/*
 * Copyright (C) 2018 Gelvazio Camargo
 *
 * Este arquivo é parte do programa DTS SYSTEMS - SOFTWARE COMERCIAL;
 * O DTS SYSTEMS e um software livre; voce pode redistribui-lo e/ou modifica-lo
 * dentro dos termos da Licenca Publica Geral GNU como publicada pela Fundacao do    
 * Software Livre - FSF;
 *
 * Este sistema e distribuido na esperanca de ser util, mas SEM NENHUMA GARANTIA,
 * sem uma garantia implicita de ADEQUACAO a qualquer MERCADO ou APLICACAO EM PARTICULAR
 * Veja GNU General Public License para mais detalhes.
 *
 * Voce deve ter recebido uma cópia da Licenca Publica Geral GNU/GPL
 * com este programa.  Se não, veja <http://www.gnu.org/licenses/>.
 */
package Model;

/**
 *
 * @author Gelvazio Camargo
 */
public class ModelCorPadrao {

    private int cd_cor;
    private String ds_cor;
    private int cd_usuario;

    public ModelCorPadrao(int cd_cor, String ds_cor, int cd_usuario) {
        this.cd_cor = cd_cor;
        this.ds_cor = ds_cor;
        this.cd_usuario = cd_usuario;
    }

    public int getCd_cor() {
        return cd_cor;
    }

    public void setCd_cor(int cd_cor) {
        this.cd_cor = cd_cor;
    }

    public String getDs_cor() {
        return ds_cor;
    }

    public void setDs_cor(String ds_cor) {
        this.ds_cor = ds_cor;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }
}
