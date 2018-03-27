package com.excilys.db.selenium;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import junit.framework.Assert;

public class List {
            private WebDriver driver;       
            @Test               
            public void testEasy() {    
                driver.get("http://localhost:8080/myapp/dashboard?actualPage=1&pageSize=10");  
                String title = driver.getTitle();                
                Assert.assertTrue(title.contains("Computer Database"));      
            }   
            @BeforeTest
            public void beforeTest() {  
                driver = new FirefoxDriver();  
            }       
            @AfterTest
            public void afterTest() {
                driver.quit();          
            }       
     
}
