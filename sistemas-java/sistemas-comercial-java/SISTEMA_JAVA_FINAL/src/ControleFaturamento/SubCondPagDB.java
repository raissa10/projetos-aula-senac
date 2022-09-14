package ControleFaturamento;

import ModeloFaturamento.SubCondPag;
import Principal.ConexaoFirebird;
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
public class SubCondPagDB {

    private static final String sqlBuscaTodosRegistros
            = "SELECT                         "
            + "    SUB_COND_PAG.*             "
            + "FROM                           "
            + "    SUB_COND_PAG               ";
    private static final String sqlBuscaRegistro
            = "SELECT                         "
            + "    SUB_COND_PAG.*             "
            + "FROM                           "
            + "    SUB_COND_PAG               "
            + "WHERE                          "
            + "    SUB_COND_PAG.CD_CONDICAO=? ";

    private static final String sqlbuscaSubCondPag
            = "SELECT                 "
            + "    COUNT(*) AS TOTAL  "
            + "FROM                   "
            + "    SUB_COND_PAG             "
            + "WHERE                  "
            + "    CD_CONDICAO=?  ";

    private static final String sqlBuscaParcelas
            = "SELECT sub_cond_pag.* FROM sub_cond_pag WHERE cd_condicao=? AND CD_PARCELA=?";
    private static final String sqlExcluir
            = "DELETE FROM SUB_COND_PAG WHERE CD_CONDICAO= ? AND CD_PARCELA=?";
    private static final String sqlInserir
            = "INSERT INTO SUB_COND_PAG                  "
            + "(CD_CONDICAO, CD_PARCELA, NR_DIAS_PARCELA,"
            + " CD_COBRANCA, CD_USUARIO, DT_ALT, HR_ALT, "
            + "DT_CAD, HR_CAD)                           "
            + "VALUES                                    "
            + "(?,?,?,?,?,CAST('NOW' AS DATE),           "
            + "CAST('NOW' AS TIME), CAST('NOW' AS DATE), "
            + "CAST('NOW' AS TIME));                     ";
    private static final String sqlAlterar = "UPDATE SUB_COND_PAG SET                       "
            + "    NR_DIAS_PARCELA = ?,                    "
            + "    CD_COBRANCA = ?,                        "
            + "    CD_USUARIO = ?,                         "
            + "    DT_ALT =CAST('NOW' AS DATE),            "
            + "    HR_ALT =CAST('NOW' AS TIME)             "
            + "WHERE (CD_CONDICAO = ?) AND (CD_PARCELA = ?)";
    private static final String sqlExcluirTodooGrid
            = "DELETE FROM                    "
            + "    SUB_COND_PAG               "
            + "WHERE                          "
            + "    SUB_COND_PAG.CD_CONDICAO=? ";

    public DefaultComboBoxModel getComboRegistro() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlBuscaTodosRegistros);
            while (rs.next()) {
                modelo.addElement(rs.getString("DS_SUB_COND_PAG"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboRegistro(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterar(SubCondPag subcondpag) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setInt(1, subcondpag.getNr_dias_parcela());
            pstmt.setInt(2, subcondpag.getCd_cobranca());
            pstmt.setInt(3, subcondpag.getCd_usuario());
            pstmt.setInt(4, subcondpag.getCd_condicao());
            pstmt.setInt(5, subcondpag.getCd_parcela());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. alterar(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return alterou;
    }

    public boolean inserir(SubCondPag subcondpag) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, subcondpag.getCd_condicao());
            pstmt.setInt(2, subcondpag.getCd_parcela());
            pstmt.setInt(3, subcondpag.getNr_dias_parcela());
            pstmt.setInt(4, subcondpag.getCd_cobranca());
            pstmt.setInt(5, subcondpag.getCd_usuario());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. inserir(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return inseriu;
    }

    public boolean excluir(int cd_condicao_pagamento, int cd_parcela) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_condicao_pagamento);
            pstmt.setInt(2, cd_parcela);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. excluir(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return excluiu;
    }

    public ArrayList listaSubCondPag(int cd_condicao) {
        ArrayList listaSubCondPag = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaRegistro);
            pstmt.setInt(1, cd_condicao);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int cd_codigo_condicao = rs.getInt("CD_CONDICAO");
                int cd_parcela = rs.getInt("CD_PARCELA");
                int nr_dias_parcela = rs.getInt("NR_DIAS_PARCELA");
                int cd_cobranca = rs.getInt("CD_COBRANCA");
                int cd_usuario = rs.getInt("CD_USUARIO");

                SubCondPag subcondpag = new SubCondPag(
                        cd_codigo_condicao,
                        cd_parcela,
                        nr_dias_parcela,
                        cd_cobranca,
                        cd_usuario);

                listaSubCondPag.add(subcondpag);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no ArrayList listaSubCondPag: \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return listaSubCondPag;
    }

    public boolean getSubCondPag(int codigo) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlbuscaSubCondPag);
            pstmt.setInt(1, codigo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("TOTAL") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getSubCondPag(): \n" + e.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return existe;
    }

    public int retornaNRDiasParcela(int codigoCondicaoPagamento, int codigoParcela) {
        int codigoParcelas = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaParcelas);
            pstmt.setInt(1, codigoCondicaoPagamento);
            pstmt.setInt(2, codigoParcela);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                codigoParcelas = rs.getInt("NR_DIAS_PARCELA");
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql,sqlBuscaParcelas:\n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return codigoParcelas;
    }

    public boolean retornaParcelaDaCondicao(int codigoCondicaoPagamento, int codigoParcela) {
        boolean codigoParcelas = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaParcelas);
            pstmt.setInt(1, codigoCondicaoPagamento);
            pstmt.setInt(2, codigoParcela);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                codigoParcelas = true;
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql,sqlBuscaParcelas:\n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return codigoParcelas;
    }

    public boolean excluirGridInteiro(int cd_condicao_pagamento) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlExcluirTodooGrid);
            pstmt.setInt(1, cd_condicao_pagamento);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirGridInteiro(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return excluiu;
    }
}
