package ControleFaturamento;

import ModeloFaturamento.CondicaoPagamento;
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
public class CondicaoPagamentoDB {

    /**
     *
     * @author Gelvazio Camargo
     */
    private static final String sqlTodos
            = "	SELECT		                "
            + "	    COND_PAG.CD_COND    ,       "
            + "     COND_PAG.DS_COND 	 	"
            + "	FROM		                "
            + "	    COND_PAG                    "
            + "     order by COND_PAG.CD_COND   ";
    private static final String sqlInserir
            = "INSERT INTO COND_PAG (CD_COND,   "
            + "DS_COND, CD_USUARIO, DT_ALT,     "
            + "HR_ALT, DT_CAD, HR_CAD)          "
            + "VALUES                           "
            + "(?,?,?,                          "
            + "CAST('NOW' AS DATE),             "
            + "CAST('NOW' AS TIME),             "
            + "CAST('NOW' AS DATE),             "
            + "CAST('NOW' AS TIME))             ";

    private static final String sqlExcluir
            = "DELETE        "
            + "FROM          "
            + "    COND_PAG  "
            + "WHERE         "
            + "    CD_COND= ?";

    private static final String sqlAlterar
            = "UPDATE COND_PAG  SET            "
            + "    DS_COND =?,                 "
            + "    CD_USUARIO = ?,             "
            + "    DT_ALT =CAST('NOW' AS DATE),"
            + "    HR_ALT =CAST('NOW' AS TIME) "
            + "WHERE (CD_COND = ?);            ";
    private static final String sqlBuscaRegistro
            = "SELECT                "
            + "    COND_PAG.*        "
            + "FROM                  "
            + "    COND_PAG          "
            + "WHERE                 "
            + "    COND_PAG.CD_COND=?";

    private static final String sqlConsultaCondicao
            = "SELECT                            "
            + "(COND_PAG.CD_COND)as codigosql    "
            + "FROM                              "
            + "    COND_PAG                      "
            + "where                             "
            + "    COND_PAG.CD_COND=?            ";

    public DefaultComboBoxModel getComboCondPag() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("DS_COND"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboCondPag(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return modelo;
    }

    public boolean getCondicaoPagamento(int cd_cond) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlConsultaCondicao);
            pstmt.setInt(1, cd_cond);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("codigosql") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getCondicaoPagamento: \n" + e.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return existe;
    }

    public boolean alterar(CondicaoPagamento condicaopagamento) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, condicaopagamento.getDs_cond());
            pstmt.setInt(2, condicaopagamento.getCd_usuario());
            pstmt.setInt(3, condicaopagamento.getCd_cond());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. alterar(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return alterou;
    }

    public boolean excluir(int cd_cond) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_cond);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluir(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return excluiu;
    }

    public boolean inserir(CondicaoPagamento condicaopagamento) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, condicaopagamento.getCd_cond());
            pstmt.setString(2, condicaopagamento.getDs_cond());
            pstmt.setInt(3, condicaopagamento.getCd_usuario());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. inserir(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return inseriu;
    }

    public ArrayList getTodos() {
        ArrayList listaCondicaoPagamento = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                int cd_cond = rs.getInt("CD_COND");
                String ds_cond = rs.getString("DS_COND");
                int cd_usuario = rs.getInt("CD_USUARIO");

                CondicaoPagamento condicaopagamento = new CondicaoPagamento(
                        cd_cond,
                        ds_cond,
                        cd_usuario
                );
                listaCondicaoPagamento.add(condicaopagamento);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getTodos(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return listaCondicaoPagamento;
    }

    public ArrayList listaCondicaoPagamento(int cd_condicao) {
        ArrayList listaCondicaoPagamento = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaRegistro);
            pstmt.setInt(1, cd_condicao);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int cd_cond = rs.getInt("CD_COND");
                String ds_cond = rs.getString("ds_cond");
                int cd_usuario = rs.getInt("CD_USUARIO");

                CondicaoPagamento condicaopagamento = new CondicaoPagamento(
                        cd_cond,
                        ds_cond,
                        cd_usuario
                );
                listaCondicaoPagamento.add(condicaopagamento);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no ArrayList listaCondicaoPagamento: \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return listaCondicaoPagamento;
    }

    public int ValidaCodigoGenerator() {
        int codigoGenerator = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT GEN_ID(CD_CONDICAO_PAGAMENTO, 1) FROM RDB$DATABASE");
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
            ConexaoFirebird.closeAll(conn);
        }
        return codigoGenerator;
    }

}
