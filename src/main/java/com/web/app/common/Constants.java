package com.web.app.common;

public class Constants {
    
    public static final String COMMON_MAP = "commonMap"; // 변경하지 말 것
    
    public static final String KEY_COMPANY_CODE = "COMPANY_CODE";
    
    public static final String KEY_LOGIN_ID = "LOGIN_ID";

    public static final String KEY_USER_ID = "USER_ID";
    
    // 사용권한정보
    public static final String KEY_SEL_AUTH = "SEL_AUTH";

    public static final String KEY_REG_AUTH = "REG_AUTH";

    public static final String KEY_UPD_AUTH = "UPD_AUTH";

    public static final String KEY_DEL_AUTH = "DEL_AUTH";

    public static final String KEY_PNT_AUTH = "PNT_AUTH";

    public static final String KEY_EXL_AUTH = "EXL_AUTH";

    public static final String KEY_FLE_AUTH = "FLE_AUTH";

    public static final String KEY_EXC_AUTH = "EXC_AUTH";
    
    public static final String KEY_CREATE_BY = "CREATE_BY";

    public static final String KEY_UPDATE_BY = "UPDATE_BY";
    
    public static final String KEY_PREFIX_NAME = "PREFIX_NAME";

    public static final String KEY_S_CERTIFY_TYPE = "S_CERTIFY_TYPE";
    
    public static class main {
        
        public static final String MAIN_VIEW = "/main/mainMove.do";
        
    }
    
    public static class session {
        
        public static final String KEY_USER_SESSION = "_sessionUser";
        
        public static final int SESSION_TIMEOUT = 60 * 60;
        
    }
}
