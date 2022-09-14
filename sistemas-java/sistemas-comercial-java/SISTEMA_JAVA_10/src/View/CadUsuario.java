package View;

import ControleCadastro.UsuarioDB;
import ModelCadastro.Usuario;
import VisaoConsultasCadastro.ConsultaUsuario;
import Principal.Conexao;
import Principal.MetodosGlobais;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class CadUsuario extends MetodosGlobais {

    private static final String sqlBuscaUsuario
            = "SELECT * FROM usuario WHERE cd_usuario=?";
    UsuarioDB usuariodb = new UsuarioDB();

    public CadUsuario() {
        initComponents();
        Centro();
        habilitaCampos(false);
    }

    private void habilitaCampos(boolean habilita) {
        edtCodigo.requestFocus();
        edtCodigo.setEnabled(!habilita);
        edtds_usuario.setEnabled(habilita);
        edtLogin.setEnabled(habilita);
        edtSenha.setEnabled(habilita);
        edtSenha_Dois.setEnabled(habilita);
        edtGrupo.setEnabled(habilita);

        btnGravar.setEnabled(habilita);
        btnCancelar.setEnabled(habilita);
        btnConsulta.setEnabled(!habilita);
        btnExcluir.setEnabled(habilita);

        if (habilita) {
            edtCodigo.requestFocus();
        } else {
            LimpaTela();
        }
    }

    private void LimpaTela() {
        edtCodigo.setText("");
        edtds_usuario.setText("");
        edtLogin.setText("");
        edtSenha.setText("");
        edtSenha_Dois.setText("");
        edtGrupo.setText("");
        edtCodigo.grabFocus();
    }

    private void Excluir_Registro() {
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            int auxCodigo = Integer.parseInt(edtCodigo.getText());
            String auxLogin = edtLogin.getText();

            if (usuariodb.getUnicoUsuario(auxLogin)) {
                mensagemErro("Ùnico usuario do banco e não pode ser deletado!");
                habilitaCampos(false);
            } else {
                if (usuariodb.excluirUsuario(auxCodigo)) {
                    JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!");
                    habilitaCampos(false);
                } else {
                    mensagemErro("Não foi possivel excluir o registro!");
                }
            }
        }
    }

    private void alterarGravar() {
        String auxcd_usuario = edtCodigo.getText();
        String auxLogin = edtLogin.getText().toUpperCase();
        String auxds_usuario = edtds_usuario.getText().toUpperCase();
        String auxSenha = edtSenha.getText();
        String auxcd_grupo = edtGrupo.getText();

        //Conversao das variaveis            
        int cd_usuario = Integer.parseInt(auxcd_usuario);
        String ds_login = auxLogin;
        String ds_usuario = auxds_usuario;
        String ds_senha = auxSenha;
        int cd_grupo = Integer.parseInt(auxcd_grupo);
        int cd_filial = 1;
        int fg_ativo = 1;
        Usuario usuario = new Usuario(
                cd_usuario,
                ds_login,
                ds_usuario,
                ds_senha,
                cd_grupo,
                cd_filial,
                fg_ativo
        );

        if (usuariodb.getUsuario(cd_usuario)) {
            if (usuariodb.alterarUsuario(usuario)) {
                JOptionPane.showMessageDialog(null, "Registro alterado com sucesso!");
                habilitaCampos(false);
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível alterar o registro!");
            }
        } else {
            if (usuariodb.inserirUsuario(usuario)) {
                JOptionPane.showMessageDialog(null, "Registro incluído com sucesso!");
                habilitaCampos(false);
                LimpaTela();
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível incluir o registro!");
                edtCodigo.grabFocus();
            }
        }

    }

    private void ValidaCodigoGenerator() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            //Pega o Valor do Generator
            rs = stmt.executeQuery("SELECT GEN_ID(GEN_CD_USUARIO, 1) FROM RDB$DATABASE");
            while (rs.next()) {
                int auxCodigoGenerator = rs.getInt("GEN_ID");
                int auxCodigo = auxCodigoGenerator + 1;
                String a;
                a = "" + auxCodigo;
                edtCodigo.setText(a);
                edtLogin.grabFocus();
                //Altera o valor do Generator se o SQL passado acima fosse""SELECT GEN_ID(GEN_CD_USUARIO, 0) FROM RDB$DATABASE""
                //rs = stmt.executeQuery("ALTER SEQUENCE GEN_CD_USUARIO RESTART WITH " + auxCodigo);
            }
        } catch (SQLException erro) {
            System.out.println("Erro de conexão! " + erro);
        } finally {
            Conexao.closeAll(conn);
        }
    }

    private void ValidaCampoCodigoNãoNulo() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cd_usuario = Integer.parseInt(edtCodigo.getText());

        if (usuariodb.getUsuario(cd_usuario)) {
            habilitaCampos(true);
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaUsuario);
                //E passado por parametro o codigo do usuario resultante do SQL de nome "cd_usuario"
                pstmt.setInt(1, cd_usuario);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxUsuario = rs.getString("ds_usuario");
                    String auxLogin = rs.getString("ds_login");
                    //edtUsuario.setText(ds_usuario);
                    String auxSenha = rs.getString("ds_senha");
                    String auxFilial = rs.getString("cd_filial");
                    int a = Integer.parseInt(auxFilial);

                    //Precisa Usar ||  para todos os casos possiveis e eliminar o campo nome do usuario;
                    edtds_usuario.setText(auxUsuario);
                    edtLogin.setText(auxLogin);
                    edtSenha.setText(auxSenha);
                    edtGrupo.setText(auxFilial);
                    edtLogin.grabFocus();
                }
            } catch (SQLException erro) {
                mensagemErro("Erro de conexão! " + erro);
            }
        } else {
            mensagemErro("Usuario Não Cadastrado!");
            habilitaCampos(false);
        }
    }

    private void GravarCompletoValidado() {
        String auxTexto = edtCodigo.getText();
        String auxLogin = edtLogin.getText();
        String auxSenhaUm = edtSenha.getText();
        String auxSenhaDois = edtSenha_Dois.getText();
        String auxGrupo = edtGrupo.getText();
        if (auxTexto.equals("")) {
            mensagemErro("Favor Preencher o Código do Usuário!");
            edtCodigo.grabFocus();
        } else if (auxLogin.equals("")) {
            mensagemErro("Favor Preencher o Login do Usuário!");
            edtLogin.grabFocus();
        } else if (auxSenhaUm.equals("")) {
            mensagemErro("Favor Preencher a Senha do Usuário!");
            edtSenha.grabFocus();
        } else if (auxSenhaDois.equals("")) {
            mensagemErro("Favor Preencher a Senha do Usuário!");
            edtSenha_Dois.grabFocus();
        } else if (auxGrupo.equals("")) {
            mensagemErro("Favor Preencher o grupo do Usuário!");
            edtGrupo.grabFocus();
        } else {
            //Valida senha igual
            if (auxSenhaUm.equals(auxSenhaDois)) {
                alterarGravar();
            } else {
                mensagemErro("Senhas não conferem!");
                edtSenha_Dois.requestFocus();
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
        edtSenha = new javax.swing.JTextField();
        edtGrupo = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        edtLogin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        edtSenha_Dois = new javax.swing.JTextField();
        edtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        edtds_usuario = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnGravar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Cadastro de Usuários");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 245, 39));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        edtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtSenhaKeyPressed(evt);
            }
        });
        jPanel1.add(edtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 140, 30));

        edtGrupo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtGrupoKeyPressed(evt);
            }
        });
        jPanel1.add(edtGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 140, 30));

        jLabel10.setText("Grupo Usuário:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 100, -1));

        jLabel8.setText("Senha:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 40, -1));

        edtLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtLoginKeyPressed(evt);
            }
        });
        jPanel1.add(edtLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 140, 30));

        jLabel2.setText("Login:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, 40, -1));

        jLabel11.setText("Confirmar Senha:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 100, -1));

        jLabel7.setText("Código:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 50, -1));

        edtSenha_Dois.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtSenha_DoisKeyPressed(evt);
            }
        });
        jPanel1.add(edtSenha_Dois, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 140, 30));

        edtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoKeyPressed(evt);
            }
        });
        jPanel1.add(edtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 80, 30));

        jLabel3.setText("Nome:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 40, -1));

        edtds_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtds_usuarioKeyPressed(evt);
            }
        });
        jPanel1.add(edtds_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 220, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 55, 350, 320));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel2.add(btnGravar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 40));

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
        jPanel2.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, 40));

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
        jPanel2.add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 120, 40));

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
        jPanel2.add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 120, 40));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        jPanel2.add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 120, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 140, 260));

        setBounds(0, 0, 536, 434);
    }// </editor-fold>//GEN-END:initComponents

    private void edtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoKeyPressed
        // Nesta parte o sistema faz 2 validações
        // 1-valida se o campo Codigo nao esta nulo ,se estiver nulo
        // ele chama uma funcao para pegar o ultimo codigo da tabela
        // Neste caso seria a funcao do generator que deve ser implementada pra frente
        // 2 Validação: Verifica se existe o codigo do campo Codigo na tabela usuario
        // Se o registro existe ele carrega os dados na tela
        // Senao existe ele da a msg de Usuario Nao cadastrado!
        String auxTexto = edtCodigo.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                habilitaCampos(true);
                ValidaCodigoGenerator();
                edtLogin.grabFocus();
            } else {
                ValidaCampoCodigoNãoNulo();
            }
        }
    }//GEN-LAST:event_edtCodigoKeyPressed

    private void edtLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtLoginKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtSenha.grabFocus();
        }
    }//GEN-LAST:event_edtLoginKeyPressed

    private void edtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtGrupo.grabFocus();
        }
    }//GEN-LAST:event_edtSenhaKeyPressed

    private void edtGrupoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtGrupoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnGravar.grabFocus();
        }
    }//GEN-LAST:event_edtGrupoKeyPressed

    private void edtSenha_DoisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtSenha_DoisKeyPressed
        
    }//GEN-LAST:event_edtSenha_DoisKeyPressed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente gravar o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            GravarCompletoValidado();
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
            //LimpaTela();
            edtCodigo.grabFocus();
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
        ConsultaUsuario form = new ConsultaUsuario(edtCodigo);
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

    private void edtds_usuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtds_usuarioKeyPressed
        
    }//GEN-LAST:event_edtds_usuarioKeyPressed

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
            java.util.logging.Logger.getLogger(CadUsuario.class
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
            @Override
            public void run() {
                new CadUsuario().setVisible(true);
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
    private javax.swing.JTextField edtGrupo;
    private javax.swing.JTextField edtLogin;
    private javax.swing.JTextField edtSenha;
    private javax.swing.JTextField edtSenha_Dois;
    private javax.swing.JTextField edtds_usuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
