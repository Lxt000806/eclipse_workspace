package com.house.framework.log.script.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.house.framework.log.script.Function;
import com.house.framework.log.script.FunctionUtil;
import com.house.framework.log.script.Script;
import com.house.framework.log.script.ScriptException;
import com.house.framework.log.util.StringUtil;

public class MutilFunctionScript implements Script {
	
	private String scriptString;
	
	private List<Function> functions = new ArrayList<Function>();
	
	public MutilFunctionScript(String scriptString) {
		this.scriptString = scriptString;
		parse();
	}
	
	private void parse() {
		if(StringUtil.isEmpty(scriptString)) {
			return;
		}
		ArrayList<Function> parsedFunctions = new ArrayList<Function>();
		List<String> strFuns = StringUtil.split(scriptString, ";");
		for(String strFun : strFuns) {
			strFun = StringUtil.trimToEmpty(strFun);
			if(StringUtil.isEmpty(strFun)) {
				continue;
			}
			parsedFunctions.add(FunctionUtil.createCachedFunction(strFun));
		}
		functions = parsedFunctions;
	}
	
	public Object execute(Object object, Object[] arguments,
			Map<String, Object> context) throws ScriptException {
		Object ret = null;
		for(Function fun : functions) {
			ret = fun.execute(object, arguments, context);
		}
		return ret;
	}

}
