package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.insales.ItemBatchDetail;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.entity.project.WorkCost;

@SuppressWarnings("serial")
@Repository
public class ItemBatchHeaderDao extends BaseDao {

	/**
	 * ItemBatchHeader分页信息
	 * 
	 * @param page
	 * @param itemBatchHeader
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemBatchHeader itemBatchHeader) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.No,a.Date,a.ItemType1,it1.Descr ItemType1Descr,rtrim(a.Remarks) remarks, "
				+ "a.CrtCZY,e1.NameChi CrtCZYDescr,a.Batchtype,x1.Note BatchTypeDescr,a.CustType,x2.Note CustTypeDescr, "
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.DispSeq,a.worktype12,wt.descr workType12Descr, "
				+ "a.ActionLog,x3.note StatusDescr,a.CustCode,c.Address,OtherRemarks, "
				+ "case when exists (select 1 from tItemPreApp in_a "
				+"	left join tItemPreAppDetail in_b on in_a.No = in_b.No "
				+"	where in_a.CustCode	= ? and in_b.ItemCode in (select in_c.ITCode from tItemBatchDetail in_c where in_c.IBDNo = a.No)) "
				+"	then '(1)' else null end existApplied "
				+ "from tItemBatchHeader a  "
				+ "left outer join tItemType1 it1 on a.ItemType1= it1.Code  "
				+ "left outer join tEmployee e1 on e1.Number = a.CrtCZY  "
				+ "left outer join tXTDM x1 on x1.ID='BatchType' and x1.CBM=a.BatchType "
				+ "left outer join tXTDM x2 on x2.ID='CUSTTYPE' and x2.CBM=a.CustType "
				+ "left outer join tWorkType12 wt on a.worktype12=wt.code "
				+ "left outer join tXTDM x3 on x3.ID='ITEMBATCHSTATUS' and x3.CBM=a.Status "
				+ "left outer join tCustomer c on c.code=a.CustCode "
				+ "where 1=1";
		
		list.add(itemBatchHeader.getCustCode());
		if(StringUtils.isNotBlank(itemBatchHeader.getCustType())){
			sql += " and (a.CustType = ? or a.CustType='' or a.CustType is null) ";
			list.add(itemBatchHeader.getCustType());
		}
		
		if (StringUtils.isNotBlank(itemBatchHeader.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+itemBatchHeader.getNo()+"%");
		}
		// 增加创建人匹配 --add by zb
		if (StringUtils.isNotBlank(itemBatchHeader.getCrtCzy())) {
			sql += " and a.CrtCZY = ? ";
			list.add(itemBatchHeader.getCrtCzy());
		}
    	if (StringUtils.isNotBlank(itemBatchHeader.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemBatchHeader.getItemType1());
		}else{
			sql += " and a.ItemType1 in ('"+itemBatchHeader.getItemRight().trim().replace(",", "','")+"')";
		}

    	if (itemBatchHeader.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(itemBatchHeader.getDateFrom());
		}
		if (itemBatchHeader.getDateTo() != null){
			sql += " and a.Date< ? ";
			list.add(DateUtil.addInteger(itemBatchHeader.getDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(itemBatchHeader.getRemarks())) {
			sql += " and a.Remarks like ? ";
			list.add("%" + itemBatchHeader.getRemarks() + "%");
		}
		if (StringUtils.isBlank(itemBatchHeader.getExpired())
				|| "F".equals(itemBatchHeader.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(itemBatchHeader.getBatchType())) {
			sql += " and a.BatchType=? ";
			list.add(itemBatchHeader.getBatchType());
		}
		if(StringUtils.isNotBlank(itemBatchHeader.getWorkerCode())){
			sql+=" and wt.code= ( select workType12 from tWorker where code = ?  )";
			list.add(itemBatchHeader.getWorkerCode());
		}
		if(StringUtils.isNotBlank(itemBatchHeader.getWorkType12())){
			sql+=" and wt.Code = ? ";
			list.add(itemBatchHeader.getWorkType12());
		}
		if (StringUtils.isNotBlank(itemBatchHeader.getAddress())) {
			sql += " and c.Address like ? ";
			list.add("%" + itemBatchHeader.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by  a.Date desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 保存
	 * 
	 * @param itemBatchHeader
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(ItemBatchHeader itemBatchHeader) {
		Assert.notNull(itemBatchHeader);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClpcgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemBatchHeader.getM_umState());
			call.setString(2, itemBatchHeader.getNo());	
			call.setString(3, itemBatchHeader.getItemType1());
			call.setString(4, itemBatchHeader.getBatchType());
			call.setString(5, itemBatchHeader.getCrtCzy());
			call.setString(6, itemBatchHeader.getRemarks());
			call.setString(7, itemBatchHeader.getLastUpdatedBy());
			call.setString(8, itemBatchHeader.getCustType());
			call.setString(9, itemBatchHeader.getDispSeq());
			call.setString(10, itemBatchHeader.getExpired());
			call.setString(11, itemBatchHeader.getWorkType12());
			call.setString(12, itemBatchHeader.getItemBatchDetailJson());
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(13)));
			result.setInfo(call.getString(14));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	/**
	 * 根据itcode找itemtype2
	 * @param itCode
	 * @return
	 */
	public List<Map<String, Object>> getItemType2(String itCode) {
		String sql="select i.ItemType2 from tItemBatchDetail a left outer join tItem i on i.code=a.ITCode where a.ITCode=?";
		return this.findListBySql(sql, new Object[]{itCode});
	}
	//判断材料批次是否做增减
	public boolean hasChgItemBatch(String no) {
		String sql =" select 1 from tItemChg where ItemBatchNo=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {no});
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
}
