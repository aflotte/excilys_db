package com.excilys.db.servlet;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.ServiceException;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Computer;
import com.excilys.db.service.ICompaniesService;
import com.excilys.db.service.IComputerService;

@Controller
public class AddComputer {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AddComputer.class);

    public AddComputer(ICompaniesService companiesService, IComputerService computerService, ComputerMapper computerMapper) {
        this.companiesService = companiesService;
        this.computerService = computerService;
        this.computerMapper = computerMapper;
    }

    private ICompaniesService companiesService;
    private IComputerService computerService;
    private ComputerMapper computerMapper;

    @GetMapping("/addComputer")
    public String handleGet(ModelMap model) {
        model.addAttribute("companies", companiesService.listCompanies());
        model.addAttribute("computer", new ComputerDTO());
        return "addComputer";
    }

    @PostMapping("/addComputer")
    public String handlePost(ModelMap model,  @ModelAttribute("computer") ComputerDTO computerDTO) {
        Computer computer = computerMapper.computerDTOToComputer(computerDTO);
        try {
            computerService.createComputer(computer);
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
        }
        return handleGet(model);
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    /*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            doGet(request, response);
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }
     */
    public List<ComputerDTO> getComputer() {

        return ComputerMapper.computerListToComputerDTO(computerService.listComputer());
    }
}
