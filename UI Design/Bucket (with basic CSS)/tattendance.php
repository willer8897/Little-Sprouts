<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Little Sprouts Attendance Tracker</title>
<link href="littleSproutsStyle.css" rel="stylesheet" type="text/css" />
<link href="tables.css" rel="stylesheet" type="text/css" />
</head>

<body>
<?php
//$monday = strtotime('last monday', strtotime('tomorrow'));
//echo 'monday = ',$monday;
//mysql_connect('144.13.22.61:3306','sericatik4839','1t5n8o3f9E=]');
//mysql_select_db('LS') or die( "Unable to select database");
//
//$query = "SELECT password,type FROM Availability WHERE account_type = 'C' AND week_start = '$monday'";
////echo 'Query: ',$query;
//$result = mysql_query($query) or die("Username does not exist" . mysql_error());
//$row = mysql_fetch_row($result);
//$storedpass = $row[0];
//$acctype = $row[1];
////echo 'Result: ',$storedpass; 
////echo ' Acctype: ',$acctype; 
//mysql_close();
//
?>

<table class="frr-100"> 

</table>

<table height="64" align="left" style="width:30%">
	<tr>
		<ul>
			<li><a href="tdashboard.html">Dashboard</a></li>
		 	<li><a href="tnews.html">News</a></li>
            <li><a href="tstatus.html">Attendance</a></li>
		 	<li><a href="tavailability.html">Availability</a></li>
		  	<li><a href="trequests.html">Requests</a></li>
		</ul>
   	</tr>
</table>
<form action="login.html">
<input type="image" style="float:right" src="images/logout.jpg" width="50" height="50" alt="logout" />
</form>
<br />
<br />
<br />
<br />
<br />
<br />
<?php
declare @body varchar(max)

 

//Create the body

set @body = cast( (

select td = dbtable + '</td><td>' + cast( entities as varchar(30) ) + '</td><td>' + cast( rows as varchar(30) )

from (

      select dbtable  = object_name( object_id ),

               entities = count( distinct name ),

               rows           = count( * )

      from sys.columns

      group by object_name( object_id )

      ) as d

for xml path( 'tr' ), type ) as varchar(max) )

 

set @body = '<table cellpadding="2" cellspacing="2" border="1">'

              + '<tr><th>Database Table</th><th>Entity Count</th><th>Total Rows</th></tr>'

              + replace( replace( @body, '&lt;', '<' ), '&gt;', '>' )

              + '<table>'

 

 

print @body
?>

<!-- Useless script for now 
<script>
	var cellid = new Array();
	cellid[0] = 'c00w';
	cellid[1] = 'c01w';
	cellid[2] = 'c02w';
	cellid[3] = 'c03w';
	cellid[4] = 'c04w';
	cellid[5] = 'c05w';
	
	var waiting = '#f1f1f1';
	var missing = '#cc0000';
	var arrived = '#33ff00';
	
	function setCellClr(cellid) {
		for(var i = 0; i < cellid.length; i++) {
			var cell = document.getElementById(cellid[i]);
			if(cellid = cell.id) { 
				if(cellid.charAt(3) = 'w') {
					cell.style.backgroundColor = arrived;
					cellid = cellid.replace("w","a");
				}
				else if(cellid.charAt(3) = 'a') {
					cell.style.backgroundColor = missing;
					cellid = cellid.replace("a","m");
				}
				else if(cellid.charAt(3) = 'm') {
					cell.style.backgroundColor = waiting;
					cellid = cellid.replace("m","w");
				}
			}
			
		}
	}

</script>
-->

<script>
function status() {
	// get our table's td elements
	var table = document.getElementById("statustable");
	var tds   = table.getElementsByTagName("td");
	
	var cell = document.getSelection();
	
	if(document.cell.backgroundColor == "lightgreen") {
    	document.cell.backgroundColor = "red";
	}
	else if(document.cell.backgroundColor == "red") {
    	document.cell.backgroundColor = "#f5f5f5";
	}
	else {
		document.cell.backgroundColor = "lightgreen";
	}
}
</script>



<!--
<script>
function status() {
	if(document.getElementById("status").style.backgroundColor == "lightgreen") {
    	document.getElementById("status").style.backgroundColor = "red";
	}
	else if(document.getElementById("status").style.backgroundColor == "red") {
    	document.getElementById("status").style.backgroundColor = "#f5f5f5";
	}
	else {
		document.getElementById("status").style.backgroundColor = "lightgreen";
	}
}
</script>


<script>
function move(elem) {
  var cell = console.log(elem.getAttribute("data-id"));
  if(cell.backgroundColor == "lightgreen") {
    	cell.backgroundColor = "red";
	}
	else if(cell.backgroundColor == "red") {
    	cell.backgroundColor = "#f5f5f5";
	}
	else {
		cell.backgroundColor = "lightgreen";
	}
}

// our click listener
function onClick(event) {
  move(this);
  event.preventDefault();
}

// get our table's td elements
var table = document.getElementById("statustable");
var tds   = table.getElementsByTagName("td");

// loop through each td and add a click listener
for (var i=0, len=tds.length; i<len; i++) {
  tds[i].addEventListener("click", onClick);
}
</script>

-->


<table>
  <thead>
    <tr>
      <th>Name</th>
      <th>Schedule</th>
      <th>Notes</th>
    </tr>
  </thead>
  <tbody id="statustable">
    <tr>
      <td data-id="td0" onclick="status()"><strong>Marie</strong></td>
      <td>09:00 - 12:00</td>
    </tr>
    <tr>
      <td data-id="td1" onclick="status()"><strong>Jake</strong></td>
      <td>09:00 - 13:30</td>
    </tr>
    <tr>
      <td data-id="td2" onclick="status()"><strong>Emma</strong></td>
      <td>09:30 - 14:00</td>
    </tr>
    <tr>
      <td data-id="td3" onclick="status()"><strong>Ben</strong></td>
      <td>10:00 - 13:00</td>
    </tr>					
    <tr>
      <td data-id="td4" onclick="status()"><strong>Franny</strong></td>
      <td>13:00 - 16:00</td>
    </tr>
    <tr>
      <td data-id="td5" onclick="status()"><strong>Kory</strong></td>
      <td>13:00 - 16:00</td>
    </tr>
  </tbody>
</table>

</body>
</html>
