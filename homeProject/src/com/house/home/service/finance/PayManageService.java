package com.house.home.service.finance;

import java.util.List;
import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.SupplierPrepay;
import com.house.home.entity.finance.SupplierPrepayDetail;
import com.house.home.entity.insales.Supplier;


public interface PayManageService extends BaseService {

	/**payManage分页信息
	 * @param page
	 * @param payManage
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SupplierPrepay supplierPrepay);
	
	public Result dopayManageSave(SupplierPrepay supplierPrepay);
	/**payManage保存
	 * @param page
	 * @param payManage
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlDetail(Page<Map<String,Object>> page, SupplierPrepayDetail supplierPrepayDetail);
	/**payManage分页信息
	 * @param page
	 * @param payManage明细表显示
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlMxSelect(Page<Map<String,Object>> page, SupplierPrepay supplierPrepay);
	/**payManage
	 * @param page
	 * @param payManage明细查询表格
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlYeSelect(Page<Map<String,Object>> page, SupplierPrepay supplierPrepay);
	/**payManage
	 * @param page
	 * @param payManage余额查询表格
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridYeChangeSelect(Page<Map<String,Object>> page, Supplier supplier);
	/**payManage
	 * @param page
	 * @param payManage余额查询变动明细表
	 * @return
	 */
	
	public Page<Map<String,Object>> getSupplAccountJqGrid(Page<Map<String,Object>> page, SupplierPrepay supplierPrepay);
	
	public List<Map<String, Object>> getDetailOrderBySupplCode(SupplierPrepay supplierPrepay);
	
	public Page<Map<String,Object>> findProcListJqGrid(Page<Map<String,Object>> page, SupplierPrepay supplierPrepay);
	
	/**
	 * 采购记录
	 * @param page
	 * @param supplierPrepay
	 * @return
	 */
	public Page<Map<String, Object>> getPuJqGrid(Page<Map<String,Object>> page, SupplierPrepay supplierPrepay);
	
	public String getPunos(String no, String supplCode);

}

