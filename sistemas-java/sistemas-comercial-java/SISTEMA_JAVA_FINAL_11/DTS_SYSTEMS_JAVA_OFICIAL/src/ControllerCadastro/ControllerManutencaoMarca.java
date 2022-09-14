package ControllerCadastro;

import Estrutura.ControllerManutencaoPadrao;
import static Estrutura.Mensagem.mensagemErro;
import ModelCadastro.ModelMarca;
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
public class ControllerManutencaoMarca extends ControllerManutencaoPadrao {

    private static final String sqlTodos
            = "SELECT * FROM MARCA ORDER BY CD_MARCA";
    private static final String sqlExcluir
            = "DELETE FROM MARCA WHERE CD_MARCA= ?";
    private static final String sqlBuscaMarcas
            = "SELECT * FROM MARCA WHERE CD_MARCA= ?";
    private static final String sqlInserir
            = "INSERT INTO MARCA ("
            + "CD_MARCA,"
            + "CD_USUARIO,"
            + "DT_ALT, "
            + "HR_ALT, "
            + "DT_CAD, "
            + "HR_CAD, "
            + "DS_MARCA,"
            + "CD_FILIAL"
            + ") VALUES (?,?,CAST('NOW' AS DATE),CAST('NOW' AS TIME), CAST('NOW' AS DATE), CAST('NOW' AS TIME),?,?);";
    private static final String sqlAlterar
            = "UPDATE MARCA  SET                     "
            + "    DS_MARCA=?,                   "
            + "    MARCA.DT_ALT=CAST('NOW' AS DATE), "
            + "    MARCA.HR_ALT=CAST('NOW' AS TIME),"
            + "     CD_USUARIO=?"
            + "WHERE                               "
            + "    (CD_MARCA = ? )                   ";
    private static final String sqlMarca
            = "SELECT                "
            + "    count(*) as total "
            + "FROM                  "
            + "    MARCA               "
            + "WHERE                 "
            + "    CD_MARCA=?          ";

    public DefaultComboBoxModel getComboMarca() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("ds_marca"));
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no sql, getComboMarca(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterarMarca(ModelMarca marca) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, marca.getDs_marca());
            pstmt.setInt(2, marca.getCd_usuario());
            pstmt.setInt(3, marca.getCd_marca());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            mensagemErro("Erro de sql. alterarMarca(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean inserirMarca(ModelMarca marca) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, marca.getCd_marca());
            pstmt.setInt(2, marca.getCd_usuario());
            pstmt.setString(3, marca.getDs_marca());
            pstmt.setInt(4, 1);
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            mensagemErro("Erro de sql. inserirMarca(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public boolean excluirMarca(int cd_marca) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_marca);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            mensagemErro("Erro de sql. excluirMarca(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public ArrayList getTodos() {
        ArrayList listaMarca = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                int cd_marca = rs.getInt("cd_marca");
                String ds_marca = rs.getString("ds_marca");
                int cd_usuario = rs.getInt("cd_usuario");

                ModelMarca marca = new ModelMarca(
                        cd_marca,
                        ds_marca,
                        cd_usuario
                );
                listaMarca.add(marca);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro de sql. getTodos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaMarca;
    }

    public boolean getMarca(int cd_marca) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlMarca);
            pstmt.setInt(1, cd_marca);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            mensagemErro("Erro de SQL. getMarca(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public int ValidaCodigoGeneratorOld() {
        int codigoGenerator = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT GEN_ID(CD_MARCA, 1) FROM RDB$DATABASE");
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

    public ArrayList listaMarcas(int cd_marca) {
        ArrayList listaMarca = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaMarcas);
            pstmt.setInt(1, cd_marca);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int cd_marcas = rs.getInt("cd_marca");
                String ds_marca = rs.getString("ds_marca");
                int cd_usuario = rs.getInt("cd_usuario");

                ModelMarca marca = new ModelMarca(
                        cd_marcas,
                        ds_marca,
                        cd_usuario
                );
                listaMarca.add(marca);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no ArrayList listaMarcas: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaMarca;
    }

}
