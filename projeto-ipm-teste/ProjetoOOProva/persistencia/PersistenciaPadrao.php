<?php

abstract class PersistenciaPadrao {

    /**
     *
     * @var ModelPadrao
     */
    protected $Model;

    /**
     *
     * @var Query
     */
    private $Query;

    /* Respons�vel por Inserir os dados no banco de dados atrav�s do modelo de dados */

    abstract function insere();

    /* Respons�vel por Alterar os dados no banco de dados atrav�s do modelo de dados */

    abstract function exclui();

    /* Respons�vel por Obter os dados no banco de dados atrav�s de um modelo de dados */

    abstract function get();

    /* Respons�vel por Obter todos os modelos no banco de dados atrav�s */

    abstract function getAll();

    /* Respons�vel por receber um array retornado do banco de dados e retornar um modelo de dados populado */

    abstract function getModelFromDb($aValor);

    function getQuery() {
        if (!isset($this->Query)) {
            $this->Query = new Query();
        }
        return $this->Query;
    }

    function setQuery(Query $Query) {
        $this->Query = $Query;
    }

    function getModel() {
        return $this->Model;
    }

    function setModel(ModelPadrao $Model) {
        $this->Model = $Model;
    }

}
