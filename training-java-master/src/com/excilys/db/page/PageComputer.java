package com.excilys.db.page;

import com.excilys.db.mapper.Computer;


import java.util.List;
import java.util.Scanner;


public class PageComputer {

	List<Computer> computers;
	int index;
	static final int NOMBRE_AFFICHAGE = 10;
	public Scanner sc;
	
	
	public PageComputer(List<Computer> list, Scanner sc) {
		computers = list;
		index = 0;
		this.sc = sc;
	}
	
	public void afficherNElements(int n) {
		int end = index + n;
		for (int i = index; i < Math.min(end,computers.size());i++) {
			System.out.println("Ordinateur " + (i+1) + computers.get(i).toString());
			index ++;
		}
		
	}
	
	public void afficher() {
		while (index < computers.size()) {
			afficherNElements(NOMBRE_AFFICHAGE);
			String exit = sc.nextLine();
			if (exit.equals("Q")||exit.equals("q")){
				index = computers.size();
			}
		}
	}
	

	
}
