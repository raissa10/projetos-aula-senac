<?php
class Query {
    
    private $conexao;
    
    public function __construct() {
        $this->conexao = ConexaoDB::getInstance();
    }
    
    public function select($sSql){
        $rSql     = $this->query($sSql);
        $aRetorno = Array();
        if ($oLinhaAtual = pg_fetch_assoc($rSql)){
            return $oLinhaAtual;
        }
        return false;
    }
    
    public function selectAll($sSql){
        $rSql     = $this->query($sSql);
        $aRetorno = Array();
        while($oLinhaAtual = pg_fetch_assoc($rSql)){
            $aRetorno[] = $oLinhaAtual;
        }
        return $aRetorno;
    }
    
    public function query($sSql){
        $rRetorno = @pg_query($this->conexao, $sSql);
        if($rRetorno !== false){
            return $rRetorno;
        }
        echo "<pre>".print_r(pg_last_error($this->conexao). $sSql)."</pre>";
        throw new Exception('Erro ao executar comando SQL');
    }
    
}