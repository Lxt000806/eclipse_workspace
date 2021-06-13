package com.house.home.client.service.resp;

/**
 * 
 *功能说明:查询返回基类
 *
 */
public class BaseQueryResp extends BaseResponse{
	
	//返回结果集总数
    private Long recordSum;

	public Long getRecordSum() {
		return recordSum;
	}

	public void setRecordSum(Long recordSum) {
		this.recordSum = recordSum;
	}

}
