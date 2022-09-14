/*
 * Copyright (C) 2015 Gelvazio Camargo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ControleCadastro;

import ModelCadastro.Cor;
import ModelCadastro.SubTabPreco;
import Principal.Conexao;
import Principal.MetodosGlobais;
import static Principal.MetodosGlobais.mensagemErro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Gelvazio Camargo
 */
public class SubTabPrecoDB extends MetodosGlobais {

    private static final String sqlTodos
            = "SELECT * FROM SUB_TAB_PRECO BY SUB_TAB_PRECO.CD_REF";
    private static final String sqlExcluir
            = "DELETE FROM SUB_TAB_PRECO WHERE SUB_TAB_PRECO.CD_REF= ?";
    private static final String sqlInserir
            = "INSERT INTO SUB_TAB_PRECO "
            + "(CD_REF, "
            + "VL_CUSTO, "
            + "VL_VENDA, "
            + "VL_PROMOCAO, "
            + "VL_ESPECIAL, "
            + "VL_CUSTO_MED, "
            + "TX_MARGEM_LUCRO_VENDA, "
            + "TX_MARGEM_LUCRO_PROMOCAO, "
            + "TX_MARGEM_LUCRO_ESPECIAL,"
            + "CD_USUARIO, "
            + "DT_ALT, "
            + "HR_ALT,"
            + "DT_CAD,"
            + "HR_CAD,"
            + "CD_FILIAL) "
            + "VALUES (?,?,?, "
            + "?,?,?,?,"
            + "?,?,?, "
            + "CAST('NOW' AS DATE), "
            + "CAST('NOW' AS TIME),"
            + " CAST('NOW' AS DATE),"
            + " CAST('NOW' AS TIME),?); ";
    private static final String sqlAlterar = "UPDATE SUB_TAB_PRECO SET VL_CUSTO = ?,"
            + "    VL_VENDA = ?,"
            + "    VL_PROMOCAO = ?,"
            + "    VL_ESPECIAL = ?,"
            + "    VL_CUSTO_MED =?,"
            + "    TX_MARGEM_LUCRO_VENDA =?,"
            + "    TX_MARGEM_LUCRO_PROMOCAO = ?,"
            + "    TX_MARGEM_LUCRO_ESPECIAL = ?,"
            + "    CD_USUARIO = ?,"
            + "    DT_ALT =CAST('NOW' AS DATE),"
            + "    HR_ALT =CAST('NOW' AS TIME),"
            + "    CD_FILIAL = ?"
            + "WHERE (CD_REF = ?);";

    private static final String sqlBuscaDados
            = "SELECT                "
            + "    count(*) as total "
            + "FROM                  "
            + "    SUB_TAB_PRECO     "
            + "WHERE                 "
            + "    SUB_TAB_PRECO.CD_REF=?          ";

    public boolean alterarSubTabPreco(SubTabPreco subtabpreco) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setDouble(1, subtabpreco.getVl_custo());
            pstmt.setDouble(2, subtabpreco.getVl_venda());
            pstmt.setDouble(3, subtabpreco.getVl_promocao());
            pstmt.setDouble(4, subtabpreco.getVl_especial());
            pstmt.setDouble(5, subtabpreco.getVl_custo_med());
            pstmt.setDouble(6, subtabpreco.getTx_margem_lucro_venda());
            pstmt.setDouble(7, subtabpreco.getTx_margem_lucro_promocao());
            pstmt.setDouble(8, subtabpreco.getTx_margem_lucro_especial());
            pstmt.setInt(9, subtabpreco.getCd_usuario());
            pstmt.setInt(10, subtabpreco.getCd_filial());
            pstmt.setLong(11, subtabpreco.getCd_ref());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            mensagemErro("Erro de sql. alterarSubTabPreco(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean inserirSubTabPreco(SubTabPreco subtabpreco) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setLong(1, subtabpreco.getCd_ref());
            pstmt.setDouble(2, subtabpreco.getVl_custo());
            pstmt.setDouble(3, subtabpreco.getVl_venda());
            pstmt.setDouble(4, subtabpreco.getVl_promocao());
            pstmt.setDouble(5, subtabpreco.getVl_especial());
            pstmt.setDouble(6, subtabpreco.getVl_custo_med());
            pstmt.setDouble(7, subtabpreco.getTx_margem_lucro_venda());
            pstmt.setDouble(8, subtabpreco.getTx_margem_lucro_promocao());
            pstmt.setDouble(9, subtabpreco.getTx_margem_lucro_especial());
            pstmt.setInt(10, subtabpreco.getCd_usuario());
            pstmt.setInt(11, subtabpreco.getCd_filial());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            mensagemErro("Erro de sql. inserirSubTabPreco(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public boolean excluirSubTabPreco(int cd_ref) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_ref);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            mensagemErro("Erro de sql. excluirSubTabPreco(): \n" + erro.getMessage());
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
                int auxcd_cor = rs.getInt("cd_cor");
                String auxnm_cor = rs.getString("ds_cor");
                int auxcd_usuario = rs.getInt("cd_usuario");

                Cor cor = new Cor(
                        auxcd_cor,
                        auxnm_cor,
                        auxcd_usuario
                );
                listaCor.add(cor);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro de sql. getTodos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaCor;
    }

    public boolean getSubTabPreco(int cd_ref) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaDados);
            pstmt.setInt(1, cd_ref);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            mensagemErro("Erro de SQL. getSubTabPreco(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

}
