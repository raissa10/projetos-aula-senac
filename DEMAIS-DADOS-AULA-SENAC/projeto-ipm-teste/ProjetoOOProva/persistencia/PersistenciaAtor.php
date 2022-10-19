<?php

class PersistenciaAtor extends PersistenciaPadrao {

    /**  @var ModelAtor  */
    protected $Model;

    public function insere() {
        $sSql = "INSERT INTO cinema.tbator(atocodigo,atonome) values(" . $this->Model->getCodigo() . ",'" . $this->Model->getNome() . "')";
        $this->getQuery()->query($sSql);
    }

    public function altera() {
        $sSql = "UPDATE cinema.tbator SET atonome='" . $this->Model->getNome() . "' WHERE atocodigo=" . $this->Model->getCodigo();
        $this->getQuery()->query($sSql);
    }

    public function exclui() {
        $sSql = "DELETE FROM cinema.tbator WHERE atocodigo=" . $this->Model->getCodigo();
        $this->getQuery()->query($sSql);
    }

    public function get() {
        $oModeloAtor = new ModelAtor();
        $oModeloAtor->setCodigo(Redirecionador::getParametro('atocodigo'));
        $oModeloAtor->setNome(Redirecionador::getParametro('atonome'));
        $this->setModel($oModeloAtor);

        $aAtores = Array();
        $sSql = "SELECT * FROM cinema.tbator WHERE atocodigo=" . $this->Model->getCodigo();
        $aAtorBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aAtorBanco as $aValor) {
            $oModelAtor = $this->getModelFromDb($aValor);
            $aAtores [] = $oModelAtor;
        }
        return $aAtores;
    }

    public function getAll() {
        $aAtores = Array();
        $sSql = "SELECT * FROM cinema.tbator order by 1";
        $aAtorBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aAtorBanco as $aValor) {
            $oModelAtor = $this->getModelFromDb($aValor);
            $aAtores [] = $oModelAtor;
        }
        return $aAtores;
    }

    public function getModelFromDb($aValor) {
        $oModelAtor = new ModelAtor();
        $oModelAtor->setCodigo($aValor['atocodigo']);
        $oModelAtor->setNome($aValor['atonome']);
        return $oModelAtor;
    }

}
