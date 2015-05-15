<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="dto.ComputerDTO, java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<spring:message code="date_error" var="date" />
	<spring:message code="computer_name" var="cname" />

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="add_computer" />
					</h1>
					<sf:form action="addcomputer" method="POST"
						modelAttribute="computerDTO">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message
										code="computer_name" /></label>
								<sf:input class="form-control" path="name" placeholder="${cname}" />
								<sf:errors cssStyle="color: #ff0000" path="name" />
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message
										code="introduced_date" /></label>
								<sf:input class="form-control" path="introduced" placeholder="${date}" />
								<sf:errors cssStyle="color: #ff0000" path="introduced" />
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message
										code="discontinued_date" /></label>
								<sf:input class="form-control" path="discontinued" placeholder="${date}" />
								<sf:errors cssStyle="color: #ff0000" path="discontinued" />
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="company" /></label>
								<select name="companyId" class="form-control" id="companyId">
									<option value="0">-</option>
									<c:forEach var="c" items="${companies}">
										<option value="${c.id}">${c.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="add" />"
								class="btn btn-primary">
							<spring:message code="or" />
							<a href="dashboard" class="btn btn-default"><spring:message
									code="cancel" /></a>
						</div>
					</sf:form>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="js/validateAddForm.js"></script>
</body>
</html>