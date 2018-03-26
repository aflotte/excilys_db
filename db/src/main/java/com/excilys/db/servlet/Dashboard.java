package com.excilys.db.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.page.PageComputerDTO;
import com.excilys.db.service.ComputerService;

/**
 * Servlet implementation class GetComputer.
 */
@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
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
        if (request.getParameter("actualPage") == null) {
            pageComputer = new PageComputerDTO();
        } else {
            if (request.getParameter("actualPage").isEmpty()) {
                pageComputer = new PageComputerDTO();
            } else {
                int actualPage = Integer.parseInt(request.getParameter("actualPage"));
                pageComputer = new PageComputerDTO(actualPage);
            }
        }
        request.setAttribute("computers", pageComputer.getPage());
        request.setAttribute("page", pageComputer);
        request.setAttribute("number", pageComputer.getComputerMax());
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



    public List<ComputerDTO> getComputer() {

        return ComputerMapper.computerListToComputerDTO(ComputerService.INSTANCE.listComputer(0, 1));
    }


}
