<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${id}
                    </div>
                    <h1>Edit Computer</h1>

                    <form:form action="editComputer" method="POST" modelAttribute="computer">
                        <form:input path="id" type="hidden" value="${id}" id="id" name ="id"/>
                        <fieldset>
                            <div class="form-group">
                                <form:label path="name" for="computerName">Computer name</form:label>
                                <form:input path="name" type="text" pattern="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\*. ]+$" class="form-control" id="computerName" name="computerName" placeholder="Computer name" value="${computer.getName()}"/>
                            </div>
                            <div class="form-group">
                                <form:label path="introduced" for="introduced">Introduced date</form:label>
                                <form:input path="introduced" type="date" pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))" class="form-control" id="introduced" name="introduced" placeholder="aaaa-MM-jj" value="${computer.getIntroduced()}"/>
                            </div>
                            <div class="form-group">
                                <form:label path="discontinued" for="discontinued">Discontinued date</form:label>
                                <form:input path="discontinued" type="date" pattern="(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))" class="form-control" id="discontinued" name="discontinued" placeholder="aaaa-MM-jj" value="${computer.getDiscontinued()}"/>
                            </div>
                            <div class="form-group">
                                <form:label path="company" for="companyId">Company</form:label>
                                <form:select path="company" class="form-control" id="companyId" name="companyId">
                                <form:option value = ''>-----</form:option>
                                    <c:forEach var="company" items="${companies}">
                                    <form:option value='${company.getName()}'>${company.getName()}</form:option>
                                    </c:forEach>
                                </form:select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary"/>
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>