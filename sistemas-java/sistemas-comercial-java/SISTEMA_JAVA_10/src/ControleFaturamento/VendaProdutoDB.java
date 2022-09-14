package ControleFaturamento;

import ModelCadastro.Municipio;
import ModeloFaturamento.VendaProdutoClassse;
import Principal.ConexaoFirebird;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Tabela banco de dados CREATE TABLE ITENS_ORC_SIMPLES (
 * CD_FILIAL SMALLINT NOT NULL, CD_MOVIMENTO INTEGER NOT NULL, CD_SEQ_ITE_PRO
 * SMALLINT NOT NULL, CD_REFER_PRO BIGINT NOT NULL, QT_ITE_PRO NUMERIC(16,4) NOT
 * NULL, VL_CUS_ITE_PRO NUMERIC(16,4), VL_VEN_ITE_PRO NUMERIC(16,4), DT_EMI_DOC
 * DATE, CD_USUARIO SMALLINT NOT NULL, DT_ALT DATE NOT NULL, HR_ALT TIME NOT
 * NULL, DT_CAD DATE NOT NULL, HR_CAD TIME NOT NULL ); ALTER TABLE
 * ITENS_ORC_SIMPLES ADD CONSTRAINT PK_ITENS_ORC_SIMPLES PRIMARY KEY (CD_FILIAL,
 * CD_MOVIMENTO, CD_SEQ_ITE_PRO);
 */
public class VendaProdutoDB {

    private static final String sqlInserir
            = "INSERT INTO ITENS_ORC_SIMPLES (CD_FILIAL, CD_MOVIMENTO, CD_SEQ_ITE_PRO,"
            + "CD_REFER_PRO, QT_ITE_PRO, VL_CUS_ITE_PRO, VL_VEN_ITE_PRO, DT_EMI_DOC,"
            + "CD_USUARIO, DT_ALT, HR_ALT, DT_CAD, HR_CAD) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, CAST('NOW' AS DATE),?, "
            + "CAST('NOW' AS DATE), CAST('NOW' AS TIME), CAST('NOW' AS DATE),CAST('NOW' AS TIME));";

    private static final String sqlAlterar
            = "UPDATE ITENS_ORC_SIMPLES SET "
            + "CD_FILIAL=?, "
            + "CD_REFER_PRO=?,"
            + "QT_ITE_PRO=?, "
            + "VL_CUS_ITE_PRO=?,"
            + "VL_VEN_ITE_PRO=?,"
            + "DT_EMI_DOC=CAST('NOW' AS DATE),"
            + "CD_USUARIO=?,"
            + "DT_ALT=CAST('NOW' AS DATE),"
            + "HR_ALT=CAST('NOW' AS TIME)"
            + "WHERE (CD_MOVIMENTO=?) "
            + "AND "
            + "(CD_SEQ_ITE_PRO=?);";

    private static final String sqlConsultaProdutodaVenda
            = "SELECT                             "
            + "ITENS_ORC_SIMPLES.cd_movimento,    "
            + "ITENS_ORC_SIMPLES.CD_SEQ_ITE_PRO   "
            + "FROM                               "
            + "    ITENS_ORC_SIMPLES              "
            + "WHERE                              "
            + "ITENS_ORC_SIMPLES.cd_movimento=?   "
            + "AND                                "
            + "ITENS_ORC_SIMPLES.CD_SEQ_ITE_PRO=? "
            + "ORDER BY 2;                        ";
    private static final String sqlTodos
            = "SELECT * FROM produto_simples order by produto_simples.cd_ref";

    public boolean gravarProdutoDaVenda(VendaProdutoClassse vendaprodutoclasse) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, vendaprodutoclasse.getCd_filial());
            pstmt.setInt(2, vendaprodutoclasse.getCd_movimento());
            pstmt.setInt(3, vendaprodutoclasse.getCd_seq_ite_pro());
            pstmt.setInt(4, vendaprodutoclasse.getProduto());
            pstmt.setInt(5, vendaprodutoclasse.getQuantidade());
            pstmt.setDouble(6, vendaprodutoclasse.getVl_custo());
            pstmt.setDouble(7, vendaprodutoclasse.getPreco());
            pstmt.setInt(8, vendaprodutoclasse.getCd_usuario());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. gravarProdutoDaVenda: \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return inseriu;
    }

    public boolean alterarProdutoDaVenda(VendaProdutoClassse vendaprodutoclasse) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setInt(1, vendaprodutoclasse.getCd_filial());
            pstmt.setInt(2, vendaprodutoclasse.getProduto());
            pstmt.setDouble(3, vendaprodutoclasse.getVl_custo());
            pstmt.setDouble(4, vendaprodutoclasse.getPreco());
            pstmt.setInt(5, vendaprodutoclasse.getQuantidade());
            pstmt.setInt(6, vendaprodutoclasse.getCd_usuario());
            pstmt.setInt(7, vendaprodutoclasse.getCd_movimento());
            pstmt.setInt(8, vendaprodutoclasse.getCd_seq_ite_pro());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. alterarProdutoDaVenda(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return alterou;
    }

    public boolean getProdutoDaVenda(int cd_movimento, int aux_sequencia) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlConsultaProdutodaVenda);
            pstmt.setInt(1, cd_movimento);
            pstmt.setInt(2, aux_sequencia);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("CD_SEQ_ITE_PRO") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getProdutoDaVenda(): \n" + e.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return existe;
    }

    public ArrayList getTodos() {
        ArrayList listaCidade = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                String auxcd_estado = rs.getString("cd_estado");
                int auxcd_municipio = rs.getInt("cd_municipio");
                String auxds_municipio = rs.getString("ds_municipio");
                int auxcd_ibge = rs.getInt("cd_ibge");
                String auxcd_cep = rs.getString("cd_cep");
                int auxcd_usuario = rs.getInt("cd_usuario");
                //int auxcd_codigo_tom = rs.getInt("cd_codigo_tom");
                int auxcd_filial = rs.getInt("cd_filial");

                Municipio cidade = new Municipio(
                        auxcd_estado,
                        auxcd_municipio,
                        auxds_municipio,
                        auxcd_ibge,
                        auxcd_cep,
                        auxcd_usuario,
                        //auxcd_codigo_tom,
                        auxcd_filial);
                listaCidade.add(cidade);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. getTodos(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return listaCidade;
    }
}
