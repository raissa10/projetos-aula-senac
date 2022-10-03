<?php

/**
 * Responsável por apresentar os dados aos usuários
 */
abstract class ViewPadrao {
    /* Em caso de consultas, representa o array com os modelos para criação da tabela */

    protected $listaModel;

    /* Responsável por montar o HTML com o título da consulta */

    abstract function montaTitulo();

    /* Responsável por montar a consulta */

    public function montaConsulta() {

    }

    /* Responsável por montar o formulário para cadastrar as informações */

    public function montaFormulario() {

    }

    /* Procedimento chamado para inicialização da tela */

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
