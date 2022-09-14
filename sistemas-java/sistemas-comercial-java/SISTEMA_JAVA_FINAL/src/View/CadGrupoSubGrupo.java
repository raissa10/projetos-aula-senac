package View;

import ControleCadastro.GrupoDB;
import ControleCadastro.SubGrupoDB;
import ModelCadastro.Grupo;
import ModelCadastro.SubGrupo;
import Principal.Conexao;
import Principal.MetodosGlobais;
import VisaoConsultasCadastro.ConsultaGrupo;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gelvazio Camargo
 */
public class CadGrupoSubGrupo extends MetodosGlobais {

    //Chama as classe d econexao d banco d dados 
    GrupoDB grupodb = new GrupoDB();
    SubGrupoDB subgrupodb = new SubGrupoDB();

    //Cria o ArrayList que vai armazenar os registros á serem alterados
    ArrayList listaCodigosSubgrupo = new ArrayList();

    int iteradorarrayCodigosSubgrupo = 0;
    int arrayCodigosSubgrupo[] = new int[iteradorarrayCodigosSubgrupo];

    private static final String sqlBuscaGrupo
            = "SELECT           "
            + "    GRUPO.*    "
            + "FROM           "
            + "    GRUPO      "
            + "WHERE          "
            + "    CD_GRUPO=? ";

    private static String codigosParaNaoDeletarSubGrupo = "-1";

    private static String sqlExcluirSubGrupoGrid
            = "";

    /**
     * Creates new form CadSubGrupo
     */
    public CadGrupoSubGrupo() {
        initComponents();
        Centro();
        habilitaCampos(false);
    }

    private void LimpaTela() {
        edtCodigo.setText("");
        edtNomeGrupo.setText("");
        edtCodigoSubGrupo.setText("");
        edtNomeSubGrupo.setText("");

        //Limpa o TableModel e o grid
        DefaultTableModel model = (DefaultTableModel) TabelaSubGrupo.getModel();
        model.setNumRows(0);
        edtCodigo.grabFocus();
    }

    private void habilitaCampos(boolean habilita) {
        edtCodigo.requestFocus();
        edtCodigo.setEnabled(!habilita);
        edtNomeGrupo.setEnabled(habilita);
        edtCodigoSubGrupo.setEnabled(habilita);
        edtNomeSubGrupo.setEnabled(habilita);
        TabelaSubGrupo.setEnabled(habilita);

        btnAdicionarSubGrupo.setEnabled(habilita);
        btnDeletarSubGrupo.setEnabled(habilita);
        btnGravar.setEnabled(habilita);
        btnCancelar.setEnabled(habilita);
        btnExcluir.setEnabled(habilita);
        btnConsulta.setEnabled(!habilita);
        //btnSair.setEnabled(habilita);

        if (habilita) {
            edtCodigo.requestFocus();
        } else {
            //Remover registros dos campos
            LimpaTela();
        }
    }

    private boolean verificaGrid() {
        int x = TabelaSubGrupo.getRowCount();
        return x > 0;
    }

    public void deletaItensSubGrupo() {
        String auxTexto = edtCodigo.getText();

        codigosParaNaoDeletarSubGrupo = "";
        for (int x = 0; x < TabelaSubGrupo.getRowCount(); x++) {
            //int auxcodigosubgrupo = (Integer.parseInt(String.valueOf(TabelaSubGrupo.getModel().getValueAt(x, 0))));
            String auxcodigosubgrupo = String.valueOf(TabelaSubGrupo.getModel().getValueAt(x, 0));
            //listaCodigosSubgrupo.add(auxcodigosubgrupo);
            codigosParaNaoDeletarSubGrupo = codigosParaNaoDeletarSubGrupo + "," + auxcodigosubgrupo;
        }
        sqlExcluirSubGrupoGrid
                = "DELETE FROM                   " + "\n"
                + "    SUB_GRUPO                 " + "\n"
                + "WHERE                         " + "\n"
                + "    SUB_GRUPO.CD_SUB_GRUPO NOT" + "\n"
                + "    IN (-2"
                + codigosParaNaoDeletarSubGrupo
                + ") AND SUB_GRUPO.CD_GRUPO="
                + auxTexto + ";";

        //JOptionPane.showMessageDialog(null, "vl fim string: \n" + sqlExcluirSubGrupoGrid);
    }

    public void adicionarGrupo() {
        //Programacao Pronta Generator
        ValidaCodigoGenerator();
        //Habilitação dos campos
        habilitaCampos(true);
    }

