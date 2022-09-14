package Testes;

import ControleCadastro.PessoaDB;
import ControleCadastro.ProdutoDB;
import ControleFaturamento.CondicaoPagamentoDB;
import ControleFaturamento.TipoNotaDB;
import ControleFaturamento.VendaSimplesDB;
import ControleFaturamento.VendaProdutoDB;
import ModeloCadastro.ProdutoSimples;
import ModeloFaturamento.VendaClasse;
import ModeloFaturamento.VendaProdutoClassse;
import Principal.Conexao;
import Principal.MetodosGlobais;
import VisaoConsultasCadastro.ConsultaPessoa;
import VisaoConsultasFaturamento.ConsultaPedido;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author Gelvazio
 */
public class FormularioDAVPadrao extends MetodosGlobais {

    private static final String sqlBuscaPessoa = "SELECT * FROM PESSOA_SIMPLES WHERE cd_pessoa=?";
    private static final String sqlBuscaQuantidadeEPreco = "SELECT itens_orc_simples.* FROM itens_orc_simples WHERE cd_movimento=? and CD_SEQ_ITE_PRO=?";
    private static final String sqlBuscaCondicaoPagamento = "SELECT * FROM COND_PAG WHERE CD_COND=?";

    private static final String sqlBuscaCodigoVendedor = "SELECT * FROM pessoa_simples where pessoa_simples.cd_pessoa=?";
    private static final String sqlCodigoCliente = "SELECT * FROM pessoa_simples where pessoa_simples.cd_pessoa=?";

    private static final String sqlBuscaProduto = "SELECT * FROM produto_simples WHERE cd_ref=?";
    private static final String sqlBuscaTipoNota = "SELECT * FROM tipo_nota_simples WHERE CD_TIPO=?";
    private static final String sqlTodos = "SELECT * FROM PESSOA_SIMPLES";
    private static final String sqlBuscaVendaPeloMovimento = "SELECT * FROM ORCAMENTO_SIMPLES WHERE ORCAMENTO_SIMPLES.CD_MOVIMENTO=?";

    PessoaDB pes = new PessoaDB();
    PessoaDB pessoadb = new PessoaDB();
    CondicaoPagamentoDB condicaopagamentodb = new CondicaoPagamentoDB();
    TipoNotaDB tiponotadb = new TipoNotaDB();
    String auxnomeprodutoteste = "";
    Date data = new Date();
    int aux_codigo_tipo_nota;

    public FormularioDAVPadrao() {
        initComponents();
        Principal.MetodosGlobais p = new Principal.MetodosGlobais();
        p.Centro();
        habilitaCampos(false);
        edtCodigoMovimento.requestFocus();
        //Tela no Centro
        //setLocationRelativeTo(null);
        Centro();
        edtData_Saida.setEnabled(false);
        edtHora_Saida.setEnabled(false);
    }

    public void AdicionarVenda() {
        //Programacao Pronta Generator
        ValidaCodigoGenerator();
        //Habilitação dos campos
        habilitaCampos(true);

        //Combobox Tipo de Nota
        cbTipo_Nota.setModel(tiponotadb.getComboTipoNota());
        ComboBoxTipoNota();
        //Combobox Vendedor
        cbVendedor.setModel(pessoadb.getComboPessoa());
        ComboBoxVendedor();
        //Combobox Cliente
        cbCliente.setModel(pessoadb.getComboPessoa());
        ComboBoxCliente();
        //Condicao de Pagamento
        cbCondicaoPagamento.setModel(condicaopagamentodb.getComboCondPag());
        ComboBoxCondicaoPagamento();
        edtCliente.requestFocus();
    }

