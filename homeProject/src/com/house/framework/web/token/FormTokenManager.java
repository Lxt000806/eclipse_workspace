package com.house.framework.web.token;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName: FormTokenManager 
 * @Description: 表单管理器，负责管理Session中的表单token。
 *
 */
public interface FormTokenManager {
    /** 
     * 生成一个新的表单 
     */  
    public FormToken newFormToken(HttpServletRequest request);
    
    /** 
     * 判断表单是否存在。 
     */  
    public boolean hasFormToken(HttpServletRequest request, String token);  
    
    /** 
     * 访问参数中是否存在表单Token。 
     */  
    public boolean hasTokenFromSubmit(HttpServletRequest request);  
    
    /** 
     * 销毁一个表单 
     */  
    public void destroyToken(HttpServletRequest request, String token);  
    
    /** 
     * 打印表单信息。 
     */  
    public String dumpFormToken(HttpServletRequest request, String token);  
}
