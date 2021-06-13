package com.house.home.dao.basic;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
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
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.SupplProm;
@SuppressWarnings("serial")
@Repository
public class SupplPromDao extends BaseDao{

	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, SupplProm supplProm) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" +
				" select a.pk,b.supplCode,c.descr supplDescr,b.Descr ActDescr,a.No, a.ItemType3, a.ItemDescr, " +
				" a.Model, a.Uom, a.ItemSize, a.UnitPrice, a.PromPrice, a.Cost, a.PromCost, a.Remarks,"+
                " a.LastUpdate, a.LastUpdatedBy, a.ActionLog, a.Expired " +
                " from tSupplPromItem a " +
				" left join tSupplProm b on a.no =b.no " +
				" left join tSupplier c on c.code = b.supplCode " +
				" where 1=1 ";
		if(StringUtils.isNotBlank(supplProm.getDescr())){
			sql+=" and b.descr like ?";
			list.add("%"+supplProm.getDescr()+"%");
		}
		if(StringUtils.isNotBlank(supplProm.getSupplCode())){
			sql+=" and b.supplCode = ? ";
			list.add(supplProm.getSupplCode());
		}
		if(supplProm.getDateFrom() != null){
			sql+=" and b.beginDate >= ? ";
			list.add(supplProm.getDateFrom());
		}
		if(supplProm.getDateTo() != null){
			sql+=" and b.beginDate <= ? ";
			list.add(supplProm.getDateTo());
		}
		if(StringUtils.isNotBlank(supplProm.getItemType3())){
			sql+=" and a.itemType3 like ? ";
			list.add("%"+supplProm.getItemType3()+"%");
		}
		if(StringUtils.isNotBlank(supplProm.getItemDescr())){
			sql+=" and a.itemDescr like ? ";
			list.add("%"+supplProm.getItemDescr()+"%");
		}
		if(StringUtils.isNotBlank(supplProm.getItemSize())){
			sql+=" and a.itemSize like ? ";
			list.add("%"+supplProm.getItemSize()+"%");
		}
		if(StringUtils.isNotBlank(supplProm.getModel())){
			sql+=" and a.model like ? ";
			list.add("%"+supplProm.getModel()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.no desc,a.lastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SupplProm supplProm) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (" +
				"select a.No,a.Descr,a.SupplCode,b.Descr SupplDescr,a.BeginDate,a.EndDate,a.Remarks, " +
				"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " +
				"from tSupplProm a " +
				"left join tSupplier b on b.Code=a.SupplCode " +
				"where 1=1 ";
		if (StringUtils.isBlank(supplProm.getExpired()) || "F".equals(supplProm.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(supplProm.getNo())) {
			sql += " and a.No=? ";
			list.add(supplProm.getNo());
		}
		if (StringUtils.isNotBlank(supplProm.getSupplCode())) {
			sql += " and a.SupplCode = ?";
			list.add(supplProm.getSupplCode());
		}
		if (StringUtils.isNotBlank(supplProm.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+supplProm.getDescr()+"%");
		}
		if (null != supplProm.getDateFrom()) {
			sql += " and a.BeginDate >= ? ";
			list.add(new Timestamp(DateUtil.startOfTheDay(supplProm.getDateFrom()).getTime()));
		}
		if (null != supplProm.getDateTo()) {
			sql += " and a.EndDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(supplProm.getDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 查询材料信息
	 * @author	created by zb
	 * @date	2019-7-13--下午4:42:02
	 * @param page
	 * @param supplProm
	 * @return
	 */
	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, SupplProm supplProm) {
		String sql = "select a.pk,a.No,a.ItemType3,a.ItemDescr,a.Model,a.Uom,a.ItemSize,a.UnitPrice,a.PromPrice, " +
					"a.Cost,a.PromCost,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired " +
					"from tSupplPromItem a " +
					"where a.Expired='F' and a.No=? ";
		return this.findPageBySql(page, sql, new Object[]{supplProm.getNo()});
	}
	/**
	 * 保存/修改
	 * @author	created by zb
	 * @date	2019-7-15--下午4:06:35
	 * @param supplProm
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSave(SupplProm supplProm) {
		Assert.notNull(supplProm);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSupplProm(?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, supplProm.getM_umState());
			call.setString(2, supplProm.getNo());
			call.setString(3, supplProm.getDescr());
			call.setString(4, supplProm.getSupplCode());
			call.setTimestamp(5, supplProm.getBeginDate()==null?null : new Timestamp(supplProm.getBeginDate().getTime()));
			call.setTimestamp(6, supplProm.getEndDate()==null?null : new Timestamp(supplProm.getEndDate().getTime()));
			call.setString(7, supplProm.getRemarks());
			call.setString(8, supplProm.getLastUpdatedBy());
			call.setString(9, supplProm.getExpired());
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.NVARCHAR);
			call.setString(12, supplProm.getDetailJson());
			System.out.println(supplProm.getDetailJson());
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

}
