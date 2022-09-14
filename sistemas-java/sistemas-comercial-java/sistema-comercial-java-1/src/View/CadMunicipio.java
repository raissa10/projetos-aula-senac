package View;

import Controller.MunicipioDB;
import Controller.EstadoDB;
import Model.Municipio;
import Estrutura.Conexao;
import Estrutura.TelaPadraoGlobal;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * @author Gelvazio Camargo
 */
public class CadMunicipio extends TelaPadraoGlobal {

    private static final String sqlBuscaCidade = "SELECT * FROM municipio WHERE cd_estado=? and cd_municipio=?";

    public CadMunicipio() {
        initComponents();
        centro();
        edtCodigo.grabFocus();
        habilitaCampos(false);
        EstadoDB estadodb = new EstadoDB();
        cbEstado.setModel(estadodb.getComboEstado());

    }

    private void habilitaCampos(boolean habilita) {
        edtCodigo.setEnabled(!habilita);
        cbEstado.setEnabled(!habilita);
        edtDescricao.setEnabled(habilita);
        edtCEP.setEnabled(habilita);
        edtIBGE.setEnabled(habilita);
        edtCodigo.requestFocus();

        btnGravar.setEnabled(habilita);
        btnCancelar.setEnabled(habilita);
        btnConsulta.setEnabled(!habilita);
        btnExcluir.setEnabled(habilita);
        if (habilita) {
            edtDescricao.requestFocus();
        } else {
            LimpaTela();
        }
    }

    private void LimpaTela() {
        edtCodigo.setText("");
        edtDescricao.setText("");
        edtCEP.setText("");
        edtIBGE.setText("");
        edtCodigo.requestFocus();
    }

