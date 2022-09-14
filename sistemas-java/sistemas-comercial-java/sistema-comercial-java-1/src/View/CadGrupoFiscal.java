package View;

import Controller.EstadoDB;
import Controller.GrupoFiscalDB;
import Controller.MarkupDB;
import Model.GrupoFiscal;
import Model.Markup;
import Model.MarkupOO;
import Estrutura.Conexao;
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
 *
 * @author Gelvazio Camargo
 *
 *
 */
public class CadGrupoFiscal extends TelaPadraoGlobal {

    GrupoFiscalDB grupofiscaldb = new GrupoFiscalDB();
    MarkupDB markupdb = new MarkupDB();

    ArrayList<MarkupOO> listaMarkups = new ArrayList();

    MarkupOO markupOO = new MarkupOO();

    //Cria o ArrayList que vai armazenar os registros á serem alterados
    ArrayList listaCodigosSubgrupo = new ArrayList();

    int iteradorarrayCodigosSubgrupo = 0;
    int arrayCodigosSubgrupo[] = new int[iteradorarrayCodigosSubgrupo];

    private static String codigosParaNaoDeletarSubGrupo = "";

    private static String sqlexcluirItemForaGrid
            = "";

    /**
     * Creates new form CadGrupoFiscal
     */
    public CadGrupoFiscal() {
        initComponents();
        centro();
        habilitaCampos(false);
    }

    private boolean verificaGrid() {
        int x = TabelaMarkup.getRowCount();
        return x > 0;
    }

    public boolean verificaCampoParaDeletar() {
        boolean verifica = false;
        String auxNomeEstado = edtNomeEstado.getText();
        String auxTXICMSInterno = edtTXICMSInterno.getText();
        String auxTXICMSInterEstadual = edtTXICMSInterEstadual.getText();

        if (cbEstado.getSelectedItem().equals("")) {
            verifica = false;
        } else if (auxNomeEstado.equals("")) {
            verifica = false;
        } else if (auxTXICMSInterno.equals("")) {
            verifica = false;
        } else if (auxTXICMSInterEstadual.equals("")) {
            verifica = false;
        } else {
            verifica = true;
        }
        return verifica;
    }

    public boolean verificaCampoMakup() {
        boolean verifica = false;
        String auxNomeEstado = edtNomeEstado.getText();
        String auxTXICMSInterno = edtTXICMSInterno.getText();
        String auxTXICMSInterEstadual = edtTXICMSInterEstadual.getText();

        if (cbEstado.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione o estado!");
            cbEstado.requestFocus();
            verifica = false;
        } else if (auxNomeEstado.equals("")) {
            JOptionPane.showMessageDialog(null, "Selecione o estado!");
            cbEstado.requestFocus();
            verifica = false;
        } else if (auxTXICMSInterno.equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o ICMS Interno!");
            edtTXICMSInterno.requestFocus();
            verifica = false;
        } else if (auxTXICMSInterEstadual.equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o ICMS Inter-Estadual!");
            edtTXICMSInterEstadual.requestFocus();
            verifica = false;
        } else {
            verifica = true;
            cbEstado.requestFocus();
        }
        return verifica;
    }

