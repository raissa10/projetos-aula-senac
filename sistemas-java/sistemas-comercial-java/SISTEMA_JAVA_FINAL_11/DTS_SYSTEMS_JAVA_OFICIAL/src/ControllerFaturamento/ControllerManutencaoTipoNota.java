package ControllerFaturamento;

import Estrutura.ControllerManutencaoPadrao;
import Principal.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio CREATE TABLE TIPO_NOTA ( CD_TIPO INTEGER NOT NULL,
 * CD_NAT_OP_ESTADUAL SMALLINT, CD_NAT_OP_INTERESTADUAL SMALLINT, FG_TRIBUTADO
 * SMALLINT DEFAULT 0 NOT NULL, FG_VENDA SMALLINT DEFAULT 0 NOT NULL,
 * DS_TIPO_NOTA VARCHAR(60) NOT NULL, FG_ENTSAI SMALLINT DEFAULT 0 NOT NULL,
 * FG_BAIXA_EST SMALLINT DEFAULT 0 NOT NULL, CD_USUARIO SMALLINT NOT NULL,
 * DT_ALT DATE NOT NULL, HR_ALT TIME NOT NULL, DT_CAD DATE NOT NULL, HR_CAD TIME
 * NOT NULL, FG_ORCAMENTO SMALLINT DEFAULT 0 NOT NULL, CD_TIPO_DOC INTEGER );
 */
public class ControllerManutencaoTipoNota extends ControllerManutencaoPadrao {

    private static final String sqlTodos
            = "SELECT * FROM tipo_nota_simples ORDER BY CD_tipo";
    private static final String sqlTipoNota
            = "SELECT                "
            + "    count(*) as total "
            + "FROM                  "
            + "    TIPO_NOTA_SIMPLES "
            + "WHERE                 "
            + "    CD_TIPO=?         ";

    public DefaultComboBoxModel getComboTipoNota() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("ds_tipo_nota"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql getComboTipoNota(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean getTipoNota(int cd_tipo) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlTipoNota);
            pstmt.setInt(1, cd_tipo);
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
}