    private void Excluir_Registro() {
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            MunicipioDB cidadedb = new MunicipioDB();
            String auxEstado = cbEstado.getSelectedItem().toString();
            int auxCodigo = Integer.parseInt(edtCodigo.getText());
            if (cidadedb.excluirCidade(auxEstado, auxCodigo)) {
                JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!");
                habilitaCampos(false);
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possivel excluir o registro!!");
            }
        }
    }

    private void Gravar_Registro() {
        String auxcd_estado = cbEstado.getSelectedItem().toString();
        String auxTexto = edtCodigo.getText();
        String auxds_municipio = edtDescricao.getText();
        String auxcd_cep = edtCEP.getText();
        int auxcd_ibge = Integer.parseInt(edtIBGE.getText());
        int auxcd_municipio = Integer.parseInt(auxTexto);
        //Será implementado depois!
        int auxcd_usuario = 1;
        int auxcd_codigo_tom = 1;
        int auxcd_filial = 1;

        MunicipioDB cidadedb = new MunicipioDB();
        Municipio cidade = new Municipio(
                auxcd_estado,
                auxcd_municipio,
                auxds_municipio,
                auxcd_ibge,
                auxcd_cep,
                auxcd_usuario,
                //auxcd_codigo_tom,
                auxcd_filial);
        if (cidadedb.inserirCidade(cidade)) {
            JOptionPane.showMessageDialog(null, "Registro incluído com sucesso!");
            LimpaTela();
            habilitaCampos(false);
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir o registro!");
            edtCodigo.grabFocus();
        }
    }

    private void Alterar_Registro() {
        String auxcd_estado = cbEstado.getSelectedItem().toString();
        String auxTexto = edtCodigo.getText();
        String auxds_municipio = edtDescricao.getText();
        String auxcd_cep = edtCEP.getText().toString();
        int auxcd_ibge = Integer.parseInt(edtIBGE.getText());
        int auxcd_municipio = Integer.parseInt(auxTexto);
        //Será implementado depois!
        int auxcd_usuario = 1;
        int auxcd_codigo_tom = 1;
        int auxcd_filial = 1;
        String a = null;
        a = "0" + auxcd_cep;
        MunicipioDB cidadedb = new MunicipioDB();
        Municipio cidade = new Municipio(
                auxcd_estado,
                auxcd_municipio,
                auxds_municipio,
                auxcd_ibge,
                a,
                auxcd_usuario,
                //auxcd_codigo_tom,
                auxcd_filial);
        if (cidadedb.alterarCidade(cidade)) {
            JOptionPane.showMessageDialog(null, "Registro alterado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o registro!");
        }
        LimpaTela();
        habilitaCampos(false);
    }

    private void validaCodigoNaoNulo() {
        String auxEstado = cbEstado.getSelectedItem().toString();
        String auxTexto = edtCodigo.getText();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        MunicipioDB cidadedb = new MunicipioDB();
        int cd_cidade = Integer.parseInt(auxTexto);
        if (cidadedb.getCidade(auxEstado, cd_cidade)) {
            habilitaCampos(true);
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaCidade);
                pstmt.setString(1, auxEstado);
                pstmt.setInt(2, cd_cidade);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxds_municipio = rs.getString("ds_municipio");
                    int auxCEP = rs.getInt("cd_cep");
                    String aCEP;
                    aCEP = "" + auxCEP;
                    int auxIBGE = rs.getInt("cd_ibge");
                    String aIBGE;
                    aIBGE = "" + auxIBGE;
                    edtDescricao.setText(auxds_municipio);
                    edtCEP.setText(aCEP);
                    edtIBGE.setText(aIBGE);
                    edtDescricao.grabFocus();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro de conexão! " + erro);
            }
        } else {
            int resposta = JOptionPane.showConfirmDialog(null, "Cidade Não Cadastrada! \n Deseja cadastrar o registro?");
            if (resposta == JOptionPane.YES_OPTION) {
                habilitaCampos(true);
            } else {
                habilitaCampos(false);
            }
        }
    }

    private void Gravar_Completo_Validado() {
        String auxTexto = edtCodigo.getText();
        String auxDescricao = edtDescricao.getText();
        String auxtCEP = edtCEP.getText();
        String auxIBGE = edtIBGE.getText();
        if (auxTexto.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Código da Cidade!");
            edtCodigo.grabFocus();
        } else if (auxDescricao.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o nome da cidade!");
            edtDescricao.grabFocus();
        } else if (auxtCEP.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o CEP da cidade!");
            edtCEP.grabFocus();
        } else if (auxIBGE.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o IBGE da cidade!!");
            edtIBGE.grabFocus();
        } else {
            Gravar_Alterar_Registro();
        }
    }

    private void Gravar_Alterar_Registro() {
        MunicipioDB cidadedb = new MunicipioDB();
        String auxEstado = cbEstado.getSelectedItem().toString();
        int auxCodigo = Integer.parseInt(edtCodigo.getText());
        if (cidadedb.getCidade(auxEstado, auxCodigo)) {
            Alterar_Registro();
        } else {
            Gravar_Registro();
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
        btnExcluir = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        edtIBGE = new javax.swing.JTextField();
        edtCEP = new javax.swing.JTextField();
        edtDescricao = new javax.swing.JTextField();
        edtCodigo = new javax.swing.JTextField();
        cbEstado = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Cadastro de Municípios");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 270, 39));

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
        jPanel1.add(btnGravar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 120, -1));

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
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 120, -1));

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
        jPanel1.add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 120, -1));

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
        jPanel1.add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 120, -1));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        jPanel1.add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 120, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, 150, 270));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        edtIBGE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtIBGEKeyPressed(evt);
            }
        });
        jPanel2.add(edtIBGE, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 100, -1));

        edtCEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCEPKeyPressed(evt);
            }
        });
        jPanel2.add(edtCEP, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 100, -1));

        edtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtDescricaoKeyPressed(evt);
            }
        });
        jPanel2.add(edtDescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 270, -1));

        edtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoKeyPressed(evt);
            }
        });
        jPanel2.add(edtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 60, -1));

        cbEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbEstado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbEstadoKeyPressed(evt);
            }
        });
        jPanel2.add(cbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 79, -1));

        jLabel7.setText("Código:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel9.setText("Estado:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel2.setText("Nome:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabel8.setText("CEP:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jLabel11.setText("Cod.ibge:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 360, 220));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 560, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void edtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoKeyPressed
        String auxTexto = edtCodigo.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                JOptionPane.showMessageDialog(null, "Digite um codigo de cidade valido!");
            } else {
                validaCodigoNaoNulo();
            }
        }
    }//GEN-LAST:event_edtCodigoKeyPressed

    private void edtDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtDescricaoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtCEP.grabFocus();
        }
    }//GEN-LAST:event_edtDescricaoKeyPressed

    private void edtCEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCEPKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtIBGE.grabFocus();
        }
    }//GEN-LAST:event_edtCEPKeyPressed

    private void edtIBGEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtIBGEKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnGravar.grabFocus();
        }
    }//GEN-LAST:event_edtIBGEKeyPressed

    private void cbEstadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbEstadoKeyPressed
        

    }//GEN-LAST:event_cbEstadoKeyPressed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente gravar o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            Gravar_Completo_Validado();
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
        
        Excluir_Registro();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExcluirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExcluirKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnConsulta.grabFocus();
        }
    }//GEN-LAST:event_btnExcluirKeyPressed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        ConsultaMunicipio form = new ConsultaMunicipio(edtCodigo, cbEstado);
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
            java.util.logging.Logger.getLogger(CadMunicipio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadMunicipio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadMunicipio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadMunicipio.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new CadMunicipio().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox cbEstado;
    private javax.swing.JTextField edtCEP;
    private javax.swing.JTextField edtCodigo;
    private javax.swing.JTextField edtDescricao;
    private javax.swing.JTextField edtIBGE;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
