package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.ResrCustRight;

@SuppressWarnings("serial")
@Repository
public class ResrCustRightDao extends BaseDao {
	/**
	 * ResrCustRight分页信息
	 * @param page
	 * @param resrCustRight
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ResrCustRight resrCustRight) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from ( select a.pk,c.Desc1 Department1Descr,a.department2,b.Desc1 Department2Descr,a.BuilderCode,d.Descr BuilderDescr, "
					+" tx1.NOTE RightTypeDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
					+" from tResrCustRight a "
					+" left join tDepartment2 b on a.department2 = b.Code "
					+" left join tDepartment1 c on b.department1 = c.Code "
					+" left join tBuilder d on a.builderCode = d.Code "
					+" left join tXTDM tx1 on a.RightType = tx1.CBM and ID='CUSTRIGHTTYPE' "
					+" where 1=1 ";
		if(StringUtils.isNotBlank(resrCustRight.getDepartment1())){
			sql += " and b.department1=? ";
			list.add(resrCustRight.getDepartment1());
		}
		if(StringUtils.isNotBlank(resrCustRight.getDepartment2())){
			sql += " and b.Code=? ";
			list.add(resrCustRight.getDepartment2());
		}
		if(StringUtils.isNotBlank(resrCustRight.getBuilderCode())){
			sql += " and a.BuilderCode=? ";
			list.add(resrCustRight.getBuilderCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Map<String,Object> getByPk(Integer pk){
		String sql =  " select a.pk,c.Desc1 Department1Descr,a.department2,b.Desc1 Department2Descr,a.BuilderCode,d.Descr BuilderDescr, "
				+" tx1.NOTE RightTypeDescr,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
				+" from tResrCustRight a "
				+" left join tDepartment2 b on a.department2 = b.Code "
				+" left join tDepartment1 c on b.department1 = c.Code "
				+" left join tBuilder d on a.builderCode = d.Code "
				+" left join tXTDM tx1 on a.RightType = tx1.CBM and ID='CUSTRIGHTTYPE' "
				+" where a.pk=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public boolean getByBuildandDept(String builderCode,String department2){
		boolean result = false;
		List<Object> lists = new ArrayList<Object>();
		String sql =  " select * "
				+" from tResrCustRight "
				+" where 1=1 ";
		if(StringUtils.isNotBlank(department2)){
			sql += " and Department2=? ";
			lists.add(department2);
		}
		if(StringUtils.isNotBlank(builderCode)){
			sql += " and BuilderCode=? ";
			lists.add(builderCode);
		}
		List<Map<String,Object>> list = this.findBySql(sql, lists.toArray());
		if(list != null && list.size()>0){
			result = true;
		}
		return result;
	}
}
