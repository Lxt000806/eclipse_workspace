package com.house.framework.commons.orm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.house.framework.annotation.DataAuthorityAnnotation;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.web.login.UserContext;

public class DataAuthorityAspect {

	/**
	 * 切面执行函数
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings({ "finally", "unchecked" })
	public Object simpleAdvice(ProceedingJoinPoint joinPoint){
		Object returnObj = null;
		try{
			Object[] args = joinPoint.getArgs(); //获取目标方法参数
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();//获取目标方法的签名
			Method method = signature.getMethod();//获取目标方法实体
			returnObj = joinPoint.proceed(args);//执行目标方法
			if(method.isAnnotationPresent(DataAuthorityAnnotation.class)){ 
				HttpServletRequest request = null;
				UserContext userContext = null;
				
				// 获取requeset
				for(int i = 0;i < args.length;i++){
					if(args[i] instanceof HttpServletRequest){
						request = (HttpServletRequest) args[i];
					}
				}
				
				// 根据request获取userContext
				if(request != null){
					userContext = (UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
				}
				
				if(userContext == null){
					userContext = new UserContext();
				}
				
				Map<String, Object> costRightMap = null;
				DataAuthorityAnnotation dataAuthorityAnnotation = (DataAuthorityAnnotation)method.getAnnotation(DataAuthorityAnnotation.class);  
				if(dataAuthorityAnnotation != null){
					// 获取成本权限Map
					costRightMap = this.getAuthorityMap(dataAuthorityAnnotation.costRight());
				}
				
				WebPage<Map<String,Object>> webPage = (WebPage<Map<String,Object>>) returnObj;
				
				// 存在权限配置,移除相应列
				if(!costRightMap.isEmpty()){
					webPage.setHideColumns(this.setHideColumns(costRightMap, webPage.getRows(), userContext.getCostRight()));
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return returnObj;
		}
	}
	
	/**
	 * 获取权限Map
	 * @param authority 权限Map字符串
	 * @return
	 */
	public Map<String, Object> getAuthorityMap(String authority){
		Map<String, Object> authorityMap = new HashMap<String, Object>();
		if(authority != null && authority.length() > 0 && authority.indexOf("{") != -1 && authority.lastIndexOf("}") != -1 && authority.indexOf("{") < authority.lastIndexOf("}")){
			authority = authority.substring(authority.indexOf("{")+1, authority.lastIndexOf("}"));
			String[] caseStrs = authority.split(",");
			for(int i = 0;i < caseStrs.length;i++){
				if(caseStrs[i].indexOf("=") != -1){
					String key = caseStrs[i].split("=")[0];
					String value = caseStrs[i].split("=")[1];
					authorityMap.put(key, value);
				}
			}
		}
		return authorityMap;
	}
	
	/**
	 * 移除隐藏列
	 * @param authorityMap 权限Map
	 * @param list 数据数组
	 * @param authority 操作员权限
	 * @return
	 */
	public String setHideColumns(Map<String, Object> authorityMap, List<Map<String, Object>> list, String authority){
		// 权限异常,清空数据集
		if(StringUtils.isBlank(authority)){
			list = new ArrayList<Map<String,Object>>();
			return "";
		}
		Object authorityObject = authorityMap.get(authority != null ? authority: "");
		if(authorityObject == null){
			return "";
		}
		String hideColumnsStr = authorityObject.toString();
		String[] hideColumns = hideColumnsStr.split("\\|");
		for(int i = 0;i < list.size();i++){
			for(int j = 0;j < hideColumns.length;j++){
				if(list.get(i).containsKey(hideColumns[j])){
					list.get(i).remove(hideColumns[j]);
				}
			}
		}
		return hideColumnsStr;
	}
}
