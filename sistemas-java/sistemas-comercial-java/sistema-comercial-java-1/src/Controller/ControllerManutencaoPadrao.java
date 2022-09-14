package Controller;

import Model.Estados;
import Estrutura.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ControllerManutencaoPadrao {    
    
    public void processaDados(){
        
    }
    
    public void processaDadosInclusao(){
        
    }
    
    public void processaDadosAlteracao(){
        
    }
    
    public void processaDadosExclusao(){
        
    }
    
        public boolean alterarEstado(Estados estado) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
//            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, estado.getDs_estado());
            pstmt.setInt(2, estado.getCd_usuario());
            pstmt.setInt(3, estado.getCd_ibge());
            pstmt.setInt(4, estado.getCd_filial());
            pstmt.setString(5, estado.getCd_estado());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. alterarEstado(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean excluirEstado(String CD_ESTADO) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
//            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setString(1, CD_ESTADO);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirEstado(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }
    
    public ArrayList getTodos() {
        ArrayList listaEstado = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
//            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                String auxCd_estado = rs.getString("cd_estado");
                String auxDs_estado = rs.getString("ds_estado");
                int auxCd_ibge = rs.getInt("cd_ibge");
                int auxcd_filial = rs.getInt("cd_filial");
                int auxcd_usuario = rs.getInt("cd_usuario");
                Estados estado = new Estados(
                        auxCd_estado,
                        auxDs_estado,
                        auxCd_ibge,
                        auxcd_filial,
                        auxcd_usuario);
                listaEstado.add(estado);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getTodos():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaEstado;
    }

    public boolean getEstado(String CD_ESTADO) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
//            pstmt = conn.prepareStatement(sqlEstado);
            pstmt.setString(1, CD_ESTADO);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getEstado(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean getEstadoPessoa(String CD_ESTADO) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
//            pstmt = conn.prepareStatement(sqlEstado);
            pstmt.setString(1, CD_ESTADO);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getEstadoPessoa(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

}
