package Principal;

/**
 *
 * @author Gelvazio Camargo
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao1 {

    private static final String DRIVER      = "org.postgresql.Driver"; //Classe do DRIVER
    private static final String STR_CONEXAO = "jdbc:postgresql://tuffi.db.elephantsql.com/comdvayv";
    private static final String USUARIO     = "comdvayv"; //Usuário da base
    private static final String SENHA       = "87Hq8BqppVuDNxtdPlDeSsDmoWylDp2e"; //Senha da base

    public static boolean validaConexao() {
        boolean verifica = true;
        try {
            Class.forName(DRIVER);
            DriverManager.getConnection(STR_CONEXAO, USUARIO, SENHA);            
        } catch (ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Erro de driver!\nContate o suporte.\n" + erro.getMessage());
            verifica = false;        
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de Conexão!\nContate o suporte.\n" + erro.getMessage());
            verifica = false;            
        }
        return verifica;
    }

    public static Connection getConexao() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(STR_CONEXAO, USUARIO, SENHA);
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
