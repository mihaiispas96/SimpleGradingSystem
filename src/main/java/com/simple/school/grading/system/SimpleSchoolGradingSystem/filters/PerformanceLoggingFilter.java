package com.simple.school.grading.system.SimpleSchoolGradingSystem.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * PerformanceLoggingFilter is a web filter that we use to log information about the performance time of the incoming requests
 *      - it has order 2, meaning it runs second in the filter chain
 *      - it has the @Component annotation -> so that it's managed by the spring container (mark it as a spring bean)
 *
 * @author Ispas Mihai
 */
@Component
@WebFilter(filterName = "performanceLoggingFilter")
@Order(2)
public class PerformanceLoggingFilter implements Filter {
    /**
     * The logger instance used to log information in this filter class
     */
    final Logger logger = LoggerFactory.getLogger(PerformanceLoggingFilter.class);

    /**
     * <p>
     *     This method initializes this web filter (overridden from Filter parent class)
     * </p>
     * @param filterConfig -> the filter config object to initialize the web filter object
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("[PerformanceMonitoringFilter] Performance logging filter is being initialized.");
        Filter.super.init(filterConfig);
    }

    /**
     * <p>
     *     This method destroys this web filter (overridden from Filter parent class)
     * </p>
     */
    @Override
    public void destroy() {
        logger.info("[PerformanceMonitoringFilter] Performance logging filter is being destroyed.");
        Filter.super.destroy();
    }

    /**
     * <p>
     *     This method does the actual "filtering" logic
     *          - it registers the time before and after the request execution and logs the execution time
     * </p>
     * @param servletRequest -> the request that is being "filtered"
     * @param servletResponse -> the response object associated to the servletRequest parameter
     * @param filterChain -> the chain of filters that are being run sequentially
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("[PerformanceMonitoringFilter] Performance logging started.");

        final long startTime = System.nanoTime();
        long endTime;

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch(final Exception e)
        {
            endTime = System.nanoTime();
            logger.info("[PerformanceMonitoringFilter] Request took : " + (double) (endTime - startTime) / 1000000 + " milliseconds to complete, but with exception thrown : \n" + e);
            throw e;
        } finally {
            endTime = System.nanoTime();
            logger.info("[PerformanceMonitoringFilter] Request took : " + (double) (endTime - startTime) / 1000000 + " milliseconds to complete successful execution.");
        }
        logger.info("[PerformanceMonitoringFilter] Performance logging ended.");
    }
}
