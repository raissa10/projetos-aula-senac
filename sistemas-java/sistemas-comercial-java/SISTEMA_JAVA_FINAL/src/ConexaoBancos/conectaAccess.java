package ConexaoBancos;
import java.sql.*;
import javax.swing.*;

public class conectaAccess
{
        public static void main(String args[])
        {
              String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
              String url = "jdbc:odbc:javaNeri";
              String usuario = "";
              String senha = "";
              Connection conexao;
              Statement statement;
              ResultSet resultset;
              try 
              {
                Class.forName(driver);
                conexao = DriverManager.getConnection(url, usuario, senha);
                JOptionPane.showMessageDialog(null,"conectou legal com o Access");
                statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				     ResultSet.CONCUR_READ_ONLY);
                resultset = statement.executeQuery("select * from clientes");
                
                while(resultset.next())
                {
                    System.out.println("Codigo do cliente .: "+resultset.getInt("cli_codigo"));
                    System.out.println("Nome do cliente .: "+resultset.getString("cli_nome"));                    
                }
                
              }
            catch(ClassNotFoundException Driver) 
            {
               JOptionPane.showMessageDialog(null,"Driver não localizado: "+Driver);
            }
            catch(SQLException Fonte) 
            {
                JOptionPane.showMessageDialog(null,"Deu erro na conexão "+
                        "com a fonte de dados: "+Fonte);
            }

        }
}