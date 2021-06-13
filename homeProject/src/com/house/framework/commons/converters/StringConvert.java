package com.house.framework.commons.converters;

import org.apache.commons.beanutils.Converter;

public class StringConvert implements Converter {

    @SuppressWarnings("rawtypes")
	public Object convert(Class arg0, Object arg1) {
    	String str = null;
    	if(arg1 != null) {
    		str = String.valueOf(arg1);
    	}
		return str == null ? null : str.trim();
    }
}
