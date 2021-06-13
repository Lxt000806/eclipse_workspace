package com.house.framework.web.token;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.utils.RedisUtil;
import com.house.framework.web.login.UserContext;

/**
 * 
 * @ClassName: DenyDuplicateFormSubmitInterceptor 
 * @Description: token拦截器 
 *
 */
public class DenyDuplicateFormSubmitInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
    private FormTokenManager formTokeManager;
	
	/**
	 * 防止重复提交
	 * 利用redis单句指令的原子性，不管是多线程、分布式、集群，同一个key只有一次返回true
	 * @param key 页面传递过来的tokenId
	 * @return
	 */
	public Boolean setExpireNx(final String key) {
		RedisConnection connection = RedisUtil.getRedisConnection();
		if (connection == null) return true; // 未安装redis，此处直接通过
		
		try {
			connection.select(0);
			
			String value = UUID.randomUUID().toString();
			connection.setNX(key.getBytes(), value.getBytes());
			connection.expire(key.getBytes(), 60L);
			byte[] nx = connection.get(key.getBytes());
			if(nx != null) {
				String result = new String(nx);
				if (value.equals(result)) {
					return true;
				}
			}
			return false;
		} finally {
			if (connection != null) connection.close();
		}
	}
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  
                             Object handler) throws Exception {  
        boolean flag = true;
        String token = null;
        if(StringUtils.isNotBlank(request.getHeader("App-TokenId"))){
             token = request.getHeader("App-TokenId");//request.getParameter(FormToken.FORM_TOKEN_UNIQ_ID);
        }else{
        	 token = request.getParameter(FormToken.FORM_TOKEN_UNIQ_ID);
        }
        String uri = request.getRequestURI();
        if (uri.indexOf("/doExcel")>=0){
        	return flag;
        }
        UserContext uc=(UserContext)request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
        String czybh="DenyDuplicate";
        if(uc!=null){
        	czybh+=uc.getCzybh();
        }
		if (token != null) {
			if (!setExpireNx(token)) return false; //利用redis防止重复提交
			
			synchronized (czybh.intern()) {
				if (formTokeManager.hasFormToken(request, token)) {
					formTokeManager.destroyToken(request, token);
				} else {
					flag = false;
					// throw new DuplicateFormSubmitException("表单重复提交或失效，令牌[ " + token + " ]");
				}
			}
		}
        return flag;  
    }  
    
    
    
    
}
