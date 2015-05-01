<?php
# FileName="Connection_php_mysql.htm"
# Type="MYSQL"
# HTTP="true"
$hostname_RegisterUser = "144.13.22.61:3306";
$database_RegisterUser = "LS";
$username_RegisterUser = "sericatik4839";
$password_RegisterUser = "1t5n8o3f9E=]";
$RegisterUser = mysql_pconnect($hostname_RegisterUser, $username_RegisterUser, $password_RegisterUser) or trigger_error(mysql_error(),E_USER_ERROR); 
?>