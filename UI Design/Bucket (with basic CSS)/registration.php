//<?php require_once('Connections/User_Information.php'); 
//error_reporting(-1);
//ini_set('display_errors', 'On');?>
//
//
//
//<?php
//class Bcrypt {
//  private $rounds;
//  public function __construct($rounds = 10) {
//    if(CRYPT_BLOWFISH != 1) {
//      throw new Exception("bcrypt not supported in this installation. See http://php.net/crypt");
//    }
//
//    $this->rounds = $rounds;
//  }
//
//  public function hash($input) {
//    $hash = crypt($input, $this->getSalt());
//
//    if(strlen($hash) > 13)
//      return $hash;
//
//    return false;
//  }
//
//  public function verify($input, $existingHash) {
//    $hash = crypt($input, $existingHash);
//
//    return $hash === $existingHash;
//  }
//
//  private function getSalt() {
//    $salt = sprintf('$2y$%02d$', $this->rounds);
//
//    $bytes = $this->getRandomBytes(16);
//
//    $salt .= $this->encodeBytes($bytes);
//
//    return $salt;
//  }
//
//  private $randomState;
//  private function getRandomBytes($count) {
//    $bytes = '';
//
//    if(function_exists('openssl_random_pseudo_bytes') &&
//        (strtoupper(substr(PHP_OS, 0, 3)) !== 'WIN')) { // OpenSSL is slow on Windows
//      $bytes = openssl_random_pseudo_bytes($count);
//    }
//
//    if($bytes === '' && is_readable('/dev/urandom') &&
//       ($hRand = @fopen('/dev/urandom', 'rb')) !== FALSE) {
//      $bytes = fread($hRand, $count);
//      fclose($hRand);
//    }
//
//    if(strlen($bytes) < $count) {
//      $bytes = '';
//
//      if($this->randomState === null) {
//        $this->randomState = microtime();
//        if(function_exists('getmypid')) {
//          $this->randomState .= getmypid();
//        }
//      }
//
//      for($i = 0; $i < $count; $i += 16) {
//        $this->randomState = md5(microtime() . $this->randomState);
//
//        if (PHP_VERSION >= '5') {
//          $bytes .= md5($this->randomState, true);
//        } else {
//          $bytes .= pack('H*', md5($this->randomState));
//        }
//      }
//
//      $bytes = substr($bytes, 0, $count);
//    }
//
//    return $bytes;
//  }
//
//  private function encodeBytes($input) {
//    // The following is code from the PHP Password Hashing Framework
//    $itoa64 = './ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
//
//    $output = '';
//    $i = 0;
//    do {
//      $c1 = ord($input[$i++]);
//      $output .= $itoa64[$c1 >> 2];
//      $c1 = ($c1 & 0x03) << 4;
//      if ($i >= 16) {
//        $output .= $itoa64[$c1];
//        break;
//      }
//
//      $c2 = ord($input[$i++]);
//      $c1 |= $c2 >> 4;
//      $output .= $itoa64[$c1];
//      $c1 = ($c2 & 0x0f) << 2;
//
//      $c2 = ord($input[$i++]);
//      $c1 |= $c2 >> 6;
//      $output .= $itoa64[$c1];
//      $output .= $itoa64[$c2 & 0x3f];
//    } while (1);
//
//    return $output;
//  }
//}
//
//$bcrypt = new Bcrypt(10);
////$hash = $bcrypt->hash('parent1');
////echo $hash;
//
////echo password_hash('admin',PASSWORD_BCRYPT, array('cost' => 10));
//?>
//
//
//
//<?php
//mysql_connect('144.13.22.61:3306','sericatik4839','1t5n8o3f9E=]');
//mysql_select_db('LS') or die( "Unable to select database");
//
//$query = "SELECT MAX(id) FROM User"; 
//	 
//$result = mysql_query($query) or die(mysql_error());
//
//$row = mysql_fetch_row($result);
//$register_id = ($row[0])+1;
//mysql_close();
//?>
//
//<?php
//if (!function_exists("GetSQLValueString")) {
//function GetSQLValueString($theValue, $theType, $theDefinedValue = "", $theNotDefinedValue = "") 
//{
//  if (PHP_VERSION < 6) {
//    $theValue = get_magic_quotes_gpc() ? stripslashes($theValue) : $theValue;
//  }
//
//  $theValue = function_exists("mysql_real_escape_string") ? mysql_real_escape_string($theValue) : mysql_escape_string($theValue);
//
//  switch ($theType) {
//    case "text":
//      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
//      break;    
//    case "long":
//    case "int":
//      $theValue = ($theValue != "") ? intval($theValue) : "NULL";
//      break;
//    case "double":
//      $theValue = ($theValue != "") ? doubleval($theValue) : "NULL";
//      break;
//    case "date":
//      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
//      break;
//    case "defined":
//      $theValue = ($theValue != "") ? $theDefinedValue : $theNotDefinedValue;
//      break;
//  }
//  return $theValue;
//}
//}
//
//$editFormAction = $_SERVER['PHP_SELF'];
//if (isset($_SERVER['QUERY_STRING'])) {
//  $editFormAction .= "?" . htmlentities($_SERVER['QUERY_STRING']);
//}
//
//if ((isset($_POST["MM_insert"])) && ($_POST["MM_insert"] == "registration")) {
//  $insertSQL = sprintf("INSERT INTO ``User`` (id,type, username, password, email, name_first, name_last, phone) VALUES (0, %s, %s, %s, %s, %s, %s, %s)",
//                       GetSQLValueString($_POST['account_type'], "text"),
//                       GetSQLValueString($_POST['username'], "text"),
//                       $bcrypt->hash(GetSQLValueString($_POST['password'], "text")),
//                       GetSQLValueString($_POST['email'], "text"),
//                       GetSQLValueString($_POST['name_first'], "text"),
//                       GetSQLValueString($_POST['name_last'], "text"),
//                       GetSQLValueString($_POST['phone'], "text"));
//
//  mysql_select_db($database_User_Information, $User_Information);
//  $Result1 = mysql_query($insertSQL, $User_Information) or die(mysql_error());
//
//  $insertGoTo = "login.php";
//  if (isset($_SERVER['QUERY_STRING'])) {
//    $insertGoTo .= (strpos($insertGoTo, '?')) ? "&" : "?";
//    $insertGoTo .= $_SERVER['QUERY_STRING'];
//  }
//  header(sprintf("Location: %s", $insertGoTo));
//}
//
//mysql_select_db($database_User_Information, $User_Information);
//$query_Recordset1 = "SELECT * FROM `User`";
//$Recordset1 = mysql_query($query_Recordset1, $User_Information) or die(mysql_error());
//$row_Recordset1 = mysql_fetch_assoc($Recordset1);
//$totalRows_Recordset1 = mysql_num_rows($Recordset1);
//?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Registration</title>
</head>

<body>
<h1>Register</h1>
<form method="POST" action="<?php echo $editFormAction; ?>" name="registration">
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
</body>
</html>
<?php
mysql_free_result($Recordset1);
?>

