package com.excilys.db.test;

import java.util.List;

import com.excilys.db.DAO.CompaniesDAO;
import com.excilys.db.mapper.Companies;
import com.excilys.db.persistance.DB_Connection;

public class test {
    public static void main(String[] args) {
    	System.out.println("Mise en route !");
    	DB_Connection conn = DB_Connection.getInstance();

    	CompaniesDAO comp = new CompaniesDAO();
    	List<Companies> l = comp.listCompanies();
    	for (int i = 0; i < l.size();i++) {
    		System.out.println(l.get(i));
    	}
    	System.out.println("Fin du test");
    }
}
