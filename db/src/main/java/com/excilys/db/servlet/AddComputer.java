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
import com.excilys.db.model.Computer;
import com.excilys.db.service.CompaniesService;
import com.excilys.db.service.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        request.setAttribute("companies", CompaniesService.INSTANCE.listCompanies());
            RequestDispatcher rd =
                 request.getRequestDispatcher("/WEB-INF/addComputer.jsp");

            rd.forward(request,response);
       } catch (Exception e) { throw new ServletException(e); }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    ComputerDTO computerDTO = new ComputerDTO();
	    System.out.println(request.getAttribute("computerName"));
	    System.out.println(request.getParameter("introduced"));
	    System.out.println(request.getParameter("discontinued"));
	    computerDTO.setCompany(request.getParameter("computerName"));
        computerDTO.setIntroduced(request.getParameter("introduced"));
        computerDTO.setDiscontinued(request.getParameter("discontinued"));
        computerDTO.setCompany(request.getParameter("companyId"));
        
        System.out.println(request.getParameter("companyId"));
        System.out.println(computerDTO.getCompany());
        Computer computer = ComputerMapper.computerDTOToComputer(computerDTO);
        ComputerService.INSTANCE.createComputer(computer);
        doGet(request, response);
	}

    public List<ComputerDTO> getComputer() {

        return ComputerMapper.computerListToComputerDTO(ComputerService.INSTANCE.listComputer());
    }
	
}
