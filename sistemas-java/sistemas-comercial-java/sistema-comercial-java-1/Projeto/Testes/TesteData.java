/*
 * Copyright (C) 2015 Gelvazio Camargo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Testes;

import Principal.Conexao;
import Principal.MetodosGlobais;
import VisaoFaturamento.FormularioPedidoCompleto;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gelvazio Camargo
 */
public class TesteData extends MetodosGlobais {

    private static final String sqlAlterar = "UPDATE TESTE_DATE SET NM_PESSOA = ?,"
            + "    DT_EMI_DOC = ?"
            //+ "    DT_SAI_DOC = ?,"
            // + "    DT_ALT = ?,"
            // + "    DT_CAD =?,"
            // + "    HR_ALT = ?,"
            // + "    HR_CAD = ?"
            + "WHERE (CD_PESSOA = ?);";
    private static final String sqlAlterarAutomatico
            = "UPDATE TESTE_DATE SET NM_PESSOA = ?,"
            + "    DT_EMI_DOC = CAST('NOW' AS DATE),"
            + "    DT_SAI_DOC = CAST('NOW' AS DATE),"
            + "    DT_ALT = CAST('NOW' AS DATE),"
            + "    DT_CAD =CAST('NOW' AS DATE),"
            + "    HR_ALT =CAST('NOW' AS TIME),"
            + "    HR_CAD = CAST('NOW' AS TIME)"
            + "WHERE (CD_PESSOA = ?);";

    private static final String sqlInserir
            = "INSERT INTO TESTE_DATE"
            + " (CD_PESSOA, NM_PESSOA, "
            + "DT_EMI_DOC, DT_SAI_DOC, "
            + "DT_ALT, DT_CAD, HR_ALT,"
            + " HR_CAD) VALUES "
            + "(1, 'GELVAZIO TESTES', "
            + "'6-JAN-2015', '21-JAN-2015',"
            + " '8-JAN-2015', '3-FEB-2015',"
            + " '02:00:00', '04:00:00');";
    private static final String sqlInserirAutomatico
            = "INSERT INTO TESTE_DATE"
            + " (CD_PESSOA, NM_PESSOA, "
            + "DT_EMI_DOC, DT_SAI_DOC, "
            + "DT_ALT, DT_CAD, HR_ALT,"
            + " HR_CAD) VALUES "
            + "(?, ?, "
            + "CAST('NOW' AS DATE),CAST('NOW' AS DATE),"
            + " CAST('NOW' AS DATE),CAST('NOW' AS DATE),"
            + " CAST('NOW' AS TIME), CAST('NOW' AS TIME));";
    private static final String sqlVerificaSeExiste
            = "       SELECT                           "
            + "            (TESTE_DATE.CD_PESSOA)as codigosql    "
            + "           FROM                             "
            + "                TESTE_DATE                      "
            + "            where                             "
            + "               TESTE_DATE.CD_PESSOA=?         ";
    private static final String sqlBuscaDados
            = "       SELECT                           "
            + "            TESTE_DATE.*   "
            + "           FROM                             "
            + "                TESTE_DATE                      "
            + "            where                             "
            + "               TESTE_DATE.CD_PESSOA=?         ";
    java.util.Date data = new java.util.Date();
    SimpleDateFormat formatadata = new SimpleDateFormat("dd/MM/yyyy");
    GregorianCalendar hora = new GregorianCalendar();
    SimpleDateFormat formatahora = new SimpleDateFormat("HH:mm:hh");

    public TesteData() {
        initComponents();
        Centro();
    }

    private class ClasseTesteData {

        private int Codigo;
        String Nome;
        Date DataEmissao;
        private Date DataSaida;
        private Date DataAlteracao;
        private Date DataCadastro;
        private Date HoraAlteracao;
        private Date HoraCadastro;

