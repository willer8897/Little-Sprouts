<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%><

!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Little Sprouts Parent Portal</title>
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


	<nav class="navbar navbar-inverse">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="#">Little Sprouts</a>
	    </div>
	    <div>
	      <ul class="nav navbar-nav">
	        <li><a href="pdashboard.html">Dashboard</a></li>
	        <li class="active"><a href="prequests.html">Requests</a></li>
	      </ul>
	    </div>
	  </div>
	</nav>

	<div class="container-fluid" align="center">

		<form action="login.html">
		<input type="image" style="float:right" src="images/logout.jpg" width="50" height="50" alt="logout" />
		</form>
		
		<table border="1" align="right" style="width:25%">
			<tr>
		    	<td>**/** - **/**</td>
		        <td><input type="image" src="assets/img/leftarrow.jpg" name="leftarrow" width="20px" height="20px"  /></td>
		        <td><input type="image" src="assets/img/rightarrow.png" width="20" height="20" alt="rightarrow" /></td>
		    </tr>
		</table>
		<br />
		<table border="1" align="center" style="width:100%">
			<tr>
		        <td height="500" align="center" valign="middle" style="wifth:75%">Requests</td>
		  </tr>
		</table>
	</div>

</body>
</html>
