package View;

import Controller.TipoCobrancaDB;
import Model.TipoCobranca;
import Estrutura.TelaPadraoGlobal;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class CadTipoCobranca extends TelaPadraoGlobal {

    TipoCobrancaDB tipocobrancadb = new TipoCobrancaDB();

    public CadTipoCobranca() {
        initComponents();
        centro();
        habilitaCampos(false);
        btnExcluir.setEnabled(false);
    }

    public void habilitaCampos(boolean habilita) {
        edtCodigo.requestFocus();
        edtCodigo.setEnabled(!habilita);
        edtDescricao.setEnabled(habilita);
        //RadioButtons da Tela
        RadioAtivo.setEnabled(habilita);
        RadioCreditoImediato.setEnabled(habilita);
        RadioCheque.setEnabled(habilita);
        RadioBoleto.setEnabled(habilita);
        RadioCrediario.setEnabled(habilita);
        RadioCartao.setEnabled(habilita);
        RadioQuitaQuandogera.setEnabled(habilita);
        //PanelCheckBox.setEnabled(habilita);        

        btnGravar.setEnabled(habilita);
        btnCancelar.setEnabled(habilita);
        btnConsulta.setEnabled(!habilita);
        //btnExcluir.setEnabled(habilita);

        if (habilita) {
            edtCodigo.requestFocus();
        } else {
            edtCodigo.setText("");
            edtDescricao.setText("");
            edtCodigo.grabFocus();
        }
    }

    private void gravarAlterar() {
        String auxTexto = edtCodigo.getText();
        String auxNome = edtDescricao.getText();

        //Declara as variaveis da tela
        int fg_credito_imediato = 0;
        int fg_cheque = 0;
        int fg_boleto = 0;
        int fg_crediario = 0;
        int fg_cartao = 0;
        int fg_quita_quando_gera = 0;
        int fg_ativo = 0;

        if (auxTexto.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Código!");
            edtCodigo.grabFocus();
        } else if (auxNome.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o nome do tipo de cobrança!");
            edtDescricao.grabFocus();
        } else {

            if (RadioCreditoImediato.isSelected()) {
                fg_credito_imediato = 1;
            } else {
                fg_credito_imediato = 0;
            }
            if (RadioCheque.isSelected()) {
                fg_cheque = 1;
            } else {
                fg_cheque = 0;
            }
            if (RadioBoleto.isSelected()) {
                fg_boleto = 1;
            } else {
                fg_boleto = 0;
            }
            if (RadioCrediario.isSelected()) {
                fg_crediario = 1;
            } else {
                fg_crediario = 0;
            }
            if (RadioCartao.isSelected()) {
                fg_cartao = 1;
            } else {
                fg_cartao = 0;
            }
            if (RadioQuitaQuandogera.isSelected()) {
                fg_quita_quando_gera = 1;
            } else {
                fg_quita_quando_gera = 0;
            }
            if (RadioAtivo.isSelected()) {
                fg_ativo = 1;
            } else {
                fg_ativo = 0;
            }

            int codigo = Integer.parseInt(auxTexto);
            int auxcd_usuario = 1;
            int auxcd_filial = 1;

            TipoCobranca tipocobranca = new TipoCobranca(
                    codigo,
                    auxNome,
                    fg_credito_imediato,
                    fg_cheque,
                    fg_boleto,
                    fg_crediario,
                    fg_cartao,
                    fg_quita_quando_gera,
                    fg_ativo,
                    auxcd_filial,
                    auxcd_usuario
            );

            if (tipocobrancadb.getTipoCobranca(codigo)) {
                //Altera
                if (tipocobrancadb.alterar(tipocobranca)) {
                    JOptionPane.showMessageDialog(null, "Registro alterado com sucesso!");
                    habilitaCampos(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possível alterar o registro!");
                    edtCodigo.grabFocus();
                }
            } else {
                //Insere
                if (tipocobrancadb.inserir(tipocobranca)) {
                    JOptionPane.showMessageDialog(null, "Registro incluído com sucesso!");
                    habilitaCampos(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possível incluir o registro!");
                    edtCodigo.grabFocus();
                }
            }
        }
    }

    private void ValidaCodigoNaoNulo() {
        int cd_cobranca = Integer.parseInt(edtCodigo.getText());

        if (tipocobrancadb.getTipoCobranca(cd_cobranca)) {
            habilitaCampos(true);
            ArrayList<TipoCobranca> tiposcobrancas = tipocobrancadb.listaTiposCobranca(cd_cobranca);
            for (TipoCobranca auxTipoCobranca : tiposcobrancas) {
                //Passa os dados aqui neste "for" de objetos 
                edtDescricao.setText(auxTipoCobranca.getDs_cobranca());

                //Aqui altera os checkbox da tela
                if (auxTipoCobranca.getFg_imediato() == 1) {
                    RadioCreditoImediato.setSelected(true);
                } else {
                    RadioCreditoImediato.setSelected(false);
                }
                if (auxTipoCobranca.getFg_cheque() == 1) {
                    RadioCheque.setSelected(true);
                } else {
                    RadioCheque.setSelected(false);
                }
                if (auxTipoCobranca.getFg_boleto() == 1) {
                    RadioBoleto.setSelected(true);
                } else {
                    RadioBoleto.setSelected(false);
                }
                if (auxTipoCobranca.getFg_crediario() == 1) {
                    RadioCrediario.setSelected(true);
                } else {
                    RadioCrediario.setSelected(false);
                }
                if (auxTipoCobranca.getFg_cartao() == 1) {
                    RadioCartao.setSelected(true);
                } else {
                    RadioCartao.setSelected(false);
                }
                if (auxTipoCobranca.getFg_quita_quando_gera() == 1) {
                    RadioQuitaQuandogera.setSelected(true);
                } else {
                    RadioQuitaQuandogera.setSelected(false);
                }
                if (auxTipoCobranca.getFg_ativo() == 1) {
                    RadioAtivo.setSelected(true);
                } else {
                    RadioAtivo.setSelected(false);
                }

            }
            edtDescricao.requestFocus();
        } else {
            //Passa o código do generator para o campo
            String auxCodigoGenerator = "" + tipocobrancadb.ValidaCodigoGenerator();
            edtCodigo.setText(auxCodigoGenerator);
            edtDescricao.requestFocus();
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
        btnGravar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        PanelCheckBox = new javax.swing.JPanel();
        RadioBoleto = new javax.swing.JRadioButton();
        RadioCheque = new javax.swing.JRadioButton();
        RadioCreditoImediato = new javax.swing.JRadioButton();
        RadioCrediario = new javax.swing.JRadioButton();
        RadioCartao = new javax.swing.JRadioButton();
        RadioQuitaQuandogera = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        edtCodigo = new javax.swing.JTextField();
        edtDescricao = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        RadioAtivo = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Tipo de Cobrança");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, -1, 28));

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Valid Green.png"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGravar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 120, 40));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 130, 120, 40));

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Remove Red.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        getContentPane().add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, 120, 40));

        btnConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Search.png"))); // NOI18N
        btnConsulta.setText("Consulta");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });
        getContentPane().add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 180, 120, 40));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        getContentPane().add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 280, 120, 40));

        PanelCheckBox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        RadioBoleto.setText("Boleto");
        RadioBoleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioBoletoActionPerformed(evt);
            }
        });

        RadioCheque.setText("Cheque");
        RadioCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioChequeActionPerformed(evt);
            }
        });

        RadioCreditoImediato.setText("Crédito Imediato");
        RadioCreditoImediato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioCreditoImediatoActionPerformed(evt);
            }
        });

        RadioCrediario.setText("Crediário");
        RadioCrediario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioCrediarioActionPerformed(evt);
            }
        });

        RadioCartao.setText("Cartão");
        RadioCartao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioCartaoActionPerformed(evt);
            }
        });

        RadioQuitaQuandogera.setText("Quita Quando Gera");
        RadioQuitaQuandogera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioQuitaQuandogeraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelCheckBoxLayout = new javax.swing.GroupLayout(PanelCheckBox);
        PanelCheckBox.setLayout(PanelCheckBoxLayout);
        PanelCheckBoxLayout.setHorizontalGroup(
            PanelCheckBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCheckBoxLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addGroup(PanelCheckBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelCheckBoxLayout.createSequentialGroup()
                        .addComponent(RadioCreditoImediato, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(RadioCrediario, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelCheckBoxLayout.createSequentialGroup()
                        .addComponent(RadioCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(100, 100, 100)
                        .addComponent(RadioCartao))
                    .addGroup(PanelCheckBoxLayout.createSequentialGroup()
                        .addComponent(RadioBoleto, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120)
                        .addComponent(RadioQuitaQuandogera, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56))
        );
        PanelCheckBoxLayout.setVerticalGroup(
            PanelCheckBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCheckBoxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelCheckBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RadioCreditoImediato)
                    .addComponent(RadioCrediario))
                .addGap(17, 17, 17)
                .addGroup(PanelCheckBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RadioCheque)
                    .addComponent(RadioCartao))
                .addGap(17, 17, 17)
                .addGroup(PanelCheckBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RadioBoleto)
                    .addComponent(RadioQuitaQuandogera))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        getContentPane().add(PanelCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 480, 130));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Código:");

        edtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoKeyPressed(evt);
            }
        });

        jLabel3.setText("Descrição:");

        RadioAtivo.setText("Ativo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addGap(13, 13, 13)
                        .addComponent(edtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RadioAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(edtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(edtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(RadioAtivo))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(edtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 480, 110));

        setBounds(0, 0, 705, 414);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        ConsultaTipoCobranca form = new ConsultaTipoCobranca(edtCodigo);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigo.grabFocus();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void edtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoKeyPressed
        
        String auxTexto = edtCodigo.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                habilitaCampos(true);
                //Passa o código do generator para o campo
                String auxCodigoGenerator = "" + tipocobrancadb.ValidaCodigoGenerator();
                edtCodigo.setText(auxCodigoGenerator);
                edtDescricao.requestFocus();
            } else {
                ValidaCodigoNaoNulo();
            }
        }
    }//GEN-LAST:event_edtCodigoKeyPressed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente gravar o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            gravarAlterar();
        }
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Cancelar a Edição?");
        if (resposta == JOptionPane.YES_OPTION) {
            habilitaCampos(false);
            edtCodigo.grabFocus();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void RadioCreditoImediatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioCreditoImediatoActionPerformed
        
        RadioBoleto.setSelected(false);
        RadioCartao.setSelected(false);
        RadioCheque.setSelected(false);
        RadioCrediario.setSelected(false);
        RadioQuitaQuandogera.setSelected(false);
    }//GEN-LAST:event_RadioCreditoImediatoActionPerformed

    private void RadioChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioChequeActionPerformed
        
        RadioCreditoImediato.setSelected(false);
    }//GEN-LAST:event_RadioChequeActionPerformed

    private void RadioBoletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioBoletoActionPerformed
        
        RadioCreditoImediato.setSelected(false);
    }//GEN-LAST:event_RadioBoletoActionPerformed

    private void RadioCrediarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioCrediarioActionPerformed
        
        RadioCreditoImediato.setSelected(false);
    }//GEN-LAST:event_RadioCrediarioActionPerformed

    private void RadioCartaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioCartaoActionPerformed
        
        RadioCreditoImediato.setSelected(false);
    }//GEN-LAST:event_RadioCartaoActionPerformed

    private void RadioQuitaQuandogeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioQuitaQuandogeraActionPerformed
        
        RadioCreditoImediato.setSelected(false);
    }//GEN-LAST:event_RadioQuitaQuandogeraActionPerformed

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
            java.util.logging.Logger.getLogger(CadTipoCobranca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadTipoCobranca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadTipoCobranca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadTipoCobranca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadTipoCobranca().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelCheckBox;
    private javax.swing.JRadioButton RadioAtivo;
    private javax.swing.JRadioButton RadioBoleto;
    private javax.swing.JRadioButton RadioCartao;
    private javax.swing.JRadioButton RadioCheque;
    private javax.swing.JRadioButton RadioCrediario;
    private javax.swing.JRadioButton RadioCreditoImediato;
    private javax.swing.JRadioButton RadioQuitaQuandogera;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JTextField edtCodigo;
    private javax.swing.JTextField edtDescricao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
