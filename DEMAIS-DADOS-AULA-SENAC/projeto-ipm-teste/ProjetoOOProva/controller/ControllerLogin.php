<?php

class ControllerLogin extends ControllerPadrao {

    public function processa() {
        //Aqui faz a verificacao da senha
        $this->MontaTela();
    }

    public function MontaTela() {
        $oViewLogin = new ViewLogin();
        $oViewLogin->montaTela();
    }

    public function validaLogin() {
        $oUsuario = new ModelUsuario();
        $oUsuario->setLogin(Redirecionador::getParametro('usulogin'));
        $oUsuario->setSenha(Redirecionador::getParametro('ususenha'));

        //instancia a persistencia usuario
        $oPersistenciaUsuario = new PersistenciaUsuario();
        $oPersistenciaUsuario->setModel($oUsuario);
        $oUser = $oPersistenciaUsuario->getUsuarioByLogin();
        if ($oUser->getSenha() == md5($oUsuario->getSenha())) {
            $_SESSION['usuario'] = serializer($oUser);
            $_SESSION['autenticado'] = 1;
            echo 'mensagem de boas vindas';
            $oControllerHome = new ControllerHome();
            $oControllerHome->processa();
        } else {
            echo 'Login ou senha incorreta!';
        }
    }

}
