package Principal;

import java.sql.*;
import javax.swing.JOptionPane;

public class ConexaoPostgreSQL {
    
    public static void main(String args[]) {
        String driver  = "org.postgresql.Driver";
        String url     = "jdbc:postgresql://tuffi.db.elephantsql.com/comdvayv";
        String usuario = "comdvayv";
        String senha   = "87Hq8BqppVuDNxtdPlDeSsDmoWylDp2e";
        Connection conexao;
        Statement statement;
        ResultSet resultset;
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            JOptionPane.showMessageDialog(null, "Conectou com o PostgreSQL!");
            statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_READ_ONLY);           
            resultset = statement.executeQuery("select * from usuario");
            String lista_dados = "";
            while (resultset.next()) {
                lista_dados = lista_dados + "Codigo .: " + resultset.getInt("cd_usuario");
                lista_dados = lista_dados + "\nNome .: " + resultset.getString("ds_usuario") + "\n";
            }
            JOptionPane.showMessageDialog(null, lista_dados);

        } catch (ClassNotFoundException Driver) {
            JOptionPane.showMessageDialog(null, "Driver não localizado: " + Driver);
        } catch (SQLException Fonte) {
            JOptionPane.showMessageDialog(null, "Deu erro na conexão com a fonte de dados: " + Fonte);
        }
    }
}
