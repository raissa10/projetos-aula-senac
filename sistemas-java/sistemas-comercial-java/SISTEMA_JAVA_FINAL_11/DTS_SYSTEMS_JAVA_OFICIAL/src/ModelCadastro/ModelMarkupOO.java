/*
 * Copyright (C) 2014 Gelvazio Camargo
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
package ModelCadastro;

public class ModelMarkupOO {

    private int cd_grupo_fiscal;
    private String cd_estado;
    private int tx_icms_interno;
    private int tx_icms_interestadual;
    private int cd_usuario;

    /*
     public ModelMarkupOO(int cd_grupo_fiscal, String cd_estado, int tx_icms_interno, int tx_icms_interestadual, int cd_usuario) {
     this.cd_grupo_fiscal = cd_grupo_fiscal;
     this.cd_estado = cd_estado;
     this.tx_icms_interno = tx_icms_interno;
     this.tx_icms_interestadual = tx_icms_interestadual;
     this.cd_usuario = cd_usuario;
     }
     */
    public int getCd_grupo_fiscal() {
        return cd_grupo_fiscal;
    }

    public void setCd_grupo_fiscal(int cd_grupo_fiscal) {
        this.cd_grupo_fiscal = cd_grupo_fiscal;
    }

    public String getCd_estado() {
        return cd_estado;
    }

    public void setCd_estado(String cd_estado) {
        this.cd_estado = cd_estado;
    }

    public int getTx_icms_interno() {
        return tx_icms_interno;
    }

    public void setTx_icms_interno(int tx_icms_interno) {
        this.tx_icms_interno = tx_icms_interno;
    }

    public int getTx_icms_interestadual() {
        return tx_icms_interestadual;
    }

    public void setTx_icms_interestadual(int tx_icms_interestadual) {
        this.tx_icms_interestadual = tx_icms_interestadual;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

}
