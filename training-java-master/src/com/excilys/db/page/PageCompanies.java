package com.excilys.db.page;

import java.util.List;
import java.util.Scanner;

import com.excilys.db.mapper.Companies;


public class PageCompanies {
	List<Companies> companies;
	int index;
	static final int NOMBRE_AFFICHAGE = 10;
	public Scanner sc;
	
	public PageCompanies(List<Companies> list,Scanner sc) {
		companies = list;
		index = 0;
		this.sc = sc;
	}
	
	public void afficherNElements(int n) {
		int end = index + n;
		for (int i = index; i < Math.min(end,companies.size());i++) {
			System.out.println("Compagnie " + (i+1) + companies.get(i).toString());
			index ++;
		}
		
	}
	
	
	
	
	public void afficher() {
		while (index < companies.size()) {
			
			String exit = sc.nextLine();
			if (exit.equals("Q")||exit.equals("q")){
				index = companies.size();
			}
			afficherNElements(NOMBRE_AFFICHAGE);
			
		}
	}
	

}
