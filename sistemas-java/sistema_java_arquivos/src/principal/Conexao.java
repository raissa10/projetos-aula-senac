package principal;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexao {

    public static void main(String args[]) {
        conectaBanco();
    }

    public static String conectaBanco(){

        //#BANCO DE DADOS LOCAL
        //#spring.datasource.url= jdbc:postgresql://localhost:5432/postgres
        //#spring.datasource.username= postgres
        //#spring.datasource.password= postgres
        //
        //# BANCO DE DADOS ONLINE
        //spring.datasource.url= jdbc:postgresql://db.vdcszqvvrwdqcnjvcoxt.supabase.co:5432/postgres
        //spring.datasource.username= postgres
        //spring.datasource.password= mB9C@SywfzkJzmS
        //
        //#ELEPHANTSQL
        //#spring.datasource.url= jdbc:postgresql://motty.db.elephantsql.com:5432/dfgxpned
        //#spring.datasource.username= dfgxpned
        //#spring.datasource.password= Qn0rrW9uSnJ4ZioM7CmZUKaZGXsH1hnj


//        String driver  = "org.postgresql.Driver";
//        String url     = "jdbc:postgresql://motty.db.elephantsql.com/dfgxpned";
//        String usuario = "dfgxpned";
//        String senha   = "Qn0rrW9uSnJ4ZioM7CmZUKaZGXsH1hnj";

        // SUPABASE
        String driver  = "org.postgresql.Driver";
        String url     = "jdbc:postgresql://db.vdcszqvvrwdqcnjvcoxt.supabase.co/postgres";
        String usuario = "postgres";
        String senha   = "mB9C@SywfzkJzmS";

        String lista_dados = "";
        String lista_dados_json = "";

        Connection conexao;
        Statement statement;
        ResultSet resultset;
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);

            System.out.println("Conectou com o PostgreSQL!");

            statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                                ResultSet.CONCUR_READ_ONLY);

            resultset = statement.executeQuery("select * from countries limit 50");

            while (resultset.next()) {
                int id       = resultset.getInt("id");
                String name  = resultset.getString("name");
                String sigla = resultset.getString("iso2");

                lista_dados_json = lista_dados_json + "{" +
                        "\"id\":\"" + id +"\"," +
                        "\"name\":\"" + name +"\"," +
                        "\"sigla\":\"" + sigla +"\"" +
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
