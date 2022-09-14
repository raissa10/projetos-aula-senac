package Estrutura;

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
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class ConfiguracaoBancoDados extends JFrame {

    // public class ConfiguracaoBancoDados extends MetodosGlobais{
    final static String LOCALHOST = "Conexao.ini";

    private static final String driver = "org.firebirdsql.jdbc.FBDriver"; //Classe do driver
    private static String banco = "";
    private static String str_conn = "";//URL de conexão
    private static final String usuario = "SYSDBA"; //Usuário da base
    private static final String senha = "masterkey"; //Senha da base

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
//            edtCaminho.setText("");
        } else {
            File arquivo = caminhoarquivo.getSelectedFile();
//            edtCaminho.setText(arquivo.getPath());
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
        boolean verifica = false;
        //Validacao dos campos da tela
//        String auxRede = edtRede.getText();
//        String auxPorta = edtPorta.getText();
//        String auxCaminho = edtCaminho.getText();
//
//        if (auxRede.equals("")) {
//            JOptionPane.showMessageDialog(null, "Endereço da Rede Nulo!");
//            edtRede.requestFocus();
//            verifica = false;
//        } else if (auxPorta.equals("")) {
//            JOptionPane.showMessageDialog(null, "Número da Porta Nulo!");
//            edtPorta.requestFocus();
//            verifica = false;
//        } else if (auxCaminho.equals("")) {
//            JOptionPane.showMessageDialog(null, "Caminho está Nulo!");
//            edtCaminho.requestFocus();
//            verifica = false;
//        } else {
//            verifica = true;
//        }
        return verifica;
    }

    public void gravarIniBancoDados() {
//
//        if (verificaCamposTela()) {
//            //Instancia a classe que le e grava arquivos do Java
//            BufferedWriter arquivo;
//            String valores
//                    = "Caminho=" + edtRede.getText() + ":"
//                    + edtPorta.getText() + "/" + edtCaminho.getText();
//            try {
//                // abre o arquivo de texto para acrescentar linhas
//                arquivo = new BufferedWriter(new FileWriter(LOCALHOST, true));
//                // escreve a linha de texto
//                arquivo.write(valores);
//                // insere uma quebra de linha
//                arquivo.newLine();
//                // força a gravação dos dados em disco
//                arquivo.flush();
//                arquivo.close();
//                JOptionPane.showMessageDialog(null, "Caminho do banco gravado com sucesso!");
//                edtCaminho.setText("");
//                edtRede.setText("");
//                edtPorta.setText("");
//                valores = "";
//            } catch (IOException erro) {
//                JOptionPane.showMessageDialog(null, "Erro ao gravar o caminho do banco de dados!" + erro.getMessage());
//            }
//        }
    }

    public Connection getConexaoTelaBancoDados() {

        banco = "";//edtRede.getText() + ":" + edtPorta.getText() + "/" + edtCaminho.getText();

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

    private void onChangeComboBoxBancoDados() {
        boolean verifica = false;
        if (verifica) {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            String sql
                    = "select * from sub_grupo where "
                    + "sub_grupo.ds_sub_grupo='" + cbServidor.getSelectedItem() + "'";
            try {
                conn = Conexao.getConexao();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                rs.next();
                String auxnome = rs.getString("ds_sub_grupo");
                cbServidor.setSelectedItem(auxnome);
            } catch (SQLException ex) {
                //mensagemErro("Funcao ComboBoxSubGrupo()!:" + ex.getMessage());
            }
        }
    }

    public static void teste() {

        int[][] array1 = {{1, 2, 3}, {4, 5, 6}};
        int[][] array2 = {{1, 2}, {3}, {4, 5, 6}};

        System.out.println("Valores no array1 passados na linha são");
        outputArray(array1); //exibe o array 2 por linha

        //System.out.println("Valores no array2 passados na linha são");
        //outputArray(array2); //exibe o array 2 por linha

    }

    public static void outputArray(int[][] array) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        //FAZ UM LOOP PELAS COLUNAS DA LINHA ATUAL
        for (int linha = 0; linha < array.length; linha++) {
            //FAZ LOOP PELAS COLUNAS DA LINHA ATUAL
            for (int coluna = 0; coluna < array[linha].length; coluna++) {
                System.out.printf("%d ", array[linha][coluna]);
                
                modelo.addElement("Teste" + array[linha][coluna]);
            }
            System.out.println();
        }        
    }

    private DefaultComboBoxModel carregaComboBancos() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();        
        String[] array = {"HEROKU", "ELEPHANTSQL 1", "ELEPHANTSQL 2"};        
        for (int linha = 0; linha < array.length; linha++) {                
            modelo.addElement("Teste: " + array[linha]);
            System.out.println();
        }
        return modelo;
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
        PainelPagamento = new javax.swing.JTabbedPane();
        PainelAbaPagamento = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnCarregaServidor = new javax.swing.JButton();
        cbServidor = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnAbreArquivoGravado3 = new javax.swing.JButton();
        btnSair3 = new javax.swing.JButton();
        btnGravarIni3 = new javax.swing.JButton();
        btnTestarConexao3 = new javax.swing.JButton();
        edtCaminho3 = new javax.swing.JTextField();
        edtPorta3 = new javax.swing.JTextField();
        edtRede3 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnPesquisa3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnAbreArquivoGravado2 = new javax.swing.JButton();
        btnSair2 = new javax.swing.JButton();
        btnGravarIni2 = new javax.swing.JButton();
        btnTestarConexao2 = new javax.swing.JButton();
        edtCaminho2 = new javax.swing.JTextField();
        edtPorta2 = new javax.swing.JTextField();
        edtRede2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnPesquisa2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Configuração de Banco de Dados");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, -1, -1));

        PainelPagamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        PainelAbaPagamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PainelAbaPagamento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        PainelPagamento.addTab("OUTROS", PainelAbaPagamento);

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel9.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCarregaServidor.setText("Carregar Servidores");
        btnCarregaServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarregaServidorActionPerformed(evt);
            }
        });
        jPanel1.add(btnCarregaServidor, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 150, 30));

        cbServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbServidorActionPerformed(evt);
            }
        });
        jPanel1.add(cbServidor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 289, -1));

        jLabel23.setText("Base Conexão:");
        jLabel23.setAlignmentX(10.0F);
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        jPanel9.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 580, 440));

        PainelPagamento.addTab("POSTGRES", jPanel9);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAbreArquivoGravado3.setText("Abrir Arquivo Gravado");
        btnAbreArquivoGravado3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbreArquivoGravado3ActionPerformed(evt);
            }
        });
        jPanel4.add(btnAbreArquivoGravado3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, 30));

        btnSair3.setText("Sair");
        btnSair3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSair3ActionPerformed(evt);
            }
        });
        jPanel4.add(btnSair3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, 119, 30));

        btnGravarIni3.setText("Gravar Dados");
        btnGravarIni3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarIni3ActionPerformed(evt);
            }
        });
        jPanel4.add(btnGravarIni3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 119, 30));

        btnTestarConexao3.setText("Testar Conexão");
        btnTestarConexao3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestarConexao3ActionPerformed(evt);
            }
        });
        jPanel4.add(btnTestarConexao3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 139, 30));
        jPanel4.add(edtCaminho3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 139, 30));

        edtPorta3.setText("3050");
        jPanel4.add(edtPorta3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 80, 30));

        edtRede3.setText("Localhost");
        jPanel4.add(edtRede3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 145, 30));

        jLabel17.setText("Localhost ou IP");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(209, 88, -1, -1));

        jLabel18.setText("Rede:");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        jLabel19.setText("Porta:");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, -1, 10));

        jLabel20.setText("Caminho:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, -1, -1));

        jLabel21.setText("Porta 3050  ou 3060");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, -1, -1));

        btnPesquisa3.setText("Pesquisar");
        btnPesquisa3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisa3ActionPerformed(evt);
            }
        });
        jPanel4.add(btnPesquisa3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 200, 119, 30));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 120, 530, 404));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAbreArquivoGravado2.setText("Abrir Arquivo Gravado");
        btnAbreArquivoGravado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbreArquivoGravado2ActionPerformed(evt);
            }
        });
        jPanel2.add(btnAbreArquivoGravado2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, 150, 30));

        btnSair2.setText("Sair");
        btnSair2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSair2ActionPerformed(evt);
            }
        });
        jPanel2.add(btnSair2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 150, 30));

        btnGravarIni2.setText("Gravar Dados");
        btnGravarIni2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarIni2ActionPerformed(evt);
            }
        });
        jPanel2.add(btnGravarIni2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 150, 30));

        btnTestarConexao2.setText("Testar Conexão");
        btnTestarConexao2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestarConexao2ActionPerformed(evt);
            }
        });
        jPanel2.add(btnTestarConexao2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 150, 150, 30));

        edtCaminho2.setAlignmentX(10.0F);
        jPanel2.add(edtCaminho2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 139, 30));

        edtPorta2.setText("3050");
        edtPorta2.setAlignmentX(10.0F);
        jPanel2.add(edtPorta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 80, 30));

        edtRede2.setText("Localhost");
        edtRede2.setAlignmentX(10.0F);
        jPanel2.add(edtRede2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 145, 30));

        jLabel12.setText("Localhost ou IP");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1, -1));

        jLabel13.setText("Rede:");
        jLabel13.setAlignmentX(10.0F);
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel14.setText("Porta:");
        jLabel14.setAlignmentX(10.0F);
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 10));

        jLabel15.setText("Caminho:");
        jLabel15.setAlignmentX(10.0F);
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel16.setText("Porta 3050  ou 3060");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, -1));

        btnPesquisa2.setText("Pesquisar");
        btnPesquisa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisa2ActionPerformed(evt);
            }
        });
        jPanel2.add(btnPesquisa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 150, 30));

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 530, 340));

        PainelPagamento.addTab("FIREBIRD", jPanel3);

        getContentPane().add(PainelPagamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 590, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbreArquivoGravado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbreArquivoGravado2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbreArquivoGravado2ActionPerformed

    private void btnSair2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSair2ActionPerformed

    private void btnGravarIni2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarIni2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGravarIni2ActionPerformed

    private void btnTestarConexao2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestarConexao2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTestarConexao2ActionPerformed

    private void btnPesquisa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisa2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPesquisa2ActionPerformed

    private void btnAbreArquivoGravado3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbreArquivoGravado3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbreArquivoGravado3ActionPerformed

    private void btnSair3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSair3ActionPerformed

    private void btnGravarIni3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarIni3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGravarIni3ActionPerformed

    private void btnTestarConexao3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestarConexao3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTestarConexao3ActionPerformed

    private void btnPesquisa3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisa3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPesquisa3ActionPerformed

    private void cbServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbServidorActionPerformed
        //seta o selecionado
    }//GEN-LAST:event_cbServidorActionPerformed

    private void btnCarregaServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarregaServidorActionPerformed
       cbServidor.setModel(carregaComboBancos());
    }//GEN-LAST:event_btnCarregaServidorActionPerformed

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
    private javax.swing.JPanel PainelAbaPagamento;
    private javax.swing.JTabbedPane PainelPagamento;
    private javax.swing.JButton btnAbreArquivoGravado2;
    private javax.swing.JButton btnAbreArquivoGravado3;
    private javax.swing.JButton btnCarregaServidor;
    private javax.swing.JButton btnGravarIni2;
    private javax.swing.JButton btnGravarIni3;
    private javax.swing.JButton btnPesquisa2;
    private javax.swing.JButton btnPesquisa3;
    private javax.swing.JButton btnSair2;
    private javax.swing.JButton btnSair3;
    private javax.swing.JButton btnTestarConexao2;
    private javax.swing.JButton btnTestarConexao3;
    private javax.swing.JComboBox cbServidor;
    private javax.swing.JTextField edtCaminho2;
    private javax.swing.JTextField edtCaminho3;
    private javax.swing.JTextField edtPorta2;
    private javax.swing.JTextField edtPorta3;
    private javax.swing.JTextField edtRede2;
    private javax.swing.JTextField edtRede3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables
}
