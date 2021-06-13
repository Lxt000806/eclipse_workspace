package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Information;

public interface InformationService extends BaseService {

	/**Information分页信息
	 * @param page
	 * @param information
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Information information);
	
	public Page<Map<String,Object>> findPageBySqlForInfo(Page<Map<String,Object>> page, Information information);
	
	public Long getCount(Information information);
	
	public Map<String,Object> getByNumber(String number);	
	
	public Page<Map<String,Object>> findInfoReadPageBySql(Page<Map<String,Object>> page, Information information);
	
	public Page<Map<String,Object>> findInfoAttachPageBySql(Page<Map<String,Object>> page, Information information);
	
	public List<Map<String,Object>> getChildrenNodes(String higherDept);
	
	public List<Map<String,Object>> getDepEmpNodes();
	
	public List<Map<String,Object>> getReceiveNodes(String infoNum);
	
	public Result doSave(Information information);
	
	public String getNeedSendInfomation(String czybm);
}
