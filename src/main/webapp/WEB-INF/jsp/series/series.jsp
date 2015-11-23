<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../include.jsp"%>
<html>
<head>
    <title>Series</title>
</head>
<body>
<section id="series" class="container-fluid">
    <h1 class="center">Series</h1>
    <c:forEach var="ser" items="${series}">
        <div class="col-md-3">
            <div class="series-block center">
                <div class="series-img-block">
                    <div class="finished-block ${ser.finished ? '' : 'hide'}">Finished</div>
                    <img src="${ser.poster}" class="series-block-img ${ser.finished ? 'finished' : ''}" height="250"
                         width="169">
                </div>
                <article class="main-info-block">
                    <h3>${ser.title}</h3><small class="season">(Season ${ser.currentSeason})</small>
                    <div class="year">${ser.yearStart} - <c:if test="${ser.yearEnd > 0}">${ser.yearEnd}</c:if></div>
                    <div class="left-block">
                        <ul>
                            <c:if test="${ser.country != null}">
                                <li>
                                    <div><b>Country:</b> ${ser.country}</div>
                                </li>
                            </c:if>
                            <c:if test="${ser.writers != null}">
                                <li>
                                    <b>Writers:</b>
                                    <c:forEach items="${ser.writers}" var="writer" varStatus="status">
                                        <c:choose>
                                            <c:when test="${status.last}">
                                                <span>${writer}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>${writer}, </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </li>
                            </c:if>
                            <c:if test="${ser.directors != null}">
                                <li>
                                    <b>Directors:</b>
                                    <c:forEach items="${ser.directors}" var="director" varStatus="status">
                                        <c:choose>
                                            <c:when test="${status.last}">
                                                <span>${director}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>${director}, </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </li>
                            </c:if>
                            <c:if test="${ser.actors != null}">
                                <li>
                                    <b>Actors:</b>
                                    <c:forEach items="${ser.actors}" var="actor" varStatus="status">
                                        <c:choose>
                                            <c:when test="${status.last}">
                                                <span>${actor.name}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>${actor.name}, </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </li>
                            </c:if>
                            <b>Genres:</b>
                            <c:forEach items="${ser.genres}" var="genre" varStatus="status">
                                <c:choose>
                                    <c:when test="${status.last}">
                                        <span>${genre}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span>${genre}, </span>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                        <c:set var="today" value="<%= LocalDate.now()%>"/>
                        <c:if test="${ser.nextEpisode != null && ser.nextEpisode.released > today}">
                            <div>
                                <b>Next Episode:
                                    <fmt:parseDate value="${ser.nextEpisode.released}" pattern="yyyy-MM-dd"
                                                   var="parsedDate" type="date" />
                                    <fmt:formatDate value="${parsedDate}" dateStyle="long"/></b>
                            </div>
                        </c:if>
                    </div>
                </article>
                <article class="additional-info-block container-fluid">
                    <c:if test="${ser.imdbRating > 0}">
                        <div class="rating"><img src="${pageContext.request.contextPath}/resources/core/images/imdb-icon.png"
                                                 alt="" width="50">
                            <span class="rating-data">${ser.imdbRating}</span></div>
                    </c:if>
                    <div class="col-md-12 plot">
                        <h4 class="center">Plot</h4>
                        <span>${ser.plot}</span>
                    </div>
                </article>
            </div>
        </div>
    </c:forEach>
</section>
</body>
</html>
