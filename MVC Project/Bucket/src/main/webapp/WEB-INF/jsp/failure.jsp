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
	<title>Little Sprouts Login Error</title>
	<link href="assets/css/littleSproutsStyle.css" rel="stylesheet"
	type="text/css" />
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	
	<!-- Latest compiled JavaScript -->
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>

<body>
	<div class="container-fluid" align="center">
		<img id="MainLogo" src="assets/img/littlespouts_logo.jpg" alt="Bucket" />
		<div align="center">
			<div>
				<h3>User Login Failure</h3>
			</div>
			<div>
				<div class="alert alert-danger" style="width: 20%">
					<strong>Error logging in!</strong> Username or password incorrect. <br /> <br />
				</div>
				<a href="<spring:url value="login.html"/>" class="btn btn-primary" role="button">Try again</a>
			</div>
		</div>		
	</div>
</body>
</html>
