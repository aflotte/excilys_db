package com.excilys.db.test;

import java.util.List;

import org.junit.Test;

import com.excilys.db.page.PageComputerDTO;

import junit.framework.TestCase;

public class PageComputerDTOTest extends TestCase {
    static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PageComputerDTOTest.class);
    
    
    @Test
    public void testPageComputerDTO(){
        PageComputerDTO test = new PageComputerDTO();
        List<Integer> listTest = test.getPagesToGo();
        logger.debug(Integer.valueOf(test.getPageMax()).toString());
        logger.debug(Integer.valueOf(test.getComputerMax()).toString());
        for (int i = 0; i < listTest.size();i++) {
        logger.debug(listTest.get(i).toString());
        }
    }
}
