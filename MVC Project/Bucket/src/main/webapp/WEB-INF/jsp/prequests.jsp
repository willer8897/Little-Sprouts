<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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

<script>
function toStraaang()
{	
	var monStart1 = 0;
	var monEnd1 = 0;
	var monStart2 = 0;
	var monEnd2 = 0;
	var temp = 0;
	var emon = false;
	
	// Process Monday's second shift if it's set
	var mon1 = document.getElementById('monCheck1');
	if (mon1.checked) {
		// Find the array position of the beginning of the first shift
		temp = document.getElementById('monhr1');
		monStart1 = parseInt(temp.options[temp.selectedIndex].value);
		monStart1 = monStart1*4;
		temp =  document.getElementById('monmin1');
		monStart1 += parseInt(temp.options[temp.selectedIndex].value);
		temp = document.getElementById('monpm1');
		monStart1 += parseInt(temp.options[temp.selectedIndex].value);
		monStart1 -= 24;
		
		// Find the array position of the ending of the first shift
		temp = document.getElementById('monhr2');
		monEnd1 = parseInt(temp.options[temp.selectedIndex].value);
		monEnd1 = monEnd1*4;
		temp =  document.getElementById('monmin2');
		monEnd1 += parseInt(temp.options[temp.selectedIndex].value);
		temp = document.getElementById('monpm2');
		monEnd1 += parseInt(temp.options[temp.selectedIndex].value);
		monEnd1 -= 24;
		
		// Store the error report objects and color
		var e1 = document.getElementById('monErr1');
		var e2 = document.getElementById('monErr2');
		var e3 = document.getElementById('monErr3');
		var e4 = document.getElementById('monErr4');
	    var errorHighlight = "#ff6666";
	    e1.style.color = errorHighlight;
	    e2.style.color = errorHighlight;
	    e3.style.color = errorHighlight;
	    e4.style.color = errorHighlight;
	    
	    e1.innerHTML = "";
	    e2.innerHTML = "";
	    e3.innerHTML = "";
	    e4.innerHTML = "";
	    
	    // Check Monday's first shift start and end for validity
	    if (monStart1 === monEnd1) {
	    	e1.innerHTML = "No span of time selected.";
	    	emon=true;
	    	return;
	    }
	    else if (monStart1 > monEnd1) {
			e1.innerHTML = "Start time is after End time.";
			emon=true;
			return;
	    }
		else if (monStart1 < 0 || monStart1 > 48) {
			e1.innerHTML = "Invalid Start time."
			emon=true;
		}
		else
			e1.innerHTML = ""
		if (monEnd1 < 0 || monEnd1 > 48){
			e2.innerHTML = "Invalid End time."
			emon=true;
		}
		else 
			e2.innerHTML = ""
	    
		// Process Monday's second shift if it's set
		var mon2 = document.getElementById('monCheck2');
		if (mon2.checked) {
			// Find the array position of the beginning of the second shift
			temp = document.getElementById('monhr3');
			monStart2 = parseInt(temp.options[temp.selectedIndex].value);
			monStart2 = monStart2*4;
			temp =  document.getElementById('monmin3');
			monStart2 += parseInt(temp.options[temp.selectedIndex].value);
			temp = document.getElementById('monpm3');
			monStart2 += parseInt(temp.options[temp.selectedIndex].value);
			monStart2 -= 24;
			
			// Find the array position of the ending of the second shift
			temp = document.getElementById('monhr4');
			monEnd2 = parseInt(temp.options[temp.selectedIndex].value);
			monEnd2 = monEnd2*4;
			temp =  document.getElementById('monmin4');
			monEnd2 += parseInt(temp.options[temp.selectedIndex].value);
			temp = document.getElementById('monpm4');
			monEnd2 += parseInt(temp.options[temp.selectedIndex].value);
			monEnd2 -= 24;
			
			if(monStart1 > monStart2 && monStart1 < monEnd2) {
				e2.innerHTML = "Overlapping times"
				emon=true;
			}
			if(monEnd1 > monStart2 && monEnd1 < monEnd2) {
				e2.innerHTML = "Overlapping times"
				emon=true;
			}
			if(monStart2 > monStart1 && monStart2 < monEnd1) {
				e2.innerHTML = "Overlapping times"
				emon=true;
			}
			if(monEnd2 > monStart1 && monEnd2 < monEnd1) {
				e2.innerHTML = "Overlapping times"
				emon=true;
			}
			
		    // Check Monday's second shift start and end for validity
			if (monStart2 === monEnd2) {
		    	e3.innerHTML = "No span of time selected.";
		    	emon=true;
		    	return;
		    }
		    else if (monStart2 > monEnd2) {
				e3.innerHTML = "Start time is after End time.";
				emon=true;
				return;
		    }
			else if (monStart2 < 0 || monStart2 > 48) {
				e3.innerHTML = "Invalid Start time."
				emon=true;
			}
			else
				e3.innerHTML = ""
			if (monEnd2 < 0 || monEnd2 > 48){
				e4.innerHTML = "Invalid End time."
				emon=true;
			}
			else 
				e4.innerHTML = ""
		}
	}
	
	// If there's no error in Monday hours
	if (emon === false) {
		// Process Monday hours to storage array
		var monFinal = [];
		// If there are two spans for Monday
		if (mon2.checked){
			// and span 2 starts before span 1
			if (monStart2 < monStart1){
				// Put them in chronological order
				temp = monStart1;
				monStart1=monStart2;
				monStart2=temp;
				temp = monEnd1;
				monEnd1=monEnd2;
				monEnd2=temp;
				monEnd1--;
				monEnd2--;
			}
		}
		
		window.alert(monStart1+"  "+monEnd1+"  "+monStart2+"  "+monEnd2);
		
		// Step through the array and set hours
		for (i=0;i<50;i++){
			if(i<monStart1)
				monFinal[i]=0;
			else if(i<monEnd1)
				monFinal[i]=1;
			else if(monStart2>0){
				if(i<monStart2)
					monFinal[i]=0;
				else if(i<monEnd2)
					monFinal[i]=1;
				else
					monFinal[i]=0;
			}
			else
				monFinal[i]=0;
		}
		
		var printout = "";
		for(i=0;i<50;i++){
			printout += monFinal[i];
		}
		window.alert(printout);
		
		
	}
	
	
	
}  	














