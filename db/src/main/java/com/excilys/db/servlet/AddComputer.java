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
import com.excilys.db.exception.ServiceException;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Computer;
import com.excilys.db.service.CompaniesService;
import com.excilys.db.service.ComputerService;

/**
 * Servlet implementation class AddComputer.
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddComputer.class);
        try {
            request.setAttribute("companies", CompaniesService.INSTANCE.listCompanies());
            RequestDispatcher rd =
                    request.getRequestDispatcher("/WEB-INF/addComputer.jsp");

            rd.forward(request, response);
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
        ComputerDTO computerDTO = new ComputerDTO();
        computerDTO.setName(request.getParameter("computerName"));
        computerDTO.setIntroduced(request.getParameter("introduced"));
        computerDTO.setDiscontinued(request.getParameter("discontinued"));
        computerDTO.setCompany(request.getParameter("companyId"));
        Computer computer = ComputerMapper.computerDTOToComputer(computerDTO);
        try {
            ComputerService.INSTANCE.createComputer(computer);
        } catch (ServiceException e) {
            logger.debug(e.getMessage());
        }
        try {
            doGet(request, response);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    public List<ComputerDTO> getComputer() {

        return ComputerMapper.computerListToComputerDTO(ComputerService.INSTANCE.listComputer());
    }
}
