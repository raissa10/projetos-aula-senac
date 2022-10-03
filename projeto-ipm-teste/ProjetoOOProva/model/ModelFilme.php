<?php

class ModelFilme extends ModelPadrao {

    private $codigo;
    private $titulo;
    private $sinopse;
    private $trailer;
    private $dataestreia;
    //atributo catcodigo já esta em categoria
    private $ModelCategoria;

    function getModelCategoria() {
        return $this->ModelCategoria;
    }

    function setModelCategoria($ModelCategoria) {
        $this->ModelCategoria = $ModelCategoria;
    }

    function getCodigo() {
        return $this->codigo;
    }

    function getTitulo() {
        return $this->titulo;
    }

    function getSinopse() {
        return $this->sinopse;
    }

    function getTrailer() {
        return $this->trailer;
    }

    function getDataestreia() {
        return $this->dataestreia;
    }

    function setCodigo($codigo) {
        $this->codigo = $codigo;
    }

    function setTitulo($titulo) {
        $this->titulo = $titulo;
    }

    function setSinopse($sinopse) {
        $this->sinopse = $sinopse;
    }

    function setTrailer($trailer) {
        $this->trailer = $trailer;
    }

    function setDataestreia($dataestreia) {
        $this->dataestreia = $dataestreia;
    }

}
