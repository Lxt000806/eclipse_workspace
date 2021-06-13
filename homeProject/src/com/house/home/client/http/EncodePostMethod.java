package com.house.home.client.http;

import org.apache.commons.httpclient.methods.PostMethod;

public class EncodePostMethod extends PostMethod {
	
	private static final String charset = "utf-8";

	public EncodePostMethod(String url){
		super(url);
	}
	
    @Override
    public String getRequestCharSet() {
        return charset;
    }
}
