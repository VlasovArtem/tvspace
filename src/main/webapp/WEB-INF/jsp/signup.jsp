<%@ include file="include.jsp"%>
<html>
<head>
    <title>Sign up</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.14.0/jquery.validate.js"></script>
    <spring:url value="/resources/core/js/validation.js" var="validation"/>
    <script src="${validation}"></script>
</head>
<body>
<%@ include file="navbar.jsp"%>
<section id="signup" class="container">
    <h2 class="center">Sign Up</h2>
    <article class="col-md-6 col-md-offset-3 reg-form">
        <c:if test="${error != null}">
            <article class="center form-error-info">${error}</article>
        </c:if>
        <form:form action="/signup" modelAttribute="user" cssClass="form-horizontal" id="tv-space-reg-form"
                   method="post">
            <div class="form-group">
                <label for="name">Name</label>
                <form:input path="name"
                            cssClass="form-control"
                            type="text"
                            name="name"
                            pattern="[A-Za-z '.\-]{6,50}"
                            placeholder="Input name"
                            autocomplete="off"/>
            </div>
            <div class="form-group">
                <label for="username">Username</label>
                <form:input path="username" cssClass="form-control" type="text" placeholder="Input username"
                            pattern="[A-Za-z0-9_\- .]{6,100}"
                            autocomplete="off"/>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <form:input path="email" cssClass="form-control" type="email" id="email" placeholder="Input email"
                            autocomplete="off"/>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <form:password path="password" cssClass="form-control" id="password" placeholder="Input password"
                               autocomplete="off"/>
            </div>
            <div class="form-group center">
                <input type="submit" value="Sign Up" class="btn btn-success">
            </div>
        </form:form>
    </article>
</section>
</body>
</html>
