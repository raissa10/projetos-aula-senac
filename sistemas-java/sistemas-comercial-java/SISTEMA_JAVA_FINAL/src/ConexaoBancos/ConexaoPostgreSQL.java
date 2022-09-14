package ConexaoBancos;

import java.sql.*;
import javax.swing.*;

public class ConexaoPostgreSQL {

    public static void main(String args[]) {
        String driver = "org.postgresql.Driver";
        //String url = "jdbc:postgresql://localhost/estoque";
        String url = "jdbc:postgresql://localhost/sistemacomercial";
        String usuario = "postgres";
        String senha = "postgres";
        Connection conexao;
        Statement statement;
        ResultSet resultset;
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            JOptionPane.showMessageDialog(null, "conectou legal com o PostgreSQL");
            statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_READ_ONLY);
            //resultset = statement.executeQuery("select * from clientes");
            resultset = statement.executeQuery("select * from cadastro.tbpessoa");
            String lista_dados = "";
            while (resultset.next()) {
                lista_dados = lista_dados + "Codigo do cliente .: " + resultset.getInt("cd_pessoa");
                lista_dados = lista_dados + "\nNome do cliente .: " + resultset.getString("nm_pessoa") + "\n";
            }
            JOptionPane.showMessageDialog(null, lista_dados);

        } catch (ClassNotFoundException Driver) {
            JOptionPane.showMessageDialog(null, "Driver nao localizado: " + Driver);
        } catch (SQLException Fonte) {
            JOptionPane.showMessageDialog(null, "Deu erro na conexao com a fonte de dados: " + Fonte);
        }

    }
}
