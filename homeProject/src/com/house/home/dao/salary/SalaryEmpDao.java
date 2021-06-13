package com.house.home.dao.salary;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.salary.SalaryEmp;

@SuppressWarnings("serial")
@Repository
public class SalaryEmpDao extends BaseDao {

	/**
	 * SalaryEmp分页信息
	 * 
	 * @param page
	 * @param salaryEmp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SalaryEmp salaryEmp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.EmpCode,a.EmpName,b.Desc2 Department1Descr,c.Desc2 Department2Descr, "
					+"x1.NOTE CategoryDescr,d.Descr ConSignCmpDescr,a.IDNum,a.FinancialCode,e.Descr PosiClassDescr,f.Descr PosiLevelDescr, "
					+"x2.NOTE StatusDescr,a.JoinDate,a.RegularDate,a.LeaveDate,case when a.BasicSalary is not null then a.BasicSalary else f.BasicSalary end BasicSalary, "
					+"case when a.Salary is not null then a.Salary else f.Salary end Salary,a.SocialInsurParam,h.Descr SocialInsurParamDescr, "
					+"a.PayMode,x3.NOTE PayModeDescr,'查看详情' PayModeView,left(g.BankTypeDescr,len(g.BankTypeDescr)-1) BankTypeDescr, "
					+"x4.NOTE IsTaxableDescr,a.Remarks,a.EdmInsurMon,a.MedInsurMon,a.HouFundMon,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, "
					+"a.Department1,a.Department2,a.PosiClass,i.Desc2 PositionDescr,a.WorkingDays,a.InsurBase, "
					+"a.PosiSalary,a.SkillSubsidy,a.OtherBonuse,a.PerfBonuse,a.StarSubsidy,a.OtherSubsidy,a.PosiChgDate "
					+"from tSalaryEmp a  "
					+"left join tDepartment1 b on a.Department1=b.Code "
					+"left join tDepartment2 c on a.Department2=c.Code "
					+"left join tConSignCmp d on a.ConSignCmp=d.Code "
					+"left join tSalaryPosiClass e on a.PosiClass=e.PK "
					+"left join tSalaryPosiLevel f on a.PosiLevel=f.PK "
					+"left join ( "
					+"	select SalaryEmp,  "
					+"	(select in_b.NOTE +'+'   "
					+"	from tSalaryEmpBankCard in_a "
					+"	left join tXTDM in_b on in_a.BankType=in_b.CBM and in_b.ID='SALBANKTYPE' "
					+"	where in_a.SalaryEmp=in_c.SalaryEmp "
					+"	for xml path('')  ) as BankTypeDescr  "
					+"	from tSalaryEmpBankCard in_c "
					+"	group by SalaryEmp  "
					+")g on a.EmpCode=g.SalaryEmp "
					+"left join tSocialInsurParam h on a.SocialInsurParam=h.PK "
					+"left join tPosition i on a.Position=i.Code "
					+"left join tXTDM x1 on a.Category=x1.CBM and x1.ID='SALEMPCATEGORY' "
					+"left join tXTDM x2 on a.Status=x2.CBM and x2.ID='EMPSTS' "
					+"left join tXTDM x3 on a.PayMode=x3.CBM and x3.ID='SALPAYMODE' "
					+"left join tXTDM x4 on a.IsTaxable=x4.CBM and x4.ID='YESNO' "
					+"where 1=1 ";

    	if (StringUtils.isNotBlank(salaryEmp.getEmpCode())) {
			sql += " and a.EmpCode=? ";
			list.add(salaryEmp.getEmpCode());
		}
    	if (StringUtils.isNotBlank(salaryEmp.getCategory())) {
			sql += " and a.Category=? ";
			list.add(salaryEmp.getCategory());
		}
    	if (StringUtils.isNotBlank(salaryEmp.getIsFront())) {
			sql += " and a.IsFront=? ";
			list.add(salaryEmp.getIsFront());
		}
    	if (StringUtils.isNotBlank(salaryEmp.getIdnum())) {
			sql += " and a.IDNum=? ";
			list.add(salaryEmp.getIdnum());
		}
    	if (salaryEmp.getPosiClass() != null) {
			sql += " and a.PosiClass=? ";
			list.add(salaryEmp.getPosiClass());
		}
    	if (salaryEmp.getPosiLevel() != null) {
			sql += " and a.PosiLevel=? ";
			list.add(salaryEmp.getPosiLevel());
		}
    	if (StringUtils.isNotBlank(salaryEmp.getStatus())) {
			sql += " and a.Status=? ";
			list.add(salaryEmp.getStatus());
		}
    	if (StringUtils.isNotBlank(salaryEmp.getConSignCmp())) {
			sql += " and a.ConSignCmp=? ";
			list.add(salaryEmp.getConSignCmp());
		}
    	if (StringUtils.isNotBlank(salaryEmp.getDepartment1())) {
			sql += " and a.Department1 in ("+SqlUtil.resetStatus(salaryEmp.getDepartment1())+")";
		}
    	if (StringUtils.isNotBlank(salaryEmp.getPayMode())) {
			sql += " and a.PayMode=? ";
			list.add(salaryEmp.getPayMode());
		}
    	if(StringUtils.isNotBlank(salaryEmp.getDateType())){
    		if (salaryEmp.getDateFrom()!= null){
    			sql += " and a."+salaryEmp.getDateType()+">= ? ";
    			list.add(salaryEmp.getDateFrom());
    		}
    		if (salaryEmp.getDateTo() != null){
    			sql += " and a."+salaryEmp.getDateType()+"<= ? ";
    			list.add(DateUtil.endOfTheDay(salaryEmp.getDateTo()));
    		}
    	}
		if (StringUtils.isBlank(salaryEmp.getExpired()) || "F".equals(salaryEmp.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if(StringUtils.isNotBlank(salaryEmp.getQueryCondition())){
			sql+=" and ( a.EmpName like ? or a.EmpCode like ? or a.IDNum like ? or a.FinancialCode like ? )";
			list.add("%"+salaryEmp.getQueryCondition()+"%");
			list.add("%"+salaryEmp.getQueryCondition()+"%");
			list.add("%"+salaryEmp.getQueryCondition()+"%");
			list.add("%"+salaryEmp.getQueryCondition()+"%");
		}
		if(StringUtils.isNotBlank(salaryEmp.getScopeNum())){
			sql+=" and a."+salaryEmp.getScopeType()+" "+salaryEmp.getScopeOperate()+" ? ";
			list.add(salaryEmp.getScopeNum());
		}
		if (salaryEmp.getSalarySettleCmp() != null) {
            sql += " and a.SalarySettleCmp = ? ";
            list.add(salaryEmp.getSalarySettleCmp());
        }
		if (salaryEmp.getSocialInsurParam() != null) {
            sql += " and a.SocialInsurParam = ? ";
            list.add(salaryEmp.getSocialInsurParam());
        }
		if(StringUtils.isNotBlank(salaryEmp.getEmpCodes())){
			sql+=" and a.empCode not in('"+salaryEmp.getEmpCodes().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(salaryEmp.getPosition())){
			sql+=" and a.Position in('"+salaryEmp.getPosition().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(salaryEmp.getBelongType())){
			sql+=" and a.BelongType = ? ";
			list.add(salaryEmp.getBelongType());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 银行卡分页信息
	 * 
	 * @param page
	 * @param salaryEmp
	 * @return
	 */
	public Page<Map<String,Object>> goBankJqGrid(Page<Map<String,Object>> page, SalaryEmp salaryEmp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.PK,a.ActName,a.CardID,a.BankType,b.NOTE BankTypeDescr,a.Remarks "
			+"from tSalaryEmpBankCard a " 
			+"left join tXTDM b on a.BankType=b.CBM and b.ID='SALBANKTYPE' "
			+"where SalaryEmp=? ";
		list.add(salaryEmp.getEmpCode());
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 员工信息同步列表
	 * 
	 * @param page
	 * @param salaryEmp
	 * @return
	 */
	public Page<Map<String,Object>> goEmpSyncJqGrid(Page<Map<String,Object>> page, SalaryEmp salaryEmp) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.EmpName,c.Desc2 Department1Descr,d.Desc2 Department2Descr, "
					+"a.IDNum,a.EmpCode,e.NOTE StatusDescr,a.JoinDate,a.RegularDate,a.LeaveDate,f.Descr ConSignCmpDescr, "
				    +"b.NameChi NewEmpName,g.Desc2 NewDepartment1Descr,h.Desc2 NewDepartment2Descr, "
				    +"b.IDNum NewIDNum,i.NOTE NewStatusDescr,b.JoinDate NewJoinDate,isnull(a.RegularDate,b.RegularDate) NewRegularDate, "
				    +"isnull(a.LeaveDate,b.LeaveDate) NewLeaveDate,j.Descr NewConSignCmpDescr,k.Desc2 PositionDescr,l.Desc2 NewPositionDescr "
					+"from tSalaryEmp a  "
					+"inner join tEmployee b on a.EmpCode=b.Number "
					+"left join tDepartment1 c on a.Department1=c.Code "
					+"left join tDepartment2 d on a.Department2=d.Code "
					+"left join tXTDM e on a.Status=e.CBM and e.ID='EMPSTS' "
					+"left join tConSignCmp f on a.ConSignCmp=f.Code "
					+"left join tDepartment1 g on b.Department1=g.Code "
					+"left join tDepartment2 h on b.Department2=h.Code  "
					+"left join tXTDM i on b.Status=i.CBM and i.ID='EMPSTS' "
					+"left join tConSignCmp j on b.ConSignCmp=j.Code " 
					+"left join tPosition k on a.Position=k.Code " 
					+"left join tPosition l on b.Position=l.Code " 
					+"where 1=1 ";
		if("1".equals(salaryEmp.getChgType())){
			sql+="and a.ConSignCmp<>b.ConSignCmp or a.EmpName<>b.NameChi  "
				+"or a.Department1<>b.Department1 or a.Department2<>b.Department2  "
				+"or isnull(a.Position,'')<>b.Position or a.LeaveDate is null and b.LeaveDate is not null "
				+"or a.RegularDate is null and b.RegularDate is not null ";
		}else if("2".equals(salaryEmp.getChgType())){
			sql+="and a.EmpName<>b.NameChi";
		}else if("3".equals(salaryEmp.getChgType())){
			sql+="and a.RegularDate is null and b.RegularDate is not null";
		}else if("4".equals(salaryEmp.getChgType())){
			sql+="and a.LeaveDate is null and b.LeaveDate is not null";
		}else if("5".equals(salaryEmp.getChgType())){
			sql+="and a.ConSignCmp<>b.ConSignCmp";
		}else if("6".equals(salaryEmp.getChgType())){
			sql+="and a.Department1<>b.Department1";
		}else if("7".equals(salaryEmp.getChgType())){
			sql+="and a.Department2<>b.Department2";
		}else if("8".equals(salaryEmp.getChgType())){
			sql+="and isnull(a.Position,'')<>b.Position";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 保存
	 * 
	 * @param salaryEmpDetail
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(SalaryEmp salaryEmp) {
		Assert.notNull(salaryEmp);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSalaryEmp(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, salaryEmp.getM_umState());
			call.setString(2, salaryEmp.getEmpCode());	
			call.setString(3, salaryEmp.getEmpName());
			call.setString(4, salaryEmp.getCategory());
			call.setString(5, salaryEmp.getIdnum());
			if(salaryEmp.getPosiClass()==null){
				call.setNull(6, Types.INTEGER);
			}else{
				call.setInt(6, salaryEmp.getPosiClass());
			}
			if(salaryEmp.getPosiLevel()==null){
				call.setNull(7, Types.INTEGER);
			}else{
				call.setInt(7, salaryEmp.getPosiLevel());
			}
			call.setTimestamp(8,  salaryEmp.getJoinDate()==null?null:new Timestamp(salaryEmp.getJoinDate().getTime()));
			call.setTimestamp(9,  salaryEmp.getRegularDate()==null?null:new Timestamp(salaryEmp.getRegularDate().getTime()));
			call.setTimestamp(10,  salaryEmp.getLeaveDate()==null?null:new Timestamp(salaryEmp.getLeaveDate().getTime()));
			call.setString(11, salaryEmp.getStatus());
			call.setString(12, salaryEmp.getConSignCmp());
			call.setString(13, salaryEmp.getDepartment1());
			call.setString(14, salaryEmp.getDepartment2());
			call.setString(15, salaryEmp.getFinancialCode());
			call.setString(16, salaryEmp.getBasicSalarySetMode());
			call.setDouble(17, salaryEmp.getBasicSalary());
			call.setDouble(18, salaryEmp.getSalary()==null?0:salaryEmp.getSalary());
			if(salaryEmp.getSocialInsurParam()==null){
				call.setNull(19, Types.INTEGER);
			}else{
				call.setInt(19, salaryEmp.getSocialInsurParam());
			}
			if(salaryEmp.getEdmInsurMon()==null){
				call.setNull(20, Types.INTEGER);
			}else{
				call.setInt(20, salaryEmp.getEdmInsurMon());
			}
			if(salaryEmp.getMedInsurMon()==null){
				call.setNull(21, Types.INTEGER);
			}else{
				call.setInt(21, salaryEmp.getMedInsurMon());
			}
			if(salaryEmp.getHouFundMon()==null){
				call.setNull(22, Types.INTEGER);
			}else{
				call.setInt(22, salaryEmp.getHouFundMon());
			}
			call.setString(23, salaryEmp.getPayMode());
			call.setDouble(24, salaryEmp.getInsurLimit()==null?0:salaryEmp.getInsurLimit());
			if(salaryEmp.getSalarySettleCmp()==null){
				call.setNull(25, Types.INTEGER);
			}else{
				call.setInt(25, salaryEmp.getSalarySettleCmp());
			}
			call.setString(26, salaryEmp.getCmpUsageType());
			call.setString(27, salaryEmp.getSalaryStatus());
			call.setString(28, salaryEmp.getIsTaxable());
			call.setString(29, salaryEmp.getLastUpdatedBy());
			call.setString(30, salaryEmp.getExpired());
			call.setString(31, salaryEmp.getPayCmp1());
			call.setDouble(32, salaryEmp.getWeight1()==null?0:salaryEmp.getWeight1());
			call.setString(33, salaryEmp.getPayCmp2());
			call.setDouble(34, salaryEmp.getWeight2()==null?0:salaryEmp.getWeight2());
			call.setString(35, salaryEmp.getPayCmp3());
			call.setDouble(36, salaryEmp.getWeight3()==null?0:salaryEmp.getWeight3());
			call.setString(37, salaryEmp.getPayCmp4());
			call.setDouble(38, salaryEmp.getWeight4()==null?0:salaryEmp.getWeight4());
			call.setString(39, salaryEmp.getBankDetailJson());
			call.setString(40, salaryEmp.getRemarks());
			call.setString(41, salaryEmp.getIsSocialInsur());
			call.setString(42, salaryEmp.getIsFront());
			call.setString(43, salaryEmp.getSalaryScheme());
			call.setString(44, salaryEmp.getPosition());
			call.setInt(45, salaryEmp.getWorkingDays());
			call.setDouble(46, salaryEmp.getInsurBase());
			call.setDouble(47, salaryEmp.getPosiSalary());
			call.setDouble(48, salaryEmp.getSkillSubsidy());
			call.setDouble(49, salaryEmp.getOtherBonuse());
			call.setDouble(50, salaryEmp.getPerfBonuse());
			call.setDouble(51, salaryEmp.getStarSubsidy());
			call.setDouble(52, salaryEmp.getOtherSubsidy());
			call.setTimestamp(53,  salaryEmp.getPosiChgDate()==null?null:new Timestamp(salaryEmp.getPosiChgDate().getTime()));
			call.setDouble(54, salaryEmp.getCash()==null?0:salaryEmp.getCash());
			call.setString(55, salaryEmp.getIsBasicSalaryPayment());
			call.registerOutParameter(56, Types.INTEGER);
			call.registerOutParameter(57, Types.NVARCHAR);
			call.setString(58,salaryEmp.getBelongType());
			call.execute();
			result.setCode(String.valueOf(call.getInt(56)));
			result.setInfo(call.getString(57));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	
	/**
	 * 根据岗位类别查前后端
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getIsFront(Integer pk) {
		String sql = "select IsFront from tSalaryPosiClass where PK=?";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {pk});
		return list;
	}
	
	/**
	 * 根据岗位级别查工资
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getSalaryByLevel(Integer pk) {
		String sql = "select salary,basicSalary from tSalaryPosiLevel where PK=?";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {pk});
		return list;
	}
	
	/**
	 * 员工信息同步
	 * 
	 * @param empCodes
	 */
	public void doEmpSync(String empCodes) {
		String sql = " update tSalaryEmp set EmpName=b.NameChi,Department1=b.Department1,Department2=b.Department2,Position=b.Position, "
				+"LeaveDate=isnull(a.LeaveDate,b.LeaveDate),Status=b.Status,RegularDate=isnull(a.RegularDate,b.RegularDate),ConSignCmp=b.ConSignCmp "
				+"from tSalaryEmp a " 
				+"left join tEmployee b on a.EmpCode=b.Number where a.EmpCode in ( '"+ empCodes.replaceAll(",", "','") + "')";
		this.executeUpdateBySql(sql, new Object[] {});
	}
	
	/**
	 * 工号查出款公司明细
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getPayCmp(String empCode) {
		String sql = "select * from tSalaryEmpPayCmp where SalaryEmp=?";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {empCode});
		return list;
	}
	
	/**
	 * 获取员工所适用的薪酬方案
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getSalaryScheme(String empCode) {
		String sql = "select SalaryEmp,(select cast(SalaryScheme as nvarchar(20))+',' from tSalarySchemeEmp a " 
				    +"where a.SalaryEmp=b.SalaryEmp for xml path('')) as Schemes "
					+"from tSalarySchemeEmp b where b.SalaryEmp=? ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {empCode});
		return list;
	}
	
	/**
	 * 根据财务编码/姓名查员工
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getEmpByParam(String category,String empName,String finacialCode) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from tSalaryEmp  where 1=1  ";
		if(StringUtils.isNotBlank(category)){
			sql+="and Category=? ";
			list.add(category);
		}
		if(StringUtils.isNotBlank(empName)){
			sql+="and EmpName=? ";
			list.add(empName);
		}
		if(StringUtils.isNotBlank(finacialCode)){
			sql+="and FinancialCode=? ";
			list.add(finacialCode);
		}
		return this.findBySql(sql,list.toArray());
		
	}
	
    /**
	 * 岗位类别
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findPosiClass(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select pk id,descr name from tSalaryPosiClass a where Expired='F' order by a.pk";
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 岗位级别
	 * 
	 * @param param
	 * @return 
	 */
	public List<Map<String, Object>> findPosiLevel(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select pk id,descr name  from tSalaryPosiLevel a where a.expired='F'  ";
		if (StringUtils.isNotBlank((String) param.get("pCode"))) {
			sql += " and a.PosiClass=?";
			list.add((String) param.get("pCode"));
		}
		sql += " order by a.pk  ";
		return this.findBySql(sql, list.toArray());
	}
}

