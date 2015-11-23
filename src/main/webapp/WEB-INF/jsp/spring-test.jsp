<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="include.jsp" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <section id="test" class="container-fluid">
            <h2 class="center">Things</h2>
            <div class="col-md-offset-4 col-md-4">
                <form:select path="things" items="${things}" cssClass="form-control"/>
            </div>
        </section>
    </body>
</html>
