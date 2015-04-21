<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Little Sprouts Signup</title>
<link href="assets/css/littleSproutsStyle.css" rel="stylesheet"
	type="text/css" />
<style>
.error {
	color: #ff0000;
	font-size: 0.9em;
	font-weight: bold;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>

<body>
	<img id="MainLogo" src="assets/img/littlespouts_logo.jpg" alt="Bucket" />

	<table id="Login_Border" border="1" align="center" cellpadding="3"
		cellspacing="3" style="width: 20%">
		<div align="center">
			<form:form id="myForm" method="post"
				 modelAttribute="user">
				<fieldset>
					<legend>User Signup Form</legend>
	
					<div>
						<label for="userNameInput">User Name</label>
						<div>
							<form:input type="text" path="username"  placeholder="User Name" /> <br />
							<form:errors path="username" cssClass="error"/>
						</div>
					</div>
					<br />
					<div>
						<label for="passwordInput">Password</label>
						<div>
							<form:input type="password" path="password"
								id="passwordInput" placeholder="Password" /> <br />
							<form:errors path="password" cssClass="error"/>
						</div>
					</div>
					<br />
					<div>
						<label for="firstNameInput">First Name</label>
						<div>
							<form:input type="text" path="name_first"
								id="firstNameInput" placeholder="First Name" /><br />
							<form:errors path="name_first" cssClass="error" />
						</div>
					</div>
					<br />
					<div>
						<label for="lastNameInput">Last Name</label>
						<div>
							<form:input type="text" path="name_last"
								id="lastNameInput" placeholder="Last Name" /><br />
							<form:errors path="name_last" cssClass="error"/>
						</div>
					</div>
					<br />	
					<div>
						<label for="typeInput">Type</label>
						<div>
							<form:input type="type" path="type" placeholder="Type" /><br />
							<form:errors path="type" cssClass="error"/>
						</div>
					</div>
					<br />
					<div>
						<label for="emailAddressInput">Email Address</label>
						<div>
							<form:input type="text" path="email" placeholder="Email Address" /><br />
							<form:errors path="email" cssClass="error"/>
						</div>
					</div>
					<br />
					<div>
						<label for="phoneInput">Phone</label>
						<div>
							<form:input type="text" path="phone" placeholder="Phone (#########)" /><br />
							<form:errors path="phone" cssClass="error"/>
						</div>
					</div>
	
					<div>
						<br />
						<button>Submit</button>
						<button>Cancel</button>					
					</div>
	
				</fieldset>
			</form:form>
		</div>
	</table>


</body>
</html>
