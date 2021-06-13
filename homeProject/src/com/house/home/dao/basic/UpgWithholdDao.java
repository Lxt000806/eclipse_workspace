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
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.basic.UpgWithhold;
import com.house.home.entity.finance.ReturnPay;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class UpgWithholdDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, UpgWithhold upgWithhold) {
		List<Object> list = new ArrayList<Object>();

		String sql =  " select a.Code,a.Descr,a.ExistCal,a.ItemType1,a.CalType,a.CalAmount,a.CustType,a.Area,c.Desc1 CustTypeDescr, " 
				+" x1.NOTE ExistCalDescr,x2.note CalTypeDescr, b.descr ItemType1Descr, "
				+" a.LastUpdate,a.LastUpdatedBy, a.ActionLog,a.Expired,a.beginDate,a.EndDate,a.Areato,x3.note LayOutDescr "
				+" from tUpgWithhold a "
				+" left outer join tXTDM x1 on x1.cbm=a.ExistCal and x1.id= 'YESNO' "
				+" left outer join tXTDM x2 on x2.cbm=a.CalType and x2.id= 'UPGCALTYPE' "
				+" left outer join tItemType1 b on  b.code=a.ItemType1 "
				+" left outer join tCusttype c on c.code = a.CustType "
				+" left outer join tXTDM x3 on a.LayOut=x3.CBM and x3.ID='LAYOUT' "
				+" where 1=1 ";							
		
    	if (StringUtils.isNotBlank(upgWithhold.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+upgWithhold.getCode()+"%");
		}
    	
    	if (StringUtils.isNotBlank(upgWithhold.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+upgWithhold.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(upgWithhold.getCustType())) {
			sql += " and a.CustType = ? ";
			list.add(upgWithhold.getCustType());
		}
    	if (StringUtils.isBlank(upgWithhold.getExpired()) || "F".equals(upgWithhold.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(upgWithhold.getItemType1())) {
			sql += " and a.ItemType1 = ? ";
			list.add(upgWithhold.getItemType1());
		}
    	if (StringUtils.isNotBlank(upgWithhold.getCalType())) {
			sql += " and a.CalType = ? ";
			list.add(upgWithhold.getCalType());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String,Object>> findDetailByCode(Page<Map<String,Object>> page, UpgWithhold upgWithhold) {
		List<Object> list = new ArrayList<Object>();
		String sql="select a.pk,a.UpgHeadCode,a.ItemCode,a.LastUpdate,a.LastUpdatedBy, a.ActionLog,a.Expired, "
					+" (case when b.Descr is not null  then b.Descr when b.Descr is null and c.Descr is not null then c.Descr else null end) ItemDescr  "
					+" from tUpgWithHoldItem a "
					+" left outer join  tBaseItem  b on b.code =a.ItemCode "
					+" left outer join  tItem c on c.code =a.ItemCode "
					+" where a.UpgHeadCode=? ";
		list.add(upgWithhold.getCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public UpgWithhold getByCode(String code){
		String hql="from UpgWithhold where code =?";
		List<UpgWithhold> list = this.find(hql, new Object[]{code});
		if (list != null && list.size() > 0) 
			return list.get(0);
		else
			return null;
	}
	
	public Result doSave(UpgWithhold upgWithhold) {
		Assert.notNull(upgWithhold);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSjkxgzGl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, upgWithhold.getM_umState());
			call.setString(2, upgWithhold.getCode());
			call.setString(3, upgWithhold.getDescr());
			call.setString(4, upgWithhold.getExistCal());
			call.setString(5, upgWithhold.getItemType1());
			call.setString(6, upgWithhold.getCalType());
			call.setDouble(7, upgWithhold.getCalAmount());
			call.setString(8, upgWithhold.getCustType());
			call.setInt(9, upgWithhold.getArea());
			call.setString(10, upgWithhold.getExpired());
			call.setString(11, upgWithhold.getLastUpdatedBy());
			call.setTimestamp(12, upgWithhold.getBeginDate()==null?null:new Timestamp(upgWithhold.getBeginDate().getTime()));
			call.setTimestamp(13, upgWithhold.getEndDate()==null?null:new Timestamp(upgWithhold.getEndDate().getTime()));
			call.setDouble(14, upgWithhold.getAreaTo());
			call.setString(15, upgWithhold.getDetailJson());
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.NVARCHAR);
			call.setString(18, upgWithhold.getLayout());
			call.execute();
			result.setCode(String.valueOf(call.getInt(16)));
			result.setInfo(call.getString(17));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	
	
}