        public ClasseTesteData(int Codigo, String Nome, Date DataEmissao, Date DataSaida, Date DataAlteracao, Date DataCadastro, Date HoraAlteracao, Date HoraCadastro) {
            this.Codigo = Codigo;
            this.Nome = Nome;
            this.DataEmissao = DataEmissao;
            this.DataSaida = DataSaida;
            this.DataAlteracao = DataAlteracao;
            this.DataCadastro = DataCadastro;
            this.HoraAlteracao = HoraAlteracao;
            this.HoraCadastro = HoraCadastro;
        }

        public int getCodigo() {
            return Codigo;
        }

        public void setCodigo(int Codigo) {
            this.Codigo = Codigo;
        }

        public String getNome() {
            return Nome;
        }

        public void setNome(String Nome) {
            this.Nome = Nome;
        }

        public Date getDataEmissao() {
            return DataEmissao;
        }

        public void setDataEmissao(Date DataEmissao) {
            this.DataEmissao = DataEmissao;
        }

        public Date getDataSaida() {
            return DataSaida;
        }

        public void setDataSaida(Date DataSaida) {
            this.DataSaida = DataSaida;
        }

        public Date getDataAlteracao() {
            return DataAlteracao;
        }

        public void setDataAlteracao(Date DataAlteracao) {
            this.DataAlteracao = DataAlteracao;
        }

        public Date getDataCadastro() {
            return DataCadastro;
        }

        public void setDataCadastro(Date DataCadastro) {
            this.DataCadastro = DataCadastro;
        }

        public Date getHoraAlteracao() {
            return HoraAlteracao;
        }

        public void setHoraAlteracao(Date HoraAlteracao) {
            this.HoraAlteracao = HoraAlteracao;
        }

        public Date getHoraCadastro() {
            return HoraCadastro;
        }

        public void setHoraCadastro(Date HoraCadastro) {
            this.HoraCadastro = HoraCadastro;
        }

    }

    private class TesteDataDB {