    public void ComboBoxEstado() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql
                = "SELECT                "
                + "    ESTADO.*          "
                + "FROM                  "
                + "     ESTADO           "
                + "WHERE                 "
                + "    ESTADO.CD_ESTADO='" + cbEstado.getSelectedItem() + "'";
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            String auxds_estado = rs.getString("ds_estado");
            edtNomeEstado.setText(auxds_estado);
            edtTXICMSInterno.requestFocus();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Estado não encontrado!Erro na funcao ComboBoxEstado()!:" + ex.getMessage());
        }
    }

    private void LimpaTela() {
        edtCodigo.setText("");
        edtNomeGrupoFiscal.setText("");
        edtNomeEstado.setText("");

        //Limpa o TableModel e o grid
        DefaultTableModel model = (DefaultTableModel) TabelaMarkup.getModel();
        model.setNumRows(0);
        edtCodigo.grabFocus();
    }

    public void habilitaCampos(boolean habilita) {
        edtCodigo.requestFocus();
        edtCodigo.setEnabled(!habilita);
        edtNomeGrupoFiscal.setEnabled(habilita);
        edtNomeEstado.setEnabled(habilita);
        edtTXICMSInterEstadual.setEnabled(habilita);
        edtTXICMSInterno.setEnabled(habilita);

        //Campos do Grid
        TabelaMarkup.setEnabled(habilita);
        cbEstado.setEnabled(habilita);
        btnAdicionarMarkup.setEnabled(habilita);
        btnDeletarMarkup.setEnabled(habilita);

        //Campos de Gravar/Consultar/Alterar
        btnGravar.setEnabled(habilita);
        btnCancelar.setEnabled(habilita);
        btnExcluir.setEnabled(habilita);
        btnConsulta.setEnabled(!habilita);

        if (habilita) {
            edtCodigo.requestFocus();
            //Carrega s estados
            EstadoDB estadodb = new EstadoDB();
            cbEstado.setModel(estadodb.getComboEstado());
            ComboBoxEstado();
        } else {
            //Remover registros dos campos
            LimpaTela();
        }
    }

    public void deletaItensMarkup() {
        String auxTexto = edtCodigo.getText();
        String auxcodigosubgrupo = "";
        codigosParaNaoDeletarSubGrupo = "";

        for (int x = 0; x < TabelaMarkup.getRowCount(); x++) {
            auxcodigosubgrupo = String.valueOf(TabelaMarkup.getModel().getValueAt(x, 0));

            if (x == 0) {
                codigosParaNaoDeletarSubGrupo = codigosParaNaoDeletarSubGrupo + "'" + auxcodigosubgrupo + "'";
            } else {
                codigosParaNaoDeletarSubGrupo = codigosParaNaoDeletarSubGrupo + "," + "'" + auxcodigosubgrupo + "'";
            }
            //JOptionPane.showMessageDialog(null, "vl codigo: "+codigosParaNaoDeletarSubGrupo+" na posicao:  "+x);
        }
        sqlexcluirItemForaGrid
                = "DELETE FROM                           " + "\n"
                + "    MARKUP                            " + "\n"
                + "WHERE                                 " + "\n"
                + "    MARKUP.CD_ESTADO NOT              " + "\n"
                + "    IN (                              "
                + codigosParaNaoDeletarSubGrupo
                + "        ) "
                + "AND "
                + "MARKUP.CD_GRUPO_FISCAL="
                + auxTexto
                + ";";

        auxcodigosubgrupo = "";
        //JOptionPane.showMessageDialog(null, "vl fim string: \n" + sqlexcluirItemForaGrid);
    }

    public void excluirItemForaGrid() {
        //Alimenta o string do SQL:
        deletaItensMarkup();

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlexcluirItemForaGrid);
            pstmt.executeUpdate();
            //Limpa  SQL para usar em nova exclusão  
            codigosParaNaoDeletarSubGrupo = "";
            sqlexcluirItemForaGrid = "";
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirItemForaGrid(): \n" + erro.getMessage());
            JOptionPane.showMessageDialog(null, "vl fim string: \n" + sqlexcluirItemForaGrid);
        } finally {
            Conexao.closeAll(conn);
        }

    }

    public void limpabancoegridsubgrup() {
        excluirItemForaGrid();
    }

    public void adicionarAlterarGrid() {
        boolean existeNoGrid = false;
        DefaultTableModel modelo = (DefaultTableModel) TabelaMarkup.getModel();
        String codigoEstado = cbEstado.getSelectedItem().toString();

        if (verificaCampoMakup()) {
            for (int x = 0; x < TabelaMarkup.getRowCount(); x++) {
                String auxcodigoestado = String.valueOf(TabelaMarkup.getModel().getValueAt(x, 0));
                //Se existe o Estado no grid deleta o velho e adiciona  o novo
                if (codigoEstado.equals(auxcodigoestado)) {
                    modelo = (DefaultTableModel) TabelaMarkup.getModel();
                    //Remove a linha atual do grid na posição x
                    modelo.removeRow(x);

                    //Aqui Adiciona os dados da tela para o grid
                    Object[] linha = {
                        cbEstado.getSelectedItem(),
                        edtTXICMSInterno.getText(),
                        edtTXICMSInterEstadual.getText()
                    };
                    modelo.addRow(linha);
                    existeNoGrid = true;
                    cbEstado.setSelectedItem("");
                    edtNomeEstado.setText("");
                    edtTXICMSInterno.setText("");
                    edtTXICMSInterEstadual.setText("");
                    cbEstado.requestFocus();
                    ComboBoxEstado();
                }
            }
            //Se não existe no grid adiciona direto
            if (!existeNoGrid) {
                Object[] linha = {
                    cbEstado.getSelectedItem(),
                    edtTXICMSInterno.getText(),
                    edtTXICMSInterEstadual.getText()
                };
                modelo.addRow(linha);
                cbEstado.setSelectedItem("");
                edtNomeEstado.setText("");
                edtTXICMSInterno.setText("");
                edtTXICMSInterEstadual.setText("");
                cbEstado.requestFocus();
                ComboBoxEstado();
            }
        }
    }

    public void gravarAlterarMarkups() {
        String auxTexto = edtCodigo.getText();
        int auxCodigoGrupo = Integer.parseInt(auxTexto);
        try {
            if (verificaGrid()) {

                for (int x = 0; x < TabelaMarkup.getRowCount(); x++) {
                    String auxcodigoestado = String.valueOf(TabelaMarkup.getModel().getValueAt(x, 0));
                    int auxtxicmsinterno = (Integer.parseInt(String.valueOf(TabelaMarkup.getModel().getValueAt(x, 1))));
                    int auxtxicmsinterestadual = (Integer.parseInt(String.valueOf(TabelaMarkup.getModel().getValueAt(x, 2))));
                    int auxcd_usuario = 1;

                    Markup markup = new Markup(
                            auxCodigoGrupo,
                            auxcodigoestado,
                            auxtxicmsinterno,
                            auxtxicmsinterestadual,
                            auxcd_usuario);

                    if (markupdb.getMarkup(auxCodigoGrupo, auxcodigoestado)) {
                        //altera
                        //JOptionPane.showMessageDialog(null, "Entrou em alterar!");
                        if (markupdb.alterar(markup)) {
                            //JOptionPane.showMessageDialog(null, "Registro do Subgrupo alterado com sucesso! "+auxcodigosubgrupo);                           
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi possível alterar o registro do Markup: " + x);
                        }
                    } else {
                        if (markupdb.inserir(markup)) {
                            //JOptionPane.showMessageDialog(null, "Registro do Markup inserido com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi possível inserir o registro do Markup: " + x);
                        }
                    }
                }
                //Chama a classe de subgrupo e 
                //que deleta os dados que não estão no grid
                limpabancoegridsubgrup();
            } else {
                //Deleta todos os Subgrupos deste Grupo para a alteração se efetivar
                //Por que não existe nenhum grupo na grade
                int auxCodigo = Integer.parseInt(edtCodigo.getText());

                if (markupdb.excluirGridInteiro(auxCodigo)) {
                    //JOptionPane.showMessageDialog(null, "Exclusão de todos os subgrupos efetuada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel excluir todos os Markups deste grupo fiscal!");
                }
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao gravar item do subgrupo! " + erro.getMessage());
        } finally {
            //JOptionPane.showMessageDialog(null, "Finalizando erro ao gravar subgrupo!");
        }
        //Limpa a tela
        habilitaCampos(false);
    }

    public void listaMarkups() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Estado");
        modelo.addColumn("TX ICMS Interno");
        modelo.addColumn("TX ICMS Inter-Estadual");

        int auxcodigogrupo = Integer.parseInt(edtCodigo.getText());

        ArrayList<Markup> markup = markupdb.listaMarkups(auxcodigogrupo);

        if (markupdb.getMarkupPeloGrupo(auxcodigogrupo)) {
            for (Markup auxmarkup : markup) {
                modelo.addRow(new Object[]{
                    auxmarkup.getCd_estado(),
                    auxmarkup.getTx_icms_interno(),
                    auxmarkup.getTx_icms_interestadual()
                });
                TabelaMarkup.setModel(modelo);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não existe Markup deste Grupo Fiscal!");
            edtNomeGrupoFiscal.requestFocus();
        }
    }

    private void GravarAlterar() {
        //Validado apenas os campos NOT NULL  do banco de dados        
        String auxTexto = edtCodigo.getText();
        String auxNome = edtNomeGrupoFiscal.getText();
        if (auxTexto.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Código do Grupo!");
            edtCodigo.grabFocus();
        } else if (auxNome.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o nome do Grupo!");
            edtNomeGrupoFiscal.grabFocus();
        } else {
            //GravarAlterarRegistro();
            int auxcd_usuario = 1;//= Integer.parseInt(login.getCodigo());
            int auxcd_filial = 1;
            int auxCodigo = Integer.parseInt(auxTexto);

            //Passa os parâmetros da tela para o construtor
            GrupoFiscal grupofiscal = new GrupoFiscal(
                    auxcd_filial,
                    auxCodigo,
                    auxNome,
                    auxcd_usuario
            );

            int codigo = Integer.parseInt(auxTexto);
            if (grupofiscaldb.getGrupoFiscal(codigo)) {
                //Altera
                if (grupofiscaldb.alterar(grupofiscal)) {
                    //Entra na programação do Grid
                    gravarAlterarMarkups();

                    JOptionPane.showMessageDialog(null, "Registro alterado com sucesso!");
                    habilitaCampos(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possível alterar o registro!");
                    edtCodigo.grabFocus();
                }
            } else {
                //Insere
                if (grupofiscaldb.inserir(grupofiscal)) {
                    //Entra na programação do Grid
                    gravarAlterarMarkups();

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
            if (grupofiscaldb.excluir(auxCodigo)) {
                JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!");
                habilitaCampos(false);
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possivel excluir o registro!!");
            }
        }
        LimpaTela();
    }

    private void ValidaCodigoTela() {
        int cd_grupo = Integer.parseInt(edtCodigo.getText());
        //Se existe o GrupoFiscal ele lista
        if (grupofiscaldb.getGrupoFiscal(cd_grupo)) {

            habilitaCampos(true);

            //Passa os dados do ArrayList para a classe GrupoFiscal
            ArrayList<GrupoFiscal> gruposfiscais = grupofiscaldb.listarGruposFiscais(cd_grupo);
            //Faz um For para incrementar na tela os dados 
            for (GrupoFiscal auxGrupoFiscal : gruposfiscais) {
                //Passa os dados aqui neste "for" de objetos 
                edtNomeGrupoFiscal.setText(auxGrupoFiscal.getDs_grupo_fiscal());
            }
            edtNomeGrupoFiscal.requestFocus();

            //Aqui Lista os dados dos Markups no Grid
            listaMarkups();

        } else {
            JOptionPane.showMessageDialog(null, "Grupo não Cadastrado!");
            habilitaCampos(true);

            int codigoGenerator = grupofiscaldb.ValidaCodigoGenerator();
            String auxCodigoGenerator = "" + codigoGenerator;
            edtCodigo.setText(auxCodigoGenerator);
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

        jLabel1 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnGravar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        edtNomeGrupoFiscal = new javax.swing.JTextField();
        edtCodigo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbEstado = new javax.swing.JComboBox();
        edtNomeEstado = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        edtTXICMSInterEstadual = new javax.swing.JTextField();
        edtTXICMSInterno = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnAdicionarMarkup = new javax.swing.JButton();
        btnDeletarMarkup = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaMarkup = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Cadastro de Grupos Fiscais");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 300, 40));

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
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, 120, 40));

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
        getContentPane().add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, 120, 40));

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
        getContentPane().add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 220, 120, 40));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        getContentPane().add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 280, 120, 40));

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
        getContentPane().add(btnGravar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 120, 40));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Código:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 50, -1));

        jLabel2.setText("Descrição:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 60, -1));

        edtNomeGrupoFiscal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtNomeGrupoFiscalKeyPressed(evt);
            }
        });
        jPanel1.add(edtNomeGrupoFiscal, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 320, 30));

        edtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoKeyPressed(evt);
            }
        });
        jPanel1.add(edtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 80, 30));

        jLabel8.setText("Estado:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 50, -1));

        cbEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbEstado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbEstadoMouseClicked(evt);
            }
        });
        cbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEstadoActionPerformed(evt);
            }
        });
        jPanel1.add(cbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 110, 30));

        edtNomeEstado.setEditable(false);
        edtNomeEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtNomeEstadoKeyPressed(evt);
            }
        });
        jPanel1.add(edtNomeEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 90, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("Markup");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 90, -1));

        edtTXICMSInterEstadual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtTXICMSInterEstadualKeyPressed(evt);
            }
        });
        jPanel1.add(edtTXICMSInterEstadual, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 90, 30));

        edtTXICMSInterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtTXICMSInternoKeyPressed(evt);
            }
        });
        jPanel1.add(edtTXICMSInterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 90, 30));

        jLabel10.setText("ICMS Interno:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 90, -1));

        btnAdicionarMarkup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Add.png"))); // NOI18N
        btnAdicionarMarkup.setText("Adicionar / Alterar");
        btnAdicionarMarkup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarMarkupActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdicionarMarkup, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, 170, 30));

        btnDeletarMarkup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Remove Red.png"))); // NOI18N
        btnDeletarMarkup.setText("Deletar");
        btnDeletarMarkup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarMarkupActionPerformed(evt);
            }
        });
        jPanel1.add(btnDeletarMarkup, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 230, 120, 30));

        jLabel11.setText("ICMS InterEstadual:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 580, 280));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TabelaMarkup.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TabelaMarkup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Estado", "TX ICMS Interno", "TX ICMS Inter-Estadual"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaMarkup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaMarkupMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TabelaMarkupMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaMarkup);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 150));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 580, 150));
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 770, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void edtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoKeyPressed
        
        String auxTexto = edtCodigo.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                habilitaCampos(true);

                int codigoGenerator = grupofiscaldb.ValidaCodigoGenerator();
                String auxCodigoGenerator = "" + codigoGenerator;

                edtCodigo.setText(auxCodigoGenerator);
                edtNomeGrupoFiscal.grabFocus();
            } else {
                ValidaCodigoTela();
            }
        }
    }//GEN-LAST:event_edtCodigoKeyPressed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnExcluirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExcluirKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnConsulta.grabFocus();
        }
    }//GEN-LAST:event_btnExcluirKeyPressed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        
        ExcluirRegistro();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnExcluir.grabFocus();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Cancelar a Edição?");
        if (resposta == JOptionPane.YES_OPTION) {
            habilitaCampos(false);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void edtNomeGrupoFiscalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtNomeGrupoFiscalKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //edtCodigoSubGrupo.requestFocus();
        }
    }//GEN-LAST:event_edtNomeGrupoFiscalKeyPressed

    private void btnConsultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSair.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaKeyPressed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        
        ConsultaGrupoFiscal form = new ConsultaGrupoFiscal(edtCodigo);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigo.grabFocus();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        
        GravarAlterar();
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnGravarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGravarKeyPressed
        
    }//GEN-LAST:event_btnGravarKeyPressed

    private void edtNomeEstadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtNomeEstadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!edtNomeEstado.getText().equals("")) {
                //btnAdicionarSubGrupo.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Informe um Nome!");
                edtNomeEstado.requestFocus();
            }
        }
    }//GEN-LAST:event_edtNomeEstadoKeyPressed

    private void TabelaMarkupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaMarkupMouseClicked
        
        int linha = TabelaMarkup.getSelectedRow();
        if (linha >= 0) {
            String auxEstado = TabelaMarkup.getValueAt(linha, 0).toString();
            String auxTXICMSInterno = TabelaMarkup.getValueAt(linha, 1).toString();
            String auxTXICMSInterEstadual = TabelaMarkup.getValueAt(linha, 2).toString();

            cbEstado.setSelectedItem(auxEstado);
            edtTXICMSInterno.setText(auxTXICMSInterno);
            edtTXICMSInterEstadual.setText(auxTXICMSInterEstadual);

            ComboBoxEstado();
        }
    }//GEN-LAST:event_TabelaMarkupMouseClicked

    private void edtTXICMSInternoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtTXICMSInternoKeyPressed
        
    }//GEN-LAST:event_edtTXICMSInternoKeyPressed

    private void edtTXICMSInterEstadualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtTXICMSInterEstadualKeyPressed
        
    }//GEN-LAST:event_edtTXICMSInterEstadualKeyPressed

    private void btnAdicionarMarkupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarMarkupActionPerformed
        //AdicionaNoGrid();       
        adicionarAlterarGrid();
    }//GEN-LAST:event_btnAdicionarMarkupActionPerformed

    private void btnDeletarMarkupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarMarkupActionPerformed
        if (verificaGrid()) {
            if (verificaCampoParaDeletar()) {
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Deletar o registro selecionado?");
                if (resposta == JOptionPane.YES_OPTION) {

                    int Codigo = TabelaMarkup.getSelectedRow();
                    DefaultTableModel modelo = (DefaultTableModel) TabelaMarkup.getModel();
                    modelo.removeRow(Codigo);
                    cbEstado.setSelectedItem("");
                    edtNomeEstado.setText("");
                    edtTXICMSInterno.setText("");
                    edtTXICMSInterEstadual.setText("");
                    cbEstado.requestFocus();
                    ComboBoxEstado();
                } else {
                    JOptionPane.showMessageDialog(null, "Voce cancelou a exclusão do item!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um item clicando no grid!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Não existem itens á serem deletados!");
        }
    }//GEN-LAST:event_btnDeletarMarkupActionPerformed

    private void cbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEstadoActionPerformed
        
        ComboBoxEstado();
    }//GEN-LAST:event_cbEstadoActionPerformed

    private void TabelaMarkupMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaMarkupMousePressed
        
        int linha = TabelaMarkup.getSelectedRow();
        if (linha >= 0) {
            String auxEstado = TabelaMarkup.getValueAt(linha, 0).toString();
            String auxTXICMSInterno = TabelaMarkup.getValueAt(linha, 1).toString();
            String auxTXICMSInterEstadual = TabelaMarkup.getValueAt(linha, 2).toString();

            cbEstado.setSelectedItem(auxEstado);
            edtTXICMSInterno.setText(auxTXICMSInterno);
            edtTXICMSInterEstadual.setText(auxTXICMSInterEstadual);

            ComboBoxEstado();
        }
    }//GEN-LAST:event_TabelaMarkupMousePressed

    private void cbEstadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbEstadoMouseClicked
        
        //ComboBoxEstado();
    }//GEN-LAST:event_cbEstadoMouseClicked

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
            java.util.logging.Logger.getLogger(CadGrupoFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadGrupoFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadGrupoFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadGrupoFiscal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new CadGrupoFiscal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelaMarkup;
    private javax.swing.JButton btnAdicionarMarkup;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnDeletarMarkup;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox cbEstado;
    private javax.swing.JTextField edtCodigo;
    private javax.swing.JTextField edtNomeEstado;
    private javax.swing.JTextField edtNomeGrupoFiscal;
    private javax.swing.JTextField edtTXICMSInterEstadual;
    private javax.swing.JTextField edtTXICMSInterno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
