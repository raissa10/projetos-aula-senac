<?php

class PersistenciaUsuario extends PersistenciaPadrao {

    /** @var ModelUsuario */
    protected $Model;

    public function insere() {
        $sSql = "INSERT INTO cinema.tbusuario(usucodigo,usunome,ususenha) values(" . $this->Model->getCodigo() . ",'" . $this->Model->getNome() . "','" . $this->Model->getSenha() . "')";
        $this->getQuery()->query($sSql);
    }

    public function altera() {
        $sSql = "UPDATE cinema.tbusuario SET usunome='" . $this->Model->getNome() . "',ususenha='" . $this->Model->getSenha() . "' WHERE usucodigo=" . $this->Model->getCodigo();
        $this->getQuery()->query($sSql);
    }

    public function exclui() {
        $sSql = "DELETE FROM cinema.tbusuario WHERE usucodigo=" . $this->Model->getCodigo();
        $this->getQuery()->query($sSql);
    }

    public function get() {
        $oModeloUsuario = new ModelUsuario();
        $oModeloUsuario->setCodigo(Redirecionador::getParametro('usucodigo'));
        $oModeloUsuario->setNome(Redirecionador::getParametro('usunome'));
        $oModeloUsuario->setSenha(Redirecionador::getParametro('ususenha'));
        $this->setModel($oModeloUsuario);

        $aUsuarios = Array();
        $sSql = "SELECT * FROM cinema.tbusuario WHERE usucodigo=" . $this->Model->getCodigo();
        $aUsuarioBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aUsuarioBanco as $aValor) {
            $oModelUsuario = $this->getModelFromDb($aValor);
            $aUsuarios [] = $oModelUsuario;
        }
        return $aUsuarios;
    }

    public function getAll() {
        $aUsuarios = Array();
        $sSql = "SELECT * FROM cinema.tbusuario order by 1";
        $aUsuarioBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aUsuarioBanco as $aValor) {
            $oModelUsuario = $this->getModelFromDb($aValor);
            $aUsuarios [] = $oModelUsuario;
        }
        return $aUsuarios;
    }

    public function getModelFromDb($aValor) {

        $oModeloUsuario = new ModelUsuario();
        $oModeloUsuario->setCodigo($aValor['usucodigo']);
        $oModeloUsuario->setNome($aValor['usunome']);
        $oModeloUsuario->setSenha($aValor['ususenha']);

        return $oModeloUsuario;
    }

    public function getUsuarioByLogin() {
        $oModeloUsuario = new ModelUsuario();
        $oModeloUsuario->setCodigo(Redirecionador::getParametro('usucodigo'));
        $oModeloUsuario->setNome(Redirecionador::getParametro('usunome'));
        $oModeloUsuario->setSenha(Redirecionador::getParametro('ususenha'));
        $this->setModel($oModeloUsuario);

        $aUsuarios = Array();
        $sSql = "SELECT * FROM cinema.tbusuario WHERE usucodigo=" . $this->Model->getCodigo();
        $aUsuarioBanco = $this->getQuery()->selectAll($sSql);
        foreach ($aUsuarioBanco as $aValor) {
            $oModelUsuario = $this->getModelFromDb($aValor);
            $aUsuarios [] = $oModelUsuario;
        }
        return $aUsuarios;
    }

}
