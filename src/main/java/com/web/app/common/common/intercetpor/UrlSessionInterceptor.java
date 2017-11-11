package com.web.app.common.common.intercetpor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UrlSessionInterceptor extends HandlerInterceptorAdapter {
    
    protected Log log = LogFactory.getLog(UrlSessionInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("======================================          UrlSessionInterceptor         ======================================");
            log.debug(" Request URI \t:  " + request.getRequestURI());
        }
        
        return super.preHandle(request, response, handler);
    }
     
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("======================================           UrlSessionInterceptor          ======================================\n");
        
        }
    }
}
