package com.house.home.dao.commi;
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
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.commi.BusinessCommiFloatRule;
import com.house.home.entity.commi.DesignCommiRule;
import com.house.home.entity.commi.MainCommiRuleNew;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class MainCommiRuleNewDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, MainCommiRuleNew mainCommiRuleNew) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from	(select	a.No, a.Remarks, a.CommiType, a.ToProfit, a.FromProfit, a.IsUpgItem, a.CommiPerc, a.LastUpdate," +
				" a.LastUpdatedBy, a.Expired, a.ActionLog,x1.note CommiTypeDescr,x2.note isUpgItemDescr " +
				" from tMainCommiRuleNew a " +
				" left join tXtdm x1 on x1.id='COMMITYPE' and x1.cbm = a.CommiType" +
				" left join tXtdm x2 on x2.id='YESNO' and x2.cbm = a.isUpgItem" +
				" where 1 = 1" ;
		if(StringUtils.isNotBlank(mainCommiRuleNew.getCommiType())){
			sql+=" and a.CommiType = ?";
			list.add(mainCommiRuleNew.getCommiType());
		}
		if(mainCommiRuleNew.getFromProfit() != null){
			sql+=" and a.FromProFit = ? ";
			list.add(mainCommiRuleNew.getFromProfit());
		}
		if(mainCommiRuleNew.getToProfit() != null){
			sql+=" and a.ToProFit = ? ";
			list.add(mainCommiRuleNew.getToProfit());
		}
		if(mainCommiRuleNew.getCommiPerc() != null){
			sql+=" and a.Commiperc = ? ";
			list.add(mainCommiRuleNew.getCommiPerc());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.no";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findItemDetailPageBySql(Page<Map<String,Object>> page, MainCommiRuleNew mainCommiRuleNew) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from	(select	a.pk, a.No, a.ItemType2, a.ItemType3, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog," +
				" b.Descr itemType2Descr, c.descr itemType3Descr " +
				" from tMainCommiRuleItemNew a " +
				" left join tItemType2 b on b.code = a.ItemType2" +
				" left join tItemType3 c on c.code = a.ItemType3" +
				" where 1 = 1" ;
		if(StringUtils.isNotBlank(mainCommiRuleNew.getNo())){
			sql+=" and a.no = ?";
			list.add(mainCommiRuleNew.getNo());
		} else {
			sql+=" and 1<>1 ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.no";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("deprecation")
	public Result doSave(MainCommiRuleNew mainCommiRuleNew) {
		Assert.notNull(mainCommiRuleNew);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pMainCommiRuleNew(?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, mainCommiRuleNew.getM_umState());
			call.setString(2, mainCommiRuleNew.getNo());
			call.setDouble(3, mainCommiRuleNew.getFromProfit());
			call.setDouble(4, mainCommiRuleNew.getToProfit());
			call.setDouble(5, mainCommiRuleNew.getCommiPerc());
			call.setString(6, mainCommiRuleNew.getIsUpgItem());
			call.setString(7, mainCommiRuleNew.getCommiType());
			call.setString(8, mainCommiRuleNew.getRemarks());
			call.setString(9, mainCommiRuleNew.getLastUpdatedBy());
			call.setString(10, mainCommiRuleNew.getDetailXml());
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.NVARCHAR); 
			call.execute();
			result.setCode(String.valueOf(call.getInt(11)));
			result.setInfo(call.getString(12));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
}
