package Migracao;

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

public class ConexaoMigracao {

    private static final String LOCALHOST     = "Conexao.ini";//Aqruivo ini a ser lido
    private static final String DRIVER        = "org.firebirdsql.jdbc.FBDriver"; //Classe do DRIVER
    private static String CAMINHO_BANCO_DADOS = "";
    private static String CAMINHO_CONEXAO            = "";//// + CAMINHO_BANCO_DADOS; //URL de conexão
    private static final String USUARIO       = "SYSDBA"; //Usuário da base
    private static final String SENHA         = "masterkey"; //Senha da base

    public static boolean verificagetConexao() {
        boolean verifica = false;
        CAMINHO_BANCO_DADOS = leIniBancoDados();
        CAMINHO_CONEXAO = "jdbc:firebirdsql://" + CAMINHO_BANCO_DADOS;
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(CAMINHO_CONEXAO, USUARIO, SENHA);
            verifica = true;
            //Limpa o caminho do CAMINHO_BANCO_DADOS
            CAMINHO_BANCO_DADOS = "";
        } catch (ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Erro de driver! \n" + erro.getMessage());
            verifica = false;
            closeAll(conn);
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de Conexão! \n" + erro.getMessage());
            verifica = false;
            closeAll(conn);
        }
        return verifica;
    }

    public static Connection getConexao() {
        CAMINHO_BANCO_DADOS = leIniBancoDados();
        CAMINHO_CONEXAO = "jdbc:firebirdsql://" + CAMINHO_BANCO_DADOS;

        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(CAMINHO_CONEXAO, USUARIO, SENHA);
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
                CAMINHO_BANCO_DADOS = colunas[1];
                //Carrega as dicas das palavras do txt para um array
            }
            //Fecha o Leitor de texto
            leitorBuf.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler ini: " + ex.toString());
        }
        return CAMINHO_BANCO_DADOS;
    }

}

