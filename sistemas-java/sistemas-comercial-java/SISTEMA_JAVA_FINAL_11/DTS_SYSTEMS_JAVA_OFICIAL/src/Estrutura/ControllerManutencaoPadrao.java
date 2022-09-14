package Estrutura;

import Principal.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * Controlador padrão
 * @author Gelvazio Camargo
 * @since 13-05-2019
 */
public class ControllerManutencaoPadrao {
    
    public int validaCodigoGenerator(String sNomeCampoGenerator) {
        int codigoGenerator = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;        
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT nextval('" + sNomeCampoGenerator + "') as " + sNomeCampoGenerator);
            if(rs.next()) {
                codigoGenerator = rs.getInt(sNomeCampoGenerator);                
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de conexão! \n" + erro.getMessage());
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro no método ValidaCodigoGenerator()\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return codigoGenerator;
    }

}
