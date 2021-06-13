package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AUTH;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.basic.ItemType1;
import com.house.home.entity.insales.ItemPreApp;
import com.house.home.entity.project.PrjJob;
import com.house.home.entity.project.PrjProgCheck;
import com.house.home.entity.project.SupplJob;

@SuppressWarnings("serial")
@Repository
public class PrjJobDao extends BaseDao {

	/**
	 * PrjJob分页信息
	 * 
	 * @param page
	 * @param prjJob
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjJob prjJob) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tPrjJob a where 1=1 ";

    	if (StringUtils.isNotBlank(prjJob.getNo())) {
			sql += " and a.No=? ";
			list.add(prjJob.getNo());
		}
    	if (StringUtils.isNotBlank(prjJob.getStatus())) {
			sql += " and a.Status=? ";
			list.add(prjJob.getStatus());
		}
    	if (StringUtils.isNotBlank(prjJob.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(prjJob.getCustCode());
		}
    	if (StringUtils.isNotBlank(prjJob.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(prjJob.getItemType1());
		}
    	if (StringUtils.isNotBlank(prjJob.getJobType())) {
			sql += " and a.JobType=? ";
			list.add(prjJob.getJobType());
		}
    	if (StringUtils.isNotBlank(prjJob.getAppCzy())) {
			sql += " and a.AppCZY=? ";
			list.add(prjJob.getAppCzy());
		}
    	if (prjJob.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(prjJob.getDateFrom());
		}
		if (prjJob.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(prjJob.getDateTo());
		}
    	if (StringUtils.isNotBlank(prjJob.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(prjJob.getRemarks());
		}
    	if (StringUtils.isNotBlank(prjJob.getDealCzy())) {
			sql += " and a.DealCZY=? ";
			list.add(prjJob.getDealCzy());
		}
    	if (prjJob.getPlanDate() != null) {
			sql += " and a.PlanDate=? ";
			list.add(prjJob.getPlanDate());
		}
    	if (prjJob.getDealDate() != null) {
			sql += " and a.DealDate=? ";
			list.add(prjJob.getDealDate());
		}
    	if (StringUtils.isNotBlank(prjJob.getDealRemark())) {
			sql += " and a.DealRemark=? ";
			list.add(prjJob.getDealRemark());
		}
    	if (StringUtils.isNotBlank(prjJob.getEndCode())) {
			sql += " and a.EndCode=? ";
			list.add(prjJob.getEndCode());
		}
    	if (prjJob.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(prjJob.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(prjJob.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(prjJob.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(prjJob.getExpired()) || "F".equals(prjJob.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(prjJob.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(prjJob.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findSupplPageBySql(Page<Map<String,Object>> page, PrjJob prjJob) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.pk,a.prjjobno,b.descr supplDescr,b.descr supplierDescr,x1.note statusDescr,e.NameChi appDescr,a.supplRemarks," +
				" a.date,a.LastUpdate ,a.RecvDate,a.PlanDate,a.completeDate,a.remarks,a.lastUpdatedBy from tSupplJob a " +
				" left join tSupplier b on b.Code=a.SupplCode" +
				" left join temployee e on e.Number=a.AppCZY" +
				" left join  txtdm x1 on x1.CBM=a.Status and x1.ID='SUPPLJOBSTS'" +
				" where 1=1";
		
		if(StringUtils.isNotBlank(prjJob.getNo())){
			sql+=" and  a.PrjJobNo = ? ";
			list.add(prjJob.getNo());
		}
		
		return findPageBySql(page, sql, list.toArray());

	}	
	
	public Page<Map<String,Object>> findSupplListPageBySql(Page<Map<String,Object>> page, SupplJob supplJob,String itemRight) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.PrjJobNo,i.descr item1Descr, c.address, j.descr JobTypeDescr,b.Date appdate,e.NameChi,e.Phone,d2.Desc2" +
				" , x1.note statusDescr,d.Descr supplDescr,a.date,a.RecvDate,a.PlanDate,a.CompleteDate,b.Remarks jobRemarks,a.Remarks,a.SupplRemarks  from  tSupplJob a " +
				" left join tPrjJob b on b.No=a.PrjJobNo" +
				" left join tCustomer c on c.Code=b.CustCode" +
				" left join tEmployee e on e.Number=b.AppCZY" +
				" left join tDepartment2 d2 on d2.Code=e.Department2" +
				" left join tSupplier d on d.Code=a.SupplCode" +
				" left join tItemType1 i on i.code=b.ItemType1 " +
				" left join tJobType j on j.Code=b.JobType" +
				" left join tXTDM x1 on x1.cbm=a.Status and x1.id='SUPPLJOBSTS'" +
				" left join txtdm x2 on x2.cbm=b.JobType and x2.id='PRJJOBTYPE'" +
				" where 1=1 ";
		if(StringUtils.isNotBlank(itemRight)){
			sql += " and i.code in " + "('"+itemRight.replaceAll(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(supplJob.getItemType1())){
			sql+=" and i.code = ?";
			list.add(supplJob.getItemType1());
		}
		if(StringUtils.isNotBlank(supplJob.getJobType())){
			sql+=" and b.jobType = ? ";
			list.add(supplJob.getJobType());
		}
		if(StringUtils.isNotBlank(supplJob.getSupplCode())){
			sql+=" and a.SupplCode= ?";
			list.add(supplJob.getSupplCode());
		}
		if(StringUtils.isNotBlank(supplJob.getAppCzy())){
			sql+=" and a.appCzy = ? ";
			list.add(supplJob.getAppCzy());
		}
		if(StringUtils.isNotBlank(supplJob.getStatus())){
			sql += " and a.Status in " + "('"+supplJob.getStatus().replaceAll(",", "','")+"')";
		}
		if(supplJob.getDateFrom()!=null){
			sql+=" and a.date>= ? ";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(supplJob.getDateFrom()).getTime()));
		};
		if(supplJob.getDateTo()!=null){
			sql+=" and a.date<= ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(supplJob.getDateTo()).getTime()));
		}
		if(supplJob.getRecvDateFrom()!=null){
			sql+=" and a.recvdate>= ? ";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(supplJob.getRecvDateFrom()).getTime()));
		};
		if(supplJob.getRecvDateTo()!=null){
			sql+=" and a.recvdate<= ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(supplJob.getRecvDateTo()).getTime()));
		}
		if(supplJob.getComfirmDateFrom()!=null){
			sql+=" and a.CompleteDate>= ? ";
			list.add(new Timestamp(
					DateUtil.startOfTheDay(supplJob.getComfirmDateFrom()).getTime()));
		};
		if(supplJob.getComfirmDateTo()!=null){
			sql+=" and a.CompleteDate<= ? ";
			list.add(new Timestamp(
					DateUtil.endOfTheDay(supplJob.getComfirmDateTo()).getTime()));
		}
		if(StringUtils.isNotBlank(supplJob.getAddress())){
			sql+=" and c.address like ? ";
			list.add("%"+supplJob.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(supplJob.getPrjJobNo())){
			sql+=" and a.PrjjobNo = ? ";
			list.add(supplJob.getPrjJobNo());
		}
		return findPageBySql(page, sql, list.toArray());

	}	

	public Page<Map<String, Object>> findPageBySql_forClient(
			Page<Map<String, Object>> page, PrjJob prjJob) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.No,a.Status,xt1.NOTE StatusDescr,a.CustCode,b.Address,a.ItemType1,c.Descr ItemType1Descr,"
			+"a.JobType,d.Descr JobTypeDescr,a.Date,a.EndCode,xt2.NOTE EndCodeDescr "
			+"from tprjjob a inner join tCustomer b on a.CustCode=b.Code "
			+"left join tItemType1 c on a.ItemType1=c.Code "
			+"left join tJobType d on a.JobType=d.Code "
			+"left join tXTDM xt1 on a.Status=xt1.CBM and xt1.ID='PRJJOBSTATUS' "
			+"left join tXTDM xt2 on a.EndCode=xt2.CBM and xt2.ID='PRJJOBENDCODE' ";
		if (StringUtils.isNotBlank(prjJob.getAppCzy())){
			sql += " where a.appczy=? ";
			list.add(prjJob.getAppCzy());
		}else{
			return null;
		}
		if (StringUtils.isNotBlank(prjJob.getStatus())){
			sql += " and a.status=? ";
			list.add(prjJob.getStatus());
		}
		if (StringUtils.isNotBlank(prjJob.getAddress())){
			sql += " and b.address like ? ";
			list.add("%"+prjJob.getAddress()+"%");
		}
		sql += " order by a.lastUpdate desc";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getByNo(String id) {
		String sql = "select a.No,a.Status,xt1.NOTE StatusDescr,a.CustCode,b.Address,a.ItemType1,c.Descr ItemType1Descr,"
				+"a.JobType,d.Descr JobTypeDescr,a.Date,a.EndCode,xt2.NOTE EndCodeDescr,a.PlanDate,a.DealDate,a.DealRemark,"
				+"a.DealCZY,f.ZWXM DealCzyDescr,a.remarks,g.zwxm applyEm,a.warBrand,a.cupBrand,"
				+"b.Mobile1 customerPhone,b.Descr customerDescr,em.NameChi projectManDescr,em.phone projectManPhone,"
				+"sp.Descr supplierDescr,sp.Mobile1 supplierPhone,d.IsNeedSuppl,d.IsDispCustPhn,d.CanEndCust,d.IsNeedReq "
				+"from tprjjob a inner join tCustomer b on a.CustCode=b.Code "
				+"left join tItemType1 c on a.ItemType1=c.Code "
				+"left join tJobType d on a.JobType=d.Code "
				+"left join tXTDM xt1 on a.Status=xt1.CBM and xt1.ID='PRJJOBSTATUS' "
				+"left join tXTDM xt2 on a.EndCode=xt2.CBM and xt2.ID='PRJJOBENDCODE' "
				+"left join tCZYBM g on a.appCZY=g.CZYBH "
				+"left join tCZYBM f on a.DealCZY=f.CZYBH "
				+"left join tEmployee em on b.projectMan=em.number "
				+"left join tSupplier sp on a.SupplCode=sp.code "
				+"where a.no=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{id});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public String getSeqNo(String tableName){
		return super.getSeqNo(tableName);
	}

	public Page<Map<String, Object>> getDealPrjJobList(
			Page<Map<String, Object>> page, PrjJob prjJob) {
		List<Object> list = new ArrayList<Object>();
		list.add(prjJob.getDealCzy());
		String sql="SELECT a.no, a.Date,a.dealDate, b.YWXM applyEm,c.Address,d.Descr JobTypeDescr FROM tPrjJob a "
				+"INNER JOIN tCZYBM b ON a.AppCZY=b.CZYBH "
				+"inner join tCustomer c on a.CustCode=c.Code "
				+"left join tJobType d on a.JobType=d.Code where a.dealCzy=? " ;
		if(StringUtils.isNotBlank(prjJob.getStatus())){
			if("3".equals(prjJob.getStatus())){
				sql+="and a.status in ('2','3') ";
			}else{
				sql+="and a.status=? ";
				list.add(prjJob.getStatus());
			}
		}
		if(StringUtils.isNotBlank(prjJob.getAddress())){
			sql+=" and c.address like ?";
			list.add("%"+prjJob.getAddress()+"%");
		}
		return  this.findPageBySql(page, sql, list.toArray());
	}

	public String getDefaultDealMan(String custCode, String role, String jobType) {
		String sql = "select c.EmpCode "
				+"from tCustStakeholder c where c.CustCode=? and c.Role=? "
				+"and exists(select 1 from tJobType where ChooseMan='0' and role=? and Code=?)";
		List<Map<String,Object>> list = this.findBySql(sql.toLowerCase(), new Object[]{custCode,role,role,jobType});
		if (list!=null && list.size()>0){
			return String.valueOf(list.get(0).get("empcode"));
		}
		return null;
	}

	public Page<Map<String, Object>> getPrjJobReceiveList(
			Page<Map<String, Object>> page, PrjJob prjJob,String itemRight) {
		// TODO Auto-generated method stub
		List<Object> list = new ArrayList<Object>();
		list.add(prjJob.getDealCzy());
		String sql="SELECT a.no, a.Date,a.dealDate, b.YWXM applyEm,c.Address,d.Descr JobTypeDescr FROM tPrjJob a "
				+"INNER JOIN tCZYBM b ON a.AppCZY=b.CZYBH "
				+"inner join tCustomer c on a.CustCode=c.Code "
				+"left join tJobType d on a.JobType=d.Code where (a.dealCzy=? or a.dealCzy is null or a.dealCzy = '') and a.ItemType1 in(" ;
		String[] taskType = itemRight.split(",");
		String itemRightNew = "";
		for(int i = 0;i<taskType.length;i++){
			itemRightNew += "'"+taskType[i]+"',";
		}
		sql += itemRightNew.substring(0, itemRightNew.length()-1)+")";
		if(StringUtils.isNotBlank(prjJob.getStatus())){
			sql+="and a.status=? ";
			list.add(prjJob.getStatus());
		}
		if(StringUtils.isNotBlank(prjJob.getAddress())){
			sql+=" and c.address like ?";
			list.add("%"+prjJob.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(prjJob.getJobType())){
			sql+=" and a.JobType = ?";
			list.add(prjJob.getJobType());
		}
		return  this.findPageBySql(page, sql, list.toArray());
	}
	public List<Map<String,Object>> isNeedReq(String custCode,String itemType1){
		String sql = " select * " 
					+" from tItemReq " 
					+" where Qty!=0 and CustCode=? and ItemType1=? ";
		return this.findBySql(sql, new Object[]{custCode,itemType1});
	}
	
	//    add by hc  集成测量分析   2017/11/21   begin 
	
	/**
	 * 集成测量分析
	 * @param page
	 * @param pGdxj_fx
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Page<Map<String,Object>> findPageBySql_gdxj_Tjfs(Page<Map<String,Object>> page, PrjJob prjJob) {
		Assert.notNull(prjJob);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pJccl_fx(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");//pJccl_fx 集成测量分析
			call.setInt(1, page.getPageNo());
			call.setInt(2,page.getPageSize());
			call.setString(3, prjJob.getsType());
			call.setTimestamp(4, new java.sql.Timestamp(prjJob.getAppDateform().getTime()));
			call.setTimestamp(5, new java.sql.Timestamp(prjJob.getAppDateto().getTime()));
			call.setString(6, prjJob.getProjectMan());
			call.setString(7, prjJob.getDepartment2());
			call.setString(8, prjJob.getJCDesigner());
			call.setString(9, prjJob.getDepartment2jc());
			call.setString(10, prjJob.getCustType());
			call.setString(11, prjJob.getStatus());
			call.setString(12, prjJob.getJobType());
			call.setString(13, prjJob.getDealOverTime());
			call.setTimestamp(14, new java.sql.Timestamp(prjJob.getConfirmDateLate()==null?new Date(0,0,0).getTime():prjJob.getConfirmDateLate().getTime()));
			call.setString(15, prjJob.getIntDesigner());
			call.setString(16, prjJob.getCupDesigner());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			if (!list.isEmpty()) {
				/*if ("1".equals(prjJob.getStatistcsMethod())) {
					 page.setTotalCount((Integer)list.get(0).get("totalcount"));
					}*/
			} else {
				page.setTotalCount(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	
	public Map<String,Object> getCupName(String cupBrand){
		String sql=" select Descr from tIntMeasureBrand where Code=? ";		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{cupBrand});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getWarName(String warBrand){
		String sql=" select Descr from tIntMeasureBrand where Code=? ";		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{warBrand});
		if (list!=null && list.size()>0){
			Map<String, Object> map = new HashMap<String, Object>();
			 return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String,Object>> getJcclPhotoListByNo(Page<Map<String,Object>> page, PrjJob prjJob){
		String sql = " select a.PhotoName from tPrjJobPhoto a where 1=1 ";
		List<Object> list = new ArrayList<Object>();
		if(StringUtils.isNotBlank(prjJob.getPrjJobNo())){
			sql += " AND a.prjjobno=? ";
			list.add(prjJob.getPrjJobNo());
		}
		return this.findPageBySql(page, sql,list.toArray());
	}
	
	public List<Map<String,Object>> getPhotoList(String no) {
		String sql = "select photoName from tPrjJobPhoto where PrjJobNo=?";
		return this.findBySql(sql, new Object[]{no});
	}

	public Page<Map<String, Object>> findManagePageBySql(
			Page<Map<String, Object>> page, PrjJob prjJob, UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql="select * from( select a.No, a.Status, a.CustCode, a.ItemType1, a.JobType, a.AppCZY, a.Date,   "
		          + " a.Remarks, a.DealCZY, a.PlanDate, a.DealDate, a.EndCode,a.DealRemark, e3.Phone, dp2.Desc1  appDepartment2 ,      "
		          + " c.Descr ItemType1Descr,x1.note StatusDescr,x2.note EndDescr, d.Descr JobTypeDescr,d.IsNeedSuppl,d.IsDispCustPhn,b.Address, "
		          + " e1.zwxm AppCZYDescr,e2.ZWXM DealCZYDescr,a.LastUpdate ,a.LastUpdatedBy ,a.Expired ,a.ActionLog, "
		          + " a.WarBrand,a.CupBrand,f.Descr WarBrandDescr, g.Descr CupBrandDescr ,a.SupplCode,b.Mobile1 CustPhone,b.descr CustName , sjdp1.Desc1  DesignDepartment1,"
		          + "  e5.NameChi jcDesign,  jcDept.Desc1 JCDepartment2,a.Address GPSAddress,b.CustType,x3.NOTE CustTypeDescr,i.Descr regionDescr,  "
		          + " case when (isnull(d.prjItem,'')<>'' and isnull(pi.Code,'')<>''  and ((isnull(pp.ConfirmDate,'')<>'' and pi.IsConfirm='1')) " 
		          + " or (pi.IsConfirm='0')) or (isnull(d.prjItem,'')='' and isnull(pi.Code,'')='') then '1' else '0 ' end IsConfirmed,pi.Descr prjItemDescr, "//节点是否验收 add by cjg 20190902
		          + " x4.NOTE ErrPosiDescr,dbo.fGetEmpNameChi(a.CustCode, '34') MainManDescr,l.Descr SupplDescr,k.supplremarks "
		          + " from  tPrjJob a "
		          + " left outer join tCustomer b on b.code = a.CustCode  "
		          + " left outer join tItemType1 c on c.code = a.ItemType1   "
		          + " left outer join txtdm x1 on  x1.CBM=a.Status and x1.id='PRJJOBSTATUS' "
		          + " left outer join tXTDM x2 on X2.cbm=a.EndCode and x2.id='PRJJOBENDCODE' "
		          + " left outer join tJobType  d on d.Code=a.JobType   "
		          + " left outer join tCZYBM e1 on e1.CZYBH=a.AppCZY  "
		          + " left outer join tCZYBM e2 on e2.CZYBH=a.DealCZY  "
		          + " left join tEmployee e3 on e3.Number = e1.EMNum "
		          + " left outer join tDepartment2 dp2 on dp2.Code = e3.Department2 "
//		          + ' left join tEmployee e4 on e4.Number = e2.EMNum '              //增加设计师部门
//		          + ' left outer join tDepartment1 sjdp1 on sjdp1.Code = e4.Department1 '
		          + " left join tEmployee e4 on e4.Number = b.DesignMan "             //增加设计师部门
		          + " left outer join tDepartment1 sjdp1 on sjdp1.Code = e4.Department1 "
		          + " left outer join  tIntMeasureBrand f on f.code=a.WarBrand and f.type='1' "
		          + " left outer join  tIntMeasureBrand g on g.code=a.CupBrand and g.type='2' "
//		          + " left outer join tSupplier s on s.code=a.SupplCode "
		          + " left outer  join tCustStakeholder gxr1   on gxr1.Role='11' and gxr1.CustCode= b.code "
		          + " left outer join  tEmployee e5 on e5.Number=gxr1.EmpCode "
		          + " left outer join tDepartment2 jcDept on jcDept.Code = e5.Department2 "
		          + " left join tXTDM x3 on x3.CBM = b.CustType and x3.Id='CUSTTYPE' " //增加客户类型显示 add by zb
		          + " left join tBuilder h on h.code=b.builderCode "
				  + " left join tRegion i on i.code=h.regionCode "
		          + " left join tPrjItem1 pi on d.PrjItem=pi.Code "
				  + " left join tPrjProg pp on pi.IsConfirm='1' and pi.Code=pp.PrjItem and pp.CustCode=a.CustCode "
				  + " left join tXTDM x4 on x4.CBM = a.ErrPosi and x4.Id='YESNO' " 
				  + " left join(" 
				  + " 	select max(PK)PK,PrjJobNo from tSupplJob " 
				  + "	group by PrjJobNo" 
				  + " ) j on a.No = j.PrjJobNo"
				  + " left join tSupplJob k on j.PK = k.PK "
				  + " left join tSupplier l on k.SupplCode = l.Code "
		          + " where 1=1 and (a.DealCZY="  
					+uc.getCzybh()+" or "+SqlUtil.getCustRight(uc, "b", 0)+" ) ";
		if(StringUtils.isNotBlank(prjJob.getItemType1())){
			sql += " and a.ItemType1=? ";
			list.add(prjJob.getItemType1());
		}else{
			String[] arr=uc.getItemRight().trim().split(",");
		    String itemRight="";
		    for(String str:arr) itemRight+="'"+str+"',";
		    sql+="  and a.ItemType1 in("+itemRight.substring(0,itemRight.length()-1)+") ";
		}
		if (StringUtils.isNotBlank(prjJob.getNo())){
			sql += " and a.no=? ";
			list.add(prjJob.getNo());
		}
		if (StringUtils.isNotBlank(prjJob.getAppCzy())){
			sql += " and a.appczy=? ";
			list.add(prjJob.getAppCzy());
		}
		if (StringUtils.isNotBlank(prjJob.getJobType())){
		    sql += " and a.JobType in (" + "'" + prjJob.getJobType().replaceAll(",", "','") + "'" + ") ";
		}
		if (prjJob.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(prjJob.getDateFrom());
		}
		if (prjJob.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(DateUtil.addInteger(prjJob.getDateTo(), Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(prjJob.getStatus())) {
			sql += " and a.Status in (" + prjJob.getStatus() + ")";
		}
		if (StringUtils.isNotBlank(prjJob.getAddress())){
			sql += " and b.address like ? ";
			list.add("%"+prjJob.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(prjJob.getDepartment2())){
			sql += " and e3.Department2= ? ";
			list.add(prjJob.getDepartment2());
		}
		if(StringUtils.isNotBlank(prjJob.getDepartment2jc())){
			sql += " and e5.Department2= ? ";
			list.add(prjJob.getDepartment2jc());
		}
		if (StringUtils.isNotBlank(prjJob.getDealCzy())){
			sql += " and ( a.DealCZY=? or a.DealCZY='' or a.DealCZY is null ) ";
			list.add(prjJob.getDealCzy());
		}
		if("0".equals(prjJob.getIsNotTr())){
			sql += " and a.Date<=getdate() ";
		}
		if (StringUtils.isNotBlank(prjJob.getCustStage())) {
			if ("sale".equals(prjJob.getCustStage())) {
				sql += " and (b.EndDate is null or b.EndDate>a.Date)";
			} else {
				sql += " and (b.EndDate is not null and b.EndDate<a.Date)";
			}
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.lastUpdate desc ";
		}
		System.out.println(sql);
		return  this.findPageBySql(page, sql, list.toArray());
	}

	public Result doPrjJobForProc(PrjJob prjJob) {
		Assert.notNull(prjJob);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRWGl(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, prjJob.getM_umState());
			call.setString(2, prjJob.getNo());
			call.setString(3, prjJob.getRemarks());
			call.setString(4, prjJob.getDealCzy());
			call.setTimestamp(5, prjJob.getPlanDate()==null?null:new java.sql.Timestamp(prjJob.getPlanDate().getTime()));
			call.setString(6, prjJob.getDealRemark());
			if("W".equals(prjJob.getM_umState())){
				call.setString(7, prjJob.getEndCode0());
			}else{
				call.setString(7, prjJob.getEndCode());
			}
			call.setString(8, prjJob.getSupplCode());
			call.setString(9, prjJob.getLastUpdatedBy());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			if(list!=null&&list.size()>0){
				Map<String, Object> map=list.get(0);
				result.setCode(map.get("ret").toString());
				result.setInfo(map.get("errmsg").toString());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public List<Map<String,Object>> findItemType1(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tItemType1 a where a.expired='F' ";
		/*if(StringUtils.isNotBlank((String)param.get("pCode")) ){//&& "auth".equals(auth)
			sql += " and a.code in ("+param.get("pCode")+") ";
		}*/
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findItemType1Auth(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tItemType1 a where a.expired='F' ";
		if(StringUtils.isNotBlank((String)param.get("pCode")) ){//&& "auth".equals(auth)
			sql += " and a.code in ("+param.get("pCode")+") ";
		}
		sql += " order by a.dispSeq";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findPrjType(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select rtrim(a.Code)id,a.Descr name from tJobType a where a.Expired='F' ";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.itemType1 = ? ";
			list.add((String)param.get("pCode"));
		}
		sql += " order by a.Code";
		return this.findBySql(sql, list.toArray());
	}

	public Map<String, Object> existPrjJob(String custCode, String jobType, String status){
		List<Object> params = new ArrayList<Object>();
		String sql = " select 1 from tPrjJob where 1=1 ";
		if(StringUtils.isNotBlank(custCode)){
			sql += " and CustCode=? ";
			params.add(custCode);
		}
		if(StringUtils.isNotBlank(jobType)){
			sql += " and JobType=? ";
			params.add(jobType);
		}
		if(StringUtils.isNotBlank(status)){
			sql += " and Status in ('"+status.trim().replace(",", "','")+"')";
		}
		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public boolean hasSupplJob(String prjJobNo){
		String sql=" select 1 from tSupplJob where prjJobNo = ? and expired='F' ";
		
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{prjJobNo});
		
		if (list!=null && list.size()>0){
			 return true;
		}
		return false;
	}
	/**
	 * PrjJob不分页信息
	 * 
	 * @param page
	 * @param prjJob
	 * @return
	 */
	public Page<Map<String,Object>> findAllBySql(Page<Map<String,Object>> page, PrjJob prjJob) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select b.address,a.date,a.Status,x1.note StatusDescr, x2.note EndDescr,a.JobType,d.Descr JobTypeDescr," +
				"e2.ZWXM DealCZYDescr ,a.DealDate,a.Remarks from tPrjJob a " +
				"left outer join tCustomer b on b.code = a.CustCode " +
				"left outer join tJobType d on d.Code = a.JobType  " +
				"left outer join txtdm x1 on x1.CBM = a.Status   and x1.id = 'PRJJOBSTATUS' " +
				"left outer join tXTDM x2 on X2.cbm = a.EndCode  and x2.id ='PRJJOBENDCODE' " +
				"left outer join tCZYBM e2 on e2.CZYBH = a.DealCZY   " +
				"where 1=1 and a.ItemType1 = 'JC' and a.jobtype='02' and a.CustCode=?";
		
		list.add(prjJob.getCustCode());
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")s order by s."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")s order by s.DealDate";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>> findNoSendYunPic(){
		String sql = " select top 100 PK, PhotoName "
				   + " from tPrjJobPhoto "
				   + " where (IsSendYun is null or IsSendYun = '0') and LastUpdate >= dateadd(day,-30,getdate()) "
				   + " order by PK desc ";
		return this.findBySql(sql, new Object[]{});
	}
}

