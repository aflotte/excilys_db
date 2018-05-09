<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>

<c:url value="/logout" var="logoutUrl" />
<form action="${logoutUrl}" method="post" id="logoutForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<script>
    function formSubmit() {
        document.getElementById("logoutForm").submit();
    }
</script>

<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
            <a class="navbar-brand" href="?lang=fr">FR</a>
            <a class="navbar-brand" href="?lang=en">EN</a>
            <div class="pull-right">
            <a href="javascript:formSubmit()"><spring:message code="logout"/></a>
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        <spring:message code="Username"/>: ${dtoUser.username}
                    </div>
                    <h1><spring:message code="UpdateUser"/></h1>
                    <c:if test="${not empty error}">
                        <p><spring:message code="${error}"/></p>
                    </c:if>
                    <form:form action="updateUser" method="POST" modelAttribute="DTOUser">
                        <fieldset>
                            <form:input type="hidden" path="username" name="userName" id="userName" value="${dtoUser.username}" />
                            <div class="form-group">
                                <label for="password"><spring:message code="Password"/></label>
                                <form:input type="text" class="form-control" path="password" name="password" id="password" placeholder="Password"/>
                            </div>                
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value=<spring:message code="Edit"/> class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default"><spring:message code="Cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>