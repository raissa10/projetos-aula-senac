<?php

class ConexaoDB {

    const HOST = '127.0.0.1';
    const PORT = '5432';
    const DBNAME = 'avaliacaofinal';
    const USER = 'postgres';
    const PASS = 'postgresql';

    private static $conexao = null;

    public static function getInstance() {
        if (is_null(self::$conexao)) {
            self::conecta();
        }
        return self::$conexao;
    }

    public static function conecta() {
        if (is_null(self::$conexao)) {
            self::$conexao = pg_connect('host=' . self::HOST . ' port=' . self::PORT . ' dbname=' . self::DBNAME . ' user=' . self::USER . ' password=' . self::PASS);
            if (self::$conexao === false) {
                throw new Exception('Erro ao comunicar com banco de dados!');
            }
        }
        return self::$conexao;
    }

    public static function desconecta() {
        $bFechou = true;
        if (!is_null(self::$conexao)) {
            $bFechou = pg_close(self::$conexao);
            self::$conexao = null;
        }
        return $bFechou;
    }

    public function __destruct() {
        self::desconecta();
    }

}
