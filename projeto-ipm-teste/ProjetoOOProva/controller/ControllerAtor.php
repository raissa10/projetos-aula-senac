<?php

class ControllerAtor extends ControllerPadrao {

    public function processa() {
        if (Redirecionador::getParametro('acao')) {
            $oPersistenciaAtor = new PersistenciaAtor();
            $oPersistenciaAtor->setModel($this->setModelFromView());
            if ((Redirecionador::getParametro('acao')) == 'del') {
                $this->processaExclusao();
            } else if ((Redirecionador::getParametro('acao')) == 'inc') {
                if ($oPersistenciaAtor->get()) {
                    $this->processaAlteracao();
                } else {
                    $this->processaInclusao();
                }
            }
        }
        $this->MontaTela();
    }

    public function setModelFromView() {
        $oModeloAtor = new ModelAtor();
        $oModeloAtor->setCodigo(Redirecionador::getParametro('atocodigo'));
        $oModeloAtor->setNome(Redirecionador::getParametro('atonome'));
        return $oModeloAtor;
    }

    public function processaInclusao() {
        $oPersistenciaAtor = new PersistenciaAtor();
        $oPersistenciaAtor->setModel($this->setModelFromView());
        $oPersistenciaAtor->insere();
    }

    public function processaAlteracao() {
        $oPersistenciaAtor = new PersistenciaAtor();
        $oPersistenciaAtor->setModel($this->setModelFromView());
        $oPersistenciaAtor->altera();
    }

    public function processaExclusao() {
        $oPersistenciaAtor = new PersistenciaAtor();
        $oPersistenciaAtor->setModel($this->setModelFromView());
        $oPersistenciaAtor->exclui();
    }

    public function MontaTela() {
        $oPersistenciaAtor = new PersistenciaAtor();
        $aListaAtores = $oPersistenciaAtor->getAll();
        $oViewAtor = new ViewAtor();
        $oViewAtor->setListaModel($aListaAtores);
        $oViewAtor->montaTela();
    }

}
