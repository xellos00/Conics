package com.web.app.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.web.app.common.Constants;
import com.web.app.common.commonMap.CommonMap;
import com.web.app.common.intercetpor.UrlSessionInterceptor;
import com.web.app.common.util.StringUtil;
import net.sf.json.JSONArray;


public class ResponseAction {
    
    protected static Log log = LogFactory.getLog(UrlSessionInterceptor.class);
    
    /**
     * ������ URL �������� forwarding
     * 
     * @param forwardUrl
     *            �������� URL
     * @return
     */
    public static ModelAndView forwarding(String forwardUrl) {
        return forwarding(forwardUrl, null, null);
    }
    
    public static ModelAndView forwarding(String forwardUrl, CommonMap commonMap) {
        return forwarding(forwardUrl, commonMap, null);
    }
    
    /**
     * �޽����� ������ ������ forwarding
     * 
     * @param forwardUrl
     * @param commandMap
     * @param message
     *            ȭ�鿡 ǥ���� �޽���
     * @return
     */
    @SuppressWarnings("unchecked")
    public static ModelAndView forwarding(String forwardUrl, CommonMap commonMap, String message) {
        ModelAndView mav = null;
        
        // Program Code ã��
        String programCode = "";
        
        String path = forwardUrl;
        int idx = 0;
        
        if (idx > 0) {
            programCode = path.substring(idx, path.length());
            if (programCode.length() > 8) {
                programCode = programCode.substring(0, 8).replaceAll("-", ""); // HTML���� '-'�� �ٴ� ��� �������߻��ϱ� ������ ��������
            }
        }
        
        if (log.isDebugEnabled()) // log.debug("PROGRAM_CODE = " + programCode);
            
            if (commonMap != null && !commonMap.isEmpty()) {
                if (!StringUtil.isNull(message)) {
                    commonMap.put("message", message);
                }
            } else {
                commonMap = new CommonMap();
            }
            
        mav = new ModelAndView(forwardUrl, (Map)commonMap);
        
        if (log.isDebugEnabled()) {
            // log.debug("Forwarding... (" + forwardUrl + ")");
        }
        
        return mav;
    }
    
    /**
     * HashMap �����͸� JsonŸ������ ��ȯ�Ѵ�.
     * 
     * @param map
     *            HashMap
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ModelAndView returnHashMap(HashMap map) {
        return reponseJsonView(map, null);
    }
    
    /**
     * HashMap �����͸� JsonŸ������ ��ȯ��Ű��, �޽����� �����Ѵ�.<br>
     * �޽����� key�� "message"�̴�.
     * 
     * @param map
     * @param message
     *            �޽��� key
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ModelAndView returnHashMap(HashMap map, String message) {
        return reponseJsonView(map, message);
    }
    
    /**
     * Map �����͸� JsonŸ������ ��ȯ�Ѵ�.
     * 
     * @param map
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ModelAndView returnMap(Map map) {
        return reponseJsonView(map, null);
    }
    
    /**
     * Map �����͸� JsonŸ������ ��ȯ��Ű��, �޽����� �����Ѵ�.<br>
     * �޽����� key�� "message"�̴�.
     * 
     * @param map
     * @param message
     *            �޽��� key
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ModelAndView returnMap(Map map, String message) {
        return reponseJsonView(map, message);
    }
    
    /**
     * List �����͸� JsonŸ������ ��ȯ�Ѵ�.<br>
     * �������� key�� "rows"�̰� list row���� key�� "total" �̴�. <br>
     * <b>JSON ����</b> List list = new ArrayList();<br>
     * <br>
     * Map map1 = new HashMap();<br>
     * <br>
     * Map map2 = new HashMap();<br>
     * <br>
     * map2.put("productid", "item01");<br>
     * map2.put("productname", "������01");<br>
     * map2.put("unitcost", "10.00");<br>
     * map2.put("status", "P");<br>
     * map2.put("listprice", "36.50");<br>
     * map2.put("attr1", "Large");<br>
     * map2.put("itemid", "1234");<br>
     * <br>
     * list.add(map2);<br>
     * <br>
     * map1.put("rows", list);<br>
     * map1.put("total", "10");<br>
     * 
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ModelAndView returnDataSet(List list) {
        return returnDataSet(list, null, null);
    }
    
    /**
     * List �����͸� JsonŸ������ ��ȯ��Ű��, message�� �����Ѵ�.<br>
     * �������� key�� "rows"�̰� list row���� key�� "total" �̴�. * <br>
     * <b>JSON ���� : Map<String, List<Map<String, Object>></b><br>
     * ����) <br>
     * List list = new ArrayList();<br>
     * <br>
     * Map map1 = new HashMap();<br>
     * <br>
     * Map map2 = new HashMap();<br>
     * <br>
     * map2.put("productid", "item01");<br>
     * map2.put("productname", "������01");<br>
     * map2.put("unitcost", "10.00");<br>
     * map2.put("status", "P");<br>
     * map2.put("listprice", "36.50");<br>
     * map2.put("attr1", "Large");<br>
     * map2.put("itemid", "1234");<br>
     * <br>
     * list.add(map2);<br>
     * <br>
     * map1.put("rows", list);<br>
     * map1.put("total", "10");<br>
     * 
     * @param list
     * @param message
     *            �޽��� key
     * @return
     */
    @SuppressWarnings({ "rawtypes" })
    public static ModelAndView returnDataSet(List list, String message) {
        return returnDataSet(list, message, null);
    }
    
