package com.house.home.dao.project;

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

import com.alibaba.fastjson.JSONArray;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.project.PrjJob;

@Repository
@SuppressWarnings("serial")
public class SendNoticeDao extends BaseDao {

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String beginOrEnd = "";
		if ("1".equals(customer.getStatistcsMethod())) {
			beginOrEnd = "Begin";
		} else {
			beginOrEnd = "End";
		}
		String sql = "select distinct a.Address,a.Code CustCode, n.JobType,d.PayNum,g.ItemType1,"
				+ "dateadd(DAY,d."
				+ beginOrEnd
				+ "AddDays,case when d."
				+ beginOrEnd
				+ "DateType='1' then e.BeginDate when d."
				+ beginOrEnd
				+ "DateType='2' then e.EndDate when d."
				+ beginOrEnd
				+ "DateType='3' then e.ConfirmDate end)shouldSendDate, "
				+ "h.Descr shouldSendNode,x1.NOTE nodeDateType,case when d."
				+ beginOrEnd
				+ "DateType='1' then e.BeginDate when d."
				+ beginOrEnd
				+ "DateType='2' then e.EndDate when d."
				+ beginOrEnd
				+ "DateType='3' then e.ConfirmDate end nodeTriggerDate,b.desc1 CustTypeDescr,"
				+ "case when d.PayNum>0 and m.shouldBanlance>0 then m.shouldBanlance else 0 end shouldBanlance,q.Descr itemType1Descr, "
				+ "case when d.PayNum>0 and m.shouldBanlance>0 then '否' else '是' end moneyInFull,o.Descr JobTypeDescr," 
				+ "case when d.PayNum>0 and m.shouldBanlance>0 then '客户'+cast(d.PayNum as nvarchar(20))+'期款未交齐，差'+cast(m.shouldBanlance as nvarchar(20))+'元' else '' end sendRemarks "
				+ "from tCustomer a "
				+ "left join tCustType b on a.CustType = b.Code "
				+ "left join tItemSendNode c on b.WorkerClassify = c.WorkerClassify "
				+ "left join tItemSendNodeDetail d on c.Code = d.Code "
				+ "inner join tPrjProg e on dateadd(DAY,d."
				+ beginOrEnd
				+ "AddDays,case when d."
				+ beginOrEnd
				+ "DateType='1' then e.BeginDate when d."
				+ beginOrEnd
				+ "DateType='2' then e.EndDate when d."
				+ beginOrEnd
				+ "DateType='3' then e.ConfirmDate end)<dateadd(day,1,getdate()) and e.PrjItem=d.BeginNode and a.Code=e.CustCode "
				+ "left join tConfItemType f on f.Code=d.ConfItemType "
				+ "inner join tItemApp g on a.Code=g.CustCode and g.Status = 'CONFIRMED' "
				+ "left join tPrjItem1 h on e.PrjItem=h.Code "
				+ "left join tBuilder i on a.BuilderCode=i.Code "
				+ "left join tRegion j on i.RegionCode=j.Code "
				+ "left join tXTDM x1 on d."
				+ beginOrEnd
				+ "DateType=x1.CBM and x1.ID='ALARMDAYTYPE' "
				+ "left join tXTDM x2 on g.DelivType=x2.CBM and x2.ID='DELIVTYPE' "
				+ "left join tEmployee k on k.Number=a.ProjectMan "
				+ "outer apply (select dbo.fGetShouldBanlanceByPayNum(a.Code,d.PayNum,default) shouldBanlance ) m "
				+ "inner join tJobTypeConfItemType n on n.ConfItemType = f.Code and n.Expired = 'F' "
				+ "inner join tJobType o on o.IsMaterialSendJob = '1' and o.Code = n.JobType "
				+ "left join tItemType1 q on g.ItemType1 = q.Code "
				+ "where a.Status='4' and c.Type='2' " 
		 		+ "and (not exists(select 1 from tPrjJob in_a where in_a.JobType = o.Code  "
		 		+ "   and in_a.CustCode = a.Code "
                + "   and in_a.ItemType1 = g.ItemType1 and in_a.status<>'5') and a.ConstructStatus <> '7' or a.ConstructStatus = '7') " 
				+ "and exists(select 1 from titemAppDetail in_a "
				+ "left join tItem in_b on in_a.ItemCode = in_b.Code "
				+ "left join tItemType2 in_c on in_b.ItemType2=in_c.Code "
				+ "left join tItemType3 in_d on in_b.ItemType3=in_d.Code  "
				+ "where in_a.No=g.No and exists(select 1 from tConfItemTypeDt in_e where ConfItemType=f.Code "
				+ "and ((in_b.ItemType2=in_e.ItemType2 and isnull(in_e.ItemType3,'')<>'' and in_b.ItemType3=in_e.ItemType3) "
				+ "or(in_b.ItemType2=in_e.ItemType2 and isnull(in_e.ItemType3,'')='')))  )";
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getCode())) {
			sql += " and a.Code=? ";
			list.add(customer.getCode());
		}
		if (StringUtils.isNotBlank(customer.getItemType1())) {
			sql += " and g.ItemType1=? ";
			list.add(customer.getItemType1());
		}else{
			sql += " and g.ItemType1 in ('"+customer.getItemRight().trim().replace(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			String str = SqlUtil.resetStatus(customer.getCustType());
			sql += " and a.CustType in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getDepartment2())) {
			sql += " and k.Department2 in ('"
					+ customer.getDepartment2().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(customer.getJobType())) {
			String str = SqlUtil.resetStatus(customer.getJobType());
			sql += " and n.JobType in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getMoneyInFull())) {
			if("0".equals(customer.getMoneyInFull())){
				sql += " and d.PayNum>0 and m.shouldBanlance>0 ";
			}else{
				sql += " and not (d.PayNum>0 and m.shouldBanlance>0) ";
			}
		}
		
		System.out.println(sql);
		System.out.println(list);
		return this.findPageByJdbcTemp(page, sql.toLowerCase(), "CustCode asc",list.toArray() );
	}
	
	public Page<Map<String, Object>> goItemAppJqGrid(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select distinct g.No,g.ConfirmDate,g.SplRemark,x2.NOTE DelivTypeDescr,x1.NOTE SendTypeDescr," 
				+ "l.Descr SupplDescr,h.Desc1 WhDescr "
				+ "from tCustomer a "
				+ "left join tCustType b on a.CustType = b.Code "
				+ "left join tItemSendNode c on b.WorkerClassify = c.WorkerClassify "
				+ "left join tItemSendNodeDetail d on c.Code = d.Code "
				+ "left join tConfItemType f on f.Code=d.ConfItemType "
				+ "inner join tItemApp g on a.Code=g.CustCode and g.Status = 'CONFIRMED' "
				+ "left join tXTDM x1 on g.SendType=x1.CBM and x1.ID='ITEMAPPSENDTYPE' "
				+ "left join tXTDM x2 on g.DelivType=x2.CBM and x2.ID='DELIVTYPE' "
				+ "left join tWareHouse h on g.WhCode=h.Code "
				+ "left join tSupplier l on g.SupplCode=l.Code "
				+ "inner join tJobTypeConfItemType n on n.ConfItemType = f.Code  and n.Expired = 'F' "
				+ "inner join tJobType o on o.IsMaterialSendJob = '1' and o.Code = n.JobType "
				+ "left join tPrjJob p on p.JobType = o.Code and p.CustCode = a.Code and p.ItemType1 = g.ItemType1 "
				+ "where a.Status='4' and c.Type='2' " 
		 		+ "and ((p.CustCode is null or p.Status = '5') and a.ConstructStatus <> '7' or a.ConstructStatus = '7') " 
				+ "and exists(select 1 from titemAppDetail in_a "
				+ "left join tItem in_b on in_a.ItemCode = in_b.Code "
				+ "left join tItemType2 in_c on in_b.ItemType2=in_c.Code "
				+ "left join tItemType3 in_d on in_b.ItemType3=in_d.Code  "
				+ "where in_a.No=g.No and exists(select 1 from tConfItemTypeDt in_e where ConfItemType=f.Code "
				+ "and ((in_b.ItemType2=in_e.ItemType2 and isnull(in_e.ItemType3,'')<>'' and in_b.ItemType3=in_e.ItemType3) "
				+ "or(in_b.ItemType2=in_e.ItemType2 and isnull(in_e.ItemType3,'')='')))  )";
		
		if (StringUtils.isNotBlank(customer.getCode())) {
			sql += " and a.Code=? ";
			list.add(customer.getCode());
		}
		if (StringUtils.isNotBlank(customer.getJobType())) {
			String str = SqlUtil.resetStatus(customer.getJobType());
			sql += " and n.JobType in (" + str + ")";
		}
		
		System.out.println(sql);
		System.out.println(list);
		return this.findPageByJdbcTemp(page, sql.toLowerCase(), "No asc",list.toArray() );
	}

	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select b.Descr ItemDescr,a.Qty,e.Descr FixAreaDescr "
				+ "from tItemAppDetail a "
				+ "left join tItem b on a.ItemCode = b.Code "
				+ "left join tFixArea e on a.FixAreaPK=e.PK "
				+ "where a.No=? "
				+ "and exists(select 1 from tConfItemTypeDt in_a where ConfItemType=? "
				+ " and ((b.ItemType2=in_a.ItemType2 and isnull(in_a.ItemType3,'')<>'' and b.ItemType3=in_a.ItemType3) "
				+ " or(b.ItemType2=in_a.ItemType2 and isnull(in_a.ItemType3,'')=''))) ";
		list.add(customer.getNo());
		list.add(customer.getConfItemType());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.ItemDescr asc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getItemAppInfo(String iaNo) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Date,b.Descr SupplDescr,c.Desc1 WHDescr,a.ConfirmDate, "
				+ "d.Address,a.No,x1.NOTE StatusDescr,x2.NOTE SendTypeDescr "
				+ "from tItemApp a "
				+ "left join tSupplier b on a.SupplCode=b.Code  "
				+ "left join tWareHouse c on a.WHCode=c.Code  "
				+ "left join tCustomer d on a.CustCode=d.Code "
				+ "left join tXTDM x1 on a.Status=x1.CBM and x1.ID='ITEMAPPSTATUS' "
				+ "left join tXTDM x2 on a.SendType=x2.CBM and x2.ID='ITEMAPPSENDTYPE' "
				+ "where a.No=? ";
		list.add(iaNo);
		return this.findBySql(sql, list.toArray()).get(0);
	}
	
	/**
	 * 发货通知保存
	 * @param resrCust
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSendNotice(PrjJob prjJob) {
		Assert.notNull(prjJob);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSendNotice(?,?,?,?)}");
			call.setString(1, prjJob.getLastUpdatedBy());
			call.setString(2, prjJob.getPrjJobJson());	
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
	
	public List<Map<String,Object>> findItemType1Auth(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tItemType1 a where a.expired='F' ";
		if(StringUtils.isNotBlank((String)param.get("pCode")) ){//&& "auth".equals(auth)
			sql += " and a.code in ("+param.get("pCode")+") ";
		}
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findPrjType(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select rtrim(a.Code)id,a.Descr name from tJobType a where a.Expired='F' and a.IsMaterialSendJob = '1' ";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.itemType1 = ? ";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.Code";
		return this.findBySql(sql, list.toArray());
	}
	
	@SuppressWarnings("deprecation")
	public Result doAddNotice(PrjJob prjJob) {
		Assert.notNull(prjJob);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pAddNotice(?,?,?,?,?,?,?)}");
			call.setString(1, prjJob.getCustCode());
			call.setString(2, prjJob.getItemType1());
			call.setString(3, prjJob.getJobType());
			call.setString(4, prjJob.getRemarks());
			call.setString(5, prjJob.getLastUpdatedBy());
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
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
