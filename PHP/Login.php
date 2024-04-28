<?php
header('Content-Type:text/html;charset=utf-8');
require 'sql.php';
if (!isset($_GET['id']) || !isset($_GET['pwd'])) {
    die('参数错误');
} else {
    die($_GET['id']);
}
?>