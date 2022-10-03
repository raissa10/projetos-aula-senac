<?php

class ViewCategoria extends ViewPadrao {

    /**
     * @var ModelCategoria
     */
    protected $Model;

    public function montaTitulo() {
        $sHTML = '';
        $sHTML .= '<h2>Categorias</h2>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaConsulta() {
        $sHTML = '';
        $sHTML .= '<table class="tabela_consulta">';
        $sHTML .= '<tr>';
        $sHTML .= '<th>Código</th>';
        $sHTML .= '<th>Nome</th>';
        $sHTML .= '<th colspan="2">Ações</th>';
        $sHTML .= '</tr>';
        if (count($this->listaModel) > 0) {
            foreach ($this->listaModel as $indice => /* @var $oModelCategoria ModelCategoria */ $oModelCategoria) {
                $sHTML.= '<tr>';
                $sHTML.= '<td>' . $oModelCategoria->getCodigo() . '</td>';
                $sHTML.= '<td>' . $oModelCategoria->getNome() . '</td>';
                $sHTML.= '<td>';
                $sHTML .= '<input class="botaoAlterar" type="button" value="Alterar" onclick="alteraCategoria(' . $oModelCategoria->getCodigo() . ',\'' . $oModelCategoria->getNome() . '\')"/>';
                $sHTML .= '<input class="botaoExcluir" type="button" value="Excluir" onclick="excluiCategoria(' . $oModelCategoria->getCodigo() . ',\'' . $oModelCategoria->getNome() . '\')"/>';
                $sHTML.= '</td>';
                $sHTML.= '</tr>';
            }
        } else {
            $sHTML .= '<tr><td colspan="2">Nenhuma Categoria Cadastrada</td></tr>';
        }
        $sHTML .= '</table>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaFormulario() {
        $sHTML = '';
        $sHTML .= '<form method="POST" action="index.php?pagina=Categoria" id="formularioCategoria">';
        $sHTML .= '<input type="hidden" name="acao" id="acao" value="inc"/>';
        $sHTML .= '<div class="field">
                       <label for="catcodigo">Código:</label>
                       <input class="label_formulario"type="text" id="catcodigo" name="catcodigo" size="5"/>
                    </div>';
        $sHTML .= '<div class="field">
                       <label for="catnome" >Nome:</label>
                       <input class="label_formulario" type="text" id="catnome" name="catnome" size="50"/>
                     </div>';
        $sHTML .= '<input class="botaoGravar" type="button" value="Gravar" onclick="validaFormCategoria()"/>
                   <input class="botaoLimpar" type="reset" value="Limpar"/>
                   <label class="mensagem_operacao_alterar_excluir"  id="idlabel_mensagem"></label>';
        $sHTML .= '</form>';
        echo $sHTML;
    }

}
