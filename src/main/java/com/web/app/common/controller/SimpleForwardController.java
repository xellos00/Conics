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
     * Request URL�� ���� filtering �� ���� �ش��ϴ� URL�� �����Ѵ�.
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
     * Ȯ���ڸ� �����ϰ� URL
     * @param req
     * @return
     */
    public String getViewName(HttpServletRequest req) {
        String viewName = "";
        
        // / .view�� ȣ��Ǵ� ��쿡�� Controller�� ��ġ�� �ʰ� �ٷ� jsp�������� �̵���Ų��.
        viewName = req.getRequestURI();
        viewName = viewName.replaceAll(req.getContextPath(), "");
        viewName = viewName.replaceAll(".view", "");
        viewName = viewName.replaceFirst("/" + StringUtil.null2void(prefixName) + "/", "/");
        
        log.debug("Simple forward path: " + viewName);
        
        return viewName;
    }
}
