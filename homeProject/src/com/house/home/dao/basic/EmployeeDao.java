package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.house.framework.commons.utils.DesUtils;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.EmpTranLog;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.project.JobType;

@SuppressWarnings("serial")
@Repository
public class EmployeeDao extends BaseDao {
	/**
	 * 员工分页信息
	 * @param page
	 * @param user
	 * @return
	 * @throws Throwable 
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Employee employee,UserContext uc){
		String sql="";
		List<Object> list = new ArrayList<Object>();
			sql = "select * from (select a.number+'|'+a.NameChi descr,ea.LastUpdate expenseDate,case when x2.note is null then x3.note else x2.note end DepTypeDescr" +
					",isNull(ea.amount,0) befAmount,a.insursigncmp,x1.note typedescr,cz.CZYBH,a.Number,a.RefCode,a.ICNum,a.NameEng,a.NameChi," +
					" e.NOTE SexDesc,a.IDNum,a.Birth,a.Edu,q.Note EduDescr,a.School,a.SchDept,a.BirtPlace,b1.Desc2 department1Descr,"
			    + "b2.Desc2 department2Descr,b3.Desc2 department3Descr,a.BasicWage,c.Desc2 PosDesc,a.Phone,a.Address,a.JoinDate,a.LeaveDate,"
			    + "d.NOTE StatusDesc,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Position,a.CarNo,a.RegularDate,a.ApplicantPK,"
			    + "i.NOTE IsPreTrainDesc,j.NOTE IsPreTrainPassDesc,n.note IsSchemedesignerDesc,k.descr Coursedescr ,a.IsPreTrain,a.IsPreTrainPass,"
			    + "a.ManagerRegularDate, a.OldDeptDate, a.PerfBelongMode, a.OldDept,l.note PerfBelongModeDecr,tx.Note MarryStatusDescr," +
			    " a.MarryStatus,a.isSchemeDesigner,a.conBeginDate,a.conEndDate,a.reTurnDate,p.Desc2 cmpDescr,o.CmpCode, "
			    + "CONVERT(VARCHAR(10),m1.Desc1) + CONVERT(VARCHAR(30),m2.Desc1)OldDeptDescr,"
			    + "CONVERT(VARCHAR(10),k.Nums) +'期'+ CONVERT(VARCHAR(30),k.Descr)NumsDescr,"
			    + "a.CardID, a.Bank,a.ConSignCmp, a.NameChi + '  ' + a.Number Description, "
			    + "a.IsLead,f.NOTE IsLeadDesc,a.LeadLevel,g.NOTE LeadLevelDesc,a.Status,a.Gender,a.Department1,a.Department2,a.Department3,a.PosiChgDate," 
			    + "a.SecondPosition,h.Desc2 positionDescr,a.ConnMan,a.ConnPhone,a.GraduationDate,a.DocumentNo, a.Department,dt.Desc2 DepartmentDescr,"
			    + "case when a.Status='SUS' then cast(DATEDIFF(MONTH,a.JoinDate,a.LeaveDate)/12 as varchar(4)) + '年' + cast(DATEDIFF(MONTH,a.JoinDate,a.LeaveDate)%12 as varchar(2)) + '月' "
			    + "else cast(DATEDIFF(MONTH,a.JoinDate,GETDATE())/12 as varchar(4)) + '年' + cast(DATEDIFF(MONTH,a.JoinDate,GETDATE())%12 as varchar(2)) + '月' end Seniority ,"
			    + "a.IdValidDate,a.PersonChgDate,c.Type,pjl.descr PrjLeveldescr,a.PrjLevel,a.PerSocialInsur,a.ComSocialInsur, csc.descr ConSignCmpdescr,tw.QualityFee PrjQualityFee ";
			    if(StringUtils.isNotBlank(employee.getSceneDesiCustCount())){
			    	sql += ",b.num scenedesicustcount ";	
			    }
			    if(StringUtils.isNotBlank(employee.getPrjManCustCount())){
			    	sql += ",isnull(prj2.thismonthcount,0) thismonthcount,isnull(prj1.nowcount,0) nowcount ";	
			    }
			    if(StringUtils.isNotBlank(employee.getIsEmpForPerf())){
			    	sql += ",lde.Number LeaderCode,lde.NameChi Leaderdescr,bce.Number BusiDrc,bce.NameChi BusiDrcdescr  ";	
			    }   
			sql += "from tEmployee a "
			    +  "left outer join tDepartment1 b1 on a.Department1=b1.Code "
			    +  "left outer join tDepartment2 b2 on a.Department2=b2.Code "
				+  "left outer join tDepartment3 b3 on a.Department3=b3.Code " +
				" left join tCzybm cz on cz.emNum = a.number "
				+  "left outer join tPosition c on a.Position=c.Code " 
			    +  "left outer join tXTDM d on a.Status=d.CBM and d.ID='EMPSTS' "
				+  "left outer join tXTDM q on a.Edu=q.CBM and q.ID='EDU' "
				+  "left outer join tXTDM e on a.Gender=e.CBM and e.ID='GENDER' "
				+  "left outer join tXTDM f on a.IsLead=f.CBM and f.ID='YESNO' "
				+  "left outer join tXTDM g on a.LeadLevel=g.CBM and g.ID='LEADLEVEL' "
				+  "left outer join tPosition h on a.SecondPosition=h.Code " 
				+  "left outer join tXTDM i on a.IsPreTrain=i.CBM and i.ID='YESNO' " 
				+  "left outer join tXTDM j on a.IsPreTrainPass=j.CBM and j.ID='YESNO' "
				+  "left outer join tCourse k on CourseCode=k.code "
			    +  "left outer join tXTDM l on a.PerfBelongMode=l.cbm and l.id='PERFBELONGMODE' "
				+  "left outer join tDepartment2 m2 on a.OldDept=m2.Code "
				+  "left outer join tDepartment1 m1 on m2.Department1=m1.Code "
				+  "left outer join tXTDM tx on a.MarryStatus=tx.CBM and tx.ID='MARRYSTATUS' "
				+  "left outer join tXTDM n on a.isschemedesigner=n.cbm and n.id='YESNO' "
			    +  "left outer join tXTDM x1 on x1.id = 'EMPTYPE' and x1.cbm = a.type "
			    +  "left outer join tPrjLevel pjl on pjl.Code=a.PrjLevel "
			    +  "left outer join tConSignCmp csc on csc.Code=a.ConSignCmp " +
			    "   left join tExpenseAdvance ea on ea.EmpCode = a.Number " +
			    "	left join tXTDM x2 on x2.id='DEPTYPE' and x2.cbm = b2.DepType " +
			    "	left join tXTDM x3 on x3.id='DEPTYPE' and x3.cbm = b3.DepType "+
			    "   left join tWorker tw on tw.EmpCode=a.Number " +
			    " left join tDepartment o on o.Code = a.Department " +
			    " left join tCompany p on p.Code = o.CmpCode "+
			    " left join tDepartment dt on a.Department = dt.Code ";
			
				if(StringUtils.isNotBlank(employee.getSceneDesiCustCount())){
					//现场设计师显示专用
			  		sql += "left join(select count(*) num,EmpCode from tcustomer ct "
					    +  "inner join tCustStakeholder cs on cs.CustCode=ct.Code and cs.Role='63' and ct.Expired='F' "
					    +  "group by EmpCode)" 
					    +  "b on b.EmpCode=a.Number ";
				}
		  		//项目经理显示专用
				if(StringUtils.isNotBlank(employee.getPrjManCustCount())){
					sql +=" left join(select count(*) nowcount,EmpCode from tcustomer ct  "
						+  " inner join tCustStakeholder cs on cs.CustCode=ct.Code and cs.Role='20' and ct.Expired='F' "
						+  " and ct.ConfirmBegin is not null and  ct.ConfirmBegin<>'' and  ct.Status='4'  "
						+  " group by EmpCode) prj1 on prj1.EmpCode=a.Number " 		   
						+  " left join(select count(*) thismonthcount,EmpCode from tcustomer ct "
						+  " inner join tCustStakeholder cs on cs.CustCode=ct.Code and cs.Role='20' and ct.Expired='F' " 
						+  " and  ConfirmBegin>=DATEADD(mm, DATEDIFF(mm,0,getdate()), 0) "
						+  " and ConfirmBegin<=dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,getdate())+1, 0)) "
						+  " and ct.Status='4' "
						+  " group by EmpCode ) prj2 on prj2.EmpCode=a.Number ";
				}
				if(StringUtils.isNotBlank(employee.getIsEmpForPerf())){
					sql +=" left join ( "
					/*	+  "	select min(Number) EmpCode, Department1, Department2 from tEmpForPerf "
						+  "	where IsLead='1' and LeadLevel='1' and Expired='F' "
						+  "	group by Department1, Department2 "*/
						+  "	select min(case when in_c.Code<>'' then in_e.LeaderCode else in_d.LeaderCode end) EmpCode,in_a.Department1,in_a.Department2 "
						+  "	from tEmpForPerf in_a " 
						+  "	left join tDepartment1 in_b on in_a.Department1=in_b.Code " 
						+  "	left join tDepartment2 in_c on in_a.Department2=in_c.Code " 
						+  "	left join tDepForPerf in_d on in_b.Department=in_d.Code " 
						+  "	left join tDepForPerf in_e on in_c.Department=in_e.Code "
						+  "	group by in_a.Department1,in_a.Department2 "
						+  " ) ld on ld.Department1=a.Department1 and ld.Department2=a.Department2 "
						+  " left join tEmployee lde on lde.Number=ld.EmpCode "
						+  " left join ( "
						+  "	select min(Number) EmpCode, Department3 from tEmpForPerf "
						+  "	where Position in (select isnull(qz,'') from tXTCS x1 where x1.ID='BUSIDRCCODE') and Department3<>'' and Expired='F' "
						+  "	group by Department3 "
						+  " ) bc on bc.Department3=a.Department3"
						+  " left join tEmployee bce on bce.Number=bc.EmpCode ";
				}
			sql += "where 1=1 " ;	
				if (StringUtils.isNotBlank(employee.getNumber())) {
					sql += " and (a.Number like ? or a.NameChi like ?) ";
					list.add("%"+employee.getNumber()+"%");
					list.add("%"+employee.getNumber()+"%");
				}
				if (StringUtils.isNotBlank(employee.getNameChi())) {
					sql += " and a.NameChi like ? ";
					list.add("%"+employee.getNameChi()+"%");
				}
				if (StringUtils.isNotBlank(employee.getDescr())) {
					sql += " and a.NameChi like ? ";
					list.add("%"+employee.getDescr()+"%");
				}
				if (StringUtils.isNotBlank(employee.getPositionType())) {
					sql += " and c.Type in ('"+employee.getPositionType().replaceAll(",", "','")+"') ";
				}
				if (StringUtils.isNotBlank(employee.getDepartment1())) {
					sql += " and a.Department1 in ('" + employee.getDepartment1().replace(",", "','") + "') ";
				}
				if (StringUtils.isNotBlank(employee.getDepartment2())) {
					sql += " and a.Department2 in ('" + employee.getDepartment2().replace(",", "','") + "') ";
				}
				if (StringUtils.isNotBlank(employee.getDepartment3())) {
					sql += " and a.Department3 in ('" + employee.getDepartment3().replace(",", "','") + "') ";
				}
				if (StringUtils.isBlank(employee.getExpired())
						|| "F".equals(employee.getExpired())) {
					sql += " and a.Expired='F' ";
				}
				if(StringUtils.isNotBlank(employee.getStatus())){
					sql += " and a.Status=? ";
					list.add(employee.getStatus());
				}
				if(StringUtils.isNotBlank(employee.getIsPreTrain())) {
					sql += " and a.IsPreTrain=? ";
					list.add(employee.getIsPreTrain());
				}
				if(StringUtils.isNotBlank(employee.getStartMan())){
					sql+=" and exists (select 1 from tEmployee in_a where in_a.number = ? and a.department =in_a.department ) ";
					list.add(employee.getStartMan());
				}
				
				if(StringUtils.isNotBlank(employee.getTeamCode())){
					sql += "and exists ( select 1 from   tTeamDept td where ( a.Department1 = td.Department1 ) "
						+  " and ( ( a.Department2 = td.Department2 )or ( td.Department2 = '' )or ( td.Department2 is null ) ) "
						+  " and ( ( a.Department3 = td.Department3 )or ( td.Department3 = '' )or ( td.Department3 is null )) "
						+  " and td.TeamCode =? )";
					list.add(employee.getTeamCode());
				}
				if (StringUtils.isNotBlank(employee.getIsPreTrainPass())) {
					sql += " and a.IsPreTrainPass=? ";
					list.add(employee.getIsPreTrainPass());
				}
				if(employee.getJoinDateFrom()!=null){
					sql += " and a.joinDate>=? ";
					list.add(employee.getJoinDateFrom());
				}
				if(employee.getJoinDateTo()!=null){
					sql += " and a.joinDate<dateadd(day,1,?) ";
					list.add(employee.getJoinDateTo());
				}
				if(employee.getLeaveDateFrom()!=null){
					sql += " and a.LeaveDate>=? ";
					list.add(employee.getLeaveDateFrom());
				}
				if(employee.getLeaveDateTo()!=null){
					sql += " and a.LeaveDate<dateadd(day,1,?) ";
					list.add(employee.getLeaveDateTo());
				}
				//验证身份证是否过期
				if(employee.getIdNumExpired()!=null){
					sql += " and a.idvalidDate<dateadd(day,1,?) ";
					list.add(employee.getIdNumExpired());
				}
     			if (StringUtils.isNotBlank(employee.getPhone())) {
					sql += " and a.phone like ? ";
					list.add("%"+employee.getPhone()+"%");
				}
    			if (StringUtils.isNotBlank(employee.getDepType())) {
    				sql += " and b1.depType in " + "('"+employee.getDepType().replaceAll(",", "','")+"')";
				}
    			if(StringUtils.isNotBlank(employee.getNums())){
    				sql +=" and k.nums like ? ";
    				list.add("%"+employee.getNums()+"%");
    			}
    			if (StringUtils.isNotBlank(employee.getPosition())) {
    				sql += " and a.position in ('"+employee.getPosition().replaceAll(",", "','")+"') ";
    			}
    			if(employee.getConBeginDate()!=null){
    				sql +="and a.conBeginDate>=? ";
    				list.add(employee.getConBeginDate());
    			}
    			if(employee.getConEndDate()!=null){
    				sql +="and a.conEndDate<dateadd(day,1,?) ";
    				list.add(employee.getConEndDate());
    			}
    			if(StringUtils.isNotBlank(employee.getIsSchemeDesigner())){
    				sql +="and a.isSchemeDesigner =? ";
    				list.add(employee.getIsSchemeDesigner());
    			}
    			if (StringUtils.isNotBlank(employee.getDep1Type())){ 
    				String str = SqlUtil.resetStatus(employee.getDep1Type());
    				sql += " and b1.depType in (" + str + ")";
    			}
    			if (StringUtils.isNotBlank(employee.getPrjDepartment2())) {
					sql += " and a.Department2 in ('"+employee.getPrjDepartment2().replaceAll(",", "','")+"') ";
				}
    			if (StringUtils.isNotBlank(employee.getPrjLevel())){ 
    				sql +="and a.PrjLevel =? ";
    				list.add(employee.getPrjLevel());
    			}
    			
    			if (StringUtils.isNotBlank(employee.getPrjManCustCount())){
    			    
    			    // 不再做限制 张海洋 20210407
    				//sql +="and (c.type='6' or h.type='6') ";
    			}
    			
    			if(StringUtils.isNotBlank(employee.getTaskKey()) && StringUtils.isNotBlank(employee.getWfProcNo())){
    				sql+=" and exists ( select 1 from tWfProcOption in_a " +
    						" left join ACT_ID_GROUP in_b on in_a.ActGroupId = in_b.id_ " +
    						" left join ACT_ID_MEMBERSHIP in_c on in_c.Group_Id_ = in_b.Id_ " +
    						" left join tCzybm in_d on in_d.czybh = in_c.User_id_ " +
    						" where in_a.taskKey = ? and in_a.wfProcNo = ? " +
    						" and (in_d.EMNum =a.Number  or in_a.ActGroupId is null or in_a.ActGroupId = '') ) ";
    					list.add(employee.getTaskKey());
    					list.add(employee.getWfProcNo());
    			}
    			if(StringUtils.isNotBlank(employee.getIsManager())){
    				sql+=" and exists ( select 1 from ACT_ID_MEMBERSHIP aim where aim.User_Id_ = a.Number and aim.Group_Id_ in ('deptManager','deptDirector')) ";
    			}
    			if(StringUtils.isNotBlank(employee.getCustCode()) && StringUtils.isNotBlank(employee.getRole())){
    				sql+=" and exists (select 1 from tCustStakeholder in_a " +
    						" left join tRoll in_b on in_a.role = in_b.code " +
    						" where in_a.custCode = ? and in_b.descr = ?" +
    						" and in_a.empCode = a.Number )";
    				list.add(employee.getCustCode());
    				list.add(employee.getRole());
    			}
    			if("1".equals(employee.getEmpAuthority())){
    				sql+=" and (exists ( " +
    						"select 1 from  tCZYDept in_b where a.Department1 = in_b.Department1 "
    	                    + " and ( a.Department2 = in_b.Department2 or in_b.Department2 = '' or in_b.Department2 is null ) "
    	                    + " and ( a.Department3 = in_b.Department3 or in_b.Department3 = '' or in_b.Department3 is null ) "
    	                    + " and in_b.CZYBH = ? and ? ='2' "+
    						" ) or ( ? ='3') or (? = '1' and number=? )) ";
    				list.add(uc.getCzybh());
    				list.add(uc.getCustRight());
    				list.add(uc.getCustRight());
    				list.add(uc.getCustRight());
    				list.add(uc.getCzybh());
    			}
    			
                if (StringUtils.isNotBlank(employee.getViewEmpType())) {
                    
                    if (employee.getViewEmpType().equals("0")) {
                        sql += " and a.Type <> '3' ";
                    } else if (employee.getViewEmpType().equals("1")) {
                        sql += " and a.Type = '3' ";
                        
                        if (!employee.isViewAllOutEmps()) {

                            sql += " and o.LeaderCode = ? ";
                            list.add(uc.getEmnum());
                        }
                    }
                    
                }
                
                if (StringUtils.isNotBlank(employee.getType())) {
                    sql += " and a.Type = ? ";
                    list.add(employee.getType());
                }
    			
    			if (StringUtils.isNotBlank(employee.getNumbers())) {
    				sql += " and a.number not in ('"+employee.getNumbers().replaceAll(",", "','")+"') ";
    			}
    			if(StringUtils.isNotBlank(employee.getConSignCmp())){
    				sql+=" and a.conSignCmp = ? ";
    				list.add(employee.getConSignCmp());
    			}
    			if("1".equals(employee.getIsStakeholder()) && StringUtils.isNotBlank(employee.getCustCode())){
    				sql+=" and ( exists(select 1 from tCustStakeholder in_a where in_a.CustCode=? and in_a.EmpCode=a.Number ) " 
    					+"      or exists(select 1 from tCustStakeholderHis in_b where in_b.CustCode=? and in_b.OldEmpCode=a.Number ) )";
    				list.add(employee.getCustCode());
    				list.add(employee.getCustCode());
    			}
    			if("0".equals(employee.getIsAddSalary())){
    				sql+=" and not exists (select 1 from tSalaryEmp se where se.EmpCode=a.Number )";
    				page.setPageOrderBy("JoinDate");
    				page.setPageOrder("desc");
    			}
    			if(StringUtils.isNotBlank(employee.getIdnum())){
    				sql +=" and a.IdNum like ? ";
    				list.add("%"+employee.getIdnum()+"%");
    			}
				if (StringUtils.isNotBlank(page.getPageOrderBy())){
					sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
				}else{
					if(StringUtils.isNotBlank(employee.getPrjManCustCount())){
						sql += " ) a order by a.PrjLevel desc,a.nowcount asc	";
					}else{
						sql += " ) a order by a.LastUpdate desc";	
					}	
				}
			return this.findPageBySql(page, sql, list.toArray());
		}
				
	@SuppressWarnings("unchecked")
	public Employee getByPhoneAndMm(String phone, String mm) {
		String hql = "select a from Employee a,Czybm b where a.number=b.emnum and a.phone=? and b.mm=? "
				+"and b.zfbz=0 ";
				//and NOT EXISTS(SELECT 1 FROM Employee c WHERE c.number=b.emnum AND c.status='SUS')
		List<Employee> list =  this.find(hql, new Object[]{phone,mm});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Employee getByPhoneWithoutMM(String phone) {
		String hql = "select a from Employee a,Czybm b where a.number=b.emnum and a.phone=?"
				+"and b.zfbz=0 ";
				//and NOT EXISTS(SELECT 1 FROM Employee c WHERE c.number=b.emnum AND c.status='SUS')
		List<Employee> list =  this.find(hql, new Object[]{phone});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Employee> getByPhone(String phone) {
		String hql="select a from Employee a where a.phone=? and status='ACT' ";
		List<Employee> list=this.find(hql, phone);
		if(list.size()==0){
			return null;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public Page<Map<String, Object>> findPageBySql_forClient(
			Page<Map<String, Object>> page, Employee employee) {
		if (StringUtils.isBlank(employee.getCode())){
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		String hql = "from JobType where code=?";
		String sql = "select a.Number,a.NameChi,a.Phone,x1.Desc2 department2Descr,isnull(p.num,0) num from tEmployee a "
		+"left join tDepartment2 x1 on a.Department2=x1.Code "
		+"left join ("
		+"select count(*) num,DealCZY from tPrjJob where JobType=? and Status in('2','3','4') and Date>=getdate()-30 group by DealCZY "
		+") p on a.Number=p.DealCZY "
		+"where a.Expired='F' and a.Status='ACT' ";
		list.add(employee.getCode());
		
		List<JobType> listType = this.find(hql, new Object[]{employee.getCode()});
		if (listType!=null && listType.size()>0){
			JobType jobType = listType.get(0);
			if (StringUtils.isNotBlank(jobType.getDepartment1())){
				sql += " and a.department1=? ";
				list.add(jobType.getDepartment1());
			}
			if (StringUtils.isNotBlank(jobType.getDepartment2())){
				sql += " and a.department2=? ";
				list.add(jobType.getDepartment2());
			}
			if (StringUtils.isNotBlank(jobType.getPosition())){
				sql += " and a.position in ("+SqlUtil.resetStatus(jobType.getPosition())+") ";
			}
			if (StringUtils.isNotBlank(employee.getNameChi())){
				sql += " and a.nameChi like ? ";
				list.add("%"+employee.getNameChi()+"%");
			}
			if ("1".equals(jobType.getIsJobDepart())){
				if (StringUtils.isNotBlank(employee.getProjectMan())){
					sql += " and a.department2 in (select a.Department2 from tJobDepart a "
							+"inner join tEmployee e1 on a.ProjectDepartment2=e1.Department2 "
							+"where e1.Number=?) ";
							list.add(employee.getProjectMan());
				}else{
					return null;
				}
			}
			sql += " order by p.num ";
			return this.findPageBySql(page, sql, list.toArray());
		}
		
		return null;
	}

	public Page<Map<String, Object>> findPageByName(
			Page<Map<String, Object>> page, String name) {
		List<Object> list = new ArrayList<Object>();
		String sql="select a.nameChi,a.number,ISNULL(b.Desc1,c.Desc1) department from tEmployee a "
				+"LEFT JOIN  tDepartment2 b  ON a.Department2=b.Code "
				+"LEFT JOIN tDepartment1 c ON a.Department1=c.Code "
				+"where a.expired='F' "; 
		if(StringUtils.isNotBlank(name)){
			sql+=" and a.nameChi like ?";
			list.add("%"+name+"%");
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 获取部门领导
	 * @return employee
	 */
	@SuppressWarnings("unchecked")
	public  Employee getDepartmentLeader(String department2){
		String hql="from Employee where  islead=1 and leadlevel=1 and department2=? ";
		List<Employee> list=this.find(hql, department2);
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public List<String> getDeptLeaders(String userId) {
		List<String> retList = new ArrayList<String>();
		List<Map<String,Object>> list = getDeptLeadersList(userId);
		if (list!=null && list.size()>0){
			for (Map<String,Object> map : list){
				retList.add(String.valueOf(map.get("leaderid")));
			}
		}
		return retList;
	}

	public List<Map<String,Object>> findByNoExpired() {
		String sql = "select number,namechi from tEmployee where status='ACT'";
		return this.findBySql(sql);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDeptLeadersList(String userId) {
		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		String sql = "select a.USER_ID_ userid,a.LEADER_ID_ leaderid,b.zwxm leadername from OA_USER_LEADER a,tczybm b where a.LEADER_ID_=b.czybh and a.USER_ID_=?";
		List<Map<String, Object>> listUserLeader = this.findBySql(sql, new Object[]{userId});
		if (listUserLeader!=null && listUserLeader.size()>0){
			for (Map<String, Object> userLeader : listUserLeader){
				Map<String,Object> map = new HashMap<String,Object>();
				String leaderid = String.valueOf(userLeader.get("leaderid")).trim();
				if (StringUtils.isNotBlank(leaderid)){
					map.put("leaderid", leaderid);
					map.put("leadername", String.valueOf(userLeader.get("leadername")));
					retList.add(map);
				}
			}
		}
		String hql = "select a from Employee a,Czybm b where a.number=b.emnum and b.czybh=?";
		List<Employee> listEmp = this.find(hql, new Object[]{userId});
		if (listEmp!=null && listEmp.size()>0){
			Employee employee = listEmp.get(0);
			if ("1".equals(employee.getIsLead()) && "1".equals(employee.getLeadLevel())){
				if (StringUtils.isNotBlank(employee.getDepartment3())){
					sql = "select c.czybh,c.zwxm "
					+"from tEmployee a inner join (select a.Department1,a.Department2,a.Department3 from tEmployee a "
					+"inner join tCZYBM b on a.Number=b.EMNum where b.CZYBH=?) b "
					+"on a.Department1=b.Department1 and a.Department2=b.Department2 and a.Department3='' "
					+"inner join tCZYBM c on a.Number=c.EMNum "
					+"where a.IsLead='1' and a.LeadLevel='1'";
					List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{userId});
					if (list!=null && list.size()>0){
						for (Map<String,Object> userLeader : list){
							Map<String,Object> map = new HashMap<String,Object>();
							String leaderid = String.valueOf(userLeader.get("czybh")).trim();
							if (StringUtils.isNotBlank(leaderid)){
								map.put("leaderid", leaderid);
								map.put("leadername", String.valueOf(userLeader.get("zwxm")));
								retList.add(map);
							}
						}
					}
				}else if (StringUtils.isNotBlank(employee.getDepartment2())){
					sql = "select c.czybh,c.zwxm "
					+"from tEmployee a inner join (select a.Department1,a.Department2,a.Department3 from tEmployee a "
					+"inner join tCZYBM b on a.Number=b.EMNum where b.CZYBH=?) b "
					+"on a.Department1=b.Department1 and a.Department2='' and a.Department3='' "
					+"inner join tCZYBM c on a.Number=c.EMNum "
					+"where a.IsLead='1' and a.LeadLevel='1'";
					List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{userId});
					if (list!=null && list.size()>0){
						for (Map<String,Object> userLeader : list){
							Map<String,Object> map = new HashMap<String,Object>();
							String leaderid = String.valueOf(userLeader.get("czybh")).trim();
							if (StringUtils.isNotBlank(leaderid)){
								map.put("leaderid", leaderid);
								map.put("leadername", String.valueOf(userLeader.get("zwxm")));
								retList.add(map);
							}
						}
					}
				}
			}else{//三级部门领导不找，找二级部门领导
				sql = "select c.czybh,c.zwxm "
				+"from tEmployee a inner join (select a.Department1,a.Department2,a.Department3 from tEmployee a "
				+"inner join tCZYBM b on a.Number=b.EMNum where b.CZYBH=?) b "
				+"on a.Department1=b.Department1 and a.Department2=b.Department2 "
				+"inner join tCZYBM c on a.Number=c.EMNum "
				+"where a.IsLead='1' and a.LeadLevel='1' ";
				List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{userId});
				if (list!=null && list.size()>0){
					for (Map<String,Object> userLeader : list){
						Map<String,Object> map = new HashMap<String,Object>();
						String leaderid = String.valueOf(userLeader.get("czybh")).trim();
						if (StringUtils.isNotBlank(leaderid)){
							map.put("leaderid", leaderid);
							map.put("leadername", String.valueOf(userLeader.get("zwxm")));
							retList.add(map);
						}
					}
				}
			}
		}
		
		return retList;
	}
	
	public List<Map<String,Object>>getErpCzy(){
		
		String sql=" select QZ from tXTCS where id='erpManager' ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{});
		String idString=list.get(0).get("QZ").toString();
		
		List<Map<String , Object>> listEmployee=getErpCzyEmployee(idString);
		
		return listEmployee;
	}

	public List<Map<String,Object>>getErpCzyEmployee(String id){
		String sql = " select Number id ,nameChi name from tEmployee where Number in " + "('"+id.replace(",", "','" )+ "')";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{});

		return list;
	}
	
	public List<String> getErpCzyList() {
		List<String> retList = new ArrayList<String>();
		List<Map<String,Object>> list = getErpCzy();
		if (list!=null && list.size()>0){
			for (Map<String,Object> map : list){
				retList.add(String.valueOf(map.get("id")));
			}
		}
		return retList;
	}
	
	public String getDepLeader(String code){
		
		String sql=" select * from temployee a  where  a.Department2=? and a.IsLead='1'  and a.LeadLevel='1' and a.expired='F' " +
				" and exists (select  * from tDepartment2 where DepType='3' and code=a.Department2) ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0).get("NameChi").toString();
		}		
		return "";
	}

	// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//	public Map<String, Object> getCZYGNQX(String czybh, String mkdm, String gnmc){
//		List<Object> params = new ArrayList<Object>();
//		String sql = "select * from tCZYGNQX where 1=1 ";
//		if(StringUtils.isNotBlank(czybh)){
//			sql += " and CZYBH=? ";
//			params.add(czybh);
//		}
//		if(StringUtils.isNotBlank(mkdm)){
//			sql += " and MKDM=? ";
//			params.add(mkdm);
//		}
//		if(StringUtils.isNotBlank(gnmc)){
//			sql += " and GNMC=? ";
//			params.add(gnmc);
//		}
//		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
//		if(list != null && list.size() > 0){
//			return list.get(0);
//		}
//		return null;
//	}

	public Map<String,Object> getDepLeaderByEmpNum(String empNum) {
		String sql = "select b.Number,b.NameChi,b.Department1,b.Department2 from ("
			+"select number,NameChi,Department1,Department2 from temployee where Number=?) a "
			+"inner join ("
			+"select min(a.Number) Number,a.NameChi, a.Department1, a.Department2 from tEmployee a "
			+"left join tDepartment1 d1 on a.Department1=d1.Code "
			+"left join tDepartment2 d2 on a.Department2=d2.Code "
			+"where a.IsLead='1' and a.LeadLevel='1' "
			+"group by a.Department1, a.Department2,a.NameChi) b on a.Department1=b.Department1 and a.Department2=b.Department2";
		List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{empNum});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> getProTypeOpt(String postype) {
		// TODO Auto-generated method stub
		String sql = " select rtrim(Code)+' '+Desc2 fd,Code from tPosition where Expired='F' and type=? order by Code";
		return this.findBySql(sql, new Object[]{postype});
	}
	public Map<String, Object> validNameChi(String nameChi){
		List<Object> params = new ArrayList<Object>();
		String sql ="select * from tEmployee where nameChi =?";
		params.add(nameChi);
		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
		if (list !=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	public Map<String, Object> validNum(String number,String idnum){
		List<Object> params = new ArrayList<Object>();
		String sql ="select * from tEmployee a where ";
		if(StringUtils.isNotBlank(idnum)){
			sql+="a.idnum =? ";
			params.add(idnum);
		}
		if(StringUtils.isNotBlank(number)){
			sql+="or a.number =? ";
			params.add(number);
		}
		List<Map<String, Object>> list = this.findBySql(sql,params.toArray());
		if (list !=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 员工信息调用存储过程（保存，修改）
	 * 
	 */
	@SuppressWarnings("deprecation")
	public Result doEmpforInfo(Employee employee) {
		Assert.notNull(employee);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
			hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pYgxx_forXml(?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,? ,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, employee.getM_umState());
			call.setString(2, employee.getNumber());
			call.setString(3, employee.getRefCode());
			call.setString(4, employee.getIcnum());
			call.setString(5, employee.getNameEng());
			call.setString(6, employee.getNameChi());
			call.setString(7, employee.getGender());
			call.setString(8, employee.getIdnum());
			call.setTimestamp(9, employee.getBirth()==null?null : new Timestamp(employee.getBirth().getTime()));
			call.setString(10, employee.getEdu());
			call.setString(11, employee.getSchool());
			call.setString(12, employee.getSchDept());
			call.setString(13, employee.getBirtPlace());
			call.setString(14, employee.getDepartment1());
			call.setString(15, employee.getDepartment2());
			call.setString(16, employee.getDepartment3());
			call.setDouble(17, employee.getBasicWage()==null?0.0: employee.getBasicWage());
			call.setString(18, employee.getPosition());
			call.setString(19, employee.getPhone());
			call.setString(20, employee.getAddress());
			call.setTimestamp(21, employee.getJoinDate()==null?null : new Timestamp(employee.getJoinDate().getTime()));
			call.setString(22, employee.getStatus());
			call.setTimestamp(23, employee.getLeaveDate()==null?null : new Timestamp(employee.getLeaveDate().getTime()));
			call.setString(24, employee.getIsLead());
			call.setString(25, employee.getLeadLevel());
			call.setString(26, employee.getRemarks());
			call.setString(27, employee.getLastUpdatedBy());
			call.setString(28, employee.getExpired());
			call.setString(29, employee.getPhotoName());
			call.setString(30, employee.getSecondPosition());
			call.setString(31, employee.getChgRemarks());
			call.setString(32, employee.getConnMan());
			call.setString(33, employee.getConnPhone());
			call.setTimestamp(34, employee.getGraduationDate()==null?null : new Timestamp(employee.getGraduationDate().getTime()));
			call.setString(35, employee.getDocumentNo());
			call.setTimestamp(36, employee.getPosiChgDate()==null?null : new Timestamp(employee.getPosiChgDate().getTime()));
			call.setString(37, employee.getCarNo());
			call.setTimestamp(38, employee.getRegularDate()==null?null : new Timestamp(employee.getRegularDate().getTime()));
			call.setDouble(39, employee.getApplicantPK()==null?0.0: employee.getApplicantPK());
			call.setTimestamp(40, employee.getManagerRegularDate()==null?null : new Timestamp(employee.getManagerRegularDate().getTime()));
			call.setTimestamp(41, employee.getOldDeptDate()==null?null : new Timestamp(employee.getOldDeptDate().getTime()));
			call.setString(42, employee.getPerfBelongMode());
			call.setString(43, employee.getOldDept());
			call.setTimestamp(44, employee.getIdValidDate()==null?null : new Timestamp(employee.getIdValidDate().getTime()));
			call.setTimestamp(45, employee.getPersonChgDate()==null?null : new Timestamp(employee.getPersonChgDate().getTime()));
			call.setString(46, employee.getMarryStatus());
			call.setTimestamp(47, employee.getReTurnDate()==null?null : new Timestamp(employee.getReTurnDate().getTime()));	
			call.setString(48, employee.getIsSchemeDesigner());
			call.setTimestamp(49, employee.getConBeginDate()==null?null : new Timestamp(employee.getConBeginDate().getTime()));	
			call.setTimestamp(50, employee.getConEndDate()==null?null : new Timestamp(employee.getConEndDate().getTime()));	
			call.setString(51, employee.getConSignCmp());//合同签约公司 add by zb on 20190327
			call.setString(52, employee.getEmpExpDetailXml());
			call.setString(53, employee.getEmpCertificationXml());
			call.registerOutParameter(54, Types.INTEGER);
			call.registerOutParameter(55, Types.NVARCHAR);
			call.setString(56, employee.getPrjLevel());
			call.setString(57,DesUtils.encode(employee.getPhone().substring(employee.getPhone().length()-6)));
			call.setString(58, employee.getDepartment());
			call.setString(59, employee.getOldDepartment());
			call.setString(60, employee.getType());
			call.setString(61, employee.getCardId());
			call.setString(62, employee.getBank());
			call.execute();
			result.setCode(String.valueOf(call.getInt(54)));
			result.setInfo(call.getString(55));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	//导出图片
	public List<Map<String, Object>> export(){
		String sql ="select * from tEmpPic where Number !='' and PhotoName !='' and pic is not null order by Number "; 
		try {
			List<Map<String, Object>> list = this.findBySql(sql);
			if (list!=null) {
				return list;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;	
	}
	//获取最新图片
	public Map<String,Object> getPhotoName(String number){
		List<Object> param = new ArrayList<Object>();	
		String sql ="select * from tEmpPic a where 1=1 ";
	       if (StringUtils.isNotBlank(number)) {
	    	   sql +="and a.number=? ";
	    	   param.add(number);
		   }
	          sql +="order by a.LastUpdate DESC";
	    List<Map<String, Object>> list = this.findBySql(sql, param.toArray());	     
	    if (list!=null && list.size()>0) {
			return list.get(0);
		}  
		return null;
	}
	/**
	 * 根据所属部门编号获取一二三级部门编号
	 * @param code
	 * @param tableName
	 * @return
	 */
	public String getCodeByDept(String tableName,String code){
		
		String sql="select code from "+tableName +" where department=?";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{code});
		if (list!=null && list.size()>0){
			return list.get(0).get("code").toString();
		}		
		return "";
	}
	
	public List<Map<String, Object>> findEmployeeExpired(){
		String sql = "select Number id,NameChi name,Department pId from tEmployee where Expired='F'";
		return this.findBySql(sql, new Object[]{});
	}
	
	public boolean hasEmpAuthority(String czybh,String empCode){
		String sql=" select 1 from   tEmployee a "
	                    + " inner join tCZYDept b on a.Department1 = b.Department1 "
	                    + " and ( a.Department2 = b.Department2 or b.Department2 = '' or b.Department2 is null  ) "
	                    + " and ( a.Department3 = b.Department3 or b.Department3 = '' or b.Department3 is null)"
	                    + " where a.Number=? and b.CZYBH=?";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{empCode,czybh});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取员工领导的部门
	 * @param number
	 * @return
	 */
	public Map<String, Object> getDeptByLeader(String number){
		
		String sql="select depts = ( STUFF(( select ',' + Desc2 "
					+"              from tDepartment "
					+"              where	LeaderCode=Test.LeaderCode " 
					+"              for xml path('') "
					+"             ), 1, 1, '') ) "
					+"from tDepartment as Test "
					+"where Test.LeaderCode=? "
					+"group by LeaderCode ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{number});
		if (list!=null && list.size()>0){
			return list.get(0);
		}		
		return null;
	}
	
	/**
	 * 员工信息修改信息
	 * @param page
	 * @param empTranLog
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_empTranLog(Page<Map<String,Object>> page, EmpTranLog empTranLog) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select a.PK,a.Number,a.OldDept,Dept,OldDept.Desc1 OldDeptDescr,Dept.Desc1 DeptDescr,a.OldPosition,a.Position, "
				+" b.Desc2 oldPositionDescr,c.Desc2 PositionDescr,a.OldIsLead,a.IsLead,x1.NOTE OldIsLeadDescr,x2.NOTE IsLeadDescr, "
				+" a.OldLeadLevel,a.LeadLevel,x3.NOTE OldLeadLevelDescr,x4.NOTE LeadLevelDescr,OldStatus,a.Status,a.ModifyCZY, " 
				+" x5.NOTE OldStatusDescr, x6.NOTE StatusDescr,d.ZWXM  ModifyCZYDescr,e.NameChi,a.date, "
				+" a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,nowDept.Desc1 nowDeptDescr,e.Department "
				+" from tEmpTranLog a "
				+" left join tDepartment OldDept on OldDept.Code=a.OldDept "
				+" left join tDepartment Dept on Dept.Code=a.Dept "
				+" left join tPosition b on b.Code=a.OldPosition "
				+" left join tPosition c on c.Code=a.Position "
				+" left join tXTDM x1 on x1.ID='YESNO' and x1.CBM=a.OldIsLead "
				+" left join tXTDM x2 on x2.ID='YESNO' and x2.CBM=a.IsLead "
				+" left join tXTDM x3 on  x3.ID='LEADLEVEL' and x3.CBM=a.OldLeadLevel "
				+" left join tXTDM x4 on  x4.ID='LEADLEVEL' and x4.CBM=a.LeadLevel "
				+" left join tXTDM x5 on  x5.ID='EMPSTS' and x5.CBM=a.OldStatus "
				+" left join tXTDM x6 on  x6.ID='EMPSTS' and x6.CBM=a.Status "
				+" left join tCzybm d on d.CZYBH=a.ModifyCZY "
				+" left join tEmployee e on e.Number=a.Number "
				+" left join tDepartment nowDept on nowDept.Code=e.Department  "
				+" where 1=1 ";
		if(StringUtils.isNotBlank(empTranLog.getNumber())){
			sql +=" and a.number =?";
			list.add(empTranLog.getNumber());
		}
		if(empTranLog.getDateFrom()!=null){
			  sql+="and a.date>=? ";
			  list.add(empTranLog.getDateFrom());
		}
		if(empTranLog.getDateTo()!=null){
			 sql+="and a.date<=? ";
			 list.add(DateUtil.endOfTheDay(empTranLog.getDateTo()));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.Department,a.date";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
    public boolean existsNameChiExceptNumber(String nameChi, String number) {
        List<Object> parameters = new ArrayList<Object>();
        
        String sql = "select * from tEmployee where NameChi = ? ";
        parameters.add(nameChi);
        
        if (StringUtils.isNotBlank(number)) {
            sql += "and Number <> ? ";
            parameters.add(number);
        }

        List<Map<String, Object>> employees = findBySql(sql, parameters.toArray());

        if (employees == null || employees.size() == 0) {
            return false;
        }

        return true;
    }
    
    public boolean existsIdNumExceptNumber(String idNum, String number, String type) {
        List<Object> parameters = new ArrayList<Object>();
        
        String sql = "select * from tEmployee where IDNum = ? ";
        parameters.add(idNum);
        
        if (StringUtils.isNotBlank(number)) {
            sql += "and Number <> ? ";
            parameters.add(number);
        }
        
        if ("outEmployee".equals(type)){
        	sql += " and Type = '3' ";
        }
        
        if ("innerEmployee".equals(type)){
        	sql += " and Type <> '3' ";
        }

        List<Map<String, Object>> employees = findBySql(sql, parameters.toArray());

        if (employees == null || employees.size() == 0) {
            return false;
        }

        return true;
    }
    
    public Result doUpdateEmpStatus(Employee employee){
		Assert.notNull(employee);
		Result result = new Result();
		String sql = " set nocount on "
				   + " declare @ret int "
				   + " declare @errmsg nvarchar(400) "
				   + " exec pYgxx_forXml ?,?,?,?,?, ?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,? ,?,?, @ret output, @errmsg output,?,?,?,?,?,?,? "
				   + " select @ret ret,@errmsg errmsg "
				   + " set nocount off "; 
		List<Map<String, Object>> list = this.findBySql(sql, new Object[]{
			employee.getM_umState(),
			employee.getNumber(),
			employee.getRefCode(),
			employee.getIcnum(),
			employee.getNameEng(),
			employee.getNameChi(),
			employee.getGender(),
			employee.getIdnum(),
			employee.getBirth()==null?null : new Timestamp(employee.getBirth().getTime()),
			employee.getEdu(),
			employee.getSchool(),
			employee.getSchDept(),
			employee.getBirtPlace(),
			employee.getDepartment1(),
			employee.getDepartment2(),
			employee.getDepartment3(),
			employee.getBasicWage()==null?0.0: employee.getBasicWage(),
			employee.getPosition(),
			employee.getPhone(),
			employee.getAddress(),
			employee.getJoinDate()==null?null : new Timestamp(employee.getJoinDate().getTime()),
			employee.getStatus(),
			employee.getLeaveDate()==null?null : new Timestamp(employee.getLeaveDate().getTime()),
			employee.getIsLead(),
			employee.getLeadLevel(),
			employee.getRemarks(),
			employee.getLastUpdatedBy(),
			employee.getExpired(),
			employee.getPhotoName(),
			employee.getSecondPosition(),
			employee.getChgRemarks(),
			employee.getConnMan(),
			employee.getConnPhone(),
			employee.getGraduationDate()==null?null : new Timestamp(employee.getGraduationDate().getTime()),
			employee.getDocumentNo(),
			employee.getPosiChgDate()==null?null : new Timestamp(employee.getPosiChgDate().getTime()),
			employee.getCarNo(),
			employee.getRegularDate()==null?null : new Timestamp(employee.getRegularDate().getTime()),
			employee.getApplicantPK()==null?0.0: employee.getApplicantPK(),
			employee.getManagerRegularDate()==null?null : new Timestamp(employee.getManagerRegularDate().getTime()),
			employee.getOldDeptDate()==null?null : new Timestamp(employee.getOldDeptDate().getTime()),
			employee.getPerfBelongMode(),
			employee.getOldDept(),
			employee.getIdValidDate()==null?null : new Timestamp(employee.getIdValidDate().getTime()),
			employee.getPersonChgDate()==null?null : new Timestamp(employee.getPersonChgDate().getTime()),
			employee.getMarryStatus(),
			employee.getReTurnDate()==null?null : new Timestamp(employee.getReTurnDate().getTime()),	
			employee.getIsSchemeDesigner(),
			employee.getConBeginDate()==null?null : new Timestamp(employee.getConBeginDate().getTime()),	
			employee.getConEndDate()==null?null : new Timestamp(employee.getConEndDate().getTime()),	
			employee.getConSignCmp(),//合同签约公司 add by zb on 20190327
			employee.getEmpExpDetailXml(),
			employee.getEmpCertificationXml(),
			employee.getPrjLevel(),
			DesUtils.encode(employee.getPhone().substring(employee.getPhone().length()-6)),
			employee.getDepartment(),
			employee.getOldDepartment(),
			employee.getType(),
			employee.getCardId(),
			employee.getBank()});
		if(list != null && list.size() > 0){
			int ret = (Integer) list.get(0).get("ret");
			String errmsg = (String) list.get(0).get("errmsg");
			if (ret == 1) {
				result.setCode(String.valueOf(ret));
				result.setInfo(errmsg);
			} else {
				logger.error("无法修改员工信息");
			}
		}
		return result;
	}
	
}
