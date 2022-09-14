package ControleCadastro;

import ModelCadastro.ProdutoSimples;
import Principal.Conexao;
import Principal.MetodosGlobais;
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
public class ProdutoDB extends MetodosGlobais {

    private static final String sqlBuscaGrupoFiscal = "SELECT PRODUTO_SIMPLES.CD_GP_FISCAL FROM PRODUTO_SIMPLES WHERE PRODUTO_SIMPLES.CD_REF=?";

    private static final String sqlTodos = "Select * from PRODUTO_SIMPLES order by PRODUTO_SIMPLES.cd_ref";
    private static final String sqlInserir = "INSERT INTO PRODUTO_SIMPLES ("
            + "CD_PROD,"
            + "DS_PROD,"
            + "CD_GRUPO,"
            + "CD_SUB_GRUPO,"
            + "FG_ATIVO,"
            + "CD_COR,"
            + "CD_FABRICA,"
            + "CD_MARCA,"
            + "DT_ALT,"
            + "HR_ALT,"
            + "DT_CAD,"
            + "HR_CAD,"
            + "CD_GP_FISCAL,"
            + "CD_NCM_SH,"
            + "CD_REF, "
            + "CD_USUARIO,"
            + "CD_FILIAL,"
            + "CD_UNIDADE_MEDIDA,"
            + "QT_ESTOQUE,"
            + "TX_IPI,"
            + "TX_ISS)"
            + "VALUES"
            + "(?,?,?,?,"
            + "?,?,?,?,"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS TIME),"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS TIME),"
            + "?,?,?,?,?,?,?,?,?,?)";
    private static final String sqlExcluir = "DELETE FROM PRODUTO_SIMPLES WHERE CD_REF= ?";
    private static final String sqlAlterar = "UPDATE PRODUTO_SIMPLES SET DS_PROD =?,"
            + "    CD_GRUPO = ?,"
            + "    CD_SUB_GRUPO = ?,"
            + "    FG_ATIVO = ?,"
            + "    CD_COR = ?,"
            + "    CD_FABRICA = ?,"
            + "    CD_MARCA = ?,"
            + "    DT_ALT =CAST('NOW' AS DATE),"
            + "    HR_ALT =CAST('NOW' AS TIME),"
            + "    CD_GP_FISCAL =?,"
            + "    CD_NCM_SH = ?,"
            + "    CD_PROD = ?,"
            + "    CD_USUARIO =?,"
            + "    CD_FILIAL = ?,"
            + "    CD_UNIDADE_MEDIDA = ?,"
            + "    QT_ESTOQUE=?,"
            + "    TX_IPI=?,"
            + "    TX_ISS=?"
            + "WHERE (CD_REF = ? );";

    //Abaixo SQLs  dos combobox da tela Produto
    private static final String sqlProdutoPeloCD_Prod = "SELECT count(*) as total FROM PRODUTO_SIMPLES WHERE cd_prod=?";
    private static final String sqlProdutoPelaCD_Ref = "SELECT count(*) as total FROM PRODUTO_SIMPLES WHERE CD_REF=?";
    private static final String sqlComboGrupoProduto = "SELECT * from grupo order by grupo.ds_grupo";
    private static final String sqlComboSub_GrupoProduto = "SELECT * from sub_grupo order by sub_grupo.ds_sub_grupo";
    private static final String sqlComboCorProduto = "SELECT * from cor order by ds_cor ";
    private static final String sqlComboMarcaProduto = "SELECT * from marca order by ds_marca";
    private static final String sqlComboGrupoFiscalProduto = "SELECT * from gp_fiscal order by ds_gp_fiscal";

    public DefaultComboBoxModel getComboProduto() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getInt("CD_REF"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboProduto(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public DefaultComboBoxModel getComboGrupoProduto() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlComboGrupoProduto);
            while (rs.next()) {
                modelo.addElement(rs.getString("ds_grupo"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboGrupoProduto(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public DefaultComboBoxModel getComboSub_GrupoProduto() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlComboSub_GrupoProduto);
            while (rs.next()) {
                modelo.addElement(rs.getString("ds_sub_grupo"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboSub_GrupoProduto(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public DefaultComboBoxModel getComboCorProduto() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlComboCorProduto);
            while (rs.next()) {
                modelo.addElement(rs.getString("ds_cor"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboCorProduto(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public DefaultComboBoxModel getComboMarcaProduto() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlComboMarcaProduto);
            while (rs.next()) {
                modelo.addElement(rs.getString("ds_marca"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboMarcaProduto(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public DefaultComboBoxModel getComboGrupoFiscalProduto() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlComboGrupoFiscalProduto);
            while (rs.next()) {
                modelo.addElement(rs.getString("ds_gp_fiscal"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboGrupoFiscalProduto: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterarProduto(ProdutoSimples produto) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, produto.getDs_prod());
            pstmt.setInt(2, produto.getCd_grupo());
            pstmt.setInt(3, produto.getCd_sub_grupo());
            pstmt.setInt(4, produto.getFg_ativo());
            pstmt.setInt(5, produto.getCd_cor());
            pstmt.setString(6, produto.getCd_fabrica());
            pstmt.setInt(7, produto.getCd_marca());
            pstmt.setInt(8, produto.getCd_gp_fiscal());
            pstmt.setString(9, produto.getCd_ncm_sh());
            pstmt.setInt(10, produto.getCd_prod());
            pstmt.setInt(11, produto.getCd_usuario());
            pstmt.setInt(12, produto.getCd_filial());
            pstmt.setInt(13, produto.getCd_unidade_medida());
            pstmt.setInt(14, produto.getQt_estoque());
            pstmt.setInt(15, produto.getTx_ipi());
            pstmt.setInt(16, produto.getTx_iss());
            pstmt.setLong(17, produto.getCd_ref());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. alterarProdutos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean excluirProduto(int CD_REF) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, CD_REF);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirProduto(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean inserirProduto(ProdutoSimples produto) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, produto.getCd_prod());
            pstmt.setString(2, produto.getDs_prod());
            pstmt.setInt(3, produto.getCd_grupo());
            pstmt.setInt(4, produto.getCd_sub_grupo());
            pstmt.setInt(5, produto.getFg_ativo());
            pstmt.setInt(6, produto.getCd_cor());
            pstmt.setString(7, produto.getCd_fabrica());
            pstmt.setInt(8, produto.getCd_marca());
            pstmt.setInt(9, produto.getCd_gp_fiscal());
            pstmt.setString(10, produto.getCd_ncm_sh());
            pstmt.setLong(11, produto.getCd_ref());
            pstmt.setInt(12, produto.getCd_usuario());
            pstmt.setInt(13, produto.getCd_filial());
            pstmt.setInt(14, produto.getCd_unidade_medida());
            pstmt.setInt(15, produto.getQt_estoque());
            pstmt.setInt(16, produto.getTx_ipi());
            pstmt.setInt(17, produto.getTx_iss());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. inserirProduto(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public ArrayList getTodos() {
        ArrayList listaProduto = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                int CD_PROD = rs.getInt("CD_PROD");
                String DS_PROD = rs.getString("DS_PROD");
                int CD_GRUPO = rs.getInt("CD_GRUPO");
                int CD_SUB_GRUPO = rs.getInt("CD_SUB_GRUPO");
                int FG_ATIVO = rs.getInt("FG_ATIVO");
                int CD_COR = rs.getInt("CD_COR");
                String CD_FABRICA = rs.getString("CD_FABRICA");
                int CD_MARCA = rs.getInt("CD_MARCA");
                int CD_GP_FISCAL = rs.getInt("CD_GP_FISCAL");
                String CD_NCM_SH = rs.getString("CD_NCM_SH");
                int CD_REF = rs.getInt("CD_REF");
                int cd_usuario = rs.getInt("cd_usuario");
                int cd_filial = rs.getInt("cd_filial");
                int cd_unidade_medida = rs.getInt("cd_unidade_medida");
                int qt_estoque = rs.getInt("qt_estoque");
                int tx_ipi = rs.getInt("tx_ipi");
                int tx_iss = rs.getInt("tx_iss");

                ProdutoSimples produto = new ProdutoSimples(
                        CD_PROD,
                        DS_PROD,
                        CD_GRUPO,
                        CD_SUB_GRUPO,
                        FG_ATIVO,
                        CD_COR,
                        CD_FABRICA,
                        CD_MARCA,
                        CD_GP_FISCAL,
                        CD_NCM_SH,
                        CD_REF,
                        cd_usuario,
                        cd_filial,
                        cd_unidade_medida,
                        qt_estoque,
                        tx_ipi,
                        tx_iss
                );
                listaProduto.add(produto);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no sql, getTodosProdutos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaProduto;
    }

    public boolean getProdutoCD_REF(long CD_REF) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlProdutoPelaCD_Ref);
            pstmt.setLong(1, CD_REF);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getProduto():" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean getProdutoCD_PROD(long CD_PROD) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlProdutoPeloCD_Prod);
            pstmt.setLong(1, CD_PROD);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getProduto():" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public int retornaCodigoGrupoFiscal(int referencia) {
        int codigoGrupoFiscal = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaGrupoFiscal);
            pstmt.setInt(1, referencia);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int auxcd_GRUPO_FISCAL = rs.getInt("CD_GP_FISCAL");
                codigoGrupoFiscal = auxcd_GRUPO_FISCAL;
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no método retornaCodigoGrupoFiscal: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return codigoGrupoFiscal;
    }

}
