<header id="additional-section-header" class="container-fluid">
    <nav class="navbar navbar-default tv-space-navbar navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#tv-space-navbar-collapse" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="tv-space-additional navbar-brand">
                    <a href="/"><div class="icon"></div></a><a href="/" class="tv-header">TV Space</a>
                </div>
            </div>
            <div class="tv-space-additional collapse navbar-collapse" id="tv-space-navbar-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="/series">Series</a></li>
                </ul>
                <sec:authorize access="isAnonymous()">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="/signup">Sign Up</a>
                        </li>
                        <li>
                            <a href="/login">Login</a>
                        </li>
                    </ul>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="/logout">Logout</a>
                        </li>
                    </ul>
                </sec:authorize>
            </div>
        </div>
    </nav>
</header>
<div class="padding-block"></div>