    private void GravarCompletoValidado() {
        String auxCodigo = edtCodigoMovimento.getText();
        String auxCodigoCliente = edtCodigoCliente.getText();
        String auxCodigoVendedor = edtCodigoVendedor.getText();
        String auxCodigoCondicaoPagamento = edtCodigoCondicaoPagamento.getText();
        String auxCodigoProduto = "";
        for (int x = 0; x < TabelaProdutos.getRowCount(); x++) {
            int CodigoProduto = (Integer.parseInt(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 0))));
            auxCodigoProduto = "" + CodigoProduto;
        }
        if (auxCodigo.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Código do movimento!");
            edtCodigoMovimento.requestFocus();
        } else if (auxCodigoCliente.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o codigo do cliente!");
            edtCodigoCliente.requestFocus();
        } else if (auxCodigoVendedor.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o codigo do vendedor!");
            edtCodigoVendedor.requestFocus();
        } else if (auxCodigoCondicaoPagamento.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o codigo da condição de pagamento!");
            edtCodigoCondicaoPagamento.requestFocus();
        } else if (auxCodigoProduto.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher pelo menos um produto!");
            edtCodigoProduto.requestFocus();
        } else {
            GravarAlterarRegistro();
        }
    }

    private void GravarAlterarRegistro() {
        String auxTexto = edtCodigoMovimento.getText();
        int codigo = Integer.parseInt(auxTexto);
        VendaSimplesDB vendadb = new VendaSimplesDB();
        if (vendadb.getVenda(codigo)) {
            AlterarRegistro();
        } else {
            GravarRegistro();
        }
    }

    public void ListaVendaCompleto() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        VendaSimplesDB vendadb = new VendaSimplesDB();
        int cd_movimento = Integer.parseInt(edtCodigoMovimento.getText());

        if (vendadb.getVenda(cd_movimento)) {
            //Habilitação dos campos
            habilitaCampos(true);
            //Combobox Tipo de Nota
            cbTipo_Nota.setModel(tiponotadb.getComboTipoNota());
            ComboBoxTipoNota();
            //Combobox Vendedor
            cbVendedor.setModel(pessoadb.getComboPessoa());
            ComboBoxVendedor();
            //Combobox Cliente
            cbCliente.setModel(pessoadb.getComboPessoa());
            ComboBoxCliente();
            //Condicao de Pagamento
            cbCondicaoPagamento.setModel(condicaopagamentodb.getComboCondPag());
            ComboBoxCondicaoPagamento();
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaVendaPeloMovimento);
                pstmt.setInt(1, cd_movimento);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int cd_tipo_nota = rs.getInt("cd_tipo_doc");
                    int cd_pessoa = rs.getInt("cd_pessoa");
                    int cd_vendedor = rs.getInt("cd_vende");
                    int cd_pagamento = rs.getInt("cd_pagto");

                    // double acrescimo= rs.getDouble("CD_MARCA");
                    // double desconto= rs.getDouble("CD_MARCA");
                    double total = rs.getDouble("VL_TOT_PRO_DOC");

                    String data_saida = rs.getString("dt_sai_doc");
                    String hora_saida = rs.getString("hr_alt");

                    //Conversao das variaveis para String
                    String auxcd_tipo_nota = "" + cd_tipo_nota;
                    String auxcd_pessoa = "" + cd_pessoa;
                    String auxcd_vendedor = "" + cd_vendedor;
                    String auxcd_pagamento = "" + cd_pagamento;

                    //Fazer Depois a busca so do tipo de nota
                    //cbTipo_Nota.setModel(null);
                    //Combobox Tipo de Nota
                    cbTipo_Nota.setModel(tiponotadb.getComboTipoNota());
                    ComboBoxTipoNota();

                    edtCodigoCliente.setText(auxcd_pessoa);
                    edtCodigoVendedor.setText(auxcd_vendedor);
                    edtCodigoCondicaoPagamento.setText(auxcd_pagamento);
                    edtData_Saida.setText(data_saida);
                    edtHora_Saida.setText(hora_saida);

                    ValidaCampoCodigoClienteNãoNulo();
                    ValidaCampoCodigoVendedorNãoNulo();
                    ValidaCampoCodigoCondicaoPagamentoNãoNulo();

                    //Aqui comeca a carregar os itens
                    ListaItensCompleto();
                    edtCodigoCliente.requestFocus();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro de conexão! " + erro);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Venda não cadastrada!");
            habilitaCampos(false);
            limparTela();
        }
    }

    public ArrayList SQLCarregaNomeItens() {
        String auxtexto = edtCodigoMovimento.getText();
        int codigomovimento = Integer.parseInt(auxtexto);
        String SQLConsulta_itens_dav = "	select	 produto_simples.*	"
                + "	from	"
                + "	    itens_orc_simples	"
                + "	    left outer join produto_simples	"
                + "	    on produto_simples.cd_ref=itens_orc_simples.cd_refer_pro	"
                + "	where	"
                + "	    itens_orc_simples.cd_movimento=?	";

        ArrayList listaProduto = new ArrayList();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(SQLConsulta_itens_dav);
            pstmt.setInt(1, codigomovimento);
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
                int cd_usuario = rs.getInt("cd_usuario");
                int cd_filial = rs.getInt("cd_filial");
                int cd_unidade_medida = rs.getInt("cd_unidade_medida");
                int qt_estoque = rs.getInt("qt_estoque");
                int tx_ipi = rs.getInt("tx_ipi");
                int tx_iss = rs.getInt("tx_iss");

                ProdutoSimples produto = new ProdutoSimples(
                        CD_PROD,
                        DS_PROD,
                        CD_GRUPO,
                        CD_SUB_GRUPO,
                        FG_ATIVO,
                        CD_COR,
                        CD_FABRICA,
                        CD_MARCA,
                        CD_GP_FISCAL,
                        CD_NCM_SH,
                        CD_REF,
                        cd_usuario,
                        cd_filial,
                        cd_unidade_medida,
                        qt_estoque,
                        tx_ipi,
                        tx_iss
                );
                listaProduto.add(produto);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql,  SQLConsultagetTodos_Completo_NomeProduto(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaProduto;
    }

    public void ListaItensCompleto() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nome");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Preco");
        habilitaCampos(true);
        int aux_sequencia = 1;//Pega a sequencia
        int aux_contador = 1;   //Pega a sequencia do item
        int aux_movimento = Integer.parseInt(edtCodigoMovimento.getText());
        ArrayList<ProdutoSimples> produtosimples = SQLCarregaNomeItens();

        VendaProdutoDB venprodb = new VendaProdutoDB();
        //aqui verifica se existe item desta venda
        if (venprodb.getProdutoDaVenda(aux_movimento, aux_sequencia)) {
            aux_sequencia = 0;
            for (ProdutoSimples auxprodutosimples : produtosimples) {
                modelo.addRow(new Object[]{
                    auxprodutosimples.getCd_ref(),
                    auxprodutosimples.getDs_prod()
                });
                TabelaProdutos.setModel(modelo);
                //Programacao do valor e quantidade
                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                //JOptionPane.showMessageDialog(null, "Valor de sequencia na linha: " + aux_sequencia);
                for (int x = aux_sequencia; x < TabelaProdutos.getRowCount(); x++) {
                    try {
                        conn = Conexao.getConexao();
                        pstmt = conn.prepareStatement(sqlBuscaQuantidadeEPreco);
                        pstmt.setInt(1, aux_movimento);
                        pstmt.setInt(2, aux_contador);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            int aux_quantidade = rs.getInt("QT_ITE_PRO");
                            double aux_preco = rs.getDouble("VL_VEN_ITE_PRO");
                            TabelaProdutos.getModel().setValueAt(aux_quantidade, x, 2);
                            TabelaProdutos.getModel().setValueAt(aux_preco, x, 3);
                        }
                    } catch (SQLException erro) {
                        JOptionPane.showMessageDialog(null, "Erro no sql, Nome Produto Itens:\n" + erro.getMessage());
                    } finally {
                        Conexao.closeAll(conn);
                    }
                }
                aux_sequencia++;//Incrementa a sequencia
                aux_contador++; //Incrementa a sequencia do banco de dados               
            }

        } else {
            JOptionPane.showMessageDialog(null, "Não existe itens nesta venda!");
            edtCodigoProduto.requestFocus();
        }
    }

    public void GravarRegistro() {
        int auxcd_filial = 1;
        int auxcd_movimento = (Integer.parseInt(edtCodigoMovimento.getText()));
        int auxcd_vendedor = (Integer.parseInt(edtCodigoVendedor.getText()));
        int auxcd_pagto = Integer.parseInt(edtCodigoCondicaoPagamento.getText());
        int auxcd_pessoa = (Integer.parseInt(edtCodigoCliente.getText()));

        double auxvl_custototaldoc = 100;
        double auxvl_totaldoc = 200;
        int aux_cd_tipo_nota = 1;
        int auxfg_situacao = 1;
        int auxcd_usuario = 1;
        VendaSimplesDB vendadb = new VendaSimplesDB();
        VendaClasse vendaclasse = new VendaClasse(
                auxcd_filial,
                auxcd_movimento,
                auxcd_usuario,
                auxcd_vendedor,
                auxcd_pagto,
                auxcd_pessoa,
                auxvl_custototaldoc,
                auxvl_totaldoc,
                auxfg_situacao,
                aux_cd_tipo_nota
        );
        if (vendadb.gravarVenda(vendaclasse)) {
            JOptionPane.showMessageDialog(null, "Venda incluída com sucesso!");
            //Parte Item já ta programado
            int auxsequencia = 1;
            for (int x = 0; x < TabelaProdutos.getRowCount(); x++) {
                // JOptionPane.showMessageDialog(null, "Entrou aqui: for (int x = 0; x < TabelaProdutos.getRowCount(); x++)");
                double auxvl_custo = 10;
                double aux_vl_preco = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 3))));
                int aux_cd_referencia = (Integer.parseInt(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 0))));
                int aux_quantidade = (Integer.parseInt(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 2))));

                VendaProdutoDB venprodb = new VendaProdutoDB();
                VendaProdutoClassse venclasse = new VendaProdutoClassse(
                        aux_quantidade,
                        aux_vl_preco,
                        aux_cd_referencia,
                        auxcd_filial,
                        auxcd_movimento,
                        auxsequencia,
                        auxcd_usuario,
                        auxvl_custo);
                if (venprodb.gravarProdutoDaVenda(venclasse)) {
                    //JOptionPane.showMessageDialog(null, "Produtos incluídos com sucesso!");
                    //LimpaTela();
                    //Incrementar a programacao de habilita e desabilita campos
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possível incluir o os Produtos!");
                    //edtCodigo.grabFocus();
                }
                auxsequencia++;
            }
            habilitaCampos(false);
            limparTela();
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível gravar a venda!");
        }
    }

    public void AlterarRegistro() {
        int auxcd_filial = 1;
        int auxcd_movimento = (Integer.parseInt(edtCodigoMovimento.getText()));
        int auxcd_vendedor = (Integer.parseInt(edtCodigoVendedor.getText()));
        int auxcd_pagto = Integer.parseInt(edtCodigoCondicaoPagamento.getText());
        int auxcd_pessoa = (Integer.parseInt(edtCodigoCliente.getText()));

        double auxvl_custototaldoc = 100.0;
        double auxvl_totaldoc = 200.0;
        int auxfg_situacao = 1;
        int auxcd_usuario = 1;
        int aux_cd_tipo_nota = 1;
        VendaSimplesDB vendadb = new VendaSimplesDB();
        VendaClasse vendaclasse = new VendaClasse(
                auxcd_filial,
                auxcd_movimento,
                auxcd_usuario,
                auxcd_vendedor,
                auxcd_pagto,
                auxcd_pessoa,
                auxvl_custototaldoc,
                auxvl_totaldoc,
                auxfg_situacao,
                aux_cd_tipo_nota
        );

        if (vendadb.alterarVenda(vendaclasse)) {
            int auxsequencia = 1;
            for (int x = 0; x < TabelaProdutos.getRowCount(); x++) {
                double auxvl_custo = 10;
                double aux_vl_preco = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 3))));
                int aux_cd_referencia = (Integer.parseInt(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 0))));
                int aux_quantidade = (Integer.parseInt(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 2))));

                VendaProdutoClassse venclasse = new VendaProdutoClassse(
                        aux_quantidade,
                        aux_vl_preco,
                        aux_cd_referencia,
                        auxcd_filial,
                        auxcd_movimento,
                        auxsequencia,
                        auxcd_usuario,
                        auxvl_custo);
                //Fazer verificação de item que ja existe
                VendaProdutoDB venprodb = new VendaProdutoDB();
                if (venprodb.getProdutoDaVenda(auxcd_movimento, auxsequencia)) {
                    if (venprodb.alterarProdutoDaVenda(venclasse)) {
                        //JOptionPane.showMessageDialog(null, "Produtos alterados com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Não foi possível alterar o Produto!");
                    }
                } else {
                    if (venprodb.gravarProdutoDaVenda(venclasse)) {
                        //JOptionPane.showMessageDialog(null, "Produtos incluídos com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Não foi possível incluir o os Produtos!");
                    }
                }
                //Incrementa a sequencia do formulario
                auxsequencia++;
            }
            habilitaCampos(false);
            limparTela();
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar a venda!");
        }
    }

    private void limparTela() {
        edtDescricao.setText("");
        edtCodigoMovimento.setText("");
        edtCodigoCliente.setText("");
        edtCodigoVendedor.setText("");
        edtValorUnitario.setText("");
        edtCodigoProduto.setText("");
        edtQuantidade.setText("");
        edtCodigoCondicaoPagamento.setText("");
        edtData_Saida.setText("");
        edtHora_Saida.setText("");

        DefaultTableModel model = (DefaultTableModel) TabelaProdutos.getModel();
        model.setNumRows(0);
        cbCliente.removeAllItems();
        cbVendedor.removeAllItems();
        cbCondicaoPagamento.removeAllItems();
        cbCondicaoPagamento.removeAllItems();
        cbTipo_Nota.removeAllItems();
        edtCodigoMovimento.requestFocus();
    }

    private void ValidaCodigoGenerator() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT GEN_ID(CD_ORC, 1) FROM RDB$DATABASE");
            while (rs.next()) {
                int auxCodigoGenerator = rs.getInt("GEN_ID");
                int auxCodigo = auxCodigoGenerator + 1;
                String a;
                a = "" + auxCodigo;
                edtCodigoMovimento.setText(a);
                cbTipo_Nota.grabFocus();
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de conexão! \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
    }

    private void habilitaCampos(boolean habilita) {
        edtCodigoMovimento.requestFocus();
        edtCodigoMovimento.setEnabled(!habilita);
        edtCodigoCliente.setEnabled(habilita);
        edtCodigoVendedor.setEnabled(habilita);
        edtValorUnitario.setEnabled(habilita);
        edtCodigoProduto.setEnabled(habilita);
        edtQuantidade.setEnabled(habilita);
        edtDescricao.setEnabled(habilita);
        edtCodigoCondicaoPagamento.setEnabled(habilita);
        btnGravar.setEnabled(habilita);
        btnCancelar.setEnabled(habilita);
        btnConsulta.setEnabled(habilita);
        btnSair.setEnabled(!habilita);
        btnConsultaCliente.setEnabled(habilita);
        btnConsultaVendedor.setEnabled(habilita);
        btnConsultaCondicaoPagamento.setEnabled(habilita);

        cbCliente.setEnabled(habilita);
        cbTipo_Nota.setEnabled(habilita);
        cbCondicaoPagamento.setEnabled(habilita);
        cbVendedor.setEnabled(habilita);

        if (habilita) {
            edtCliente.requestFocus();
        } else {            //Remover registros dos campos

        }
    }

    public void ComboBoxTipoNota() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql
                = "select * from tipo_nota_simples where "
                + "tipo_nota_simples.ds_tipo_nota='" + cbTipo_Nota.getSelectedItem() + "'";
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            aux_codigo_tipo_nota = rs.getInt("cd_tipo");

        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Tipo de Nota não encontrado!Erro na funcao ComboBoxTipoNota()!:" + ex.getMessage());
        }
    }

    public void ComboBoxVendedor() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "select * from pessoa_simples where pessoa_simples.nm_pessoa= '" + cbVendedor.getSelectedItem() + "'";
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            int auxcd_pessoa = rs.getInt("cd_pessoa");
            String aux = "";
            aux = "" + auxcd_pessoa;
            edtCodigoVendedor.setText(aux);
            edtCodigoVendedor.setVisible(true);
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Vendedor não encontrado!Erro na funcao ComboBoxVendedor()!:" + ex.getMessage());
        }
    }

    public void ComboBoxCliente() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "select * from pessoa_simples where pessoa_simples.nm_pessoa= '" + cbCliente.getSelectedItem() + "'";
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            int auxcd_pessoa = rs.getInt("cd_pessoa");
            String aux = "";
            aux = "" + auxcd_pessoa;
            edtCodigoCliente.setText(aux);
            edtCodigoCliente.setVisible(true);
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Cliente não encontrado!Erro na funcao ComboBoxCliente()!:" + ex.getMessage());
        }
    }

    public void ComboBoxCondicaoPagamento() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM COND_PAG WHERE ds_cond='" + cbCondicaoPagamento.getSelectedItem() + "'";
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            int auxcd_condicaopagamento = rs.getInt("cd_cond");
            String aux = "";
            aux = "" + auxcd_condicaopagamento;
            edtCodigoCondicaoPagamento.setText(aux);
            edtCodigoCondicaoPagamento.setVisible(true);
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Condicao de Pagamento não encontrada!Erro na funcao ComboBoxCondicaoPagamento()!:" + ex.getMessage());
        }
    }

    public DefaultComboBoxModel ValidaCampoTipoNota() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_tipo = 1;//Passado Fixos
        if (tiponotadb.getTipoNota(cd_tipo)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaTipoNota);
                pstmt.setInt(1, cd_tipo);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    modelo.addElement(rs.getString("ds_tipo_nota"));
                    cbTipo_Nota.setModel(modelo);
                    edtCodigoCliente.grabFocus();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro no sql, ValidaCampoTipoNota(): \n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tipo de Nota 1 não cadastrado!");
            edtCodigoMovimento.requestFocus();
        }
        return modelo;
    }

    public DefaultComboBoxModel ValidaCampoCodigoClienteNãoNulo() {
        String auxTexto = edtCodigoCliente.getText();
        int cd_pessoa = Integer.parseInt(auxTexto);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (pessoadb.getPessoa(cd_pessoa)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaPessoa);
                pstmt.setInt(1, cd_pessoa);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxNome = rs.getString("nm_pessoa");
                    cbCliente.setSelectedItem(auxNome);
                    edtCodigoVendedor.grabFocus();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro no sql, getComboPessoa(): \n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pessoa nao cadastrada");
            edtCodigoCliente.requestFocus();
        }
        return modelo;
    }

    public DefaultComboBoxModel ValidaCampoCodigoVendedorNãoNulo() {
        String auxTexto = edtCodigoVendedor.getText();
        int cd_pessoa = Integer.parseInt(auxTexto);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (pessoadb.getPessoa(cd_pessoa)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaPessoa);
                pstmt.setInt(1, cd_pessoa);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxNome = rs.getString("nm_pessoa");
                    cbVendedor.setSelectedItem(auxNome);
                    edtCodigoCondicaoPagamento.grabFocus();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro no sql, ValidaCampoCodigoVendedorNãoNulo() \n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vendedor nao cadastrado!");
            edtCodigoVendedor.requestFocus();
        }
        return modelo;
    }

    public DefaultComboBoxModel ValidaCampoCodigoCondicaoPagamentoNãoNulo() {
        String auxTexto = edtCodigoCondicaoPagamento.getText();
        int cd_cond = Integer.parseInt(auxTexto);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (condicaopagamentodb.getCondicaoPagamento(cd_cond)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaCondicaoPagamento);
                pstmt.setInt(1, cd_cond);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxNome = rs.getString("DS_COND");
                    cbCondicaoPagamento.setSelectedItem(auxNome);
                    edtCodigoProduto.grabFocus();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro no sql, ValidaCampoCodigoCondicaoPagamentoNãoNulo():\n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Condicao de Pagamento nao cadastrada!");
            edtCodigoCondicaoPagamento.requestFocus();
        }
        return modelo;
    }

    public boolean ValidaCampoCodigoProdutoNaoNulo() {
        ProdutoDB produtosimplesdb = new ProdutoDB();
        String auxTexto = edtCodigoProduto.getText();
        int cd_ref = Integer.parseInt(auxTexto);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (produtosimplesdb.getProdutoCD_REF(cd_ref)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaProduto);
                pstmt.setInt(1, cd_ref);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxnome = rs.getString("ds_prod");
                    edtDescricao.setText(auxnome);
                    edtQuantidade.grabFocus();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro no sql, ValidaCampoCodigoProdutoNaoNulo():\n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Produto nao cadastrado!");
            edtCodigoProduto.requestFocus();
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaProdutos = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        edtCodigoMovimento = new javax.swing.JTextField();
        cbTipo_Nota = new javax.swing.JComboBox();
        edtData_Saida = new javax.swing.JFormattedTextField();
        edtHora_Saida = new javax.swing.JFormattedTextField();
        edtCliente = new javax.swing.JLabel();
        edtCondicao_Pagamento = new javax.swing.JLabel();
        cbCliente = new javax.swing.JComboBox();
        cbVendedor = new javax.swing.JComboBox();
        cbCondicaoPagamento = new javax.swing.JComboBox();
        btnConsultaCliente = new javax.swing.JButton();
        btnConsultaCondicaoPagamento = new javax.swing.JButton();
        edtCodigoCliente = new javax.swing.JTextField();
        btnConsultaVendedor = new javax.swing.JButton();
        edtCodigoVendedor = new javax.swing.JTextField();
        edtVendedor = new javax.swing.JLabel();
        edtCodigoCondicaoPagamento = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        edtQuantidade = new javax.swing.JTextField();
        edtValorUnitario = new javax.swing.JTextField();
        edtCodigoProduto = new javax.swing.JTextField();
        edtDescricao = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelaProdutos1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnGravar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form_Padrao");

        TabelaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descrição", "Quantidade", "Valor"
            }
        ));
        TabelaProdutos.setEnabled(false);
        jScrollPane1.setViewportView(TabelaProdutos);
        if (TabelaProdutos.getColumnModel().getColumnCount() > 0) {
            TabelaProdutos.getColumnModel().getColumn(3).setMinWidth(100);
            TabelaProdutos.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jLabel4.setText("Movimento");

        jLabel5.setText("Tipo Nota");

        jLabel6.setText("Data Saída");

        jLabel7.setText("Hora Saída");

        edtCodigoMovimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoMovimentoKeyPressed(evt);
            }
        });

        cbTipo_Nota.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        edtData_Saida.setEditable(false);
        edtData_Saida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));

        edtHora_Saida.setEditable(false);
        edtHora_Saida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));

        edtCliente.setText("Cliente");

        edtCondicao_Pagamento.setText("Condição Pagamento");

        cbCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbClienteMouseClicked(evt);
            }
        });
        cbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbClienteActionPerformed(evt);
            }
        });

        cbVendedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVendedorActionPerformed(evt);
            }
        });

        cbCondicaoPagamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCondicaoPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCondicaoPagamentoActionPerformed(evt);
            }
        });

        btnConsultaCliente.setText("...");
        btnConsultaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaClienteActionPerformed(evt);
            }
        });

        btnConsultaCondicaoPagamento.setText("...");
        btnConsultaCondicaoPagamento.setSelected(true);
        btnConsultaCondicaoPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaCondicaoPagamentoActionPerformed(evt);
            }
        });

        edtCodigoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoClienteKeyPressed(evt);
            }
        });

        btnConsultaVendedor.setText("...");
        btnConsultaVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaVendedorActionPerformed(evt);
            }
        });

        edtCodigoVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoVendedorKeyPressed(evt);
            }
        });

        edtVendedor.setText("Vendedor");

        edtCodigoCondicaoPagamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoCondicaoPagamentoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(161, 161, 161)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(edtCliente)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(edtVendedor)
                        .addGap(284, 284, 284)
                        .addComponent(edtCondicao_Pagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(edtCodigoMovimento, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(cbTipo_Nota, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(edtData_Saida, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(edtHora_Saida, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(edtCodigoVendedor)
                            .addComponent(edtCodigoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConsultaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(btnConsultaVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(cbVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(edtCodigoCondicaoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConsultaCondicaoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbCondicaoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edtCodigoMovimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbTipo_Nota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edtData_Saida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edtHora_Saida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(edtCliente)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(edtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(btnConsultaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edtVendedor)
                    .addComponent(edtCondicao_Pagamento))
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edtCodigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsultaVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(edtCodigoCondicaoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnConsultaCondicaoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbCondicaoPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel8.setText("Produto");

        jLabel9.setText("Valor Unitario");

        jLabel10.setText("Quantidade");

        edtQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtQuantidadeKeyPressed(evt);
            }
        });

        edtValorUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtValorUnitarioKeyPressed(evt);
            }
        });

        edtCodigoProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoProdutoKeyPressed(evt);
            }
        });

        edtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtDescricaoKeyPressed(evt);
            }
        });

        jLabel11.setText("Descrição:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(edtCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(edtDescricao)
                                        .addGap(10, 10, 10))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(edtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(edtValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(97, 97, 97))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtQuantidade)
                    .addComponent(edtDescricao)
                    .addComponent(edtCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edtValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Nota", jPanel3);

        TabelaProdutos1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dia", "Data", "Valor", "Tipo Cobranca"
            }
        ));
        TabelaProdutos1.setEnabled(false);
        jScrollPane2.setViewportView(TabelaProdutos1);
        if (TabelaProdutos1.getColumnModel().getColumnCount() > 0) {
            TabelaProdutos1.getColumnModel().getColumn(3).setMinWidth(100);
            TabelaProdutos1.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jLabel12.setText("Acréscimo");

        jLabel13.setText("Desconto");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 328, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(242, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pagamento", jPanel7);

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

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Add.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

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

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel14.setText("Pedido");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnConsulta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGravar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(352, 352, 352)
                        .addComponent(jLabel14)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(btnGravar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConsulta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSair)))
                .addGap(38, 38, 38))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void edtValorUnitarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtValorUnitarioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!edtValorUnitario.getText().equals("")) {
                DefaultTableModel model = (DefaultTableModel) TabelaProdutos.getModel();
                Object[] linha = {edtCodigoProduto.getText(), edtDescricao.getText(), edtQuantidade.getText(), edtValorUnitario.getText()};
                model.addRow(linha);
                edtDescricao.setText("");
                edtValorUnitario.setText("");
                edtCodigoProduto.setText("");
                edtQuantidade.setText("");
                edtCodigoProduto.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Informe um Valor!");
                edtValorUnitario.requestFocus();
            }
        }
    }//GEN-LAST:event_edtValorUnitarioKeyPressed

    private void edtCodigoProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoProdutoKeyPressed
        // TODO add your handling code here:
        String auxTexto = edtCodigoProduto.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                JOptionPane.showMessageDialog(null, "Codigo do produto nulo!");
                edtCodigoProduto.grabFocus();
            } else {
                ValidaCampoCodigoProdutoNaoNulo();
            }
        }

    }//GEN-LAST:event_edtCodigoProdutoKeyPressed

    private void edtDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtDescricaoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtQuantidade.grabFocus();
        }
    }//GEN-LAST:event_edtDescricaoKeyPressed

    private void edtQuantidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtQuantidadeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtValorUnitario.grabFocus();
        }
    }//GEN-LAST:event_edtQuantidadeKeyPressed

    private void edtCodigoMovimentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoMovimentoKeyPressed

        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            if (edtCodigoMovimento.getText().equals("")) {
                //Programacao Pronta Generator
                ValidaCodigoGenerator();
                //Habilitação dos campos
                habilitaCampos(true);
                edtCliente.requestFocus();
                //Combobox Tipo de Nota
                cbTipo_Nota.setModel(tiponotadb.getComboTipoNota());
                ComboBoxTipoNota();
                //Combobox Vendedor
                cbVendedor.setModel(pessoadb.getComboPessoa());
                ComboBoxVendedor();
                //Combobox Cliente
                cbCliente.setModel(pessoadb.getComboPessoa());
                ComboBoxCliente();
                //Condicao de Pagamento
                cbCondicaoPagamento.setModel(condicaopagamentodb.getComboCondPag());
                ComboBoxCondicaoPagamento();
            } else {
                //Lista os dados da Venda
                ListaVendaCompleto();
                //Lista os itens da Venda
                ListaItensCompleto();
                //Carrega os registros dos combobox da tela
            }
        }

    }//GEN-LAST:event_edtCodigoMovimentoKeyPressed

    private void edtCodigoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoClienteKeyPressed
        // TODO add your handling code here:
        String auxTexto = edtCodigoCliente.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                JOptionPane.showMessageDialog(null, "Codigo da pessoa nulo!");
                edtCodigoCliente.requestFocus();
            } else {
                ValidaCampoCodigoClienteNãoNulo();
            }
        }
    }//GEN-LAST:event_edtCodigoClienteKeyPressed

    private void edtCodigoVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoVendedorKeyPressed
        // TODO add your handling code here:
        String auxTexto = edtCodigoVendedor.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                JOptionPane.showMessageDialog(null, "Codigo do Vendedor nulo!");
                edtCodigoVendedor.requestFocus();
            } else {
                ValidaCampoCodigoVendedorNãoNulo();
            }
        }
    }//GEN-LAST:event_edtCodigoVendedorKeyPressed

    private void edtCodigoCondicaoPagamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoCondicaoPagamentoKeyPressed
        // TODO add your handling code here:
        String auxTexto = edtCodigoCondicaoPagamento.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                JOptionPane.showMessageDialog(null, "Codigo da Condicao nula!");
                edtCodigoCondicaoPagamento.requestFocus();
            } else {
                ValidaCampoCodigoCondicaoPagamentoNãoNulo();
                edtCodigoProduto.requestFocus();
            }
        }
    }//GEN-LAST:event_edtCodigoCondicaoPagamentoKeyPressed

    private void cbVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbVendedorActionPerformed
        ComboBoxVendedor();
    }//GEN-LAST:event_cbVendedorActionPerformed

    private void cbClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbClienteActionPerformed
        ComboBoxCliente();
    }//GEN-LAST:event_cbClienteActionPerformed

    private void cbClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbClienteMouseClicked
        //cbCliente.setModel(pessoadb.getComboPessoa());
        // ComboBoxCliente();
    }//GEN-LAST:event_cbClienteMouseClicked

    private void cbCondicaoPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCondicaoPagamentoActionPerformed
        // TODO add your handling code here:
        ComboBoxCondicaoPagamento();
    }//GEN-LAST:event_cbCondicaoPagamentoActionPerformed

    private void btnConsultaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaClienteActionPerformed
        // TODO add your handling code here:
        ConsultaPessoa form = new ConsultaPessoa(edtCodigoCliente);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigoCliente.requestFocus();
    }//GEN-LAST:event_btnConsultaClienteActionPerformed

    private void btnConsultaVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaVendedorActionPerformed
        // TODO add your handling code here:
        ConsultaPessoa form = new ConsultaPessoa(edtCodigoVendedor);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigoVendedor.requestFocus();
    }//GEN-LAST:event_btnConsultaVendedorActionPerformed

    private void btnConsultaCondicaoPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaCondicaoPagamentoActionPerformed
        // TODO add your handling code here:
        //ConsultaCondicaoPagamento form = new ConsultaCondicaoPagamento(edtCodigoCondicaoPagamento);
        //this.getDesktopPane().add(form);
        //form.setVisible(true);
        //edtCodigoCondicaoPagamento.requestFocus();
    }//GEN-LAST:event_btnConsultaCondicaoPagamentoActionPerformed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente gravar o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            GravarCompletoValidado();
        }
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnGravarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGravarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnCancelar.grabFocus();
        }
    }//GEN-LAST:event_btnGravarKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Cancelar a Edição?");
        if (resposta == JOptionPane.YES_OPTION) {
            habilitaCampos(false);
            limparTela();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnAdicionar.grabFocus();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
        AdicionarVenda();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        ConsultaPedido form = new ConsultaPedido(edtCodigoMovimento);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigoMovimento.grabFocus();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btnConsultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSair.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaKeyPressed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioDAVPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormularioDAVPadrao().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelaProdutos;
    private javax.swing.JTable TabelaProdutos1;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnConsultaCliente;
    private javax.swing.JButton btnConsultaCondicaoPagamento;
    private javax.swing.JButton btnConsultaVendedor;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox cbCliente;
    private javax.swing.JComboBox cbCondicaoPagamento;
    private javax.swing.JComboBox cbTipo_Nota;
    private javax.swing.JComboBox cbVendedor;
    private javax.swing.JLabel edtCliente;
    private javax.swing.JTextField edtCodigoCliente;
    private javax.swing.JTextField edtCodigoCondicaoPagamento;
    private javax.swing.JTextField edtCodigoMovimento;
    private javax.swing.JTextField edtCodigoProduto;
    private javax.swing.JTextField edtCodigoVendedor;
    private javax.swing.JLabel edtCondicao_Pagamento;
    private javax.swing.JFormattedTextField edtData_Saida;
    private javax.swing.JTextField edtDescricao;
    private javax.swing.JFormattedTextField edtHora_Saida;
    private javax.swing.JTextField edtQuantidade;
    private javax.swing.JTextField edtValorUnitario;
    private javax.swing.JLabel edtVendedor;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
