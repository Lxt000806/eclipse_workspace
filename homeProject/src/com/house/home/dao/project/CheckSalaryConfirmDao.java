package com.house.home.dao.project;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.project.CustCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class CheckSalaryConfirmDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustCheck custCheck) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from (select a.CustCode, b.Address, b.Area, a.AppDate, IsSalaryConfirm,X1.NOTE IsSalaryConfirmDescr," 
					+ " SalaryConfirmCZY,d.ZWXM SalaryConfirmCZYDescr, SalaryConfirmDate,dpt2.Desc2 prjDeptDescr,"
					+ " e.NameChi ProjectManDescr,c.Desc1 CusttypeDescr, "
					+ " b.CustCheckDate "
					+ " from tCustCheck a "
					+ " left join tCustomer b on a.CustCode = b.Code "
					+ " left join tCusttype c on c.code = b.CustType "
					+ " left join tCZYBM d on d.CZYBH = a.SalaryConfirmCZY "
					+ " left join tEmployee e on b.ProjectMan = e.Number "
					+ " left join tDepartment2 dpt2 on dpt2.Code = e.Department2 "
					+ " left join txtdm x1 on x1.CBM =a.IsSalaryConfirm and x1.id='YESNO' " 
					+ " where 1=1 ";
		
		if (custCheck.getCustCheckDateFrom() != null){
			sql += " and b.CustCheckDate >= ? ";
			list.add(custCheck.getCustCheckDateFrom());
		}
		
    	if (custCheck.getCustCheckDateTo() != null){
			sql += " and b.CustCheckDate < ? ";
			list.add(DateUtil.addDateOneDay(custCheck.getCustCheckDateTo()));
		}
    	
    	if (custCheck.getSalaryConfirmDateFrom() != null){
			sql += " and a.salaryConfirmDate>= ? ";
			list.add(custCheck.getSalaryConfirmDateFrom());
		}
    	if (custCheck.getSalaryConfirmDateTo() != null){
			sql += " and a.salaryConfirmDate< ? ";
			list.add(DateUtil.addDateOneDay(custCheck.getSalaryConfirmDateTo()));
		}
    	
    	if (StringUtils.isNotBlank(custCheck.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%" + custCheck.getAddress().trim() + "%");
		}
    	if (StringUtils.isNotBlank(custCheck.getIsSalaryConfirm())) {
    		if("0".equals(custCheck.getIsSalaryConfirm())){
    			sql += " and (a.IsSalaryConfirm=? or isnull(a.IsSalaryConfirm,'')='')";
    			list.add(custCheck.getIsSalaryConfirm());
    		}else{
    			sql += " and a.IsSalaryConfirm=? ";
    			list.add(custCheck.getIsSalaryConfirm());
    		}	
		}
    	if(StringUtils.isNotBlank(custCheck.getPrjDepartment2())){
			sql += " and e.Department2 in " + "('"+custCheck.getPrjDepartment2().replaceAll(",", "','")+"')";
		}
    	if (StringUtils.isNotBlank(custCheck.getCustType())) {
			sql += " and b.CustType in " + "('"+custCheck.getCustType().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by AppDate,a.SalaryConfirmDate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageSalaryDetailBySql(
			Page<Map<String, Object>> page, CustCheck custCheck) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from(select a.workercode, b.namechi workercodedescr,c.descr worktype12descr,a.comedate,isnull(FixSalary,0)FixSalary, "
				    +" isnull(f.paySalary,0) paySalary,case when isnull(FixSalary,0)=0 then 0 else round(isnull(f.paySalary,0)*100/isnull(e.FixSalary,0),2) end payper,"
				    +" isnull(f.AppAmount,0) AppAmount "
				    +" from ( " 
				    +" 		select in_a.CustCode,in_a.WorkerCode,in_a.WorkType12, max(in_a.ComeDate) ComeDate "
				    +"		from tCustWorker in_a "
				    +"		group by in_a.CustCode,in_a.WorkerCode,in_a.WorkType12 "
					+" ) a "
				    +" left join tworker b on a.workercode = b.code "
				    +" left join tworktype12 c on a.worktype12 = c.code "
				    +" left join tcustomer d on a.custcode = d.code "
				    +" left join temployee emp on d.projectman = emp.number "
				    +" left join (select in_b.worktype12,in_a.custcode,sum(isnull(in_a.qty, 0) * isnull(in_a.offerpri,0)) FixSalary "
				    +" from tbasecheckitemplan in_a "  
				    +" inner join tbasecheckitem in_b on in_b.code = in_a.basecheckitemcode " 
				    +" group by in_b.worktype12,in_a.custcode "
				    +" )e on e.worktype12=a.worktype12  and e.CustCode=a.CustCode "   
				    +" left join ( select in_a.WorkerCode,in_a.CustCode,in_c.WorkType12,"
				    +" 		sum(case when in_a.Status='2' and in_b.ConfirmDate is not null then in_a.ConfirmAmount else 0 end) paysalary, "
				    +"		sum(case when in_a.status<>'3' then in_a.AppAmount else 0 end) AppAmount "
				    +" from tWorkCostDetail in_a "
					+" left join tWorkCost in_b on in_a.No=In_b.No "
//					+" left join tWorkType2 in_c on in_c.Code=in_a.WorkType2 " +
					+" left join tPrjItem2 in_c on in_c.OfferWorkType2=in_a.WorkType2 "
					+" group by in_c.WorkType12,in_a.WorkerCode,in_a.CustCode "
				    +" )f on f.CustCode=a.CustCode and f.WorkerCode=a.WorkerCode and f.Worktype12=a.Worktype12  "
				    +" where 1=1 and (e.FixSalary <> 0 or f.paysalary <> 0) ";
		if (StringUtils.isNotBlank(custCheck.getCustCode())) {
			sql += "  and a.CustCode = ? ";
			list.add(custCheck.getCustCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.comedate ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
}
