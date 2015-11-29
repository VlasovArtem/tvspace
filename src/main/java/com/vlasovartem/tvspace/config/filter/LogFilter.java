package com.vlasovartem.tvspace.config.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by artemvlasov on 24/11/15.
 */
public class LogFilter implements Filter {
    public static final Logger LOGGER = Logger.getLogger(LogFilter.class.getSimpleName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        LOGGER.info("Address: " + servletRequest.getRemoteAddr() + " from " + LocalDate.now());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
