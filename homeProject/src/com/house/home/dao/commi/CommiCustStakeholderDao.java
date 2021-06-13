package com.house.home.dao.commi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiCustStakeholder;
import com.house.home.entity.finance.PerfCycle;

@SuppressWarnings("serial")
@Repository
public class CommiCustStakeholderDao extends BaseDao {

	/**
	 * CommiCustStakeholder分页信息
	 * 
	 * @param page
	 * @param commiCustStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.CommiNo,a.CustCode,b.Address,a.EmpCode,a.PerfPK,a.PerfAmount, "
					+"a.WeightPer,a.CommiAmount,a.CheckPerfPK,a.CommiExprPK, "
					+"a.CommiExpr,a.CommiExprRemarks,a.CommiExprWithNum,a.CommiPer,a.CommiProvidePer, "
					+"a.SubsidyPer,a.SubsidyProvidePer,a.Multiple,a.MultipleProvidePer,a.ShouldProvideAmount,a.AdjustAmount, "
					+"a.RealProvideAmount,a.Remarks,a.TotalRealProvideAmount,a.SignCommiNo, "
					+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,c.NameChi EmpName, "
					+"d.Descr RoleDescr,e.NOTE TypeDescr,f.Mon CommiMon,g.Mon SignCommiMon, "
					+"h.Desc2 Department1Descr,i.Desc2 Department2Descr,f.Mon,a.RightCardinal,a.RightCommiPer "
					+"from tCommiCustStakeholder a "
					+"left join tCustomer b on a.CustCode = b.Code "
					+"left join tEmployee c on a.EmpCode = c.Number "
					+"left join tRoll d on a.Role = d.Code "
					+"left join tXTDM e on a.Type = e.CBM and e.ID = 'COMMICSTYPE' "
					+"left join tCommiCycle f on a.CommiNo = f.No "
					+"left join tCommiCycle g on a.SignCommiNo = g.No "
					+"left join tDepartment1 h on a.Department1 = h.Code "
					+"left join tDepartment2 i on a.Department2 = i.Code "
					+"where 1=1 ";

    	if (commiCustStakeholder.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(commiCustStakeholder.getPk());
		}
    	if (commiCustStakeholder.getMon() != null) {
			sql += " and f.Mon=? ";
			list.add(commiCustStakeholder.getMon());
		}
    	if (commiCustStakeholder.getBeginMon() != null) {
			sql += " and f.Mon>=? ";
			list.add(commiCustStakeholder.getBeginMon());
		}
    	if (commiCustStakeholder.getEndMon() != null) {
			sql += " and f.Mon<=? ";
			list.add(commiCustStakeholder.getEndMon());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getNo())) {
			sql += " and a.CommiNo=? ";
			list.add(commiCustStakeholder.getNo());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getCommiNo())) {
			sql += " and a.CommiNo=? ";
			list.add(commiCustStakeholder.getCommiNo());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(commiCustStakeholder.getCustCode());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+commiCustStakeholder.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(commiCustStakeholder.getEmpCode());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getType())) {
			sql += " and a.Type=? ";
			list.add(commiCustStakeholder.getType());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getRole())) {
			sql += " and a.Role=? ";
			list.add(commiCustStakeholder.getRole());
		}
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.CustCode,a.PK ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 更新tCommiCustStakeholderAdjust表调整金额
	 * 
	 * @param commiCustStakeholder
	 */
	public Long modifyAdjustAmount(CommiCustStakeholder commiCustStakeholder) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tCommiCustStakeholderAdjust set AdjustAmount = ?,AdjustRemarks = ?,LastUpdatedBy = ? "
				+"where CommiNo = ? and CustCode = ? and EmpCode = ? and Role = ? and Type = ?  ";
		list.add(commiCustStakeholder.getAdjustAmount());
		list.add(commiCustStakeholder.getAdjustRemarks());
		list.add(commiCustStakeholder.getLastUpdatedBy());
		list.add(commiCustStakeholder.getCommiNo());
		list.add(commiCustStakeholder.getCustCode());
		list.add(commiCustStakeholder.getEmpCode());
		list.add(commiCustStakeholder.getRole());
		list.add(commiCustStakeholder.getType());
		if(commiCustStakeholder.getPerfPk() == null){
			sql += "and PerfPK is null ";
		}else {
			sql += "and PerfPK = ? ";
			list.add(commiCustStakeholder.getPerfPk());
		}
		return this.executeUpdateBySql(sql, list.toArray());
	}
	
	/**
	 * 新增调整金额
	 * 
	 * @param commiCustStakeholder
	 */
	public void insertAdjustAmount(CommiCustStakeholder commiCustStakeholder) {
		String sql = "insert into tCommiCustStakeholderAdjust(CommiNo,CustCode,EmpCode,Role,Type,PerfPK,AdjustAmount," 
				+"AdjustRemarks,LastUpdate,LastUpdatedBy,Expired,ActionLog,SignCommiNo) "
				+"values(?,?,?,?,?,?,?,?,getdate(),?,'F','ADD',?)";
		this.executeUpdateBySql(sql, new Object[] {commiCustStakeholder.getCommiNo(),
				commiCustStakeholder.getCustCode(),commiCustStakeholder.getEmpCode(),
				commiCustStakeholder.getRole(),commiCustStakeholder.getType(),
				commiCustStakeholder.getPerfPk(),commiCustStakeholder.getAdjustAmount(),
				commiCustStakeholder.getAdjustRemarks(),commiCustStakeholder.getLastUpdatedBy(),
				commiCustStakeholder.getSignCommiNo()});
	}
	
	/**
	 * 更新tCommiCustStakeholder表调整金额
	 * 
	 * @param commiCustStakeholder
	 */
	public void updateAdjustAmount(CommiCustStakeholder commiCustStakeholder) {
		String sql = "update tCommiCustStakeholder set AdjustAmount=?," +
				"RealProvideAmount=ShouldProvideAmount+? where PK = ?";
		this.executeUpdateBySql(sql, new Object[] {
				commiCustStakeholder.getAdjustAmount(),
				commiCustStakeholder.getAdjustAmount(),commiCustStakeholder.getPk()});
	}
	
	/**
	 * CommiCustStakeholder分页信息
	 * 
	 * @param page
	 * @param commiCustStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> goDesignFeeJqGrid(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.CommiNo,a.CustCode,b.Address,a.EmpCode, "
					+"a.WeightPer,a.CommiAmount,a.CommiExprPK,  "
					+"a.CommiExpr,a.CommiExprRemarks,a.CommiExprWithNum,a.CommiPer,a.CommiProvidePer,  "
					+"a.ShouldProvideAmount,a.AdjustAmount,  "
					+"a.RealProvideAmount,a.Remarks,a.TotalRealProvideAmount,  "
					+"a.DesignFee,a.DrawFee,a.ColorDrawFee,a.StdDrawFee,a.StdColorDrawFee,"
					+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,c.NameChi EmpName,  "
					+"d.Descr RoleDescr,e.NOTE TypeDescr,f.Mon,d1.Desc2 Dept1Descr,d2.Desc2 Dept2Descr   "
					+"from tCommiCustStakeholder_DesignFee a  "
					+"left join tCustomer b on a.CustCode = b.Code  "
					+"left join tEmployee c on a.EmpCode = c.Number  "
					+"left join tRoll d on a.Role = d.Code  "
					+"left join tXTDM e on a.Type = e.CBM and e.ID = 'DESICOMMICSTYPE'  "
					+"left join tCommiCycle f on a.CommiNo = f.No  "
					+"left join tDepartment1 d1 on a.Department1 = d1.Code "
					+"left join tDepartment2 d2 on a.Department2 = d2.Code "
					+"where 1=1 ";

    	if (commiCustStakeholder.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(commiCustStakeholder.getPk());
		}
    	if (commiCustStakeholder.getMon() != null) {
			sql += " and f.Mon=? ";
			list.add(commiCustStakeholder.getMon());
		}
    	if (commiCustStakeholder.getBeginMon() != null) {
			sql += " and f.Mon>=? ";
			list.add(commiCustStakeholder.getBeginMon());
		}
    	if (commiCustStakeholder.getEndMon() != null) {
			sql += " and f.Mon<=? ";
			list.add(commiCustStakeholder.getEndMon());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getNo())) {
			sql += " and a.CommiNo=? ";
			list.add(commiCustStakeholder.getNo());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getCommiNo())) {
			sql += " and a.CommiNo=? ";
			list.add(commiCustStakeholder.getCommiNo());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(commiCustStakeholder.getCustCode());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+commiCustStakeholder.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(commiCustStakeholder.getEmpCode());
		}
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.CustCode,a.PK ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * CommiCustStakeholder分页信息
	 * 
	 * @param page
	 * @param commiCustStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> goHisJqGrid(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.CommiNo,a.CustCode,b.Address,a.EmpCode,a.PerfPK,a.PerfAmount, "
					+"a.WeightPer,a.CommiAmount,a.CheckPerfPK,a.CommiExprPK, "
					+"a.CommiExpr,a.CommiExprRemarks,a.CommiExprWithNum,a.CommiPer,a.CommiProvidePer, "
					+"a.SubsidyPer,a.SubsidyProvidePer,a.Multiple,a.MultipleProvidePer,a.ShouldProvideAmount,a.AdjustAmount, "
					+"a.RealProvideAmount,a.Remarks,a.TotalRealProvideAmount,a.SignCommiNo, "
					+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,c.NameChi EmpName, "
					+"d.Descr RoleDescr,e.NOTE TypeDescr,f.Mon CommiMon,g.Mon SignCommiMon,a.Role "
					+"from tCommiCustStakeholder a "
					+"left join tCustomer b on a.CustCode = b.Code "
					+"left join tEmployee c on a.EmpCode = c.Number "
					+"left join tRoll d on a.Role = d.Code "
					+"left join tXTDM e on a.Type = e.CBM and e.ID = 'COMMICSTYPE' "
					+"left join tCommiCycle f on a.CommiNo = f.No "
					+"left join tCommiCycle g on a.SignCommiNo = g.No "
					+"where f.Mon < ? and a.CustCode = ? ";
		list.add(commiCustStakeholder.getMon());
		list.add(commiCustStakeholder.getCustCode());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.CommiNo,a.Role ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * CommiCustStakeholder分页信息
	 * 
	 * @param page
	 * @param commiCustStakeholder
	 * @return
	 */
	public Page<Map<String,Object>> goBasePersonalJqGrid(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PK,a.CustCode,a.EmpCode,a.BusiType,a.BaseItemType1,a.ChgNo,a.WeightPer, "
					+"a.Amount,a.CommiPer,a.CommiAmount,a.CommiProvidePer,a.ShouldProvideAmount, "
					+"a.AdjustAmount,a.RealProvideAmount,a.TotalRealProvideAmount,a.LastUpdate, "
					+"a.LastUpdatedBy,a.Expired,a.ActionLog,b.Address,c.Descr,d.NameChi EmpName, "
					+"e.Mon,x1.NOTE BusiTypeDescr,c.Descr BaseItemType1Descr,x2.NOTE TypeDescr,x3.NOTE IsProvideDescr, "
					+"f.Mon SignCommiMon,g.Mon ReProvideMon,x4.NOTE IsFirstProvideDescr,a.PerfAmount,a.PerfPer, "
					+"d1.Desc2 Dept1Descr,d2.Desc2 Dept2Descr "
					+"from tCommiCustStakeholder_BasePersonal a  "
					+"left join tCustomer b on a.CustCode = b.Code "
					+"left join tBaseItemType1 c on a.BaseItemType1 = c.Code "
					+"left join tEmployee d on a.EmpCode = d.Number "
					+"left join tCommiCycle e on a.CommiNo = e.No "
					+"left join tCommiCycle f on a.SignCommiNo = f.No "
					+"left join tCommiCycle g on a.ReProvideNo = g.No "
					+"left join tXTDM x1 on a.BusiType = x1.CBM and x1.ID = 'COMMIBUSITYPE' "
					+"left join tXTDM x2 on a.Type = x2.CBM and x2.ID = 'COMMICSTYPE' "
					+"left join tXTDM x3 on a.IsProvide = x3.CBM and x3.ID = 'YESNO' "
					+"left join tXTDM x4 on a.IsFirstProvide = x4.CBM and x4.ID = 'YESNO' "
					+"left join tDepartment1 d1 on a.Department1 = d1.Code "
					+"left join tDepartment2 d2 on a.Department2 = d2.Code "
					+"where 1=1 ";

    	if (commiCustStakeholder.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(commiCustStakeholder.getPk());
		}
    	if (commiCustStakeholder.getMon() != null) {
			sql += " and e.Mon=? ";
			list.add(commiCustStakeholder.getMon());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getCommiNo())) {
			sql += " and a.CommiNo=? ";
			list.add(commiCustStakeholder.getCommiNo());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getNo())) {
			sql += " and a.CommiNo=? ";
			list.add(commiCustStakeholder.getNo());
		}
    	if (commiCustStakeholder.getBeginMon() != null) {
			sql += " and e.Mon>=? ";
			list.add(commiCustStakeholder.getBeginMon());
		}
    	if (commiCustStakeholder.getEndMon() != null) {
			sql += " and e.Mon<=? ";
			list.add(commiCustStakeholder.getEndMon());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(commiCustStakeholder.getCustCode());
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+commiCustStakeholder.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(commiCustStakeholder.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(commiCustStakeholder.getEmpCode());
		}
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.CustCode,a.PK ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 基础个性化提成干系人
	 * 
	 * @param page
	 * @param intBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goStakeholderJqGrid(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
				+"select b.NameChi EmpName,c.Descr RoleDescr,a.WeightPer,a.ProvidePer,d.Desc2 Department1Descr, "
				+"e.Desc2 Department2Descr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog  "
				+"from tBasePersonalStakeholder a  "
				+"left join tEmployee b on a.EmpCode = b.Number  "
				+"left join tRoll c on a.Role = c.Code  "
				+"left join tDepartment1 d on a.Department1 = d.Code " 
				+"left join tDepartment2 e on a.Department2 = e.Code "
				+"where CommiPK = ?";
		
		list.add(commiCustStakeholder.getPk());
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.EmpName";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
}

