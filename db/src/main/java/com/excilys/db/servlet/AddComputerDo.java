package com.excilys.db.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.db.dao.ComputerDAO;
import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Computer;
import com.excilys.db.service.ComputerService;

/**
 * Servlet implementation class AddComputerDo
 */
@WebServlet("/AddComputerDo")
public class AddComputerDo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerDo() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd =
                request.getRequestDispatcher("/WEB-INF/index.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        ComputerDTO computerDTO = new ComputerDTO();
        computerDTO.setCompany(request.getParameter("computerName"));
        computerDTO.setIntroduced(request.getParameter("introduced"));
        computerDTO.setDiscontinued(request.getParameter("discontinued"));
        computerDTO.setCompany(request.getParameter("companyId"));
        Computer computer = ComputerMapper.computerDTOToComputer(computerDTO);
        ComputerService.INSTANCE.createComputer(computer);
        doGet(request, response);
    }

}
