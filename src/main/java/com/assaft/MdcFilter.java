package com.assaft;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

// MDC == Mapped Diagnostic Context
@Component
public class MdcFilter implements Filter {

    private static String PATH_KEY = "path";
    private static String SESSION_ID_KEY = "sessionId";
    private static String CORRELATION_ID_KEY = "Correlation-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String path = req.getRequestURI();
        String sessionId = req.getSession().getId();
        String correlationId = req.getHeader(CORRELATION_ID_KEY);

        if (StringUtils.isEmpty(correlationId)) {
            correlationId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        }

        MDC.put(PATH_KEY, path);
        MDC.put(SESSION_ID_KEY, sessionId);
        MDC.put(CORRELATION_ID_KEY, correlationId);

        HttpServletResponse resp = (HttpServletResponse) response;

        resp.addHeader(CORRELATION_ID_KEY, correlationId);

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(PATH_KEY);
            MDC.remove(SESSION_ID_KEY);
            MDC.remove(CORRELATION_ID_KEY);
        }
    }
}

