<?php
$email = $_POST['email'];
$pass = $_POST['password'];

echo $email, ' ', $pass;
?>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PHP Submission Test</title>
</head>

<body>
<h1>Login Test:</h1>
<p>Login attempt with email: <?= $email ?> and password: <?= $pass ?>.</p>
</body>
</html>