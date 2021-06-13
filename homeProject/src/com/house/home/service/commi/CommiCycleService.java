package com.house.home.service.commi;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiCustStakeholderSuppl;
import com.house.home.entity.commi.CommiCycle;

public interface CommiCycleService extends BaseService {

	/**CommiCycle分页信息
	 * @param page
	 * @param commiCycle
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCycle commiCycle);
	
	/**
	 * 查询计算状态
	 * 
	 * @param no
	 * @return
	 */
	public String checkStatus(String no);
	
	/**
	 * 检查周期
	 * 
	 * @param no
	 * @param beginDate
	 * @return
	 */
	public String isExistsPeriod(String no, Integer mon);
	
	/**
	 * 计算完成
	 * 
	 * @param no
	 */
	public void doComplete(String no);
	
	/**
	 * 退回
	 * 
	 * @param no
	 */
	public void doReturn(String no);
	
	/**
	 * 生成提成数据
	 * 
	 * @param commiCycle
	 */
	public Map<String, Object> doCount(CommiCycle commiCycle);

	/**
	 * 商家返利信息
	 * 
	 * @param page
	 * @param commiCustStakeholderSuppl
	 * @return
	 */
    public Page<Map<String,Object>> findSupplierRebatePageBySql(Page<Map<String, Object>> page,
            CommiCustStakeholderSuppl commiCustStakeholderSuppl);

    /**
     * 商家返利信息Excel数据导入
     * 
     * @param objs
     */
    public void doImportExcelForSupplierRebate(List<CommiCustStakeholderSuppl> objs, String czybh);
    
    /**
	 * 获取最近计算完成的月份
	 * 
	 * @param mon
	 * @return
	 */
	public String getMaxMon();
}
