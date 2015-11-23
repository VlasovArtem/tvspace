<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="include.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2 class="center">Things</h2>
    <div>
        <form:select path="things" items="${things}" cssClass="form-control"></form:select>
    </div>
</body>
</html>
