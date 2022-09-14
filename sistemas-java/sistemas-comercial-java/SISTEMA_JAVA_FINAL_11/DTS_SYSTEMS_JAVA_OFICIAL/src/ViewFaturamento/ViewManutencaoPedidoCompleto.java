package ViewFaturamento;

import ControllerCadastro.ControllerManutencaoPessoa;
import ControllerCadastro.ControllerManutencaoProduto;
import ControllerConfiguracao.ControllerManutencaoFilial;
import ControllerFaturamento.ControllerManutencaoCondicaoPagamento;
import ControllerFaturamento.ControllerManutencaoItem;
import ControllerFaturamento.ControllerManutencaoParcela;
import ControllerFaturamento.ControllerManutencaoTipoNota;
import ControllerFaturamento.ControllerManutencaoVenda;
import static Estrutura.Mensagem.mensagemErro;
import ModelCadastro.ModelPessoa;
import ModelCadastro.ModelProduto;
import ModelCadastro.ModelTributacao;
import ModelFaturamento.ModelItem;
import ModelFaturamento.ModelParcelaDados;
import ModelFaturamento.ModelVenda;
import Principal.Conexao;
import Estrutura.ViewManutencaoPadrao;
import ViewConsultasCadastro.ViewConsultaPessoa;
import ViewConsultasCadastro.ViewConsultaProduto;
import ViewConsultasCadastro.ViewConsultaTransportadora;
import ViewConsultasFaturamento.ViewConsultaCondicaoPagamento;
import ViewConsultasFaturamento.ViewConsultaPedido;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gelvazio
 */
public class ViewManutencaoPedidoCompleto extends ViewManutencaoPadrao {

    private static final String SQL_BUSCA_PESSOA = "SELECT * FROM PESSOA WHERE cd_pessoa=?";
    private static final String SQL_BUSCA_TIPO_NOTA = "SELECT * FROM TIPO_NOTA_SIMPLES WHERE cd_filial=? and cd_tipo=?";
    private static final String SQL_BUSCA_QUANTIDADE_E_PRECO
            = "SELECT                "
            + "    ITENS_ORC.*       "
            + "FROM                  "
            + "    ITENS_ORC         "
            + "WHERE                 "
            + "    CD_MOVIMENTO=?    "
            + "    AND               "
            + "    CD_SEQ_ITE_PRO=?  ";
    private static final String SQL_BUSCA_CONDICAO_PAGAMENTO = "SELECT * FROM COND_PAG WHERE CD_COND=?";

    private static final String SQL_BUSCA_PRODUTO
            = "  SELECT SUB_TAB_PRECO.VL_VENDA,                   "               
            +"          PRODUTO_SIMPLES.DS_PROD                 "      
            +"     FROM PRODUTO_SIMPLES                         "      
            +"LEFT JOIN SUB_TAB_PRECO                           "
            +"       ON SUB_TAB_PRECO.CD_REF=PRODUTO_SIMPLES.CD_REF   "
            +"    WHERE (PRODUTO_SIMPLES.CD_REF=?)                    ";
    private static final String SQL_BUSCA_TRANSPORTADORA = "SELECT * FROM PESSOA  WHERE PESSOA.FG_TRANSPORTADOR=1 AND cd_pessoa=?";

    private static final String SQL_BUSCA_VENDA_PELO_MOVIMENTO = "SELECT * FROM ORCAMENTO WHERE ORCAMENTO.CD_FILIAL=? AND ORCAMENTO.CD_MOVIMENTO=?";

    ControllerManutencaoPessoa pes = new ControllerManutencaoPessoa();
    ControllerManutencaoPessoa pessoadb = new ControllerManutencaoPessoa();
    ControllerManutencaoCondicaoPagamento condicaopagamentodb = new ControllerManutencaoCondicaoPagamento();
    ControllerManutencaoTipoNota tiponotadb = new ControllerManutencaoTipoNota();
    ControllerManutencaoFilial filialdb = new ControllerManutencaoFilial();
    ControllerManutencaoProduto produtosimplesdb = new ControllerManutencaoProduto();
    ControllerManutencaoParcela parceladb = new ControllerManutencaoParcela();
    String auxnomeprodutoteste = "";
    double AcrescimoTotal = 0;
    double DescontoTotal = 0;
    double totalNota = 0;
    boolean VerificaHabilitacaoCampos = false;
    boolean VerificaCondicaopagamento = false;
    int aux_codigo_tipo_nota;
    //Variáveis de Data e Hora
    Date data = new Date();
    SimpleDateFormat formatadata = new SimpleDateFormat("dd/MM/yyyy");
    GregorianCalendar hora = new GregorianCalendar();
    SimpleDateFormat formatahora = new SimpleDateFormat("HH:mm:hh");
    boolean gravouVenda = true;
    ControllerManutencaoVenda vendacompletodb = new ControllerManutencaoVenda();
    ControllerManutencaoItem itemdb = new ControllerManutencaoItem();
    ControllerManutencaoProduto produtodb = new ControllerManutencaoProduto();

    public ViewManutencaoPedidoCompleto() {
        initComponents();
        habilitaCampos(false);
        edtDataSaida.setEnabled(false);
        edtHoraSaida.setEnabled(false);
        edtDataEmissao.setEnabled(false);
        edtHoraEmissao.setEnabled(false);
        edtTipoNota.setVisible(false);
        edtCodigoMovimento.grabFocus();
    }

    private void habilitaCampos(boolean habilita) {
        edtCodigoMovimento.requestFocus();
        edtCodigoMovimento.setEnabled(!habilita);
        edtCodigoCliente.setEnabled(habilita);
        edtCodigoVendedor.setEnabled(habilita);
        edtValorUnitario.setEnabled(habilita);
        edtCodigoProduto.setEnabled(habilita);
        edtQuantidadeItem.setEnabled(habilita);
        edtDescricao.setEnabled(habilita);
        edtCodigoCondicaoPagamento.setEnabled(habilita);

        btnGravar.setEnabled(habilita);
        btnCancelar.setEnabled(habilita);
        btnConsulta.setEnabled(!habilita);
        btnAdicionar.setEnabled(!habilita);

        btnConsultaCliente.setEnabled(habilita);
        btnConsultaVendedor.setEnabled(habilita);
        btnConsultaCondicaoPagamento.setEnabled(habilita);
        btnConsultaProduto.setEnabled(habilita);

        cbCliente.setEnabled(habilita);
        cbTipo_Nota.setEnabled(habilita);
        cbCondicaoPagamento.setEnabled(habilita);
        cbVendedor.setEnabled(habilita);

//Campos da Aba Pagamento
        edtAcrescimoTotal.setEnabled(habilita);
        edtDescontoTotal.setEnabled(habilita);
        edtInfoAdicionais.setEnabled(habilita);
        btnConsultaTransportadora.setEnabled(habilita);
        edtTransportadora.setEnabled(habilita);
        edtPlacaVeiculo.setEnabled(habilita);
        edtQuantidadeNota.setEnabled(habilita);
        edtFrete.setEnabled(habilita);
        edtVolume.setEnabled(habilita);
        edtEspecie.setEnabled(habilita);
        edtMarca.setEnabled(habilita);
        edtPesoBruto.setEnabled(habilita);
        edtPesoLiquido.setEnabled(habilita);

        RadioEmitente.setEnabled(habilita);
        RadioDestinatario.setEnabled(habilita);
        RadioOutros.setEnabled(habilita);

        RadioOrcamento.setEnabled(habilita);
        RadioPedido.setEnabled(habilita);
        RadioVenda.setEnabled(habilita);

        labelAcrescimoTotal.setEnabled(habilita);
        labelDescontoTotal.setEnabled(habilita);
        edtTotalProduto.setEnabled(habilita);
        edtDescontoItemTotal.setEnabled(habilita);
        edtTotalVenda.setEnabled(habilita);
        LabelMovimento.setEnabled(habilita);

        //Aba Impostos
        edtBaseTotalICMS.setEnabled(habilita);
        edtVLTotalICMS.setEnabled(habilita);
        edtBaseTotalICMS_ST.setEnabled(habilita);
        edtVLTotalICMS_ST.setEnabled(habilita);
        edtTotalFrete.setEnabled(habilita);
        edtVLTotalDesconto.setEnabled(habilita);
        edtDespesasAcessorias.setEnabled(habilita);
        edtTotalProdutos2.setEnabled(habilita);
        edtTotalIPI.setEnabled(habilita);
        edtTotalNota.setEnabled(habilita);
        edtBaseCOFINS.setEnabled(habilita);
        edtValorCOFINS.setEnabled(habilita);
        edtBasePIS.setEnabled(habilita);
        edtValorPIS.setEnabled(habilita);
        edtValorSeguro.setEnabled(habilita);

        if (habilita) {
            VerificaHabilitacaoCampos = true;
            edtCliente.requestFocus();
            labelAcrescimoTotal.setText("0,00");
            labelDescontoTotal.setText("0,00");
            edtTotalProduto.setText("0,00");
            edtDescontoItemTotal.setText("0,00");
            edtTotalVenda.setText("0,00");

        } else {
            //Remover registros dos campos
            VerificaHabilitacaoCampos = false;
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
        edtQuantidadeItem.setText("");
        edtCodigoCondicaoPagamento.setText("");
        edtDataSaida.setText("");
        edtHoraSaida.setText("");
        edtDataEmissao.setText("");
        edtHoraEmissao.setText("");

        DefaultTableModel model = (DefaultTableModel) TabelaProdutos.getModel();
        model.setNumRows(0);
        DefaultTableModel model1 = (DefaultTableModel) TabelaParcelas.getModel();
        model1.setNumRows(0);
        cbCliente.removeAllItems();
        cbVendedor.removeAllItems();
        cbCondicaoPagamento.removeAllItems();
        cbCondicaoPagamento.removeAllItems();
        cbTipo_Nota.removeAllItems();
        edtCodigoMovimento.requestFocus();
        //Campos da Aba Pagamento
        edtAcrescimoTotal.setText("");
        edtDescontoTotal.setText("");

        edtInfoAdicionais.setText("");
        edtTransportadora.setText("");
        edtPlacaVeiculo.setText("");
        edtQuantidadeNota.setText("");
        edtFrete.setText("");
        edtVolume.setText("");
        edtEspecie.setText("");
        edtMarca.setText("");
        edtPesoBruto.setText("");
        edtPesoLiquido.setText("");

        RadioEmitente.setSelected(false);
        RadioDestinatario.setSelected(false);
        RadioOutros.setSelected(false);

        RadioOrcamento.setSelected(false);
        RadioPedido.setSelected(false);
        RadioVenda.setSelected(false);

        labelAcrescimoTotal.setText("");
        labelDescontoTotal.setText("");
        edtTotalProduto.setText("");
        edtDescontoItemTotal.setText("");
        edtTotalVenda.setText("");
        LabelMovimento.setText("");
        //Aba Impostos
        edtBaseTotalICMS.setText("");
        edtVLTotalICMS.setText("");
        edtBaseTotalICMS_ST.setText("");
        edtVLTotalICMS_ST.setText("");
        edtTotalFrete.setText("");
        edtVLTotalDesconto.setText("");
        edtDespesasAcessorias.setText("");
        edtTotalProdutos2.setText("");
        edtTotalIPI.setText("");
        edtTotalNota.setText("");
        edtBaseCOFINS.setText("");
        edtValorCOFINS.setText("");
        edtBasePIS.setText("");
        edtValorPIS.setText("");
        edtValorSeguro.setText("");
    }

    private void DataAtual() {
        //Passa a Data atual para o campo
        edtDataSaida.setText(formatadata.format(data));
    }

    private void HoraAtual() {
        //JOptionPane.showMessageDialog(null, "Hora Atual:" + formatahora.format(hora.getTime()));
        //Passa a Hora atual para o campo
        edtHoraSaida.setText(formatahora.format(hora.getTime()));
    }

    private void AdicionarVenda() {
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

    private void GravarCompletoValidado() throws ParseException {
        String auxCodigo = edtCodigoMovimento.getText();
        String auxCodigoCliente = edtCodigoCliente.getText();
        String auxCodigoVendedor = edtCodigoVendedor.getText();
        String auxCodigoCondicaoPagamento = edtCodigoCondicaoPagamento.getText();
        String auxCodigoProduto = "";
        for (int x = 0; x < TabelaProdutos.getRowCount(); x++) {
            int CodigoProduto = (Integer.parseInt(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 0))));
            auxCodigoProduto = "" + CodigoProduto;
        }
        //Validação da Aba Pagamento e Transportadora
        String auxcdTransportadora = edtTransportadora.getText();
        String auxNrPlaca = edtPlacaVeiculo.getText();
        String auxEspecie = edtEspecie.getText();
        String auxQuantidade = edtQuantidadeNota.getText();
        String auxFrete = edtFrete.getText();
        String auxVolume = edtVolume.getText();
        String auxMarca = edtMarca.getText();
        String auxPesoBruto = edtPesoBruto.getText();
        String auxPesoLiquido = edtPesoLiquido.getText();

        //Campos de Acrescimo e desconto
        String auxVl_Acrescimo = edtAcrescimoTotal.getText();
        String auxVl_Desconto = edtDescontoTotal.getText();

        if (auxCodigo.equals("")) {
            mensagemErro("Favor Preencher o Código do movimento!");
            edtCodigoMovimento.requestFocus();
        } else if (auxCodigoCliente.equals("")) {
            mensagemErro("Favor Preencher o codigo do cliente!");
            edtCodigoCliente.requestFocus();
        } else if (auxCodigoVendedor.equals("")) {
            mensagemErro("Favor Preencher o codigo do vendedor!");
            edtCodigoVendedor.requestFocus();
        } else if (auxCodigoCondicaoPagamento.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o codigo da condição de pagamento!");
            edtCodigoCondicaoPagamento.requestFocus();
        } else if (auxCodigoProduto.equals("")) {
            mensagemErro("Favor Preencher pelo menos um produto!");
            edtCodigoProduto.requestFocus();
        } else if ((!RadioEmitente.isSelected()) && (!RadioDestinatario.isSelected()) && (!RadioOutros.isSelected())) {
            mensagemErro("Preencha o tipo de frete!");
            RadioEmitente.requestFocus();
        } else if (!auxcdTransportadora.equals("")) {
            //Se existir transportadora os campos devem
            //ser preenchidos,senão automaticamente pega 
            //valor da tela que tiver ou 0 (Zero) ou nulo 
            //Verifica a validacao dos campos da Aba da Transportadora
            if (auxNrPlaca.equals("")) {
                mensagemErro("Preencha a placa do veiculo!");
                edtPlacaVeiculo.requestFocus();
            } else if (auxEspecie.equals("")) {
                mensagemErro("Preencha a espécie!");
                edtEspecie.requestFocus();
            } else if (auxQuantidade.equals("")) {
                mensagemErro("Preencha a quantidade da nota!");
                edtQuantidadeNota.requestFocus();
            } else if (auxVolume.equals("")) {
                mensagemErro("Preencha o valor de volume!");
                edtVolume.requestFocus();
            } else if (auxFrete.equals("")) {
                mensagemErro("Preencha o valor de frete!");
                edtFrete.requestFocus();
            } else if (auxMarca.equals("")) {
                mensagemErro("Preencha a Marca!");
                edtMarca.requestFocus();
            } else if (auxPesoBruto.equals("")) {
                mensagemErro("Preencha o peso bruto!");
                edtPesoBruto.requestFocus();
            } else if (auxPesoLiquido.equals("")) {
                mensagemErro("Preencha o peso liquido!");
                edtPesoLiquido.requestFocus();
            }
        } else {
            //GravarAlterarComRateioItens();
            GravarAlterar();
        }

        if (auxVl_Acrescimo.equals("")) {
            edtAcrescimoTotal.setText("0");
        }
        if (auxVl_Desconto.equals("")) {
            edtDescontoTotal.setText("0");
        }
    }

