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
		<link rel="stylesheet"
			href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

			<!-- jQuery library -->
			<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
			<script type="text/javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
				
			<!-- Latest compiled JavaScript -->
			<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>


			<script type="text/javascript">
				//$.fn.dataTable.ext.errMode = 'throw';
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

					$("#example").dataTable({
						"bProcessing" : true,
						"bServerSide" : true,
						"sort" : "position",
						//bStateSave variable you can use to save state on client cookies: set value "true" 
						"bStateSave" : false,
						//Default: Page display length
						"iDisplayLength" : 10,
						"iDisplayStart" : 0,
						"fnDrawCallback" : function() {
							//Get page numer on client. Please note: number start from 0 So
							//for the first page you will see 0 second page 1 third page 2...
							//Un-comment below alert to see page number
							//alert("Current page number: "+this.fnPagingInfo().iPage);    
						},
						"sAjaxSource" : "maccounts.html",
						"aoColumns" : [ {
							"mData" : "username"
						}, {
							"mData" : "email"
						}, {
							"mData" : "name_first"
						}, {
							"mData" : "name_last"
						},{
							"mData" : "type"
						},{
							"mData" : "phone"},
						]
					  } );
					 
				} );
			</script>
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

		<form:form action="" method="GET">
		<h2 >User Accounts in System<br><br></h2>
		<table width="70%" style="border: 3px;background: rgb(243, 244, 248);"><tr><td>
			<table id="example" class="display" cellspacing="0" width="100%">
		        <thead>
		            <tr>
		                <th>User Name</th>
		     			<th>Email</th>
		     			<th>First Name</th>
		     			<th>Last Name</th>
		     			<th>Type</th>
		     			<th>Phone</th>
		            </tr>
		        </thead>       
		    </table>
		    </td></tr></table>
		</form:form>
	</div>

</body>
</html>
