<?php

class PersistenciaFilme extends PersistenciaPadrao {

    /** @var ModelFilme */
    protected $Model;

    public function insere() {
        $sSql = "INSERT INTO cinema.tbfilme(filcodigo,filtitulo,filsinopse,filtrailer,fildataestreia,catcodigo) "
                . "values("
                . $this->Model->getCodigo() . "," .
                "'" . $this->Model->getTitulo() . "'," .
                "'" . $this->Model->getSinopse() . "'," .
                "'" . $this->Model->getTrailer() . "'," .
                "'" . $this->Model->getDataestreia() . "',"
                . $this->Model->getModelCategoria() . ")";
        $this->getQuery()->query($sSql);
    }

    public function altera() {
        $sSql = "UPDATE cinema.tbfilme SET filtitulo='" . $this->Model->getTitulo() . "',"
                . "filsinopse='" . $this->Model->getSinopse() . "',"
                . "filtrailer='" . $this->Model->getTrailer() . "',"
                . "fildataestreia='" . $this->Model->getDataestreia() . "',"
                . "catcodigo=" . $this->Model->getModelCategoria()
                . "WHERE filcodigo=" . $this->Model->getCodigo();
        $this->getQuery()->query($sSql);
    }

    public function exclui() {
        $sSql = "DELETE FROM cinema.tbfilme WHERE filcodigo=" . $this->Model->getCodigo();
        $this->getQuery()->query($sSql);
    }

    public function get() {
        $aFilmes = Array();
        $sSql = "  SELECT                                                 "
                . "cinema.tbfilme.*,                                      "
                . "cinema.tbcategoria.catnome                             "
                . "FROM                                                   "
                . "     cinema.tbfilme                                    "
                . "INNER JOIN cinema.tbcategoria ON                       "
                . "cinema.tbfilme.catcodigo=cinema.tbcategoria.catcodigo  "
                . "WHERE filcodigo=" . $this->Model->getCodigo();
        $aFilmeBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aFilmeBanco as $aValor) {
            $oModelFilme = $this->getModelFromDb($aValor);
            $aFilmes [] = $oModelFilme;
        }
        return $aFilmes;
    }

    public function getAtorFromFilme($CodigoFilme) {
        $aFilmes = Array();
        $sSql = "  SELECT                                               "
                . "cinema.tbatorfilme.*,                                "
                . "cinema.tbator.*                                      "
                . "FROM cinema.tbatorfilme                              "
                . "INNER JOIN cinema.tbator ON                          "
                . "cinema.tbatorfilme.atocodigo=cinema.tbator.atocodigo "
                . "WHERE filcodigo=" . $CodigoFilme;
        $aFilmeBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aFilmeBanco as $aValor) {
            $aFilmes [] = $aValor;
        }
        return $aFilmes;
    }

    public function getAll() {
        $aFilmes = Array();
        $sSql = "  SELECT                                                 "
                . "cinema.tbfilme.*,                                      "
                . "cinema.tbcategoria.catnome                             "
                . "FROM                                                   "
                . "     cinema.tbfilme                                    "
                . "INNER JOIN cinema.tbcategoria ON                       "
                . "cinema.tbfilme.catcodigo=cinema.tbcategoria.catcodigo  "
                . "order by 1";
        $aFilmeBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aFilmeBanco as $aValor) {
            $oModelFilme = $this->getModelFromDb($aValor);
            $aFilmes [] = $oModelFilme;
        }
        return $aFilmes;
    }

    public function getAllFromFilmes() {
        $sSql = "  SELECT                                                 "
                . "cinema.tbfilme.*,                                      "
                . "cinema.tbcategoria.catnome                             "
                . "FROM                                                   "
                . "     cinema.tbfilme                                    "
                . "INNER JOIN cinema.tbcategoria ON                       "
                . "cinema.tbfilme.catcodigo=cinema.tbcategoria.catcodigo  ";
        $aLista = $this->getQuery()->selectAll($sSql);
        foreach ($aLista as $Indice => $aValor) {
            $aAtores = $this->getAtorFromFilme($aValor['filcodigo']);
            $aLista[$Indice]['atores'] = $aAtores;
        }
        return $aLista;
    }

    public function getModelFromDb($aValor) {
        $oModelFilme = new ModelFilme();
        $oModelFilme->setCodigo($aValor['filcodigo']);
        $oModelFilme->setTitulo($aValor['filtitulo']);
        $oModelFilme->setSinopse($aValor['filsinopse']);
        $oModelFilme->setTrailer($aValor['filtrailer']);
        $oModelFilme->setDataestreia($aValor['fildataestreia']);

        $oModelCategoria = new ModelCategoria();
        $oModelCategoria->setCodigo($aValor['catcodigo']);
        $oModelCategoria->setNome($aValor['catnome']);
        $oModelFilme->setModelCategoria($oModelCategoria);

        return $oModelFilme;
    }

}
