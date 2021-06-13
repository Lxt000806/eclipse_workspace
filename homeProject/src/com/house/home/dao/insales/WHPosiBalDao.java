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

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.insales.WHPosiBal;
import com.house.home.entity.insales.WareHouse;

@SuppressWarnings("serial")
@Repository
public class WHPosiBalDao extends BaseDao {

	/**
	 * WHPosiBal分页信息
	 * 
	 * @param page
	 * @param wHPosiBal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WHPosiBal wHPosiBal) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tWHPosiBal a where 1=1 ";

    	if (wHPosiBal.getWhppk() != null) {
			sql += " and a.WHPPk=? ";
			list.add(wHPosiBal.getWhppk());
		}
    	if (StringUtils.isNotBlank(wHPosiBal.getItcode())) {
			sql += " and a.ITCode=? ";
			list.add(wHPosiBal.getItcode());
		}
    	if (wHPosiBal.getQtyCal() != null) {
			sql += " and a.QtyCal=? ";
			list.add(wHPosiBal.getQtyCal());
		}
    	if (wHPosiBal.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(wHPosiBal.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(wHPosiBal.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(wHPosiBal.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(wHPosiBal.getExpired()) || "F".equals(wHPosiBal.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(wHPosiBal.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(wHPosiBal.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Whppk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * @Description: TODO 库位调整分页查询
	 * @author	created by zb
	 * @date	2018-8-15--下午1:58:01
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String, Object>> findPageByInnerSql(
			Page<Map<String, Object>> page, WareHouse wareHouse) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from (select a.Code,a.Desc1 WhDescr,b.Desc1 WHPDescr,aa.ITCode,d.Descr ItemDescr,e.Descr itemType1Descr,f.Descr itemType2Descr, " +
				" isnull(aa.QtyCal,0) QtyCal,b.PK,aa.Expired,aa.LastUpdate,aa.LastUpdatedBy,isnull(ff.QtyCal,0)-isnull(dd.QtyCal,0) QtyNo, " +
				" isnull(ff.QtyCal,0) QtyAll  " +
				" from tWareHouse a " +
				" inner join tWareHousePosi b on a.Code=b.WHCode " +
				" inner join tWHPosiBal aa on b.PK=aa.WHPPk " +
				" left join tItem d on aa.ITCode=d.Code " +
				" left join titemtype1 e on d.ItemType1=e.Code " +
				" left join titemtype2 f on d.itemtype2=f.Code " +
				" left join ( " +
				" select b.WHCode,a.ITCode,sum(a.QtyCal) QtyCal " +
				" from tWHPosiBal a inner join tWareHousePosi b on a.WHPPk=b.PK " +
				" group by b.WHCode,a.ITCode) dd on a.Code=dd.WHCode and aa.ITCode=dd.ITCode " +
				" left join ( " +
				" select a.WHCode,a.ITCode,a.QtyCal " +
				" from tItemWHBal a) ff on a.Code=ff.WHCode and aa.ITCode=ff.ITCode " +
				" where 1=1 and isnull(aa.QtyCal,0)>0";
		
		sql += " and a.code in (select WHCode from tWareHouseOperater where CZYBH= ? and Expired='F') ";
		list.add(wareHouse.getCzybh());
		if (StringUtils.isBlank(wareHouse.getExpired()) || "F".equals(wareHouse.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(wareHouse.getCode())) {
			sql += " and a.Code = ? ";
			list.add(wareHouse.getCode());
		}
		if (null != wareHouse.getPk()) {
			sql += " and b.pk = ? ";
			list.add(wareHouse.getPk());
		}
		if (StringUtils.isNotBlank(wareHouse.getItCode())) {
			sql += "and aa.ITCode = ? ";
			list.add(wareHouse.getItCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " )a order by a.Code, a.ITCode "; 
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: TODO 上下架数据查询
	 * @author	created by zb
	 * @date	2018-8-16--上午10:02:09
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String, Object>> findDataBySql(
			Page<Map<String, Object>> page, WareHouse wareHouse) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select ff.QtyCal QtyAll,isnull(gg.QtyCal,0) QtyCal,ff.QtyCal-isnull(dd.QtyCal,0) QtyNo " +
				" from (" +
				"	select a.WHCode,a.ITCode,a.QtyCal from tItemWHBal a" +
				" ) ff " +
				" left join (" +
				"	select b.WHCode,a.ITCode,sum(a.QtyCal) QtyCal " +
				" 	from tWHPosiBal a " +
				"		inner join tWareHousePosi b on a.WHPPk=b.PK " +
				" 	group by b.WHCode,a.ITCode" +
				" ) dd on ff.WHCode=dd.WHCode and ff.ITCode=dd.ITCode " +
				" left join (" +
				"	select b.WHCode,a.ITCode,sum(a.QtyCal) QtyCal " +
				" 	from tWHPosiBal a " +
				"		inner join tWareHousePosi b on a.WHPPk=b.PK and b.pk= ? ";
		list.add(wareHouse.getPk());
		sql += 	" 	group by b.WHCode,a.ITCode" +
				" ) gg on ff.WHCode=gg.WHCode and ff.ITCode=gg.ITCode " +
				" where 1=1 and ff.ITCode= ? ";
		list.add(wareHouse.getItCode().trim());
		if (StringUtils.isNotBlank(wareHouse.getCode())) {
			sql += " and ff.WHCode= ? ";
			list.add(wareHouse.getCode());
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: TODO 库位调整上下架存储过程
	 * @author	created by zb
	 * @date	2018-8-21--上午11:12:25
	 * @param wareHouse
	 * @return
	 */
	public Result doSave(WareHouse wareHouse) {
		Assert.notNull(wareHouse);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pKwtz_forXml(?,?,?,?,?)}");
			call.setString(1, wareHouse.getM_umState());
			call.setString(2, wareHouse.getLastUpdatedBy());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.setString(5, wareHouse.getDetailJson());
//			System.out.println(wareHouse.getDetailJson());
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

