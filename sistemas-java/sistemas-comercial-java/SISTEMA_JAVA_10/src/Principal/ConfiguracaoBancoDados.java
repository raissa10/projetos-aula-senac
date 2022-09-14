package Principal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio
 */
public class ConfiguracaoBancoDados extends JFrame {

    // public class ConfiguracaoBancoDados extends MetodosGlobais{
    final static String LOCALHOST = "Conexao.ini";

    private static final String driver  = "org.firebirdsql.jdbc.FBDriver"; //Classe do driver
    private static String banco         = "";
    private static String str_conn      = "";//URL de conexão
    private static final String usuario = "SYSDBA"; //Usuário da base
    private static final String senha   = "masterkey"; //Senha da base

    /**
     * Creates new form ConfiguracaoBancoDados
     */
    public ConfiguracaoBancoDados() {
        initComponents();
        setLocationRelativeTo(this);
    }

    public void selecionaBancoDados() {
        JFileChooser caminhoarquivo = new JFileChooser();
        caminhoarquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int i = caminhoarquivo.showSaveDialog(null);
        if (i == 1) {
            edtCaminho.setText("");
        } else {
            File arquivo = caminhoarquivo.getSelectedFile();
            edtCaminho.setText(arquivo.getPath());
        }
    }

    public void carregaArquivoEmTexto() {
        //Este método carrega o arquivo ini na tela
        try {
            java.awt.Desktop.getDesktop().open(new File(LOCALHOST));
        } catch (FileNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Erro!Arquivo não encontrado! \n" + erro.getMessage());
        } catch (IOException erro) {
            JOptionPane.showMessageDialog(null, "Erro!Arquivo não encontrado!Parte dois! \n" + erro.getMessage());
        } finally {
            //Finalizando o evento
            System.exit(0);
        }
    }

    public boolean verificaCamposTela() {
        boolean verifica;
        //Validacao dos campos da tela
        String auxRede = edtRede.getText();
        String auxPorta = edtPorta.getText();
        String auxCaminho = edtCaminho.getText();

        if (auxRede.equals("")) {
            JOptionPane.showMessageDialog(null, "Endereço da Rede Nulo!");
            edtRede.requestFocus();
            verifica = false;
        } else if (auxPorta.equals("")) {
            JOptionPane.showMessageDialog(null, "Número da Porta Nulo!");
            edtPorta.requestFocus();
            verifica = false;
        } else if (auxCaminho.equals("")) {
            JOptionPane.showMessageDialog(null, "Caminho está Nulo!");
            edtCaminho.requestFocus();
            verifica = false;
        } else {
            verifica = true;
        }
        return verifica;
    }

    public void gravarIniBancoDados() {

        if (verificaCamposTela()) {
            //Instancia a classe que le e grava arquivos do Java
            BufferedWriter arquivo;
            String valores
                    = "Caminho=" + edtRede.getText() + ":"
                    + edtPorta.getText() + "/" + edtCaminho.getText();
            try {
                // abre o arquivo de texto para acrescentar linhas
                arquivo = new BufferedWriter(new FileWriter(LOCALHOST, true));
                // escreve a linha de texto
                arquivo.write(valores);
                // insere uma quebra de linha
                arquivo.newLine();
                // força a gravação dos dados em disco
                arquivo.flush();
                arquivo.close();
                JOptionPane.showMessageDialog(null, "Caminho do banco gravado com sucesso!");
                edtCaminho.setText("");
                edtRede.setText("");
                edtPorta.setText("");
                valores = "";
            } catch (IOException erro) {
                JOptionPane.showMessageDialog(null, "Erro ao gravar o caminho do banco de dados!" + erro.getMessage());
            }
        }
    }

