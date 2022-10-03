<?php

class ModelLogin extends ModelPadrao {

    private $sessao;
    private $ModelUsuario;

    function getSessao() {
        return $this->sessao;
    }

    function getModelUsuario() {
        return $this->ModelUsuario;
    }

    function setSessao($sessao) {
        $this->sessao = $sessao;
    }

    function setModelUsuario($ModelUsuario) {
        $this->ModelUsuario = $ModelUsuario;
    }

}