</script>

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
		<input type="image" style="float:right" src="assets/img/logout.jpg" width="50" height="50" alt="logout" />
		</form>
		
		<div style="width: 20%">
				<form:form id="myForm" method="post" modelAttribute="request">
					<fieldset>
						<legend>Parent Request Form</legend>
						<div align="left">
							<input type="checkbox" id="monCheck1" checked="true" onclick="document.getElementById('mon1').style.visibility=this.checked ? 'visible' : 'hidden';document.getElementById('mon2').style.visibility=(this.checked && document.getElementById('monCheck2').checked) ? 'visible' : 'hidden'"></input>
							<label for="requestTimeStartInput">Monday Hours</label>
							<input type="checkbox" id="monCheck2" onclick="document.getElementById('mon2').style.visibility=(this.checked && document.getElementById('monCheck1').checked) ? 'visible' : 'hidden'">Additional</input>
							<div id='mon1'>
							Start:
								<select name="monhr1" id="monhr1">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="0">12</option>
								</select>
								:
								<select name="monmin1" id="monmin1">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="monpm1" id="monpm1">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="monErr1" class="errorMessage"></span>
								</br>
							End :
								<select name="monhr2" id="monhr2">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="0">12</option>
								</select>
								:
								<select name="monmin2" id="monmin2">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="monpm2" id="monpm2">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="monErr2" class="errorMessage"></span>
								<form:errors path="monhours" cssClass="error" />
							</div>
							</br>
							<div id='mon2' style="visibility:hidden">
							Start:
								<select name="monhr3" id="monhr3">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="0">12</option>
								</select>
								:
								<select name="monmin3" id="monmin3">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="monpm3" id="monpm3">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="monErr3" class="errorMessage"></span>
								</br>
							End :
								<select name="monhr4" id="monhr4">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
									<option value="11">11</option>
									<option value="0">12</option>
								</select>
								:
								<select name="monmin4" id="monmin4">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="monpm4" id="monpm4">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="monErr4" class="errorMessage"></span>
								<form:errors path="monhours" cssClass="error" />
							</div>
							<button onclick="toStraaang()" >TEST THAT SHIT</button>
						</div>
						<br />
						<div align="left">
							<label for="requestTimeStartInput">Tuesday Hours</label>
							<div>
								<form:input type="text" path="tuehours" id="dateInput"
									placeholder="Time" class="form-control" />
								<form:errors path="tuehours" cssClass="error" />
							</div>
						</div>
						<br />
						<div align="left">
							<label for="requestTimeStartInput">Wednesday Hours</label>
							<div>
								<form:input type="text" path="wedhours" id="dateInput"
									placeholder="Time" class="form-control" />
								<form:errors path="wedhours" cssClass="error" />
							</div>
						</div>
						<br />
						<div align="left">
							<label for="requestTimeStartInput">Thursday Hours</label>
							<div>
								<form:input type="text" path="thuhours" id="dateInput"
									placeholder="Time" class="form-control" />
								<form:errors path="thuhours" cssClass="error" />
							</div>
						</div>
						<br />
						<div align="left">
							<label for="requestTimeStartInput">Friday Hours</label>
							<div>
								<form:input type="text" path="frihours" id="dateInput"
									placeholder="Time" class="form-control" />
								<form:errors path="frihours" cssClass="error" />
							</div>
						</div>
						<br />
						<div align="left">
							<label for="requestTimeStartInput">Note</label>
							<div>
								<form:textarea
								 type="text" path="request_note" id="dateInput"
									placeholder="Note" class="form-control" />
								<form:errors path="request_note" cssClass="error" />
							</div>
						</div>
						<br />
						<div>
							<button class="btn btn-primary">Submit Request</button>
						</div>
	
					</fieldset>
				</form:form>
			</div>
	</div>

</body>
</html>
