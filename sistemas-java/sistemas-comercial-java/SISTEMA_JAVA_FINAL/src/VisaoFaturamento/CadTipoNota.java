package VisaoFaturamento;

import ControleCadastro.PessoaDB;
import ControleFaturamento.CondicaoPagamentoDB;
import ControleFaturamento.TipoNotaDB;
import Principal.MetodosGlobais;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * @author Gelvazio
 */
public class CadTipoNota extends MetodosGlobais {

    private static final String sqlBuscaPessoa = "SELECT * FROM PESSOA_SIMPLES WHERE cd_pessoa=?";
    private static final String sqlBuscaQuantidadeEPreco = "SELECT itens_orc_simples.* FROM itens_orc_simples WHERE cd_movimento=? and CD_SEQ_ITE_PRO=?";
    private static final String sqlBuscaCondicaoPagamento = "SELECT * FROM COND_PAG WHERE CD_COND=?";

    private static final String sqlBuscaCodigoVendedor = "SELECT * FROM pessoa_simples where pessoa_simples.cd_pessoa=?";
    private static final String sqlCodigoCliente = "SELECT * FROM pessoa_simples where pessoa_simples.cd_pessoa=?";

    //private static final String sqlBuscaProduto = "SELECT * FROM produto_simples WHERE cd_ref=?";
    //Traz o preço no sql abaixo:
    private static final String sqlBuscaProduto
            = "SELECT                                            "
            + "    SUB_TAB_PRECO.VL_VENDA,                       "
            + "    PRODUTO_SIMPLES.DS_PROD                       "
            + "FROM                                              "
            + "    PRODUTO_SIMPLES                               "
            + "    LEFT OUTER JOIN SUB_TAB_PRECO ON              "
            + "    SUB_TAB_PRECO.CD_REF=PRODUTO_SIMPLES.CD_REF   "
            + "WHERE                                             "
            + "    (PRODUTO_SIMPLES.CD_REF=?)                    ";
    /*
     --Altera os custos conforme a venda
     update sub_tab_preco s set
     s.vl_custo=
     (
     (s.vl_venda-(s.vl_venda*0.4))
     );
     COMMIT WORK;
     --Altera os Valores de Venda
     --Aumenta o Valor da venda em 40% e
     --Recalcula todos os outros valores atraves disso
     update sub_tab_preco s set
     s.vl_venda=
     (
     (s.vl_venda+(s.vl_venda*0.4))
     ),

     s.vl_promocao=
     (
     (s.vl_venda+(s.vl_venda * 0.2))
     ),
     s.vl_especial=
     (
     (s.vl_venda+(s.vl_venda*0.5) )

     );
     COMMIT WORK;
     --Altera as Margens de Venda conforme o valor

     update sub_tab_preco s set
     s.TX_MARGEM_LUCRO_VENDA=
     (
     ((s.vl_venda*100)/s.vl_custo)
     ),

     TX_MARGEM_LUCRO_PROMOCAO=
     (
     ((s.vl_promocao * 100)/s.vl_custo)
     ),
     TX_MARGEM_LUCRO_ESPECIAL=
     (
     ((s.vl_especial * 100)/s.vl_custo)

     );
     COMMIT WORK;
     */
    private static final String sqlBuscaTipoNota = "SELECT * FROM tipo_nota_simples WHERE CD_TIPO=?";
    private static final String sqlTodos = "SELECT * FROM PESSOA_SIMPLES";
    private static final String sqlBuscaVendaPeloMovimento = "SELECT * FROM ORCAMENTO_SIMPLES WHERE ORCAMENTO_SIMPLES.CD_MOVIMENTO=?";

    PessoaDB pes = new PessoaDB();
    PessoaDB pessoadb = new PessoaDB();
    CondicaoPagamentoDB condicaopagamentodb = new CondicaoPagamentoDB();
    TipoNotaDB tiponotadb = new TipoNotaDB();
    String auxnomeprodutoteste = "";
    int aux_codigo_tipo_nota;

    //Variáveis de Data e Hora
    Date data = new Date();
    SimpleDateFormat formatadata = new SimpleDateFormat("dd/MM/yyyy");
    GregorianCalendar hora = new GregorianCalendar();
    SimpleDateFormat formatahora = new SimpleDateFormat("HH:mm:hh");

