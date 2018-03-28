package com.excilys.db.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.db.dao.ComputerDAO;
import com.excilys.db.service.ComputerService;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/delete")
public class Delete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        if ((request.getParameter("selection") == null) || (request.getParameter("selection").isEmpty())) {

        } else {
            String[] toDelete = (request.getParameterValues("selection")[0]).split(",");
            int[] toDeleteId = new int[toDelete.length];
            for (int i = 0; i < toDelete.length; i++) {
                toDeleteId[i] = Integer.parseInt(toDelete[i]);
            }
            ComputerService.INSTANCE.deleteListComputer(toDeleteId);
        }
        RequestDispatcher rd = request.getRequestDispatcher("/dashboard");
        rd.forward(request,response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
