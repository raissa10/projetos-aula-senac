package View;

import Controller.CondicaoPagamentoDB;
import Controller.SubCondPagDB;
import Controller.TipoCobrancaDB;
import Model.ModelCondicaoPagamento;
import Model.SubCondPag;
import Model.TipoCobranca;
import Estrutura.ConexaoFirebird;
import Estrutura.TelaPadraoGlobal;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gelvazio Camargo
 *
 */
public class CadCondicaoPagamento extends TelaPadraoGlobal {

    private int codigoTipoCobranca = 0;

    CondicaoPagamentoDB condicaopagamentodb = new CondicaoPagamentoDB();
    SubCondPagDB subcondpagdb = new SubCondPagDB();
    TipoCobrancaDB tipocobrancadb = new TipoCobrancaDB();

    //Cria o ArrayList que vai armazenar os registros á serem alterados
    ArrayList listaCodigosParcelas = new ArrayList();

    private static String codigosParaNaoDeletar = "";

    private static String sqlexcluirItemForaGrid
            = "";

    public CadCondicaoPagamento() {
        initComponents();
        centro();
        habilitaCampos(false);
        habilitaCamposParcelas(false);
    }

    private boolean verificaGrid() {
        int x = TabelaParcelas.getRowCount();
        return x > 0;
    }

    public boolean verificaCampoParaDeletar() {
        boolean verifica = false;
        String auxCodigoParcela = edtCodigoParcela.getText();
        String auxCodigoTipoCobranca = edtCodigoTipoCobranca.getText();
        String auxNRDias = edtQtdeDias.getText();

        if (auxCodigoParcela.equals("")) {
            verifica = false;
        } else if (auxCodigoTipoCobranca.equals("")) {
            verifica = false;
        } else {
            verifica = !auxNRDias.equals("");
        }

        return verifica;
    }

    public boolean verificaCampos() {
        boolean verifica = false;
        String auxtexto = edtCodigoParcela.getText();
        int CodigoParcela = Integer.parseInt(auxtexto);
        String auxCodigoTipoCobranca = edtCodigoTipoCobranca.getText();
        String auxNRDiasParcelas = edtQtdeDias.getText();
        int dias = Integer.parseInt(auxNRDiasParcelas);

        if (auxtexto.equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha um código de parcela válido!");
            edtCodigoParcela.requestFocus();
            verifica = false;
        } else {
            if (auxCodigoTipoCobranca.equals("")) {
                JOptionPane.showMessageDialog(null, "Selecione o Tipo de cobranca!");
                edtCodigoTipoCobranca.requestFocus();
                verifica = false;
            } else {
                if (auxNRDiasParcelas.equals("")) {
                    if (dias <= 0) {
                        JOptionPane.showMessageDialog(null, "Preencha a quantidade de dias da parcela!");
                        edtQtdeDias.requestFocus();
                        verifica = false;
                    }
                } else {
                    verifica = true;
                }
            }
        }
        return verifica;
    }

    private void LimpaTela() {
        edtCodigo.setText("");
        edtDescricao.setText("");
        edtCodigoTipoCobranca.setText("");

        //Limpa o TableModel e o grid
        DefaultTableModel model = (DefaultTableModel) TabelaParcelas.getModel();
        model.setNumRows(0);
        edtCodigo.grabFocus();
    }

    public void habilitaCamposParcelas(boolean habilita) {
        edtCodigoParcela.requestFocus();
        cbTipoCobranca.setEnabled(habilita);
        edtCodigoTipoCobranca.setEnabled(habilita);
        edtQtdeDias.setEnabled(habilita);

        //btnAdicionarGrid.setEnabled(habilita);
        btnCancelarGrid.setEnabled(habilita);
        //btnDeletarGrid.setEnabled(habilita);

        if (habilita) {
            cbTipoCobranca.setModel(tipocobrancadb.getComboRegistro());
            ComboBoxTipoCobranca();
        } else {
            //Limpa Campos das Parcelas
            edtCodigoTipoCobranca.setText("");
            edtQtdeDias.setText("");
            edtCodigoParcela.setText("");
            edtCodigoParcela.requestFocus();
        }

    }

