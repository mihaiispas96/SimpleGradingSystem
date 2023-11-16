package com.simple.school.grading.system.SimpleSchoolGradingSystem.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * MiscLoggingFilter is a web filter that we use to log information about incoming requests
 *      - it has order 1, meaning it runs first in the filter chain
 *      - it has the @Component annotation -> so that it's managed by the spring container (mark it as a spring bean)
 *
 * @author Ispas Mihai
 */
@Component
@WebFilter(filterName = "miscLoggingFilter")
@Order(1)
public class MiscLoggingFilter implements Filter {
    /**
     * The logger instance used to log information in this filter class
     */
    final Logger logger = LoggerFactory.getLogger(MiscLoggingFilter.class);

    /**
     * <p>
     *     This method initializes this web filter (overridden from Filter parent class)
     * </p>
     * @param filterConfig -> the filter config object to initialize the web filter object
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("[MiscLoggingFilter] Miscellaneous logging filter initializing.");
        Filter.super.init(filterConfig);
    }

    /**
     * <p>
     *     This method destroys this web filter (overridden from Filter parent class)
     * </p>
     */
    @Override
    public void destroy() {
        logger.info("[MiscLoggingFilter] Miscellaneous logging filter destroying.");
        Filter.super.destroy();
    }

    /**
     * <p>
     *     This method does the actual "filtering" logic
     *          - before the actual request execution, it logs some miscellaneous information about the incoming requests
     * </p>
     * @param servletRequest -> the request that is being "filtered"
     * @param servletResponse -> the response object associated to the servletRequest parameter
     * @param filterChain -> the chain of filters that are being run sequentially
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("[MiscLoggingFilter] Miscellaneous logging started.");

        logger.info("[MiscLoggingFilter] Server port : " + servletRequest.getLocalPort());
        logger.info("[MiscLoggingFilter] Server name : " + servletRequest.getServerName());
        logger.info("[MiscLoggingFilter] Server port : " + servletRequest.getLocalPort());

        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        logger.info("[MiscLoggingFilter] Request URL : " + httpServletRequest.getRequestURL());
        logger.info("[MiscLoggingFilter] Request URI : " + httpServletRequest.getRequestURI());
        logger.info("[MiscLoggingFilter] Request method : " + httpServletRequest.getMethod());
        logger.info("[MiscLoggingFilter] Servlet path : " + httpServletRequest.getServletPath());

        filterChain.doFilter(servletRequest, servletResponse);

        logger.info("[MiscLoggingFilter] Miscellaneous logging ended.");
    }
}
