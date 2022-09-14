package Testes;

import Principal.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
// @author Gelvazio

public class TesteConexao {

    public void MetotoTestaConexao() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM ESTADO where estado.cd_estado='SC'");
            while (rs.next()) {
                JOptionPane.showMessageDialog(null, "Conexão bem sucedida!");
                JOptionPane.showMessageDialog(null,
                        "Código do Estado:" + rs.getString("cd_estado")
                        + "\n Nome do Estado:" + rs.getString("ds_estado")
                        + "\n Usuario do Estado: " + rs.getString("cd_usuario")
                        + "\n Código IBGE do Estado: " + rs.getString("cd_ibge"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de conexão!!! Erro abaixo! \n" + erro);
        } finally {
            Conexao.closeAll(conn);
        }
    }

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM ESTADO where estado.cd_estado='SC'");
            while (rs.next()) {
                JOptionPane.showMessageDialog(null, "Conexão bem sucedida!");
                JOptionPane.showMessageDialog(null,
                        "Código do Estado:" + rs.getString("cd_estado")
                        + "\n Nome do Estado:" + rs.getString("ds_estado")
                        + "\n Usuario do Estado: " + rs.getString("cd_usuario")
                        + "\n Código IBGE do Estado: " + rs.getString("cd_ibge"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de conexão!!! Erro abaixo! \n" + erro);
        } finally {
            Conexao.closeAll(conn);
        }
    }

}
