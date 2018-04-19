package com.excilys.db.servlet;

import java.util.Optional;

import javax.servlet.http.HttpServlet;

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
import com.excilys.db.validator.ComputerValidator;


@Controller
@RequestMapping(value = "/editComputer")
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

    
    @GetMapping
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
    
    @PostMapping
    public ModelAndView handlePost(@RequestParam(value = "id", defaultValue = "0") int id,
            @RequestParam(value = "computerName", defaultValue = "") String name,
            @RequestParam(value = "introduced", defaultValue = "")  String introduced,
            @RequestParam(value = "discontinued", defaultValue = "") String discontinued,
            @RequestParam(value = "companyId", defaultValue = "") String companyId) {
        Computer computer = postComputer(name,introduced,discontinued,companyId);
        try {
            computerService.updateAComputer(computer, id);
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
            return new ModelAndView(DASHBOARD);
        }
        return new ModelAndView(DASHBOARD);
    }

    /**
     *
     * @param request la requete
     * @return l'ordinateur construit grâce à elle
     */
    private Computer postComputer(String name, String introduced, String discontinued, String CompanyId) {
        ComputerDTO computerDTO = new ComputerDTO();

        computerDTO.setName(name);
        computerDTO.setIntroduced(introduced);
        computerDTO.setDiscontinued(discontinued);
        computerDTO.setCompany(CompanyId);
        return computerMapper.computerDTOToComputer(computerDTO);
    }

}
