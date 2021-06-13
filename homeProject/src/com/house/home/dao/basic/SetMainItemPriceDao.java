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
import com.house.home.entity.basic.SetMainItemPrice;
import com.house.home.entity.salary.SalaryEmpDeduction;

@SuppressWarnings("serial")
@Repository
public class SetMainItemPriceDao extends BaseDao {

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SetMainItemPrice setMainItemPrice) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ( "
					+"	select a.PK,a.CustType,b.Desc1 CustTypeDescr,a.FromArea,a.ToArea,a.BasePrice,a.BaseArea,a.UnitPrice,a.BaseToiletCount, "
					+"	a.ToiletPrice,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
					+"	from tSetMainItemPrice a "
					+"	left join tCusttype b on b.Code=a.CustType "
					+"  where 1=1 ";

    	if (StringUtils.isNotBlank(setMainItemPrice.getCustType())) {
			sql += " and a.CustType = ? ";
			list.add(setMainItemPrice.getCustType());
		}
    	if (null != setMainItemPrice.getFromArea()) {
			sql += " and a.FromArea >=? ";
			list.add(setMainItemPrice.getFromArea());
		}
    	if (null != setMainItemPrice.getToArea()) {
			sql += " and a.ToArea <=? ";
			list.add(setMainItemPrice.getToArea());
		}
		/*if (StringUtils.isBlank(setMainItemPrice.getExpired()) || "F".equals(setMainItemPrice.getExpired())) {
			sql += " and a.Expired='F' ";
		}*/
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 检查面积是否重复
	 * @author	created by zb
	 * @date	2020-1-9--下午4:44:25
	 * @param custType
	 * @param area
	 * @return
	 */
	public List<Map<String, Object>> getIsRepeatArea(String custType,
			Double area) {
		String sql = "select 1 from tSetMainItemPrice where CustType=? and (FromArea=? or ToArea=?) ";
		return this.findBySql(sql, new Object[]{custType, area, area});
	}
	
	/**
	 * Excel导入调过程
	 * @param setMainItemPrice
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveBatch (SetMainItemPrice setMainItemPrice) {
		Assert.notNull(setMainItemPrice);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSetMainItemPrice_import(?,?,?,?)}");
			call.setString(1, setMainItemPrice.getLastUpdatedBy());
			call.setString(2, setMainItemPrice.getDetailXml());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

}

