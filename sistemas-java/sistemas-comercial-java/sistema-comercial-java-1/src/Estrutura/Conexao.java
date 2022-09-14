package Estrutura;

/**
 * Classe Principal de conexões
 * @author Gelvazio Camargo
 * @since 20/06/2021
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

    // ConexaoDBHeroku
    //    const HOST = 'ec2-18-204-101-137.compute-1.amazonaws.com';
    //    const PORT = '5432';
    //    const DBNAME = 'd3gcuuncl1dsrp';
    //    const USER = 'zherahydsltxbg';
    //    const PASS = '8c3c7c4e916e003529d4ac1f5704e6801a5e05be7410b4a0bec9bf0fb28a9dd1';


    private static final String LOCALHOST = "Conexao.ini";//Aqruivo ini a ser lido
    private static final String driver = "org.firebirdsql.jdbc.FBDriver"; //Classe do driver
    private static final String banco = "";
    private static final String str_conn = "";//// + banco; //URL de conexão
    private static final String usuario = "SYSDBA"; //Usuário da base
    private static final String senha = "masterkey"; //Senha da base

    public static boolean testaConexao() {
        return true;
    }
    
    
    public static boolean testaTodasConexao() {
        boolean verifica = false;
//        banco = leIniBancoDados();
//        str_conn = "jdbc:firebirdsql://" + banco;
//        Connection conn = null;
//        try {
//            Class.forName(driver);
//            conn = DriverManager.getConnection(str_conn, usuario, senha);
//            verifica = true;
//            //Limpa o caminho do banco
//            banco = "";
//        } catch (ClassNotFoundException erro) {
//            JOptionPane.showMessageDialog(null, "Erro de driver! \n" + erro.getMessage());
//            verifica = false;
//            ConexaoFirebird.closeAll(conn);
//        } catch (SQLException erro) {
//            JOptionPane.showMessageDialog(null, "Erro de Conexão! \n" + erro.getMessage());
//            verifica = false;
//            ConexaoFirebird.closeAll(conn);
//        }
        return verifica;
    }

    public static Connection getConexao() {
        Connection conn = null;
//        banco = leIniBancoDados();
//
//        str_conn = "jdbc:firebirdsql://" + banco;
//
//        try {
//            Class.forName(driver);
//            conn = DriverManager.getConnection(str_conn, usuario, senha);
//        } catch (ClassNotFoundException erro) {
//            JOptionPane.showMessageDialog(null, "Erro de driver! \n" + erro.getMessage());
//        } catch (SQLException erro) {
//            JOptionPane.showMessageDialog(null, "Erro de Conexão! \n" + erro.getMessage());
//        }
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
