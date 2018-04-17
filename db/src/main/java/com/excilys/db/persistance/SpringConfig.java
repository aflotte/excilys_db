package com.excilys.db.persistance;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = "{mapper, persistance, service, page, controller,servlet}")
public class SpringConfig implements WebApplicationInitializer {

    @Bean
    public DataSource getDataSource () {
        HikariConfig config;
        HikariDataSource ds;
        Properties prop = new Properties();
        InputStream pathDB = DataSource.class.getClassLoader().getResourceAsStream("datasource.properties");

        try {
            prop.load(pathDB);
            Class.forName(prop.getProperty("dataSource.driver"));
        } catch (Exception e) {
            //TODO : Gérer les loggers et les Exceptions
        }
        config = new HikariConfig(prop);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource(config);        
        return ds;
    }

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext webAppContext = new AnnotationConfigWebApplicationContext();
        webAppContext.setServletContext(container);

        ServletRegistration.Dynamic dispatcher = container.addServlet("dashboard", new DispatcherServlet(webAppContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/dashboard");
    }
}