<?php

class ViewFilme extends ViewPadrao {

    /**
     * @var ModelFilme
     */
    protected $Model;
    protected $listaCategoria;

    function getListaCategoria() {
        return $this->listaCategoria;
    }

    function setListaCategoria($listaCategoria) {
        $this->listaCategoria = $listaCategoria;
    }

    public function montaTitulo() {
        $sHTML = '';
        $sHTML .= '<h2>Filmes</h2>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaConsulta() {
        $sHTML = '';
        $sHTML .= '<table class="tabela_consulta">';
        $sHTML .= '<tr>';
        $sHTML .= '<th>Codigo</th>';
        $sHTML .= '<th>Titulo</th>';
        $sHTML .= '<th>Sinopse</th>';
        $sHTML .= '<th>Data</th>';
        $sHTML .= '<th>Categoria</th>';
        $sHTML .= '<th>Trailer</th>';
        $sHTML .= '<th colspan="2">Ações</th>';
        $sHTML .= '</tr>';
        if (count($this->listaModel) > 0) {
            foreach ($this->listaModel as $indice => /* @var $oModelFilme ModelFilme */ $oModelFilme) {
                $sHTML.= '<tr>';
                $sHTML.= '<td>' . $oModelFilme->getCodigo() . '</td>';
                $sHTML.= '<td>' . $oModelFilme->getTitulo() . '</td>';
                //Sinopse original-arrumar
                $sStringSinopse = '';
                $sStringSinopse = $oModelFilme->getSinopse();
                $sStringSinopse = substr($sStringSinopse, 0, 10);
                $sHTML.= '<td>' . $sStringSinopse . '...</td>';
                $sHTML.= '<td >' . $oModelFilme->getDataestreia() . '</td>';
                $sHTML.= '<td>' . $oModelFilme->getModelCategoria()->getNome() . '</td>';
                $sHTML.= '<td><a href="' . $oModelFilme->getTrailer() . '" target="_blank" >Trailer</a></td>';
                $sHTML.= '<td>';
                $sHTML .= '<input class="botaoAlterar" type="button" value="Alterar" onclick="alteraFilme(' . $oModelFilme->getCodigo() . ','
                        . '\'' . $oModelFilme->getTitulo() . '\','
                        . '\'' . $oModelFilme->getSinopse() . '\','
                        . '\'' . $oModelFilme->getDataestreia() . '\','
                        . '\'' . $oModelFilme->getTrailer() . '\','
                        . '' . $oModelFilme->getModelCategoria()->getCodigo() . ','
                        . '\'' . $oModelFilme->getModelCategoria()->getNome() . '\')"/>';
                $sHTML .= '<input class="botaoExcluir" type="button" value="Excluir" onclick="excluiFilme(' . $oModelFilme->getCodigo() . ')"/>';
                $sHTML.= '</td>';
                $sHTML.= '</tr>';
            }
        } else {
            $sHTML .= '<tr><td colspan="2">Nenhum Filme Cadastrado</td></tr>';
        }
        $sHTML .= '</table>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaFormulario() {
        $sHTML = '';
        $sHTML .= '<form method="POST" action="index.php?pagina=Filme" id="formularioFilme">';
        $sHTML .= '<input type="hidden" name="acao" id="acao" value="inc"/>';
        $sHTML .= '<div class="field">
                       <label for="filcodigo">Código:</label>
                       <input class="label_formulario" type="text" id="filcodigo" name="filcodigo" size="5"/>
                    </div>';
        $sHTML .= '<div class="field">
                       <label for="filtitulo">Nome:</label>
                       <input class="label_formulario" type="text" id="filtitulo" name="filtitulo" size="50"/>
                     </div>';
        $sHTML .= '<div class="field">
                       <label for="filtrailer" >Trailer:</label>
                       <input class="label_formulario" type="text" id="filtrailer" name="filtrailer" size="50"/>
                     </div>';
        $sHTML .= '<div class="field">
                       <label for="fildataestreia" >Estréia:</label>
                       <input class="label_formulario" type="date" id="fildataestreia" name="fildataestreia" size="50"/>
                   </div>';
        $sHTML .= '<div class="field">
                       <label for="catcodigo" >Categoria:</label>
                            <select class="label_formulario" name="catcodigo" id="catcodigo">';
        if (count($this->listaCategoria) > 0) {
            foreach ($this->listaCategoria as $indice => /* @var $oModelCategoria ModelCategoria */ $oModelCategoria) {
                $sHTML.= '<option class="label_formulario" value="' . $oModelCategoria->getCodigo() . '">';
                $sHTML.=$oModelCategoria->getNome();
                $sHTML.= '</option>';
            }
        } else {
            $sHTML .= '<option>Não existe categoria cadastrado</option>';
        }
        $sHTML .='</select></div>';
        $sHTML .= '<div class="field">
                       <label for="filsinopse" >Sinopse:</label>
                       <textarea class="label_formulario" id="filsinopse" name="filsinopse"> </textarea>
                     </div>';
        $sHTML .= '<input class="botaoGravar" type="button" value="Gravar" onclick="validaFormFilme()"/>
                   <input class="botaoLimpar" type="reset" value="Limpar"/>
                   <label class="mensagem_operacao_alterar_excluir"  id="idlabel_mensagem"></label>';
        $sHTML .= '</form>';
        echo $sHTML;
    }

}
