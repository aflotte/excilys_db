package com.excilys.db.controller;


import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Computer;
import com.excilys.db.page.IPageComputerDTO;
import com.excilys.db.service.ICompaniesService;
import com.excilys.db.service.IComputerService;
import com.excilys.db.validator.ComputerValidator;

@RestController
public class ComputerREST {
    private static final String ORDER_BY = "orderBy";
    private static final String ACTUAL_PAGE = "actualPage";
    private static final String PAGE_SIZE = "pageSize";
    private static final String SEARCH = "search";
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String DEFAULT_SEARCH = "";
    private static final String DEFAULT_SORT = "computer.id";
    private static final String DEFAULT_ORDER = "asc";
    

    private IPageComputerDTO pageComputer;
    private ICompaniesService companiesService;
    private IComputerService computerService;
    private ComputerMapper computerMapper;
    private ComputerValidator computerValidator;

    public ComputerREST(IPageComputerDTO pageComputer, ComputerValidator computerValidator, ICompaniesService companiesService, IComputerService computerService, ComputerMapper computerMapper) {
        this.pageComputer = pageComputer;
        this.computerValidator = computerValidator;
        this.computerMapper = computerMapper;
        this.companiesService = companiesService;
        this.computerService = computerService;
    }

    @RequestMapping("/computer/list")
    public List<ComputerDTO> handleGet(@RequestParam(value = ACTUAL_PAGE, defaultValue = DEFAULT_PAGE) int actualPage,
            @RequestParam(value = PAGE_SIZE, defaultValue = DEFAULT_PAGE_SIZE) int sizePage,
            @RequestParam(value = SEARCH, defaultValue = DEFAULT_SEARCH) String search,
            @RequestParam(value = "sort", defaultValue = DEFAULT_SORT) String toSort,
            @RequestParam(value = ORDER_BY, defaultValue = DEFAULT_ORDER) String orderBy) {
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
        return pageComputer.getPage();
    }
}
