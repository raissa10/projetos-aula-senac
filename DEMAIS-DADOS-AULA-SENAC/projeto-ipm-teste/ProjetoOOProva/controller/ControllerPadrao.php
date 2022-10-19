<?php

abstract class ControllerPadrao {
    /* M�todo disparado no Redirecionador.php, deve implementar o m�todo padr�o do controlador */

    abstract function processa();

    /* Processa informa��es para inclus�o, deve carregar um modelo de dados com as informa��es vindas do POST e mandar gravar */

    public function processaInclusao() {

    }

    public function processaExclusao() {

    }

    public function montaTela() {
        //busca o metodo montaTela de ViewPadrao
    }

}
