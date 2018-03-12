package com.excilys.db.test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.excilys.db.DAO.CompaniesDAO;
import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.mapper.Companies;
import com.excilys.db.mapper.Computer;
import com.excilys.db.persistance.DB_Connection;

public class test {
    public static void main(String[] args) {
    	System.out.println("Mise en route !");
    	DB_Connection conn = DB_Connection.getInstance();

    	CompaniesDAO companies = new CompaniesDAO();
    	List<Companies> companiesList = companies.listCompanies();
    	for (int i = 0; i < companiesList.size();i++) {
    		System.out.println(companiesList.get(i).getName());
    	}
    	ComputerDAO computers = new ComputerDAO();
    	List<Computer> computerList = computers.listComputer();
    	for (int i = 0; i < computerList.size();i++) {
    		System.out.println(computerList.get(i).getName());
    	}
    	Computer testComp = new Computer();
    	testComp.setCompanyId(1);
    	testComp.setName("Test");
    	testComp.setIntroduced(new Date());
    	testComp.setDiscontinued(new Date());
    	int id  = computers.createAComputer(testComp);
    	System.out.println(id);
    	//computers.deleteAComputer(id);
    	System.out.println("Fin du test");
    }
}
