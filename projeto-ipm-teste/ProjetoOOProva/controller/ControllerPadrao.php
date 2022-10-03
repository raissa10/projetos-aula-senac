<?php

abstract class ControllerPadrao {
    /* Método disparado no Redirecionador.php, deve implementar o método padrão do controlador */

    abstract function processa();

    /* Processa informações para inclusão, deve carregar um modelo de dados com as informações vindas do POST e mandar gravar */

    public function processaInclusao() {

    }

    public function processaExclusao() {

    }

    public function montaTela() {
        //busca o metodo montaTela de ViewPadrao
    }

}