    /**
     * List �����͸� JsonŸ������ ��ȯ��Ű��, message�� �����Ѵ�.<br>
     * �������� key�� "rows"�̰� list row���� key�� "total", ���� footer�� key�� "footer" �̴�.
     * 
     * @param list
     *            data list
     * @param message
     *            view meesage
     * @param flist
     *            footer list
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static ModelAndView returnDataSet(List list, String message, List flist) {
        Map map = new HashMap();
        
        if (list != null && list.size() < 1) {
            Map emptyMap = new HashMap();
            
            emptyMap.put("empty", "MSG_NO_SEARCH_RESULT");
            
            list.add(emptyMap);
        }
        
        // easyUI�׸��忡 ���� jsonŸ���� ������ ����
        if (list != null && !list.isEmpty()) {
            map.put("rows", list);
            map.put("total", ((Map) list.get(0)).get("TOTALCOUNT"));
            
            // footer(�׸��� ���� �հ� ��) ����
            if (flist != null && !flist.isEmpty()) {
                map.put("footer", flist);
            }
            
        } else {
            map.put("rows", new ArrayList());
            map.put("total", 0);
        }
        
        return reponseJsonView(map, message);
    }
    
    /**
     * ������Ʈ Ÿ�Ժ��� json�����͸� ���� �� �����Ѵ�.
     * 
     * @param dataSet
     * @param message
     *            �޽��� key
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static ModelAndView reponseJsonView(Object obj, String message) {
        // if (log.isDebugEnabled()) //log.debug("reponseJsonView.....");
        List<Map<String, Object>> list = null;
        Map<String, Object> modelMap = new HashMap<String, Object>();
        
        if (obj instanceof List) {
            // input type = [{col1=value, col2=value},{col1=value, col2=value}]
            modelMap.put("rows", list);
            
            list = (List) obj;
        } else if (obj instanceof Map) {
            // input type = {rows=[{col1=value, col2=value},{col1=value, col2=value},{headers=[head1, head2], file=string, sheet=string},{empty=string}],total=string}
            Map mapData = (Map) obj;
            modelMap.putAll(mapData);
            
            Object objt = ((Map) obj).get("rows");
            if (objt != null) {
                list = (List) objt;
            }
        } else if (obj instanceof HashMap) {
            // input type = {rows=[{col1=value, col2=value},{col1=value, col2=value},{headers=[head1, head2], file=string, sheet=string},{empty=string}],total=string}
            modelMap.putAll((HashMap) obj);
            
            Object objt = ((HashMap) obj).get("rows");
            if (objt != null) {
                list = (List) objt;
            }
        }
        // ���������� ����� ���� ���� �߰�(2013.09.16)
        if (list != null && list.size() > 0) {
            int lastIndex = list.size() - 1;
            // 1. ������ �����Ϳ� �ش������� �ִ��� üũ
            if (list.get(lastIndex) != null && list.get(lastIndex) instanceof Map) {
                Map map = (Map) list.get(lastIndex);
                
                String file = StringUtil.null2void(map.get("file"));
                Object colObj = map.get("headers");
                
                // 2. �ش������� �ִٸ� ���� �ٿ�ε�� �����Ŵ
                if (colObj != null && colObj instanceof List) {
                    List colList = (List) colObj;
                    
                    if (log.isDebugEnabled()) // log.debug("header:" + colList.get(colList.size() - 1));
                        
                        if (colList != null && colList.size() > 0) {
                        return returnExcelView(Arrays.asList(list), file, message);
                        } else {
                        if (StringUtil.isNull(message)) {
                        message = "No search header info.";
                        }
                        }
                }
            }
        }
        
        modelMap.put("success", true);
        
        if (!StringUtil.isNull(message)) {
            modelMap.put("message", message);
        }
        
        ModelAndView mav = new ModelAndView("json", modelMap);
        
        return mav;
    }
    
    /**
     * ��ûó�� �������ο� �޽����� �����Ѵ�.
     * 
     * @param success
     * @param message
     *            �޽��� key
     * @return ModelAndView
     */
    public static ModelAndView resultJsonMsg(boolean success, String message) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        
        modelMap.put("success", success);
        
