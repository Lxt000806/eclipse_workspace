package com.house.home.dao.basic;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.SetItemQuota;
import com.house.home.entity.finance.ReturnPay;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class SetItemQuotaDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SetItemQuota setItemQuota) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select  a.No,a.CustType,b.Desc1 CustTypeDescr,a.FromArea,a.ToArea,a.Remark, "
				+ " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+ " from tSetItemQuota a "
				+ " left join tCustType b on a.CustType=b.Code "
				+ " where 1=1 ";
		
		if (StringUtils.isNotBlank(setItemQuota.getCustType())) {
			sql += " and a.CustType = ? ";
			list.add(setItemQuota.getCustType());
		}
		
		if (setItemQuota.getFromArea()!=null&&setItemQuota.getFromArea()!=0) {
			sql += " and a.FromArea = ? ";
			list.add(setItemQuota.getFromArea());
		}
		
		if (setItemQuota.getToArea()!=null&&setItemQuota.getToArea()!=0) {
			sql += " and a.ToArea = ? ";
			list.add(setItemQuota.getToArea());
		}
		if (StringUtils.isBlank(setItemQuota.getExpired()) 
				|| "F".equals(setItemQuota.getExpired())) {
			sql += " and a.expired='F' ";
		}
			
		sql +=" order by a.LastUpdate DESC";
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDetailByNo(Page<Map<String,Object>> page, SetItemQuota setItemQuota) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.No,a.PK,a.FixArea,a.JudgeSend,a.ItemCode,a.Qty,b.note JudgeSendDescr,c.descr ItemCodeDescr, "
				+" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+" from tSetItemQuotaDetail a "
				+" left join txtdm b on a.JudgeSend=b.cbm and b.id='JudgeSend' "
				+" left join tItem c on a.ItemCode=c.code "
				+" where 1=1 ";
		
		if (StringUtils.isNotBlank(setItemQuota.getNo())) {
			sql += " and a.No = ? ";
			list.add(setItemQuota.getNo());
		}else{
			sql += " and a.No = '-1' ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Result doSave(SetItemQuota setItemQuota) {
		Assert.notNull(setItemQuota);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSetItemQuotaSave_forXml(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, setItemQuota.getNo());
			call.setString(2, setItemQuota.getM_umState());
			call.setString(3, setItemQuota.getCustType());
			call.setDouble(4, setItemQuota.getFromArea());
			call.setDouble(5, setItemQuota.getToArea());
			call.setString(6, setItemQuota.getRemark());
			call.setString(7, setItemQuota.getLastUpdatedBy());
			call.setString(8, setItemQuota.getExpired());
			call.setString(9, setItemQuota.getDetailJson());
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(10)));
			result.setInfo(call.getString(11));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public SetItemQuota getSupplierTimeByNo(String no) {
		String hql = " from tSupplierTime where SendTimeNo= ? ";
		List<SetItemQuota> list = this.find(hql, new Object[]{no});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public Result updateSendTime(String deleteNo) {
		Assert.notNull(deleteNo);
		String sql="update tSendTime set expired='T' where no= ?";
		return (Result) this.findBySql(sql,new Object[]{deleteNo});
	}

}
