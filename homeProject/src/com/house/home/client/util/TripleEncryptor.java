package com.house.home.client.util;

import org.apache.commons.lang3.StringUtils;

public class TripleEncryptor implements ITokenEncryptor {

	public String getDesString(String strMi, String secretKey) {
		if (!StringUtils.isBlank(strMi) && !StringUtils.isBlank(secretKey)) {
			return TripleDES.getDesString(strMi, secretKey);
		}
		return null;
	}

	public String getEncString(String strMing, String secretKey) {
		if (!StringUtils.isBlank(strMing) && !StringUtils.isBlank(secretKey)) {
			return TripleDES.getEncString(strMing, secretKey);
		}
		return null;
	}

}
