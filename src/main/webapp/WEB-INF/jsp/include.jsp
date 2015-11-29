<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<spring:url value="/resources/bower_components/bootstrap/dist/js/bootstrap.js" var="bootstrapJs"/>
<spring:url value="/resources/bower_components/jquery/dist/jquery.js" var="jqueryJs"/>
<spring:url value="/resources/core/js/tvspace.js" var="tvspaceJs"/>
<spring:url value="/resources/bower_components/bootstrap/dist/css/bootstrap.css" var="bootstrapCss"/>
<spring:url value="/resources/core/css/tvspace.css" var="tvspaceCss"/>
<spring:url value="/resources/bower_components/Stickyfill/dist/stickyfill.js" var="stickyfill"/>
<spring:url value="/resources/bower_components/underscore/underscore.js" var="underscore"/>
<link rel="shortcut icon" type="image/x-icon" href="../../resources/core/images/tv-space-icon-250x250.png"/>
<link rel="stylesheet" href="${bootstrapCss}">
<link rel="stylesheet" href="${tvspaceCss}">
<link href='https://fonts.googleapis.com/css?family=Alfa+Slab+One' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Nunito' rel='stylesheet' type='text/css'>
<script src="${stickyfill}"></script>
<script src="${jqueryJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="${tvspaceJs}"></script>
<script src="${underscore}"></script>
<nav class="navbar navbar-default tv-space-navbar navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#tv-space-navbar-collapse" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/"><img
                    src="../../resources/core/images/tv-space-icon-250x250.png" width="70"
                    height="70" alt="icon"></a>
        </div>
        <div class="collapse navbar-collapse" id="tv-space-navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="/series">Series<span class="line"></span></a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="padding-block"></div>

