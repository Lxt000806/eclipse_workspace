package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Roll;

@SuppressWarnings("serial")
@Repository
public class RollDao extends BaseDao {

	/**
	 * Roll分页信息
	 * 
	 * @param page
	 * @param roll
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Roll roll) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Code,a.Descr,a.ChildCode,b.Descr ChildCodeDescr,"
		          + " c1.Desc1 Department1Descr,a.Department1,c2.Desc1 Department2Descr,"
		          + " a.Department2,c3.Desc1 Department3Descr,a.Department3,"
		          + " case(a.IsSale)"
		          + " when '0' then '否'"
		          + " when '1' then '是' end IsSaleDescr,"
		          + " a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog"
		          + " from tRoll a  "
		          + " left outer join tRoll b on b.Code=a.ChildCode "
		          + " left outer join tDepartment1 c1 on c1.Code=a.Department1 "
		          + " left outer join tDepartment2 c2 on c2.Code=a.Department2 "
		          + " left outer join tDepartment3 c3 on c3.Code=a.Department3 "
		          + " where 1=1 ";

    	if (StringUtils.isNotBlank(roll.getCode())) {
			sql += " and a.Code=? ";
			list.add(roll.getCode());
		}
    	if (StringUtils.isNotBlank(roll.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+roll.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(roll.getChildCode())) {
			sql += " and a.ChildCode=? ";
			list.add(roll.getChildCode());
		}
    	if (roll.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(roll.getDateFrom());
		}
		if (roll.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(roll.getDateTo());
		}
    	if (StringUtils.isNotBlank(roll.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(roll.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(roll.getExpired()) || "F".equals(roll.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(roll.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(roll.getActionLog());
		}
    	if (StringUtils.isNotBlank(roll.getDepartment1())) {
			sql += " and a.Department1=? ";
			list.add(roll.getDepartment1());
		}
    	if (StringUtils.isNotBlank(roll.getDepartment2())) {
			sql += " and a.Department2=? ";
			list.add(roll.getDepartment2());
		}
    	if (StringUtils.isNotBlank(roll.getDepartment3())) {
			sql += " and a.Department3=? ";
			list.add(roll.getDepartment3());
		}
    	if (StringUtils.isNotBlank(roll.getIsSale())) {
			sql += " and a.IsSale=? ";
			list.add(roll.getIsSale());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getByCode(String code) {
		String sql = "select a.Code,a.Descr,a.ChildCode,b.Descr ChildCodeDescr,"
          +"c1.Desc1 Department1Descr,a.Department1,c2.Desc1 Department2Descr,"
          +"a.Department2,c3.Desc1 Department3Descr,a.Department3,a.expired, "
          +"case(a.IsSale) "
          +"when '0' then '否' "
          +"when '1' then '是' end as IsSale "
          +"from tRoll a "
          +"left join tRoll b on b.Code=a.ChildCode "
          +"left join tDepartment1 c1 on c1.Code=a.Department1 "
          +"left join tDepartment2 c2 on c2.Code=a.Department2 "
          +"left join tDepartment3 c3 on c3.Code=a.Department3 "
          +"where a.code=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

