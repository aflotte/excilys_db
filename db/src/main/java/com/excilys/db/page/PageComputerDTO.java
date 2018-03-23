package com.excilys.db.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.DBConnection;
import com.excilys.db.service.ComputerService;

public class PageComputerDTO extends Page {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PageComputerDTO.class);


    List<ComputerDTO> computers;
    int index;
    static int NOMBRE_AFFICHAGE = 10;
    public Scanner sc;
    public List<Integer> pagesToGo;


    public List<Integer> getPagesToGo() {
        return pagesToGo;
    }

    public void setPagesToGo(List<Integer> pagesToGo) {
        this.pagesToGo = pagesToGo;
    }

    public PageComputerDTO() {
        super();
        setComputerMax(ComputerService.INSTANCE.getCount());
        this.pageNumber = 1;
        this.setPageMax(this.computerMax/pageSize + 1);
        logger.debug(Integer.valueOf(this.getPageMax()).toString());
        pagesToGo = new ArrayList<Integer>();
        pagesToGo.add(1);
        for (int i = -3; i < 3; i++) {
            if ((this.pageNumber + i > this.pageMin)&&(i!=0)&&(this.pageNumber + i < this.pageMax)) {
                pagesToGo.add(this.pageNumber + i);
            }
        }
        pagesToGo.add(this.pageMax);
    }
    
    public PageComputerDTO(int page) {
        super();
        setComputerMax(ComputerService.INSTANCE.getCount());
        if (page > 0) {
            this.pageNumber = page;
        }else {
            this.pageNumber = 1;
        }
        this.setPageMax(this.computerMax/pageSize + 1);
        pagesToGo = new ArrayList<Integer>();
        pagesToGo.add(1);
        for (int i = -3; i < 3; i++) {
            if ((this.pageNumber + i > this.pageMin)&&(i!=0)&&(this.pageNumber + i < this.pageMax)) {
                pagesToGo.add(this.pageNumber + i);
            }
        }
        pagesToGo.add(this.pageMax);
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
   public List<ComputerDTO> getPage() {
       List<ComputerDTO> list = ComputerMapper.computerListToComputerDTO(ComputerService.INSTANCE.listComputer((this.pageNumber-1)*this.pageSize, this.pageSize));
       return list;
   }


}
