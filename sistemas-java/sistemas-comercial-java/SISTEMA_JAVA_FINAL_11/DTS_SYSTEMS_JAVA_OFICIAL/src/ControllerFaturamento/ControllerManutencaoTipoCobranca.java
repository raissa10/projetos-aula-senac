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
package ControllerFaturamento;

import Estrutura.ControllerManutencaoPadrao;
import ModelFaturamento.ModelTipoCobranca;
import Principal.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio
 */
public class ControllerManutencaoTipoCobranca extends ControllerManutencaoPadrao {

    private static final String sqlTodos
            = "SELECT * FROM TIPO_COBRANCA ORDER BY DS_COBRANCA";
    private static final String sqlExcluir
            = "DELETE FROM TIPO_COBRANCA WHERE CD_COBRANCA= ?";
    private static final String sqlInserir
            = "INSERT INTO TIPO_COBRANCA        "
            + "(CD_COBRANCA, DS_COBRANCA,       "
            + "FG_IMEDIATO, FG_CHEQUE,FG_CARTAO,"
            + " CD_FILIAL,                      "
            + "CD_USUARIO, DT_ALT, HR_ALT,      "
            + "DT_CAD, HR_CAD,                  "
            + "FG_CREDIARIO, FG_BOLETO,         "
            + "FG_QUITA_QUANDO_GERA,            "
            + "FG_ATIVO)                        "
            + "VALUES                           "
            + "(?,?,?,?,?,?,?,                  "
            + "CAST('NOW' AS DATE),             "
            + "CAST('NOW' AS TIME),             "
            + "CAST('NOW' AS DATE),             "
            + "CAST('NOW' AS TIME),             "
            + "?,?,?,?)                         ";
    private static final String sqlAlterar
            = "UPDATE TIPO_COBRANCA             "
            + "SET DS_COBRANCA =?,              "
            + "    FG_IMEDIATO = ?,             "
            + "    FG_CHEQUE =?,                "
            + "    FG_CARTAO = ?,               "
            + "    CD_FILIAL = ?,               "
            + "    CD_USUARIO = ?,              "
            + "    DT_ALT = CAST('NOW' AS DATE),"
            + "    HR_ALT = CAST('NOW' AS TIME),"
            + "    DT_CAD = CAST('NOW' AS DATE),"
            + "    HR_CAD = CAST('NOW' AS TIME),"
            + "    FG_CREDIARIO = ?,            "
            + "    FG_BOLETO = ?,               "
            + "    FG_QUITA_QUANDO_GERA = ?,    "
            + "    FG_ATIVO = ?                 "
            + "WHERE (CD_COBRANCA = ?);         ";
    private static final String sqlVerificaRegistro
            = "SELECT                "
            + "    count(*) as total "
            + "FROM                  "
            + "    TIPO_COBRANCA     "
            + "WHERE                 "
            + "    CD_COBRANCA=?     ";

    private static final String sqlBuscaRegistro
            = "SELECT                "
            + "    TIPO_COBRANCA.*   "
            + "FROM                  "
            + "    TIPO_COBRANCA     "
            + "WHERE                 "
            + "    CD_COBRANCA=?     ";

    private static final String SQLCONSULTANOMETIPOCOBRANCAS
            = "SELECT                                                  "
            + "    TIPO_COBRANCA.*                                     "
            + "FROM                                                    "
            + "    SUB_COND_PAG                                        "
            + "    INNER JOIN TIPO_COBRANCA ON                         "
            + "    TIPO_COBRANCA.CD_COBRANCA=SUB_COND_PAG.CD_COBRANCA  "
            + "WHERE                                                   "
            + "    SUB_COND_PAG.CD_CONDICAO=?                          ";

