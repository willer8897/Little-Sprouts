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
		<title>Little Sprouts Management Portal</title>
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
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet"
			href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

			<!-- jQuery library -->
			<script
				src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

			<!-- Latest compiled JavaScript -->
			<script
				src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>

<body>


	<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="index.jsp">Little Sprouts</a>
		</div>
		<div>
			<ul class="nav navbar-nav">
				<li><a href="mdashboard.html">Dashboard</a></li>
				<li><a href="mrequests.html">Requests</a></li>
				<li class="active"><a href="maccounts.html">Accounts</a></li>
			</ul>
		</div>
	</div>
	</nav>

	<div class="container-fluid" align="center">
		<form action="login.html">
			<input type="image" style="float: right" src="assets/img/logout.jpg"
				width="50" height="50" alt="logout" />
		</form>

		<table border="1" align="center" style="width: 100%" class="table">
			<tr>
				<td><div style="width: min-width">
						<input type="text" />
					</div></td>
				<td><div style="width: min-width">
						<select>
							<option value="Name">Name</option>
							<option value="Phone">Phone Number</option>
							<option value="eMail Address">eMail Address</option>
						</select>
					</div></td>
				<td><div style="width: min-width">
						<button type="button">Search</button>
					</div></td>
			</tr>
			<tr>
				<table border="1" align="center" style="width: 100%" class="table">
					<tr>
						<td><h4>Name</h4></td>
						<td><h4>Account Type</h4></td>
						<td><h4>EMail Address</h4></td>
						<td><h4>Phone Number</h4></td>
						<td><h4>Added On</h4></td>
					</tr>
					<tr>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
					</tr>
					<tr>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
					</tr>
					<tr>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
					</tr>
					<tr>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
					</tr>
					<tr>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
						<td>Data</td>
					</tr>
				</table>
			</tr>
		</table>
	</div>

</body>
</html>
