package Testes;

import Principal.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Gelvazio
 *
 */
public class Teste_Generator {
//Pegando o Valor do Generator

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT GEN_ID(GEN_CD_USUARIO, 0) FROM RDB$DATABASE");
            while (rs.next()) {
                System.out.println("Valor do Generator: " + rs.getInt("GEN_ID"));
            }
        } catch (SQLException erro) {
            System.out.println("Erro de conexão! " + erro);
        } finally {
            Conexao.closeAll(conn);
        }
    }
}
