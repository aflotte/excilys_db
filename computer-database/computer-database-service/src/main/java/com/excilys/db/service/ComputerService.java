package com.excilys.db.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.db.exception.CompaniesIdIncorrectException;
import com.excilys.db.exception.DAOAccesExeption;
import com.excilys.db.exception.IncoherentDatesException;
import com.excilys.db.exception.ServiceException;
import com.excilys.db.exception.ValidatorException;
import com.excilys.db.model.Computer;
import com.excilys.db.persistance.IComputerDAO;
import com.excilys.db.validator.ComputerValidator;

import page.PageComputerInterface;

@Service("computerService")
public class ComputerService implements IComputerService {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerService.class);
    @Autowired
    private IComputerDAO computer;
    @Autowired
    private ComputerValidator computerValidator;

    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#listComputer()
     */
    @Override
    public List<Computer> listComputer() {
        return computer.listComputer();
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#listComputer(int, int, java.lang.String, java.lang.String)
     */
    @Override
    public List<Computer> listComputer(int offset, int limit, String sortBy, String orderBy) {
        return computer.listComputer(offset, limit, sortBy, orderBy);
    }

    
    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#listComputer(com.excilys.db.page.PageComputerDTO)
     */
    @Override
    public List<Computer> listComputer(PageComputerInterface page) {
        int offset = (page.getPageNumber() - 1) * page.getPageSize();
        int limit = page.getPageSize();
        String sortBy = page.getSortBy();
        String orderBy = page.getOrderBy();
        return listComputer(offset,limit,sortBy,orderBy);
    }
    
    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#listComputer(int, int)
     */
    @Override
    public List<Computer> listComputer(int offset, int limit) {
        return computer.listComputer(offset, limit, "computer.id", "asc");
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#createComputer(com.excilys.db.model.Computer)
     */
    @Override
    public int createComputer(Computer aAjouter) throws ServiceException {
        try {
            if (computerValidator.validate(aAjouter)) {
                return computer.createAComputer(aAjouter);
            }
        } catch (IncoherentDatesException | CompaniesIdIncorrectException | DAOAccesExeption e) {
            logger.warn(e.getMessage());
            throw new ServiceException();
        }
        return 0;
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#deleteComputer(int)
     */
    @Override
    public void deleteComputer(int id) {
        computer.deleteAComputer(id);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#showDetails(int)
     */
    @Override
    public Optional<Computer> showDetails(int id) throws ServiceException {
        try {
            return computer.showDetails(id);
        } catch (DAOAccesExeption e) {
            logger.warn(e.getMessage());
            throw new ServiceException();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#updateAComputer(com.excilys.db.model.Computer, int)
     */
    @Override
    public void updateAComputer(Computer aAjouter, int toUpdate) throws ServiceException {
        try {
            if (computerValidator.validate(aAjouter)) {
                computer.updateAComputer(aAjouter, toUpdate);
            }
        } catch (IncoherentDatesException | CompaniesIdIncorrectException | DAOAccesExeption e) {
            logger.warn(e.getMessage());
            throw new ServiceException();
        }
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#getCount()
     */
    @Override
    public int getCount() {
        return computer.getCount();
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#getCount(java.lang.String)
     */
    @Override
    public int getCount(String search) {
        return computer.getCount(search);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#listComputerLike(int, int, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<Computer> listComputerLike(int offset, int limit, String name, String sortBy, String orderBy) {
        return computer.listComputerLike(offset, limit, name, sortBy, orderBy);
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#listComputerLike(int, int, java.lang.String)
     */
    @Override
    public List<Computer> listComputerLike(int offset, int limit, String name) {
        return computer.listComputerLike(offset, limit, name, "computer.id", "asc");
    }

    /* (non-Javadoc)
     * @see com.excilys.db.service.IComputerService#deleteListComputer(int[])
     */
    @Override
    public void deleteListComputer(List<Integer> ids) {
        computer.deleteListComputer(ids);
    }
}