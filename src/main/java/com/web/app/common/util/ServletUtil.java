package com.web.app.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletRequest;

import com.web.app.common.commonMap.CommonMap;
import com.web.app.common.util.StringUtil;

/**
 * 요청파라메터 처리
 * @author kmi86
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ServletUtil {
    
    private static Log log = LogFactory.getLog(ServletUtil.class);
    
    /**
     * MultipartFiles을 new FormFile로 변환해서 dataMap에 위치시킨다.
     * 
     * @param multipartFiles
     * @param dataMap
     */
    public static Map getBindMultipartFiles(Map multipartFiles) throws Exception {
      Map map = new HashMap();
      
      for (Iterator it = multipartFiles.entrySet().iterator(); it.hasNext();) {
        Map.Entry entry = (Map.Entry) it.next();
        String key = (String) entry.getKey();
        MultipartFile value = (MultipartFile) entry.getValue();
        
        // log.debug("getBindMultipartFiles's value : " + value);
        
        if (!value.isEmpty()) {
          // if(log.isDebugEnabled()) log.debug("file 객체 key name : [" + key + "]");
          
          map.put(key, new FileUtil(value));
        }
      }
      
      return map;
    }
    
    /**
     * MultipartFiles을 new FormFile로 변환해서 dataMap에 위치시킨다.
     * 
     * @param multipartFiles
     * @param dataMap
     */
    public static void getBindMultipartFiles(Map multipartFiles, CommonMap commonMap) throws Exception {
      for (Iterator it = multipartFiles.entrySet().iterator(); it.hasNext();) {
        Map.Entry entry = (Map.Entry) it.next();
        String key = (String) entry.getKey();
        MultipartFile value = (MultipartFile) entry.getValue();
        
        if (!value.isEmpty()) {
          // if(log.isDebugEnabled()) log.debug("file 객체 key name : [" + key + "]");
          
            commonMap.put(key, new FileUtil(value));
        }
      }
    }
    
    /**
     * request 에 담긴 parameter만 빼내서 Map으로 저장한 후 리턴한다.
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public static Map getReqeustParameters(ServletRequest request) throws Exception {
      Map map = new HashMap();
      Enumeration enum2 = request.getParameterNames();
      
      while (enum2 != null && enum2.hasMoreElements()) {
        String paramName = (String) enum2.nextElement();
        
        map.put(paramName, StringUtil.unescape(StringUtil.null2void(request.getParameter(paramName))));
      }
      
      return map;
    }
    
    /**
     * Like 검색을 위한 검색어를 변환시킨 후 리턴한다.
     * 
     * @param request
     * @param prefix
     * @return
     */
    public static Map getChangeParameters(ServletRequest request, String prefix) {
      Enumeration enum2 = request.getParameterNames();
      Map params = new HashMap();
      if (prefix == null) {
        prefix = "";
      }
      
      while (enum2 != null && enum2.hasMoreElements()) {
        String paramName = (String) enum2.nextElement();
        
        if ("".equals(prefix) || paramName.startsWith(prefix)) {
          String unprefixed = paramName.substring(prefix.length());
          String values = request.getParameter(paramName);
          
          // Like 검색 쿼리에서 사용할 검색어를 재 정의한다.
          if (paramName.equals("schKeyWord") && !StringUtil.isNull(values)) {
            String likeKey = StringUtil.null2void(request.getParameter("schKeyLike"));
            String keyWord = values;
            
            // if(log.isDebugEnabled()) log.debug("schKeyWord = " + keyWord + " / schKeyLike = " + likeKey);
            
            if (likeKey.equals("C")) {
              values = "%" + keyWord + "%";
            } else if (likeKey.equals("S")) {
              values = keyWord + "%";
            } else if (likeKey.equals("E")) {
              values = "%" + keyWord;
            }
          }
          
          // log.debug("param name = " + paramName + ", defore value = " + values + ", after = " + values);
          
          if (values != null) {
            if (values.equals(" ")) {
              values = values.trim();
            } else {
              values = values;
            }
            
            params.put(unprefixed, values);
          } else {
            params.put(unprefixed, (values).trim());
          }
        }
      }
      
      return params;
    }
    
     /**
       * Method for validate external user authentication.
       * @param userId String.
       * @param authtoken String.
       * @return true Ok.
       */
    public Boolean isValidExternalAuthentication(String userId, String authtoken, Boolean byApi) {
          if(StringUtils.isNotEmpty(authtoken)) {
              
              authtoken = new String(Base64.decodeBase64(authtoken.getBytes()));
              authtoken = authtoken.replace("{","");
              authtoken = authtoken.replace("}","");  
              String [] authtokens = authtoken.split(":");
              
              String date = authtokens[2]; 


              if(byApi && "fta".equals(authtokens[0]) && userId.equals(authtokens[1]) && userId.equals(authtokens[2])) {
                  return true;            
              }
              
              if("fta".equals(authtokens[0]) && userId.equals(authtokens[1]) && this.isSameDateOfTheToken(date)) {
                  return true;
              }
          }
          
          
          return false;
      }
    
     /**
       * Method for validate date base GTM+0.
       * @param date String.
       * @return true isSameDate.
       */
      private Boolean isSameDateOfTheToken(String date) {
          SimpleDateFormat dateFormatGmt = new SimpleDateFormat("ddMMyyyy");
          dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT+0"));               
          return date.equals(dateFormatGmt.format(new Date()));   
      }
}
