<?php

class ViewPessoa extends ViewPadrao {

    /**
     * @var ModelPessoa
     */
    protected $Model;

    public function montaTitulo() {
        $sHTML = '';
        $sHTML .= '<h2>Pessoas</h2>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaConsulta() {
        $sHTML = '';
        $sHTML .= '<table class="tabela_consulta">';
        $sHTML .= '<tr>';
        $sHTML .= '<th>Codigo</th>';
        $sHTML .= '<th>Nome</th>';
        $sHTML .= '<th>Data Nascimento</th>';
        $sHTML .= '<th>Bairro</th>';
        $sHTML .= '<th>Cidade</th>';
        $sHTML .= '<th>Estado</th>';
        $sHTML .= '<th>ICMS</th>';
        $sHTML .= '<th>Excluir</th>';
        $sHTML .= '</tr>';
        if (count($this->listaModel) > 0) {
            foreach ($this->listaModel as $indice => /* @var $oModelPessoa ModelPessoa */ $oModelPessoa) {
                $sHTML.= '<tr>';
                $sHTML.= '<td>' . $oModelPessoa->getPescodigo() . '</td>';
                $sHTML.= '<td>' . $oModelPessoa->getPesnome() . '</td>';
                $sHTML.= '<td>' . $oModelPessoa->getPesdatanascimento() . '</td>';
                $sHTML.= '<td>' . $oModelPessoa->getPesbairro() . '</td>';
                $sHTML.= '<td>' . $oModelPessoa->getPescidade() . '</td>';
                $sHTML.= '<td>' . $oModelPessoa->getPesestado() . '</td>';
                $sHTML.= '<td>' . $oModelPessoa->getPesicms() . '</td>';
                $sHTML.= '<td>';
                $sHTML .= '<input class="botao" id="botaoExcluir" type="button" value="Excluir" onclick="excluiPessoa(' . $oModelPessoa->getPescodigo() . ')"/>';
                $sHTML.= '</td>';
                $sHTML.= '</tr>';
            }
        } else {
            $sHTML .= '<tr><td>Nenhuma Pessoa Cadastrada</td></tr>';
        }
        $sHTML .= '</table>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaFormulario() {
        $sHTML = '';
        $sHTML .= '<form method="POST" action="index.php?pagina=Pessoa" id="formularioPessoa">';
        $sHTML .= '<input type="hidden" name="acao" id="acao" value="inc"/>';
        $sHTML .= '<div class="field">
                       <label for="pescodigo">Código:</label>
                       <input class="label_formulario" type="text" id="pescodigo" name="pescodigo" size="5"/>
                    </div>';
        $sHTML .= '<div class="field">
                       <label for="pesnome">Nome:</label>
                       <input class="label_formulario" type="text" id="pesnome" name="pesnome" size="50"/>
                     </div>';
        $sHTML .= '<div class="field">
                       <label for="pesdatanascimento" >Data Nascimento:</label>
                       <input class="label_formulario" type="date" id="pesdatanascimento" name="pesdatanascimento" size="50"/>
                     </div>';
        $sHTML .= '<div class="field">
                       <label for="pesbairro" >Bairro:</label>
                       <input class="label_formulario" type="text" id="pesbairro" name="pesbairro" size="30"/>
                   </div>';
        $sHTML .= '<div class="field">
                       <label for="pescidade" >Cidade:</label>
                       <input class="label_formulario" type="text" id="pescidade" name="pescidade" size="50"/>
                   </div>';

        $sHTML .= '<div class="field">
                       <label for="pesestado" >Estado:</label>
                            <select class="label_formulario" name="pesestado" id="pesestado" onclick="verificaEstadoICMS()">';
        $sHTML.= '<option class="label_formulario" value="1">SP</option>';
        $sHTML.= '<option class="label_formulario" value="2">PR</option>';
        $sHTML.= '<option class="label_formulario" value="3">RS</option>';
        $sHTML.= '<option class="label_formulario" value="4">SC</option>';
        $sHTML.= '<option class="label_formulario" value="5">MS</option>';
        $sHTML.= '<option class="label_formulario" value="6">AC</option>';
        $sHTML.= '<option class="label_formulario" value="7">AL</option>';
        $sHTML.= '<option class="label_formulario" value="8">AP</option>';
        $sHTML.= '<option class="label_formulario" value="9">SE</option>';
        $sHTML.= '<option class="label_formulario" value="10">AM</option>';
        $sHTML.= '<option class="label_formulario" value="11">BA</option>';
        $sHTML.= '<option class="label_formulario" value="12">CE</option>';
        $sHTML.= '<option class="label_formulario" value="13">DF</option>';
        $sHTML.= '<option class="label_formulario" value="14">ES</option>';
        $sHTML.= '<option class="label_formulario" value="15">GO</option>';
        $sHTML.= '<option class="label_formulario" value="16">MA</option>';
        $sHTML.= '<option class="label_formulario" value="17">MT</option>';
        $sHTML.= '<option class="label_formulario" value="18">MG</option>';
        $sHTML.= '<option class="label_formulario" value="19">PA</option>';
        $sHTML.= '<option class="label_formulario" value="20">PB</option>';
        $sHTML.= '<option class="label_formulario" value="21">PE</option>';
        $sHTML.= '<option class="label_formulario" value="22">PI</option>';
        $sHTML.= '<option class="label_formulario" value="23">RJ</option>';
        $sHTML.= '<option class="label_formulario" value="24">RN</option>';
        $sHTML.= '<option class="label_formulario" value="25">RO</option>';
        $sHTML.= '<option class="label_formulario" value="26">RR</option>';
        $sHTML .='</select></div>';
        $sHTML .= '<div class="field" id="divicms">
                       <label for="pesicms" id="label_icms" ></label>
                       <input class="label_formulario" type="hidden" id="pesicms" name="pesicms" value="0" size="10"/>
                   </div>';
        $sHTML .= '<input class="botao" id="botaoGravar" type="button" value="Gravar" onclick="validaFormPessoa()"/>
                   <input class="botao" id="botaoLimpar" type="reset" value="Limpar"/>';
        $sHTML .= '</form>';
        echo $sHTML;
    }

}