    public void habilitaCampos(boolean habilita) {
        edtCodigo.setEnabled(!habilita);
        edtCodigo.requestFocus();
        edtCodigo.grabFocus();

        edtDescricao.setEnabled(habilita);
        edtCodigoTipoCobranca.setEnabled(habilita);
        edtQtdeDias.setEnabled(habilita);

        //Campos do Grid
        TabelaParcelas.setEnabled(habilita);
        cbTipoCobranca.setEnabled(habilita);
        btnAdicionarGrid.setEnabled(habilita);
        btnDeletarGrid.setEnabled(habilita);
        edtCodigoParcela.setEnabled(habilita);

        //Campos de Gravar/Consultar/Alterar
        btnGravar.setEnabled(habilita);
        btnCancelar.setEnabled(habilita);
        btnExcluir.setEnabled(habilita);
        btnConsulta.setEnabled(!habilita);

        if (habilita) {
            edtCodigo.requestFocus();
            //Só deve habilitar os campos se estiver editando parcelas
            //Chama um metodo pra isso
            cbTipoCobranca.setModel(tipocobrancadb.getComboRegistro());
            ComboBoxTipoCobranca();
            habilitaCamposParcelas(false);
        } else {
            //Remover registros dos campos
            LimpaTela();
            habilitaCamposParcelas(false);
        }
    }

    public void deletaItensGrid() {
        String auxTexto = edtCodigo.getText();
        String auxCodigoParcela = "";
        codigosParaNaoDeletar = "";

        for (int x = 0; x < TabelaParcelas.getRowCount(); x++) {
            auxCodigoParcela = String.valueOf(TabelaParcelas.getModel().getValueAt(x, 0));

            if (x == 0) {
                codigosParaNaoDeletar = codigosParaNaoDeletar + "'" + auxCodigoParcela + "'";
            } else {
                codigosParaNaoDeletar = codigosParaNaoDeletar + "," + "'" + auxCodigoParcela + "'";
            }
            //JOptionPane.showMessageDialog(null, "vl CodigoParcela: "+codigosParaNaoDeletar+" na posicao:  "+x);
        }
        sqlexcluirItemForaGrid
                = "DELETE FROM                           " + "\n"
                + "    SUB_COND_PAG                            " + "\n"
                + "WHERE                                 " + "\n"
                + "    SUB_COND_PAG.CD_PARCELA NOT              " + "\n"
                + "    IN (                              "
                + codigosParaNaoDeletar
                + "        ) "
                + "AND "
                + "SUB_COND_PAG.CD_CONDICAO="
                + auxTexto
                + ";";

        auxCodigoParcela = "";
        //JOptionPane.showMessageDialog(null, "vl fim string: \n" + sqlexcluirItemForaGrid);
    }

    public void excluirItemForaGrid() {
        //Alimenta o string do SQL:
        deletaItensGrid();

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConexaoFirebird.getConexao();
            pstmt = conn.prepareStatement(sqlexcluirItemForaGrid);
            pstmt.executeUpdate();
            //Limpa  SQL para usar em nova exclusão  
            codigosParaNaoDeletar = "";
            sqlexcluirItemForaGrid = "";
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirItemForaGrid(): \n" + erro.getMessage());
            JOptionPane.showMessageDialog(null, "vl fim string: \n" + sqlexcluirItemForaGrid);
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
    }

