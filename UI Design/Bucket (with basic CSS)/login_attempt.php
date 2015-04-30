<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body>

<?php
$db_user = 'sericatik4839';
$db_pass = '1t5n8o3f9E=]';

$username = $_POST['username'];
$password = $_POST['password'];

mysql_connect('144.13.22.61:3306',$db_user,$db_pass);
mysql_select_db('LS') or die( "Unable to select database");

$query = "SELECT password,type FROM User WHERE username = '$username'";
//echo 'Query: ',$query;
$result = mysql_query($query) or die("Malformed Login" . mysql_error());
$row = mysql_fetch_row($result);
$storedpass = $row[0];
$acctype = $row[1];
//echo 'Result: ',$storedpass; 
//echo ' Acctype: ',$acctype; 
mysql_close();

// Hash the submitted password and verify against the stored password. 
$bcrypt = new Bcrypt(10);
$hash = $bcrypt->hash($password);
$isGood = $bcrypt->verify($password, $storedpass);
// If verify checks out, look at the account type to redirect to appropriate dashboard. 
if($isGood) {
	if($acctype=='P') {
		//echo 'Login successful. Redirect to parent dashboard.';
		header('Location: http://144.13.22.61/LittleSprouts/pdashboard.html');
	}
	else if($acctype=='T') {
		//echo 'Login successful. Redirect to teacher dashboard.';
		header('Location: http://144.13.22.61/LittleSprouts/tdashboard.html');
	}
	else if($acctype=='M') {
		//echo 'Login successful. Redirect to management dashboard.';
		header('Location: http://144.13.22.61/LittleSprouts/mdashboard.html');
	}
}
else {
	//echo 'Login failed. Redirect to login';
	header('Location: http://144.13.22.61/LittleSprouts/login.html');	
}
//echo password_hash('admin',PASSWORD_BCRYPT, array('cost' => 10));
?>

<?php
class Bcrypt {
  private $rounds;
  public function __construct($rounds = 10) {
    if(CRYPT_BLOWFISH != 1) {
      throw new Exception("bcrypt not supported in this installation. See http://php.net/crypt");
    }
    $this->rounds = $rounds;
  }
  public function hash($input) {
    $hash = crypt($input, $this->getSalt());
    if(strlen($hash) > 13)
      return $hash;
    return false;
  }
  public function verify($input, $existingHash) {
    $hash = crypt($input, $existingHash);
    return $hash === $existingHash;
  }
  private function getSalt() {
    $salt = sprintf('$2y$%02d$', $this->rounds);
    $bytes = $this->getRandomBytes(16);
    $salt .= $this->encodeBytes($bytes);
    return $salt;
  }
  private $randomState;
  private function getRandomBytes($count) {
    $bytes = '';
    if(function_exists('openssl_random_pseudo_bytes') &&
        (strtoupper(substr(PHP_OS, 0, 3)) !== 'WIN')) { // OpenSSL is slow on Windows
      $bytes = openssl_random_pseudo_bytes($count);
    }
    if($bytes === '' && is_readable('/dev/urandom') &&
       ($hRand = @fopen('/dev/urandom', 'rb')) !== FALSE) {
      $bytes = fread($hRand, $count);
      fclose($hRand);
    }
    if(strlen($bytes) < $count) {
      $bytes = '';
      if($this->randomState === null) {
        $this->randomState = microtime();
        if(function_exists('getmypid')) {
          $this->randomState .= getmypid();
        }
      }
      for($i = 0; $i < $count; $i += 16) {
        $this->randomState = md5(microtime() . $this->randomState);
        if (PHP_VERSION >= '5') {
          $bytes .= md5($this->randomState, true);
        } else {
          $bytes .= pack('H*', md5($this->randomState));
        }
      }
      $bytes = substr($bytes, 0, $count);
    }
    return $bytes;
  }
  private function encodeBytes($input) {
    // Code from the PHP Password Hashing Framework
    $itoa64 = './ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    $output = '';
    $i = 0;
    do {
      $c1 = ord($input[$i++]);
      $output .= $itoa64[$c1 >> 2];
      $c1 = ($c1 & 0x03) << 4;
      if ($i >= 16) {
        $output .= $itoa64[$c1];
        break;
      }
      $c2 = ord($input[$i++]);
      $c1 |= $c2 >> 4;
      $output .= $itoa64[$c1];
      $c1 = ($c2 & 0x0f) << 2;
      $c2 = ord($input[$i++]);
      $c1 |= $c2 >> 6;
      $output .= $itoa64[$c1];
      $output .= $itoa64[$c2 & 0x3f];
    } while (1);
    return $output;
  }
}
?>

</body>
</html>