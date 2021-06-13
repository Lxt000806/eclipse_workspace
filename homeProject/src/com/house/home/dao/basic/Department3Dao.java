package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Department3;

@SuppressWarnings("serial")
@Repository
public class Department3Dao extends BaseDao {

	/**
	 * 三级部门分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Department3 department3) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.Code,a.Desc1,a.Desc2,a.Department2,d.Desc2 DeptDescr2,a.DepType,b.note bDepType,a.PlanNum,d.Department1, "
         		+" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, c.NOTE IsOutChannelDescr "
         		+" from tDepartment3 a " 
         		+" left outer join tDepartment2 d on a.Department2 = d.Code "
         		+" left outer join tXTDM b ON b.cbm=a.DepType AND  b.ID='DEPTYPE' "
         		+" left outer join tXTDM c ON c.cbm=a.IsOutChannel AND c.ID='YESNO' "
         		+" where 1 = 1 ";

		if (StringUtils.isNotBlank(department3.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+department3.getCode()+"%");
		}
		if (StringUtils.isNotBlank(department3.getDesc1())) {
			sql += " and a.Desc1 like ? ";
			list.add("%"+department3.getDesc1()+"%");
		}
		
		if (StringUtils.isNotBlank(department3.getDesc2())) {
		    sql += " and a.Desc2 like ? ";
		    list.add("%" + department3.getDesc2() + "%");
		}
		
		if(StringUtils.isNotBlank(department3.getDepartment1())){
			sql +=" and a.Department2 in (select Code from tDepartment2 ss where ss.Department1  = ?)";
			list.add(department3.getDepartment1());
		}
		if(StringUtils.isNotBlank(department3.getDepartment2())){
			sql +="  and a.Department2= ?";
			list.add(department3.getDepartment2());
		}
		if(StringUtils.isNotBlank(department3.getDepType())){
			sql +="   and a.DepType= ?";
			list.add(department3.getDepType());
		}
		if (StringUtils.isBlank(department3.getExpired())
				|| "F".equals(department3.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("unchecked")
	public Department3 getByDesc2(String desc2) {
		String hql = "from Department3 a where a.desc2=? ";
		List<Department3> list = this.find(hql, new Object[]{desc2});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Department3> getByDepartment2(String code) {
		String hql = "from Department3 a where a.department2=? and a.expired='F' ";
		List<Department3> list = this.find(hql, new Object[]{code});
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Department3> findByNoExpired() {
		String hql = "from Department3 a where a.expired='F' order by a.code ";
		return this.find(hql);
	}

	public List<Map<String, Object>> findDepType3(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,desc1 name from tDepartment3 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.Department2=?";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.code";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String, Object>> findDep3All(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,desc1 name from tDepartment3 a where 1=1 ";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.Department2=?";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.code";
		return this.findBySql(sql, list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public Department3 getByCode(String code) {
		String hql = "from Department3 a where a.code=? ";
		List<Department3> list = this.find(hql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