    public void AdicionaNoGrid() {
        boolean existeNoGrid = false;
        DefaultTableModel modelo = (DefaultTableModel) TabelaParcelas.getModel();
        if (verificaCampos()) {
            String auxtexto = edtCodigoParcela.getText();
            int CodigoParcela = Integer.parseInt(auxtexto);
            habilitaCamposParcelas(true);
            for (int x = 0; x < TabelaParcelas.getRowCount(); x++) {
                int auxcodigoparcela = (Integer.parseInt(String.valueOf(TabelaParcelas.getModel().getValueAt(x, 0))));
                if (CodigoParcela == auxcodigoparcela) {
                    //JOptionPane.showMessageDialog(null, "Metodo AdicionaNoGrid");
                    //já existe no grid e o mesmo deve ser atualizado
                    //Pega os dados da tela e altera no grid
                    String auxcodigoTipoCobranca = edtCodigoTipoCobranca.getText();
                    String auxNRDiasParcelas = edtQtdeDias.getText();

                    TabelaParcelas.getModel().setValueAt(auxcodigoTipoCobranca, x, 1);
                    TabelaParcelas.getModel().setValueAt(auxNRDiasParcelas, x, 3);
                    habilitaCamposParcelas(false);
                    existeNoGrid = true;
                }
            }
            //Se não existe no grid adiciona
            if (!existeNoGrid) {
                //adicionar
                //JOptionPane.showMessageDialog(null, "Metodo existeNoGrid");
                Object[] linha = {
                    edtCodigoParcela.getText(),
                    edtCodigoTipoCobranca.getText(),
                    cbTipoCobranca.getSelectedItem(),
                    edtQtdeDias.getText()
                };
                modelo.addRow(linha);
                habilitaCamposParcelas(false);
            }
        }
        edtCodigoParcela.setEnabled(true);
    }

    public int retornaCodigoParcela() {
        int codigo = 0;
        if (TabelaParcelas.getRowCount() == 0) {
            codigo = 1;
        } else {
            for (int x = 0; x < TabelaParcelas.getRowCount(); x++) {
                int auxCodigoGrid = (Integer.parseInt(String.valueOf(TabelaParcelas.getModel().getValueAt(x, 0))));

                if (x == TabelaParcelas.getRowCount() - 1) {
                    codigo = auxCodigoGrid + 1;
                }
            }
        }
        return codigo;
    }

    public boolean retornaCodigoParcelaDoGrid() {
        boolean verifica = false;
        if (TabelaParcelas.getRowCount() == 0) {
            verifica = false;
        } else {
            String auxtexto = edtCodigoParcela.getText();
            int codigoparcela = Integer.parseInt(auxtexto);
            for (int x = 0; x < TabelaParcelas.getRowCount(); x++) {
                int auxCodigoGrid = (Integer.parseInt(String.valueOf(TabelaParcelas.getModel().getValueAt(x, 0))));
                //Apenas retorna true se existir o codigo no grid
                if (auxCodigoGrid == codigoparcela) {
                    verifica = true;
                }
            }
        }
        return verifica;
    }

    public void carregaParceladoGrid() {
        String auxtexto = edtCodigoParcela.getText();
        int codigoparcela = Integer.parseInt(auxtexto);
        for (int x = 0; x < TabelaParcelas.getRowCount(); x++) {
            int auxCodigoGrid = (Integer.parseInt(String.valueOf(TabelaParcelas.getModel().getValueAt(x, 0))));

            if (auxCodigoGrid == codigoparcela) {
                //JOptionPane.showMessageDialog(null, "Metodo carregaParceladoGrid");
                String auxCodigo = TabelaParcelas.getValueAt(x, 0).toString();
                String auxcodigoTipoCobranca = TabelaParcelas.getValueAt(x, 1).toString();
                String auxcbTipoCobranca = TabelaParcelas.getValueAt(x, 2).toString();
                String auxNR_DIAS = TabelaParcelas.getValueAt(x, 3).toString();

                edtCodigoParcela.setText(auxCodigo);
                edtCodigoTipoCobranca.setText(auxcodigoTipoCobranca);
                cbTipoCobranca.setSelectedItem(auxcbTipoCobranca);
                edtQtdeDias.setText(auxNR_DIAS);
                cbTipoCobranca.requestFocus();
                //habilitaCamposParcelas(false);
            }
        }
    }

