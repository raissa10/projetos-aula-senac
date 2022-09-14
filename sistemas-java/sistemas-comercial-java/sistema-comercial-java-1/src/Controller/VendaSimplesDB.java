package Controller;

import Model.VendaClasse;
import Estrutura.ConexaoFirebird;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio * INSERT INTO ORCAMENTO_SIMPLES (CD_FILIAL, CD_MOVIMENTO,
 * CD_VENDE, CD_PAGTO, CD_PESSOA, DT_EMI_DOC, DT_SAI_DOC, VL_TOT_CUS_DOC,
 * VL_TOT_PRO_DOC, FG_SITUACAO, CD_USUARIO, DT_ALT, HR_ALT, DT_CAD, HR_CAD)
 * VALUES (1, 1, 1, 1, 1, '8-APR-2014', '16-APR-2014', 1, 2, 1, 1, '7-APR-2014',
 * '02:00:00', '15-APR-2014', '03:00:00');
 */
public class VendaSimplesDB {

    private static final String sqlAlterar
            = "UPDATE ORCAMENTO_SIMPLES  SET "
            + "CD_VENDE=?, "
            + "CD_PAGTO=?,"
            + "CD_PESSOA=?, "
            + "DT_SAI_DOC=CAST('NOW' AS DATE), "
            + "VL_TOT_CUS_DOC=?,"
            + "VL_TOT_PRO_DOC=?,"
            + "FG_SITUACAO=?, "
            + "CD_USUARIO=?,"
            + "CD_FILIAL=?,"
            + "DT_ALT=CAST('NOW' AS DATE), "
            + "HR_ALT=CAST('NOW' AS TIME),"
            + "CD_TIPO_DOC=? "
            + "WHERE (ORCAMENTO_SIMPLES.CD_MOVIMENTO=?)";
    private static final String sqlInserir
            = "INSERT INTO ORCAMENTO_SIMPLES (CD_FILIAL, CD_MOVIMENTO, CD_VENDE, CD_PAGTO,"
            + "CD_PESSOA, DT_EMI_DOC, DT_SAI_DOC, VL_TOT_CUS_DOC, VL_TOT_PRO_DOC,"
            + "FG_SITUACAO, CD_USUARIO, DT_ALT, HR_ALT, DT_CAD, HR_CAD,CD_TIPO_DOC)"
            + "VALUES (?, ?, ?, ?, ?,CAST('NOW' AS DATE), CAST('NOW' AS DATE), ?, ?, ?, ?,"
            + "CAST('NOW' AS DATE), CAST('NOW' AS TIME),  CAST('NOW' AS DATE), CAST('NOW' AS TIME),?);";
    private static final String sqlConsultaVenda = "SELECT (ORCAMENTO_SIMPLES.cd_movimento)as codigosql        "
            + "FROM ORCAMENTO_SIMPLES where ORCAMENTO_SIMPLES.cd_movimento=? ";

    public boolean gravarVenda(VendaClasse venda) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, venda.getCd_filial());
            pstmt.setInt(2, venda.getCd_movimento());
            pstmt.setInt(3, venda.getCd_vende());
            pstmt.setInt(4, venda.getCd_pagto());
            pstmt.setInt(5, venda.getCd_pessoa());
            pstmt.setDouble(6, venda.getVl_tot_cus_doc());
            pstmt.setDouble(7, venda.getVl_tot_pro_doc());
            pstmt.setInt(8, venda.getFg_situacao());
            pstmt.setInt(9, venda.getCd_usuario());
            pstmt.setInt(10, venda.getCd_tipo_nota());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. gravarVenda: \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return inseriu;
    }

    public boolean alterarVenda(VendaClasse venda) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setInt(1, venda.getCd_vende());
            pstmt.setInt(2, venda.getCd_pagto());
            pstmt.setInt(3, venda.getCd_pessoa());
            pstmt.setDouble(4, venda.getVl_tot_cus_doc());
            pstmt.setDouble(5, venda.getVl_tot_pro_doc());
            pstmt.setInt(6, venda.getFg_situacao());
            pstmt.setInt(7, venda.getCd_usuario());
            pstmt.setInt(8, venda.getCd_filial());
            pstmt.setInt(9, venda.getCd_tipo_nota());
            pstmt.setInt(10, venda.getCd_movimento());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. alterarVenda: \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return alterou;
    }

    public boolean getVenda(int cd_movimento) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlConsultaVenda);
            pstmt.setInt(1, cd_movimento);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("codigosql") > 0) {
                    existe = true;
                } else {
                    existe = false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getVenda: \n" + e.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return existe;
    }

}
