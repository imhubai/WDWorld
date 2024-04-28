<?php 
require  'medoo.php';
function GetDatabase()
{
	$database = new medoo([
   		'database_type' => 'mysql',
   		'database_name' => 'wdw',
   		'server' => 'localhost',
   		'username' => 'root',
   		'password' => '',
   		'charset' => 'utf8',
	]);
	return $database;
}
 ?>