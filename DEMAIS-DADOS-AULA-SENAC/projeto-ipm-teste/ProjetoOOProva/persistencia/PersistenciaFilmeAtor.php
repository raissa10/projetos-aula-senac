<?php

class PersistenciaFilmeAtor extends PersistenciaPadrao {

    /** @var ModelFilmeAtor */
    protected $Model;

    public function insere() {
        $sSql = "INSERT INTO cinema.tbatorfilme(filcodigo,atocodigo,atfpapel) values("
                . $this->Model->getModelFilme()->getCodigo() . ","
                . $this->Model->getModelAtor()->getCodigo() . ","
                . "'" . $this->Model->getAtfpapel() . "')";
        $this->getQuery()->query($sSql);
    }

    public function altera() {
        $sSql = "  UPDATE                  "
                . "    cinema.tbatorfilme  "
                . " SET                    "
                . "    atfpapel=          '" . $this->Model->getAtfpapel() . "'"
                . " WHERE                  "
                . "    filcodigo=          " . $this->Model->getModelFilme()->getCodigo() . ""
                . " AND                    "
                . "    atocodigo=          " . $this->Model->getModelAtor()->getCodigo();
        $this->getQuery()->query($sSql);
    }

    public function exclui() {
        $sSql = "  DELETE                 "
                . "FROM                   "
                . "    cinema.tbatorfilme "
                . "WHERE                  "
                . "    filcodigo=         " . $this->Model->getModelFilme()->getCodigo() . ""
                . "AND                    "
                . "atocodigo=             " . $this->Model->getModelAtor()->getCodigo();
        $this->getQuery()->query($sSql);
    }

    public function get() {
        $aFilmeAtores = Array();

        $sSql = "  SELECT                                                           "
                . "		cinema.tbatorfilme.*,				    "
                . "		cinema.tbcategoria.catcodigo,	         	    "
                . "		cinema.tbcategoria.catnome,		            "
                . "		cinema.tbator.atocodigo,                            "
                . "		cinema.tbator.atonome,                              "
                . "		cinema.tbfilme.*                                    "
                . "	FROM                                                        "
                . "		cinema.tbatorfilme                                  "
                . "	INNER JOIN cinema.tbfilme ON                                "
                . "	cinema.tbfilme.filcodigo=cinema.tbatorfilme.filcodigo       "
                . "	                                                            "
                . "	INNER JOIN cinema.tbator ON                                 "
                . "	cinema.tbator.atocodigo=cinema.tbatorfilme.atocodigo        "
                . "	                                                            "
                . "	INNER JOIN cinema.tbcategoria ON                            "
                . "	cinema.tbcategoria.catcodigo=cinema.tbfilme.catcodigo       "
                . "WHERE                                                            "
                . "      cinema.tbatorfilme.filcodigo=" . $this->Model->getModelFilme()->getCodigo() . "
                   AND                                                              "
                . "      cinema.tbatorfilme.atocodigo=" . $this->Model->getModelAtor()->getCodigo();
        $aFilmeAtorBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aFilmeAtorBanco as $aValor) {
            $oModelFilmeAtor = $this->getModelFromDb($aValor);
            $aFilmeAtores [] = $oModelFilmeAtor;
        }
        return $aFilmeAtores;
    }

    public function getAll() {
        $aFilmeAtores = Array();
        $sSql = "	SELECT                                                      "
                . "		cinema.tbatorfilme.*,				    "
                . "		cinema.tbcategoria.catcodigo,	         	    "
                . "		cinema.tbcategoria.catnome,		            "
                . "		cinema.tbator.atocodigo,                            "
                . "		cinema.tbator.atonome,                              "
                . "		cinema.tbfilme.*                                    "
                . "	FROM                                                        "
                . "		cinema.tbatorfilme                                  "
                . "	INNER JOIN cinema.tbfilme ON                                "
                . "	cinema.tbfilme.filcodigo=cinema.tbatorfilme.filcodigo       "
                . "	                                                            "
                . "	INNER JOIN cinema.tbator ON                                 "
                . "	cinema.tbator.atocodigo=cinema.tbatorfilme.atocodigo        "
                . "	                                                            "
                . "	INNER JOIN cinema.tbcategoria ON                            "
                . "	cinema.tbcategoria.catcodigo=cinema.tbfilme.catcodigo       "
                . "order by 1                                                       ";

        $aFilmeAtorBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aFilmeAtorBanco as $aValor) {
            $oModelFilmeAtor = $this->getModelFromDb($aValor);
            $aFilmeAtores [] = $oModelFilmeAtor;
        }
        return $aFilmeAtores;
    }

    public function getModelFromDb($aValor) {
        $oModelFilmeAtor = new ModelFilmeAtor();
        $oModelFilmeAtor->setAtfpapel($aValor['atfpapel']);

        $oModelFilme = new ModelFilme();
        $oModelFilme->setCodigo($aValor['filcodigo']);
        $oModelFilme->setTitulo($aValor['filtitulo']);
        $oModelFilme->setSinopse($aValor['filsinopse']);
        $oModelFilme->setTrailer($aValor['filtrailer']);
        $oModelFilme->setDataestreia($aValor['fildataestreia']);
        $oModelFilmeAtor->setModelFilme($oModelFilme);

        $oModelAtor = new ModelAtor();
        $oModelAtor->setCodigo($aValor['atocodigo']);
        $oModelAtor->setNome($aValor['atonome']);
        $oModelFilmeAtor->setModelAtor($oModelAtor);

        return $oModelFilmeAtor;
    }

}
