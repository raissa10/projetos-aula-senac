<?php
function __autoload($className){
    if (strpos($className, 'Controller') !== false){
        require_once('controller/'.$className.'.php');
    } else if (strpos($className, 'View') !== false){
        require_once('view/'.$className.'.php');
    } else if (strpos($className, 'Persistencia') !== false){
        require_once('persistencia/'.$className.'.php');
    } else if (strpos($className, 'Model') !== false){
        require_once('model/'.$className.'.php');
    } else {
        require_once('core/'.$className.'.php');
    }
}