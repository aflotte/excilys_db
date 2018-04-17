package com.excilys.db.persistance;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

@Override
protected Class<?>[] getRootConfigClasses() {
return new Class<?>[] { SpringConfig.class };
}

@Override
protected Class<?>[] getServletConfigClasses() {
return new Class<?>[] { SpringConfig.class };
}

@Override
protected String[] getServletMappings() {
return new String[] {"/"};
}

}