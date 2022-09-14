/*
 * Copyright (C) 2015 Gelvazio Camargo
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
package Model;

/**
 *
 * @author Gelvazio Camargo
 */
public class ParcelaDados {
    private int cd_parcela;
    private int cd_cobranca;
    private String ds_cobranca;
    private int nr_dias_parcelas;

    public ParcelaDados(int cd_parcela, int cd_cobranca, String ds_cobranca, int nr_dias_parcelas) {
        this.cd_parcela = cd_parcela;
        this.cd_cobranca = cd_cobranca;
        this.ds_cobranca = ds_cobranca;
        this.nr_dias_parcelas = nr_dias_parcelas;
    }

    public int getCd_parcela() {
        return cd_parcela;
    }

    public void setCd_parcela(int cd_parcela) {
        this.cd_parcela = cd_parcela;
    }

    public int getCd_cobranca() {
        return cd_cobranca;
    }

    public void setCd_cobranca(int cd_cobranca) {
        this.cd_cobranca = cd_cobranca;
    }

    public String getDs_cobranca() {
        return ds_cobranca;
    }

    public void setDs_cobranca(String ds_cobranca) {
        this.ds_cobranca = ds_cobranca;
    }

    public int getNr_dias_parcelas() {
        return nr_dias_parcelas;
    }

    public void setNr_dias_parcelas(int nr_dias_parcelas) {
        this.nr_dias_parcelas = nr_dias_parcelas;
    }

    
}
