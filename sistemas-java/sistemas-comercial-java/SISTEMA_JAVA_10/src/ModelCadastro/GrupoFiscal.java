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

/**
 *
 * @author Gelvazio Camargo
 */
public class GrupoFiscal {

    private int cd_filial;
    private int cd_grupo_fiscal;
    private String ds_grupo_fiscal;
    private int cd_usuario;

    public GrupoFiscal(int cd_filial, int cd_grupo_fiscal, String ds_grupo_fiscal, int cd_usuario) {
        this.cd_filial = cd_filial;
        this.cd_grupo_fiscal = cd_grupo_fiscal;
        this.ds_grupo_fiscal = ds_grupo_fiscal;
        this.cd_usuario = cd_usuario;
    }

    public int getCd_filial() {
        return cd_filial;
    }

    public void setCd_filial(int cd_filial) {
        this.cd_filial = cd_filial;
    }

    public int getCd_grupo_fiscal() {
        return cd_grupo_fiscal;
    }

    public void setCd_grupo_fiscal(int cd_grupo_fiscal) {
        this.cd_grupo_fiscal = cd_grupo_fiscal;
    }

    public String getDs_grupo_fiscal() {
        return ds_grupo_fiscal;
    }

    public void setDs_grupo_fiscal(String ds_grupo_fiscal) {
        this.ds_grupo_fiscal = ds_grupo_fiscal;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

}
