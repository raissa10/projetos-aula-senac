<?php

class ViewFilmeAtor extends ViewPadrao {

    /**
     * @var ModelFilmeAtor
     */
    protected $Model;
    protected $listaFilme;
    protected $listaAtor;

    function getListaFilme() {
        return $this->listaFilme;
    }

    function getListaAtor() {
        return $this->listaAtor;
    }

    function setListaFilme($listaFilme) {
        $this->listaFilme = $listaFilme;
    }

    function setListaAtor($listaAtor) {
        $this->listaAtor = $listaAtor;
    }

    public function montaTitulo() {
        $sHTML = '';
        $sHTML .= '<h2>Filme x Ator</h2>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaConsulta() {
        $sHTML = '';
        $sHTML .= '<table class="tabela_consulta">';
        $sHTML .= '<tr>';
        $sHTML .= '<th>Filme</th>';
        $sHTML .= '<th>Ator</th>';
        $sHTML .= '<th>Papel</th>';
        $sHTML .= '<th colspan="2">Ações</th>';
        $sHTML .= '</tr>';
        if (count($this->listaModel) > 0) {
            foreach ($this->listaModel as $indice => /* @var $oModelFilmeAtor ModelFilmeAtor */ $oModelFilmeAtor) {
                $sHTML.= '<tr>';
                $sHTML.= '<td>' . $oModelFilmeAtor->getModelFilme()->getTitulo() . '</td>';
                $sHTML.= '<td>' . $oModelFilmeAtor->getModelAtor()->getNome() . '</td>';
                $sHTML.= '<td>' . $oModelFilmeAtor->getAtfpapel() . '</td>';
                $sHTML.= '<td>';
                $sHTML .= '<input class="botaoAlterar" type="button" value="Alterar" onclick="alteraFilmeAtor(\'' . $oModelFilmeAtor->getAtfpapel() . '\',' . '\'' . $oModelFilmeAtor->getModelFilme()->getTitulo() . '\',\'' . $oModelFilmeAtor->getModelAtor()->getNome() . '\',' . $oModelFilmeAtor->getModelAtor()->getCodigo() . ',' . $oModelFilmeAtor->getModelFilme()->getCodigo() . ')"/>';
                $sHTML .= '<input class="botaoExcluir" type="button" value="Excluir" onclick="excluiFilmeAtor(' . $oModelFilmeAtor->getModelFilme()->getCodigo() . ',' . $oModelFilmeAtor->getModelAtor()->getCodigo() . ')"/>';
                $sHTML.= '</td>';
                $sHTML.= '</tr>';
            }
        } else {
            $sHTML .= '<tr><td colspan="2">Não existe ator com papel relacionado</td></tr>';
        }
        $sHTML .= '</table>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaFormulario() {
        $sHTML = '';
        $sHTML .= '<form method="POST" action="index.php?pagina=FilmeAtor" id="formularioFilmeAtor">';
        $sHTML .= '<input type="hidden" name="acao" id="acao" value="inc"/>';
        $sHTML .= '<div class="field">
                       <label for="atocodigo">Ator:</label>
                       <select class="label_formulario" id="atocodigo" name="atocodigo">';
        if (count($this->listaAtor) > 0) {
            foreach ($this->listaAtor as $indice => /* @var $oModelAtor ModelAtor */ $oModelAtor) {
                $sHTML.= '<option class="label_formulario" value="' . $oModelAtor->getCodigo() . '">';
                $sHTML.=$oModelAtor->getNome();
                $sHTML.= '</option>';
            }
        } else {
            $sHTML .= '<option>Não existe ator cadastrado</option>';
        }
        $sHTML .='</select>';
        $sHTML .='            </div>';
        $sHTML .= '<div class="field">
                       <label for="filcodigo" >Filme:</label>
                        <select class="label_formulario" id="filcodigo" name="filcodigo">';
        if (count($this->listaFilme) > 0) {
            foreach ($this->listaFilme as $indice => /* @var $oModelFilme ModelFilme */ $oModelFilme) {
                $sHTML.= '<option class="label_formulario" value="' . $oModelFilme->getCodigo() . '">';
                $sHTML.=$oModelFilme->getTitulo();
                $sHTML.= '</option>';
            }
        } else {
            $sHTML .= '<option>Não existe filme cadastrado</option>';
        }
        $sHTML .= '</select>';
        $sHTML .= '</div>';
        $sHTML .= '<div class="field">
                       <label for="atfpapel" >Papel:</label>
                       <input class="label_formulario" type="text" id="atfpapel" name="atfpapel" size="50"/>
                   </div>';
        $sHTML .= '<input class="botaoGravar" type="button" value="Gravar" onclick="validaFormFilmeAtor()"/>
                   <input class="botaoLimpar" type="reset" value="Limpar"/>
                   <label class="mensagem_operacao_alterar_excluir"  id="idlabel_mensagem"></label>';
        $sHTML .= '</form>';
        echo $sHTML;
    }

}
