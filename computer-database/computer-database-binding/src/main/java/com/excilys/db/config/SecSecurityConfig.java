package com.excilys.db.config;

import java.util.Properties;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    private Environment environment;

    public SecSecurityConfig(Environment environment) {
        this.environment = environment;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("user1").password("user1Pass").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/", "/dashboard").permitAll()
        .antMatchers("/addComputer", "/editComputer").hasAnyRole("ADMIN", "USER")
        .and().formLogin().loginPage("/login").defaultSuccessUrl("/dashboard")
        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/dashboard")
        .permitAll();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth, @SuppressWarnings("deprecation") NoOpPasswordEncoder passwordEncoder) throws Exception {
        auth.jdbcAuthentication().dataSource((javax.sql.DataSource) dataSource())
        .usersByUsernameQuery("select username,password, enabled from users where username=?")
        .authoritiesByUsernameQuery("select username, role from user_roles where username=?")
        .passwordEncoder(passwordEncoder);
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource( environment.getProperty("database"),
                environment.getProperty("dbuser"),
                environment.getProperty("dbpassword"));
        driverManagerDataSource.setDriverClassName(environment.getProperty("dbdriver"));
        return (DataSource) driverManagerDataSource;
    }


    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        return hibernateProperties;
    }

    @Bean
    public LocalSessionFactoryBean getSessionFactory(DataSource datasource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource((javax.sql.DataSource) datasource);
        sessionFactory.setPackagesToScan("com.excilys.computer.database.core.modele");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }


}