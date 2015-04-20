<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mlib" tagdir="/WEB-INF/tags" %>
<%@ attribute name="page" required="true" type="java.lang.Integer" %>
<%@ attribute name="currentRange" required="true" type="java.lang.Integer" %>
<%@ attribute name="pageNumber" required="true" type="java.lang.Integer" %>

<ul class="pagination">
	<c:if test="${page-1 != 0}">
		<li><mlib:link body="<span aria-hidden='true'>&laquo;</span>"
				range="${currentRange}" page="${page-1}" target="DashboardServlet" /></li>
	</c:if>
	<c:choose>
		<c:when test="${page <= 2}">
			<c:forEach var="i" begin="1" end="${page+2}">
				<li><mlib:link target="DashboardServlet" page="${i}"
						range="${currentRange}" body="${i}" /></li>
			</c:forEach>
		</c:when>
		<c:when test="${page > 2 && page <= pageNumber-2}">
			<c:forEach var="i" begin="${page-2}" end="${page+2}">
				<li><mlib:link target="DashboardServlet" page="${i}"
						range="${currentRange}" body="${i}" /></li>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:forEach var="i" begin="${page-2}" end="${pageNumber+1}">
				<li><mlib:link target="DashboardServlet" page="${i}"
						range="${currentRange}" body="${i}" /></li>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	<c:if test="${page <= pageNumber}">
		<li><mlib:link body="<span aria-hidden='true'>&raquo;</span>"
				range="${currentRange}" page="${page+1}" target="DashboardServlet"
				ariaValue="Next" /></li>
	</c:if>
</ul>