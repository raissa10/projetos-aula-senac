package View;

import Controller.CorDB;
import Controller.GrupoDB;
import Controller.GrupoFiscalDB;
import Controller.MarcaDB;
import Controller.ProdutoDB;
import Controller.SubGrupoDB;
import Controller.SubTabPrecoDB;
import Controller.UnidadeMedidaDB;
import Model.ProdutoSimples;
import Model.SubTabPreco;
import Estrutura.Conexao;
import Estrutura.TelaPadraoGlobal;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class CadProduto extends TelaPadraoGlobal {

    private static final String sqlBuscaProdutoPeloCodigo = "SELECT * FROM Produto_simples WHERE cd_prod=?";
    private static final String sqlBuscaPrecoProdutoPelaReferencia = "SELECT * FROM  SUB_TAB_PRECO WHERE CD_REF=?";
    private static final String sqlBuscaProdutoPelaReferencia = "SELECT * FROM Produto_simples WHERE cd_ref=?";
    private static final String sqlBuscaGrupo = "SELECT * FROM grupo WHERE cd_grupo=?";
    private static final String sqlBuscaSubGrupo = "SELECT  * FROM sub_grupo WHERE cd_grupo=? and cd_sub_grupo=?";
    private static final String sqlBuscaGrupoFiscal = "SELECT * FROM grupo_fiscal WHERE cd_grupo_fiscal=?";
    private static final String sqlBuscaCor = "SELECT * FROM COR WHERE CD_COR=?";
    private static final String sqlBuscaMarca = "SELECT * FROM MARCA WHERE CD_MARCA=?";
    private static final String sqlBuscaUnidadeMedida = "SELECT * FROM UNIDADE_MEDIDA WHERE CD_UNIDADE=?";

//Variaveis globais
    boolean VerificaHabilitacaoCampos = false;
//Import das classes necessárias
    ProdutoDB produtodb = new ProdutoDB();
    SubTabPrecoDB subtabprecodb = new SubTabPrecoDB();
    GrupoDB grupodb = new GrupoDB();
    SubGrupoDB subgrupodb = new SubGrupoDB();
    GrupoFiscalDB grupofiscaldb = new GrupoFiscalDB();
    CorDB cordb = new CorDB();
    MarcaDB marcadb = new MarcaDB();
    UnidadeMedidaDB unidademedidadb = new UnidadeMedidaDB();

    public CadProduto() {
        initComponents();
        centro();
        habilitaCampos(false);
        //desabilita os campos de Data
        edtDataCadastro.setEnabled(false);
        edtDataAlteracao.setEnabled(false);
        edtHoraCadastro.setEnabled(false);
        edtHoraAlteracao.setEnabled(false);
    }

    private void habilitaCampos(boolean habilita) {
        edtCodigo.requestFocus();
        edtCodigo.setEnabled(!habilita);
        edtReferencia.setEnabled(!habilita);
        edtDesricao.setEnabled(habilita);
        edtCodigoGrupo.setEnabled(habilita);
        edtCodigoSub_Grupo.setEnabled(habilita);
        edtCodigoGp_Fiscal.setEnabled(habilita);
        edtCodigoCor.setEnabled(habilita);
        edtCodigoMarca.setEnabled(habilita);
        edtCodigoNCMSH.setEnabled(habilita);
        edtCodigoFabrica.setEnabled(habilita);
        edtCodigoMarca.setEnabled(habilita);
        edtCodigoUnidadeMedida.setEnabled(habilita);
        //Combobox da Tela
        cbGrupo.setEnabled(habilita);
        cbSubGrupo.setEnabled(habilita);
        cbGrupoFiscal.setEnabled(habilita);
        cbMarca.setEnabled(habilita);
        cbCor.setEnabled(habilita);
        cbUnidadeMedida.setEnabled(habilita);

        btnConsultaGrupo.setEnabled(habilita);
        btnConsultaSubGrupo.setEnabled(habilita);
        btnConsultaGrupoFiscal.setEnabled(habilita);
        btnConsultaMarca.setEnabled(habilita);
        btnConsultaUnidadeMedida.setEnabled(habilita);
        btnConsultaCor.setEnabled(habilita);

        //Campos de preço e Margem
        edtPrecoCusto.setEnabled(habilita);
        edtPrecoVenda.setEnabled(habilita);
        edtPrecoPromocao.setEnabled(habilita);
        edtPrecoEspecial.setEnabled(habilita);
        edtCustoMedio.setEnabled(habilita);
        edtMargemVenda.setEnabled(habilita);
        edtMargemPromocao.setEnabled(habilita);
        edtMargemEspecial.setEnabled(habilita);
        RadioAtivo.setEnabled(habilita);
        btnGravar.setEnabled(habilita);
        btnCancelar.setEnabled(habilita);
        btnConsulta.setEnabled(!habilita);

        LabelEstoque.setEnabled(habilita);
        edtTXIPI.setEnabled(habilita);
        edtTXISS.setEnabled(habilita);        

        if (habilita) {
            VerificaHabilitacaoCampos = true;
            edtDesricao.requestFocus();

        } else {
            //Remover registros dos campos
            VerificaHabilitacaoCampos = false;
            LimpaTela();
        }
    }

    private void LimpaTela() {
        edtCodigo.setText("");
        edtReferencia.setText("");
        edtDesricao.setText("");
        edtCodigoGrupo.setText("");
        edtCodigoSub_Grupo.setText("");
        edtCodigoGp_Fiscal.setText("");
        edtCodigoCor.setText("");
        edtCodigoMarca.setText("");
        edtCodigoNCMSH.setText("");
        edtCodigoFabrica.setText("");
        edtCodigoUnidadeMedida.setText("");
        edtCodigo.grabFocus();
        RadioAtivo.setSelected(false);
        //Remocao dos dados dos combobox
        cbGrupo.removeAllItems();
        cbSubGrupo.removeAllItems();
        cbGrupoFiscal.removeAllItems();
        cbMarca.removeAllItems();
        cbCor.removeAllItems();
        cbUnidadeMedida.removeAllItems();
        //Remocao dos dados de preco
        edtPrecoCusto.setText("");
        edtPrecoVenda.setText("");
        edtPrecoPromocao.setText("");
        edtPrecoEspecial.setText("");
        edtCustoMedio.setText("");
        edtMargemVenda.setText("");
        edtMargemPromocao.setText("");
        edtMargemEspecial.setText("");
        LabelEstoque.setText("0,00");
        edtTXIPI.setText("");
        edtTXISS.setText("");
    }

    private void Excluir_Registro() {
        String auxTexto = edtCodigo.getText();
        if (auxTexto.equals("")) {
            JOptionPane.showMessageDialog(null, "Codigo ou Referencia deve ser maior que zero!");
            edtCodigo.grabFocus();
        } else {
            int auxCodigo = Integer.parseInt(edtCodigo.getText());
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?");
            if (resposta == JOptionPane.YES_OPTION) {
                if (produtodb.getProdutoCD_PROD(auxCodigo)) {
                    if (produtodb.excluirProduto(auxCodigo)) {
                        JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!");
                        LimpaTela();
                    } else {
                        JOptionPane.showMessageDialog(null, "Não foi possivel excluir o registro!!");
                        edtCodigo.grabFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Não existe no banco de dados o registro para excluir!");
                    edtCodigo.grabFocus();
                }
            }
        }
    }

    private void validaCodigoReferencia() {
        String auxCodigo = edtCodigo.getText();
        String auxReferencia = edtReferencia.getText();
        if (auxCodigo.equals("")) {
            if (auxReferencia.equals("")) {
                //Chama os Generators
                ValidaCodigoGenerator();
                ValidaReferenciaGenerator();
                habilitaCampos(true);
            } else {
                int referencia = Integer.parseInt(auxReferencia);
                if (referencia > 0) {
                    ValidaCampoReferenciaNaoNulo();
                    edtReferencia.grabFocus();
                } else {
                    edtDesricao.grabFocus();
                }
            }
        } else {
            ValidaCampoCodigoNaoNulo();
        }
    }

    private void GravarAlterarRegistro() {
        int auxCodigo = Integer.parseInt(edtCodigo.getText());
        int auxReferencia = Integer.parseInt(edtReferencia.getText());
        String auxDescricao = edtDesricao.getText();
        int auxGrupo = Integer.parseInt(edtCodigoGrupo.getText());
        int auxSubGrupo = Integer.parseInt(edtCodigoSub_Grupo.getText());
        int auxGrupo_Fiscal = Integer.parseInt(edtCodigoGp_Fiscal.getText());
        int auxCor = Integer.parseInt(edtCodigoCor.getText());
        int auxMarca = Integer.parseInt(edtCodigoMarca.getText());
        String auxNCMSH = edtCodigoNCMSH.getText();
        String auxCodFabrica = edtCodigoFabrica.getText();
        int auxFG_Ativo = 0;

        if (RadioAtivo.isSelected()) {
            auxFG_Ativo = 1;
        } else {
            auxFG_Ativo = 0;
        }

        int cd_usuario = 1;
        int cd_filial = 1;
        int cd_unidade_medida = Integer.parseInt(edtCodigoUnidadeMedida.getText());
        int qt_estoque = Integer.parseInt(LabelEstoque.getText());
        int tx_ipi = Integer.parseInt(edtTXIPI.getText());
        int tx_iss = Integer.parseInt(edtTXISS.getText());

        ProdutoSimples produto_simples = new ProdutoSimples(
                auxCodigo,
                auxDescricao,
                auxGrupo,
                auxSubGrupo,
                auxFG_Ativo,
                auxCor,
                auxCodFabrica,
                auxMarca,
                auxGrupo_Fiscal,
                auxNCMSH,
                auxReferencia,
                cd_usuario,
                cd_filial,
                cd_unidade_medida,
                qt_estoque,
                tx_ipi,
                tx_iss
        );

        int codigo = Integer.parseInt(edtCodigo.getText());
        int Referencia = Integer.parseInt(edtCodigo.getText());
        if ((produtodb.getProdutoCD_PROD(codigo)) || (produtodb.getProdutoCD_REF(Referencia))) {
            if (produtodb.alterarProduto(produto_simples)) {
                JOptionPane.showMessageDialog(null, "Registro alterado com sucesso!");
                habilitaCampos(false);
            } else {
                mensagemErro("Não foi possível alterar o registro!");
                edtCodigo.grabFocus();
            }
        } else {
            if (produtodb.inserirProduto(produto_simples)) {
                JOptionPane.showMessageDialog(null, "Registro incluído com sucesso!");
                habilitaCampos(false);
            } else {
                mensagemErro("Não foi possível incluir o registro!");
                edtCodigo.grabFocus();
            }
        }

    }

    private void ValidaCodigoGenerator() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            //Pega o Valor do Generator
            rs = stmt.executeQuery("SELECT GEN_ID(CD_PRODUTO, 1) FROM RDB$DATABASE");
            while (rs.next()) {
                int auxCodigoGenerator = rs.getInt("GEN_ID");
                int auxCodigo = auxCodigoGenerator + 1;
                String a;
                a = "" + auxCodigo;
                edtCodigo.setText(a);
            }
        } catch (SQLException erro) {
            System.out.println("Erro de conexão! " + erro);
        } finally {
            Conexao.closeAll(conn);
        }
    }

    private void ValidaReferenciaGenerator() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            //Pega o Valor do Generator
            rs = stmt.executeQuery("SELECT GEN_ID(CD_REF, 1) FROM RDB$DATABASE");
            while (rs.next()) {
                int auxCodigoGenerator = rs.getInt("GEN_ID");
                int auxCodigo = auxCodigoGenerator + 1;
                String a;
                a = "" + auxCodigo;
                edtReferencia.setText(a);
            }
        } catch (SQLException erro) {
            System.out.println("Erro de conexão! " + erro);
        } finally {
            Conexao.closeAll(conn);
        }
    }

    private void ValidaCampoReferenciaNaoNulo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_ref = Integer.parseInt(edtReferencia.getText());

        if (produtodb.getProdutoCD_REF(cd_ref)) {
            cbGrupo.setModel(grupodb.getComboGrupo());
            cbSubGrupo.setModel(subgrupodb.getComboSubGrupo());
            cbGrupoFiscal.setModel(grupofiscaldb.getComboGrupoFiscal());
            cbCor.setModel(cordb.getComboCor());
            cbMarca.setModel(marcadb.getComboMarca());
            cbUnidadeMedida.setModel(unidademedidadb.getComboUnidadeMedida());
            habilitaCampos(true);
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaProdutoPelaReferencia);
                //E passado por parametro o codigo do usuario resultante do SQL de nome "cd_ref"
                pstmt.setInt(1, cd_ref);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int CD_PROD = rs.getInt("CD_PROD");
                    String DS_PROD = rs.getString("DS_PROD");
                    int CD_GRUPO = rs.getInt("CD_GRUPO");
                    int CD_SUB_GRUPO = rs.getInt("CD_SUB_GRUPO");
                    int FG_ATIVO = rs.getInt("FG_ATIVO");
                    int CD_COR = rs.getInt("CD_COR");
                    String CD_FABRICA = rs.getString("CD_FABRICA");
                    int CD_MARCA = rs.getInt("CD_MARCA");
                    int CD_GP_FISCAL = rs.getInt("CD_GP_FISCAL");
                    String CD_NCM_SH = rs.getString("CD_NCM_SH");
                    int CD_REF = rs.getInt("CD_REF");
                    int CD_UNIDADE = rs.getInt("CD_UNIDADE_MEDIDA");
                    int qt_estoque = rs.getInt("qt_estoque");

                    //Conversao das variaveis para String
                    String auxCD_PROD = "" + CD_PROD;
                    String auxCD_GRUPO = "" + CD_GRUPO;
                    String auxCD_SUB_GRUPO = "" + CD_SUB_GRUPO;
                    // String auxFG_ATIVO = "" + FG_ATIVO;
                    String auxCD_COR = "" + CD_COR;
                    String auxCD_MARCA = "" + CD_MARCA;
                    String auxCD_GP_FISCAL = "" + CD_GP_FISCAL;
                    String auxCD_NCM_SH = "" + CD_NCM_SH;
                    String auxCD_REF = "" + CD_REF;
                    String auxDS_PROD = "" + DS_PROD;
                    String auxCD_UNIDADE = "" + CD_UNIDADE;
                    String auxqt_estoque = "" + qt_estoque;

                    //Validação do Ativo
                    if (FG_ATIVO == 1) {
                        RadioAtivo.setSelected(true);
                    } else {
                        RadioAtivo.setSelected(false);
                    }

                    edtCodigo.setText(auxCD_PROD);
                    edtReferencia.setText(auxCD_REF);
                    edtDesricao.setText(auxDS_PROD);
                    edtCodigoGrupo.setText(auxCD_GRUPO);
                    edtCodigoSub_Grupo.setText(auxCD_SUB_GRUPO);
                    edtCodigoGp_Fiscal.setText(auxCD_GP_FISCAL);
                    edtCodigoCor.setText(auxCD_COR);
                    edtCodigoMarca.setText(auxCD_MARCA);
                    edtCodigoNCMSH.setText(auxCD_NCM_SH);
                    edtCodigoFabrica.setText(CD_FABRICA);
                    edtCodigoUnidadeMedida.setText(auxCD_UNIDADE);
                    LabelEstoque.setText(auxqt_estoque);
                    edtReferencia.grabFocus();

                    //Carregamento de Codigos
                    ValidaCampoGrupoNaoNulo();
                    ValidaCampoSubGrupoNaoNulo();
                    ValidaCampoGrupoFiscalNaoNulo();
                    ValidaCampoCorNaoNulo();
                    ValidaCampoMarcaNaoNulo();
                    ValidaCampoUnidadeMedidaNaoNulo();
                    ListarPrecos();
                    edtReferencia.grabFocus();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro de conexão! " + erro);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Produto Não Cadastrado!");
            edtReferencia.grabFocus();
        }
    }

    private void ValidaCampoCodigoNaoNulo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_prod = 0;
        String auxTexto = edtCodigo.getText();
        if ((!auxTexto.equals(""))) {
            cd_prod = Integer.parseInt(auxTexto);
        }
        if (produtodb.getProdutoCD_PROD(cd_prod)) {
            cbGrupo.setModel(grupodb.getComboGrupo());
            cbSubGrupo.setModel(subgrupodb.getComboSubGrupo());
            cbGrupoFiscal.setModel(grupofiscaldb.getComboGrupoFiscal());
            cbCor.setModel(cordb.getComboCor());
            cbMarca.setModel(marcadb.getComboMarca());
            cbUnidadeMedida.setModel(unidademedidadb.getComboUnidadeMedida());

            habilitaCampos(true);
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaProdutoPeloCodigo);
                //E passado por parametro o codigo do usuario resultante do SQL de nome "cd_prod"
                pstmt.setInt(1, cd_prod);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int CD_PROD = rs.getInt("CD_PROD");
                    String DS_PROD = rs.getString("DS_PROD");
                    int CD_GRUPO = rs.getInt("CD_GRUPO");
                    int CD_SUB_GRUPO = rs.getInt("CD_SUB_GRUPO");
                    int FG_ATIVO = rs.getInt("FG_ATIVO");
                    int CD_COR = rs.getInt("CD_COR");
                    String CD_FABRICA = rs.getString("CD_FABRICA");
                    int CD_MARCA = rs.getInt("CD_MARCA");
                    int CD_GP_FISCAL = rs.getInt("CD_GP_FISCAL");
                    String CD_NCM_SH = rs.getString("CD_NCM_SH");
                    int CD_REF = rs.getInt("CD_REF");
                    int CD_UNIDADE = rs.getInt("CD_UNIDADE_MEDIDA");
                    int qt_estoque = rs.getInt("qt_estoque");
                    int tx_ipi = rs.getInt("tx_ipi");
                    int tx_iss = rs.getInt("tx_iss");

                    //Conversao das variaveis para String
                    String auxCD_PROD = "" + CD_PROD;
                    String auxCD_GRUPO = "" + CD_GRUPO;
                    String auxCD_SUB_GRUPO = "" + CD_SUB_GRUPO;
                    // String auxFG_ATIVO = "" + FG_ATIVO;
                    String auxCD_COR = "" + CD_COR;
                    String auxCD_MARCA = "" + CD_MARCA;
                    String auxCD_GP_FISCAL = "" + CD_GP_FISCAL;
                    String auxCD_NCM_SH = "" + CD_NCM_SH;
                    String auxCD_REF = "" + CD_REF;
                    String auxDS_PROD = "" + DS_PROD;
                    String auxCD_UNIDADE = "" + CD_UNIDADE;
                    String auxqt_estoque = "" + qt_estoque;
                    String auxTXIPI = "" + tx_ipi;
                    String auxTXISS = "" + tx_iss;

                    //Validação do Ativo
                    if (FG_ATIVO == 1) {
                        RadioAtivo.setSelected(true);
                    } else {
                        RadioAtivo.setSelected(false);
                    }

                    edtCodigo.setText(auxCD_PROD);
                    edtReferencia.setText(auxCD_REF);
                    edtDesricao.setText(auxDS_PROD);
                    edtCodigoGrupo.setText(auxCD_GRUPO);
                    edtCodigoSub_Grupo.setText(auxCD_SUB_GRUPO);
                    edtCodigoGp_Fiscal.setText(auxCD_GP_FISCAL);
                    edtCodigoCor.setText(auxCD_COR);
                    edtCodigoMarca.setText(auxCD_MARCA);
                    edtCodigoNCMSH.setText(auxCD_NCM_SH);
                    edtCodigoFabrica.setText(CD_FABRICA);
                    edtCodigoUnidadeMedida.setText(auxCD_UNIDADE);
                    LabelEstoque.setText(auxqt_estoque);
                    edtTXIPI.setText(auxTXIPI);
                    edtTXISS.setText(auxTXISS);
                    edtReferencia.grabFocus();

                    //Carregamento de Codigos
                    ValidaCampoGrupoNaoNulo();
                    ValidaCampoSubGrupoNaoNulo();
                    ValidaCampoGrupoFiscalNaoNulo();
                    ValidaCampoCorNaoNulo();
                    ValidaCampoMarcaNaoNulo();
                    ValidaCampoUnidadeMedidaNaoNulo();
                    ListarPrecos();
                    edtReferencia.grabFocus();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro de conexão! " + erro);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Produto Não Cadastrado!");
            edtCodigo.requestFocus();
        }
    }

    private void ValidaCampoGrupoNaoNulo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_grupo = 0;
        String auxTexto = edtCodigoGrupo.getText();
        if (!auxTexto.equals("")) {
            cd_grupo = Integer.parseInt(auxTexto);
        }
        if (grupodb.getGrupo(cd_grupo)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaGrupo);
                pstmt.setInt(1, cd_grupo);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxnomegrupo = rs.getString("DS_GRUPO");
                    cbGrupo.setSelectedItem(auxnomegrupo);
                }
            } catch (SQLException erro) {
                mensagemErro("Erro de conexão!\n Método ValidaCampoGrupoNaoNulo " + erro);
            }
        } else {
            mensagemErro("Grupo Não Cadastrado!");
            btnConsultaGrupoFiscal.grabFocus();
        }
    }

    private void ValidaCampoSubGrupoNaoNulo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_grupo = 0;
        int cd_sub_grupo = 0;
        String auxTexto = edtCodigoGrupo.getText();
        String auxTexto1 = edtCodigoSub_Grupo.getText();
        if ((!auxTexto.equals("")) && (!auxTexto1.equals(""))) {
            cd_grupo = Integer.parseInt(auxTexto);
            cd_sub_grupo = Integer.parseInt(auxTexto1);
        }

        if (subgrupodb.getSubGrupo(cd_grupo, cd_sub_grupo)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaSubGrupo);
                pstmt.setInt(1, cd_grupo);
                pstmt.setInt(2, cd_sub_grupo);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxnomegrupo = rs.getString("DS_SUB_GRUPO");
                    cbSubGrupo.setSelectedItem(auxnomegrupo);
                }
            } catch (SQLException erro) {
                mensagemErro("Erro de conexão! " + erro);
            }
        } else {
            mensagemErro("Sub-Grupo Não Cadastrado!");
            btnConsultaGrupoFiscal.grabFocus();
        }
    }

    private void ValidaCampoGrupoFiscalNaoNulo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_grupo_fiscal = 0;
        String auxTexto = edtCodigoGp_Fiscal.getText();
        if ((!auxTexto.equals(""))) {
            cd_grupo_fiscal = Integer.parseInt(auxTexto);
        }

        Integer.parseInt(edtCodigoGp_Fiscal.getText());
        if (grupofiscaldb.getGrupoFiscal(cd_grupo_fiscal)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaGrupoFiscal);
                pstmt.setInt(1, cd_grupo_fiscal);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxnomegrupo = rs.getString("DS_GRUPO_FISCAL");
                    cbGrupoFiscal.setSelectedItem(auxnomegrupo);
                }
            } catch (SQLException erro) {
                mensagemErro("Erro de conexão! " + erro);
            }
        } else {
            mensagemErro("Grupo Fiscal Não Cadastrado!");
            btnConsultaGrupoFiscal.grabFocus();
        }
    }

    private void ValidaCampoCorNaoNulo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_cor = 0;
        String auxTexto = edtCodigoCor.getText();
        if ((!auxTexto.equals(""))) {
            cd_cor = Integer.parseInt(auxTexto);
        }
        if (cordb.getCor(cd_cor)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaCor);
                pstmt.setInt(1, cd_cor);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxnome = rs.getString("DS_COR");
                    cbCor.setSelectedItem(auxnome);
                }
            } catch (SQLException erro) {
                mensagemErro("Erro de conexão! " + erro);
            }
        } else {
            mensagemErro("Cor Não Cadastrada!");
            btnConsultaGrupoFiscal.grabFocus();
        }
    }

    private void ValidaCampoMarcaNaoNulo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_marca = 0;
        String auxTexto = edtCodigoMarca.getText();
        if ((!auxTexto.equals(""))) {
            cd_marca = Integer.parseInt(auxTexto);
        }
        if (marcadb.getMarca(cd_marca)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaMarca);
                pstmt.setInt(1, cd_marca);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxnomegrupo = rs.getString("DS_MARCA");
                    cbMarca.setSelectedItem(auxnomegrupo);
                }
            } catch (SQLException erro) {
                mensagemErro("Erro de conexão! " + erro);
            }
        } else {
            mensagemErro("Marca Não Cadastrada!");
            btnConsultaGrupoFiscal.grabFocus();
        }
    }

    private void ValidaCampoUnidadeMedidaNaoNulo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_unidade = 0;
        String auxTexto = edtCodigoUnidadeMedida.getText();
        if (!auxTexto.equals("")) {
            cd_unidade = Integer.parseInt(auxTexto);
            if (unidademedidadb.getUnidadeMedida(cd_unidade)) {
                try {
                    conn = Conexao.getConexao();
                    pstmt = conn.prepareStatement(sqlBuscaUnidadeMedida);
                    pstmt.setInt(1, cd_unidade);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String auxnome = rs.getString("DS_UNIDADE");
                        cbUnidadeMedida.setSelectedItem(auxnome);
                    }
                } catch (SQLException erro) {
                    mensagemErro("Erro de conexão! " + erro);
                }
            } else {
                mensagemErro("Unidade de Medida Não Cadastrada!");
                btnConsultaUnidadeMedida.grabFocus();
            }
        }
    }

    private void ListarPrecos() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_ref = 0;
        String auxReferencia = edtReferencia.getText();
        if (!auxReferencia.equals("")) {
            cd_ref = Integer.parseInt(auxReferencia);
        }
        if (subtabprecodb.getSubTabPreco(cd_ref)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaPrecoProdutoPelaReferencia);
                pstmt.setInt(1, cd_ref);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int cd_referencia = rs.getInt("cd_ref");
                    double vl_custo = rs.getDouble("vl_custo");
                    double vl_venda = rs.getDouble("vl_venda");
                    double vl_promocao = rs.getDouble("vl_promocao");
                    double vl_especial = rs.getDouble("vl_especial");
                    double vl_custo_medio = rs.getDouble("vl_custo_med");
                    double tx_margem_lucro_venda = rs.getDouble("tx_margem_lucro_venda");
                    double tx_margem_lucro_promocao = rs.getDouble("tx_margem_lucro_promocao");
                    double tx_margem_lucro_especial = rs.getDouble("tx_margem_lucro_especial");
                    int cd_usuario = rs.getInt("cd_usuario");
                    int cd_filial = rs.getInt("cd_filial");

                    //Conversao das variaveis para String
                    String auxcd_referencia = "" + cd_referencia;//Não usa
                    String auxvl_custo = "" + vl_custo;
                    String auxvl_venda = "" + vl_venda;
                    String auxvl_promocao = "" + vl_promocao;
                    String auxvl_especial = "" + vl_especial;
                    String auxvl_custo_medio = "" + vl_custo_medio;
                    String auxtx_margem_lucro_venda = "" + tx_margem_lucro_venda;
                    String auxtx_margem_lucro_promocao = "" + tx_margem_lucro_promocao;
                    String auxtx_margem_lucro_especial = "" + tx_margem_lucro_especial;
                    String auxcd_usuario = "" + cd_usuario;//Não usa
                    String auxcd_filial = "" + cd_filial;//Não usa

                    edtPrecoCusto.setText(auxvl_custo);
                    edtPrecoVenda.setText(auxvl_venda);
                    edtPrecoPromocao.setText(auxvl_promocao);
                    edtPrecoEspecial.setText(auxvl_especial);
                    edtCustoMedio.setText(auxvl_custo_medio);
                    edtMargemVenda.setText(auxtx_margem_lucro_venda);
                    edtMargemPromocao.setText(auxtx_margem_lucro_promocao);
                    edtMargemEspecial.setText(auxtx_margem_lucro_especial);
                }
            } catch (SQLException erro) {
                mensagemErro("Erro de conexão! " + erro.getMessage());
                JOptionPane.showMessageDialog(null, "aqui de conexão!referencia: " + cd_ref);
            }
        } else {
            mensagemErro("Preço do Produto Não Cadastrado!");
            edtReferencia.grabFocus();
        }
    }

    private void GravarProduto() {
        String auxReferencia = edtReferencia.getText();
        String auxVlCusto = edtPrecoCusto.getText();
        String auxVLVenda = edtPrecoVenda.getText();
        String auxVlPromocao = edtReferencia.getText();
        String auxVlEspecial = edtPrecoCusto.getText();
        String auxCustoMedio = edtPrecoVenda.getText();
        String auxTXMargemVenda = edtReferencia.getText();
        String auxTXMargemPromocao = edtPrecoCusto.getText();
        String auxTXMargemEspecial = edtPrecoVenda.getText();

        //Validacao dos Campos
        double vl_custo = 0;
        double vl_venda = 0;
        double vl_promocao = 0;
        double vl_especial = 0;
        double vl_custo_medio = 0;
        double vl_margem_venda = 0;
        double vl_margem_promocao = 0;
        double vl_margem_especial = 0;
        boolean validaCamposPreco = true;
        //double vl_venda=Double.parseDouble(auxVLVenda);        
        if (auxVlCusto.equals("")) {
            mensagemErro("Preencha o Valor de Custo!");
            edtPrecoCusto.requestFocus();
            validaCamposPreco = false;
        } else {
            vl_custo = Double.parseDouble(auxVlCusto);
        }
        if (auxVLVenda.equals("")) {
            mensagemErro("Preencha o Valor de Venda!");
            edtPrecoVenda.requestFocus();
            validaCamposPreco = false;
        } else {
            vl_venda = Double.parseDouble(auxVLVenda);
        }
        if (auxVlPromocao.equals("")) {
            edtPrecoPromocao.setText("0,00");
        } else {
            vl_promocao = Double.parseDouble(auxVlPromocao);
        }
        if (auxVlEspecial.equals("")) {
            edtPrecoEspecial.setText("0,00");
        } else {
            vl_especial = Double.parseDouble(auxVlEspecial);
        }
        if (auxCustoMedio.equals("")) {
            edtCustoMedio.setText("0,00");
        } else {
            vl_custo_medio = Double.parseDouble(auxCustoMedio);
        }
        if (auxTXMargemVenda.equals("")) {
            edtMargemVenda.setText("0,00");
        } else {
            vl_margem_venda = Double.parseDouble(auxTXMargemVenda);
        }
        if (auxTXMargemPromocao.equals("")) {
            edtMargemPromocao.setText("0,00");
        } else {
            vl_margem_promocao = Double.parseDouble(auxTXMargemPromocao);
        }
        if (auxTXMargemEspecial.equals("")) {
            edtMargemEspecial.setText("0,00");
        } else {
            vl_margem_especial = Double.parseDouble(auxTXMargemEspecial);
        }

        int cd_usuario = 1;
        int cd_filial = 1;

        int referencia = Integer.parseInt(auxReferencia);

        SubTabPreco subtabpreco = new SubTabPreco(
                cd_filial,
                vl_custo,
                vl_venda,
                vl_promocao,
                vl_especial,
                vl_custo_medio,
                vl_margem_venda,
                vl_margem_promocao,
                vl_margem_especial,
                cd_usuario,
                cd_filial
        );
        if (validaCamposPreco) {
            if (subtabprecodb.getSubTabPreco(referencia)) {
                if (subtabprecodb.alterarSubTabPreco(subtabpreco)) {
                    //JOptionPane.showMessageDialog(null,"Sucesso ao alterar o preço!");
                    mensagemSucesso("Preço Alterado com sucesso!");
                    GravarAlterarValidado();
                } else {
                    mensagemErro("Não alterou o preço da referência: " + referencia);
                }
            } else {
                if (subtabprecodb.inserirSubTabPreco(subtabpreco)) {
                    //JOptionPane.showMessageDialog(null,"Sucesso ao alterar o preço!");
                    mensagemSucesso("Inserido preço com sucesso!");
                    GravarAlterarValidado();
                } else {
                    mensagemErro("Não inseriu o preço da referência: " + referencia);
                }
            }
        }
    }

    private void GravarAlterarValidado() {
        int auxCodigo = Integer.parseInt(edtCodigo.getText());
        int auxReferencia = Integer.parseInt(edtReferencia.getText());
        String auxDescricao = edtDesricao.getText();
        int auxGrupo = Integer.parseInt(edtCodigoGrupo.getText());
        int auxSubGrupo = Integer.parseInt(edtCodigoSub_Grupo.getText());
        int auxGrupo_Fiscal = Integer.parseInt(edtCodigoGp_Fiscal.getText());
        int auxCor = Integer.parseInt(edtCodigoCor.getText());
        int auxMarca = Integer.parseInt(edtCodigoMarca.getText());
        int auxNCMSH = Integer.parseInt(edtCodigoNCMSH.getText());
        String auxCodFabrica = edtCodigoFabrica.getText();
        if (auxCodigo <= 0) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Código do Produto!");
            edtCodigo.grabFocus();
        } else if (auxReferencia <= 0) {
            JOptionPane.showMessageDialog(null, "Favor Preencher a Referencia do Produto!");
            edtReferencia.grabFocus();
        } else if (auxDescricao.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher a Descricao do Produto!");
            edtDesricao.grabFocus();
        } else if (auxGrupo <= 0) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Grupo do Produto!");
            edtCodigoGrupo.grabFocus();
        } else if (auxSubGrupo <= 0) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Sub_Grupo do Produto!");
            edtCodigoSub_Grupo.grabFocus();
        } else if (auxGrupo <= 0) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Grupo do Produto!");
            edtCodigoGrupo.grabFocus();
        } else if (auxGrupo_Fiscal <= 0) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Grupo Fiscal do Produto!");
            edtCodigoGp_Fiscal.grabFocus();
        } else if (auxCor <= 0) {
            JOptionPane.showMessageDialog(null, "Favor Preencher a Cor do Produto!");
            edtCodigoCor.grabFocus();
        } else if (auxMarca <= 0) {
            JOptionPane.showMessageDialog(null, "Favor Preencher a Marca do Produto!");
            edtCodigoMarca.grabFocus();
        } else if (auxNCMSH <= 0) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o NCMSHs do Produto!");
            edtCodigoNCMSH.grabFocus();
        } else if (auxCodFabrica.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Codigo de Fabrica do Produto!");
            edtCodigoFabrica.grabFocus();
        } else {
            GravarAlterarRegistro();
        }
    }

    private void ComboBoxGrupo() {
        if (VerificaHabilitacaoCampos) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            String sql
                    = "select * from grupo where "
                    + "grupo.ds_grupo='" + cbGrupo.getSelectedItem() + "'";
            try {
                conn = Conexao.getConexao();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();
                String auxnome = rs.getString("ds_grupo");
                cbGrupo.setSelectedItem(auxnome);
            } catch (SQLException ex) {
                mensagemErro("Grupo não encontrado!Erro na funcao ComboBoxGrupo()()!:" + ex.getMessage());
            }
        }
    }

    private void ComboBoxSubGrupo() {
        if (VerificaHabilitacaoCampos) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            String sql
                    = "select * from sub_grupo where "
                    + "sub_grupo.ds_sub_grupo='" + cbSubGrupo.getSelectedItem() + "'";
            try {
                conn = Conexao.getConexao();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();
                String auxnome = rs.getString("ds_sub_grupo");
                cbSubGrupo.setSelectedItem(auxnome);
            } catch (SQLException ex) {
                mensagemErro("Funcao ComboBoxSubGrupo()!:" + ex.getMessage());
            }
        }
    }

    private void ComboBoxGrupoFiscal() {
        if (VerificaHabilitacaoCampos) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            String sql
                    = "select * from grupo_fiscal where "
                    + "grupo_fiscal.ds_grupo_fiscal='" + cbGrupoFiscal.getSelectedItem() + "'";
            try {
                conn = Conexao.getConexao();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();
                String auxnome = rs.getString("ds_grupo_fiscal");
                cbGrupoFiscal.setSelectedItem(auxnome);
            } catch (SQLException ex) {
                mensagemErro("Funcao ComboBoxGrupoFiscal()!:" + ex.getMessage());
            }
        }
    }

    private void ComboBoxCor() {
        if (VerificaHabilitacaoCampos) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            String sql
                    = "select * from cor where "
                    + "cor.ds_cor='" + cbCor.getSelectedItem() + "'";
            try {
                conn = Conexao.getConexao();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();
                String auxnome = rs.getString("ds_cor");
                cbCor.setSelectedItem(auxnome);
            } catch (SQLException ex) {
                mensagemErro("Funcao ComboBoxCor()!:" + ex.getMessage());
            }
        }
    }

    private void ComboBoxMarca() {
        if (VerificaHabilitacaoCampos) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            String sql
                    = "select * from marca where "
                    + "marca.ds_marca='" + cbMarca.getSelectedItem() + "'";
            try {
                conn = Conexao.getConexao();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();
                String auxnome = rs.getString("ds_marca");
                cbMarca.setSelectedItem(auxnome);
            } catch (SQLException ex) {
                mensagemErro("Funcao ComboBoxMarca()!:" + ex.getMessage());
            }
        }
    }

    private void ComboBoxUnidadeMedida() {
        if (VerificaHabilitacaoCampos) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            String sql
                    = "select * from Unidade_Medida where "
                    + "Unidade_Medida.ds_unidade='" + cbUnidadeMedida.getSelectedItem() + "'";
            try {
                conn = Conexao.getConexao();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();
                String auxnome = rs.getString("ds_unidade");
                cbUnidadeMedida.setSelectedItem(auxnome);
            } catch (SQLException ex) {
                mensagemErro("Funcao ComboBoxUnidadeMedida()!:" + ex.getMessage());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        edtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        labelRadioAtivo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        RadioAtivo = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        edtReferencia = new javax.swing.JTextField();
        edtDesricao = new javax.swing.JTextField();
        edtCodigoFabrica = new javax.swing.JTextField();
        edtCodigoGrupo = new javax.swing.JTextField();
        edtCodigoCor = new javax.swing.JTextField();
        edtCodigoSub_Grupo = new javax.swing.JTextField();
        edtCodigoMarca = new javax.swing.JTextField();
        edtCodigoGp_Fiscal = new javax.swing.JTextField();
        btnConsultaGrupo = new javax.swing.JButton();
        btnConsultaSubGrupo = new javax.swing.JButton();
        btnConsultaGrupoFiscal = new javax.swing.JButton();
        btnConsultaCor = new javax.swing.JButton();
        btnConsultaMarca = new javax.swing.JButton();
        edtCodigoUnidadeMedida = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbGrupo = new javax.swing.JComboBox();
        edtCodigoNCMSH = new javax.swing.JTextField();
        cbSubGrupo = new javax.swing.JComboBox();
        cbCor = new javax.swing.JComboBox();
        cbMarca = new javax.swing.JComboBox();
        cbGrupoFiscal = new javax.swing.JComboBox();
        cbUnidadeMedida = new javax.swing.JComboBox();
        btnConsultaUnidadeMedida = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        edtPrecoVenda = new javax.swing.JTextField();
        edtPrecoCusto = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        edtCustoMedio = new javax.swing.JTextField();
        edtMargemVenda = new javax.swing.JTextField();
        edtMargemPromocao = new javax.swing.JTextField();
        edtMargemEspecial = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        edtPrecoPromocao = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        edtPrecoEspecial = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        edtTXIPI = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        edtTXISS = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnGravar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        LabelEstoque = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        edtDataCadastro = new javax.swing.JFormattedTextField();
        edtDataAlteracao = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        edtHoraCadastro = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        edtHoraAlteracao = new javax.swing.JFormattedTextField();
        jPanel9 = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );

        setTitle("Cadastro de Produto");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Codigo:");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 14, 90, -1));

        edtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoKeyPressed(evt);
            }
        });
        jPanel5.add(edtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 11, 68, -1));

        jLabel2.setText("Descricao");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 45, 90, -1));

        labelRadioAtivo.setText("Ativo:");
        jPanel5.add(labelRadioAtivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 14, 37, -1));

        jLabel4.setText("Cor:");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 166, 90, -1));

        jLabel5.setText("Grupo:");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 74, 90, -1));

        jLabel6.setText("Sub_Grupo:");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 106, 90, -1));
        jPanel5.add(RadioAtivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, -1));

        jLabel8.setText("Marca:");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 196, 90, -1));

        jLabel9.setText("Grupo Fiscal:");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 136, 90, -1));

        jLabel10.setText("Referencia:");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 14, -1, -1));

        jLabel11.setText("NCM_SH:");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 90, -1));

        edtReferencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtReferenciaKeyPressed(evt);
            }
        });
        jPanel5.add(edtReferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 11, 190, -1));

        edtDesricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtDesricaoKeyPressed(evt);
            }
        });
        jPanel5.add(edtDesricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 42, 334, -1));

        edtCodigoFabrica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoFabricaKeyPressed(evt);
            }
        });
        jPanel5.add(edtCodigoFabrica, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 270, 120, -1));

        edtCodigoGrupo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoGrupoKeyPressed(evt);
            }
        });
        jPanel5.add(edtCodigoGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 72, 40, -1));

        edtCodigoCor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoCorKeyPressed(evt);
            }
        });
        jPanel5.add(edtCodigoCor, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 163, 40, -1));

        edtCodigoSub_Grupo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoSub_GrupoKeyPressed(evt);
            }
        });
        jPanel5.add(edtCodigoSub_Grupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 103, 40, -1));

        edtCodigoMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoMarcaKeyPressed(evt);
            }
        });
        jPanel5.add(edtCodigoMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 193, 41, -1));

        edtCodigoGp_Fiscal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoGp_FiscalKeyPressed(evt);
            }
        });
        jPanel5.add(edtCodigoGp_Fiscal, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 133, 40, -1));

        btnConsultaGrupo.setText("Consulta");
        btnConsultaGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaGrupoActionPerformed(evt);
            }
        });
        btnConsultaGrupo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnConsultaGrupoKeyPressed(evt);
            }
        });
        jPanel5.add(btnConsultaGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 71, 80, -1));

        btnConsultaSubGrupo.setText("Consulta");
        btnConsultaSubGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaSubGrupoActionPerformed(evt);
            }
        });
        btnConsultaSubGrupo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnConsultaSubGrupoKeyPressed(evt);
            }
        });
        jPanel5.add(btnConsultaSubGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 103, 80, -1));

        btnConsultaGrupoFiscal.setText("Consulta");
        btnConsultaGrupoFiscal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaGrupoFiscalActionPerformed(evt);
            }
        });
        btnConsultaGrupoFiscal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnConsultaGrupoFiscalKeyPressed(evt);
            }
        });
        jPanel5.add(btnConsultaGrupoFiscal, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 132, 80, -1));

        btnConsultaCor.setText("Consulta");
        btnConsultaCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaCorActionPerformed(evt);
            }
        });
        btnConsultaCor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnConsultaCorKeyPressed(evt);
            }
        });
        jPanel5.add(btnConsultaCor, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 161, 80, -1));

        btnConsultaMarca.setText("Consulta");
        btnConsultaMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaMarcaActionPerformed(evt);
            }
        });
        btnConsultaMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnConsultaMarcaKeyPressed(evt);
            }
        });
        jPanel5.add(btnConsultaMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 80, -1));

        edtCodigoUnidadeMedida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoUnidadeMedidaKeyPressed(evt);
            }
        });
        jPanel5.add(edtCodigoUnidadeMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 39, -1));

        jLabel12.setText("Unidade Medida");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 90, -1));

        cbGrupo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGrupoActionPerformed(evt);
            }
        });
        jPanel5.add(cbGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 289, -1));

        edtCodigoNCMSH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoNCMSHKeyPressed(evt);
            }
        });
        jPanel5.add(edtCodigoNCMSH, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 80, -1));

        cbSubGrupo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbSubGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSubGrupoActionPerformed(evt);
            }
        });
        jPanel5.add(cbSubGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 289, -1));

        cbCor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCorActionPerformed(evt);
            }
        });
        jPanel5.add(cbCor, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 289, -1));

        cbMarca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMarcaActionPerformed(evt);
            }
        });
        jPanel5.add(cbMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 289, -1));

        cbGrupoFiscal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbGrupoFiscal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGrupoFiscalActionPerformed(evt);
            }
        });
        jPanel5.add(cbGrupoFiscal, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 290, -1));

        cbUnidadeMedida.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbUnidadeMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUnidadeMedidaActionPerformed(evt);
            }
        });
        jPanel5.add(cbUnidadeMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 289, -1));

        btnConsultaUnidadeMedida.setText("Consulta");
        btnConsultaUnidadeMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaUnidadeMedidaActionPerformed(evt);
            }
        });
        btnConsultaUnidadeMedida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnConsultaUnidadeMedidaKeyPressed(evt);
            }
        });
        jPanel5.add(btnConsultaUnidadeMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, 80, -1));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Formação de Preços", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setText("Preço de Custo:");
        jPanel7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel17.setText("Preço de Venda:");
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        edtPrecoVenda.setText("0,00");
        jPanel7.add(edtPrecoVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 74, -1));

        edtPrecoCusto.setText("0,00");
        jPanel7.add(edtPrecoCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 74, -1));

        jLabel22.setText("Custo Médio:");
        jPanel7.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 80, -1));

        edtCustoMedio.setText("0,00");
        jPanel7.add(edtCustoMedio, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 60, -1));

        edtMargemVenda.setText("0,00");
        jPanel7.add(edtMargemVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 60, -1));

        edtMargemPromocao.setText("0,00");
        jPanel7.add(edtMargemPromocao, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, 60, -1));

        edtMargemEspecial.setText("0,00");
        jPanel7.add(edtMargemEspecial, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 150, 60, -1));

        jLabel23.setText("Tx.Margem Lucro Especial:");
        jPanel7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 150, -1));

        jLabel25.setText("Tx. Margem Lucro Promocao:");
        jPanel7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 160, -1));

        jLabel24.setText("Tx. Margem Lucro Venda:");
        jPanel7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 140, -1));

        edtPrecoPromocao.setText("0,00");
        jPanel7.add(edtPrecoPromocao, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 74, -1));

        jLabel20.setText("Preço Promoção:");
        jPanel7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jLabel21.setText("Preço Especial:");
        jPanel7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 90, -1));

        edtPrecoEspecial.setText("0,00");
        jPanel7.add(edtPrecoEspecial, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 74, -1));

        jPanel5.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 330, 410, 200));

        jLabel26.setText("Cód.Fabrica:");
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 90, -1));

        jLabel28.setText("Taxa de IPI:");
        jPanel5.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 90, -1));

        edtTXIPI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtTXIPIKeyPressed(evt);
            }
        });
        jPanel5.add(edtTXIPI, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 80, -1));

        jLabel29.setText("Taxa de ISS:");
        jPanel5.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 90, -1));

        edtTXISS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtTXISSKeyPressed(evt);
            }
        });
        jPanel5.add(edtTXISS, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 300, 120, -1));

        jTabbedPane1.addTab("Registro", jPanel5);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 570, 590));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setText("Cadastro de Produto");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, -1, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 640, 800, 20));

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Valid Green.png"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });
        btnGravar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGravarKeyPressed(evt);
            }
        });
        jPanel1.add(btnGravar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 120, 40));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        btnCancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCancelarKeyPressed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 120, 40));

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Remove Red.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        btnExcluir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnExcluirKeyPressed(evt);
            }
        });
        jPanel1.add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 120, 40));

        btnConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Search.png"))); // NOI18N
        btnConsulta.setText("Consulta");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });
        btnConsulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnConsultaKeyPressed(evt);
            }
        });
        jPanel1.add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 120, 40));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        jPanel1.add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 120, 40));

        jPanel8.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 170, 258));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LabelEstoque.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelEstoque.setText("0,00");
        jPanel4.add(LabelEstoque, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 70, 20));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Estoque:");
        jPanel4.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, -1));

        jPanel8.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 170, 40));

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setText("Data Cadastro:");

        edtDataCadastro.setEditable(false);
        edtDataCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtDataCadastroActionPerformed(evt);
            }
        });

        edtDataAlteracao.setEditable(false);
        edtDataAlteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtDataAlteracaoActionPerformed(evt);
            }
        });

        jLabel14.setText("Data Alteracao:");

        jLabel15.setText("Hora Cadasto:");

        edtHoraCadastro.setEditable(false);
        edtHoraCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtHoraCadastroActionPerformed(evt);
            }
        });

        jLabel19.setText("Hora Alteracao:");

        edtHoraAlteracao.setEditable(false);
        edtHoraAlteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtHoraAlteracaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edtDataAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(edtHoraCadastro)
                        .addComponent(edtHoraAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(edtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(edtDataAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(edtHoraCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtHoraAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 170, -1));

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 190, 560));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 60, 20, 520));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void edtDataCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtDataCadastroActionPerformed
        
    }//GEN-LAST:event_edtDataCadastroActionPerformed

    private void edtDataAlteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtDataAlteracaoActionPerformed
        
    }//GEN-LAST:event_edtDataAlteracaoActionPerformed

    private void edtHoraCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtHoraCadastroActionPerformed
        
    }//GEN-LAST:event_edtHoraCadastroActionPerformed

    private void edtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validaCodigoReferencia();
        }
    }//GEN-LAST:event_edtCodigoKeyPressed

    private void edtReferenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtReferenciaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validaCodigoReferencia();
        }
    }//GEN-LAST:event_edtReferenciaKeyPressed

    private void edtDesricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtDesricaoKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtCodigoGrupo.grabFocus();
        }
    }//GEN-LAST:event_edtDesricaoKeyPressed

    private void edtCodigoGrupoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoGrupoKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnConsultaGrupo.grabFocus();
            ValidaCampoGrupoNaoNulo();
        }
    }//GEN-LAST:event_edtCodigoGrupoKeyPressed

    private void edtCodigoSub_GrupoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoSub_GrupoKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ValidaCampoSubGrupoNaoNulo();
            btnConsultaSubGrupo.grabFocus();
        }
    }//GEN-LAST:event_edtCodigoSub_GrupoKeyPressed

    private void edtCodigoGp_FiscalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoGp_FiscalKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ValidaCampoGrupoFiscalNaoNulo();
            btnConsultaGrupoFiscal.grabFocus();
        }
    }//GEN-LAST:event_edtCodigoGp_FiscalKeyPressed

    private void edtCodigoCorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoCorKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ValidaCampoCorNaoNulo();
            btnConsultaCor.grabFocus();
        }
    }//GEN-LAST:event_edtCodigoCorKeyPressed

    private void edtCodigoMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoMarcaKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ValidaCampoMarcaNaoNulo();
            btnConsultaMarca.grabFocus();
        }
    }//GEN-LAST:event_edtCodigoMarcaKeyPressed

    private void edtCodigoFabricaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoFabricaKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnGravar.grabFocus();
        }
    }//GEN-LAST:event_edtCodigoFabricaKeyPressed

    private void btnConsultaGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaGrupoActionPerformed
        
    }//GEN-LAST:event_btnConsultaGrupoActionPerformed

    private void btnConsultaSubGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaSubGrupoActionPerformed
        
    }//GEN-LAST:event_btnConsultaSubGrupoActionPerformed

    private void btnConsultaGrupoFiscalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaGrupoFiscalActionPerformed
        
    }//GEN-LAST:event_btnConsultaGrupoFiscalActionPerformed

    private void btnConsultaCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaCorActionPerformed

    }//GEN-LAST:event_btnConsultaCorActionPerformed

    private void btnConsultaMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaMarcaActionPerformed
        
    }//GEN-LAST:event_btnConsultaMarcaActionPerformed

    private void btnConsultaGrupoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaGrupoKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtCodigoSub_Grupo.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaGrupoKeyPressed

    private void btnConsultaSubGrupoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaSubGrupoKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtCodigoGp_Fiscal.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaSubGrupoKeyPressed

    private void btnConsultaGrupoFiscalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaGrupoFiscalKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtCodigoCor.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaGrupoFiscalKeyPressed

    private void btnConsultaCorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaCorKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtCodigoMarca.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaCorKeyPressed

    private void btnConsultaMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaMarcaKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtCodigoNCMSH.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaMarcaKeyPressed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente gravar o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            //Gravar_Completo_Validado();
            GravarProduto();
        }
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnGravarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGravarKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnCancelar.grabFocus();
        }
    }//GEN-LAST:event_btnGravarKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Cancelar a Edição?");
        if (resposta == JOptionPane.YES_OPTION) {
            habilitaCampos(false);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnExcluir.grabFocus();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        
        //Excluir_Registro();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExcluirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExcluirKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnConsulta.grabFocus();
        }
    }//GEN-LAST:event_btnExcluirKeyPressed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        ConsultaProduto form = new ConsultaProduto(edtCodigo);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigo.grabFocus();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btnConsultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSair.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaKeyPressed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void edtCodigoUnidadeMedidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoUnidadeMedidaKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ValidaCampoUnidadeMedidaNaoNulo();
        }
    }//GEN-LAST:event_edtCodigoUnidadeMedidaKeyPressed

    private void edtCodigoNCMSHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoNCMSHKeyPressed
        
    }//GEN-LAST:event_edtCodigoNCMSHKeyPressed

    private void edtHoraAlteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtHoraAlteracaoActionPerformed
        
    }//GEN-LAST:event_edtHoraAlteracaoActionPerformed

    private void btnConsultaUnidadeMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaUnidadeMedidaActionPerformed
        
    }//GEN-LAST:event_btnConsultaUnidadeMedidaActionPerformed

    private void btnConsultaUnidadeMedidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaUnidadeMedidaKeyPressed
        
    }//GEN-LAST:event_btnConsultaUnidadeMedidaKeyPressed

    private void cbGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGrupoActionPerformed
        
        ComboBoxGrupo();
    }//GEN-LAST:event_cbGrupoActionPerformed

    private void cbSubGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSubGrupoActionPerformed
        
        ComboBoxSubGrupo();
    }//GEN-LAST:event_cbSubGrupoActionPerformed

    private void cbGrupoFiscalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGrupoFiscalActionPerformed
        
        ComboBoxGrupoFiscal();
    }//GEN-LAST:event_cbGrupoFiscalActionPerformed

    private void cbCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCorActionPerformed
        
        ComboBoxCor();
    }//GEN-LAST:event_cbCorActionPerformed

    private void cbMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMarcaActionPerformed
        
        ComboBoxMarca();
    }//GEN-LAST:event_cbMarcaActionPerformed

    private void cbUnidadeMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUnidadeMedidaActionPerformed
        
        ComboBoxUnidadeMedida();
    }//GEN-LAST:event_cbUnidadeMedidaActionPerformed

    private void edtTXIPIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtTXIPIKeyPressed
        
    }//GEN-LAST:event_edtTXIPIKeyPressed

    private void edtTXISSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtTXISSKeyPressed
        
    }//GEN-LAST:event_edtTXISSKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadProduto().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelEstoque;
    private javax.swing.JRadioButton RadioAtivo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnConsultaCor;
    private javax.swing.JButton btnConsultaGrupo;
    private javax.swing.JButton btnConsultaGrupoFiscal;
    private javax.swing.JButton btnConsultaMarca;
    private javax.swing.JButton btnConsultaSubGrupo;
    private javax.swing.JButton btnConsultaUnidadeMedida;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox cbCor;
    private javax.swing.JComboBox cbGrupo;
    private javax.swing.JComboBox cbGrupoFiscal;
    private javax.swing.JComboBox cbMarca;
    private javax.swing.JComboBox cbSubGrupo;
    private javax.swing.JComboBox cbUnidadeMedida;
    private javax.swing.JTextField edtCodigo;
    private javax.swing.JTextField edtCodigoCor;
    private javax.swing.JTextField edtCodigoFabrica;
    private javax.swing.JTextField edtCodigoGp_Fiscal;
    private javax.swing.JTextField edtCodigoGrupo;
    private javax.swing.JTextField edtCodigoMarca;
    private javax.swing.JTextField edtCodigoNCMSH;
    private javax.swing.JTextField edtCodigoSub_Grupo;
    private javax.swing.JTextField edtCodigoUnidadeMedida;
    private javax.swing.JTextField edtCustoMedio;
    private javax.swing.JFormattedTextField edtDataAlteracao;
    private javax.swing.JFormattedTextField edtDataCadastro;
    private javax.swing.JTextField edtDesricao;
    private javax.swing.JFormattedTextField edtHoraAlteracao;
    private javax.swing.JFormattedTextField edtHoraCadastro;
    private javax.swing.JTextField edtMargemEspecial;
    private javax.swing.JTextField edtMargemPromocao;
    private javax.swing.JTextField edtMargemVenda;
    private javax.swing.JTextField edtPrecoCusto;
    private javax.swing.JTextField edtPrecoEspecial;
    private javax.swing.JTextField edtPrecoPromocao;
    private javax.swing.JTextField edtPrecoVenda;
    private javax.swing.JTextField edtReferencia;
    private javax.swing.JTextField edtTXIPI;
    private javax.swing.JTextField edtTXISS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelRadioAtivo;
    // End of variables declaration//GEN-END:variables
}
