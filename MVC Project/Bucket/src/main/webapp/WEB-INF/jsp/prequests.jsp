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
function processInput(){
	toStraaang(
			document.getElementById('monCheck1'),
			document.getElementById('monCheck2'),
			document.getElementById('monErr1'),
			document.getElementById('monErr2'),
			document.getElementById('monErr3'),
			document.getElementById('monErr4'),
			document.getElementById('monhr1'),
			document.getElementById('monhr2'),
			document.getElementById('monhr3'),
			document.getElementById('monhr4'),
			document.getElementById('monmin1'),
			document.getElementById('monmin2'),
			document.getElementById('monmin3'),
			document.getElementById('monmin4'),
			document.getElementById('monpm1'),
			document.getElementById('monpm2'),
			document.getElementById('monpm3'),
			document.getElementById('monpm4'));
	toStraaang(
			document.getElementById('tueCheck1'),
			document.getElementById('tueCheck2'),
			document.getElementById('tueErr1'),
			document.getElementById('tueErr2'),
			document.getElementById('tueErr3'),
			document.getElementById('tueErr4'),
			document.getElementById('tuehr1'),
			document.getElementById('tuehr2'),
			document.getElementById('tuehr3'),
			document.getElementById('tuehr4'),
			document.getElementById('tuemin1'),
			document.getElementById('tuemin2'),
			document.getElementById('tuemin3'),
			document.getElementById('tuemin4'),
			document.getElementById('tuepm1'),
			document.getElementById('tuepm2'),
			document.getElementById('tuepm3'),
			document.getElementById('tuepm4'));
	toStraaang(
			document.getElementById('wedCheck1'),
			document.getElementById('wedCheck2'),
			document.getElementById('wedErr1'),
			document.getElementById('wedErr2'),
			document.getElementById('wedErr3'),
			document.getElementById('wedErr4'),
			document.getElementById('wedhr1'),
			document.getElementById('wedhr2'),
			document.getElementById('wedhr3'),
			document.getElementById('wedhr4'),
			document.getElementById('wedmin1'),
			document.getElementById('wedmin2'),
			document.getElementById('wedmin3'),
			document.getElementById('wedmin4'),
			document.getElementById('wedpm1'),
			document.getElementById('wedpm2'),
			document.getElementById('wedpm3'),
			document.getElementById('wedpm4'));
	toStraaang(
			document.getElementById('thuCheck1'),
			document.getElementById('thuCheck2'),
			document.getElementById('thuErr1'),
			document.getElementById('thuErr2'),
			document.getElementById('thuErr3'),
			document.getElementById('thuErr4'),
			document.getElementById('thuhr1'),
			document.getElementById('thuhr2'),
			document.getElementById('thuhr3'),
			document.getElementById('thuhr4'),
			document.getElementById('thumin1'),
			document.getElementById('thumin2'),
			document.getElementById('thumin3'),
			document.getElementById('thumin4'),
			document.getElementById('thupm1'),
			document.getElementById('thupm2'),
			document.getElementById('thupm3'),
			document.getElementById('thupm4'));
	toStraaang(
			document.getElementById('friCheck1'),
			document.getElementById('friCheck2'),
			document.getElementById('friErr1'),
			document.getElementById('friErr2'),
			document.getElementById('friErr3'),
			document.getElementById('friErr4'),
			document.getElementById('frihr1'),
			document.getElementById('frihr2'),
			document.getElementById('frihr3'),
			document.getElementById('frihr4'),
			document.getElementById('frimin1'),
			document.getElementById('frimin2'),
			document.getElementById('frimin3'),
			document.getElementById('frimin4'),
			document.getElementById('fripm1'),
			document.getElementById('fripm2'),
			document.getElementById('fripm3'),
			document.getElementById('fripm4'));
}

