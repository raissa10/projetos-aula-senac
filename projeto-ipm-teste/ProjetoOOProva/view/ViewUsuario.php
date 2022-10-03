<?php

class ViewUsuario extends ViewPadrao {

    /**
     * @var ModelUsuario
     */
    protected $Model;

    public function montaTitulo() {
        $sHTML = '';
        $sHTML .= '<h2>Usuarios</h2>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaConsulta() {
        $sHTML = '';
        $sHTML .= '<table class="tabela_consulta">';
        $sHTML .= '<tr>';
        $sHTML .= '<th>Código</th>';
        $sHTML .= '<th>Nome</th>';
        $sHTML .= '<th>Senha</th>';
        $sHTML .= '<th colspan="2">Ações</th>';
        $sHTML .= '</tr>';
        if (count($this->listaModel) > 0) {
            foreach ($this->listaModel as $indice => /* @var $oModelUsuario ModelUsuario */ $oModelUsuario) {
                $sHTML.= '<tr>';
                $sHTML.= '<td>' . $oModelUsuario->getCodigo() . '</td>';
                $sHTML.= '<td>' . $oModelUsuario->getNome() . '</td>';
                $sHTML.= '<td>' . $oModelUsuario->getSenha() . '</td>';
                $sHTML.= '<td>';
                $sHTML .= '<input class="botaoAlterar" type="button" value="Alterar" onclick="alteraUsuario(' . $oModelUsuario->getCodigo() . ',\'' . $oModelUsuario->getNome() . '\')"/>';
                $sHTML .= '<input class="botaoExcluir" type="button" value="Excluir" onclick="excluiUsuario(' . $oModelUsuario->getCodigo() . ')"/>';
                $sHTML.= '</td>';
                $sHTML.= '</tr>';
            }
        } else {
            $sHTML .= '<tr><td colspan="2">Nenhuma Usuario Cadastrado</td></tr>';
        }
        $sHTML .= '</table>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaFormulario() {
        $sHTML = '';
        $sHTML .= '<form method="POST" action="index.php?pagina=Usuario" id="formularioUsuario">';
        $sHTML .= '<input type="hidden" name="acao" id="acao" value="inc"/>';
        $sHTML .= '<div class="field">
                       <label for="usucodigo">Código:</label>
                       <input class="label_formulario"type="text" id="usucodigo" name="usucodigo" size="5"/>
                    </div>';
        $sHTML .= '<div class="field">
                       <label for="usunome" >Nome:</label>
                       <input class="label_formulario" type="text" id="usunome" name="usunome" size="50"/>
                     </div>';
        $sHTML .= '<div class="field">
                       <label for="ususenha" >Senha:</label>
                       <input class="label_formulario" type="password" id="ususenha" name="ususenha" size="5"/>
                     </div>';
        $sHTML .= '<input class="botaoGravar" type="button" value="Gravar" onclick="validaFormUsuario()"/>
                   <input class="botaoLimpar" type="reset" value="Limpar"/>
                   <label class="mensagem_operacao_alterar_excluir"  id="idlabel_mensagem"></label>';
        $sHTML .= '</form>';
        echo $sHTML;
    }

}
