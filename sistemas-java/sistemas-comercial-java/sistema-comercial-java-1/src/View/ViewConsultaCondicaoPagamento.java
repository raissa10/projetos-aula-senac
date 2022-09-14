
package View;

import Estrutura.Campo;
import Estrutura.ModelPadrao;
import Estrutura.ViewConsultaBase;
import Model.ModelCondicaoPagamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gelvazio Camargo
 */
public class ViewConsultaCondicaoPagamento extends ViewConsultaBase {
    
    public ViewConsultaCondicaoPagamento(JTextField campoCodigo) {
        super(campoCodigo);
        ModelCondicaoPagamento oModel = new ModelCondicaoPagamento();
        this.setModel(oModel);
        this.setTitle("Meu Titulo");
        this.setTitulo("Titulo testes");
    }

    @Override
    public String getTabela() {
        return "cond_pag";
    }
    
    @Override
    protected void criaCampos() {
        this.adicionaCampoConsulta(Campo.TIPO_NUMERICO, "cd_cond", "codigo", "Código", true);
        this.adicionaCampoConsulta(Campo.TIPO_TEXTO, "ds_cond", "descricao", "Descrição", false);
    }
    
    //Aqui adiciona os campos na consulta confoirme cada modelo
    @Override
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

    @Override
    protected ModelPadrao getModelCarregado(ResultSet rs) throws SQLException {
        ModelCondicaoPagamento condicaopagamento = new ModelCondicaoPagamento();
        condicaopagamento.setCodigo(rs.getInt("cd_cond"));
        condicaopagamento.setDescricao(rs.getString("ds_cond"));
        condicaopagamento.setCodigoUsuario(rs.getInt("cd_usuario"));
        return condicaopagamento;
    }    
}
