<?php

class ModelPessoa extends ModelPadrao {

    private $pescodigo;
    private $pesnome;
    private $pesdatanascimento;
    private $pesbairro;
    private $pescidade;
    private $pesestado;
    private $pesicms;

    function getPesestado() {
        return $this->pesestado;
    }

    function setPesestado($pesestado) {
        $this->pesestado = $pesestado;
    }

    function getPescodigo() {
        return $this->pescodigo;
    }

    function getPesnome() {
        return $this->pesnome;
    }

    function getPesdatanascimento() {
        return $this->pesdatanascimento;
    }

    function getPesbairro() {
        return $this->pesbairro;
    }

    function getPescidade() {
        return $this->pescidade;
    }

    function getPesicms() {
        return $this->pesicms;
    }

    function setPescodigo($pescodigo) {
        $this->pescodigo = $pescodigo;
    }

    function setPesnome($pesnome) {
        $this->pesnome = $pesnome;
    }

    function setPesdatanascimento($pesdatanascimento) {
        $this->pesdatanascimento = $pesdatanascimento;
    }

    function setPesbairro($pesbairro) {
        $this->pesbairro = $pesbairro;
    }

    function setPescidade($pescidade) {
        $this->pescidade = $pescidade;
    }

    function setPesicms($pesicms) {
        $this->pesicms = $pesicms;
    }

}
