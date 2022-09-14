package VisaoConsultasCadastro;

import ControleCadastro.PessoaDB;
import ModelCadastro.Pessoa;
import Principal.Conexao;
import Principal.MetodosGlobais;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gelvazio
 */
public class ConsultaPessoa extends MetodosGlobais {

    String mensagem;
    String mensagem_dois;
    String SQLConsulta_Pessoa;
    JTextField campoCodigo;

    /**
     * Creates new form LocalizaPessoa
     */
    private DefaultComboBoxModel getComboCampo() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        String auxCodigo = "Codigo";
        String auxNome = "Nome";
        try {
            modelo.addElement(auxCodigo);
            modelo.addElement(auxNome);
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

    private void PegaValorCamposComboboxCampo_E_Valor(String SQLValorCamposComboboxCampo_E_Valor) {
        String auxedtPesquisa = null;
        String auxCampo = cbCampo.getSelectedItem().toString();
        String auxValor = cbValor.getSelectedItem().toString();
        //Programação do SQL do Codigo
        if ((auxCampo.equals("Codigo")) & (auxValor.equals("Igual"))) {
            auxCampo = "cd_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Diferente"))) {
            auxCampo = "cd_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<>" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Maior"))) {
            auxCampo = "cd_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Menor"))) {
            auxCampo = "cd_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("MaiorIgual"))) {
            auxCampo = "cd_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("MenorIgual"))) {
            auxCampo = "cd_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<=" + auxedtPesquisa;
        } else if ((auxCampo.equals("Codigo")) & (auxValor.equals("Parecido"))) {
            auxCampo = "cd_pessoa";
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
            auxCampo = "nm_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "LIKE UPPER('" + auxedtPesquisa + "%')";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("Diferente"))) {
            auxCampo = "nm_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<> '" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("Maior"))) {
            auxCampo = "nm_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">'" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("Menor"))) {
            auxCampo = "nm_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<'" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("MaiorIgual"))) {
            auxCampo = "nm_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = ">='" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("MenorIgual"))) {
            auxCampo = "nm_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "<='" + auxedtPesquisa + "'";
        } else if ((auxCampo.equals("Nome")) & (auxValor.equals("Parecido"))) {
            auxCampo = "nm_pessoa";
            auxedtPesquisa = edtPesquisa.getText();
            auxValor = "LIKE UPPER('" + auxedtPesquisa + "%')";
        }

        SQLValorCamposComboboxCampo_E_Valor = "select * from PESSOA_SIMPLES where " + auxCampo + " " + auxValor;
        //A Variavel global "SQLConsulta_Pessoa" recebe por parametro a variavel "SQLValorCamposComboboxCampo_E_Valor"
        SQLConsulta_Pessoa = SQLValorCamposComboboxCampo_E_Valor;
    }

    private void ValidaCampoPesquisa() {
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

    private ArrayList SQLConsultagetTodos_Completo() {
        //Aqui é chamado o Metodo "PegaValorCamposComboboxCampo_E_Valor("");" para pegar os valores da tela
        //Caso nao seja repassado ele nao da certo pois nao pega nada do edtPesquisa
        PegaValorCamposComboboxCampo_E_Valor("");
        ArrayList listaPessoa = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            //Nessa Parte é passado po parametro os Dados da Variavel "SQLConsulta_Pessoa" que contem o sql da pesquisa.
            rs = stmt.executeQuery(SQLConsulta_Pessoa);
            while (rs.next()) {
                int cd_pessoa = rs.getInt("CD_PESSOA");
                String nm_pessoa = rs.getString("NM_PESSOA");
                int fg_cliente = rs.getInt("fg_cliente");
                int fg_vendedor = rs.getInt("fg_vendedor");
                int fg_fornecedor = rs.getInt("fg_fornecedor");
                String ds_endereco = rs.getString("ds_endereco");
                String nr_endereco = rs.getString("nr_endereco");
                String ds_bairro = rs.getString("ds_bairro");
                String cd_estado = rs.getString("cd_estado");
                int cd_cidade = rs.getInt("cd_cidade");
                int cd_pais = rs.getInt("cd_pais");
                String cd_cep = rs.getString("cd_cep");
                String ds_email = rs.getString("ds_email");
                String nr_telefone = rs.getString("nr_telefone");
                String cd_cgccpf = rs.getString("cd_cgccpf");
                int cd_usuario = rs.getInt("cd_usuario");
                Date dt_alt = rs.getDate("dt_alt");
                Time hr_alt = rs.getTime("hr_alt");
                Date dt_cad = rs.getDate("dt_cad");
                Time hr_cad = rs.getTime("hr_cad");
                int cd_filial = rs.getInt("cd_filial");
                String nr_inscricao_estadual = rs.getString("nr_inscricao_estadual");
                int tipo_consumo = rs.getInt("tipo_consumo");
                int regime_tributacao = rs.getInt("regime_tributacao");
                int fg_transportador = rs.getInt("fg_transportador");
                int fg_ativo = rs.getInt("fg_ativo");

                Pessoa pessoa = new Pessoa(
                        cd_pessoa,
                        nm_pessoa,
                        fg_cliente,
                        fg_vendedor,
                        fg_fornecedor,
                        ds_endereco,
                        nr_endereco,
                        ds_bairro,
                        cd_estado,
                        cd_cidade,
                        cd_pais,
                        cd_cep,
                        ds_email,
                        nr_telefone,
                        cd_cgccpf,
                        cd_usuario,
                        dt_alt,
                        hr_alt,
                        dt_cad,
                        hr_cad,
                        cd_filial,
                        nr_inscricao_estadual,
                        tipo_consumo,
                        regime_tributacao,
                        fg_transportador,
                        fg_ativo
                );
                listaPessoa.add(pessoa);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, SQLConsultagetTodos_Completo: \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaPessoa;
    }

    private void ListaTodasPessoas() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nome");
        modelo.addColumn("CPF");
        modelo.addColumn("CEP");
        modelo.addColumn("Cidade");
        modelo.addColumn("Estado");
        modelo.addColumn("Pais");
        modelo.addColumn("Bairro");
        modelo.addColumn("E-mail");
        modelo.addColumn("Endereço");
        modelo.addColumn("NR Endereço");

        PessoaDB pessoadb = new PessoaDB();
        ArrayList<Pessoa> pessoas = pessoadb.getTodos();
        for (Pessoa auxPessoa : pessoas) {
            modelo.addRow(new Object[]{
                auxPessoa.getCd_pessoa(),
                auxPessoa.getNm_pessoa(),
                auxPessoa.getCd_cgccpf(),
                auxPessoa.getCd_cep(),
                auxPessoa.getCd_cidade(),
                auxPessoa.getCd_estado(),
                auxPessoa.getCd_pais(),
                auxPessoa.getDs_bairro(),
                auxPessoa.getDs_email(),
                auxPessoa.getDs_endereco(),
                auxPessoa.getNr_endereco(),
                auxPessoa.getCd_filial()
            });
        }
        tbGrid.setModel(modelo);
    }

    public void ListaPessoasCompleto() {
        //Nesta Parte o ArrayList dos Usuarios(Que chama a Classe Usuario) recebe por parametro o Metodo "SQLConsultagetTodos_Completo()" que tera os dados da pesquisa
        //Este Metodo Chama o ArrayList  que tera os dados e passa para a DefaultTableModel
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nome");
        modelo.addColumn("CPF");
        modelo.addColumn("CEP");
        modelo.addColumn("Cidade");
        modelo.addColumn("Estado");
        modelo.addColumn("Pais");
        modelo.addColumn("Bairro");
        modelo.addColumn("E-mail");
        modelo.addColumn("Endereço");
        modelo.addColumn("Numero Endereço");
        ArrayList<Pessoa> pessoas = SQLConsultagetTodos_Completo();
        for (Pessoa auxPessoa : pessoas) {
            modelo.addRow(new Object[]{
                auxPessoa.getCd_pessoa(),
                auxPessoa.getNm_pessoa(),
                auxPessoa.getCd_cgccpf(),
                auxPessoa.getCd_cep(),
                auxPessoa.getCd_cidade(),
                auxPessoa.getCd_estado(),
                auxPessoa.getCd_pais(),
                auxPessoa.getDs_bairro(),
                auxPessoa.getDs_email(),
                auxPessoa.getDs_endereco(),
                auxPessoa.getNr_endereco(),
                auxPessoa.getCd_filial()
            });
        }
        tbGrid.setModel(modelo);
    }

    public ConsultaPessoa(JTextField campoCodigo) {
        initComponents();
        ListaTodasPessoas();
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
        jLabel4.setText("Consulta Pessoa");

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
                .addContainerGap(342, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
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
