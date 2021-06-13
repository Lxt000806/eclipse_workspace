package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.WorkType12Dept;

@SuppressWarnings("serial")
@Repository
public class WorkType12DeptDao extends BaseDao{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkType12Dept workType12Dept) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select a.*,b.descr workType12Descr from tWorkType12Dept a " +
				" left join tWorkType12 b on b.code=a.workType12 " +
				" where 1=1 ";
		if(StringUtils.isNotBlank(workType12Dept.getWorkType12())){
			sql += " and b.code in " + "('"+workType12Dept.getWorkType12().replace(",", "','" )+ "')";
		}
		if (StringUtils.isBlank(workType12Dept.getExpired()) || "F".equals(workType12Dept.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}	
	
	/**
	 * 判断编号是否存在
	 * @param code
	 * @return
	 */
	public boolean getIsExists(String code){
		String sql=" select  * from tWorkType12Dept where code = ? ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public boolean getIsExistsDescr(String descr,String workType12){
		String sql=" select * from tWorkType12Dept where descr= ? and workType12 = ? ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{descr,workType12});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public boolean getIsExistsDescrByCode(String code,String descr,String workType12){
		String sql=" select * from tWorkType12Dept where descr= ? and workType12 = ? and code <> ?";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{descr,workType12,code});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public Page<Map<String,Object>> getWorkType12DeptList(Page<Map<String,Object>> page, WorkType12Dept workType12Dept) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select Code,Descr from tWorkType12Dept a " +
				" where 1=1 and a.Expired = 'F' ";
		if(StringUtils.isNotBlank(workType12Dept.getWorkType12())){
			sql += " and a.workType12 in " + "('"+workType12Dept.getWorkType12().replace(",", "','" )+ "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " )a  ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}	
}
