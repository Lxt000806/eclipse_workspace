package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.MtAddCustInfoEvt;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.entity.basic.MtCustInfo;
import com.house.home.entity.design.Customer;

public interface MtCustInfoService extends BaseService {
	
	public void addMtCustInfo(MtAddCustInfoEvt evt, BaseResponse respon);
	
	public boolean existsCustCodeMT(String custCodeMT);
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MtCustInfo mtCustInfo);

	public void cancel(MtCustInfo mtCustInfo);
	
	public Map<String, Object> getMtPerfNoSend();
	
	public Page<Map<String,Object>> goMtRegionJqGrid(Page<Map<String,Object>> page);
	
	public void setCzy(MtCustInfo mtCustInfo);
	
	public Page<Map<String,Object>> findCustConPageBySql(Page<Map<String,Object>> page, MtCustInfo mtCustInfo);
	
	public String getCustCodeByPk(MtCustInfo mtCustInfo);
	
	public List<Map<String, Object>> getCustPerfData(Customer customer);
}