    public void excluirSubGrupoForaGrid() {
        //Alimenta o string do SQL:
        deletaItensSubGrupo();
        //valor do SQL:
        // JOptionPane.showMessageDialog(null, "SQL oficial que "
        //        + "executa na base: \n" + sqlExcluirSubGrupoGrid);
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlExcluirSubGrupoGrid);
            pstmt.executeUpdate();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql. excluirSubGrupoForaGrid(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        //Limpa  SQL paa usar em nova exclusão
        codigosParaNaoDeletarSubGrupo = "";
    }

    public void limpabancoegridsubgrup() {
        excluirSubGrupoForaGrid();
    }

    public void insereAlteraDeletaSubGrupo() {
        String auxTexto = edtCodigo.getText();
        int auxCodigoGrupo = Integer.parseInt(auxTexto);
        try {
            if (verificaGrid()) {
                //Antes de Iniciar as gravações no banco do que esta no grid
                //Eu deleto todos os grupos que foram deletados
                //E apenas executa o delete se existir pelo menos 1 
                //registro deletado da tela
                //Para isso,criar um ArrayList que guarda os codigos
                //dos subgrupos deletados lá na funcão de deleta subgrupos

                for (int x = 0; x < TabelaSubGrupo.getRowCount(); x++) {
                    int auxcodigosubgrupo = (Integer.parseInt(String.valueOf(TabelaSubGrupo.getModel().getValueAt(x, 0))));
                    String auxnomesubgrupo = String.valueOf(TabelaSubGrupo.getModel().getValueAt(x, 1));

                    //Armazena o codigo para nao deletar depois
                    //arrayCodigosSubgrupo[x] = auxCodigoGrupo;
                    //iteradorarrayCodigosSubgrupo++;
                    //Passa os dados da tela para a classe de subgrupo
                    int auxcd_usuario = 1;

                    SubGrupo subgrupo = new SubGrupo(
                            auxCodigoGrupo,
                            auxcodigosubgrupo,
                            auxcd_usuario,
                            auxnomesubgrupo
                    );
                    //Verifica se o subgrupo já existe
                    //Se existe , altera , senao grava o sub_grupo
                    //Tambem apenas pega o registro que está na tela
                    //Ou seja se eu já deletei ele do grid ele nao esta mais nesta seleção.
                    if (subgrupodb.getSubGrupo(auxCodigoGrupo, auxcodigosubgrupo)) {
                        //altera
                        //JOptionPane.showMessageDialog(null, "Entrou em alterar!");
                        if (subgrupodb.alterarSubGrupo(subgrupo)) {
                            //     JOptionPane.showMessageDialog(null, "Registro do Subgrupo alterado com sucesso! "+auxcodigosubgrupo);                           
                        } else {
                            JOptionPane.showMessageDialog(null, "Não foi possível alterar o registro do Subgrupo: " + x);
                        }
                    } else {
                        //insere o sub-grupo
                        //JOptionPane.showMessageDialog(null, "codigo
                        if (subgrupodb.inserirSubGrupo(subgrupo)) {
                            //JOptionPane.showMessageDialog(null, "Registro do Subgrupo inserido com sucesso!");
                            //if (auxcodigosubgrupo == TabelaSubGrupo.getRowCount()) {
                            //JOptionPane.showMessageDialog(null, "Ultimo Registro do Subgrupo: " + auxcodigosubgrupo);
                            //}
                        } else {
                            //JOptionPane.showMessageDialog(null, "Valor repassado: " + auxcodigosubgrupo);
                            JOptionPane.showMessageDialog(null, "Não foi possível inserir o registro do Subgrupo: " + x);
                        }
                    }
                }
                //Chama a classe de subgrupo e 
                //que deleta os dados que não estão no grid
                limpabancoegridsubgrup();
            } else {
                //Deleta todos os Subgrupos deste Grupo para a alteração se efetivar
                //Por que não existe nenhum grupo na grade
                int auxCodigo = Integer.parseInt(edtCodigo.getText());
                if (subgrupodb.excluirSubGrupoInteiro(auxCodigo)) {
                    //JOptionPane.showMessageDialog(null, "Exclusão de todos os subgrupos efetuada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel excluir todos os subgrupos deste grupo!");
                }
            }
        } catch (NumberFormatException | HeadlessException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao gravar item do subgrupo! " + erro.getMessage());
        } finally {
            //JOptionPane.showMessageDialog(null, "Finalizando erro ao gravar subgrupo!");
        }
        //Limpa a tela
        habilitaCampos(false);
    }

