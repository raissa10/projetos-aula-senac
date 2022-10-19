<?php

class PersistenciaCategoria extends PersistenciaPadrao {

    /** @var ModelCategoria */
    protected $Model;

    public function insere() {
        $sSql = "INSERT INTO cinema.tbcategoria(catcodigo,catnome) values(" . $this->Model->getCodigo() . ",'" . $this->Model->getNome() . "')";
        $this->getQuery()->query($sSql);
    }

    public function altera() {
        $sSql = "UPDATE cinema.tbcategoria SET catnome='" . $this->Model->getNome() . "' WHERE catcodigo=" . $this->Model->getCodigo();
        $this->getQuery()->query($sSql);
    }

    public function exclui() {
        $sSql = "DELETE FROM cinema.tbcategoria WHERE catcodigo=" . $this->Model->getCodigo();
        $this->getQuery()->query($sSql);
    }

    public function get() {
        $oModeloCategoria = new ModelCategoria();
        $oModeloCategoria->setCodigo(Redirecionador::getParametro('catcodigo'));
        $oModeloCategoria->setNome(Redirecionador::getParametro('catnome'));
        $this->setModel($oModeloCategoria);

        $aCategorias = Array();
        $sSql = "SELECT * FROM cinema.tbcategoria WHERE catcodigo=" . $this->Model->getCodigo();
        $aCategoriaBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aCategoriaBanco as $aValor) {
            $oModelCategoria = $this->getModelFromDb($aValor);
            $aCategorias [] = $oModelCategoria;
        }
        return $aCategorias;
    }

    public function getCategoriaFromFilme() {		
		$oModeloCategoria = new ModelCategoria();
        $oModeloCategoria->setCodigo(Redirecionador::getParametro('catcodigo'));
        $oModeloCategoria->setNome(Redirecionador::getParametro('catnome'));
        $this->setModel($oModeloCategoria);

        $aCategorias = Array();
        $sSql = "  SELECT                                                 "
                . "cinema.tbfilme.*,                                      "
                . "cinema.tbcategoria.catnome                             "
                . "FROM                                                   "
                . "     cinema.tbfilme                                    "
                . "INNER JOIN cinema.tbcategoria ON                       "
                . "cinema.tbfilme.catcodigo=cinema.tbcategoria.catcodigo  "
                . "WHERE filcodigo=" . $this->Model->getCodigo();
				
        $aCategoriaBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aCategoriaBanco as $aValor) {
            $oModelCategoria = $this->getModelFromDb($aValor);
            $aCategorias [] = $oModelCategoria;			
        }

        return $aCategorias;
    }

    public function getAll() {
        $aCategorias = Array();
        $sSql = "SELECT * FROM cinema.tbcategoria order by 1";
        $aCategoriaBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aCategoriaBanco as $aValor) {
            $oModelCategoria = $this->getModelFromDb($aValor);
            $aCategorias [] = $oModelCategoria;
        }
        return $aCategorias;
    }

    public function getModelFromDb($aValor) {
        $oModelCategoria = new ModelCategoria();
        $oModelCategoria->setCodigo($aValor['catcodigo']);
        $oModelCategoria->setNome($aValor['catnome']);
        
		return $oModelCategoria;
    }

}
