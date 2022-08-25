package Forca;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Cadastro extends javax.swing.JFrame {

    public Cadastro() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public void recarregar_vetores() {
        principal p = new principal();
        JOptionPane.showMessageDialog(null, "Jogo será recarregado!");
        p.reiniciar_jogo();
    }

    public void gravar_palavra_e_dica() {
        //Instancia a classe que le e grava arquivos do Java
        BufferedWriter arquivo;
        String valores = edtpalavra.getText() + "#" + edtdica.getText();
        try {
            // abre o arquivo de texto para acrescentar linhas
            arquivo = new BufferedWriter(new FileWriter("Palavras.txt", true));
            // escreve a linha de texto
            arquivo.write(valores);
            // insere uma quebra de linha
            arquivo.newLine();
            // força a gravação dos dados em disco
            arquivo.flush();
            arquivo.close();
            edtpalavra.setText("");
            edtdica.setText("");

        } catch (IOException erro) {
            // trata o erro
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        edtpalavra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        edtdica = new javax.swing.JTextField();
        botao_Salvar = new javax.swing.JButton();
        botao_Sair = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro de Palavras e Dicas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 1, 11))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel1.setText("Insira a Palavra:");

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 11)); // NOI18N
        jLabel2.setText("Insira a Dica:");

        botao_Salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/forca/salvar.jpg"))); // NOI18N
        botao_Salvar.setText("Salvar");
        botao_Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_SalvarActionPerformed(evt);
            }
        });

        botao_Sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/forca/cancelar.jpg"))); // NOI18N
        botao_Sair.setText("Sair");
        botao_Sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botao_SairActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Forca/jogodaforca.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(botao_Salvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botao_Sair, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(edtpalavra, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                            .addComponent(edtdica)))
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtpalavra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtdica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botao_Salvar)
                    .addComponent(botao_Sair))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botao_SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_SalvarActionPerformed
        String auxtextoPalavra = edtpalavra.getText();
        String auxtextoDica = edtdica.getText();
        if (auxtextoPalavra.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor preencher a palavra!");
            edtpalavra.grabFocus();
        } else if (auxtextoDica.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor preencher a Dica!");
            edtdica.grabFocus();
        } else {
            gravar_palavra_e_dica();
            recarregar_vetores();
        }
    }//GEN-LAST:event_botao_SalvarActionPerformed

    private void botao_SairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botao_SairActionPerformed
        dispose();
    }//GEN-LAST:event_botao_SairActionPerformed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cadastro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botao_Sair;
    private javax.swing.JButton botao_Salvar;
    private javax.swing.JTextField edtdica;
    private javax.swing.JTextField edtpalavra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
