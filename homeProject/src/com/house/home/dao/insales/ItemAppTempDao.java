package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.insales.ItemAppTemp;
import com.house.home.entity.insales.ItemAppTempArea;

@SuppressWarnings("serial")
@Repository
public class ItemAppTempDao extends BaseDao {

	/**
	 * ItemAppTemp分页信息
	 * 
	 * @param page
	 * @param itemAppTemp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppTemp itemAppTemp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tItemAppTemp a where 1=1 and a.Expired='F' ";

    	if (StringUtils.isNotBlank(itemAppTemp.getNo())) {
			sql += " and a.No=? ";
			list.add(itemAppTemp.getNo());
		}
    	if (StringUtils.isNotBlank(itemAppTemp.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(itemAppTemp.getItemType1());
		}
    	if (StringUtils.isNotBlank(itemAppTemp.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemAppTemp.getRemarks());
		}
    	if (itemAppTemp.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemAppTemp.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(itemAppTemp.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemAppTemp.getLastUpdatedBy());
		}
//		if (StringUtils.isBlank(itemAppTemp.getExpired()) || "F".equals(itemAppTemp.getExpired())) {
//			sql += " and a.Expired='F' ";
//		}
    	if (StringUtils.isNotBlank(itemAppTemp.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemAppTemp.getActionLog());
		}
    	if(itemAppTemp.getWorkerCode()!=null){
    		sql+=" and a.workType12 = (select workType12 from tWorker where code = ? )" ;
    		list.add(itemAppTemp.getWorkerCode());
    	}
    	if(StringUtils.isNotBlank(itemAppTemp.getWorkType12())){
    		sql +=" and a.WorkType12 = ? ";
    		list.add(itemAppTemp.getWorkType12());
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * @Description: TODO 领料申请模板分页查询
	 * @author	created by zb
	 * @date	2018-9-7--下午12:06:00
	 * @param page
	 * @param itemAppTemp
	 * @return
	 */
	public Page<Map<String,Object>> findItemAPPTempPageBySql(Page<Map<String,Object>> page, ItemAppTemp itemAppTemp) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.No, a.ItemType1, it1.Descr ItemType1Descr, a.Remarks, " +
				" a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, a.WorkType12, " +
				" wt12.Descr WorkType12Descr " +
				" from tItemAppTemp a " +
				" left join tItemType1 it1 on a.ItemType1=it1.Code " +
				" left join tWorkType12 wt12 on wt12.Code = a.WorkType12 " +
				" where 1=1 ";
		
