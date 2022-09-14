package VisaoConsultasCadastro;

import ControleCadastro.ProdutoDB;
import ModelCadastro.ProdutoSimples;
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
public class ConsultaProduto extends MetodosGlobais {

    String SQLConsulta_Produto;
    JTextField campoCodigo;

    /**
     * Creates new form LocalizaProduto
     */
    private DefaultComboBoxModel getComboCampo() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        try {
            modelo.addElement("Codigo");
            modelo.addElement("Referencia");
            modelo.addElement("Descricao");
            modelo.addElement("Grupo-Implementar!");
            modelo.addElement("Sub_Grupo-Implementar!");
            modelo.addElement("Cor-Implementar!");
            modelo.addElement("Cod.Fabrica-Implementar!");
            modelo.addElement("Marca-Implementar!");
            modelo.addElement("GrupoFiscal-Implementar!");
            modelo.addElement("NCMSH-Implementar!");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro no Método getComboCampo(): \n" + erro.getMessage());
        } finally {

        }
        return modelo;
    }

    private DefaultComboBoxModel getComboValor() {
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

        }
        return modelo;
    }

    public void PegaValorCamposComboboxCampo_E_Valor(String SQLValorCamposComboboxCampo_E_Valor) {
        String auxedtPesquisa = null;
        String auxCampo = cbCampo.getSelectedItem().toString();
        String auxValor = cbValor.getSelectedItem().toString();
        //Programação do SQL pelo Codigo do Produto
        if ((auxCampo.equals("Codigo")) & (auxValor.equals("Igual"))) {
            auxCampo = "cd_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Diferente"))) {
            auxCampo = "cd_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<>" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Maior"))) {
            auxCampo = "cd_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Menor"))) {
            auxCampo = "cd_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("MaiorIgual"))) {
            auxCampo = "cd_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("MenorIgual"))) {
            auxCampo = "cd_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Parecido"))) {
            auxCampo = "cd_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "LIKE UPPER('%" + auxedtPesquisa + "%')";
        }//######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################

        //Programação do SQL pela Referencia do Produto
        if ((auxCampo.equals("Referencia")) & (auxValor.equals("Igual"))) {
            auxCampo = "cd_ref";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Referencia")) & (auxValor.equals("Diferente"))) {
            auxCampo = "cd_ref";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<>" + auxedtPesquisa;
        } else if ((auxCampo.equals("Referencia")) & (auxValor.equals("Maior"))) {
            auxCampo = "cd_ref";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">" + auxedtPesquisa;
        } else if ((auxCampo.equals("Referencia")) & (auxValor.equals("Menor"))) {
            auxCampo = "cd_ref";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<" + auxedtPesquisa;
        } else if ((auxCampo.equals("Referencia")) & (auxValor.equals("MaiorIgual"))) {
            auxCampo = "cd_ref";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Referencia")) & (auxValor.equals("MenorIgual"))) {
            auxCampo = "cd_ref";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Referencia")) & (auxValor.equals("Parecido"))) {
            auxCampo = "cd_ref";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "LIKE UPPER('%" + auxedtPesquisa + "%')";
        }//######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################
        //######################################################################################################

        //Programação do SQL pelo Descricao do Produto
        if ((auxCampo.equals("Descricao")) & (auxValor.equals("Igual"))) {
            auxCampo = "ds_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "LIKE UPPER('" + auxedtPesquisa + "%')";
        } else if ((auxCampo.equals("Descricao")) & (auxValor.equals("Diferente"))) {
            auxCampo = "ds_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<> '" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Descricao")) & (auxValor.equals("Maior"))) {
            auxCampo = "ds_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">'" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Descricao")) & (auxValor.equals("Menor"))) {
            auxCampo = "ds_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<'" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Descricao")) & (auxValor.equals("MaiorIgual"))) {
            auxCampo = "ds_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">='" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Descricao")) & (auxValor.equals("MenorIgual"))) {
            auxCampo = "ds_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<='" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Descricao")) & (auxValor.equals("Parecido"))) {
            auxCampo = "ds_prod";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "LIKE UPPER('%" + auxedtPesquisa + "%')";
        }

        SQLValorCamposComboboxCampo_E_Valor = "select * from Produto_SIMPLES where " + auxCampo + " " + auxValor + "order by Produto_SIMPLES.cd_ref";
        //A Variavel global "SQLConsulta_Produto"
        //recebe por parametro a variavel "SQLValorCamposComboboxCampo_E_Valor"
        SQLConsulta_Produto = SQLValorCamposComboboxCampo_E_Valor;
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
            ListaPessoasCompleto();
        }
    }

    public void mostrar_mensagem_tres() {
        JOptionPane.showMessageDialog(null, "Teste de Mensagem Tres Valor do SQL: !!!\n " + SQLConsulta_Produto);
    }

    public ArrayList SQLConsultagetTodos_Completo() {
        //Aqui é chamado o Metodo "PegaValorCamposComboboxCampo_E_Valor("");" para pegar os valores da tela
        //Caso nao seja repassado ele nao da certo pois nao pega nada do edtPesquisa
        PegaValorCamposComboboxCampo_E_Valor("");
        ArrayList listaProduto = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            //Nessa Parte é passado po parametro os Dados da Variavel "SQLConsulta_Produto" que contem o sql da pesquisa.
            rs = stmt.executeQuery(SQLConsulta_Produto);
            while (rs.next()) {
                int CD_PROD = rs.getInt("CD_PROD");
                String DS_PROD = rs.getString("DS_PROD");
                int CD_GRUPO = rs.getInt("CD_GRUPO");
                int CD_SUB_GRUPO = rs.getInt("CD_SUB_GRUPO");
                int FG_ATIVO = rs.getInt("FG_ATIVO");
                int CD_COR = rs.getInt("CD_COR");
                String CD_FABRICA = rs.getString("CD_FABRICA");
                int CD_MARCA = rs.getInt("CD_MARCA");
                int CD_GP_FISCAL = rs.getInt("CD_GP_FISCAL");
                String CD_NCM_SH = rs.getString("CD_NCM_SH");
                int CD_REF = rs.getInt("CD_REF");
                int cd_usuario = rs.getInt("cd_usuario");
                int cd_filial = rs.getInt("cd_filial");
                int cd_unidade_medida = rs.getInt("cd_unidade_medida");
                int qt_estoque = rs.getInt("qt_estoque");
                int tx_ipi = rs.getInt("tx_ipi");
                int tx_iss = rs.getInt("tx_iss");

                ProdutoSimples produto = new ProdutoSimples(
                        CD_PROD,
                        DS_PROD,
                        CD_GRUPO,
                        CD_SUB_GRUPO,
                        FG_ATIVO,
                        CD_COR,
                        CD_FABRICA,
                        CD_MARCA,
                        CD_GP_FISCAL,
                        CD_NCM_SH,
                        CD_REF,
                        cd_usuario,
                        cd_filial,
                        cd_unidade_medida,
                        qt_estoque,
                        tx_ipi,
                        tx_iss
                );
                listaProduto.add(produto);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, SQLConsultagetTodos_Completo: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaProduto;
    }

    private void ListaTodosProdutos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Referencia");
        modelo.addColumn("Descricao");
        modelo.addColumn("Grupo");
        modelo.addColumn("Sub_Grupo");
        modelo.addColumn("Cor");
        modelo.addColumn("Cod.Fabrica");
        modelo.addColumn("Marca");
        modelo.addColumn("GrupoFiscal");
        modelo.addColumn("NCMSH");
        ProdutoDB produtosimplesdb = new ProdutoDB();
        ArrayList<ProdutoSimples> produtos_simples = produtosimplesdb.getTodos();
        for (ProdutoSimples auxProduto_Simples : produtos_simples) {
            modelo.addRow(new Object[]{
                auxProduto_Simples.getCd_prod(),
                auxProduto_Simples.getCd_ref(),
                auxProduto_Simples.getDs_prod(),
                auxProduto_Simples.getCd_grupo(),
                auxProduto_Simples.getCd_sub_grupo(),
                auxProduto_Simples.getCd_cor(),
                auxProduto_Simples.getCd_fabrica(),
                auxProduto_Simples.getCd_marca(),
                auxProduto_Simples.getCd_gp_fiscal(),
                auxProduto_Simples.getCd_ncm_sh()
            });
        }
        tbGrid.setModel(modelo);
    }

    public void ListaPessoasCompleto() {
        //Nesta Parte o ArrayList dos Usuarios(Que chama a Classe Usuario) recebe por parametro o Metodo "SQLConsultagetTodos_Completo()" que tera os dados da pesquisa
        //Este Metodo Chama o ArrayList  que tera os dados e passa para a DefaultTableModel
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Referencia");
        modelo.addColumn("Descricao");
        modelo.addColumn("Grupo");
        modelo.addColumn("Sub_Grupo");
        modelo.addColumn("Cor");
        modelo.addColumn("Cod.Fabrica");
        modelo.addColumn("Marca");
        modelo.addColumn("GrupoFiscal");
        modelo.addColumn("NCMSH");
        ArrayList<ProdutoSimples> produtos_simples = SQLConsultagetTodos_Completo();
        for (ProdutoSimples auxProduto_Simples : produtos_simples) {
            modelo.addRow(new Object[]{
                auxProduto_Simples.getCd_prod(),
                auxProduto_Simples.getCd_ref(),
                auxProduto_Simples.getDs_prod(),
                auxProduto_Simples.getCd_grupo(),
                auxProduto_Simples.getCd_sub_grupo(),
                auxProduto_Simples.getCd_cor(),
                auxProduto_Simples.getCd_fabrica(),
                auxProduto_Simples.getCd_marca(),
                auxProduto_Simples.getCd_gp_fiscal(),
                auxProduto_Simples.getCd_ncm_sh()
            });
        }
        tbGrid.setModel(modelo);
    }

    public ConsultaProduto(JTextField campoCodigo) {
        initComponents();
        ListaTodosProdutos();
        Centro();
        cbCampo.setModel(getComboCampo());
        cbValor.setModel(getComboValor());
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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(edtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPesquisa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
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
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(3, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Consulta Produto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(317, 317, 317)
                .addComponent(jLabel4)
                .addContainerGap(334, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
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

    private void edtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtPesquisaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ValidaCampoPesquisa();
        }
    }//GEN-LAST:event_edtPesquisaKeyPressed

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
    private javax.swing.JButton btnPesquisa;
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
