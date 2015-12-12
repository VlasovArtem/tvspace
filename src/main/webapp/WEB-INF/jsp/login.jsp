<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="include.jsp"%>
<html>
<head>
    <title>Login</title>
</head>
<body>
<%@ include file="navbar.jsp"%>
<section id="login" class="container">
    <h2 class="center hide">Login</h2>
    <article class="col-md-offset-3 col-md-6 login-block">
        <article class="center error">${error}</article>
        <form id="login-form" class="form-horizontal">
            <fieldset>
                <legend>Login</legend>
                <div class="form-group">
                    <input type="text" placeholder="Input email or username" name="loginData" id="loginData" class="form-control">
                </div>
                <div class="form-group">
                    <input type="password" placeholder="Input password" name="password" id="password" class="form-control">
                </div>
                <div class="form-group">
                    <input type="checkbox" id="rememberMe" value="true"/> Remember Me
                </div>
                <div class="form-group">
                    <input type="button" class="btn btn-success" value="Login" onclick="login()">
                </div>
            </fieldset>
        </form>
    </article>
</section>
<section class="container">
</section>
</body>
</html>
