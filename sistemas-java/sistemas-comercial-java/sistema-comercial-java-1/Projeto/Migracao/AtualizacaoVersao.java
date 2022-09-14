package Migracao;

import Estrutura.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class AtualizacaoVersao {
    
    private String sqlAtualizacaoVersao = "";

    public String getSqlAtualizacaoVersao() {
        return sqlAtualizacaoVersao;
    }

    public void setSqlAtualizacaoVersao(String sqlAtualizacaoVersao) {
        this.sqlAtualizacaoVersao = sqlAtualizacaoVersao;
    }
       
    public void atualizaVersao(){
        ScriptVersaoSistema script = new ScriptVersaoSistema();        
        int codigoVersao = this.getCodigoAtualizacaoVersao();
        switch(codigoVersao){
            case 0:
                this.setSqlAtualizacaoVersao(script.getScriptVersaoInicial());
            break;
            default:
                JOptionPane.showMessageDialog(null, "Atualização indisponível para a versão:" + codigoVersao);
                break;
        }
        this.executaSql();        
    }
    
    protected int getCodigoAtualizacaoVersao(){
        int codigo = 0;
        String sqlConsultaVersao = "SELECT coalesce(MAX(CD_VERSAO),0) AS codigoversao FROM VERSAO";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlConsultaVersao);            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                codigo = rs.getInt("codigoversao");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getPessoa: \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return codigo;
    }
    
    public boolean executaSql() {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAtualizacaoVersao);            
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro na funcao executaSql(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }


}


