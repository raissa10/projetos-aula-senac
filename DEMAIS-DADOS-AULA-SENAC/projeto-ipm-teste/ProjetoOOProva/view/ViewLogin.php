<?php

class ViewLogin extends ViewPadrao {

    public function montaTitulo() {
        $sHTML = '';
        $sHTML .= '<h2>Login</h2>';
        $sHTML .= '<hr>';
        echo $sHTML;
    }

    public function montaFormulario() {
        $sHTML = '';
        $sHTML .= '<form method="POST" action="index.php?pagina=Login" id="formularioLogin">';
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
                       <input class="label_formulario" type="password" id="ususenha" name="ususenha" size="50"/>
                     </div>';
        $sHTML .= '<input class="botaoGravar" type="button" value="Entrar" onclick="validaFormUsuario()"/>
                   <input class="botaoGravar" type="button" value="Sair" onclick="validaFormUsuario()"/>
                   <input class="botaoLimpar" type="reset" value="Limpar"/>
                   <label class="mensagem_operacao_alterar_excluir"  id="idlabel_mensagem"></label>';
        $sHTML .= '</form>';
        echo $sHTML;
    }

}