        if (!StringUtil.isNull(message)) {
            modelMap.put("message", message);
        }
        
        return new ModelAndView("json", modelMap);
    }
    
    /**
     * ���� �������� forwarding
     * 
     * @return ModelAndView
     */
    public static ModelAndView goMain() {
        return new ModelAndView("redirect:" + Constants.main.MAIN_VIEW);
    }
    
    /**
     * process ó�� ������ json �������� return (view.xml)-xmlFileViewResolver
     * 
     * @param String
     *            message
     * @return ModelAndView
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static ModelAndView describeMessage(String message) {
        Map result = new HashMap();
        
        result.put("message", message);
        
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        
        modelMap.put("total", 1);
        modelMap.put("rows", result);
        
        return new ModelAndView("json", modelMap);
        
    }
    
    /**
     * process ó�� ����� json �������� return (view.xml)-xmlFileViewResolver
     * 
     * @param String
     *            message
     * @return ModelAndView
     */
    public static ModelAndView processResult(String message) {
        
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        modelMap.put("success", true);
        
        if (!StringUtil.isNull(message)) {
            
            Map<String, Object> errorMap = new HashMap<String, Object>(2);
            errorMap.put("message", message);
            modelMap.put("messages", errorMap);
        }
        
        return new ModelAndView("json", modelMap);
    }
    
    /**
     * <code>List</code>�� �⺻ jsonŸ������ ��ȯ�� <code>String</code>��ü�� �����Ѵ�.<br>
     * 
     * @param list
     *            jsonŸ������ ��ȯ��ų ��ü
     * @return ModelAndView
     */
    public static ModelAndView returnJsonView(List<Map<String, Object>> list) {
        return returnJsonView(list, null);
    }
    
    /**
     * <code>List</code>�� �⺻ jsonŸ������ ��ȯ�� <code>String</code>��ü�� �����Ѵ�.<br>
     * 
     * @param list
     *            jsonŸ������ ��ȯ��ų ��ü
     * @param message
     *            ���� �޽���
     * @return ModelAndView
     */
    public static ModelAndView returnJsonView(List<Map<String, Object>> list, String message) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        String jsonString = null;
        
        if (!StringUtil.isNull(message)) {
            modelMap.put("success", false);
            modelMap.put("message", message);
            
            return new ModelAndView("json", modelMap);
        }
        
        try {
            JSONArray jsonObject = JSONArray.fromObject(list);
            
            jsonString = jsonObject.toString();
        } catch (Exception e) {
            jsonString = "";
        }
        
        modelMap.put("rows", jsonString);
        
        return new ModelAndView("jrows", modelMap);
    }
    
    /**
     * <code>File</code>�� �ٿ�ε� �Ѵ�.<br>
     * 
     * @param map
     *            File ��ü
     * @return ModelAndView
     */
    public static ModelAndView returnFileView(File file) {
        return returnFileView(file, null, null);
    }
    
    /**
     * <code>File</code>�� �ٿ�ε� �Ѵ�.<br>
     * 
     * @param map
     *            File ��ü
     * @param fname
     *            �ٿ�ε� �� ����� ������ ��Ī
     * @return ModelAndView
     */
    public static ModelAndView returnFileView(File file, String fname) {
        return returnFileView(file, fname, null);
    }
    
    /**
     * <code>File</code>�� �ٿ�ε� �Ѵ�.<br>
     * 
     * @param map
     *            File ��ü
     * @param fname
     *            �ٿ�ε� �� ����� ������ ��Ī
     * @param message
     *            ���� �޽���
     * @return ModelAndView
     */
    public static ModelAndView returnFileView(File file, String fname, String message) {
        return returnFileView((Object) file, fname, message);
    }
    
    /**
     * <code>File</code>�� �ٿ�ε� �Ѵ�.<br>
     * 
     * @param map
     *            FileInputStream ��ü
     * @return ModelAndView
     */
    public static ModelAndView returnFileView(FileInputStream stream) {
        return returnFileView(stream, null, null);
    }
    
    /**
     * <code>File</code>�� �ٿ�ε� �Ѵ�.<br>
     * 
     * @param map
     *            FileInputStream ��ü
     * @param fname
     *            �ٿ�ε� �� ����� ������ ��Ī
     * @return ModelAndView
     */
    public static ModelAndView returnFileView(FileInputStream stream, String fname) {
        return returnFileView(stream, fname, null);
    }
    
    /**
     * <code>File</code>�� �ٿ�ε� �Ѵ�.<br>
     * 
     * @param map
     *            FileInputStream ��ü
     * @param fname
     *            �ٿ�ε� �� ����� ������ ��Ī
     * @param message
     *            ���� �޽���
     * @return ModelAndView
     */
    public static ModelAndView returnFileView(FileInputStream stream, String fname, String message) {
        return returnFileView((Object) stream, fname, message);
    }
    
    /**
     * <code>File</code>�� �ٿ�ε� �Ѵ�.<br>
     * 
     * @param map
     *            byte[] ��ü
     * @return ModelAndView
     */
    public static ModelAndView returnFileView(byte[] data) {
        return returnFileView(data, null, null);
    }
    
    /**
     * <code>File</code>�� �ٿ�ε� �Ѵ�.<br>
     * 
     * @param map
     *            byte[] ��ü
     * @param fname
     *            �ٿ�ε� �� ����� ������ ��Ī
     * @return ModelAndView
     */
    public static ModelAndView returnFileView(byte[] data, String fname) {
        return returnFileView(data, fname, null);
    }
    
    /**
     * <code>File</code>�� �ٿ�ε� �Ѵ�.<br>
     * 
     * @param map
     *            byte[] ��ü
     * @param fname
     *            �ٿ�ε� �� ����� ������ ��Ī
     * @param message
     *            ���� �޽���
     * @return ModelAndView
     */
    public static ModelAndView returnFileView(byte[] data, String fname, String message) {
        return returnFileView((Object) data, fname, message);
    }
    
    /**
     * <code>File</code>�� �ٿ�ε� �Ѵ�.<br>
     * 
     * @param map
     *            ���� ���� ��ü
     * @param fname
     *            �ٿ�ε� �� ����� ������ ��Ī
     * @param message
     *            ���� �޽���
     * @return ModelAndView
     */
    private static ModelAndView returnFileView(Object obj, String fname, String message) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        
        if (!StringUtil.isNull(message)) {
            modelMap.put("success", false);
            modelMap.put("message", message);
            
            return new ModelAndView("json", modelMap);
        }
        
        modelMap.put("file", obj);
        modelMap.put("fileName", fname);
        
        return new ModelAndView("file", modelMap);
    }
    
    /**
     * <p>
     * <code>Excel File</code>�� �ٿ�ε� �Ѵ�.<br>
     * ��, ���� �޽����� ��ϵǾ� �ִ� ��쿡�� �޽����� ���ϵȴ�.
     * </p>
     * 
     * @param list
     *            �������Ϸ� ������ List
     * @return ModelAndView
     */
    public static ModelAndView returnExcelView(List<List<Map<String, Object>>> list) {
        return returnExcelView(list, null, null);
    }
    
    /**
     * <p>
     * <code>Excel File</code>�� �ٿ�ε� �Ѵ�.<br>
     * ��, ���� �޽����� ��ϵǾ� �ִ� ��쿡�� �޽����� ���ϵȴ�.
     * </p>
     * 
     * @param list
     *            �������Ϸ� ������ List
     * @param fname
     *            �ٿ�ε� �� ����� ������ ��Ī
     * @return ModelAndView
     */
    public static ModelAndView returnExcelView(List<List<Map<String, Object>>> list, String fname) {
        return returnExcelView(list, fname, null);
    }
    
    /**
     * <p>
     * <code>Excel File</code>�� �ٿ�ε� �Ѵ�.<br>
     * ��, ���� �޽����� ��ϵǾ� �ִ� ��쿡�� �޽����� ���ϵȴ�.
     * </p>
     * 
     * @param list
     *            �������Ϸ� ������ List(List<Map> �Ǵ� List<List<Map>> ����)<br>
     *            Map �ȿ� ��ϵǴ� ������<br>
     *            rows = ������ row data (type = Map)<br>
     *            ,headers = ��Ʈ�� header ���� (type = List)<br>
     *            ,headers.sheet = ��Ʈ�� (type = String)<br>
     * @param fname
     *            �ٿ�ε� �� ����� ������ ��Ī
     * @param message
     *            ���� �޽���
     * @return ModelAndView
     */
    public static ModelAndView returnExcelView(List<List<Map<String, Object>>> list, String fname, String message) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        
        if (!StringUtil.isNull(message)) {
            modelMap.put("success", false);
            modelMap.put("message", message);
            
            return new ModelAndView("json", modelMap);
        }
        
        // list type = [[{col1=value, col2=value},{col1=value, col2=value},{headers=[head1, head2], file=string, sheet=string}],
        // ,[{col1=value, col2=value},{col1=value, col2=value},{headers=[head1, head2], file=string, sheet=string}]]
        modelMap.put("list", list);
        modelMap.put("fileName", StringUtil.null2void(fname));
        
        return new ModelAndView("excel", modelMap);
    }
}
