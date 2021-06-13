package com.house.home.service.project;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CustItemConfDate;
import com.house.home.entity.project.CustItemConfirm;

public interface CustItemConfirmService extends BaseService {

	/**CustItemConfirm分页信息
	 * @param page
	 * @param custItemConfirm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfirm custItemConfirm);
	/**
	 * 材料确认
	 * @param custItemConfDate
	 */
	public void doItemConfirm(CustItemConfDate custItemConfDate);
	/**
	 * 材料取消
	 * @param custItemConfDate
	 */
	public void doItemCancel(CustItemConfDate custItemConfDate);
	public CustItemConfirm getCustItemConfirm(String custCode,String confItemType);
	public Page<Map<String,Object>> findConfirmResultPageBySql(Page<Map<String, Object>> page,
			String custCode);
	public Page<Map<String,Object>> findConfirmStatusPageBySql(Page<Map<String, Object>> page,
			String custCode);
	/**
	 * 查询客户材料选定信息（工地进度拖延分析查看用）
	 * @author	created by zb
	 * @date	2019-11-8--下午2:49:16
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Page<Map<String,Object>> findCustConfirmResultPageBySql(Page<Map<String, Object>> page,
			String custCode);
}
