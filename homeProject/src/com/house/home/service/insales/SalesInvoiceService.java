package com.house.home.service.insales;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.SaleCust;
import com.house.home.entity.insales.ItemWHBal;
import com.house.home.entity.insales.SalesInvoice;

public interface SalesInvoiceService extends BaseService {
	/**
	 *SalesInvoice
	 *@param page
	 *@param purchase
	 *@return
	 **/
	public Page<Map<String , Object>>findPageBySql(Page<Map<String , Object>> page,SalesInvoice salesInvoice, Czybm czybm);
	
	/**
	 * @Description:  销售订单管理分页查询
	 * @author	created by zb
	 * @date	2018-9-14--下午3:03:46
	 * @param page
	 * @param salesInvoice
	 * @return
	 */
	public Page<Map<String , Object>> findSalesInvoicePageBySql(Page<Map<String, Object>> page,
			SalesInvoice salesInvoice);

	/**
	 * @Description:  销售客户新增
	 * @author	created by zb
	 * @date	2018-9-15--上午11:30:53
	 * @param saleCust
	 * @return
	 */
	public Result doCustSave(SaleCust saleCust);
	
	/**
	 * @Description:  根据操作员编号获取员工信息
	 * @author	created by zb
	 * @date	2018-9-17--下午2:57:34
	 * @param czybh
	 * @return
	 */
	public Employee getEmpDescrByCZYBH(String czybh);

	/**
	 * @Description:  是否授权查询
	 * @author	created by zb
	 * @date	2018-9-18--下午4:23:01
	 * @param no
	 * @return
	 */
	public boolean isAuthorized(String no);

	/**
	 * @Description:  库存结余分页查询
	 * @author	created by zb
	 * @date	2018-9-19--上午9:30:58
	 * @param page
	 * @param itemWHBal
	 * @return
	 */
	public Page<Map<String , Object>> findItemWHBalPageBySql(Page<Map<String, Object>> page,
			ItemWHBal itemWHBal);

	/**
	 * @Description:  根据两个编码获取数量
	 * @author	created by zb
	 * @date	2018-9-19--下午2:53:35
	 * @param itCode
	 * @param whCode
	 * @return
	 */
	public Map<String, Object> getQtyNow(String itCode, String whCode);

	/**
	 * @Description:  销售单存储过程
	 * @author	created by zb
	 * @date	2018-9-25--上午10:01:51
	 * @param salesInvoice
	 * @return
	 */
	public Result doSalesOrder(SalesInvoice salesInvoice);

	/**
	 * @Description:  采购单明细分页查询
	 * @author	created by zb
	 * @date	2018-9-26--下午5:06:44
	 * @param page
	 * @param sino
	 */
	public Page<Map<String , Object>> findPurDetailPageBySql(Page<Map<String, Object>> page,
			String sino);
	
	/**
	 * @Description:  批次导入——获取批次编号
	 * @author	created by zb
	 * @date	2018-9-27--下午5:13:35
	 * @param itemType1
	 * @param czybhCode
	 * @return
	 */
	public Map<String, Object> getItemBatchHeader(String itemType1,
			String czybhCode);

	/**
	 * @Description:  批次材料分页查询
	 * @author	created by zb
	 * @date	2018-9-27--下午6:03:06
	 * @param page
	 * @param ibdno
	 * @return
	 */
	public Page<Map<String , Object>> findItemBatchPageBySql(Page<Map<String, Object>> page,
			String ibdno);

	/**
	 * @Description:  根据材料编号获取材料信息
	 * @author	created by zb
	 * @date	2018-9-28--上午9:50:25
	 * @param itCode
	 * @return
	 */
	public Map<String, Object> getItemInfo(String itCode);

	/**
	 * @Description:  销售明细查询——销售订单管理用
	 * @author	created by zb
	 * @date	2018-9-28--下午4:13:19
	 * @param page
	 * @param sino
	 * @return
	 */
	public Page<Map<String , Object>> findSalesInvoiceDetailPageBySql(Page<Map<String, Object>> page,
			String sino);

	/**
	 * @Description:  销售单明细分页查询
	 * @author	created by zb
	 * @date	2018-10-17--上午11:13:02
	 * @param page
	 * @param no
	 * @return
	 */
	public Page<Map<String , Object>> findDetailPageBySql(Page<Map<String, Object>> page, String no);

	/**
	 * @Description:  根据no获取采购记录
	 * @author	created by zb
	 * @date	2018-10-18--下午5:26:09
	 * @param no
	 * @return
	 */
	public Boolean getPurchaseCount(String no);

	/**
	 * @Description:  明细分页查询
	 * @author	created by zb
	 * @date	2018-10-26--上午11:48:30
	 * @param page
	 * @param salesInvoice
	 * @return
	 */
	public Page<Map<String , Object>> findDetailViewPageBySql(Page<Map<String, Object>> page,
			SalesInvoice salesInvoice);


}
