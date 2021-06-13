package com.house.home.dao.commi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiCustStakeholder;
import com.house.home.entity.commi.MainBusiCommi;

@SuppressWarnings("serial")
@Repository
public class CustCommiProvideAnalysisDao extends BaseDao {

	/**
	 * CustCommiProvideAnalysis分页信息
	 * 
	 * @param page
	 * @param mainBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustStakeholder commiCustStakeholder) {
		List<Object> list = new ArrayList<Object>();
		String sql = "  exec pCustCommiProvideAnalysis ?,?,? ";
		list.add(commiCustStakeholder.getAddress());
		list.add(commiCustStakeholder.getMon());
		list.add(commiCustStakeholder.getType());
		page.setResult(findListBySql(sql, list.toArray()));
		page.setTotalCount(page.getResult().size());
		return page;
	}
	
	/**
	 * MainBusiCommi分页信息
	 * 
	 * @param page
	 * @param mainBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goBaseJqGrid(Page<Map<String,Object>> page, MainBusiCommi mainBusiCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
			+"select x1.NOTE BusiTypeDescr,a.CustCode,a.ItemChgNo,d.Descr RegionDescr, "
			+"a.ItemCode,e.Descr ItemDescr,x2.NOTE IsOutSetDescr,x3.NOTE IsServiceDescr,x4.NOTE IsGiftDescr,a.Qty, "
			+"a.Price,a.ProcessCost,a.Markup,a.BefLineAmount,a.LineAmount,a.Cost,a.Amount, "
			+"a.Profit,a.ProfitPer*100 ProfitPer,a.CommiPer,a.CommiAmount,a.CrtMon,b.Address, "
			+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
			+"from tMainBusiCommiItem a  "
			+"left join tCustomer b on a.CustCode = b.Code "
			+"left join tBuilder c on b.BuilderCode = c.Code "
			+"left join tRegion d on c.RegionCode = d.Code "
			+"left join tItem e on a.ItemCode = e.Code "
			+"left join tXTDM x1 on a.BusiType = x1.CBM and x1.ID = 'COMMIBUSITYPE' "
			+"left join tXTDM x2 on a.IsOutSet = x2.CBM and x2.ID = 'YESNO' "
			+"left join tXTDM x3 on a.IsService = x3.CBM and x3.ID = 'YESNO' "
			+"left join tXTDM x4 on a.IsGift = x4.CBM and x4.ID = 'YESNO' "
			+"where 1=1 ";

    	if (mainBusiCommi.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(mainBusiCommi.getPk());
		}
    	if (StringUtils.isNotBlank(mainBusiCommi.getCommiNo())) {
			sql += " and a.CommiNo=? ";
			list.add(mainBusiCommi.getCommiNo());
		}
    	if (StringUtils.isNotBlank(mainBusiCommi.getChgNo())) {
			sql += " and a.ItemChgNo=? ";
			list.add(mainBusiCommi.getChgNo());
		}
    	if (StringUtils.isNotBlank(mainBusiCommi.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+mainBusiCommi.getAddress()+"%");
		}
    	if (mainBusiCommi.getCrtMon() != null) {
			sql += " and a.CrtMon=? ";
			list.add(mainBusiCommi.getCrtMon());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.CustCode";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * MainBusiCommi分页信息
	 * 
	 * @param page
	 * @param mainBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goSumJqGrid(Page<Map<String,Object>> page, MainBusiCommi mainBusiCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
			+"select CommiNo,CustCode,b.DocumentNo,b.Address,sum(ThisAmount)ThisAmount,e.Dept2Descr,c.Desc1 CustTypeDescr,d.NameChi EmpName "
			+"from tMainBusiCommi a  "
			+"left join tCustomer b on a.CustCode = b.Code "
			+"left join tCusttype c on b.CustType = c.Code "
			+"left join tEmployee d on a.EmpCode = d.Number "
			+"left join vDepartment e on a.Department=e.Code "
			+"left join tCommiCycle f on a.CommiNo = f.No "
			+"where 1=1 ";

    	if (mainBusiCommi.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(mainBusiCommi.getPk());
		}
    	if (StringUtils.isNotBlank(mainBusiCommi.getCommiNo())) {
			sql += " and a.CommiNo=? ";
			list.add(mainBusiCommi.getCommiNo());
		}
    	if (StringUtils.isNotBlank(mainBusiCommi.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(mainBusiCommi.getEmpCode());
		}
    	if (StringUtils.isNotBlank(mainBusiCommi.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+mainBusiCommi.getAddress()+"%");
		}
    	if (mainBusiCommi.getCrtMon() != null) {
			sql += " and f.Mon=? ";
			list.add(mainBusiCommi.getCrtMon());
		}
    	sql+="group by CommiNo,CustCode,b.DocumentNo,b.Address,e.Dept2Descr,c.Desc1,d.NameChi";
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.CustCode";
		}
		System.out.println(sql);
		System.out.println(list);
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * MainBusiCommi分页信息
	 * 
	 * @param page
	 * @param mainBusiCommi
	 * @return
	 */
	public Page<Map<String,Object>> goHisJqGrid(Page<Map<String,Object>> page, MainBusiCommi mainBusiCommi) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
		+"select a.PK,CommiNo,CustCode,b.Address,x1.NOTE TypeDescr,x2.NOTE BusiTypeDescr,c.NameChi EmpName,d.Desc2 DepartmentDescr, "
		+"a.ChgNo,a.ChgNo ItemChgNo,x3.NOTE IsServiceDescr,a.TotalAmount,a.ShouldProvidePer,a.ShouldProvideAmount,a.TotalProvideAmount,a.ThisAmount, "
		+"x4.NOTE IsCompleteDescr,x5.NOTE IsDesignSaleDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.OldCommiPK,a.EndCommiNo, "
		+"e.Desc1 CustTypeDescr,b.DocumentNo,b.SignDate,f.Mon "
		+"from tMainBusiCommi a  "
		+"left join tCustomer b on a.CustCode = b.Code "
		+"left join tEmployee c on a.EmpCode = c.Number "
		+"left join tDepartment d on a.Department = d.Code "
		+"left join tCustType e on b.CustType = e.Code "
		+"left join tCommiCycle f on a.CommiNo = f.No "
		+"left join tXTDM x1 on x1.ID = 'COMMIMAINTYPE' and a.Type = x1.CBM "
		+"left join tXTDM x2 on x2.ID = 'COMMIBUSITYPE' and a.BusiType = x2.CBM "
		+"left join tXTDM x3 on x3.ID = 'YESNO' and a.IsService = x3.CBM "
		+"left join tXTDM x4 on x4.ID = 'YESNO' and a.IsComplete = x4.CBM "
		+"left join tXTDM x5 on x5.ID = 'YESNO' and a.IsDesignSale = x5.CBM "
		+"where a.CustCode = ? and f.Mon < ? ";
		list.add(mainBusiCommi.getCustCode());
		list.add(mainBusiCommi.getCrtMon());
		//list.add(mainBusiCommi.getBusiType());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Address";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
}

