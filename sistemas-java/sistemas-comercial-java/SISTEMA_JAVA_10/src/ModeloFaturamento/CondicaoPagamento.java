/*
 * Copyright (C) 2014 Gelvazio
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ModeloFaturamento;

/**
 *
 * @author Gelvazio
 *
 * CREATE TABLE COND_PAG ( CD_COND INTEGER NOT NULL, DS_COND VARCHAR(50) NOT
 * NULL, CD_USUARIO SMALLINT NOT NULL, DT_ALT DATE NOT NULL, HR_ALT TIME NOT
 * NULL, DT_CAD DATE NOT NULL, HR_CAD TIME NOT NULL );
 */
public class CondicaoPagamento {

    private int cd_cond;
    private String ds_cond;
    private int cd_usuario;

    public CondicaoPagamento(int cd_cond, String ds_cond, int cd_usuario) {
        this.cd_cond = cd_cond;
        this.ds_cond = ds_cond;
        this.cd_usuario = cd_usuario;
    }

    public int getCd_cond() {
        return cd_cond;
    }

    public void setCd_cond(int cd_cond) {
        this.cd_cond = cd_cond;
    }

    public String getDs_cond() {
        return ds_cond;
    }

    public void setDs_cond(String ds_cond) {
        this.ds_cond = ds_cond;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

}
