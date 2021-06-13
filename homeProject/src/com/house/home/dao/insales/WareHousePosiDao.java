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

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.client.service.evt.OperateWareHousePosiEvt;
import com.house.home.client.service.resp.BaseResponse;
import com.house.home.entity.insales.WareHousePosi;

@SuppressWarnings("serial")
@Repository
public class WareHousePosiDao extends BaseDao {

	/**
	 * WareHousePosi分页信息
	 * 
	 * @param page
	 * @param wareHousePosi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WareHousePosi wareHousePosi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tWareHousePosi a where 1=1 ";

    	if (wareHousePosi.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(wareHousePosi.getPk());
		}
    	if (StringUtils.isNotBlank(wareHousePosi.getDesc1())) {
			sql += " and a.Desc1=? ";
			list.add(wareHousePosi.getDesc1());
		}
    	if (StringUtils.isNotBlank(wareHousePosi.getWhcode())) {
			sql += " and a.WHCode=? ";
			list.add(wareHousePosi.getWhcode());
		}
    	if (wareHousePosi.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(wareHousePosi.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(wareHousePosi.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(wareHousePosi.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(wareHousePosi.getExpired()) || "F".equals(wareHousePosi.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(wareHousePosi.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(wareHousePosi.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String,Object>> getWareHousePosiList(Page page,String whCode, String itCode,String desc1) {
		List<Object> list=new ArrayList<Object>();
		list.add(itCode);
		String sql=	"SELECT a.Desc1,ISNULL(b.QtyCal,0.00) QtyCal,a.pk FROM  dbo.tWareHousePosi a LEFT JOIN tWHPosiBal b ON b.WHPPk=a.pk AND b.ITCode=? ";
		sql+="WHERE a.WHCode=? ";
		list.add(whCode);
		if(StringUtils.isNotBlank(desc1)){
			sql+="and a.desc1 like ? ";
			list.add("%"+desc1+"%");
		}
		sql+="ORDER BY b.QtyCal Desc";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public void operateWareHousePosi(BaseResponse respon,
			OperateWareHousePosiEvt evt) {
		Assert.notNull(evt);

		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pWhPosiBalTran(?,?,?,?,?,?,?)}");
			call.setString(1, evt.getWhCode());
			call.setString(2, evt.getItCode());
			call.setInt(3, Integer.parseInt(evt.getWhPk()));
			call.setDouble(4,Double.parseDouble(evt.getQty()));
			call.setString(5, evt.getCzy());
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.execute();
			if(call.getInt(6)==-1){
				respon.setReturnCode("100006");	
				respon.setReturnInfo(call.getString(7));
				return;
			}
			respon.setReturnCode("000000");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		
	}

	/**
	 * @Description: TODO 库位管理左链接分页查询
	 * @author	created by zb
	 * @date	2018-8-9--上午11:53:15
	 * @param page
	 * @param wareHousePosi
	 * @return
	 */
	public Page<Map<String, Object>> findPageByleftSql(
			Page<Map<String, Object>> page, WareHousePosi wareHousePosi) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from (select  a.Pk, a.Desc1, a.WHCode, b.Desc1 WHDescr, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog " +
				" from    tWareHousePosi a " +
				" left outer join tWareHouse b on b.Code = a.WHCode " +
				" where   1 = 1 ";
		
		if (StringUtils.isBlank(wareHousePosi.getExpired()) || "F".equals(wareHousePosi.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (wareHousePosi.getPk() != null) {
			sql += " and a.pk like ? ";
			list.add("%"+wareHousePosi.getPk()+"%");
		}
		if (StringUtils.isNotBlank(wareHousePosi.getDesc1())) {
			sql += " and a.Desc1 like ? ";
			list.add("%"+wareHousePosi.getDesc1()+"%");
		}
		if (StringUtils.isNotBlank(wareHousePosi.getWhcode())) {
			sql += " and a.WHCode= ? ";
			list.add(wareHousePosi.getWhcode());
		}
		sql += " and a.WHCode in (select WHCode from tWareHouseOperater where CZYBH= ? ) ";
		list.add(wareHousePosi.getCzybh());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: TODO 检查desc1是否重复
	 * @author	created by zb
	 * @date	2018-8-9--下午4:46:52
	 * @param desc1
	 * @param pk
	 * @return
	 */
	public boolean checkDesc1PK(String desc1, Integer pk) {
		String sql;
		List<Map<String,Object>> list;
		if (pk != null) {// 判断是否有pk传入
			sql = " select 1 from tWareHousePosi where Desc1 = ? and pk <> ? ";
			list = this.findBySql(sql, new Object[]{desc1,pk});
		} else {
			sql = " select 1 from tWareHousePosi where Desc1 = ? ";
			list = this.findBySql(sql, new Object[]{desc1});
		}
		if(list != null && list.size() > 0){// 当存在时返回false
			return false;
		}
		return true;
	}

	/**
	 * @Description: TODO wareHousePosi_code分页查询
	 * @author	created by zb
	 * @date	2018-8-15--下午4:23:12
	 * @param page
	 * @param wareHousePosi
	 * @return
	 */
	public Page<Map<String, Object>> findCodePageBySql(
			Page<Map<String, Object>> page, WareHousePosi wareHousePosi) {
		List<Object> list = new ArrayList<Object>();
		String sql =" select a.PK,a.Desc1 from tWareHousePosi a " +
				" where 1=1 ";
		
		if (StringUtils.isNotBlank(wareHousePosi.getWhcode())) {
			sql += " and a.WHCode = ? ";
			list.add(wareHousePosi.getWhcode());
		}
    	if (null != wareHousePosi.getPk()) {
			sql += " and a.PK = ? ";
			list.add(wareHousePosi.getPk());
		}
    	if (StringUtils.isNotBlank(wareHousePosi.getDesc1())) {
			sql += " and a.Desc1 like ? ";
			list.add("%"+wareHousePosi.getDesc1()+"%");
		}
		if (StringUtils.isBlank(wareHousePosi.getExpired()) || "F".equals(wareHousePosi.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.PK ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

