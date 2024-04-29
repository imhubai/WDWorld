<?php

use Medoo\Medoo;

require 'medoo.php';
global $ver;
$ver='1.0';
function GetDatabase(): Medoo
{
    return new medoo([
        'database_type' => 'mysql',
        'database_name' => 'wdw',
        'server' => 'localhost',
        'username' => 'root',
        'password' => '',
        'charset' => 'utf8',
    ]);
}