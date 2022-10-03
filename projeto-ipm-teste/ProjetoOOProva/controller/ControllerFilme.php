<?php

class ControllerFilme extends ControllerPadrao {

    public function processa() {
        if (Redirecionador::getParametro('acao')) {
            $oPersistenciaFilme = new PersistenciaFilme();
            $oPersistenciaFilme->setModel($this->setModelFromView());
            if ((Redirecionador::getParametro('acao')) == 'del') {
                $this->processaExclusao();
            } else if ((Redirecionador::getParametro('acao')) == 'inc') {
                if ($oPersistenciaFilme->get()) {
                    $this->processaAlteracao();
                } else {
                    $this->processaInclusao();
                }
            }
        }
        $this->MontaTela();
    }

    public function setModelFromView() {
        $oModeloFilme = new ModelFilme();
        $oModeloFilme->setCodigo(Redirecionador::getParametro('filcodigo'));
        $oModeloFilme->setTitulo(Redirecionador::getParametro('filtitulo'));
        $oModeloFilme->setSinopse(Redirecionador::getParametro('filsinopse'));
        $oModeloFilme->setTrailer(Redirecionador::getParametro('filtrailer'));
        $oModeloFilme->setDataestreia(Redirecionador::getParametro('fildataestreia'));
        $oModeloFilme->setModelCategoria(Redirecionador::getParametro('catcodigo'));
        return $oModeloFilme;
    }

    public function processaInclusao() {
        $oPersistenciaFilme = new PersistenciaFilme();
        $oPersistenciaFilme->setModel($this->setModelFromView());
        $oPersistenciaFilme->insere();
    }

    public function processaAlteracao() {
        $oPersistenciaFilme = new PersistenciaFilme();
        $oPersistenciaFilme->setModel($this->setModelFromView());
        $oPersistenciaFilme->altera();
    }

    public function processaExclusao() {
        $oPersistenciaFilme = new PersistenciaFilme();
        $oPersistenciaFilme->setModel($this->setModelFromView());
        $oPersistenciaFilme->exclui();
    }

    public function MontaTela() {
        $oPersistenciaFilme = new PersistenciaFilme();
        $aListaFilmes = $oPersistenciaFilme->getAll();
        $opersistenciaCategoria = new PersistenciaCategoria();
        $aListaCategorias = $opersistenciaCategoria->getAll();
        $oViewFilme = new ViewFilme();
        $oViewFilme->setListaModel($aListaFilmes);
        $oViewFilme->setListaCategoria($aListaCategorias);
        $oViewFilme->montaTela();
    }

}
