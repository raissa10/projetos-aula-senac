package ControllerCadastro;

import Estrutura.ControllerManutencaoPadrao;
import ModelCadastro.ModelSubGrupo;
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
public class ControllerManutencaoSubGrupo extends ControllerManutencaoPadrao {

    private static final String sqlTodos
            = "SELECT                     "
            + "    SUB_GRUPO.CD_GRUPO,    "
            + "    SUB_GRUPO.CD_SUB_GRUPO,"
            + "    SUB_GRUPO.CD_USUARIO,  "
            + "    SUB_GRUPO.DS_SUB_GRUPO "
            + "FROM                       "
            + "    SUB_GRUPO              "
            + "ORDER BY                   "
            + "    SUB_GRUPO.CD_SUB_GRUPO ";
    private static final String sqlInserir
            = "INSERT INTO SUB_GRUPO                       "
            + "(CD_GRUPO, CD_SUB_GRUPO, DS_SUB_GRUPO,      "
            + "CD_USUARIO, DT_ALT, HR_ALT, DT_CAD, HR_CAD) "
            + "VALUES                                      "
            + "(?, ?,?, ?,                                 "
            + " CAST('NOW' AS DATE),CAST('NOW' AS TIME),   "
            + " CAST('NOW' AS DATE),CAST('NOW' AS TIME));  ";

    private static final String sqlExcluir
            = "DELETE FROM                  "
            + "    SUB_GRUPO                "
            + "WHERE                        "
            + "    SUB_GRUPO.CD_SUB_GRUPO=? "
            + "    AND                      "
            + "    SUB_GRUPO.CD_GRUPO=?     ";
    private static final String sqlExcluirTodooGrupo
            = "DELETE FROM                  "
            + "    SUB_GRUPO                "
            + "WHERE                        "
            + "    SUB_GRUPO.CD_GRUPO=?     ";
    private static final String sqlExcluirSubGrupoGrid
            = "DELETE FROM                  "
            + "    SUB_GRUPO                "
            + "WHERE                        "
            + "    SUB_GRUPO.SUB_GRUPO NOT  "
            + "    IN (?);                  ";
    private static final String sqlAlterar
            = "UPDATE SUB_GRUPO SET                        "
            + "    DS_SUB_GRUPO = ?,                       "
            + "    CD_USUARIO = ?,                         "
            + "    DT_ALT = CAST('NOW' AS DATE),           "
            + "    HR_ALT = CAST('NOW' AS TIME)            "
            + "WHERE (CD_GRUPO = ?) AND (CD_SUB_GRUPO = ?);";

    private static final String sqlsubgrupo
            = "SELECT                 "
            + "    COUNT(*) AS TOTAL  "
            + "FROM                   "
            + "    SUB_GRUPO          "
            + "WHERE                  "
            + "    CD_GRUPO=?         "
            + "    AND                "
            + "    SUB_GRUPO.CD_SUB_GRUPO=?";
    private static final String sqlsubgrupoPeloGrupo
            = "SELECT                 "
            + "    COUNT(*) AS TOTAL  "
            + "FROM                   "
            + "    SUB_GRUPO          "
            + "WHERE                  "
            + "    CD_GRUPO=?         ";

    public DefaultComboBoxModel getComboSubGrupo() {
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
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboSubGrupo(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterarSubGrupo(ModelSubGrupo subgrupo) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, subgrupo.getDs_sub_grupo());
            pstmt.setInt(2, subgrupo.getCd_usuario());
            pstmt.setInt(3, subgrupo.getCd_grupo());
            pstmt.setInt(4, subgrupo.getCd_sub_grupo());

            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. AlterarSubGrupo(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean excluirSubGrupo(int cdgrupo, int cdsubgrupo) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cdsubgrupo);
            pstmt.setInt(2, cdgrupo);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. ExcluirSubGrupo(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean excluirSubGrupoInteiro(int cdgrupo) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluirTodooGrupo);
            pstmt.setInt(1, cdgrupo);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirSubGrupoInteiro(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean excluirSubGrupoForaGrid(int cdsubgrupo) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluirSubGrupoGrid);
            pstmt.setInt(1, cdsubgrupo);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirSubGrupoForaGrid(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean inserirSubGrupo(ModelSubGrupo subgrupo) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, subgrupo.getCd_grupo());
            pstmt.setInt(2, subgrupo.getCd_sub_grupo());
            pstmt.setString(3, subgrupo.getDs_sub_grupo());
            pstmt.setInt(4, subgrupo.getCd_usuario());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. InserirSubGrupo():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public ArrayList getTodos() {

        ArrayList listaSubGrupo = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                int auxcdgrupo = rs.getInt("cd_grupo");
                int auxcdsubgrupo = rs.getInt("cd_sub_grupo");
                int auxcdusuario = rs.getInt("cd_usuario");
                String auxdssubgrupo = rs.getString("ds_sub_grupo");

                ModelSubGrupo subgrupo = new ModelSubGrupo(
                        auxcdgrupo,
                        auxcdsubgrupo,
                        auxcdusuario,
                        auxdssubgrupo);
                listaSubGrupo.add(subgrupo);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getTodos():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaSubGrupo;
    }

    public boolean getSubGrupo(int cdgrupo, int cd_sub_grupo) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlsubgrupo);
            pstmt.setInt(1, cdgrupo);
            pstmt.setInt(2, cd_sub_grupo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("TOTAL") > 0) {
                    existe = true;
                } else {
                    existe = false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getsubgrupo(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean getSubGrupoPeloGrupo(int cdgrupo) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlsubgrupoPeloGrupo);
            pstmt.setInt(1, cdgrupo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("TOTAL") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getsubgrupo(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }
}
