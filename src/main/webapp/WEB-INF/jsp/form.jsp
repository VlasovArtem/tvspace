<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="include.jsp" %>
<html>
<head>
    <title>Add Actor</title>
</head>
<body>
<section class="container-fluid">
    <form:form commandName="actor" method="post" action="/addActor"
               cssClass="form-horizontal col-md-offset-4 col-md-4" title="actorForm">
        <div class="form-group">
            <label for="lastname">Actor Lastname</label>
            <form:input path="lastname" id="lastname" cssClass="form-control"/>
        </div>
        <div class="form-group">
            <label for="firstname">Actor firstname</label>
            <form:input path="firstname" id="firstname" cssClass="form-control"/>
        </div>
        <div class="form-group">
            <label for="birthday">Birthday</label>
            <form:input path="birthday" type="date" id="birthday" cssClass="form-control"/>
        </div>
        <div class="form-group center">
            <input type="submit" value="Submit" class="btn btn-success" onclick="sayHello()">
        </div>
    </form:form>
</section>
</body>
</html>
