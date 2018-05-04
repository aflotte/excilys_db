<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>

<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
            <div class="pull-right">
                <a class="navbar-brand" href="?lang=fr">FR</a>
                <a class="navbar-brand" href="?lang=en">EN</a>
            </div>
        </div>
    </header>
    
    <c:url value="/login" var="loginUrl"/>
    <form action="${loginUrl}" method="post">       
        <c:if test="${param.error != null}">        
            <p><spring:message code="autherror"/></p>
        </c:if>
        <c:if test="${param.logout != null}">       
            <a class="navbar-brand"><spring:message  code="logedout"/></a>
        </c:if>
        <p>
            <label for="username"><spring:message code="Username"/></label>
            <input type="text" id="username" name="username"/>  
        </p>
        <p>
            <label for="password"><spring:message code="Password"/></label>
            <input type="password" id="password" name="password"/>  
        </p>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary"><spring:message code="LogIn"/></button>
    </form>
    
<script src="static/js/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/dashboard.js"></script>

</body>
</html>