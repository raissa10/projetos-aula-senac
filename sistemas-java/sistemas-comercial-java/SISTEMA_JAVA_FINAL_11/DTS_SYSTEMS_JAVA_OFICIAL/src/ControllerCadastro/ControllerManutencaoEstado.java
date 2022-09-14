package ControllerCadastro;

import Estrutura.ControllerManutencaoPadrao;
import ModelCadastro.ModelEstado;
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
 * @author Gelvazio Camargo
 */
public class ControllerManutencaoEstado extends ControllerManutencaoPadrao {

    private static final String sqlTodos = "SELECT * FROM estado order by CD_ESTADO";
    private static final String sqlInserir
            = "INSERT INTO ESTADO (CD_ESTADO, DS_ESTADO,"
            + " CD_IBGE, CD_FILIAL, CD_USUARIO, DT_ALT, HR_ALT, DT_CAD, HR_CAD)"
            + "VALUES "
            + "(?,?,?,?,?,"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS TIME),"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS TIME))";
    private static final String sqlExcluir = "DELETE FROM ESTADO WHERE CD_ESTADO = ?";
    private static final String sqlAlterar
            = "UPDATE ESTADO SET "
            + "DS_ESTADO = ?,"
            + "CD_USUARIO =?,"
            + "CD_IBGE = ?,"
            + "CD_FILIAL=?,"
            + "DT_ALT = CAST('NOW' AS DATE),"
            + "HR_ALT = CAST('NOW' AS TIME)"
            + " WHERE (CD_ESTADO = ?)";
    private static final String sqlEstado = "SELECT count(*) as total FROM estado WHERE CD_ESTADO  LIKE UPPER(?)";

    public DefaultComboBoxModel getComboEstado() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("CD_ESTADO"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboEstado(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public DefaultComboBoxModel getComboboxPessoaEstado() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("ds_estado"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboboxPessoaEstado(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterarEstado(ModelEstado estado) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, estado.getDs_estado());
            pstmt.setInt(2, estado.getCd_usuario());
            pstmt.setInt(3, estado.getCd_ibge());
            pstmt.setInt(4, estado.getCd_filial());
            pstmt.setString(5, estado.getCd_estado());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. alterarEstado(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean excluirEstado(String CD_ESTADO) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setString(1, CD_ESTADO);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirEstado(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean inserirEstado(ModelEstado estado) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setString(1, estado.getCd_estado());
            pstmt.setString(2, estado.getDs_estado());
            pstmt.setInt(3, estado.getCd_ibge());
            pstmt.setInt(4, estado.getCd_filial());
            pstmt.setInt(5, estado.getCd_usuario());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. inserirEstado():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public ArrayList getTodos() {
        ArrayList listaEstado = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                String auxCd_estado = rs.getString("cd_estado");
                String auxDs_estado = rs.getString("ds_estado");
                int auxCd_ibge = rs.getInt("cd_ibge");
                int auxcd_filial = rs.getInt("cd_filial");
                int auxcd_usuario = rs.getInt("cd_usuario");
                ModelEstado estado = new ModelEstado(
                        auxCd_estado,
                        auxDs_estado,
                        auxCd_ibge,
                        auxcd_filial,
                        auxcd_usuario);
                listaEstado.add(estado);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getTodos():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaEstado;
    }

    public boolean getEstado(String CD_ESTADO) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlEstado);
            pstmt.setString(1, CD_ESTADO);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getEstado(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean getEstadoPessoa(String CD_ESTADO) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlEstado);
            pstmt.setString(1, CD_ESTADO);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getEstadoPessoa(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }
}
