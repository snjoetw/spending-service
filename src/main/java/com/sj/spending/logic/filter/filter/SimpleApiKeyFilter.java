package com.sj.spending.logic.filter.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SimpleApiKeyFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SimpleApiKeyFilter.class);

    @Value("${SpendingService.ApiKeyFilter.IsEnabled}")
    private boolean isEnabled;

    @Value("${SpendingService.ApiKeyFilter.ApiKey}")
    private String apiKey;

    @Value("${SpendingService.ApiKeyFilter.ApiKeyHeaderName}")
    private String apiKeyHeaderName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("Initializing SimpleApiKeyFilter isEnabled={} apiKey={} apiKeyHeaderName={} allowHeaders={} maxAge={}", isEnabled, apiKey, apiKeyHeaderName);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        if (shouldFilter(request) && !hasValidApiKey(request)) {
            HttpServletResponse response = (HttpServletResponse) res;
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean shouldFilter(HttpServletRequest request) {
        if (!isEnabled) {
            return false;
        }

        if (StringUtils.equalsIgnoreCase(HttpMethod.OPTIONS.name(), request.getMethod())) {
            return false;
        }

        return true;
    }

    private boolean hasValidApiKey(HttpServletRequest request) {
        String value = request.getHeader(apiKeyHeaderName);
        boolean match = StringUtils.equals(value, apiKey);

        if (!match) {
            logger.error("Api key mismatch requestApiKey={}", value);
        }

        return match;
    }

    @Override
    public void destroy() {
    }

}
