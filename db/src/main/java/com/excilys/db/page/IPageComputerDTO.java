package com.excilys.db.page;

import java.util.List;

import com.excilys.db.dto.ComputerDTO;
import com.excilys.db.exception.CompaniesInexistantException;

public interface IPageComputerDTO {

    String getOrderBy();

    void setOrderBy(String orderBy);

    List<Integer> getNext();

    void setNext(List<Integer> next);

    List<Integer> getPrevious();

    void setPrevious(List<Integer> previous);

    List<Integer> getPagesToGo();

    void setPagesToGo(List<Integer> pagesToGo);

    void init(int page);

    void init(int page, int size);
    
    void init(int page, int size, String name);

    /**
     *
     * @return la liste des ComputerDTO
     */
    List<ComputerDTO> getPage();

    /**
     *
     * @param name le nom
     * @return la liste des ComputerDTO
     * @throws CompaniesInexistantException
     */
    List<ComputerDTO> getPage(String name);

    String getSortBy();

    void setSortBy(String sortBy);

}