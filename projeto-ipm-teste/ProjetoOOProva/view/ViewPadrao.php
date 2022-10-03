<?php

/**
 * Respons�vel por apresentar os dados aos usu�rios
 */
abstract class ViewPadrao {
    /* Em caso de consultas, representa o array com os modelos para cria��o da tabela */

    protected $listaModel;

    /* Respons�vel por montar o HTML com o t�tulo da consulta */

    abstract function montaTitulo();

    /* Respons�vel por montar a consulta */

    public function montaConsulta() {

    }

    /* Respons�vel por montar o formul�rio para cadastrar as informa��es */

    public function montaFormulario() {

    }

    /* Procedimento chamado para inicializa��o da tela */

    public function montaTela() {
        $this->montaTitulo();
        $this->montaConsulta();
        $this->montaFormulario();
    }

    /* Setter's e Getter's */

    function getListaModel() {
        return $this->listaModel;
    }

    function setListaModel($listaModel) {
        $this->listaModel = $listaModel;
    }

}
