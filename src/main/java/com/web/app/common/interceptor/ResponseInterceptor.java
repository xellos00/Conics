package com.web.app.common.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.web.app.common.Constants;
import com.web.app.common.common.main.service.MainService;
import com.web.app.common.util.FileUtil;
import com.web.app.common.util.StringUtil;
import com.web.app.common.commonMap.CommonMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResponseInterceptor  extends HandlerInterceptorAdapter {
    
private static Log log = LogFactory.getLog(ResponseInterceptor.class);
    
    private MainService mainService;
    
    public void setMainService(MainService service) {
        this.mainService = service;
    }
    
    private String prefixName;
    
    public void setPrefixName(String name) {
        this.prefixName = name;
    }
    
    public String prefixName() {
        return Constants.session.KEY_USER_SESSION;
    }
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("preHandle : request URL(" + request.getRequestURI() + ")");
        // 사용자 사용로그 기록
        if (StringUtil.null2void(request.getParameter("MENU_AUTH_YN")).equals("Y")) {
            Map param = new HashMap();
            Map sessionMap = (LinkedHashMap) request.getSession().getAttribute(prefixName());
            
            param.put("MENU_ID", StringUtil.null2void(request.getParameter("MENU_ID")));
            if (sessionMap != null) {
                param.put(Constants.KEY_COMPANY_CODE, sessionMap.get(Constants.KEY_COMPANY_CODE));
                param.put(Constants.KEY_LOGIN_ID, sessionMap.get(Constants.KEY_LOGIN_ID));
                param.put(Constants.KEY_USER_ID, sessionMap.get(Constants.KEY_USER_ID) + "");
            }
            
            this.setMenuAuthority(request, param);
        }
        
        log.debug("end of preHandle");
        return true;
    }
    
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            
            CommonMap commonMap = (CommonMap) multipartRequest.getAttribute(Constants.COMMON_MAP);
            /*Map map = DataMapHelper.getMap(dataMap);*/
            
            FileUtil.removeFormFile(commonMap);
        }
    }
    
    /**
     * 사용자 메뉴 및 권한 조회
     * @param request
     * @param map
     * @throws Exception
     */
    public void setMenuAuthority(HttpServletRequest request, Map map) throws Exception {
        Map sessionMap = (LinkedHashMap) request.getSession().getAttribute(prefixName());
        
        /*if (sessionMap != null) {
            String certifyType = StringUtil.null2void(sessionMap.get(Constants.KEY_S_CERTIFY_TYPE));
            if ("contents".equals(certifyType)) { // 외부 협력사 사용자인 경우
                sessionMap.put(Constants.KEY_SEL_AUTH, "inline");
                sessionMap.put(Constants.KEY_REG_AUTH, "inline");
                sessionMap.put(Constants.KEY_UPD_AUTH, "inline");
                sessionMap.put(Constants.KEY_DEL_AUTH, "inline");
                sessionMap.put(Constants.KEY_PNT_AUTH, "inline");
                sessionMap.put(Constants.KEY_EXL_AUTH, "inline");
                sessionMap.put(Constants.KEY_EXC_AUTH, "inline");
                sessionMap.put(Constants.KEY_FLE_AUTH, "inline");
                
                request.setAttribute(Constants.KEY_SEL_AUTH, "inline");
                request.setAttribute(Constants.KEY_REG_AUTH, "inline");
                request.setAttribute(Constants.KEY_UPD_AUTH, "inline");
                request.setAttribute(Constants.KEY_DEL_AUTH, "inline");
                request.setAttribute(Constants.KEY_PNT_AUTH, "inline");
                request.setAttribute(Constants.KEY_EXL_AUTH, "inline");
                request.setAttribute(Constants.KEY_EXC_AUTH, "inline");
                request.setAttribute(Constants.KEY_FLE_AUTH, "inline");
                
            } else if ("external".equals(certifyType)) { // 외부 협력사 사용자인 경우
                sessionMap.put(Constants.KEY_SEL_AUTH, "inline");
                sessionMap.put(Constants.KEY_REG_AUTH, "inline");
                sessionMap.put(Constants.KEY_UPD_AUTH, "inline");
                sessionMap.put(Constants.KEY_DEL_AUTH, "inline");
                sessionMap.put(Constants.KEY_PNT_AUTH, "inline");
                sessionMap.put(Constants.KEY_EXL_AUTH, "inline");
                sessionMap.put(Constants.KEY_EXC_AUTH, "inline");
                sessionMap.put(Constants.KEY_FLE_AUTH, "inline");
                
                request.setAttribute(Constants.KEY_SEL_AUTH, "inline");
                request.setAttribute(Constants.KEY_REG_AUTH, "inline");
                request.setAttribute(Constants.KEY_UPD_AUTH, "inline");
                request.setAttribute(Constants.KEY_DEL_AUTH, "inline");
                request.setAttribute(Constants.KEY_PNT_AUTH, "inline");
                request.setAttribute(Constants.KEY_EXL_AUTH, "inline");
                request.setAttribute(Constants.KEY_EXC_AUTH, "inline");
                request.setAttribute(Constants.KEY_FLE_AUTH, "inline");
                
            } else {
                Map authMap = mainService.selectMenuAuthorityInfo(map);
                
                // 스케쥴이 특정 서버에서만 실행되도록 지정할 경우 이와 관련된 서버에서만 스케쥴을 제어할 수 있도록 권한을 변경한다.
                String menuId = StringUtil.null2void(map.get("MENU_ID"));
                
                if (authMap != null) {
                    sessionMap.put(Constants.KEY_SEL_AUTH, StringUtil.null2string(authMap.get("SEL_AUTH"), "none"));
                    sessionMap.put(Constants.KEY_REG_AUTH, StringUtil.null2string(authMap.get("REG_AUTH"), "none"));
                    sessionMap.put(Constants.KEY_UPD_AUTH, StringUtil.null2string(authMap.get("UPD_AUTH"), "none"));
                    sessionMap.put(Constants.KEY_DEL_AUTH, StringUtil.null2string(authMap.get("DEL_AUTH"), "none"));
                    sessionMap.put(Constants.KEY_PNT_AUTH, StringUtil.null2string(authMap.get("PNT_AUTH"), "none"));
                    sessionMap.put(Constants.KEY_EXL_AUTH, StringUtil.null2string(authMap.get("EXL_AUTH"), "none"));
                    sessionMap.put(Constants.KEY_EXC_AUTH, StringUtil.null2string(authMap.get("EXC_AUTH"), "none"));
                    sessionMap.put(Constants.KEY_FLE_AUTH, StringUtil.null2string(authMap.get("FLE_AUTH"), "none"));
                    
                    request.getSession().setAttribute(Constants.KEY_SEL_AUTH, StringUtil.null2string(authMap.get("SEL_AUTH"), "none"));
                    request.getSession().setAttribute(Constants.KEY_REG_AUTH, StringUtil.null2string(authMap.get("REG_AUTH"), "none"));
                    request.getSession().setAttribute(Constants.KEY_UPD_AUTH, StringUtil.null2string(authMap.get("UPD_AUTH"), "none"));
                    request.getSession().setAttribute(Constants.KEY_DEL_AUTH, StringUtil.null2string(authMap.get("DEL_AUTH"), "none"));
                    request.getSession().setAttribute(Constants.KEY_PNT_AUTH, StringUtil.null2string(authMap.get("PNT_AUTH"), "none"));
                    request.getSession().setAttribute(Constants.KEY_EXL_AUTH, StringUtil.null2string(authMap.get("EXL_AUTH"), "none"));
                    request.getSession().setAttribute(Constants.KEY_EXC_AUTH, StringUtil.null2string(authMap.get("EXC_AUTH"), "none"));
                    request.getSession().setAttribute(Constants.KEY_FLE_AUTH, StringUtil.null2string(authMap.get("FLE_AUTH"), "none"));
                    
                } else {
                    sessionMap.put(Constants.KEY_SEL_AUTH, "none");
                    sessionMap.put(Constants.KEY_REG_AUTH, "none");
                    sessionMap.put(Constants.KEY_UPD_AUTH, "none");
                    sessionMap.put(Constants.KEY_DEL_AUTH, "none");
                    sessionMap.put(Constants.KEY_PNT_AUTH, "none");
                    sessionMap.put(Constants.KEY_EXL_AUTH, "none");
                    sessionMap.put(Constants.KEY_EXC_AUTH, "none");
                    sessionMap.put(Constants.KEY_FLE_AUTH, "none");
                    
                    request.getSession().setAttribute(Constants.KEY_SEL_AUTH, "none");
                    request.getSession().setAttribute(Constants.KEY_REG_AUTH, "none");
                    request.getSession().setAttribute(Constants.KEY_UPD_AUTH, "none");
                    request.getSession().setAttribute(Constants.KEY_DEL_AUTH, "none");
                    request.getSession().setAttribute(Constants.KEY_PNT_AUTH, "none");
                    request.getSession().setAttribute(Constants.KEY_EXL_AUTH, "none");
                    request.getSession().setAttribute(Constants.KEY_EXC_AUTH, "none");
                    request.getSession().setAttribute(Constants.KEY_FLE_AUTH, "none");
                }
            }
            
            map.put("REQUEST_URL", request.getRequestURI());
            map.put("PARAMETER", map.toString().replace("end of map info ============================", ""));
            String MENU_ID = StringUtil.null2void(map.get("MENU_ID"));
            
            if (!MENU_ID.isEmpty() && StringUtil.null2void(map.get("LINK_URL")).indexOf("selectBookMarkYn.do") == -1) {
                mainService.insertLogMgrDtl(map);
            }
            
            request.getSession().setAttribute(prefixName(), sessionMap);
        }*/
    }
}
