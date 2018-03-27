package com.excilys.db.page;

import java.io.InputStream;
import java.util.Properties;

public class Page {
    InputStream input;
    Properties prop = new Properties();
    protected int pageNumber;
    protected int pageSize;
    protected int computerMax;
    public int getComputerMax() {
        return computerMax;
    }
    public void setComputerMax(int computerMax) {
        this.computerMax = computerMax;
    }
    protected int pageMax;
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageMax() {
        return pageMax;
    }
    public void setPageMax(int pageMax) {
        this.pageMax = pageMax;
    }
    protected final int pageMin;

    public Page() {
        pageSize = 10;
        pageMin = 1;
    }
    public void nextPage() {
        if (pageNumber + 1 <= pageMax) {
            pageNumber += 1;
        }
    }
    public void previousPage() {
        if (pageNumber - 1 >= pageMin) {
            pageNumber -= 1;
        }
    }
}