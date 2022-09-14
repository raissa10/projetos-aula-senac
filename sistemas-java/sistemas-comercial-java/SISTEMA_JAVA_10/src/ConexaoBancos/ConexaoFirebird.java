/*VÍDEO AULAS PROF NERI NEITZKE-www.informaticon.com.br-videoaulasneri@gmail.com
Ninguém está autorizado a vender essas vídeo aulas a não ser a 
INFORMATICON ou o próprio professor Neri, se outro vender, denuncie*/
package ConexaoBancos;
import java.sql.*;
import javax.swing.*;
public class ConexaoFirebird
{
        public static void main(String args[])
        {
              String driver = "org.firebirdsql.jdbc.FBDriver";
              String url = "jdbc:firebirdsql:localhost/3050:D:/videoaulas/java SE II/netbeans6/aula_neri/banco_dados/NERIFIREBIRD.FDB";
              String usuario = "SYSDBA";
              String senha = "masterkey";
              Connection conexao;
              Statement statement;
              ResultSet resultset;
              try 
              {
                Class.forName(driver);
                conexao = DriverManager.getConnection(url, usuario, senha);
                JOptionPane.showMessageDialog(null,"conectou legal com o Firebird");
                statement = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				     ResultSet.CONCUR_READ_ONLY);
                resultset = statement.executeQuery("select * from clientes");
/*VÍDEO AULAS PROF NERI NEITZKE-www.informaticon.com.br-videoaulasneri@gmail.com
Ninguém está autorizado a vender essas vídeo aulas a não ser a 
INFORMATICON ou o próprio professor Neri, se outro vender, denuncie*/
                String lista_dados="";
                while(resultset.next())
                {
                    lista_dados = lista_dados + "Codigo do cliente .: "+resultset.getInt("cli_codigo");
                    lista_dados = lista_dados + "\nNome do cliente .: "+resultset.getString("cli_nome")+"\n";                    
                }
                JOptionPane.showMessageDialog(null, lista_dados);
                
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