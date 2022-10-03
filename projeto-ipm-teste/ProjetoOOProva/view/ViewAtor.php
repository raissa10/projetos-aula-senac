<?php

class ViewAtor extends ViewPadrao {

    /**
     * @var ModelAtor
     */
    protected $Model;

    public function montaTitulo() {
        $sHTML = '';
        $sHTML .= '<h2>Atores</h2>';
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
            foreach ($this->listaModel as $indice => /* @var $oModelAtor ModelAtor */ $oModelAtor) {
                $sHTML.= '<tr>';
                $sHTML.= '<td>' . $oModelAtor->getCodigo() . '</td>';
                $sHTML.= '<td>' . $oModelAtor->getNome() . '</td>';
                $sHTML.= '<td>';
                $sHTML .= '<input class="botaoAlterar" type="button" value="Alterar" onclick="alteraAtor(' . $oModelAtor->getCodigo() . ',\'' . $oModelAtor->getNome() . '\')"/>';
                $sHTML .= '<input class="botaoExcluir" type="button" value="Excluir" onclick="excluiAtor(' . $oModelAtor->getCodigo() . ')"/>';
                $sHTML.= '</td>';
                $sHTML.= '</tr>';
            }
        } else {
            $sHTML .= '<tr><td colspan="2">Nenhum Ator Cadastrado</td></tr>';
        }
        $sHTML .= '</table>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaFormulario() {
        $sHTML = '';
        $sHTML .= '<form method="POST" action="index.php?pagina=Ator" id="formularioAtor">';
        $sHTML .= '<input type="hidden" name="acao" id="acao" value="inc"/>';
        $sHTML .= '<div class="field">
                       <label for="atocodigo">Código:</label>
                       <input class="label_formulario" type="text" id="atocodigo" name="atocodigo" size="5"/>
                    </div>';
        $sHTML .= '<div class="field">
                       <label for="atonome">Nome:</label>
                       <input class="label_formulario" type="text" id="atonome" name="atonome" size="50"/>
                     </div>';
        $sHTML .= '<input class="botaoGravar" type="button" value="Gravar" onclick="validaFormAtor()"/>
                   <input class="botaoLimpar" type="reset" value="Limpar"/>
                   <label class="mensagem_operacao_alterar_excluir"  id="idlabel_mensagem"></label>';
        $sHTML .= '</form>';
        echo $sHTML;
    }

}
