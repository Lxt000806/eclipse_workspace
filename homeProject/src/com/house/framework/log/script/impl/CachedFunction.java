package com.house.framework.log.script.impl;

import java.util.HashMap;
import java.util.Map;
import com.house.framework.log.script.Function;
import com.house.framework.log.script.FunctionUtil;
import com.house.framework.log.script.ScriptException;

public class CachedFunction implements Function {

	private static final Map<String, StringFunction> cache = new HashMap<String, StringFunction>();
	
	private String functionString;
	
	public CachedFunction(String functionString) {
		this.functionString = functionString;
	}
	
	@SuppressWarnings("unused")
	public Object execute(Object object, Object[] arguments, Map<String, Object> context) throws ScriptException {
		StringFunction ret = cache.get(functionString);
		if(ret != null) {
			return ret.execute(object, arguments, context);
		}
		synchronized(FunctionUtil.class) {
			if(ret != null) {
				return ret.execute(object, arguments, context);
			}
			ret = new StringFunction(functionString);
			ret.parse(object.getClass());
			cache.put(functionString, ret);
		}
		return ret.execute(object, arguments, context);
	}

}
