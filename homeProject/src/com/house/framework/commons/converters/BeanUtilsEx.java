package com.house.framework.commons.converters;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 修改org.apache.commons.beanutils.BeanUtils不能转化时间的bug
 */
public class BeanUtilsEx extends BeanUtils {
	static {
		ConvertUtils.register(new DateConvert(), java.util.Date.class);
		ConvertUtils.register(new DateConvert(), java.sql.Date.class);
		ConvertUtils.register(new DateConvert(), java.sql.Timestamp.class);
		ConvertUtils.register(new StringConvert(), java.lang.String.class);
	}

	public static void copyProperties(Object dest, Object orig) {
		try {
			if (orig==null){
				return;
			}
			if ((orig instanceof String) && StringUtils.isBlank((String)orig)){  
	            return;
	        }
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		}
	}
}
