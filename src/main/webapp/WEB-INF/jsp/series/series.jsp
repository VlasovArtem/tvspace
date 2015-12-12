<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="qu" uri="myTags" %>
<%@ taglib prefix="sfn" uri="series-functions" %>
<%@ include file="../include.jsp" %>
<html>
<head>
    <title>Series</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.4/jquery-ui.js"></script>
</head>
<body onload="init()">
<%@ include file="../navbar.jsp" %>
<aside id="series-search" class="col-md-3">
    <div class="col-md-offset-1 col-md-9 sticky">
        <div class="center search-title col-md-12"><b>Search</b></div>
        <form:form action="/series/search" modelAttribute="search" method="get" id="searchForm"
                   cssClass="container-fluid col-md-12 tv-space-form">
            <div class="form-group">
                <form:select path="genre" id="searchGenre" cssClass="form-control tv-space-form-control">
                    <form:option value="" disabled="true" selected="true">Select genre</form:option>
                    <form:options items="${genres}"/>
                </form:select>
            </div>
            <div class="form-group">
                <form:select path="year" id="searchYear" cssClass="form-control tv-space-form-control">
                    <form:option value="" disabled="true" selected="true">Select year</form:option>
                    <form:options items="${years}"/>
                </form:select>
            </div>
            <div class="form-group">
                <form:input path="title" cssClass="form-control tv-space-form-control" placeholder="Input title"/>
            </div>
            <div class="form-group hide-finished">
                <table>
                    <tr>
                        <td><label>Hide Finished</label></td>
                        <td><form:checkbox path="hideFinished"/></td>
                    </tr>
                    <sec:authorize access="isAuthenticated()">
                        <tr>
                            <td><label>Show my Series</label></td>
                            <td><form:checkbox path="showUserSeries"/></td>
                        </tr>
                    </sec:authorize>
                </table>
            </div>
            <hr>
            <div class="col-md-12 center search-title"><b>Sort</b></div>
            <div class="form-group col-md-9 sort">
                <form:select path="sort" cssClass="form-control tv-space-form-control" items="${sortProperties}"
                             id="sort" onchange="sortChange()"/>
            </div>
            <div class="form-group col-md-3 direction-block" onclick="changeSort()">
                <div class="images">
                    <img src="${pageContext.request.contextPath}/resources/core/images/desc.png"
                         class="desc-img">
                    <img src="${pageContext.request.contextPath}/resources/core/images/asc.png"
                         class="asc-img">
                </div>
                <small class="direction">${search.direction}</small>
                <form:hidden path="direction" id="direction"/>
            </div>
            <div class="col-md-12 sort-info">
                Warning: This type of sort will hide some series.
            </div>
            <div class="form-group buttons center">
                <input type="button" value="Search" class="btn btn-success" onclick="searchSeries()">
                <spring:url value="/series" var="main"/>
                <a href="${main}"><input type="button" value="Reset" class="btn btn-danger"></a>
            </div>
        </form:form>
    </div>
