package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.ListCompareUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.basic.CzybmDao;
import com.house.home.entity.insales.ItemApp;
@SuppressWarnings("serial")
@Repository
public class ItemSendAnalyseDao extends BaseDao {
	
	@Autowired
	private CzybmDao czybmDao;

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemApp itemApp, UserContext uc) {
		Assert.notNull(itemApp);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pQry_PPXSFX(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, DateUtil.DateToString(itemApp.getSendDateFrom()));
			call.setString(2, DateUtil.DateToString(itemApp.getSendDateTo()));
			call.setString(3, itemApp.getItemType1());
			call.setString(4, itemApp.getItemType2());
			call.setString(5, itemApp.getItemType3());
			call.setString(6, itemApp.getSqlCode());
			call.setString(7, itemApp.getSupplCode());
			call.setString(8, itemApp.getItemCode());
			call.setString(9, itemApp.getGroupType());
			call.setString(10, itemApp.getSendCzy());
			call.setString(11, itemApp.getDepartment2());
			call.setString(12, uc.getCzybh());
			call.setString(13, itemApp.getContainCmpCust());
			call.setString(14, itemApp.getCustType());
			call.setString(15, itemApp.getSendType());
			call.setString(16, itemApp.getIsSetItem());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			if(StringUtils.isNotBlank(page.getPageOrderBy())){
				Collections.sort(list, new ListCompareUtil(page.getPageOrderBy(),page.getPageOrder()));
			}
			page.setResult(list); 			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}

	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, ItemApp itemApp) {
		List<Object> list = new ArrayList<Object>();
		list.add(itemApp.getCustCode());
		// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//		String sql=" if exists(select * from tCZYGNQX where CZYBH=? and MKDM='0038' and GNMC='只查看买手部门')"
//		          +"   select ret='2'"
//		          +" else if exists(select 1 from tCZYGNQX where CZYBH=? and MKDM='0038' and GNMC='只查看买手本人')"
//		          +" select ret='1'  else select ret='0' ";
//		Map<String, Object>	map=this.findBySql(sql, new Object[]{itemApp.getLastUpdatedBy(),itemApp.getLastUpdatedBy()}).get(0);
		String sql="select a.No,c.Address,c.Area,a.SendDate,b.ItemCode,d.Descr,case when a.Type='S' then b.SendQty else b.SendQty*-1 end Qty,"
         + "case when e.UnitPrice is null or e.IsOutSet='0' then isnull(b.ProjectCost,d.ProjectCost) else e.UnitPrice*e.Markup/100 end UnitPrice,"
         + "(case when e.UnitPrice is null or e.IsOutSet='0' then isnull(b.ProjectCost,d.ProjectCost) else e.UnitPrice*e.Markup/100 end) "
         + "*(case when a.Type='S' then b.SendQty else b.SendQty*-1 end) SaleAmount, "
         + "b.Cost,b.Cost*(case when a.Type='S' then b.SendQty else b.SendQty*-1 end) PurchaseAmount, "
         + "((case when e.UnitPrice is null or e.IsOutSet='0' then d.Price else e.UnitPrice*e.Markup/100 end)- b.Cost) "
         + "*(case when a.Type='S' then b.SendQty else b.SendQty*-1 end) Profit "
		 + "from tItemApp a  "
         + "inner join tItemAppDetail b on a.No=b.No "
	     + "inner join tCustomer c on a.CustCode=c.Code "
		 + "inner join tItem d on b.ItemCode=d.Code "
		 + "left join tItemReq e on b.ReqPk=e.PK "
         + "left join tEmployee e1 on d.Buyer1=e1.Number "
         + "left join tEmployee e2 on d.Buyer2=e2.Number "
		 + "where a.Status in ('SEND','CONFIRMED') and b.Qty<>0 ";
		 if(itemApp.getSendDateFrom()!=null){
			  sql+=" and a.SendDate>='"+DateUtil.DateToString(itemApp.getSendDateFrom())+"' ";
			 
		 }
		 if(itemApp.getSendDateTo()!=null){
			  sql+=" and a.SendDate<='"+DateUtil.DateToString(itemApp.getSendDateTo())+"' ";
			 
		 }
		if("1".equals(itemApp.getGroupType())){
			sql+="and d.SqlCode=? ";
		}else if("2".equals(itemApp.getGroupType())){
			sql+="and d.ItemType2=? ";
		}else if("3".equals(itemApp.getGroupType())){
			sql+="and d.ItemType3=? ";
		}else{
			sql+="and d.Code=? ";
		}
		if(StringUtils.isNotBlank(itemApp.getItemType1())){
			sql+="and d.ItemType1=? ";
			list.add(itemApp.getItemType1().trim());
		}
		if(StringUtils.isNotBlank(itemApp.getItemType2())){
			sql+="and d.ItemType2=? ";
			list.add(itemApp.getItemType2().trim());
		}
		if(StringUtils.isNotBlank(itemApp.getItemType3())){
			sql+="and d.ItemType3=? ";
			list.add(itemApp.getItemType3().trim());
		}
		if(StringUtils.isNotBlank(itemApp.getSqlCode())){
			sql+="and d.SqlCode =? ";
			list.add(itemApp.getSqlCode());
		}
		if(StringUtils.isNotBlank(itemApp.getSupplCode())){
			sql+="and d.SupplCode =? ";
			list.add(itemApp.getSupplCode());
		}
		if(StringUtils.isNotBlank(itemApp.getSendCzy())){
			sql+="and (d.Buyer1 =? or d.Buyer2 =?) ";
			list.add(itemApp.getSendCzy());
			list.add(itemApp.getSendCzy());
		}
		if(StringUtils.isNotBlank(itemApp.getDepartment2())){
			sql+="and (e1.Department2 =? or e2.Department2 = ?)";
			list.add(itemApp.getDepartment2());
			list.add(itemApp.getDepartment2());
		}
		if("4".equals(itemApp.getGroupType())){
			String buyerRight = "0"; //map.get("ret").toString(); 
			if (czybmDao.hasGNQXByCzybh(itemApp.getLastUpdatedBy(), "0038", "只查看买手部门")) {
				buyerRight = "2";
			} else if (czybmDao.hasGNQXByCzybh(itemApp.getLastUpdatedBy(), "0038", "只查看买手本人")) {
				buyerRight = "1";
			}
			if("1".equals(buyerRight)){
				sql+="and (d.Buyer1=? or d.Buyer2=?) ";
				//当操作员编号和员工号相等
				list.add(itemApp.getLastUpdatedBy());
				list.add(itemApp.getLastUpdatedBy());
			}else if("2".equals(buyerRight)){
				sql+=" and exists ("
	                 +" select 1 from tEmployee e1"
	                 +" inner join tEmployee e2 on e2.Department1=e1.Department1 and (e2.Department2=e1.Department2 or e1.Department2 is null or e1.Department2='')"
	                 +" where e1.Number=? and (d.Buyer1=e2.Number or d.Buyer2=e2.Number)) ";
				//当操作员编号和员工号相等
				list.add(itemApp.getLastUpdatedBy());
			}
		}
		if(StringUtils.isNotBlank(itemApp.getCustType())){
			sql+=" and c.custType in "+ "('"+itemApp.getCustType().replace(",", "','" )+ "')";
		}
		if(StringUtils.isNotBlank(itemApp.getIsSetItem())){
			sql+=" and a.IsSetItem=  '" + itemApp.getIsSetItem() +"'" ;
		}
		if(StringUtils.isNotBlank(itemApp.getSendType())){
			sql+=" and a.SendType=  '" + itemApp.getSendType() +"'" ;
		}
		if("0".equals(itemApp.getContainCmpCust())){
			sql+=" and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+c.Code+'%') order by a.SendDate ";
		}
		
		
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * TODO 根据材料不同执行不同的sql查询
	 * @author	created by zb
	 * @date	2018-7-25--下午5:22:43
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String, Object>> findItemPageBySql(
			Page<Map<String, Object>> page, ItemApp itemApp) {
		List<Object> list = new ArrayList<Object>();
							   
		String sql = " select * from(select e.No iasno, a.No iano, b.ItemCode, c.Descr itemDescr, d2.Code item2Code, " +
				" d2.Descr item2Descr, d3.Code item3Code, d3.Descr item3Descr, " +
				" f.SendQty * case when f.SendQty < 0 then -1 else 1 end * case when a.Type = 'R' then -1 else 1 end Qty, g.Descr uomDescr, " +
				" f.SendQty * c.PerWeight * case when f.SendQty < 0 then -1 else 1 end * case when a.Type = 'R' then -1 else 1 end allWeight, " +
				" f.SendQty * c.PerWeight * case when f.SendQty < 0 then -1 else 1 end * case when a.Type = 'R' then -1 else 1 end allWeight1, " +
				" a.Remarks, a.ConfirmDate, e.Date sendDate " +
				" from tItemApp a " +
				" inner join tItemAppSend e on e.IANo = a.No " +
				" inner join tItemAppSendDetail f on f.No = e.No " +
				" inner join tItemAppDetail b on b.PK = f.RefPk " +
				" inner join tItem c on c.Code = b.ItemCode " +
				" inner join tItemType2 d2 on d2.Code = c.ItemType2 " +
				" inner join tItemType3 d3 on d3.Code = c.ItemType3 " +
				" left join tUOM g on g.Code = c.Uom " +
				" left join tEmployee e1 on c.Buyer1=e1.Number " +
				" left join tEmployee e2 on c.Buyer2=e2.Number " +
				" inner join tCustomer h on a.CustCode=h.Code " +
				" where 1=1 and b.SendQty <> 0 ";
		
		//判断是什么发货，存储过程那里要groupBy，没办法再传1和2
		if (StringUtils.isNotBlank(itemApp.getSendType())) {
			if ("仓库发货".equals(itemApp.getSendType())) {
				sql += " and a.SendType = '2' ";
			} else {
				sql += " and a.SendType = '1' ";
			}
		}
		//根据itemType来判断要查什么材料的数据
		if (StringUtils.isNotBlank(itemApp.getItemType())) {
			// 无法兼容switch时，调用该块
			/*if ("1".equals(itemApp.getItemType())) {
				sql += " and d2.ItemType12='11' ";
			}
			if ("2".equals(itemApp.getItemType())) {
				sql += " and (select ','+QZ+',' from tXTCS where id = 'floor') like '%,'+rtrim(c.ItemType3)+',%' and c.ItemType3 <> '' ";
			}
			if ("3".equals(itemApp.getItemType())) {
				sql += " and (select ','+QZ+',' from tXTCS where id = 'cabinet') like '%,'+rtrim(c.ItemType3)+',%' and c.ItemType3 <> '' ";
			}
			if ("4".equals(itemApp.getItemType())) {
				sql += " and (select ','+QZ+',' from tXTCS where id = 'toilet') like '%,'+rtrim(c.ItemType3)+',%' and c.ItemType3 <> '' ";
			}*/
			
			char itemTypeChar = itemApp.getItemType().trim().charAt(0);
			switch (itemTypeChar) {
			case '1':
				sql += " and d2.ItemType12='11' ";
				break;
			case '2':
				sql += " and (select ','+QZ+',' from tXTCS where id = 'floor') like '%,'+rtrim(c.ItemType3)+',%' and c.ItemType3 <> '' ";
				break;
			case '3':
				sql += " and (select ','+QZ+',' from tXTCS where id = 'cabinet') like '%,'+rtrim(c.ItemType3)+',%' and c.ItemType3 <> '' ";
				break;
			default:
				sql += " and (select ','+QZ+',' from tXTCS where id = 'toilet') like '%,'+rtrim(c.ItemType3)+',%' and c.ItemType3 <> '' ";
				break;
			}
		}
		if (itemApp.getSendDateFrom() != null) {
			sql += " and e.Date >= ? ";
			list.add(itemApp.getSendDateFrom());
		}
		if (itemApp.getSendDateTo() != null) {
			sql += " and e.Date <= ? ";
			list.add(itemApp.getSendDateTo());
		}
		if(StringUtils.isNotBlank(itemApp.getItemType1())){
			sql+=" and c.ItemType1=? ";
			list.add(itemApp.getItemType1().trim());
		}
		if(StringUtils.isNotBlank(itemApp.getItemType2())){
			sql+=" and c.ItemType2=? ";
			list.add(itemApp.getItemType2().trim());
		}
		if(StringUtils.isNotBlank(itemApp.getItemType3())){
			sql+=" and c.ItemType3=? ";
			list.add(itemApp.getItemType3().trim());
		}
		if(StringUtils.isNotBlank(itemApp.getSqlCode())){
			sql+=" and c.SqlCode =? ";
			list.add(itemApp.getSqlCode());
		}
		if(StringUtils.isNotBlank(itemApp.getSupplCode())){
			sql+=" and c.SupplCode =? ";
			list.add(itemApp.getSupplCode());
		}
		if(StringUtils.isNotBlank(itemApp.getSendCzy())){
			sql+=" and (c.Buyer1 =? or c.Buyer2 =?) ";
			list.add(itemApp.getSendCzy());
			list.add(itemApp.getSendCzy());
		}
		if(StringUtils.isNotBlank(itemApp.getDepartment2())){
			sql+=" and (e1.Department2 =? or e2.Department2 = ?) ";
			list.add(itemApp.getDepartment2());
			list.add(itemApp.getDepartment2());
		}
		if("0".equals(itemApp.getContainCmpCust())){
			sql += " and not exists(select 1 from tXTCS x1 where x1.ID='CmpCustCode' and ','+x1.QZ+',' like '%'+h.Code+'%') ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.sendDate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}
