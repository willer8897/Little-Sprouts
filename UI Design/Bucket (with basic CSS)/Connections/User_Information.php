<?php
# FileName="Connection_php_mysql.htm"
# Type="MYSQL"
# HTTP="true"
$hostname_User_Information = "144.13.22.61:3306";
$database_User_Information = "LS";
$username_User_Information = "sericatik4839";
$password_User_Information = "1t5n8o3f9E=]";
$User_Information = mysql_pconnect($hostname_User_Information, $username_User_Information, $password_User_Information) or trigger_error(mysql_error(),E_USER_ERROR); 
?>