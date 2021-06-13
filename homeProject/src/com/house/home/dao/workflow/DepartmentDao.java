package com.house.home.dao.workflow;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.WorkCost;
import com.house.home.entity.workflow.Department;
import com.microsoft.sqlserver.jdbc.SQLServerException;

@SuppressWarnings("serial")
@Repository
public class DepartmentDao extends BaseDao {

	/**
	 * Department分页信息
	 * 
	 * @param page
	 * @param department
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, Department department,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from(select * from(select a.LeaderCode,a.higherDep,a.depType,a.busiType,a.isActual," 
				+ "a.Code,a.Path,a.Desc2,a.desc2 desc1,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired,a.PlanNum,a.DispSeq, "
				+ "a.CmpCode,b.Desc2 cmpDescr,c.NameChi leaderName,d.Desc2 higherDepDescr,e.NOTE depTypeDescr,"
				+ "f.NOTE busiTypeDescr,g.NOTE isActualDescr,len(a.Path)-len(replace(a.Path,'/',''))+1 level, "
				+ "d.Expired pExpired,h.Expired ppExpired,i.NOTE isProcDeptDescr,a.isProcDept, a.IsOutChannel, j.NOTE IsOutChannelDescr "
				+ "from tDepartment a  "
				+ "left join tCompany b on a.CmpCode=b.Code "
				+ "left join tEmployee c on a.LeaderCode=c.Number "
				+ "left join tDepartment d on a.HigherDep=d.Code "//上级部门
				+ "left join tDepartment h on d.HigherDep=h.Code "//上上级部门
				+ "left join tXTDM e ON e.cbm=a.DepType AND  e.ID='DEPTYPE' "
				+ "left join tXTDM f ON f.cbm=a.BusiType AND  f.ID='BUSITYPE' "
				+ "left join tXTDM g ON g.cbm=a.IsActual AND  g.ID='YESNO' "
				+ "left join tXTDM i ON i.cbm=a.IsProcDept AND  i.ID='YESNO' "
				+ "left join tXTDM j ON j.cbm=a.IsOutChannel AND  j.ID='YESNO' "
				+ ")a where  1=1 ";
		if (StringUtils.isNotBlank(department.getDesc2())) {
			sql += " and a.Desc2 like ? ";
			list.add("%"+department.getDesc2()+"%");
		}
		
		if (StringUtils.isBlank(department.getExpired())
				|| "F".equals(department.getExpired())) {
			sql += " and a.Expired='F' ";
			if("1".equals(department.getIsEmp())){//员工信息调用时，把上级和上上级部门过期的也过滤掉
				sql += " and ((a.pExpired is not null and a.pExpired='F' ) or a.pExpired is null) " 
					+"and ((a.ppExpired is not null and a.ppExpired='F') or a.ppExpired is null)";
			}
		}
		//父部门名称查询条件
		if (StringUtils.isNotBlank(department.getParentDep())) {
			//模糊查询找出满足名称的编号
			String sql_parent="select code from tDepartment where Desc2 like ? ";
			List<Object> list_parent = new ArrayList<Object>();
			list_parent.add("%"+department.getParentDep()+"%");
			List<Map<String, Object>> parentList=this.findListBySql(sql_parent, list_parent.toArray());
			//循环遍历拼接sql，根据路径匹配父部门编号
			if(parentList.size()>0 && null!=parentList){
				sql+="and ( ";
				for (int i = 0; i < parentList.size(); i++) {
					sql+="  a.Path like ? and a.Code <> ? ";
					list.add("%"+parentList.get(i).get("code").toString()+"%");
					list.add(parentList.get(i).get("code").toString());
					if(i < parentList.size()-1){
						sql+=" or ";
					}
				}
				sql+=" ) ";
			}else{
				sql+="and 1<>1 ";
			}
		}
		if (StringUtils.isNotBlank(department.getLevel())) {
			sql += " and a.level=? ";
			list.add(department.getLevel());
		}
		//不能选择本身和子部门作为上级部门，不然生成路径会死循环
		if (StringUtils.isNotBlank(department.getCode())) {
			sql += " and a.Path not like ?  ";
			list.add("%"+department.getCode()+"%");
		}
		if (StringUtils.isNotBlank(department.getIsActual())) {
			sql += " and a.IsActual=? ";
			list.add(department.getIsActual());
		}
		if(StringUtils.isNotBlank(department.getSalfDept())){
			sql+=" and exists(select 1 from temployee in_a where in_a.department = a.code and in_a.number = ?)";
			list.add(uc.getCzybh());
		}
		
		if (StringUtils.isNotBlank(department.getIsOutChannel())) {
            sql += " and a.IsOutChannel = ? ";
            list.add(department.getIsOutChannel());
        }
		//员工编号查询条件
		if (StringUtils.isNotBlank(department.getEmpCode())) {
            sql += " and exists (select 1 from tDepEmp in_a where in_a.Department=a.Code and in_a.EmpCode=?) ";
            list.add(department.getEmpCode());
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.Path";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * DepEmp分页信息
	 * 
	 * @param page
	 * @param department
	 * @return
	 */
	public Page<Map<String, Object>> findEmpBySql(Page<Map<String, Object>> page, Department department) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.pk,a.EmpCode,a.Department,c.Expired,c.LastUpdate,"
				+ "a.LastUpdatedBy,a.ActionLog,b.Desc2 departmentDesc2,c.NameChi empName "
				+ "from tDepEmp a "
				+ "inner join tDepartment b on a.Department=b.Code "
				+ "inner join tEmployee c on a.EmpCode=c.Number "
				+ "where a.Department=?  ";
		list.add(department.getCode());
		if (StringUtils.isBlank(department.getExpired()) || "F".equals(department.getExpired())) {
			sql += " and c.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.EmpCode";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 保存
	 * 
	 * @param department
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(Department department) {
		Assert.notNull(department);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDepartment(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, department.getM_umState());
			call.setString(2, department.getCode());
			call.setString(3, department.getDesc2());
			call.setString(4, department.getCmpCode());
			call.setString(5, department.getLastUpdatedBy());
			call.setString(6, department.getExpired());
			call.setInt(7, department.getPlanNum()==null?0:department.getPlanNum());
			call.setInt(8, department.getDispSeq()==null?0:department.getDispSeq());
			call.setString(9, department.getBusiType());
			call.setString(10, department.getDepType());
			call.setString(11, department.getHigherDep());
			call.setString(12, department.getLeaderCode());
			call.setString(13, department.getIsActual());
			call.setString(14, department.getEmpCode());
			call.setString(15, department.getEmpPk());
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.NVARCHAR);
			call.setString(18, department.getIsProcDept());
			call.setString(19, department.getIsOutChannel());
			call.execute();
			result.setCode(String.valueOf(call.getInt(16)));
			result.setInfo(call.getString(17));
		} catch (Exception e) {
			e.printStackTrace();
			result.setInfo(e.getMessage());
		}finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	/**
	 * 所有子部门
	 * 
	 * @param page
	 * @param department
	 * @return
	 */
	public List<Map<String, Object>> findLowerDeptBySql(String code) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select cast(len(path) - len(replace(path, '/', '')) + 1 as nvarchar(1)) level,code,desc2,higherDep "
				+"from tDepartment where Path like '%'+?+'%' order by len(path)-len(replace(path,'/',''))+1 ";
		list.add(code);
		return this.findListBySql(sql, list.toArray());
	}
	/**
	 * 是否有部门人员
	 * 
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> hasEmp(String code) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select 1 from tDepEmp where department=?";
		list.add(code);
		return this.findListBySql(sql, list.toArray());
	}
	
	public List<Map<String, Object>> findDepartmentNoExpired(){
		String sql = "select Code id,Desc1 name,case when isnull(HigherDep,'')='' then '_VIRTUAL_RO0T_ID_' " +
				"else HigherDep end pId " +
				"from tDepartment " +
				"where Expired='F' and IsActual='1' ";
		return this.findBySql(sql, new Object[]{});
	}
	
	/**
	 * 本部门+权限部门
	 * @author cjg
	 * @date 2020-3-2
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findDeptList(Page<Map<String, Object>> page,String czybh,String desc2,String  containsChild) {
		List<Object> list=new ArrayList<Object>();
		String joinContent="";
		if("1".equals(containsChild)){
			joinContent="b.Path like '%'+a.Department+'%'";
		}else{
			joinContent="a.Department=b.Code";
		}
		String sql = "select a.Code,a.Desc2,a.DispSeq,case when b.childNum>0 then '1' else '0' end hasChild from (" 
				+"  select b.Code,b.Desc2,b.DispSeq from ( "
				+"    select case when d.Department is not null then d.Department when c.Department is not null then c.Department " 
				+"    else b.Department end Department "
				+"    from tCzyDept a  "
				+"    left join tDepartment1 b on a.Department1=b.Code " 
				+"    left join tDepartment2 c on a.Department2=c.Code " 
				+"    left join tDepartment3 d on a.Department3=d.Code " 
				+"    where CZYBH=?  " 
				+"  )a "
				+"  left join tDepartment b on "+joinContent
				+"  where  b.Expired='F' and b.IsActual='1'";
		if(!"1".equals(containsChild)){
			sql+=" and b.Path not like '%'+(select Department from tEmployee where Number=?)+'%' "; 
			list.add(czybh);
		}
				sql+="  union all "
				+"  select b.Code,b.Desc2,b.DispSeq from tEmployee a  " 
				+"  left join tDepartment b on a.Department=b.Code " 
				+"  where a.Number=? and b.IsActual='1' " 
				+") a  "
				+"left join(" 
				+"  select count(1) childNum,HigherDep from tDepartment where Expired='F' and IsActual='1'  group by HigherDep"
				+")b on a.Code=b.HigherDep where 1=1 ";
		
		list.add(czybh);
		list.add(czybh);
		if(StringUtils.isNotBlank(desc2)){
			sql+="and a.Desc2 like ?";
			list.add("%"+desc2+"%");
		}
		sql+=" order by a.DispSeq ";
		return this.findPageBySql(page, sql,list.toArray());
	}
	
	/**
	 * 查询所有一级部门
	 * @author cjg
	 * @date 2020-3-2
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findDept1List(Page<Map<String, Object>> page,String desc2) {
		List<Object> list=new ArrayList<Object>();
		String sql = " select a.Code,a.Desc2,case when b.childNum>0 then '1' else '0' end hasChild "
				 +"from tDepartment a  "
				 +"left join(" 
				 +"  select count(1) childNum,HigherDep from tDepartment where Expired='F' and IsActual='1' group by HigherDep" 
				 +")b on a.Code=b.HigherDep "
				 +"where isnull(a.HigherDep,'')='' and a.Expired='F' and a.IsActual='1' ";
		if(StringUtils.isNotBlank(desc2)){
			sql+="and Desc2 like ?";
			list.add("%"+desc2+"%");
		}
		sql+=" order by a.DispSeq ";
		return this.findPageBySql(page, sql,list.toArray());
	}
	
	/**
	 * 查询子部门
	 * @author cjg
	 * @date 2020-3-2
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findChildDeptList(Page<Map<String, Object>> page, String department,String desc2) {
		List<Object> list=new ArrayList<Object>();
		String sql = " select a.Code,a.Desc2,case when b.childNum>0 then '1' else '0' end hasChild "
		 +"from tDepartment a  "
		 +"left join(" 
		 +"  select count(1) childNum,HigherDep from tDepartment where Expired='F' and IsActual='1' group by HigherDep" 
		 +")b on a.Code=b.HigherDep "
		 +"where a.HigherDep=? and a.Expired='F' and a.IsActual='1' ";
		list.add(department);
		if(StringUtils.isNotBlank(desc2)){
			sql+="and Desc2 like ?";
			list.add("%"+desc2+"%");
		}
		sql+=" order by a.DispSeq ";
		return this.findPageBySql(page, sql,list.toArray());
	}

	/**
	 * 查询员工
	 * @author cjg
	 * @date 2020-3-2
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findEmpList(Page<Map<String, Object>> page, String department,String nameChi) {
		List<Object> list=new ArrayList<Object>();
		String sql = " select Number,NameChi from tEmployee where Department=? and Expired='F' ";
		list.add(department);
		if(StringUtils.isNotBlank(nameChi)){
			sql+="and NameChi like ?";
			list.add("%"+nameChi+"%");
		}
		return this.findPageBySql(page, sql,list.toArray());
	}
	
	/**
	 * 获取子部门，以逗号拼接
	 * @param number
	 * @return
	 */
	public String getChildDeptStr(String number){
		String sql="declare @depts nvarchar(max)='' "
		+"select @depts= @depts+Code+',' "
		+"from tEmployee a " 
		+"left join tDepartment b on b.Path like '%'+a.Department+'%' "
		+"where a.Number=? and b.Expired='F' "  
		+"select @depts depts";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{number});
		if (list!=null && list.size()>0){
			return list.get(0).get("depts").toString();
		}		
		return null;
	}
	
	/**
	 * 根据员工编号获取部门领导编号
	 * 
	 * @param empNum 员工编号
	 * @return 领导编号
	 */
	public String getLeaderCodeByEmpCode(String empNum) {
		String sql = "select tDepartment.LeaderCode from tDepartment " +
				"left join tDepEmp on tDepartment.Code = tDepEmp.Department " +
				"where tDepartment.IsActual = '1' and tDepEmp.EmpCode = ?";
		
		List<Map<String, Object>> result = findBySql(sql, empNum);
		
		if (result.size() > 0) {
			return (String) result.get(0).get("LeaderCode");
		}
		
		return null;
	}
	
    
}

