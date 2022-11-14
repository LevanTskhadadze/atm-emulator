package com.egc.bankservice.config;

import com.egc.bankservice.logging.requestresponse.LoggingManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;


@Slf4j
@WebFilter
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Autowired
    private LoggingManager loggingManager;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);

        loggingManager.logReq(contentCachingRequestWrapper);

        long startTime = System.currentTimeMillis();

        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper);

        long timeTaken = System.currentTimeMillis() - startTime;

        loggingManager.logResp(contentCachingRequestWrapper, contentCachingResponseWrapper, timeTaken);

        contentCachingResponseWrapper.copyBodyToResponse();
    }
}
