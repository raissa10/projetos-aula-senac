package Estrutura;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

/**
 *
 * @author Gelvazio
 * @since 28/07/2019
 */
public abstract class ViewConsultaPadrao extends JInternalFrame {

    String SQLConsultaDados;
    JTextField campoCodigo;
    
    private String titulo;
    private ModelPadrao Model;    
    
    TelaPadraoGlobal m = new TelaPadraoGlobal();
    
    abstract protected void criaCampos();
    abstract protected void validaCampoPesquisa();
    
    public ViewConsultaPadrao(JTextField campoCodigo){        
        initComponents();        
        this.campoCodigo = campoCodigo;
        centralizaFormulario();
    }
    
    public void centralizaFormulario() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        // variavel largura com a largura da tela
        int screenWidth = screenSize.width - this.getWidth();
        // variavel largura com a altura da tela
        int screenHeigth = screenSize.height - this.getHeight();
        //Divide o tamanho da tela para ficar no centro
        setLocation(screenWidth / 2, screenHeigth / 2);
    }

    /**
     * Este método é chamado de dentro do construtor para inicializar o
     * formulário.    ATENÇÃO: Não modifique o código. O conteúdo deste método é
     * sempre     regenerados pelo editor de formulários.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbGrid = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbCampo = new javax.swing.JComboBox();
        cbValor = new javax.swing.JComboBox();
        edtPesquisa = new javax.swing.JTextField();
        btnPesquisa = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnCarregar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);

        tbGrid.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbGrid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbGridMouseClicked(evt);
            }
        });
        tbGrid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbGridKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbGrid);

        jLabel1.setText("Valor:");

        jLabel2.setText("Digite sua pesquisa ...");

        jLabel3.setText("Campo:");

        cbCampo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCampo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCampoActionPerformed(evt);
            }
        });

        cbValor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        edtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtPesquisaKeyPressed(evt);
            }
        });

        btnPesquisa.setText("Pesquisar");
        btnPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPesquisaMouseClicked(evt);
            }
        });

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnCarregar.setText("Carregar");
        btnCarregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCarregarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbValor, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 85, Short.MAX_VALUE))
                    .addComponent(edtPesquisa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPesquisa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCarregar)
                .addGap(36, 36, 36)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(edtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCarregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(3, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText(this.getTitulo());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(322, 322, 322)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(5, 5, 5)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbGridMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGridMouseClicked
        int linha = tbGrid.getSelectedRow();
        if (linha >= 0) {
            String auxCodigo = tbGrid.getValueAt(linha, 0).toString();
            campoCodigo.setText(auxCodigo);
        }
    }//GEN-LAST:event_tbGridMouseClicked

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void cbCampoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCampoActionPerformed
    }//GEN-LAST:event_cbCampoActionPerformed

    private void btnPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPesquisaMouseClicked
        this.validaCampoPesquisa();
    }//GEN-LAST:event_btnPesquisaMouseClicked

    private void edtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtPesquisaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.validaCampoPesquisa();
        }
    }//GEN-LAST:event_edtPesquisaKeyPressed

    private void btnCarregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCarregarMouseClicked
        int linha = tbGrid.getSelectedRow();
        if (linha >= 0) {
            String auxCodigo = tbGrid.getValueAt(linha, 0).toString();
            campoCodigo.setText(auxCodigo);
            campoCodigo.grabFocus();
            dispose();
        }
    }//GEN-LAST:event_btnCarregarMouseClicked

	private void tbGridKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbGridKeyPressed
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                int linha = tbGrid.getSelectedRow();
                if (linha >= 0) {
                    String auxCodigo = tbGrid.getValueAt(linha, 0).toString();
                    campoCodigo.setText(auxCodigo);
                    campoCodigo.grabFocus();
                    dispose();
                }
            }
	}//GEN-LAST:event_tbGridKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCarregar;
    private javax.swing.JButton btnPesquisa;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox cbCampo;
    private javax.swing.JComboBox cbValor;
    private javax.swing.JTextField edtPesquisa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbGrid;
    // End of variables declaration//GEN-END:variables

    public ModelPadrao getModel() {
        return Model;
    }

    public void setModel(ModelPadrao Model) {
        this.Model = Model;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public javax.swing.JTable getTbGrid() {
        return tbGrid;
    }

    public void setTbGrid(javax.swing.JTable tbGrid) {
        this.tbGrid = tbGrid;
    }

    public javax.swing.JComboBox getCbCampo() {
        return cbCampo;
    }

    public void setCbCampo(javax.swing.JComboBox cbCampo) {
        this.cbCampo = cbCampo;
    }

    public javax.swing.JComboBox getCbValor() {
        return cbValor;
    }

    public void setCbValor(javax.swing.JComboBox cbValor) {
        this.cbValor = cbValor;
    }

    public javax.swing.JTextField getEdtPesquisa() {
        return edtPesquisa;
    }

    public void setEdtPesquisa(javax.swing.JTextField edtPesquisa) {
        this.edtPesquisa = edtPesquisa;
    }
}
