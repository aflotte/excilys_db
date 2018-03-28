<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
            <h1 id="homeTitle">
    ${page.getComputerMax()} Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Delete</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                <c:forEach var="computer" items="${page.getPage()}">
            <tr>
            <td class='editMode'>
            <input type='checkbox' name='cb' class='cb' value='0'>
            </td>
            <td>
            <a href='editComputer?id=${computer.getId()}' onclick=''>  ${computer.getName()} </a>
            <td> ${computer.getIntroduced()} </td>
            <td> ${computer.getDiscontinued()} </td>
            <td> ${computer.getCompany()}</td>
             </tr>
                
                </c:forEach>
                
                    
                    
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
            
            <c:forEach var="pagePrev" items="${page.getPrevious()}">
                  <li>
                    <a href="?actualPage=${pagePrev}&pageSize=${page.getPageSize()}${search}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
                  
                  </li>
                  </c:forEach>
            
                
                  <c:forEach var="pageToPrint" items="${page.getPagesToGo()}">
                  <li><a href="?actualPage=${pageToPrint}&pageSize=${page.getPageSize()}${search}">${pageToPrint}</a></li>
                  </c:forEach>
                  
                  
             <c:forEach var="pageNext" items="${page.getNext()}">
                  
                  
              <li>
                <a href="?actualPage=${pageNext}&pageSize=${page.getPageSize()}${search}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
            </c:forEach>
        </ul>

        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default" onclick="location.href='?actualPage=${page.getPageNumber()}&pageSize=10${search}'">10</button>
            <button type="button" class="btn btn-default" onclick="location.href='?actualPage=${page.getPageNumber()}&pageSize=50${search}'">50</button>
            <button type="button" class="btn btn-default" onclick="location.href='?actualPage=${page.getPageNumber()}&pageSize=100${search}'">100</button>
        </div>
    </div>
    </footer>
<script src="static/js/jquery.min.js"></script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/dashboard.js"></script>

</body>
</html>