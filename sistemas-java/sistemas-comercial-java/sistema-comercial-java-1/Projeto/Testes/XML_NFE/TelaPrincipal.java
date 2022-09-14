package Testes.XML_NFE;

import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alunos
 */
public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TesteFrame
     */
    public TelaPrincipal() {
        initComponents();
        atualizarGrade();
        CampoCod.setVisible(false);
    }
    Mensagem msg = new Mensagem();
    Criptografia ctf = new Criptografia();
    Mensagem msgaux = new Mensagem();
    Criptografia ctfaux = new Criptografia();
    ListMensagem listMsg = ManipXML.lerXMLMensagem();
    ListCriptografia listCrip = ManipXML.lerXMLCriptografia();

    int codRem = 0;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Painel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Grade = new javax.swing.JTable();
        CampoCri = new javax.swing.JTextField();
        BotaoSai = new javax.swing.JButton();
        Titulo = new javax.swing.JLabel();
        CampoTit = new javax.swing.JTextField();
        Chave = new javax.swing.JLabel();
        CampoCha = new javax.swing.JTextField();
        Texto = new javax.swing.JLabel();
        Crip = new javax.swing.JLabel();
        BotaoCri = new javax.swing.JButton();
        BotaoDec = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        CampoTex = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        BotaoAlt = new javax.swing.JButton();
        BotaoRem = new javax.swing.JButton();
        BotaoLim = new javax.swing.JButton();
        CampoCod = new javax.swing.JTextField();
        btnAtualizarGrade = new javax.swing.JButton();
        BotaoRemTodos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        Painel.setBackground(new java.awt.Color(216, 216, 245));
        Painel.setForeground(new java.awt.Color(102, 102, 102));
        Painel.setToolTipText("");
        Painel.setAutoscrolls(true);
        Painel.setName(""); // NOI18N
        Painel.setPreferredSize(new java.awt.Dimension(777, 327));
        Painel.setVerifyInputWhenFocusTarget(false);

        Grade.setAutoCreateRowSorter(true);
        Grade.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Título", "Chave", "Texto", "Criptografia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Grade.setToolTipText("");
        Grade.setGridColor(new java.awt.Color(153, 153, 153));
        Grade.setInheritsPopupMenu(true);
        Grade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GradeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Grade);
        if (Grade.getColumnModel().getColumnCount() > 0) {
            Grade.getColumnModel().getColumn(3).setMinWidth(200);
            Grade.getColumnModel().getColumn(4).setMinWidth(200);
        }

        BotaoSai.setText("Sair");
        BotaoSai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotaoSai.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        BotaoSai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoSaiActionPerformed(evt);
            }
        });

        Titulo.setText("Título:");

        CampoTit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoTitActionPerformed(evt);
            }
        });
        CampoTit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CampoTitKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CampoTitKeyTyped(evt);
            }
        });

        Chave.setText("Chave:");

        CampoCha.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        CampoCha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoChaActionPerformed(evt);
            }
        });
        CampoCha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CampoChaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CampoChaKeyTyped(evt);
            }
        });

        Texto.setText("Texto:");

        Crip.setText("Criptografia: ");

        BotaoCri.setText("Criptografar");
        BotaoCri.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotaoCri.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        BotaoCri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoCriActionPerformed(evt);
            }
        });

        BotaoDec.setText("Decriptografar");
        BotaoDec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotaoDec.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        BotaoDec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoDecActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        CampoTex.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CampoTexKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SISTEMA DE CRIPTOGRAFIA");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        BotaoAlt.setText("Alterar");
        BotaoAlt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotaoAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                BotaoAltFocusLost(evt);
            }
        });
        BotaoAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoAltActionPerformed(evt);
            }
        });

        BotaoRem.setText("Remover");
        BotaoRem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotaoRem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoRemActionPerformed(evt);
            }
        });

        BotaoLim.setText("Limpar");
        BotaoLim.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotaoLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoLimActionPerformed(evt);
            }
        });

        CampoCod.setEditable(false);
        CampoCod.setBackground(new java.awt.Color(204, 204, 204));

        btnAtualizarGrade.setText("Atualizar Grade");
        btnAtualizarGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarGradeActionPerformed(evt);
            }
        });

        BotaoRemTodos.setText("Remover Todos");
        BotaoRemTodos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BotaoRemTodos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                BotaoRemTodosStateChanged(evt);
            }
        });
        BotaoRemTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoRemTodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelLayout = new javax.swing.GroupLayout(Painel);
        Painel.setLayout(PainelLayout);
        PainelLayout.setHorizontalGroup(
            PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelLayout.createSequentialGroup()
                        .addComponent(BotaoAlt)
                        .addGap(18, 18, 18)
                        .addComponent(BotaoRem)
                        .addGap(18, 18, 18)
                        .addComponent(BotaoRemTodos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotaoSai))
                    .addComponent(jScrollPane2)
                    .addGroup(PainelLayout.createSequentialGroup()
                        .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAtualizarGrade)
                            .addGroup(PainelLayout.createSequentialGroup()
                                .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Crip)
                                    .addComponent(Texto)
                                    .addComponent(Chave)
                                    .addComponent(Titulo))
                                .addGap(18, 18, 18)
                                .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PainelLayout.createSequentialGroup()
                                        .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(CampoCha, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(CampoTit, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(454, 454, 454))
                                    .addComponent(CampoTex, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(CampoCri, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(PainelLayout.createSequentialGroup()
                                        .addComponent(BotaoLim)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(BotaoCri)
                                        .addGap(18, 18, 18)
                                        .addComponent(BotaoDec)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnSalvar)
                                        .addGap(149, 149, 149))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CampoCod, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)))
                .addContainerGap())
        );
        PainelLayout.setVerticalGroup(
            PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CampoCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PainelLayout.createSequentialGroup()
                        .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Titulo)
                            .addComponent(CampoTit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CampoCha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Chave))
                        .addGap(18, 18, 18)
                        .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Texto)
                            .addComponent(CampoTex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CampoCri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Crip))
                        .addGap(20, 20, 20)
                        .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSalvar)
                            .addComponent(BotaoDec)
                            .addComponent(BotaoCri)
                            .addComponent(BotaoLim))))
                .addGap(30, 30, 30)
                .addComponent(btnAtualizarGrade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addGap(8, 8, 8)
                .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelLayout.createSequentialGroup()
                        .addComponent(BotaoSai)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelLayout.createSequentialGroup()
                        .addGroup(PainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BotaoAlt)
                            .addComponent(BotaoRem)
                            .addComponent(BotaoRemTodos))
                        .addGap(30, 30, 30))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Painel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(Painel, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void CampoChaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoChaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoChaActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (!CampoTit.getText().isEmpty() && !CampoCha.getText().isEmpty()
                && !CampoTex.getText().isEmpty() && !CampoCri.getText().isEmpty()) {

            ctfaux = new Criptografia();
            msgaux = new Mensagem();

            msgaux.setTitulo(CampoTit.getText());
            msgaux.setChave(CampoCha.getText());
            msgaux.setTexto(CampoTex.getText());

            if (CampoCod.getText().isEmpty()) {
                int codMsg = listMsg.retornaCodigo();
                msgaux.setCodigo(codMsg);
                ctfaux.setMensagem(msgaux);
                ctfaux.setCriptografia(CampoCri.getText());

                listMsg.incluirBuscandoCodigo(msgaux);
                listCrip.incluirBuscandoCodigo(ctfaux);
            } else {
                int codCrip = Integer.parseInt(CampoCod.getText());
                int codMsg = listCrip.buscaCodigoMensagemDaCrip(codCrip);
                msgaux.setCodigo(codMsg);
                ctfaux.setCodigo(codCrip);
                ctfaux.setMensagem(msgaux);
                ctfaux.setCriptografia(CampoCri.getText());
                int posicaoCrip = listCrip.RetornaPosicaoCriptografia(codCrip);
                int posicaoMsg = listMsg.RetornaPosicaoMensagem(codMsg);

                if (posicaoMsg > -1 && posicaoCrip > -1) {
                    listMsg.alterarMensagem(posicaoMsg, msgaux);
                    listCrip.alterarCriptografia(posicaoCrip, ctfaux);
                } else {
                    mensagemErro("Houve um problema ao encontrar a mensagem no XML!");
                }
            }

            atualizarGrade();
            limparCampos();
        } else {
            if (CampoTit.getText().isEmpty()) {
                mensagemErro("Você deve preencher o título!");
            } else if (CampoCha.getText().isEmpty()) {
                mensagemErro("Você deve preencher a chave!");
            } else if (CampoTex.getText().isEmpty()) {
                mensagemErro("Você deve preencher o texto!");
            } else {
                mensagemErro("Você deve preencher a criptografia!");
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void BotaoCriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoCriActionPerformed
        try {
            if (!CampoTex.getText().isEmpty() && !CampoCha.getText().isEmpty()) {
                msg.setTexto(CampoTex.getText());
                msg.setChave(CampoCha.getText());
                ctf.criptografarMensagem(msg);
                CampoCri.setText(ctf.getCriptografia());

            } else {
                if (CampoTex.getText().isEmpty()) {
                    mensagemErro("Você deve preencher o texto!");
                } else {
                    mensagemErro("Você deve preencher a chave!");
                }
            }
        } catch (NumberFormatException e) {
            mensagemErro("Chave inválida!");
        }
    }//GEN-LAST:event_BotaoCriActionPerformed

    private void BotaoDecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoDecActionPerformed
        try {
            if (!CampoCri.getText().isEmpty() && !CampoCha.getText().isEmpty()) {
                ctf.setCriptografia(CampoCri.getText());
                ctf.setMensagem(msg);
                msg.setChave(CampoCha.getText());
                CampoTex.setText(ctf.decriptografarMensagem(ctf));
            } else {
                if (CampoCri.getText().isEmpty()) {
                    mensagemErro("Você deve preencher a criptografia!");
                } else {
                    mensagemErro("Você deve preencher a chave!");
                }
            }
        } catch (NumberFormatException e) {
            mensagemErro("Chave inválida!");
        }

    }//GEN-LAST:event_BotaoDecActionPerformed

    private void BotaoSaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoSaiActionPerformed
        ManipXML.gravarXMLCriptografia(listCrip.getListCriptografia());
        System.exit(0);
    }//GEN-LAST:event_BotaoSaiActionPerformed

    private void BotaoLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoLimActionPerformed
        limparCampos();
    }//GEN-LAST:event_BotaoLimActionPerformed

    private void BotaoRemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoRemActionPerformed
        if (verificaLinha()) {
            codRem = Grade.getSelectedRow();
            removeGrade((int) Grade.getValueAt(codRem, 0));
            limparCampos();
            atualizarGrade();
        } else {
            mensagemErro("Você deve selecionar uma linha!");
        }
    }//GEN-LAST:event_BotaoRemActionPerformed

    private void BotaoAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoAltActionPerformed
        if (verificaLinha()) {
            aoClicarAlterar();
        } else {
            mensagemErro("Você deve selecionar uma linha!");
        }
    }//GEN-LAST:event_BotaoAltActionPerformed

    private void btnAtualizarGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarGradeActionPerformed
        atualizarGrade();

    }//GEN-LAST:event_btnAtualizarGradeActionPerformed

    private void GradeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GradeMouseClicked
        if (evt.getClickCount() == 2) {
            if (verificaLinha()) {
                aoClicarAlterar();
            } else {
                mensagemErro("Você deve selecionar uma linha!");
            }
        }

    }//GEN-LAST:event_GradeMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        ManipXML.gravarXMLCriptografia(listCrip.getListCriptografia());
    }//GEN-LAST:event_formWindowClosing

    private void BotaoAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BotaoAltFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoAltFocusLost

    private void CampoTitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoTitActionPerformed

    }//GEN-LAST:event_CampoTitActionPerformed

    private void CampoTitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoTitKeyTyped

    }//GEN-LAST:event_CampoTitKeyTyped

    private void CampoTitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoTitKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            CampoCha.requestFocus();
        }
    }//GEN-LAST:event_CampoTitKeyPressed

    private void CampoChaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoChaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            CampoTex.requestFocus();
        }
    }//GEN-LAST:event_CampoChaKeyPressed

    private void CampoTexKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoTexKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            CampoCri.requestFocus();
        }
    }//GEN-LAST:event_CampoTexKeyPressed

    private void CampoChaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoChaKeyTyped
        String caracteres = "0987654321";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_CampoChaKeyTyped

    private void BotaoRemTodosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_BotaoRemTodosStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_BotaoRemTodosStateChanged

    private void BotaoRemTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoRemTodosActionPerformed
        for (int i = 0; i < Grade.getRowCount(); i++) {
            removeGrade((int) Grade.getValueAt(i, 0));
        }
        limparCampos();
        atualizarGrade();
    }//GEN-LAST:event_BotaoRemTodosActionPerformed

    /**
     * @param args the command line arguments
     */
    private void limparCampos() {
        CampoCod.setText("");
        CampoTit.setText("");
        CampoCha.setText("");
        CampoTex.setText("");
        CampoCri.setText("");
    }

    private void atualizarGrade() {

        javax.swing.table.DefaultTableModel modelo = (DefaultTableModel) Grade.getModel();

        Object[] obj = new Object[Grade.getColumnCount()];

        int tamanhoGrade = modelo.getRowCount();
        for (int x = 0; x < tamanhoGrade; x++) {
            modelo.removeRow(0);
        }

        for (int i = 0; i < listCrip.listCriptografia.size(); i++) {
            modelo.addRow(obj);
            Grade.setValueAt(listCrip.listCriptografia.get(i).getCodigo(), i, 0);
            Grade.setValueAt(listCrip.listCriptografia.get(i).getMensagem().getTitulo(), i, 1);
            Grade.setValueAt(listCrip.listCriptografia.get(i).getMensagem().getChave(), i, 2);
            Grade.setValueAt(listCrip.listCriptografia.get(i).getMensagem().getTexto(), i, 3);
            Grade.setValueAt(listCrip.listCriptografia.get(i).getCriptografia(), i, 4);
        }

    }

    private void aoClicarAlterar() {
        codRem = Grade.getSelectedRow();
        int CodigoCriptografia = (int) Grade.getValueAt(codRem, 0);

        Criptografia criptografia = new Criptografia();
        Mensagem mensagem = new Mensagem();
        criptografia = listCrip.RetornaCriptografia(CodigoCriptografia);
        mensagem = criptografia.getMensagem();

        CampoCod.setText(String.valueOf(criptografia.getCodigo()));
        CampoTit.setText(mensagem.getTitulo());
        CampoCha.setText(mensagem.getChave());
        CampoTex.setText(mensagem.getTexto());
        CampoCri.setText(criptografia.getCriptografia());
    }

    private boolean verificaLinha() {
        int x = Grade.getSelectedRowCount();
        return x > 0;
    }

    private void removeGrade(int CodigoCriptografia) {
        Criptografia criptografia = new Criptografia();
        Mensagem mensagem = new Mensagem();
        criptografia = listCrip.RetornaCriptografia(CodigoCriptografia);
        mensagem = criptografia.getMensagem();

        listCrip.removerPorCodigo(criptografia);
        listMsg.removerPorCodigo(mensagem);
    }

    public static void mensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro!", JOptionPane.ERROR_MESSAGE);
    }

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotaoAlt;
    private javax.swing.JButton BotaoCri;
    private javax.swing.JButton BotaoDec;
    private javax.swing.JButton BotaoLim;
    private javax.swing.JButton BotaoRem;
    private javax.swing.JButton BotaoRemTodos;
    private javax.swing.JButton BotaoSai;
    private javax.swing.JTextField CampoCha;
    private javax.swing.JTextField CampoCod;
    private javax.swing.JTextField CampoCri;
    private javax.swing.JTextField CampoTex;
    private javax.swing.JTextField CampoTit;
    private javax.swing.JLabel Chave;
    private javax.swing.JLabel Crip;
    private javax.swing.JTable Grade;
    private javax.swing.JPanel Painel;
    private javax.swing.JLabel Texto;
    private javax.swing.JLabel Titulo;
    private javax.swing.JButton btnAtualizarGrade;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
