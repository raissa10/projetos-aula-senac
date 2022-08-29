package sistema.sistema_java_ok.src.principal.conexao;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexao {

    public static void main(String args[]) {
        conectaBanco();
    }

    public static String conectaBanco(){
        String driver  = "org.postgresql.Driver";
        String url     = "jdbc:postgresql://motty.db.elephantsql.com/dfgxpned";
        String usuario = "dfgxpned";
        String senha   = "Qn0rrW9uSnJ4ZioM7CmZUKaZGXsH1hnj";

        String lista_dados = "";
        String lista_dados_json = "";

        Connection conexao;
        Statement statement;
        ResultSet resultset;
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);

            //JOptionPane.showMessageDialog(null, "Conectou com o PostgreSQL!");

            System.out.println("Conectou com o PostgreSQL!");

            statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                                ResultSet.CONCUR_READ_ONLY);

            resultset = statement.executeQuery("select * from usuario limit 1");

            while (resultset.next()) {
                int usucodigo = resultset.getInt("usucodigo");
                String usuemail = resultset.getString("usucodigo");
                String ususenha = resultset.getString("ususenha");

                lista_dados = lista_dados + "Codigo .: " + resultset.getInt("usucodigo");
                lista_dados = lista_dados + "\nE-mail .: " + resultset.getString("usuemail") + "\n";
                lista_dados = lista_dados + "\nSenha .: " + resultset.getString("ususenha") + "\n";

                lista_dados_json = lista_dados_json + "{" +
                        "\"usucodigo\":\"" + usucodigo +"\"," +
                        "\"usuemail\":\"" + usuemail +"\"," +
                        "\"ususenha\":\"" + ususenha +"\"" +
                        "}";
            }

            //JOptionPane.showMessageDialog(null, lista_dados);

            System.out.println(lista_dados);

        } catch (ClassNotFoundException Driver) {
            JOptionPane.showMessageDialog(null, "Driver não localizado: " + Driver);
        } catch (SQLException Fonte) {
            JOptionPane.showMessageDialog(null, "Deu erro na conexão com a fonte de dados: " + Fonte);
        }

        return lista_dados_json;
    }
}
