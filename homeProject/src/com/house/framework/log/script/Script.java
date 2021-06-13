package com.house.framework.log.script;

import java.util.Map;

public interface Script {
	
	Object execute(Object object, Object[] arguments, Map<String, Object> context) throws ScriptException;

}
