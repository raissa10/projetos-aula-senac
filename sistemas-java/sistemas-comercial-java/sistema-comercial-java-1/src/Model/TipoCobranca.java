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
 Tabela do Banco de Dados
 CREATE TABLE TIPO_COBRANCA (
 CD_COBRANCA           INTEGER NOT NULL,
 DS_COBRANCA           VARCHAR(30) NOT NULL,
 FG_IMEDIATO           SMALLINT DEFAULT 0 NOT NULL,
 FG_CHEQUE             SMALLINT DEFAULT 0 NOT NULL,
 FG_BOLETO             SMALLINT DEFAULT 0 NOT NULL,
 FG_CREDIARIO          SMALLINT DEFAULT 0 NOT NULL,
 FG_CARTAO             SMALLINT DEFAULT 0 NOT NULL,
 FG_QUITA_QUANDO_GERA  SMALLINT DEFAULT 0 NOT NULL,
 FG_ATIVO              SMALLINT DEFAULT 0 NOT NULL,
 CD_FILIAL             SMALLINT DEFAULT 0 NOT NULL,
 CD_USUARIO            SMALLINT NOT NULL,
 DT_ALT                DATE NOT NULL,
 HR_ALT                TIME NOT NULL,
 DT_CAD                DATE NOT NULL,
 HR_CAD                TIME NOT NULL
 );
 */
public class TipoCobranca {

    private int cd_cobranca;
    private String ds_cobranca;
    private int fg_imediato;
    private int fg_cheque;
    private int fg_boleto;
    private int fg_crediario;
    private int fg_cartao;
    private int fg_quita_quando_gera;
    private int fg_ativo;
    private int cd_filial;
    private int cd_usuario;

    public TipoCobranca(int cd_cobranca, String ds_cobranca, int fg_imediato, int fg_cheque, int fg_boleto, int fg_crediario, int fg_cartao, int fg_quita_quando_gera, int fg_ativo, int cd_filial, int cd_usuario) {
        this.cd_cobranca = cd_cobranca;
        this.ds_cobranca = ds_cobranca;
        this.fg_imediato = fg_imediato;
        this.fg_cheque = fg_cheque;
        this.fg_boleto = fg_boleto;
        this.fg_crediario = fg_crediario;
        this.fg_cartao = fg_cartao;
        this.fg_quita_quando_gera = fg_quita_quando_gera;
        this.fg_ativo = fg_ativo;
        this.cd_filial = cd_filial;
        this.cd_usuario = cd_usuario;
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

    public int getFg_imediato() {
        return fg_imediato;
    }

    public void setFg_imediato(int fg_imediato) {
        this.fg_imediato = fg_imediato;
    }

    public int getFg_cheque() {
        return fg_cheque;
    }

    public void setFg_cheque(int fg_cheque) {
        this.fg_cheque = fg_cheque;
    }

    public int getFg_boleto() {
        return fg_boleto;
    }

    public void setFg_boleto(int fg_boleto) {
        this.fg_boleto = fg_boleto;
    }

    public int getFg_crediario() {
        return fg_crediario;
    }

    public void setFg_crediario(int fg_crediario) {
        this.fg_crediario = fg_crediario;
    }

    public int getFg_cartao() {
        return fg_cartao;
    }

    public void setFg_cartao(int fg_cartao) {
        this.fg_cartao = fg_cartao;
    }

    public int getFg_quita_quando_gera() {
        return fg_quita_quando_gera;
    }

    public void setFg_quita_quando_gera(int fg_quita_quando_gera) {
        this.fg_quita_quando_gera = fg_quita_quando_gera;
    }

    public int getFg_ativo() {
        return fg_ativo;
    }

    public void setFg_ativo(int fg_ativo) {
        this.fg_ativo = fg_ativo;
    }

    public int getCd_filial() {
        return cd_filial;
    }

    public void setCd_filial(int cd_filial) {
        this.cd_filial = cd_filial;
    }

    public int getCd_usuario() {
        return cd_usuario;
    }

    public void setCd_usuario(int cd_usuario) {
        this.cd_usuario = cd_usuario;
    }

}
