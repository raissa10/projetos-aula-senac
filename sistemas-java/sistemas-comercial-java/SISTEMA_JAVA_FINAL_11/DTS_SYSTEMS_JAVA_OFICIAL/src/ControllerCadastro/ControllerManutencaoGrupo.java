package ControllerCadastro;

import Estrutura.ControllerManutencaoPadrao;
import ModelCadastro.ModelGrupo;
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
public class ControllerManutencaoGrupo extends ControllerManutencaoPadrao {

    private static final String sqlTodos
            = "SELECT * FROM GRUPO ORDER BY CD_GRUPO";
    private static final String sqlExcluir
            = "DELETE FROM GRUPO WHERE CD_GRUPO= ?";
    private static final String sqlInserir
            = "INSERT INTO GRUPO                "
            + "(CD_GRUPO,DS_GRUPO,              "
            + "DT_ALT, DT_CAD,                  "
            + "HR_CAD, HR_ALT, CD_USUARIO)      "
            + "VALUES                           "
            + "(?,?,                            "
            + "CAST('NOW' AS DATE),             "
            + "CAST('NOW' AS DATE),             "
            + "CAST('NOW' AS TIME),             "
            + "CAST('NOW' AS TIME),             "
            + "?)                               ";
    private static final String sqlAlterar
            = "UPDATE GRUPO  SET                     "
            + "    GRUPO.DS_GRUPO=?,                 "
            + "    GRUPO.DT_ALT=CAST('NOW' AS DATE), "
            + "    GRUPO.HR_ALT=CAST('NOW' AS TIME), "
            + "    GRUPO.CD_USUARIO=?                "
            + "WHERE                                 "
            + "    (CD_GRUPO = ? )                   ";
    private static final String sqlGrupo
            = "SELECT                "
            + "    count(*) as total "
            + "FROM                  "
            + "    GRUPO             "
            + "WHERE                 "
            + "    CD_GRUPO=?        ";

    public DefaultComboBoxModel getComboGrupo() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("ds_grupo"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboGrupo(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterarGrupo(ModelGrupo grupo) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, grupo.getDs_grupo());
            pstmt.setInt(2, grupo.getCd_usuario());
            pstmt.setInt(3, grupo.getCd_grupo());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. alterarGrupo(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean inserirGrupo(ModelGrupo grupo) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, grupo.getCd_grupo());
            pstmt.setString(2, grupo.getDs_grupo());
            pstmt.setInt(3, grupo.getCd_usuario());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. inserirGrupo(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public boolean excluirGrupo(int cd_grupo) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_grupo);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. excluirGrupo(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public ArrayList getTodos() {
        ArrayList listaGrupo = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                int auxcd_grupo = rs.getInt("cd_grupo");
                String auxnm_grupo = rs.getString("ds_grupo");
                int auxcd_usuario = rs.getInt("cd_usuario");

                ModelGrupo grupo = new ModelGrupo(
                        auxcd_grupo,
                        auxnm_grupo,
                        auxcd_usuario
                );
                listaGrupo.add(grupo);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. getTodos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaGrupo;
    }

    public boolean getGrupo(int cd_grupo) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlGrupo);
            pstmt.setInt(1, cd_grupo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("total") > 0) {
                    existe = true;
                } else {
                    existe = false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getGrupo(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }
}
