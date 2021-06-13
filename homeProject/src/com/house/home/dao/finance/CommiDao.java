package com.house.home.dao.finance;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Item;
import com.house.home.entity.finance.Commi;
import com.house.home.entity.finance.PerfCycle;

@SuppressWarnings("serial")
@Repository
public class CommiDao extends BaseDao {

	/**
	 * Commi分页信息
	 * 
	 * @param page
	 * @param commi
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Commi commi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select a.No, a.BeginDate, a.EndDate, a.Status, b.NOTE StatusDescr, a.PerfCycleNo,"
				+ " a.PrjPerfNo,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Remarks "
				+ " from tCommi a"
				+ " left join tXTDM b on b.ID='COMMISTATUS' and b.CBM=a.Status"
				+ " where 1=1 ";

		if (StringUtils.isNotBlank(commi.getNo())) {
			sql += " and a.no = ? ";
			list.add(commi.getNo());
		}
		if (commi.getDateFrom() != null) {
			sql += " and a.beginDate>= ?  ";
			list.add(commi.getDateFrom());
		}
		if (commi.getDateTo() != null) {
			sql += " and a.beginDate< dateAdd(d,1,?) ";
			list.add(commi.getDateTo());
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.beginDate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 查询状态
	 * 
	 * @param no
	 * @return
	 */
	public String checkStatus(String no) {
		String sql = " select Status from tCommi where No= ?  ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { no });
		if (list != null && list.size() > 0) {
			return list.get(0).get("Status").toString();
		} else {
			return null;
		}
	}

