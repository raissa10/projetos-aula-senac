package ControleCadastro;

import Principal.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import ModelCadastro.Municipio;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class MunicipioDB {

    private static final String sqlTodos = "SELECT * FROM municipio ORDER BY cd_estado,cd_municipio";
    private static final String sqlExcluir = "DELETE FROM municipio WHERE (CD_ESTADO = ?) AND (CD_MUNICIPIO = ?)";
    private static final String sqlInserir
            = "INSERT INTO MUNICIPIO (CD_ESTADO, CD_MUNICIPIO, DS_MUNICIPIO, CD_USUARIO,"
            + " DT_ALT, HR_ALT, DT_CAD, HR_CAD, CD_IBGE, CD_CEP, CD_FILIAL)"
            + "VALUES (? , ? , ? , ? ,"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS TIME),"
            + "CAST('NOW' AS DATE),"
            + "CAST('NOW' AS TIME),"
            + "?, ?, ?)";
    private static final String sqlAlterar
            = "UPDATE municipio SET "
            + "ds_municipio=?,"
            + "CD_IBGE=?,"
            + "CD_CEP=?,"
            + "CD_USUARIO=?,"
            + "CD_FILIAL = ?,"
            + "DT_ALT=CAST('NOW' AS DATE),"
            + "HR_ALT=CAST('NOW' AS TIME) "
            + "WHERE (CD_ESTADO =?) AND(CD_MUNICIPIO =?)";
    private static final String sqlBuscaCidade_E_Estado = "SELECT count(*) as total FROM municipio WHERE CD_ESTADO LIKE UPPER(?) and cd_municipio=?";

    private static final String sqlBuscaNome = "SELECT count(*) as total FROM municipio WHERE CD_ESTADO LIKE UPPER(?)";

    public DefaultComboBoxModel getComboCidade() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("DS_MUNICIPIO"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboCidade(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterarCidade(Municipio cidade) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, cidade.getDs_municipio());
            pstmt.setInt(2, cidade.getCd_ibge());
            pstmt.setString(3, cidade.getCd_cep());
            pstmt.setInt(4, cidade.getCd_usuario());
            pstmt.setInt(5, cidade.getCd_filial());
            pstmt.setString(6, cidade.getCd_estado());
            pstmt.setInt(7, cidade.getCd_municipio());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. alterarCidade(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean inserirCidade(Municipio cidade) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setString(1, cidade.getCd_estado());
            pstmt.setInt(2, cidade.getCd_municipio());
            pstmt.setString(3, cidade.getDs_municipio());
            pstmt.setInt(4, cidade.getCd_usuario());
            pstmt.setInt(5, cidade.getCd_ibge());
            pstmt.setString(6, cidade.getCd_cep());
            pstmt.setInt(7, cidade.getCd_filial());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. inserirCidade(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public boolean excluirCidade(String cd_estado, int cd_municipio) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setString(1, cd_estado);
            pstmt.setInt(2, cd_municipio);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. excluirCidade(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public ArrayList getTodos() {
        ArrayList listaCidade = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                String auxcd_estado = rs.getString("cd_estado");
                int auxcd_municipio = rs.getInt("cd_municipio");
                String auxds_municipio = rs.getString("ds_municipio");
                int auxcd_ibge = rs.getInt("cd_ibge");
                String auxcd_cep = rs.getString("cd_cep");
                int auxcd_usuario = rs.getInt("cd_usuario");
                int auxcd_filial = rs.getInt("cd_filial");

                Municipio cidade = new Municipio(
                        auxcd_estado,
                        auxcd_municipio,
                        auxds_municipio,
                        auxcd_ibge,
                        auxcd_cep,
                        auxcd_usuario,
                        auxcd_filial);
                listaCidade.add(cidade);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. getTodos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaCidade;
    }

    public boolean getCidade(String cd_estado, int cd_municipio) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaCidade_E_Estado);
            pstmt.setString(1, cd_estado);
            pstmt.setInt(2, cd_municipio);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("total") > 0) {
                    existe = true;
                } else {
                    existe = false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getCidade(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean getNomeCidade(String cd_estado) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaNome);
            pstmt.setString(1, cd_estado);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("total") > 0) {
                    existe = true;
                } else {
                    existe = false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getNomeCidade(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean getNomeCidadeEstadodaPessoa(String cd_estado) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaNome);
            pstmt.setString(1, cd_estado);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("total") > 0) {
                    existe = true;
                } else {
                    existe = false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getNomeCidade(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

}