    public void insereAlteraDeletaParcelas() {
        String auxTexto = edtCodigo.getText();
        int auxCodigoCondicaoPagamento = Integer.parseInt(auxTexto);
        try {
            if (verificaGrid()) {
                int codigoparcela = 1;
                for (int x = 0; x < TabelaParcelas.getRowCount(); x++) {
                    int auxCodigoTipoCobranca = (Integer.parseInt(String.valueOf(TabelaParcelas.getModel().getValueAt(x, 1))));
                    int auxNRDiasParcela = (Integer.parseInt(String.valueOf(TabelaParcelas.getModel().getValueAt(x, 3))));

                    int auxcd_usuario = 1;

                    SubCondPag subcondpag = new SubCondPag(
                            auxCodigoCondicaoPagamento,
                            codigoparcela,
                            auxNRDiasParcela,
                            auxCodigoTipoCobranca,
                            auxcd_usuario);

                    if (subcondpagdb.retornaParcelaDaCondicao(auxCodigoCondicaoPagamento, codigoparcela)) {
                        //altera
                        //JOptionPane.showMessageDialog(null, "Entrou em alterar!");
                        if (subcondpagdb.alterar(subcondpag)) {
                            //JOptionPane.showMessageDialog(null, "Registro do Subgrupo alterado com sucesso! "+auxCodigoParcela);
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi possível alterar o registro da parcela: " + x + "\n"
                                    + " Condicao: " + auxCodigoCondicaoPagamento + "\n"
                                    + " parcela" + codigoparcela + "\n"
                                    + " Numero de dias: " + auxNRDiasParcela + "\n"
                                    + " Tipo de cobranca: " + auxCodigoTipoCobranca + "\n"
                                    + " Usuario: " + auxcd_usuario);
                        }
                    } else {
                        if (subcondpagdb.inserir(subcondpag)) {
                            //JOptionPane.showMessageDialog(null, "Registro do Markup inserido com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi possível inserir o registro da parcela: " + x);
                        }
                    }
                    codigoparcela++;
                }
                excluirItemForaGrid();
            } else {
                int auxCodigo = Integer.parseInt(edtCodigo.getText());
                if (subcondpagdb.excluirGridInteiro(auxCodigo)) {
                    //JOptionPane.showMessageDialog(null, "Exclusão de todos os dados do grid efetuada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel excluir todas as parcelas desta Condicao de Pagamento!");
                }
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao gravar dados do grid! " + erro.getMessage());
        } finally {

        }
        //Limpa a tela
        habilitaCampos(false);
    }

    public void listaParcelasCondicaoPagamento() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Parcela");
        modelo.addColumn("Cód Cobrança");
        modelo.addColumn("Cobrança");
        modelo.addColumn("Dias");

        habilitaCampos(true);

        int auxcodigoCondicaoPagamento = Integer.parseInt(edtCodigo.getText());

        ArrayList<TipoCobranca> tiposcobrancas = tipocobrancadb.listaNomeTipoCobranca(auxcodigoCondicaoPagamento);

        int auxCodigoParcelas = 1;//Pega a sequencia das parcelas
        if (subcondpagdb.getSubCondPag(auxcodigoCondicaoPagamento)) {

            for (TipoCobranca auxTipoCobranca : tiposcobrancas) {
                modelo.addRow(new Object[]{
                    auxCodigoParcelas,
                    auxTipoCobranca.getCd_cobranca(),
                    auxTipoCobranca.getDs_cobranca()
                });
                TabelaParcelas.setModel(modelo);
                auxCodigoParcelas++;
            }
            //Lista os nr de dias das parcelas
            auxCodigoParcelas = 1;
            for (int x = 0; x < TabelaParcelas.getRowCount(); x++) {
                int auxparcela = subcondpagdb.retornaNRDiasParcela(auxcodigoCondicaoPagamento, auxCodigoParcelas);
                modelo.setValueAt(auxparcela, x, 3);
                auxCodigoParcelas++;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não existem parcelas desta Condição de Pagamento!");
            edtDescricao.requestFocus();
        }
    }

