package ControllerFaturamento;

import Estrutura.ControllerManutencaoPadrao;
import ModelCadastro.ModelCor;
import ModelFaturamento.ModelTipoLancamento;
import Principal.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class ControllerManutencaoLancamento extends ControllerManutencaoPadrao {

    // create table lancamento(
//cd_lancamento int2 not null, 
//ds_lancamento varchar(100) not null);
//cd_tipo_lancamento int2 not null, 
//cd_conta_credito int2 not null, 
//cd_conta_debito int2 not null, 
//vl_valor numeric(16,2)
//fg_mensal SMALLINT not null default 0

    private static final String sqlTodos
            = "SELECT * FROM lancamento ORDER BY cd_tipo_lancamento";
    private static final String sqlExcluir
            = "DELETE FROM COR WHERE CD_COR= ?";
    private static final String sqlInserir
            = "INSERT INTO LANCAMENTO (CD_LANCAMENTO,DS_LANCAMENTO, CD_TIPO_LANCAMENTO, CD_CONTA_CREDITO, CD_CONTA_DEBITO, VL_VALOR, FG_MENSAL) VALUES (?,?)";
    private static final String sqlAlterar
            = "UPDATE TIPO_LANCAMENTO  SET "            
            + "    DS_TIPO_LANCAMENTO=?                "
            + "WHERE                               "
            + "    (CD_TIPO_LANCAMENTO = ? )";
    private static final String sqlCor
            = "SELECT                "
            + "    count(*) as total "
            + "FROM                  "
            + "    tipo_lancamento               "
            + "WHERE                 "
            + "    CD_TIPO_LANCAMENTO=?          ";

    private static final String sqlBuscaCor
            = "SELECT * "
            + "FROM                  "
            + "    COR               "
            + "WHERE                 "
            + "    CD_COR=?          ";

    public DefaultComboBoxModel getComboDados() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {                
                modelo.addElement(rs.getString("ds_tipo_lancamento"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboDados(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterar(ModelTipoLancamento model) {
        boolean alterou = false;
        Connection conn = null;        
        try {
            conn = Conexao.getConexao();
            PreparedStatement pstmt = conn.prepareStatement(sqlAlterar);
            
            pstmt.setString(1, model.getDs_tipo_lancamento()); 
            pstmt.setInt(2, model.getCd_tipo_lancamento());
            
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. alterar(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }
    
    public boolean inserir(ModelTipoLancamento model) {
        boolean alterou = false;
        Connection conn = null;        
        try {
            conn = Conexao.getConexao();
            PreparedStatement pstmt = conn.prepareStatement(sqlInserir);
            
            pstmt.setInt(1, model.getCd_tipo_lancamento());
            pstmt.setString(2, model.getDs_tipo_lancamento()); 
            
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. sqlInserir(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean excluir(int cd_tipo_lancamento) {
        boolean excluiu = false;
        Connection conn = null;
        try {
            conn = Conexao.getConexao();
            PreparedStatement pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_tipo_lancamento);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. excluir \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public ArrayList getTodos() {
        ArrayList listaCor = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                int auxCodigo = rs.getInt("cd_tipo_lancamento");
                String auxDescricao = rs.getString("ds_tipo_lancamento");

                ModelTipoLancamento model = new ModelTipoLancamento(
                        auxCodigo,
                        auxDescricao
                );
                listaCor.add(model);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. getTodos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaCor;
    }
    
    public boolean existeRegistro(int cd_tipo_lancamento) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlCor);
            pstmt.setInt(1, cd_tipo_lancamento);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. existe: \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    //public ArrayList listaCores(int cd_cor) {
    public ArrayList listaRegistros(int cd_cor) {
        ArrayList listaCor = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaCor);
            pstmt.setInt(1, cd_cor);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int auxcd_cor = rs.getInt("cd_cor");
                String auxnm_cor = rs.getString("ds_cor");
                int auxcd_usuario = rs.getInt("cd_usuario");

                ModelCor cor = new ModelCor(
                        auxcd_cor,
                        auxnm_cor,
                        auxcd_usuario
                );
                listaCor.add(cor);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no ArrayList listaCores: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaCor;
    }
}