    private void GravarAlterar() throws ParseException {
        //Definição das variaveis deste método
        int auxcd_filial = 0;
        int auxcd_movimento = 0;
        int auxcd_vendedor = 0;
        int auxcd_pagto = 0;
        int auxcd_pessoa = 0;
        double auxVl_tot_cus_doc = 0;
        double auxVl_tot_pro_doc = 0;
        double auxVl_acrescimo = 0;
        double auxVl_desconto = 0;
        int auxfg_situacao = 0;
        int auxFg_movimentou_estoque = 0;
        int auxcd_usuario = 0;
        int auxCfop = 0;
        double auxVl_base_icm_total = 0;
        double auxVl_icm_total = 0;
        double auxVl_base_icm_sub_total = 0;
        double auxVl_icm_sub_total = 0;
        double auxVl_base_pis_total = 0;
        double auxVl_pis_total = 0;
        double auxVl_base_cofins_total = 0;
        double auxVl_cofins_total = 0;
        double auxVl_base_ipi_total = 0;
        double auxVl_ipi_total = 0;
        double auxVl_base_servico_total = 0;
        double auxVl_servico_total = 0;
        double auxVl_base_issqn_total = 0;
        double auxVl_issqn_total = 0;
        int auxCd_transportadora = 0;
        String auxNr_PlacaVeiculo = "";
        int auxQtdeVolume = 0;
        int auxFgEmitente = 0;
        double auxVl_tot_frete = 0;
        String auxNm_especie = "";
        String auxNr_nota_nfe = "";
        String auxNr_chave_nfe = "";
        String auxNr_prot_autorizacao = "";
        String auxDs_inf_adicionais = "";
        double auxVl_tot_seguro = 0;
        double auxVl_total_pedido_nota = 0;
        String auxDsMarca = "";
        int auxNumeracao = 0;
        double auxVl_peso_liquido = 0;
        double auxVl_peso_bruto = 0;
        double auxVl_Total_impostos = 0;

//Reinicia as Variaveis antes de ler
        totalNota = 0;
        for (int x = 0; x < TabelaProdutos.getRowCount(); x++) {
            double auxVl_ven_ite_pro = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 3))));
            double auxQuantidade = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 2))));
            double total_item = auxQuantidade * auxVl_ven_ite_pro;
            totalNota = totalNota + total_item;
            //Aqui deve zerar as variaveis totalizadores e itens
            //Limpando as variaveis
            auxcd_filial = 0;
            auxcd_movimento = 0;
            auxcd_vendedor = 0;
            auxcd_pagto = 0;
            auxcd_pessoa = 0;
            auxVl_tot_cus_doc = 0;
            auxVl_tot_pro_doc = 0;
            auxVl_acrescimo = 0;
            auxVl_desconto = 0;
            auxfg_situacao = 0;
            auxFg_movimentou_estoque = 0;
            auxcd_usuario = 0;
            auxCfop = 0;
            auxVl_base_icm_total = 0;
            auxVl_icm_total = 0;
            auxVl_base_icm_sub_total = 0;
            auxVl_icm_sub_total = 0;
            auxVl_base_pis_total = 0;
            auxVl_pis_total = 0;
            auxVl_base_cofins_total = 0;
            auxVl_cofins_total = 0;
            auxVl_base_ipi_total = 0;
            auxVl_ipi_total = 0;
            auxVl_base_servico_total = 0;
            auxVl_servico_total = 0;
            auxVl_base_issqn_total = 0;
            auxVl_issqn_total = 0;
            auxCd_transportadora = 0;
            auxNr_PlacaVeiculo = "";
            auxQtdeVolume = 0;
            auxFgEmitente = 0;
            auxVl_tot_frete = 0;
            auxNm_especie = "";
            auxNr_nota_nfe = "";
            auxNr_chave_nfe = "";
            auxNr_prot_autorizacao = "";
            auxDs_inf_adicionais = "";
            auxVl_tot_seguro = 0;
            auxVl_total_pedido_nota = 0;
            auxDsMarca = "";
            auxNumeracao = 0;
            auxVl_peso_liquido = 0;
            auxVl_peso_bruto = 0;
            auxVl_Total_impostos = 0;
        }
        //mensagemErro("Total de Produtos da Nota: " + totalNota);

        //Inicia com true e so deixa False se der erro ao inserir ou alterar item.
        boolean gravouTodosItens = true;
        auxcd_filial = 1;
        auxcd_movimento = (Integer.parseInt(edtCodigoMovimento.getText()));
        auxcd_vendedor = (Integer.parseInt(edtCodigoVendedor.getText()));
        auxcd_pagto = Integer.parseInt(edtCodigoCondicaoPagamento.getText());
        auxcd_pessoa = (Integer.parseInt(edtCodigoCliente.getText()));

        String auxTransportadora = edtTransportadora.getText();
        auxNr_PlacaVeiculo = edtPlacaVeiculo.getText();
        String auxVolume = edtVolume.getText();
        String auxFrete = edtFrete.getText();
        auxNm_especie = edtEspecie.getText();
        auxDsMarca = edtMarca.getText();
        String auxVlPesoLiquido = edtPesoLiquido.getText();
        String auxVlPesoBruto = edtPesoLiquido.getText();

        auxVl_Total_impostos = 0;
        auxNumeracao = 10;

        //Campos de Acrescimo e desconto
        String auxAcrescimo = edtAcrescimoTotal.getText();
        String auxDesconto = edtDescontoTotal.getText();

        if (auxAcrescimo.equals("")) {
            auxVl_acrescimo = 0;
            edtAcrescimoTotal.setText("0,00");
        } else {
            if (auxVl_acrescimo <= 0) {
                auxVl_acrescimo = 0;
            }
        }
        if (auxDesconto.equals("")) {
            auxVl_desconto = 0;
            edtDescontoTotal.setText("0,00");
        } else {
            if (auxVl_desconto <= 0) {
                auxVl_desconto = 0;
            }
        }
        //Rotina de Rateio de Acrescimo e Desconto
        boolean rateioAcrescimo = false;
        boolean rateioDesconto = false;
        if ((auxVl_desconto > 0) && (auxVl_acrescimo <= 0)) {
            DescontoTotal = auxVl_desconto;
            AcrescimoTotal = 0;
            auxVl_acrescimo = 0;
            auxVl_desconto = DescontoTotal;
            rateioAcrescimo = false;
            rateioDesconto = true;
            edtAcrescimoTotal.setText("0,00");
        } else if ((auxVl_desconto <= 0) && (auxVl_acrescimo > 0)) {
            DescontoTotal = 0;
            AcrescimoTotal = auxVl_acrescimo;
            auxVl_acrescimo = AcrescimoTotal;
            auxVl_desconto = 0;
            rateioAcrescimo = true;
            rateioDesconto = false;
            edtDescontoTotal.setText("0,00");
        } else if ((auxVl_desconto > 0) && (auxVl_acrescimo > 0)) {
            AcrescimoTotal = auxVl_acrescimo - auxVl_desconto;
            if (AcrescimoTotal < 0) {
                DescontoTotal = AcrescimoTotal * -1;
                AcrescimoTotal = 0;
                auxVl_acrescimo = 0;
                auxVl_desconto = DescontoTotal;
                rateioAcrescimo = false;
                rateioDesconto = true;
                edtAcrescimoTotal.setText("0,00");
            } else {
                DescontoTotal = 0;
                AcrescimoTotal = auxVl_acrescimo - auxVl_desconto;
                auxVl_acrescimo = AcrescimoTotal;
                auxVl_desconto = 0;
                rateioAcrescimo = true;
                rateioDesconto = false;
                edtDescontoTotal.setText("0,00");
            }
        } else {
            //mensagemErro("sem acrescimo e sem desconto AQUI");
            rateioAcrescimo = false;
            rateioDesconto = false;
        }
        //Rotina de Rateio de Frete
        boolean rateioFrete = false;
        String auxRateioFrete = edtFrete.getText();
        if (auxRateioFrete.equals("")) {
            auxVl_tot_frete = 0;
            rateioFrete = false;
        } else {
            auxVl_tot_frete = Double.parseDouble(edtFrete.getText());
            rateioFrete = true;
        }
        //testes
        //testes 2 lamego

        //Conversao do valor com virgula
        //String conversaoDesconto = auxDesconto;
        /*
         DecimalFormat df = new DecimalFormat("###,##0.0000");
         String valorDesconto = auxDesconto.replaceAll("\\.", "").replace(",", ".");
         String valorAcrescimo = auxAcrescimo.replaceAll("\\.", "").replace(",", ".");
         String valorFrete = auxFrete.replaceAll("\\.", "").replace(",", ".");

         System.out.println("Valor Desconto nao formatado: " + valorDesconto);
         System.out.println("Valor Desconto formatado: " + df.format(new Double(valorDesconto)));

         System.out.println("Valor Acrescimo nao formatado: " + valorAcrescimo);
         System.out.println("Valor Acrescimo formatado: " + df.format(new Double(valorAcrescimo)));

         System.out.println("Valor Frete nao formatado: " + valorFrete);
         System.out.println("Valor Frete formatado: " + df.format(new Double(valorFrete)));

        
         auxVl_desconto = Double.parseDouble(valorDesconto);
         auxVl_acrescimo = Double.parseDouble(valorAcrescimo);
         auxVl_tot_frete = Double.parseDouble(valorFrete);

         System.out.println("Valor de desconto: " + auxVl_desconto);
         System.out.println("Valor de acrescimo: " + auxVl_acrescimo);
         System.out.println("Valor de frete: " + auxVl_tot_frete);
         //Não use Double para valores monetários, use BigDecimal
         */
        if (auxTransportadora.equals("")) {
            auxCd_transportadora = 0;
            //JOptionPane.showMessageDialog(null, "Codigo da transportadora pego: " + auxCd_transportadora);
            if (auxNr_PlacaVeiculo.equals("")) {
                auxNr_PlacaVeiculo = "MNG6589";
            }
            if (auxQtdeVolume <= 0) {
                auxQtdeVolume = 0;
            }
            if (auxVl_tot_frete <= 0) {
                auxVl_tot_frete = 0;
            }
            if (auxDsMarca.equals("")) {
                auxDsMarca = "NIKE";
            }
            if (auxNm_especie.equals("")) {
                auxNm_especie = "CAIXA";
            }
            if (auxVl_peso_bruto <= 0) {
                auxVl_peso_bruto = 0;
            }
            if (auxVl_peso_liquido <= 0) {
                auxVl_peso_liquido = 0;
            }
        } else {
            auxCd_transportadora = Integer.parseInt(auxTransportadora);
            auxVl_tot_frete = Double.parseDouble(auxFrete);
            auxQtdeVolume = Integer.parseInt(auxVolume);
            auxVl_peso_liquido = Double.parseDouble(auxVlPesoLiquido);
            auxVl_peso_bruto = Double.parseDouble(auxVlPesoBruto);
        }
        //Verificar o Radio de Frete
        auxFgEmitente = 0;
        if (RadioEmitente.isSelected()) {
            auxFgEmitente = 0;
        } else if (RadioDestinatario.isSelected()) {
            auxFgEmitente = 1;
        } else {
            auxFgEmitente = 2;
        }

        //Verificar o Radio Tipo de Nota
        if (RadioOrcamento.isSelected()) {
            auxfg_situacao = 0;
        } else if (RadioPedido.isSelected()) {
            auxfg_situacao = 1;
        } else {
            auxfg_situacao = 2;
        }

        auxNr_nota_nfe = "1";
        auxNr_chave_nfe = "42110413359412000164550010000000111045729005";
        auxNr_prot_autorizacao = "342110031444982";
        auxDs_inf_adicionais = edtInfoAdicionais.getText();
        auxVl_tot_seguro = 0;
        auxVl_total_pedido_nota = 0;//Total da Nota/Pedido com impostos

        String auxDataEmissao = "";
        //Pega a Data de emissão,e Hora de emissão ou alteração
        //Se não existe a venda muda pra data atual o campo data emissão
        if (!vendacompletodb.getVenda(auxcd_filial, auxcd_movimento)) {
            edtDataEmissao.setText(formatadata.format(data));
            edtHoraEmissao.setText(formatahora.format(hora.getTime()));
            edtDataSaida.setText(formatadata.format(data));
            edtHoraSaida.setText(formatahora.format(hora.getTime()));
        } else {
            edtDataSaida.setText(formatadata.format(data));
            edtHoraSaida.setText(formatahora.format(hora.getTime()));
        }
        auxDataEmissao = edtDataEmissao.getText();
        String auxDataSaida = edtDataSaida.getText();
        String auxHoraEmissao = edtHoraEmissao.getText();
        String auxHoraSaida = edtHoraSaida.getText();

        //Conversao de Datas e Horas
        //Inicio
        java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        //Inicia com null as variaveis
        java.util.Date javautilDateDataEmissao = null;
        java.util.Date javautilDateDataSaida = null;
        java.util.Date javautilDateDataAlteracao = null;
        //Altera para java.util.date a String do campo de texto
        javautilDateDataEmissao = (java.util.Date) formatter.parse(auxDataEmissao);
        //Data saida e alteracao são iguais
        javautilDateDataSaida = (java.util.Date) formatter.parse(auxDataSaida);
        javautilDateDataAlteracao = (java.util.Date) formatter.parse(auxDataSaida);
        //Altera de java.util.date para java.sql.date
        java.sql.Date DataEmissao = new java.sql.Date(javautilDateDataEmissao.getTime());
        java.sql.Date DataSaida = new java.sql.Date(javautilDateDataSaida.getTime());
        java.sql.Date DataAlteracao = new java.sql.Date(javautilDateDataAlteracao.getTime());

        //Conversao da Hora
        //Converte para java.util.Date
        java.sql.Date horaEmissao = new java.sql.Date(formatahora.parse(auxHoraEmissao).getTime());
        java.sql.Date horaSaida = new java.sql.Date(formatahora.parse(auxHoraSaida).getTime());
        //Converte de java.util.Date para  para java.sql.Time
        java.sql.Time javasqltimeHoraEmissao = new java.sql.Time(horaEmissao.getTime());
        java.sql.Time javasqltimeHoraSaida = new java.sql.Time(horaSaida.getTime());
        //Fim Parte Nota

        //Parte Item já ta programado
        //Cria o Total da Venda para Rateio        
        int auxsequencia = 1;
        double TotaldoItem = 0;
        for (int x = 0; x < TabelaProdutos.getRowCount(); x++) {
            //JOptionPane.showMessageDialog(null, "Entrou aqui: for (int x = 0; x < TabelaProdutos.getRowCount(); x++)");
            int auxCd_refer_pro = (Integer.parseInt(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 0))));
            double auxQuantidade = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 2))));
            double auxVl_ven_ite_pro = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 3))));

            double auxVl_Total_Item = auxQuantidade * auxVl_ven_ite_pro;
            double Vl_Total_Item = auxVl_Total_Item;
            //VL  Rateado do frete é calculado no valor de venda do item
            //sem os descontos ou acrescimos adicionais do rateio
            double vl_frete = 0;
            double novoValorFrete = 0;
            if (rateioFrete) {
                novoValorFrete = ((auxVl_tot_frete * auxVl_Total_Item) / totalNota);
                vl_frete = novoValorFrete;
                //mensagemErro("Valor de Frete: " + vl_frete + " \n do item: " + auxCd_refer_pro);
            }

            //vl rateado do item
            double novoValorItem = 0;
            //Se a venda ja existe nao recalcula o rateio
            if ((rateioAcrescimo) || (rateioDesconto)) {
                //Novo valor do item recebe acrescimo ou desconto
                if (rateioAcrescimo) {
                    novoValorItem = auxVl_Total_Item + ((AcrescimoTotal * auxVl_Total_Item) / totalNota);
                    auxVl_Total_Item = novoValorItem;
                    novoValorItem = 0;
                } else if (rateioDesconto) {
                    //Aqui Novo valor do item recebe desconto
                    novoValorItem = auxVl_Total_Item - ((DescontoTotal * auxVl_Total_Item) / totalNota);
                    auxVl_Total_Item = novoValorItem;
                    novoValorItem = 0;
                    //auxVl_ven_ite_pro=auxVl_Total_Item/auxQuantidade;
                }
            }
            int cd_filial = 1;
            double auxVl_cus_ite_pro = 0;
            double auxvl_real_ite_pro = 0;

            double tx_icms = 0;
            double vl_base_icm = 0;
            double vl_icm = 0;

            double tx_icms_substituicao = 0;
            double vl_mva = 0;
            double vl_base_icm_substituicao = 0;
            double vl_icm_substituicao = 0;

            double tx_iss = 0;
            double vl_base_iss = 0;
            double vl_iss = 0;

            int cst_ipi = 0;
            double vl_base_ipi = 0;
            double tx_ipi = 0;
            double vl_ipi = 0;

            int cst_pis = 0;
            double vl_base_pis = 0;
            double tx_pis = 0;
            double vl_pis = 0;

            int cst_cofins = 0;
            double vl_base_cofins = 0;
            double tx_cofins = 0;
            double vl_cofins = 0;

            Date dt_emi_doc = DataEmissao;
            int cd_tipo_doc = aux_codigo_tipo_nota;
            String ab_unidade = "";
            int cd_vendedor = auxcd_vendedor;
            int cd_usuario = auxcd_usuario;
            Date dt_alt = DataAlteracao;
            Time hr_alt = javasqltimeHoraSaida;
            Date dt_cad = DataEmissao;
            Time hr_cad = javasqltimeHoraEmissao;

            int cfop = auxCfop;
            double vl_desconto = 0;
            double vl_acrescimo = 0;

            //Programacao do Desconto
            if (auxVl_ven_ite_pro < auxvl_real_ite_pro) {
                vl_desconto = auxvl_real_ite_pro - auxVl_ven_ite_pro;
            } else {
                vl_desconto = 0;
                //edtDescontoTotal.setText("0,00");
            }
            //Programacao do Acrescimo
            if (auxVl_ven_ite_pro > auxvl_real_ite_pro) {
                vl_acrescimo = auxVl_ven_ite_pro - auxvl_real_ite_pro;
            } else {
                vl_acrescimo = 0;
                //edtAcrescimoTotal.setText("0,00");
            }
            int cd_grupo_fiscal = 0;
            int cd_cst = 0;
            //deve pegar do produto estes valores
            double vl_peso_liquido = 10;
            double vl_peso_bruto = 15;
            double vl_volume = 3;
            int fg_situacao = 1;
            double vl_rateado = 0;
            double vl_impostos = 0;

            //Programação dos impostos
            //Métodos de Busca dos Dados
            //INICIO
            // public ArrayList listaDadosItemNaVenda
            //(int cd_filialparametro,int CD_GRUPO_FISCALparametro,
            //String CD_ESTADOparametro,int CD_TIPO_NOTAparametro,
            //int TIPOCONSUMOparametro, int referenciaparametro) {
            int cd_filialparametro = cd_filial;
            int CD_GRUPO_FISCALparametro = produtodb.retornaCodigoGrupoFiscal(auxCd_refer_pro);
            String CD_ESTADO_DESTINO = pessoadb.retornaCodigoEstado(auxcd_pessoa);
            String CD_ESTADO_ORIGEM = filialdb.retornaCodigoEstadoFilial(cd_filial);
            int CD_TIPO_NOTAparametro = cd_tipo_doc;
            int TIPOCONSUMOparametro = pessoadb.retornaTipoConsumo(auxcd_pessoa);
            int referenciaparametro = auxCd_refer_pro;

            //POR MSG COM DADOS DOS PARAMETROS 
            /*
             mensagemErro("Dados dos Parametros: \n"
             + "Filial:       " + cd_filialparametro + "\n"
             + "Grupo Fiscal: " + CD_GRUPO_FISCALparametro + "\n"
             + "Estado:       " + CD_ESTADOparametro + "\n"
             + "Tipo de Nota: " + CD_TIPO_NOTAparametro + "\n"
             + "Tipo  Consumo:" + TIPOCONSUMOparametro + "\n"
             + "Referencia:   " + referenciaparametro);
             */
            //mensagemErro("Dados dos Parametros precisam ser validado!");
            ArrayList<ModelTributacao> tributacoes = itemdb.listaDadosTributacaoVenda(
                    cd_filialparametro,
                    CD_GRUPO_FISCALparametro,
                    CD_ESTADO_DESTINO,
                    CD_ESTADO_ORIGEM,
                    CD_TIPO_NOTAparametro,
                    TIPOCONSUMOparametro,
                    referenciaparametro
            );
            //Variaveis de impostos
            double tx_icms_interno_destino;
            double tx_icms_interestadual_destino;
            String cd_estado_destino;
            double tx_icms_interno_origem;
            double tx_icms_interestadual_origem;
            String cd_estado_origem;

            double Aliquota_do_ICMS_Operacao_Propria_2 = 0;

            for (ModelTributacao auxItem : tributacoes) {
                auxVl_cus_ite_pro = auxItem.getVl_cus_ite_pro();
                auxvl_real_ite_pro = auxItem.getVl_ven_ite_pro();
                tx_icms_interno_destino = auxItem.getTx_icms_interno_destino();
                tx_icms_interestadual_destino = auxItem.getTx_icms_interestadual_destino();
                cd_estado_destino = auxItem.getCd_estado_destino();
                tx_icms_interno_origem = auxItem.getTx_icms_interno_origem();
                tx_icms_interestadual_origem = auxItem.getTx_icms_interestadual_origem();
                cd_estado_origem = auxItem.getCd_estado_origem();

                vl_mva = auxItem.getVl_mva();

                tx_iss = auxItem.getTx_iss();
                cst_ipi = auxItem.getCst_ipi();
                tx_ipi = auxItem.getTx_ipi();
                cst_pis = auxItem.getCst_pis();
                tx_pis = auxItem.getTx_pis();

                cst_cofins = auxItem.getCst_cofins();
                tx_cofins = auxItem.getTx_cofins();
                ab_unidade = auxItem.getAb_unidade();

                if (cd_estado_destino.equals(cd_estado_origem)) {
                    cfop = auxItem.getCfop_estadual();
                    tx_icms = auxItem.getTx_icms_interno_origem();
                    tx_icms_substituicao = auxItem.getTx_icms_interno_origem();
                    Aliquota_do_ICMS_Operacao_Propria_2 = auxItem.getTx_icms_interno_origem();
                } else if (!cd_estado_destino.equals(cd_estado_origem)) {
                    cfop = auxItem.getCfop_inter_estadual();
                    tx_icms = auxItem.getTx_icms_interestadual_origem();
                    tx_icms_substituicao = auxItem.getTx_icms_interno_destino();
                    Aliquota_do_ICMS_Operacao_Propria_2 = auxItem.getTx_icms_interestadual_origem();
                }
                cd_grupo_fiscal = auxItem.getCd_grupo_fiscal();
                cd_cst = auxItem.getCd_cst();
            }
            /*
             mensagemErro("Dados dos Objetos retornados: \n"
             + "Valor de Custo:                          " + auxVl_cus_ite_pro + "\n"
             + "Valor Real do Item:			" + auxvl_real_ite_pro + "\n"
             + "Taxa de ICMS:				" + tx_icms + "\n"
             + "Taxa de ICMS ST:				" + tx_icms_substituicao + "\n"
             + "Valor de MVA:				" + vl_mva + "\n"
             + "Taxa de ISS:				" + tx_iss + "\n"
             + "CST de IPI:				" + cst_ipi + "\n"
             + "Taxa de IPI:				" + tx_ipi + "\n"
             + "CST de PIS:				" + cst_pis + "\n"
             + "Taxa de PIS:				" + tx_pis + "\n"
             + "CST de Cofins:				" + cst_cofins + "\n"
             + "Taxa de Cofins:				" + tx_cofins + "\n"
             + "AB Unidade:				" + ab_unidade + "\n"
             + "CFOP da Venda:				" + cfop + "\n"
             + "Grupo Fiscal:				" + cd_grupo_fiscal + "\n"
             + "CST da Venda:				" + cd_cst);
             */
            //Calculos dos Valores
            //ICMS
            vl_base_icm = auxVl_Total_Item + vl_frete;
            vl_icm = (tx_icms / 100) * vl_base_icm;
            //ISS
            vl_base_iss = auxVl_Total_Item;
            vl_iss = auxVl_Total_Item * (tx_iss / 100);
            //IPI
            vl_base_ipi = auxVl_Total_Item;
            vl_ipi = vl_base_ipi * (tx_ipi / 100);
            //PIS
            vl_base_pis = auxVl_Total_Item;
            vl_pis = vl_base_pis * (tx_pis / 100);
            //COFINS
            vl_base_cofins = auxVl_Total_Item;
            vl_cofins = vl_base_cofins * (tx_cofins / 100);

            //FIM
            //Calculo ST Completo 
            double Valor_do_Produto_1 = auxVl_Total_Item;
            double Aliquota_do_IPI_3 = tx_ipi;//Pega do Cadastro do Produto
            double Valor_do_IPI_4 = (Valor_do_Produto_1 + vl_frete) * (Aliquota_do_IPI_3 / 100); //1 * 3 
            double Valor_do_Frete_5 = vl_frete;//Pega do Rateio de Frete  
            double Valor_Despesas_Acessorias_6 = vl_rateado;//Pega do Rateio do acrescimo  
            double Valor_do_Seguro_7 = 0;//Nao tera no caso,fica sempre zero   
            double Valor_de_Desconto_8 = vl_rateado;//Pega do Rateio do Desconto 
            double Percent_Reduc_ICMS_Operacao_Propria_9 = 0;//Nao tera no caso,fica sempre zero 
            double Percent_Reduc_ICMS_na_UF_Destinataria_10 = 0;//Nao tera no caso,fica sempre zero 

            //Cáculos
            double Valor_Operacao_Propria_11 = 0;//  ((1+5+6+7)-8)-(((1+5+6+7)-8)*9)
            double aux_11_Valor_Operacao_Propria = (Valor_do_Produto_1 + Valor_do_Frete_5 + Valor_Despesas_Acessorias_6 + Valor_do_Seguro_7) - Valor_de_Desconto_8;
            Valor_Operacao_Propria_11 = aux_11_Valor_Operacao_Propria - (aux_11_Valor_Operacao_Propria * Percent_Reduc_ICMS_Operacao_Propria_9);

            double Valor_Operacao_ST_12 = 0;// ((1+4+5+6+7)-8)-(((1+4+5+6+7)-8)*10)
            double aux_12_Valor_Operacao_ST = (Valor_do_Produto_1 + Valor_do_IPI_4 + Valor_do_Frete_5 + Valor_Despesas_Acessorias_6 + Valor_do_Seguro_7) - Valor_de_Desconto_8;
            Valor_Operacao_ST_12 = aux_12_Valor_Operacao_ST - (aux_12_Valor_Operacao_ST * Percent_Reduc_ICMS_na_UF_Destinataria_10);

            double Valor_ICMS_Operacao_Propria_13 = 0;// (11x2)	
            Valor_ICMS_Operacao_Propria_13 = Valor_Operacao_Propria_11 * (Aliquota_do_ICMS_Operacao_Propria_2 / 100);

            double MVA_Original_14 = vl_mva;//Pega do Cadastro do Produto
            double MVA_Utilizado_15 = MVA_Original_14;//So nao usa se a pessoa for consumo,dai é zero

            double Valor_Agregado_16 = 0;// (12x15)
            Valor_Agregado_16 = Valor_Operacao_ST_12 * (MVA_Utilizado_15 / 100);

            double Base_ST_17 = 0;// (12+16)	
            Base_ST_17 = Valor_Operacao_ST_12 + Valor_Agregado_16;

            double Aliquota_ICMS_ST_18 = tx_icms_substituicao;
            //-Se Estado Destinatario for igual origem,
            //Aliquota =Origem,senao aliquota interna é igual do Destinatario

            double Valor_ICMS_ST_19 = 0;// (17 x 18) - 13
            Valor_ICMS_ST_19 = (Base_ST_17 * (Aliquota_ICMS_ST_18 / 100)) - Valor_ICMS_Operacao_Propria_13;

            //Teste dos Valores Passados:
            /*
             JOptionPane.showMessageDialog(null, "Dados da ST:\n"
             + "Aliquota_ICMS_ST_18: " + Aliquota_ICMS_ST_18 + "\n"
             + "MVA_Utilizado_15: " + MVA_Utilizado_15 + "\n"
             + "Valor_Operacao_ST_12: " + Valor_Operacao_ST_12 + "\n"
             + "Valor_Agregado_16: " + Valor_Agregado_16 + "\n"
             + "");
             */
            //Atribuicao dos valores para a classe
            vl_ipi = Valor_do_IPI_4;
            tx_icms_substituicao = Aliquota_ICMS_ST_18;
            vl_mva = MVA_Utilizado_15;
            vl_base_icm_substituicao = Base_ST_17; //Calculado
            vl_icm_substituicao = Valor_ICMS_ST_19;

            //Verifica que tipo de imposto será calculado ou não
            /*
             Verificar quando é calculado os impostos abaixo:
             Verificar tipo de Empresa
             1.0->Variavel int TipoPessoa 
             1.1-Se for igual a 0 a pessoa é fisica 
             1.2-Se for igual a 1 a pessoa é Simples Nacional
             1.2.1-Verificar se é ISENTO só calcula se for com Inscricao Estadual
             1.3-Se for igual a 2 a pessoa é Regime Normal
             1.3.1-Verificar se é ISENTO só calcula se for com Inscricao Estadual		
             ->ModelPessoa Fisica   ->Aqui não calcula nada
             ->Simples Nacional->Aqui apenas calcula ICMS Substituição Tributária e IPI
             ->Regime Normal-> Aqui calcula Tudo
             */
            int isento = 0;
            String auxisento = "";
            int TipoTributacao = 0;
            int TipoPessoa = 0;

            boolean ICMSNormal = false;
            boolean ICMSSubstituicaoTributaria = false;
            boolean IPI = false;
            boolean PIS = false;
            boolean COFINS = false;
            boolean ISS = false;

            ArrayList<ModelPessoa> pessoas = pessoadb.verificaDadosImpostos(auxcd_pessoa);
            for (ModelPessoa auxPessoa : pessoas) {
                TipoPessoa = auxPessoa.getTipo_consumo();
                TipoTributacao = auxPessoa.getRegime_tributacao();
                auxisento = auxPessoa.getNr_inscricao_estadual().toUpperCase();
                if (auxisento.equals("ISENTO")) {
                    isento = 1;
                } else {
                    isento = 0;
                }
            }

            if (TipoPessoa == 0) {
                //Todos Impostos Zerados
                ICMSNormal = false;
                ICMSSubstituicaoTributaria = false;
                IPI = true;
                PIS = false;
                COFINS = false;
                ISS = false;
            } else {
                //Verifica o tipo de tributação
                switch (TipoTributacao) {
                    case 0:
                        //Todos Impostos Zerados
                        ICMSNormal = false;
                        ICMSSubstituicaoTributaria = false;
                        IPI = true;
                        PIS = false;
                        COFINS = false;
                        ISS = false;
                        break;
                    case 1:
                        //Simples Nacional
                        //Verificar se não é ISENTO
                        if (isento == 1) {
                            //Todos Impostos Zerados
                            ICMSNormal = false;
                            ICMSSubstituicaoTributaria = false;
                            IPI = true;
                            PIS = false;
                            COFINS = false;
                            ISS = false;
                        } else {
                            //Calcula Impostos
                            ICMSNormal = false;
                            ICMSSubstituicaoTributaria = true;
                            IPI = true;
                            PIS = false;
                            COFINS = false;
                            ISS = false;
                        }   break;
                    case 2:
                        //Regime Normal
                        //Verificar se não é ISENTO
                        if (isento == 1) {
                            //Todos Impostos Zerados
                            ICMSNormal = false;
                            ICMSSubstituicaoTributaria = false;
                            IPI = true;
                            PIS = false;
                            COFINS = false;
                            ISS = false;
                        } else {
                            //Calcula todos os Impostos
                            ICMSNormal = true;
                            ICMSSubstituicaoTributaria = true;
                            IPI = true;
                            PIS = true;
                            COFINS = true;
                            ISS = true;
                        }   break;
                    default:
                        break;
                }
            }
            //Validacao dos Impostos Totais
            if (!ICMSNormal) {
                //Zera valores de ICMS
                tx_icms = 0;
                vl_base_icm = 0;
                vl_icm = 0;
            } else {
                //Valida Dados para Calculo de ICMS
            }
            if (!ICMSSubstituicaoTributaria) {
                //Zera valores de Substituicao
                tx_icms_substituicao = 0;
                vl_mva = 0;
                vl_base_icm_substituicao = 0;
                vl_icm_substituicao = 0;
            } else {
                //Valida Dados para Calculo de ICMS Substituicao
            }
            if (!IPI) {
                //Zera Valores de IPI
                vl_base_ipi = 0;
                tx_ipi = 0;
                vl_ipi = 0;
            } else {
                //Valida Dados para Calculo de IPI
            }
            if (!PIS) {
                //Zera Valores de PIS
                vl_base_pis = 0;
                tx_pis = 0;
                vl_pis = 0;
            } else {
                //Valida Dados para Calculo de PIS
            }
            if (!COFINS) {
                //Zera Valores de COFINS
                vl_base_cofins = 0;
                tx_cofins = 0;
                vl_cofins = 0;
            } else {
                //Valida Dados para Calculo de COFINS
            }
            if (!ISS) {
                //Zera Valores de ISS                
                tx_iss = 0;
                vl_base_iss = 0;
                vl_iss = 0;
            } else {
                //Valida Dados para Calculo de ISS
            }
            ModelItem item = new ModelItem(
                    cd_filial,
                    auxcd_movimento,
                    auxsequencia,
                    auxCd_refer_pro,
                    fg_situacao,
                    auxVl_cus_ite_pro,
                    auxVl_ven_ite_pro,
                    auxvl_real_ite_pro,
                    tx_icms,
                    vl_base_icm,
                    vl_icm,
                    tx_icms_substituicao,
                    vl_mva,
                    vl_base_icm_substituicao,
                    vl_icm_substituicao,
                    tx_iss,
                    vl_base_iss,
                    vl_iss,
                    cst_ipi,
                    vl_base_ipi,
                    tx_ipi,
                    vl_ipi,
                    cst_pis,
                    vl_base_pis,
                    tx_pis,
                    vl_pis,
                    cst_cofins,
                    vl_base_cofins,
                    tx_cofins,
                    vl_cofins,
                    dt_emi_doc,
                    cd_tipo_doc,
                    ab_unidade,
                    cd_vendedor,
                    cd_usuario,
                    dt_alt,
                    hr_alt,
                    dt_cad,
                    hr_cad,
                    cfop,
                    vl_desconto,
                    vl_acrescimo,
                    cd_grupo_fiscal,
                    cd_cst,
                    vl_peso_liquido,
                    vl_peso_bruto,
                    vl_volume,
                    fg_situacao,
                    vl_rateado,
                    vl_frete,
                    vl_impostos
            );

            if (itemdb.getItem(cd_filial, auxcd_movimento, auxsequencia)) {
                //Alterar Item
                if (itemdb.alterar(item)) {
                    //JOptionPane.showMessageDialog(null, "Item da sequencia: " + auxsequencia + " alterado com sucesso!");
                } else {
                    mensagemErro("Não foi possível alterar o item da sequencia" + auxsequencia);
                    gravouTodosItens = false;
                }
            } else {
                //Apenas aqui Deve somar os totalizadores
                if (itemdb.inserir(item)) {
                    //JOptionPane.showMessageDialog(null, "Item da sequencia: " + auxsequencia + " inserido com sucesso!");
                } else {
                    mensagemErro("Não foi possível incluir o Item da sequencia: " + auxsequencia);
                    gravouTodosItens = false;
                }
            }

            //Valores Totalizadores sendo carregados
            //Custos
            auxVl_tot_cus_doc = auxVl_tot_cus_doc + auxVl_cus_ite_pro;
            //Produtos
            auxVl_tot_pro_doc = auxVl_tot_pro_doc + Vl_Total_Item;
            //ICMS
            if (vl_base_icm > 0) {
                auxVl_base_icm_total = auxVl_base_icm_total + vl_base_icm + vl_ipi;
                auxVl_icm_total = auxVl_icm_total + vl_icm;
            } else {
                auxVl_base_icm_total = 0;
                auxVl_icm_total = 0;
            }
            //IPI
            auxVl_base_ipi_total = auxVl_base_icm_total;
            auxVl_ipi_total = auxVl_ipi_total + vl_ipi;
            //ST
            auxVl_base_icm_sub_total = auxVl_base_icm_sub_total + vl_base_icm_substituicao;
            auxVl_icm_sub_total = auxVl_icm_sub_total + vl_icm_substituicao;
            //PIS
            auxVl_base_pis_total = auxVl_base_pis_total + vl_base_pis;
            //PIS
            auxVl_pis_total = auxVl_pis_total + vl_pis;
            //COFINS
            auxVl_base_cofins_total = auxVl_base_cofins_total + vl_base_cofins;
            auxVl_cofins_total = auxVl_cofins_total + vl_cofins;
            //Serviço
            auxVl_base_servico_total = auxVl_base_servico_total + vl_base_iss;
            auxVl_servico_total = auxVl_servico_total + vl_iss;
            //ISSQN
            auxVl_base_issqn_total = auxVl_base_issqn_total + vl_base_iss;
            auxVl_issqn_total = auxVl_issqn_total + vl_iss;

            TotaldoItem = TotaldoItem + auxVl_Total_Item;
            auxsequencia++;
        }
        //Total Impostos Pagos
        auxVl_Total_impostos = auxVl_ipi_total + auxVl_icm_total + auxVl_icm_sub_total + auxVl_pis_total + auxVl_cofins_total + auxVl_servico_total;

        //Total da Nota
        auxVl_total_pedido_nota = (TotaldoItem
                + auxVl_icm_sub_total
                + auxVl_tot_frete
                + auxVl_ipi_total);
        //Arredondando Total da Nota
        //Uso da classe BigDecimal para formatar os valores decimais
        //double d = 12.548795;
        //BigDecimal bd = new BigDecimal(d).setScale(3, RoundingMode.HALF_EVEN);
        //System.out.println(bd.doubleValue());
        //Imprime 12.549
        //auxVl_total_pedido_nota
        int CasasAposVirgula = 2;
        BigDecimal bd = new BigDecimal(auxVl_base_icm_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd1 = new BigDecimal(auxVl_icm_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd2 = new BigDecimal(auxVl_base_icm_sub_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd3 = new BigDecimal(auxVl_icm_sub_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd4 = new BigDecimal(auxVl_tot_frete).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);

        BigDecimal bd5 = new BigDecimal(auxVl_desconto).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd6 = new BigDecimal(auxVl_acrescimo).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd7 = new BigDecimal(TotaldoItem).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd8 = new BigDecimal(auxVl_ipi_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd9 = new BigDecimal(auxVl_total_pedido_nota).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);

        BigDecimal bd10 = new BigDecimal(auxVl_base_cofins_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd11 = new BigDecimal(auxVl_cofins_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd12 = new BigDecimal(auxVl_base_pis_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd13 = new BigDecimal(auxVl_pis_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);

        auxVl_base_icm_total = (bd.doubleValue());
        auxVl_icm_total = (bd1.doubleValue());
        auxVl_base_icm_sub_total = (bd2.doubleValue());
        auxVl_icm_sub_total = (bd3.doubleValue());
        auxVl_tot_frete = (bd4.doubleValue());

        auxVl_desconto = (bd5.doubleValue());
        auxVl_acrescimo = (bd6.doubleValue());
        TotaldoItem = (bd7.doubleValue());
        auxVl_ipi_total = (bd8.doubleValue());
        auxVl_total_pedido_nota = (bd9.doubleValue());

        auxVl_base_cofins_total = (bd10.doubleValue());
        auxVl_cofins_total = (bd11.doubleValue());
        auxVl_base_pis_total = (bd12.doubleValue());
        auxVl_pis_total = (bd13.doubleValue());
        /*
         mensagemErro(
         "Total de Produtos: " + TotaldoItem + "\n"
         + "Total nota: " + auxVl_total_pedido_nota + "\n"
         + "Total de ST: " + auxVl_icm_sub_total + "\n"
         + "Total de Frete:" + auxVl_tot_frete + "\n"
         + "Total de IPI: " + auxVl_ipi_total + "\n"
         );
         */
        //JOptionPane.showMessageDialog(null, "Programacao da Tabela Itens_orc finalizada com sucesso!\n"
        //        + "Iniciando Programacao da Tabela Orcamento!");
        //Repassa valores da Nota para a classe
        ModelVenda venda = new ModelVenda(
                auxcd_filial,
                auxcd_movimento,
                auxcd_vendedor,
                auxcd_pagto,
                auxcd_pessoa,
                DataEmissao,
                DataSaida,
                auxVl_tot_cus_doc,
                auxVl_tot_pro_doc,
                auxVl_acrescimo,
                auxVl_desconto,
                aux_codigo_tipo_nota,
                auxfg_situacao,
                auxFg_movimentou_estoque,
                auxcd_usuario,
                DataAlteracao,
                javasqltimeHoraSaida,
                DataEmissao,
                javasqltimeHoraEmissao,
                auxCfop,
                auxVl_base_icm_total,
                auxVl_icm_total,
                auxVl_base_icm_sub_total,
                auxVl_icm_sub_total,
                auxVl_base_pis_total,
                auxVl_pis_total,
                auxVl_base_cofins_total,
                auxVl_cofins_total,
                auxVl_base_ipi_total,
                auxVl_ipi_total,
                auxVl_base_servico_total,
                auxVl_servico_total,
                auxVl_base_issqn_total,
                auxVl_issqn_total,
                auxCd_transportadora,
                auxNr_PlacaVeiculo,
                auxQtdeVolume,
                auxFgEmitente,
                auxVl_tot_frete,
                auxNm_especie,
                auxNr_nota_nfe,
                auxNr_chave_nfe,
                auxNr_prot_autorizacao,
                auxDs_inf_adicionais,
                auxVl_tot_seguro,
                auxVl_total_pedido_nota,
                auxDsMarca,
                auxNumeracao,
                auxVl_peso_liquido,
                auxVl_peso_bruto,
                auxVl_Total_impostos
        );
        //So Deve inserir a Venda se inseriu todos os itens verificando pela variavel "gravouTodosItens"
        if (gravouTodosItens) {
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja Pré-Visualizar os Impostos?");
            if (resposta == JOptionPane.NO_OPTION) {
                if (vendacompletodb.getVenda(auxcd_filial, auxcd_movimento)) {
                    //AlterarRegistro();
                    if (vendacompletodb.alterarVenda(venda)) {
                        JOptionPane.showMessageDialog(null, "Venda alterada com sucesso!");
                        habilitaCampos(false);
                    } else {
                        mensagemErro("Problema ao alterar a venda!");
                        gravouVenda = false;
                    }
                } else {
                    //Aqui deve Gravar a Venda,depois de gravar os itens
                    if (vendacompletodb.gravarVenda(venda)) {
                        JOptionPane.showMessageDialog(null, "Venda incluída com sucesso!");
                        habilitaCampos(false);
                    } else {
                        mensagemErro("Problema ao gravar a venda!");
                        gravouVenda = false;
                    }
                }

            } else {
                //11 Valores            
                String campoauxVl_base_icm_total = "" + auxVl_base_icm_total;
                String campoauxVl_icm_total = "" + auxVl_icm_total;
                String campoauxVl_base_icm_sub_total = "" + auxVl_base_icm_sub_total;
                String campoauxVl_icm_sub_total = "" + auxVl_icm_sub_total;
                String campoauxVl_frete_total = "" + auxVl_tot_frete;
                String campoauxVl_Desconto = "" + auxVl_desconto;
                String campoauxVl_Acrescimo = "" + auxVl_acrescimo;
                String campoauxTotalProdutos = "" + TotaldoItem;
                String campoauxTotalIPI = "" + auxVl_ipi_total;
                String campoauxVl_TOTAL_NOTA = "" + auxVl_total_pedido_nota;
                String campoauxBaseCOFINS = "" + auxVl_base_cofins_total;
                String campoauxVL_COFINS = "" + auxVl_cofins_total;
                String campoauxBASE_PIS = "" + auxVl_base_pis_total;
                String campoauxVl_PIS = "" + auxVl_pis_total;

                //Passando valores para os campos
                edtBaseTotalICMS.setText(campoauxVl_base_icm_total);
                edtVLTotalICMS.setText(campoauxVl_icm_total);
                edtBaseTotalICMS_ST.setText(campoauxVl_base_icm_sub_total);
                edtVLTotalICMS_ST.setText(campoauxVl_icm_sub_total);
                edtTotalFrete.setText(campoauxVl_frete_total);
                edtVLTotalDesconto.setText(campoauxVl_Desconto);
                edtDespesasAcessorias.setText(campoauxVl_Acrescimo);
                edtTotalProdutos2.setText(campoauxTotalProdutos);
                edtTotalIPI.setText(campoauxTotalIPI);
                edtTotalNota.setText(campoauxVl_TOTAL_NOTA);
                edtBaseCOFINS.setText(campoauxBaseCOFINS);
                edtValorCOFINS.setText(campoauxVL_COFINS);
                edtBasePIS.setText(campoauxBASE_PIS);
                edtValorPIS.setText(campoauxVl_PIS);

                //Totais da Nota
                labelAcrescimoTotal.setText(campoauxVl_Acrescimo);
                labelDescontoTotal.setText(campoauxVl_Desconto);
                edtTotalProduto.setText(campoauxTotalProdutos);
                edtTotalVenda.setText(campoauxVl_TOTAL_NOTA);
            }
        } else {
            mensagemErro("Problema ao gravar a venda!Itens não foram inseridos com sucesso!");
        }
    }

    private void GeraTotaisNota() throws ParseException {
        //Definição das variaveis deste método
        int auxcd_filial = 0;
        int auxcd_movimento = 0;
        int auxcd_vendedor = 0;
        int auxcd_pagto = 0;
        int auxcd_pessoa = 0;
        double auxVl_tot_cus_doc = 0;
        double auxVl_tot_pro_doc = 0;
        double auxVl_acrescimo = 0;
        double auxVl_desconto = 0;
        int auxfg_situacao = 0;
        int auxFg_movimentou_estoque = 0;
        int auxcd_usuario = 0;
        int auxCfop = 0;
        double auxVl_base_icm_total = 0;
        double auxVl_icm_total = 0;
        double auxVl_base_icm_sub_total = 0;
        double auxVl_icm_sub_total = 0;
        double auxVl_base_pis_total = 0;
        double auxVl_pis_total = 0;
        double auxVl_base_cofins_total = 0;
        double auxVl_cofins_total = 0;
        double auxVl_base_ipi_total = 0;
        double auxVl_ipi_total = 0;
        double auxVl_base_servico_total = 0;
        double auxVl_servico_total = 0;
        double auxVl_base_issqn_total = 0;
        double auxVl_issqn_total = 0;
        int auxCd_transportadora = 0;
        String auxNr_PlacaVeiculo = "";
        int auxQtdeVolume = 0;
        int auxFgEmitente = 0;
        double auxVl_tot_frete = 0;
        String auxNm_especie = "";
        String auxNr_nota_nfe = "";
        String auxNr_chave_nfe = "";
        String auxNr_prot_autorizacao = "";
        String auxDs_inf_adicionais = "";
        double auxVl_tot_seguro = 0;
        double auxVl_total_pedido_nota = 0;
        String auxDsMarca = "";
        int auxNumeracao = 0;
        double auxVl_peso_liquido = 0;
        double auxVl_peso_bruto = 0;
        double auxVl_Total_impostos = 0;

//Reinicia as Variaveis antes de ler
        totalNota = 0;
        for (int x = 0; x < TabelaProdutos.getRowCount(); x++) {
            double auxVl_ven_ite_pro = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 3))));
            double auxQuantidade = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 2))));
            double total_item = auxQuantidade * auxVl_ven_ite_pro;
            totalNota = totalNota + total_item;
            //Aqui deve zerar as variaveis totalizadores e itens
            //Limpando as variaveis
            auxcd_filial = 0;
            auxcd_movimento = 0;
            auxcd_vendedor = 0;
            auxcd_pagto = 0;
            auxcd_pessoa = 0;
            auxVl_tot_cus_doc = 0;
            auxVl_tot_pro_doc = 0;
            auxVl_acrescimo = 0;
            auxVl_desconto = 0;
            auxfg_situacao = 0;
            auxFg_movimentou_estoque = 0;
            auxcd_usuario = 0;
            auxCfop = 0;
            auxVl_base_icm_total = 0;
            auxVl_icm_total = 0;
            auxVl_base_icm_sub_total = 0;
            auxVl_icm_sub_total = 0;
            auxVl_base_pis_total = 0;
            auxVl_pis_total = 0;
            auxVl_base_cofins_total = 0;
            auxVl_cofins_total = 0;
            auxVl_base_ipi_total = 0;
            auxVl_ipi_total = 0;
            auxVl_base_servico_total = 0;
            auxVl_servico_total = 0;
            auxVl_base_issqn_total = 0;
            auxVl_issqn_total = 0;
            auxCd_transportadora = 0;
            auxNr_PlacaVeiculo = "";
            auxQtdeVolume = 0;
            auxFgEmitente = 0;
            auxVl_tot_frete = 0;
            auxNm_especie = "";
            auxNr_nota_nfe = "";
            auxNr_chave_nfe = "";
            auxNr_prot_autorizacao = "";
            auxDs_inf_adicionais = "";
            auxVl_tot_seguro = 0;
            auxVl_total_pedido_nota = 0;
            auxDsMarca = "";
            auxNumeracao = 0;
            auxVl_peso_liquido = 0;
            auxVl_peso_bruto = 0;
            auxVl_Total_impostos = 0;
        }
        //mensagemErro("Total de Produtos da Nota: " + totalNota);

        //Inicia com true e so deixa False se der erro ao inserir ou alterar item.
        boolean gravouTodosItens = true;
        auxcd_filial = 1;
        auxcd_movimento = (Integer.parseInt(edtCodigoMovimento.getText()));
        auxcd_vendedor = (Integer.parseInt(edtCodigoVendedor.getText()));
        auxcd_pagto = Integer.parseInt(edtCodigoCondicaoPagamento.getText());
        auxcd_pessoa = (Integer.parseInt(edtCodigoCliente.getText()));

        String auxTransportadora = edtTransportadora.getText();
        auxNr_PlacaVeiculo = edtPlacaVeiculo.getText();
        String auxVolume = edtVolume.getText();
        String auxFrete = edtFrete.getText();
        auxNm_especie = edtEspecie.getText();
        auxDsMarca = edtMarca.getText();
        String auxVlPesoLiquido = edtPesoLiquido.getText();
        String auxVlPesoBruto = edtPesoLiquido.getText();

        auxVl_Total_impostos = 0;
        auxNumeracao = 10;

        //Campos de Acrescimo e desconto
        String auxAcrescimo = edtAcrescimoTotal.getText();
        String auxDesconto = edtDescontoTotal.getText();

        if (auxAcrescimo.equals("")) {
            auxVl_acrescimo = 0;
            edtAcrescimoTotal.setText("0.00");
        } else {
            auxVl_acrescimo = Double.parseDouble(auxAcrescimo);
            if (auxVl_acrescimo <= 0) {
                auxVl_acrescimo = 0;
            }
        }

        if (auxDesconto.equals("")) {
            auxVl_desconto = 0;
            edtDescontoTotal.setText("0.00");
        } else {
            auxVl_desconto = Double.parseDouble(auxDesconto);
            if (auxVl_desconto <= 0) {
                auxVl_desconto = 0;
            }
        }

        //Rotina de Rateio de Acrescimo e Desconto
        boolean rateioAcrescimo = false;
        boolean rateioDesconto = false;
        if ((auxVl_desconto > 0) && (auxVl_acrescimo <= 0)) {
            DescontoTotal = auxVl_desconto;
            AcrescimoTotal = 0;
            auxVl_acrescimo = 0;
            auxVl_desconto = DescontoTotal;
            rateioAcrescimo = false;
            rateioDesconto = true;
            edtAcrescimoTotal.setText("0.00");
        } else if ((auxVl_desconto <= 0) && (auxVl_acrescimo > 0)) {
            DescontoTotal = 0;
            AcrescimoTotal = auxVl_acrescimo;
            auxVl_acrescimo = AcrescimoTotal;
            auxVl_desconto = 0;
            rateioAcrescimo = true;
            rateioDesconto = false;
            edtDescontoTotal.setText("0.00");
        } else if ((auxVl_desconto > 0) && (auxVl_acrescimo > 0)) {
            AcrescimoTotal = auxVl_acrescimo - auxVl_desconto;
            if (AcrescimoTotal < 0) {
                DescontoTotal = AcrescimoTotal * -1;
                AcrescimoTotal = 0;
                auxVl_acrescimo = 0;
                auxVl_desconto = DescontoTotal;
                rateioAcrescimo = false;
                rateioDesconto = true;
                edtAcrescimoTotal.setText("0.00");
            } else {
                DescontoTotal = 0;
                AcrescimoTotal = auxVl_acrescimo - auxVl_desconto;
                auxVl_acrescimo = AcrescimoTotal;
                auxVl_desconto = 0;
                rateioAcrescimo = true;
                rateioDesconto = false;
                edtDescontoTotal.setText("0.00");
            }
        } else {
            //mensagemErro("sem acrescimo e sem desconto AQUI");
            rateioAcrescimo = false;
            rateioDesconto = false;
        }
        //Rotina de Rateio de Frete
        boolean rateioFrete = false;
        String auxRateioFrete = edtFrete.getText();
        if (auxRateioFrete.equals("")) {
            auxVl_tot_frete = 0;
            rateioFrete = false;
        } else {
            auxVl_tot_frete = Double.parseDouble(edtFrete.getText());
            rateioFrete = true;
        }
        //Conversao do valor com virgula
        //String conversaoDesconto = auxDesconto;

        //Comentando programacao da virgula para testes
