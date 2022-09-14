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
package ControleCadastro;

import ModelCadastro.GrupoFiscal;
import ModelCadastro.Markup;
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
 * @author Gelvazio Camargo
 */
public class MarkupDB {

    private static final String sqlTodos
            = "SELECT                     "
            + "    MARKUP.*               "
            + "FROM                       "
            + "    MARKUP                 "
            + "ORDER BY                   "
            + "    MARKUP.CD_GRUPO_FISCAL ";
    private static final String sqlInserir
            = "INSERT INTO MARKUP (CD_GRUPO_FISCAL, "
            + "CD_ESTADO, TX_ICMS_INTERNO,          "
            + "TX_ICMS_INTERESTADUAL, CD_USUARIO,   "
            + "DT_CAD,DT_ALT, HR_CAD, HR_ALT)       "
            + "VALUES                               "
            + "(?, ?, ?, ?, ?,                      "
            + "CAST('NOW' AS DATE),                 "
            + "CAST('NOW' AS DATE),                 "
            + "CAST('NOW' AS TIME),                 "
            + "CAST('NOW' AS TIME));                ";

    private static final String sqlExcluir
            = "DELETE FROM                  "
            + "    MARKUP                   "
            + "WHERE                        "
            + "    MARKUP.CD_ESTADO=?       "
            + "    AND                      "
            + "    MARKUP.CD_GRUPO_FISCAL=? ";
    private static final String sqlExcluirTodooGrid
            = "DELETE FROM                  "
            + "    MARKUP                   "
            + "WHERE                        "
            + "    MARKUP.CD_GRUPO_FISCAL=? ";
    private static final String excluirTudoForaGrid
            = "DELETE FROM                  "
            + "    MARKUP                   "
            + "WHERE                        "
            + "    MARKUP.CD_ESTADO NOT  "
            + "    IN (?);                  ";
    private static final String sqlAlterar
            = "UPDATE MARKUP     SET                       "
            + "    TX_ICMS_INTERNO = ?,                    "
            + "    TX_ICMS_INTERESTADUAL=?,                "
            + "    CD_USUARIO = ?,                         "
            + "    DT_ALT = CAST('NOW' AS DATE),           "
            + "    HR_ALT = CAST('NOW' AS TIME)            "
            + "WHERE                                       "
            + "(CD_GRUPO_FISCAL = ?) AND (CD_ESTADO = ?);  ";

    private static final String sqlMarkup
            = "SELECT                      "
            + "    COUNT(*) AS TOTAL       "
            + "FROM                        "
            + "    MARKUP                  "
            + "WHERE                       "
            + "    MARKUP.CD_GRUPO_FISCAL=?"
            + "    AND                     "
            + "    MARKUP.CD_ESTADO=?      ";

    private static final String sqlMarkupPeloGrupoFiscal
            = "SELECT                 "
            + "    COUNT(*) AS TOTAL  "
            + "FROM                   "
            + "    MARKUP             "
            + "WHERE                  "
            + "    CD_GRUPO_FISCAL=?  ";

    private static final String sqlBuscaMarkup
            = "SELECT                      "
            + "    MARKUP.*                "
            + "FROM                        "
            + "    MARKUP                  "
            + "WHERE                       "
            + "    MARKUP.CD_GRUPO_FISCAL=?";

    public DefaultComboBoxModel getComboMarkup() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("DS_SUB_GRUPO"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboMarkup(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterar(Markup markup) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setInt(1, markup.getTx_icms_interno());
            pstmt.setInt(2, markup.getTx_icms_interestadual());
            pstmt.setInt(3, markup.getCd_grupo_fiscal());
            pstmt.setInt(4, markup.getCd_grupo_fiscal());
            pstmt.setString(5, markup.getCd_estado());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. alterar(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean inserir(Markup markup) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, markup.getCd_grupo_fiscal());
            pstmt.setString(2, markup.getCd_estado());
            pstmt.setInt(3, markup.getTx_icms_interno());
            pstmt.setInt(4, markup.getTx_icms_interestadual());
            pstmt.setInt(5, markup.getCd_usuario());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. inserir():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public boolean excluir(String cdestado, int cdgrupofiscal) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setString(1, cdestado);
            pstmt.setInt(2, cdgrupofiscal);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluir(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean excluirGridInteiro(int cdgrupofiscal) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluirTodooGrid);
            pstmt.setInt(1, cdgrupofiscal);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirGridInteiro(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean excluirTudoForaGrid(int cdsubgrupo) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(excluirTudoForaGrid);
            pstmt.setInt(1, cdsubgrupo);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirTudoForaGrid(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public ArrayList getTodos() {

        ArrayList listaMarkup = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                int auxcdgrupofiscal = rs.getInt("CD_GRUPO_FISCAL");
                String cdestado = rs.getString("CD_ESTADO");
                int auxtxicmsinterno = rs.getInt("TX_ICMS_INTERNO");
                int auxtxicmsinterestadual = rs.getInt("TX_ICMS_INTERESTADUAL");
                int auxcdusuario = rs.getInt("CD_USUARIO");

                Markup markup = new Markup(
                        auxcdgrupofiscal,
                        cdestado,
                        auxtxicmsinterno,
                        auxtxicmsinterestadual,
                        auxcdusuario);
                listaMarkup.add(markup);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getTodos():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaMarkup;
    }

    public boolean getMarkup(int cdgrupofiscal, String cdestado) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlMarkup);
            pstmt.setInt(1, cdgrupofiscal);
            pstmt.setString(2, cdestado);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("TOTAL") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getMarkup(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean getMarkupPeloGrupo(int cdgrupofiscal) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlMarkupPeloGrupoFiscal);
            pstmt.setInt(1, cdgrupofiscal);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("TOTAL") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getMarkupPeloGrupo(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public ArrayList listaMarkups(int codigo) {
        ArrayList listaMarkup = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaMarkup);
            pstmt.setInt(1, codigo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int auxcdgrupofiscal = rs.getInt("CD_GRUPO_FISCAL");
                String cdestado = rs.getString("CD_ESTADO");
                int auxtxicmsinterno = rs.getInt("TX_ICMS_INTERNO");
                int auxtxicmsinterestadual = rs.getInt("TX_ICMS_INTERESTADUAL");
                int auxcdusuario = rs.getInt("CD_USUARIO");

                Markup markup = new Markup(
                        auxcdgrupofiscal,
                        cdestado,
                        auxtxicmsinterno,
                        auxtxicmsinterestadual,
                        auxcdusuario);
                listaMarkup.add(markup);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no Método listaMarkups()! " + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaMarkup;
    }

}