	/**
	 * 计算完成
	 * 
	 * @param no
	 */
	public void doSaveCount(String no) {
		String sql = " update tCommi set Status='2' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}

	/**
	 * 退回
	 * 
	 * @param no
	 */
	public void doSaveCountBack(String no) {
		String sql = " update tCommi set Status='1' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[] { no });
	}

	/**
	 * 检查是否能计算周期
	 * 
	 * @param no
	 * @param beginDate
	 * @return
	 */
	public String isExistsPeriod(String no, String beginDate) {
		String sql = " select BeginDate from tCommi where No= ?  and Status='1'";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { no });
		if (list != null && list.size() > 0) {
			String sqlDate = " select 1 from tCommi where BeginDate< ? and Status='1' ";
			List<Map<String, Object>> listDate = this.findBySql(sqlDate,
					new Object[] { list.get(0).get("BeginDate") });
			if (listDate != null && listDate.size() > 0) {
				return "2";// 之前的统计周期未计算完成，不允许计算本周期业绩!
			} else {
				return null;
			}
		} else {
			return "1";// 没有找到相应的业绩统计周期或该统计周期已计算完成!
		}
	}
	/**
	 * 主材提成客户列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findCustBySql(
			Page<Map<String, Object>> page, Commi commi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.pk,b.DocumentNo,b.Address,a.CustCode,a.DesignMan,c1.NameChi DesignManDescr,a.CommiAmount, "
				+ "a.SceneDesignMan,c2.NameChi SceneDesignManDescr,a.Type,e.NOTE TypeDescr,a.LineAmount,a.LastUpdate, "
				+ "a.LastUpdatedBy,a.Expired,a.ActionLog,d1.Desc1 DesignManDeptDescr,d2.Desc1 SceneDesignManDeptDescr "
				+ "from tCommiCust a "
				+ "left join tCustomer b on a.CustCode=b.Code "
				+ "left join tEmployee c1 on a.DesignMan=c1.Number "
				+ "left join tEmployee c2 on a.SceneDesignMan=c2.Number "
				+ "left join tDepartment2 d1 on c1.Department2=d1.Code "
				+ "left join tDepartment2 d2 on c2.Department2=d2.Code "
				+ "left join tXTDM e on e.ID='COMMICUSTTYPE' and e.IBM=a.Type "
				+ "where a.No=? ";
		list.add(commi.getNo());
		if (StringUtils.isNotBlank(commi.getCommiType())) {
			sql += " and a.Type in " + "('"
					+ commi.getCommiType().replace(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(commi.getAddress())) {
			sql += " and b.Address like ?";
			list.add("%"+commi.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(commi.getCustCode())) {
			sql += " and a.CustCode = ?";
			list.add(commi.getCustCode());
		}
		if (StringUtils.isNotBlank(commi.getDesignMan())) {
			sql += " and a.DesignMan = ?";
			list.add(commi.getDesignMan());
		}
		if (StringUtils.isNotBlank(commi.getSceneDesignMan())) {
			sql += " and a.SceneDesignMan = ?";
			list.add(commi.getSceneDesignMan());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 主材提成材料列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findItemBySql(
			Page<Map<String, Object>> page, Commi commi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.pk,b.DocumentNo,b.Address,a.CustCode,i.DesignMan,c1.NameChi DesignManDescr,i.SceneDesignMan,"
				+ "c2.NameChi SceneDesignManDescr,a.CommiType,e.NOTE CommiTypeDescr,a.BefLineAmount,a.Markup,a.CommiCustPK, "
				+ "a.ItemCode,f.Descr ItemDescr,f.ItemType2,g.Descr ItemType2Descr,f.Uom,h.Descr UomDescr,a.Qty,a.Cost, "
				+ "a.CommiPerc,a.LineAmount,a.Remark,a.Profit,a.ProcessCost,a.ProfitPer,a.CommiAmount, "
				+ "(a.BefLineAmount*a.Markup/100) TmpLineAmount,a.CostAmount,a.UnitPrice,d1.Desc1 DesignManDeptDescr, "
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,d2.Desc1 SceneDesignManDeptDescr,j.Descr FixAreaDescr "
				+ "from tCommiItem a  "
				+ "left join tCustomer b on a.CustCode=b.Code "
				+ "left join tCommiCust i on a.CommiCustPK=i.PK "
				+ "left join tEmployee c1 on i.DesignMan=c1.Number "
				+ "left join tEmployee c2 on i.SceneDesignMan=c2.Number "
				+ "left join tDepartment2 d1 on c1.Department2=d1.Code "
				+ "left join tDepartment2 d2 on c2.Department2=d2.Code "
				+ "left join tXTDM e on e.ID='COMMITYPE' and e.IBM=a.CommiType "
				+ "left join tItem f on a.ItemCode=f.Code "
				+ "left join tItemType2 g on f.ItemType2=g.Code "
				+ "left join tUOM h on f.Uom=h.Code "
				+ "left join tFixArea j on j.pk=a.FixAreaPk "
				+ "where a.No=? ";
		list.add(commi.getNo());
		if(StringUtils.isNotBlank(commi.getCommiType())){//只有提成类型为2的才有材料明细
			if (!commi.getCommiType().contains("2")) {
				sql += " and a.CommiType ='99' ";
			}
		}
		if (StringUtils.isNotBlank(commi.getAddress())) {
			sql += " and b.Address like ?";
			list.add("%" + commi.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(commi.getCustCode())) {
			sql += " and a.CustCode = ?";
			list.add(commi.getCustCode());
		}
		if (StringUtils.isNotBlank(commi.getDesignMan())) {
			sql += " and i.DesignMan = ?";
			list.add(commi.getDesignMan());
		}
		if (StringUtils.isNotBlank(commi.getSceneDesignMan())) {
			sql += " and i.SceneDesignMan = ?";
			list.add(commi.getSceneDesignMan());
		}
		if (StringUtils.isNotBlank(commi.getItemCode())) {
			sql += " and a.ItemCode = ?";
			list.add(commi.getItemCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.address ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 生成提成数据
	 * 
	 * @param no
	 * @param lastUpdatedBy
	 */
	public Map<String, Object> doCount(String no, String lastUpdatedBy,String isRegenCommiPerc) {
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCalcDesignManMainCommi(?,?,?)}");
			call.setString(1, no);
			call.setString(2, lastUpdatedBy);
			call.setString(3, isRegenCommiPerc);
			call.execute();
			ResultSet rs = call.getResultSet();
			List<Map<String, Object>> list = BeanConvertUtil
					.resultSetToList(rs);
			if (list.size() != 0 && list != null) {
				if (list.get(0).get("errmsg") != null)
					return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return null;
	}
	/**
	 * 客户明细map
	 * 
	 * @param pk
	 */
	public Map<String, Object> findCustMap(Page<Map<String, Object>> page,String pk) {
		String sql = "select a.pk,b.DocumentNo,b.Address,a.CustCode,a.DesignMan,c1.NameChi DesignManDescr,a.CommiAmount, "
				+ "a.SceneDesignMan,c2.NameChi SceneDesignManDescr,a.Type,e.NOTE TypeDescr,a.LineAmount,a.LastUpdate, "
				+ "a.LastUpdatedBy,a.Expired,a.ActionLog,d1.Desc1 DesignManDeptDescr,d2.Desc1 SceneDesignManDeptDescr "
				+ "from tCommiCust a "
				+ "left join tCustomer b on a.CustCode=b.Code "
				+ "left join tEmployee c1 on a.DesignMan=c1.Number "
				+ "left join tEmployee c2 on a.SceneDesignMan=c2.Number "
				+ "left join tDepartment2 d1 on c1.Department2=d1.Code "
				+ "left join tDepartment2 d2 on c2.Department2=d2.Code "
				+ "left join tXTDM e on e.ID='COMMICUSTTYPE' and e.IBM=a.Type "
				+ "where a.pk=?";
		return this.findPageBySql(page, sql, new Object[] {pk}).getResult().get(0);
	}
	/**
	 * 材料明细map
	 * 
	 * @param pk
	 */
	public Map<String, Object> findItemMap(Page<Map<String, Object>> page,String pk) {
		String sql = "select a.pk,b.DocumentNo,b.Address,a.CustCode,i.DesignMan,c1.NameChi DesignManDescr,i.SceneDesignMan,"
				+ "c2.NameChi SceneDesignManDescr,a.CommiType,e.NOTE CommiTypeDescr,a.BefLineAmount,a.Markup,a.CommiCustPK, "
				+ "a.ItemCode,f.Descr ItemDescr,f.ItemType2,g.Descr ItemType2Descr,f.Uom,h.Descr UomDescr,a.Qty,a.Cost, "
				+ "a.CommiPerc,a.LineAmount,a.Remark,a.Profit,a.ProcessCost,a.ProfitPer*100 ProfitPer,a.CommiAmount, "
				+ "(a.BefLineAmount*a.Markup/100) TmpLineAmount,a.CostAmount,a.UnitPrice,d1.Desc1 DesignManDeptDescr, "
				+ "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,d2.Desc1 SceneDesignManDeptDescr,j.Descr FixAreaDescr "
				+ "from tCommiItem a  "
				+ "left join tCustomer b on a.CustCode=b.Code "
				+ "left join tCommiCust i on a.CommiCustPK=i.PK "
				+ "left join tEmployee c1 on i.DesignMan=c1.Number "
				+ "left join tEmployee c2 on i.SceneDesignMan=c2.Number "
				+ "left join tDepartment2 d1 on c1.Department2=d1.Code "
				+ "left join tDepartment2 d2 on c2.Department2=d2.Code "
				+ "left join tXTDM e on e.ID='COMMITYPE' and e.IBM=a.CommiType "
				+ "left join tItem f on a.ItemCode=f.Code "
				+ "left join tItemType2 g on f.ItemType2=g.Code "
				+ "left join tUOM h on f.Uom=h.Code "
				+ "left join tFixArea j on j.pk=a.FixAreaPk "
				+ "where a.pk=? ";
		return this.findPageBySql(page, sql, new Object[] {pk}).getResult().get(0);
	}
	/**
	 * 材料需求列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findItemReqBySql(Page<Map<String, Object>> page, Commi commi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select  a.CustCode,b.Descr itemDescr,c.Descr fixAreaDescr,e.Address,"
				+ "d.NOTE commiTypeDescr, a.PK,a.FixAreaPK,a.ItemCode,a.Qty,a.Cost,a.UnitPrice, "
				+ "a.CommiType,isnull(a.CommiPerc,0)CommiPerc,a.Cost*a.Qty+case when ProcessCost>0 then ProcessCost else 0 end CostAmount, "
				+ "a.LineAmount-(a.Cost*a.Qty+case when ProcessCost>0 then ProcessCost else 0 end)Profit, "
				+ "case when a.LineAmount<>0 then (a.LineAmount-(a.Cost*a.Qty+case when ProcessCost>0 then ProcessCost else 0 end))/a.LineAmount else 0 end ProfitPer, "
				+ "a.LineAmount*isnull(a.CommiPerc,0)CommiAmount,(a.BefLineAmount*a.Markup/100) TmpLineAmount, "
				+ "a.BefLineAmount,a.Markup,a.ProcessCost,a.LineAmount,a.Remark,f.Descr itemType2Descr,g.Note isOutSetDescr, "
				+ "case when a.CommiType='1' then a.CommiPerc else a.CommiPerc*a.LineAmount end CommiAmounts,a.IsCommi "
				+ "from  tItemReq a  "
				+ "left join tItem b on a.ItemCode=b.Code  "
				+ "left join tFixArea c on a.FixAreaPK=c.PK "
				+ "left join tXTDM d on a.CommiType=d.CBM and d.ID='COMMITYPE' "
				+ "left join tCustomer e on a.CustCode=e.Code "
				+ "left join tItemType2 f on b.ItemType2=f.Code "
				+ "left join tXTDM g on a.IsOutSet=g.CBM and g.ID='YESNO' "
				+ "where 1=1 and a.ItemType1='ZC' " 
				+ "and not exists (select 1 from tCommiItem ci inner join tCommi cm on ci.No=cm.No  where a.PK=ci.ReqPK and cm.status='2' and a.CustCode=ci.CustCode)  ";//已计算过提成的过滤掉
		
		if (StringUtils.isNotBlank(commi.getCustCode())) {
			sql += " and a.CustCode = ?";
			list.add(commi.getCustCode());
		}
		if (commi.getSignDateFrom() != null) {
			sql += " and e.signDate>= ? ";
			list.add(commi.getSignDateFrom() );
		}
		if (commi.getSignDateTo()!= null) {
			sql += " and e.signDate< ? ";
			list.add(DateUtil.addInteger(commi.getSignDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(commi.getItemDescr())) {
			sql += " and b.Descr like ?";
			list.add("%"+commi.getItemDescr()+"%");
		}
		if (StringUtils.isNotBlank(commi.getItemType2())) {
			sql += " and b.itemType2 = ?";
			list.add(commi.getItemType2());
		}
		if (StringUtils.isNotBlank(commi.getItemCode())) {
			sql += " and a.ItemCode = ?";
			list.add(commi.getItemCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.address ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 批量更提成类型，提成点数
	 * @param commi
	 */
	public void doUpdateBatch(Commi commi){
		String sql="update tItemReq set  lastUpdatedBy =?, LastUpdate=?, ActionLog='EDIT' ";
		
		if (StringUtils.isNotBlank(commi.getCommiType())) {
			sql +=" ,CommiType='"+commi.getCommiType()+"'" ;
			
		}
		if (commi.getCommiPerc() != null && StringUtils.isNotBlank(commi.getCommiPerc())) {
			sql +=" ,CommiPerc="+commi.getCommiPerc() ;	
		}
		sql += " where pk in " + "('"+ commi.getReqPks().replace(",", "','") + "')";
		System.out.println("hhh"+sql);
		this.executeUpdateBySql(sql, new Object[]{commi.getLastUpdatedBy(),commi.getLastUpdate()});				
	}	
}
