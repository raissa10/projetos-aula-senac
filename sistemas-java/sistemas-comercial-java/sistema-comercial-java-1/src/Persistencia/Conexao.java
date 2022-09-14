package Persistencia;

/**
 *
 * @author Gelvazio Camargo
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao {

    private static final String LOCALHOST = "Conexao.ini";//Aqruivo ini a ser lido
    private static final String driver = "org.postgresql.Driver"; //Classe do driver
    private static String banco = "";
    private static String str_conn = "";//// + banco; //URL de conexão
    private static final String usuario = "postgres"; //Usuário da base
    private static final String senha = "postgres"; //Senha da base

    public static boolean verificagetConexao() {
        boolean verifica = false;
       // banco = leIniBancoDados();
        str_conn = "jdbc:postgresql://localhost/sistemacomercial";
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(str_conn, usuario, senha);
            verifica = true;
            //Limpa o caminho do banco
            banco = "";
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
        //banco = leIniBancoDados();

        str_conn = "jdbc:postgresql://localhost/sistemacomercial";
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

    public static String leIniBancoDados() {
        try {
            File Arquivo = new File(LOCALHOST); //lê o arquivo Txt            
            FileReader leitor = new FileReader(Arquivo);
            BufferedReader leitorBuf = new BufferedReader(leitor);
            String linha = null;
            while ((linha = leitorBuf.readLine()) != null) {
                //lê a linha contendo a palavra e a dica, 
                //sabendo q as mesmas sao separadas pelo =
                String colunas[] = linha.split("=");
                //Carrega as palavras do txt para um array
                banco = colunas[1];
                //Carrega as dicas das palavras do txt para um array
            }
            //Fecha o Leitor de texto
            leitorBuf.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler ini: " + ex.toString());
        }
        return banco;
    }

}
