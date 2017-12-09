package com.web.app.conics.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.web.app.common.Constants;
import com.web.app.common.action.ResponseAction;
import com.web.app.common.common.main.service.MainService;
import com.web.app.common.commonMap.CommonMap;
import com.web.app.common.util.ServletUtil;
import com.web.app.common.util.StringUtil;


public class ConicsForwordController implements Controller{
    
    protected static Log log = LogFactory.getLog(ConicsForwordController.class);
    
    private MainService mainService;
    
    private String certifyType; // internal : ���� �����, external : �ܺλ����(���»�)
    
    private String prefixName; // ������
    
    public void setCertifyType(String type) {
        this.certifyType = type;
    }
    
    public void setPrefixName(String name) {
        this.prefixName = name;
    }
    
    public void setMainService(MainService service) {
        this.mainService = service;
    }
    
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse respone) throws Exception {
        ModelAndView mav = new ModelAndView();
        String sessionKey = Constants.session.KEY_USER_SESSION;
        
        /*if (certifyType.equals("internal")) {
            mav = this.internalForward(req, respone, sessionKey);
        } else if (certifyType.equals("external")) {
            mav = this.externalForward(req, respone, sessionKey);
        }*/
        
        mav = this.externalForward(req, respone, sessionKey);
        
        return mav;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ModelAndView externalForward(HttpServletRequest req, HttpServletResponse respone, String sessionKey) throws Exception {
        if (log.isDebugEnabled()) {
            // log.debug("start externalCertify user......."); } System.out.println("=================================================================================");
        }
        
        ModelAndView mav = new ModelAndView();
        
        String redirectUrl = "redirect:/conics/" + StringUtil.null2void(prefixName) + "/main/mainMove.do";
        String url = StringUtil.null2void(req.getRequestURL());
        String strHost = null;
        String redirectionUrl = null;
        
        HttpSession session = req.getSession();
        CommonMap commonOMap = new CommonMap();
        
        if (url.indexOf("/cert/login.do") > -1) { // �α��� ����
            Map certInfo = null;
            
            CommonMap commonMap = new CommonMap();
            
            // ����ȭ�鿡�� ȸ�纯�� ��
            String CB_COMPANY_CODE = StringUtil.null2string(req.getParameter("CB_COMPANY_CODE"), "");
            String CHANGE_COMPANY_YN = StringUtil.null2string(req.getParameter("CHANGE_COMPANY_YN"), "N");
            
            String id = StringUtil.null2void(req.getParameter("userId"));
            String pwd = StringUtil.null2void(req.getParameter("password"));
            String company_code = StringUtil.null2string(CB_COMPANY_CODE, StringUtil.null2void(req.getParameter(Constants.KEY_COMPANY_CODE)));
            strHost = req.getRemoteAddr(); // ���� Local�� IP�� ��´�.
            
            commonMap.put("CERTIFY_TYPE", "external");
            commonMap.put("CHANGE_COMPANY_YN", CHANGE_COMPANY_YN);
            
            commonMap.put("userId", id);
            commonMap.put("password", pwd);
            commonMap.put(Constants.KEY_COMPANY_CODE, company_code);
            commonMap.put("COMPANY_CODE_S", company_code);
            commonMap.put("IP", strHost);
            commonOMap = commonMap;
            
            ServletUtil servletHelper = new ServletUtil(); 
            String authtoken = req.getParameter("authtoken");
            
            if(servletHelper.isValidExternalAuthentication(id, authtoken, false)) {
                commonMap.put("CHANGE_COMPANY_YN", "Y");
            }   
            
            //certInfo = this.getUserLoginInfo(commonMap);
            
            if (certInfo == null) {
                String redirectURL = "";
                //return policyService.selectLoginFailProcedueModelAndView(dataMap, prefixName, redirectURL);
                
            } else {
                if ("N".equals(CHANGE_COMPANY_YN)) {
                    // Is this first login
                    commonMap.put("UPPER", certInfo.get("SESSION_UPPER"));
                    commonMap.put("LOWER", certInfo.get("SESSION_LOWER"));
                    commonMap.put("NUMBER", certInfo.get("SESSION_NUMBER"));
                    commonMap.put("SYMBOL", certInfo.get("SESSION_SYMBOL"));
                    commonMap.put("MAX_LT", certInfo.get("SESSION_MAX_LT"));
                    commonMap.put("MIN_LT", certInfo.get("SESSION_MIN_LT"));
                    /*if (policyService.isFirstLogin(dataMap)) {
                        return policyService.selectPasswordFailModelAndView(dataMap, redirectUrl);
                    }*/
                    
                    // Redirect URL
                    redirectionUrl = StringUtil.null2void(req.getParameter("redirectionUrl"));
                    
                    commonMap.put("EXPIRE_YN", certInfo.get("SESSION_LIER_USE_YN"));
                    commonMap.put("EXPIRE_DT", certInfo.get("SESSION_LIER_EXP_LT"));
                    
                    /*if (policyService.selectPasswordChangePeriod(dataMap)) {
                        
                        return policyService.selectPasswordFailModelAndView(dataMap, redirectUrl);
                    }*/
                    
                    if (!redirectionUrl.equals("")) {
                        redirectUrl = "redirect:" + redirectionUrl;
                    }
                }
            }
            
            // Session ������ Setting �Ѵ�.
            session.setAttribute(sessionKey, certInfo);
            session.setMaxInactiveInterval(60 * 60);
            
            // ����
            /*SessionLocaleResolver localeResolver = null;
            localeResolver = (SessionLocaleResolver) ApplicationContextFactory.getAppContext().getBean("localeResolver");
            
            String defaultLanguage = StringHelper.null2string(StringHelper.null2void(certInfo.get(Constants.KEY_USE_LANG_CODE)), Constants.DEFAULT_LANGUAGE);
            localeResolver.setDefaultLocale(new Locale(defaultLanguage));*/
            // }
            
            if (log.isDebugEnabled()) { // log.debug("complete login(id=" + id + ", session=" + session.getAttribute(Constants.SESSION_KEY) + ")");
            
            }
            
            commonOMap.put("CHANGE_TYPE", "FAILR_CNT");
            commonOMap.put("FAILR_CNT", 0);
            /*policyService.updateFailCountUpOrClean(DataMapHelper.getMap(dataOMap));*/
            
        } else if (url.indexOf("/cert/logout.do") > -1) { // �α׾ƿ� ���� // �α׾ƿ�
            Map map = null;
            // �α׾ƿ�
            session.removeAttribute(sessionKey);
            session.invalidate();
            ModelAndView movs = ResponseAction.forwarding("/login", (CommonMap) map);
            if (log.isErrorEnabled()) log.error("complete logout.(session=" + sessionKey + ")");
            return movs;
            
        } else if (url.indexOf("/cert/sessionCheck.do") > -1) { // �α��� �������� üũ // ������� ����� ���� ���������� �ִ��� üũ
            Map member = (LinkedHashMap) session.getAttribute(sessionKey);
            
            if (member == null) {
                redirectUrl = "redirect:/" + StringUtil.null2void(prefixName) + "/main/mainForward.view";
                mav.addAllObjects(ServletUtil.getReqeustParameters(req));
                mav.addObject("MEMBER_SESSION", "N");
            } else {
                redirectUrl = "redirect:/" + StringUtil.null2void(prefixName) + "/main/mainForward.view";
                mav.addAllObjects(ServletUtil.getReqeustParameters(req));
                mav.addObject("MEMBER_SESSION", "Y");
            }
        }
        
        mav.setViewName(redirectUrl);
        
        return mav;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Map getUserLoginInfo(CommonMap commonMap) throws Exception {
        /*Map result = mainService.selectMember(commonMap.getMap());*/
        
        Map result = new HashMap();
        
        if (result != null && result.size() > 0) {
            
            commonMap.put(Constants.KEY_COMPANY_CODE, result.get(Constants.KEY_COMPANY_CODE) + "");
            
            /*Map conResult = mainService.selectConfiguration(DataMapHelper.getMap(dataMap));
            
            if (conResult != null && conResult.size() > 0 && (certifyType.equals("internal") || certifyType.equals("settings") || certifyType.equals("external"))) {
                
                Iterator<String> iterator = conResult.keySet().iterator();
                while (iterator.hasNext()) {
                    String hkey = (String) iterator.next();
                    String hvalue = (String) conResult.get(hkey);
                    result.put(hkey, hvalue);
                }
            }*/
            
            // ����� ������ ������ �⺻��� ����
            /*String defaultLanguage = StringHelper.null2string(result.get(Constants.KEY_USE_LANG_CODE), ConfiguratorFactory.getInstance().getConfigurator().getString("application.context.language"));
            result.put(Constants.KEY_USE_LANG_CODE, defaultLanguage);
            result.put(Constants.KEY_S_DEFAULT_LANGUAGE, defaultLanguage);*/
            
            // ���� �ο�(���:inline, �ź�:none)
            result.put(Constants.KEY_SEL_AUTH, "none"); // ���� ��ȸ ������ ������ ���(inline)�ϰ� ����
            result.put(Constants.KEY_REG_AUTH, "none");
            result.put(Constants.KEY_UPD_AUTH, "none");
            result.put(Constants.KEY_DEL_AUTH, "none");
            result.put(Constants.KEY_EXC_AUTH, "none");
            result.put(Constants.KEY_FLE_AUTH, "none");
            
            // ȸ������ ����
            result.put(Constants.KEY_COMPANY_CODE, result.get(Constants.KEY_COMPANY_CODE) + "");
            
            // �����ڿ� ������ ����
            result.put(Constants.KEY_LOGIN_ID, result.get(Constants.KEY_LOGIN_ID) + "");
            result.put(Constants.KEY_USER_ID, result.get(Constants.KEY_USER_ID) + "");
            result.put(Constants.KEY_CREATE_BY, result.get(Constants.KEY_USER_ID) + "");
            result.put(Constants.KEY_UPDATE_BY, result.get(Constants.KEY_USER_ID) + "");
            result.put(Constants.KEY_S_CERTIFY_TYPE, commonMap.get("CERTIFY_TYPE"));
            
            // login log�� ����
            commonMap.put(Constants.KEY_USER_ID, result.get(Constants.KEY_USER_ID) + "");
            commonMap.put(Constants.KEY_COMPANY_CODE, result.get(Constants.KEY_COMPANY_CODE) + "");
            /*String logSeq = mainService.insertLogMgrMst(DataMapHelper.getMap(dataMap));
            result.put(Constants.KEY_LOG_SN, logSeq);*/
            
        } else {
            
            return null;
            // throw new EframeException("not found user information.");
        }
        
        return result;
    }
    
}
