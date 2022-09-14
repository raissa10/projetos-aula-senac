package Controller;

import Model.NCMSH;
import Estrutura.Conexao;
import Estrutura.TelaPadraoGlobal;
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
public class NCMSHDB extends TelaPadraoGlobal {

    private static final String sqlTodos = "SELECT FIRST 100 NCMSH.*  FROM NCMSH ";//where NCMSH.CD_CODIGO <10";
    private static final String sqlInserir
            = "INSERT INTO NCMSH (CD_CODIGO, CD_NCMSH, DS_TITULO_1, DS_TITULO_2, DS_NCMSH,VL_MVA)"
            + " VALUES (?,?,?,?,?,?)";
    private static final String sqlExcluir = "DELETE FROM NCMSH WHERE NCMSH.CD_CODIGO = ?";
    private static final String sqlAlterar
            = "	UPDATE NCMSH SET     CD_NCMSH=?,"
            + "    DS_TITULO_1=?,"
            + "    DS_TITULO_2=?,"
            + "    DS_NCMSH=?,"
            + "    VL_MVA=?  "
            + "WHERE (CD_CODIGO = ?);";
    private static final String sqlconsultancmsh = "SELECT count(*) as total FROM ncmsh WHERE cd_ncmsh=?";
    private static final String sqlconsultaRegistros = "SELECT * FROM ncmsh WHERE cd_codigo=?";
    private static final String sqlconsultaCodigoncmsh = "SELECT count(*) as total FROM ncmsh WHERE cd_codigo=?";

    public boolean alterarNCMSH(NCMSH ncmsh) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, ncmsh.getCd_ncmsh());
            pstmt.setString(2, ncmsh.getDs_titulo_1());
            pstmt.setString(3, ncmsh.getDs_titulo_2());
            pstmt.setString(4, ncmsh.getDs_ncmsh());
            pstmt.setInt(5, ncmsh.getVl_mva());
            pstmt.setInt(6, ncmsh.getCd_codigo());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            mensagemErro("Erro no sql. alterarNCMSH(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean excluirNCMSH(int cd_codigo) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_codigo);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            mensagemErro("Erro no sql. excluirNCMSH(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean inserirNCMSH(NCMSH ncmsh) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, ncmsh.getCd_codigo());
            pstmt.setString(2, ncmsh.getCd_ncmsh());
            pstmt.setString(3, ncmsh.getDs_titulo_1());
            pstmt.setString(4, ncmsh.getDs_titulo_2());
            pstmt.setString(5, ncmsh.getDs_ncmsh());
            pstmt.setInt(6, ncmsh.getVl_mva());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            mensagemErro("Erro no sql. inserirNCMSH():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public ArrayList getTodos() {

        ArrayList listaNCMSH = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                int cd_codigo = rs.getInt("cd_codigo");
                String cd_ncmsh = rs.getString("cd_ncmsh");
                String ds_titulo_1 = rs.getString("ds_titulo_1");
                String ds_titulo_2 = rs.getString("ds_titulo_2");
                String ds_ncmsh = rs.getString("ds_ncmsh");
                int vl_mva = rs.getInt("vl_mva");

                NCMSH ncmsh = new NCMSH(
                        cd_codigo,
                        cd_ncmsh,
                        ds_titulo_1,
                        ds_titulo_2,
                        ds_ncmsh,
                        vl_mva
                );
                listaNCMSH.add(ncmsh);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no sql, getTodos():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaNCMSH;
    }

    public ArrayList listaNCMSH(int cod_ncmsh) {
        ArrayList listaNCMSH = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlconsultaRegistros);
            pstmt.setInt(1, cod_ncmsh);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int cd_codigo = rs.getInt("cd_codigo");
                String cd_ncmsh = rs.getString("cd_ncmsh");
                String ds_titulo_1 = rs.getString("ds_titulo_1");
                String ds_titulo_2 = rs.getString("ds_titulo_2");
                String ds_ncmsh = rs.getString("ds_ncmsh");
                int vl_mva = rs.getInt("vl_mva");

                NCMSH ncmsh = new NCMSH(
                        cd_codigo,
                        cd_ncmsh,
                        ds_titulo_1,
                        ds_titulo_2,
                        ds_ncmsh,
                        vl_mva
                );
                listaNCMSH.add(ncmsh);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no ArrayList listaNCMSH: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaNCMSH;
    }

    public boolean getNCMSH(String cd_ncmsh) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlconsultancmsh);
            pstmt.setString(1, cd_ncmsh);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            mensagemErro("Erro de SQL. getNCMSH(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean getCodigoNCMSH(int cd_codigo) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlconsultaCodigoncmsh);
            pstmt.setInt(1, cd_codigo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            mensagemErro("Erro de SQL. getCodigoNCMSH(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public int ValidaCodigoGenerator() {
        int codigoGenerator = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT GEN_ID(CD_NCMSH, 1) FROM RDB$DATABASE");
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
            Conexao.closeAll(conn);
        }
        return codigoGenerator;
    }

}
