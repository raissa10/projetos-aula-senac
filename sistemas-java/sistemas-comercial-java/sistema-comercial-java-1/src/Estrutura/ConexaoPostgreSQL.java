package Estrutura;

/**
 *
 * @author Gelvazio Camargo
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexaoPostgreSQL {

    private static final String DRIVER   = "org.postgresql.Driver";
    private static final String BANCO    = "";
    private static final String CONEXAO  = "jdbc:postgresql://localhost/sistemacomercial" + BANCO;
    private static final String USUARIO  = "postgres";
    private static final String SENHA    = "postgres";

    public static boolean verificagetConexao() {
        boolean verifica = false;        
        try {
            Class.forName(DRIVER);
            DriverManager.getConnection(CONEXAO, USUARIO, SENHA);
            verifica = true;
        } catch (ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Erro de driver! \n" + erro.getMessage());
            verifica = false;            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de Conexão! \n" + erro.getMessage());
            verifica = false;            
        }
        return verifica;
    }

    public static Connection getConexao() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(CONEXAO, USUARIO, SENHA);
        } catch (ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Erro de driver! \n" + erro.getMessage());
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de Conexão! \n" + erro.getMessage());
        }
        return conn;
    }

    public static void closeAll(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexão! \n " + erro.getMessage());
        }
    }
}
