package example.forum.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Spring Web application initializer. Replaces traditional XML approach.
 * 
 * @author nevenc
 *
 */
public class ForumApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // "no-listener-no-web.xml" setup:
        // XmlWebApplicationContext applicationContext = new XmlWebApplicationContext();
        // applicationContext.setConfigLocation("classpath:applicationContext.xml");

        // "no-xml-100-percent-code-based-with-scanning" setup:
        // AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        // applicationContext.scan("example.services");

        // "no-xml-100-percent-code-based-with-config" setup:
        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(ForumApplicationConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(springContext));

    }

}