    public Connection getConexaoTelaBancoDados() {

        banco = edtRede.getText() + ":" + edtPorta.getText() + "/" + edtCaminho.getText();

        str_conn = "jdbc:firebirdsql://" + banco;

        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(str_conn, usuario, senha);

        } catch (ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Erro de driver! \n" + erro.getMessage());
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de Conexão! \n" + erro.getMessage());
        }
        return conn;
    }

    public boolean verificaConexaoTela() {
        String sqlUsuario
                = "SELECT                "
                + "    count(*) as total "
                + "FROM                  "
                + "    USUARIO           ";
        boolean existe = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConexaoTelaBancoDados();
            pstmt = conn.prepareStatement(sqlUsuario);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existe = rs.getInt("total") > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL. verificaConexaoTela(): \n" + e.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return existe;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnAbreArquivoGravado = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnGravarIni = new javax.swing.JButton();
        btnTestarConexao = new javax.swing.JButton();
        edtCaminho = new javax.swing.JTextField();
        edtPorta = new javax.swing.JTextField();
        edtRede = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnPesquisa = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAbreArquivoGravado.setText("Abrir Arquivo Gravado");
        btnAbreArquivoGravado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbreArquivoGravadoActionPerformed(evt);
            }
        });
        jPanel1.add(btnAbreArquivoGravado, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, 30));

        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        jPanel1.add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, 119, 30));

        btnGravarIni.setText("Gravar Dados");
        btnGravarIni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarIniActionPerformed(evt);
            }
        });
        jPanel1.add(btnGravarIni, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 119, 30));

        btnTestarConexao.setText("Testar Conex�o");
        btnTestarConexao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestarConexaoActionPerformed(evt);
            }
        });
        jPanel1.add(btnTestarConexao, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 139, 30));
        jPanel1.add(edtCaminho, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 139, 30));

        edtPorta.setText("3050");
        jPanel1.add(edtPorta, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 80, 30));

        edtRede.setText("Localhost");
        jPanel1.add(edtRede, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 145, 30));

        jLabel4.setText("Localhost ou IP");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(209, 88, -1, -1));

        jLabel3.setText("Rede:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        jLabel6.setText("Porta:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, 10));

        jLabel2.setText("Caminho:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, -1, -1));

        jLabel5.setText("Porta 3050  ou 3060");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, -1, -1));

        btnPesquisa.setText("Pesquisar");
        btnPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisaActionPerformed(evt);
            }
        });
        jPanel1.add(btnPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 119, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Configura��o de Banco de Dados");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 390, 350));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 20, 350));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 390, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnGravarIniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarIniActionPerformed
        // TODO add your handling code here:
        if (verificaConexaoTela()) {
            gravarIniBancoDados();
        }
    }//GEN-LAST:event_btnGravarIniActionPerformed

    private void btnAbreArquivoGravadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbreArquivoGravadoActionPerformed
        // TODO add your handling code here:
        carregaArquivoEmTexto();
    }//GEN-LAST:event_btnAbreArquivoGravadoActionPerformed

    private void btnTestarConexaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestarConexaoActionPerformed
        // TODO add your handling code here:
        if (verificaCamposTela()) {
            if (verificaConexaoTela()) {
                JOptionPane.showMessageDialog(null, "Conexão ok!");
            } else {
                JOptionPane.showMessageDialog(null, "Nao existem usuarios cadastrados!");
            }
        }
    }//GEN-LAST:event_btnTestarConexaoActionPerformed

    private void btnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaActionPerformed
        // TODO add your handling code here:
        selecionaBancoDados();
    }//GEN-LAST:event_btnPesquisaActionPerformed

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
            java.util.logging.Logger.getLogger(ConfiguracaoBancoDados.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConfiguracaoBancoDados().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbreArquivoGravado;
    private javax.swing.JButton btnGravarIni;
    private javax.swing.JButton btnPesquisa;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnTestarConexao;
    private javax.swing.JTextField edtCaminho;
    private javax.swing.JTextField edtPorta;
    private javax.swing.JTextField edtRede;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
