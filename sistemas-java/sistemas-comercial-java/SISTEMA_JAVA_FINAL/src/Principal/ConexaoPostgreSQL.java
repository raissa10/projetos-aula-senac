package Principal;

/**
 *
 * @author Gelvazio Camargo
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexaoPostgreSQL {

    private static final String LOCALHOST = "Conexao.ini";//Aqruivo ini a ser lido
    private static final String driver = "org.postgresql.Driver"; //Classe do driver
    private static final String banco = "";
    private static final String str_conn = "jdbc:postgresql://localhost/sistemacomercial";//// + banco; //URL de conexão
    private static final String usuario = "postgres"; //Usuário da base
    private static final String senha = "postgres"; //Senha da base

    public static boolean verificagetConexao() {
        boolean verifica = false;
        //banco = leIniBancoDados();
        //str_conn = "jdbc:postgresql://localhost/sistemacomercial";
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(str_conn, usuario, senha);
            verifica = true;
        } catch (ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Erro de driver! \n" + erro.getMessage());
            verifica = false;
            Conexao.closeAll(conn);
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de Conexão! \n" + erro.getMessage());
            verifica = false;
            Conexao.closeAll(conn);
        }
        return verifica;
    }

    public static Connection getConexao() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(str_conn, usuario, senha);
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
        } finally {

        }
    }
}
