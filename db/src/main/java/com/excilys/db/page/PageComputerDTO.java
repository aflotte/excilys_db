package com.excilys.db.page;

import java.util.List;
import java.util.Scanner;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.model.Computer;
import com.excilys.db.service.ComputerService;

public class PageComputerDTO extends Page {


    List<ComputerDTO> computers;
    int index;
    static int NOMBRE_AFFICHAGE = 10;
    public Scanner sc;


    public PageComputerDTO() {
        super();
        setComputerMax(ComputerService.INSTANCE.getCount());
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

            String exit = sc.nextLine();
            if (exit.equals("Q")||exit.equals("q")){
                index = computers.size();
            }
            afficherNElements(NOMBRE_AFFICHAGE);
        }
    }
    
    /**
    *
    * @param offset l'offset
    * @param limit la limit
    * @return 
    * @throws CompaniesInexistantException
    */
   public List<Computer> getPage(int offset, int limit) {
       List<Computer> list = ComputerService.INSTANCE.listComputer(offset, limit);
       return list;
   }


}
