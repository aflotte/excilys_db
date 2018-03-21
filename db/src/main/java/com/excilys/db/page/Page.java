package com.excilys.db.page;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Page {
    InputStream input;
    Properties prop = new Properties();
    protected int pageNumber;
    protected int pageSize;
    protected final int pageMax;
    protected final int pageMin;

    public Page() {
        try {
            input = new FileInputStream("./src/main/ressources/properties/page.properties");
            prop.load(input);
           
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        pageSize = Integer.parseInt(prop.getProperty("pageSize"));
        pageMax = 5;
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