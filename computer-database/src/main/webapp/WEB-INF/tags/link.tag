<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="target" required="true" type="java.lang.String"%>
<%@ attribute name="page" required="true" type="java.lang.Integer"%>
<%@ attribute name="range" required="true" type="java.lang.Integer"%>
<%@ attribute name="ariaValue" type="java.lang.String"%>
<%@ attribute name="search" type="java.lang.String"%>
<%@ attribute name="orderBy" type="java.lang.String"%>
<%@ attribute name="desc" type="java.lang.String"%>
<%@ attribute name="change" type="java.lang.String"%>

<a
	href="${target}?page=${page}&range=${range}&search=${search}&orderby=${orderBy}&desc=${desc}&change=${change}"
	aria-label="${ariaValue}"><jsp:doBody /></a>