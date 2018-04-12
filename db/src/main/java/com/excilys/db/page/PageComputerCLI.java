package com.excilys.db.page;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.model.Computer;
import com.excilys.db.service.IComputerService;


@Component
public class PageComputerCLI extends Page {

    List<Computer> computers;
    int index;
    private Scanner sc;
    @Autowired
    IComputerService computerService;

    /**
     *
     */
    public PageComputerCLI() {
        super();
    }

    /**
     *
     * @param list des ordinateurs
     * @param sc le scanner
     */
    public PageComputerCLI(List<Computer> list, Scanner sc) {
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
     * @param sortBy sortBy
     * @param orderBy l'ordre
     * @return la liste des ordinateurs
     * @throws CompaniesInexistantException
     */
    public List<Computer> getPage(int offset, int limit, String sortBy, String orderBy) {
        return computerService.listComputer(offset, limit, sortBy, orderBy);
    }

    /**
     *
     */
    public void updateComputer() {
        computers = computerService.listComputer(this.pageNumber, this.pageSize);
    }

}