function toStraaang(day1, day2, e1, e2, e3, e4, hr1, hr2, hr3, hr4, min1, min2, min3, min4, pm1, pm2, pm3, pm4)
{	
	var errorHighlight = "#ff6666";
	var temp = 0;
	var err = false;
	var start1 = 0;
	var end1 = 0;
	var start2 = 0;
	var end2 = 0;
	
	// Process day's second shift if it's set
	if (day1.checked) {
		// Find the array position of the beginning of the first shift
		temp = hr1;
		start1 = parseInt(temp.options[temp.selectedIndex].value);
		start1 = start1*4;
		temp =  min1;
		start1 += parseInt(temp.options[temp.selectedIndex].value);
		temp = pm1;
		start1 += parseInt(temp.options[temp.selectedIndex].value);
		start1 -= 24;
		
		// Find the array position of the ending of the first shift
		temp = hr2;
		end1 = parseInt(temp.options[temp.selectedIndex].value);
		end1 = end1*4;
		temp =  min2;
		end1 += parseInt(temp.options[temp.selectedIndex].value);
		temp = pm2;
		end1 += parseInt(temp.options[temp.selectedIndex].value);
		end1 -= 24;
		
		// Set the error report objects and color
	    e1.style.color = errorHighlight;
	    e2.style.color = errorHighlight;
	    e3.style.color = errorHighlight;
	    e4.style.color = errorHighlight;
	    
	    e1.innerHTML = "";
	    e2.innerHTML = "";
	    e3.innerHTML = "";
	    e4.innerHTML = "";
	    
	    // Check day's first shift start and end for validity
	    if (start1 === end1) {
	    	e1.innerHTML = "No span of time selected.";
	    	err=true;
	    	return;
	    }
	    else if (start1 > end1) {
			e1.innerHTML = "Start time is after End time.";
			err=true;
			return;
	    }
		else if (start1 < 0 || start1 > 48) {
			e1.innerHTML = "Invalid Start time."
			err=true;
		}
		else
			e1.innerHTML = ""
		if (end1 < 0 || end1 > 48){
			e2.innerHTML = "Invalid End time."
			err=true;
		}
		else 
			e2.innerHTML = ""
	    
		// Process day's second shift if it's set
		if (day2.checked) {
			// Find the array position of the beginning of the second shift
			temp = hr3;
			start2 = parseInt(temp.options[temp.selectedIndex].value);
			start2 = start2*4;
			temp =  min3;
			start2 += parseInt(temp.options[temp.selectedIndex].value);
			temp = pm3;
			start2 += parseInt(temp.options[temp.selectedIndex].value);
			start2 -= 24;
			
			// Find the array position of the ending of the second shift
			temp = hr4;
			end2 = parseInt(temp.options[temp.selectedIndex].value);
			end2 = end2*4;
			temp =  min4;
			end2 += parseInt(temp.options[temp.selectedIndex].value);
			temp = pm4;
			end2 += parseInt(temp.options[temp.selectedIndex].value);
			end2 -= 24;
			
			if(start1 > start2 && start1 < end2) {
				e2.innerHTML = "Overlapping times"
				err=true;
			}
			if(end1 > start2 && end1 < end2) {
				e2.innerHTML = "Overlapping times"
				err=true;
			}
			if(start2 > start1 && start2 < end1) {
				e2.innerHTML = "Overlapping times"
				err=true;
			}
			if(end2 > start1 && end2 < end1) {
				e2.innerHTML = "Overlapping times"
				err=true;
			}
			
		    // Check day's second shift start and end for validity
			if (start2 === end2) {
		    	e3.innerHTML = "No span of time selected.";
		    	err=true;
		    	return;
		    }
		    else if (start2 > end2) {
				e3.innerHTML = "Start time is after End time.";
				err=true;
				return;
		    }
			else if (start2 < 0 || start2 > 48) {
				e3.innerHTML = "Invalid Start time."
				err=true;
			}
			else
				e3.innerHTML = ""
			if (end2 < 0 || end2 > 48){
				e4.innerHTML = "Invalid End time."
				err=true;
			}
			else 
				e4.innerHTML = ""
		}
	}
	
	// If there's no error in Monday hours
	if (err === false) {
		// Process hours to storage array
		var finalHours = [];
		// If there are two spans for the day
		if (day2.checked){
			// and span 2 starts before span 1
			if (start2 < start1){
				// Put them in chronological order
				temp = start1;
				start1 = start2;
				start2 = temp;
				temp = end1;
				end1 = end2;
				end2 = temp;
			}
		}
		
		window.alert(start1+"  "+end1+"  "+start2+"  "+end2);
		
		// Step through the array and set hours
		for (i=0;i<50;i++){
			if(i<start1)
				finalHours[i]=0;
			else if(i<end1)
				finalHours[i]=1;
			else if(start2>0){
				if(i<start2)
					finalHours[i]=0;
				else if(i<end2)
					finalHours[i]=1;
				else
					finalHours[i]=0;
			}
			else
				finalHours[i]=0;
		}
		
		var printout = "";
		for(i=0;i<50;i++){
			printout += finalHours[i];
		}
		// Debug processed output\
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
<%-- 								<form:errors path="monhours" cssClass="error" /> --%>
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
<%-- 								<form:errors path="monhours" cssClass="error" /> --%>
							</div>
						</div>
						<br />
						<div align="left">
							<input type="checkbox" id="tueCheck1" checked="true" onclick="document.getElementById('tue1').style.visibility=this.checked ? 'visible' : 'hidden';document.getElementById('tue2').style.visibility=(this.checked && document.getElementById('tueCheck2').checked) ? 'visible' : 'hidden'"></input>
							<label for="requestTimeStartInput">Tuesday Hours</label>
							<input type="checkbox" id="tueCheck2" onclick="document.getElementById('tue2').style.visibility=(this.checked && document.getElementById('tueCheck1').checked) ? 'visible' : 'hidden'">Additional</input>
							<div id='tue1'>
							Start:
								<select name="tuehr1" id="tuehr1">
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
								<select name="tuemin1" id="tuemin1">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="tuepm1" id="tuepm1">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="tueErr1" class="errorMessage"></span>
								</br>
							End :
								<select name="tuehr2" id="tuehr2">
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
								<select name="tuemin2" id="tuemin2">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="tuepm2" id="tuepm2">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="tueErr2" class="errorMessage"></span>
<%-- 								<form:errors path="tuehours" cssClass="error" /> --%>
							</div>
							</br>
							<div id='tue2' style="visibility:hidden">
							Start:
								<select name="tuehr3" id="tuehr3">
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
								<select name="tuemin3" id="tuemin3">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="tuepm3" id="tuepm3">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="tueErr3" class="errorMessage"></span>
								</br>
							End :
								<select name="tuehr4" id="tuehr4">
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
								<select name="tuemin4" id="tuemin4">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="tuepm4" id="tuepm4">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="tueErr4" class="errorMessage"></span>
<%-- 								<form:errors path="tuehours" cssClass="error" /> --%>
							</div>
						</div>
						<br />
						<div align="left">
							<input type="checkbox" id="wedCheck1" checked="true" onclick="document.getElementById('wed1').style.visibility=this.checked ? 'visible' : 'hidden';document.getElementById('wed2').style.visibility=(this.checked && document.getElementById('wedCheck2').checked) ? 'visible' : 'hidden'"></input>
							<label for="requestTimeStartInput">Wednesday Hours</label>
							<input type="checkbox" id="wedCheck2" onclick="document.getElementById('wed2').style.visibility=(this.checked && document.getElementById('wedCheck1').checked) ? 'visible' : 'hidden'">Additional</input>
							<div id='wed1'>
							Start:
								<select name="wedhr1" id="wedhr1">
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
								<select name="wedmin1" id="wedmin1">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="wedpm1" id="wedpm1">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="wedErr1" class="errorMessage"></span>
								</br>
							End :
								<select name="wedhr2" id="wedhr2">
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
								<select name="wedmin2" id="wedmin2">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="wedpm2" id="wedpm2">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="wedErr2" class="errorMessage"></span>
<%-- 								<form:errors path="wedhours" cssClass="error" /> --%>
							</div>
							</br>
							<div id='wed2' style="visibility:hidden">
							Start:
								<select name="wedhr3" id="wedhr3">
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
								<select name="wedmin3" id="wedmin3">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="wedpm3" id="wedpm3">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="wedErr3" class="errorMessage"></span>
								</br>
							End :
								<select name="wedhr4" id="wedhr4">
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
								<select name="wedmin4" id="wedmin4">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="wedpm4" id="wedpm4">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="wedErr4" class="errorMessage"></span>
<%-- 								<form:errors path="wedhours" cssClass="error" /> --%>
							</div>
						</div>
						<br />
						<div align="left">
							<input type="checkbox" id="thuCheck1" checked="true" onclick="document.getElementById('thu1').style.visibility=this.checked ? 'visible' : 'hidden';document.getElementById('thu2').style.visibility=(this.checked && document.getElementById('thuCheck2').checked) ? 'visible' : 'hidden'"></input>
							<label for="requestTimeStartInput">Thursday Hours</label>
							<input type="checkbox" id="thuCheck2" onclick="document.getElementById('thu2').style.visibility=(this.checked && document.getElementById('thuCheck1').checked) ? 'visible' : 'hidden'">Additional</input>
							<div id='thu1'>
							Start:
								<select name="thuhr1" id="thuhr1">
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
								<select name="thumin1" id="thumin1">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="thupm1" id="thupm1">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="thuErr1" class="errorMessage"></span>
								</br>
							End :
								<select name="thuhr2" id="thuhr2">
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
								<select name="thumin2" id="thumin2">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="thupm2" id="thupm2">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="thuErr2" class="errorMessage"></span>
<%-- 								<form:errors path="thuhours" cssClass="error" /> --%>
							</div>
							</br>
							<div id='thu2' style="visibility:hidden">
							Start:
								<select name="thuhr3" id="thuhr3">
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
								<select name="thumin3" id="thumin3">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="thupm3" id="thupm3">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="thuErr3" class="errorMessage"></span>
								</br>
							End :
								<select name="thuhr4" id="thuhr4">
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
								<select name="thumin4" id="thumin4">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="thupm4" id="thupm4">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="thuErr4" class="errorMessage"></span>
<%-- 								<form:errors path="thuhours" cssClass="error" /> --%>
							</div>
						</div>
						<br />
						<div align="left">
							<input type="checkbox" id="friCheck1" checked="true" onclick="document.getElementById('fri1').style.visibility=this.checked ? 'visible' : 'hidden';document.getElementById('fri2').style.visibility=(this.checked && document.getElementById('friCheck2').checked) ? 'visible' : 'hidden'"></input>
							<label for="requestTimeStartInput">Friday Hours</label>
							<input type="checkbox" id="friCheck2" onclick="document.getElementById('fri2').style.visibility=(this.checked && document.getElementById('friCheck1').checked) ? 'visible' : 'hidden'">Additional</input>
							<div id='fri1'>
							Start:
								<select name="frihr1" id="frihr1">
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
								<select name="frimin1" id="frimin1">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="fripm1" id="fripm1">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="friErr1" class="errorMessage"></span>
								</br>
							End :
								<select name="frihr2" id="frihr2">
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
								<select name="frimin2" id="frimin2">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="fripm2" id="fripm2">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="friErr2" class="errorMessage"></span>
<%-- 								<form:errors path="frihours" cssClass="error" /> --%>
							</div>
							</br>
							<div id='fri2' style="visibility:hidden">
							Start:
								<select name="frihr3" id="frihr3">
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
								<select name="frimin3" id="frimin3">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="fripm3" id="fripm3">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="friErr3" class="errorMessage"></span>
								</br>
							End :
								<select name="frihr4" id="frihr4">
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
								<select name="frimin4" id="frimin4">
									<option value="0">00</option>
									<option value="1">15</option>
									<option value="2">30</option>
									<option value="3">45</option>
								</select>
								<select name="fripm4" id="fripm4">
									<option value="0">am</option>
									<option value="48">pm</option>
								</select><span id="friErr4" class="errorMessage"></span>
<%-- 								<form:errors path="frihours" cssClass="error" /> --%>
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
							<button type="button" class="btn btn-primary" onclick="processInput()">Submit Request</button>
						</div>
	
					</fieldset>
				</form:form>
			</div>
	</div>

</body>
</html>
