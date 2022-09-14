package ControllerCadastro;

import Estrutura.ControllerManutencaoPadrao;
import static Estrutura.Mensagem.mensagemErro;
import ModelCadastro.ModelUnidadeMedida;
import Principal.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Gelvazio Camargo
 */
public class ControllerManutencaoUnidadeMedida extends ControllerManutencaoPadrao {

    private static final String sqlTodos
            = "SELECT * FROM unidade_medida order by unidade_medida.cd_unidade";
    private static final String sqlInserir
            = "INSERT INTO UNIDADE_MEDIDA ("
            + "CD_UNIDADE,"
            + "DS_UNIDADE,"
            + "DT_ALT,"
            + "DT_CAD, "
            + "HR_CAD, "
            + "HR_ALT, "
            + "CD_USUARIO,"
            + "CD_FILIAL,"
            + "DS_SIGLA"
            + ") VALUES "
            + "(?,?,"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS TIME),"
            + "CAST('NOW' AS TIME),"
            + "?,?,?)";
    private static final String sqlExcluir = "DELETE FROM UNIDADE_MEDIDA WHERE CD_UNIDADE = ?";
    private static final String sqlAlterar = "UPDATE UNIDADE_MEDIDA SET DS_UNIDADE = ?,"
            + "    DT_ALT =CAST('NOW' AS DATE),"
            + "    HR_ALT = CAST('NOW' AS TIME),"
            + "    CD_USUARIO = ?,"
            + "    CD_FILIAL = ?,"
            + "    DS_SIGLA = ?"
            + "WHERE (CD_UNIDADE = ?);";
    private static final String sqlUnidadeMedida = "SELECT count(*) as total FROM UNIDADE_MEDIDA WHERE CD_UNIDADE=?";

    public DefaultComboBoxModel getComboUnidadeMedida() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("ds_unidade"));
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no sql, getComboUnidadeMedida(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterarUnidadeMedida(ModelUnidadeMedida unidademedida) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, unidademedida.getDs_unidade());
            pstmt.setInt(2, unidademedida.getCd_usuario());
            pstmt.setInt(3, unidademedida.getCd_filial());
            pstmt.setString(4, unidademedida.getDs_sigla());
            pstmt.setInt(5, unidademedida.getCd_unidade());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            mensagemErro("Erro no sql. alterarUnidadeMedida(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean excluirUnidadeMedida(int cd_unidade) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_unidade);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            mensagemErro("Erro no sql. excluirUnidadeMedida(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean inserirUnidadeMedida(ModelUnidadeMedida unidademedida) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, unidademedida.getCd_unidade());
            pstmt.setString(2, unidademedida.getDs_unidade());
            pstmt.setInt(3, unidademedida.getCd_usuario());
            pstmt.setInt(4, unidademedida.getCd_filial());
            pstmt.setString(5, unidademedida.getDs_sigla());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            mensagemErro("Erro no sql. inserirUnidadeMedida():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public ArrayList getTodos() {

        ArrayList listaUnidadeMedida = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                int auxcd_unidade = rs.getInt("cd_unidade");
                String auxds_sigla = rs.getString("ds_sigla");
                String auxds_unidade = rs.getString("ds_unidade");
                int auxcd_usuario = rs.getInt("cd_usuario");
                int auxcd_filial = rs.getInt("cd_filial");
                ModelUnidadeMedida unidademedida = new ModelUnidadeMedida(
                    auxcd_unidade,
                    auxds_unidade,                
                    auxcd_filial,
                    auxcd_usuario,
                    auxds_sigla);
                listaUnidadeMedida.add(unidademedida);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no sql, getTodosUnidadeMedidas():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaUnidadeMedida;
    }

    public boolean getUnidadeMedida(int cd_unidade) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlUnidadeMedida);
            pstmt.setInt(1, cd_unidade);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            mensagemErro("Erro de SQL. getUnidadeMedida(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }   
}
