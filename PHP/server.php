<?php
header('Content-Type:text/html;charset=utf-8');
require 'sql.php';

$serverList = [
    0 => ['name' => '测试 修真', 'path' => 'hugongzi.top', 'port' => 6666],
    1 => ['name' => '测试 地狱', 'path' => 'hugongzi.top', 'port' => 7777],
];

if (!isset($_GET['username']) || !isset($_GET['password'])) {
    die('fail|参数错误');
}
$username = $_GET['username'];
$password = $_GET['password'];
if (empty($username)) die('fail|账号不能为空');
if (empty($password)) die('fail|密码不能为空');
$database = GetDatabase();
$res = $database->get('users', ['username', 'password'], ['username' => $username]);
if ($res['username'] != $username) {
    die('fail|用户名不存在');
}
if ($res['password'] != $password) {
    die('fail|密码错误');
}
echo 'success|';
foreach ($serverList as $server) {
    $line = GenServerLine($server);
    echo($line . '#');
}
function GenServerLine($server): string
{
    return $server['name'] . '&' . $server['path'] . ':' . $server['port'];
}
