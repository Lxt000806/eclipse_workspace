package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.WorkType2;

@SuppressWarnings("serial")
@Repository
public class WorkType2Dao extends BaseDao {

	/**
	 * @Description: TODO workType2_list分页查询
	 * @author	created by zb
	 * @date	2018-8-17--下午2:08:19
	 * @param page
	 * @param workType2
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkType2 workType2) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from(select a.Code,a.Descr,a.DispSeq,a.WorkType1,b.Descr WorkType1Descr,a.CalType,c.NOTE CalTypeDescr, " +
				" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, a.PrjItem,d.Note PrjItemDescr,a.IsCalProJectCost,a.SalaryCtrlType, " +
				" e.Note IsCalProJectCostDescr,a.Salary, a.Worktype12, f.Descr WorkType12Descr, a.IsConfirmTwo, xt.NOTE IsConfirmTwoDescr, " +
				" a.IsPrjApp, xt2.NOTE IsPrjAppDescr,a.DenyOfferWorkType2,twt2.Descr DenyOfferWorkType2Descr,xt3.NOTE SalaryCtrlTypeDescr" +
				" from tWorkType2 a " +
				" left outer join tWorkType1 b on LTrim(a.WorkType1)=LTrim(b.Code) " +
				" left join tWorkType12 f on ltrim(a.Worktype12) = ltrim(f.Code) " +
				" left outer join tXTDM c on a.CalType=c.IBM and c.ID='CalType' " +
				" left outer join tXTDM d on a.PrjItem=d.IBM and d.ID='PrjItem' " +
				" left outer join tXTDM e on a.IsCalProJectCost=e.IBM and e.ID='YESNO' " +
				" left join tXTDM xt on a.IsConfirmTwo=xt.IBM and xt.ID='YESNO' " +
				" left join tXTDM xt2 on a.IsPrjApp=xt2.IBM and xt2.ID='YESNO' " +
				" left join tXTDM xt3 on a.SalaryCtrlType=xt3.CBM and xt3.ID='SALACTRLTYPE' " +
				" left join tWorkType2 twt2 on a.DenyOfferWorkType2=twt2.Code " +
				" where 1=1 ";

    	if (StringUtils.isNotBlank(workType2.getCode())) {
			sql += " and RTrim(a.Code) = ? ";
			list.add(workType2.getCode());
		}
    	if (StringUtils.isNotBlank(workType2.getDescr())) {
			sql += " and RTrim(a.Descr) like ? ";
			list.add("%"+workType2.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(workType2.getWorkType1())) {
			sql += " and a.WorkType1=? ";
			list.add(workType2.getWorkType1());
		}
    	if (workType2.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(workType2.getDispSeq());
		}
    	if (StringUtils.isNotBlank(workType2.getCalType())) {
			sql += " and a.CalType=? ";
			list.add(workType2.getCalType());
		}
    	if (workType2.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(workType2.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(workType2.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(workType2.getLastUpdatedBy());
    	}
		if (StringUtils.isBlank(workType2.getExpired()) || "F".equals(workType2.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(workType2.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(workType2.getActionLog());
		}
    	if (StringUtils.isNotBlank(workType2.getPrjItem())) {
			sql += " and a.PrjItem=? ";
			list.add(workType2.getPrjItem());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.LastUpdate desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String,Object>> findWorkType2(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tWorkType2 a where a.expired='F' and CalType='2' "; //?
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.workType1=?";
			list.add((String)param.get("pCode"));
		}
		
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findWorkType2ForWorker(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tWorkType2 a where a.expired='F' ";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.workType1=?";
			list.add((String)param.get("pCode"));
		}
		
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findWorkType2All(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tWorkType2 a where a.expired='F'   ";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.workType1=?";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}

	public Page<Map<String,Object>> getPreWorkCostWorkType2(Page<Map<String, Object>> page, WorkType2 workType2){
		List<Object> params = new ArrayList<Object>();
		String sql = "select code,descr,a.calType from tWorkType2 a where ((a.expired='F' and a.isPrjApp = '1')  ";

		if(StringUtils.isNotBlank(workType2.getCode())){
			sql += " or a.Code = ?";
			params.add(workType2.getCode());
		}
		
		sql += ")";
		
    	if (StringUtils.isNotBlank(workType2.getWorkType1())) {
			sql += " and a.WorkType1=? ";
			params.add(workType2.getWorkType1());
		}
		return this.findPageBySql(page, sql, params.toArray());
	}
	/**
	 * 三级联动人工工种分类
	 */
	
	public List<Map<String,Object>> findOfferWorkType2(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tWorkType2 a where a.expired='F' and CalType=1 ";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.workType1=?";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}
}

