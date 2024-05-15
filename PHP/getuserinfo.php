<?php
header('Content-Type:text/html;charset=utf-8');
require 'sql.php';
if (!isset($_GET['username']) || !isset($_GET['password']) || !isset($_GET['version'])) {
    die('fail|参数错误');
}
$username = $_GET['username'];
$password = $_GET['password'];
$version = $_GET['version'];
if (empty($username)) die('fail|账号不能为空');
if (empty($password)) die('fail|密码不能为空');
if (empty($version)) die('fail|ver不能为空');
$database = GetDatabase();
$res = $database->get('users', ['username', 'password', 'email', 'name'], ['username' => $username]);
if ($res['username'] != $username) {
    die('fail|用户名不存在');
}
if ($res['password'] != $password) {
    die('fail|密码错误');
}
$mail = $res['email'];
die('success|' . $res['name'] . '&' . $res['password'] . '&' . $res['email']);