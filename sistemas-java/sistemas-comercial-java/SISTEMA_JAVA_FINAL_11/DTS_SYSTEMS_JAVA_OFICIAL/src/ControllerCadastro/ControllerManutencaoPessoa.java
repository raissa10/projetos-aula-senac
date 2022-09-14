package ControllerCadastro;

import Estrutura.ControllerManutencaoPadrao;
import static Estrutura.Mensagem.mensagemErro;
import ModelCadastro.ModelPessoa;
import Principal.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class ControllerManutencaoPessoa extends ControllerManutencaoPadrao {

    private static final String sqlBuscaDadosPessoa         = " SELECT CD_ESTADO,TIPO_CONSUMO FROM PESSOA WHERE CD_PESSOA=?";
    private static final String sqlTodos                    = " SELECT * FROM PESSOA ORDER BY CD_PESSOA; ";
    private static final String sqlTodosFornecedores        = "	SELECT * FROM PESSOA WHERE FG_FORNECEDOR = 1  ORDER BY CD_PESSOA; ";
    private static final String sqlTodosVendedores          = " SELECT * FROM PESSOA WHERE FG_VENDEDOR = 1  ORDER BY CD_PESSOA; ";
    private static final String sqlBuscaDadosPessoaImpostos = "	SELECT * FROM PESSOA WHERE CD_PESSOA=?";
    private static final String sqlTodasTransportadoras     = "	SELECT * FROM PESSOA WHERE FG_TRANSPORTADOR=1  ORDER by CD_PESSOA; ";
    private static final String sqlInserir = "INSERT INTO pessoa("
            + " CD_PESSOA,"
            + " NM_PESSOA,"
            + " FG_CLIENTE,"
            + " FG_VENDEDOR,"
            + " FG_FORNECEDOR,"
            + " DS_ENDERECO,"
            + " NR_ENDERECO,"
            + " DS_BAIRRO,"
            + " CD_ESTADO,"
            + " CD_CIDADE,"
            + " CD_PAIS, "
            + " CD_CEP,"
            + " DS_EMAIL,"
            + " NR_TELEFONE,"
            + " CD_CGCCPF, "
            + " CD_USUARIO,"
            + " DT_ALT,"
            + " HR_ALT,"
            + " DT_CAD,"
            + " HR_CAD,"
            + " CD_FILIAL,"
            + " NR_INSCRICAO_ESTADUAL,"
            + " TIPO_CONSUMO,"
            + " REGIME_TRIBUTACAO,"
            + " FG_TRANSPORTADOR,"
            + "fg_ativo) "
            + "VALUES "
            + "(?,?,?,?,?,?,?,?,?,?,"
            + "?,?,?,?,?,?,?,?,?,?,"
            + "?,?,?,?,?,?);";
    private static final String sqlExcluir = "DELETE FROM PESSOA WHERE CD_PESSOA= ?";
    private static final String sqlAlterar = "UPDATE PESSOA SET NM_PESSOA = ?,"
            + "    FG_CLIENTE =  ?,"
            + "    FG_VENDEDOR =  ?,"
            + "    FG_FORNECEDOR =  ?,"
            + "    DS_ENDERECO =  ?,"
            + "    NR_ENDERECO =  ?,"
            + "    DS_BAIRRO =  ?,"
            + "    CD_ESTADO =  ?,"
            + "    CD_CIDADE =  ?,"
            + "    CD_PAIS =  ?,"
            + "    CD_CEP =  ?,"
            + "    DS_EMAIL = ?,"
            + "    NR_TELEFONE = ?,"
            + "    CD_CGCCPF =  ?,"
            + "    CD_USUARIO =  ?,"
            + "    DT_ALT =  ?,"
            + "    HR_ALT =  ?,"
            + "    DT_CAD =  ?,"
            + "    HR_CAD =  ?,"
            + "    CD_FILIAL =  ?,"
            + "    NR_INSCRICAO_ESTADUAL =  ?,"
            + "    TIPO_CONSUMO =  ?,"
            + "    REGIME_TRIBUTACAO =  ?,"
            + "    FG_TRANSPORTADOR =  ?,"
            + "    FG_ATIVO =  ?"
            + "WHERE (CD_PESSOA = ?);";

    private static final String sqlPessoa                       = "SELECT count(*) as total FROM PESSOA WHERE CD_PESSOA=?";
    private static final String sqlConsultaPessoa               = "SELECT (cd_pessoa)as codigosql FROM PESSOA where cd_pessoa=?";
    private static final String sqlConsultaPessoaTransportadora = "SELECT (cd_pessoa)as codigosql FROM PESSOA where FG_TRANSPORTADOR=1 AND cd_pessoa=?";

    public DefaultComboBoxModel getComboPessoa() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            while (rs.next()) {
                modelo.addElement(rs.getString("nm_pessoa"));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getComboPessoa(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return modelo;
    }

    public boolean alterarPessoa(ModelPessoa pessoa) {
        boolean alterou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlAlterar);
            pstmt.setString(1, pessoa.getNm_pessoa());
            pstmt.setInt(2, pessoa.getFg_cliente());
            pstmt.setInt(3, pessoa.getFg_vendedor());
            pstmt.setInt(4, pessoa.getFg_fornecedor());
            pstmt.setString(5, pessoa.getDs_endereco());
            pstmt.setString(6, pessoa.getNr_endereco());
            pstmt.setString(7, pessoa.getDs_bairro());
            pstmt.setString(8, pessoa.getCd_estado());
            pstmt.setInt(9, pessoa.getCd_cidade());
            pstmt.setInt(10, pessoa.getCd_pais());
            pstmt.setString(11, pessoa.getCd_cep());
            pstmt.setString(12, pessoa.getDs_email());
            pstmt.setString(13, pessoa.getNr_telefone());
            pstmt.setString(14, pessoa.getCd_cgccpf());
            pstmt.setInt(15, pessoa.getCd_usuario());
            pstmt.setDate(16, pessoa.getDt_alt());
            pstmt.setTime(17, pessoa.getHr_alt());
            pstmt.setDate(18, pessoa.getDt_cad());
            pstmt.setTime(19, pessoa.getHr_cad());
            pstmt.setInt(20, pessoa.getCd_filial());
            pstmt.setString(21, pessoa.getNr_inscricao_estadual());
            pstmt.setInt(22, pessoa.getTipo_consumo());
            pstmt.setInt(23, pessoa.getRegime_tributacao());
            pstmt.setInt(24, pessoa.getFg_transportador());
            pstmt.setInt(25, pessoa.getFg_ativo());
            pstmt.setInt(26, pessoa.getCd_pessoa());

            pstmt.executeUpdate();
            alterou = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. alterarPessoa(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return alterou;
    }

    public boolean excluirPessoa(int CD_PESSOA) {
        boolean excluiu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluir);
            pstmt.setInt(1, CD_PESSOA);
            pstmt.executeUpdate();
            excluiu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirPessoa(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return excluiu;
    }

    public boolean inserirPessoa(ModelPessoa pessoa) {
        boolean inseriu = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlInserir);
            pstmt.setInt(   1, pessoa.getCd_pessoa());
            pstmt.setString(2, pessoa.getNm_pessoa());
            pstmt.setInt(   3, pessoa.getFg_cliente());
            pstmt.setInt(   4, pessoa.getFg_vendedor());
            pstmt.setInt(   5, pessoa.getFg_fornecedor());
            pstmt.setString(6, pessoa.getDs_endereco());
            pstmt.setString(7, pessoa.getNr_endereco());
            pstmt.setString(8, pessoa.getDs_bairro());
            pstmt.setString(9, pessoa.getCd_estado());
            pstmt.setInt(   10, pessoa.getCd_cidade());
            pstmt.setInt(   11, pessoa.getCd_pais());
            pstmt.setString(12, pessoa.getCd_cep());
            pstmt.setString(13, pessoa.getDs_email());
            pstmt.setString(14, pessoa.getNr_telefone());
            pstmt.setString(15, pessoa.getCd_cgccpf());
            pstmt.setInt(   16, pessoa.getCd_usuario());
            pstmt.setDate(  17, pessoa.getDt_alt());
            pstmt.setTime(  18, pessoa.getHr_alt());
            pstmt.setDate(  19, pessoa.getDt_cad());
            pstmt.setTime(  20, pessoa.getHr_cad());
            pstmt.setInt(   21, pessoa.getCd_filial());
            pstmt.setString(22, pessoa.getNr_inscricao_estadual());
            pstmt.setInt(   23, pessoa.getTipo_consumo());
            pstmt.setInt(   24, pessoa.getRegime_tributacao());
            pstmt.setInt(   25, pessoa.getFg_transportador());
            pstmt.setInt(   26, pessoa.getFg_ativo());

            pstmt.executeUpdate();
            inseriu = true;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. inserirPessoa(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return inseriu;
    }

    public ArrayList getTodos() {
        ArrayList listaPessoa = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodos);
            //OBSERVE MUITO IMPORTANTE!!!!!
            //NAO DEIXAR ESPAÇOS,POIS DA ERROS DEPOIS!!!!            
            while (rs.next()) {
                int cd_pessoa = rs.getInt("CD_PESSOA");
                String nm_pessoa = rs.getString("NM_PESSOA");
                int fg_cliente = rs.getInt("fg_cliente");
                int fg_vendedor = rs.getInt("fg_vendedor");
                int fg_fornecedor = rs.getInt("fg_fornecedor");
                String ds_endereco = rs.getString("ds_endereco");
                String nr_endereco = rs.getString("nr_endereco");
                String ds_bairro = rs.getString("ds_bairro");
                String cd_estado = rs.getString("cd_estado");
                int cd_cidade = rs.getInt("cd_cidade");
                int cd_pais = rs.getInt("cd_pais");
                String cd_cep = rs.getString("cd_cep");
                String ds_email = rs.getString("ds_email");
                String nr_telefone = rs.getString("nr_telefone");
                String cd_cgccpf = rs.getString("cd_cgccpf");
                int cd_usuario = rs.getInt("cd_usuario");
                Date dt_alt = rs.getDate("dt_alt");
                Time hr_alt = rs.getTime("hr_alt");
                Date dt_cad = rs.getDate("dt_cad");
                Time hr_cad = rs.getTime("hr_cad");
                int cd_filial = rs.getInt("cd_filial");
                String nr_inscricao_estadual = rs.getString("nr_inscricao_estadual");
                int tipo_consumo = rs.getInt("tipo_consumo");
                int regime_tributacao = rs.getInt("regime_tributacao");
                int fg_transportador = rs.getInt("fg_transportador");
                int fg_ativo = rs.getInt("fg_ativo");

                ModelPessoa pessoa = new ModelPessoa(
                        cd_pessoa,
                        nm_pessoa,
                        fg_cliente,
                        fg_vendedor,
                        fg_fornecedor,
                        ds_endereco,
                        nr_endereco,
                        ds_bairro,
                        cd_estado,
                        cd_cidade,
                        cd_pais,
                        cd_cep,
                        ds_email,
                        nr_telefone,
                        cd_cgccpf,
                        cd_usuario,
                        dt_alt,
                        hr_alt,
                        dt_cad,
                        hr_cad,
                        cd_filial,
                        nr_inscricao_estadual,
                        tipo_consumo,
                        regime_tributacao,
                        fg_transportador,
                        fg_ativo
                );
                listaPessoa.add(pessoa);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getTodos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaPessoa;
    }

    public ArrayList getTodosFornecedores() {
        ArrayList listaPessoa = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodosFornecedores);        
            while (rs.next()) {
                int cd_pessoa = rs.getInt("CD_PESSOA");
                String nm_pessoa = rs.getString("NM_PESSOA");
                int fg_cliente = rs.getInt("fg_cliente");
                int fg_vendedor = rs.getInt("fg_vendedor");
                int fg_fornecedor = rs.getInt("fg_fornecedor");
                String ds_endereco = rs.getString("ds_endereco");
                String nr_endereco = rs.getString("nr_endereco");
                String ds_bairro = rs.getString("ds_bairro");
                String cd_estado = rs.getString("cd_estado");
                int cd_cidade = rs.getInt("cd_cidade");
                int cd_pais = rs.getInt("cd_pais");
                String cd_cep = rs.getString("cd_cep");
                String ds_email = rs.getString("ds_email");
                String nr_telefone = rs.getString("nr_telefone");
                String cd_cgccpf = rs.getString("cd_cgccpf");
                int cd_usuario = rs.getInt("cd_usuario");
                Date dt_alt = rs.getDate("dt_alt");
                Time hr_alt = rs.getTime("hr_alt");
                Date dt_cad = rs.getDate("dt_cad");
                Time hr_cad = rs.getTime("hr_cad");
                int cd_filial = rs.getInt("cd_filial");
                String nr_inscricao_estadual = rs.getString("nr_inscricao_estadual");
                int tipo_consumo = rs.getInt("tipo_consumo");
                int regime_tributacao = rs.getInt("regime_tributacao");
                int fg_transportador = rs.getInt("fg_transportador");
                int fg_ativo = rs.getInt("fg_ativo");

                ModelPessoa pessoa = new ModelPessoa(
                        cd_pessoa,
                        nm_pessoa,
                        fg_cliente,
                        fg_vendedor,
                        fg_fornecedor,
                        ds_endereco,
                        nr_endereco,
                        ds_bairro,
                        cd_estado,
                        cd_cidade,
                        cd_pais,
                        cd_cep,
                        ds_email,
                        nr_telefone,
                        cd_cgccpf,
                        cd_usuario,
                        dt_alt,
                        hr_alt,
                        dt_cad,
                        hr_cad,
                        cd_filial,
                        nr_inscricao_estadual,
                        tipo_consumo,
                        regime_tributacao,
                        fg_transportador,
                        fg_ativo
                );
                listaPessoa.add(pessoa);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getTodosFornecedores(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaPessoa;
    }

    public ArrayList getTodosVendedores() {
        ArrayList listaPessoa = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodosVendedores);        
            while (rs.next()) {
                int cd_pessoa = rs.getInt("CD_PESSOA");
                String nm_pessoa = rs.getString("NM_PESSOA");
                int fg_cliente = rs.getInt("fg_cliente");
                int fg_vendedor = rs.getInt("fg_vendedor");
                int fg_fornecedor = rs.getInt("fg_fornecedor");
                String ds_endereco = rs.getString("ds_endereco");
                String nr_endereco = rs.getString("nr_endereco");
                String ds_bairro = rs.getString("ds_bairro");
                String cd_estado = rs.getString("cd_estado");
                int cd_cidade = rs.getInt("cd_cidade");
                int cd_pais = rs.getInt("cd_pais");
                String cd_cep = rs.getString("cd_cep");
                String ds_email = rs.getString("ds_email");
                String nr_telefone = rs.getString("nr_telefone");
                String cd_cgccpf = rs.getString("cd_cgccpf");
                int cd_usuario = rs.getInt("cd_usuario");
                Date dt_alt = rs.getDate("dt_alt");
                Time hr_alt = rs.getTime("hr_alt");
                Date dt_cad = rs.getDate("dt_cad");
                Time hr_cad = rs.getTime("hr_cad");
                int cd_filial = rs.getInt("cd_filial");
                String nr_inscricao_estadual = rs.getString("nr_inscricao_estadual");
                int tipo_consumo = rs.getInt("tipo_consumo");
                int regime_tributacao = rs.getInt("regime_tributacao");
                int fg_transportador = rs.getInt("fg_transportador");
                int fg_ativo = rs.getInt("fg_ativo");

                ModelPessoa pessoa = new ModelPessoa(
                        cd_pessoa,
                        nm_pessoa,
                        fg_cliente,
                        fg_vendedor,
                        fg_fornecedor,
                        ds_endereco,
                        nr_endereco,
                        ds_bairro,
                        cd_estado,
                        cd_cidade,
                        cd_pais,
                        cd_cep,
                        ds_email,
                        nr_telefone,
                        cd_cgccpf,
                        cd_usuario,
                        dt_alt,
                        hr_alt,
                        dt_cad,
                        hr_cad,
                        cd_filial,
                        nr_inscricao_estadual,
                        tipo_consumo,
                        regime_tributacao,
                        fg_transportador,
                        fg_ativo
                );
                listaPessoa.add(pessoa);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, getTodos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaPessoa;
    }

    public ArrayList getTodasTransportadoras() {
        ArrayList listaPessoa = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlTodasTransportadoras);
            while (rs.next()) {
                int cd_pessoa = rs.getInt("CD_PESSOA");
                String nm_pessoa = rs.getString("NM_PESSOA");
                int fg_cliente = rs.getInt("fg_cliente");
                int fg_vendedor = rs.getInt("fg_vendedor");
                int fg_fornecedor = rs.getInt("fg_fornecedor");
                String ds_endereco = rs.getString("ds_endereco");
                String nr_endereco = rs.getString("nr_endereco");
                String ds_bairro = rs.getString("ds_bairro");
                String cd_estado = rs.getString("cd_estado");
                int cd_cidade = rs.getInt("cd_cidade");
                int cd_pais = rs.getInt("cd_pais");
                String cd_cep = rs.getString("cd_cep");
                String ds_email = rs.getString("ds_email");
                String nr_telefone = rs.getString("nr_telefone");
                String cd_cgccpf = rs.getString("cd_cgccpf");
                int cd_usuario = rs.getInt("cd_usuario");
                Date dt_alt = rs.getDate("dt_alt");
                Time hr_alt = rs.getTime("hr_alt");
                Date dt_cad = rs.getDate("dt_cad");
                Time hr_cad = rs.getTime("hr_cad");
                int cd_filial = rs.getInt("cd_filial");
                String nr_inscricao_estadual = rs.getString("nr_inscricao_estadual");
                int tipo_consumo = rs.getInt("tipo_consumo");
                int regime_tributacao = rs.getInt("regime_tributacao");
                int fg_transportador = rs.getInt("fg_transportador");
                int fg_ativo = rs.getInt("fg_ativo");

                ModelPessoa pessoa = new ModelPessoa(
                        cd_pessoa,
                        nm_pessoa,
                        fg_cliente,
                        fg_vendedor,
                        fg_fornecedor,
                        ds_endereco,
                        nr_endereco,
                        ds_bairro,
                        cd_estado,
                        cd_cidade,
                        cd_pais,
                        cd_cep,
                        ds_email,
                        nr_telefone,
                        cd_cgccpf,
                        cd_usuario,
                        dt_alt,
                        hr_alt,
                        dt_cad,
                        hr_cad,
                        cd_filial,
                        nr_inscricao_estadual,
                        tipo_consumo,
                        regime_tributacao,
                        fg_transportador,
                        fg_ativo
                );
                listaPessoa.add(pessoa);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no sql,getTodasTransportadoras(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaPessoa;
    }

    public boolean getPessoa(int cd_pessoa) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlConsultaPessoa);
            pstmt.setInt(1, cd_pessoa);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("codigosql") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getPessoa: \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean getPessoaTransportadora(int cd_pessoa) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlConsultaPessoaTransportadora);
            pstmt.setInt(1, cd_pessoa);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("codigosql") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getPessoaTransportadora: \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public boolean getCidade_Pessoa(int CD_PESSOA) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlPessoa);
            pstmt.setInt(1, CD_PESSOA);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getPessoa(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

    public String retornaCodigoEstado(int cd_pessoa) {
        String codigoEstado = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaDadosPessoa);
            pstmt.setInt(1, cd_pessoa);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String auxcd_estado = rs.getString("CD_ESTADO");
                codigoEstado = auxcd_estado;
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no método retornaCodigoEstado: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return codigoEstado;
    }
    
    public int retornaTipoConsumo(int cd_pessoa) {
        int TipoConsumo =0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaDadosPessoa);
            pstmt.setInt(1, cd_pessoa);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int auxtipo_consumo = rs.getInt("TIPO_CONSUMO");
                TipoConsumo = auxtipo_consumo;
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no método retornaTipoConsumo: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return TipoConsumo;
    }

    public boolean getPais_Pessoa(int CD_PESSOA) {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlPessoa);
            pstmt.setInt(1, CD_PESSOA);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. getPessoa(): \n" + e.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return existe;
    }

      public ArrayList verificaDadosImpostos(int auxcd_pessoa) {
        ArrayList listaPessoa = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlBuscaDadosPessoaImpostos);
            pstmt.setInt(1, auxcd_pessoa);
            rs = pstmt.executeQuery();            
            while (rs.next()) {
                int cd_pessoa = rs.getInt("CD_PESSOA");
                String nm_pessoa = rs.getString("NM_PESSOA");
                int fg_cliente = rs.getInt("fg_cliente");
                int fg_vendedor = rs.getInt("fg_vendedor");
                int fg_fornecedor = rs.getInt("fg_fornecedor");
                String ds_endereco = rs.getString("ds_endereco");
                String nr_endereco = rs.getString("nr_endereco");
                String ds_bairro = rs.getString("ds_bairro");
                String cd_estado = rs.getString("cd_estado");
                int cd_cidade = rs.getInt("cd_cidade");
                int cd_pais = rs.getInt("cd_pais");
                String cd_cep = rs.getString("cd_cep");
                String ds_email = rs.getString("ds_email");
                String nr_telefone = rs.getString("nr_telefone");
                String cd_cgccpf = rs.getString("cd_cgccpf");
                int cd_usuario = rs.getInt("cd_usuario");
                Date dt_alt = rs.getDate("dt_alt");
                Time hr_alt = rs.getTime("hr_alt");
                Date dt_cad = rs.getDate("dt_cad");
                Time hr_cad = rs.getTime("hr_cad");
                int cd_filial = rs.getInt("cd_filial");
                String nr_inscricao_estadual = rs.getString("nr_inscricao_estadual");
                int tipo_consumo = rs.getInt("tipo_consumo");
                int regime_tributacao = rs.getInt("regime_tributacao");
                int fg_transportador = rs.getInt("fg_transportador");
                int fg_ativo = rs.getInt("fg_ativo");

                ModelPessoa pessoa = new ModelPessoa(
                        cd_pessoa,
                        nm_pessoa,
                        fg_cliente,
                        fg_vendedor,
                        fg_fornecedor,
                        ds_endereco,
                        nr_endereco,
                        ds_bairro,
                        cd_estado,
                        cd_cidade,
                        cd_pais,
                        cd_cep,
                        ds_email,
                        nr_telefone,
                        cd_cgccpf,
                        cd_usuario,
                        dt_alt,
                        hr_alt,
                        dt_cad,
                        hr_cad,
                        cd_filial,
                        nr_inscricao_estadual,
                        tipo_consumo,
                        regime_tributacao,
                        fg_transportador,
                        fg_ativo
                );
                listaPessoa.add(pessoa);
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no sql, verificaDadosImpostos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaPessoa;
    }

}
