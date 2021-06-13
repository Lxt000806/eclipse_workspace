package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BaseCheckItem;

@SuppressWarnings("serial")
@Repository
public class BaseCheckItemDao extends BaseDao {
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, BaseCheckItem baseCheckItem) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select *  from( select a.Code,a.Descr,a.BaseItemType1,a.BaseItemType2,a.WorkType12,a.OfferPri,a.Material,a.PrjOfferPri,a.PrjMaterial,"
				+ " a.Remark,a.Type,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,"
				+ " b.descr BaseItemType1Descr,c.descr BaseItemType2Descr,d.descr WorkType12Descr,e.descr uomdescr,f.note TypeDescr, "
				+ " a.IsFixItem,f2.NOTE IsFixItemDescr,a.DispSeq,dbo.fGetWorkerClassIfy(a.Code) workerClassIfyDescr,f3.NOTE IsSubsidyItemDescr "
				+ " from tBaseCheckItem a "
				+ " left join tBaseItemType1 b on a.BaseItemType1 = b.code "
				+ " left join tBaseItemType2 c on a.BaseItemType2 = c.code "
				+ " left join tWorkType12 d on a.WorkType12 = d.code "
				+ " left join tUom e on a.Uom=e.Code "
				+ " left join tXTDM f on a.type=f.cbm and f.id='BASECKITEMTYPE' "
				+ " left join tXTDM f2 on f2.ID='YESNO' and f2.CBM=a.IsFixItem "
				+ " left join tXTDM f3 on f3.ID='YESNO' and f3.CBM=a.IsSubsidyItem "
				+ " where 1=1 ";
		if (StringUtils.isNotBlank(baseCheckItem.getCode())) {
			sql += "and a.code like ?";
			list.add("%" + baseCheckItem.getCode() + "%");
		}
		if (StringUtils.isNotBlank(baseCheckItem.getDescr())) {
			sql += "and a.descr like ? ";
			list.add("%" + baseCheckItem.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(baseCheckItem.getBaseItemType1())) {
			sql += "and a.baseItemType1 = ? ";
			list.add(baseCheckItem.getBaseItemType1());
		}
		if (StringUtils.isNotBlank(baseCheckItem.getWorkType12())) {
			sql += "and a.WorkType12 = ? ";
			list.add(baseCheckItem.getWorkType12());
		}
		if (StringUtils.isNotBlank(baseCheckItem.getBaseItemType1())) {
			sql += "and a.baseItemType1 = ? ";
			list.add(baseCheckItem.getBaseItemType1());
		}
		if (StringUtils.isNotBlank(baseCheckItem.getRemark())) {
			sql += "and a.remark like ? ";
			list.add("%" + baseCheckItem.getRemark() + "%");
		}
		if (StringUtils.isBlank(baseCheckItem.getExpired())
				|| "F".equals(baseCheckItem.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by " + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: TODO 基础结算项目code查询
	 * @author created by zb
	 * @date 2018-9-20--下午5:39:02
	 * @param page
	 * @param baseCheckItem
	 * @return
	 */
	public Page<Map<String, Object>> findCodePageBySql(
			Page<Map<String, Object>> page, BaseCheckItem baseCheckItem) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.Code,a.Descr,a.BaseItemType1,b.Descr BaseItemType1Descr,a.BaseItemType2, "
				+ " c.Descr BaseItemType2Descr,a.WorkType12,d.Descr WorkType12Descr,tu.Descr Uom,a.OfferPri,a.Material, "
				+ " a.PrjOfferPri,a.PrjMaterial,a.Remark,a.Type,tx.NOTE TypeDescr,d.WorkType1,e.descr worktype1descr  "
				+ " from tBaseCheckItem a "
				+ " left join tBaseItemType1 b on b.Code=a.BaseItemType1 "
				+ " left join tBaseItemType2 c on c.Code = a.BaseItemType2 "
				+ " left join tWorkType12 d on d.Code = a.WorkType12 "
				+ " left join tWorkType1 e on e.Code=d.WorkType1 "
				+ " left join tXTDM tx on tx.CBM = a.Type and tx.ID = 'BASECKITEMTYPE' "
				+ " left join tUOM tu on tu.Code = a.Uom " 
				+ " where 1=1 ";

		if (StringUtils.isNotBlank(baseCheckItem.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%" + baseCheckItem.getCode() + "%");
		}
		if (StringUtils.isNotBlank(baseCheckItem.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + baseCheckItem.getDescr() + "%");
		}
		if (StringUtils.isNotBlank(baseCheckItem.getBaseItemType1())) {
			sql += " and a.BaseItemType1=? ";
			list.add(baseCheckItem.getBaseItemType1());
		}
		if (StringUtils.isNotBlank(baseCheckItem.getBaseItemType2())) {
			sql += " and a.BaseItemType2=? ";
			list.add(baseCheckItem.getBaseItemType2());
		}
		if(StringUtils.isNotBlank(baseCheckItem.getWorkType12())){
			sql +=" and a.workType12=?";
			list.add(baseCheckItem.getWorkType12());
		}
		if(StringUtils.isNotBlank(baseCheckItem.getCustCode())){
			sql+=" and exists(select 1 from tCustomer in_a" +
					" left join tCusttype in_b on in_b.code = in_a.CustType" +
					" left join tBaseCheckItemWorkerClassify in_c on in_c.WorkerClassify =  in_b.WorkerClassify" +
					" where in_a.Code= ? and in_c.BaseCheckItemCode=a.Code )" +
					" and a.isFixItem ='1'";
			list.add(baseCheckItem.getCustCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> getItemBaseType(
			Page<Map<String, Object>> page, BaseCheckItem baseCheckItem) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code,a.Descr,a.OfferPri,a.Material,a.PrjOfferPri,a.PrjMaterial,b.Descr Uom,a.Remark,d.WorkType1,e.descr worktype1descr, "
				+ "rtrim(a.BaseItemType1) BaseItemType1,rtrim(a.BaseItemType2) BaseItemType2,d.descr worktype12descr,a.worktype12,"
				+ "f.descr+' - '+d.Descr ordername,a.type,tx.note typeDescr,a.OfferPri TempOfferPri,a.Material TempMaterial,a.PrjOfferPri TempPrjOfferPri," 
				+ " a.PrjMaterial TempPrjMaterial,a.issubsidyitem,x1.note issubsidyitemdescr "
				+ "from tBaseCheckItem a "
				+ "left join tUom b on a.Uom=b.Code "
				+ "left join tWorkType12 d on a.WorkType12 = d.code "
				+ "left join tWorkType1 e on e.Code=d.WorkType1 "
				+ "left join tBaseItemType1 f ON f.Code=a.baseitemtype1 "
				+ "left join tXTDM tx on tx.CBM = a.Type and tx.ID = 'BASECKITEMTYPE' "
				+ "left join tXTDM X1 on X1.CBM = a.IsSubsidyItem and X1.ID='YESNO' "
				+ "where a.Expired='F' ";

		if (StringUtils.isNotBlank(baseCheckItem.getBaseItemType1())) {
			sql += "and BaseItemType1=? ";
			list.add(baseCheckItem.getBaseItemType1());
		}
		if (StringUtils.isNotBlank(baseCheckItem.getBaseItemType2())) {
			sql += "and BaseItemType2=? ";
			list.add(baseCheckItem.getBaseItemType2());
		}
		if (StringUtils.isNotBlank(baseCheckItem.getDescr())) {
			sql += " and a.descr like ?";
			list.add("%" + baseCheckItem.getDescr() + "%");
		}
		sql += "order by a.Descr desc";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public void doDeleteWorkClassify(BaseCheckItem baseCheckItem){
		String sql=" delete from tBaseCheckItemWorkerClassify where BaseCheckItemCode = ? ";
		this.executeUpdateBySql(sql,new Object[]{baseCheckItem.getCode()});
	}
	
	//获取对应的工人分类
	public String getWorkerClassify(String code){
		String result = "";
		String sql = " select WorkerClassify from tBaseCheckItemWorkerClassify where BaseCheckItemCode = ? "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{code});
		if(list != null && list.size() > 0){
			for(Map<String , Object> map : list){
				if(StringUtils.isNotBlank(result)){
					result+=","+map.get("WorkerClassify").toString();
				}else{
					result =map.get("WorkerClassify").toString();
				}
			}
		}
		return result;
	}
	
}
