package ControleCadastro;

import Principal.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import ModelCadastro.Estado;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author Gelvazio Camargo
 */
public class ControllerManutencaoEstado extends ControllerManutencaoPadrao{

    private static final String sqlTodos = "SELECT * FROM cadastro.tbestado order by CD_ESTADO";
    private static final String sqlInserir
            = "INSERT INTO CADASTRO.TBESTADO (CD_ESTADO, DS_ESTADO,"
            + " CD_IBGE, CD_FILIAL, CD_USUARIO, DT_ALT, HR_ALT, DT_CAD, HR_CAD)"
            + "VALUES "
            + "(?,?,?,?,?,"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS TIME),"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS TIME))";
    private static final String sqlExcluir = "DELETE FROM TBESTADO WHERE CD_ESTADO = ?";
    private static final String sqlAlterar
            = "UPDATE CADASTRO.TBESTADO SET "
            + "DS_ESTADO = ?,"
            + "CD_USUARIO =?,"
            + "CD_IBGE = ?,"
            + "CD_FILIAL=?,"
            + "DT_ALT = CAST('NOW' AS DATE),"
            + "HR_ALT = CAST('NOW' AS TIME)"
            + " WHERE (CD_ESTADO = ?)";
    private static final String sqlEstado = "SELECT count(*) as total FROM cadastro.tbestado WHERE CD_ESTADO  LIKE UPPER(?)";

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
    
}
