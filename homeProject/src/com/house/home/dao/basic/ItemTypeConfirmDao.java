package com.house.home.dao.basic;

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
import com.house.home.entity.basic.ItemTypeConfirm;
import com.house.home.entity.basic.SpcBuilder;

@SuppressWarnings("serial")
@Repository
public class ItemTypeConfirmDao extends BaseDao {
	
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,ItemTypeConfirm itemTypeConfirm) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select a.*,cit.descr itemTimeDescr,b.descr prjitemDescr,c.Descr ItemType1Descr,x.note DispatchOrderDescr from tConfItemType a " +
				" left join tConfItemTime cit on cit.code=a.itemTimeCode  " +
				" left join tprjItem1 b on b.code = a.prjItem" +
				" left join tItemType1 c on a.ItemType1=c.Code" +
				" left join tXtdm x on x.cbm = a.DispatchOrder and x.id = 'YESNO'" +
				" where 1=1 ";
		if(StringUtils.isNotBlank(itemTypeConfirm.getCode())){
			sql+=" and a.code=? ";
			list.add(itemTypeConfirm.getCode());
		}
		if(StringUtils.isNotBlank(itemTypeConfirm.getDescr())){
			sql+=" and a.descr=? ";
			list.add(itemTypeConfirm.getDescr());
		}
		if(StringUtils.isNotBlank(itemTypeConfirm.getItemTimeCode())){
			sql+=" and a.itemTimeCode = ? ";
			list.add(itemTypeConfirm.getItemTimeCode());
		}
		
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "   order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "  order by a.dispSeq ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,ItemTypeConfirm itemTypeConfirm) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select it2.descr itemtype2descr,it3.descr itemtype3descr,a.itemType2,a.itemType3 from tConfItemTypeDt a " +
				" left join titemType2 it2 on it2.code=a.itemType2 " +
				" left join tItemType3 it3 on it3.code=a.itemType3 "+
				"  where 1=1 " ;
		
		if(StringUtils.isNotBlank(itemTypeConfirm.getCode())){
			sql+=" and a.confItemType=? ";
			list.add(itemTypeConfirm.getCode());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "   order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "  order by a.pk ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	@SuppressWarnings("deprecation")
	public Result doSave(ItemTypeConfirm itemTypeConfirm) {
		Assert.notNull(itemTypeConfirm);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pItemTypeConfirm_forXml(?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemTypeConfirm.getCode());
			call.setString(2, itemTypeConfirm.getDescr());
			call.setString(3, itemTypeConfirm.getItemTimeCode());
			call.setString(4, itemTypeConfirm.getLastUpdatedBy());
			call.setString(5, itemTypeConfirm.getM_umState());
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.setString(8, itemTypeConfirm.getDetailXml());
			call.setString(9, itemTypeConfirm.getPrjItem());
			call.setInt(10, itemTypeConfirm.getAvgSendDay()== null ? 0 :itemTypeConfirm.getAvgSendDay());
			call.setString(11, itemTypeConfirm.getItemType1());
			call.setString(12, itemTypeConfirm.getDispatchOrder());
			call.execute();
			result.setCode(String.valueOf(call.getInt(6)));
			result.setInfo(call.getString(7));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	
	
	
}
