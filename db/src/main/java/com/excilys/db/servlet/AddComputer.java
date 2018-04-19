package com.excilys.db.servlet;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.ServiceException;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Computer;
import com.excilys.db.service.ICompaniesService;
import com.excilys.db.service.IComputerService;

@Controller
@RequestMapping(value = "/addComputer")
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

    @GetMapping
    public ModelAndView handleGet() {
        ModelAndView modelAndView = new ModelAndView("addComputer");
        modelAndView.addObject("companies", companiesService.listCompanies());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView handlePost(@RequestParam(value = "computerName", defaultValue = "") String computerName,
            @RequestParam(value = "introduced", defaultValue = "") String introduced,
            @RequestParam(value = "discontinued", defaultValue = "") String discontinued,
            @RequestParam(value = "companyId", defaultValue = "") String companyId) {
        ComputerDTO computerDTO = new ComputerDTO();
        computerDTO.setName(computerName);
        computerDTO.setIntroduced(introduced);
        computerDTO.setDiscontinued(discontinued);
        computerDTO.setCompany(companyId);
        Computer computer = computerMapper.computerDTOToComputer(computerDTO);
        try {
            computerService.createComputer(computer);
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
        }
        return handleGet();
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
