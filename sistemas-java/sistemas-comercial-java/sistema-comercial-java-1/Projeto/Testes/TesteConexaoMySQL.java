package Testes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Marciel
 */
public class TesteConexaoMySQL {

    public void MetodoTestaConexaMySQL() {
        JOptionPane.showMessageDialog(null, "Para usar esta opção precisa iniciar o Wamp!");
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Testar a Conexão?");
        if (resposta == JOptionPane.YES_OPTION) {
            ConexaoMysql Conexao = new ConexaoMysql();
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                conn = Conexao.getConexao();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM estado");
                while (rs.next()) {
                    System.out.println("Sigla: " + rs.getString("sc"));
                }
            } catch (SQLException erro) {
                System.out.println("Erro de conexão! ");
            } finally {
                Conexao.closeAll(conn);
            }
        }
    }

    public static void main(String[] args) {
        ConexaoMysql Conexao = new ConexaoMysql();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM estado");
            while (rs.next()) {
                System.out.println("Sigla: " + rs.getString("sc"));
            }
        } catch (SQLException erro) {
            System.out.println("Erro de conexão! ");
        } finally {
            Conexao.closeAll(conn);
        }
    }
}
