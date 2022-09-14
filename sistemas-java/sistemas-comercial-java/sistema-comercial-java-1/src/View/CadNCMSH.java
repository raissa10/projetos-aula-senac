package View;

import Controller.NCMSHDB;
import Model.NCMSH;
import Estrutura.TelaPadraoGlobal;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class CadNCMSH extends TelaPadraoGlobal {

    NCMSHDB ncmshdb = new NCMSHDB();

    public CadNCMSH() {
        initComponents();
        centro();
        habilitaCampos(false);
    }

    private void habilitaCampos(boolean habilita) {
        edtCodigo.requestFocus();
        edtCodigo.setEnabled(!habilita);

        edtCodNCMSH.setEnabled(habilita);
        edtVLMVA.setEnabled(habilita);
        edtDescricao1.setEnabled(habilita);
        edtDescricao2.setEnabled(habilita);
        edtDescricaoNCMSH.setEnabled(habilita);

        btnGravar.setEnabled(habilita);
        btnCancelar.setEnabled(habilita);
        btnConsulta.setEnabled(!habilita);
        btnExcluir.setEnabled(habilita);

        if (habilita) {
            edtCodNCMSH.requestFocus();
        } else {
            //LimpaTela
            edtCodigo.setText("");
            edtVLMVA.setText("");
            edtDescricao1.setText("");
            edtDescricao2.setText("");
            edtDescricaoNCMSH.setText("");
            edtCodNCMSH.setText("");
            edtCodigo.requestFocus();
        }
    }

    private void gravarAlterar() {
        String auxcd_codigo = edtCodigo.getText();
        String auxcd_ncmsh = edtCodNCMSH.getText();
        String ds_titulo_1 = edtDescricao1.getText();
        String ds_titulo_2 = edtDescricao2.getText();
        String ds_ncmsh = edtDescricaoNCMSH.getText();
        String auxvl_mva = edtVLMVA.getText();
        if (auxcd_ncmsh.equals("")) {
            mensagemErro("Favor Preencher o código do NCMSH!");
            edtCodNCMSH.grabFocus();
        } else if (auxvl_mva.equals("")) {
            mensagemErro("Favor Preencher o valor de MVA!");
            edtVLMVA.grabFocus();
        } else {
            //Conversao das variaveis da tela
            int cd_codigo = Integer.parseInt(auxcd_codigo);
            int vl_mva = Integer.parseInt(auxvl_mva);

            NCMSH ncmsh = new NCMSH(
                    cd_codigo,
                    auxcd_ncmsh,
                    ds_titulo_1,
                    ds_titulo_2,
                    ds_ncmsh,
                    vl_mva
            );

            if (ncmshdb.getNCMSH(auxcd_ncmsh)) {
                //Altera
                if (ncmshdb.alterarNCMSH(ncmsh)) {
                    JOptionPane.showMessageDialog(null, "Registro alterado com sucesso!");
                    habilitaCampos(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possível alterar o registro!");
                    edtCodigo.grabFocus();
                }
            } else {
                //Insere
                if (ncmshdb.inserirNCMSH(ncmsh)) {
                    JOptionPane.showMessageDialog(null, "Registro incluído com sucesso!");
                    habilitaCampos(false);
                } else {
                    mensagemErro("Não foi possível incluir o registro!");
                    edtCodigo.grabFocus();
                }
            }
        }
    }

    private void ExcluirRegistro() {
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            int auxCodigo = Integer.parseInt(edtCodigo.getText());
            if (ncmshdb.excluirNCMSH(auxCodigo)) {
                JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!");
                habilitaCampos(false);
            } else {
                mensagemErro("Não foi possivel excluir o registro!!");
            }
        }
    }

    private void ValidaCodigoNaoNulo() {

        int cd_codigo = Integer.parseInt(edtCodigo.getText());

        if (ncmshdb.getCodigoNCMSH(cd_codigo)) {
            habilitaCampos(true);

            //Aqui deve chamar o ArrayList 
            //Tendo como parâmetro o código do ncmsh
            ArrayList<NCMSH> ncmshs = ncmshdb.listaNCMSH(cd_codigo);
            for (NCMSH auxNCMSH : ncmshs) {
                //Passa os dados aqui neste "for" de objetos 
                edtCodNCMSH.setText(auxNCMSH.getCd_ncmsh());
                edtDescricao1.setText(auxNCMSH.getDs_titulo_1());
                edtDescricao2.setText(auxNCMSH.getDs_titulo_2());
                edtDescricaoNCMSH.setText(auxNCMSH.getDs_ncmsh());
                String auxvlmva = "" + auxNCMSH.getVl_mva();
                edtVLMVA.setText(auxvlmva);
            }
            edtDescricao1.requestFocus();
        } else {
            int resposta = JOptionPane.showConfirmDialog(null, "Não existe o NCMSH! \n Deseja Cadastrar?");
            if (resposta == JOptionPane.YES_OPTION) {
                habilitaCampos(true);
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
        jPanel1 = new javax.swing.JPanel();
        btnGravar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        edtVLMVA = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        edtDescricao1 = new javax.swing.JTextField();
        edtCodNCMSH = new javax.swing.JTextField();
        edtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        edtDescricao2 = new javax.swing.JTextField();
        edtDescricaoNCMSH = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Cadastro de NCMSH");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, -1, 28));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Valid Green.png"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
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
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 120, 40));

        btnConsulta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Search.png"))); // NOI18N
        btnConsulta.setText("Consulta");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });
        jPanel1.add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 120, 40));

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Remove Red.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        jPanel1.add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 120, 40));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        jPanel1.add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 120, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 160, 330));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(edtVLMVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 120, 25));

        jLabel4.setText("Valor de MVA:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, 100, -1));

        jLabel3.setText("Descrição 1:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));
        jPanel2.add(edtDescricao1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 390, 25));
        jPanel2.add(edtCodNCMSH, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 130, 25));

        edtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoKeyPressed(evt);
            }
        });
        jPanel2.add(edtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 80, 25));

        jLabel2.setText("Código:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel5.setText("Cód. NCMSH:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel6.setText("Descrição 2:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));
        jPanel2.add(edtDescricao2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 390, 25));
        jPanel2.add(edtDescricaoNCMSH, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 390, 25));

        jLabel7.setText("Descrição NCMSH:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 450, 330));

        setBounds(0, 0, 708, 424);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        
        ConsultaNCMSH form = new ConsultaNCMSH(edtCodigo);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigo.grabFocus();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        gravarAlterar();
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        ExcluirRegistro();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Cancelar a Edição?");
        if (resposta == JOptionPane.YES_OPTION) {
            habilitaCampos(false);
            edtCodigo.grabFocus();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void edtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoKeyPressed
        
        String auxTexto = edtCodigo.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                //Passa o código do generator para o campo
                String auxCodigoGenerator = "" + ncmshdb.ValidaCodigoGenerator();
                edtCodigo.setText(auxCodigoGenerator);
                edtCodNCMSH.requestFocus();
                habilitaCampos(true);
            } else {
                ValidaCodigoNaoNulo();
            }
        }
    }//GEN-LAST:event_edtCodigoKeyPressed

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
            java.util.logging.Logger.getLogger(CadNCMSH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadNCMSH().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JTextField edtCodNCMSH;
    private javax.swing.JTextField edtCodigo;
    private javax.swing.JTextField edtDescricao1;
    private javax.swing.JTextField edtDescricao2;
    private javax.swing.JTextField edtDescricaoNCMSH;
    private javax.swing.JTextField edtVLMVA;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
