package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.driver.ItemSendPhoto;
import com.house.home.entity.project.PrjProblem;
import com.house.home.entity.query.PrjProblemPic;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;

@SuppressWarnings("serial")
@Repository
public class PrjProblemDao extends BaseDao {

	public String getSeqNo(String tableName) {
		return super.getSeqNo(tableName);
	}

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrjProblem prjProblem,
			UserContext uc) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.no,a.status,a.appDate,a.confirmDate,a.feedbackDate,a.dealDate,a.cancelDate,a.planDealDate,c.code "
				+ " ,c.address,b.desc1 custTypedescr,x2.note custStatusdescr,x1.note statusDescr,x5.NOTE isBringStopDescr,stopDays,"
				+ " eapp.nameChi appDescr,eCon.nameChi confirmDescr,eFee.nameChi feedBackDescr,eDea.nameChi dealDescr ,eCel.nameChi cancelDescr,"
				+ " f.descr DeptDescr,g.descr promTypeDescr,x3.note prompropDescr,a.lastUpdate,a.lastUpdatedby,a.Expired,a.Actionlog"
				+ " ,x4.note isDealDescr,a.remarks,a.dealRemarks,prjd.desc1 prjDepartment2Descr,ds.nameChi DesignManDescr, "
				+ "  dsd.desc1 DesignDepartment2Descr ,epm.phone projectPhone,cast(dbo.fGetEmpNameChi(c.Code,'34') as nvarchar(20)) MainDescr," 
				+ " jc.nameChi JCDesignManDescr ,jcd.desc1 jcDepartment2Descr,a.confirmCZY,eFix.NameChi FixDutyManDescr,a.FixDutyDate,isnull(pic.picNum,0)picNum,cg.nameChi CGDesignManDescr "
				+ " from tprjProblem a "
				+ " left join tcustomer c on c.code=a.custcode "
				+ " left join tcustType b on b.code =c.custtype "
				+ " left join tEmployee eApp on eApp.number=a.Appczy  "
				+ " left join tEmployee eCon on eCon.number=a.confirmCZY "
				+ " left join temployee eFee on eFee.number=a.feedbackCZY"
				+ " left join temployee eDea on eDea.number=a.dealCZY "
				+ " left join temployee eCel on eCel.number=a.CancelCZY "
				+ " left join temployee ePM on epm.number=c.projectMan "
				+ " left join tPrjPromDept f on f.code= a.promDeptCode "
				+ " left join tPrjPromType g on g.code= a.promTypeCode"
				+ " left join txtdm x1 on x1.cbm=a.status and x1.id='PRJPROMSTATUS' "
				+ " left join txtdm x2 on x2.cbm=c.status and x2.id='CUSTOMERSTATUS' "
				+ " left join txtdm x3 on x3.cbm=a.prompropCode and x3.id='PRJPROMPROP' "
				+ " left join txtdm x4 on x4.cbm=a.isDeal and x4.id='YESNO' "
				+ " left join txtdm x5 on x5.cbm=a.isBringStop and x5.id='YESNO' "
				+ " left join tDepartment2 prjd on prjd.Code =eapp.Department2  "
				+ " left outer join tEmployee ds on c.DesignMan=ds.Number "
				+ " left join tDepartment2 dsd on dsd.Code =ds.Department2  "
				+ " left outer join (select CustCode,max(EmpCode) EmpCode from tCustStakeHolder where role='11' group by CustCode) JCDS on c.Code=JCDS.CustCode "
				+ " left outer join (select CustCode,max(EmpCode) EmpCode from tCustStakeHolder where role='34' group by CustCode) cs on cs.CustCode=a.custCode "
				+ " left outer join tEmployee jc on jc.Number=JCDS.EmpCode "
				+ " left join tDepartment2 jcd on jcd.Code =jc.Department2 "
				+ " left outer join tEmployee eFix on a.FixDutyMan=eFix.Number "
				+ " left join (select count(1) picNum,No from tPrjProblemPic group by No ) pic on a.No=pic.No "
				+ " left outer join (select CustCode,max(EmpCode) EmpCode from tCustStakeHolder where role='61' group by CustCode) CGDS on c.Code=CGDS.CustCode "
				+ " left outer join tEmployee cg on cg.Number=CGDS.EmpCode "
				+ " where 1=1 and " + SqlUtil.getCustRight(uc, "c", 0);
		if (StringUtils.isNotBlank(prjProblem.getAddress())) {
			sql += " and c.address like ? ";
			list.add("%" + prjProblem.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(prjProblem.getPromDeptCode())) {
			sql += " and f.code in " + "('"
					+ prjProblem.getPromDeptCode().replaceAll(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(prjProblem.getPromTypeCode())) {
			sql += " and g.code in " + "('"
					+ prjProblem.getPromTypeCode().replaceAll(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(prjProblem.getPromPropCode())) {
			sql += " and x3.cbm in " + "('"
					+ prjProblem.getPromPropCode().replaceAll(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(prjProblem.getStatus())) {
			sql += " and a.status in " + "('"
					+ prjProblem.getStatus().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(prjProblem.getIsDeal())) {
			sql += " and a.isDeal = ? ";
			list.add(prjProblem.getIsDeal());
		}
		if (prjProblem.getAppDateFrom() != null) {
			sql += " and a.appDate>= ? ";
			list.add(prjProblem.getAppDateFrom());
		}
		if (prjProblem.getAppDateTo() != null) {
			sql += " and a.appDate<DATEADD(d,1,?) ";
			list.add(prjProblem.getAppDateTo());
		}
		if (prjProblem.getConfirmDateFrom() != null) {
			sql += " and a.confirmDate>= ? ";
			list.add(prjProblem.getConfirmDateFrom());
		}
		if (prjProblem.getConfirmDateTo() != null) {
			sql += " and a.confirmDate<DATEADD(d,1,?) ";
			list.add(prjProblem.getConfirmDateTo());
		}
		if (prjProblem.getFeedbackDateFrom() != null) {
			sql += " and a.FeedbackDate>= ? ";
			list.add(prjProblem.getFeedbackDateFrom());
		}
		if (prjProblem.getFeedbackDateTo() != null) {
			sql += " and a.FeedbackDate<DATEADD(d,1,?) ";
			list.add(prjProblem.getFeedbackDateTo());
		}
		if (prjProblem.getDealDateFrom() != null) {
			sql += " and a.DealDate>= ? ";
			list.add(prjProblem.getDealDateFrom());
		}
		if (prjProblem.getDealDateTo() != null) {
			sql += " and a.dealDate<DATEADD(d,1,?) ";
			list.add(prjProblem.getDealDateTo());
		}
		if (prjProblem.getPlanDealDateFrom() != null) {
			sql += " and a.PlanDealDate>= ? ";
			list.add(prjProblem.getPlanDealDateFrom());
		}
		if (prjProblem.getPlanDealDateTo() != null) {
			sql += " and a.PlanDealDate<DATEADD(d,1,?) ";
			list.add(prjProblem.getPlanDealDateTo());
		}
		if (StringUtils.isNotBlank(prjProblem.getPrjDepartment2())) {
			sql += " and eapp.Department2 = ?";
			list.add(prjProblem.getPrjDepartment2());
		}
		if (StringUtils.isNotBlank(prjProblem.getJcDepartment2())) {
			sql += " and jc.Department2 = ?";
			list.add(prjProblem.getJcDepartment2());
		}
		if (StringUtils.isNotBlank(prjProblem.getIsBringStop())) {
			sql += " and a.IsBringStop = ?";
			list.add(prjProblem.getIsBringStop());
		}
		if (StringUtils.isNotBlank(prjProblem.getMaterialSteward())) {
			sql += " and cs.empCode= ? ";
			list.add(prjProblem.getMaterialSteward());
		}
		
        if (prjProblem.getFixDutyDateFrom() != null) {
            sql += " and a.FixDutyDate >= ? ";
            list.add(prjProblem.getFixDutyDateFrom());
        }
        
        if (prjProblem.getFixDutyDateTo() != null) {
            sql += " and a.FixDutyDate < DATEADD(d,1,?) ";
            list.add(prjProblem.getFixDutyDateTo());
        }
		
		if (StringUtils.isNotBlank(prjProblem.getEmpCode())) {
			sql += " and exists (select 1 from tCustStakeholder in_a where in_a.CustCode=a.CustCode and  in_a.Role in ('00','34','11') and in_a.EmpCode=?  ) ";
			list.add(prjProblem.getEmpCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> findPromDept(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tPrjPromDept a where a.expired='F'";
		if (StringUtils.isNotBlank((String) param.get("pCode"))) {
			sql += " and a.code in (" + param.get("pCode") + ") ";
		}
		sql += " order by a.code ";
		return this.findBySql(sql, list.toArray());
	}

	public List<Map<String, Object>> findPromType(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tPrjPromType a where a.expired='F'";
		if (StringUtils.isNotBlank((String) param.get("pCode"))) {
			sql += " and a.DeptCode in (" + param.get("pCode") + ") ";
		}
		sql += " order by a.code";
		return this.findBySql(sql, list.toArray());
	}

	public boolean isExistType(String deptCode) {
		String sql = "select * from tPrjPromType where deptCode = ? ";

		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { deptCode });

		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	public Page<Map<String, Object>> getPrjProblemList(
			Page<Map<String, Object>> page, PrjProblem prjProblem) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select tx1.NOTE statusDescr,tc.Address,tpp.appDate,tppd.Descr promDeptCodeDescr,tppt.Descr promTypeCodeDescr,tpp.no "
				+ " from tPrjProblem tpp "
				+ " left join tCustomer tc on tc.Code = tpp.CustCode "
				+ " left join tPrjPromDept tppd on tppd.Code = tpp.promDeptCode "
				+ " left join tPrjPromType tppt on tppt.Code = tpp.promTypeCode "
				+ " left join tXTDM tx1 on tx1.ID='PRJPROMSTATUS' and tx1.cbm = tpp.status "
				+ " where 1=1 ";
		if (StringUtils.isNotBlank(prjProblem.getAddress())) {
			sql += " and tc.Address like ? ";
			params.add("%" + prjProblem.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(prjProblem.getStatus())) {
			sql += " and tpp.Status = ? ";
			params.add(prjProblem.getStatus());
		}
		if (StringUtils.isNotBlank(prjProblem.getAppCZY())) {
			sql += " and tpp.AppCZY = ? ";
			params.add(prjProblem.getAppCZY());
		}
		sql += " order by tpp.appDate desc ";
		return this.findPageBySql(page, sql, params.toArray());
	}

	public List<Map<String, Object>> getPrjPromDeptList() {
		String sql = " select code,descr from tPrjPromDept where Expired='F' ";
		return this.findBySql(sql, new Object[] {});
	}

	public List<Map<String, Object>> getPrjPromTypeList(String prjPromDept) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select code,descr from tPrjPromType where Expired='F' ";
		if (StringUtils.isNotBlank(prjPromDept)) {
			sql += " and DeptCode = ? ";
			params.add(prjPromDept);
		}
		return this.findBySql(sql, params.toArray());
	}

	public Map<String, Object> getPrjProblem(String no) {
		String sql = " select tpp.no,tc.Address,tpp.Status,tpp.AppDate,tpp.PromDeptCode,tpp.PromTypeCode,tpp.PromPropCode,tpp.Remarks, "
				+ " te1.NameChi ConfirmCZYDescr,tpp.ConfirmDate,te2.NameChi FeedBackCZYDescr,tpp.FeedbackDate,tpp.PlanDealDate,tpp.DealRemarks, "
				+ " te3.NameChi DealCZYDescr,tpp.DealDate,tpp.IsDeal,te4.NameChi CancelCZYDescr,tpp.CancelDate,tpp.IsBringStop,tx.NOTE IsBringStopDescr, "
				+ " isnull(tpp.StopDays,0) StopDays,tx1.NOTE StatusDescr "
				+ " from tPrjProblem tpp "
				+ " left join tCustomer tc on tpp.CustCode = tc.Code "
				+ " left join tEmployee te1 on te1.Number = tpp.ConfirmCZY "
				+ " left join tEmployee te2 on te2.Number = tpp.FeedbackCZY "
				+ " left join tEmployee te3 on te3.Number = tpp.DealCZY "
				+ " left join tEmployee te4 on te4.Number = tpp.CancelCZY "
				+ " left join tXTDM tx on tx.CBM = tpp.IsBringStop and tx.ID = 'YESNO' "
				+ " left join tXTDM tx1 on tx1.CBM = tpp.Status and tx1.Id = 'PRJPROMSTATUS' "
				+ " where tpp.No = ? ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { no });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public void doUpdate(PrjProblem prjProblem) {
		String sql = " update tPrjProblem set dealRemarks=?,lastupdate=?,lastupdatedby=?,expired=?,actionlog=? where no=?";
		this.executeUpdateBySql(sql, new Object[] {
				prjProblem.getDealRemarks(), prjProblem.getLastUpdate(),
				prjProblem.getLastUpdatedBy(), prjProblem.getExpired(),
				prjProblem.getActionLog(), prjProblem.getNo() });
	}
	
	@SuppressWarnings("unchecked")
	public PrjProblemPic getPicByName(String photoName) {
		String hql = "from PrjProblemPic where photoName=?";
		List<PrjProblemPic> list = this.find(hql, new Object[]{photoName});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String, Object>> getPhotoList(String no) {
		String sql = " select photoName,isSendYun from tPrjProblemPic where Expired='F' and no =? ";
		return this.findBySql(sql, new Object[] {no});
	}
	
	public Page<Map<String,Object>> goPicJqGrid(Page<Map<String,Object>> page, PrjProblem prjProblem) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  select pk,no,photoname from tPrjProblemPic where no=?";
		list.add(prjProblem.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> findPrjProblemPageBySql(
			Page<Map<String, Object>> page, PrjProblem prjProblem) {
		
		List<Object> params = new ArrayList<Object>();
		
		String sql = " select a.AppDate, a.StopDays,a.DealRemarks,e1.NameChi appCzyDescr,e2.NameChi confirmCzyDescr," +
				"	 e3.NameChi feedbackCzyDescr, e4.NameChi dealCzyDescr, f.Descr deptDescr ,g.Descr TypeDescr, " +
				"	 x1.NOTE statusDescr, x3.note prompropDescr,x5.NOTE isBringStopDescr, x4.NOTE isDealDescr ," +
				"	 a.Remarks, a.ConfirmDate,a.FeedbackDate ,a.PlanDealDate,a.DealDate,g.descr promTypeDescr, "
				+ "     case when a.AppDate >= j.FourthStageStartDate "
				+ "                     and a.AppDate <= isnull(j.FourthStageEndDate, getdate()) "
				+ "              then j.FourthStageName "
				+ "          when a.AppDate >= j.ThirdStageStartDate "
				+ "                     and a.AppDate <= isnull(j.ThirdStageEndDate, getdate()) "
				+ "              then j.ThirdStageName "
				+ "          when a.AppDate >= j.SecondStageStartDate "
				+ "                     and a.AppDate <= isnull(j.SecondStageEndDate, getdate()) "
				+ "              then j.SecondStageName "
				+ "          when a.AppDate >= isnull(i.EarliestComeDate, h.ConfirmBegin) "
				+ "                     and a.AppDate <= isnull(j.FirstStageEndDate, getdate()) "
				+ "              then j.FirstStageName "
				+ "     end ConstructionStage "
				+"	 from  tPrjProblem a" +
				"	 left join tEmployee e1 on e1.Number = a.AppCZY" +
				"	 left join tEmployee e2 on e2.number=a.confirmCZY  " +
				"	 left join temployee e3 on e3.number=a.feedbackCZY" +
				"	 left join temployee e4 on e4.number=a.dealCZY " +
				"	 left join tPrjPromDept f on f.code= a.promDeptCode " +
				"	 left join tPrjPromType g on g.code= a.promTypeCode" +
				"	 left join tXTDM x1 on x1.ID = 'PRJPROMSTATUS' and x1.CBM = a.Status" +
				"	 left join txtdm x3 on x3.cbm=a.prompropCode and x3.id='PRJPROMPROP' " +
				"	 left join txtdm x5 on x5.cbm=a.isBringStop and x5.id='YESNO' " +
				"	 left join txtdm x4 on x4.cbm=a.isDeal and x4.id='YESNO' "
				+ " left join tCustomer h on h.Code = a.CustCode "
				+ " outer apply ( "
				+ "     select min(in_a.ComeDate) EarliestComeDate "
				+ "     from tCustWorker in_a "
				+ "     inner join tWorkType12 in_b on in_b.Code = in_a.WorkType12 "
				+ "     where in_b.BeginCheck = '1' "
				+ "         and in_a.CustCode = a.CustCode "
				+ " ) i "
				+ " outer apply ( "
				+ "     select "
				+ "         max(case in_b.Code when '01' then in_d.ConfirmDate end) FirstStageEndDate, "
				+ "         max(case in_b.Code when '01' then in_b.Descr end) FirstStageName, "
				+ "         max(case in_b.Code when '02' then dateadd(day, 1, in_c.ConfirmDate) end) SecondStageStartDate, "
				+ "         max(case in_b.Code when '02' then in_d.ConfirmDate end) SecondStageEndDate, "
				+ "         max(case in_b.Code when '02' then in_b.Descr end) SecondStageName, "
				+ "         max(case in_b.Code when '03' then dateadd(day, 1, in_c.ConfirmDate) end) ThirdStageStartDate, "
				+ "         max(case in_b.Code when '03' then in_d.ConfirmDate end) ThirdStageEndDate, "
				+ "         max(case in_b.Code when '03' then in_b.Descr end) ThirdStageName, "
				+ "         max(case in_b.Code when '04' then dateadd(day, 1, in_c.ConfirmDate) end) FourthStageStartDate, "
				+ "         max(case in_b.Code when '04' then in_d.ConfirmDate end) FourthStageEndDate, "
				+ "         max(case in_b.Code when '04' then in_b.Descr end) FourthStageName "
				+ "     from tCustomer in_a "
				+ "     cross join tProgStage in_b "
				+ "     left join tPrjProg in_c on in_c.PrjItem = in_b.LastPrjItem and in_c.CustCode = in_a.Code "
				+ "     left join tPrjProg in_d on in_d.PrjItem = in_b.EndPrjItem and in_d.CustCode = in_a.Code "
				+ "     where in_a.Code = a.CustCode "
				+ "     group by in_a.Code "
				+ " ) j "
				+ "  where 1=1 and a.custcode = ? ";
		
			params.add(prjProblem.getCustCode());
			
		return this.findPageBySql(page, sql, params.toArray());
	}
}
