package com.web.app.conic.ex.externaldashboard.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.web.app.conic.ex.externaldashboard.service.ExternalDashboardService;

@Controller
public class ExternalDashboardController{
	
    @Resource(name = "externalDashboardService")
    private ExternalDashboardService externalDashboardService;
    
    @RequestMapping(value = "/main.do")
    public ModelAndView simpleDeterminationListMove(Map dataMap) throws Exception {
        //return forwarding("/conic/src/main/webapp/WEB-INF/jsp/conic/ex/main", dataMap);
    	return null;
    }
   
    
   
}











