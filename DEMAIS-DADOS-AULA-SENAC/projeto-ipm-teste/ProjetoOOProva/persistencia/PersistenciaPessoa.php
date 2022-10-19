<?php

class PersistenciaPessoa extends PersistenciaPadrao {

    /** @var ModelPessoa */
    protected $Model;

    public function insere() {
        $sSql = "INSERT INTO cadastro.tbpessoa(pescodigo,pesnome,pesdatanascimento,pesbairro,pescidade,pesicms,pesestado) "
                . "values("
                . $this->Model->getPescodigo() . "," .
                "'" . $this->Model->getPesnome() . "'," .
                "'" . $this->Model->getPesdatanascimento() . "'," .
                "'" . $this->Model->getPesbairro() . "'," .
                "'" . $this->Model->getPescidade() . "'," .
                "'" . $this->Model->getPesicms() . "',"
                . $this->Model->getPesestado() . ")";
        $this->getQuery()->query($sSql);
    }

    public function exclui() {
        $sSql = "DELETE FROM cadastro.tbpessoa WHERE pescodigo=" . $this->Model->getPescodigo();
        $this->getQuery()->query($sSql);
    }

    public function get() {
        $aPessoas = Array();
        $sSql = "  SELECT                                                 "
                . "cadastro.tbpessoa.*"
                . "FROM                                                   "
                . "WHERE pescodigo=" . $this->Model->getPescodigo();
        $aPessoaBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aPessoaBanco as $aValor) {
            $oModelPessoa = $this->getModelFromDb($aValor);
            $aPessoas [] = $oModelPessoa;
        }
        return $aPessoas;
    }

    public function getAll() {
        $aPessoas = Array();
        $sSql = "  SELECT                                                 "
                . "cadastro.tbpessoa.*                             "
                . "FROM                                                   "
                . "     cadastro.tbpessoa                                    "
                . " order by 1";
        $aPessoaBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aPessoaBanco as $aValor) {
            $oModelPessoa = $this->getModelFromDb($aValor);
            $aPessoas [] = $oModelPessoa;
        }
        return $aPessoas;
    }

    public function getModelFromDb($aValor) {
        $oModelPessoa = new ModelPessoa();
        $oModelPessoa->setPescodigo($aValor['pescodigo']);
        $oModelPessoa->setPesnome($aValor['pesnome']);
        $oModelPessoa->setPesdatanascimento($aValor['pesdatanascimento']);
        $oModelPessoa->setPesbairro($aValor['pesbairro']);
        $oModelPessoa->setPescidade($aValor['pescidade']);
        $oModelPessoa->setPesicms($aValor['pesicms']);
        $oModelPessoa->setPesestado($aValor['pesestado']);

        return $oModelPessoa;
    }

}
