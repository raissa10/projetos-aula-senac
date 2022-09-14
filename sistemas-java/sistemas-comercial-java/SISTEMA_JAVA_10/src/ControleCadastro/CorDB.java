package ControleCadastro;

import ModelCadastro.Cor;
import Principal.Conexao;
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
public class CorDB {

    private static final String sqlTodos
            = "SELECT * FROM COR ORDER BY CD_COR";
    private static final String sqlExcluir
            = "DELETE FROM COR WHERE CD_COR= ?";
    private static final String sqlInserir
            = "INSERT INTO COR                  "
            + "(CD_COR, DS_COR, DT_ALT, DT_CAD, "
            + "HR_CAD, HR_ALT, CD_USUARIO)      "
            + "VALUES                           "
            + "(?,?,                            "
            + "CAST('NOW' AS DATE),             "
            + "CAST('NOW' AS DATE),             "
            + "CAST('NOW' AS TIME),             "
            + "CAST('NOW' AS TIME),             "
            + "?)                               ";
    private static final String sqlAlterar
            = "UPDATE COR  SET                     "
            + "    COR.DS_COR=?,                   "
            + "    COR.DT_ALT=CAST('NOW' AS DATE), "
            + "    COR.HR_ALT=CAST('NOW' AS TIME), "
            + "    COR.CD_USUARIO=?                "
            + "WHERE                               "
            + "    (CD_COR = ? )                   ";
    private static final String sqlCor
            = "SELECT                "
            + "    count(*) as total "
            + "FROM                  "
            + "    COR               "
            + "WHERE                 "
            + "    CD_COR=?          ";

    private static final String sqlBuscaCor
            = "SELECT                "
            + "    COR.*             "
            + "FROM                  "
            + "    COR               "
            + "WHERE                 "
            + "    CD_COR=?          ";

    public DefaultComboBoxModel getComboCor() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("ds_cor"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboCor(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterarCor(Cor cor) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, cor.getDs_cor());
            pstmt.setInt(2, cor.getCd_usuario());
            pstmt.setInt(3, cor.getCd_cor());
            //conn.TRANSACTION_NONE;
            //pstmt.execute
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. alterarCor(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean inserirCor(Cor cor) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(1, cor.getCd_cor());
            pstmt.setString(2, cor.getDs_cor());
            pstmt.setInt(3, cor.getCd_usuario());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. inserirCor(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public boolean excluirCor(int cd_cor) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_cor);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de sql. excluirCor(): \n" + erro.getMessage());
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
            JOptionPane.showMessageDialog(null, "Erro de sql. getTodos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaCor;
    }

    public boolean getCor(int cd_cor) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlCor);
            pstmt.setInt(1, cd_cor);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("total") > 0) {
                    existe = true;
                } else {
                    existe = false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getCor(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public int ValidaCodigoGeneratorAtual() {
        int codigoGenerator = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT GEN_ID(CD_COR, 1) FROM RDB$DATABASE");
            while (rs.next()) {
                int auxCodigoGenerator = rs.getInt("GEN_ID");
                int auxCodigo = auxCodigoGenerator + 1;
                codigoGenerator = auxCodigo;
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de conexão! \n" + erro.getMessage());
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro no método ValidaCodigoGenerator()\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return codigoGenerator;
    }

    public int ValidaCodigoGenerator(String sNomeGenerator,String sNomeCampoGenerator) {
        
        int codigoGenerator = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            
            String sSQL = "SELECT" + sNomeGenerator + "("+sNomeCampoGenerator+", 1) FROM RDB$DATABASE";
            
            rs = stmt.executeQuery(sSQL);
            //rs = stmt.executeQuery("SELECT GEN_ID(CD_COR, 1) FROM RDB$DATABASE");
            while (rs.next()) {
                //int auxCodigoGenerator = rs.getInt("GEN_ID");
                int auxCodigoGenerator = rs.getInt(sNomeGenerator);
                int auxCodigo = auxCodigoGenerator + 1;
                codigoGenerator = auxCodigo;
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de conexão! \n" + erro.getMessage());
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro no método ValidaCodigoGenerator()\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return codigoGenerator;
    }

    public ArrayList listaCores(int cd_cor) {
        ArrayList listaCor = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaCor);
            pstmt.setInt(1, cd_cor);
            rs = pstmt.executeQuery();
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
            JOptionPane.showMessageDialog(null, "Erro no ArrayList listaCores: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaCor;
    }
}
