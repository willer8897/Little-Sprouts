<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Little Sprouts Signup</title>
	<link href="assets/css/littleSproutsStyle.css" rel="stylesheet" type="text/css" />
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
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	
	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	
	<!-- Latest compiled JavaScript -->
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>

<body>
	<div div class="container-fluid" align="center">
	<img id="MainLogo" src="assets/img/littlespouts_logo.jpg" alt="Bucket" />
		<div align="center" style="width: 20%">
			<form:form id="myForm" method="post"
				 modelAttribute="user">
				<fieldset>
					<legend>User Signup Form</legend>
	
					<div align="left">
						<label for="userNameInput">User Name</label>
						<div>
							<form:input type="text" path="username"  placeholder="User Name" class="form-control" />
							<form:errors path="username" cssClass="error"/>
						</div>
					</div>
					<br />
					<div align="left">
						<label for="passwordInput">Password</label>
						<div>
							<form:input type="password" path="password"
								id="passwordInput" placeholder="Password" class="form-control"/>
							<form:errors path="password" cssClass="error"/>
						</div>
					</div>
					<br />
					<div align="left">
						<label for="firstNameInput">First Name</label>
						<div>
							<form:input type="text" path="name_first"
								id="firstNameInput" placeholder="First Name" class="form-control"/>
							<form:errors path="name_first" cssClass="error" />
						</div>
					</div>
					<br />
					<div align="left">
						<label for="lastNameInput">Last Name</label>
						<div>
							<form:input type="text" path="name_last"
								id="lastNameInput" placeholder="Last Name" class="form-control"/>
							<form:errors path="name_last" cssClass="error"/>
						</div>
					</div>
					<br />	
					<div align="left">
						<label for="typeInput">Type</label>
						<div class="radio">
							<label><form:radiobutton value="M" path="type" checked="checked" />Administration</label><br />
							<label><form:radiobutton value="T" path="type" />Teacher</label><br />
							<label><form:radiobutton value="P" path="type" />Parent</label><br />
							<form:errors path="type" cssClass="error"/>
						</div>
					</div>
					<br />
					<div align="left">
						<label for="emailAddressInput">Email Address</label>
						<div>
							<form:input type="text" path="email" placeholder="Email Address" class="form-control"/>
							<form:errors path="email" cssClass="error"/>
						</div>
					</div>
					<br />
					<div align="left">
						<label for="phoneInput">Phone</label>
						<div>
							<form:input type="text" path="phone" placeholder="Phone (#########)" class="form-control"/>
							<form:errors path="phone" cssClass="error"/>
						</div>
					</div>
	
					<div>
						<br />
						<button class="btn btn-primary" name="signup" value="register">Register</button>
						<button class="btn btn-primary" name="signup" value="cancel">Cancel</button>
					</div>
					<br /><br /><br />
				</fieldset>
			</form:form>
		</div>
	</div>


</body>
</html>