    public DefaultComboBoxModel getComboRegistro() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("DS_COBRANCA"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboRegistro(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterar(ModelTipoCobranca tipocobranca) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, tipocobranca.getDs_cobranca());
            pstmt.setInt(2, tipocobranca.getFg_imediato());
            pstmt.setInt(3, tipocobranca.getFg_cheque());
            pstmt.setInt(4, tipocobranca.getFg_cartao());
            pstmt.setInt(5, tipocobranca.getCd_filial());
            pstmt.setInt(6, tipocobranca.getCd_usuario());
            pstmt.setInt(7, tipocobranca.getFg_crediario());
            pstmt.setInt(8, tipocobranca.getFg_boleto());
            pstmt.setInt(9, tipocobranca.getFg_quita_quando_gera());
            pstmt.setInt(10, tipocobranca.getFg_ativo());
            pstmt.setInt(11, tipocobranca.getCd_cobranca());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. alterar(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean inserir(ModelTipoCobranca tipocobranca) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(11, tipocobranca.getCd_cobranca());
            pstmt.setString(2, tipocobranca.getDs_cobranca());
            pstmt.setInt(3, tipocobranca.getFg_imediato());
            pstmt.setInt(4, tipocobranca.getFg_cheque());
            pstmt.setInt(5, tipocobranca.getFg_cartao());
            pstmt.setInt(6, tipocobranca.getCd_filial());
            pstmt.setInt(7, tipocobranca.getCd_usuario());
            pstmt.setInt(8, tipocobranca.getFg_crediario());
            pstmt.setInt(9, tipocobranca.getFg_boleto());
            pstmt.setInt(10, tipocobranca.getFg_quita_quando_gera());
            pstmt.setInt(11, tipocobranca.getFg_ativo());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. inserir(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public boolean excluir(int cd_cobranca) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_cobranca);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. excluir(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public ArrayList getTodos() {
        ArrayList listaTiposCobranca = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                String auxds_cobranca = rs.getString("ds_cobranca");
                int auxfg_cheque = rs.getInt("fg_cheque");
                int auxfg_imediato = rs.getInt("fg_imediato");
                int auxfg_cartao = rs.getInt("fg_cartao");
                int auxcd_filial = rs.getInt("cd_filial");
                int auxcd_usuario = rs.getInt("cd_usuario");
                int auxfg_crediario = rs.getInt("fg_crediario");
                int auxfg_boleto = rs.getInt("fg_boleto");
                int auxfg_quita_quando_gera = rs.getInt("fg_quita_quando_gera");
                int auxfg_ativo = rs.getInt("fg_ativo");
                int auxcd_cobranca = rs.getInt("cd_cobranca");

                ModelTipoCobranca tipocobranca = new ModelTipoCobranca(
                        auxcd_cobranca,
                        auxds_cobranca,
                        auxfg_imediato,
                        auxfg_cheque,
                        auxfg_boleto,
                        auxfg_crediario,
                        auxfg_cartao,
                        auxfg_quita_quando_gera,
                        auxfg_ativo,
                        auxcd_filial,
                        auxcd_usuario
                );
                listaTiposCobranca.add(tipocobranca);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. getTodos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaTiposCobranca;
    }

    public boolean getTipoCobranca(int cd_cor) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlVerificaRegistro);
            pstmt.setInt(1, cd_cor);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("total") > 0) {
                    existe = true;
                } else {
                    existe = false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getCor(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public int ValidaCodigoGenerator() {
        int codigoGenerator = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT GEN_ID(CD_TIPO_COBRANCA, 1) FROM RDB$DATABASE");
            while (rs.next()) {
                int auxCodigoGenerator = rs.getInt("GEN_ID");
                int auxCodigo = auxCodigoGenerator + 1;
                codigoGenerator = auxCodigo;
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de conexão! \n" + erro.getMessage());
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro no método ValidaCodigoGenerator()\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return codigoGenerator;
    }

    public ArrayList listaTiposCobranca(int cd_cobranca) {
        ArrayList listaTiposCobranca = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaRegistro);
            pstmt.setInt(1, cd_cobranca);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String auxds_cobranca = rs.getString("ds_cobranca");
                int auxfg_cheque = rs.getInt("fg_cheque");
                int auxfg_imediato = rs.getInt("fg_imediato");
                int auxfg_cartao = rs.getInt("fg_cartao");
                int auxcd_filial = rs.getInt("cd_filial");
                int auxcd_usuario = rs.getInt("cd_usuario");
                int auxfg_crediario = rs.getInt("fg_crediario");
                int auxfg_boleto = rs.getInt("fg_boleto");
                int auxfg_quita_quando_gera = rs.getInt("fg_quita_quando_gera");
                int auxfg_ativo = rs.getInt("fg_ativo");
                int auxcd_cobranca = rs.getInt("cd_cobranca");

                ModelTipoCobranca tipocobranca = new ModelTipoCobranca(
                        auxcd_cobranca,
                        auxds_cobranca,
                        auxfg_imediato,
                        auxfg_cheque,
                        auxfg_boleto,
                        auxfg_crediario,
                        auxfg_cartao,
                        auxfg_quita_quando_gera,
                        auxfg_ativo,
                        auxcd_filial,
                        auxcd_usuario
                );
                listaTiposCobranca.add(tipocobranca);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no ArrayList listaTiposCobranca: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaTiposCobranca;
    }

    public ArrayList listaNomeTipoCobranca(int cd_movimento) {
        ArrayList listaNomeTipoCobranca = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(SQLCONSULTANOMETIPOCOBRANCAS);
            pstmt.setInt(1, cd_movimento);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String auxds_cobranca = rs.getString("ds_cobranca");
                int auxfg_cheque = rs.getInt("fg_cheque");
                int auxfg_imediato = rs.getInt("fg_imediato");
                int auxfg_cartao = rs.getInt("fg_cartao");
                int auxcd_filial = rs.getInt("cd_filial");
                int auxcd_usuario = rs.getInt("cd_usuario");
                int auxfg_crediario = rs.getInt("fg_crediario");
                int auxfg_boleto = rs.getInt("fg_boleto");
                int auxfg_quita_quando_gera = rs.getInt("fg_quita_quando_gera");
                int auxfg_ativo = rs.getInt("fg_ativo");
                int auxcd_cobranca = rs.getInt("cd_cobranca");

                ModelTipoCobranca tipocobranca = new ModelTipoCobranca(
                        auxcd_cobranca,
                        auxds_cobranca,
                        auxfg_imediato,
                        auxfg_cheque,
                        auxfg_boleto,
                        auxfg_crediario,
                        auxfg_cartao,
                        auxfg_quita_quando_gera,
                        auxfg_ativo,
                        auxcd_filial,
                        auxcd_usuario
                );
                listaNomeTipoCobranca.add(tipocobranca);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql,  listaNomeTipoCobranca(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaNomeTipoCobranca;
    }

}
