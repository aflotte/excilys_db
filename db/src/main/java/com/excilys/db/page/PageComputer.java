package com.excilys.db.page;

import java.util.List;
import java.util.Scanner;

import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.model.Computer;
import com.excilys.db.service.ComputerService;


public class PageComputer extends Page{

    List<Computer> computers;
    int index;
    public Scanner sc;
    
    public PageComputer() {
        super();
    }

    /**
     *
     * @param list des ordinateurs
     * @param sc le scanner
     */
    public PageComputer(List<Computer> list, Scanner sc) {
        computers = list;
        index = 0;
        this.sc = sc;
    }

    /**
     *
     * @param n le nombre d'element a afficher
     */
    public void afficherNElements(int n) {
        int end = index + n;
        for (int i = index; i < Math.min(end, computers.size()); i++) {
            System.out.println("Ordinateur " + (i + 1) + computers.get(i).toString());
            index++;
        }

    }

    /**
     *
     */
    public void afficher() {
        while (index < computers.size()) {
            String exit = sc.nextLine();
            if (exit.equals("Q") || exit.equals("q")) {
                index = computers.size();
            }
            afficherNElements(this.pageSize);
        }
    }


    /**
     *
     * @param offset l'offset
     * @param limit la limit
     * @return 
     * @throws CompaniesInexistantException
     */
    public List<Computer> getPage(int offset, int limit, String sortBy, String orderBy) {
        List<Computer> list = ComputerService.INSTANCE.listComputer(offset, limit, sortBy, orderBy);
        return list;
    }

    /**
     *
     */
    public void updateComputer() {
        computers = ComputerService.INSTANCE.listComputer(this.pageNumber,this.pageSize);
    }

}