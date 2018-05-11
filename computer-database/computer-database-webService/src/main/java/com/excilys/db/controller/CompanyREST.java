package com.excilys.db.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.db.dto.CompanyDTO;
import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.ValidatorException;
import com.excilys.db.mapper.CompaniesMapper;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Company;
import com.excilys.db.model.Computer;
import com.excilys.db.page.IPageComputerDTO;
import com.excilys.db.service.ICompaniesService;
import com.excilys.db.service.IComputerService;

@RestController
public class CompanyREST {
    private IPageComputerDTO pageComputer;
    private ICompaniesService companiesService;
    private IComputerService computerService;

    public CompanyREST(IPageComputerDTO pageComputer, ICompaniesService companiesService, IComputerService computerService) {
        this.pageComputer = pageComputer;
        this.companiesService = companiesService;
        this.computerService = computerService;
    }
    @GetMapping(value = "listCompany")
    protected List<CompanyDTO> getListComputers() {
        List<Company> liste = null;
        List<CompanyDTO> listeResult = new ArrayList<>();
        liste = companiesService.listCompanies();
        for(Company company : liste) {
            listeResult.add(CompaniesMapper.companyToDTO(company));
        }
        return listeResult;
    }
    
    
    @DeleteMapping(value = "company/delete/{id}")
    protected void delete(@PathVariable int id) {
        try {
            companiesService.destroy(id);
        } catch (ValidatorException e) {
            // TODO Auto-generated catch block
        }
    }
    
}
