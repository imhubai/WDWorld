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
    die('fail|参数错误');
}
$username = $_GET['username'];
$password = $_GET['password'];
$name = $_GET['name'];
$mail = $_GET['email'];
$version = $_GET['version'];
if (empty($username)) die('fail|账号不能为空');
if (strlen($username) > 18 || strlen($username) < 4) die('fail|账号长度为4-18个字节');
if (!preg_match("/^[a-z\s]+$/", $username[0])) die('fail|账号必须以小写字母开头');
if (empty($name)) die('fail|用户名不能为空');
if (empty($password)) die('fail|密码不能为空');
if (empty($mail)) die('fail|邮箱不能为空');
if (!preg_match('/\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/', $mail)) {
    //die("邮箱不合法");
}
if ($version != $ver) die('fail|客户端验证失败:版本不兼容');
$database = GetDatabase();
$res = $database->get('users', ['username',], ['username[=]' => $username]);
if (!$res) {
    $res = $database->insert('users', [
        'username' => $username,
        'password' => $password,
        'name' => $name,
        'email' => $mail
    ]);
    if ($res) echo 'success|注册成功';
    else echo 'fail|注册失败';
} else {
    echo 'fail|账号已经被注册';
}