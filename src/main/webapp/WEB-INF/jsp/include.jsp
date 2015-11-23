<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<spring:url value="/resources/bower_components/bootstrap/dist/js/bootstrap.js" var="bootstrapJs"/>
<spring:url value="/resources/bower_components/jquery/dist/jquery.js" var="jqueryJs"/>
<spring:url value="/resources/core/js/tvspace.js" var="tvspaceJs"/>
<spring:url value="/resources/bower_components/bootstrap/dist/css/bootstrap.css" var="bootstrapCss"/>
<spring:url value="/resources/core/css/tvspace.css" var="tvspaceCss"/>
<link rel="stylesheet" href="${bootstrapCss}">
<link rel="stylesheet" href="${tvspaceCss}">
<link href='https://fonts.googleapis.com/css?family=Alfa+Slab+One' rel='stylesheet' type='text/css'>
<script src="${jqueryJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="${tvspaceJs}"></script>

