package Migracao;

//import Principal.ConexaoFirebird;
//import static Principal.MetodosGlobais.mensagemErro;
import static Estrutura.MetodosGlobais.mensagemErro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gelvazio Camargo
 */
public class MigracaoDados {

    private static final String SQLCONSULTAUSUARIO = "SELECT cd_prod, ds_prod FROM produto";

    public boolean listaPessoas() {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoMigracao.getConexao();
            pstmt = conn.prepareStatement(SQLCONSULTAUSUARIO);
           // pstmt.setInt(1, CD_USUARIO);
            rs = pstmt.executeQuery();
            System.out.println("Dados: \n");
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
                System.out.println("Código:"+rs.getInt("cd_prod")+"\n");
                System.out.println("Descricao:"+rs.getString("ds_prod")+"\n");             
            }
        } catch (SQLException e) {
            mensagemErro("Erro de SQL. getUsuario: \n" + e.getMessage());
        } finally {
            ConexaoMigracao.closeAll(conn);
        }
        return existe;
    }

}
