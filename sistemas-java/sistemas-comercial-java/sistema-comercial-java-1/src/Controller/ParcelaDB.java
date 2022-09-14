package Controller;

import Model.Parcela;
import Model.ParcelaDados;
import Estrutura.ConexaoFirebird;
import Estrutura.TelaPadraoGlobal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Gelvazio
 */
/*
 CREATE TABLE PARCELAS (
 CD_FILIAL              SMALLINT NOT NULL,
 CD_MOVIMENTO           INTEGER NOT NULL,
 CD_DOCUMENTO           INTEGER NOT NULL,
 NR_PARCELA             INTEGER NOT NULL,
 FG_SITUACAO            INTEGER NOT NULL,
 VL_PARCELA             NUMERIC(16,4),
 VL_JUROS               NUMERIC(16,4),
 CD_CONDICAO_PAGAMENTO  INTEGER NOT NULL,
 SEQUENCIA_PARCELA      SMALLINT NOT NULL,
 VL_ACRESCIMO           NUMERIC(16,4),
 VL_DESCONTO            NUMERIC(16,4),
 DT_VENCTO_PAR          DATE NOT NULL,
 CD_TIPO_COBRANCA       NUMERIC(3,0),
 CD_USUARIO             SMALLINT NOT NULL,
 DT_ALT                 DATE NOT NULL,
 HR_ALT                 TIME NOT NULL,
 DT_CAD                 DATE NOT NULL,
 HR_CAD                 TIME NOT NULL,
 DT_PAG_PAR             DATE,
 VL_PAGO                NUMERIC(16,4),
 cd_pessoa bigint not null
 );
 */
public class ParcelaDB extends TelaPadraoGlobal {

    private static final String sqlInserir
            = "INSERT INTO PARCELAS ("
            + "CD_FILIAL, "
            + "CD_MOVIMENTO,"
            + " CD_DOCUMENTO, "
            + "NR_PARCELA, "
            + "FG_SITUACAO, "
            + "VL_PARCELA, "
            + "VL_JUROS, "
            + "CD_CONDICAO_PAGAMENTO, "
            + "SEQUENCIA_PARCELA, "
            + "VL_ACRESCIMO, "
            + "VL_DESCONTO, "
            + "DT_VENCTO_PAR, "
            + "CD_TIPO_COBRANCA,"
            + "CD_USUARIO,"
            + " DT_ALT,"
            + " HR_ALT, "
            + "DT_CAD,"
            + " HR_CAD,"
            + " DT_PAG_PAR,"
            + " VL_PAGO,"
            + " CD_PESSOA) "
            + "VALUES "
            + "(?,?,?,?,?,?,?,?,?,?,?,?, "
            + "?,?,?,?,?,?,?, ?,?); ";
    private static final String sqlAlterar
            = "UPDATE PARCELAS SET "
            + "    NR_PARCELA =?,"
            + "    FG_SITUACAO = ?,"
            + "    VL_PARCELA = ?,"
            + "    VL_JUROS = ?,"
            + "    VL_ACRESCIMO = ?,"
            + "    VL_DESCONTO = ?,"
            + "    DT_VENCTO_PAR = ?,"
            + "    CD_USUARIO = ?,"
            + "    DT_ALT = ?,"
            + "    HR_ALT = ?,"
            + "    DT_PAG_PAR = ?,"
            + "    VL_PAGO = ?"
            + "WHERE (CD_FILIAL = ?) AND"
            + "      (CD_MOVIMENTO = ?) AND"
            + "      (CD_DOCUMENTO = ?);";

    private static final String sqlListaParcelas
            = "SELECT                                                          "
            + "		SUB_COND_PAG.CD_PARCELA,                               "
            + "		SUB_COND_PAG.CD_COBRANCA,                              "
            + "		TIPO_COBRANCA.DS_COBRANCA,                             "
            + "		SUB_COND_PAG.NR_DIAS_PARCELA                           "
            + "	FROM                                                       "
            + "		COND_PAG                                               "
            + "	INNER JOIN SUB_COND_PAG ON                                 "
            + "		SUB_COND_PAG.CD_CONDICAO=COND_PAG.CD_COND              "
            + "	INNER JOIN TIPO_COBRANCA ON                                "
            + "		TIPO_COBRANCA.CD_COBRANCA=SUB_COND_PAG.CD_COBRANCA     "
            + "	WHERE                                                      "
            + "		COND_PAG.CD_COND=?                                     "
            + "	ORDER BY 1                                                 ";
    private static final String sqlBuscaParcelas
            = "SELECT                       "
            + "    PARCELAS.*               "
            + "FROM                         "
            + "    PARCELAS                 "
            + "WHERE                        "
            + "    PARCELAS.CD_FILIAL=?     "
            + "    AND                      "
            + "    PARCELAS.CD_MOVIMENTO=?  "
            + "    AND                      "
            + "    PARCELAS.CD_DOCUMENTO=?  "
            + "    AND                      "
            + "    PARCELAS.NR_PARCELA=?    ";

    public boolean alterarParcela(Parcela parcela) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            //pstmt.setString(1, cor.getDs_cor());
            //pstmt.setInt(2, cor.getCd_usuario());
            //pstmt.setInt(3, cor.getCd_cor());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            mensagemErro("Erro de sql. alterarParcela(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return alterou;
    }

    public boolean inserirParcela(Parcela parcela) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            //pstmt.setInt(1, cor.getCd_cor());
            // pstmt.setString(2, cor.getDs_cor());
            //pstmt.setInt(3, cor.getCd_usuario());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            mensagemErro("Erro de sql. inserirParcela(): \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return inseriu;
    }

    public boolean getParcela(int cd_filial, int cd_movimento, int cd_documento, int nr_parcela) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaParcelas);
            pstmt.setInt(1, cd_filial);
            pstmt.setInt(2, cd_movimento);
            pstmt.setInt(3, cd_documento);
            pstmt.setInt(4, nr_parcela);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            mensagemErro("Erro de SQL. getParcela(): \n" + e.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return existe;
    }

    public int ValidaCodigoGenerator() {
        int codigoGenerator = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT GEN_ID(CD_COR, 1) FROM RDB$DATABASE");
            while (rs.next()) {
                int auxCodigoGenerator = rs.getInt("GEN_ID");
                int auxCodigo = auxCodigoGenerator + 1;
                codigoGenerator = auxCodigo;
            }
        } catch (SQLException erro) {
            mensagemErro("Erro de conexão! \n" + erro.getMessage());
        } catch (Exception erro) {
            mensagemErro("Erro no método ValidaCodigoGenerator()\n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return codigoGenerator;
    }

    public ArrayList listaParcelas(int cd_condicao_pagamento) {
        ArrayList listaParcelas = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlListaParcelas);
            pstmt.setInt(1, cd_condicao_pagamento);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int auxCD_PARCELA = rs.getInt("CD_PARCELA");
                int auxCD_COBRANCA = rs.getInt("CD_COBRANCA");
                String auDS_COBRANCA = rs.getString("DS_COBRANCA");
                int auxNR_DIAS_PARCELA = rs.getInt("NR_DIAS_PARCELA");

                ParcelaDados parceladados = new ParcelaDados(
                        auxCD_PARCELA,
                        auxCD_COBRANCA,
                        auDS_COBRANCA,
                        auxNR_DIAS_PARCELA                        
                );
                listaParcelas.add(parceladados);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no ArrayList listaParcelas: \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return listaParcelas;
    }
}