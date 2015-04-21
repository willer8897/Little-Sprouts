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
</head>

<body>
	<img id="MainLogo" src="assets/img/littlespouts_logo.jpg" alt="Bucket" />

	<table id="Login_Border" border="1" align="center" cellpadding="3"
		cellspacing="3" style="width: 20%">
		
		<form:form id="myForm" method="post"
			 modelAttribute="user">
			<fieldset>
				<legend>User Signup Form</legend>

				<div>
					<label for="userNameInput">User Name</label>
					<div>
						<form:input type="text" path="username"  placeholder="User Name" />
						<form:errors path="username"/>
					</div>
				</div>

				<div>
					<label for="passwordInput">Password</label>
					<div>
						<form:input type="password" path="password"
							id="passwordInput" placeholder="Password" />
						<form:errors path="password"/>
					</div>
				</div>

				<div>
					<label for="firstNameInput">First Name</label>
					<div>
						<form:input type="text" path="name_first"
							id="firstNameInput" placeholder="First Name" />
						<form:errors path="name_first" />
					</div>
				</div>

				<div>
					<label for="lastNameInput">Last Name</label>
					<div>
						<form:input type="text" path="name_last"
							id="lastNameInput" placeholder="Last Name" />
						<form:errors path="name_last" />
					</div>
				</div>

				<div>
					<label for="typeInput">Type</label>
					<div>
						<form:input type="type" path="type" placeholder="Type" />
						<form:errors path="type"/>
					</div>
				</div>

				<div>
					<label for="emailAddressInput">Email Address</label>
					<div>
						<form:input type="text" path="email" placeholder="Email Address" />
						<form:errors path="email" />
					</div>
				</div>
				
				<div>
					<label for="phoneInput">Phone</label>
					<div>
						<form:input type="text" path="phone" placeholder="Phone (#########)" />
						<form:errors path="phone" />
					</div>
				</div>

				<div>
					<button>Cancel</button>

					<button data-toggle="modal"
						data-target="#themodal">Submit</button>
					<div id="themodal" data-backdrop="static">
						<div>
							<div>
								<div>
									<button type="button" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<h3>Signup Form Submission</h3>
								</div>
								<div>
									<p>Confirm?</p>
									<div>
										<div></div>
									</div>
								</div>
								<div class="modal-footer">
									<a href="#" data-dismiss="modal">Close</a>
									<input type="submit" value="Yes" data-loading-text="Saving.."
										data-complete-text="Submit Complete!">
								</div>
							</div>
						</div>
					</div>

				</div>

			</fieldset>
		</form:form>
	</table>


</body>
</html>
