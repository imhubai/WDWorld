<?php
global $ver;
header('Content-Type:text/html;charset=utf-8');
require 'sql.php';
if (!isset($_GET['username']) ||
    !isset($_GET['name']) ||
    !isset($_GET['password']) ||
    !isset($_GET['email']) ||
    !isset($_GET['version'])
) {
    die('参数错误');
}
$username = $_GET['username'];
$password = $_GET['password'];
$name = $_GET['name'];
$mail = $_GET['email'];
$version = $_GET['version'];
if (empty($username)) die('账号不能为空');
if (strlen($username) > 18 || strlen($username) < 4) die('账号长度为4-18个字节');
if (!preg_match("/^[a-z\s]+$/", $username[0])) die('账号必须以小写字母开头');
if (empty($name)) die('用户名不能为空');
if (empty($password)) die('密码不能为空');
if (empty($mail)) die('邮箱不能为空');
if (!preg_match("/^[a-z0-9!#$%&'*+\/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+\/=?^_`{|}~-]+)*@(?:[-_a-z0-9]+\.)*[a-z0-9][-a-z0-9]{0,62}\.(?:[a-z]{2}\.)?[a-z]{2,}$/i", $mail)) {
    die('邮箱地址格式错误');
}
if ($version != $ver) die('客户端验证失败:版本不兼容');
$database = GetDatabase();
$res = $database->get('users', ['username',], ['username[=]' => $username]);
if (!$res) {
    $res = $database->insert('users', [
        'username' => $username,
        'password' => $password,
        'name' => $name,
        'email' => $mail
    ]);
    if ($res) echo '注册成功';
    else echo '注册失败';
} else {
    echo '账号已经被注册';
}