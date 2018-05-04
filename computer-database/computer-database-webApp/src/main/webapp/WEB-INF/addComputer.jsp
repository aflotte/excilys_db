<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
   <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<title><spring:message code="index.applName"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
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
            <a class="navbar-brand" href="dashboard"> <spring:message code="index.application"/> </a>
            <a class="navbar-brand" href="?lang=fr">FR</a>
            <a class="navbar-brand" href="?lang=en">EN</a>
            <div class="pull-right">
                <a href="updateUser?username=${username}">${username}</a>
                <a href="javascript:formSubmit()"><spring:message code="logout"/></a>
</div>
            
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="index.add"/></h1>
                    <form:form action="addComputer" method="POST" modelAttribute="computer">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="index.name"/></label>
                                <spring:message code="index.name" var="i18nName"/>
                                <form:input path="name" type="text" pattern="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\*. ]+$" class="form-control" id="computerName" name="computerName" placeholder="${i18nName}" required="required"/>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="index.introduced"/></label>
                                <form:input path="introduced" type="date" pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))" class="form-control" id="introduced" name="introduced" placeholder="aaaa-MM-jj"/>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="index.discontinued"/></label>
                                <form:input path="discontinued" type="date" pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))" class="form-control" id="discontinued" name="discontinued" placeholder="aaaa-MM-jj"/>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="index.company"/></label>
                                <form:select path="company" class="form-control" id="companyId" name="companyId">
                                    <form:option path="company" value = ''>-----</form:option>
                                <c:forEach var="company" items="${companies}">
                                    <form:option path="company" value='${company.getName()}'>${company.getName()}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default"><spring:message code="index.cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
    <script src="<c:url value="/static"/>/js/jquery.min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
<script>
    $.validate({
        lang: 'en',
        modules: 'html5'
    });
</script>
</body>
</html>