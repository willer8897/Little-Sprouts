<?php require_once('Connections/RegisterUser.php'); ?>
<?php
if (!function_exists("GetSQLValueString")) {
function GetSQLValueString($theValue, $theType, $theDefinedValue = "", $theNotDefinedValue = "") 
{
  if (PHP_VERSION < 6) {
    $theValue = get_magic_quotes_gpc() ? stripslashes($theValue) : $theValue;
  }

  $theValue = function_exists("mysql_real_escape_string") ? mysql_real_escape_string($theValue) : mysql_escape_string($theValue);

  switch ($theType) {
    case "text":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;    
    case "long":
    case "int":
      $theValue = ($theValue != "") ? intval($theValue) : "NULL";
      break;
    case "double":
      $theValue = ($theValue != "") ? doubleval($theValue) : "NULL";
      break;
    case "date":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;
    case "defined":
      $theValue = ($theValue != "") ? $theDefinedValue : $theNotDefinedValue;
      break;
  }
  return $theValue;
}
}

$editFormAction = $_SERVER['PHP_SELF'];
if (isset($_SERVER['QUERY_STRING'])) {
  $editFormAction .= "?" . htmlentities($_SERVER['QUERY_STRING']);
}

if ((isset($_POST["MM_insert"])) && ($_POST["MM_insert"] == "registration")) {
  $insertSQL = sprintf("INSERT INTO ``User`` (id, type, username, password, email, name_first, name_last, phone) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)",
                       GetSQLValueString($_POST['id'], "int"),
                       GetSQLValueString($_POST['account_type'], "text"),
                       GetSQLValueString($_POST['username'], "text"),
                       GetSQLValueString($_POST['password'], "text"),
                       GetSQLValueString($_POST['email'], "text"),
                       GetSQLValueString($_POST['name_first'], "text"),
                       GetSQLValueString($_POST['name_last'], "text"),
                       GetSQLValueString($_POST['phone'], "text"));

  mysql_select_db($database_RegisterUser, $RegisterUser);
  $Result1 = mysql_query($insertSQL, $RegisterUser) or die(mysql_error());

  $insertGoTo = "login.html";
  if (isset($_SERVER['QUERY_STRING'])) {
    $insertGoTo .= (strpos($insertGoTo, '?')) ? "&" : "?";
    $insertGoTo .= $_SERVER['QUERY_STRING'];
  }
  header(sprintf("Location: %s", $insertGoTo));
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Registration</title>
</head>

<body>
<h1>Register</h1>
<form method="POST" action="<?php echo $editFormAction; ?>" name="registration">
<input type="hidden" name="id" value=0 />
<label>Username:</label><br/>
<input type="text" name="username" required="required"/><br/>
<label>Password:</label><br/>
<input type="password" name="password" required="required"/><br/>
<label>First:</label>
<input type="text" name="name_first" required="required"/><br/>
<label>Last:</label>
<input type="text" name="name_last" required="required"/><br/>
<label>Email:</label><br/>
<input type="text" name="email" required="required"/><br/>
<label>Phone:</label><br/>
<input type="tel" size=10 required="required" name="phone"><br/>
<input type="radio" name="account_type" value="P"> Parent<br>
<input type="radio" name="account_type" value="T" checked> Teacher<br>
<input type="submit" value="Register" />
<input type="hidden" name="MM_insert" value="registration" />
</form>
Already have an account? <a href="login.html">Login</a>