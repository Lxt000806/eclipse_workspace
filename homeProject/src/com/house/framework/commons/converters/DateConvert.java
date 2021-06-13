package com.house.framework.commons.converters;

import java.util.Date;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;

public class DateConvert implements Converter {

	 @SuppressWarnings("rawtypes")
	 public Object convert(Class arg0, Object arg1) {
		 if (arg1 == null) {
			 return null;
         }
		 if ((arg1 instanceof String) && StringUtils.isBlank((String)arg1)){  
			 return null;
	     }
		 Date date = null;
		 try{
			 date = (Date) arg1;
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return date == null ? null : date;
		 
	 }
//	@SuppressWarnings("rawtypes")
//	public Object convert(Class arg0, Object arg1) {
//		String p = (String) arg1;
//		if (p == null || p.trim().length() == 0) {
//			return null;
//		}
//		try {
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			return df.parse(p.trim());
//		} catch (Exception e) {
//			try {
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//				return df.parse(p.trim());
//			} catch (Exception ex) {
//				return null;
//			}
//		}
//
//	}
}