/*
         DecimalFormat df = new DecimalFormat("###,##0.0000");
         String valorDesconto = auxDesconto.replaceAll("\\.", "").replace(",", ".");
         String valorAcrescimo = auxAcrescimo.replaceAll("\\.", "").replace(",", ".");
         String valorFrete = auxFrete.replaceAll("\\.", "").replace(",", ".");

         System.out.println("Valor Desconto nao formatado: " + valorDesconto);
         System.out.println("Valor Desconto formatado: " + df.format(new Double(valorDesconto)));

         System.out.println("Valor Acrescimo nao formatado: " + valorAcrescimo);
         System.out.println("Valor Acrescimo formatado: " + df.format(new Double(valorAcrescimo)));

         System.out.println("Valor Frete nao formatado: " + valorFrete);
         System.out.println("Valor Frete formatado: " + df.format(new Double(valorFrete)));

         auxVl_desconto = Double.parseDouble(valorDesconto);
         auxVl_acrescimo = Double.parseDouble(valorAcrescimo);
         auxVl_tot_frete = Double.parseDouble(valorFrete);
        
     
         System.out.println("Valor Frete apos o parse do double: " +auxVl_tot_frete);

         System.out.println("Valor de desconto: " + auxVl_desconto);
         System.out.println("Valor de acrescimo: " + auxVl_acrescimo);
         System.out.println("Valor de frete: " + auxVl_tot_frete);
         //Não use Double para valores monetários, use BigDecimal
         */
        if (auxTransportadora.equals("")) {
            auxCd_transportadora = 0;
            //JOptionPane.showMessageDialog(null, "Codigo da transportadora pego: " + auxCd_transportadora);

            if (auxNr_PlacaVeiculo.equals("")) {
                auxNr_PlacaVeiculo = "MNG6589";
            }
            if (auxQtdeVolume <= 0) {
                auxQtdeVolume = 0;
            }
            if (auxVl_tot_frete <= 0) {
                auxVl_tot_frete = 0;
            }
            if (auxDsMarca.equals("")) {
                auxDsMarca = "NIKE";
            }
            if (auxNm_especie.equals("")) {
                auxNm_especie = "CAIXA";
            }
            if (auxVl_peso_bruto <= 0) {
                auxVl_peso_bruto = 0;
            }
            if (auxVl_peso_liquido <= 0) {
                auxVl_peso_liquido = 0;
            }
        } else {
            auxCd_transportadora = Integer.parseInt(auxTransportadora);
            auxVl_tot_frete = Double.parseDouble(auxFrete);
            auxQtdeVolume = Integer.parseInt(auxVolume);
            auxVl_peso_liquido = Double.parseDouble(auxVlPesoLiquido);
            auxVl_peso_bruto = Double.parseDouble(auxVlPesoBruto);
        }

        //Verificar o Radio de Frete
        auxFgEmitente = 0;
        if (RadioEmitente.isSelected()) {
            auxFgEmitente = 0;
        } else if (RadioDestinatario.isSelected()) {
            auxFgEmitente = 1;
        } else {
            auxFgEmitente = 2;
        }

        //Verificar o Radio Tipo de Nota
        if (RadioOrcamento.isSelected()) {
            auxfg_situacao = 0;
        } else if (RadioPedido.isSelected()) {
            auxfg_situacao = 1;
        } else {
            auxfg_situacao = 2;
        }

        auxNr_nota_nfe = "1";
        auxNr_chave_nfe = "42110413359412000164550010000000111045729005";
        auxNr_prot_autorizacao = "342110031444982";
        auxDs_inf_adicionais = edtInfoAdicionais.getText();
        auxVl_tot_seguro = 0;
        auxVl_total_pedido_nota = 0;//Total da Nota/Pedido com impostos

        String auxDataEmissao = "";
        //Pega a Data de emissão,e Hora de emissão ou alteração
        //Se não existe a venda muda pra data atual o campo data emissão
        if (!vendacompletodb.getVenda(auxcd_filial, auxcd_movimento)) {
            edtDataEmissao.setText(formatadata.format(data));
            edtHoraEmissao.setText(formatahora.format(hora.getTime()));
            edtDataSaida.setText(formatadata.format(data));
            edtHoraSaida.setText(formatahora.format(hora.getTime()));
        } else {
            edtDataSaida.setText(formatadata.format(data));
            edtHoraSaida.setText(formatahora.format(hora.getTime()));
        }
        auxDataEmissao = edtDataEmissao.getText();
        String auxDataSaida = edtDataSaida.getText();
        String auxHoraEmissao = edtHoraEmissao.getText();
        String auxHoraSaida = edtHoraSaida.getText();

        //Conversao de Datas e Horas
        //Inicio
        java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        //Inicia com null as variaveis
        java.util.Date javautilDateDataEmissao = null;
        java.util.Date javautilDateDataSaida = null;
        java.util.Date javautilDateDataAlteracao = null;
        //Altera para java.util.date a String do campo de texto
        javautilDateDataEmissao = (java.util.Date) formatter.parse(auxDataEmissao);
        //Data saida e alteracao são iguais
        javautilDateDataSaida = (java.util.Date) formatter.parse(auxDataSaida);
        javautilDateDataAlteracao = (java.util.Date) formatter.parse(auxDataSaida);
        //Altera de java.util.date para java.sql.date
        java.sql.Date DataEmissao = new java.sql.Date(javautilDateDataEmissao.getTime());
        java.sql.Date DataSaida = new java.sql.Date(javautilDateDataSaida.getTime());
        java.sql.Date DataAlteracao = new java.sql.Date(javautilDateDataAlteracao.getTime());

        //Conversao da Hora
        //Converte para java.util.Date
        java.sql.Date horaEmissao = new java.sql.Date(formatahora.parse(auxHoraEmissao).getTime());
        java.sql.Date horaSaida = new java.sql.Date(formatahora.parse(auxHoraSaida).getTime());
        //Converte de java.util.Date para  para java.sql.Time
        java.sql.Time javasqltimeHoraEmissao = new java.sql.Time(horaEmissao.getTime());
        java.sql.Time javasqltimeHoraSaida = new java.sql.Time(horaSaida.getTime());
        //Fim Parte Nota

        //Parte Item já ta programado
        //Cria o Total da Venda para Rateio        
        int auxsequencia = 1;
        double TotaldoItem = 0;
        for (int x = 0; x < TabelaProdutos.getRowCount(); x++) {
            //JOptionPane.showMessageDialog(null, "Entrou aqui: for (int x = 0; x < TabelaProdutos.getRowCount(); x++)");
            int auxCd_refer_pro = (Integer.parseInt(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 0))));
            double auxQuantidade = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 2))));
            double auxVl_ven_ite_pro = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 3))));

            double auxVl_Total_Item = auxQuantidade * auxVl_ven_ite_pro;
            System.out.println("total item: " + auxVl_Total_Item);
            System.out.println("total nota: " + auxVl_total_pedido_nota);
            double Vl_Total_Item = auxVl_Total_Item;
            //VL  Rateado do frete é calculado no valor de venda do item
            //sem os descontos ou acrescimos adicionais do rateio
            double vl_frete = 0;
            double novoValorFrete = 0;
            if (rateioFrete) {
                novoValorFrete = ((auxVl_tot_frete * auxVl_Total_Item) / totalNota);
                vl_frete = novoValorFrete;
                //mensagemErro("Valor de Frete: " + vl_frete + " \n do item: " + auxCd_refer_pro);
            }

            //vl rateado do item
            double novoValorItem = 0;
            //Se a venda ja existe nao recalcula o rateio
            if ((rateioAcrescimo) || (rateioDesconto)) {
                //Novo valor do item recebe acrescimo ou desconto
                if (rateioAcrescimo) {
                    novoValorItem = auxVl_Total_Item + ((AcrescimoTotal * auxVl_Total_Item) / totalNota);
                    auxVl_Total_Item = novoValorItem;
                    novoValorItem = 0;
                } else if (rateioDesconto) {
                    //Aqui Novo valor do item recebe desconto
                    novoValorItem = auxVl_Total_Item - ((DescontoTotal * auxVl_Total_Item) / totalNota);
                    auxVl_Total_Item = novoValorItem;
                    novoValorItem = 0;
                    //auxVl_ven_ite_pro=auxVl_Total_Item/auxQuantidade;
                }
            }
            int cd_filial = 1;
            double auxVl_cus_ite_pro = 0;
            double auxvl_real_ite_pro = 0;

            double tx_icms = 0;
            double vl_base_icm = 0;
            double vl_icm = 0;

            double tx_icms_substituicao = 0;
            double vl_mva = 0;
            double vl_base_icm_substituicao = 0;
            double vl_icm_substituicao = 0;

            double tx_iss = 0;
            double vl_base_iss = 0;
            double vl_iss = 0;

            int cst_ipi = 0;
            double vl_base_ipi = 0;
            double tx_ipi = 0;
            double vl_ipi = 0;

            int cst_pis = 0;
            double vl_base_pis = 0;
            double tx_pis = 0;
            double vl_pis = 0;

            int cst_cofins = 0;
            double vl_base_cofins = 0;
            double tx_cofins = 0;
            double vl_cofins = 0;

            Date dt_emi_doc = DataEmissao;
            int cd_tipo_doc = aux_codigo_tipo_nota;
            String ab_unidade = "";
            int cd_vendedor = auxcd_vendedor;
            int cd_usuario = auxcd_usuario;
            Date dt_alt = DataAlteracao;
            Time hr_alt = javasqltimeHoraSaida;
            Date dt_cad = DataEmissao;
            Time hr_cad = javasqltimeHoraEmissao;

            int cfop = auxCfop;
            double vl_desconto = 0;
            double vl_acrescimo = 0;

            //Programacao do Desconto
            if (auxVl_ven_ite_pro < auxvl_real_ite_pro) {
                vl_desconto = auxvl_real_ite_pro - auxVl_ven_ite_pro;
            } else {
                vl_desconto = 0;
                //edtDescontoTotal.setText("0,00");
            }
            //Programacao do Acrescimo
            if (auxVl_ven_ite_pro > auxvl_real_ite_pro) {
                vl_acrescimo = auxVl_ven_ite_pro - auxvl_real_ite_pro;
            } else {
                vl_acrescimo = 0;
                //edtAcrescimoTotal.setText("0,00");
            }
            int cd_grupo_fiscal = 0;
            int cd_cst = 0;
            //deve pegar do produto estes valores
            double vl_peso_liquido = 10;
            double vl_peso_bruto = 15;
            double vl_volume = 3;
            int fg_situacao = 1;
            double vl_rateado = 0;
            double vl_impostos = 0;

            //Programação dos impostos
            //Métodos de Busca dos Dados
            //INICIO
            // public ArrayList listaDadosItemNaVenda
            //(int cd_filialparametro,int CD_GRUPO_FISCALparametro,
            //String CD_ESTADOparametro,int CD_TIPO_NOTAparametro,
            //int TIPOCONSUMOparametro, int referenciaparametro) {
            int cd_filialparametro = cd_filial;
            int CD_GRUPO_FISCALparametro = produtodb.retornaCodigoGrupoFiscal(auxCd_refer_pro);
            String CD_ESTADO_DESTINO = pessoadb.retornaCodigoEstado(auxcd_pessoa);
            String CD_ESTADO_ORIGEM = filialdb.retornaCodigoEstadoFilial(cd_filial);
            int CD_TIPO_NOTAparametro = cd_tipo_doc;
            int TIPOCONSUMOparametro = pessoadb.retornaTipoConsumo(auxcd_pessoa);
            int referenciaparametro = auxCd_refer_pro;

            //POR MSG COM DADOS DOS PARAMETROS 
            /*
             mensagemErro("Dados dos Parametros: \n"
             + "Filial:       " + cd_filialparametro + "\n"
             + "Grupo Fiscal: " + CD_GRUPO_FISCALparametro + "\n"
             + "Estado:       " + CD_ESTADOparametro + "\n"
             + "Tipo de Nota: " + CD_TIPO_NOTAparametro + "\n"
             + "Tipo  Consumo:" + TIPOCONSUMOparametro + "\n"
             + "Referencia:   " + referenciaparametro);
             */
            //mensagemErro("Dados dos Parametros precisam ser validado!");
            ArrayList<ModelTributacao> tributacoes = itemdb.listaDadosTributacaoVenda(
                    cd_filialparametro,
                    CD_GRUPO_FISCALparametro,
                    CD_ESTADO_DESTINO,
                    CD_ESTADO_ORIGEM,
                    CD_TIPO_NOTAparametro,
                    TIPOCONSUMOparametro,
                    referenciaparametro
            );
            //Variaveis de impostos
            double tx_icms_interno_destino;
            double tx_icms_interestadual_destino;
            String cd_estado_destino;
            double tx_icms_interno_origem;
            double tx_icms_interestadual_origem;
            String cd_estado_origem;

            double Aliquota_do_ICMS_Operacao_Propria_2 = 0;

            for (ModelTributacao auxItem : tributacoes) {
                auxVl_cus_ite_pro = auxItem.getVl_cus_ite_pro();
                auxvl_real_ite_pro = auxItem.getVl_ven_ite_pro();
                tx_icms_interno_destino = auxItem.getTx_icms_interno_destino();
                tx_icms_interestadual_destino = auxItem.getTx_icms_interestadual_destino();
                cd_estado_destino = auxItem.getCd_estado_destino();
                tx_icms_interno_origem = auxItem.getTx_icms_interno_origem();
                tx_icms_interestadual_origem = auxItem.getTx_icms_interestadual_origem();
                cd_estado_origem = auxItem.getCd_estado_origem();

                vl_mva = auxItem.getVl_mva();

                tx_iss = auxItem.getTx_iss();
                cst_ipi = auxItem.getCst_ipi();
                tx_ipi = auxItem.getTx_ipi();
                cst_pis = auxItem.getCst_pis();
                tx_pis = auxItem.getTx_pis();

                cst_cofins = auxItem.getCst_cofins();
                tx_cofins = auxItem.getTx_cofins();
                ab_unidade = auxItem.getAb_unidade();

                if (cd_estado_destino.equals(cd_estado_origem)) {
                    cfop = auxItem.getCfop_estadual();
                    tx_icms = auxItem.getTx_icms_interno_origem();
                    tx_icms_substituicao = auxItem.getTx_icms_interno_origem();
                    Aliquota_do_ICMS_Operacao_Propria_2 = auxItem.getTx_icms_interno_origem();
                } else if (!cd_estado_destino.equals(cd_estado_origem)) {
                    cfop = auxItem.getCfop_inter_estadual();
                    tx_icms = auxItem.getTx_icms_interestadual_origem();
                    tx_icms_substituicao = auxItem.getTx_icms_interno_destino();
                    Aliquota_do_ICMS_Operacao_Propria_2 = auxItem.getTx_icms_interestadual_origem();
                }
                cd_grupo_fiscal = auxItem.getCd_grupo_fiscal();
                cd_cst = auxItem.getCd_cst();
            }
            /*
             mensagemErro("Dados dos Objetos retornados: \n"
             + "Valor de Custo:                          " + auxVl_cus_ite_pro + "\n"
             + "Valor Real do Item:			" + auxvl_real_ite_pro + "\n"
             + "Taxa de ICMS:				" + tx_icms + "\n"
             + "Taxa de ICMS ST:				" + tx_icms_substituicao + "\n"
             + "Valor de MVA:				" + vl_mva + "\n"
             + "Taxa de ISS:				" + tx_iss + "\n"
             + "CST de IPI:				" + cst_ipi + "\n"
             + "Taxa de IPI:				" + tx_ipi + "\n"
             + "CST de PIS:				" + cst_pis + "\n"
             + "Taxa de PIS:				" + tx_pis + "\n"
             + "CST de Cofins:				" + cst_cofins + "\n"
             + "Taxa de Cofins:				" + tx_cofins + "\n"
             + "AB Unidade:				" + ab_unidade + "\n"
             + "CFOP da Venda:				" + cfop + "\n"
             + "Grupo Fiscal:				" + cd_grupo_fiscal + "\n"
             + "CST da Venda:				" + cd_cst);
             */
            //Calculos dos Valores
            //ICMS
            vl_base_icm = auxVl_Total_Item + vl_frete;
            vl_icm = (tx_icms / 100) * vl_base_icm;
            //ISS
            vl_base_iss = auxVl_Total_Item;
            vl_iss = auxVl_Total_Item * (tx_iss / 100);
            //IPI
            vl_base_ipi = auxVl_Total_Item;
            vl_ipi = vl_base_ipi * (tx_ipi / 100);
            //PIS
            vl_base_pis = auxVl_Total_Item;
            vl_pis = vl_base_pis * (tx_pis / 100);
            //COFINS
            vl_base_cofins = auxVl_Total_Item;
            vl_cofins = vl_base_cofins * (tx_cofins / 100);

            //FIM
            //Calculo ST Completo 
            double Valor_do_Produto_1 = auxVl_Total_Item;
            double Aliquota_do_IPI_3 = tx_ipi;//Pega do Cadastro do Produto
            double Valor_do_IPI_4 = (Valor_do_Produto_1 + vl_frete) * (Aliquota_do_IPI_3 / 100); //1 * 3 
            double Valor_do_Frete_5 = vl_frete;//Pega do Rateio de Frete  
            double Valor_Despesas_Acessorias_6 = vl_rateado;//Pega do Rateio do acrescimo  
            double Valor_do_Seguro_7 = 0;//Nao tera no caso,fica sempre zero   
            double Valor_de_Desconto_8 = vl_rateado;//Pega do Rateio do Desconto 
            double Percent_Reduc_ICMS_Operacao_Propria_9 = 0;//Nao tera no caso,fica sempre zero 
            double Percent_Reduc_ICMS_na_UF_Destinataria_10 = 0;//Nao tera no caso,fica sempre zero 

            //Cáculos
            double Valor_Operacao_Propria_11 = 0;//  ((1+5+6+7)-8)-(((1+5+6+7)-8)*9)
            double aux_11_Valor_Operacao_Propria = (Valor_do_Produto_1 + Valor_do_Frete_5 + Valor_Despesas_Acessorias_6 + Valor_do_Seguro_7) - Valor_de_Desconto_8;
            Valor_Operacao_Propria_11 = aux_11_Valor_Operacao_Propria - (aux_11_Valor_Operacao_Propria * Percent_Reduc_ICMS_Operacao_Propria_9);

            double Valor_Operacao_ST_12 = 0;// ((1+4+5+6+7)-8)-(((1+4+5+6+7)-8)*10)
            double aux_12_Valor_Operacao_ST = (Valor_do_Produto_1 + Valor_do_IPI_4 + Valor_do_Frete_5 + Valor_Despesas_Acessorias_6 + Valor_do_Seguro_7) - Valor_de_Desconto_8;
            Valor_Operacao_ST_12 = aux_12_Valor_Operacao_ST - (aux_12_Valor_Operacao_ST * Percent_Reduc_ICMS_na_UF_Destinataria_10);

            double Valor_ICMS_Operacao_Propria_13 = 0;// (11x2)	
            Valor_ICMS_Operacao_Propria_13 = Valor_Operacao_Propria_11 * (Aliquota_do_ICMS_Operacao_Propria_2 / 100);

            double MVA_Original_14 = vl_mva;//Pega do Cadastro do Produto
            double MVA_Utilizado_15 = MVA_Original_14;//So nao usa se a pessoa for consumo,dai é zero

            double Valor_Agregado_16 = 0;// (12x15)
            Valor_Agregado_16 = Valor_Operacao_ST_12 * (MVA_Utilizado_15 / 100);

            double Base_ST_17 = 0;// (12+16)	
            Base_ST_17 = Valor_Operacao_ST_12 + Valor_Agregado_16;

            double Aliquota_ICMS_ST_18 = tx_icms_substituicao;
            //-Se Estado Destinatario for igual origem,
            //Aliquota =Origem,senao aliquota interna é igual do Destinatario

            double Valor_ICMS_ST_19 = 0;// (17 x 18) - 13
            Valor_ICMS_ST_19 = (Base_ST_17 * (Aliquota_ICMS_ST_18 / 100)) - Valor_ICMS_Operacao_Propria_13;

            //Teste dos Valores Passados:
            /*
             JOptionPane.showMessageDialog(null, "Dados da ST:\n"
             + "Aliquota_ICMS_ST_18: " + Aliquota_ICMS_ST_18 + "\n"
             + "MVA_Utilizado_15: " + MVA_Utilizado_15 + "\n"
             + "Valor_Operacao_ST_12: " + Valor_Operacao_ST_12 + "\n"
             + "Valor_Agregado_16: " + Valor_Agregado_16 + "\n"
             + "");
             */
            //Atribuicao dos valores para a classe
            vl_ipi = Valor_do_IPI_4;
            tx_icms_substituicao = Aliquota_ICMS_ST_18;
            vl_mva = MVA_Utilizado_15;
            vl_base_icm_substituicao = Base_ST_17; //Calculado
            vl_icm_substituicao = Valor_ICMS_ST_19;

            //Verifica que tipo de imposto será calculado ou não
            /*
             Verificar quando é calculado os impostos abaixo:
             Verificar tipo de Empresa
             1.0->Variavel int TipoPessoa 
             1.1-Se for igual a 0 a pessoa é fisica 
             1.2-Se for igual a 1 a pessoa é Simples Nacional
             1.2.1-Verificar se é ISENTO só calcula se for com Inscricao Estadual
             1.3-Se for igual a 2 a pessoa é Regime Normal
             1.3.1-Verificar se é ISENTO só calcula se for com Inscricao Estadual		
             ->ModelPessoa Fisica   ->Aqui não calcula nada
             ->Simples Nacional->Aqui apenas calcula ICMS Substituição Tributária e IPI
             ->Regime Normal-> Aqui calcula Tudo
             */
            int isento = 0;
            String auxisento = "";
            int TipoTributacao = 0;
            int TipoPessoa = 0;

            boolean ICMSNormal = false;
            boolean ICMSSubstituicaoTributaria = false;
            boolean IPI = false;
            boolean PIS = false;
            boolean COFINS = false;
            boolean ISS = false;

            ArrayList<ModelPessoa> pessoas = pessoadb.verificaDadosImpostos(auxcd_pessoa);
            for (ModelPessoa auxPessoa : pessoas) {
                TipoPessoa = auxPessoa.getTipo_consumo();
                TipoTributacao = auxPessoa.getRegime_tributacao();
                auxisento = auxPessoa.getNr_inscricao_estadual().toUpperCase();
                if (auxisento.equals("ISENTO")) {
                    isento = 1;
                } else {
                    isento = 0;
                }
            }

            if (TipoPessoa == 0) {
                //Todos Impostos Zerados
                ICMSNormal = false;
                ICMSSubstituicaoTributaria = false;
                IPI = true;
                PIS = false;
                COFINS = false;
                ISS = false;
            } else {
                //Verifica o tipo de tributação
                switch (TipoTributacao) {
                    case 0:
                        //Todos Impostos Zerados
                        ICMSNormal = false;
                        ICMSSubstituicaoTributaria = false;
                        IPI = true;
                        PIS = false;
                        COFINS = false;
                        ISS = false;
                        break;
                    case 1:
                        //Simples Nacional
                        //Verificar se não é ISENTO
                        if (isento == 1) {
                            //Todos Impostos Zerados
                            ICMSNormal = false;
                            ICMSSubstituicaoTributaria = false;
                            IPI = true;
                            PIS = false;
                            COFINS = false;
                            ISS = false;
                        } else {
                            //Calcula Impostos
                            ICMSNormal = false;
                            ICMSSubstituicaoTributaria = true;
                            IPI = true;
                            PIS = false;
                            COFINS = false;
                            ISS = false;
                        }   break;
                    case 2:
                        //Regime Normal
                        //Verificar se não é ISENTO
                        if (isento == 1) {
                            //Todos Impostos Zerados
                            ICMSNormal = false;
                            ICMSSubstituicaoTributaria = false;
                            IPI = true;
                            PIS = false;
                            COFINS = false;
                            ISS = false;
                        } else {
                            //Calcula todos os Impostos
                            ICMSNormal = true;
                            ICMSSubstituicaoTributaria = true;
                            IPI = true;
                            PIS = true;
                            COFINS = true;
                            ISS = true;
                        }   break;
                    default:
                        break;
                }
            }
            //Validacao dos Impostos Totais
            if (!ICMSNormal) {
                //Zera valores de ICMS
                tx_icms = 0;
                vl_base_icm = 0;
                vl_icm = 0;
            } else {
                //Valida Dados para Calculo de ICMS
            }
            if (!ICMSSubstituicaoTributaria) {
                //Zera valores de Substituicao
                tx_icms_substituicao = 0;
                vl_mva = 0;
                vl_base_icm_substituicao = 0;
                vl_icm_substituicao = 0;
            } else {
                //Valida Dados para Calculo de ICMS Substituicao
            }
            if (!IPI) {
                //Zera Valores de IPI
                vl_base_ipi = 0;
                tx_ipi = 0;
                vl_ipi = 0;
            } else {
                //Valida Dados para Calculo de IPI
            }
            if (!PIS) {
                //Zera Valores de PIS
                vl_base_pis = 0;
                tx_pis = 0;
                vl_pis = 0;
            } else {
                //Valida Dados para Calculo de PIS
            }
            if (!COFINS) {
                //Zera Valores de COFINS
                vl_base_cofins = 0;
                tx_cofins = 0;
                vl_cofins = 0;
            } else {
                //Valida Dados para Calculo de COFINS
            }
            if (!ISS) {
                //Zera Valores de ISS                
                tx_iss = 0;
                vl_base_iss = 0;
                vl_iss = 0;
            } else {
                //Valida Dados para Calculo de ISS
            }
            ModelItem item = new ModelItem(
                    cd_filial,
                    auxcd_movimento,
                    auxsequencia,
                    auxCd_refer_pro,
                    fg_situacao,
                    auxVl_cus_ite_pro,
                    auxVl_ven_ite_pro,
                    auxvl_real_ite_pro,
                    tx_icms,
                    vl_base_icm,
                    vl_icm,
                    tx_icms_substituicao,
                    vl_mva,
                    vl_base_icm_substituicao,
                    vl_icm_substituicao,
                    tx_iss,
                    vl_base_iss,
                    vl_iss,
                    cst_ipi,
                    vl_base_ipi,
                    tx_ipi,
                    vl_ipi,
                    cst_pis,
                    vl_base_pis,
                    tx_pis,
                    vl_pis,
                    cst_cofins,
                    vl_base_cofins,
                    tx_cofins,
                    vl_cofins,
                    dt_emi_doc,
                    cd_tipo_doc,
                    ab_unidade,
                    cd_vendedor,
                    cd_usuario,
                    dt_alt,
                    hr_alt,
                    dt_cad,
                    hr_cad,
                    cfop,
                    vl_desconto,
                    vl_acrescimo,
                    cd_grupo_fiscal,
                    cd_cst,
                    vl_peso_liquido,
                    vl_peso_bruto,
                    vl_volume,
                    fg_situacao,
                    vl_rateado,
                    vl_frete,
                    vl_impostos
            );
            //Valores Totalizadores sendo carregados
            //Custos
            auxVl_tot_cus_doc = auxVl_tot_cus_doc + auxVl_cus_ite_pro;
            //Produtos
            auxVl_tot_pro_doc = auxVl_tot_pro_doc + Vl_Total_Item;
            //ICMS
            if (vl_base_icm > 0) {
                auxVl_base_icm_total = auxVl_base_icm_total + vl_base_icm + vl_ipi;
                auxVl_icm_total = auxVl_icm_total + vl_icm;
            } else {
                auxVl_base_icm_total = 0;
                auxVl_icm_total = 0;
            }
            //IPI
            auxVl_base_ipi_total = auxVl_base_icm_total;
            auxVl_ipi_total = auxVl_ipi_total + vl_ipi;
            //ST
            auxVl_base_icm_sub_total = auxVl_base_icm_sub_total + vl_base_icm_substituicao;
            auxVl_icm_sub_total = auxVl_icm_sub_total + vl_icm_substituicao;
            //PIS
            auxVl_base_pis_total = auxVl_base_pis_total + vl_base_pis;
            //PIS
            auxVl_pis_total = auxVl_pis_total + vl_pis;
            //COFINS
            auxVl_base_cofins_total = auxVl_base_cofins_total + vl_base_cofins;
            auxVl_cofins_total = auxVl_cofins_total + vl_cofins;
            //Serviço
            auxVl_base_servico_total = auxVl_base_servico_total + vl_base_iss;
            auxVl_servico_total = auxVl_servico_total + vl_iss;
            //ISSQN
            auxVl_base_issqn_total = auxVl_base_issqn_total + vl_base_iss;
            auxVl_issqn_total = auxVl_issqn_total + vl_iss;

            TotaldoItem = TotaldoItem + auxVl_Total_Item;
            auxsequencia++;
        }
        //Total Impostos Pagos
        auxVl_Total_impostos = auxVl_ipi_total + auxVl_icm_total + auxVl_icm_sub_total + auxVl_pis_total + auxVl_cofins_total + auxVl_servico_total;

        //Total da Nota
        auxVl_total_pedido_nota = (TotaldoItem
                + auxVl_icm_sub_total
                + auxVl_tot_frete
                + auxVl_ipi_total);
        //Arredondando Total da Nota
        //Uso da classe BigDecimal para formatar os valores decimais
        //double d = 12.548795;
        //BigDecimal bd = new BigDecimal(d).setScale(3, RoundingMode.HALF_EVEN);
        //System.out.println(bd.doubleValue());
        //Imprime 12.549
        //auxVl_total_pedido_nota
        int CasasAposVirgula = 2;
        BigDecimal bd = new BigDecimal(auxVl_base_icm_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd1 = new BigDecimal(auxVl_icm_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd2 = new BigDecimal(auxVl_base_icm_sub_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd3 = new BigDecimal(auxVl_icm_sub_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd4 = new BigDecimal(auxVl_tot_frete).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);

        BigDecimal bd5 = new BigDecimal(auxVl_desconto).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd6 = new BigDecimal(auxVl_acrescimo).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd7 = new BigDecimal(TotaldoItem).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd8 = new BigDecimal(auxVl_ipi_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd9 = new BigDecimal(auxVl_total_pedido_nota).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);

        BigDecimal bd10 = new BigDecimal(auxVl_base_cofins_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd11 = new BigDecimal(auxVl_cofins_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd12 = new BigDecimal(auxVl_base_pis_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        BigDecimal bd13 = new BigDecimal(auxVl_pis_total).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);

        auxVl_base_icm_total = (bd.doubleValue());
        auxVl_icm_total = (bd1.doubleValue());
        auxVl_base_icm_sub_total = (bd2.doubleValue());
        auxVl_icm_sub_total = (bd3.doubleValue());
        auxVl_tot_frete = (bd4.doubleValue());

        auxVl_desconto = (bd5.doubleValue());
        auxVl_acrescimo = (bd6.doubleValue());
        TotaldoItem = (bd7.doubleValue());
        auxVl_ipi_total = (bd8.doubleValue());
        auxVl_total_pedido_nota = (bd9.doubleValue());

        auxVl_base_cofins_total = (bd10.doubleValue());
        auxVl_cofins_total = (bd11.doubleValue());
        auxVl_base_pis_total = (bd12.doubleValue());
        auxVl_pis_total = (bd13.doubleValue());
        /*
         mensagemErro(
         "Total de Produtos: " + TotaldoItem + "\n"
         + "Total nota: " + auxVl_total_pedido_nota + "\n"
         + "Total de ST: " + auxVl_icm_sub_total + "\n"
         + "Total de Frete:" + auxVl_tot_frete + "\n"
         + "Total de IPI: " + auxVl_ipi_total + "\n"
         );
         */
        //JOptionPane.showMessageDialog(null, "Programacao da Tabela Itens_orc finalizada com sucesso!\n"
        //        + "Iniciando Programacao da Tabela Orcamento!");
        //Repassa valores da Nota para a classe
        ModelVenda venda = new ModelVenda(
                auxcd_filial,
                auxcd_movimento,
                auxcd_vendedor,
                auxcd_pagto,
                auxcd_pessoa,
                DataEmissao,
                DataSaida,
                auxVl_tot_cus_doc,
                auxVl_tot_pro_doc,
                auxVl_acrescimo,
                auxVl_desconto,
                aux_codigo_tipo_nota,
                auxfg_situacao,
                auxFg_movimentou_estoque,
                auxcd_usuario,
                DataAlteracao,
                javasqltimeHoraSaida,
                DataEmissao,
                javasqltimeHoraEmissao,
                auxCfop,
                auxVl_base_icm_total,
                auxVl_icm_total,
                auxVl_base_icm_sub_total,
                auxVl_icm_sub_total,
                auxVl_base_pis_total,
                auxVl_pis_total,
                auxVl_base_cofins_total,
                auxVl_cofins_total,
                auxVl_base_ipi_total,
                auxVl_ipi_total,
                auxVl_base_servico_total,
                auxVl_servico_total,
                auxVl_base_issqn_total,
                auxVl_issqn_total,
                auxCd_transportadora,
                auxNr_PlacaVeiculo,
                auxQtdeVolume,
                auxFgEmitente,
                auxVl_tot_frete,
                auxNm_especie,
                auxNr_nota_nfe,
                auxNr_chave_nfe,
                auxNr_prot_autorizacao,
                auxDs_inf_adicionais,
                auxVl_tot_seguro,
                auxVl_total_pedido_nota,
                auxDsMarca,
                auxNumeracao,
                auxVl_peso_liquido,
                auxVl_peso_bruto,
                auxVl_Total_impostos
        );
//11 Valores sendo passados para os totais da nota           
        String campoauxVl_base_icm_total = "" + auxVl_base_icm_total;
        String campoauxVl_icm_total = "" + auxVl_icm_total;
        String campoauxVl_base_icm_sub_total = "" + auxVl_base_icm_sub_total;
        String campoauxVl_icm_sub_total = "" + auxVl_icm_sub_total;
        String campoauxVl_frete_total = "" + auxVl_tot_frete;
        String campoauxVl_Desconto = "" + auxVl_desconto;
        String campoauxVl_Acrescimo = "" + auxVl_acrescimo;
        String campoauxTotalProdutos = "" + TotaldoItem;
        String campoauxTotalIPI = "" + auxVl_ipi_total;
        String campoauxVl_TOTAL_NOTA = "" + auxVl_total_pedido_nota;
        String campoauxBaseCOFINS = "" + auxVl_base_cofins_total;
        String campoauxVL_COFINS = "" + auxVl_cofins_total;
        String campoauxBASE_PIS = "" + auxVl_base_pis_total;
        String campoauxVl_PIS = "" + auxVl_pis_total;

        //Passando valores para os campos
        edtBaseTotalICMS.setText(campoauxVl_base_icm_total);
        edtVLTotalICMS.setText(campoauxVl_icm_total);
        edtBaseTotalICMS_ST.setText(campoauxVl_base_icm_sub_total);
        edtVLTotalICMS_ST.setText(campoauxVl_icm_sub_total);
        edtTotalFrete.setText(campoauxVl_frete_total);
        edtVLTotalDesconto.setText(campoauxVl_Desconto);
        edtDespesasAcessorias.setText(campoauxVl_Acrescimo);
        edtTotalProdutos2.setText(campoauxTotalProdutos);
        edtTotalIPI.setText(campoauxTotalIPI);
        edtTotalNota.setText(campoauxVl_TOTAL_NOTA);
        edtBaseCOFINS.setText(campoauxBaseCOFINS);
        edtValorCOFINS.setText(campoauxVL_COFINS);
        edtBasePIS.setText(campoauxBASE_PIS);
        edtValorPIS.setText(campoauxVl_PIS);

        //Totais da Nota
        labelAcrescimoTotal.setText(campoauxVl_Acrescimo);
        labelDescontoTotal.setText(campoauxVl_Desconto);
        edtTotalProduto.setText(campoauxTotalProdutos);
        edtTotalVenda.setText(campoauxVl_TOTAL_NOTA);

        /*Zera Variaveis para nao recalcular os valores novamente
         totalNota = 0;
         for (int x = 0; x < TabelaProdutos.getRowCount(); x++) {
         double auxVl_ven_ite_pro = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 3))));
         double auxQuantidade = (Double.parseDouble(String.valueOf(TabelaProdutos.getModel().getValueAt(x, 2))));
         double total_item = auxQuantidade * auxVl_ven_ite_pro;
         totalNota = totalNota + total_item;
         //Aqui deve zerar as variaveis totalizadores e itens
         //Limpando as variaveis
         auxcd_filial = 0;
         auxcd_movimento = 0;
         auxcd_vendedor = 0;
         auxcd_pagto = 0;
         auxcd_pessoa = 0;
         auxVl_tot_cus_doc = 0;
         auxVl_tot_pro_doc = 0;
         auxVl_acrescimo = 0;
         auxVl_desconto = 0;
         auxfg_situacao = 0;
         auxFg_movimentou_estoque = 0;
         auxcd_usuario = 0;
         auxCfop = 0;
         auxVl_base_icm_total = 0;
         auxVl_icm_total = 0;
         auxVl_base_icm_sub_total = 0;
         auxVl_icm_sub_total = 0;
         auxVl_base_pis_total = 0;
         auxVl_pis_total = 0;
         auxVl_base_cofins_total = 0;
         auxVl_cofins_total = 0;
         auxVl_base_ipi_total = 0;
         auxVl_ipi_total = 0;
         auxVl_base_servico_total = 0;
         auxVl_servico_total = 0;
         auxVl_base_issqn_total = 0;
         auxVl_issqn_total = 0;
         auxCd_transportadora = 0;
         auxNr_PlacaVeiculo = "";
         auxQtdeVolume = 0;
         auxFgEmitente = 0;
         auxVl_tot_frete = 0;
         auxNm_especie = "";
         auxNr_nota_nfe = "";
         auxNr_chave_nfe = "";
         auxNr_prot_autorizacao = "";
         auxDs_inf_adicionais = "";
         auxVl_tot_seguro = 0;
         auxVl_total_pedido_nota = 0;
         auxDsMarca = "";
         auxNumeracao = 0;
         auxVl_peso_liquido = 0;
         auxVl_peso_bruto = 0;
         auxVl_Total_impostos = 0;
        
         }
         */
    }

    private void GeraTotaisNotaItem() {
        //mensagemErro("método GeraTotaisNotaItem!");
        double totalCampoNota = 0;
        String auxtexto = edtTotalVenda.getText();
        String auxQuantidade = edtQuantidadeItem.getText();
        String auxValorVenda = edtValorUnitario.getText();

        //Fazendo o Parse
        double Quantidade = 0;//Double.parseDouble(auxQuantidade);
        double ValorVenda = 0;//Double.parseDouble(auxValorVenda);
        double TotalItem = 0;//Quantidade * ValorVenda;

        if (auxtexto.equals("")) {
            Quantidade = Double.parseDouble(auxQuantidade);
            ValorVenda = Double.parseDouble(auxValorVenda);
            TotalItem = Quantidade * ValorVenda;
            totalCampoNota = TotalItem;
            edtTotalVenda.setText(String.valueOf(totalCampoNota));
        } else {
            Quantidade = Double.parseDouble(auxQuantidade);
            ValorVenda = Double.parseDouble(auxValorVenda);
            TotalItem = Quantidade * ValorVenda;
            totalCampoNota = TotalItem + Double.parseDouble(auxtexto);
            edtTotalVenda.setText(String.valueOf(totalCampoNota));
        }
        edtDescricao.setText("");
        edtValorUnitario.setText("");
        edtCodigoProduto.setText("");
        edtQuantidadeItem.setText("");
        edtCodigoProduto.grabFocus();
        edtCodigoProduto.requestFocus();
    }

    private void ListaVenda() throws ParseException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_filial = 1;
        int cd_movimento = Integer.parseInt(edtCodigoMovimento.getText());

        if (vendacompletodb.getVenda(cd_filial, cd_movimento)) {
            //Habilitação dos campos
            habilitaCampos(true);
            //Combobox Tipo de Nota
            cbTipo_Nota.setModel(tiponotadb.getComboTipoNota());
            //ComboBoxTipoNota();
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
                pstmt = conn.prepareStatement(SQL_BUSCA_VENDA_PELO_MOVIMENTO);
                pstmt.setInt(1, cd_filial);
                pstmt.setInt(2, cd_movimento);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    //Campos do Banco de Dados
                    // int auxcd_filial = rs.getInt("cd_filial");
                    int auxcd_movimento = rs.getInt("cd_movimento");
                    int auxcd_vende = rs.getInt("cd_vende");
                    int auxcd_pagto = rs.getInt("cd_pagto");
                    int auxcd_pessoa = rs.getInt("cd_pessoa");
                    Date auxdt_emi_doc = rs.getDate("dt_emi_doc");
                    Date auxdt_sai_doc = rs.getDate("dt_sai_doc");
                    //double auxvl_tot_cus_doc = rs.getDouble("vl_tot_cus_doc");
                    double auxvl_tot_pro_doc = rs.getDouble("vl_tot_pro_doc");
                    double auxvl_acrescimo = rs.getDouble("vl_acrescimo");
                    double auxvl_desconto = rs.getDouble("vl_desconto");
                    int auxcd_tipo_doc = rs.getInt("cd_tipo_doc");
                    int auxfg_situacao = rs.getInt("fg_situacao");
                    //int auxfg_movimentou_estoque = rs.getInt("fg_movimentou_estoque");
                    //int auxcd_usuario = rs.getInt("cd_usuario");
                    //Date auxdt_alt = rs.getDate("dt_alt");
                    Time auxhr_alt = rs.getTime("hr_alt");
                    //Date auxdt_cad = rs.getDate("dt_cad");
                    Time auxhr_cad = rs.getTime("hr_cad");
                    //int auxcd_cfop = rs.getInt("cd_cfop");
                    double auxvl_base_icm_total = rs.getDouble("vl_base_icm_total");
                    double auxvl_icm_total = rs.getDouble("vl_icm_total");
                    double auxvl_base_icm_sub_total = rs.getDouble("vl_base_icm_sub_total");
                    double auxvl_icm_sub_total = rs.getDouble("vl_icm_sub_total");
                    double auxvl_base_pis_total = rs.getDouble("vl_base_pis_total");
                    double auxvl_pis_total = rs.getDouble("vl_pis_total");
                    double auxvl_base_cofins_total = rs.getDouble("vl_base_cofins_total");
                    double auxvl_cofins_total = rs.getDouble("vl_cofins_total");
                    //double auxvl_base_ipi_total = rs.getDouble("vl_base_ipi_total");
                    double auxvl_ipi_total = rs.getDouble("vl_ipi_total");
                    //double auxvl_base_servico_total = rs.getDouble("vl_base_servico_total");
                    //double auxvl_servico_total = rs.getDouble("vl_servico_total");
                    //double auxvl_base_issqn_total = rs.getDouble("vl_base_issqn_total");
                    //double auxvl_issqn_total = rs.getDouble("vl_issqn_total");
                    int auxcd_transportadora = rs.getInt("cd_transportadora");
                    String auxnr_placa_veiculo = rs.getString("nr_placa_veiculo");
                    int auxqtd_volume = rs.getInt("qtd_volume");
                    int auxfg_emitente = rs.getInt("fg_emitente");
                    double auxvl_tot_frete = rs.getDouble("vl_tot_frete");
                    String auxnm_especie = rs.getString("nm_especie");
                    int auxnr_nota_nfe = rs.getInt("nr_nota_nfe");
                    String auxnr_chave_nfe = rs.getString("nr_chave_nfe");
                    //double auxnr_prot_autorizacao = rs.getDouble("nr_prot_autorizacao");
                    String auxds_inf_adicionais = rs.getString("ds_inf_adicionais");
                    //double auxvl_tot_seguro = rs.getDouble("vl_tot_seguro");
                    double auxvl_tot_pedido_nota = rs.getDouble("vl_tot_pedido_nota");
                    String auxds_marca = rs.getString("ds_marca");
                    //double auxnr_numeracao = rs.getDouble("nr_numeracao");
                    double auxvl_peso_liquido = rs.getDouble("vl_peso_liquido");
                    double auxvl_peso_bruto = rs.getDouble("vl_peso_bruto");
                    //double auxvl_tot_impostos = rs.getDouble("vl_tot_impostos");

                    //Conversao das variaveis para String
                    aux_codigo_tipo_nota = auxcd_tipo_doc;
                    String auxcd_pessoa_conversao = "" + auxcd_pessoa;
                    String auxcd_vendedor = "" + auxcd_vende;
                    String auxcd_pagamento = "" + auxcd_pagto;

                    //Dados da Aba Pagamento
                    String auxacrescimo = "" + auxvl_acrescimo;
                    String auxdesconto = "" + auxvl_desconto;
                    String auxInfoAdicional = auxds_inf_adicionais;

                    //Dados do Emitente
                    int auxfgemitente = auxfg_emitente;
                    switch (auxfgemitente) {
                        case 0:
                            RadioEmitente.setSelected(true);
                            break;
                        case 1:
                            RadioDestinatario.setSelected(true);
                            break;
                        default:
                            RadioOutros.setSelected(true);
                            break;
                    }
                    //Dados da Venda da Aba Totalizadores
                    switch (auxfg_situacao) {
                        case 0:
                            RadioOrcamento.setSelected(true);
                            break;
                        case 1:
                            RadioPedido.setSelected(true);
                            break;
                        case 2:
                            RadioVenda.setSelected(true);
                            RadioOrcamento.setEnabled(false);
                            RadioVenda.setEnabled(false);
                            RadioPedido.setEnabled(false);
                            break;
                        default:
                            break;
                    }

                    String auxacrescimototal = auxacrescimo;
                    String auxdescontototal = "" + auxvl_desconto;
                    String auxtotalprodutos = "" + auxvl_tot_pro_doc;
                    String auxdescontoitenstotal = "" + auxvl_desconto;
                    String auxtotalnota = "" + auxvl_tot_pedido_nota;
                    String cd_movimentos = "" + auxcd_movimento;

                    //Dados da Transportadora
                    String auxtransportadora = "";
                    if (auxcd_transportadora > 0) {
                        auxtransportadora = "" + auxcd_transportadora;
                    }
                    String auxplaca = "" + auxnr_placa_veiculo;
                    String auxespecie = "" + auxnm_especie;
                    String auxquantidade = "" + auxqtd_volume;
                    String auxfrete = "" + auxvl_tot_frete;
                    String auxvolume = "" + auxqtd_volume;
                    String auxmarca = "" + auxds_marca;

                    String auxvlpesobruto = "" + auxvl_peso_bruto;
                    String auxvlpesoliquido = "" + auxvl_peso_liquido;

                    String auxTipoNota = "" + aux_codigo_tipo_nota;
                    edtTipoNota.setText(auxTipoNota);
                    edtCodigoCliente.setText(auxcd_pessoa_conversao);
                    edtCodigoVendedor.setText(auxcd_vendedor);
                    edtCodigoCondicaoPagamento.setText(auxcd_pagamento);

                    String formataDataEmissao = formatadata.format(auxdt_emi_doc);
                    String formataDataSaida = formatadata.format(auxdt_sai_doc);

                    //String formataDataCadastro = formatadata.format(auxdt_emi_doc);
                    String formataHoraCadastro = formatahora.format(auxhr_cad);
                    //String formataDataAlteracao = formatadata.format(auxdt_emi_doc);
                    String formataHoraAlteracao = formatahora.format(auxhr_alt);

                    edtDataEmissao.setText(formataDataEmissao);
                    edtDataSaida.setText(formataDataSaida);
                    edtHoraEmissao.setText(formataHoraCadastro);
                    edtHoraSaida.setText(formataHoraAlteracao);

                    //Aba Pagamento
                    edtAcrescimoTotal.setText(auxacrescimo);
                    edtDescontoTotal.setText(auxdesconto);
                    edtInfoAdicionais.setText(auxInfoAdicional);

                    //Aba Transportadora
                    edtTransportadora.setText(auxtransportadora);
                    edtPlacaVeiculo.setText(auxplaca);
                    edtEspecie.setText(auxespecie);
                    edtQuantidadeNota.setText(auxquantidade);
                    edtFrete.setText(auxfrete);
                    edtVolume.setText(auxvolume);
                    edtMarca.setText(auxmarca);
                    edtPesoBruto.setText(auxvlpesobruto);
                    edtPesoLiquido.setText(auxvlpesoliquido);

                    //Aba Totalizadores
                    LabelMovimento.setText(cd_movimentos);
                    labelAcrescimoTotal.setText(auxacrescimototal);
                    labelDescontoTotal.setText(auxdescontototal);
                    edtTotalProduto.setText(auxtotalprodutos);
                    edtDescontoItemTotal.setText(auxdescontoitenstotal);
                    edtTotalVenda.setText(auxtotalnota);

                    //Impostos
                    String campoauxVl_base_icm_total = "" + auxvl_base_icm_total;
                    String campoauxVl_icm_total = "" + auxvl_icm_total;
                    String campoauxVl_base_icm_sub_total = "" + auxvl_base_icm_sub_total;
                    String campoauxVl_icm_sub_total = "" + auxvl_icm_sub_total;
                    String campoauxVl_frete_total = "" + auxfrete;
                    String campoauxVl_Desconto = "" + auxvl_desconto;
                    String campoauxVl_Acrescimo = "" + auxvl_acrescimo;
                    String campoauxTotalProdutos = "" + auxvl_tot_pro_doc;
                    String campoauxTotalIPI = "" + auxvl_ipi_total;
                    String campoauxVl_TOTAL_NOTA = "" + auxvl_tot_pedido_nota;
                    String campoauxBaseCOFINS = "" + auxvl_base_cofins_total;
                    String campoauxVL_COFINS = "" + auxvl_cofins_total;
                    String campoauxBASE_PIS = "" + auxvl_base_pis_total;
                    String campoauxVl_PIS = "" + auxvl_pis_total;

                    //Passando valores para os campos
                    edtBaseTotalICMS.setText(campoauxVl_base_icm_total);
                    edtVLTotalICMS.setText(campoauxVl_icm_total);
                    edtBaseTotalICMS_ST.setText(campoauxVl_base_icm_sub_total);
                    edtVLTotalICMS_ST.setText(campoauxVl_icm_sub_total);
                    edtTotalFrete.setText(campoauxVl_frete_total);
                    edtVLTotalDesconto.setText(campoauxVl_Desconto);
                    edtDespesasAcessorias.setText(campoauxVl_Acrescimo);
                    edtTotalProdutos2.setText(campoauxTotalProdutos);
                    edtTotalIPI.setText(campoauxTotalIPI);
                    edtTotalNota.setText(campoauxVl_TOTAL_NOTA);
                    edtBaseCOFINS.setText(campoauxBaseCOFINS);
                    edtValorCOFINS.setText(campoauxVL_COFINS);
                    edtBasePIS.setText(campoauxBASE_PIS);
                    edtValorPIS.setText(campoauxVl_PIS);

                    //Totais da Nota
                    labelAcrescimoTotal.setText(campoauxVl_Acrescimo);
                    labelDescontoTotal.setText(campoauxVl_Desconto);
                    edtTotalProduto.setText(campoauxTotalProdutos);
                    edtTotalVenda.setText(campoauxVl_TOTAL_NOTA);

                    ValidaCampoCodigoTipoNotaNaoNulo();
                    ValidaCampoCodigoClienteNaoNulo();
                    ValidaCampoCodigoVendedorNaoNulo();
                    ValidaCampoCodigoCondicaoPagamentoNaoNulo();
                    ValidaCampoCodigoTransportadoraNaoNulo();
                    //Aqui comeca a carregar os itens
                    ListaItens();
                    //Aqui carrega as parcelas da venda
                    ListaParcelas();
                }
            } catch (SQLException erro) {
                mensagemErro("Erro de conexão! " + erro);
            }
        } else {
            mensagemErro("Venda não cadastrada!");
            edtCodigoMovimento.requestFocus();
            habilitaCampos(false);
        }
    }

    private ArrayList SQLCarregaNomeItens() {
        String auxtexto = edtCodigoMovimento.getText();
        int codigomovimento = Integer.parseInt(auxtexto);
        String SQLConsulta_itens_dav
                = "SELECT                                               "
                + "    PRODUTO_SIMPLES.*                                "
                + "FROM                                                 "
                + "    ITENS_ORC                                        "
                + "    LEFT OUTER JOIN PRODUTO_SIMPLES                  "
                + "    ON PRODUTO_SIMPLES.CD_REF=ITENS_ORC.CD_REFER_PRO "
                + "WHERE                                                "
                + "    ITENS_ORC.CD_MOVIMENTO=?                         ";

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

                ModelProduto produto = new ModelProduto(
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
            mensagemErro("Erro no sql,  SQLConsultagetTodos_Completo_NomeProduto(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaProduto;
    }

    private void ListaItens() {
        // mensagemErro("Falta Programar!!");
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nome");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Preco");
        //habilitaCampos(true);
        int cd_filial = 1;
        int aux_sequencia = 1;//Pega a sequencia
        int aux_contador = 1;   //Pega a sequencia do item
        int aux_movimento = Integer.parseInt(edtCodigoMovimento.getText());
        ArrayList<ModelProduto> produtosimples = SQLCarregaNomeItens();

        //aqui verifica se existe item desta venda
        if (itemdb.getItem(cd_filial, aux_movimento, aux_sequencia)) {
            mensagemErro("Referencia a partir do tamanho: \n 9850116000 \n  não lista e fica zero corrigir!!!");
            aux_sequencia = 0;
            for (ModelProduto auxprodutosimples : produtosimples) {
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
                        pstmt = conn.prepareStatement(SQL_BUSCA_QUANTIDADE_E_PRECO);
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
                        mensagemErro("Erro no sql, Nome Produto Itens:\n" + erro.getMessage());
                    } finally {
                        Conexao.closeAll(conn);
                    }
                }
                aux_sequencia++;//Incrementa a sequencia
                aux_contador++; //Incrementa a sequencia do banco de dados                 
                //Habilita a verificacao da condicao de pagamento
                VerificaCondicaopagamento = true;
            }

        } else {
            mensagemErro("Não existe itens nesta venda!");
            edtCodigoProduto.requestFocus();
        }
    }

    private void ListaParcelas() throws ParseException {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Dia");
        modelo.addColumn("Data");
        //String Data = "01/01/2015";//Programar a conta da data         
        java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        java.util.Date javautilDateDataAlteracao = null;
        //Altera para java.util.date a String do campo de texto
        String auxDataSaida = edtDataSaida.getText();
        javautilDateDataAlteracao = (java.util.Date) formatter.parse(auxDataSaida);
        //Programacao 
        //Passe ela para um java.util.Calendar  
        Calendar c = Calendar.getInstance();
        c.setTime(javautilDateDataAlteracao);

        //Some um mes  
        //c.add(Calendar.MONTH, 1);  
        //Se vc precisa somar 30 e não um mes  
        //c.add(Calendar.DATE, 30);  
        //Retorne sua data para o objeto java.util.Date  
        Date mesSeguinte = c.getTime();

        //Fim Programacao da Data da Parcela
        modelo.addColumn("Valor");
        modelo.addColumn("Tipo Cobranca");
        int aux_condicao_pagamento = Integer.parseInt(edtCodigoCondicaoPagamento.getText());
        ArrayList<ModelParcelaDados> parceladados = parceladb.listaParcelas(aux_condicao_pagamento);
        //Pega Total de Parcelas
        double total_parcela = 0;
        for (ModelParcelaDados auxparceladados : parceladados) {
            total_parcela++;
        }
        //Gera Valor da Parcela
        double valor_total_venda = Double.parseDouble(edtTotalNota.getText());
        double valor_parcela = 0;
        valor_parcela = valor_total_venda / total_parcela;
        int CasasAposVirgula = 2;
        //Arredonda Valor pra 2 casas apos a virgula
        BigDecimal bd = new BigDecimal(valor_parcela).setScale(CasasAposVirgula, RoundingMode.HALF_EVEN);
        valor_parcela = (bd.doubleValue());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = "";
        for (ModelParcelaDados auxparceladados : parceladados) {
            //Formata a data antes de mostrar na tela
            dataFormatada = df.format(mesSeguinte);
            modelo.addRow(new Object[]{
                auxparceladados.getNr_dias_parcelas(),
                dataFormatada,
                valor_parcela,
                auxparceladados.getDs_cobranca()
            });
            TabelaParcelas.setModel(modelo);
            c.setTime(mesSeguinte);
            c.add(Calendar.MONTH, 1);
            mesSeguinte = c.getTime();
        }
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
                LabelMovimento.setText(a);
                cbTipo_Nota.grabFocus();
            }
        } catch (SQLException erro) {
            mensagemErro("Erro no ValidaCodigoGenerator()! \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
    }

    private void ComboBoxTipoNota() {
        if (VerificaHabilitacaoCampos) {
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
                String aux_tipo_nota = "" + aux_codigo_tipo_nota;
                edtTipoNota.setText(aux_tipo_nota);
                //JOptionPane.showMessageDialog(null, "Codigo Tipo de Nota"+aux_codigo_tipo_nota);

            } catch (SQLException ex) {
                mensagemErro("Tipo de Nota não encontrado!Erro na funcao ComboBoxTipoNota()!:" + ex.getMessage());
            }
        }
    }

    private void ComboBoxVendedor() {
        if (VerificaHabilitacaoCampos) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "select * from pessoa where pessoa.nm_pessoa= '" + cbVendedor.getSelectedItem() + "'";
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
                mensagemErro("Vendedor não encontrado!Erro na funcao ComboBoxVendedor()!:" + ex.getMessage());
            }
        }
    }

    private void ComboBoxCliente() {
        if (VerificaHabilitacaoCampos) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            String sql = "select * from pessoa where pessoa.nm_pessoa= '" + cbCliente.getSelectedItem() + "'";
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
                mensagemErro("Cliente não encontrado!Erro na funcao ComboBoxCliente()!:" + ex.getMessage());
            }
        }
    }

    private void ComboBoxCondicaoPagamento() {
        if (VerificaHabilitacaoCampos) {
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
                mensagemErro("Condicao de Pagamento não encontrada!Erro na funcao ComboBoxCondicaoPagamento()!:" + ex.getMessage());
            }
        }
    }

    private DefaultComboBoxModel ValidaCampoCodigoTipoNotaNaoNulo() {
        String auxTipoNota = edtTipoNota.getText();
        int cd_filial = 1;
        int cd_tipo_nota = Integer.parseInt(auxTipoNota);

        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (tiponotadb.getTipoNota(cd_tipo_nota)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(SQL_BUSCA_TIPO_NOTA);
                pstmt.setInt(1, cd_filial);
                pstmt.setInt(2, cd_tipo_nota);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxNome = rs.getString("ds_tipo_nota");
                    cbTipo_Nota.setSelectedItem(auxNome);
                }
            } catch (SQLException erro) {
                mensagemErro("Erro no sql, ValidaCampoCodigoTipoNotaNaoNulo(): \n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
        } else {
            mensagemErro("Tipo de Nota nao cadastrada!");
            edtCodigoCliente.requestFocus();
        }
        return modelo;
    }

    private DefaultComboBoxModel ValidaCampoCodigoClienteNaoNulo() {
        String auxTexto = edtCodigoCliente.getText();
        int cd_pessoa = Integer.parseInt(auxTexto);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (pessoadb.getPessoa(cd_pessoa)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(SQL_BUSCA_PESSOA);
                pstmt.setInt(1, cd_pessoa);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxNome = rs.getString("nm_pessoa");
                    cbCliente.setSelectedItem(auxNome);
                    edtCodigoVendedor.grabFocus();
                }
            } catch (SQLException erro) {
                mensagemErro("Erro no sql, getComboPessoa(): \n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
        } else {
            mensagemErro("Pessoa nao cadastrada");
            edtCodigoCliente.requestFocus();
        }
        return modelo;
    }

    private DefaultComboBoxModel ValidaCampoCodigoVendedorNaoNulo() {
        String auxTexto = edtCodigoVendedor.getText();
        int cd_pessoa = Integer.parseInt(auxTexto);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (pessoadb.getPessoa(cd_pessoa)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(SQL_BUSCA_PESSOA);
                pstmt.setInt(1, cd_pessoa);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxNome = rs.getString("nm_pessoa");
                    cbVendedor.setSelectedItem(auxNome);
                    edtCodigoCondicaoPagamento.grabFocus();
                }
            } catch (SQLException erro) {
                mensagemErro("Erro no sql, ValidaCampoCodigoVendedorNãoNulo() \n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
        } else {
            mensagemErro("Vendedor nao cadastrado!");
            edtCodigoVendedor.requestFocus();
        }
        return modelo;
    }

    private DefaultComboBoxModel ValidaCampoCodigoCondicaoPagamentoNaoNulo() {
        String auxTexto = edtCodigoCondicaoPagamento.getText();
        int cd_cond = Integer.parseInt(auxTexto);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (condicaopagamentodb.getCondicaoPagamento(cd_cond)) {
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(SQL_BUSCA_CONDICAO_PAGAMENTO);
                pstmt.setInt(1, cd_cond);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxNome = rs.getString("DS_COND");
                    cbCondicaoPagamento.setSelectedItem(auxNome);
                    edtCodigoProduto.grabFocus();
                }
            } catch (SQLException erro) {
                mensagemErro("Erro no sql, ValidaCampoCodigoCondicaoPagamentoNãoNulo():\n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
        } else {
            mensagemErro("Condicao de Pagamento nao cadastrada!");
            edtCodigoCondicaoPagamento.requestFocus();
        }
        return modelo;
    }

    private boolean ValidaCampoCodigoProdutoNaoNulo() {
        String auxTexto = edtCodigoProduto.getText();
        int cd_ref = Integer.parseInt(auxTexto);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (produtosimplesdb.getProdutoCD_REF(cd_ref)) {
            if (ValidaCodigoProdutoImpostos()) {
                try {
                    conn = Conexao.getConexao();
                    pstmt = conn.prepareStatement(SQL_BUSCA_PRODUTO);
                    pstmt.setInt(1, cd_ref);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String auxnome = rs.getString("ds_prod");
                        double vl_preco = rs.getDouble("vl_venda");
                        edtDescricao.setText(auxnome);
                        //Sugere a quantidade 1
                        edtQuantidadeItem.setText("1");
                        edtQuantidadeItem.grabFocus();

                        if (vl_preco > 0) {
                            //Conversao de variavel
                            String aux = "";
                            aux = "" + vl_preco;

                            //JOptionPane.showMessageDialog(null, "Item: "+aux);
                            //Muda o preço do item para o preço da tabela                   
                            edtValorUnitario.setText(aux);
                        } else {
                            mensagemErro("Item com preço zerado!");
                            edtCodigoProduto.grabFocus();
                        }
                    }
                } catch (SQLException erro) {
                    mensagemErro("Erro no sql, ValidaCampoCodigoProdutoNaoNulo():\n" + erro.getMessage());
                } finally {
                    Conexao.closeAll(conn);
                }
            }
        } else {
            mensagemErro("Produto nao cadastrado!");
            edtCodigoProduto.requestFocus();
        }
        return true;
    }

    private boolean ValidaCodigoProdutoImpostos() {
        boolean validaImposto = false;
        String Codigo = edtCodigoProduto.getText();
        String Pessoa = edtCodigoCliente.getText();
        int auxCd_refer_pro = Integer.parseInt(Codigo);
        int auxcd_pessoa = Integer.parseInt(Pessoa);

        int cd_filialparametro = 1;
        int CD_GRUPO_FISCALparametro = produtodb.retornaCodigoGrupoFiscal(auxCd_refer_pro);
        String CD_ESTADO_DESTINO = pessoadb.retornaCodigoEstado(auxcd_pessoa);
        String CD_ESTADO_ORIGEM = filialdb.retornaCodigoEstadoFilial(cd_filialparametro);
        int CD_TIPO_NOTAparametro = aux_codigo_tipo_nota;
        int TIPOCONSUMOparametro = pessoadb.retornaTipoConsumo(auxcd_pessoa);
        int referenciaparametro = auxCd_refer_pro;

        ArrayList<ModelTributacao> tributacoes = itemdb.listaDadosTributacaoVenda(
                cd_filialparametro,
                CD_GRUPO_FISCALparametro,
                CD_ESTADO_DESTINO,
                CD_ESTADO_ORIGEM,
                CD_TIPO_NOTAparametro,
                TIPOCONSUMOparametro,
                referenciaparametro
        );
        double auxVl_cus_ite_pro = 0;
        double auxvl_real_ite_pro = 0;
        double tx_icms_interno_destino = 0;
        double tx_icms_interestadual_destino = 0;
        double tx_icms_interno_origem = 0;
        double tx_icms_interestadual_origem = 0;
        double vl_mva = 0;

        double tx_iss = 0;
        int cst_ipi = 0;
        double tx_ipi = 0;
        int cst_pis = 0;
        double tx_pis = 0;

        int cst_cofins = 0;
        double tx_cofins = 0;
        String ab_unidade = "";

        int cfop = 0;
        double tx_icms = 0;
        double tx_icms_substituicao = 0;
        double Aliquota_do_ICMS_Operacao_Propria_2 = 0;

        int cd_grupo_fiscal = 0;
        int cd_cst = 0;

        for (ModelTributacao auxItem : tributacoes) {
            auxVl_cus_ite_pro = auxItem.getVl_cus_ite_pro();
            auxvl_real_ite_pro = auxItem.getVl_ven_ite_pro();
            tx_icms_interno_destino = auxItem.getTx_icms_interno_destino();
            tx_icms_interestadual_destino = auxItem.getTx_icms_interestadual_destino();
            CD_ESTADO_DESTINO = auxItem.getCd_estado_destino();
            tx_icms_interno_origem = auxItem.getTx_icms_interno_origem();
            tx_icms_interestadual_origem = auxItem.getTx_icms_interestadual_origem();
            CD_ESTADO_ORIGEM = auxItem.getCd_estado_origem();

            vl_mva = auxItem.getVl_mva();

            tx_iss = auxItem.getTx_iss();
            cst_ipi = auxItem.getCst_ipi();
            tx_ipi = auxItem.getTx_ipi();
            cst_pis = auxItem.getCst_pis();
            tx_pis = auxItem.getTx_pis();

            cst_cofins = auxItem.getCst_cofins();
            tx_cofins = auxItem.getTx_cofins();
            ab_unidade = auxItem.getAb_unidade();

            if (CD_ESTADO_DESTINO.equals(CD_ESTADO_ORIGEM)) {
                cfop = auxItem.getCfop_estadual();
                tx_icms = auxItem.getTx_icms_interno_origem();
                tx_icms_substituicao = auxItem.getTx_icms_interno_origem();
                Aliquota_do_ICMS_Operacao_Propria_2 = auxItem.getTx_icms_interno_origem();
            } else if (!CD_ESTADO_DESTINO.equals(CD_ESTADO_ORIGEM)) {
                cfop = auxItem.getCfop_inter_estadual();
                tx_icms = auxItem.getTx_icms_interestadual_origem();
                tx_icms_substituicao = auxItem.getTx_icms_interno_destino();
                Aliquota_do_ICMS_Operacao_Propria_2 = auxItem.getTx_icms_interestadual_origem();
            }
            cd_grupo_fiscal = auxItem.getCd_grupo_fiscal();
            cd_cst = auxItem.getCd_cst();
        }
        //Validacao
        if (cd_filialparametro <= 0) {
            mensagemErro("Filial incorreta ou inexistente!" + cd_filialparametro);
        } else if (CD_GRUPO_FISCALparametro <= 0) {
            mensagemErro("Grupo Fiscal incorreto ou inexistente!" + CD_GRUPO_FISCALparametro);
        } else if (CD_ESTADO_DESTINO.equals("")) {
            mensagemErro("Estado do Destinatario incorreto ou inexistente!" + CD_ESTADO_DESTINO);
        } else if (CD_ESTADO_ORIGEM.equals("")) {
            mensagemErro("Estado da Filial incorreto ou inexistente!" + CD_ESTADO_ORIGEM);
        } else if (CD_TIPO_NOTAparametro <= 0) {
            mensagemErro("Tipo de Nota incorreto ou inexistente!" + CD_TIPO_NOTAparametro);
        } else if (TIPOCONSUMOparametro < 0) {
            mensagemErro("Tipo de Consumo do cliente incorreto ou inexistente!" + TIPOCONSUMOparametro);
        } else if (referenciaparametro < 0) {
            mensagemErro("Referencia incorreta ou inexistente!" + referenciaparametro);
        } else if (auxVl_cus_ite_pro < 0) {
            mensagemErro("Custo incorreto ou inexistente!" + auxVl_cus_ite_pro);
        } else if (auxvl_real_ite_pro < 0) {
            mensagemErro("Valor de Venda incorreto ou inexistente!" + referenciaparametro);
        } else if (tx_icms_interno_destino < 0) {
            mensagemErro("tx_icms_interno_destino incorreta ou inexistente!" + tx_icms_interno_destino);
        } else if (tx_icms_interestadual_destino < 0) {
            mensagemErro("tx_icms_interestadual_destino incorreta ou inexistente!" + tx_icms_interestadual_destino);
        } else if (tx_icms_interno_origem < 0) {
            mensagemErro("tx_icms_interno_origem incorreto ou inexistente!" + tx_icms_interno_origem);
        } else if (tx_icms_interestadual_origem < 0) {
            mensagemErro("tx_icms_interestadual_origem incorreto ou inexistente!" + tx_icms_interestadual_origem);
        } else if (tx_icms_interestadual_origem < 0) {
            mensagemErro("tx_icms_interestadual_origem incorreto ou inexistente!" + tx_icms_interestadual_origem);
        } else if (vl_mva < 0) {
            mensagemErro("vl_mva incorreto ou inexistente!" + vl_mva);
        } else if (tx_iss < 0) {
            mensagemErro("tx_iss incorreto ou inexistente!" + tx_iss);
        } else if (cst_ipi < 0) {
            mensagemErro("cst_ipi incorreto ou inexistente!" + cst_ipi);
        } else if (tx_ipi < 0) {
            mensagemErro("tx_ipi incorreto ou inexistente!" + tx_ipi);
        } else if (cst_pis < 0) {
            mensagemErro("cst_pis incorreto ou inexistente!" + cst_pis);
        } else if (tx_pis < 0) {
            mensagemErro("tx_pis incorreto ou inexistente!" + tx_pis);
        } else if (cst_cofins < 0) {
            mensagemErro("cst_cofins incorreto ou inexistente!" + cst_cofins);
        } else if (tx_cofins < 0) {
            mensagemErro("tx_cofins incorreto ou inexistente!" + tx_cofins);
        } else if (cfop < 0) {
            mensagemErro("cfop incorreto ou inexistente!" + cfop);
        } else if (tx_icms < 0) {
            mensagemErro("tx_icms incorreto ou inexistente!" + tx_icms);
        } else if (tx_icms_substituicao < 0) {
            mensagemErro("tx_icms_substituicao incorreto ou inexistente!" + tx_icms_substituicao);
        } else if (cd_grupo_fiscal < 0) {
            mensagemErro("cd_grupo_fiscal incorreto ou inexistente!" + cd_grupo_fiscal);
        } else if (cd_cst < 0) {
            mensagemErro("cd_cst incorreto ou inexistente!" + cd_cst);
        } else if (ab_unidade.equals("")) {
            mensagemErro("ab_unidade incorreto ou inexistente!" + ab_unidade);
        } else {
            validaImposto = true;
        }
        return validaImposto;
    }

    private boolean ValidaCampoCodigoTransportadoraNaoNulo() {
        String auxTexto = edtTransportadora.getText();
        if (!auxTexto.equals("")) {
            int cd_transportadora = Integer.parseInt(auxTexto);
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            if (pessoadb.getPessoaTransportadora(cd_transportadora)) {
                try {
                    conn = Conexao.getConexao();
                    pstmt = conn.prepareStatement(SQL_BUSCA_TRANSPORTADORA);
                    pstmt.setInt(1, cd_transportadora);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String auxnome = rs.getString("nm_pessoa");
                        LabelNomeTransportadora.setText(auxnome);
                        edtTransportadora.setEnabled(false);
                    }
                } catch (SQLException erro) {
                    mensagemErro("Erro no sql, ValidaCampoCodigoTransportadoraNaoNulo():\n" + erro.getMessage());
                } finally {
                    Conexao.closeAll(conn);
                }
            } else {
                mensagemErro("Pessoa nao cadastrada!");
                edtCodigoProduto.requestFocus();
            }
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

        jPanel15 = new javax.swing.JPanel();
        PainelPagamento = new javax.swing.JTabbedPane();
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
        edtDataSaida = new javax.swing.JFormattedTextField();
        edtHoraSaida = new javax.swing.JFormattedTextField();
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
        edtDataEmissao = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        edtHoraEmissao = new javax.swing.JFormattedTextField();
        edtTipoNota = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        edtCodigoProduto = new javax.swing.JTextField();
        edtDescricao = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        edtQuantidadeItem = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        edtValorUnitario = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnConsultaProduto = new javax.swing.JButton();
        PainelAbaPagamento = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LabelNomeTransportadora = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        edtPlacaVeiculo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        edtEspecie = new javax.swing.JTextField();
        edtFrete = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        edtMarca = new javax.swing.JTextField();
        edtPesoLiquido = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        edtPesoBruto = new javax.swing.JTextField();
        edtVolume = new javax.swing.JTextField();
        edtQuantidadeNota = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        btnConsultaTransportadora = new javax.swing.JButton();
        edtTransportadora = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        RadioOutros = new javax.swing.JRadioButton();
        RadioDestinatario = new javax.swing.JRadioButton();
        RadioEmitente = new javax.swing.JRadioButton();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelaParcelas = new javax.swing.JTable();
        edtAcrescimoTotal = new javax.swing.JTextField();
        edtDescontoTotal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        edtInfoAdicionais = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        edtVLTotalICMS_ST = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        edtBaseTotalICMS = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        edtBaseTotalICMS_ST = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        edtVLTotalICMS = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        edtTotalFrete = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        edtValorSeguro = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        edtVLTotalDesconto = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        edtValorPIS = new javax.swing.JTextField();
        jPanel30 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        edtDespesasAcessorias = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        edtTotalProdutos2 = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        edtTotalNota = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        edtTotalIPI = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        edtValorCOFINS = new javax.swing.JTextField();
        jPanel36 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        edtBasePIS = new javax.swing.JTextField();
        jPanel37 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        edtBaseCOFINS = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        RadioOrcamento = new javax.swing.JRadioButton();
        RadioPedido = new javax.swing.JRadioButton();
        RadioVenda = new javax.swing.JRadioButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnGravar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        labelDescontoTotal = new javax.swing.JTextField();
        labelAcrescimoTotal = new javax.swing.JTextField();
        edtTotalVenda = new javax.swing.JTextField();
        edtTotalProduto = new javax.swing.JTextField();
        edtDescontoItemTotal = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        LabelMovimento = new javax.swing.JTextField();

        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DTS_SYSTEMS BY GELVAZIO CAMARGO");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PainelPagamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TabelaProdutos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TabelaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descrição", "Quantidade", "Valor"
            }
        ));
        jScrollPane1.setViewportView(TabelaProdutos);
        if (TabelaProdutos.getColumnModel().getColumnCount() > 0) {
            TabelaProdutos.getColumnModel().getColumn(3).setMinWidth(100);
            TabelaProdutos.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 660, 190));

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("Movimento");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 1, 60, -1));

        jLabel5.setText("Tipo Nota");
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 1, 60, -1));

        jLabel6.setText("Data Saída:");
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(463, 1, 102, -1));

        jLabel7.setText("Hora Saída:");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 1, 90, -1));

        edtCodigoMovimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoMovimentoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtCodigoMovimentoKeyTyped(evt);
            }
        });
        jPanel6.add(edtCodigoMovimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 21, 101, -1));

        cbTipo_Nota.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTipo_Nota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipo_NotaActionPerformed(evt);
            }
        });
        jPanel6.add(cbTipo_Nota, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 21, 210, -1));

        edtDataSaida.setEditable(false);
        edtDataSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jPanel6.add(edtDataSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(463, 21, 102, -1));

        edtHoraSaida.setEditable(false);
        edtHoraSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        jPanel6.add(edtHoraSaida, new org.netbeans.lib.awtextra.AbsoluteConstraints(571, 21, 79, -1));

        edtCliente.setText("Cliente");
        jPanel6.add(edtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 47, -1, -1));

        edtCondicao_Pagamento.setText("Condição Pagamento");
        jPanel6.add(edtCondicao_Pagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 102, 131, -1));

        cbCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbClienteActionPerformed(evt);
            }
        });
        jPanel6.add(cbCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 71, 210, -1));

        cbVendedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVendedorActionPerformed(evt);
            }
        });
        jPanel6.add(cbVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 122, 210, -1));

        cbCondicaoPagamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCondicaoPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCondicaoPagamentoActionPerformed(evt);
            }
        });
        jPanel6.add(cbCondicaoPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(449, 122, 201, -1));

        btnConsultaCliente.setText("...");
        btnConsultaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaClienteActionPerformed(evt);
            }
        });
        jPanel6.add(btnConsultaCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 70, 29, 22));

        btnConsultaCondicaoPagamento.setText("...");
        btnConsultaCondicaoPagamento.setSelected(true);
        btnConsultaCondicaoPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaCondicaoPagamentoActionPerformed(evt);
            }
        });
        jPanel6.add(btnConsultaCondicaoPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 122, 32, 22));

        edtCodigoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoClienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtCodigoClienteKeyTyped(evt);
            }
        });
        jPanel6.add(edtCodigoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 68, 65, -1));

        btnConsultaVendedor.setText("...");
        btnConsultaVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaVendedorActionPerformed(evt);
            }
        });
        jPanel6.add(btnConsultaVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 122, 30, 22));

        edtCodigoVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoVendedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtCodigoVendedorKeyTyped(evt);
            }
        });
        jPanel6.add(edtCodigoVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 122, 65, -1));

        edtVendedor.setText("Vendedor");
        jPanel6.add(edtVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 102, -1, -1));

        edtCodigoCondicaoPagamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoCondicaoPagamentoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtCodigoCondicaoPagamentoKeyTyped(evt);
            }
        });
        jPanel6.add(edtCodigoCondicaoPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(341, 123, 60, -1));

        edtDataEmissao.setEditable(false);
        edtDataEmissao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jPanel6.add(edtDataEmissao, new org.netbeans.lib.awtextra.AbsoluteConstraints(463, 67, 102, -1));

        jLabel20.setText("Data Emissão:");
        jPanel6.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(463, 47, 102, -1));

        jLabel21.setText("Hora Emissão:");
        jPanel6.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 47, 90, -1));

        edtHoraEmissao.setEditable(false);
        edtHoraEmissao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        jPanel6.add(edtHoraEmissao, new org.netbeans.lib.awtextra.AbsoluteConstraints(571, 67, 79, -1));

        edtTipoNota.setEditable(false);
        edtTipoNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtTipoNotaKeyPressed(evt);
            }
        });
        jPanel6.add(edtTipoNota, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 21, 60, -1));

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 660, 160));

        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setText("Produto");
        jPanel13.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 49, -1));

        edtCodigoProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoProdutoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtCodigoProdutoKeyTyped(evt);
            }
        });
        jPanel13.add(edtCodigoProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 70, -1));

        edtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtDescricaoKeyPressed(evt);
            }
        });
        jPanel13.add(edtDescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 309, -1));

        jLabel11.setText("Descrição:");
        jPanel13.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, -1));

        edtQuantidadeItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtQuantidadeItemKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtQuantidadeItemKeyTyped(evt);
            }
        });
        jPanel13.add(edtQuantidadeItem, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 80, -1));

        jLabel10.setText("Quantidade");
        jPanel13.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, -1, -1));

        edtValorUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtValorUnitarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtValorUnitarioKeyTyped(evt);
            }
        });
        jPanel13.add(edtValorUnitario, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 111, -1));

        jLabel9.setText("Valor Unitario");
        jPanel13.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, -1, -1));

        btnConsultaProduto.setText("...");
        btnConsultaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaProdutoActionPerformed(evt);
            }
        });
        jPanel13.add(btnConsultaProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 30, -1));

        jPanel3.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 660, 70));

        PainelPagamento.addTab("Nota", jPanel3);

        PainelAbaPagamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PainelAbaPagamento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Transportadora:");
        jPanel8.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 100, -1));

        LabelNomeTransportadora.setText("Nome Transportadora...");
        jPanel8.add(LabelNomeTransportadora, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 260, 20));

        jLabel23.setText("Placa:");
        jPanel8.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 40, -1));

        edtPlacaVeiculo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtPlacaVeiculoKeyPressed(evt);
            }
        });
        jPanel8.add(edtPlacaVeiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 120, -1));

        jLabel3.setText("Espécie:");
        jPanel8.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 50, -1));

        edtEspecie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtEspecieKeyPressed(evt);
            }
        });
        jPanel8.add(edtEspecie, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 120, -1));

        edtFrete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtFreteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtFreteKeyTyped(evt);
            }
        });
        jPanel8.add(edtFrete, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 60, -1));

        jLabel17.setText("Frete:");
        jPanel8.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 40, -1));

        jLabel22.setText("Marca:");
        jPanel8.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        edtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtMarcaKeyPressed(evt);
            }
        });
        jPanel8.add(edtMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 120, -1));

        edtPesoLiquido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtPesoLiquidoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtPesoLiquidoKeyTyped(evt);
            }
        });
        jPanel8.add(edtPesoLiquido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 120, -1));

        jLabel24.setText("Peso Líquido:");
        jPanel8.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 80, -1));

        edtPesoBruto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtPesoBrutoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtPesoBrutoKeyTyped(evt);
            }
        });
        jPanel8.add(edtPesoBruto, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 120, -1));

        edtVolume.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtVolumeKeyPressed(evt);
            }
        });
        jPanel8.add(edtVolume, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 120, -1));

        edtQuantidadeNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtQuantidadeNotaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtQuantidadeNotaKeyTyped(evt);
            }
        });
        jPanel8.add(edtQuantidadeNota, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 70, -1));

        jLabel2.setText("Quantidade:");
        jPanel8.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 70, -1));

        jLabel16.setText("Volume:");
        jPanel8.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 50, -1));

        jLabel25.setText("Peso Bruto:");
        jPanel8.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 70, -1));

        btnConsultaTransportadora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Search.png"))); // NOI18N
        btnConsultaTransportadora.setText("Consulta");
        btnConsultaTransportadora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaTransportadoraActionPerformed(evt);
            }
        });
        jPanel8.add(btnConsultaTransportadora, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 120, 30));

        edtTransportadora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtTransportadoraActionPerformed(evt);
            }
        });
        edtTransportadora.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtTransportadoraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtTransportadoraKeyTyped(evt);
            }
        });
        jPanel8.add(edtTransportadora, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 45, -1));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 280, 290));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RadioOutros.setText("Outros");
        RadioOutros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioOutrosActionPerformed(evt);
            }
        });
        jPanel4.add(RadioOutros, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 69, 83, -1));

        RadioDestinatario.setText("Destinatario");
        RadioDestinatario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioDestinatarioActionPerformed(evt);
            }
        });
        jPanel4.add(RadioDestinatario, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 41, -1, -1));

        RadioEmitente.setText("Emitente");
        RadioEmitente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioEmitenteActionPerformed(evt);
            }
        });
        RadioEmitente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RadioEmitenteKeyPressed(evt);
            }
        });
        jPanel4.add(RadioEmitente, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 11, 86, -1));

        jLabel18.setText("Frete Por Conta de:");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 20, 132, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 280, 100));

        PainelAbaPagamento.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 300, 420));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TabelaParcelas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TabelaParcelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dia", "Data", "Valor", "Tipo Cobranca"
            }
        ));
        TabelaParcelas.setEnabled(false);
        jScrollPane2.setViewportView(TabelaParcelas);
        if (TabelaParcelas.getColumnModel().getColumnCount() > 0) {
            TabelaParcelas.getColumnModel().getColumn(3).setMinWidth(100);
            TabelaParcelas.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 57, 340, 210));

        edtAcrescimoTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtAcrescimoTotalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtAcrescimoTotalKeyTyped(evt);
            }
        });
        jPanel2.add(edtAcrescimoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 31, 130, -1));

        edtDescontoTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtDescontoTotalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                edtDescontoTotalKeyTyped(evt);
            }
        });
        jPanel2.add(edtDescontoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 31, 130, -1));

        jLabel13.setText("Desconto");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(153, 12, -1, -1));

        jLabel12.setText("Acréscimo");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 12, -1, -1));

        PainelAbaPagamento.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 350, 290));

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        edtInfoAdicionais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtInfoAdicionaisKeyPressed(evt);
            }
        });
        jPanel5.add(edtInfoAdicionais, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 21, 320, 80));

        jLabel19.setText("Informações Adicionais:");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        PainelAbaPagamento.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 350, 110));

        PainelPagamento.addTab("Pagamento", PainelAbaPagamento);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel7.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel17.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1095, 1, -1, -1));

        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel33.setText("VALOR DO ICMS S.T.");

        edtVLTotalICMS_ST.setText("0,00");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addGap(39, 39, 39))
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtVLTotalICMS_ST, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel33)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel19Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtVLTotalICMS_ST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 200, 50));

        jPanel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel17.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1257, 1, -1, -1));

        jPanel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setText("BASE DE CÁCULO DO ICMS");

        edtBaseTotalICMS.setText("0,00");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel21Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtBaseTotalICMS, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addContainerGap(34, Short.MAX_VALUE))
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel21Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtBaseTotalICMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 12, 200, 50));

        jPanel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel32.setText("BASE DE CALCULO DO ICMS S.T.");

        edtBaseTotalICMS_ST.setText("0,00");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel23Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtBaseTotalICMS_ST, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel32)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel23Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtBaseTotalICMS_ST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 12, 200, 50));

        jPanel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel31.setText("VALOR DO ICMS");

        edtVLTotalICMS.setText("0,00");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addComponent(jLabel31)
                .addGap(55, 55, 55))
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel22Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtVLTotalICMS, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel31)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel22Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtVLTotalICMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 12, 200, 50));

        jPanel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel35.setText("VALOR DO FRETE");

        edtTotalFrete.setText("0,00");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addGap(53, 53, 53))
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel24Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtTotalFrete, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel35)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel24Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtTotalFrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 72, 200, 50));

        jPanel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel36.setText("VALOR DO SEGURO");

        edtValorSeguro.setText("0,00");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel36)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel25Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtValorSeguro, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(jLabel36)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel25Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtValorSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 72, 200, 50));

        jPanel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel37.setText("VALOR DO DESCONTO");

        edtVLTotalDesconto.setText("0,00");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel26Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtVLTotalDesconto, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel26Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtVLTotalDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 200, 50));

        jPanel27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel17.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(966, 121, -1, -1));

        jPanel28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel17.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(1251, 155, -1, -1));

        jPanel29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel39.setText("VALOR DE PIS");

        edtValorPIS.setText("0,00");

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel39)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel29Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtValorPIS, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(jLabel39)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel29Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtValorPIS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 200, 50));

        jPanel30.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel38.setText("OUTRAS DESP. ACESSÓRIAS");

        edtDespesasAcessorias.setText("0,00");

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel30Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtDespesasAcessorias, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel38)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel30Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtDespesasAcessorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 200, 50));

        jPanel31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel34.setText("VALOR TOTAL DOS PRODUTOS");

        edtTotalProdutos2.setText("0,00");

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel31Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtTotalProdutos2, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel34)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel31Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtTotalProdutos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, 200, 50));

        jPanel32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel40.setText("VALOR TOTAL DA NOTA");

        edtTotalNota.setText("0,00");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel32Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtTotalNota, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addComponent(jLabel40)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel32Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtTotalNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 250, 200, 50));

        jPanel34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel41.setText("VALOR TOTAL DO IPI");

        edtTotalIPI.setText("0,00");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel34Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtTotalIPI, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addComponent(jLabel41)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel34Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtTotalIPI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 200, 50));

        jPanel35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel42.setText("VALOR DE COFINS");

        edtValorCOFINS.setText("0,00");

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel35Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtValorCOFINS, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addComponent(jLabel42)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel35Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtValorCOFINS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 200, 50));

        jPanel36.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel43.setText("BASE DE PIS");

        edtBasePIS.setText("0,00");

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel43)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel36Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtBasePIS, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addComponent(jLabel43)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel36Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtBasePIS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 200, 50));

        jPanel37.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel44.setText("BASE DE COFINS");

        edtBaseCOFINS.setText("0,00");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel44)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel37Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(edtBaseCOFINS, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addComponent(jLabel44)
                .addGap(0, 34, Short.MAX_VALUE))
            .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel37Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(edtBaseCOFINS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel17.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 200, 50));

        jPanel7.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 660, 320));

        PainelPagamento.addTab("Impostos", jPanel7);

        getContentPane().add(PainelPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 690, 490));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel14.setText("Pedido-Nota");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, -1, -1));

        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        RadioOrcamento.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        RadioOrcamento.setText("Orçamento");
        RadioOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioOrcamentoActionPerformed(evt);
            }
        });
        jPanel10.add(RadioOrcamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));

        RadioPedido.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        RadioPedido.setText("Pedido");
        RadioPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioPedidoActionPerformed(evt);
            }
        });
        jPanel10.add(RadioPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 80, -1));

        RadioVenda.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        RadioVenda.setText("Venda");
        RadioVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioVendaActionPerformed(evt);
            }
        });
        jPanel10.add(RadioVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 120, -1));

        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGravar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
        jPanel9.add(btnGravar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 121, 28));

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
        jPanel9.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 121, 28));

        btnAdicionar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Add.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        jPanel9.add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 121, 28));

        btnConsulta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
        jPanel9.add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 121, 28));

        btnSair.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        jPanel9.add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 121, 28));

        jPanel10.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 140, 210));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Total Produtos:");
        jPanel10.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel27.setText("Total Nota:");
        jPanel10.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Desconto Itens:");
        jPanel10.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setText("Acréscimos:");
        jPanel10.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setText("Descontos:");
        jPanel10.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        labelDescontoTotal.setEditable(false);
        jPanel10.add(labelDescontoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 110, -1));

        labelAcrescimoTotal.setEditable(false);
        jPanel10.add(labelAcrescimoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 110, -1));

        edtTotalVenda.setEditable(false);
        jPanel10.add(edtTotalVenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 110, -1));

        edtTotalProduto.setEditable(false);
        jPanel10.add(edtTotalProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 110, -1));

        edtDescontoItemTotal.setEditable(false);
        jPanel10.add(edtDescontoItemTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 110, -1));

        getContentPane().add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 40, 250, 490));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, 950, 20));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 40, 10, 500));

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 40, 20, 480));

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel45.setText("Movimento:");
        getContentPane().add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, -1, -1));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getContentPane().add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, -1, 20));

        LabelMovimento.setEditable(false);
        LabelMovimento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        getContentPane().add(LabelMovimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 110, -1));

        getAccessibleContext().setAccessibleName("Form_Padrao");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void edtValorUnitarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtValorUnitarioKeyPressed
        String auxTexto = edtCodigoProduto.getText();
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            if (auxTexto.equals("")) {
                mensagemErro("Preencha o código do produto!");
                edtQuantidadeItem.grabFocus();
            } else if (edtQuantidadeItem.getText().equals("")) {
                mensagemErro("Preencha a quantidade do produto!");
                edtQuantidadeItem.grabFocus();
            } else if (edtValorUnitario.getText().equals("")) {
                mensagemErro("Preencha o preço do produto!");
                edtQuantidadeItem.grabFocus();
            } else {
                //Adiciona no grid
                DefaultTableModel model = (DefaultTableModel) TabelaProdutos.getModel();
                Object[] linha = {
                    edtCodigoProduto.getText(),
                    edtDescricao.getText(),
                    edtQuantidadeItem.getText(),
                    edtValorUnitario.getText()
                };
                model.addRow(linha);

                GeraTotaisNotaItem();

                edtDescricao.setText("");
                edtValorUnitario.setText("");
                edtCodigoProduto.setText("");
                edtQuantidadeItem.setText("");
                edtCodigoProduto.requestFocus();
            }
        }
    }//GEN-LAST:event_edtValorUnitarioKeyPressed

    private void edtCodigoProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoProdutoKeyPressed
        
        String auxTexto = edtCodigoProduto.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                edtDescricao.grabFocus();
            } else {
                ValidaCampoCodigoProdutoNaoNulo();
            }
        }
    }//GEN-LAST:event_edtCodigoProdutoKeyPressed

    private void edtDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtDescricaoKeyPressed
        String auxTexto = edtCodigoProduto.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                //Pesquisar como traz o painel para o foco
                //PainelPagamento.setFocusCycleRoot(closable);
                PainelAbaPagamento.requestFocus();
                edtAcrescimoTotal.requestFocus();
            } else {
                ValidaCampoCodigoProdutoNaoNulo();
            }
        }
    }//GEN-LAST:event_edtDescricaoKeyPressed

    private void edtQuantidadeItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtQuantidadeItemKeyPressed
        String auxTexto = edtCodigoProduto.getText();
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            if (auxTexto.equals("")) {
                mensagemErro("Preencha a quantidade!");
                edtQuantidadeItem.grabFocus();
            } else {
                edtValorUnitario.grabFocus();
            }
        }
    }//GEN-LAST:event_edtQuantidadeItemKeyPressed

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
                //Pega a Data Atual
                DataAtual();
                HoraAtual();

                edtAcrescimoTotal.setText("0.0");
                edtDescontoTotal.setText("0.0");
                RadioEmitente.setSelected(true);
                RadioOrcamento.setSelected(true);
                edtCodigoCliente.grabFocus();
            } else {
                try {
                    ListaVenda();
                } catch (ParseException ex) {
                    Logger.getLogger(ViewManutencaoPedidoCompleto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }//GEN-LAST:event_edtCodigoMovimentoKeyPressed

    private void edtCodigoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoClienteKeyPressed
        
        String auxTexto = edtCodigoCliente.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                mensagemErro("Codigo da pessoa nulo!");
                edtCodigoCliente.requestFocus();
            } else {
                ValidaCampoCodigoClienteNaoNulo();
            }
        }
    }//GEN-LAST:event_edtCodigoClienteKeyPressed

    private void edtCodigoVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoVendedorKeyPressed
        
        String auxTexto = edtCodigoVendedor.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                mensagemErro("Codigo do Vendedor nulo!");
                edtCodigoVendedor.requestFocus();
            } else {
                ValidaCampoCodigoVendedorNaoNulo();
            }
        }
    }//GEN-LAST:event_edtCodigoVendedorKeyPressed

    private void edtCodigoCondicaoPagamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoCondicaoPagamentoKeyPressed
        
        String auxTexto = edtCodigoCondicaoPagamento.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                mensagemErro("Codigo da Condicao nula!");
                edtCodigoCondicaoPagamento.requestFocus();
            } else {
                try {
                    ValidaCampoCodigoCondicaoPagamentoNaoNulo();
                    //Verifica se existe total da nota e se existe gera os totais da nota
                    String auxtotalnota = edtTotalNota.getText();
                    //Se nao for nulo gera os totais da nota
                    if (!auxtotalnota.equals("")) {
                        double valor = Double.parseDouble(auxtotalnota);
                        if (valor > 0) {
                            GeraTotaisNota();
                            ListaParcelas();
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(ViewManutencaoPedidoCompleto.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    private void cbCondicaoPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCondicaoPagamentoActionPerformed
        ComboBoxCondicaoPagamento();
    }//GEN-LAST:event_cbCondicaoPagamentoActionPerformed

    private void btnConsultaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaClienteActionPerformed
        
        ViewConsultaPessoa form = new ViewConsultaPessoa(edtCodigoCliente);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigoCliente.requestFocus();
    }//GEN-LAST:event_btnConsultaClienteActionPerformed

    private void btnConsultaVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaVendedorActionPerformed
        
        ViewConsultaPessoa form = new ViewConsultaPessoa(edtCodigoVendedor);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigoVendedor.requestFocus();
    }//GEN-LAST:event_btnConsultaVendedorActionPerformed

    private void btnConsultaCondicaoPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaCondicaoPagamentoActionPerformed
        ViewConsultaCondicaoPagamento form = new ViewConsultaCondicaoPagamento(edtCodigoCondicaoPagamento);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigoCondicaoPagamento.requestFocus();
    }//GEN-LAST:event_btnConsultaCondicaoPagamentoActionPerformed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente gravar o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            try {
                GravarCompletoValidado();
            } catch (ParseException ex) {
                mensagemErro("Erro no método GravarCompletoValidado() na conversão de Datas;" + ex.getMessage());
                Logger.getLogger(ViewManutencaoPedidoCompleto.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            btnSair.grabFocus();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        
        AdicionarVenda();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        ViewConsultaPedido form = new ViewConsultaPedido(edtCodigoMovimento);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigoMovimento.grabFocus();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btnConsultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSair.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaKeyPressed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Sair da tela?");
        if (resposta == JOptionPane.YES_OPTION) {
            if (gravouVenda) {
                dispose();
            } else {
                int resposta2 = JOptionPane.showConfirmDialog(null, "Você perderá os dados Adicionados ou Alterados!Deseja continuar?");
                if (resposta2 == JOptionPane.YES_OPTION) {
                    habilitaCampos(false);
                    limparTela();
                    dispose();
                } else {
                    edtCodigoCliente.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_btnSairActionPerformed

    private void RadioEmitenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioEmitenteActionPerformed
        RadioEmitente.setSelected(true);
        RadioDestinatario.setSelected(false);
        RadioOutros.setSelected(false);
    }//GEN-LAST:event_RadioEmitenteActionPerformed

    private void RadioDestinatarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioDestinatarioActionPerformed
        RadioDestinatario.setSelected(true);
        RadioEmitente.setSelected(false);
        RadioOutros.setSelected(false);
    }//GEN-LAST:event_RadioDestinatarioActionPerformed

    private void RadioOutrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioOutrosActionPerformed
        RadioOutros.setSelected(true);
        RadioDestinatario.setSelected(false);
        RadioEmitente.setSelected(false);
    }//GEN-LAST:event_RadioOutrosActionPerformed

    private void RadioOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioOrcamentoActionPerformed
        RadioOrcamento.setSelected(true);
        RadioPedido.setSelected(false);
        RadioVenda.setSelected(false);
    }//GEN-LAST:event_RadioOrcamentoActionPerformed

    private void RadioPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioPedidoActionPerformed
        RadioPedido.setSelected(true);
        RadioOrcamento.setSelected(false);
        RadioVenda.setSelected(false);
    }//GEN-LAST:event_RadioPedidoActionPerformed

    private void RadioVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioVendaActionPerformed
        RadioVenda.setSelected(true);
        RadioOrcamento.setSelected(false);
        RadioPedido.setSelected(false);
    }//GEN-LAST:event_RadioVendaActionPerformed

    private void cbTipo_NotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipo_NotaActionPerformed
        ComboBoxTipoNota();
    }//GEN-LAST:event_cbTipo_NotaActionPerformed

    private void edtTipoNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtTipoNotaKeyPressed
        
    }//GEN-LAST:event_edtTipoNotaKeyPressed

    private void btnConsultaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaProdutoActionPerformed
        ViewConsultaProduto form = new ViewConsultaProduto(edtCodigoProduto);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigoProduto.grabFocus();
    }//GEN-LAST:event_btnConsultaProdutoActionPerformed

    private void btnConsultaTransportadoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaTransportadoraActionPerformed
        ViewConsultaTransportadora form = new ViewConsultaTransportadora(edtTransportadora);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtTransportadora.grabFocus();
    }//GEN-LAST:event_btnConsultaTransportadoraActionPerformed

    private void edtTransportadoraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtTransportadoraKeyPressed
        String auxcodigo = edtTransportadora.getText();
        if (auxcodigo.equals("")) {
            mensagemErro("Campo Nulo!");
        } else {
            ValidaCampoCodigoTransportadoraNaoNulo();

            edtPlacaVeiculo.requestFocus();
        }
    }//GEN-LAST:event_edtTransportadoraKeyPressed

    private void edtTransportadoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtTransportadoraActionPerformed
        
        ValidaCampoCodigoTransportadoraNaoNulo();
    }//GEN-LAST:event_edtTransportadoraActionPerformed

    private void edtAcrescimoTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtAcrescimoTotalKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            String auxtotalnota = edtTotalNota.getText();
            //Se nao for nulo
            if (!auxtotalnota.equals("")) {
                double valor = Double.parseDouble(auxtotalnota);
                if (valor > 0) {
                    try {
                        GeraTotaisNota();
                        ListaParcelas();
                    } catch (ParseException ex) {
                        Logger.getLogger(ViewManutencaoPedidoCompleto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            edtDescontoTotal.grabFocus();
        }
    }//GEN-LAST:event_edtAcrescimoTotalKeyPressed

    private void edtDescontoTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtDescontoTotalKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            RadioEmitente.grabFocus();

            String auxtotalnota = edtTotalNota.getText();
            //Se nao for nulo
            if (!auxtotalnota.equals("")) {
                double valor = Double.parseDouble(auxtotalnota);
                if (valor > 0) {
                    try {
                        //GeraTotaisNotaItem();
                        GeraTotaisNota();
                        ListaParcelas();
                    } catch (ParseException ex) {
                        Logger.getLogger(ViewManutencaoPedidoCompleto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_edtDescontoTotalKeyPressed

    private void edtPlacaVeiculoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtPlacaVeiculoKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            edtQuantidadeNota.grabFocus();
        }
    }//GEN-LAST:event_edtPlacaVeiculoKeyPressed

    private void edtQuantidadeNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtQuantidadeNotaKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            edtFrete.grabFocus();
        }
    }//GEN-LAST:event_edtQuantidadeNotaKeyPressed

    private void edtFreteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtFreteKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            String auxtotalnota = edtTotalNota.getText();
            //Se nao for nulo
            if (!auxtotalnota.equals("")) {
                double valor = Double.parseDouble(auxtotalnota);
                if (valor > 0) {
                    try {
                        GeraTotaisNota();
                        ListaParcelas();
                    } catch (ParseException ex) {
                        Logger.getLogger(ViewManutencaoPedidoCompleto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            edtVolume.grabFocus();
        }
    }//GEN-LAST:event_edtFreteKeyPressed

    private void edtVolumeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtVolumeKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            edtEspecie.grabFocus();
        }
    }//GEN-LAST:event_edtVolumeKeyPressed

    private void edtEspecieKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtEspecieKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            edtMarca.grabFocus();
        }
    }//GEN-LAST:event_edtEspecieKeyPressed

    private void edtMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtMarcaKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            edtPesoBruto.grabFocus();
        }
    }//GEN-LAST:event_edtMarcaKeyPressed

    private void edtPesoBrutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtPesoBrutoKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            edtPesoLiquido.grabFocus();
        }
    }//GEN-LAST:event_edtPesoBrutoKeyPressed

    private void edtPesoLiquidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtPesoLiquidoKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            edtInfoAdicionais.grabFocus();
        }
    }//GEN-LAST:event_edtPesoLiquidoKeyPressed

    private void edtInfoAdicionaisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtInfoAdicionaisKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            btnGravar.grabFocus();
        }
    }//GEN-LAST:event_edtInfoAdicionaisKeyPressed

    private void RadioEmitenteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadioEmitenteKeyPressed
        
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_TAB)) {
            edtTransportadora.grabFocus();
        }
    }//GEN-LAST:event_RadioEmitenteKeyPressed

    private void edtDescontoTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtDescontoTotalKeyTyped
        
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtDescontoTotalKeyTyped

    private void edtAcrescimoTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtAcrescimoTotalKeyTyped
        
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtAcrescimoTotalKeyTyped

    private void edtFreteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtFreteKeyTyped
        
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtFreteKeyTyped

    private void edtPesoBrutoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtPesoBrutoKeyTyped
        
        String caracteres = "0987654321,";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtPesoBrutoKeyTyped

    private void edtPesoLiquidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtPesoLiquidoKeyTyped
        
        String caracteres = "0987654321,";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtPesoLiquidoKeyTyped

    private void edtQuantidadeNotaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtQuantidadeNotaKeyTyped
        
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtQuantidadeNotaKeyTyped

    private void edtTransportadoraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtTransportadoraKeyTyped
        
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtTransportadoraKeyTyped

    private void edtCodigoMovimentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoMovimentoKeyTyped
        
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtCodigoMovimentoKeyTyped

    private void edtCodigoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoClienteKeyTyped
        
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtCodigoClienteKeyTyped

    private void edtCodigoVendedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoVendedorKeyTyped
        
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtCodigoVendedorKeyTyped

    private void edtCodigoCondicaoPagamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoCondicaoPagamentoKeyTyped
        
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtCodigoCondicaoPagamentoKeyTyped

    private void edtCodigoProdutoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoProdutoKeyTyped
        
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtCodigoProdutoKeyTyped

    private void edtQuantidadeItemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtQuantidadeItemKeyTyped
        
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtQuantidadeItemKeyTyped

    private void edtValorUnitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtValorUnitarioKeyTyped
        
        String caracteres = "0987654321,";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_edtValorUnitarioKeyTyped

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
            java.util.logging.Logger.getLogger(ViewManutencaoPedidoCompleto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewManutencaoPedidoCompleto().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField LabelMovimento;
    private javax.swing.JLabel LabelNomeTransportadora;
    private javax.swing.JPanel PainelAbaPagamento;
    private javax.swing.JTabbedPane PainelPagamento;
    private javax.swing.JRadioButton RadioDestinatario;
    private javax.swing.JRadioButton RadioEmitente;
    private javax.swing.JRadioButton RadioOrcamento;
    private javax.swing.JRadioButton RadioOutros;
    private javax.swing.JRadioButton RadioPedido;
    private javax.swing.JRadioButton RadioVenda;
    private javax.swing.JTable TabelaParcelas;
    private javax.swing.JTable TabelaProdutos;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnConsultaCliente;
    private javax.swing.JButton btnConsultaCondicaoPagamento;
    private javax.swing.JButton btnConsultaProduto;
    private javax.swing.JButton btnConsultaTransportadora;
    private javax.swing.JButton btnConsultaVendedor;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox cbCliente;
    private javax.swing.JComboBox cbCondicaoPagamento;
    private javax.swing.JComboBox cbTipo_Nota;
    private javax.swing.JComboBox cbVendedor;
    private javax.swing.JTextField edtAcrescimoTotal;
    private javax.swing.JTextField edtBaseCOFINS;
    private javax.swing.JTextField edtBasePIS;
    private javax.swing.JTextField edtBaseTotalICMS;
    private javax.swing.JTextField edtBaseTotalICMS_ST;
    private javax.swing.JLabel edtCliente;
    private javax.swing.JTextField edtCodigoCliente;
    private javax.swing.JTextField edtCodigoCondicaoPagamento;
    private javax.swing.JTextField edtCodigoMovimento;
    private javax.swing.JTextField edtCodigoProduto;
    private javax.swing.JTextField edtCodigoVendedor;
    private javax.swing.JLabel edtCondicao_Pagamento;
    private javax.swing.JFormattedTextField edtDataEmissao;
    private javax.swing.JFormattedTextField edtDataSaida;
    private javax.swing.JTextField edtDescontoItemTotal;
    private javax.swing.JTextField edtDescontoTotal;
    private javax.swing.JTextField edtDescricao;
    private javax.swing.JTextField edtDespesasAcessorias;
    private javax.swing.JTextField edtEspecie;
    private javax.swing.JTextField edtFrete;
    private javax.swing.JFormattedTextField edtHoraEmissao;
    private javax.swing.JFormattedTextField edtHoraSaida;
    private javax.swing.JTextField edtInfoAdicionais;
    private javax.swing.JTextField edtMarca;
    private javax.swing.JTextField edtPesoBruto;
    private javax.swing.JTextField edtPesoLiquido;
    private javax.swing.JTextField edtPlacaVeiculo;
    private javax.swing.JTextField edtQuantidadeItem;
    private javax.swing.JTextField edtQuantidadeNota;
    private javax.swing.JTextField edtTipoNota;
    private javax.swing.JTextField edtTotalFrete;
    private javax.swing.JTextField edtTotalIPI;
    private javax.swing.JTextField edtTotalNota;
    private javax.swing.JTextField edtTotalProduto;
    private javax.swing.JTextField edtTotalProdutos2;
    private javax.swing.JTextField edtTotalVenda;
    private javax.swing.JTextField edtTransportadora;
    private javax.swing.JTextField edtVLTotalDesconto;
    private javax.swing.JTextField edtVLTotalICMS;
    private javax.swing.JTextField edtVLTotalICMS_ST;
    private javax.swing.JTextField edtValorCOFINS;
    private javax.swing.JTextField edtValorPIS;
    private javax.swing.JTextField edtValorSeguro;
    private javax.swing.JTextField edtValorUnitario;
    private javax.swing.JLabel edtVendedor;
    private javax.swing.JTextField edtVolume;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField labelAcrescimoTotal;
    private javax.swing.JTextField labelDescontoTotal;
    // End of variables declaration//GEN-END:variables
}
