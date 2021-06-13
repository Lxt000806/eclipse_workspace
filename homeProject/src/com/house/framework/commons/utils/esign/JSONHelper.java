package com.house.framework.commons.utils.esign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @description JSON 处理辅助类
 * @author 宫清
 * @date 2019年7月20日 下午5:35:31
 * @since JDK1.7
 */
public class JSONHelper {
	
	private static Logger logger = LoggerFactory.getLogger(JSONHelper.class);
	
	private JSONHelper() {
	}

	/**
	 * @description 格式化json data数据
	 *
	 * @param json
	 * @return
	 * @throws DefineException
	 * @author 宫清
	 * @date 2019年7月20日 下午5:34:38
	 */
	@SuppressWarnings("unchecked")
	public static <T> T castDataJson(JSONObject json,Class<T> clz)  {
		Object obj = json.get("data");
		int code = json.getIntValue("code");
		if (code != 0 && obj == null) {
			logger.error(json.getString("message"));
		}
		return (T)obj;
	}
}