    private void GravarAlterar() {
        //Validado apenas os campos NOT NULL  do banco de dados        
        String auxTexto = edtCodigo.getText();
        String auxNome = edtDescricao.getText();
        if (auxTexto.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Código do Grupo!");
            edtCodigo.grabFocus();
        } else if (auxNome.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o nome do Grupo!");
            edtDescricao.grabFocus();
        } else {
            int auxcd_usuario = 1;//= Integer.parseInt(login.getCodigo());
            int codigo = Integer.parseInt(auxTexto);

            ModelCondicaoPagamento condicaopagamento = new ModelCondicaoPagamento();
            condicaopagamento.setCodigo(codigo);
            condicaopagamento.setDescricao(auxNome);
            condicaopagamento.setCodigoUsuario(auxcd_usuario);

            if (condicaopagamentodb.getCondicaoPagamento(codigo)) {
                //Alterar
                if (condicaopagamentodb.alterar(condicaopagamento)) {
                    insereAlteraDeletaParcelas();

                    JOptionPane.showMessageDialog(null, "Registro alterado com sucesso!");
                    habilitaCampos(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possível alterar o registro!");
                    edtCodigo.grabFocus();
                }
            } else {
                //Inserir
                if (condicaopagamentodb.inserir(condicaopagamento)) {
                    insereAlteraDeletaParcelas();
                    JOptionPane.showMessageDialog(null, "Registro incluído com sucesso!");
                    habilitaCampos(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possível incluir o registro!");
                    edtCodigo.grabFocus();
                }
            }
        }
    }

    private void ExcluirRegistro() {
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            int auxCodigo = Integer.parseInt(edtCodigo.getText());
            if (condicaopagamentodb.excluir(auxCodigo)) {
                JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!");
                habilitaCampos(false);
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possivel excluir o registro!!");
            }
        }

    }

    private void ValidaCodigoNaoNulo() {
        int cd_condicao = Integer.parseInt(edtCodigo.getText());
        if (condicaopagamentodb.getCondicaoPagamento(cd_condicao)) {
            habilitaCampos(true);
            edtDescricao.grabFocus();
            ArrayList<ModelCondicaoPagamento> condicoespagamentos = condicaopagamentodb.listaCondicaoPagamento(cd_condicao);
            for (ModelCondicaoPagamento auxCondicaoPagamento : condicoespagamentos) {
                edtDescricao.setText(auxCondicaoPagamento.getDescricao());
            }
            listaParcelasCondicaoPagamento();
        } else {
            //Passa o código do generator para o campo
            String auxCodigoGenerator = "" + condicaopagamentodb.ValidaCodigoGenerator();
            edtCodigo.setText(auxCodigoGenerator);
            edtDescricao.requestFocus();
            habilitaCampos(true);
        }
    }

    public void ComboBoxTipoCobranca() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql
                = "SELECT TIPO_COBRANCA.*                   "
                + "  FROM TIPO_COBRANCA                    "
                + " WHERE TIPO_COBRANCA.DS_COBRANCA='" + cbTipoCobranca.getSelectedItem() + "'";
        try {
            conn = ConexaoFirebird.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();

            int auxCodigoTipoCobranca = rs.getInt("cd_cobranca");
            codigoTipoCobranca = auxCodigoTipoCobranca;
            String auxCodigoTipocobranca = "" + codigoTipoCobranca;

            edtCodigoTipoCobranca.setText(auxCodigoTipocobranca);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "TIPO_COBRANCA não encontrado!Erro na funcao ComboBoxTipoCobranca()!:" + ex.getMessage());
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

        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnGravar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaParcelas = new javax.swing.JTable();
        edtQtdeDias = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbTipoCobranca = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        edtCodigoTipoCobranca = new javax.swing.JTextField();
        edtDescricao = new javax.swing.JTextField();
        edtCodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnAdicionarGrid = new javax.swing.JButton();
        btnDeletarGrid = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        edtCodigoParcela = new javax.swing.JTextField();
        btnCancelarGrid = new javax.swing.JButton();
        btnCancelarGrid1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 110, 120, 40));

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
        getContentPane().add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 160, 120, 40));

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
        getContentPane().add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 220, 120, 40));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        getContentPane().add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 280, 120, 40));

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Valid Green.png"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGravar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 60, 120, 40));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TabelaParcelas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TabelaParcelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Parcela", "C?d Cobran?a", "Cobran?a", "Dias"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaParcelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaParcelasMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TabelaParcelasMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaParcelas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 760, 200));

        edtQtdeDias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtQtdeDiasKeyPressed(evt);
            }
        });
        jPanel1.add(edtQtdeDias, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 90, 30));

        jLabel11.setText("Qtde de Dias:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        cbTipoCobranca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTipoCobranca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoCobrancaActionPerformed(evt);
            }
        });
        cbTipoCobranca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbTipoCobrancaKeyPressed(evt);
            }
        });
        jPanel1.add(cbTipoCobranca, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 220, 30));

        jLabel10.setText("Tipo de Cobran?a:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 110, -1));

        jLabel8.setText("C?digo Parcela :");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        edtCodigoTipoCobranca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoTipoCobrancaKeyPressed(evt);
            }
        });
        jPanel1.add(edtCodigoTipoCobranca, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 230, 100, 30));

        edtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtDescricaoKeyPressed(evt);
            }
        });
        jPanel1.add(edtDescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 480, 30));

        edtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoKeyPressed(evt);
            }
        });
        jPanel1.add(edtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 80, 30));

        jLabel5.setText("C?digo:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));

        jLabel2.setText("Descri??o:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        btnAdicionarGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Add.png"))); // NOI18N
        btnAdicionarGrid.setText("Gravar");
        btnAdicionarGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarGridActionPerformed(evt);
            }
        });
        btnAdicionarGrid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAdicionarGridKeyPressed(evt);
            }
        });
        jPanel1.add(btnAdicionarGrid, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 270, 110, 30));

        btnDeletarGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Remove Red.png"))); // NOI18N
        btnDeletarGrid.setText("Deletar");
        btnDeletarGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarGridActionPerformed(evt);
            }
        });
        jPanel1.add(btnDeletarGrid, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 270, 110, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("Tipos de Cobran?a");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 210, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Condi??o de Pagamento");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 260, 40));

        edtCodigoParcela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoParcelaKeyPressed(evt);
            }
        });
        jPanel1.add(edtCodigoParcela, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 90, 30));

        btnCancelarGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Cancel.png"))); // NOI18N
        btnCancelarGrid.setText("Cancelar");
        btnCancelarGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarGridActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelarGrid, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 270, 120, 30));

        btnCancelarGrid1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Add.png"))); // NOI18N
        btnCancelarGrid1.setText("Novo");
        btnCancelarGrid1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarGrid1MouseClicked(evt);
            }
        });
        btnCancelarGrid1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarGrid1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelarGrid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 270, 110, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 780, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void edtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoKeyPressed
        // TODO add your handling code here:
        String auxTexto = edtCodigo.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                habilitaCampos(true);
                //Passa o código do generator para o campo
                String auxCodigoGenerator = "" + condicaopagamentodb.ValidaCodigoGenerator();
                edtCodigo.setText(auxCodigoGenerator);
                edtDescricao.requestFocus();
            } else {
                ValidaCodigoNaoNulo();
            }
        }
    }//GEN-LAST:event_edtCodigoKeyPressed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnExcluirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExcluirKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnConsulta.grabFocus();
        }
    }//GEN-LAST:event_btnExcluirKeyPressed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        ExcluirRegistro();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnExcluir.grabFocus();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Cancelar a Edição?");
        if (resposta == JOptionPane.YES_OPTION) {
            habilitaCampos(false);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void edtDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtDescricaoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtCodigoParcela.requestFocus();
        }
    }//GEN-LAST:event_edtDescricaoKeyPressed

    private void btnConsultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSair.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaKeyPressed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        ViewConsultaCondicaoPagamento form = new ViewConsultaCondicaoPagamento(edtCodigo);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigo.grabFocus();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        // TODO add your handling code here:
        String auxCodigoParcela = edtCodigoParcela.getText();
        if (verificaGrid()) {
            if (!auxCodigoParcela.equals("")) {
                if (verificaCampos()) {
                    GravarAlterar();
                }
            } else {
                GravarAlterar();
            }
        } else {
            GravarAlterar();
        }
    }//GEN-LAST:event_btnGravarActionPerformed

    private void edtCodigoTipoCobrancaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoTipoCobrancaKeyPressed
        String auxtexto = edtCodigoTipoCobranca.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            if (auxtexto.equals("")) {
                JOptionPane.showMessageDialog(null, "Selecione um tipo de cobrança!");
                cbTipoCobranca.requestFocus();
            } else {
                edtQtdeDias.requestFocus();
            }
        }
    }//GEN-LAST:event_edtCodigoTipoCobrancaKeyPressed

    private void TabelaParcelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaParcelasMouseClicked
        // TODO add your handling code here:
        int linha = TabelaParcelas.getSelectedRow();
        if (linha >= 0) {
            String auxCodigo = TabelaParcelas.getValueAt(linha, 0).toString();
            String auxcodigoTipoCobranca = TabelaParcelas.getValueAt(linha, 1).toString();
            String auxcbTipoCobranca = TabelaParcelas.getValueAt(linha, 2).toString();
            String auxNR_DIAS = TabelaParcelas.getValueAt(linha, 3).toString();

            edtCodigoParcela.setText(auxCodigo);
            edtCodigoTipoCobranca.setText(auxcodigoTipoCobranca);
            cbTipoCobranca.setSelectedItem(auxcbTipoCobranca);
            edtQtdeDias.setText(auxNR_DIAS);

        }
    }//GEN-LAST:event_TabelaParcelasMouseClicked

    private void edtQtdeDiasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtQtdeDiasKeyPressed
        // TODO add your handling code here:
        String auxtexto = edtQtdeDias.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            if (auxtexto.equals("")) {
                JOptionPane.showMessageDialog(null, "Selecione a quantidade de dias!");
                edtQtdeDias.requestFocus();
            } else {
                btnAdicionarGrid.requestFocus();
            }
        }
    }//GEN-LAST:event_edtQtdeDiasKeyPressed

    private void btnAdicionarGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarGridActionPerformed
        String auxtexto = edtCodigoParcela.getText();
        if (auxtexto.equals("")) {
            habilitaCamposParcelas(true);
            int aux = retornaCodigoParcela();
            String auxCodigoParcela = "" + aux;
            edtCodigoParcela.setText(auxCodigoParcela);
            cbTipoCobranca.requestFocus();
            edtCodigoParcela.setEnabled(false);
        } else {
            AdicionaNoGrid();
        }
    }//GEN-LAST:event_btnAdicionarGridActionPerformed

    private void btnDeletarGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarGridActionPerformed
        if (verificaGrid()) {
            if (verificaCampoParaDeletar()) {
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Deletar o registro selecionado?");
                if (resposta == JOptionPane.YES_OPTION) {

                    int Codigo = TabelaParcelas.getSelectedRow();
                    DefaultTableModel modelo = (DefaultTableModel) TabelaParcelas.getModel();
                    modelo.removeRow(Codigo);
                    habilitaCamposParcelas(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Voce cancelou a exclusão do item!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um item clicando no grid!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não existem itens á serem deletados!");
        }
    }//GEN-LAST:event_btnDeletarGridActionPerformed

    private void TabelaParcelasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaParcelasMousePressed
        // TODO add your handling code here:
        int linha = TabelaParcelas.getSelectedRow();
        if (linha >= 0) {
            String auxCodigo = TabelaParcelas.getValueAt(linha, 0).toString();
            String auxcodigoTipoCobranca = TabelaParcelas.getValueAt(linha, 1).toString();
            String auxcbTipoCobranca = TabelaParcelas.getValueAt(linha, 2).toString();
            String auxNR_DIAS = TabelaParcelas.getValueAt(linha, 3).toString();

            edtCodigoParcela.setText(auxCodigo);
            edtCodigoTipoCobranca.setText(auxcodigoTipoCobranca);
            cbTipoCobranca.setSelectedItem(auxcbTipoCobranca);
            edtQtdeDias.setText(auxNR_DIAS);

            //ComboBoxTipoCobranca();
        }
    }//GEN-LAST:event_TabelaParcelasMousePressed

    private void cbTipoCobrancaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoCobrancaActionPerformed
        // TODO add your handling code here:
        ComboBoxTipoCobranca();
    }//GEN-LAST:event_cbTipoCobrancaActionPerformed

    private void edtCodigoParcelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoParcelaKeyPressed
        String auxtexto = edtCodigoParcela.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //Se o CodigoParcela da parcela esta nulo carrega o proximo do grid
            if (auxtexto.equals("")) {
                habilitaCamposParcelas(true);
                int aux = retornaCodigoParcela();
                String auxCodigoParcela = "" + aux;
                edtCodigoParcela.setText(auxCodigoParcela);
                cbTipoCobranca.requestFocus();
                edtCodigoParcela.setEnabled(false);
            } else {
                habilitaCamposParcelas(true);
                edtCodigoParcela.setEnabled(false);
                //Se o CodigoParcela da parcela existe no grid carrega os dados
                if (retornaCodigoParcelaDoGrid()) {
                    //Carrega os dados do grid na tela
                    carregaParceladoGrid();
                } else {
                    //JOptionPane.showMessageDialog(null, "Aqui!");

                    //Carrega proximo CodigoParcela do grid
                    int aux = retornaCodigoParcela();
                    String auxCodigoParcela = "" + aux;
                    edtCodigoParcela.setText(auxCodigoParcela);
                    cbTipoCobranca.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_edtCodigoParcelaKeyPressed

    private void cbTipoCobrancaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTipoCobrancaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtQtdeDias.requestFocus();
        }
    }//GEN-LAST:event_cbTipoCobrancaKeyPressed

    private void btnAdicionarGridKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAdicionarGridKeyPressed
        // TODO add your handling code here:
        String auxtexto = edtCodigoParcela.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxtexto.equals("")) {
                habilitaCamposParcelas(true);
                int aux = retornaCodigoParcela();
                String auxCodigoParcela = "" + aux;
                edtCodigoParcela.setText(auxCodigoParcela);
                cbTipoCobranca.requestFocus();
                edtCodigoParcela.setEnabled(false);
            } else {
                AdicionaNoGrid();
            }
        }
    }//GEN-LAST:event_btnAdicionarGridKeyPressed

    private void btnCancelarGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarGridActionPerformed
        // TODO add your handling code here:
        habilitaCamposParcelas(false);
        edtCodigoParcela.setEnabled(true);
    }//GEN-LAST:event_btnCancelarGridActionPerformed

    private void btnCancelarGrid1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarGrid1ActionPerformed
        // TODO add your handling code here:
        habilitaCamposParcelas(true);
        int aux = retornaCodigoParcela();
        String auxCodigoParcela = "" + aux;
        edtCodigoParcela.setText(auxCodigoParcela);
        edtCodigoParcela.setEnabled(false);
        cbTipoCobranca.requestFocus();
    }//GEN-LAST:event_btnCancelarGrid1ActionPerformed

    private void btnCancelarGrid1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarGrid1MouseClicked
        // TODO add your handling code here:
        habilitaCamposParcelas(true);
        int aux = retornaCodigoParcela();
        String auxCodigoParcela = "" + aux;
        edtCodigoParcela.setText(auxCodigoParcela);
        edtCodigoParcela.setEnabled(false);
        cbTipoCobranca.requestFocus();
    }//GEN-LAST:event_btnCancelarGrid1MouseClicked

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
            java.util.logging.Logger.getLogger(CadCondicaoPagamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadCondicaoPagamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadCondicaoPagamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadCondicaoPagamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            @Override
            public void run() {
                new CadCondicaoPagamento().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelaParcelas;
    private javax.swing.JButton btnAdicionarGrid;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarGrid;
    private javax.swing.JButton btnCancelarGrid1;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnDeletarGrid;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox cbTipoCobranca;
    private javax.swing.JTextField edtCodigo;
    private javax.swing.JTextField edtCodigoParcela;
    private javax.swing.JTextField edtCodigoTipoCobranca;
    private javax.swing.JTextField edtDescricao;
    private javax.swing.JTextField edtQtdeDias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
