package no.ciber.chat.config;

import no.ciber.chat.filters.CORSFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class ApiInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/chat-api/*"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                PersistenceConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                ApiConfiguration.class
        };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                /** HiddenHttpMethodFilter gjør at man kan bruke post, som oppfattes som en PUT, DELETE etc. Kjekt dersom apiet skal bruke fra html-forms  */
                new HiddenHttpMethodFilter(),
                /** Dersom browsere asynkront skal hente ressurser fra et annet domene så må CORS-headere legges til.  */
                new CORSFilter(),
                /** Beregner en hashverdi fra responsen, minimerer overføring av unødvendige data.  */
                new ShallowEtagHeaderFilter()
        };
    }
}