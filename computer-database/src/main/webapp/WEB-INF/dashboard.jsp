<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mlib" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${computersNumber}" />
				<spring:message code="computers_found" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm"
						action="dashboard?range=${page.range}&page=${page}" method="GET"
						class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control"
							placeholder="<spring:message code="search_name" />" /> <input
							type="submit" id="searchsubmit"
							value="<spring:message code="filter_name" />"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addcomputer"><spring:message
							code="add_computer" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
							code="delete" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="deleteComputer" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<!--  Tableau des computers -->
		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><mlib:link target="dashboard" range="${page.range}"
								page="${page.page}" orderBy="name" desc="${page.desc}"
								change="true" search="${page.search}">
								<spring:message code="computer_name" />
							</mlib:link></th>
						<th><mlib:link target="dashboard" range="${page.range}"
								page="${page.page}" orderBy="introduced" desc="${page.desc}"
								change="true" search="${page.search}">
								<spring:message code="introduced_date" />
							</mlib:link></th>
						<!-- Table header for Discontinued Date -->
						<th><mlib:link target="dashboard" range="${page.range}"
								page="${page.page}" orderBy="discontinued" desc="${page.desc}"
								change="true" search="${page.search}">
								<spring:message code="discontinued_date" />
							</mlib:link></th>
						<!-- Table header for Company -->
						<th><mlib:link target="dashboard" range="${page.range}"
								page="${page.page}" orderBy="company" desc="${page.desc}"
								change="true" search="${page.search}">
								<spring:message code="company" />
							</mlib:link></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computer" items="${computers}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="editComputer?id=${computer.id}" onclick="">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.companyName}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<!-- Choix de page -->
	<footer class="navbar-fixed-bottom">
		<div>
			<a
				href="dashboard?lang=fr&range=${page.range}&page=${page.page}&search=${page.search}&orderby=${page.orderBy}&desc=${page.desc}">
				<spring:message code="french" />
			</a> | <a
				href="dashboard?lang=en&range=${page.range}&page=${page.page}&search=${page.search}&orderby=${page.orderBy}&desc=${page.desc}">
				<spring:message code="english" />
			</a>
		</div>
		<div class="container text-center">
			<mlib:pagination page="${page.page}" currentRange="${page.range}"
				pageNumber="${pageNumber}" orderBy="${page.orderBy}"
				desc="${page.desc}" search="${page.search}" />

			<div class="btn-group btn-group-sm pull-right" role="group">
				<mlib:link range="10" page="1" search="${page.search}"
					target="dashboard" orderBy="${page.orderBy}" desc="${page.desc}">
					<button type='button' class='btn btn-default'>10</button>
				</mlib:link>
				<mlib:link range="50" page="1" search="${page.search}"
					target="dashboard" orderBy="${page.orderBy}" desc="${page.desc}">
					<button type='button' class='btn btn-default'>50</button>
				</mlib:link>
				<mlib:link range="100" page="1" search="${page.search}"
					target="dashboard" orderBy="${page.orderBy}" desc="${page.desc}">
					<button type='button' class='btn btn-default'>100</button>
				</mlib:link>
			</div>
		</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>