/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControleConfig;

import Principal.ConexaoFirebird;
import Principal.MetodosGlobais;
import static Principal.MetodosGlobais.mensagemErro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gelvazio
 */
public class FilialDB extends MetodosGlobais{
        private static final String sqlBuscaDadosEstado = "SELECT FILIAL.CD_UF_FIL FROM FILIAL WHERE FILIAL.CD_FILIAL=?";

    public String retornaCodigoEstadoFilial(int cd_filial) {
        String codigoEstado = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaDadosEstado);
            pstmt.setInt(1, cd_filial);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String auxcd_estado = rs.getString("CD_UF_FIL");
                codigoEstado = auxcd_estado;
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no método retornaCodigoEstadoFilial: \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return codigoEstado;
    }

}
