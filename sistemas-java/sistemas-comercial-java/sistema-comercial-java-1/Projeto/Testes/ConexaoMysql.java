package Testes;

/**
 *
 * @author Gelvazio Camargo
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMysql {

    //Dados da Conexao MYSql
    //Usuario=admin
    //Senha=admin
    private static String driver = "com.mysql.jdbc.Driver"; //Classe do driver
    private static String banco = "livraria"; //Nome da base de dados
    private static String host = "localhost"; //Servidor da base de dados
    private static String str_conn = "jdbc:mysql://" + host + ":3050/" + banco; //URL de conexão
    private static String usuario = "root"; //Usuário da base
    private static String senha = ""; //Senha da base

    public static Connection getConexao() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(str_conn, usuario, senha);
        } catch (ClassNotFoundException erro) {
            System.out.println("Erro de driver! " + erro.getMessage());
        } catch (SQLException erro) {
            System.out.println("Erro de Conexão! " + erro.getMessage());
        }
        return conn;
    }

    public static void closeAll(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao fechar conexão! " + erro.getMessage());
        }
    }
}
