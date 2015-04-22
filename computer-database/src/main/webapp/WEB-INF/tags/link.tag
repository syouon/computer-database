<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="target" required="true" type="java.lang.String" %>
<%@ attribute name="page" required="true" type="java.lang.Integer" %>
<%@ attribute name="range" required="true" type="java.lang.Integer" %>
<%@ attribute name="body" required="true" type="java.lang.String" %>
<%@ attribute name="ariaValue" type="java.lang.String" %>
<%@ attribute name="search" type="java.lang.String" %>

<a href="${target}?page=${page}&range=${range}&search=${search}" aria-label="${aria_value}">${body}</a>