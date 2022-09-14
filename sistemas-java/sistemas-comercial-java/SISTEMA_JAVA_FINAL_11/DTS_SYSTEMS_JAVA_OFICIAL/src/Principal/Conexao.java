package Principal;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Conexao {

    private static final String LOCALHOST = "Conexao.ini";//Aqruivo ini a ser lido
    //private static final String driver = "org.firebirdsql.jdbc.FBDriver"; //Classe do driver
    //private static String banco = "";
    //private static String str_conn = "";//// + banco; //URL de conexao
    //private static final String usuario = "SYSDBA"; //Usuario da base
    //private static final String senha = "masterkey"; //Senha da base
    private static final String TIPO_BANCO = "POSTGRES_SQL"; //Senha da base

    public static boolean testaConexao() {
        boolean verifica = false;
        if (TIPO_BANCO.equals("POSTGRES_SQL")) {
            String database = "d4ad3k4iqgv3p0";
            String driver   = "org.postgresql.Driver";
            String url      = "jdbc:postgresql://ec2-34-239-241-121.compute-1.amazonaws.com/" + database;
            String usuario  = "fvloawfjpyhmfy";
            String senha    = "7f01459347a2b41ef74918675c0f4d7f65c9cb52157886682ed8184725e29928";

            Connection conexao;
            Statement statement;
            ResultSet resultset;
            try {
                Class.forName(driver);
                conexao = DriverManager.getConnection(url, usuario, senha);

                System.out.println("Conectou legal com o PostgreSQL");

                statement = conexao.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
                resultset = statement.executeQuery("select * from usuario");
                String lista_dados = "";
                verifica = true;
                while (resultset.next()) {
                    lista_dados = lista_dados + "Usuario .: " + resultset.getInt("usucodigo") + " - " + resultset.getString("usunome");
                    lista_dados = lista_dados + "\nSenha .: " + resultset.getString("ususenha") + "\n";
                }

                System.out.println(lista_dados);

            } catch (ClassNotFoundException Driver) {
                JOptionPane.showMessageDialog(null, "Driver nao localizado: " + Driver);
            } catch (SQLException Fonte) {
                JOptionPane.showMessageDialog(null, "Deu erro na conexao com a fonte de dados: " + Fonte);
            }
        } else {
            String driver   = "org.firebirdsql.jdbc.FBDriver"; //Classe do driver
            String banco    = "";
            String str_conn = "";//// + banco; //URL de conexao
            String usuario  = "SYSDBA"; //Usuario da base
            String senha    = "masterkey"; //Senha da base

            banco = leIniBancoDados();

            str_conn = "jdbc:firebirdsql://" + banco;
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
                //ConexaoFirebird.closeAll(conn);
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro de Conexao! \n" + erro.getMessage());
                verifica = false;
            }
        }
        return verifica;
    }

    public static Connection getConexao() {
        if (TIPO_BANCO.equals("POSTGRES_SQL")) {
            return getConexaoPostgres();
        }

        return getConexaoFirebird();
    }

    private static Connection getConexaoFirebird() {
        Connection conn = null;
        String driver   = "org.firebirdsql.jdbc.FBDriver"; //Classe do driver
        String banco    = "";
        String str_conn = "";//// + banco; //URL de conexao
        String usuario  = "SYSDBA"; //Usuario da base
        String senha    = "masterkey"; //Senha da base

        banco = leIniBancoDados();

        str_conn = "jdbc:firebirdsql://" + banco;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(str_conn, usuario, senha);
        } catch (ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Erro de driver! \n" + erro.getMessage());
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de Conexao! \n" + erro.getMessage());
        }
        return conn;
    }

    private static Connection getConexaoPostgres() {

        String database = "d4ad3k4iqgv3p0";
        String driver   = "org.postgresql.Driver";
        String url      = "jdbc:postgresql://ec2-34-239-241-121.compute-1.amazonaws.com/" + database;
        String usuario  = "fvloawfjpyhmfy";
        String senha    = "7f01459347a2b41ef74918675c0f4d7f65c9cb52157886682ed8184725e29928";

        Connection conexao = null;
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            return conexao;
        } catch (ClassNotFoundException Driver) {
            JOptionPane.showMessageDialog(null, "Driver nao localizado: " + Driver);
        } catch (SQLException Fonte) {
            JOptionPane.showMessageDialog(null, "Deu erro na conexao com a fonte de dados: " + Fonte);
        }
        return conexao;
    }

    public static void closeAll(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao fechar conexao! \n " + erro.getMessage());
        } finally {

        }
    }

    public static String leIniBancoDados() {
        String banco = "";
        try {
            File Arquivo = new File(LOCALHOST); //le o arquivo Txt
            FileReader leitor = new FileReader(Arquivo);
            BufferedReader leitorBuf = new BufferedReader(leitor);
            String linha = null;
            while ((linha = leitorBuf.readLine()) != null) {
                // le a linha contendo a palavra e a dica,
                // sabendo q as mesmas sao separadas pelo =
                String colunas[] = linha.split("=");
                // Carrega as palavras do txt para um array
                banco = colunas[1];
                // Carrega as dicas das palavras do txt para um array
            }
            //Fecha o Leitor de texto
            leitorBuf.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler ini: " + ex.toString());
        }
        return banco;
    }
}
