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
import com.house.home.entity.driver.ItemSendBatch;
import com.house.home.entity.project.WorkCost;
import com.house.home.entity.project.WorkCostDetail;

@SuppressWarnings("serial")
@Repository
public class ItemSendBatchDao extends BaseDao {

	public Page<Map<String,Object>> getJqGrid(Page<Map<String,Object>> page,ItemSendBatch itemSendBatch){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.No,a.DriverCode, a.AppCZY,c.NameChi AppCZYDesc, b.NameChi DriverName ,a.Status,x1.note StatusDesc,a.Remarks,a.Date , "
	               + " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.followMan,d.NameChi followManDescr " 
	               + " from tItemSendBatch a  "
	               + " left outer join tDriver b on b.Code=a.DriverCode "
	               + " left outer join tEmployee c on c.Number=a.AppCZY "
	               + " left outer join tEmployee d on d.Number=a.followMan "
	               + " left outer join tXTDM  x1 on x1.cbm=a.Status and x1.id='SENDBATCHSTATUS' "
	               + " where 1=1  ";
		if(StringUtils.isNotBlank(itemSendBatch.getRequestPage())){
			if("itemApp_connectSendBatch".equals(itemSendBatch.getRequestPage())){
				sql += " and exists(select 1 from  tItemReturn ir where ir.CustCode=? and a.no=ir.SendBatchNo ) ";
				params.add(itemSendBatch.getCustCode());
			}
		}
		if("S".equals(itemSendBatch.getM_umState())){
			sql += " and datediff(dd,date,getdate())=0  and a.Status='1' ";
		}
		if(StringUtils.isNotBlank(itemSendBatch.getNo())){
			sql += " and a.No like ? ";
			params.add("%"+itemSendBatch.getNo()+"%");
		}
		if(StringUtils.isNotBlank(itemSendBatch.getDriverCodeDescr())){
			sql += " and b.nameChi like ? ";
			params.add("%"+itemSendBatch.getDriverCodeDescr()+"%");
		}
		if(StringUtils.isNotBlank(itemSendBatch.getDriverCode())){
			sql += " and a.driverCode = ? ";
			params.add(itemSendBatch.getDriverCode());
		}
		if(StringUtils.isNotBlank(itemSendBatch.getStatus())){
			sql += " and a.status = ? ";
			params.add(itemSendBatch.getStatus());
		}
		if (StringUtils.isBlank(itemSendBatch.getExpired()) || "F".equals(itemSendBatch.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (itemSendBatch.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			params.add(itemSendBatch.getDateFrom());
		}
		if (itemSendBatch.getDateTo() != null){
			sql += " and a.Date< ? ";
			params.add(DateUtil.addInteger(itemSendBatch.getDateTo(),
					Calendar.DATE, 1));
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String,Object>> goReturnDetailJqGrid(Page<Map<String,Object>> page,ItemSendBatch itemSendBatch){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.status,a.NO,a.ItemType1,a.CustCode,a.appczy,a.date,a.Remarks,a.DriverCode,a.SendBatchNo, "
	               + " b.descr ItemType1Descr,c.zwxm appczyDescr, d.address, x1.NOTE statusdescr,f.phone "
	               + " from tItemReturn a "
	               + " left outer join titemType1 b on b.Code = a.itemType1 "
	               + " left outer join tCZYBM  c  on C.CZYBH=a.AppCZY  "
	               + " left outer join tCustomer d on d.code = a.CustCode "
	               + " left outer join tXTDM x1 on x1.CBM=a.Status and x1.Id='ITEMRETSTATUS' "
	               + " left outer join tEmployee f on f.Number=c.EMNum "
	               + " where a.Expired='F' and a.SendBatchNo=? ";
		params.add(itemSendBatch.getNo());
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	/**
	 * 司机是否有两天前的配送任务未完成
	 * 
	 * @param itemSendBatch
	 * @return
	 */
	public List<Map<String, Object>> checkDriver(ItemSendBatch itemSendBatch) {
		String sql = "select 1 from tItemSendBatch where date<(dateadd(day,-2,convert(char(10),getdate(),120)))" 
               +"and Status in('1','2') and DriverCode=?";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { itemSendBatch.getDriverCode() });
		return list;
	}
	/**
	 * 保存
	 * 
	 * @param itemSendBatch
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(ItemSendBatch itemSendBatch) {
		Assert.notNull(itemSendBatch);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPsGl_Add_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemSendBatch.getM_umState());
			call.setString(2, itemSendBatch.getNo());	
			call.setString(3, itemSendBatch.getDriverCode());
			call.setString(4, itemSendBatch.getStatus());
			call.setString(5, itemSendBatch.getRemarks());
			call.setString(6, itemSendBatch.getAppCZY());
			call.setString(7, itemSendBatch.getExpired());
			call.setString(8, itemSendBatch.getLastUpdatedBy());
			call.setString(9, itemSendBatch.getItemAppSendJson());
			call.setString(10, itemSendBatch.getItemReturnJson());
			call.setString(11, itemSendBatch.getItemAppSendBackJson());
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.setString(14, itemSendBatch.getFollowMan());
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
}

