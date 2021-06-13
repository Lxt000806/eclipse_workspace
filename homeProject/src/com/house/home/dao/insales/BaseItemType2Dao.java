package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.BaseItemType2;

@SuppressWarnings("serial")
@Repository
public class BaseItemType2Dao extends BaseDao {

	/**
	 * BaseItemType2分页信息
	 * 
	 * @param page
	 * @param baseItemType2
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemType2 baseItemType2) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select a.Code,a.Descr,a.DispSeq,a.BaseItemType1,c1.Code Code1,c1.Descr WorkType1Descr,c4.Code Code2,c4.Descr WorkType1Descr1,b.Descr BaseItemType1Descr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,c2.Code Code3,"
		           + "c2.Descr OfferWorkType2Descr,c3.Code Code4,c3.Descr MaterWorkType2Descr "
		           + "from tBaseItemType2 a left outer join tBaseItemType1 b on LTrim(a.BaseItemType1)=LTrim(b.Code)"
		           + "left outer join tWorkType2 c2 on LTrim(a.OfferWorkType2)=LTrim(c2.Code) "
		           + "left outer join tWorkType2 c3 on LTrim(a.MaterWorkType2)=LTrim(c3.Code) "
		           + "left outer join tWorkType1 c1 on LTrim(c2.WorkType1)=LTrim(c1.Code)"
		           + "left outer join tWorkType1 c4 on LTrim(c3.WorkType1)=LTrim(c4.Code)"
		           + "where 1=1 ";
    	if (StringUtils.isNotBlank(baseItemType2.getCode())) {
			sql += " and a.Code=? ";
			list.add(baseItemType2.getCode());
		}
    	if (StringUtils.isNotBlank(baseItemType2.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+baseItemType2.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(baseItemType2.getBaseItemType1())) {
			sql += " and a.BaseItemType1=? ";
			list.add(baseItemType2.getBaseItemType1());
		}
    	if (baseItemType2.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(baseItemType2.getDispSeq());
		}
    	if (baseItemType2.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(baseItemType2.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(baseItemType2.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(baseItemType2.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(baseItemType2.getExpired()) || "F".equals(baseItemType2.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(baseItemType2.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(baseItemType2.getActionLog());
		}
    	if (StringUtils.isNotBlank(baseItemType2.getOfferWorkType2())) {
			sql += " and a.OfferWorkType2=? ";
			list.add(baseItemType2.getOfferWorkType2());
		}
    	if (StringUtils.isNotBlank(baseItemType2.getMaterWorkType2())) {
			sql += " and a.MaterWorkType2=? ";
			list.add(baseItemType2.getMaterWorkType2());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.lastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> findBaseItemType2(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tBaseItemType2 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.baseItemType1=?";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}
	public boolean validBaseItemCode(String code){
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(code)) {
			String sql="select * from tbaseItemType2 a where a.code=? ";
			System.out.println(code+"/n"+"s");
			params.add(code);
			List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
			if (list !=null && list.size()>0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 根据类型1的code获取类型2_PrjAPP
	 * @author	created by zb
	 * @date	2019-3-4--下午4:43:24
	 * @param page
	 * @param code
	 * @return
	 */
	public Page<Map<String, Object>> getBaseItemType2(
			Page<Map<String, Object>> page, String code) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select rtrim(b.Code) Code2, b.Descr Code2Descr " +
					"from tBaseItemType2 b " +
					"where b.Expired='F' ";
		if (StringUtils.isNotBlank(code)){
			sql += "and b.BaseItemType1=? ";
			list.add(code);
		}
		sql +=		"order by b.DispSeq ";
		return this.findPageBySql(page, sql, list.toArray());
	}

}

