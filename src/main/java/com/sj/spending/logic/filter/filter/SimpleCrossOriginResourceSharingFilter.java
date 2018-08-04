package com.sj.spending.logic.filter.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleCrossOriginResourceSharingFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(SimpleCrossOriginResourceSharingFilter.class);

	@Value("${SpendingService.CrossOriginResourceSharingFilter.IsEnabled}")
	private boolean isEanbled;

	@Value("${SpendingService.CrossOriginResourceSharingFilter.AllowOrigin}")
	private String allowOrigin;

	@Value("${SpendingService.CrossOriginResourceSharingFilter.AllowMethods}")
	private String allowMethods;

	@Value("${SpendingService.CrossOriginResourceSharingFilter.AllowHeaders}")
	private String allowHeaders;

	@Value("${SpendingService.CrossOriginResourceSharingFilter.MaxAge}")
	private String maxAge;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("Initializing SimpleApiKeyFilter isEanbled={} allowOrigin={} allowMethods={} allowHeaders={} maxAge={}", isEanbled, allowOrigin, allowMethods, allowHeaders, maxAge);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		if (isEanbled) {
			HttpServletResponse response = (HttpServletResponse) res;
			
			if (StringUtils.isNotEmpty(allowOrigin)) {
				response.setHeader("Access-Control-Allow-Origin", allowOrigin);
			}
			
			if (StringUtils.isNotEmpty(allowMethods)) {
				response.setHeader("Access-Control-Allow-Methods", allowMethods);
			}
			
			if (StringUtils.isNotEmpty(allowHeaders)) {
				response.setHeader("Access-Control-Allow-Headers", allowHeaders);
			}
			
			if (StringUtils.isNotEmpty(maxAge)) {
				response.setHeader("Access-Control-Max-Age", maxAge);
			}
		}
		
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}

}
