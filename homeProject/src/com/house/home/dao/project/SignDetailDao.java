package com.house.home.dao.project;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.commons.utils.StringUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.SignDetailEvt;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class SignDetailDao extends BaseDao{

	public Page<Map<String, Object>> getCheckConfirmList(
			Page<Map<String, Object>> page,Customer customer,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		
		String prjProgPic =" select * from (select '工程巡检验收' typeDescr,'今天'+cast(count(a.confirmTimes)as nvarchar(5))+'人已拍照，共'+ " +
				"		cast(isnull(sum(isnull(a.custNum, 0)), 0) as nvarchar(5))+'套工地' describe," +
				" '' as no,'1' as type ,'0' deptType,0 dispSeq,null PersonNum " +
				" from (" +
				"   select	a.LastUpdatedBy, 1 confirmTimes,sum(custNum) custNum from (" +
				"		select	a.LastUpdatedBy, 1 confirmTimes,count(1) custNum from (" +
				"			select	a.LastUpdatedBy, 1 confirmTimes,count(1) custNum,custCode from tPrjProgConfirm a " +
				"			where a.Date >= ? and a.date< ? and a.ErrPosi = '0'" +
				"			group by a.LastUpdatedBy, a.custCode " +
				" 		) a  group by a.LastUpdatedBy" +
				"		union all" +
				"		select	a.AppCZY, 1 checkTimes,count(1) custNum from (" +
				"			select	a.AppCZY, 1 checkTimes,count(1) custNum,CustCode from tPrjProgCheck a " +
				"			where a.Date >=? and a.date< ? and a.ErrPosi = '0'" +
				"			group by a.appczy,CustCode " +
				"		) a " +
				"		group by a.appczy" +
				" 	)a group BY a.lastUpdatedBY" +
				" )a  ) a where 1=1 " +getUserAuthSql(uc.getCzybh(), "0834", "工程巡检验收");
		String prjSignIn = " select case when a.typeDescr is null then e.Desc1 else a.typeDescr end + case when e.DepType='3' then '监理签到' else '签到' end typeDescr," +
				"			 case when  a.describe is null then '0人签到' else a.describe end describe, " +
				"			 case when a.deptCode is null then e.Code else a.deptCode end deptCode,'2' Type,e.DeptType,e.dispSeq," +
				"			case when c.Num is not null then c.Num else b.Num end PersonNum " +
				"			 from (" +
				"					select in_a.Code, in_a.Desc1, '2' deptType, in_a.dispSeq, in_a.DepType from tDepartment2 in_a "+
				" 					left join tDepartment1 in_b on in_a.Department1 = in_b.Code "+
				" 					where (in_a.DepType in ( '3', '8' ) or in_b.DepType in ( '3', '8' )) and in_a.expired = 'F' "+
				"					union all " +
				"					select a.Code,a.Desc1,'1' deptType,dispSeq,DepType from tDepartment1 a" +
				"					where not exists (select 1 from tDepartment2 in_a where in_a.Department1 = a.Code) " +
				"					and DepType in ('3','8') and expired = 'F' " +
				"			)e" +
				"			left join (" +
				"				select (case when d.Desc1 is null or d.Desc1 = '' then c.desc1 else d.Desc1 end) typeDescr," +
				"						'今天'+cast(count(1)as nvarchar(5))+'人已拍照，共'+cast(sum(a.custNum)as nvarchar(5))+'套工地' describe," +
				"				 case when d.code is null or d.code = '' then c.code else d.code end deptCode,'2' Type, " +
				"				 case when d.Desc1 is null or d.Desc1 = '' then '1' else '2' end deptType " +
				"				 from  (" +
				"					select a.SignCZY,count(1) custNum from (" +
				"							select	a.SignCZY, convert(nvarchar(10), a.CrtDate, 102) crtDate,a.CustCode" +
				"							from tSignIn a" +
				"							inner join tSignInPic b on b.No = a.no " +
				"							left join tCustomer c on c.Code = a.CustCode" +
				"							where a.CrtDate >= ? and a.CrtDate < ? " +
				"							group by a.SignCZY, convert(nvarchar(10), a.CrtDate, 102),a.CustCode" +
				"						)a group by signCZY) a" +
				"				 left join tEmployee b on b.Number = a.SignCZY" +
				"				 left join tDepartment1 c on c.Code = b.Department1" +
				"				 left join tDepartment2 d on d.Code = b.Department2 " +
				"				 where 1=1 " +
				"				 group by c.Code,c.desc1,d.Code,d.Desc1" +
				"		)a on a.DeptType = e.Depttype and a.deptCode = e.code " +
				" 		left join ( " +
				" 			select count(1) Num,in_a.Department1 from tEmployee in_a " +
				" 			left join tDepartment1 in_b on in_a.Department1 = in_b.Code " +
				" 			where isnull(in_a.Department1,'') <> '' and in_a.Expired = 'F' " +
				" 			group by in_a.Department1 " +
				" 		) b on e.Code = b.Department1 " +
				" 		left join ( " +
				" 			select count(1) Num,in_a.Department2 from tEmployee in_a " +
				" 			left join tDepartment2 in_b on in_a.Department2 = in_b.Code " +
				" 			where isnull(in_a.Department1,'') <> '' and in_a.Expired = 'F' " +
				" 			group by in_a.Department2 " +
				" 		) c on e.Code = c.Department2 " +
				" where  1=1 "+getUserAuthSql(uc.getCzybh(), "0834", "项目经理签到")+
				getDeptAuthSql(uc,"e")+
			//	" order by a.describe desc,e.dispSeq" +
				"";
		
		String WorkSignIn = " select a.Descr + '班组签到' typeDescr, case when b.describe is null then '0人签到' else b.describe end describe," +
				" a.Code,'3' type,'0' DeptType,a. dispSeq,c.PersonNum " +
				" from tWorkType12 a  " +
				" left join (" +
				" select c.Descr+'班组签到' typeDescr,'今天'+cast(count(1) as nvarchar(5))+'人已拍照，共'+cast(sum(a.custNum) as nvarchar(5))+'套工地' describe," +
				" c.Code,'3' Type,'0' deptType " +
				" from (" +
				" 	select a.WorkType12,a.WorkerCode,count(1) custNum from (" +
				"							select	c.WorkType12, a.WorkerCode,c.CustCode" +
				"							from tWorkSign a" +
				"							inner join tWorkSignPic d on d.No = a.No" +
				"							left join tCustWorker c on c.PK = a.CustWkPk" +
				"							where" +
				"								a.CrtDate >= ? " +
				"								and a.CrtDate < ? " +
				"							group by  a.WorkerCode, c.WorkType12,c.CustCode" +
				"						)a  group by  a.WorkerCode, a.WorkType12" +
				" ) a " +
				" left join tWorker b on b.Code = a.WorkerCode " +
				" left join tWorkType12 c on c.Code = a.WorkType12 " +
				"  where (c.MinPhotoNum is  null or c.MinPhotoNum >0) and c.Expired = 'F' " +
				" group by c.Code,c.Descr " +
				" )b on b.Code = a.code " +
				" left join ( " +
				" 	select count(1) PersonNum,in_b.WorkType12 from ( " +
				" 		select  in_a.WorkerCode, in_a.WorkType12 from tCustWorker in_a " +
				" 		where in_a.EndDate is null and convert(nvarchar(10),in_a.ComeDate,112) <= convert(nvarchar(10),getdate(),112) " +
				" 		group by in_a.WorkerCode, in_a.WorkType12 " +
				" 	) in_b group by in_b.WorkType12 " +
				" ) c on a.Code = c.WorkType12 " +
				" where 1=1 " +
				getUserAuthSql(uc.getCzybh(), "0834", "工人签到")+
				" and exists (select 1 from tCZYBM in_a "
                + " left join tPrjRole in_b on in_a.PrjRole=in_b.Code "
                + " left join tPrjRoleWorkType12 in_c on in_b.Code=in_c.PrjRole "
                + " where in_a.CZYBH= ? and (in_b.Code is null or in_c.WorkType12=a.code))"+
				"  and a.expired = 'F' and (a.MinPhotoNum is null or a.minPhotoNum > 0 )" +
				"" ;
		System.out.println(WorkSignIn);
		System.out.println(list);
		String sql = " select * from (" + 
				prjProgPic+ 
				" union all "+
				prjSignIn+
				" union all "+
				WorkSignIn +
				" )a  order by type,dispSeq ";
		if(customer.getBeginDate() == null || customer.getBeginDate() == null){
			return page;
		} 
		
		/*String beginDate="20190901";
		String endDate = "20190902";*/
		Timestamp beginDate = new Timestamp(
				DateUtil.startOfTheDay(customer.getBeginDate()).getTime());
		
		Timestamp endDate = new Timestamp(
				DateUtil.endOfTheDay( customer.getEndDate()).getTime());
		System.out.println(beginDate);
		System.out.println(endDate);
		List<Map<String, Object>> newList = this.findBySql(sql, new Object[]{beginDate,endDate,beginDate,endDate,beginDate,
				endDate,beginDate,endDate,uc.getCzybh().trim()});
		page.setResult(newList);
		return page;
	}
	
	public Page<Map<String, Object>> getSignDetail(
			Page<Map<String, Object>> page,Customer customer, UserContext uc) {
		
		List<Object> list = new ArrayList<Object>();

		String sql = "";
		// 1 巡检验收
		if(StringUtils.isNotBlank(customer.getType()) && "1".equals(customer.getType())){
			sql = "select * from ( select b.Address,c.NameCHi empName, b.Code,a.Date,a.No,'1' prjType,'验收' typeDescr,'1' type , Phone mobile " +
					" from tPrjProgConfirm a " +
					" left join tCustomer b on b.Code = a.CustCode " +
					" left join tEmployee c on c.number = a.LastUpdatedBy" +
					" where a.Date >= ? and a.date< ? and a.ErrPosi = '0' " ;
			list.add(new Timestamp(
					DateUtil.startOfTheDay(customer.getBeginDate()).getTime()));
			list.add(new Timestamp(
					DateUtil.endOfTheDay( customer.getEndDate()).getTime()));
			
			if(StringUtils.isNotBlank(customer.getCheckManDescr())){
				sql+=" and c.NameChi like ?" ;
				list.add("%"+customer.getCheckManDescr()+"%");
			}
			sql += " union all ";
			sql += " select b.Address,c.NameChi empName, b.Code,a.Date,a.No,'2' prjType,'巡检' typeDescr,'1' type, Phone mobile" +
					" from tPrjProgCheck a " +
					" left join tCustomer b on b.Code = a.CustCode" +
					" left join tEmployee c on c.number = a.AppCZY" +
					" where a.Date >= ? and a.date< ? and a.ErrPosi = '0'" ;
			list.add(new Timestamp(
					DateUtil.startOfTheDay(customer.getBeginDate()).getTime()));
			list.add(new Timestamp(
					DateUtil.endOfTheDay( customer.getEndDate()).getTime()));
			
			if(StringUtils.isNotBlank(customer.getCheckManDescr())){
				sql += " and c.NameChi like ? ";
				list.add("%"+customer.getCheckManDescr()+"%");
			}
			sql += " )a order by Date desc";
		}
		
		// 2 项目经理签到
		if(StringUtils.isNotBlank(customer.getType()) && "2".equals(customer.getType())){
			sql = "select * from (select e.address,b.NameChi empName,e.Code,a.CrtDate date,a.no,f.descr typeDescr,'2' type " +
					" from tSignIn a" +
					" left join tCustomer e on e.code = a.CustCode" +
					" left join tEmployee b on b.Number = a.SignCZY" +
					" left join tDepartment1 c on c.Code = b.Department1" +
					" left join tDepartment2 d on d.Code = b.Department2 " +
					" left Join tprjItem1 f on f.code = a.PrjItem " +
					" where 1=1 and a.CrtDate >= ? and a.CrtDate < ? " +
					" and exists( select 1 from tSignInPic in_a where in_a.no = a.no) and (c.expired = 'F' " +
					" or d.Expired = 'F') " ;
			
			list.add(new Timestamp(
					DateUtil.startOfTheDay(customer.getBeginDate()).getTime()));
			list.add(new Timestamp(
					DateUtil.endOfTheDay( customer.getEndDate()).getTime()));
			
			if(StringUtils.isNotBlank(customer.getCheckManDescr())){
				sql+=" and b.Namechi like ?";
				list.add("%"+customer.getCheckManDescr()+"%");
			}
			if(StringUtils.isNotBlank(customer.getDepType()) && StringUtils.isNotBlank(customer.getNo())){
				if("1".equals(customer.getDepType())){
					sql+=" and c.code = ? ";
				} else {
					sql+=" and d.code = ? ";
				}
				list.add(customer.getNo());
			}
			sql+=" )a order by date desc";
		}
		
		// 3 工人签到
		if(StringUtils.isNotBlank(customer.getType()) && "3".equals(customer.getType())){
			sql = "select * from (select d.Address,e.NameChi empName,d.Code,a.CrtDate date,a.no , c.descr typeDescr,'3'type " +
					" from tWorkSign a " +
					" inner join tCustWorker b on b.PK = a.CustWkPk " +
					" left join tWorkType12 c on c.Code = b.WorkType12" +
					" left join tcustomer d on d.Code = b.CustCode " +
					" left join tWorker e on e.Code = a.WorkerCode" +
					" where 1=1 and a.CrtDate >= ? and a.CrtDate < ? " +
					" and exists( select 1 from tWorkSignPic in_a where in_a.no = a.no) " +
					" and (c.MinPhotoNum is null or c.MinPhotoNum >0 )" +
					" and c.expired = 'F' " ;
			
			list.add(new Timestamp(
					DateUtil.startOfTheDay(customer.getBeginDate()).getTime()));
			list.add(new Timestamp(
					DateUtil.endOfTheDay( customer.getEndDate()).getTime()));
			
			if(StringUtils.isNotBlank(customer.getCheckManDescr())){
				sql+=" and e.NameChi like ? ";
				list.add("%"+customer.getCheckManDescr()+"%");
			}
			if(StringUtils.isNotBlank(customer.getWorkType12Dept())){
				sql += " and e.WorkType12Dept = ? ";
				list.add(customer.getWorkType12Dept());
			}
			if(StringUtils.isNotBlank(customer.getWorkerClassify())){
				sql+=" and e.WorkerClassify = ? ";
				list.add(customer.getWorkerClassify());
			}
			if(StringUtils.isNotBlank(customer.getNo())){
				sql+=" and c.code = ? ";
				list.add(customer.getNo());
			}
			sql+= ") a  order by date";
		}
		
		if(customer.getBeginDate() == null || customer.getBeginDate() == null 
				|| StringUtils.isBlank(customer.getType())){
			return page;
		} 
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>> getPrjProgConfirmPhoto(String prjProgConfirmNo, Integer number){
		String sql = " select " + (number == null || number == 0 ? "": "top " + number) + " a.PhotoName src,isnull(a.isSendYun, '0') isSendYun,totalNum" +
				"	,case when sendDate is null then 0 else 1 end hasSend "
				   + " from tPrjProgPhoto a "
				   + " left join (select count(1) totalNum from tPrjProgPhoto where refNo = ?)b on 1=1"
				   + " where a.RefNo=? ";
		return this.findBySql(sql, new Object[]{prjProgConfirmNo, prjProgConfirmNo});
	}
	
	public List<Map<String, Object>> getSignInPic(String no, Integer number){
		String sql = " select " + (number == null || number == 0 ? "": "top " + number) + " a.PhotoName src,isnull(a.isSendYun, '0') isSendYun" +
				" ,totalNum,case when sendDate is null then 0 else 1 end hasSend "
				   + " from tSignInpic a "
				   + " left join (select count(1) totalNum from tSignInPic where no = ?)b on 1=1"
				   + " where a.no=? ";
		return this.findBySql(sql, new Object[]{no, no});
	}
	
	public List<Map<String, Object>> getWorkSignInPic(String no, Integer number){
		String sql = " select " + (number == null || number == 0 ? "": "top " + number) + " a.PhotoName src,isnull(a.isSendYun, '0') isSendYun,totalNum,c.CustCode" +
				" ,case when sendDate is null then 0 else 1 end hasSend "
				   + " from tWorkSignPic a "
				   + " left join (select count(1) totalNum from tWorkSignPic where no = ?)b on 1=1 " +
				   "   inner join tWorkSign c on c.no = a.no "
				   + " where a.no=? ";
		return this.findBySql(sql, new Object[]{no, no});
	}
	
	public List<Map<String, Object>> getPicMore(Customer customer) {
		
		List<Object> list = new ArrayList<Object>();
		
		String sql = "";
		// 1 巡检验收
		if(StringUtils.isNotBlank(customer.getType()) && "1".equals(customer.getType())){
			sql = " select PhotoName src,case when sendDate is null then 0 else 1 end hasSend from tPrjProgPhoto where RefNo = ? ";
			list.add(customer.getNo());
		}
		
		// 2 项目经理签到
		if(StringUtils.isNotBlank(customer.getType()) && "2".equals(customer.getType())){
			sql = " select PhotoName src,case when sendDate is null then 0 else 1 end hasSend from tSignInPic where No = ? " ;
			list.add(customer.getNo());
		}
		
		// 3 工人签到
		if(StringUtils.isNotBlank(customer.getType()) && "3".equals(customer.getType())){
			sql = "select PhotoName src,b.CustCode,case when sendDate is null then 0 else 1 end hasSend from tWorkSignPic a " +
					" inner join tWorkSign b on b.No = a.No" +
					" where a.No = ? " ;
				list.add(customer.getNo());
		}
		
		return this.findBySql(sql, list.toArray());
	}
	
	public String getUserAuthSql(String czybm, String mkdm, String gnmc){
		String userAuth = " and exists (select	GNMC" +
				"	from	tCZYGNQX" +
				"	where	CZYBH = '"+czybm+"' and MKDM = '"+mkdm+"' and GNMC = '"+gnmc+"'" +
				"	union" +
				"	select	a.GNMC" +
				"	from	tRoleGNQX a" +
				"	where	exists ( select	1 from TSYS_ROLE_USER in_a" +
				"						where in_a.CZYBH = '"+czybm+"' and in_a.ROLE_ID = a.ROLE_ID )" +
				"			and a.MKDM = '"+mkdm+"' and a.GNMC = '"+gnmc+"'" +
				"	union" +
				"	select	e.GNMC from	TSYS_ROLE_AUTHORITY a" +
				"	inner join TSYS_AUTHORITY b on a.AUTHORITY_ID = b.AUTHORITY_ID" +
				"	inner join TSYS_MENU c on b.MENU_ID = c.MENU_ID" +
				"	inner join tMODULE d on d.MKMC = c.MENU_NAME" +
				"	inner join tModuleFunc e on d.MKDM = e.MKDM" +
				"								and e.GNMC = substring(b.AUTH_NAME, charindex('>', AUTH_NAME) + 1, 100)" +
				"	where	a.ROLE_ID in (select	in_a.ROLE_ID from	TSYS_ROLE_USER in_a" +
				"							where	in_a.CZYBH = '"+czybm+"') and d.MKDM = '"+mkdm+"' and e.GNMC = '"+gnmc+"' )";
		
		return userAuth;
	}
	
	public String getDeptAuthSql(UserContext uc,String tableId){
		String deptAuthSql = "";
		// 查看本人
		if("1".equals(uc.getCustRight())){
			deptAuthSql = " and exists(select 1 from tEmployee in_a where 1=1 and " +
					"	(("+tableId+".DeptType = '1' and isNull(in_a.department2,'') = '' and "+tableId+".code = in_a.Department1 ) or " +
							"("+tableId+".DeptType = '2' and isnull(in_a.department3,'')= '' ) and "+tableId+".code = in_a.Department2)" +
					")";
		}
		
		// 查看本部门
		if("2".equals(uc.getCustRight())){
			deptAuthSql =" and ( exists (select 1 from tCZYDept in_a where in_a.CZYBH = '"+uc.getCzybh()+"' and "+tableId+".deptType = '1' " +
					" and "+tableId+".code = in_a.Department1 and " +
					"						isnull(in_a.Department2 ,'')='' and isnull(in_a.Department3,'') = '')" +
					"		or exists (  select  1 from tDepartment2 in_a" +
					"			 		 left join tDepartment1 in_b on in_b.Code = in_a.Department1" +
					"					 inner join tczydept in_c on ((in_c.Department2 = in_a.Code and isnull(in_c.Department3, '') = '')" +
					"													or (in_c.Department1 = in_b.Code and isnull(in_c.Department2 ,'')= '')" +
					"												)" +
					"					 where in_a.Code = "+tableId+".Code and "+tableId+".DeptType = '2' and in_c.CZYBH = '"+uc.getCzybh()+"'))";
		}
		
		if("3".equals(uc.getCustRight())){
			deptAuthSql = " and 1=1 ";
		}
		
		return deptAuthSql;
	}
	
	public List<Map<String, Object>> getNoSignList(
			Page<Map<String, Object>> page, SignDetailEvt evt, Boolean iSAdminAssign) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select b.Address,c.NameChi WorkerName,a.ComeDate,d.LastCrtDate,e.Remark,e.Date " ;
		if(iSAdminAssign) {
			sql += " ,c.Phone ";
		}
		sql += " from tCustWorker a "
				+" left join tCustomer b on a.CustCode = b.Code "
				+" left join tWorker c on a.WorkerCode = c.Code "
				+" outer apply ( "
				+" 		select  max(in_a.CrtDate) LastCrtDate "
				+"		from    tWorkSign in_a "
                +"		where in_a.CustWkPk = a.PK and in_a.WorkerCode = a.WorkerCode "
                +" ) d "
                +" outer apply ( "
				+" 		select top 1 Remark,Date "
				+"		from    tWorkerProblem in_a "
                +"		where in_a.CustWkPk = a.PK "
				+"		order by in_a.Date desc "	
                +" ) e "
				+" where a.EndDate is null ";
		
		// 昨天 进场日期小于昨天，其它小于等于今天
		if("2".equals(evt.getDayRange())){
			sql += " and convert(nvarchar(10),a.ComeDate,112) < convert(nvarchar(10),getdate(),112) ";
		}else{
			sql += " and convert(nvarchar(10),a.ComeDate,112) <= convert(nvarchar(10),getdate(),112) ";
		}
		
		// 1.本日，2.昨日，3.本周，4.本月  不存在规定日期内的签到记录
		sql += " and not exists(select 1 from tWorkSign in_a where";
		if("1".equals(evt.getDayRange())){
			sql += " datediff(dd,in_a.CrtDate,getdate()) = 0 ";
		}else if("2".equals(evt.getDayRange())){
			sql += " datediff(dd,in_a.CrtDate,getdate()) = 1 ";
		}else if("3".equals(evt.getDayRange())){
			sql += " datediff(week,in_a.CrtDate,getdate()) = 0 ";
		}else if("4".equals(evt.getDayRange())){
			sql += " datediff(month,in_a.CrtDate,getdate()) = 0 ";
		}
		
		sql += " and in_a.WorkerCode = a.WorkerCode ) ";
		
		if(StringUtils.isNotBlank(evt.getCheckMan())){
			sql+=" and c.NameChi like ? ";
			list.add("%"+evt.getCheckMan()+"%");
		}
		if(StringUtils.isNotBlank(evt.getWorkType12Dept())){
			sql += " and c.WorkType12Dept = ? ";
			list.add(evt.getWorkType12Dept());
		}
		if(StringUtils.isNotBlank(evt.getWorkerClassify())){
			sql+=" and c.WorkerClassify = ? ";
			list.add(evt.getWorkerClassify());
		}
		if(StringUtils.isNotBlank(evt.getWorkType12())){
			sql+=" and a.WorkType12 = ? ";
			list.add(evt.getWorkType12());
		}
		sql += " order by a.WorkerCode ";
		System.out.println(sql);
		System.out.println(list);
		
		return this.findBySql(sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getPrjNoSignList(
			Page<Map<String, Object>> page, SignDetailEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.NameChi empName "
 				+" from tEmployee a "
				+" left join tDepartment1 b on a.Department1 = b.Code "
				+" left join tDepartment2 c on a.Department2 = c.Code "
				+" where a.Expired = 'F' ";
		// 1.本日，2.昨日，3.本周，4.本月  不存在规定日期内的签到记录
		sql += " and not exists(select 1 from tSignIn in_a where";
		if("1".equals(evt.getDayRange())){
			sql += " datediff(dd,in_a.CrtDate,getdate()) = 0 ";
		}else if("2".equals(evt.getDayRange())){
			sql += " datediff(dd,in_a.CrtDate,getdate()) = 1 ";
		}else if("3".equals(evt.getDayRange())){
			sql += " datediff(week,in_a.CrtDate,getdate()) = 0 ";
		}else if("4".equals(evt.getDayRange())){
			sql += " datediff(month,in_a.CrtDate,getdate()) = 0 ";
		}
		sql += " and in_a.SignCZY = a.Number ) ";
		if(StringUtils.isNotBlank(evt.getDeptType())){
			if("1".equals(evt.getDeptType())){
				sql+=" and b.code = ? ";
			} else {
				sql+=" and c.code = ? ";
			}
			list.add(evt.getNo());
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}
