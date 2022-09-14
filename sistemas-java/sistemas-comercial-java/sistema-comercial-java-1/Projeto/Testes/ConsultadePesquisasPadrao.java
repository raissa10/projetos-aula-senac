package Testes;

import ControleCadastro.UsuarioDB;
import ModeloCadastro.Usuario;
import Principal.Conexao;
import Principal.MetodosGlobais;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gelvazio
 */
public class ConsultadePesquisasPadrao extends MetodosGlobais {

    String mensagem;
    String mensagem_dois;
    String SQLConsulta_Usuario;
    JTextField campoCodigo;

    /**
     * Creates new form LocalizaUsuario
     */
    public DefaultComboBoxModel getComboCampo() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        String auxCodigo = "Codigo";
        String auxNome = "Nome";
        String auxFilial = "Filial";
        try {
            modelo.addElement(auxCodigo);
            modelo.addElement(auxNome);
            modelo.addElement(auxFilial);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro no Método getComboCampo(): \n" + erro.getMessage());
        } finally {
            return modelo;
        }
    }

    public DefaultComboBoxModel getComboValor() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        String auxIgual = "Igual";
        String auxDiferente = "Diferente";
        String auxMaior = "Maior";
        String auxMenor = "Menor";
        String auxMaiorIgual = "MaiorIgual";
        String auxMenorIgual = "MenorIgual";
        String auxParecido = "Parecido";
        try {
            modelo.addElement(auxIgual);
            modelo.addElement(auxParecido);
            modelo.addElement(auxMaior);
            modelo.addElement(auxMenor);
            modelo.addElement(auxMaiorIgual);
            modelo.addElement(auxMenorIgual);
            modelo.addElement(auxDiferente);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro no Método getComboValor(): \n" + erro.getMessage());
        } finally {
            return modelo;
        }
    }

    public void PegaValorCamposComboboxCampo_E_Valor(String SQLValorCamposComboboxCampo_E_Valor) {
        String auxedtPesquisa = null;
        String auxCampo = cbCampo.getSelectedItem().toString();
        String auxValor = cbValor.getSelectedItem().toString();
        //Programação do SQL do Codigo
        if ((auxCampo.equals("Codigo")) & (auxValor.equals("Igual"))) {
            auxCampo = "cd_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Diferente"))) {
            auxCampo = "cd_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<>" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Maior"))) {
            auxCampo = "cd_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Menor"))) {
            auxCampo = "cd_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("MaiorIgual"))) {
            auxCampo = "cd_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("MenorIgual"))) {
            auxCampo = "cd_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Parecido"))) {
            auxCampo = "cd_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "LIKE UPPER('" + auxedtPesquisa + "%')";
        }//######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //Programação do SQL do Nome
        if ((auxCampo.equals("Nome")) & (auxValor.equals("Igual"))) {
            auxCampo = "ds_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "LIKE UPPER('" + auxedtPesquisa + "%')";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("Diferente"))) {
            auxCampo = "ds_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<> '" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("Maior"))) {
            auxCampo = "ds_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">'" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("Menor"))) {
            auxCampo = "ds_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<'" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("MaiorIgual"))) {
            auxCampo = "ds_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">='" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("MenorIgual"))) {
            auxCampo = "ds_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<='" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("Parecido"))) {
            auxCampo = "ds_usuario";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "LIKE UPPER('" + auxedtPesquisa + "%')";
        }
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //Programação do SQL da Filial
        if ((auxCampo.equals("Filial")) & (auxValor.equals("Igual"))) {
            auxCampo = "cd_filial";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Filial")) & (auxValor.equals("Diferente"))) {
            auxCampo = "cd_filial";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<>" + auxedtPesquisa;
        } else if ((auxCampo.equals("Filial")) & (auxValor.equals("Maior"))) {
            auxCampo = "cd_filial";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">" + auxedtPesquisa;
        } else if ((auxCampo.equals("Filial")) & (auxValor.equals("Menor"))) {
            auxCampo = "cd_filial";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<" + auxedtPesquisa;
        } else if ((auxCampo.equals("Filial")) & (auxValor.equals("MaiorIgual"))) {
            auxCampo = "cd_filial";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Filial")) & (auxValor.equals("MenorIgual"))) {
            auxCampo = "cd_filial";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Filial")) & (auxValor.equals("Parecido"))) {
            auxCampo = "cd_filial";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "LIKE UPPER('" + auxedtPesquisa + "%')";
        }
        //E passado por parametro os valores do Edit  mais os valores selecionados do Combobox  para o SQL
        //A Variavel "SQLValorCamposComboboxCampo_E_Valor" recebe o SQL
        SQLValorCamposComboboxCampo_E_Valor = "select * from usuario where " + auxCampo + " " + auxValor;
        //A Variavel global "SQLConsulta_Usuario" recebe por parametro a variavel "SQLValorCamposComboboxCampo_E_Valor"
        SQLConsulta_Usuario = SQLValorCamposComboboxCampo_E_Valor;
    }

    public void ValidaCampoPesquisa() {
        //JOptionPane.showMessageDialog(null, "Precisa Fazer Validacao de deixar digitar so numero qdo for o Codigo!!");
        //Nesta parte é feita a Validação do Edit  edtPesquisa, para que caso seja nulo de uma MSG.
        String auxedtPesquisa = edtPesquisa.getText();
        if (auxedtPesquisa.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Digitar algum valor ou caractere no campo Pesquisa!");
            edtPesquisa.grabFocus();
        } else {
            //Não estando Nulo o campo é chamado o Metodo abaixo que é o responsavel pela pesquisa Compelta da Tela.
            ListaUsuariosParametrosCompleto();
        }
    }

    public void mostrar_mensagem_tres() {
        JOptionPane.showMessageDialog(null, "Teste de Mensagem Tres Valor do SQL: !!!\n " + SQLConsulta_Usuario);
    }

    public ArrayList SQLConsultagetTodos_Completo() {
        //Aqui é chamado o Metodo "PegaValorCamposComboboxCampo_E_Valor("");" para pegar os valores da tela
        //Caso nao seja repassado ele nao da certo pois nao pega nada do edtPesquisa
        PegaValorCamposComboboxCampo_E_Valor("");
        ArrayList listaUsuario = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            //Nessa Parte é passado po parametro os Dados da Variavel "SQLConsulta_Usuario" que contem o sql da pesquisa.
            rs = stmt.executeQuery(SQLConsulta_Usuario);
            while (rs.next()) {
                int cd_usuario = rs.getInt("cd_usuario");
                String ds_login = rs.getString("ds_login");
                String ds_usuario = rs.getString("ds_usuario");
                String ds_senha = rs.getString("ds_senha");
                int cd_grupo = rs.getInt("cd_grupo");
                int cd_filial = rs.getInt("cd_filial");
                int fg_ativo = rs.getInt("fg_ativo");
                Usuario usuario = new Usuario(
                        cd_usuario,
                        ds_login,
                        ds_usuario,
                        ds_senha,
                        cd_grupo,
                        cd_filial,
                        fg_ativo
                );
                listaUsuario.add(usuario);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, SQLConsultagetTodos_Completo: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaUsuario;
    }

    public void ListaTodosUsuarios() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Filial");
        modelo.addColumn("Login");
        modelo.addColumn("Senha");
        UsuarioDB usuariodb = new UsuarioDB();
        ArrayList<Usuario> usuarios = usuariodb.getTodos();
        //ArrayList<Usuario> usuarios = SQLConsultagetTodos_Completo();
        for (Usuario auxUsuario : usuarios) {
            modelo.addRow(new Object[]{
                auxUsuario.getCd_usuario(),
                auxUsuario.getCd_filial(),
                auxUsuario.getDs_usuario(),
                auxUsuario.getDs_senha()
            });
        }
        tbGrid.setModel(modelo);

    }

    public void ListaUsuariosParametrosCompleto() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Filial");
        modelo.addColumn("Login");
        modelo.addColumn("Senha");
        //Nesta Parte o ArrayList dos Usuarios(Que chama a Classe Usuario) recebe por parametro o Metodo "SQLConsultagetTodos_Completo()" que tera os dados da pesquisa
        //Este Metodo Chama o ArrayList  que tera os dados e passa para a DefaultTableModel
        ArrayList<Usuario> usuarios = SQLConsultagetTodos_Completo();
        for (Usuario auxUsuario : usuarios) {
            modelo.addRow(new Object[]{
                auxUsuario.getCd_usuario(),
                auxUsuario.getCd_filial(),
                auxUsuario.getDs_usuario(),
                auxUsuario.getDs_senha()
            });
        }
        tbGrid.setModel(modelo);
    }

    public ConsultadePesquisasPadrao(JTextField campoCodigo) {
        initComponents();
        cbCampo.setModel(getComboCampo());
        cbValor.setModel(getComboValor());
        Centro();
        this.campoCodigo = campoCodigo;
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
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnTeste_SQL = new javax.swing.JButton();
        btnAtualizarTela = new javax.swing.JButton();
        btnParametro = new javax.swing.JButton();
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

        jButton2.setText("Sair");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Carregar");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        btnTeste_SQL.setText("Teste SQL");
        btnTeste_SQL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTeste_SQLMouseClicked(evt);
            }
        });

        btnAtualizarTela.setText("Listar Todos Usuarios");
        btnAtualizarTela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAtualizarTelaMouseClicked(evt);
            }
        });

        btnParametro.setText("Parametro");
        btnParametro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnParametroMouseClicked(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTeste_SQL)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(edtPesquisa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnPesquisa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addGap(36, 36, 36)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAtualizarTela)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnParametro, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTeste_SQL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAtualizarTela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnParametro))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(edtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Consultas Padrao de Exemplo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbGridMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGridMouseClicked
        // TODO add your handling code here:
        int linha = tbGrid.getSelectedRow();
        if (linha >= 0) {
            String auxCodigo = tbGrid.getValueAt(linha, 0).toString();
            campoCodigo.setText(auxCodigo);
        }
    }//GEN-LAST:event_tbGridMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbCampoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCampoActionPerformed
    }//GEN-LAST:event_cbCampoActionPerformed

    private void btnPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPesquisaMouseClicked
        ValidaCampoPesquisa();
        edtPesquisa.getText();
        //PegaValorCamposComboboxCampo_E_Valor("");
    }//GEN-LAST:event_btnPesquisaMouseClicked

    private void btnTeste_SQLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTeste_SQLMouseClicked
        // TODO add your handling code here:
        PegaValorCamposComboboxCampo_E_Valor("");
        mostrar_mensagem_tres();
    }//GEN-LAST:event_btnTeste_SQLMouseClicked

    private void btnAtualizarTelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAtualizarTelaMouseClicked
        // TODO add your handling code here:
        ListaTodosUsuarios();
    }//GEN-LAST:event_btnAtualizarTelaMouseClicked

    private void edtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtPesquisaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ValidaCampoPesquisa();
        }
    }//GEN-LAST:event_edtPesquisaKeyPressed

    private void btnParametroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnParametroMouseClicked
        // TODO add your handling code here:
        PegaValorCamposComboboxCampo_E_Valor("");
        mostrar_mensagem_tres();

    }//GEN-LAST:event_btnParametroMouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        int linha = tbGrid.getSelectedRow();
        if (linha >= 0) {
            String auxCodigo = tbGrid.getValueAt(linha, 0).toString();
            campoCodigo.setText(auxCodigo);
            campoCodigo.grabFocus();
            dispose();
        }
    }//GEN-LAST:event_jButton3MouseClicked

private void tbGridKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbGridKeyPressed
// TODO add your handling code here:
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
    private javax.swing.JButton btnAtualizarTela;
    private javax.swing.JButton btnParametro;
    private javax.swing.JButton btnPesquisa;
    private javax.swing.JButton btnTeste_SQL;
    private javax.swing.JComboBox cbCampo;
    private javax.swing.JComboBox cbValor;
    private javax.swing.JTextField edtPesquisa;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbGrid;
    // End of variables declaration//GEN-END:variables
}
