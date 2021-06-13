package com.house.home.dao.commi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.SoftBusiCommi;

@SuppressWarnings("serial")
@Repository
public class SoftBusiCommiDao extends BaseDao {

	/**
	 * SoftBusiCommi分页信息
	 * 
	 * @param page
	 * @param softBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
		+"select a.PK,CommiNo,CustCode,b.Address,x1.NOTE TypeDescr,x2.NOTE BusiTypeDescr,c.NameChi EmpName,d.Desc2 DepartmentDescr, "
		+"a.ChgNo,a.ChgNo ItemChgNo,a.TotalAmount,a.ShouldProvidePer,a.ShouldProvideAmount,a.TotalProvideAmount,a.ThisAmount, "
		+"x4.NOTE IsCompleteDescr,x5.NOTE IsDesignSaleDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.OldCommiPK,a.EndCommiNo, "
		+"e.Desc1 CustTypeDescr,b.DocumentNo,b.SignDate,a.BusiType,f.Mon,a.SaleAmount,x6.NOTE IsFirstProvideDescr,a.PerfPer,a.PerfAmount, "
		+"d1.Desc2 Dept1Descr,d2.Desc2 Dept2Descr "
		+"from tSoftBusiCommi a  "
		+"left join tCustomer b on a.CustCode = b.Code "
		+"left join tEmployee c on a.EmpCode = c.Number "
		+"left join tDepartment d on a.Department = d.Code "
		+"left join tCustType e on b.CustType = e.Code "
		+"left join tCommiCycle f on a.CommiNo = f.No "
		+"left join tXTDM x1 on x1.ID = 'COMMIMAINTYPE' and a.Type = x1.CBM "
		+"left join tXTDM x2 on x2.ID = 'COMMIBUSITYPE' and a.BusiType = x2.CBM "
		+"left join tXTDM x4 on x4.ID = 'YESNO' and a.IsComplete = x4.CBM "
		+"left join tXTDM x5 on x5.ID = 'YESNO' and a.IsDesignSale = x5.CBM "
		+"left join tXTDM x6 on x6.ID = 'YESNO' and a.IsFirstProvide = x6.CBM "
		+"left join tDepartment1 d1 on a.Department1 = d1.Code "
		+"left join tDepartment2 d2 on a.Department2 = d2.Code "
		+"where a.Type <> '3' ";
		
    	if (softBusiCommi.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(softBusiCommi.getPk());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getCommiNo())) {
			sql += " and a.CommiNo=? ";
			list.add(softBusiCommi.getCommiNo());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getNo())) {
			sql += " and a.CommiNo=? ";
			list.add(softBusiCommi.getNo());
		}
    	if (softBusiCommi.getBeginMon() != null) {
			sql += " and f.Mon>=? ";
			list.add(softBusiCommi.getBeginMon());
		}
    	if (softBusiCommi.getEndMon() != null) {
			sql += " and f.Mon<=? ";
			list.add(softBusiCommi.getEndMon());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+softBusiCommi.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getType())) {
			sql += " and a.Type=? ";
			list.add(softBusiCommi.getType());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getBusiType())) {
			sql += " and a.BusiType=? ";
			list.add(softBusiCommi.getBusiType());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(softBusiCommi.getEmpCode());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getDepartment())) {
			sql += " and a.Department=? ";
			list.add(softBusiCommi.getDepartment());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Address";
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * SoftBusiCommi分页信息
	 * 
	 * @param page
	 * @param softBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goBaseJqGrid(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
			+"select x1.NOTE BusiTypeDescr,a.CustCode,a.ItemChgNo,c.Descr FixAreaDescr, "
			+"a.ItemCode,e.Descr ItemDescr,x2.NOTE IsOutSetDescr,x4.NOTE IsGiftDescr,a.Qty, "
			+"a.Price,a.ProcessCost,a.Markup,a.BefLineAmount,a.LineAmount,a.Cost,a.Amount, "
			+"a.Profit,a.ProfitPer*100 ProfitPer,a.CommiPer,a.CommiAmount,a.CrtMon,b.Address, "
			+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DiscCost "
			+"from tSoftBusiCommiItem a  "
			+"left join tCustomer b on a.CustCode = b.Code "
			+"left join tFixArea c on a.FixAreaPK = c.PK "
			+"left join tItem e on a.ItemCode = e.Code "
			+"left join tXTDM x1 on a.BusiType = x1.CBM and x1.ID = 'COMMIBUSITYPE' "
			+"left join tXTDM x2 on a.IsOutSet = x2.CBM and x2.ID = 'YESNO' "
			+"left join tXTDM x4 on a.IsGift = x4.CBM and x4.ID = 'YESNO' "
			+"where 1=1 ";
		
    	if (softBusiCommi.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(softBusiCommi.getPk());
		}
    	if (softBusiCommi.getCrtMon() != null) {
			sql += " and a.CrtMon=? ";
			list.add(softBusiCommi.getCrtMon());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getCommiNo())) {
			sql += " and a.ItemCommiNo=? ";
			list.add(softBusiCommi.getCommiNo());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getChgNo())) {
			sql += " and a.ItemChgNo=? ";
			list.add(softBusiCommi.getChgNo());
		}else if(StringUtils.isNotBlank(softBusiCommi.getIsInd())){
			sql += " and a.ItemCommiNo is not null ";
		}else {
			sql += " and a.ItemCommiNo is null ";
		}
    	
    	if (StringUtils.isNotBlank(softBusiCommi.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+softBusiCommi.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.CustCode";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * SoftBusiCommi分页信息
	 * 
	 * @param page
	 * @param softBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goSumJqGrid(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
			+"select CommiNo,CustCode,b.DocumentNo,b.Address,sum(ThisAmount)ThisAmount,e.Dept2Descr,c.Desc1 CustTypeDescr,d.NameChi EmpName "
			+"from tSoftBusiCommi a  "
			+"left join tCustomer b on a.CustCode = b.Code "
			+"left join tCusttype c on b.CustType = c.Code "
			+"left join tEmployee d on a.EmpCode = d.Number "
			+"left join vDepartment e on a.Department=e.Code "
			+"left join tCommiCycle f on a.CommiNo = f.No "
			+"where 1=1 ";

    	if (softBusiCommi.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(softBusiCommi.getPk());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getCommiNo())) {
			sql += " and a.CommiNo=? ";
			list.add(softBusiCommi.getCommiNo());
		}
    	if(StringUtils.isNotBlank(softBusiCommi.getIsInd())){
    		sql += " and a.Type='3' ";
    	}else {
    		sql += " and a.Type<>'3' ";
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(softBusiCommi.getEmpCode());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+softBusiCommi.getAddress()+"%");
		}
    	if (softBusiCommi.getCrtMon() != null) {
			sql += " and f.Mon=? ";
			list.add(softBusiCommi.getCrtMon());
		}
    	sql+="group by CommiNo,CustCode,b.DocumentNo,b.Address,e.Dept2Descr,c.Desc1,d.NameChi";
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.CustCode";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * SoftBusiCommi分页信息
	 * 
	 * @param page
	 * @param softBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goHisJqGrid(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
		+"select a.PK,CommiNo,CustCode,b.Address,x1.NOTE TypeDescr,x2.NOTE BusiTypeDescr,c.NameChi EmpName,d.Desc2 DepartmentDescr, "
		+"a.ChgNo,a.ChgNo ItemChgNo,a.TotalAmount,a.ShouldProvidePer,a.ShouldProvideAmount,a.TotalProvideAmount,a.ThisAmount, "
		+"x4.NOTE IsCompleteDescr,x5.NOTE IsDesignSaleDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.OldCommiPK,a.EndCommiNo, "
		+"e.Desc1 CustTypeDescr,b.DocumentNo,b.SignDate,f.Mon "
		+"from tSoftBusiCommi a  "
		+"left join tCustomer b on a.CustCode = b.Code "
		+"left join tEmployee c on a.EmpCode = c.Number "
		+"left join tDepartment d on a.Department = d.Code "
		+"left join tCustType e on b.CustType = e.Code "
		+"left join tCommiCycle f on a.CommiNo = f.No "
		+"left join tXTDM x1 on x1.ID = 'COMMIMAINTYPE' and a.Type = x1.CBM "
		+"left join tXTDM x2 on x2.ID = 'COMMIBUSITYPE' and a.BusiType = x2.CBM "
		+"left join tXTDM x4 on x4.ID = 'YESNO' and a.IsComplete = x4.CBM "
		+"left join tXTDM x5 on x5.ID = 'YESNO' and a.IsDesignSale = x5.CBM "
		+"where a.CustCode = ? ";
		
		list.add(softBusiCommi.getCustCode());
		if(StringUtils.isNotBlank(softBusiCommi.getCommiNo())){
			sql += "and a.CommiNo < ?";
			list.add(softBusiCommi.getCommiNo());
		}
		if(softBusiCommi.getCrtMon() != null){
			sql += "and f.Mon < ?";
			list.add(softBusiCommi.getCrtMon());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Address";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * SoftBusiCommi分页信息
	 * 
	 * @param page
	 * @param softBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goIndJqGrid(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
		+"select a.PK,CommiNo,CustCode,b.Address,x1.NOTE TypeDescr,x2.NOTE BusiTypeDescr,c.NameChi EmpName,d.Desc2 DepartmentDescr, "
		+"a.ChgNo,a.ChgNo ItemChgNo,a.TotalAmount,a.ShouldProvidePer,a.ShouldProvideAmount,a.TotalProvideAmount,a.ThisAmount, "
		+"x4.NOTE IsCompleteDescr,x5.NOTE IsDesignSaleDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.OldCommiPK,a.EndCommiNo, "
		+"e.Desc1 CustTypeDescr,b.DocumentNo,b.SignDate,a.BusiType,a.ConfirmDate,a.CommiDate,a.SaleAmount,x6.NOTE IsFirstProvideDescr, "
		+"a.PerfPer,a.PerfAmount,a.MainPlan,a.MainServPlan,a.SoftPlan,a.IntegratePlan,a.CupboardPlan,a.Tax,a.BaseDisc,a.ContractFee,"
		+"x3.NOTE IsCalcPerfDescr,d1.Desc2 Dept1Descr,d2.Desc2 Dept2Descr  "
		+"from tSoftBusiCommi a  "
		+"left join tCustomer b on a.CustCode = b.Code "
		+"left join tCustomer f on b.OldCode = f.Code "
		+"left join tEmployee c on a.EmpCode = c.Number "
		+"left join tDepartment d on a.Department = d.Code "
		+"left join tCustType e on b.CustType = e.Code "
		+"left join tXTDM x1 on x1.ID = 'COMMIMAINTYPE' and a.Type = x1.CBM "
		+"left join tXTDM x2 on x2.ID = 'COMMIBUSITYPE' and a.BusiType = x2.CBM "
		+"left join tXTDM x3 on x3.ID = 'YESNO' and a.IsCalcPerf = x3.CBM "
		+"left join tXTDM x4 on x4.ID = 'YESNO' and a.IsComplete = x4.CBM "
		+"left join tXTDM x5 on x5.ID = 'YESNO' and a.IsDesignSale = x5.CBM "
		+"left join tXTDM x6 on x6.ID = 'YESNO' and a.IsFirstProvide = x6.CBM "
		+"left join tDepartment1 d1 on a.Department1 = d1.Code "
		+"left join tDepartment2 d2 on a.Department2 = d2.Code "
		+"inner join tItemCommiCycle icc on a.CommiNo = icc.No "
		+"where a.Type = '3' ";
		
		if (StringUtils.isNotBlank(softBusiCommi.getCommiNo())) {
			sql += " and a.CommiNo=? ";
			list.add(softBusiCommi.getCommiNo());
		}
		if (softBusiCommi.getBeginMon() != null) {
			sql += " and icc.Mon>=? ";
			list.add(softBusiCommi.getBeginMon());
		}
    	if (softBusiCommi.getEndMon() != null) {
			sql += " and icc.Mon<=? ";
			list.add(softBusiCommi.getEndMon());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getCustCode())) {
			sql += " and f.Code=? ";
			list.add(softBusiCommi.getCustCode());
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+softBusiCommi.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(softBusiCommi.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(softBusiCommi.getEmpCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Address";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 干系人
	 * 
	 * @param page
	 * @param intBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goStakeholderJqGrid(Page<Map<String,Object>> page, SoftBusiCommi softBusiCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
				+"select b.NameChi EmpName,c.Descr RoleDescr,a.WeightPer,a.ProvidePer,d.Desc2 Department1Descr, "
				+"e.Desc2 Department2Descr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,  "
				+"x1.NOTE IsCalcDeptPerfDescr,x2.NOTE IsCalcPersonPerfDescr "
				+"from tSoftBusiStakeholder a  "
				+"left join tEmployee b on a.EmpCode = b.Number  "
				+"left join tRoll c on a.Role = c.Code  "
				+"left join tDepartment1 d on a.Department1 = d.Code " 
				+"left join tDepartment2 e on a.Department2 = e.Code "
				+"left join tXTDM x1 on x1.ID = 'YESNO' and a.IsCalcDeptPerf = x1.CBM "
				+"left join tXTDM x2 on x2.ID = 'YESNO' and a.IsCalcPersonPerf = x2.CBM "
				+"where ChgNo = ?";
		
		list.add(softBusiCommi.getChgNo());
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.EmpName";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
}

