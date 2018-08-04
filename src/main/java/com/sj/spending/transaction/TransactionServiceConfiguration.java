package com.sj.spending.transaction;

import com.sj.spending.logic.filter.filter.SimpleApiKeyFilter;
import com.sj.spending.logic.filter.filter.SimpleCrossOriginResourceSharingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.Filter;

@Configuration
public class TransactionServiceConfiguration {

    @Bean
    public Filter loggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(5120);
        return filter;
    }

    @Bean
    public Filter crossOriginResourceSharingFilter() {
        return new SimpleCrossOriginResourceSharingFilter();
    }

    @Bean
    public Filter apiKeyFilter() {
        return new SimpleApiKeyFilter();
    }

}
