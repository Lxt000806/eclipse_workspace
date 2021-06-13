package com.house.home.dao.project;
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
import com.house.home.entity.project.SpecItemReq;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class SpecItemReqDao extends BaseDao{

	/**
	 * @Description: 集成解单管理分页查询
	 * @author	created by zb
	 * @date	2018-10-7--下午5:28:41
	 * @param page
	 * @param specItemReq
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SpecItemReq specItemReq) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from(" +
				" select a.Code CustCode, a.Descr CustName, a.Address, a.Status, tx.NOTE StatusDescr, a.CustType, tx2.NOTE CustTypeDescr, " +
				" a.Area, c1.EmpCode IntDesignCode, e1.NameChi IntDesign,c2.EmpCode CupDesignCode, e2.NameChi CupDesign, " +
				" b.CupDownHigh, b.CupUpHigh, b.CupLastupdatedBy, te.NameChi CupLastupdatedByDescr, b.CupLastUpdate, b.IntLastupdatedBy, " +
				" te2.NameChi IntLastupdatedByDescr, b.IntLastupdate, a.LastUpdate,e3.NameChi jcSpecMan,e4.NameChi cgSpecMan,b.BathUpHigh,b.BathDownHigh," +
				" d.hasCheck " +
				" from tCustomer a " +
				" left join tSpecItemReq b on b.CustCode = a.Code " +
				" left join tCustStakeholder c1 on c1.CustCode = a.Code and c1.Role = '11' " +
				" left join tEmployee e1 on e1.Number = c1.EmpCode " +
				" left join tCustStakeholder c2 on c2.CustCode = a.Code and c2.Role = '61' " +
				" left join tEmployee e2 on e2.Number = c2.EmpCode " +
				" left join tCustStakeholder c3 on c3.CustCode = a.Code and c3.Role = '67' " +
				" left join tEmployee e3 on e3.Number = c3.EmpCode " +
				" left join tCustStakeholder c4 on c4.CustCode = a.Code and c4.Role = '68' " +
				" left join tEmployee e4 on e4.Number = c4.EmpCode " +
				" left join tXTDM tx on tx.CBM = a.Status and tx.ID = 'CUSTOMERSTATUS' " +
				" left join tXTDM tx2 on tx2.CBM = a.CustType and tx2.ID = 'CUSTTYPE' " +
				" left join tEmployee te on te.Number = b.CupLastupdatedBy " +
				" left join tEmployee te2 on te2.Number = b.IntLastupdatedBy " +
				" left join (select count(1) hasCheck,CustCode from tItemCheck " +
				" where ItemType1 = 'JC' and Status = '2'" +
				" group by CustCode)d on d.CustCode = a.Code" +
				" where 1=1 ";
	
		if (StringUtils.isNotBlank(specItemReq.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%"+specItemReq.getAddress().trim()+"%");
		}
		if (StringUtils.isNotBlank(specItemReq.getStatus())) {
			sql += " and a.Status in ('"+specItemReq.getStatus().replace(",", "','")+"') ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.CupLastUpdate desc,a.IntLastupdate desc,a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: 详细分页查询
	 * @author	created by zb
	 * @date	2018-10-9--上午11:58:34
	 * @param page
	 * @param custCode
	 * @param iscupboard 是否为橱柜
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, String custCode, String iscupboard) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.PK, a.CustCode, a.ItemType1, b.Descr ItemType1Descr, a.IsCupboard, tx.NOTE IsCupboardDescr, " +
				" a.IntProdPK, c.Descr IntProdPKDescr, a.ItemCode, d.Descr ItemDescr, isnull(a.Qty,0) Qty, a.Cost MoveCost, a.Remark, " +
				" d.Price, isnull(d.Cost,0) Cost, isnull(a.Qty,0)*isnull(d.Cost,0) TotalCost, isnull(q.AppQty,0) AppQty,q.isExist, " +
				" a.LastUpdatedBy, a.LastUpdate, a.ActionLog, a.Expired" +
				" from tSpecItemReqDt a " +
				" left join tItemType1 b on b.Code = a.ItemType1 " +
				" left join tIntProd c on c.PK = a.IntProdPK " +
				" left join tXTDM tx on tx.CBM = a.IsCupboard and tx.ID = 'YESNO' " +
				" left join tItem d on d.Code = a.ItemCode " +
				" left join ( " +
				" 	select a.SpecReqPk,a.isExist,c.AppAddQty,d.AppCutQty,isnull(c.AppAddQty,0) - isnull(d.AppCutQty,0) AppQty "+
				"	from ( "+
				"		select a.SpecReqPk,1 isExist "+
				"		from tItemAppDetail a "+
				"		left join tItemApp b on b.No = a.No "+
				"		where a.SpecReqPk > -1 and b.Status <> 'CANCEL' and b.CustCode = ? "+
				"		group by a.SpecReqPk "+
				"	)a "+
				"	left join ( "+
				"		select a.SpecReqPk,sum(a.Qty) AppAddQty "+
				"		from tItemAppDetail a "+
				"		left join tItemApp b on b.No = a.No "+
				"		where a.SpecReqPk > -1 and b.Type = 'S' and b.Status not in ('CANCEL','RETURN') "+
				"		and b.CustCode = ? "+
				"		group by a.SpecReqPk "+
				"	)c on c.SpecReqPk = a.SpecReqPk "+
				"	left join ( "+
				"		select a.SpecReqPk,sum(a.Qty) AppCutQty "+
				"		from tItemAppDetail a "+
				"		left join tItemApp b on b.No = a.No "+
				"		where a.SpecReqPk > -1 and b.Type = 'R' and b.Status = 'RETURN' "+
				"		and b.CustCode = ? "+
				"		group by a.SpecReqPk "+
				"	)d on d.SpecReqPk = a.SpecReqPk "+
				" ) q on q.SpecReqPk = a.PK " +
				" where a.CustCode = ? ";
		list.add(custCode);
		list.add(custCode);
		list.add(custCode);
		list.add(custCode);
		if (StringUtils.isNotBlank(iscupboard)) {
			sql += " and a.IsCupboard = ? ";
			list.add(iscupboard);
		}
		sql += " order by a.LastUpdate ";
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: 根据成品名称获取成品pk
	 * @author	created by zb
	 * @date	2018-10-11--下午6:20:54
	 * @param custCode
	 * @param productName
	 * @return
	 */
	public Map<String, Object> getIntProd(String custCode, String productName) {
		String sql = " select a.PK, a.Descr, a.FixAreaPK, a.IsCupboard " +
				" from tIntProd a " +
				" left join tFixArea b on b.PK = a.FixAreaPK " +
				" where b.CustCode = ? and b.ItemType1 = 'JC' and a.Descr = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,productName});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * @Description: 根据客户编号、材料编号获取已下单数量
	 * @author	created by zb
	 * @date	2018-10-12--上午10:48:58
	 * @param custCode
	 * @param itemCode
	 * @return
	 */
	public Map<String, Object> getAppQty(String custCode, String itemCode) {
		String sql = " select a.SpecReqPk,a.isExist,c.AppAddQty,d.AppCutQty,isnull(c.AppAddQty,0) - isnull(d.AppCutQty,0) AppQty "+
				" from ( "+
				" 	select a.SpecReqPk,1 isExist "+
				" 	from tItemAppDetail a "+
				" 	left join tItemApp b on b.No = a.No "+
				" 	where a.SpecReqPk > -1 and b.Status <> 'CANCEL' and b.CustCode = ? "+
				" 	group by a.SpecReqPk "+
				" )a "+
				" left join ( "+
				" 	select a.SpecReqPk,sum(a.Qty) AppAddQty "+
				" 	from tItemAppDetail a "+
				" 	left join tItemApp b on b.No = a.No "+
				" 	where a.SpecReqPk > -1 and b.Type = 'S' and b.Status not in ('CANCEL','RETURN') "+
				" 	and b.CustCode = ? "+
				" 	group by a.SpecReqPk "+
				" )c on c.SpecReqPk = a.SpecReqPk "+
				" left join ( "+
				" 	select a.SpecReqPk,sum(a.Qty) AppCutQty "+
				" 	from tItemAppDetail a "+
				" 	left join tItemApp b on b.No = a.No "+
				" 	where a.SpecReqPk > -1 and b.Type = 'R' and b.Status = 'RETURN' "+
				" 	and b.CustCode = ? "+
				" 	group by a.SpecReqPk "+
				" )d on d.SpecReqPk = a.SpecReqPk "+
				" where a.SpecReqPk = ?";

		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,custCode,custCode,itemCode});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * @Description: 保存
	 * @author	created by zb
	 * @date	2018-10-13--下午12:00:51
	 * @param specItemReq
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSave(SpecItemReq specItemReq) {
		Assert.notNull(specItemReq);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSpecItemReq(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, specItemReq.getCustCode());
			call.setString(2, specItemReq.getM_umState());
			call.setDouble(3, specItemReq.getCupUpHigh()==null?0:specItemReq.getCupUpHigh());
			call.setDouble(4, specItemReq.getCupDownHigh()==null?0:specItemReq.getCupDownHigh());
			call.setString(5, specItemReq.getLastUpdatedBy());
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.setString(8, specItemReq.getDetailJson());
			call.setTimestamp(9, specItemReq.getCupLastUpdate()==null?null : new Timestamp(specItemReq.getCupLastUpdate().getTime()));//由long转为time
			call.setString(10, specItemReq.getCupLastupdatedBy());
			call.setTimestamp(11, specItemReq.getIntLastupdate()==null?null : new Timestamp(specItemReq.getIntLastupdate().getTime()));//由long转为time
			call.setString(12, specItemReq.getIntLastupdatedBy());
			call.setDouble(13, specItemReq.getBathUpHigh()==null?0:specItemReq.getBathUpHigh());
			call.setDouble(14, specItemReq.getBathDownHigh()==null?0:specItemReq.getBathDownHigh());
			call.setString(15, specItemReq.getHasStove());
			call.setString(16, specItemReq.getIsSelfMetal_Cup());
			call.setString(17, specItemReq.getIsSelfMetal_Int());
			call.setString(18, specItemReq.getIsSelfSink());
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
	/**
	 * 明细查询
	 * @author cjg
	 * @date 2019-8-15
	 * @param page
	 * @param specItemReq
	 * @return
	 */
	public Page<Map<String, Object>> goDetailQuery(Page<Map<String, Object>> page, SpecItemReq specItemReq) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select  a.CustCode, b.Descr ItemType1Descr,tx.NOTE IsCupboardDescr, "
				+ "a.IntProdPK, c.Descr IntProdPKDescr, a.ItemCode, d.Descr ItemDescr, isnull(a.Qty,0) Qty, a.Cost MoveCost, a.Remark,  "
				+ "d.Price, isnull(d.Cost,0) Cost, isnull(a.Qty,0)*isnull(d.Cost,0) TotalCost,g.Address,h.Desc1 CustTypeDescr, "
				+ "a.LastUpdatedBy, a.LastUpdate, a.ActionLog, a.Expired,e.Descr ItemType2Descr,f.Descr ItemType3Descr "
				+ "from tSpecItemReqDt a  "
				+ "left join tItemType1 b on b.Code = a.ItemType1  "
				+ "left join tIntProd c on c.PK = a.IntProdPK  "
				+ "left join tXTDM tx on tx.CBM = a.IsCupboard and tx.ID = 'YESNO' "
				+ "left join tItem d on d.Code = a.ItemCode  "
				+ "left join tItemType2 e on d.ItemType2=e.Code "
				+ "left join tItemType3 f on d.ItemType3=f.Code "
				+ "left join tCustomer g on a.CustCode=g.Code "
				+ "left join tCusttype h on g.CustType=h.Code where 1=1 ";
		if (StringUtils.isNotBlank(specItemReq.getIsCupboard())) {
			sql += " and a.IsCupboard = ? ";
			list.add(specItemReq.getIsCupboard());
		}
		if (StringUtils.isNotBlank(specItemReq.getItemType2())) {
			sql += " and d.ItemType2 = ? ";
			list.add(specItemReq.getItemType2());
		}
		if (StringUtils.isNotBlank(specItemReq.getItemType3())) {
			sql += " and d.ItemType3 = ? ";
			list.add(specItemReq.getItemType3());
		}
		if (specItemReq.getDateFrom() != null) {
			sql += " and a.LastUpdate >= ? ";
			list.add(specItemReq.getDateFrom());
		}
		if (specItemReq.getDateTo() != null) {
			sql += " and a.LastUpdate < dateadd(day,1,?) ";
			list.add(specItemReq.getDateTo());
		}
		if (StringUtils.isNotBlank(specItemReq.getAddress())) {
			sql += " and g.Address like ? ";
			list.add("%"+specItemReq.getAddress().trim()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 明细查询--按材料类型3汇总
	 * @param page
	 * @param specItemReq
	 * @return
	 */
	public Page<Map<String, Object>> goDetailQuery_itmeType3Total(Page<Map<String, Object>> page, SpecItemReq specItemReq) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select  a.CustCode, g.Address, h.Desc1 CustTypeDescr, tx.NOTE IsCupboardDescr,f.Descr ItemType3Descr," 
				+ " case when a.IsCupboard='0' then f.IntInstallFee else xc.qz end  IntInstallFee, "
		 		+" sum(isnull(a.Qty, 0)) Qty,sum(isnull(iad.qty,0)) IAQty "
				+ "from tSpecItemReqDt a  "
				+ "left join tItemType1 b on b.Code = a.ItemType1  "
				+ "left join tXTDM tx on tx.CBM = a.IsCupboard and tx.ID = 'YESNO' "
				+ "left join tItem d on d.Code = a.ItemCode  "
				+ "left join tItemType3 f on d.ItemType3=f.Code "
				+ "left join tCustomer g on a.CustCode=g.Code "
				+ "left join tCusttype h on g.CustType=h.Code "
				+ "left join txtcs xc on xc.id ='cupinsfee' "
				+ "left join (select sum(isnull(case when ia.type='s' then iad.qty else (-1.0)* iad.qty end,0)) qty,iad.SpecReqPK "
				+ "  from tItemAppDetail iad "
				+ "  left join tItemApp ia on ia.no=iad.no "
				+ "  where Status<>'CANCEL' " 
				+ " group by SpecReqPK "
				+ "  )iad on iad.SpecReqPK=a.pk "	
				+ "where 1=1 ";
		if (StringUtils.isNotBlank(specItemReq.getIsCupboard())) {
			sql += " and a.IsCupboard = ? ";
			list.add(specItemReq.getIsCupboard());
		}
		if (StringUtils.isNotBlank(specItemReq.getItemType2())) {
			sql += " and d.ItemType2 = ? ";
			list.add(specItemReq.getItemType2());
		}
		if (StringUtils.isNotBlank(specItemReq.getItemType3())) {
			sql += " and d.ItemType3 = ? ";
			list.add(specItemReq.getItemType3());
		}
		if (specItemReq.getDateFrom() != null) {
			sql += " and a.LastUpdate >= ? ";
			list.add(specItemReq.getDateFrom());
		}
		if (specItemReq.getDateTo() != null) {
			sql += " and a.LastUpdate < dateadd(day,1,?) ";
			list.add(specItemReq.getDateTo());
		}
		if (StringUtils.isNotBlank(specItemReq.getAddress())) {
			sql += " and g.Address like ? ";
			list.add("%"+specItemReq.getAddress().trim()+"%");
		}
		sql +=" group by  a.CustCode,g.Address, h.Desc1,tx.NOTE,case when a.IsCupboard='0' then f.IntInstallFee else xc.qz end, "
         	+ " case when a.IsCupboard='0' then f.IntInstallFee else xc.qz end ,f.Descr ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.CustCode desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 查询干系人（解单员）
	 * @author cjg
	 * @date 2019-8-15
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getStakeholderInfo(String custCode) {
		String sql=" select isnull(a.jcSpecMan,'') jcSpecMan,isnull(e1.NameChi,'') jcSpecManDescr,isnull(a.cgSpecMan,'') cgSpecMan," +
				" isnull(e2.NameChi,'') cgSpecManDescr,b.intDesignDate,b.cupDesignDate from (   " +
				"	select CustCode,max(case when Role='67' then EmpCode end) jcSpecMan,   max(case when Role='68' then EmpCode end) cgSpecMan "
				+" 	from tCustStakeholder where CustCode=? group by CustCode" +
				"  ) a " 
				+" left join tEmployee e1 on e1.Number=a.jcSpecMan " 
				+" left join tEmployee e2 on e2.Number=a.cgSpecMan "
				+" left join tCustIntProg b on b.CustCode = a.CustCode";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
