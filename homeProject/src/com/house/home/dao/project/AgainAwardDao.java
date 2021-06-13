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

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.AgainAward;
import com.house.home.entity.project.AgainAwardDetail;

@SuppressWarnings("serial")
@Repository
public class AgainAwardDao extends BaseDao {

	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, AgainAward againAward, UserContext userContext){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 		select taa.No,taa.Status,tx1.NOTE statusDescr,te1.NameChi appCZYDescr,taa.Date,te2.NameChi confirmCZYDescr,taa.confirmDate, "
				   + " 		taa.Remarks,taa.LastUpdatedBy,taa.LastUpdate,taa.Expired,taa.ActionLog,taa.documentNo "
				   + " 		from tAgainAward taa "
				   + " 		left join tXTDM tx1 on tx1.ID='AGAINAWARDSTS' and tx1.CBM = taa.Status "
				   + " 		left join tEmployee te1 on te1.Number = taa.AppCZY "
				   + " 		left join tEmployee te2 on te2.Number = taa.ConfirmCZY "
				   + " 		where 1=1 ";
		if(StringUtils.isNotBlank(againAward.getAddress())){
			sql += " and exists ( "
				 + " 	select 1 "
				 + " 	from tAgainAwardDetail a "
				 + " 	left join tCustomer b on a.CustCode = b.Code "
				 + " 	where a.No=taa.No and b.Address like ? ) ";
			params.add("%"+againAward.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(againAward.getStatus())){
			sql += " and taa.Status in ('"+againAward.getStatus().trim().replace(",", "','")+"')";
		}
		if(againAward.getDateFrom() != null){
			sql += " and taa.Date >= ? ";
			params.add(DateUtil.startOfTheDay(againAward.getDateFrom()));
		}
		if(againAward.getDateTo() != null){
			sql += " and taa.Date < ? ";
			params.add(DateUtil.addDate(againAward.getDateTo(), 1));
		}
		if(againAward.getConfirmDateFrom() != null){
			sql += " and taa.ConfirmDate >= ? ";
			params.add(DateUtil.startOfTheDay(againAward.getConfirmDateFrom()));
		}
		if(againAward.getConfirmDateTo() != null){
			sql += " and taa.ConfirmDate < ? ";
			params.add(DateUtil.addDate(againAward.getConfirmDateTo(), 1));
		}
		if(StringUtils.isNotBlank(againAward.getCustCode())){
			sql += " and exists ( "
					 + " 	select 1 "
					 + " 	from tAgainAwardDetail a "
					 + " 	where a.No=taa.No and a.CustCode=? ) ";
				params.add(againAward.getCustCode());
		}
		
		if (!againAward.canViewAll()) {
            sql += " and taa.AppCZY = ? ";
            params.add(userContext.getCzybh());
        }
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

	public Page<Map<String, Object>> goJqGridAddDetail(Page<Map<String, Object>> page, Customer customer){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 		select tc.Address,tc.Descr custDescr,tx1.NOTE custStatusDescr,tx2.NOTE layoutDescr,tc.Area, "
				   + " 		tc.ContractFee,cast(0 as money) amount,'' remarks,isnull(a.receiveAmount, 0) receiveAmount,tx3.NOTE hadCalcPerfDescr, "
				   + " 		isnull(b.paidAmount, 0) paidAmount,tc.FirstPay,tx4.NOTE payTypeDescr,tc.SetDate,tc.SignDate,tx5.NOTE sourceDescr,tc.Code, "
				   + "      dbo.fGetEmpNameChi(tc.Code, '00') DesignMen, dbo.fGetDept2Descr(tc.Code, '00') DesignManDepts, "
				   + "      dbo.fGetEmpNameChi(tc.Code, '01') BusinessMen, dbo.fGetDept2Descr(tc.Code, '01') BusinessManDepts "
				   + " 		from tCustomer tc "
				   + " 		left join tXTDM tx1 on tx1.ID = 'CUSTOMERSTATUS' and tx1.CBM = tc.Status "
				   + " 		left join tXTDM tx2 on tx2.ID = 'LAYOUT' and tx2.CBM = tc.Layout "
//				   + " 		left join tEmployee te on te.Number = tc.BusinessMan "
//				   + " 		left join tDepartment2 td2 on td2.Code = te.Department2 "
				   + " 		left join ( "
				   + " 			select taad.CustCode,sum(taad.Amount) receiveAmount "
				   + "			from tAgainAward taa "
				   + " 			left join tAgainAwardDetail taad on taa.No = taad.No "
				   + " 			where taa.Status='2' "
				   + " 			group by taad.CustCode "
				   + " 		) a on a.custCode = tc.Code "
				   + " 		left join tXTDM tx3 on tx3.ID='YESNO' and tx3.CBM = tc.HadCalcPerf "
				   + " 		left join ( "
				   + " 			select CustCode,sum(Amount) paidAmount "
				   + " 			from tCustPay "
				   + " 			group by CustCode "
				   + " 		) b on b.custCode = tc.Code "
				   + " 		left join tXTDM tx4 on tx4.ID='TIMEPAYTYPE' and tx4.CBM = tc.PayType "
				   + " 		left join tXTDM tx5 on tx5.ID='CUSTOMERSOURCE' and tx5.CBM = tc.Source "
				   + " 		left join (select * from dbo.fStrToTable('"+customer.getCodes()+"',',')) c on tc.Code = c.item"
				   + "		left join tCusttype d on tc.CustType = d.Code "
				   + " 		where c.item is null and d.IsPartDecorate <> '3' "; 
		if(StringUtils.isNotBlank(customer.getStatus())){
			sql += " and tc.Status in ('"+customer.getStatus().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(customer.getSource())){
			sql += " and tc.Source in ('"+customer.getSource().trim().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(customer.getAddress())){
			sql += " and tc.Address like ? ";
			params.add("%"+customer.getAddress()+"%");
		}
		if(customer.getSignDateFrom() != null){
			sql += " and tc.SignDate >= ? ";
			params.add(DateUtil.startOfTheDay(customer.getSignDateFrom()));
		}
		if(customer.getSignDateTo() != null){
			sql += " and tc.SignDate < ? ";
			params.add(DateUtil.addDate(customer.getSignDateTo(), 1));
		}
		if(customer.getDateFrom() != null){
			sql += " and tc.SetDate >= ? ";
			params.add(DateUtil.startOfTheDay(customer.getDateFrom()));
		}
		if(customer.getDateTo() != null){
			sql += " and tc.SetDate < ? ";
			params.add(DateUtil.addDate(customer.getDateTo(), 1));
		}
		if (StringUtils.isNotBlank(customer.getCode())) {
            sql += " and tc.Code = ? ";
            params.add(customer.getCode());
        }
		if (StringUtils.isNotBlank(customer.getPaidAmount())) {
            sql += " and isnull(b.paidAmount, 0) >= ? ";
            params.add(customer.getPaidAmount());
        }
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	
	public Result doSave(AgainAward againAward, String xml){
		Assert.notNull(againAward);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDoSaveAgainAward_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, againAward.getM_umState());
			call.setString(2, againAward.getNo());
			call.setString(3, againAward.getOldStatus());
			call.setTimestamp(4, againAward.getDate() != null ? new Timestamp(againAward.getDate().getTime()):new Timestamp(0));
			call.setString(5, againAward.getAppCZY());
			call.setTimestamp(6, againAward.getConfirmDate() != null ? new Timestamp(againAward.getConfirmDate().getTime()):new Timestamp(0));
			call.setString(7, againAward.getConfirmCZY());
			call.setString(8, againAward.getRemarks());
			call.setString(9, againAward.getCzybh());
			call.setString(10, againAward.getStatus());
			call.setString(11, xml);
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.setString(14, againAward.getDocumentNo());
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Map<String, Object> getAgainAwardByNo(String no){
		String sql = " select taa.no,taa.status oldStatus,taa.appCZY,te1.NameChi appCZYDescr,taa.date,te2.NameChi confirmCZYDescr,taa.confirmDate,taa.remarks,taa.confirmCZY,taa.documentNo "
				   + " from tAgainAward taa "
				   + " left join tEmployee te1 on te1.Number = taa.AppCZY "
				   + " left join tEmployee te2 on te2.Number = taa.ConfirmCZY "
				   + " where taa.No=? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> goJqGridAgainAwardDetail(Page<Map<String, Object>> page, String no){
		String sql = " select tc.address,tc.Descr custdescr,tx1.NOTE custstatusdescr,tx2.NOTE layoutdescr,tc.area, "
				   + " tc.contractfee,taad.amount,taad.remarks,isnull(a.receiveAmount, 0) receiveamount,tx3.NOTE hadcalcperfdescr, "
				   + " isnull(b.paidAmount, 0) paidamount,tc.firstpay,tx4.NOTE paytypedescr,tc.setdate,tc.signdate,tx5.NOTE sourcedescr,taad.pk,tc.code, "
				   + " taad.role, tr.Descr roledescr, taad.empcode, e.NameChi empname, "
				   + " e.Department2 empdept2, f.Desc1 empdept2descr "
				   + " from tAgainAwardDetail taad "
				   + " left join tCustomer tc on taad.CustCode = tc.Code "
				   + " left join tXTDM tx1 on tx1.ID = 'CUSTOMERSTATUS' and tx1.CBM = tc.Status "
				   + " left join tXTDM tx2 on tx2.ID = 'LAYOUT' and tx2.CBM = tc.Layout "
				   + " left join tEmployee te on te.Number = tc.BusinessMan "
				   + " left join tDepartment2 td2 on td2.Code = te.Department2 "
				   + " left join ( "
				   + " 		select taad.CustCode, taad.Role, taad.EmpCode, sum(taad.Amount) receiveAmount "
				   + " 		from tAgainAward taa "
				   + " 		left join tAgainAwardDetail taad on taa.No = taad.No "
				   + " 		where taa.Status='2' "
				   + " 		group by taad.CustCode, taad.Role, taad.EmpCode "
				   + " ) a on a.custCode = taad.CustCode and a.Role = taad.Role and a.EmpCode = taad.EmpCode "
				   + " left join tXTDM tx3 on tx3.ID='YESNO' and tx3.CBM = tc.HadCalcPerf "
				   + " left join ( "
				   + " 		select CustCode,sum(Amount) paidAmount "
				   + " 		from tCustPay "
				   + " 		group by CustCode "
				   + " ) b on b.custCode = taad.CustCode "
				   + " left join tXTDM tx4 on tx4.ID='TIMEPAYTYPE' and tx4.CBM = tc.PayType "
				   + " left join tXTDM tx5 on tx5.ID='CUSTOMERSOURCE' and tx5.CBM = tc.Source "
				   + " left join tRoll tr on taad.Role = tr.Code "
				   + " left join tEmployee e on taad.EmpCode = e.Number "
				   + " left join tDepartment2 f on e.Department2 = f.Code "
				   + " where taad.No=? "
				   + " order by tc.Code ";
		            //改为直接删除，所以不限制amount modify by zb
//				   + "and taad.amount<>''"; 
		return this.findBySql(sql, new Object[]{no});
	}
	
	public Page<Map<String, Object>> goJqGridDetailList(Page<Map<String, Object>> page,
	        AgainAwardDetail againAwardDetail, UserContext userContext) {
	    
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( "
				   + " 	 select tc.Address,tc.Descr custDescr,tx1.NOTE custStatusDescr,tx2.NOTE layoutDescr,tc.area, "
				   + " 	   tc.SetDate,tc.SignDate,tx3.NOTE sourceDescr,taad.amount,taad.Remarks,taad.No,tx4.NOTE statusDescr,taa.confirmDate, "
	               + "     taad.Role, tr.Descr RoleDescr, taad.EmpCode, e.NameChi EmpName, "
	               + "     e.Department2 EmpDepartment2, f.Desc1 EmpDpt2Descr "
				   + " 	 from tAgainAwardDetail taad "
				   + " 	   left join tCustomer tc on taad.CustCode = tc.Code "
				   + " 	   left join tXTDM tx1 on tx1.ID='CUSTOMERSTATUS' and tx1.CBM = tc.Status "
				   + " 	   left join tXTDM tx2 on tx2.ID = 'LAYOUT' and tx2.CBM = tc.Layout "
//				   + "	   left join tEmployee te on te.Number = tc.BusinessMan "
//				   + " 	   left join tDepartment2 td2 on td2.Code = te.Department2 "
				   + " 	   left join tXTDM tx3 on tx3.ID='CUSTOMERSOURCE' and tx3.CBM = tc.Source "
				   + " 	   left join tAgainAward taa on taad.No = taa.No "
				   + " 	   left join tXTDM tx4 on tx4.ID='AGAINAWARDSTS' and tx4.CBM = taa.Status "
	               + "     left join tRoll tr on taad.Role = tr.Code "
	               + "     left join tEmployee e on taad.EmpCode = e.Number "
	               + "     left join tDepartment2 f on e.Department2 = f.Code "
				   + " 	 where 1=1 ";
		if(StringUtils.isNotBlank(againAwardDetail.getAddress())){
			sql += " and tc.Address like ? ";
			params.add("%"+againAwardDetail.getAddress()+"%");
		}
		if(againAwardDetail.getSignDateFrom() != null){
			sql += " and tc.SignDate >= ? ";
			params.add(DateUtil.startOfTheDay(againAwardDetail.getSignDateFrom()));
		}
		if(againAwardDetail.getSignDateTo() != null){
			sql += " and tc.SignDate < ? ";
			params.add(DateUtil.addDate(againAwardDetail.getSignDateTo(), 1));
		}
		if(againAwardDetail.getConfirmDateFrom() != null){
			sql += " and taa.ConfirmDate >= ? ";
			params.add(DateUtil.startOfTheDay(againAwardDetail.getConfirmDateFrom()));
		}
		if(againAwardDetail.getConfirmDateTo() != null){
			sql += " and taa.ConfirmDate < ? ";
			params.add(DateUtil.addDate(againAwardDetail.getConfirmDateTo(), 1));
		}
		if(StringUtils.isNotBlank(againAwardDetail.getStatus())){
			sql += " and taa.Status in ('"+againAwardDetail.getStatus().trim().replace(",", "','")+"')";
		}
		
        if (!againAwardDetail.canViewAll()) {
            sql += " and taa.AppCZY = ? ";
            params.add(userContext.getCzybh());
        }
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.No desc ";
		}
		return this.findPageBySql(page, sql, params.toArray());
	}

    public List<Map<String, Object>> getStakeholders(String custCode, String bonusScheme) {
        
        String sql = "select "
        		+ "    a.CustCode, a.role, b.Descr roledescr, a.empcode, c.NameChi empname, "
        		+ "    c.Department2 empdept2, d.Desc1 empdept2descr, e.receiveamount "
                + "from tCustStakeholder a "
        		+ "    left join tRoll b on a.Role = b.Code "
                + "    left join tEmployee c on a.EmpCode = c.Number "
                + "    left join tDepartment2 d on c.Department2 = d.Code "
                + "    left join ( "
                + "        select in_b.CustCode, in_b.Role, in_b.EmpCode, sum(in_b.Amount) receiveamount "
                + "        from tAgainAward in_a "
                + "            left join tAgainAwardDetail in_b on in_a.No = in_b.No "
                + "        where in_a.Status = '2' "
                + "        group by in_b.CustCode, in_b.Role, in_b.EmpCode "
                + "    ) e on a.CustCode = e.CustCode and a.Role = e.Role and a.EmpCode = e.EmpCode "
                + "where a.CustCode = ? ";
                
        if (bonusScheme.equals("1")) {
            sql += "and a.Role = '01' ";
        } else if (bonusScheme.equals("2")) {
            sql += "and a.Role = '00' ";
        } else if (bonusScheme.equals("3")) {
            sql += "and a.Role in ('00', '01') ";
        }
        System.out.println("\n\n"+sql+"\n");
        List<Map<String, Object>> stakeholders = findBySql(sql, custCode);

        return stakeholders.size() > 0 ? stakeholders : new ArrayList<Map<String,Object>>();
    }
}	
