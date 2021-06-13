package com.house.framework.log.script;

import com.house.framework.log.script.impl.CachedFunction;

public class FunctionUtil {
	
	public static Function createCachedFunction(String functionString) {
		return new CachedFunction(functionString);
	}
	

}
