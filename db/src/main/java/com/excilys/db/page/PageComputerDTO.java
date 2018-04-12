package com.excilys.db.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.mapper.ComputerMapper;
import com.excilys.db.service.IComputerService;
import com.excilys.db.utils.Debugging;

//TODO :remplacer les fonctions en mettant page en argument

@Component
public class PageComputerDTO extends Page implements IPageComputerDTO {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PageComputerDTO.class);
    private String search = "";
    private String sortBy = "computer.id";
    private String orderBy = "asc";
    @Autowired
    IComputerService computerService;

    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#getOrderBy()
     */
    @Override
    public String getOrderBy() {
        return orderBy;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#setOrderBy(java.lang.String)
     */
    @Override
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

    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#getNext()
     */
    @Override
    public List<Integer> getNext() {
        return next;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#setNext(java.util.List)
     */
    @Override
    public void setNext(List<Integer> next) {
        this.next = next;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#getPrevious()
     */
    @Override
    public List<Integer> getPrevious() {
        return previous;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#setPrevious(java.util.List)
     */
    @Override
    public void setPrevious(List<Integer> previous) {
        this.previous = previous;
    }

    private List<Integer> pagesToGo;
    private List<Integer> next;
    private List<Integer> previous;


    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#getPagesToGo()
     */
    @Override
    public List<Integer> getPagesToGo() {
        return pagesToGo;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#setPagesToGo(java.util.List)
     */
    @Override
    public void setPagesToGo(List<Integer> pagesToGo) {
        this.pagesToGo = pagesToGo;
    }

    /**
     *
     */
    public PageComputerDTO() {
        super();

    }
    
    public void init() {
        setComputerMax(computerService.getCount());
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
        search = "";
    }
    
    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#init(int)
     */
    @Override
    public void init(int page) {
        setComputerMax(computerService.getCount());
        if (page > 0) {
            this.pageNumber = page;
        } else {
            this.pageNumber = 1;
        }
        this.setPageMax(this.computerMax / pageSize + 1);
        initPages(page);
        search = "";
    }

    
    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#init(int, int)
     */
    @Override
    public void init(int page, int size) {
        setComputerMax(computerService.getCount());
        pageSizeInit(page, size);
        search = "";
    }

    
    @Override
    public void init(int page, int size, String name) {
        setComputerMax(computerService.getCount(name));
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



    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#getPage()
     */
    @Override
    public List<ComputerDTO> getPage() {
        if (search.isEmpty()) {
            return ComputerMapper.computerListToComputerDTO(computerService.listComputer(this));
        } else {
            return ComputerMapper.computerListToComputerDTO(computerService.listComputerLike((this.pageNumber - 1) * this.pageSize, this.pageSize, search, sortBy, orderBy));
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#getPage(java.lang.String)
     */
    @Override
    public List<ComputerDTO> getPage(String name) {
        return ComputerMapper.computerListToComputerDTO(computerService.listComputerLike((this.pageNumber - 1) * this.pageSize, this.pageSize, name));
    }

    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#getSortBy()
     */
    @Override
    public String getSortBy() {
        return sortBy;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.page.IPageComputerDTO#setSortBy(java.lang.String)
     */
    @Override
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }




}
