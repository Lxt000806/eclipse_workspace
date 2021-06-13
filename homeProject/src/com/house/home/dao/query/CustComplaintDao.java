package com.house.home.dao.query;

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
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetCustComplaintListEvt;
import com.house.home.entity.finance.GiftCheckOut;
import com.house.home.entity.project.CustComplaint;

@Repository
@SuppressWarnings("serial")
public class CustComplaintDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page,CustComplaint custComplaint){
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select a.No, a.Status,x1.note StatusDescr, a.CustCode, a.Source, a.Remarks, a.CrtDate, a.CrtCZY,"
          + " b.Descr CustDescr,b.Address,b.ProjectMan ,e1.NameChi ProjectManDescr, e2.NameChi CrtCZYDescr,dpt.Desc1 ProjectDept2 ,"
          + " a.LastUpdate,a.LastUpdatedBy, a.Expired, a.ActionLog,dpt.Desc1 ProjectManDpt2,dpt2.desc1 designDept2,x2.NOTE ComplTypeDescr,a.ComplType "
          + " from  tCustComplaint  a  "
          + " left outer join tXTDM x1 on x1.cbm=a.Status and x1.id='COMPSTATUS' "
          + " left outer join  tCustomer b on b.code=a.CustCode "
          + " left outer  join tEmployee e1 on  e1.Number=b.ProjectMan"
          + " left outer  join tEmployee e2 on  e2.Number=a.CrtCZY " +
	        " left join temployee e3 on e3.Number= b.DesignMan " +
	        " left join tDepartment2 dpt2 on dpt2.code =e3.Department2 "
          + " left outer  join tDepartment2 dpt on  dpt.code=e1.Department2 "
	      + " left join tXTDM x2 on x2.ID='COMPLTYPE' and a.ComplType=x2.CBM "
          + " where 1=1 ";
		if (StringUtils.isBlank(custComplaint.getExpired())
				|| "F".equals(custComplaint.getExpired())) {
			sql += " and a.Expired='F'  ";
		}
		if(StringUtils.isNotBlank(custComplaint.getNo())){
			sql+=" and a.no = ? ";
			list.add(custComplaint.getNo());
		}
		if(StringUtils.isNotBlank(custComplaint.getAddress())){
			sql+=" and b.address like ? ";
			list.add("%"+custComplaint.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custComplaint.getDepartment2())){
			sql+="  and e1.Department2 = ? ";
			list.add(custComplaint.getDepartment2());
		}
		if(custComplaint.getDateFrom()!=null){
			sql+=" and  a.CrtDate >= ?";
			list.add(new Timestamp(
				DateUtil.startOfTheDay( custComplaint.getDateFrom()).getTime()));
		}
		if(custComplaint.getDateTo()!=null){
			sql+=" and  a.CrtDate < ?";
			list.add(new Timestamp(
				DateUtil.endOfTheDay( custComplaint.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(custComplaint.getPromDept1Code())){
			sql+=" and exists( select 1 from tCustProblem cp  "
             + " inner join  tEmployee e on e.Number = cp.DealCZY  "
             + " where cp.No=a.no and e.Department1= ? ";
			list.add(custComplaint.getPromDept1Code());
			if(StringUtils.isNotBlank(custComplaint.getPromDept2Code())){
				sql+="  and e.Department2= ? ";
				list.add(custComplaint.getPromDept2Code());
			}
			sql+=" ) ";
		}
		if(StringUtils.isNotBlank(custComplaint.getStatus())){
			sql+=" and a.status = ? ";
			list.add(custComplaint.getStatus());
		}
		if(StringUtils.isNotBlank(custComplaint.getCrtCZY())){
			sql+=" and a.CrtCZY= ? ";
			list.add(custComplaint.getCrtCZY());
		}
		if(StringUtils.isNotBlank(custComplaint.getComplType())){
			sql+=" and a.ComplType= ? ";
			list.add(custComplaint.getComplType());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate Desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page,CustComplaint custComplaint){
		List<Object> list = new ArrayList<Object>();
		String sql = "select *from (select  a.pk, a.No, a.PromSource, a.Status, a.PromType1, a.PromType2, a.PromRsn, " +
				" a.SupplCode, a.CrtDate, a.InfoDate, a.DealCZY dealczycode, a.PlanDealDate, " +
				" a.DealRemarks, a.DealDate, b.Descr PromType1Descr, f.Descr PromRsnDescr," +
				" c.Descr PromType2Descr, d.Descr SupplDescr, e.NameChi DealCZYDescr,x1.note StatusDescr ," +
				" a.LastUpdatedBy, a.Expired, a.ActionLog, a.LastUpdate,a.RcvDate,g.FeedBackRemark, " +
				" g.Status ServiceStatus, x2.NOTE ServiceStatusDescr " +
				" from tCustProblem a " +
				" left outer join tPromType1 b on b.code = a.PromType1 " +
				" left outer join tPromType2 c on c.code = a.PromType2 " +
				" left outer join tSupplier d on d.Code = a.SupplCode " +
	            " left outer join tEmployee e on e.Number = a.DealCZY " +
	            " left outer join tPromRsn f on f.code = a.PromRsn " +
	            " left outer join tXTDM x1 on x1.cbm = a.status and x1.id='PROMSTATUS' " +
	            " left outer join tCustService g on  a.PK = g.CustProblemPK " +
	            " left outer join tXTDM x2 on x2.ID = 'CUSTSRVSTATUS' and x2.CBM = g.Status " +
	            " where 1=1";
		
		if(StringUtils.isNotBlank(custComplaint.getNo())){
			sql+=" and a.no = ? ";
			list.add(custComplaint.getNo());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate Desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findComplaintDetailPageBySql(Page<Map<String, Object>> page,CustComplaint custComplaint){
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select a.pk, a.No, a.PromSource, a.PromType1, a.PromType2,  "+ 
              " a.PromRsn, a.SupplCode, a.CrtDate, a.InfoDate, a.DealCZY, "+
              " a.PlanDealDate, a.DealRemarks, a.DealDate, b.Descr PromType1Descr, "+
              " f.Descr PromRsnDescr, c.Descr PromType2Descr, d.Descr SupplDescr,"+
              " e.NameChi DealCZYDescr, a.LastUpdatedBy, "+
              " a.Expired, a.ActionLog, a.LastUpdate,  "+
              " a.Status, x1.note StatusDescr,  J.Source, J.Remarks, "+
              " j.CrtDate tsDate , J.CrtCZY, g.Descr CustDescr, g.Address, g.ProjectMan, "+
              " e2.NameChi CrtCZYDescr, "+
              " dpt.Desc1 ProjectDept2, dpt.Desc1 ProjectManDpt2,ct.Desc1 CustTypeDescr,e1.namechi ProjectManDescr ,"+
              " dpt1.Desc1 DesiDpt1,dpt2.Desc1 DesiDpt2,eds.NameChi DesignManDescr,g.ConfirmBegin ,g.CustCheckDate,x3.NOTE ComplTypeDescr "+
              " from    tCustComplaint  j "+
              " left  outer join  tCustProblem a  on j.no=a.no "+
              " left outer join tPromType1 b on b.code = a.PromType1  "+
              " left outer join tPromType2 c on c.code = a.PromType2 "+
              " left outer join tSupplier d on d.Code = a.SupplCode  "+
              " left  outer join tEmployee e on e.Number = a.DealCZY "+
              " left outer join tPromRsn f on f.code = a.PromRsn  "+
              " left outer join tXTDM x1 on x1.cbm = a.status and x1.id = 'PROMSTATUS' "+
              " left outer join tXTDM x2 on x2.cbm = a.Status  and x2.id = 'COMPSTATUS' "+
              " left outer join tXTDM x3 on x3.ID='COMPLTYPE' and j.ComplType=x3.CBM "+
              " left outer join tCustomer g on g.code = j.CustCode "+
              " left outer  join tEmployee e1 on e1.Number = g.ProjectMan "+
              " left outer  join tEmployee e2 on e2.Number = J.CrtCZY   "+
              " left outer  join tDepartment2 dpt on dpt.code = e1.Department2  "+
              " left outer  join tcusttype ct on ct.code = g.custtype  "+
              " left join tCustomer b1 on  j.CustCode=b1.Code "+
              " left join tEmployee c1 on b1.code=c1.Number "+
              " left outer join tEmployee eds on g.DesignMan=eds.Number "+
              " left outer join tDepartment1 dpt1 on dpt1.Code=eds.department1 "+
              " left outer join tDepartment2 dpt2 on dpt2.Code=eds.department2 "+
              " where 1=1";
		
		if (StringUtils.isBlank(custComplaint.getExpired())
				|| "F".equals(custComplaint.getExpired())) {
			sql += " and j.Expired='F' ";
		}
		if(StringUtils.isNotBlank(custComplaint.getAddress())){
			sql+=" and g.address like ? ";
			list.add("%"+custComplaint.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custComplaint.getDepartment2())){
			sql+="  and e1.Department2 = ? ";
			list.add(custComplaint.getDepartment2());
		}
		if(custComplaint.getDateFrom()!=null){
			sql+=" and  j.CrtDate >= ?";
			list.add(new Timestamp(
				DateUtil.startOfTheDay( custComplaint.getDateFrom()).getTime()));
		}
		if(custComplaint.getDateTo()!=null){
			sql+=" and  j.CrtDate < ?";
			list.add(new Timestamp(
				DateUtil.endOfTheDay( custComplaint.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(custComplaint.getSource())){
			sql+=" and j.source like  ? ";
			list.add("%"+custComplaint.getSource()+"%");
		}
		if(StringUtils.isNotBlank(custComplaint.getStatus())){
			sql+=" and j.status = ? ";
			list.add(custComplaint.getStatus());
		}
		if(StringUtils.isNotBlank(custComplaint.getPromType1())){
			sql+=" and a.promType1 = ? ";
			list.add(custComplaint.getPromType1());
		}
		if(StringUtils.isNotBlank(custComplaint.getPromType2())){
			sql+=" and  a.promType2 = ? ";
			list.add(custComplaint.getPromType2());
		}
		if(StringUtils.isNotBlank(custComplaint.getPromRsn())){
			sql+=" and  a.promRsn = ? ";
			list.add(custComplaint.getPromRsn());
		}
		if(StringUtils.isNotBlank(custComplaint.getSupplCode())){
			sql+=" and a.supplCOde= ? ";
			list.add(custComplaint.getSupplCode());
		}
		if(StringUtils.isNotBlank(custComplaint.getComplType())){
			sql+=" and j.ComplType= ? ";
			list.add(custComplaint.getComplType());
		}
		if(StringUtils.isNotBlank(custComplaint.getCrtCZY())){
			sql+=" and j.CrtCZY= ? ";
			list.add(custComplaint.getCrtCZY());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate Desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getCustComplaintList(Page<Map<String, Object>> page, GetCustComplaintListEvt evt, UserContext uc){
		List<Object> params = new ArrayList<Object>();
		String sql = " select tc.Address,isnull(td2.Desc1,'') department2Descr,tx1.NOTE statusDescr,tcc.CrtDate,tcc.No "
				   + " from tCustComplaint tcc "
				   + " left join tCustomer tc on tcc.CustCode = tc.Code "
				   + " left join tEmployee te on tc.ProjectMan = te.Number "
				   + " left join tDepartment2 td2 on td2.Code = te.Department2 "
				   + " left join tXTDM tx1 on tx1.ID='COMPSTATUS' and tx1.CBM = tcc.Status "
				   + " where 1=1 and " + SqlUtil.getCustRight(uc, "tc", 0);
		if(StringUtils.isNotBlank(evt.getAddress())){
			sql += " and tc.Address like ? ";
			params.add("%"+evt.getAddress()+"%");
		}
		if("1".equals(evt.getHaveGd())){
			sql += " and tc.Status in ('4','5') ";
		}else{
			sql += " and tc.Status = '4' ";
		}
		if(StringUtils.isNotBlank(evt.getStatus())){
			sql += " and tcc.Status = ? ";
			params.add(evt.getStatus());
		}
		if(StringUtils.isNotBlank(evt.getDepartment2Descr())){
			sql += " and td2.Desc1 = ? ";
			params.add(evt.getDepartment2Descr());
		}
		sql += " order by tcc.CrtDate desc ";
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Map<String, Object> getCustComplaintDetail(String no){
		String sql = " select tc.Address, te.NameChi+(case when td2.Desc1 is not null and td2.Desc1 <> '' then '('+td2.Desc1+')' else '' end) projectManDescr, "
				   + " tcc.Source,tcc.Status,tcc.CrtDate,tcc.Remarks,tc.CustType,tc.Code CustCode "
				   + " from tCustComplaint tcc "
				   + " left join tCustomer tc on tcc.CustCode = tc.Code "
				   + " left join tEmployee te on te.Number = tc.ProjectMan "
				   + " left join tDepartment2 td2 on td2.Code = te.Department2 "
				   + " where tcc.No = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String, Object>>  getCustComplaint(String no){
		String sql = " select tcc.remarks,tcc.no "
				   + " from tCustComplaint tcc "
				   + " left join tCustomer tc on tcc.CustCode = tc.Code "
				   + " left join tEmployee te on te.Number = tc.ProjectMan "
				   + " left join tDepartment2 td2 on td2.Code = te.Department2 "
				   + " where tcc.no = ? and tcc.source = '客户App' ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List<Map<String, Object>>  getCustEval(String custCode){
		String sql = " select a.prjScore prjScope ,a.designScore designScope," +
				" case when dateDiff(d,a.LastUpdate,getDate()) = 0 then '1' else '0' end canUpdate from tCustEval a where a.custCode = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public List<Map<String, Object>>  getCustAddress(String identity,String phone){
		
		String sql = " select address,code,conId from tCustomer where ConId = ? and mobile1 = ? and CustAccountPk is not null ";
		if(StringUtils.isBlank(identity)){
			sql+= " and  1<>1 ";
		}
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{identity,phone});
		
		return list;
	}
	
	public List<Map<String, Object>> getCustComplaintDetailList(String no){
		String sql = " select te.NameChi dealCZYDescr,tcp.infoDate,tcp.dealDate,tx1.NOTE statusDescr,tcp.dealRemarks "
				   + " from tCustProblem  tcp "
				   + " left join tEmployee te on tcp.DealCZY = te.Number "
				   + " left join tXTDM tx1 on tx1.ID='PROMSTATUS' and tx1.CBM = tcp.Status "
				   + " where No = ? ";
		return this.findBySql(sql, new Object[]{no});
	}
	
	public Map<String, Object> getCustInfo(String custCode){
		String sql = " select a.descr custDescr,e1.NameChi designDescr ,e1.Phone designPhone" +
				" ,a.Address,d1.Desc1 designDept2Descr,e3.NameChi MainHousekeeper,e4.NameChi LiveDesgin, " +//增加主材管家、现场设计师字段 --add by zb
				" a.status,e2.NameChi projectDescr,e2.Phone projectPhone,d2.Desc1 projectDeptDescr," +
				" a.Mobile1,CONVERT(varchar,a.CheckOutDate,121) CheckOutDate,a.CustType,a.Mobile1,a.Mobile2 "+
				"from tcustomer a " +
				" left join temployee e1 on e1.number=a.DesignMan" +
				" left join tEmployee e2 on e2.Number=a.ProjectMan" +
				" left join tDepartment2 d1 on d1.Code=e1.Department2 " +
				" left join tDepartment2 d2 on d2.code=e2.Department2 " +
				" left join tCustStakeholder tcs on tcs.CustCode = a.Code and tcs.Role = '34'"+
				" left join tCustStakeholder tcs2 on tcs2.CustCode = a.Code and tcs2.Role = '63'"+
				" left join temployee e3 on e3.Number= tcs.EmpCode"+
				" left join temployee e4 on e4.Number= tcs2.EmpCode" +
				" where a.code = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public Result doSave(CustComplaint custComplaint) {
		Assert.notNull(custComplaint);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pKhtsgl_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, custComplaint.getM_umState());
			call.setString(2, custComplaint.getNo());
			call.setString(3, custComplaint.getStatus());
			call.setString(4, custComplaint.getCustCode());
			call.setString(5, custComplaint.getSource());
			call.setString(6, custComplaint.getRemarks());
			call.setString(7, custComplaint.getCrtCZY());
			call.setString(8, custComplaint.getExpired());
			call.setString(9, custComplaint.getLastUpdatedBy());
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.NVARCHAR);
			call.setString(12, custComplaint.getGiftAppDetailXml());
			call.setString(13, custComplaint.getComplType());
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
	
	public Page<Map<String, Object>> getCustComplaintList_forCust(Page<Map<String, Object>> page, GetCustComplaintListEvt evt){
		List<Object> params = new ArrayList<Object>();
		String sql = " select tcc.CrtDate,tcc.No,tcc.remarks,e.namechi dealCzyDescr  "
				   + " from tCustComplaint tcc " +
				   " left join tEmployee e on e.number = tcc.crtCzy "
				   + " where 1=1 and tcc.source ='客户App' and tcc.custCode= ? " ;
		params.add(evt.getId());
		
		sql += " order by tcc.CrtDate desc ";
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	
	
	
	
	
	
	
	
	
	
}
