<?php

class ControllerFilmeAtor extends ControllerPadrao {

    public function processa() {
        if (Redirecionador::getParametro('acao')) {

            $oPersistenciaFilmeAtor = new PersistenciaFilmeAtor();
            $oPersistenciaFilmeAtor->setModel($this->setModelFromView());

            if ((Redirecionador::getParametro('acao')) == 'del') {
                $this->processaExclusao();
            } else if ((Redirecionador::getParametro('acao')) == 'inc') {

                if ($oPersistenciaFilmeAtor->get()) {
                    $this->processaAlteracao();
                } else {
                    $this->processaInclusao();
                }
            }
        }
        $this->MontaTela();
    }

    public function setModelFromView() {
        $oModeloFilmeAtor = new ModelFilmeAtor();
        $oModelAtor = new ModelAtor();
        $oModelAtor->setCodigo(Redirecionador::getParametro('atocodigo'));

        $oModelFilme = new ModelFilme();
        $oModelFilme->setCodigo(Redirecionador::getParametro('filcodigo'));

        $oModeloFilmeAtor->setAtfpapel(Redirecionador::getParametro('atfpapel'));
        $oModeloFilmeAtor->setModelAtor($oModelAtor);
        $oModeloFilmeAtor->setModelFilme($oModelFilme);

        return $oModeloFilmeAtor;
    }

    public function processaInclusao() {
        $oPersistenciaFilmeAtor = new PersistenciaFilmeAtor();
        $oPersistenciaFilmeAtor->setModel($this->setModelFromView());
        $oPersistenciaFilmeAtor->insere();
    }

    public function processaAlteracao() {
        $oPersistenciaFilmeAtor = new PersistenciaFilmeAtor();
        $oPersistenciaFilmeAtor->setModel($this->setModelFromView());
        $oPersistenciaFilmeAtor->altera();
    }

    public function processaExclusao() {
        $oPersistenciaFilmeAtor = new PersistenciaFilmeAtor();
        $oPersistenciaFilmeAtor->setModel($this->setModelFromView());
        $oPersistenciaFilmeAtor->exclui();
    }

    public function MontaTela() {
        $oPersistenciaFilmeAtor = new PersistenciaFilmeAtor();
        $aListaFilmeAtores = $oPersistenciaFilmeAtor->getAll();

        $oPersistenciaFilme = new PersistenciaFilme();
        $aListaFilmes = $oPersistenciaFilme->getAll();

        $oPersistenciaAtor = new PersistenciaAtor();
        $aListaAtores = $oPersistenciaAtor->getAll();

        $oViewFilmeAtor = new ViewFilmeAtor();
        $oViewFilmeAtor->setListaModel($aListaFilmeAtores);
        $oViewFilmeAtor->setListaFilme($aListaFilmes);
        $oViewFilmeAtor->setListaAtor($aListaAtores);

        $oViewFilmeAtor->montaTela();
    }

}
