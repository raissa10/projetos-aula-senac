<?php

class ControllerPessoa extends ControllerPadrao {

    public function processa() {
        if (Redirecionador::getParametro('acao')) {
            $oPersistenciaPessoa = new PersistenciaPessoa();
            $oPersistenciaPessoa->setModel($this->setModelFromView());
            if ((Redirecionador::getParametro('acao')) == 'del') {
                $this->processaExclusao();
            } else if ((Redirecionador::getParametro('acao')) == 'inc') {
                $this->processaInclusao();
            }
        }
        $this->MontaTela();
    }

    public function setModelFromView() {
        $oModeloPessoa = new ModelPessoa();
        $oModeloPessoa->setPescodigo(Redirecionador::getParametro('pescodigo'));
        $oModeloPessoa->setPesnome(Redirecionador::getParametro('pesnome'));
        $oModeloPessoa->setPesdatanascimento(Redirecionador::getParametro('pesdatanascimento'));
        $oModeloPessoa->setPesbairro(Redirecionador::getParametro('pesbairro'));
        $oModeloPessoa->setPescidade(Redirecionador::getParametro('pescidade'));
        $oModeloPessoa->setPesicms(Redirecionador::getParametro('pesicms'));
        $oModeloPessoa->setPesestado(Redirecionador::getParametro('pesestado'));

        return $oModeloPessoa;
    }

    public function processaInclusao() {
        $oPersistenciaPessoa = new PersistenciaPessoa();
        $oPersistenciaPessoa->setModel($this->setModelFromView());
        $oPersistenciaPessoa->insere();
    }

    public function processaExclusao() {
        $oPersistenciaPessoa = new PersistenciaPessoa();
        $oPersistenciaPessoa->setModel($this->setModelFromView());
        $oPersistenciaPessoa->exclui();
    }

    public function MontaTela() {
        $oPersistenciaPessoa = new PersistenciaPessoa();
        $aListaPessoas = $oPersistenciaPessoa->getAll();

        $oViewPessoa = new ViewPessoa();
        $oViewPessoa->setListaModel($aListaPessoas);
        $oViewPessoa->montaTela();
    }

}
