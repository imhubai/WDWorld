<?php
header('Content-Type:text/html;charset=utf-8');
require 'sql.php';
if (!isset($_GET['username']) || !isset($_GET['password']) || !isset($_GET['version'])) {
    die('参数错误');
}
$username = $_GET['username'];
$password = $_GET['password'];
$version = $_GET['version'];
$database = GetDatabase();
$res = $database->get('users', ['username', 'password'], ['username' => $username]);
if ($res['username'] != $username) {
    die('用户名不存在');
}
if ($res['password'] != $password) {
    die('密码错误');
}
if (isset($_GET['newpwd'])) {
    $newpwd = $_GET['newpwd'];
    if (empty($newpwd)) die('密码不能为空');
    $res = $database->update('users', ['password' => $newpwd], ['username' => $username]);
    if ($res) echo '密码修改成功' . $newpwd;
    else echo '密码修改失败 请重试';
} else if (isset($_GET['newemail'])) {
    $newemail = $_GET['newemail'];
    if (empty($newemail)) die('邮箱不能为空');
    if (!preg_match("/^[a-z0-9!#$%&'*+\/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+\/=?^_`{|}~-]+)*@(?:[-_a-z0-9]+\.)*[a-z0-9][-a-z0-9]{0,62}\.(?:[a-z]{2}\.)?[a-z]{2,}$/i", $newemail)) {
        die('邮箱地址格式错误');
    }
    $res = $database->update('users', ['email' => $newemail], ['name' => $username]);
    if ($res) echo '邮箱绑定成功' . $newemail;
    else echo '邮箱绑定失败 请重试';
} else {
    die('参数错误！');
}


