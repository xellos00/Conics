package com.web.app.common.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.web.app.common.util.ServletUtil;
import com.web.app.common.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleForwardController implements Controller{
    
    private Log log = LogFactory.getLog(SimpleForwardController.class);
    
    private String prefixName;
    
    public void setPrefixName(String name) {
        this.prefixName = name;
    }
    
    /**
     * Request URL에 대한 filtering 을 거쳐 해당하는 URL로 응답한다.
     */
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse respone) throws Exception {
        ModelAndView mav = new ModelAndView();
        
        Map map = ServletUtil.getReqeustParameters(req);
        
        log.debug("MEMBER_SESSION = " + map);
        
        mav.addAllObjects(map);
        mav.setViewName(getViewName(req));
        
        return mav;
    }
    
    /**
     * 확장자를 제거하고 URL
     * @param req
     * @return
     */
    public String getViewName(HttpServletRequest req) {
        String viewName = "";
        
        // / .view로 호출되는 경우에는 Controller를 거치디 않고 바로 jsp페이지로 이동시킨다.
        viewName = req.getRequestURI();
        viewName = viewName.replaceAll(req.getContextPath(), "");
        viewName = viewName.replaceAll(".view", "");
        viewName = viewName.replaceFirst("/" + StringUtil.null2void(prefixName) + "/", "/");
        
        log.debug("Simple forward path: " + viewName);
        
        return viewName;
    }
}
