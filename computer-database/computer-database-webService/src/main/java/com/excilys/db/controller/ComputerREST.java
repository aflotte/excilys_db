package com.excilys.db.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.ServiceException;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Computer;
import com.excilys.db.page.IPageComputerDTO;
import com.excilys.db.service.ICompaniesService;
import com.excilys.db.service.IComputerService;
import com.excilys.db.validator.ComputerValidator;

@RestController
public class ComputerREST {
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerREST.class);

    private static final String DEFAULT_SORT = "computer.id";


    private IPageComputerDTO pageComputer;
    private ICompaniesService companiesService;
    private IComputerService computerService;

    public ComputerREST(IPageComputerDTO pageComputer, ICompaniesService companiesService, IComputerService computerService) {
        this.pageComputer = pageComputer;
        this.companiesService = companiesService;
        this.computerService = computerService;
    }

    @GetMapping(value = "listComputer")
    protected List<ComputerDTO> getListComputers() {
        List<Computer> liste = null;
        List<ComputerDTO> listeResult = new ArrayList<>();
        liste = computerService.listComputer();
        for(Computer computer : liste) {
            listeResult.add(ComputerMapper.computerToDTO(computer));
        }
        return listeResult;
    }

    @RequestMapping(value = {"/computer/list/{actualPage}/{sizePage}","/computer/list/{actualPage}/{sizePage}/{search}","/computer/list/{actualPage}/{sizePage}/{search}/{toSort}","/computer/list/{actualPage}/{sizePage}/{search}/{toSort}/{orderBy}"})
    public List<ComputerDTO> handleGet(@PathVariable int actualPage,
            @PathVariable int sizePage,
            @PathVariable Optional<String> search,
            @PathVariable Optional<String> toSort,
            @PathVariable Optional<String> orderBy) {
        if (sizePage < 1) {
            sizePage = 1;
        }
        String trueOrderBy = orderBy.isPresent() ? orderBy.get() : "";
        String trueToSort = toSort.isPresent() ? toSort.get() : "";
        String trueSearch = search.isPresent() ? search.get() : "";
        trueToSort = makeTrueToSort(trueToSort);
        if (!trueOrderBy.equals("DESC")) {
            trueOrderBy = "ASC";
        }
        if (trueSearch.equals("")) {
            pageComputer.init(actualPage, sizePage);
        }else {
            pageComputer.init(actualPage, sizePage, trueSearch);
        }
        pageComputer.setOrderBy(trueOrderBy);
        pageComputer.setSortBy(trueToSort);
        return pageComputer.getPage();
    }

    private String makeTrueToSort(String trueToSort) {
        switch (trueToSort) {
        case "name":
            trueToSort = "computer.name";
            break;
        case "introduced":
            trueToSort = "computer.introduced";
            break;
        case "discontinued":
            trueToSort = "computer.discontinued";
            break;
        case "company":
            trueToSort = "computer.company.name";
            break;
        default:
            trueToSort = DEFAULT_SORT;
            break;
        }
        return trueToSort;
    }

    @GetMapping(value = {"computer/Computer/{optionalId}"})
    protected ComputerDTO getComputer(@PathVariable Optional<Integer> optionalId) {
        ComputerDTO computerDto = null;
        Optional<Computer> optionalComputer;
        int id = 0;
        if (optionalId.isPresent()) {
            id = optionalId.get();
        }
        try {
            optionalComputer = computerService.showDetails(id);
            if (optionalComputer.isPresent()) {
                computerDto = ComputerMapper.computerToDTO(optionalComputer.get());
            }
        } catch (ServiceException e) {
            logger.debug("Problem in webService in get computer by id {}", e);
        }
        return computerDto;
    }

    @PostMapping(value= "computer/addComputer")
    protected void addComputer(@RequestBody ComputerDTO computerDTO) {
        Computer computer = ComputerMapper.computerDTOToComputer(computerDTO,companiesService);
        try {
            computerService.createComputer(computer);
        } catch (ServiceException e) {
            logger.debug(e.getMessage());
        }

    }

    @PutMapping(value= "computer/updateComputer")
    protected void updateComputer(@RequestBody ComputerDTO computerDTO) {
        Computer computer = ComputerMapper.computerDTOToComputer(computerDTO,companiesService);
        try {
            computerService.updateAComputer(computer,computer.getId());
        } catch (ServiceException e) {
            logger.debug(e.getMessage());
        }
    }

    @DeleteMapping(value = "computer/delete/{id}")
    protected void delete(@PathVariable int id) {
        computerService.deleteComputer(id);
    }


    @DeleteMapping(value = "computer/deleteList")
    protected void deleteList(@RequestBody List<Integer> ids) {
        computerService.deleteListComputer(ids);
    }

}
