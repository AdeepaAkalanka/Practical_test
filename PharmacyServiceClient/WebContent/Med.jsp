<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="model.Med"%>




<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Medicine Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.0.min.js"></script>
<script src="Components/meds.js"></script>
</head>
<body>
	<h1>Medicine Management</h1>



	<form id="formMed" name="formMed" method="post" action="Med.jsp">
		Medicine Name : <input id="med_name" name="med_name" type="text"
			class="form-control form-control-sm"> <br> Medicine Code : <input
			id="med_code" name="med_code" type="text"
			class="form-control form-control-sm"> <br> Price : <input
			id="med_price" name="med_price" type="text" class="form-control form-control-sm">
		<br> Description : <input id="med_description" name="med_description" type="text"
			class="form-control form-control-sm"> <br> <input
			id="btnSave" name="btnSave" type="button" value="Save"
			class="btn btn-primary"> <input type="hidden"
			id="hidMedIDSave" name="hidMedIDSave" value="">
	</form>

	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>
	<br>
	<div id="divMedsGrid">
		<%
			Med MedObj = new Med();
			out.print(MedObj.readMeds());
		%>
	</div>

</body>
</html>