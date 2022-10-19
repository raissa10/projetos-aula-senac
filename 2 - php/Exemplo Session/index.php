<?php

require_once('usuario.php');
//sessions
/*
  session_start();
  if (isset($_SESSION['usuario'])) {
  echo "Bem vindo: {$_SESSION['usuario']}!";
  echo '<br>Id da Sessao: <br>' . session_id() . ' <br>';
  } else {
  echo 'voce nunca passou por aqui! ';
  $_SESSION['usuario'] = 'joao';
  }
 */
echo '<br>';
echo '<br>';
echo '<br>';
echo 'Classe usuario:';
echo '<br>';
echo '<br>';

session_start();
$oUsuario = new Usuario();
if (isset($_SESSION['usuario'])) {
    $oUsuario = unserialize($_SESSION['usuario']);
    $oUsuario->imprime();
	echo '<br>Id da Sessao: <br>' . session_id() . ' <br>';
} else {
    echo 'Voce nunca passou por aqui! ';
    $oUsuario = new Usuario();
    $oUsuario->setCodigo(1);
    $oUsuario->setNome('Geo');
    $_SESSION['usuario'] = serialize($oUsuario);
}

unset($_SESSION['usuario']);
