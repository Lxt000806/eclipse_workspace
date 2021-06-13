package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CourseType;
import com.house.home.entity.basic.Department1;
import com.house.home.entity.basic.Department2;

@SuppressWarnings("serial")
@Repository
public class Department1Dao extends BaseDao {

	/**
	 * 一级部门分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Department1 department1) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.code,a.Desc1,a.desc2,a.cmpCode,a.DepType,b.note bDepType, a.lastupdate,a.lastupdatedby,a.expired,a.actionlog,a.PlanNum,a.DispSeq," 
				+ " a.BusiType, c.NOTE BusiTypeDescr, d.NOTE IsOutChannelDescr " 
				+ " from tDepartment1 a  "
				+ " left outer join tXTDM b ON b.cbm=a.DepType AND  b.ID='DEPTYPE'"
				+ " left outer join tXTDM c ON c.cbm=a.BusiType AND  c.ID='BUSITYPE'"
				+ " left outer join tXTDM d ON d.cbm=a.IsOutChannel AND d.ID='YESNO'"
				+ " where 1=1 ";

		if (StringUtils.isNotBlank(department1.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+department1.getCode()+"%");
		}
		if (StringUtils.isNotBlank(department1.getDesc2())) {
			sql += " and a.Desc2 like ? ";
			list.add("%"+department1.getDesc2()+"%");
		}
		if (StringUtils.isNotBlank(department1.getDesc1())) {
			sql += " and a.Desc1 like ? ";
			list.add("%"+department1.getDesc1()+"%");
		}
		if (StringUtils.isNotBlank(department1.getDepType())) {
			sql += " and a.DepType= ? ";
			list.add(department1.getDepType());
		}
		if (StringUtils.isBlank(department1.getExpired())
				|| "F".equals(department1.getExpired())) {
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
	public Department1 getByDesc2(String desc2) {
		String hql = "from Department1 a where a.desc2=? ";
		List<Department1> list = this.find(hql, new Object[]{desc2});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Department1> findByNoExpired() {
		String hql = "from Department1 a where a.expired='F' order by a.code ";
		return this.find(hql);
	}
	@SuppressWarnings("unchecked")
	public List<Department1> getByDepType(String depType) {
		String hql = "from Department1 a where a.depType=? and a.expired='F' order by  dispSeq ";
		return this.find(hql, new Object[]{depType});
	}
	
	@SuppressWarnings("unchecked")
	public Department1 getByCode(String code) {
		String hql = "from Department1 a where a.code=? ";
		List<Department1> list = this.find(hql, new Object[]{code});
		if (list != null && list.size() > 0) 
			return list.get(0);
		else
			return null;
	}
	

	public List<Map<String, Object>> findDepType1(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,desc1 name from tDepartment1 a where a.expired='F'";
		//排序
		sql += " order by a.code";
		//sql携带着第一次选择的行号,list携带查找出来的数据
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String, Object>> findDep1All(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,desc1 name from tDepartment1 a where 1=1 ";
		//排序
		sql += " order by a.code";
		//sql携带着第一次选择的行号,list携带查找出来的数据
		return this.findBySql(sql, list.toArray());
	}
	
	/**
	 * 过期子部门
	 * @param department1
	 */
	public void expiredChild(Department1 department1) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tDepartment2 set Expired='T' where Department1=? "
				+" update tDepartment3 set Expired='T' where Department2 in (select Code from tDepartment2 where Department1=? ) "
				+" update tDepartment set Expired='T' where Path like ? ";
		list.add(department1.getCode());
		list.add(department1.getCode());
		list.add("%"+department1.getDepartment()+"%");
		executeUpdateBySql(sql, list.toArray());
	}
}
