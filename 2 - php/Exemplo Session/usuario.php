<?php

class usuario {

    private $codigo;
    private $nome;

    function getCodigo() {
        return $this->codigo;
    }

    function getNome() {
        return $this->nome;
    }

    function setCodigo($codigo) {
        $this->codigo = $codigo;
    }

    function setNome($nome) {
        $this->nome = $nome;
    }

    public function imprime() {
        echo $this->codigo . ' - ' . $this->nome;
    }

}
