package com.excilys.db.test;


import java.util.Date;
import java.util.List;

import com.excilys.db.DAO.CompaniesDAO;
import com.excilys.db.DAO.ComputerDAO;
import com.excilys.db.mapper.Companies;
import com.excilys.db.mapper.Computer;

public class test {
    public static void main(String[] args) {
    	System.out.println("Mise en route !");

    	CompaniesDAO companies = CompaniesDAO.getInstance();
    	List<Companies> companiesList = companies.listCompanies();
    	for (int i = 0; i < companiesList.size();i++) {
    		System.out.println(companiesList.get(i).getName());
    	}
    	ComputerDAO computers = ComputerDAO.getInstance();
    	List<Computer> computerList = computers.listComputer();
    	for (int i = 0; i < computerList.size();i++) {
    		System.out.println(computerList.get(i).getName());
    	}
    	Computer testComp = new Computer();
    	testComp.setCompanyId(1);
    	testComp.setName("Test");
    	testComp.setIntroduced(null);
    	testComp.setDiscontinued(new Date());
    	computers.createAComputer(testComp);
    	List<Integer> testList = computers.getId(testComp);
    	
    	for (int i = 0; i < testList.size(); i++) {
    		System.out.println("L'id de l'ordinateur ajouté est :" +testList.get(i) );
    		testComp.setName("7357");
    		computers.updateAComputer(testComp, testList.get(i));
    		Computer testDetails = computers.showDetails(testList.get(i));
    		System.out.println("testDetails = " + testDetails);
    		computers.deleteAComputer(testList.get(i));
    	}
    	/*
    	//clean
    	List<Integer> testList2 = computers.getIdFromName("Test");
    	for (int i = 0; i < testList2.size(); i++) {
    		computers.deleteAComputer(testList2.get(i));
    	}*/
    	System.out.println("Fin du test");
    }
}
