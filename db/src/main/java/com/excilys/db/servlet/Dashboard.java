package com.excilys.db.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.db.page.PageComputerDTO;

/**
 * Servlet implementation class GetComputer.
 */


//TODO: pass by StringUtils.isBlank
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Dashboard.class);
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PageComputerDTO pageComputer;
            int actualPage;
            String[] preparedSort = prepareSort(request);
            String toSort = preparedSort[0];
            String orderBy = preparedSort[1];
            if (request.getParameter("actualPage") == null) {
                actualPage = 1;
                pageComputer = new PageComputerDTO();
            } else {
                if (request.getParameter("actualPage").isEmpty()) {
                    actualPage = 1;
                } else {
                    actualPage = Integer.parseInt(request.getParameter("actualPage"));
                }
            }
            int sizePage;
            if ((request.getParameter("pageSize") == null) || (request.getParameter("pageSize").isEmpty())) {
                sizePage = 10;
            } else {
                sizePage = Integer.parseInt(request.getParameter("pageSize"));
            }
            sizePage = sizePage < 10 ? sizePage = 10 : sizePage;
            String search;
            String searchJSP;
            if ((request.getParameter("search") == null) || (request.getParameter("search").isEmpty())) {
                searchJSP="";
                pageComputer = new PageComputerDTO(actualPage, sizePage);
            } else {
                logger.debug("enter in search not empty");
                search = request.getParameter("search");
                searchJSP="&search="+search;
                pageComputer = new PageComputerDTO(actualPage, sizePage, search);
            }
            pageComputer.setSortBy(toSort);
            pageComputer.setOrderBy(orderBy);
            request.setAttribute("search", searchJSP);
            request.setAttribute("page", pageComputer);
            RequestDispatcher rd =
                    request.getRequestDispatcher("/WEB-INF/index.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     *
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    
    
    private String[] prepareSort(HttpServletRequest request) {
        String toSort = "";
        String preparation = "";
        request.setAttribute("computerPath", "&sort=computer&orderBy=asc");
        request.setAttribute("introducedPath", "&sort=introduced&orderBy=asc");
        request.setAttribute("discontinuedPath", "&sort=discontinued&orderBy=asc");
        request.setAttribute("companyPath", "&sort=company&orderBy=asc");
        request.setAttribute("sortPath", "");
        if (!((request.getParameter("sort") == null) || (request.getParameter("sort").isEmpty()))) {
            toSort = request.getParameter("sort");
            request.setAttribute("sortPath", "");
            switch (toSort) {
            case "computer":
                preparation = "&sort=computer";
                if ((request.getParameter("orderBy")!=null)&&(request.getParameter("orderBy").equals("asc"))) {
                    request.setAttribute("computerPath", "&sort=computer&orderBy=desc");
                }
                toSort = "computer.name";
                break;
            case "introduced":
                preparation = "&sort=introduced";
                if ((request.getParameter("orderBy")!=null)&&(request.getParameter("orderBy").equals("asc"))) {
                    request.setAttribute("introducedPath", "&sort=introduced&orderBy=desc");
                }
                toSort = "computer.introduced";
                break;
            case "discontinued":
                preparation = "&sort=discontinued";
                if ((request.getParameter("orderBy")!=null)&&(request.getParameter("orderBy").equals("asc"))) {
                    request.setAttribute("discontinuedPath", "&sort=discontinued&orderBy=desc");
                }
                toSort = "computer.discontinued";
                break;
            case "company":
                preparation = "&sort=company";
                if ((request.getParameter("orderBy")!=null)&&(request.getParameter("orderBy").equals("asc"))) {
                    request.setAttribute("companyPath", "&sort=company&orderBy=desc");
                }
                toSort = "company.name";
                break;
            default:
                toSort="computer.id";
                break;
            }
        }else {
            toSort="computer.id";
        }
        String orderBy;
        if (!((request.getParameter("orderBy") == null) || (request.getParameter("orderBy").isEmpty()))) {
            if (!((preparation == null)||preparation.isEmpty())) {
                switch(request.getParameter("orderBy")) {
                case "desc":
                    preparation += "&orderBy=desc";
                    orderBy = "desc";
                    break;
                default:
                    preparation += "&orderBy=asc";
                    orderBy = "asc";
                    break;
                }
            }else {
                orderBy = "asc";
            }
        }else {
            orderBy = "asc";
        }
        
        request.setAttribute("sortPath", preparation);
        String[] answer = {toSort,orderBy};
        return answer;
    }


}
