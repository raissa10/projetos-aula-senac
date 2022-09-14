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
package Model;

/**
 *
 * @author Gelvazio Camargo
 */
/*
 Tabla Banco de dados
 CREATE TABLE SUB_COND_PAG (
 CD_CONDICAO      INTEGER NOT NULL,
 CD_PARCELA       SMALLINT NOT NULL,
 NR_DIAS_PARCELA  SMALLINT NOT NULL,
 CD_COBRANCA      INTEGER NOT NULL,
 CD_USUARIO       SMALLINT NOT NULL,
 DT_ALT           DATE NOT NULL,
 HR_ALT           TIME NOT NULL,
 DT_CAD           DATE NOT NULL,
 HR_CAD           TIME NOT NULL
 );
 */
public class SubCondPag {

    private int cd_condicao;
    private int cd_parcela;
    private int nr_dias_parcela;
    private int cd_cobranca;
    private int cd_usuario;

    public SubCondPag(int cd_condicao, int cd_parcela, int nr_dias_parcela, int cd_cobranca, int cd_usuario) {
        this.cd_condicao = cd_condicao;
        this.cd_parcela = cd_parcela;
        this.nr_dias_parcela = nr_dias_parcela;
        this.cd_cobranca = cd_cobranca;
        this.cd_usuario = cd_usuario;
    }

    public int getCd_condicao() {
        return cd_condicao;
    }

    public void setCd_condicao(int cd_condicao) {
        this.cd_condicao = cd_condicao;
    }

    public int getCd_parcela() {
        return cd_parcela;
    }

    public void setCd_parcela(int cd_parcela) {
        this.cd_parcela = cd_parcela;
    }

    public int getNr_dias_parcela() {
        return nr_dias_parcela;
    }

    public void setNr_dias_parcela(int nr_dias_parcela) {
        this.nr_dias_parcela = nr_dias_parcela;
    }

    public int getCd_cobranca() {
        return cd_cobranca;
    }

    public void setCd_cobranca(int cd_cobranca) {
        this.cd_cobranca = cd_cobranca;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

}
