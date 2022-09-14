package Estrutura;

import Controller.CondicaoPagamentoDB;
import Model.ModelCondicaoPagamento;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gelvazio Camargo
 * @since 29/07/2019
 */
public abstract class ViewConsultaBase extends ViewConsultaPadrao {
 
    protected ArrayList listaCampos = new ArrayList();
    protected String SQL;
    private String tabela;

    public ViewConsultaBase(JTextField campoCodigo) {
        super(campoCodigo);
        criaCampos();
        this.getCbCampo().setModel(getComboCampo());
        this.getCbValor().setModel(getComboValor());
        this.listaTodosRegistros(true, this.getTbGrid(), true);        
    }
    
    protected Class getClasse(){        
        return this.getClass();
    }
    
    protected DefaultComboBoxModel getComboCampo() {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        String auxCodigo = "Codigo";
        String auxNome = "Nome";
        try {
            modelo.addElement(auxCodigo);
            modelo.addElement(auxNome);
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro no Método getComboCampo(): \n" + erro.getMessage());
        }
        return modelo;
    }
           
    protected DefaultComboBoxModel getComboValor() {
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
        }
        return modelo;
    }
    
    protected void adicionaCampoConsulta(String tipo, String nomeBanco, String nome, String titulo, boolean isChave){
        Campo campo = new Campo(nomeBanco, nome, titulo, isChave, tipo);
        listaCampos.add(campo);
    }
    
    @Override
    protected void validaCampoPesquisa() {
        if (this.getEdtPesquisa().getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Favor Digitar algum valor ou caracter no campo Pesquisa!");
            this.getEdtPesquisa().grabFocus();
        } else {
            this.adicionaColunasConsulta();
        }
    }

    protected DefaultTableModel addRowToModel(DefaultTableModel oModelo){
        ArrayList<ModelCondicaoPagamento> dados = SQLConsultagetTodosCompleto();
        for (ModelCondicaoPagamento aux : dados) {
            oModelo.addRow(new Object[]{
                aux.getCodigo(),
                aux.getDescricao()
            });
        }
        return oModelo;
    }
        
    protected void adicionaColunasConsulta() {
        DefaultTableModel oModelo = new DefaultTableModel();
        for (Iterator iterator = listaCampos.iterator(); iterator.hasNext(); ) { 
            Campo campo = (Campo) iterator.next(); 
            oModelo.addColumn(campo.getNome());
        }        
        this.getTbGrid().setModel(this.addRowToModel(oModelo));
    }
    
    protected ArrayList SQLConsultagetTodosCompleto() {
        this.adicionaCondicao();
        ArrayList listaDados = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConexaoFirebird.getConexao();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(this.SQL);
            while (rs.next()) {
                listaDados.add(this.getModelCarregado(rs));
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro no sql, SQLConsultagetTodosCompleto: \n" + erro.getMessage());
        } finally {
            ConexaoFirebird.closeAll(conn);
        }
        return listaDados;
    }

    protected String getValorOperador(String tipoCampo) {
        String operador = "";
        if(tipoCampo == Campo.TIPO_TEXTO){
            if(this.getCbValor().getSelectedItem().toString().equals("Igual")){
                return "LIKE UPPER('" + this.getEdtPesquisa().getText() + "')";
            }
            if(this.getCbValor().getSelectedItem().toString().equals("Diferente")){
                return "NOT LIKE UPPER('" + this.getEdtPesquisa().getText() + "')";
            }
            if(this.getCbValor().getSelectedItem().toString().equals("Maior") ||
                this.getCbValor().getSelectedItem().toString().equals("MaiorIgual")){
                return "LIKE UPPER('%" + this.getEdtPesquisa().getText() + "')";
            }
            if(this.getCbValor().getSelectedItem().toString().equals("Menor") ||
                this.getCbValor().getSelectedItem().toString().equals("MenorIgual")){
                return "LIKE UPPER('" + this.getEdtPesquisa().getText() + "%')";
            }
            if(this.getCbValor().getSelectedItem().toString().equals("Parecido")){
                return "LIKE UPPER('%" + this.getEdtPesquisa().getText() + "%')";
            }            
        }
        if(tipoCampo == Campo.TIPO_NUMERICO){
            if(this.getCbValor().getSelectedItem().toString().equals("Igual")){
                operador = "=";
            }            
            if(this.getCbValor().getSelectedItem().toString().equals("Diferente")){
                operador = "<>";
            }            
            if(this.getCbValor().getSelectedItem().toString().equals("Maior")){
                operador = ">";
            }            
            if(this.getCbValor().getSelectedItem().toString().equals("Menor")){
                operador = "<";
            }            
            if(this.getCbValor().getSelectedItem().toString().equals("MaiorIgual")){
                operador = ">=";
            }            
            if(this.getCbValor().getSelectedItem().toString().equals("MenorIgual")){
                operador = "<=";
            }            
        }
        if(this.getCbValor().getSelectedItem().toString().equals("Parecido")){
            return "LIKE UPPER('" + this.getEdtPesquisa().getText() + "%')";
        }
        return operador + this.getEdtPesquisa().getText();
    }
    
    protected void adicionaCondicao() {
        String condicaoWhere = "";        
        for (Iterator iterator = listaCampos.iterator(); iterator.hasNext(); ) { 
            Campo campo = (Campo) iterator.next();
            if (this.getCbCampo().getSelectedItem().toString().equals(campo.getNomeBanco())){
                condicaoWhere = campo.getNomeBanco() + " " + this.getValorOperador(campo.getTipo());
                break;
            }
        }        
        this.SQL = "select * from " + this.getTabela() + " where 1 = 1 and " + condicaoWhere + " ";
    }

    protected ModelPadrao getModelCarregado(ResultSet rs) throws SQLException {
        ModelCondicaoPagamento condicaopagamento = new ModelCondicaoPagamento();
        condicaopagamento.setCodigo(rs.getInt("cd_cond"));
        condicaopagamento.setDescricao(rs.getString("ds_cond"));
        condicaopagamento.setCodigoUsuario(rs.getInt("cd_usuario"));
        return condicaopagamento;
    }
    
    protected Object getValorCampo(String valorCampo, int posicao){
        Object [] objeto = new Object[1];
        objeto[1] = "teste";
        return objeto;
    } 
    
    protected Object getDadosColunas(Class<?> oClasse){
        Object [] objeto = new Object[listaCampos.size()];
        for (int i = 0;i < listaCampos.size();i++){
            objeto[i] = "teste"+i;
        }
        
int posicao = 0;
            for (Iterator iterator = listaCampos.iterator(); iterator.hasNext(); ) { 
                Campo campo = (Campo) iterator.next();
                System.out.println("Campo:" + campo.getNomeBanco());
                if (this.getCbCampo().getSelectedItem().toString().equals(campo.getNome())){
                    String valor = null;
                    Class<?> classe = oClasse.getClass();
                    for (Method metodo : classe.getDeclaredMethods()) {
                        if (metodo.getParameterTypes().length == 0) {
//                            try {
                                System.out.println("Invocando o metodo: " + metodo.getName());
                                if (metodo.getReturnType().getName().equals("void")){
//                                    metodo.invoke(classe, new Object[0]);
                                } else {
//                                    valor = metodo.invoke(aux, new Object[0]).toString();
//                                    objeto[posicao] = valor;
                                    break;
                                }
//                            } catch (IllegalAccessException e) {
//                                
//                            } 
                            
//                            throw (IllegalAccessException e) {
//                                
//                            }                            
                        }
                    }                    
                    break;
                }
               posicao++;
            }                     
        
    return objeto;
    }
    
    protected void listaTodosRegistros(boolean bAberturaConsulta, JTable tbGrid, boolean bFiltraTodos){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Descrição");

        CondicaoPagamentoDB dadosdb = new CondicaoPagamentoDB();
        ArrayList<ModelCondicaoPagamento> dados;
        if(bAberturaConsulta){
            dados = dadosdb.getTodos();
        } else {
            dados = this.SQLConsultagetTodosCompleto();
        }
       
        Object [] objeto = new Object[2];
        for (int i=0;i<2;i++){
            objeto[i] = "teste"+i;
        }
        for (ModelCondicaoPagamento aux : dados) {
            //modelo.addRow(new Object[]{vetor});
            modelo.addRow(objeto);

//            modelo.addRow(new Object[]{
//                aux.getCodigo(),
//                aux.getDescricao()
//            });
            
            ArrayList<Object> colunas = new ArrayList<>();
            int posicao = 0;
            for (Iterator iterator = listaCampos.iterator(); iterator.hasNext(); ) { 
                Campo campo = (Campo) iterator.next();
                colunas.add("teste_" + posicao++);
//                System.out.println("Campo:" + campo.getNomeBanco());
//                if (this.getCbCampo().getSelectedItem().toString().equals(campo.getNome())){
//                    String valor = null;
//                    Class<?> classe = aux.getClass();
//                    for (Method metodo : classe.getDeclaredMethods()) {
//                        if (metodo.getParameterTypes().length == 0) {
//                            System.out.println("Invocando o metodo: " + metodo.getName());
//                            if (metodo.getReturnType().getName().equals("void")){
//                                metodo.invoke(aux, new Object[0]);
//                            } else {
//                                valor = metodo.invoke(aux, new Object[0]).toString();
//                                colunas.add(valor);
//                                break;
//                            }
//                        }
//                    }                    
//                    break;
//                }
            }                     
        }
        tbGrid.setModel(modelo);
    }

    public void executaMetodos(Object o, String nomeMetodo) throws Exception {
        Class<?> classe = o.getClass();
        for (Method m : classe.getDeclaredMethods()) {
            if (m.getParameterTypes().length == 0) {
                if(m.getName() == nomeMetodo){
                    //System.out.println("Invocando o metodo: " + metodo.getName());
                    if (m.getReturnType().getName().equals("void")){
                        m.invoke(o, new Object[0]);
                    } else {
                        System.out.println(m.invoke(o, new Object[0]));
                    }                    
                }
            }
        }
    }
    
    protected Object adicionaColunaConsultaObjeto(ModelCondicaoPagamento aux){
        Object objeto = new Object[]{
            aux.getCodigo(),
            aux.getDescricao()
        };
        return objeto;
    }
    
    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }
    
}

