<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Little Sprouts Login Error</title>
<link href="assets/css/littleSproutsStyle.css" rel="stylesheet"
	type="text/css" />
</head>

<body>
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<img id="MainLogo" src="assets/img/littlespouts_logo.jpg" alt="Bucket" />

	<table id="Login_Border" border="1" align="center" cellpadding="3"
		cellspacing="3" style="width: 20%">
		<div align="center">
			<div>
				<h3>User Login failure</h3>
			</div>
			<div>
				<div>
					<strong>Error logging in!</strong> Try logging in again. <br /> <br />
					<a href="<spring:url value="login.html"/>">Try again?</a>
				</div>
			</div>
		</div>		
	</table>


</body>
</html>
