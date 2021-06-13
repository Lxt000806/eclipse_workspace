package com.house.framework.log.script;

import java.util.Map;
import com.house.framework.log.script.impl.MutilFunctionScript;

public class ScriptUtil {
	
	public static Object execute(String scripts, Object object, Object[] arguments, Map<String, Object> context) {
		MutilFunctionScript script = new MutilFunctionScript(scripts);
		return script.execute(object, arguments, context);
	}

}
