package com.excilys.db.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/connect.properties")
@ComponentScan(basePackages = {
        "com.excilys.db.persistance",
        "com.excilys.db.service",
        "com.excilys.db.page",
        "com.excilys.db.mapper",
        "com.excilys.db.cli",
        "com.excilys.db.validator",
        "com.excilys.db.controller"
})
public class CLIConfig {

    private Environment environment;

    public CLIConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource( environment.getProperty("database"),
                environment.getProperty("dbuser"),
                environment.getProperty("dbpassword"));
        driverManagerDataSource.setDriverClassName(environment.getProperty("dbdriver"));
        return driverManagerDataSource;
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.excilys.db.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        
        return sessionFactory;
    }

    
    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
          = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        
        return transactionManager;
    }

    @Bean
    public DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    
    
    
    
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(
      SessionFactory sessionFactory) {
   
       HibernateTransactionManager txManager
        = new HibernateTransactionManager();
       txManager.setSessionFactory(sessionFactory);
  
       return txManager;
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
       return new PersistenceExceptionTranslationPostProcessor();
    }
 
    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
          "hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty(
          "hibernate.dialect", "org.hibernate.dialect.H2Dialect");
 
        return hibernateProperties;
    }
}