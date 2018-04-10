package com.excilys.db.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.ServiceException;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Computer;
import com.excilys.db.service.CompaniesService;
import com.excilys.db.service.ComputerService;
import com.excilys.db.validator.ComputerValidator;

/**
 * Servlet implementation class EditComputer.
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String INDEX = "/WEB-INF/index.jsp";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
        super();
    }

    /**
     *
     * @param request la requête
     * @param response la réponse
     * @return si il est null
     */
    private boolean testNullEmpty(HttpServletRequest request, HttpServletResponse response) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EditComputer.class);
        if (request.getParameter("id") == null) {
            RequestDispatcher rd =
                    request.getRequestDispatcher(INDEX);
            try {
                rd.forward(request, response);
                return true;
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        }
        if (request.getParameter("id").isEmpty()) {
            RequestDispatcher rd =
                    request.getRequestDispatcher(INDEX);
            try {
                rd.forward(request, response);
                return true;
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        }
        return false;
    }

    /**
     *
     * @param request la requête
     * @param response la réponse
     * @param id l'id de l'ordinateur
     * @return si il existe
     */
    private boolean testComputerNotExist(HttpServletRequest request, HttpServletResponse response, int id) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EditComputer.class);
        if (!ComputerValidator.INSTANCE.exist(id)) {
            RequestDispatcher rd =
                    request.getRequestDispatcher("/WEB-INF/404.jsp");
            try {
                rd.forward(request, response);
                return true;
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
        }
        return false;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EditComputer.class);
        int id = 0;
        if (testNullEmpty(request, response)) {
            return;
        }
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        if (testComputerNotExist(request, response, id)) {
            return;
        }
        ComputerDTO computerDTO = null;
        try {
            Optional<Computer> computer = ComputerService.INSTANCE.showDetails(id);
            if (computer.isPresent()) {
                computerDTO = ComputerMapper.computerToDTO(computer.get());
            }
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher(INDEX);
            try {
                rd.forward(request, response);
            } catch (Exception e1) {
                logger.warn(e.getMessage());
            }
        }
        request.setAttribute("id", id);
        request.setAttribute("computer", computerDTO);
        request.setAttribute("companies", CompaniesService.INSTANCE.listCompanies());
        try {
            RequestDispatcher rd =
                    request.getRequestDispatcher("/WEB-INF/editComputer.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EditComputer.class);
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        Computer computer = postComputer(request);
        computer.setId(id);
        try {
            ComputerService.INSTANCE.updateAComputer(computer, id);
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
            try {
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } catch (Exception e1) {
                logger.debug(e1.getMessage());
            }
        }
        try {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    /**
     *
     * @param request la requete
     * @return l'ordinateur construit grâce à elle
     */
    private Computer postComputer(HttpServletRequest request) {
        ComputerDTO computerDTO = new ComputerDTO();

        computerDTO.setName(request.getParameter("computerName"));
        computerDTO.setIntroduced(request.getParameter("introduced"));
        computerDTO.setDiscontinued(request.getParameter("discontinued"));
        computerDTO.setCompany(request.getParameter("companyId"));
        return ComputerMapper.computerDTOToComputer(computerDTO);
    }

}
