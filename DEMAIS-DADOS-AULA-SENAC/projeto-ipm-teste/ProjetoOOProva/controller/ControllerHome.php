<?php

class ControllerHome extends ControllerPadrao {

    public function processa() {
        $this->MontaTela();
    }

    public function MontaTela() {
        $oPersistenciaFilmeAtor = new PersistenciaFilmeAtor();
        $aListaFilmeAtores = $oPersistenciaFilmeAtor->getAll();

        $oPersistenciaFilme = new PersistenciaFilme();
        $aListaFilmes = $oPersistenciaFilme->getAllFromFilmes();

        $oPersistenciaAtor = new PersistenciaAtor();
        $aListaAtores = $oPersistenciaAtor->getAll();

        $oViewHome = new ViewHome();
        $oViewHome->setListaModel($aListaFilmeAtores);
        $oViewHome->setListaFilme($aListaFilmes);
        $oViewHome->setListaAtor($aListaAtores);
        $oViewHome->montaTela();
    }

}
