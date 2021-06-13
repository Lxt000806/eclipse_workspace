package com.house.home.dao.design;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.PrePlan;
import com.house.home.entity.design.PrePlanArea;
import com.house.home.entity.project.CustComplaint;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class PrePlanAreaDao extends BaseDao{

	public Page<Map<String, Object>> findPlanAreaJqgridBySql(
			Page<Map<String, Object>> page, PrePlan prePlan) {
		
		List<Object> list = new ArrayList<Object>();
		String sql=" select a.OldArea, a.OldPerimeter, a.OldHeight, a.OldPaveType, a.OldBeamLength, a.OldShowerLength, a.OldShowerWidth " +
				",b.isDefaultArea,a.pk,a.pavetype,d.descr custDescr,b.descr fixAreaTypeDescr,a.Area,a.perimeter,a.Height ," +
				" a.BeamLength ,a.ShowerLength ,a.ShowerWidth ,a.DispSeq,x1.NOTE pavetypedescr,a.fixareatype ," +
				" a.descr,a.descr preplanAreaDescr ,isnull(p1.length,0) length1 ,isnull(p1.height,0) height1," +
				" isnull(p2.length,0) length2,isnull(p2.height,0) height2,p1.pk p1pk,p2.pk p2pk," +
				"isnull(p1.oldlength,0) oldlength1 ,isnull(p1.oldheight,0) oldheight1," +
				" isnull(p2.oldlength,0) oldlength2,isnull(p2.oldheight,0) oldheight2 " +
				" from tPrePlanArea a" +
				" left join tFixAreaType b on b.Code=a.FixAreaType" +
				" left join tcustomer d on a.CustCode=d.code " +
				" left join txtdm x1 on x1.CBM=a.PaveType and x1.id='PAVETYPE'" +
				" left join (select  min(pk) pk,AreaPK from tPrePlanDoor where type='1' group by AreaPK ) ppd1 on ppd1.Areapk=a.pk " +
				" left join (select  min(pk) pk,AreaPK from tPrePlanDoor where type='2' group by AreaPK ) ppd2 on ppd2.Areapk=a.pk " +
				" left join tPrePlanDoor p1 on p1.pk=ppd1.pk " +//门
				" left join tPrePlanDoor p2 on p2.pk=ppd2.pk" + //窗
				" where 1=1 ";
				//" order by a.DispSeq ";
		
		if(StringUtils.isNotBlank(prePlan.getCustCode())){
			sql+=" and a.custCode = ? ";
			list.add(prePlan.getCustCode());
		}
		if (StringUtils.isNotBlank(prePlan.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+prePlan.getDescr()+"%");
		}
		sql+=" order by a.DispSeq ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findDoorWindJqgridBySql(
			Page<Map<String, Object>> page, Integer pk) {
		List<Object> list = new ArrayList<Object>();
		
		String sql=" select x1.note typedescr,length,height,a.type,a.pk,a.oldHeight,a.oldLength from tPrePlanDoor a " +
				" left join tXtdm x1 on x1.cbm=a.type and x1.id='DOORWINDOW' " +
				" where 1=1 ";

		if(pk!=null){
			sql+=" and a.AreaPk = ? ";
			list.add(pk);
		}else {
			sql+=" and a.AreaPk = 0 ";
		} 
		sql+=" order by a.pk";
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public String getNoByCustCode(String custCode){
		String sql = " select No from tPrePlan  where custCode= ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return list.get(0).get("No").toString() ;
		}
		return "";
	}
	
	@SuppressWarnings("deprecation")
	public Result doSave(PrePlanArea prePlanArea) {
		Assert.notNull(prePlanArea);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemPlan_prePlanArea(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, prePlanArea.getM_umState());
			call.setString(2, prePlanArea.getCustCode());
			call.setString(3, prePlanArea.getDescr());
			call.setDouble(4, prePlanArea.getArea()==null?0:prePlanArea.getArea());
			call.setDouble(5, prePlanArea.getPerimeter()==null?0:prePlanArea.getPerimeter());
			call.setDouble(6, prePlanArea.getHeight()==null?0:prePlanArea.getHeight());
			call.setDouble(7, prePlanArea.getBeamLength()==null?0:prePlanArea.getBeamLength());
			call.setDouble(8, prePlanArea.getShowerLength()==null?0:prePlanArea.getShowerLength());
			call.setDouble(9, prePlanArea.getShowerWidth()==null?0:prePlanArea.getShowerWidth());
			call.setDouble(10, prePlanArea.getDispSeq()==null?0:prePlanArea.getDispSeq());
			call.setString(11, prePlanArea.getLastUpdatedBy());
			call.setString(12, prePlanArea.getPaveType());
			call.setString(13, prePlanArea.getFixAreaType());
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.NVARCHAR);
			call.setString(16, prePlanArea.getDetailXml());
			call.setInt(17, prePlanArea.getPk()==null?0:prePlanArea.getPk());
			call.setString(18, prePlanArea.getCopyCustCode());
			call.setString(19, prePlanArea.getModule());
			call.execute();
			result.setCode(String.valueOf(call.getInt(14)));
			result.setInfo(call.getString(15));
			System.out.println(result.getInfo());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public String getDWMaxPk(String type,Integer areaPk) {
		String sql =" select max(pk) pk from tPrePlanDoor where type = ? and areaPk = ? ";
		List<Map<String, Object>> list = this.findBySql(sql,new Object[] { type,areaPk });
		if (list != null && list.size() > 0) {
			return list.get(0).get("pk").toString();
		}
		return null;
	}
	
	public boolean getCanUpdateArea(String custCode) {
		boolean result = false;
		String sql =" select * from tCustomer a" +
				" left join tCusttype b on b.Code = a.CustType " +
				" where a.Code = ? and b.SignQuoteType = '2' and " +
				" not exists( select 1 from  tBaseItemChg in_a " +
				"	where in_a.CustCode = a.Code and TempNo is not null and status='2')";
		List<Map<String, Object>> list = this.findBySql(sql,new Object[] {custCode});
		if (list != null && list.size() > 0) {
			result = true;
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
