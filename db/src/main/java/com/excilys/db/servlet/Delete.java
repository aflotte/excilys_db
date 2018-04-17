package com.excilys.db.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.db.service.IComputerService;

/**
 * Servlet implementation class Delete.
 */
@Controller
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

    @Autowired
    private IComputerService computerService;



    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddComputer.class);
        if (!((request.getParameter(SELECTION) == null) || (request.getParameter(SELECTION).isEmpty()))) {
            String[] toDelete = (request.getParameterValues(SELECTION)[0]).split(",");
            List<Integer> toDeleteId = new ArrayList<>();
            for (int i = 0; i < toDelete.length; i++) {
                try {
                    toDeleteId.add(Integer.parseInt(toDelete[i]));
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                }
            }
            computerService.deleteListComputer(toDeleteId);
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
