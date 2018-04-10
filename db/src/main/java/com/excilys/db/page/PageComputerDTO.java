package com.excilys.db.page;

import java.util.ArrayList;
import java.util.List;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.CompaniesInexistantException;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.service.ComputerService;
import com.excilys.db.utils.Debugging;

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
    static int nombreAffichage = 10;
    public static int getNombreAffichage() {
        return nombreAffichage;
    }

    public static void setNombreAffichage(int toFix) {
        nombreAffichage = toFix;
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

    private List<Integer> pagesToGo;
    private List<Integer> next;
    private List<Integer> previous;


    public List<Integer> getPagesToGo() {
        return pagesToGo;
    }

    public void setPagesToGo(List<Integer> pagesToGo) {
        this.pagesToGo = pagesToGo;
    }

    /**
     *
     */
    public PageComputerDTO() {
        super();
        setComputerMax(ComputerService.INSTANCE.getCount());
        this.pageNumber = 1;
        this.setPageMax(this.computerMax / pageSize + 1);
        Debugging.simpleDebugInt(logger, "PageMax : {0}", this.getPageMax());
        pagesToGo = new ArrayList<>();
        pagesToGo.add(1);
        for (int i = -3; i < 3; i++) {
            if ((this.pageNumber + i > this.pageMin) && (i != 0) && (this.pageNumber + i < this.pageMax)) {
                pagesToGo.add(this.pageNumber + i);
            }
        }
        pagesToGo.add(this.pageMax);
        this.previous = new ArrayList<>();
        this.next = new ArrayList<>();
        this.next.add(2);
    }

    /**
     *
     * @param page numéro de page
     */
    public PageComputerDTO(int page) {
        super();
        setComputerMax(ComputerService.INSTANCE.getCount());
        if (page > 0) {
            this.pageNumber = page;
        } else {
            this.pageNumber = 1;
        }
        this.setPageMax(this.computerMax / pageSize + 1);
        initPages(page);
    }

    /**
     *
     * @param page numéro de page
     * @param size taille de la page
     */
    public PageComputerDTO(int page, int size) {
        super();
        setComputerMax(ComputerService.INSTANCE.getCount());
        pageSizeInit(page, size);
    }

    /**
     *
     * @param page numéro de la page
     * @param size taille de la page
     * @param name nom de la recherche
     */
    public PageComputerDTO(int page, int size, String name) {
        super();
        setComputerMax(ComputerService.INSTANCE.getCount(name));
        pageSizeInit(page, size);
        search = name;
    }


    /**
     *
     * @param page numéro de page
     * @param size taille de la page
     */
    private void pageSizeInit(int page, int size) {
        this.setPageSize(size);
        this.setPageMax(this.computerMax / pageSize + 1);
        if (page > 0) {
            if (page < this.getPageMax()) {
                this.pageNumber = page;
            } else {
                this.pageNumber = this.getPageMax();
            }
        } else {
            this.pageNumber = 1;
        }
        initPages(page);
    }

    /**
     *
     * @param page numéro de la page
     */
    private void initPages(int page) {
        pagesToGo = new ArrayList<>();
        pagesToGo.add(1);
        for (int i = -3; i < 3; i++) {
            if ((this.pageNumber + i > this.pageMin) && (i != 0) && (this.pageNumber + i < this.pageMax)) {
                pagesToGo.add(this.pageNumber + i);
            }
        }
        pagesToGo.add(this.pageMax);
        this.previous = new ArrayList<>();
        if (page > pageMin) {
            this.previous.add(page - 1);
        }
        this.next = new ArrayList<>();
        if (page < pageMax) {
            this.next.add(page + 1);
        }
    }



    /**
     *
     * @return la liste des ComputerDTO
     */
    public List<ComputerDTO> getPage() {
        if (search.isEmpty()) {
            return ComputerMapper.computerListToComputerDTO(ComputerService.INSTANCE.listComputer((this.pageNumber - 1) * this.pageSize, this.pageSize, sortBy, orderBy));
        } else {
            return ComputerMapper.computerListToComputerDTO(ComputerService.INSTANCE.listComputerLike((this.pageNumber - 1) * this.pageSize, this.pageSize, search, sortBy, orderBy));
        }
    }

    /**
     *
     * @param name le nom
     * @return la liste des ComputerDTO
     * @throws CompaniesInexistantException
     */
    public List<ComputerDTO> getPage(String name) {
        return ComputerMapper.computerListToComputerDTO(ComputerService.INSTANCE.listComputerLike((this.pageNumber - 1) * this.pageSize, this.pageSize, name));
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }


}
