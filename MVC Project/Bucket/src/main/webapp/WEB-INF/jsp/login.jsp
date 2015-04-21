<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Little Sprouts Login</title>
<link href="assets/css/littleSproutsStyle.css" rel="stylesheet" type="text/css" />
</head>

<body>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<img id="MainLogo" src="assets/img/littlespouts_logo.jpg" alt="Bucket" />

<table id="Login_Border" border="1" align="center" cellpadding="3" cellspacing="3" style="width:20%">
		<form:form id="myForm" method="post" commandName="userLogin">
			<fieldset>
				<legend>Login Form</legend>
				<div>
					<label for="userNameInput">User Name</label>
					<div>
						<form:input type="text" path="userName" id="userNameInput" placeholder="User Name" />
						<form:errors path="userName" />
					</div>
				</div>

				<div>
					<label for="passwordInput">Password</label>
					<div>
						<form:input type="password" path="password" id="passwordInput" placeholder="Password" />
						<form:errors path="password"/>
					</div>
				</div>

				<div>
					<a href="<spring:url value="login.html"/>">Cancel</a>

					<button>Login</button>
				</div>

			</fieldset>
		</form:form>
	</table>


</body>
</html>
