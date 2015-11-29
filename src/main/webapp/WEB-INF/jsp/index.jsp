<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="include.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<header id="info-section" class="container-fluid block">
    <div class="overlay"></div>
    <nav class="navbar navbar-default tv-space-main-nav">
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
                <div class="tv-space navbar-brand">
                    <div class="icon"></div><div class="tv-header">TV Space</div>
                </div>
            </div>
            <div class="tv-space collapse navbar-collapse" id="tv-space-navbar-collapse">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="/series">Series<span class="line"></span></a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <article class="container-fluid text-info">
        <div class="row">
            <div class="section-header col-md-offset-3 col-md-6">Your personal tv series guide</div>
        </div>
        <div class="row">
            <div class="section-plot col-md-offset-3 col-md-6">
                We live in time of amazing and endless world of different tv series. There different genres or cast
                of these series, and it is very hard work to keep in mind all series that you watch or not. And
                for this we create this amazing project. You can track your favorite series, mark watched series,
                look for the dates of the next episodes, communicate with like-minded persons. We are sure that you
                can find new amazing episodes and share your thoughts with others.
            </div>
        </div>
    </article>
    <div class="arrow">
        <a href="#project-info"><span class="glyphicon glyphicon-chevron-down"></span></a>
    </div>
</header>
<section id="project-info" class="block">
    <div class="col-md-4 series-tracking">
        <div class="row">
            <img src="../../resources/core/images/background/main-page/project-info/poster-series-tracking.jpg"
                 height="300" alt="Series tracking">
        </div>
        <article class="col-md-offset-3 col-md-6">
            Track your favorite series, do not choke your head with useless information "What was the last episode I
            watched?"
        </article>
    </div>
    <div class="col-md-4 find-new-series">
        <div class="row">
            <img src="../../resources/core/images/background/main-page/project-info/poster-search-series.jpg"
                 alt="Find new series" height="300">
        </div>
        <article class="col-md-offset-3 col-md-6">
            Find something new is always interesting. Sometimes it should make you happier, good luck in search.
        </article>
    </div>
    <div class="col-md-4 conversation-series">
        <div class="row">
            <img src="../../resources/core/images/background/main-page/project-info/poster-like-minded-talk.jpg"
                 alt="Like-minded talk" height="300">
        </div>
        <article class="col-md-offset-3 col-md-6">
            It is always interesting to talk about your favorite tv series with like-minded persons.
        </article>
    </div>
</section>
</body>
</html>
