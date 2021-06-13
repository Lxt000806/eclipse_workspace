package com.house.framework.commons.workflow;

import java.util.HashMap;
import java.util.Map;
import jodd.util.StringUtil;
import org.apache.commons.beanutils.ConvertUtils;

public class Variable {

	private String keys;
	private String values;
	private String types;

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Map<String, Object> getVariableMap() {
		Map<String, Object> vars = new HashMap<String, Object>();

		ConvertUtils.register(new DateConverter(), java.util.Date.class);

		if (StringUtil.isBlank(keys)) {
			return vars;
		}

		String[] arrayKey = keys.split(",");
		String[] arrayValue = values.split(",");
		String[] arrayType = types.split(",");
		for (int i = 0; i < arrayKey.length; i++) {
			String key = arrayKey[i];
			String value = arrayValue[i];
			String type = arrayType[i];
			if ("null".equals(value)){
				vars.put(key, null);
			}else{
				Class<?> targetType = Enum.valueOf(PropertyType.class, type).getValue();
				Object objectValue = ConvertUtils.convert(value, targetType);
				vars.put(key, objectValue);
			}
		}
		return vars;
	}
	public static void main(String[] args) {
		String a = "a,b,c,d,,,";
		String[] str = a.split(",");
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}
		System.out.println(String.valueOf(null));
	}

}
