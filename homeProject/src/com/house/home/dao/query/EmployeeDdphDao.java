package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.design.Customer;
@Repository
@SuppressWarnings("serial")
public class EmployeeDdphDao extends BaseDao {
	public Page<Map<String,Object>> findPageBySql_pDdtj_ph(Page<Map<String,Object>> page, Customer customer) {
		Assert.notNull(customer);

		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pddtj_ph(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setTimestamp(1, customer.getDateFrom() == null ? null : new Timestamp(
					customer.getDateFrom().getTime()));
			call.setTimestamp(2, customer.getDateTo() == null ? null : new Timestamp(
					DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
			call.setString(3, customer.getTeam());
			call.setString(4, customer.getCustType());
			call.setString(5, customer.getBuilderCode());
			call.setString(6, customer.getStatistcsMethod());
			call.setString(7, customer.getOrderBy());
			call.setString(8, customer.getRole());
			call.setString(9, customer.getDepartment1());
			call.setString(10, customer.getDepartment2());
			call.setString(11, customer.getDepartment3());
			call.setInt(12, page.getPageNo());
			call.setInt(13,page.getPageSize());
			call.setString(14, customer.getExpired());
			call.setString(15, customer.getIsContainDraftsMan());
			call.setString(16, customer.getRegion());
			call.setString(17, customer.getStakeholder());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
			page.setTotalCount(page.getResult().size());
			/*if (!list.isEmpty()) {
				page.setTotalCount((Integer)list.get(0).get("totalcount"));
			} else {
				page.setTotalCount(0);
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	public Page<Map<String, Object>> findSignPageBySql(
		Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from"; 
			if("00".equals(customer.getRole().trim())){
				sql+=" (select e1.NameChi DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr,";
			}else if("01".equals(customer.getRole().trim())){
				sql+=" (select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,e2.NameChi BusinessManDescr, ";
			}else{
				sql+=" (select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr, ";
			}
			 sql+="cc.Address,cc.Code,cc.SignDate,cc.ContractFee,cc.ContractFee/cs4.num ContractFee_num,cs4.num,cc.DesignFee,cc.ManageFee,cc.Area,"
               + " cc.FirstPay,cp.Amount PayAmount,     "
               //+ " (case when (cc.paytype='1' and ((cc.FirstPay=0) or (isnull(cp.Amount,0)-cc.DesignFee)/cc.FirstPay>=isnull(ct.FirstPayPer,1)) ) or "
               //+ "           (cc.paytype='2' and (isnull(cp.Amount,0))>=cc.FirstPay) "   //--工程款零首付,只要已付款大于首付款就达标 paytype:1 正常付款,2零首付
               //+ " (case when  isnull(cp.PayAmount,0)- case when prd.IsRcvDesignFee='1' then  "
	           + " ( case when  isnull(cp.Amount,0)-  case when prd.IsRcvDesignFee='1' then " 
	           + "	case when  pr.DesignFeeType='1' then  cc.DesignFee  "
	 		   + "	when  pr.DesignFeeType='2' then cc.stdDesignFee else 0  end "
	 		   + "	else 0 end>=isnull(ct.FirstPayPer,1)*cc.FirstPay   "
               + " then "            //--业绩金额算法，软装家具按50%，服务性产品按三分一算
               + " round(cc.DesignFee+cc.BaseFee_Dirct+cc.BaseFee_Comp-cc.LongFee+ (cc.MainFee-cc.MainDisc)*cc.ContainMain+  "
               + " (cc.IntegrateFee-cc.IntegrateDisc)*cc.ContainInt+ "
               + " (cc.CupboardFee-cc.CupBoardDisc)*cc.ContainCup+ "
               + " (cc.SoftFee-cc.SoftDisc-cc.SoftFee_Furniture)*cc.ContainSoft+   "
               + " cc.SoftFee_Furniture*cc.ContainSoft*0.5+   "
		   	   + " cc.MainServFee*cc.ContainMainServ*1.0/3-cc.BaseDisc,2)/cs4.num "
		   	   + " else 0  end)  AchievFee ,"
		   	   + " case when (cc.FirstPay=0) then 0   else  (isnull(cp.Amount,0)-case  when  cc.paytype='1' then cc.DesignFee else 0  end)/cc.FirstPay*100 end  PayPer, "
		   	   + " cc.MainServFee*cc.ContainMainServ MainServFee,cc.AchievDed,cc.SetDate ,'正常' ContractType, ct.Desc1 CustTypeDescr "
		   	   + " from tCustomer cc "
		   	   + " inner join tCustType ct on cc.Custtype=ct.code "
		   	   + " inner join tCustStakeholder cs on cs.CustCode=cc.Code"
		   	   + " inner join (select CustCode,Role,count(1) num from tCustStakeholder group by CustCode,Role) cs4 on cs4.CustCode=cc.Code and cs4.Role=cs.Role"
			   + " left join tPayRule pr on pr.CustType=cc.CustType and cc.PayType=pr.PayType "
			   + " left join tpayruledetail prd on prd.no=pr.No  and prd.PayNum='1' ";
		 if("00".equals(customer.getRole().trim())){
		    // sql+= " left outer join tCustStakeholder cs2 on cs2.CustCode=cc.Code and cs2.Role='00' "
		    sql+= " left outer join tEmployee e1 on e1.Number=cs.EmpCode ";
		 }else if("01".equals(customer.getRole().trim())){
			// sql+= " left outer join tCustStakeholder cs3 on cs3.CustCode=cc.Code and cs3.Role='01' "
		       sql+=" left outer join tEmployee e2 on e2.Number=cs.EmpCode ";
		 }
		 sql+= " left outer join (select CustCode,sum(Amount) Amount from tCustPay  "
		      + " where Date<=? group by CustCode) cp on cp.CustCode=cc.Code  "
		      + " where cc.Expired='F' and cs.Role=? and cc.SignDate>=? and cc.SignDate<=? "
		      + " and cs.EmpCode=? "
		      + " and not exists( select 1 from  tAgainSign where CustCode=cc.Code)  " ;
		
		list.add(customer.getDateTo());	 
		list.add(customer.getRole());
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		list.add(customer.getEmpCode());
		if (StringUtils.isNotBlank(customer.getCustType())) {
				sql += " and cc.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
		}	
		 //重签
		if("00".equals(customer.getRole().trim())){
			sql += " union all select e1.NameChi DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr,";
		}else if("01".equals(customer.getRole().trim())){
			sql += " union all select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,e2.NameChi BusinessManDescr,";
		}else{
			sql += " union all select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr,";
		}	//签单金额，分摊签单金额 改为用客户表的数据，tAgainSign 表为重签前的数据     当月重签的取客户表,否则取重签表比较合理   
//			sql += "  cc.Address,cc.Code,asn.SignDate,cc.ContractFee,cc.ContractFee/cs4.num ContractFee_num,cs4.num,asn.DesignFee,cc.ManageFee,cc.Area,"
//		      + " asn.FirstPay,cp.Amount PayAmount, "
//              + " (case when  isnull(cp.Amount,0)-  case when prd.IsRcvDesignFee='1' then " 
//              + " case when  pr.DesignFeeType='1' then  asn.DesignFee  "
//			  + " when  pr.DesignFeeType='2' then cc.stdDesignFee else 0  end "
//			  + " else 0 end>=isnull(ct.FirstPayPer,1)*asn.FirstPay   "
//		      + " then "           //--业绩金额算法，软装家具按50%，服务性产品按三分一算
//		      + " round(cc.DesignFee+cc.BaseFee_Dirct+cc.BaseFee_Comp-cc.LongFee+ (cc.MainFee-cc.MainDisc)*cc.ContainMain+    " 
//			  + " (cc.IntegrateFee-cc.IntegrateDisc)*cc.ContainInt+     " 
//			  + " (cc.CupboardFee-cc.CupBoardDisc)*cc.ContainCup+    " 
//			  + " (cc.SoftFee-cc.SoftDisc-cc.SoftFee_Furniture)*cc.ContainSoft+    " 
//			  + " cc.SoftFee_Furniture*cc.ContainSoft*0.5+      " 
//			  + " cc.MainServFee*cc.ContainMainServ*1.0/3-cc.BaseDisc,2)/cs4.num   " 
//		      + " else 0  end)  AchievFee , " //分摊业绩金额，改为用客户表的数据
//		      + " case when (asn.FirstPay=0) then 0   else  (isnull(cp.Amount,0)-case  when  asn.paytype='1' then asn.DesignFee else 0  end)/asn.FirstPay*100 end  PayPer,  " 
//		      + " asn.MainServFee*asn.ContainMainServ MainServFee,cc.AchievDed,cc.SetDate  , '重签' ContractType " 
		      
		// 考虑重签客户签单金额，分摊签单金额的统计合理性 当月重签的取客户表,否则取重签表 byzjf20200207   
		sql += " cc.Address,cc.Code,asn.SignDate," 
			  + " case when convert(varchar(7), asn.SignDate, 126) = convert(varchar(7), asn.NewSignDate, 126) then cc.ContractFee else asn.ContractFee end ContractFee," 
			  + " case when convert(varchar(7), asn.SignDate, 126) = convert(varchar(7), asn.NewSignDate, 126) then cc.ContractFee else asn.ContractFee end/cs4.num ContractFee_num,cs4.num," 
			  + " case when convert(varchar(7), asn.SignDate, 126) = convert(varchar(7), asn.NewSignDate, 126) then cc.DesignFee else asn.DesignFee end DesignFee," 
			  + " cc.ManageFee,cc.Area, " 
			  + " case when convert(varchar(7), asn.SignDate, 126) = convert(varchar(7), asn.NewSignDate, 126) then cc.FirstPay else asn.FirstPay end FirstPay," 
		      + " cp.Amount PayAmount, "
              + " (case when isnull(cp.Amount,0)- case when prd.IsRcvDesignFee='1' then " // 根据付款规则判断业绩是否达标
              + " 		case when  pr.DesignFeeType='1' then " 
              + "	   			case when convert(varchar(7), asn.SignDate, 126) = convert(varchar(7), asn.NewSignDate, 126) then cc.DesignFee else asn.DesignFee end  "
			  + " 	   	when  pr.DesignFeeType='2' then cc.stdDesignFee else 0  end "
			  + " else 0 end >= isnull(ct.FirstPayPer,1)* case when convert(varchar(7), asn.SignDate, 126) = convert(varchar(7), asn.NewSignDate, 126) then cc.FirstPay else asn.FirstPay end   "
		      + " then " // 业绩金额算法，软装家具按50%，服务性产品按三分一算     
		      + " 	case when convert(varchar(7), asn.SignDate, 126) = convert(varchar(7), asn.NewSignDate, 126) then "
		      + "   	round(cc.DesignFee+cc.BaseFee_Dirct+cc.BaseFee_Comp-cc.LongFee+ (cc.MainFee-cc.MainDisc)*cc.ContainMain+ "
			  + "		(cc.IntegrateFee-cc.IntegrateDisc)*cc.ContainInt+ "
			  + "		(cc.CupboardFee-cc.CupBoardDisc)*cc.ContainCup+ "
			  + "		(cc.SoftFee-cc.SoftDisc-cc.SoftFee_Furniture)*cc.ContainSoft+ "
			  + "		cc.SoftFee_Furniture*cc.ContainSoft*0.5+ "
			  + "		cc.MainServFee*cc.ContainMainServ*1.0/3-cc.BaseDisc,2) "
			  + " 	else  round(asn.DesignFee+asn.BaseFee_Dirct+asn.BaseFee_Comp-asn.LongFee+ (asn.MainFee-asn.MainDisc)*asn.ContainMain+ "
			  + " 		(asn.IntegrateFee-asn.IntegrateDisc)*asn.ContainInt+ "
			  + " 		(asn.CupboardFee-asn.CupBoardDisc)*asn.ContainCup+ "
			  + " 		(asn.SoftFee-asn.SoftDisc-asn.SoftFee_Furniture)*asn.ContainSoft+ "
			  + " 		asn.SoftFee_Furniture*asn.ContainSoft*0.5+ "
			  + " 		asn.MainServFee*asn.ContainMainServ*1.0/3-asn.BaseDisc,2) end  /cs4.num   " 
              + " 		else 0  end)  AchievFee , " //分摊业绩金额，改为用客户表的数据
		      + " case when asn.FirstPay = 0 then 0  else (isnull(cp.Amount,0) - case when asn.paytype='1' then case when convert(varchar(7), asn.SignDate, 126) = convert(varchar(7), asn.NewSignDate, 126) then cc.DesignFee else asn.DesignFee end " 
		      + " else 0  end)/asn.FirstPay*100 end PayPer,  " 
		      + " case when convert(varchar(7), asn.SignDate, 126) = convert(varchar(7), asn.NewSignDate, 126) then cc.MainServFee*cc.ContainMainServ else asn.MainServFee*asn.ContainMainServ end MainServFee, " 
		      + " cc.AchievDed,cc.SetDate, '重签' ContractType, ct.Desc1 CustTypeDescr " 
		      + " from tCustomer cc "
		      + " inner join tCustType ct on cc.Custtype=ct.code  " 
		      + " inner join (select custcode,min(pk) minpk from tAgainSign  group by CustCode) AsMin on cc.Code =AsMin.Custcode   "  
		      + " inner join tAgainSign Asn on asmin.minpk=asn.pk and  asn.SignDate >=?  and asn.SignDate <= ?    " 
		      + " inner join tCustStakeholder cs on cs.CustCode=cc.Code"
		      + " inner join (select CustCode,Role,count(1) num from tCustStakeholder group by CustCode,Role) cs4 on cs4.CustCode=cc.Code and cs4.Role=cs.Role "
			  + " left join tPayRule pr on pr.CustType=asn.CustType and asn.PayType=pr.PayType "
			  + " left join tpayruledetail prd on prd.no=pr.No  and prd.PayNum='1' ";
			list.add(customer.getDateFrom());
		list.add(customer.getDateTo());	 
		if("00".equals(customer.getRole().trim())){
			// sql += " left outer join tCustStakeholder cs2 on cs2.CustCode=cc.Code and cs2.Role='00' "
			sql += " left outer join tEmployee e1 on e1.Number=cs.EmpCode ";
		}else if("01".equals(customer.getRole().trim())){
			// sql += " left outer join tCustStakeholder cs3 on cs3.CustCode=cc.Code and cs3.Role='01' "
			sql += " left outer join tEmployee e2 on e2.Number=cs.EmpCode ";
		}
	    sql += " left outer join (select CustCode,sum(Amount) Amount from tCustPay  "
	       + " where Date<= ? group by CustCode) cp on cp.CustCode=cc.Code  "
	       + " where cc.Expired='F' and cs.Role=? and asn.SignDate>=? and asn.SignDate<=? "
	       + " and cs.EmpCode=? ";
		list.add(customer.getDateTo());	 
		list.add(customer.getRole());
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		list.add(customer.getEmpCode());	  
		if (StringUtils.isNotBlank(customer.getCustType())) {
				sql += " and asn.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
		}	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.SignDate";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findSignSetPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from"; 
		if("00".equals(customer.getRole().trim())){
			sql+=" (select e1.NameChi DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr, ";
		}else if("01".equals(customer.getRole().trim())){
			sql+=" (select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,e1.NameChi BusinessManDescr, ";
		}else{
			sql+=" (select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr, ";
		}
		sql+="cc.Code,cc.Address,cc.SetDate,cc.SignDate,cc.EndDate, ct.Desc1 CustTypeDescr, " +
				"cc.Status,s.NOTE StatusDescr,cc.EndCode,e.NOTE EndCodeDescr " +
				"from tCustomer cc " +
				"inner join tCustStakeholder cs on cs.CustCode=cc.Code " +
				"left outer join tCustType ct on cc.CustType = ct.Code " +
				// "left outer join tCustStakeholder cs2 on cs2.CustCode=cc.Code and cs2.Role=? " +
				"left outer join tEmployee e1 on e1.Number=cs.EmpCode  " +
				"left outer join tXTDM s on s.CBM=cc.Status and s.ID='CUSTOMERSTATUS' " +
				"left outer join tXTDM e on e.CBM=cc.EndCode and e.ID='CUSTOMERENDCODE' " +
				"where cc.Expired='F'  and (cc.SignDate>=? " +
				"or (cc.EndDate>=? and cc.EndCode='2') or (cc.SignDate is null and cc.Status='3') or cc.EndCode='5' ) " ;
		//list.add(customer.getRole());
		list.add(customer.getDateFrom());
		list.add(customer.getDateFrom());
		if(StringUtils.isNotBlank(customer.getRole())){
			sql+="and cs.Role=? ";
			list.add(customer.getRole());
		}
		if (customer.getDateTo()!=null) {
			sql += "  and cc.SetDate<=? ";
			list.add(customer.getDateTo());
		}
    	if (StringUtils.isNotBlank(customer.getEmpCode())) {
			sql += " and cs.EmpCode=? ";
			list.add(customer.getEmpCode());
		}
    	if (StringUtils.isNotBlank(customer.getCustType())) {
    		sql += " and cc.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
		}
    	System.out.print(customer.getCustType());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.Status,a.SetDate";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String, Object>> findNowSignPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from"; 
		if("00".equals(customer.getRole().trim())){
			sql+=" (select e1.NameChi DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr, ";
		}else if("01".equals(customer.getRole().trim())){
			sql+=" (select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,e1.NameChi BusinessManDescr, ";
		}else{
			sql+=" (select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr, ";
		}
		//String sql="select * from(select cc.Code,cc.Address,e1.NameChi DesignManDescr,e2.NameChi BusinessManDescr,cc.SetDate,cc.SignDate,cc.EndDate, cc.Status,s.NOTE StatusDescr," +
		sql+="cc.Code,cc.Address,cc.SetDate,cc.SignDate,cc.EndDate, cc.Status,s.NOTE StatusDescr " +
				"from tCustomer cc " +
				"inner join tCustStakeholder cs on cs.CustCode=cc.Code " +
				//"left outer join tCustStakeholder cs2 on cs2.CustCode=cc.Code and cs2.Role='00' " +
				//"left outer join tCustStakeholder cs3 on cs3.CustCode=cc.Code and cs3.Role='01' " +
				"left outer join tEmployee e1 on e1.Number=cs.EmpCode  " +
				"left outer join tEmployee e2 on e2.Number=cs.EmpCode  " +
				"left outer join tXTDM s on s.CBM=cc.Status and s.ID='CUSTOMERSTATUS' " +
				"left outer join tXTDM e on e.CBM=cc.EndCode and e.ID='CUSTOMERENDCODE' " +
				"left outer join tCustType ct on cc.CustType = ct.Code " +
				"where cc.Expired='F'  and cc.Status='3' ";
		if(StringUtils.isNotBlank(customer.getRole())){
			sql+="and cs.Role=? ";
			list.add(customer.getRole());
		}
    	if (StringUtils.isNotBlank(customer.getEmpCode())) {
			sql += " and cs.EmpCode=? ";
			list.add(customer.getEmpCode());
		}
    	if (StringUtils.isNotBlank(customer.getCustType())) {
    		sql += " and cc.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.Status,a.SetDate";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String, Object>> findCrtPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from"; 
			if("00".equals(customer.getRole().trim())){
				sql+=" (select e2.NameChi DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr, ";
			}else if("01".equals(customer.getRole().trim())){
				sql+=" (select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,e2.NameChi BusinessManDescr, ";
			}else{
				sql+=" (select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr, ";
			}
	
		  sql+=" cc.Code,cc.Address,cc.CrtDate,cc.SetDate,cc.SignDate,cc.EndDate,  " +
				"cc.Status,s.NOTE StatusDescr,cc.EndCode,e.NOTE EndCodeDescr, ct.Desc1 CustTypeDescr " +
				"from tCustomer cc " +
				"inner join tCustStakeholder cs on cs.CustCode=cc.Code " +
				"inner join tEmployee em on cc.DesignMan=em.Number      " +
				"left join tCustType ct on cc.CustType = ct.Code " +
				"left join tDepartment1 dm1 on em.Department1=dm1.Code      " +
				"left join tDepartment2 dm2 on em.Department2=dm2.Code      " +
				"left join tDepartment3 dm3 on em.Department2=dm3.Code  " +
				// "left outer join tCustStakeholder cs3 on cs3.CustCode=cc.Code and cs3.Role=? " +
				"left outer join tEmployee e2 on e2.Number=cs.EmpCode  " +
				"left outer join tXTDM s on s.CBM=cc.Status and s.ID='CUSTOMERSTATUS' " +
				"left outer join tXTDM e on e.CBM=cc.EndCode and e.ID='CUSTOMERENDCODE' " +
				"where cc.Expired='F'  and cs.Role=?   and cc.Status <> '1' "; // and (dm1.DepType='2' or dm2.DepType='2'  or dm3.DepType='2' )
		 // list.add(customer.getRole());
		  list.add(customer.getRole());
		  if(customer.getDateFrom()!=null){
			  sql+="and cc.VisitDate>=? ";
			  list.add(customer.getDateFrom());
		  }
		  if(customer.getDateTo()!=null){
			  sql+="and cc.VisitDate<=? ";
//			  list.add(customer.getDateTo());
			  list.add(DateUtil.endOfTheDay(customer.getDateTo()));
		  }
		  if (StringUtils.isNotBlank(customer.getEmpCode())) {
				sql += " and cs.EmpCode=? ";
				list.add(customer.getEmpCode());
			}
		  if (StringUtils.isNotBlank(customer.getCustType())) {
				sql += " and cc.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.CrtDate";
			}

			return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String, Object>> findSetPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql ="select * from"; 
			if("00".equals(customer.getRole().trim())){
				sql+=" (select e2.NameChi DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr, ";
			}else if("01".equals(customer.getRole().trim())){
				sql+=" (select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,e2.NameChi BusinessManDescr, ";
			}else{
				sql+=" (select dbo.fGetEmpNameChi(cc.Code,'00') DesignManDescr,dbo.fGetEmpNameChi(cc.Code,'01') BusinessManDescr, ";
			}
		    sql+="	cc.Code,cc.Address,cc.SetDate,cc.CrtDate, ct.Desc1 CustTypeDescr " +
				"from tCustomer cc inner join tCustStakeholder cs on cs.CustCode=cc.Code " +
				//"left outer join tCustStakeholder cs3 on cs3.CustCode=cc.Code and cs3.Role=? " +
				"left outer join tEmployee e2 on e2.Number=cs.EmpCode  " +
				"left outer join tCustType ct on cc.CustType = ct.Code " +
				"where cc.Expired='F' ";
		
		 // list.add(customer.getRole());
		 if(StringUtils.isNotBlank(customer.getRole())){
				sql+="and cs.Role=? ";
				list.add(customer.getRole());
			}
		  if(customer.getDateFrom()!=null){
			  sql+="and cc.SetDate>=? ";
			  list.add(customer.getDateFrom());
		  }
		  if(customer.getDateTo()!=null){
			  sql+="and cc.SetDate<=? ";
			  list.add(customer.getDateTo());
		  }
		  if (StringUtils.isNotBlank(customer.getEmpCode())) {
				sql += " and cs.EmpCode=? ";
				list.add(customer.getEmpCode());
			}
		  if (StringUtils.isNotBlank(customer.getCustType())) {
				sql += " and cc.CustType in" + "('"+(customer.getCustType().replaceAll(",", "','")+"')");
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " )a order by a.SetDate";
			}

			return this.findPageBySql(page, sql, list.toArray());
	} 
	
	/**
	 * 操作员czybm是否有查看权限
	 * @param czybm
	 * @param wareHouse
	 * @return
	 */
	public boolean hasViewRight(Czybm czybm,  Employee employee) {
		List<Object> list = new ArrayList<Object>();
		if (czybm.getCustRight().equals("1") ){
			String s1=StringUtils.trim(czybm.getCzybh());
			String s2=StringUtils.trim(employee.getNumber());
			return s1.equals(s2);
			
		}
		if (czybm.getCustRight().equals("2") ){
		     String sql = " select 1 from   tEmployee a "
	                    + " inner join tCZYDept b on a.Department1 = b.Department1 "
	                    + " and ( a.Department2 = b.Department2 or b.Department2 = '' or b.Department2 is null  ) "
	                    + " and ( a.Department3 = b.Department3 or b.Department3 = '' or b.Department3 is null)"
	                    + " where a.Number=? and b.CZYBH=?";
		 	 list.add(employee.getNumber());
		 	 list.add(czybm.getCzybh());
		 	List<Map<String, Object>> page = this.findBySql(sql, list.toArray());
		 	System.out.print(!page.isEmpty());
			return !page.isEmpty();
			
			
		}
		else {
			return true;
		}
	}
	public Page<Map<String, Object>> findQdyjPageBySql(Page<Map<String, Object>> page, Customer customer,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from";
		if ("00".equals(customer.getRole().trim())) {
			sql += " (select e.NameChi DesignManDescr,dbo.fGetPerfEmpNameChi(a.PK,'01') BusinessManDescr, ";
		} else if ("01".equals(customer.getRole().trim())) {
			sql += " (select dbo.fGetPerfEmpNameChi(a.PK,'00') DesignManDescr,e.NameChi BusinessManDescr, ";
		}else{
			sql += " (select dbo.fGetPerfEmpNameChi(a.PK,'00') DesignManDescr,dbo.fGetPerfEmpNameChi(a.PK,'01') BusinessManDescr, ";
		}
		sql += " b.EmpCode,a.RecalPerf InitPerfAmount,a.RecalPerf*b.PerfPer RealPerfAmount,c.Address,a.AchieveDate, "
				+ "c.Area,x1.NOTE PerfType,b.PerfPer, "
				+ "a.DesignFee,a.baseplan,a.mainplan,a.integrateplan,a.cupboardplan,a.softplan,a.mainservplan,a.basedisc,"
				+ "a.ManageFee_Base+a.ManageFee_Main+a.ManageFee_Serv+a.ManageFee_Soft+a.ManageFee_Int+a.ManageFee_Cup managefee_sum,"
		        + "a.softfee_furniture,a.contractfee*b.PerfPer contractfee,a.gift, "
		        + "cast(dbo.fGetPerfDept2Descr(a.PK,'01') as nvarchar(100)) businessmandeptdescr, "
		        + "cast(dbo.fGetPerfDept2Descr(a.PK,'00') as nvarchar(100)) designmandeptdescr, "
		        + "ct.Desc1 CustTypeDescr "
				+ "from tPerformance a  "
				+ "left join tPerfStakeholder b on a.PK=b.PerfPK "
				+ "inner join tCustomer c on a.CustCode=c.Code "
				+ "left join tCustType ct on c.CustType = ct.Code "
				+ "left join tBuilder d on c.BuilderCode=d.Code "
				+ "left join tEmployee e on b.EmpCode=e.Number "
				+ "left join tXTDM x1 on a.Type=x1.CBM and x1.ID='PERFTYPE' "
				+ "where c.Expired='F' and (a.Type in ('1','2')  or a.Type='5') and b.role=? ";
		sql += " and " + SqlUtil.getCustRight(uc, "c", 0);
		list.add(customer.getRole());
		if (customer.getDateFrom() != null) {
			sql += "and a.AchieveDate>=? ";
			list.add(customer.getDateFrom());
		}
		if (customer.getDateTo() != null) {
			sql += "and a.AchieveDate<=? ";
			list.add(DateUtil.endOfTheDay(customer.getDateTo()));
		}
		if (StringUtils.isNotBlank(customer.getEmpCode())) {
			sql += " and b.EmpCode=? ";
			list.add(customer.getEmpCode());
		}
		if (StringUtils.isNotBlank(customer.getDepartment1())) {
			sql += " and b.Department1=? ";
			list.add(customer.getDepartment1());
		}
		if (StringUtils.isNotBlank(customer.getDepartment2())) {
			sql += " and b.Department2=? ";
			list.add(customer.getDepartment2());
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and c.CustType in" + "('"
					+ (customer.getCustType().replaceAll(",", "','") + "')");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.AchieveDate";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}	
	public Page<Map<String, Object>> findZjyjPageBySql(Page<Map<String, Object>> page, Customer customer,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from";
		if ("00".equals(customer.getRole().trim())) {
			sql += " (select e.NameChi DesignManDescr,dbo.fGetPerfEmpNameChi(a.PK,'01') BusinessManDescr, ";
		} else if ("01".equals(customer.getRole().trim())) {
			sql += " (select dbo.fGetPerfEmpNameChi(a.PK,'00') DesignManDescr,e.NameChi BusinessManDescr, ";
		}else{
			sql += " (select dbo.fGetPerfEmpNameChi(a.PK,'00') DesignManDescr,dbo.fGetPerfEmpNameChi(a.PK,'01') BusinessManDescr, ";
		}
		sql += " b.EmpCode,a.RecalPerf InitPerfAmount,a.RecalPerf*b.PerfPer RealPerfAmount,c.Address,a.AchieveDate, "
				+ "c.Area,x1.NOTE PerfType,b.PerfPer, "
				+ "a.DesignFee,a.baseplan,a.mainplan,a.integrateplan,a.cupboardplan,a.softplan,a.mainservplan,a.basedisc,"
				+ "a.ManageFee_Base+a.ManageFee_Main+a.ManageFee_Serv+a.ManageFee_Soft+a.ManageFee_Int+a.ManageFee_Cup managefee_sum,"
		        + "a.softfee_furniture,a.contractfee*b.PerfPer contractfee,a.gift, "
		        + "cast(dbo.fGetPerfDept2Descr(a.PK,'01') as nvarchar(100)) businessmandeptdescr, "
		        + "cast(dbo.fGetPerfDept2Descr(a.PK,'00') as nvarchar(100)) designmandeptdescr, "
		        + "x2.NOTE CheckStatusdescr,c.CustCheckDate, ct.Desc1 CustTypeDescr "
				+ "from tPerformance a  "
				+ "left join tPerfStakeholder b on a.PK=b.PerfPK "
				+ "inner join tCustomer c on a.CustCode=c.Code "
				+ "left join tCustType ct on c.CustType = ct.Code "
				+ "left join tBuilder d on c.BuilderCode=d.Code "
				+ "left join tEmployee e on b.EmpCode=e.Number "
				+ "left join tXTDM x1 on a.Type=x1.CBM and x1.ID='PERFTYPE' "
				+ " left outer join tXTDM x2 on c.CheckStatus=x2.CBM and x2.ID='CheckStatus'  "
				+ "where c.Expired='F' and a.Type  in ('3','4','6')  and b.role=? ";
		sql += " and " + SqlUtil.getCustRight(uc, "c", 0);
		list.add(customer.getRole());
		if (customer.getDateFrom() != null) {
			sql += "and a.AchieveDate>=? ";
			list.add(customer.getDateFrom());
		}
		if (customer.getDateTo() != null) {
			sql += "and a.AchieveDate<=? ";
			list.add(DateUtil.endOfTheDay(customer.getDateTo()));
		}
		if (StringUtils.isNotBlank(customer.getEmpCode())) {
			sql += " and b.EmpCode=? ";
			list.add(customer.getEmpCode());
		}
		if (!"1".equals(customer.getStatistcsMethod())) {
			if (StringUtils.isNotBlank(customer.getDepartment1())) {
				sql += " and b.Department1=? ";
				list.add(customer.getDepartment1());
			}
			if (StringUtils.isNotBlank(customer.getDepartment2())) {
				sql += " and b.Department2=? ";
				list.add(customer.getDepartment2());
			}
		}
		
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and c.CustType in" + "('"
					+ (customer.getCustType().replaceAll(",", "','") + "')");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.AchieveDate";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}	
}
