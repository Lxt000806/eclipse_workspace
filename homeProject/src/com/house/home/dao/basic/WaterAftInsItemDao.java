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
import com.house.home.client.service.evt.WaterAftInsItemEvt;
import com.house.home.entity.basic.WaterAftInsItem;

@SuppressWarnings("serial")
@Repository
public class WaterAftInsItemDao extends BaseDao {

	/**
	 * waterAftInsItem分页信息
	 * 
	 * @param page
	 * @param waterAftInsItem
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WaterAftInsItem waterAftInsItem) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.No,a.ItemType2,a.Remarks,b.Descr ItemType2Descr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+" from tWaterAftInsItemRule a "
				+" left join tItemType2 b on a.ItemType2=b.Code "
				+" where 1=1 ";

    	if (StringUtils.isNotBlank(waterAftInsItem.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+waterAftInsItem.getNo()+"%");
		}
    	
    	if (StringUtils.isNotBlank(waterAftInsItem.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(waterAftInsItem.getItemType2());
		}
    	
		if (StringUtils.isBlank(waterAftInsItem.getExpired()) || "F".equals(waterAftInsItem.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findPageBySqlForDetail(Page<Map<String,Object>> page, WaterAftInsItem waterAftInsItem) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Descr,a.Uom,b.Descr UomDescr "
				+" from tWaterAftInsItemRuleDetail a "
				+" left join tUOM b on a.Uom=b.Code "
				+" where 1=1 and a.No = ?";
		list.add(waterAftInsItem.getNo());
		if (StringUtils.isBlank(waterAftInsItem.getExpired()) || "F".equals(waterAftInsItem.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("deprecation")
	public Result saveForProc(WaterAftInsItem waterAftInsItem, String xml) {
		Assert.notNull(waterAftInsItem);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pWaterAftInsItem_forXml(?,?,?,?,?,?,?,?)}");
			call.setString(1, waterAftInsItem.getM_umState());
			call.setString(2, waterAftInsItem.getNo());
			call.setString(3, waterAftInsItem.getItemType2());
			call.setString(4, waterAftInsItem.getRemarks());
			call.setString(5, waterAftInsItem.getLastUpdatedBy());
			call.setString(6, xml);
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Page<Map<String,Object>> getWaterAftInsItemType2List(Page<Map<String,Object>> page) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.No, b.Descr ItemType2Descr "
				+" from tWaterAftInsItemRule a "
				+" left join tItemType2 b on a.ItemType2=b.Code "
				+" where 1=1 ";

		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> getWaterAftInsItemDetailList(Page<Map<String,Object>> page, WaterAftInsItemEvt evt) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Descr, a.Uom, b.Descr UomDescr,a.Pk "
				+" from tWaterAftInsItemRuleDetail a "
				+" left join tUOM b on a.Uom=b.Code "
				+" where 1=1 ";

		if(StringUtils.isNotBlank(evt.getNo())){
			sql += " and a.no=? ";
			list.add(evt.getNo());
		}
		
		if(StringUtils.isNotBlank(evt.getDescr())){
			sql += " and a.Descr like ? ";
			list.add("%"+evt.getDescr()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result saveWaterAftInsItemAppForProc(String custCode, String workerCode, String xml) {
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSaveWaterAftInsItemApp_forXml(?,?,?,?,?)}");
			call.setString(1, custCode);
			call.setString(2, workerCode);
			call.setString(3, xml);
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Page<Map<String,Object>> getWaterAftInsItemList(Page<Map<String,Object>> page, WaterAftInsItemEvt evt) {
		List<Object> list = new ArrayList<Object>();

		String sql=" select c.Remarks ItemType2Descr, b.Descr, a.Qty, d.Descr UomDescr  from tWaterAftInsItemApp a "
				+" left join tWaterAftInsItemRuleDetail b on a.WaterAftInsItemPk = b.PK "
				+" left join tWaterAftInsItemRule c on b.No = c.No "
				+" left join tUOM d on b.Uom = d.Code "
				+" where a.CustCode = ? ";
		list.add(evt.getCustCode());

		if(StringUtils.isNotBlank(evt.getNo())){
			sql += " and b.no=? ";
			list.add(evt.getNo());
		}
		
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
}

