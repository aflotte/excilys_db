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
                searchJSP="vide";
                pageComputer = new PageComputerDTO(actualPage, sizePage);
            } else {
                logger.debug("enter in search not empty");
                search = request.getParameter("search");
                searchJSP="&search="+search;
                pageComputer = new PageComputerDTO(actualPage, sizePage, search);
            }
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




}