        private boolean alterar(ClasseTesteData classetestedata) {
            boolean alterou = false;
            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlAlterar);
                pstmt.setString(1, classetestedata.getNome());
                //String DataEmissao;
                //DataEmissao = new Date(formatadata.format(classetestedata.getDataEmissao()));
                //pstmt.setDate(2, classetestedata.getDataEmissao());
                pstmt.setDate(2, classetestedata.getDataEmissao());
                //pstmt.setDate(3, classetestedata.getDataSaida());
                // pstmt.setDate(4, classetestedata.getDataAlteracao());
                //pstmt.setDate(5, classetestedata.getDataCadastro());
                //pstmt.setDate(6, classetestedata.getHoraAlteracao());
                //pstmt.setDate(7, classetestedata.getHoraCadastro());
                // pstmt.setInt(8, classetestedata.getCodigo());
                pstmt.setInt(3, classetestedata.getCodigo());
                pstmt.executeUpdate();
                alterou = true;
            } catch (SQLException erro) {
                mensagemErro("Erro no sql. alterar(): \n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
            return alterou;
        }

        private boolean alterarAutomatico(ClasseTesteData classetestedata) {
            boolean alterou = false;
            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlAlterarAutomatico);
                pstmt.setString(1, classetestedata.getNome());
                pstmt.setInt(2, classetestedata.getCodigo());
                pstmt.executeUpdate();
                alterou = true;
            } catch (SQLException erro) {
                mensagemErro("Erro no sql. alterarAutomatico(): \n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
            return alterou;
        }

        private boolean inserir(ClasseTesteData classetestedata) {
            boolean inseriu = false;
            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlInserir);
                pstmt.setInt(1, classetestedata.getCodigo());
                pstmt.setString(2, classetestedata.getNome());
                pstmt.setDate(3, classetestedata.getDataEmissao());
                pstmt.setDate(4, classetestedata.getDataSaida());
                pstmt.setDate(5, classetestedata.getDataAlteracao());
                pstmt.setDate(6, classetestedata.getDataCadastro());
                pstmt.setDate(7, classetestedata.getHoraAlteracao());
                pstmt.setDate(8, classetestedata.getHoraCadastro());

                pstmt.executeUpdate();
                inseriu = true;
            } catch (SQLException erro) {
                mensagemErro("sql. inserir(): \n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
            return inseriu;
        }

        private boolean inserirAutomatico(ClasseTesteData classetestedata) {
            boolean inseriu = false;
            Connection conn = null;
            PreparedStatement pstmt = null;
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlInserirAutomatico);
                pstmt.setInt(1, classetestedata.getCodigo());
                pstmt.setString(2, classetestedata.getNome());
                pstmt.executeUpdate();
                inseriu = true;
            } catch (SQLException erro) {
                mensagemErro("sql. inserirAutomatico(): \n" + erro.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
            return inseriu;
        }

        private boolean getTesteData(int codigo) {
            boolean existe = false;
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlVerificaSeExiste);
                pstmt.setInt(1, codigo);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    existe = rs.getInt("codigosql") > 0;
                }
            } catch (SQLException e) {
                mensagemErro("Erro de SQL. getTesteData: \n" + e.getMessage());
            } finally {
                Conexao.closeAll(conn);
            }
            return existe;
        }
    }

    private void GravarCompletoAutomatico() throws ParseException {
        String auxCodigo = edtCodigo.getText();
        String auxNome = edtNome.getText();
        String auxDataEmissao = edtDataEmissao.getText();
        String auxDataSaida = edtDataSaida.getText();
        String auxDataAlteracao = edtDataAlteracao.getText();
        String auxDataCadastro = edtDataCadastro.getText();
        String auxHoraAlteracao = edtHoraAlteracao.getText();
        String auxHoraCadastro = edtHoraCadastro.getText();
        int codigo = Integer.parseInt(auxCodigo);

        //Formatando a Data de String para Date
        //Date dataEmissao=formatadata.parse(auxDataEmissao);
        //Date dataSaida=formatadata.parse(auxDataSaida);
        //Date horaEmissao=formatahora.parse(auxHoraEmissao);
        //Date horaSaida=formatahora.parse(auxHoraSaida);
        java.sql.Date DataEmissao = new java.sql.Date(formatadata.parse(auxDataEmissao).getTime());
        java.sql.Date DataSaida = new java.sql.Date(formatadata.parse(auxDataSaida).getTime());
        java.sql.Date DataAlteracao = new java.sql.Date(formatadata.parse(auxDataAlteracao).getTime());
        java.sql.Date DataCadastro = new java.sql.Date(formatadata.parse(auxDataCadastro).getTime());
        java.sql.Date HoraCadastro = new java.sql.Date(formatahora.parse(auxHoraCadastro).getDate());
        java.sql.Date HoraAlteracao = new java.sql.Date(formatahora.parse(auxHoraAlteracao).getDate());

        ClasseTesteData classetestedata = new ClasseTesteData(
                codigo,
                auxNome,
                DataEmissao,
                DataSaida,
                DataAlteracao,
                DataCadastro,
                HoraAlteracao,
                HoraCadastro
        );

        TesteDataDB testedatadb = new TesteDataDB();
        if (testedatadb.getTesteData(codigo)) {
            if (testedatadb.alterarAutomatico(classetestedata)) {
                JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
            } else {
                mensagemErro("Erro ao alterar!");
            }
        } else {
            if (testedatadb.inserirAutomatico(classetestedata)) {
                JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso!");
            } else {
                mensagemErro("Erro ao inserir!");
            }
        }

    }

    private void GravarCompletoValidado() throws ParseException {
        String auxCodigo = edtCodigo.getText();
        String auxNome = edtNome.getText();
        String auxDataEmissao = edtDataEmissao.getText();
        String auxDataSaida = edtDataSaida.getText();
        String auxDataAlteracao = edtDataAlteracao.getText();
        String auxDataCadastro = edtDataCadastro.getText();
        String auxHoraAlteracao = edtHoraAlteracao.getText();
        String auxHoraCadastro = edtHoraCadastro.getText();
        int codigo = Integer.parseInt(auxCodigo);

        //Formatando a Data de String para Date
        //Date data=formatadata.format(auxDataEmissao).getT
        //Date dataEmissao=formatadata.format(auxDataEmissao);
        //String datas= "04/04/2014";
        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //java.util.Date DataEmissao;
        //DataEmissao = formatter.parse(auxDataEmissao);
        //Date dataSaida=formatadata.parse(auxDataSaida);
        //Date horaEmissao=formatahora.parse(auxHoraEmissao);
        //Date horaSaida=formatahora.parse(auxHoraSaida);
        //java.sql.Date DataEmissao = new java.sql.Date(formatadata.parse(auxDataEmissao).getTime());
        java.sql.Date DataSaida = new java.sql.Date(formatadata.parse(auxDataSaida).getTime());
        java.sql.Date DataAlteracao = new java.sql.Date(formatadata.parse(auxDataAlteracao).getTime());
        java.sql.Date DataCadastro = new java.sql.Date(formatadata.parse(auxDataCadastro).getTime());
        java.sql.Date HoraCadastro = new java.sql.Date(formatahora.parse(auxHoraCadastro).getDate());
        java.sql.Date HoraAlteracao = new java.sql.Date(formatahora.parse(auxHoraAlteracao).getDate());

        //String DataEmissao=auxDataEmissao.ToDatt;
// Como converter String para java.util.Date
        java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        java.util.Date datasss = null;
        try {
            String str = "04/07/2011";
            datasss = (java.util.Date) formatter.parse(str);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

// Como converter java.util.Date para String
        formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        datasss = new java.util.Date();
        String str = formatter.format(datasss);

// Como converter java.util.Date para java.sql.Date
        java.util.Date hoje = new java.util.Date();
        java.sql.Date today = new java.sql.Date(hoje.getTime());

//Conversao Sistema Geo
        java.util.Date javautilDateDataEmissao = null;
        javautilDateDataEmissao = (java.util.Date) formatter.parse(auxDataEmissao);

        java.sql.Date DataEmissao = new java.sql.Date(javautilDateDataEmissao.getTime());

        ClasseTesteData classetestedata = new ClasseTesteData(
                codigo,
                auxNome,
                DataEmissao,
                DataSaida,
                DataAlteracao,
                DataCadastro,
                HoraAlteracao,
                HoraCadastro
        );

        TesteDataDB testedatadb = new TesteDataDB();
        if (testedatadb.getTesteData(codigo)) {
            if (testedatadb.alterar(classetestedata)) {
                JOptionPane.showMessageDialog(null, "Dados alterados com sucesso!");
            } else {
                mensagemErro("Erro ao alterar!");
            }
        } else {
            if (testedatadb.inserir(classetestedata)) {
                JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso!");
            } else {
                mensagemErro("Erro ao inserir!");
            }
        }

    }

    private void ListaDados() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TesteDataDB testedatadb = new TesteDataDB();
        int cd_pessoa = Integer.parseInt(edtCodigo.getText());

        if (testedatadb.getTesteData(cd_pessoa)) {

            try {
                conn = Conexao.getConexao();
                pstmt = conn.prepareStatement(sqlBuscaDados);
                pstmt.setInt(1, cd_pessoa);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int pessoa = rs.getInt("cd_pessoa");
                    String nome = rs.getString("nm_pessoa");

                    java.util.Date data_emissao = rs.getDate("dt_emi_doc");
                    java.util.Date data_saida = rs.getDate("dt_sai_doc");
                    java.util.Date data_alteracao = rs.getDate("dt_alt");
                    java.util.Date data_cadastro = rs.getDate("dt_cad");

                    String hora_saida = rs.getString("hr_alt");
                    String hora_emissao = rs.getString("hr_cad");

                    String auxpessoa = "" + pessoa;
                    edtCodigo.setText(auxpessoa);
                    edtNome.setText(nome);

                    String formataDataEmissao = formatadata.format(data_emissao);
                    String formataDataSaida = formatadata.format(data_saida);
                    String formataDataAlteracao = formatadata.format(data_alteracao);
                    String formataDataCadastro = formatadata.format(data_cadastro);

                    edtDataEmissao.setText(formataDataEmissao);
                    edtDataSaida.setText(formataDataSaida);
                    edtDataAlteracao.setText(formataDataAlteracao);
                    edtDataCadastro.setText(formataDataCadastro);

                    edtHoraCadastro.setText(hora_emissao);
                    edtHoraAlteracao.setText(hora_saida);

                    edtCodigo1.setText(auxpessoa);
                    edtNome1.setText(nome);
                    edtDataEmissao1.setText(formataDataEmissao);
                    edtDataSaida1.setText(formataDataSaida);
                    edtDataAlteracao1.setText(formataDataAlteracao);
                    edtDataCadastro1.setText(formataDataCadastro);

                    edtHoraCadastro1.setText(hora_emissao);
                    edtHoraAlteracao1.setText(hora_saida);

                    edtCodigo.requestFocus();

                }
            } catch (SQLException erro) {
                mensagemErro("Erro de conexão! " + erro);
            }
        } else {
            mensagemErro("Pessoa não cadastrada!");
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
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        edtCodigo = new javax.swing.JTextField();
        edtNome = new javax.swing.JTextField();
        edtDataEmissao = new javax.swing.JTextField();
        edtDataSaida = new javax.swing.JTextField();
        edtDataAlteracao = new javax.swing.JTextField();
        edtDataCadastro = new javax.swing.JTextField();
        edtHoraAlteracao = new javax.swing.JTextField();
        edtHoraCadastro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        edtCodigo1 = new javax.swing.JTextField();
        edtNome1 = new javax.swing.JTextField();
        edtDataEmissao1 = new javax.swing.JTextField();
        edtDataSaida1 = new javax.swing.JTextField();
        edtDataAlteracao1 = new javax.swing.JTextField();
        edtDataCadastro1 = new javax.swing.JTextField();
        edtHoraAlteracao1 = new javax.swing.JTextField();
        edtHoraCadastro1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnGravar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnGravar1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("TESTE DE DATAS");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel10.setText("Datas do Banco de Dados");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        edtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigoKeyPressed(evt);
            }
        });

        jLabel8.setText("Hora de Cadastro:");

        jLabel9.setText("Hora de Alteração:");

        jLabel7.setText("Data de Cadastro:");

        jLabel6.setText("Data de Alteração:");

        jLabel4.setText("Data de Emissão:");

        jLabel3.setText("Nome:");

        jLabel5.setText("Data de Saída:");

        jLabel2.setText("Código:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(edtDataEmissao)
                    .addComponent(edtNome)
                    .addComponent(edtHoraCadastro)
                    .addComponent(edtHoraAlteracao)
                    .addComponent(edtDataCadastro)
                    .addComponent(edtDataAlteracao)
                    .addComponent(edtDataSaida)
                    .addComponent(edtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(edtDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(edtDataSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtDataAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtHoraAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtHoraCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        edtCodigo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edtCodigo1KeyPressed(evt);
            }
        });

        jLabel11.setText("Hora de Cadastro:");

        jLabel12.setText("Hora de Alteração:");

        jLabel13.setText("Data de Cadastro:");

        jLabel14.setText("Data de Alteração:");

        jLabel15.setText("Data de Emissão:");

        jLabel16.setText("Nome:");

        jLabel17.setText("Data de Saída:");

        jLabel18.setText("Código:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(edtDataEmissao1)
                    .addComponent(edtNome1)
                    .addComponent(edtHoraCadastro1)
                    .addComponent(edtHoraAlteracao1)
                    .addComponent(edtDataCadastro1)
                    .addComponent(edtDataAlteracao1)
                    .addComponent(edtDataSaida1)
                    .addComponent(edtCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtNome1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(edtDataEmissao1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edtCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(edtDataSaida1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtDataAlteracao1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtDataCadastro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtHoraAlteracao1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtHoraCadastro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jLabel19.setText("Datas Formatadas");

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Red.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnGravar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Faturamento/Knob Valid Green.png"))); // NOI18N
        btnGravar1.setText("GravarAutomatico");
        btnGravar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravar1ActionPerformed(evt);
            }
        });
        btnGravar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnGravar1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(btnGravar1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGravar)
                    .addComponent(btnSair)
                    .addComponent(btnGravar1))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(327, 327, 327)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(132, 132, 132)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(385, 385, 385))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente gravar o registro?");
        if (resposta == JOptionPane.YES_OPTION) {
            try {
                GravarCompletoValidado();
            } catch (ParseException ex) {
                mensagemErro("Erro no método GravarCompletoValidado() na conversão de Datas;" + ex.getMessage());
                Logger.getLogger(FormularioPedidoCompleto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnGravarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGravarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSair.grabFocus();
        }
    }//GEN-LAST:event_btnGravarKeyPressed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente Sair da tela?");
        if (resposta == JOptionPane.YES_OPTION) {
            int resposta2 = JOptionPane.showConfirmDialog(null, "Você perderá os dados Adicionados Alterados!Deseja continuar?");
            if (resposta2 == JOptionPane.YES_OPTION) {
                // habilitaCampos(false);
                //limparTela();
                dispose();
            } else {
                //edtCodigoCliente.requestFocus();
            }
        }
    }//GEN-LAST:event_btnSairActionPerformed

    private void edtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigoKeyPressed
        // TODO add your handling code here:
        String auxTexto = edtCodigo.getText();
        String auxTexto1 = edtCodigo.getText();

        int pessoa = Integer.parseInt(auxTexto);
        int pessoa1 = Integer.parseInt(auxTexto1);

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                mensagemErro("Pessoa vazia, preencha!");
                edtCodigo.requestFocus();
            } else {
                //Listar os Dados
                TesteDataDB testedatadb = new TesteDataDB();
                if (testedatadb.getTesteData(pessoa)) {
                    ListaDados();
                } else if (testedatadb.getTesteData(pessoa1)) {
                    ListaDados();
                } else {
                    mensagemErro("Pessoa não Cadastrada!");
                }
            }
        }
    }//GEN-LAST:event_edtCodigoKeyPressed

    private void edtCodigo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edtCodigo1KeyPressed
        // TODO add your handling code here:
        String auxTexto = edtCodigo.getText();

        int pessoa = Integer.parseInt(edtCodigo.getText());
        int pessoa1 = Integer.parseInt(edtCodigo1.getText());

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (auxTexto.equals("")) {
                mensagemErro("Pessoa vazia, preencha!");
                edtCodigo.requestFocus();
            } else {
                //Listar os Dados
                TesteDataDB testedatadb = new TesteDataDB();
                if (testedatadb.getTesteData(pessoa)) {
                    ListaDados();
                } else if (testedatadb.getTesteData(pessoa1)) {
                    ListaDados();
                } else {
                    mensagemErro("Pessoa não Cadastrada!");
                }
            }
        }
    }//GEN-LAST:event_edtCodigo1KeyPressed

    private void btnGravar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravar1ActionPerformed
        try {
            // TODO add your handling code here:
            GravarCompletoAutomatico();
        } catch (ParseException ex) {
            Logger.getLogger(TesteData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGravar1ActionPerformed

    private void btnGravar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnGravar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGravar1KeyPressed

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
            java.util.logging.Logger.getLogger(TesteData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TesteData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TesteData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TesteData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TesteData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnGravar1;
    private javax.swing.JButton btnSair;
    private javax.swing.JTextField edtCodigo;
    private javax.swing.JTextField edtCodigo1;
    private javax.swing.JTextField edtDataAlteracao;
    private javax.swing.JTextField edtDataAlteracao1;
    private javax.swing.JTextField edtDataCadastro;
    private javax.swing.JTextField edtDataCadastro1;
    private javax.swing.JTextField edtDataEmissao;
    private javax.swing.JTextField edtDataEmissao1;
    private javax.swing.JTextField edtDataSaida;
    private javax.swing.JTextField edtDataSaida1;
    private javax.swing.JTextField edtHoraAlteracao;
    private javax.swing.JTextField edtHoraAlteracao1;
    private javax.swing.JTextField edtHoraCadastro;
    private javax.swing.JTextField edtHoraCadastro1;
    private javax.swing.JTextField edtNome;
    private javax.swing.JTextField edtNome1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}
