package Controller;

import Model.Usuario;
import Estrutura.Conexao;
import Estrutura.Log;
import Estrutura.TelaPadraoGlobal;
import static Estrutura.TelaPadraoGlobal.mensagemErro;
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
public class UsuarioDB extends TelaPadraoGlobal {

    //Parte dos SQL de Consultas
    private static final String sqlTodos = "SELECT * FROM USUARIO";
    private final String sqlInserir
        = "INSERT INTO USUARIO (CD_USUARIO,DS_LOGIN,DS_USUARIO, "
        + "DS_SENHA,CD_GRUPO ,DT_ALT, HR_ALT, DT_CAD, HR_CAD,CD_FILIAL,FG_ATIVO)"
        + "VALUES( ?, ?, ?, ? ,?," 
        + "CAST('NOW' AS DATE),             "
        + "CAST('NOW' AS TIME),             "
        + "CAST('NOW' AS DATE),             "            
        + "CAST('NOW' AS TIME),             "
        + "?,? )";
    private static final String sqlExcluir = "DELETE FROM usuario WHERE CD_usuario= ?";
    private static final String sqlAlterar = "UPDATE USUARIO SET DS_LOGIN = ?,"
            + "    DS_USUARIO = ?,"
            + "    DS_SENHA = ?,"
            + "    CD_GRUPO = ?,"
            + "    DT_ALT = CAST('NOW' AS DATE),"
            + "    HR_ALT =CAST('NOW' AS TIME),"
            + "    FG_ATIVO = ?,"
            + "    CD_FILIAL=?"
            + "WHERE (CD_USUARIO = ?);";

    private static final String SQLCONSULTAUSUARIO = "SELECT (usuario.cd_usuario)as codigosql FROM usuario where usuario.cd_usuario=?";
    private static final String sqlConsultaUsuarioLogin = "SELECT (usuario.CD_USUARIO)as codigosql FROM usuario where usuario.ds_login=?";
    private static final String sqlUnicoUsuario = "SELECT count(*) as total FROM usuario where usuario.ds_login=?";
    
    public boolean getUsuario(int CD_USUARIO) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(SQLCONSULTAUSUARIO);
            pstmt.setInt(1, CD_USUARIO);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("codigosql") > 0;
            }
        } catch (SQLException e) {
            mensagemErro("Erro de SQL. getUsuario: \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean getLogin(String ds_login) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlConsultaUsuarioLogin);
            pstmt.setString(1, ds_login);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("codigosql") > 0;
            }
        } catch (SQLException e) {
            mensagemErro("Erro de SQL. getLogin: \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean alterarUsuario(Usuario usuario) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, usuario.getDs_login());
            pstmt.setString(2, usuario.getDs_usuario());
            pstmt.setString(3, usuario.getDs_senha());
            pstmt.setInt(4, usuario.getCd_grupo());
            pstmt.setInt(5, usuario.getCd_filial());
            pstmt.setInt(6, usuario.getFg_ativo());
            pstmt.setInt(7, usuario.getCd_usuario());
            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            mensagemErro("Erro no sql. alterarUsuario(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean excluirUsuario(int cd_usuario) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, cd_usuario);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            mensagemErro("Erro no sql. excluirUsuario():\n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean inserirUsuario(Usuario usuario) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(   1, usuario.getCd_usuario());
            pstmt.setString(2, usuario.getDs_login());
            pstmt.setString(3, usuario.getDs_usuario());
            pstmt.setString(4, usuario.getDs_senha());
            pstmt.setInt(   5, usuario.getCd_grupo());
            pstmt.setInt(   6, usuario.getCd_filial());
            pstmt.setInt(   7, usuario.getFg_ativo());
            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            String sMensagemErro = "Erro no sql. inserirUsuarios(): \n" + erro.getMessage()+"sql \n"+sqlInserir;
            Log log = new Log();
            log.gravarLog(sMensagemErro);
            mensagemErro(sMensagemErro);
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public ArrayList getTodos() {
        ArrayList listaUsuario = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                int cd_usuario = rs.getInt("cd_usuario");
                String ds_login = rs.getString("ds_login");
                String ds_usuario = rs.getString("ds_usuario");
                String ds_senha = rs.getString("ds_senha");
                int cd_grupo = rs.getInt("cd_grupo");
                int cd_filial = rs.getInt("cd_filial");
                int fg_ativo = rs.getInt("fg_ativo");
                Usuario usuario = new Usuario(
                    cd_usuario,
                    ds_login,
                    ds_usuario,
                    ds_senha,
                    cd_grupo,
                    cd_filial,
                    fg_ativo
                );
                listaUsuario.add(usuario);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no sql, getTodos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaUsuario;
    }

    public boolean getUnicoUsuario(String ds_login) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlUnicoUsuario);
            pstmt.setString(1, ds_login);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") == 1;
            }
        } catch (SQLException e) {
            String sMensagemErro = "Erro de SQL. getPessoa(): \n" + e.getMessage();
            Log log = new Log();
            log.gravarLog(sMensagemErro);
            mensagemErro(sMensagemErro);
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

}
