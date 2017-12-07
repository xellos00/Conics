package com.web.app.common.intercetpor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.app.common.util.ServletUtil;
import com.web.app.common.util.StringUtil;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.web.app.common.Constants;
import com.web.app.common.commonMap.CommonMap;


public class UrlSessionInterceptor extends HandlerInterceptorAdapter {
    
    protected Log log = LogFactory.getLog(UrlSessionInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        
        Object sessionUserObject = request.getSession().getAttribute(Constants.session.USER_SESSION_KEY);
        String getRequestURI = request.getRequestURI();
        String getRequestURL = request.getRequestURL().toString();
        String getServletPath = request.getServletPath(); // /util/selectCompanyList.do
        String getContextPath = request.getContextPath(); // /sejong
        String getQueryString = request.getQueryString();
        String xreq = request.getHeader("X-Requested-With");
        
        log.debug("getRequestURI = " + getRequestURI);
        log.debug("getServletPath = " + getServletPath);
        log.debug("getContextPath = " + getContextPath);
        log.debug("getHeader = " + xreq);
        
        StringBuffer s = new StringBuffer();
        CommonMap commonMap = new CommonMap();
        
        if ( null!=getRequestURI && getRequestURI.startsWith("/common") || null!=getRequestURI && getRequestURI.startsWith("/util")) { // '/common' 으로 시작하는 경우 세션체크하지 않음
            s.append("+--------------------------------------------------------------------------+\n");
            s.append("+                             common request                               +\n");
            s.append("+--------------------------------------------------------------------------+\n");
            log.debug(s.toString());
            return true;
        }
        
        if(StringUtil.isNull(StringUtil.null2void(sessionUserObject))){
            String redirectUrl = getContextPath + "/login";
            s.append("+ No session information!\n");
            s.append("+ Redirect to login form page : '").append(redirectUrl).append("'\n");
            s.append("+--------------------------------------------------------------------------+\n");
            log.debug(s.toString());
            response.sendRedirect(redirectUrl);
            return false;
        }
        
        
        commonMap.putAll(ServletUtil.getChangeParameters(request, null));
        
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            commonMap.putAll(ServletUtil.getBindMultipartFiles(multipartRequest.getFileMap()));

            log.debug("MultipartHttpServletRequest's commonMap : " + commonMap);
        }

        log.debug("Constants.DATA_MAP : " + Constants.COMMON_MAP);
        log.debug("dataMap : " + commonMap);
        request.setAttribute(Constants.COMMON_MAP, commonMap);
        
        return true;
    }
     
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("======================================           UrlSessionInterceptor          ======================================\n");
        
        }
    }
}