    public ArrayList sqlCarregaNomeSubGrupos() {
        String auxtexto = edtCodigo.getText();
        int codigogrupo = Integer.parseInt(auxtexto);
        String sqlConsultaSubGrupo
                = "SELECT                   "
                + "    SUB_GRUPO.*	    "
                + "FROM	                    "
                + "    SUB_GRUPO	    "
                + "WHERE	            "
                + "    SUB_GRUPO.cd_GRUPO=? ";

        ArrayList listaSubGrupo = new ArrayList();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            pstmt = conn.prepareStatement(sqlConsultaSubGrupo);
            pstmt.setInt(1, codigogrupo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int auxcdgrupo = rs.getInt("cd_grupo");
                int auxcdsubgrupo = rs.getInt("cd_sub_grupo");
                int auxcdusuario = rs.getInt("cd_usuario");
                String auxdssubgrupo = rs.getString("ds_sub_grupo");

                SubGrupo subgrupo = new SubGrupo(
                        auxcdgrupo,
                        auxcdsubgrupo,
                        auxcdusuario,
                        auxdssubgrupo);
                listaSubGrupo.add(subgrupo);
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql,  sqlCarregaNomeSubGrupos(): \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
        return listaSubGrupo;
    }

    public void listaItensSubGrupo() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Codigo");
        modelo.addColumn("Nome");
        habilitaCampos(true);
        int auxcodigogrupo = Integer.parseInt(edtCodigo.getText());

        ArrayList<SubGrupo> subgrupo = sqlCarregaNomeSubGrupos();
        //aqui verifica se existe item DE SUBGRUPO DESTE GRUPO       
        if (subgrupodb.getSubGrupoPeloGrupo(auxcodigogrupo)) {
            for (SubGrupo auxsubgrupo : subgrupo) {
                modelo.addRow(new Object[]{
                    auxsubgrupo.getCd_sub_grupo(),
                    auxsubgrupo.getDs_sub_grupo()
                });
                TabelaSubGrupo.setModel(modelo);
                iteradorarrayCodigosSubgrupo++;
            }
            //Aqui recebe o valor inicial do grid para o iterador
            //Que vai fazer o controle no banco de dados
            //iteradorarrayCodigosSubgrupo=TabelaSubGrupo.getRowCount();
            //JOptionPane.showMessageDialog(null, "Tamanho do array: " + iteradorarrayCodigosSubgrupo);
            //Iniciando array e deixando os dados zerados
        } else {
            JOptionPane.showMessageDialog(null, "Não existe subgrupos deste grupo!");
            edtNomeGrupo.requestFocus();
        }
    }

    private void GravarCompletoValidado() {
        //Validado apenas os campos NOT NULL  do banco de dados        
        String auxTexto = edtCodigo.getText();
        String auxNome = edtNomeGrupo.getText();
        if (auxTexto.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o Código do Grupo!");
            edtCodigo.grabFocus();
        } else if (auxNome.equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Preencher o nome do Grupo!");
            edtNomeGrupo.grabFocus();
        } else {
            GravarAlterarRegistro();
        }
    }

    private void GravarAlterarRegistro() {
        String auxTexto = edtCodigo.getText();
        int codigo = Integer.parseInt(auxTexto);
        if (grupodb.getGrupo(codigo)) {
            AlterarRegistro();
        } else {
            GravarRegistro();
        }
    }

