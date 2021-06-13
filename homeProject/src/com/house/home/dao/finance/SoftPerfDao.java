package com.house.home.dao.finance;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.finance.SoftPerf;

@SuppressWarnings("serial")
@Repository
public class SoftPerfDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SoftPerf softPerf) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select a.No, a.BeginDate, a.EndDate, a.Status, b.NOTE StatusDescr, a.Remarks,"
          + " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.mon "
          + " from tSoftPerf a"
          + " left join tXTDM b on b.ID='SOFTPERFSTATUS' and b.CBM=a.Status"
          + " where 1=1 ";
		
		if(StringUtils.isNotBlank(softPerf.getNo())){
			sql+=" and a.no = ? ";
			list.add(softPerf.getNo());
		}
		if(softPerf.getDateFrom()!=null){
			sql+=" and a.beginDate>= ?  ";
			list.add(softPerf.getDateFrom());
		}
		if(softPerf.getDateTo()!=null){
			sql+=" and a.beginDate< dateAdd(d,1,?) ";
			list.add(softPerf.getDateTo());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.beginDate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findListPageBySql(Page<Map<String,Object>> page, SoftPerf softPerf) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select a.No, a.BeginDate, a.EndDate, a.Status, b.NOTE StatusDescr, a.Remarks,"
          + " a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog"
          + " from tSoftPerf a"
          + " left join tXTDM b on b.ID='SOFTPERFSTATUS' and b.CBM=a.Status"
          + " where 1=1 ";
		
		if(StringUtils.isNotBlank(softPerf.getNo())){
			sql+=" and a.no = ? ";
			list.add(softPerf.getNo());
		}
		if(softPerf.getDateFrom()!=null){
			sql+=" and a.beginDate>= ?  ";
			list.add(softPerf.getDateFrom());
		}
		if(softPerf.getDateTo()!=null){
			sql+=" and a.beginDate< dateAdd(d,1,?) ";
			list.add(softPerf.getDateTo());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findEmpInfoPageBySql(Page<Map<String,Object>> page, Employee employee) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select  a.PK, a.EmpCode, e.NameChi EmpDescr, a.Department1, a.Department2, d1.Desc2 Department1Descr, d2.Desc2 Department2Descr,"
          + " a.BeginDate, a.EndDate, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, d3.Desc2 NowDepartment1Descr, d4.Desc2 NowDepartment2Descr"
          + " from tEmpForSoftPerf a"
          + " left join tDepartment1 d1 on d1.Code=a.Department1"
          + " left join tDepartment2 d2 on d2.Code=a.Department2"
          + " left join tEmployee e on e.Number=a.EmpCode"
          + " left join tDepartment1 d3 on d3.Code=e.Department1"
          + " left join tDepartment2 d4 on d4.Code=e.Department2"
          + " where 1=1 ";
		if(StringUtils.isNotBlank(employee.getNumber())){
			sql+=" and e.number= ? ";
			list.add(employee.getNumber());
		}
		if(StringUtils.isNotBlank(employee.getDepartment2())){
			sql+=" and d2.code= ? ";
			list.add(employee.getNumber());
		}
		if (StringUtils.isBlank(employee.getExpired())
				|| "F".equals(employee.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findCountSoftPerPageBySql(Page<Map<String,Object>> page, SoftPerf softPerf) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select case when c.IsInternal=1 then '是' else '否' end IsInternaldescr,ct.Desc1 custTypeDescr,c.documentNo,a.pk,a.CustCode,c.descr custdescr,c.address,a.type ," +
				" x1.note typedescr,a.IANo,a.ItemCode,b.descr itemdescr,e2.nameChi againManDescr ,case when a.againMan is null then 0 else a.AgainManCommi end AgainManCommi" +
				"	,case when a.againMan is null then 0 else isnull(a.AgainManCommiPer ,0) end AgainManCommiPer," +
				" d.descr item12descr,e.descr item2descr,a.Qty,a.UnitPrice,a.BefLineAmount,a.Markup,a.ProcessCost,a.LineAmount,a.Cost,a.DiscCost," +
				" a.CostAmount,c.CustCheckDate,a.ConfirmDate,a.Per,a.ProfitPer,f.NameChi BusinessManDescr,g.NameChi DesignManDescr,i.NameChi BuyerDescr," +
				" h.desc2 DesignDept2Descr,a.PerfAmount,j.Desc2 BuyerDept2Descr,a.DesignManCommiPer,a.DesignManCommi,a.BuyerCommiPer," +
				" a.BuyerCommi,k.desc2 businessDept2Descr,a.Orderdate,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog," +
				" x2.note isClearInvDescr,m.NameChi DesignLeaderDescr,a.PerfPer,a.PerfAmount*a.PerfPer*a.OtherEmpPer PerfCard, " + //业绩基数发放比例per改成  OtherEmpPer
				// " case when c.IsInternal='1' or (c.custType<>'2' and c.custType<>'20')  then 0 else isnull(a.businessManCommi,0)end businessManCommi, " +
				" case when c.IsInternal='1' then 0 else isnull(a.businessManCommi,0)end businessManCommi, " +
				" i2.NameChi Buyer2Descr,j2.Desc2 Buyer2Dept2Descr,a.Buyer2CommiPer,a.Buyer2Commi,a.ProdMgrCommiPer,a.ProdMgrCommi,a.OtherEmpPer,sp.Mon,b.Remark ItemReqRemark " +	//买手2、买手2部门、买手2提成点数、买手2提成、产品经理提成点数、产品经理提成 add by zb on 20200406
				" from tSoftPerfCommi a " +
				" left join tcustomer c on c.Code=a.CustCode" +
				" left join txtdm x1 on x1.cbm =a.Type and x1.id='SOFTPERFTYPE'" +
				" left join titem b on b.Code=a.ItemCode" +
				" left join tItemType2 e on e.Code=b.itemtype2" +
				" left join tItemType12 d on d.Code=e.ItemType12" +
				" left join tEmployee f on f.Number=a.BusinessMan" +
				" left join tEmployee g on g.Number=a.DesignMan" +
				" left join tEmployee i on i.Number=a.Buyer " +
				" left join tEmployee i2 on i2.Number = a.Buyer2 " +
				" left join tEmployee e2 on e2.Number = a.AgainMan " +
				" left join tDepartment2 h on h.Code=a.DesignManDepartment2 " +
				" left join (" +
				"   select min(Number) EmpCode, Department2 from tEmployee" +
				"   where IsLead='1' and LeadLevel='1'" +
				"   group by Department2" +
				" ) le on le.Department2=a.DesignManDepartment2" +	 
				" left join tEmployee m on m.Number=le.EmpCode" +
				" left join tDepartment2 j on j.code=a.BuyerDepartment2" +
				" left join tDepartment2 j2 on j2.code=a.Buyer2Department2" +
				" left join tDepartment2 k on k.code=f.department2 " +
				" left join tXtdm x2 on x2.cbm=b.isClearInv and x2.Id='YESNO' " +
				" left join tCustType ct on ct.code=c.CustType " +
				" left join tSoftPerf sp on a.No=sp.No " +
				" left join tItemReq ir on a.ReqPK = ir.PK " +
				" where 1=1 ";
		
		if(StringUtils.isNotBlank(softPerf.getNo())){
			sql+=" and a.no = ? ";
			list.add(softPerf.getNo());
		}
		//提成异常报表调用
		if(StringUtils.isNotBlank(softPerf.getCustCode())){
			sql+=" and a.custcode=? ";
			list.add(softPerf.getCustCode());
		}
		if(StringUtils.isNotBlank(softPerf.getAddress())){
			sql+=" and c.Address like ? ";
			list.add("%"+softPerf.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(softPerf.getType())){
			sql+=" and exists(select 1 from tCommiCustStakeholder in_a where in_a.CustCode = a.CustCode and in_a.Type = ?) ";
			list.add(softPerf.getType());
		}
		if(StringUtils.isNotBlank(softPerf.getSoftPerType())){
			sql+=" and a.Type = ? ";
			list.add(softPerf.getSoftPerType());
		}
		if(StringUtils.isNotBlank(softPerf.getBuyer())){
			sql+=" and a.buyer = ? ";
			list.add(softPerf.getBuyer());
		}
		if(StringUtils.isNotBlank(softPerf.getBusinessMan())){
			sql+=" and a.businessMan = ? ";
			list.add(softPerf.getBusinessMan());
		}
		if(softPerf.getDateFrom()!=null){
			sql+=" and a.confirmDate >= ? ";
			list.add(softPerf.getDateFrom());
		}
		if(StringUtils.isNotBlank(softPerf.getDesignMan())){
			sql+=" and a.designMan = ? ";
			list.add(softPerf.getDesignMan());
		}
		if(softPerf.getDateTo()!=null){
			sql+=" and a.confirmDate < dateAdd(d,1,?) ";
			list.add(softPerf.getDateTo());
		}
		
		if(softPerf.getEndDateFrom()!=null){
			sql+=" and sp.EndDate >= ? ";
			list.add(softPerf.getEndDateFrom());
		}
		
		if(softPerf.getEndDateTo()!=null){
			sql+=" and sp.EndDate < dateAdd(d,1,?) ";
			list.add(softPerf.getEndDateTo());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findCountSoftPerIndPageBySql(Page<Map<String,Object>> page, SoftPerf softPerf) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select case when c.IsInternal=1 then '是' else '否' end IsInternaldescr,ct.Desc1 custTypeDescr,c.documentNo,a.pk,a.CustCode,c.descr custdescr,c.address,a.type ," +
				" x1.note typedescr,a.IANo,a.ItemCode,b.descr itemdescr,e2.nameChi againManDescr ,case when a.againMan is null then 0 else a.AgainManCommi end AgainManCommi" +
				"	,case when a.againMan is null then 0 else isnull(a.AgainManCommiPer ,0) end AgainManCommiPer," +
				" d.descr item12descr,e.descr item2descr,a.Qty,a.UnitPrice,a.BefLineAmount,a.Markup,a.ProcessCost,a.LineAmount,a.Cost,a.DiscCost," +
				" a.CostAmount,c.CustCheckDate,a.ConfirmDate,a.Per,a.ProfitPer,f.NameChi BusinessManDescr,g.NameChi DesignManDescr,i.NameChi BuyerDescr," +
				" h.desc2 DesignDept2Descr,a.PerfAmount,j.Desc2 BuyerDept2Descr,a.DesignManCommiPer,a.DesignManCommi,a.BuyerCommiPer," +
				" a.BuyerCommi,k.desc2 businessDept2Descr,a.Orderdate,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog," +
				" x2.note isClearInvDescr,m.NameChi DesignLeaderDescr,a.PerfPer,a.PerfAmount*a.PerfPer*a.OtherEmpPer PerfCard, " +
				" isnull(a.businessManCommi,0) businessManCommi, " +
				" i2.NameChi Buyer2Descr,j2.Desc2 Buyer2Dept2Descr,a.Buyer2CommiPer,a.Buyer2Commi,a.ProdMgrCommiPer,a.ProdMgrCommi,a.OtherEmpPer,sp.Mon " +	//买手2、买手2部门、买手2提成点数、买手2提成、产品经理提成点数、产品经理提成 add by zb on 20200406
				" from tSoftPerfCommi a " +
				" inner join tcustomer c1 on c1.Code=a.CustCode" +
				" left join tcustomer c on c1.OldCode=c.Code" +
				" left join txtdm x1 on x1.cbm =a.Type and x1.id='SOFTPERFTYPE'" +
				" left join titem b on b.Code=a.ItemCode" +
				" left join tItemType2 e on e.Code=b.itemtype2" +
				" left join tItemType12 d on d.Code=e.ItemType12" +
				" left join tEmployee f on f.Number=a.BusinessMan" +
				" left join tEmployee g on g.Number=a.DesignMan" +
				" left join tEmployee i on i.Number=a.Buyer " +
				" left join tEmployee i2 on i2.Number = a.Buyer2 " +
				" left join tEmployee e2 on e2.Number = a.AgainMan " +
				" left join tDepartment2 h on h.Code=a.DesignManDepartment2 " +
				" left join (" +
				"   select min(Number) EmpCode, Department2 from tEmployee" +
				"   where IsLead='1' and LeadLevel='1'" +
				"   group by Department2" +
				" ) le on le.Department2=a.DesignManDepartment2" +	 
				" left join tEmployee m on m.Number=le.EmpCode" +
				" left join tDepartment2 j on j.code=a.BuyerDepartment2" +
				" left join tDepartment2 j2 on j2.code=a.Buyer2Department2" +
				" left join tDepartment2 k on k.code=f.department2 " +
				" left join tXtdm x2 on x2.cbm=b.isClearInv and x2.Id='YESNO' " +
				" left join tCustType ct on ct.code=c.CustType " +
				" inner join tSoftPerf sp on a.No=sp.No " +
				" where 1=1 and c1.SaleType = '2' and a.BusinessMan = ? ";
		
		//提成总额发放报表调用
		if(StringUtils.isNotBlank(softPerf.getAddress())){
			sql+=" and c.Address like ? ";
			list.add("%"+softPerf.getAddress()+"%");
		}
		list.add(softPerf.getBusinessMan());
		if (softPerf.getBeginMon() != null) {
			sql += " and sp.Mon>=? ";
			list.add(softPerf.getBeginMon());
		}
    	if (softPerf.getEndMon() != null) {
			sql += " and sp.Mon<=? ";
			list.add(softPerf.getEndMon());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findReport_empPageBySql(Page<Map<String,Object>> page, SoftPerf softPerf) {
		List<Object> list = new ArrayList<Object>();

		String DesignSql = " select a.Number, a.NameChi, a.Department1, d1.Desc2 Dept1Descr," +
				" a.Department2, d2.Desc2 Dept2Descr, a.IDNum, a.ConSignCmp, csc.Descr ConSignCmpDescr," +
				" sum(b.PerfAmount) designAmount,sum(b.DesignManCommi) designPer," +
				" 0 buyerAmount,0 buyer2Amount,0 buyerPer ,0 buyer2Per,0 BusinessManAmount,0 businessPer " +
				" from tEmployee a " +
				" inner join tDepartment1 d1 on d1.Code = a.Department1" +
				" left join tDepartment2 d2 on d2.Code = a.Department2" +
				" left join tConSignCmp csc on a.ConSignCmp = csc.Code " +
				" inner join tSoftPerfCommi b on a.Number = b.DesignMan" +
				" where 1 = 1 ";
		if(StringUtils.isNotBlank(softPerf.getNo())){
			DesignSql+=" and b.No= ? ";
			list.add(softPerf.getNo());
		}
		if(StringUtils.isNotBlank(softPerf.getDepartment2())){
			DesignSql += " and a.Department2 in " + "('"+softPerf.getDepartment2().replace(",", "','" )+ "')";
		}
		if(softPerf.getSendDateFrom()!=null){
			DesignSql+=" and b.confirmDate >= ? ";
			list.add(new Timestamp(DateUtil.startOfTheDay(softPerf.getDateFrom()).getTime()));
		}
		if(softPerf.getSendDateTo()!=null){
			DesignSql+=" and b.confirmDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(softPerf.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(softPerf.getEmpCode())){
			DesignSql+=" and a.Number= ? ";
			list.add(softPerf.getEmpCode());
		}
		DesignSql+=" group by a.Number, a.NameChi, a.Department1, d1.Desc2, a.Department2, d2.Desc2, a.IDNum, a.ConSignCmp, csc.Descr ";
		
		String Buyer1Sql=" select    a.Number, a.NameChi, a.Department1, d1.Desc2 Dept1Descr," +
				" a.Department2, d2.Desc2 Dept2Descr, a.IDNum, a.ConSignCmp, csc.Descr ConSignCmpDescr," +
				" 0 designAmount,0 designPer,sum(b.PerfAmount) buyerAmount,0 buyer2Amount," +
				" sum(b.buyerCommi) buyerPer ,0 buyer2Per,0 BusinessManAmount,0 businessper  " +
				" from tEmployee a" +
				" inner join tDepartment1 d1 on d1.Code = a.Department1" +
				" left join tDepartment2 d2 on d2.Code = a.Department2" +
				" left join tConSignCmp csc on a.ConSignCmp = csc.Code " +
				" inner join tSoftPerfCommi b on a.Number = b.Buyer" +
				" where  1 = 1 ";
		
		if(StringUtils.isNotBlank(softPerf.getNo())){
			Buyer1Sql+=" and b.No= ? ";
			list.add(softPerf.getNo());
		}
		if(StringUtils.isNotBlank(softPerf.getDepartment2())){
			Buyer1Sql += " and a.Department2 in " + "('"+softPerf.getDepartment2().replace(",", "','" )+ "')";
		}
		if(softPerf.getDateFrom()!=null){
			Buyer1Sql+=" and b.confirmDate >= ? ";
			list.add(new Timestamp(DateUtil.startOfTheDay(softPerf.getDateFrom()).getTime()));
		}
		if(softPerf.getDateTo()!=null){
			Buyer1Sql+=" and b.confirmDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(softPerf.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(softPerf.getEmpCode())){
			Buyer1Sql+=" and a.Number= ? ";
			list.add(softPerf.getEmpCode());
		}
		Buyer1Sql+=" group by a.Number, a.NameChi, a.Department1, d1.Desc2, a.Department2, d2.Desc2, a.IDNum, a.ConSignCmp, csc.Descr ";
		
		String Buyer2Sql=" select    a.Number, a.NameChi, a.Department1, d1.Desc2 Dept1Descr," +
				" a.Department2, d2.Desc2 Dept2Descr, a.IDNum, a.ConSignCmp, csc.Descr ConSignCmpDescr," +
				" 0 designAmount,0 designPer,0 buyerAmount,sum(b.PerfAmount) buyer2Amount,0 buyerPer," +
				" sum(b.buyer2Commi) buyer2Per ,0 BusinessManAmount,0 businessper  " +
				" from tEmployee a" +
				" inner join tDepartment1 d1 on d1.Code = a.Department1" +
				" left join tDepartment2 d2 on d2.Code = a.Department2" +
				" left join tConSignCmp csc on a.ConSignCmp = csc.Code " +
				" inner join tSoftPerfCommi b on a.Number = b.Buyer2" +
				" where  1 = 1 ";
		
		if(StringUtils.isNotBlank(softPerf.getNo())){
			Buyer2Sql+=" and b.No= ? ";
			list.add(softPerf.getNo());
		}
		if(StringUtils.isNotBlank(softPerf.getDepartment2())){
			Buyer2Sql += " and a.Department2 in " + "('"+softPerf.getDepartment2().replace(",", "','" )+ "')";
		}
		if(softPerf.getDateFrom()!=null){
			Buyer2Sql+=" and b.confirmDate >= ? ";
			list.add(new Timestamp(DateUtil.startOfTheDay(softPerf.getDateFrom()).getTime()));
		}
		if(softPerf.getDateTo()!=null){
			Buyer2Sql+=" and b.confirmDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(softPerf.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(softPerf.getEmpCode())){
			Buyer2Sql+=" and a.Number= ? ";
			list.add(softPerf.getEmpCode());
		}
		Buyer2Sql+=" group by a.Number, a.NameChi, a.Department1, d1.Desc2, a.Department2, d2.Desc2, a.IDNum, a.ConSignCmp, csc.Descr ";
		
		String BusinessManSql = " select a.Number, a.NameChi, a.Department1, d1.Desc2 Dept1Descr," +
				" a.Department2, d2.Desc2 Dept2Descr, a.IDNum, a.ConSignCmp, csc.Descr ConSignCmpDescr," +
				" 0 designAmount,0 designPer,0 buyerAmount,0 buyer2Amount, " +
				" 0 buyerPer ,0 buyer2Per,sum(b.PerfAmount) BusinessManAmount,sum(b.businessManCommi) businessPer  " +
				" from tEmployee a " +
				" inner join tDepartment1 d1 on d1.Code = a.Department1" +
				" left join tDepartment2 d2 on d2.Code = a.Department2" +
				" left join tConSignCmp csc on a.ConSignCmp = csc.Code " +
				" inner join tSoftPerfCommi b on a.Number = b.BusinessMan" +
				" where 1 = 1 ";
		if(StringUtils.isNotBlank(softPerf.getNo())){
			BusinessManSql+=" and b.No= ? ";
			list.add(softPerf.getNo());
		}
		if(StringUtils.isNotBlank(softPerf.getDepartment2())){
			BusinessManSql += " and a.Department2 in " + "('"+softPerf.getDepartment2().replace(",", "','" )+ "')";
		}
		if(softPerf.getSendDateFrom()!=null){
			BusinessManSql+=" and b.confirmDate >= ? ";
			list.add(new Timestamp(DateUtil.startOfTheDay(softPerf.getDateFrom()).getTime()));
		}
		if(softPerf.getSendDateTo()!=null){
			BusinessManSql+=" and b.confirmDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(softPerf.getDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(softPerf.getEmpCode())){
			BusinessManSql+=" and a.Number= ? ";
			list.add(softPerf.getEmpCode());
		}
		BusinessManSql+=" group by a.Number, a.NameChi, a.Department1, d1.Desc2, a.Department2, d2.Desc2, a.IDNum, a.ConSignCmp, csc.Descr ";
		
		
		String sql= "select  * from ( select Number,NameChi, a.Department1, a.Dept1Descr," +
				"a.department2,Dept2Descr Depa2Descr, a.IDNum, a.ConSignCmp, a.ConSignCmpDescr," +
				"sum(designAmount) designamount,sum(designPer) designPer," +
				"sum(buyerAmount) buyerAmount,sum(buyer2Amount) buyer2Amount,sum(buyerPer) buyerPer," +
				"sum(buyer2Per) buyer2Per,sum(designPer+buyerPer+buyer2Per+businessPer) allPer," +
				"sum(businessManAmount) businessManAmount,sum(businessPer) businessPer " +
				"from ( "
		          + DesignSql
		          + "   union all "
		          + Buyer1Sql
		          + "   union all "
		          + Buyer2Sql
		           +" union all "
		           +BusinessManSql
		          + " ) a group by a.Number, a.NameChi, a.Department1, a.Dept1Descr, a.Department2, a.Dept2Descr, a.IDNum, a.ConSignCmp, a.ConSignCmpDescr ";
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findReport_teamPageBySql(Page<Map<String,Object>> page, SoftPerf softPerf) {
		List<Object> list = new ArrayList<Object>();

		String DesignSql = "select d1.Code Department1, d1.Desc2 Dept1Descr, d2.Code Department2, d2.Desc2 depa2Descr," +
				" sum(a.PerfAmount) designAmount,0 buyerAmount,0 buyer2Amount," +
				" sum(a.DesignManCommi) designPer ," +
				" 0 buyerPer,0 buyer2Per,0 BusinessManAmount,0 businessPer from tSoftPerfCommi a " +
				" inner join tDepartment1 d1 on d1.Code=a.DesignManDepartment1 " +
				" left join tDepartment2 d2 on d2.Code=a.DesignManDepartment2 " +
				" where 1=1";
		if(StringUtils.isNotBlank(softPerf.getNo())){
			DesignSql+=" and a.No= ? ";
			list.add(softPerf.getNo());
		}
		if(StringUtils.isNotBlank(softPerf.getDepartment2())){
			DesignSql += " and d2.code in " + "('"+softPerf.getDepartment2().replace(",", "','" )+ "')";
		}
		if(softPerf.getDateFrom()!=null){
			DesignSql+=" and a.confirmDate >= ?";
			list.add(new Timestamp(DateUtil.startOfTheDay(softPerf.getDateFrom()).getTime()));
		}
		if(softPerf.getDateTo()!=null){
			DesignSql+=" and a.confirmDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(softPerf.getDateTo()).getTime()));
		}
		
		DesignSql+=" group by d1.Code, d1.Desc2, d2.Code, d2.Desc2 ";
		
		String Buyer1Sql=" select d1.Code Department1, d1.Desc2 Dept1Descr, d2.Code Department2, d2.Desc2 depa2Descr," +
				" 0 designAmount,sum(a.PerfAmount) buyerAmount,0 buyer2Amount,0 designPer ," +
				" sum(a.BuyerCommi) buyerPer,0 buyer2Per,0 BusinessManAmount,0 businessPer from tSoftPerfCommi a " +
				" inner join tDepartment1 d1 on d1.Code=a.BuyerDepartment1" +
				" left join tDepartment2 d2 on d2.Code=a.BuyerDepartment2" +
				"  where 1=1 ";
		
		if(StringUtils.isNotBlank(softPerf.getNo())){
			Buyer1Sql+=" and a.No= ? ";
			list.add(softPerf.getNo());
		}
		if(StringUtils.isNotBlank(softPerf.getDepartment2())){
			Buyer1Sql += " and d2.code in " + "('"+softPerf.getDepartment2().replace(",", "','" )+ "')";
		}
		if(softPerf.getDateFrom()!=null){
			Buyer1Sql+=" and a.confirmDate >= ?";
			list.add(new Timestamp(DateUtil.startOfTheDay(softPerf.getDateFrom()).getTime()));
		}
		if(softPerf.getDateTo()!=null){
			Buyer1Sql+=" and a.confirmDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(softPerf.getDateTo()).getTime()));
		}
		Buyer1Sql+=" group by d1.Code, d1.Desc2, d2.Code, d2.Desc2 ";
		
		String Buyer2Sql=" select d1.Code Department1, d1.Desc2 Dept1Descr, d2.Code Department2, d2.Desc2 depa2Descr," +
				" 0 designAmount,0 buyerAmount,sum(a.PerfAmount) buyer2Amount,0 designPer ," +
				" 0 buyerPer,sum(a.Buyer2Commi) buyer2Per,0 BusinessManAmount,0 businessPer from tSoftPerfCommi a " +
				" inner join tDepartment1 d1 on d1.Code=a.Buyer2Department1" +
				" left join tDepartment2 d2 on d2.Code=a.Buyer2Department2" +
				"  where 1=1 ";
		
		if(StringUtils.isNotBlank(softPerf.getNo())){
			Buyer2Sql+=" and a.No= ? ";
			list.add(softPerf.getNo());
		}
		if(StringUtils.isNotBlank(softPerf.getDepartment2())){
			Buyer2Sql += " and d2.code in " + "('"+softPerf.getDepartment2().replace(",", "','" )+ "')";
		}
		if(softPerf.getDateFrom()!=null){
			Buyer2Sql+=" and a.confirmDate >= ?";
			list.add(new Timestamp(DateUtil.startOfTheDay(softPerf.getDateFrom()).getTime()));
		}
		if(softPerf.getDateTo()!=null){
			Buyer2Sql+=" and a.confirmDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(softPerf.getDateTo()).getTime()));
		}
		Buyer2Sql+=" group by d1.Code, d1.Desc2, d2.Code, d2.Desc2 ";
		
		String businessmanSql=" select d1.Code Department1, d1.Desc2 Dept1Descr, d2.Code Department2, d2.Desc2 depa2Descr," +
				" 0 designAmount,0 buyerAmount,0 buyer2Amount,0 designPer ," +
				" 0 buyerPer,0 buyer2Per, " +
				" sum(a.PerfAmount) BusinessManAmount,sum(a.BusinessManCommi) businessPer from tSoftPerfCommi a " +
				" inner join tDepartment1 d1 on d1.Code=a.BusinessManDepartment1" +
				" left join tDepartment2 d2 on d2.Code=a.BusinessManDepartment2" +
				"  where 1=1 ";
		
		if(StringUtils.isNotBlank(softPerf.getNo())){
			businessmanSql+=" and a.No= ? ";
			list.add(softPerf.getNo());
		}
		if(StringUtils.isNotBlank(softPerf.getDepartment2())){
			businessmanSql += " and d2.code in " + "('"+softPerf.getDepartment2().replace(",", "','" )+ "')";
		}
		if(softPerf.getDateFrom()!=null){
			businessmanSql+=" and a.confirmDate >= ?";
			list.add(new Timestamp(DateUtil.startOfTheDay(softPerf.getDateFrom()).getTime()));
		}
		if(softPerf.getDateTo()!=null){
			businessmanSql+=" and a.confirmDate <= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(softPerf.getDateTo()).getTime()));
		}
		businessmanSql+=" group by d1.Code, d1.Desc2, d2.Code, d2.Desc2 ";
		
		String sql= " select  * from (select Department1, Dept1Descr, Department2, Depa2Descr," +
				" sum(designAmount) designAmount," +
				" sum(buyerAmount) buyerAmount,sum(buyer2Amount) buyer2Amount,sum(designPer) designPer,sum(buyerPer) buyerPer,sum(buyer2Per) buyer2Per " +
				",sum(designPer+buyerPer+buyer2Per+businessPer) allPer,sum(BusinessManAmount) BusinessManAmount," +
				" sum(businessPer) businessPer from ("
		          + DesignSql
		          + " union all "
		          + Buyer1Sql
		          + " union all "
		          + Buyer2Sql
		          + " union all "
		          + businessmanSql
		          + " ) a group by a.Department1, a.Dept1Descr, a.Department2, a.Depa2Descr ";
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findReportDetail_empPageBySql(Page<Map<String,Object>> page, SoftPerf softPerf) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  * from (select a.CustCode,c.descr custdescr,c.address,a.type ,x1.note typedescr,a.IANo,a.ItemCode,b.descr itemdescr," +
				" d.descr item12descr,e.descr item2descr,a.Qty,a.UnitPrice,a.BefLineAmount,a.Markup,a.ProcessCost,a.LineAmount,a.Cost,a.DiscCost," +
				" a.qty*a.Cost allCost,c.CheckOutDate,a.ConfirmDate,a.Per,a.ProfitPer,f.NameChi businessMan,g.NameChi designMan,i.NameChi buyerMan," +
				" h.desc2 designDept2Descr ,j.Desc2 buyerDept2Descr,a.DesignManCommiPer,a.DesignManCommi designPer,a.BuyerCommiPer," +
				" a.BuyerCommi buyerPer,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.perfamount , x2.note isClearInvDescr," +
				" a.buyer2,i2.NameChi buyer2Man,a.buyer2CommiPer,a.buyer2Commi buyer2Per,a.prodMgrCommiPer,a.prodMgrCommi, " +	//买手2、买手2提成点数、买手2提成、产品经理提成点数、产品经理提成 add by zb on 202020407
				" j2.Desc2 buyer2Dept2Descr " +
				" from tSoftPerfCommi  a" +
				" left join tcustomer c on c.Code=a.CustCode" +
				" left join txtdm x1 on x1.cbm =a.Type and x1.id='SOFTPERFTYPE'" +
				" left join titem b on b.Code=a.ItemCode" +
				" left join tItemType2 e on e.Code=b.itemtype2" +
				" left join tItemType12 d on d.Code=e.ItemType12" +
				" left join tEmployee f on f.Number=a.BusinessMan" +
				" left join tEmployee g on g.Number=a.DesignMan" +
				" left join tEmployee i on i.Number=a.Buyer" +
				" left join tEmployee i2 on i2.Number=a.Buyer2" +
				" left join tDepartment2 h on h.Code=a.DesignManDepartment2" +
				" left join tDepartment2 j on j.code=a.BuyerDepartment2 " +
				" left join tDepartment2 j2 on j2.code=a.Buyer2Department2 " +
				" left join tXtdm x2 on x2.cbm=b.isClearInv and x2.Id='YESNO' " +
				" where 1=1 " +
				"";
		if(StringUtils.isNotBlank(softPerf.getDesignMan())){
			sql+=" and a.designMan = ? ";
			list.add(softPerf.getDesignMan());
		}
		if(StringUtils.isNotBlank(softPerf.getBuyer())){
			sql+=" and a.Buyer = ? ";
			list.add(softPerf.getBuyer());
		}
		if(StringUtils.isNotBlank(softPerf.getBusinessMan())){
			sql+=" and a.businessMan = ? ";
			list.add(softPerf.getBusinessMan());
		}
		if(StringUtils.isNotBlank(softPerf.getNo())){
			sql+=" and a.no = ? ";
			list.add(softPerf.getNo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findReportDetail_teamPageBySql(Page<Map<String,Object>> page, SoftPerf softPerf) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select  * from (select a.CustCode,c.descr custdescr,c.address,a.type ,x1.note typedescr,a.IANo,a.ItemCode,b.descr itemdescr," +
				" d.descr item12descr,e.descr item2descr,a.Qty,a.UnitPrice,a.BefLineAmount,a.Markup,a.ProcessCost,a.LineAmount,a.Cost,a.DiscCost," +
				" a.qty*a.Cost allCost,c.CheckOutDate,a.ConfirmDate,a.Per,a.ProfitPer,f.NameChi businessMan,g.NameChi designMan,i.NameChi buyerMan," +
				" h.desc2 designDept2Descr ,j.Desc2 buyerDept2Descr,a.DesignManCommiPer,a.DesignManCommi designPer,a.BuyerCommiPer," +
				" a.BuyerCommi buyerPer,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog ,x2.note isClearInvDescr, " +
				" a.buyer2,i2.NameChi buyer2Man,a.buyer2CommiPer,a.buyer2Commi buyer2Per,a.prodMgrCommiPer,a.prodMgrCommi, " +	//买手2、买手2提成点数、买手2提成、产品经理提成点数、产品经理提成 add by zb on 202020407
				" j2.Desc2 buyer2Dept2Descr, a.PerfAmount " +
				" from tSoftPerfCommi  a" +
				" left join tcustomer c on c.Code=a.CustCode" +
				" left join txtdm x1 on x1.cbm =a.Type and x1.id='SOFTPERFTYPE'" +
				" left join titem b on b.Code=a.ItemCode" +
				" left join tItemType2 e on e.Code=b.itemtype2" +
				" left join tItemType12 d on d.Code=e.ItemType12" +
				" left join tEmployee f on f.Number=a.BusinessMan" +
				" left join tEmployee g on g.Number=a.DesignMan" +
				" left join tEmployee i on i.Number=a.Buyer" +
				" left join tEmployee i2 on i2.Number=a.Buyer2 " +
				" left join tDepartment2 h on h.Code=a.DesignManDepartment2" +
				" left join tDepartment2 j on j.code=a.BuyerDepartment2" +
				" left join tDepartment2 j2 on j2.code=a.Buyer2Department2" +
				" left join tXtdm x2 on x2.cbm=b.isClearInv and x2.Id='YESNO' " +
				" where 1=1 " +
				"";
		
		if (StringUtils.isNotBlank(softPerf.getTabName())) {
		    String tabName = softPerf.getTabName();
		    
            if (tabName.equals("DESIGNER")) {
                sql += " and a.DesignManDepartment2 = ? ";
                list.add(softPerf.getDepartment2());
            } else if (tabName.equals("BUYER")) {
                sql += " and (a.BuyerDepartment2 = ? or a.Buyer2Department2 = ?) ";
                list.add(softPerf.getDepartment2());
                list.add(softPerf.getDepartment2());
            } else if (tabName.equals("SALESMAN")) {
                sql += " and a.BusinessManDepartment2 = ? ";
                list.add(softPerf.getDepartment2());
            }
        }
		
		if(StringUtils.isNotBlank(softPerf.getNo())){
			sql+=" and a.no = ? ";
			list.add(softPerf.getNo());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	
	public String  isExistsPeriod(String no,String beginDate) {
		String sql = " select BeginDate from tSoftPerf where No= ?  and Status='1'";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			String sqlDate = " select 1 from tSoftPerf where BeginDate< ? and Status='1' ";
			List<Map<String,Object>> listDate = this.findBySql(sqlDate, new Object[]{list.get(0).get("BeginDate")});
			if(listDate != null && listDate.size() > 0){
				return "2";//之前的统计周期未计算完成，不允许计算本周期业绩!
			}else{
				return null;
			}
		}else {
			return "1";//没有找到相应的业绩统计周期或该统计周期已计算完成!
		}
	}
	
	public void doSaveCount(String no) {
		String sql = " update tSoftPerf set Status='2' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[]{no});
	}
	
	public void doSaveCountBack(String no) {
		String sql = " update tSoftPerf set Status='1' where No= ?  ";
		this.executeUpdateBySql(sql, new Object[]{no});
	}
	
	public void doGetSoftPerDetail(String no,String lastUpdatedBy) {
		String sql = " exec pCalcSoftPerfCommi ?,? ";
		this.executeUpdateBySql(sql, new Object[]{no,lastUpdatedBy});
	}
	
	public String  checkStatus(String no) {
		String sql = " select Status from tSoftPerf where No= ?  ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no});
		if(list != null && list.size() > 0){
			return list.get(0).get("Status").toString();
		}else {
			return null;
		}
	}
	
	public Map<String , Object>  getSoftPerfDetail(Integer pk) {
		String sql = "select a.againManCommi,a.againManCommiPer,a.businessManCommiPer,a.businessManCommi,a.pk,a.custCode,c.descr custdescr,c.address,a.type ,x1.note typeDescr,a.iaNo,a.itemCode,b.descr itemDescr," +
				" d.descr item12Descr,e.descr item2Descr,a.qty,a.unitPrice,a.befLineAmount,a.markup,a.processCost,a.lineAmount,a.cost,a.discCost," +
				" a.qty*a.Cost allCost,c.checkOutDate,a.confirmDate,a.per,a.profitPer,f.number businessMan,f.NameChi businessManDescr," +
				" g.number designMan,g.NameChi designManDescr,i.number buyer,i.NameChi buyerMan," +
				" h.desc2 dept2Design ,j.Desc2 dept2Buyer,a.designManCommiPer,a.designManCommi designPer,a.buyerCommiPer," +
				" a.buyerCommi buyerPer,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.costamount,a.perfamount,a.orderdate, " +
				" a.buyer2,i2.NameChi buyer2Man, j2.Desc2 dept2Buyer2,a.buyer2CommiPer,a.buyer2Commi buyer2Per,a.prodMgrCommiPer,a.prodMgrCommi,k.Remark itemReqRemark " +	//买手2、买手2提成点数、买手2提成、产品经理提成点数、产品经理提成 add by zb on 202020407
				" from tSoftPerfCommi  a" +
				" left join tcustomer c on c.Code=a.CustCode" +
				" left join txtdm x1 on x1.cbm =a.Type and x1.id='SOFTPERFTYPE'" +
				" left join titem b on b.Code=a.ItemCode" +
				" left join tItemType2 e on e.Code=b.itemtype2" +
				" left join tItemType12 d on d.Code=e.ItemType12" +
				" left join tEmployee f on f.Number=a.BusinessMan" +
				" left join tEmployee g on g.Number=a.DesignMan" +
				" left join tEmployee i on i.Number=a.Buyer" +
				" left join tEmployee i2 on i2.Number=a.Buyer2 " +
				" left join tDepartment2 h on h.Code=a.DesignManDepartment2" +
				" left join tDepartment2 j on j.code=a.BuyerDepartment2" +
				" left join tDepartment2 j2 on j2.code=a.Buyer2Department2" +
				" left join tItemReq k on k.Pk=a.ReqPK " +
				" where 1=1 and a.pk= ?  ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
		
	}
	
	public List<Map<String , Object>>  checkMonExists(String no, Integer mon) {
		String sql = " select 1 from tSoftPerf where (? = '' or no = ?) and mon = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{no, no, mon});
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	/**
	 * 获取周期
	 * 
	 * @param mon
	 * @return
	 */
	public String getSoftPerfNoByMon(Integer mon) {
		String sql = " select no from tSoftPerf where Mon= ?  ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {mon});
		if (list != null && list.size() > 0) {
			return list.get(0).get("no").toString();
		} else {
			return " ";
		}
	}
}
