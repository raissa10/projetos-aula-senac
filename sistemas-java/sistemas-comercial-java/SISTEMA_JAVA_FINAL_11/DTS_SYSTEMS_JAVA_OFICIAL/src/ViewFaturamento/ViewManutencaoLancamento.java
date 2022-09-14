package ViewFaturamento;

import ControllerFaturamento.ControllerManutencaoLancamento;
import ControllerFaturamento.ControllerManutencaoTipoLancamento;
import static Estrutura.Mensagem.mensagemErro;
import Estrutura.ViewManutencaoPadrao;
import ModelFaturamento.ModelTipoLancamento;
import Principal.Conexao;
import ViewConsultasFaturamento.ViewConsultaTipoLancamento;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class ViewManutencaoLancamento extends ViewManutencaoPadrao {

    ControllerManutencaoLancamento controller = new ControllerManutencaoLancamento();
    ControllerManutencaoTipoLancamento controllerTipoLancamento = new ControllerManutencaoTipoLancamento();

    boolean verificaHabilitacaoCampos = false;
    
    /**
     * Creates new form CadCores
     */
    public ViewManutencaoLancamento() {
        initComponents();
        habilitaCampos(false);
        centralizaFormulario();
    }

    private void habilitaCampos(boolean habilita) {
        edtCodigo.requestFocus();
        edtCodigo.setEnabled(!habilita);
        edtDescricao.setEnabled(habilita);

        btnGravar.setEnabled(habilita);
        btnCancelar.setEnabled(habilita);
        btnConsulta.setEnabled(!habilita);
        btnExcluir.setEnabled(habilita);

        if (habilita) {
            edtCodigo.requestFocus();
            verificaHabilitacaoCampos = true;
        } else {
            //LimpaTela
            edtCodigo.setText("");
            edtDescricao.setText("");
            edtCodigo.grabFocus();
            verificaHabilitacaoCampos = false;
        }
    }

    private ModelTipoLancamento getModel(Integer codigo, String auxNome) {
        ModelTipoLancamento modelTipoLancamento = new ModelTipoLancamento(
                    codigo,
                    auxNome
            );
        return modelTipoLancamento;
    }
    
    private void gravarAlterar() {      
        String auxTexto = edtCodigo.getText();
        String auxNome = edtDescricao.getText();
        if (auxTexto.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Código da Cor!");
            edtCodigo.grabFocus();
        } else if (auxNome.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o nome da Cor!");
            edtDescricao.grabFocus();
        } else {
            int codigo = Integer.parseInt(auxTexto);
            ModelTipoLancamento model = this.getModel(codigo, auxNome);
            
            if (controller.existeRegistro(codigo)) {
                //Altera
                if (controller.alterar(model)) {
                    JOptionPane.showMessageDialog(null, "Registro alterado com sucesso!");
                    habilitaCampos(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possível alterar o registro!");
                    edtCodigo.grabFocus();
                }
            } else {
                if (controller.inserir(model)) {
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
            if (controller.excluir(auxCodigo)) {
                JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possivel excluir o registro!!");
            }
        }
        habilitaCampos(true);
    }

    private void validaCodigoNaoNulo() {
        if (controller.existeRegistro(Integer.parseInt(edtCodigo.getText()))) {
            habilitaCampos(true);
            ArrayList<ModelTipoLancamento> modelos = controller.listaRegistros(Integer.parseInt(edtCodigo.getText()));
            for (ModelTipoLancamento auxModel : modelos) {
                edtDescricao.setText(auxModel.getDs_tipo_lancamento());
            }
            edtDescricao.requestFocus();
        } else {
            String auxCodigoGenerator = "" + controller.validaCodigoGenerator("CD_TIPO_LANCAMENTO");
            edtCodigo.setText(auxCodigoGenerator);
            edtDescricao.requestFocus();
        }
    }
    
    public void comboBoxTipoLancamento(){
        if (verificaHabilitacaoCampos) {
            String sql = "select * from tipo_lancamento where ds_tipo_lancamento='" + jComboBoxTipoLancamento.getSelectedItem() + "'";
            try {
                Connection conn = Conexao.getConexao();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                rs.next();
                String auxnome = rs.getString("ds_tipo_lancamento");
                jComboBoxTipoLancamento.setSelectedItem(auxnome);
            } catch (SQLException ex) {
                mensagemErro("Registro nao encontrado!Erro na funcao ComboBox()" + ex.getMessage());
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        edtCodigo = new javax.swing.JTextField();
        edtDescricao = new javax.swing.JTextField();
        btnGravar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxTipoLancamento = new javax.swing.JComboBox<>();

        setTitle("Sistema Comercial DTS SYSTEMS");
        setPreferredSize(new java.awt.Dimension(450, 350));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Lançamento");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 140, 24));

        jLabel2.setText("Descrição:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        jLabel5.setText("Código:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        edtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoKeyPressed(evt);
            }
        });
        getContentPane().add(edtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 80, 25));

        edtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtDescricaoKeyPressed(evt);
            }
        });
        getContentPane().add(edtDescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 340, 25));

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
        getContentPane().add(btnGravar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 120, 40));

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
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 90, 120, 40));

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
        getContentPane().add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 120, 40));

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
        getContentPane().add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 190, 120, 40));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        getContentPane().add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 240, 120, 40));

        jLabel3.setText("Descrição:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        jComboBoxTipoLancamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxTipoLancamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTipoLancamentoActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBoxTipoLancamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 340, -1));

        setBounds(0, 0, 686, 350);
    }// </editor-fold>//GEN-END:initComponents

    private void edtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoKeyPressed
        String auxTexto = edtCodigo.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                habilitaCampos(true);
                String auxCodigoGenerator = "" + controller.validaCodigoGenerator("CD_TIPO_LANCAMENTO");
                edtCodigo.setText(auxCodigoGenerator);
                
                edtDescricao.requestFocus();
                
                jComboBoxTipoLancamento.setModel(controllerTipoLancamento.getComboDados());
            } else {
                validaCodigoNaoNulo();
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
            edtCodigo.grabFocus();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGravarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGravarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnCancelar.grabFocus();
        }
    }//GEN-LAST:event_btnGravarKeyPressed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente gravar o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            gravarAlterar();
        }
    }//GEN-LAST:event_btnGravarActionPerformed

    private void edtDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtDescricaoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnGravar.grabFocus();
        }
    }//GEN-LAST:event_edtDescricaoKeyPressed

    private void btnConsultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSair.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaKeyPressed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        ViewConsultaTipoLancamento form = new ViewConsultaTipoLancamento(edtCodigo);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigo.grabFocus();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void jComboBoxTipoLancamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTipoLancamentoActionPerformed
        comboBoxTipoLancamento();
    }//GEN-LAST:event_jComboBoxTipoLancamentoActionPerformed
    
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
            java.util.logging.Logger.getLogger(ViewManutencaoLancamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewManutencaoLancamento().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JTextField edtCodigo;
    private javax.swing.JTextField edtDescricao;
    private javax.swing.JComboBox<String> jComboBoxTipoLancamento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
