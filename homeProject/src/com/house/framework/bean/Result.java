package com.house.framework.bean;

import java.io.Serializable;

/**
 *功能说明:返回信息bean
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;

    private String info;
    
    private String json;//json数组字符串

    public static final String SUCCESS_CODE = "1";
    public static final String SUCCESS_INFO = "操作成功";
    public static final String FAIL_CODE = "0";
    public static final String FAIL_INFO = "操作失败";

    public static final Result SUCCESS = new Result(SUCCESS_CODE, SUCCESS_INFO);
    public static final Result FAIL = new Result(FAIL_CODE, FAIL_INFO);

    public Result() {
    	this(FAIL_CODE, FAIL_INFO);
    }

    public Result(String code) {
        this.code = code;
    }

    public Result(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public boolean isSuccess() {
        if (code != null && SUCCESS_CODE.equals(code))
            return true;
        return false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setResult(Result result){
    	if(result != null){
    		this.setCode(result.getCode());
    		this.setInfo(result.getInfo());
    	}
    }

    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                '}';
    }

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