		if (StringUtils.isBlank(itemAppTemp.getExpired()) || "F".equals(itemAppTemp.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(itemAppTemp.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+itemAppTemp.getNo()+"%");
		}
		if (StringUtils.isNotBlank(itemAppTemp.getItemType1())) {
			sql += " and a.ItemType1 = ? ";
			list.add(itemAppTemp.getItemType1());
		} else {
			sql += " and a.ItemType1 in (select Code from tItemType1 where Expired != 'T') ";
		}
		if (StringUtils.isNotBlank(itemAppTemp.getRemarks())) {
			sql += " and a.Remarks like ? ";
			list.add("%"+itemAppTemp.getRemarks()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getAppItemTypeBatch(Page<Map<String,Object>> page, ItemAppTemp itemAppTemp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.isSetItem, a.code ,case when a.isSetItem = '1' then '(套内)'+a.descr else '(套外)'+a.descr end descr from tAppItemTypeBatch a " +
				" where 1=1 and  exists (" +
				"	select 1 from tAppItemTypeBatchDetail in_a " +
				"	left join tConfItemType in_b on in_a.ConfItemType=in_b.Code" +
				"	left join tConfItemTypeDt in_c on in_c.ConfItemType=in_b.Code" +
				"	where exists (" +
				"		select 1 from tItemReq in_d " +
				"		left join titem in_e on in_e.code=in_d.ItemCode" +
				"		where in_d.CustCode = ? and " +
				"		 in_c.ItemType2=in_e.ItemType2" +
				"		 and isnull(in_c.ItemType3,in_e.ItemType3)=in_e.ItemType3" +
				"	) and (not exists (select 1 from tXtcs where id = 'ItemConfCtrl' and qz = '2' ) " +
				"			or exists (select 1 from tCustItemConfirm in_f where in_f.ConfItemType = in_b.Code and in_f.ItemConfStatus = '2' and in_f.custCode = ?)" +
				"		  )" +
				" ) ";
		list.add(itemAppTemp.getCustCode());
		list.add(itemAppTemp.getCustCode());
		if(StringUtils.isNotBlank(itemAppTemp.getIsItemSet())){
			sql+=" and a.IsSetItem= ? ";
			list.add(itemAppTemp.getIsItemSet());
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * @Description: TODO 运行存储过程
	 * @author	created by zb
	 * @date	2018-9-7--下午5:14:28
	 * @param itemAppTemp
	 * @return
	 */
	public Result doSave(ItemAppTemp itemAppTemp) {
		Assert.notNull(itemAppTemp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemAppTemp_forProc(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemAppTemp.getM_umState());
			call.setString(2, itemAppTemp.getNo());
			call.setString(3, itemAppTemp.getItemType1());
			call.setString(4, itemAppTemp.getRemarks());
			call.setString(5, itemAppTemp.getExpired());
			call.setString(6, itemAppTemp.getLastUpdatedBy());
			call.setString(7, itemAppTemp.getWorkType12());
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(8)));
			result.setInfo(call.getString(9));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	/**
	 * @Description: 
	 * @author	created by zb
	 * @date	2018-9-9--上午10:04:11
	 * @param page
	 * @param itemAppTemp
	 * @return
	 */
	public Page<Map<String, Object>> findAreaPageBySql(Page<Map<String, Object>> page, ItemAppTemp itemAppTemp) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.No,a.IATNo,a.FromArea,a.ToArea,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " +
				" from tItemAppTempArea a where IATNo=? " +
				" order by a.FromArea, a.ToArea, a.No ";
		
		list.add(itemAppTemp.getNo());

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * @Description: TODO 领料申请明细分页查询（tItemAppTempAreaDetail)
	 * @author	created by zb
	 * @date	2018-9-9--上午11:00:42
	 * @param page
	 * @param itemAppTempArea
	 * @return
	 */
	public Page<Map<String, Object>> findAreaDetailPageBySql(Page<Map<String, Object>> page, ItemAppTempArea itemAppTempArea) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.PK,a.ATANo,a.ITCode,i.Descr ITDescr,a.Qty,a.DispSeq,a.calType,x1.Note calTypeDescr, " +
				" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,i.Expired ITExpired " +
				" from tItemAppTempAreaDetail a " +
				" left join tItem i on a.ITCode=i.Code " +
				" left join tXtdm x1 on a.calType=x1.cbm and x1.id='ITEMTEMPTYPE' ";
		if ("A".equals(itemAppTempArea.getM_umState())) {
			sql += " where 1=2 ";
		} else {
			sql += " where a.ATANo = ? ";
			list.add(itemAppTempArea.getNo());
		}
		sql += " order by a.DispSeq ";
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: TODO 根据材料编号获取材料信息（只可增加字段，不可删除）
	 * @author	created by zb
	 * @date	2018-9-10--上午11:32:30
	 * @param code 材料编号
	 * @return
	 */
	public Map<String, Object> getItemByCode(String code) {
		String sql = " select a.Code, a.Descr, a.Expired " +
				" from tItem a " +
				" where a.Code = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * @Description: TODO 领料申请面积区间存储过程
	 * @author	created by zb
	 * @date	2018-9-11--上午10:21:11
	 * @param itemAppTempArea
	 * @return
	 */
	public Result doAreaSave(ItemAppTempArea itemAppTempArea) {
		Assert.notNull(itemAppTempArea);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemAppTempArea_forXml(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemAppTempArea.getM_umState());
			call.setString(2, itemAppTempArea.getNo());
			call.setString(3, itemAppTempArea.getIatno());
			call.setInt(4, itemAppTempArea.getFromArea()==null?-1:itemAppTempArea.getFromArea());
			call.setInt(5, itemAppTempArea.getToArea()==null?-1:itemAppTempArea.getToArea());
			call.setString(6, itemAppTempArea.getLastUpdatedBy());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
			call.setString(9, itemAppTempArea.getDetailJson());
//			System.out.println(itemAppTempArea.getDetailJson());
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
}