    private void ExcluirRegistro() {
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            int auxCodigo = Integer.parseInt(edtCodigo.getText());
            if (subgrupodb.excluirSubGrupoInteiro(auxCodigo)) {
                if (grupodb.excluirGrupo(auxCodigo)) {
                    JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!");
                    habilitaCampos(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possivel excluir o registro!!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possivel excluir o subgrupo inteiro!");
            }
        }

    }

    private void GravarRegistro() {
        String auxTexto = edtCodigo.getText();
        String auxNome = edtNomeGrupo.getText();

        int auxcd_usuario = 1;//= Integer.parseInt(login.getCodigo());
        int auxCodigo = Integer.parseInt(auxTexto);
        Grupo grupo = new Grupo(
                auxCodigo,
                auxNome,
                auxcd_usuario
        );
        if (grupodb.inserirGrupo(grupo)) {
            JOptionPane.showMessageDialog(null, "Registro incluído com sucesso!");
            insereAlteraDeletaSubGrupo();
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir o registro!");
            edtCodigo.grabFocus();
        }
    }

    private void AlterarRegistro() {
        String auxTexto = edtCodigo.getText();
        String auxNome = edtNomeGrupo.getText();
        int auxcd_usuario = 1;
        int auxCodigo = Integer.parseInt(auxTexto);
        Grupo grupo = new Grupo(
                auxCodigo,
                auxNome,
                auxcd_usuario
        );
        if (grupodb.alterarGrupo(grupo)) {
            //Acessa o método de insereAlteraDeletaSubGrupo.
            //Este metodo verifica se é para inserir,deletar ou alterar o 
            //registro do grid
            insereAlteraDeletaSubGrupo();
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o registro!");
            edtCodigo.grabFocus();
        }
    }

    private void ValidaCodigoGenerator() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexao.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT GEN_ID(CD_GRUPO, 1) FROM RDB$DATABASE");
            while (rs.next()) {
                int auxCodigoGenerator = rs.getInt("GEN_ID");
                int auxCodigo = auxCodigoGenerator + 1;
                String a;
                a = "" + auxCodigo;
                edtCodigo.setText(a);
                edtNomeGrupo.grabFocus();
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro de conexão! \n" + erro.getMessage());
        } finally {
            Conexao.closeAll(conn);
        }
    }

    private void ValidaCampoCodigoTela() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int cd_grupo = Integer.parseInt(edtCodigo.getText());

