<?php

class ModelFilmeAtor extends ModelPadrao {

    private $atfpapel;
    private $ModelFilme;
    private $ModelAtor;

    function getAtfpapel() {
        return $this->atfpapel;
    }

    function getModelAtor() {
        return $this->ModelAtor;
    }

    function getModelFilme() {
        return $this->ModelFilme;
    }

    function setAtfpapel($atfpapel) {
        $this->atfpapel = $atfpapel;
    }

    function setModelAtor($ModelAtor) {
        $this->ModelAtor = $ModelAtor;
    }

    function setModelFilme($ModelFilme) {
        $this->ModelFilme = $ModelFilme;
    }

}
