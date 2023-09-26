package com.aq.blogapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Component
public class LoggingFilter extends OncePerRequestFilter {

    private final static Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);

        long timeTaken = System.currentTimeMillis() - startTime;

        String requestBody = getStringValue(cachingRequestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        String responseBody = getStringValue(cachingResponseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

        log.info("Filter Logs :\n method = {};\n requestURI={};\n request body={};\n response code= {};\n response body ={};\n time taken = {} ms",
                request.getMethod(), request.getRequestURI(), requestBody, response.getStatus(), responseBody, timeTaken
                );

        cachingResponseWrapper.copyBodyToResponse();
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try{

            return  new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        }
        catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
        }

        return "";

    }
}
