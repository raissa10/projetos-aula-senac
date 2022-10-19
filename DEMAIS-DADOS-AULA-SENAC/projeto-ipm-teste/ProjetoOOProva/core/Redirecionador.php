<?php

class Redirecionador {

    static $oParametros = Array();

    public function __construct() {
        $this->getAllParametros();
        $this->verificaDestino();
    }

    private function verificaDestino() {
        if (self::getParametro('pagina')) {
            $sController = 'Controller' . $_GET['pagina'];
            if (file_exists('./controller/' . $sController . '.php')) {
                $oController = new $sController();
                $oController->processa();
            } else {
                throw new Exception('P�gina solicitada n�o encontrada! ' . $sController);
            }
        } else {
            $oControllerPessoa = new ControllerPessoa();
            $oControllerPessoa->processa();
        }
    }

    private function getAllParametros() {
        foreach ($_GET as $chave => $valor) {
            self::$oParametros[$chave] = $valor;
        }
        foreach ($_POST as $chave => $valor) {
            self::$oParametros[$chave] = $valor;
        }
    }

    public static function getParametro($name) {
        if (isset(self::$oParametros[$name])) {
            return self::$oParametros[$name];
        }
        return false;
    }

}
