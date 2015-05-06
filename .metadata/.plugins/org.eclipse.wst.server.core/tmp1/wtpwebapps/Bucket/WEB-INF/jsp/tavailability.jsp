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
	<title>Little Sprouts Teacher Portal</title>
	<link href="assets/css/littleSproutsStyle.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css">
	
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
	<script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
	
	<!-- Latest compiled JavaScript -->
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
				jQuery.fn.dataTableExt.oApi.fnPagingInfo = function(oSettings) {
					return {
						"iStart" : oSettings._iDisplayStart,
						"iEnd" : oSettings.fnDisplayEnd(),
						"iLength" : oSettings._iDisplayLength,
						"iTotal" : oSettings.fnRecordsTotal(),
						"iFilteredTotal" : oSettings.fnRecordsDisplay(),
						"iPage" : oSettings._iDisplayLength === -1 ? 0 : Math
								.ceil(oSettings._iDisplayStart
										/ oSettings._iDisplayLength),
						"iTotalPages" : oSettings._iDisplayLength === -1 ? 0
								: Math.ceil(oSettings.fnRecordsDisplay()
										/ oSettings._iDisplayLength)
					};
				};

				$(document).ready(function() {

					$("#schedules").dataTable({
						"bProcessing" : true,
						"bServerSide" : true,
						"sort" : "position", 
						"bStateSave" : false,
						"iDisplayLength" : 10,
						"iDisplayStart" : 0,
						"sAjaxSource" : "tavailability.html",
						"aoColumns" : [ {
							"mData" : "account_id"
						}, {
							"mData" : "week_start"
						}, {
							"mData" : "monhours"
						}, {
							"mData" : "tuehours"
						},{
							"mData" : "wedhours"
						},{
							"mData" : "thuhours"
						},{
							"mData" : "frihours"
						},
						]
					  } );
				} );
			</script>
	
</head>

<body>

	<nav class="navbar navbar-inverse">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="#">Little Sprouts</a>
	    </div>
	    <div>
	      <ul class="nav navbar-nav">
	       <li><a href="tdashboard.html">Dashboard</a></li>
	        <li class="active"><a href="tavailability.html">Availability</a></li>
	        <li><a href="trequests.html">Requests</a></li>
	      </ul>
	    </div>
	  </div>
	</nav>

	<div class="container-fluid" align="center">
		<form action="login.html">
			<input type="image" style="float: right" src="assets/img/logout.jpg"
				width="50" height="50" alt="logout" />
		</form>

		<form:form action="" method="GET">
		<legend>Little Sprouts Availability<br><br></legend>
		<table width="70%" style="border: 3px;background: rgb(230, 244, 230);"><tr><td>
			<table id="schedules" class="display" cellspacing="0" width="100%">
		        <thead>
		            <tr>
		                <th>Account Id</th>
		     			<th>Week Start</th>
		     			<th>Monday Hours</th>
		     			<th>Tuesday Hours</th>
		     			<th>Wednesday Hours</th>
		     			<th>Thursday Hours</th>
		     			<th>Friday Hours</th>
		            </tr>
		        </thead>       
		    </table>
		</td></tr></table>
		</form:form>
	</div>

</body>
</html>
