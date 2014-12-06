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

        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(ForumApplicationConfiguration.class);
        servletContext.addListener(new ContextLoaderListener(springContext));

    }

}