    //Exemplo:
    //JOptionPane.showMessageDialog(null, "Valor da data atual: " + formatadata.format(data));
    //JOptionPane.showMessageDialog(null, "Hora Atual:" + formatahora.format(hora.getTime()));
    public CadTipoNota() {
        initComponents();
        Principal.MetodosGlobais p = new Principal.MetodosGlobais();
        p.Centro();
        //habilitaCampos(false);
        //edtCodigoMovimento.requestFocus();
        //Tela no Centro
        //setLocationRelativeTo(null);
        Centro();
        //edtDataSaida.setEnabled(false);
        //edtHoraSaida.setEnabled(false);
    }
    /*
     public void DataAtual() {
     //JOptionPane.showMessageDialog(null, "Valor da data atual: " + formatadata.format(data));
     //Passa a Data atual para o campo
     edtDataSaida.setText(formatadata.format(data));
     }

     public void HoraAtual() {
     //JOptionPane.showMessageDialog(null, "Hora Atual:" + formatahora.format(hora.getTime()));
     //Passa a Hora atual para o campo
     //edtHoraSaida.setText(formatahora.format(hora.getTime()));
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
     //cbVendedor.setModel(pessoadb.getComboPessoa());
     ComboBoxVendedor();
     //Combobox Cliente
     //cbCliente.setModel(pessoadb.getComboPessoa());
     ComboBoxCliente();
     //Condicao de Pagamento
     //cbCondicaoPagamento.setModel(condicaopagamentodb.getComboCondPag());
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
     edtDataSaida.setText(data_saida);
     edtHoraSaida.setText(hora_saida);

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
     edtCodigoMovimento.requestFocus();
     //habilitaCampos(false);
     //limparTela();
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
     int CD_NCM_SH = rs.getInt("CD_NCM_SH");
     int CD_REF = rs.getInt("CD_REF");
     ProdutoSimples produto = new ProdutoSimples(
     CD_PROD,
     CD_GRUPO,
     CD_SUB_GRUPO,
     FG_ATIVO,
     CD_COR,
     CD_FABRICA,
     CD_MARCA,
     CD_GP_FISCAL,
     CD_NCM_SH,
     CD_REF,
     DS_PROD);
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
     auxprodutosimples.getCD_REF(),
     auxprodutosimples.getDS_PROD()
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
     } else {
     JOptionPane.showMessageDialog(null, "Não foi possível alterar a venda!");
     }
     }

     public void habilitaCampos(boolean habilita) {
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
     btnConsulta.setEnabled(!habilita);
     btnAdicionar.setEnabled(!habilita);

     //btnSair.setEnabled(!habilita);
     btnConsultaCliente.setEnabled(habilita);
     btnConsultaVendedor.setEnabled(habilita);
     btnConsultaCondicaoPagamento.setEnabled(habilita);

     cbCliente.setEnabled(habilita);
     cbTipo_Nota.setEnabled(habilita);
     cbCondicaoPagamento.setEnabled(habilita);
     cbVendedor.setEnabled(habilita);

     if (habilita) {
     edtCliente.requestFocus();
     } else {
     //Remover registros dos campos
     limparTela();
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
     edtDataSaida.setText("");
     edtHoraSaida.setText("");

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
     TipoNotaDB tiponotadb = new TipoNotaDB();
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
     PessoaDB pessoadb = new PessoaDB();
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
     PessoaDB pessoadb = new PessoaDB();
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
     CondicaoPagamentoDB condicaopagamentodb = new CondicaoPagamentoDB();
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
     double vl_preco = rs.getDouble("vl_venda");
     edtDescricao.setText(auxnome);
     //Sugere a quantidade 1
     edtQuantidade.setText("1");
     edtQuantidade.grabFocus();

     if (vl_preco > 0) {
     //Conversao de variavel
     String aux = "";
     aux = "" + vl_preco;

     //JOptionPane.showMessageDialog(null, "Item: "+aux);
     //Muda o preço do item para o preço da tabela                   
     edtValorUnitario.setText(aux);
     } else {
     JOptionPane.showMessageDialog(null, "Item com preço zerado!");
     edtCodigoProduto.grabFocus();
     }
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

     */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Principal = new javax.swing.JTabbedPane();
        AbaPrincipal = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        edtInfoComplementar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        AbaFiscal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaProdutos = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbTipo_Nota = new javax.swing.JComboBox();
        edtCliente = new javax.swing.JLabel();
        edtCodigoCliente = new javax.swing.JTextField();
        edtCliente1 = new javax.swing.JLabel();
        edtCodigoCliente1 = new javax.swing.JTextField();
        edtCliente2 = new javax.swing.JLabel();
        edtCodigoCliente2 = new javax.swing.JTextField();
        edtCliente3 = new javax.swing.JLabel();
        edtCodigoCliente3 = new javax.swing.JTextField();
        edtCliente4 = new javax.swing.JLabel();
        edtCliente5 = new javax.swing.JLabel();
        edtCliente6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jComboBox5 = new javax.swing.JComboBox();
        jComboBox6 = new javax.swing.JComboBox();
        edtCodigoCliente4 = new javax.swing.JTextField();
        edtCodigoCliente5 = new javax.swing.JTextField();
        edtCodigoCliente6 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        btnGravar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DTS Systems-by Gelvázio Camargo");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Principal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipos de Notas", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));

        AbaPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        AbaPrincipal.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 60, -1));
        AbaPrincipal.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 310, -1));

        jLabel12.setText("Código:");
        AbaPrincipal.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jLabel13.setText("Descrição:");
        AbaPrincipal.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jLabel1.setText("Tipo:");
        AbaPrincipal.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jRadioButton4.setText("Entrada");

        jRadioButton1.setText("Saída");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton4)
                .addGap(17, 17, 17)
                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton1))
                .addContainerGap())
        );

        AbaPrincipal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 150, 40));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel19.setText("Informação Complementar:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Código", "Descrição"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Search.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(35, 35, 35)
                        .addComponent(edtInfoComplementar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(edtInfoComplementar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );

        AbaPrincipal.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 630, 170));

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jRadioButton9.setText("Gera Lançamento(À Pagar)");

        jRadioButton6.setText("Gera Lançamento(À Receber)");

        jRadioButton8.setText("Venda");

        jRadioButton5.setText("Devolução");

        jRadioButton3.setText("Pedido");

        jRadioButton10.setText("Movimenta Estoque");

        jRadioButton11.setText("Permite Venda Com Estoque Negativo");

        jRadioButton7.setText("Permite Mudar Preço");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jRadioButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jRadioButton5)
                        .addGap(17, 17, 17)
                        .addComponent(jRadioButton8)
                        .addGap(17, 17, 17)
                        .addComponent(jRadioButton6)
                        .addGap(17, 17, 17)
                        .addComponent(jRadioButton9))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addGap(17, 17, 17)
                        .addComponent(jRadioButton10)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton11)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AbaPrincipal.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 630, 160));

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel20.setText("Quantidade Máxima de Item na Nota:");

        jRadioButton2.setText("Preço de Venda");

        jRadioButton12.setText("Preço de Custo");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jRadioButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AbaPrincipal.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 630, 50));

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 668, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        AbaPrincipal.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 610, -1, 20));

        jLabel14.setText("Complemento CFOP:");
        AbaPrincipal.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));
        AbaPrincipal.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 260, -1));

        Principal.addTab("Principal", AbaPrincipal);

        AbaFiscal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        AbaFiscal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TabelaProdutos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TabelaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "GP Fiscal", "TP Consumo", "CFOP Estadual", "CFOP InterEstadual", "CST", "CST PIS", "CST COFINS", "CST IPI"
            }
        ));
        TabelaProdutos.setEnabled(false);
        jScrollPane1.setViewportView(TabelaProdutos);
        if (TabelaProdutos.getColumnModel().getColumnCount() > 0) {
            TabelaProdutos.getColumnModel().getColumn(3).setMinWidth(100);
            TabelaProdutos.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        AbaFiscal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 660, 90));

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Grupo Fiscal:");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 80, -1));

        cbTipo_Nota.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(cbTipo_Nota, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 450, 25));

        edtCliente.setText("CFOP InterEstadual:");
        jPanel6.add(edtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 120, 20));

        edtCodigoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoClienteKeyPressed(evt);
            }
        });
        jPanel6.add(edtCodigoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 65, -1));

        edtCliente1.setText("Tipo de Consumo:");
        jPanel6.add(edtCliente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 110, 20));

        edtCodigoCliente1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoCliente1KeyPressed(evt);
            }
        });
        jPanel6.add(edtCodigoCliente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 65, -1));

        edtCliente2.setText("CFOP Estadual:");
        jPanel6.add(edtCliente2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 90, 20));

        edtCodigoCliente2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoCliente2KeyPressed(evt);
            }
        });
        jPanel6.add(edtCodigoCliente2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 65, -1));

        edtCliente3.setText("CST COFINS:");
        jPanel6.add(edtCliente3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 80, 20));

        edtCodigoCliente3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoCliente3KeyPressed(evt);
            }
        });
        jPanel6.add(edtCodigoCliente3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 65, -1));

        edtCliente4.setText("CST PIS:");
        jPanel6.add(edtCliente4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 60, 20));

        edtCliente5.setText("CST IPI:");
        jPanel6.add(edtCliente5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 60, 20));

        edtCliente6.setText("CST:");
        jPanel6.add(edtCliente6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 40, 20));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, 380, 25));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, 380, 25));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 380, 25));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 80, 380, 25));

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 380, 25));

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel6.add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 380, 25));

        edtCodigoCliente4.setEditable(false);
        edtCodigoCliente4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoCliente4KeyPressed(evt);
            }
        });
        jPanel6.add(edtCodigoCliente4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 65, -1));

        edtCodigoCliente5.setEditable(false);
        edtCodigoCliente5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoCliente5KeyPressed(evt);
            }
        });
        jPanel6.add(edtCodigoCliente5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 65, -1));

        edtCodigoCliente6.setEditable(false);
        edtCodigoCliente6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoCliente6KeyPressed(evt);
            }
        });
        jPanel6.add(edtCodigoCliente6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 65, -1));

        jLabel2.setText("0 -Consumo  1- Revenda  2- Indústria");
        jPanel6.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 240, 20));

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setText("Base de ICMS  Estadual:");

        jLabel16.setText("Valor de ICMS  Estadual:");

        jLabel17.setText("Base de ICMS  InterEstadual:");

        jLabel18.setText("Valor de ICMS  InterEstadual:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("ICMS", jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setText("Base de ICMS \"ST\" Estadual:");

        jLabel9.setText("Valor de ICMS \"ST\" Estadual:");

        jLabel10.setText("Base de ICMS \"ST\" InterEstadual:");

        jLabel11.setText("Valor de ICMS \"ST\" InterEstadual:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField5))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("ICMS ST", jPanel3);

        jPanel6.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 640, 180));

        AbaFiscal.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 450));

        Principal.addTab("Fiscal", AbaFiscal);

        getContentPane().add(Principal, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 670, 580));

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 40, 120, 250));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void edtCodigoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoClienteKeyPressed
        // TODO add your handling code here:
        String auxTexto = edtCodigoCliente.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                JOptionPane.showMessageDialog(null, "Codigo da pessoa nulo!");
                edtCodigoCliente.requestFocus();
            } else {
                //ValidaCampoCodigoClienteNãoNulo();
            }
        }
    }//GEN-LAST:event_edtCodigoClienteKeyPressed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente gravar o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            //GravarCompletoValidado();
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
            //habilitaCampos(false);
            //limparTela();
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
        // AdicionarVenda();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        //ConsultaPedido form = new ConsultaPedido(edtCodigoMovimento);
        // this.getDesktopPane().add(form);
        // form.setVisible(true);
        //edtCodigoMovimento.grabFocus();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btnConsultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSair.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaKeyPressed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Sair da tela?");
        if (resposta == JOptionPane.YES_OPTION) {
            int resposta2 = JOptionPane.showConfirmDialog(null, "Você perderá os dados Adicionados Alterados!Deseja continuar?");
            if (resposta2 == JOptionPane.YES_OPTION) {
                //  habilitaCampos(false);
                // limparTela();
                dispose();
            } else {
                edtCodigoCliente.requestFocus();
            }
        }
    }//GEN-LAST:event_btnSairActionPerformed

    private void edtCodigoCliente1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoCliente1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtCodigoCliente1KeyPressed

    private void edtCodigoCliente2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoCliente2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtCodigoCliente2KeyPressed

    private void edtCodigoCliente3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoCliente3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtCodigoCliente3KeyPressed

    private void edtCodigoCliente4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoCliente4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtCodigoCliente4KeyPressed

    private void edtCodigoCliente5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoCliente5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtCodigoCliente5KeyPressed

    private void edtCodigoCliente6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoCliente6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtCodigoCliente6KeyPressed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CadTipoNota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadTipoNota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadTipoNota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadTipoNota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadTipoNota().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AbaFiscal;
    private javax.swing.JPanel AbaPrincipal;
    private javax.swing.JTabbedPane Principal;
    private javax.swing.JTable TabelaProdutos;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox cbTipo_Nota;
    private javax.swing.JLabel edtCliente;
    private javax.swing.JLabel edtCliente1;
    private javax.swing.JLabel edtCliente2;
    private javax.swing.JLabel edtCliente3;
    private javax.swing.JLabel edtCliente4;
    private javax.swing.JLabel edtCliente5;
    private javax.swing.JLabel edtCliente6;
    private javax.swing.JTextField edtCodigoCliente;
    private javax.swing.JTextField edtCodigoCliente1;
    private javax.swing.JTextField edtCodigoCliente2;
    private javax.swing.JTextField edtCodigoCliente3;
    private javax.swing.JTextField edtCodigoCliente4;
    private javax.swing.JTextField edtCodigoCliente5;
    private javax.swing.JTextField edtCodigoCliente6;
    private javax.swing.JTextField edtInfoComplementar;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
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
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
