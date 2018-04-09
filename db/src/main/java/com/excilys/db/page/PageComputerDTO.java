package com.excilys.db.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.service.ComputerService;

public class PageComputerDTO extends Page {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PageComputerDTO.class);
    private String search = "";
    private String sortBy = "computer.id";
    private String orderBy = "asc";

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    List<ComputerDTO> computers;
    int index;
    static int NOMBRE_AFFICHAGE = 10;
    public static int getNOMBRE_AFFICHAGE() {
        return NOMBRE_AFFICHAGE;
    }

    public static void setNOMBRE_AFFICHAGE(int nOMBRE_AFFICHAGE) {
        NOMBRE_AFFICHAGE = nOMBRE_AFFICHAGE;
    }

    public List<Integer> getNext() {
        return next;
    }

    public void setNext(List<Integer> next) {
        this.next = next;
    }

    public List<Integer> getPrevious() {
        return previous;
    }

    public void setPrevious(List<Integer> previous) {
        this.previous = previous;
    }

    public Scanner sc;
    public List<Integer> pagesToGo;
    public List<Integer> next;
    public List<Integer> previous;


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
        this.previous = new ArrayList<Integer>();
        this.next = new ArrayList<Integer>();
        this.next.add(2);
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
        this.previous = new ArrayList<Integer>();
        if (page > pageMin) {
            this.previous.add(page - 1);
        }
        this.next = new ArrayList<Integer>();
        if (page < pageMax) {
            this.next.add(page + 1);
        }
    }

    public PageComputerDTO(int page, int size) {
        super();
        setComputerMax(ComputerService.INSTANCE.getCount());
        this.setPageSize(size);
        this.setPageMax(this.computerMax/pageSize + 1);
        if (page > 0) {
            if (page < this.getPageMax()) {
                this.pageNumber = page;
            }else {
                this.pageNumber = this.getPageMax();
            }
        }else {
            this.pageNumber = 1;
        }

        pagesToGo = new ArrayList<Integer>();
        pagesToGo.add(1);
        for (int i = -3; i < 3; i++) {
            if ((this.pageNumber + i > this.pageMin)&&(i!=0)&&(this.pageNumber + i < this.pageMax)) {
                pagesToGo.add(this.pageNumber + i);
            }
        }
        pagesToGo.add(this.pageMax);
        this.previous = new ArrayList<Integer>();
        if (page > pageMin) {
            this.previous.add(page - 1);
        }
        this.next = new ArrayList<Integer>();
        if (page < pageMax) {
            this.next.add(page + 1);
        }
    }

    public PageComputerDTO(int page, int size, String name) {
        super();
        //TODO: count pour recherche
        setComputerMax(ComputerService.INSTANCE.getCount(name));
        this.setPageSize(size);
        this.setPageMax(this.computerMax/pageSize + 1);
        if (page > 0) {
            if (page < this.getPageMax()) {
                this.pageNumber = page;
            }else {
                this.pageNumber = this.getPageMax();
            }
        }else {
            this.pageNumber = 1;
        }

        pagesToGo = new ArrayList<Integer>();
        pagesToGo.add(1);
        for (int i = -3; i < 3; i++) {
            if ((this.pageNumber + i > this.pageMin)&&(i!=0)&&(this.pageNumber + i < this.pageMax)) {
                pagesToGo.add(this.pageNumber + i);
            }
        }
        pagesToGo.add(this.pageMax);
        this.previous = new ArrayList<Integer>();
        if (page > pageMin) {
            this.previous.add(page - 1);
        }
        this.next = new ArrayList<Integer>();
        if (page < pageMax) {
            this.next.add(page + 1);
        }
        search = name;
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
     * @return la liste des ComputerDTO
     * @throws CompaniesInexistantException
     */
    public List<ComputerDTO> getPage() {
        if (search.isEmpty()) {
            List<ComputerDTO> list = ComputerMapper.computerListToComputerDTO(ComputerService.INSTANCE.listComputer((this.pageNumber - 1) * this.pageSize, this.pageSize, sortBy, orderBy));
            return list;
        }else {
            List<ComputerDTO> list = ComputerMapper.computerListToComputerDTO(ComputerService.INSTANCE.listComputerLike((this.pageNumber - 1) * this.pageSize, this.pageSize, search, sortBy, orderBy));
            return list;
        }
    }

    /**
     *
     * @param offset l'offset
     * @param limit la limit
     * @return la liste des ComputerDTO
     * @throws CompaniesInexistantException
     */
    public List<ComputerDTO> getPage(String name) {
        List<ComputerDTO> list = ComputerMapper.computerListToComputerDTO(ComputerService.INSTANCE.listComputerLike((this.pageNumber - 1) * this.pageSize, this.pageSize, name));
        return list;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }


}
