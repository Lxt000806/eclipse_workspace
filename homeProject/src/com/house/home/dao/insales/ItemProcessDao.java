package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.insales.ItemProcess;
import com.house.home.entity.insales.ItemProcessDetail;

@SuppressWarnings("serial")
@Repository
public class ItemProcessDao extends BaseDao {
	@Autowired
	private HttpServletRequest request;

	/**
	 * ItemProcess分页信息
	 * 
	 * @param page
	 * @param itemProcess
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemProcess itemProcess) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" +
				" select  a.No, a.ItemType1, a.ProcessItemWHCode, a.SourceItemWHCode, a.Remarks, a.Status," +
				"    a.AppCZY, c1.zwxm AppCZYDescr, a.AppDate, a.ConfirmCZY, c2.zwxm ConfirmCZYDescr,a.ConfirmDate," +
				"    a.LastUpdate, a.LastUpdatedBy, a.Expired, " + 
                "    a.ActionLog,w1.desc1 ProcessItemWHDescr, w2.desc1 SourceItemWHDescr,it1.descr itemType1descr,x1.note StatusDescr" +
                " from tItemProcess a " +
                " left join tCZYBM c1 on a.AppCZY=c1.czybh  " +
                " left join tCZYBM c2 on a.ConfirmCZY=c2.czybh  " +
                " left join txtdm  x1 on x1.id='PROCESSSTATUS' and x1.cbm=a.Status  " +
                " left join tWareHouse w1 on w1.code=a.ProcessItemWHCode  " +
                " left join tWareHouse w2 on w2.code=a.SourceItemWHCode  " +
                " left join titemType1 it1 on it1.code=a.itemType1  " +
                " where 1=1 ";
		UserContext uc = (UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		
		sql += " and exists( " +
			   "  	select 1 from tWareHouseOperater in_a where in_a.czybh=? and  (a.ProcessItemWHCode=in_a.whCode or a.ProcessItemWHCode=in_a.whCode) " +	
			   " ) ";
		
			list.add(uc.getCzybh());

    	if (StringUtils.isNotBlank(itemProcess.getNo())) {
			sql += " and a.No=? ";
			list.add(itemProcess.getNo());
		}
    	
    	if (StringUtils.isNotBlank(itemProcess.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemProcess.getItemType1());
		}else {
			
		    String itemRight = "";
			for (String str : uc.getItemRight().trim().split(",")) {
				itemRight += "'" + str + "',";
			}
			sql += " and a.ItemType1 in("
					+ itemRight.substring(0, itemRight.length() - 1) + ") ";
		}
    	
    	if (StringUtils.isNotBlank(itemProcess.getProcessItemWHCode())) {
			sql += " and a.ProcessItemWHCode=? ";
			list.add(itemProcess.getProcessItemWHCode());
		}
    	if (StringUtils.isNotBlank(itemProcess.getSourceItemWHCode())) {
			sql += " and a.SourceItemWHCode=? ";
			list.add(itemProcess.getSourceItemWHCode());
		}
    	if (StringUtils.isNotBlank(itemProcess.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemProcess.getRemarks());
		}
    	if (StringUtils.isNotBlank(itemProcess.getStatus())) {
			sql += " and a.Status=? ";
			list.add(itemProcess.getStatus());
		}
    	if (StringUtils.isNotBlank(itemProcess.getAppCzy())) {
			sql += " and a.AppCZY=? ";
			list.add(itemProcess.getAppCzy());
		}
    	if (itemProcess.getAppDate() != null) {
			sql += " and a.AppDate=? ";
			list.add(itemProcess.getAppDate());
		}
    	if (StringUtils.isNotBlank(itemProcess.getConfirmCzy())) {
			sql += " and a.ConfirmCZY=? ";
			list.add(itemProcess.getConfirmCzy());
		}
    	if (itemProcess.getConfirmDate() != null) {
			sql += " and a.ConfirmDate=? ";
			list.add(itemProcess.getConfirmDate());
		}
    	if (itemProcess.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemProcess.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(itemProcess.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemProcess.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemProcess.getExpired()) || "F".equals(itemProcess.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemProcess.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemProcess.getActionLog());
		}
    	
    	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	/**
	 * 加工材料明细分页信息
	 * @param page
	 * @param itemProcessDetai
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_itemProcessDetail(Page<Map<String,Object>> page, ItemProcessDetail itemProcessDetai) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" +
				" select a.No, a.ItemCode,b.Descr ItemDescr, a.Qty, a.Cost, a.ItemTransformNo, a.LastUpdate," +
				"  c.Descr UomDescr, d.Remarks ItemTransformRemarks,a.LastUpdatedBy, a.Expired,a.ActionLog " + 
				" from tItemProcessDetail a " +
				" left join tItem b on a.ItemCode=b.Code " +
				" left join tUOM c on c.Code=b.Uom "+
				" left join tItemTransform d on a.ItemTransformNo=d.No "+
				" where 1=1 ";
    	if (StringUtils.isNotBlank(itemProcessDetai.getNo())) {
			sql += " and a.no=?  ";
			list.add(itemProcessDetai.getNo());
		}
    	
    	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	/**
	 * 源材料分页信息
	 * @param page
	 * @param itemProcessDetai
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_itemProcessSourceDetail(Page<Map<String,Object>> page, ItemProcessDetail itemProcessDetai) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" +
				" select a.PK, a.ProcessDetailPK, a.ItemCode,b.Descr ItemDescr, a.Qty,d.ItemTransformNo, a.LastUpdate," +
				" a.LastUpdatedBy, a.Expired,a.ActionLog,c.Descr UomDescr " + 
				" from tItemProcessSourceDetail a " +
				" left join tItem b on a.ItemCode=b.Code " +
				" left join tUOM c on c.Code=b.Uom "+
				" left join tItemProcessDetail d on d.pk=a.ProcessDetailPK "+
				" where 1=1 ";
    	if (StringUtils.isNotBlank(itemProcessDetai.getNo())) {
			sql += " and a.ProcessDetailPK in (select pk from tItemProcessDetail where no=?) ";
			list.add(itemProcessDetai.getNo());
		}
    	
    	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 通过转换信息获得源材料
	 * @param page
	 * @param itemProcessDetail
	 * @return
	 */
	public Page<Map<String,Object>> getSourceItemByTransform(Page<Map<String,Object>> page, ItemProcessDetail itemProcessDetail) {
		List<Object> list = new ArrayList<Object>();
		if (StringUtils.isBlank(itemProcessDetail.getItemTransformNo())){
			return null;
		}
		String sql = "select * from (" 
				+"  select a.No, a.ItemCode, b.descr ItemDescr, TransformPer, TransformPer*? Qty,c.Descr UomDescr "
				+" from tItemTransformDetail a "
				+" left join  tItem b on a.ItemCode=b.Code "
				+" left join tUOM c on c.Code=b.Uom "
				+" where a.No=? ";
		
		list.add(itemProcessDetail.getQty() == null ? 0.0: itemProcessDetail.getQty());
		list.add(itemProcessDetail.getItemTransformNo()); 
   	
   	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.ItemCode Desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 存储过程
	 * @param salesInvoice
	 * @return
	 */
	public Result doSave(ItemProcess itemProcess) {
		Assert.notNull(itemProcess);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemProcess(?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemProcess.getM_umState());
			call.setString(2, itemProcess.getNo());
			call.setString(3, itemProcess.getItemType1());
			call.setString(4, itemProcess.getProcessItemWHCode());
			call.setString(5, itemProcess.getSourceItemWHCode());
			call.setString(6, itemProcess.getRemarks());
			call.setString(7, itemProcess.getLastUpdatedBy());
			call.setString(8, itemProcess.getDetailJson());
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(9)));
			result.setInfo(call.getString(10));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
}

