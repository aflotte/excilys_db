package com.excilys.db.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.db.service.ComputerService;

/**
 * Servlet implementation class Delete.
 */
@WebServlet("/delete")
public class Delete extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String SELECTION = "selection";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddComputer.class);
        if (!((request.getParameter(SELECTION) == null) || (request.getParameter(SELECTION).isEmpty()))) {
            String[] toDelete = (request.getParameterValues(SELECTION)[0]).split(",");
            int[] toDeleteId = new int[toDelete.length];
            for (int i = 0; i < toDelete.length; i++) {
                try {
                    toDeleteId[i] = Integer.parseInt(toDelete[i]);
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                }
            }
            ComputerService.INSTANCE.deleteListComputer(toDeleteId);
        }
        try {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddComputer.class);
        try {
            doGet(request, response);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }
}