	/**
	 * @Description: TODO 根据仓库编号、库位编号、材料编号获取上下架数量
	 * @author	created by zb
	 * @date	2018-8-22--上午11:35:03
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String, Object>> findMovePageBySql(
			Page<Map<String, Object>> page, WareHouse wareHouse) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select isnull(aa.QtyCal, isnull(a.QtyCal, 0)) QtyCal, " +
				" isnull(b.QtyCal, 0) - isnull(a.QtyCal, 0) QtyNo " +
				" from (" +
				"	select b.WHCode, a.ITCode, sum(a.QtyCal) QtyCal " +
				"	from tWHPosiBal a " +
				" 	inner join tWareHousePosi b on a.WHPPk = b.PK " +
				" 	group by b.WHCode, a.ITCode " +
				" ) a " +
				" left join ( " +
				"	select a.WHCode,a.ITCode,a.QtyCal " +
				" 	from tItemWHBal a " +
				" ) b on a.WHCode = b.WHCode and a.ITCode=b.ITCode " +
				" left join tWHPosiBal aa on aa.WHPPk = ? and aa.ITCode = a.ITCode " +
				" where  a.WHCode = ? and a.ITCode = ? ";
		
		list.add(wareHouse.getPk());
		list.add(wareHouse.getCode());
		list.add(wareHouse.getItCode());
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: TODO 查询材料分页信息
	 * @author	created by zb
	 * @date	2018-8-23--上午11:12:35
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, WareHouse wareHouse) {
		List<Object> list = new ArrayList<Object>();
		
		String sql = " select * from (" +
				" Select 0 as IsCheck,a.WHCode,a.ITCode,isnull(t.QtyCal,a.QtyCal) QtyCal,b.Desc1 WhDescr,c.Descr ItemDescr, " +
				" 	isnull(t.WHPPk,'') FromKw,w.Desc1 FromKwDescr " +
				" from tItemWHBal a " +
				" left join tWareHouse b on a.WHCode=b.Code " +
				" left join tItem c on a.ITCode=c.Code " +
				" left join (select a.ITCode,b.WHCode,a.QtyCal,a.WHPPK from tWHPosiBal a " +
				" inner join tWareHousePosi b on a.WHPPk=b.PK) t on a.WHCode=t.WHCode and a.ITCode=t.ITCode " +
				" left join tWareHousePosi w on t.WHPPk=w.pk " +
				" where 1=1 and a.WhCode= ? ";
		
		list.add(wareHouse.getCode());
		if ("R".equals(wareHouse.getM_umState())) {
			sql += " and t.WHPPk is null ";
		} else {
			sql += " and t.WHPPk is not null ";
		}
		if (StringUtils.isNotBlank(wareHouse.getItCode())) {
			sql += " and a.ITCode= ? ";
			list.add(wareHouse.getItCode());
		}
		if (StringUtils.isNotBlank(wareHouse.getItemDescr())) {
			sql += " and c.Descr like ? ";
			list.add("%"+wareHouse.getItemDescr()+"%");
		}
		if ("R".equals(wareHouse.getM_umState())) {
			sql += " and a.ITCode not in ('"+wareHouse.getHaveSelect().replace(",", "','")+"') ";
		} else {
			// 循环输入限制条件
			for ( int i =0;i<wareHouse.getHaveSelect().split(",").length;i++){
				sql+=" and not exists (select 1 where  a.ITCode = ? and t.WHPPK = ? ) " ;
				list.add(wareHouse.getHaveSelect().split(",")[i]);
				// list.add(wareHouse.getHaveFromKwPk().split(",").length<=i?"":wareHouse.getHaveFromKwPk().split(",")[i]);
				list.add(wareHouse.getHaveFromKwPk().split(",")[i]);
			}
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * @Description: TODO 导入Excel，检查数据是否在tItemWHBal表中存在
	 * @author	created by zb
	 * @date	2018-8-26--下午4:21:45
	 * @param code 仓库编号
	 * @param itCode 材料编号
	 * @return true-存在，false-不存在
	 */
	public boolean hasItem(String code, String itCode) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select 1 from tItemWHBal where WHCode=? and ITCode=?";
		list.add(code);
		list.add(itCode);
		List<Map<String, Object>> page = this.findBySql(sql, list.toArray());
		return !page.isEmpty();
	}

	/**
	 * @Description: TODO 检查是否存在该pk
	 * @author	created by zb
	 * @date	2018-8-26--下午5:44:56
	 * @param code
	 * @param pk
	 * @return
	 */
	public boolean checkPk(String code, Integer pk) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select 1 from tWareHousePosi where WHCode= ? and PK = ? ";
		list.add(code);
		list.add(pk);
		List<Map<String, Object>> page = this.findBySql(sql, list.toArray());
		return !page.isEmpty();
	}
	
}