        if (grupodb.getGrupo(cd_grupo)) {
            habilitaCampos(true);
            edtNomeGrupo.grabFocus();
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaGrupo);
                pstmt.setInt(1, cd_grupo);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auxDS_GRUPO = rs.getString("DS_GRUPO");
                    edtNomeGrupo.setText(auxDS_GRUPO);
                    edtNomeGrupo.grabFocus();
                    //Aqui após buscar o grupo deve carregar os registros de sub grupo
                    listaItensSubGrupo();
                }
            } catch (SQLException erro) {
                JOptionPane.showMessageDialog(null, "Erro de conexão no Método ValidaCampoCodigoNãoNulo()! " + erro.getMessage());
            }
        } else {
            int resposta = JOptionPane.showConfirmDialog(null, "Grupo não Cadastrado! \n Deseja cadastrar?");
            if (resposta == JOptionPane.YES_OPTION) {
                habilitaCampos(true);
            } else {
                habilitaCampos(false);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        edtCodigo = new javax.swing.JTextField();
        edtNomeGrupo = new javax.swing.JTextField();
        btnAdicionarSubGrupo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnConsulta = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        edtNomeSubGrupo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        edtCodigoSubGrupo = new javax.swing.JTextField();
        btnGravar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaSubGrupo = new javax.swing.JTable();
        btnDeletarSubGrupo = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Cadastro de Grupos");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, 24));

        jLabel2.setText("Descrição:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 55, -1));

        jLabel5.setText("Código:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 55, -1));

        edtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoKeyPressed(evt);
            }
        });
        getContentPane().add(edtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 80, 25));

        edtNomeGrupo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtNomeGrupoKeyPressed(evt);
            }
        });
        getContentPane().add(edtNomeGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 310, 25));

        btnAdicionarSubGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Add.png"))); // NOI18N
        btnAdicionarSubGrupo.setText("Adicionar");
        btnAdicionarSubGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarSubGrupoActionPerformed(evt);
            }
        });
        btnAdicionarSubGrupo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAdicionarSubGrupoKeyPressed(evt);
            }
        });
        getContentPane().add(btnAdicionarSubGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, 120, 40));

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
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 140, 120, 40));

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
        getContentPane().add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 190, 120, 40));

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
        getContentPane().add(btnConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 240, 120, 40));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        getContentPane().add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 290, 120, 40));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("Sub-Grupos");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, 160, -1));

        jLabel7.setText("Nome:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, -1, -1));

        edtNomeSubGrupo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtNomeSubGrupoKeyPressed(evt);
            }
        });
        getContentPane().add(edtNomeSubGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 150, 25));

        jLabel8.setText("Código:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 55, -1));

        edtCodigoSubGrupo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoSubGrupoKeyPressed(evt);
            }
        });
        getContentPane().add(edtCodigoSubGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 60, 25));

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
        getContentPane().add(btnGravar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, 120, 40));

        TabelaSubGrupo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome"
            }
        ));
        TabelaSubGrupo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaSubGrupoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TabelaSubGrupoMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(TabelaSubGrupo);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 220, 530, 250));

        btnDeletarSubGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Remove Red.png"))); // NOI18N
        btnDeletarSubGrupo.setText("Deletar");
        btnDeletarSubGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarSubGrupoActionPerformed(evt);
            }
        });
        getContentPane().add(btnDeletarSubGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, 120, 40));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 780, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void edtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoKeyPressed
        
        String auxTexto = edtCodigo.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                habilitaCampos(true);
                ValidaCodigoGenerator();
                edtNomeGrupo.grabFocus();
            } else {
                ValidaCampoCodigoTela();
            }
        }
    }//GEN-LAST:event_edtCodigoKeyPressed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        
        dispose();
        habilitaCampos(false);
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnExcluirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnExcluirKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnConsulta.grabFocus();
        }
    }//GEN-LAST:event_btnExcluirKeyPressed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        
        ExcluirRegistro();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnCancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCancelarKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnExcluir.grabFocus();
        }
    }//GEN-LAST:event_btnCancelarKeyPressed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Cancelar a Edição?");
        if (resposta == JOptionPane.YES_OPTION) {
            habilitaCampos(false);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAdicionarSubGrupoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAdicionarSubGrupoKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!edtNomeSubGrupo.getText().equals("")) {
                DefaultTableModel model = (DefaultTableModel) TabelaSubGrupo.getModel();
                Object[] linha = {
                    edtCodigoSubGrupo.getText(),
                    edtNomeSubGrupo.getText()
                };
                model.addRow(linha);
                edtCodigoSubGrupo.setText("");
                edtNomeSubGrupo.setText("");
                edtCodigoSubGrupo.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Informe um Nome!");
                edtNomeSubGrupo.requestFocus();
            }
        }
    }//GEN-LAST:event_btnAdicionarSubGrupoKeyPressed

    private void btnAdicionarSubGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarSubGrupoActionPerformed
        
        if (!edtNomeSubGrupo.getText().equals("")) {
            DefaultTableModel model = (DefaultTableModel) TabelaSubGrupo.getModel();
            Object[] linha = {
                edtCodigoSubGrupo.getText(),
                edtNomeSubGrupo.getText()
            };
            model.addRow(linha);
            edtCodigoSubGrupo.setText("");
            edtNomeSubGrupo.setText("");
            edtCodigoSubGrupo.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "Informe um Nome!");
            edtNomeSubGrupo.requestFocus();
        }
    }//GEN-LAST:event_btnAdicionarSubGrupoActionPerformed

    private void edtNomeGrupoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtNomeGrupoKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            edtCodigoSubGrupo.requestFocus();
        }
    }//GEN-LAST:event_edtNomeGrupoKeyPressed

    private void btnConsultaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnConsultaKeyPressed
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSair.grabFocus();
        }
    }//GEN-LAST:event_btnConsultaKeyPressed

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        
        ConsultaGrupo form = new ConsultaGrupo(edtCodigo);
        this.getDesktopPane().add(form);
        form.setVisible(true);
        edtCodigo.grabFocus();
    }//GEN-LAST:event_btnConsultaActionPerformed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        
        GravarCompletoValidado();
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnGravarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGravarKeyPressed
        
    }//GEN-LAST:event_btnGravarKeyPressed

    private void edtNomeSubGrupoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtNomeSubGrupoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!edtNomeSubGrupo.getText().equals("")) {
                btnAdicionarSubGrupo.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Informe um Nome!");
                edtNomeSubGrupo.requestFocus();
            }
        }
    }//GEN-LAST:event_edtNomeSubGrupoKeyPressed

    private void edtCodigoSubGrupoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoSubGrupoKeyPressed
        
        String auxTexto = edtCodigoSubGrupo.getText();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                JOptionPane.showMessageDialog(null, "Codigo do subgrupo nulo!");
                edtCodigoSubGrupo.requestFocus();
            } else {
                //Valida o Campo Codigo SubGrupo  Nao  Nulo                     
                int auxTotalSubGrupos = TabelaSubGrupo.getRowCount();
                String auxCodigoSubgrupoTela = edtCodigoSubGrupo.getText();
                int auxCodigo = Integer.parseInt(auxCodigoSubgrupoTela);
                try {
                    if (verificaGrid()) {
                        for (int x = 0; x < TabelaSubGrupo.getRowCount(); x++) {
                            int auxcodigosubgrupo = (Integer.parseInt(String.valueOf(TabelaSubGrupo.getModel().getValueAt(x, 0))));
                            String auxnomegrid = String.valueOf(TabelaSubGrupo.getModel().getValueAt(x, 1));
                            //Se o código da tabela for igual ao que estou digitando
                            //Seto o nome do campo para este
                            if (auxcodigosubgrupo == auxCodigo) {
                                edtNomeSubGrupo.setText(auxnomegrid);
                            } else if (x == (auxTotalSubGrupos - 1)) {
                                edtNomeSubGrupo.requestFocus();
                            }
                        }
                    } else {
                        //Seta o foco para o campo do nome
                        edtNomeSubGrupo.requestFocus();
                    }
                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, "Erro ao pesquisar os dados do grid!!: " + erro.getMessage());
                } finally {
                    //JOptionPane.showMessageDialog(null, "Finalizando o 'Erro ao pesquisar os dados do grid!!'");
                }
            }
        }

    }//GEN-LAST:event_edtCodigoSubGrupoKeyPressed

    private void TabelaSubGrupoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaSubGrupoMouseClicked
        
        int linha = TabelaSubGrupo.getSelectedRow();
        if (linha >= 0) {
            String auxCodigo = TabelaSubGrupo.getValueAt(linha, 0).toString();
            String auxNome = TabelaSubGrupo.getValueAt(linha, 1).toString();
            edtCodigoSubGrupo.setText(auxCodigo);
            edtNomeSubGrupo.setText(auxNome);

        }
    }//GEN-LAST:event_TabelaSubGrupoMouseClicked

    private void btnDeletarSubGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarSubGrupoActionPerformed
        String auxCodigo = edtCodigoSubGrupo.getText();
        if (verificaGrid()) {
            //JOptionPane.showMessageDialog(null, "Quantidade de itens do grid: "+TabelaSubGrupo.getRowCount());
            if (auxCodigo.equals("")) {
                JOptionPane.showMessageDialog(null, "Selecione um subgrupo clicando no grid!");
                btnDeletarSubGrupo.requestFocus();
            } else {
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Deletar o registro selecionado?");
                if (resposta == JOptionPane.YES_OPTION) {

                    int Codigo = TabelaSubGrupo.getSelectedRow();
                    //JOptionPane.showMessageDialog(null, "Codigo pego do grid: " + Codigo);

                    //TabelaSubGrupo.remove(TabelaSubGrupo.getSelectedRow());                
                    //TabelaSubGrupo.remove(TabelaSubGrupo.getSelectedRow());
                    //TabelaSubGrupo.remove(TabelaSubGrupo.getSelectedColumn()); 
                    //((DefaultTableModel) suaTabela.getModel()).removeRow(indiceDaLinha);  
                    //Importante:
                    //Sempre que mexer no grid deve chamar o DefaultTableModel
                    //e passar o grid pra ele e depois executar a função desejada.
                    DefaultTableModel modelo = (DefaultTableModel) TabelaSubGrupo.getModel();
                    modelo.removeRow(Codigo);

                    //Adiciona o codigo atual ao arraylist
                    // int Codigo1 = TabelaSubGrupo.getRowCount();
                    //Falta iniciar o array
                    //arrayCodigosSubgrupo[Codigo1] = Codigo;
                    //Incrementa 1 no iterador do array
                    //iteradorarrayCodigosSubgrupo++;TabelaSubGrupo.getRowCount()
                    //Limpa os campos da tela
                    edtCodigoSubGrupo.setText("");
                    edtNomeSubGrupo.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Voce cancelou a exclusão do item!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Grid Não aceito!");
        }
    }//GEN-LAST:event_btnDeletarSubGrupoActionPerformed

    private void TabelaSubGrupoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaSubGrupoMousePressed
        
        int linha = TabelaSubGrupo.getSelectedRow();
        if (linha >= 0) {
            String auxCodigo = TabelaSubGrupo.getValueAt(linha, 0).toString();
            String auxNome = TabelaSubGrupo.getValueAt(linha, 1).toString();
            edtCodigoSubGrupo.setText(auxCodigo);
            edtNomeSubGrupo.setText(auxNome);

        }
    }//GEN-LAST:event_TabelaSubGrupoMousePressed

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
            java.util.logging.Logger.getLogger(CadGrupoSubGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadGrupoSubGrupo().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelaSubGrupo;
    private javax.swing.JButton btnAdicionarSubGrupo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JButton btnDeletarSubGrupo;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JTextField edtCodigo;
    private javax.swing.JTextField edtCodigoSubGrupo;
    private javax.swing.JTextField edtNomeGrupo;
    private javax.swing.JTextField edtNomeSubGrupo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
