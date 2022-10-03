<?php

class ControllerUsuario extends ControllerPadrao {

    public function processa() {
        if (Redirecionador::getParametro('acao')) {
            $oPersistenciaUsuario = new PersistenciaUsuario();
            $oPersistenciaUsuario->setModel($this->setModelFromView());
            if ((Redirecionador::getParametro('acao')) == 'del') {
                $this->processaExclusao();
            } else if ((Redirecionador::getParametro('acao')) == 'inc') {
                if ($oPersistenciaUsuario->get()) {
                    $this->processaAlteracao();
                } else {
                    $this->processaInclusao();
                }
            }
        }
        $this->MontaTela();
    }

    public function setModelFromView() {
        $oModeloUsuario = new ModelUsuario();
        $oModeloUsuario->setCodigo(Redirecionador::getParametro('usucodigo'));
        $oModeloUsuario->setNome(Redirecionador::getParametro('usunome'));
        $oModeloUsuario->setSenha(Redirecionador::getParametro('ususenha'));
        return $oModeloUsuario;
    }

    public function processaInclusao() {
        $oPersistenciaUsuario = new PersistenciaUsuario();
        $oPersistenciaUsuario->setModel($this->setModelFromView());
        $oPersistenciaUsuario->insere();
    }

    public function processaAlteracao() {
        $oPersistenciaUsuario = new PersistenciaUsuario();
        $oPersistenciaUsuario->setModel($this->setModelFromView());
        $oPersistenciaUsuario->altera();
    }

    public function processaExclusao() {
        $oPersistenciaUsuario = new PersistenciaUsuario();
        $oPersistenciaUsuario->setModel($this->setModelFromView());
        $oPersistenciaUsuario->exclui();
    }

    public function MontaTela() {
        $oPersistenciaUsuario = new PersistenciaUsuario();
        $aListaUsuarios = $oPersistenciaUsuario->getAll();

        $oViewUsuario = new ViewUsuario();
        $oViewUsuario->setListaModel($aListaUsuarios);
        $oViewUsuario->montaTela();
    }

}
