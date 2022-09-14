package ControllerFaturamento;

/**
 *
 * @author Gelvazio Camargo
 */
import Estrutura.ControllerManutencaoPadrao;
import static Estrutura.Mensagem.mensagemErro;
import ModelFaturamento.ModelVenda;
import Principal.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerManutencaoVenda extends ControllerManutencaoPadrao {

    private static final String sqlAlterar = "UPDATE ORCAMENTO SET CD_VENDE = ?,"
            + "    CD_PAGTO = ?,"
            + "    CD_PESSOA = ?,"
            + "    DT_EMI_DOC=?,"
            + "    DT_SAI_DOC = ?,"
            + "    VL_TOT_CUS_DOC = ?,"
            + "    VL_TOT_PRO_DOC = ?,"
            + "    VL_ACRESCIMO = ?,"
            + "    VL_DESCONTO = ?,"
            + "    CD_TIPO_DOC = ?,"
            + "    FG_SITUACAO = ?,"
            + "    FG_MOVIMENTOU_ESTOQUE = ?,"
            + "    CD_USUARIO = ?,"
            + "    DT_ALT = ?,"
            + "    HR_ALT = ?,"
            + "    DT_CAD=?, "
            + "    HR_CAD=?, "
            + "    CD_CFOP = ?,"
            + "    VL_BASE_ICM_TOTAL = ?,"
            + "    VL_ICM_TOTAL = ?,"
            + "    VL_BASE_ICM_SUB_TOTAL = ?,"
            + "    VL_ICM_SUB_TOTAL = ?,"
            + "    VL_BASE_PIS_TOTAL = ?,"
            + "    VL_PIS_TOTAL = ?,"
            + "    VL_BASE_COFINS_TOTAL = ?,"
            + "    VL_COFINS_TOTAL = ?,"
            + "    VL_BASE_IPI_TOTAL = ?,"
            + "    VL_IPI_TOTAL = ?,"
            + "    VL_BASE_SERVICO_TOTAL = ?,"
            + "    VL_SERVICO_TOTAL = ?,"
            + "    VL_BASE_ISSQN_TOTAL = ?,"
            + "    VL_ISSQN_TOTAL = ?,"
            + "    CD_TRANSPORTADORA = ?,"
            + "    NR_PLACA_VEICULO = ?,"
            + "    QTD_VOLUME = ?,"
            + "    FG_EMITENTE = ?,"
            + "    VL_TOT_FRETE = ?,"
            + "    NM_ESPECIE = ?,"
            + "    NR_NOTA_NFE =?,"
            + "    NR_CHAVE_NFE =?,"
            + "    NR_PROT_AUTORIZACAO = ?,"
            + "    DS_INF_ADICIONAIS = ?,"
            + "    VL_TOT_SEGURO = ?,"
            + "    VL_TOT_PEDIDO_NOTA = ?,"
            + "    DS_MARCA = ?,"
            + "    NR_NUMERACAO =?,"
            + "    VL_PESO_LIQUIDO = ?,"
            + "    VL_PESO_BRUTO = ?,"
            + "    VL_TOT_IMPOSTOS = ?"
            + "WHERE (CD_FILIAL = ?) AND (CD_MOVIMENTO = ?);";

    private static final String sqlInserir = "INSERT INTO ORCAMENTO ("
            + "CD_FILIAL, "
            + "CD_MOVIMENTO,"
            + "CD_VENDE, "
            + "CD_PAGTO, "
            + "CD_PESSOA, "
            + "DT_EMI_DOC, "
            + "DT_SAI_DOC, "
            + "VL_TOT_CUS_DOC, "
            + "VL_TOT_PRO_DOC, "
            + "VL_ACRESCIMO, "
            + "VL_DESCONTO, "
            + "CD_TIPO_DOC, "
            + "FG_SITUACAO, "
            + "FG_MOVIMENTOU_ESTOQUE, "
            + "CD_USUARIO, "
            + "DT_ALT, "
            + "HR_ALT, "
            + "DT_CAD, "
            + "HR_CAD, "
            + "CD_CFOP, "
            + "VL_BASE_ICM_TOTAL, "
            + "VL_ICM_TOTAL, "
            + "VL_BASE_ICM_SUB_TOTAL, "
            + "VL_ICM_SUB_TOTAL, "
            + "VL_BASE_PIS_TOTAL, "
            + "VL_PIS_TOTAL, "
            + "VL_BASE_COFINS_TOTAL, "
            + "VL_COFINS_TOTAL, "
            + "VL_BASE_IPI_TOTAL, "
            + "VL_IPI_TOTAL, "
            + "VL_BASE_SERVICO_TOTAL, "
            + "VL_SERVICO_TOTAL, "
            + "VL_BASE_ISSQN_TOTAL, "
            + "VL_ISSQN_TOTAL, "
            + "CD_TRANSPORTADORA, "
            + "NR_PLACA_VEICULO, "
            + "QTD_VOLUME, "
            + "FG_EMITENTE, "
            + "VL_TOT_FRETE, "
            + "NM_ESPECIE, "
            + "NR_NOTA_NFE, "
            + "NR_CHAVE_NFE, "
            + "NR_PROT_AUTORIZACAO, "
            + "DS_INF_ADICIONAIS, "
            + "VL_TOT_SEGURO, "
            + "VL_TOT_PEDIDO_NOTA, "
            + "DS_MARCA, "
            + "NR_NUMERACAO, "
            + "VL_PESO_LIQUIDO, "
            + "VL_PESO_BRUTO, "
            + "VL_TOT_IMPOSTOS) "
            + "VALUES "
            + "(?, ?, ?, ?, ?, ?,"
            + "?, ?, ?, ?, ?,?, ?, ?, ?,"
            + "?,?,?, ?,"
            + "?, ?, ?, ?, ?,?, ?, ?, ?, ?,"
            + "?, ?, ?, ?, ?,?, ?, ?, ?, ?,"
            + "?, ?, ?, ?, ?,?, ?, ?, ?, ?,"
            + " ?, ?);";

    private static final String sqlConsultaVenda = "SELECT (ORCAMENTO.cd_movimento)as codigosql    "
            + "FROM ORCAMENTO where ORCAMENTO.CD_FILIAL=? AND ORCAMENTO.cd_movimento=? ";

    public boolean gravarVenda(ModelVenda venda) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, venda.getCd_filial());
            pstmt.setInt(2, venda.getCd_movimento());
            pstmt.setInt(3, venda.getCd_vende());
            pstmt.setInt(4, venda.getCd_pagto());
            pstmt.setInt(5, venda.getCd_pessoa());
            pstmt.setDate(6, venda.getDt_emi_doc());
            pstmt.setDate(7, venda.getDt_sai_doc());
            pstmt.setDouble(8, venda.getVl_tot_cus_doc());
            pstmt.setDouble(9, venda.getVl_tot_pro_doc());
            pstmt.setDouble(10, venda.getVl_acrescimo());
            pstmt.setDouble(11, venda.getVl_desconto());
            pstmt.setInt(12, venda.getCd_tipo_doc());
            pstmt.setInt(13, venda.getFg_situacao());
            pstmt.setInt(14, venda.getFg_movimentou_estoque());
            pstmt.setInt(15, venda.getCd_usuario());
            pstmt.setDate(16, venda.getDt_alt());
            pstmt.setTime(17, venda.getHr_alt());
            pstmt.setDate(18, venda.getDt_cad());
            pstmt.setTime(19, venda.getHr_cad());
            pstmt.setInt(20, venda.getCfop());
            pstmt.setDouble(21, venda.getVl_base_icm_total());
            pstmt.setDouble(22, venda.getVl_icm_total());
            pstmt.setDouble(23, venda.getVl_base_icm_sub_total());
            pstmt.setDouble(24, venda.getVl_icm_sub_total());
            pstmt.setDouble(25, venda.getVl_base_pis_total());
            pstmt.setDouble(26, venda.getVl_pis_total());
            pstmt.setDouble(27, venda.getVl_base_cofins_total());
            pstmt.setDouble(28, venda.getVl_cofins_total());
            pstmt.setDouble(29, venda.getVl_base_ipi_total());
            pstmt.setDouble(30, venda.getVl_ipi_total());
            pstmt.setDouble(31, venda.getVl_base_servico_total());
            pstmt.setDouble(32, venda.getVl_servico_total());
            pstmt.setDouble(33, venda.getVl_base_issqn_total());
            pstmt.setDouble(34, venda.getVl_issqn_total());
            pstmt.setInt(35, venda.getCd_transportadora());
            pstmt.setString(36, venda.getNr_placa_veiculo());
            pstmt.setInt(37, venda.getQtd_volume());
            pstmt.setInt(38, venda.getFg_emitente());
            pstmt.setDouble(39, venda.getVl_tot_frete());
            pstmt.setString(40, venda.getNm_especie());
            pstmt.setString(41, venda.getNr_nota_nfe());
            pstmt.setString(42, venda.getNr_chave_nfe());
            pstmt.setString(43, venda.getNr_prot_autorizacao());
            pstmt.setString(44, venda.getDs_inf_adicionais());
            pstmt.setDouble(45, venda.getVl_tot_seguro());
            pstmt.setDouble(46, venda.getVl_tot_pedido_nota());
            pstmt.setString(47, venda.getDs_marca());
            pstmt.setInt(48, venda.getNr_numeracao());
            pstmt.setDouble(49, venda.getVl_peso_liquido());
            pstmt.setDouble(50, venda.getVl_peso_bruto());
            pstmt.setDouble(51, venda.getVl_tot_impostos());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            mensagemErro("sql. gravarVenda: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public boolean alterarVenda(ModelVenda venda) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setInt(1, venda.getCd_vende());
            pstmt.setInt(2, venda.getCd_pagto());
            pstmt.setInt(3, venda.getCd_pessoa());
            pstmt.setDate(4, venda.getDt_emi_doc());
            pstmt.setDate(5, venda.getDt_sai_doc());
            pstmt.setDouble(6, venda.getVl_tot_cus_doc());
            pstmt.setDouble(7, venda.getVl_tot_pro_doc());
            pstmt.setDouble(8, venda.getVl_acrescimo());
            pstmt.setDouble(9, venda.getVl_desconto());
            pstmt.setInt(10, venda.getCd_tipo_doc());
            pstmt.setInt(11, venda.getFg_situacao());
            pstmt.setInt(12, venda.getFg_movimentou_estoque());
            pstmt.setInt(13, venda.getCd_usuario());
            pstmt.setDate(14, venda.getDt_alt());
            pstmt.setTime(15, venda.getHr_alt());
            pstmt.setDate(16, venda.getDt_cad());
            pstmt.setTime(17, venda.getHr_cad());
            pstmt.setInt(18, venda.getCfop());
            pstmt.setDouble(19, venda.getVl_base_icm_total());
            pstmt.setDouble(20, venda.getVl_icm_total());
            pstmt.setDouble(21, venda.getVl_base_icm_sub_total());
            pstmt.setDouble(22, venda.getVl_icm_sub_total());
            pstmt.setDouble(23, venda.getVl_base_pis_total());
            pstmt.setDouble(24, venda.getVl_pis_total());
            pstmt.setDouble(25, venda.getVl_base_cofins_total());
            pstmt.setDouble(26, venda.getVl_cofins_total());
            pstmt.setDouble(27, venda.getVl_base_ipi_total());
            pstmt.setDouble(28, venda.getVl_ipi_total());
            pstmt.setDouble(29, venda.getVl_base_servico_total());
            pstmt.setDouble(30, venda.getVl_servico_total());
            pstmt.setDouble(31, venda.getVl_base_issqn_total());
            pstmt.setDouble(32, venda.getVl_issqn_total());
            pstmt.setDouble(33, venda.getCd_transportadora());
            pstmt.setString(34, venda.getNr_placa_veiculo());
            pstmt.setDouble(35, venda.getQtd_volume());
            pstmt.setInt(36, venda.getFg_emitente());
            pstmt.setDouble(37, venda.getVl_tot_frete());
            pstmt.setString(38, venda.getNm_especie());
            pstmt.setString(39, venda.getNr_nota_nfe());
            pstmt.setString(40, venda.getNr_chave_nfe());
            pstmt.setString(41, venda.getNr_prot_autorizacao());
            pstmt.setString(42, venda.getDs_inf_adicionais());
            pstmt.setDouble(43, venda.getVl_tot_seguro());
            pstmt.setDouble(44, venda.getVl_tot_pedido_nota());
            pstmt.setString(45, venda.getDs_marca());
            pstmt.setInt(46, venda.getNr_numeracao());
            pstmt.setDouble(47, venda.getVl_peso_liquido());
            pstmt.setDouble(48, venda.getVl_peso_bruto());
            pstmt.setDouble(49, venda.getVl_tot_impostos());
            pstmt.setInt(50, venda.getCd_filial());
            pstmt.setInt(51, venda.getCd_movimento());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            mensagemErro("sql. alterarVenda: \n" + erro.getMessage());
            System.out.println("" + erro.getSQLState());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean getVenda(int cd_filial, int cd_movimento) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlConsultaVenda);
            pstmt.setInt(1, cd_filial);
            pstmt.setInt(2, cd_movimento);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("codigosql") > 0) {
                    existe = true;
                } else {
                    existe = false;
                }
            }
        } catch (SQLException e) {
            mensagemErro("SQL. getVenda: \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

}
