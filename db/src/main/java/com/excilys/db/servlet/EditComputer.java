package com.excilys.db.servlet;

import java.util.Optional;

import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.ServiceException;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Computer;
import com.excilys.db.service.ICompaniesService;
import com.excilys.db.service.IComputerService;
import com.excilys.db.validator.ComputerValidator;


@Controller
public class EditComputer {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EditComputer.class);
    private static final String DASHBOARD = "redirect:/dashboard";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer(ComputerValidator computerValidator, ICompaniesService companiesService, IComputerService computerService, ComputerMapper computerMapper) {
        this.computerValidator = computerValidator;
        this.companiesService = companiesService;
        this.computerService = computerService;
        this.computerMapper = computerMapper;
    }

    private ComputerValidator computerValidator;
    private ICompaniesService companiesService;
    private IComputerService computerService;
    private ComputerMapper computerMapper;

    
    @GetMapping("/editComputer")
    public ModelAndView handleGet(@RequestParam(value = "id", defaultValue = "0") int id) {
        if ((id == 0)||(!computerValidator.exist(id))) {
            return new ModelAndView(DASHBOARD);
        }
        ComputerDTO computerDTO = null;
        try {
            Optional<Computer> computer = computerService.showDetails(id);
            if (computer.isPresent()) {
                computerDTO = ComputerMapper.computerToDTO(computer.get());
            }
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
            return new ModelAndView(DASHBOARD);
        }
        ModelAndView modelAndView = new ModelAndView("editComputer");
        modelAndView.addObject("id", id);
        modelAndView.addObject("computer", computerDTO);
        modelAndView.addObject("companies", companiesService.listCompanies());
        return modelAndView;
    }
    
    @PostMapping("/editComputer")
    public String handlePost(ModelMap model, @ModelAttribute("computerDTO") ComputerDTO computerDTO, @RequestParam(value = "id", defaultValue = "0") int id) {
        try {
            Computer computer = computerMapper.computerDTOToComputer(computerDTO);
            computerService.updateAComputer(computer, id);
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
            return DASHBOARD;
        }
        return DASHBOARD;
    }


}