</aside>
<section id="series" class="col-md-9">
    <c:if test="${series == null || fn:length(series) == 0}">
        <h2 class="empty-result">Oops... We did not find matches.</h2>
    </c:if>
    <h1 class="hide">Series</h1>
    <c:forEach var="ser" items="${series}">
        <div class="col-md-4">
            <div class="series-block center">
                <div class="series-img-block">
                    <div class="finished-block ${ser.finished ? '' : 'hide'}">Finished</div>
                    <img src="${ser.posterUrl}" class="series-block-img ${ser.finished ? 'finished' : ''}" height="250"
                         width="169">
                </div>
                <article class="main-info-block">
                    <div class="series-header">
                        <h3>${ser.title}</h3><small class="season">(Season ${ser.nextEpisode != null ? ser
                            .nextEpisode.seasonNumber : fn:length(ser.seasons)})</small>
                        <div class="year">${ser.seriesStart.year} -
                            <c:if test="${ser.seriesEnd.year > 0}">${ser.seriesEnd.year}</c:if></div>
                    </div>
                    <div class="left-block">
                        <ul>
                            <li>
                                <b>Creators: </b>${sfn:joining(ser.creators)}
                            </li>
                            <li>
                                <b>Actors: </b>${sfn:joining(ser.actors)}
                            </li>
                            <li>
                                <b>Genres:</b>
                                <c:forEach items="${ser.genres}" var="genre" varStatus="status">
                                    <c:choose>
                                        <c:when test="${status.last}">
                                        <span><a href="
                                                <qu:queryFormat genre="${genre}"
                                                                query="${pageContext.request.queryString}"
                                                                initQuery="/series/search?"/>">
                                                ${genre}</a></span>
                                        </c:when>
                                        <c:otherwise>
                                        <span><a href=
                                                         "<qu:queryFormat genre="${genre}"
                                                                query="${pageContext.request.queryString}"
                                                                initQuery="/series/search?"/>">
                                                ${genre}</a>, </span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </li>
                        </ul>
                    </div>
                    <c:if test="${sfn:checkDate(ser.nextEpisode.episodeDate)}">
                        <div class="next-episode">
                            <b>Next Episode:
                                <fmt:parseDate value="${ser.nextEpisode.episodeDate}" pattern="yyyy-MM-dd"
                                               var="parsedDate" type="date" />
                                <fmt:formatDate value="${parsedDate}" dateStyle="long"/></b>
                        </div>
                    </c:if>
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
                <sec:authorize access="isAuthenticated()">
                    <article class="tracking-series-block" id="${ser.id}">
                        <span title="Not Watch" class="glyphicon glyphicon-eye-open watching
                        ${sfn:checkUserSeries(ser.id, userSeries) ? 'show' : 'hide'}"
                              onclick="notWatching(${ser.id})"></span>
                        <span title="Watching" class="glyphicon glyphicon-eye-open not-watching
                        ${sfn:checkUserSeries(ser.id, userSeries) ? 'hide' : 'show'}"
                              onclick="watching(${ser.id})"></span>
                        <article
                                class="choose-series-info
                                ${sfn:checkUserSeries(ser.id, userSeries) ? 'show' : 'hide'}">
                            <c:set value="${sfn:findUserSeries(ser.id, userSeries)}" var="userSer"/>
                            <c:set value="${sfn:findSeasonToSelect(ser, userSeries)}" var="seasonNumber"/>
                            <c:set value="${sfn:findSeasonEpisodeToSelect(ser, seasonNumber, userSeries)}"
                                   var="episodeNumber"/>
                            <div class="overlay-choose-series"></div>
                            <form class="form-inline">
                                <fieldset>
                                    <div class="form-group">
                                        <label for="season">Season:</label>
                                        <select name="season" id="season"
                                                class="tv-space-form-control form-control"
                                                onchange='changeSeason(${sfn:seriesSeasonMap(ser)}, ${ser.id})'>
                                            <c:forEach begin="1" end="${fn:length(ser.seasons)}" var="season"
                                                       varStatus="status">
                                                <c:choose>
                                                    <c:when
                                                            test="${status.current == seasonNumber}">
                                                        <option value="${season}"
                                                                selected="selected">${season}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${season}">${season}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="episode">Episode:</label>
                                        <select name="episode" id="episode" class="tv-space-form-control form-control">
                                            <c:forEach begin="1" end="${sfn:findSeasonEpisodeLength(ser,
                                            seasonNumber)}" var="episode" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${status.current == episodeNumber}">
                                                        <option value="${episode}"
                                                                selected="selected">${episode}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${episode}">${episode}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group watch-button">
                                        <input type="button" class="btn btn-sm btn-success" value="Watch"
                                               onclick="markWatching(${ser.id})">
                                    </div>
                                </fieldset>
                            </form>
                        </article>
                    </article>
                </sec:authorize>
            </div>
        </div>
    </c:forEach>
</section>
</body>
</html>
