package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.WorkType12;
import com.house.home.entity.project.WorkType12Item;

public interface WorkType12Service extends BaseService {

	/**WorkType12分页信息
	 * @param page
	 * @param workType12
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkType12 workType12);
	
	public List<WorkType12> findByNoExpired();
	
	public Map<String,Object> getWorkType12InfoByCode(String code);
	
	public List<Map<String,Object>> findWorkType12Dept(int type,String pCode,UserContext uc);
	
	public Map<String, Object> findViewInfo(String id);
	
	public List<Map<String, Object>> findBefWorkType(String id);
	
	public List<Map<String, Object>> findWorkType12Item(String id);
	
	public void addWorkType12Item(WorkType12Item workType12Item);
	
	public void deleteWorkType12Item(Integer pk);
	/**
	 * 工艺列表
	 * @author	created by zb
	 * @date	2019-4-30--上午11:20:55
	 * @param page
	 * @param workType12
	 * @return
	 */
	public Page<Map<String,Object>> getTechBySql(Page<Map<String, Object>> page,
			WorkType12 workType12);
	/**
	 * 获取是否上传图片模板为是的工种
	 * @author	created by zb
	 * @date	2019-5-1--下午3:12:06
	 * @return
	 */
	public List<Map<String, Object>> getTechWorkType12();
	
	public List<Map<String, Object>> getWorkType12List();
	
	public Page<Map<String, Object>> goQualityJqGrid(Page<Map<String, Object>> page, WorkType12 workType12);
	
	public void doAddQlt(WorkType12 workType12);
	
	public void doUpdateQlt(WorkType12 workType12);
	
	public void doDeleteQlt(WorkType12 workType12);
}
