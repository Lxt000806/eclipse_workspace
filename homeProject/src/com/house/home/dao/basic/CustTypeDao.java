package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.basic.CustType;

@SuppressWarnings("serial")
@Repository
public class CustTypeDao extends BaseDao {

	/**
	 * CustType分页信息
	 * 
	 * @param page
	 * @param custType
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustType custType) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Code, a.Desc1, a.Type, a.AreaPer, a.BaseFee_CompPer, a.BaseFee_DirctPer, "
				+ "a.MainSetFeePer, a.SetMinusPer, a.SetAddPer, a.LongFeePer, a.ManageFee_BasePer,  "
				+ "a.MainFeePer, a.MainDiscPer, a.ManageFee_MainPer, a.MainServFeePer,  "
				+ "a.ManageFee_ServPer, a.SoftFeePer, a.SoftDiscPer, a.ManageFee_SoftPer,  "
				+ "a.IntegrateFeePer, a.IntegrateDiscPer,a.ManageFee_IntPer, a.CupboardFeePer, "
				+ "a.CupBoardDiscPer, a.ManageFee_CupPer, a.ChgManageFeePer, a.IsDefaultStatic,  "
				+ "a.Expired, a.ConfirmItem, a.IsAddAllInfo, a.CtrlExpr, a.IsCalcPerf, a.FirstPayPer,  "
				+ "x1.note TypeDescr,x2.note IsDefaultStaticDescr,x3.note ConfirmItemDescr,x4.note IsAddAllInfoDescr, "
				+ "x5.note IsCalcPerfDescr,a.SetCtrlExpr,a.setContainInt,x6.note setContainIntdescr,  "
				+ "a.MaterialExpr,a.IsSetMainCtrl,x7.note IsSetMainCtrlDescr,a.InnerAreaPer, "
				+ "a.cmpnyName,a.LogoFile,a.CmpnyFullName,a.CmpnyAddress,BasePerfPer, "
				+ "a.IsCalcBaseDisc,x8.Note IsCalcBaseDiscDescr,a.MustImportTemp, x9.Note MustImportTempDescr, " 
				+ "a.PerfExpr, a.PerfExprRemarks, a.WaterCtrlPri, "//增加维护两个字段：业绩公式、业绩公式说明;增加维护字段防水补贴; --add by zb
				+ "a.IntSaleAmount_Set,a.CupSaleAmount_Set,a.TaxExpr, "//增加不同产品线，衣柜和橱柜套内销售金额的维护:集成套餐内销售额,橱柜套餐内销售额 --add by gdf"
				+ "a.CtrlExprRemarks,a.dispSeq,a.KitchenStdArea,a.ToiletStdArea,a.OverAreaSubsidyPer, " //增加维护字段发包公式说明、厨房（发包）标准面积、卫生间（发包）标准面积、厨卫超面积每平米补贴 add by zb
				+ "a.ItemRemark,a.PricRemark ,x10.note BaseSpecDescr " 
				+ "from tCustType a "
				+ "left outer join txtdm x1 on x1.cbm=a.Type and x1.id='CUSTTYPESORT' "
				+ "left outer join txtdm x2 on x2.cbm=a.IsDefaultStatic and x2.id='YESNO' "
				+ "left outer join txtdm x3 on x3.cbm=a.ConfirmItem and x3.id='YESNO' "
				+ "left outer join txtdm x4 on x4.cbm=a.IsAddAllInfo and x4.id='YESNO' "
				+ "left outer join txtdm x5 on x5.cbm=a.IsCalcPerf and x5.id='YESNO' "
				+ "left outer join txtdm x6 on x6.cbm=a.setContainInt and x6.id='YESNO' "
				+ "left outer join txtdm x7 on x7.cbm=a.IsSetMainCtrl and x7.id='YESNO' "
				+ "left outer join txtdm x8 on x8.cbm=a.IsCalcBaseDisc and x8.id='YESNO' "
				+ "left outer join txtdm x9 on x9.cbm=a.MustImportTemp and x9.id='YESNO' "
				+ "left outer join txtdm x10 on x10.cbm=a.BaseSpec and x10.id='YESNO' "
				+ "where 1=1 ";
		if (StringUtils.isNotBlank(custType.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+custType.getCode()+"%");
		}
		if (StringUtils.isNotBlank(custType.getDesc1())) {
			sql += " and a.Desc1 like ? ";
			list.add("%"+custType.getDesc1()+"%");
		}
		if (StringUtils.isNotBlank(custType.getType())) {
			sql += " and a.Type=? ";
			list.add(custType.getType());
		}
		if (StringUtils.isBlank(custType.getExpired())
				|| "F".equals(custType.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.dispSeq ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public List<CustType> findByDefaultStatic() {
		String hql = "from CustType a where a.isDefaultStatic='1' ";
		return this.find(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CustType> findByIsAddAllInfo() {
		String hql = "from CustType a where a.isAddAllInfo='1' ";
		return this.find(hql);
	}
	
	public boolean isFdlCust(String custType) {
		String sql =" select 1 from tCusttype where IsAddAllInfo='0' and code= ?  ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] { custType});
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 检查编号是否已存在
	 * @param custType
	 * @return
	 */
	public List<Map<String, Object>> checkExist(CustType custType) {
		String sql = "";
		if("A".equals(custType.getM_umState()) || "C".equals(custType.getM_umState())){
			sql = "select * from tCustType a where a.code=? or desc1=? ";
		}else{	
			sql = "select * from tCustType a where a.code<>? and desc1=? ";
		}
		return findBySql(sql, new Object[]{custType.getCode(), custType.getDesc1()});
	}
	
	@SuppressWarnings("unchecked")
	public List<CustType> findByAllCustType() {
		String hql = "from CustType a order by dispSeq ";
		return this.find(hql);
	}
	/**
	 * 根据条件获得code
	 * @author	created by zb
	 * @date	2019-4-20--下午3:12:00
	 * @param custType
	 * @return
	 */
	public List<Map<String, Object>> getNeedCode(CustType custType) {
		List<Object> params = new ArrayList<Object>();
		String sql = "select Code from tCusttype where 1=1 ";
		if (StringUtils.isNotBlank(custType.getType())) {
			String type = SqlUtil.resetStatus(custType.getType());
			sql += "and type in ("+type+") ";
		}
		if (StringUtils.isNotBlank(custType.getIsAddAllInfo())) {
			sql += "and isAddAllInfo = ? ";
			params.add(custType.getIsAddAllInfo());
		}
		if (StringUtils.isNotBlank(custType.getIsPartDecorate())) {
			sql += "and isPartDecorate in ('" + custType.getIsPartDecorate().replace(",", "', '") + "')";
		}
		
		return this.findBySql(sql, params.toArray());
	}
	
	public String getSelectCustType(CustType custType){
		String value = "";
		String sql =" select Code from tCustType where type = ? and IsPartDecorate = ?  ";
		List<Map<String, Object>> list = this.findBySql(sql, custType.getType(),custType.getIsPartDecorate());
		if (list != null && list.size() > 0) {
			for(int i= 0 ; i<list.size();i++){
				if(value==""){
					value += list.get(i).get("Code").toString();
				}else {
					value += ","+list.get(i).get("Code").toString();
				}
			}
			return value;
		}
		return "";
	}

}
