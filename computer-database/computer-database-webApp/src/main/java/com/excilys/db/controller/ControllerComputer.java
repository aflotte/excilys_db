package com.excilys.db.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.excilys.db.page.IPageComputerDTO;
import com.excilys.db.service.ICompaniesService;
import com.excilys.db.service.IComputerService;
import com.excilys.db.validator.ComputerValidator;

/**
 * Servlet implementation class GetComputer.
 */

@Controller
public class ControllerComputer {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ControllerComputer.class);
    private static final String ORDER_BY = "orderBy";
    private static final String ACTUAL_PAGE = "actualPage";
    private static final String PAGE_SIZE = "pageSize";
    private static final String SEARCH = "search";
    private static final String SORT_PATH = "sortPath";
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String DEFAULT_SEARCH = "";
    private static final String DEFAULT_SORT = "computer.id";
    private static final String DEFAULT_ORDER = "asc";
    private static final String SELECTION = "selection";
    private static final String DEFAULT_VALUE = "";
    private static final String DASHBOARD = "redirect:/dashboard";
    private static final String COMPUTER = "computer";

    private IPageComputerDTO pageComputer;
    private ICompaniesService companiesService;
    private IComputerService computerService;
    private ComputerMapper computerMapper;
    private ComputerValidator computerValidator;

    public ControllerComputer(IPageComputerDTO pageComputer, ComputerValidator computerValidator, ICompaniesService companiesService, IComputerService computerService, ComputerMapper computerMapper) {
        this.pageComputer = pageComputer;
        this.computerValidator = computerValidator;
        this.computerMapper = computerMapper;
        this.companiesService = companiesService;
        this.computerService = computerService;
    }

    @GetMapping("/dashboard")
    public ModelAndView handleGet(@RequestParam(value = ACTUAL_PAGE, defaultValue = DEFAULT_PAGE) int actualPage,
            @RequestParam(value = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE) int sizePage,
            @RequestParam(value = SEARCH, defaultValue = DEFAULT_SEARCH) String search,
            @RequestParam(value = "sort", defaultValue = DEFAULT_SORT) String toSort,
            @RequestParam(value = ORDER_BY, defaultValue = DEFAULT_ORDER) String orderBy) {
        ModelAndView modelAndView = new ModelAndView("index");
        String[] preparedSort = prepareSort(toSort,orderBy,modelAndView);
        toSort = preparedSort[0];
        orderBy = preparedSort[1];
        if (sizePage < 10) {
            sizePage = 10;
        }
        String searchJSP;
        if (search.equals("")) {
            searchJSP = "";
            pageComputer.init(actualPage, sizePage);
        }else {
            searchJSP = "&search=" + search;
            pageComputer.init(actualPage, sizePage, search);
        }
        pageComputer.setSortBy(toSort);
        pageComputer.setOrderBy(orderBy);
        modelAndView.addObject(SEARCH, searchJSP);
        modelAndView.addObject("page", pageComputer);
        return modelAndView;
    }

    @PostMapping("/dashboard")
    public ModelAndView handlePost() {
        return new ModelAndView("index");
    }

    @GetMapping("/addComputer")
    public String handleGet(ModelMap model) {
        model.addAttribute("companies", companiesService.listCompanies());
        model.addAttribute(COMPUTER, new ComputerDTO());
        return "addComputer";
    }

    @PostMapping("/addComputer")
    public String handlePost(ModelMap model,  @ModelAttribute("computer") ComputerDTO computerDTO) {
        Computer computer = computerMapper.computerDTOToComputer(computerDTO, companiesService);
        try {
            computerService.createComputer(computer);
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
        }
        return handleGet(model);
    }

    @GetMapping("/delete")
    public ModelAndView handleGet(@RequestParam(value = SELECTION, defaultValue = DEFAULT_VALUE) String toDeleteString) {
        logger.warn(toDeleteString);
        if (!toDeleteString.equals(DEFAULT_VALUE)) {
            String[] toDelete = toDeleteString.split(",");
            List<Integer> toDeleteId = new ArrayList<>();
            for (int i = 0; i < toDelete.length; i++) {
                logger.warn(toDelete[i]);
                try {
                    toDeleteId.add(Integer.parseInt(toDelete[i]));
                } catch (Exception e) {
                    logger.debug(e.getMessage());
                }
            }
            computerService.deleteListComputer(toDeleteId);
        }
        return new ModelAndView(DASHBOARD);
    }

    @PostMapping("/delete")
    public ModelAndView handlePost(@RequestParam(value = SELECTION, defaultValue = DEFAULT_VALUE) String toDeleteString) {
        return handleGet(toDeleteString);
    }

    public List<ComputerDTO> getComputer() {
        return ComputerMapper.computerListToComputerDTO(computerService.listComputer());
    }
    
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
        modelAndView.addObject(COMPUTER, computerDTO);
        modelAndView.addObject("companies", companiesService.listCompanies());
        return modelAndView;
    }
    
    @PostMapping("/editComputer")
    public String handlePost(ModelMap model, @ModelAttribute("computerDTO") ComputerDTO computerDTO, @RequestParam(value = "id", defaultValue = "0") int id) {
        try {
            Computer computer = computerMapper.computerDTOToComputer(computerDTO, companiesService);
            computerService.updateAComputer(computer, id);
        } catch (ServiceException e) {
            logger.warn(e.getMessage());
            return DASHBOARD;
        }
        return DASHBOARD;
    }
    
    
    /**
     *
     * @param request la requete
     * @param tested la colonne servant pour sort
     * @return la string de l'url
     */
    private String casePrepareSort(ModelAndView modelAndView, String tested, String orderBy) {
        String preparation = MessageFormat.format("&sort={0}", tested);
        if (orderBy.equals("asc")) {
            modelAndView.addObject(MessageFormat.format("{0}Path",tested), MessageFormat.format("&sort={0}&orderBy=desc", tested));
        }
        return preparation;
    }

    /**
     *
     * @param modelAndView 
     * @param request la requete
     * @return { Sort, OrderBy }
     */
    private String[] prepareSort(String toSort, String orderBy, ModelAndView modelAndView) {
        String preparation = "";
        initModelAndViewForPrepareSort(modelAndView);
        if (!toSort.equals(DEFAULT_SORT)) {
            switch (toSort) {
            case COMPUTER:
                preparation = casePrepareSort(modelAndView, COMPUTER, orderBy);
                toSort = "computer.name";
                break;
            case "introduced":
                preparation = casePrepareSort(modelAndView, "introduced", orderBy);
                toSort = "computer.introduced";
                break;
            case "discontinued":
                preparation = casePrepareSort(modelAndView, "discontinued", orderBy);
                toSort = "computer.discontinued";
                break;
            case "company":
                preparation = casePrepareSort(modelAndView, "company", orderBy);
                toSort = "company.name";
                break;
            default:
                toSort = DEFAULT_SORT;
                break;
            }
        }
        if (!preparation.isEmpty()) {
            if (orderBy.equals("desc")) {
                preparation += "&orderBy=desc";
                orderBy = "desc";
            } else {
                preparation += "&orderBy=asc";
                orderBy = "asc";
            }
        } else {
            orderBy = "asc";
        }
        modelAndView.addObject(SORT_PATH, preparation);
        return new String[]{toSort, orderBy};
    }

    private void initModelAndViewForPrepareSort(ModelAndView modelAndView) {
        modelAndView.addObject("computerPath", "&sort=computer&orderBy=asc");
        modelAndView.addObject("introducedPath", "&sort=introduced&orderBy=asc");
        modelAndView.addObject("discontinuedPath", "&sort=discontinued&orderBy=asc");
        modelAndView.addObject("companyPath", "&sort=company&orderBy=asc");
        modelAndView.addObject(SORT_PATH, "");
    }

}
