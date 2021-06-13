package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.entity.Menu;
import com.house.home.entity.basic.Department2;

@SuppressWarnings("serial")
@Repository
public class Department2Dao extends BaseDao {

	/**
	 * 二级部门分页信息
	 * 
	 * @param page
	 * @param user
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Department2 department2) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Code,a.Desc2,a.DispSeq,a.PlanNum,a.Department1,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Desc1," +
				" b.desc2 department1Descr,c.note depTypeDescr, d.note BusiTypeDescr, e.NOTE IsOutChannelDescr " +
				" from tDepartment2 a " +
				" left join tdepartment1 b on b.Code=a.Department1 " +
				" left join tXTDM c ON c.cbm=a.DepType AND  c.ID='DEPTYPE' " +
				" left join tXTDM d ON d.cbm=a.BusiType AND  d.ID='BUSITYPE'" +
				" left join tXTDM e ON e.cbm=a.IsOutChannel AND e.ID='YESNO'" +
				" where 1=1 ";

		if (StringUtils.isNotBlank(department2.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+department2.getCode()+"%");
		}
		if (StringUtils.isNotBlank(department2.getDepartment1())) {
			sql += " and a.Department1=? ";
			list.add(department2.getDepartment1());
		}
		if (StringUtils.isNotBlank(department2.getDesc1())) {
			sql += " and a.desc1 like ? ";
			list.add("%"+department2.getDesc1()+"%");
		}
		
		if (StringUtils.isNotBlank(department2.getDesc2())) {
		    sql += " and a.desc2 like ? ";
		    list.add("%"+department2.getDesc2()+"%");
		}
		
		if(StringUtils.isNotBlank(department2.getDepType())){
			sql += " and a.DepType=? ";
			list.add(department2.getDepType());
		}
		if (StringUtils.isBlank(department2.getExpired())
				|| "F".equals(department2.getExpired())) {
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
	public Department2 getByDesc2(String desc2) {
		String hql = "from Department2 a where a.desc2=? ";
		List<Department2> list = this.find(hql, new Object[]{desc2});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Department2 getByCode(String code) {
		String hql = "from Department2 a where a.code=? ";
		List<Department2> list = this.find(hql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Department2> getByDepartment1(String code) {
		String hql = "from Department2 a where a.department1=? ";
		List<Department2> list = this.find(hql, new Object[]{code});
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Department2> findByNoExpired() {
		String hql = "from Department2 a where a.expired='F' order by a.department1,a.dispSeq";
		return this.find(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Department2> getByDepType(String depType) {
		String hql = "from Department2 a where a.depType=? and a.expired='F' order by  dispSeq,department1";
		return this.find(hql, new Object[]{depType});
	}
	
	public List<Map<String, Object>> findDepType2(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,desc1 name from tDepartment2 a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.Department1=?";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.code";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String, Object>> findDep2All(Map<String, Object> param) {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,desc1 name from tDepartment2 a where 1=1 ";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.Department1=?";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.code";
		return this.findBySql(sql, list.toArray());
	}
	
	/**
	 * 过期子部门
	 * @param department2
	 */
	public void expiredChild(Department2 department2) {
		List<Object> list = new ArrayList<Object>();
		String sql = "update tDepartment3 set Expired='T' where Department2=? "
				+" update tDepartment set Expired='T' where Path like ? ";
		list.add(department2.getCode());
		list.add("%"+department2.getDepartment()+"%");
		executeUpdateBySql(sql, list.toArray());
	}
	
	public List<Department2> getDepartment2WithLeader() {
		String sql = "  select a.*,isnull(c.NameChi,'') depLeader from tDepartment2 a " 
				+" inner join tDepartment1 b on a.Department1=b.code "
				+"  outer apply ( "
				+" 	select top 1 in_a.NameChi,in_a.Department2 "
				+" 	from   temployee in_a "
				+" 	where  in_a.Department2 = a.Code "
				+" 		and in_a.IsLead = '1' "
				+" 		and in_a.LeadLevel = '1' "
				+" 		and in_a.expired = 'F' "
				+" 		and exists ( select 1 from   tDepartment2 "
				+" 						 where  DepType = '3' and code = in_a.Department2 "
				+" 		) " 							 							
				+"   )c " +
				" where b.expired='F' and a.expired='F'     ";
		List<Map<String,Object>> list = this.findBySql(sql);
		return BeanConvertUtil.mapToBeanList(list, Department2.class);
	}
}
