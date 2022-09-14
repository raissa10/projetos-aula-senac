package Controller;

import Model.GrupoFiscal;
import Model.Markup;
import Estrutura.Conexao;
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
public class GrupoFiscalDB {
    /*
     private int cd_filial;
     private int cd_grupo_fiscal;
     private String ds_grupo_fiscal;
     private int cd_usuario;
     */

    private static final String sqlTodos
            = "	SELECT		                   "
            + "	    GRUPO_FISCAL.*                 "
            + "	FROM		                   "
            + "	    GRUPO_FISCAL                   "
            + "ORDER BY                            "
            + "     GRUPO_FISCAL.CD_GRUPO_FISCAL   ";
    private static final String sqlInserir
            = " INSERT INTO GRUPO_FISCAL       "
            + "(CD_FILIAL, CD_GRUPO_FISCAL,    "
            + "DS_GRUPO_FISCAL, CD_USUARIO,    "
            + "DT_CAD, DT_ALT, HR_CAD, HR_ALT) "
            + "VALUES                          "
            + "(?, ?, ?, ?,                    "
            + "CAST('NOW' AS DATE),            "
            + "CAST('NOW' AS DATE),            "
            + "CAST('NOW' AS TIME),            "
            + "CAST('NOW' AS TIME))            ";
    private static final String sqlExcluir
            = "DELETE                              "
            + "FROM                                "
            + "    GRUPO_FISCAL                    "
            + "WHERE                               "
            + "    GRUPO_FISCAL.CD_GRUPO_FISCAL= ? ";
    private static final String sqlAlterar
            = "UPDATE GRUPO_FISCAL SET         "
            + "    DS_GRUPO_FISCAL = ?,        "
            + "    CD_USUARIO = ?,             "
            + "    DT_ALT =CAST('NOW' AS DATE),"
            + "    HR_ALT = CAST('NOW' AS TIME)"
            + "WHERE (CD_FILIAL = ?)           "
            + "    AND                         "
            + "      (CD_GRUPO_FISCAL = ?);    ";

    private static final String sqlConsultaGrupoFiscal
            = "SELECT                "
            + "    COUNT(*) AS TOTAL "
            + "FROM                  "
            + "    GRUPO_FISCAL      "
            + "WHERE                 "
            + "    CD_GRUPO_FISCAL=? ";

    private static final String sqlBuscaGrupoFiscal
            = "SELECT                "
            + "    GRUPO_FISCAL.*    "
            + "FROM                  "
            + "    GRUPO_FISCAL      "
            + "WHERE                 "
            + "    CD_GRUPO_FISCAL=? ";

    public DefaultComboBoxModel getComboGrupoFiscal() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("DS_grupo_fiscal"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboGrupoFiscal(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterar(GrupoFiscal grupofiscal) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, grupofiscal.getDs_grupo_fiscal());
            pstmt.setInt(2, grupofiscal.getCd_usuario());
            pstmt.setInt(3, grupofiscal.getCd_filial());
            pstmt.setInt(4, grupofiscal.getCd_grupo_fiscal());

            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. alterar(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean excluir(int cdgrupofiscal) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cdgrupofiscal);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluir(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean inserir(GrupoFiscal grupofiscal) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, grupofiscal.getCd_filial());
            pstmt.setInt(2, grupofiscal.getCd_grupo_fiscal());
            pstmt.setString(3, grupofiscal.getDs_grupo_fiscal());
            pstmt.setInt(4, grupofiscal.getCd_usuario());

            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. inserir(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public ArrayList getTodos() {
        ArrayList listaGrupoFiscal = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);

            while (rs.next()) {
                int cdfilial = rs.getInt("CD_FILIAL");
                int cdgrupofiscal = rs.getInt("CD_GRUPO_FISCAL");
                String ds_grupofiscal = rs.getString("DS_GRUPO_FISCAL");
                int cdusuario = rs.getInt("CD_USUARIO");

                GrupoFiscal grupofiscal = new GrupoFiscal(
                        cdfilial,
                        cdgrupofiscal,
                        ds_grupofiscal,
                        cdusuario
                );
                listaGrupoFiscal.add(grupofiscal);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getTodos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaGrupoFiscal;
    }

    public boolean getGrupoFiscal(int cd_grupofiscal) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlConsultaGrupoFiscal);
            pstmt.setInt(1, cd_grupofiscal);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("TOTAL") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getGrupoFiscal: \n" + e.getMessage());
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
            rs = stmt.executeQuery("SELECT GEN_ID(CD_GRUPO_FISCAL, 1) FROM RDB$DATABASE");
            while (rs.next()) {
                codigoGenerator = rs.getInt("GEN_ID");
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de conexão! \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return codigoGenerator;
    }

    public ArrayList listarGruposFiscais(int codigo) {
        ArrayList listaGruposFiscais = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaGrupoFiscal);
            pstmt.setInt(1, codigo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int cdfilial = rs.getInt("CD_FILIAL");
                int cdgrupofiscal = rs.getInt("CD_GRUPO_FISCAL");
                String ds_grupofiscal = rs.getString("DS_GRUPO_FISCAL");
                int cdusuario = rs.getInt("CD_USUARIO");

                GrupoFiscal grupofiscal = new GrupoFiscal(
                        cdfilial,
                        cdgrupofiscal,
                        ds_grupofiscal,
                        cdusuario
                );
                listaGruposFiscais.add(grupofiscal);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no Método listarGruposFiscais()! " + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaGruposFiscais;
    }

}
