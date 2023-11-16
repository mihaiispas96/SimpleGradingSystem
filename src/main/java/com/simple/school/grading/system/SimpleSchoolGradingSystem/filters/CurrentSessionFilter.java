package com.simple.school.grading.system.SimpleSchoolGradingSystem.filters;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.session.SessionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * CurrentSessionFilter is a web filter that we use to open a new hibernate session for the incoming requests
 *      - it has order 3, meaning it runs third in the filter chain
 *      - it opens stores a Session in a thread safe singleton instance of com.simple.school.grading.system.SimpleSchoolGradingSystem.session.SessionContext
 *      - it has the @Component annotation -> so that it's managed by the spring container (mark it as a spring bean)
 *
 * @author Ispas Mihai
 */
@Component
@WebFilter(filterName = "currentSessionFilter")
@Order(3)
public class CurrentSessionFilter implements Filter {
    /**
     * The logger instance used to log information in this filter class
     */
    private final Logger logger = LoggerFactory.getLogger(MiscLoggingFilter.class);

    /**
     * The EntityManagerFactory hibernate object member that will have an instance injected through the class constructor
     */
    private final EntityManagerFactory entityManagerFactory;

    /**
     * <p>
     *     This is a constructor used to inject an instance of the EntityManagerFactory hibernate object that is used to manage hibernate entities
     * </p>
     * @param entityManagerFactory -> the EntityManagerFactory being injected by spring
     */
    public CurrentSessionFilter(final EntityManagerFactory entityManagerFactory)
    {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * <p>
     *     This method initializes this web filter (overridden from Filter parent class)
     * </p>
     * @param filterConfig -> the filter config object to initialize the web filter object
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("[CurrentSessionFilter] Filter for opening a new hibernate session is being initialized.");
        Filter.super.init(filterConfig);
    }

    /**
     * <p>
     *     This method destroys this web filter (overridden from Filter parent class)
     * </p>
     */
    @Override
    public void destroy() {
        logger.info("[CurrentSessionFilter] Filter for opening a new hibernate session is being destroyed.");
        Filter.super.destroy();
    }

    /**
     * <p>
     *     This method does the actual "filtering" logic
     *          - before the actual request execution, it opens a new hibernate session for it and stores it in a context
     *          - after the execution of the request, it closes the session opened for a particular request and detaches it from the context
     * </p>
     * @param servletRequest -> the request that is being "filtered"
     * @param servletResponse -> the response object associated to the servletRequest parameter
     * @param filterChain -> the chain of filters that are being run sequentially
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        logger.info("[CurrentSessionFilter] Opening a new hibernate session for request to the path : " + httpServletRequest.getServletPath());

        final Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        SessionContext.getInstance().setCurrentSession(session);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch(final Exception e)
        {
            detachSessionFromContext();
            logger.info("[CurrentSessionFilter] Closing the hibernate session for request to the path : " + httpServletRequest.getServletPath() + " after exception thrown : \n + " + e);
            throw e;
        } finally {
            logger.info("[CurrentSessionFilter] Closing the hibernate session for request to the path : " + httpServletRequest.getServletPath() + " after successful execution.");
            detachSessionFromContext();
        }
    }

    /**
     * <p>
     *     This is a private utility method that is used for code cleanness
     *          - it closes the session opened for a particular request
     *          - removes that session object from the session context
     * </p>
     */
    private void detachSessionFromContext()
    {
        if (SessionContext.getInstance() != null)
        {
            SessionContext.getInstance().getCurrentSession().close();
            SessionContext.getInstance().removeCurrentSession();
        }
    }
}
