package com.house.home.dao.project;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.project.WorkerProblem;
import com.house.home.entity.project.WorkerProblemPic;

@SuppressWarnings("serial")
@Repository
public class WorkerProblemDao extends BaseDao{

	/**
	 * 分页信息
	 * @param page
	 * @param workerProblem
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkerProblem workerProblem) {
		List<Object> list = new ArrayList<Object>();	
		
		String sql = "select  * from (select a.No, c.CustCode, d.Address, c.WorkType12, " +
				"f.Descr WorkType12Descr, c.WorkerCode, e.NameChi, a.Date, x.NOTE, a.Remark, a.StopDay, " +
				"b.PicNum, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog , " +
				"e.PrjRegionCode, g.Descr PrjRegionDescr,x2.NOTE statusDescr,a.ConfirmDate,a.ConfirmCZY,a.ConfirmRemark, " +
				"a.DealDate,a.DealCZY,a.DealRemark,a.status,e1.NameChi ConfirmDescr,e2.NameChi DealDescr,c.status cwStatus,a.CustWkPk, " +
				"d2.Desc1 DeptDescr, e3.NameChi ProjectManDescr " +
				"from tWorkerProblem a " +
				"left join (select a.no, count(*) PicNum from tWorkerProblemPic a where a.Expired = 'F' group by a.No) b on a.No = b.No " +
				"left join tCustWorker c on a.CustWkPk = c.PK " +
				"left join tCustomer d on c.CustCode = d.Code " +
				"left join tWorker e on c.WorkerCode = e.Code " +
				"left join tWorkType12 f on c.WorkType12 = f.Code " +
				"left join tPrjRegion g on e.PrjRegionCode = g.Code " +
				"left join tXTDM x on a.type = x.CBM and x.ID = 'WKPBLTYPE' " +
				"left join tXTDM x2 on a.status = x2.CBM and x2.ID = 'PRJPROMSTATUS' " +
				"left join tEmployee e1 on a.ConfirmCZY = e1.Number " +
				"left join tEmployee e2 on a.DealCZY = e2.Number " +
				"left join tEmployee e3 on d.ProjectMan = e3.Number " +
				"left join tDepartment2 d2 on e3.Department2 = d2.Code " +
				"where 1=1 ";
		if (StringUtils.isNotBlank(workerProblem.getNo())) {
			sql += " and a.No=? ";
			list.add(workerProblem.getNo());
		}
		if (StringUtils.isNotBlank(workerProblem.getAddress())) {
			sql += " and d.Address like ? ";
			list.add("%"+workerProblem.getAddress()+"%");
		}
		if (workerProblem.getDateFrom() != null) {
			sql += " and a.Date >= ? ";
			list.add(workerProblem.getDateFrom());
		}
		if (workerProblem.getDateTo() != null) {
			sql += " and a.Date < ? ";
			list.add(DateUtil.addInteger(workerProblem.getDateTo(), Calendar.DATE, 1));/*增加日期中某类型的某数值。如增加日期*/
		}
		if (StringUtils.isNotBlank(workerProblem.getWorkType12())) {
			sql += " and c.WorkType12 = ? ";
			list.add(workerProblem.getWorkType12());
		}
		if (StringUtils.isNotBlank(workerProblem.getWorkerCode())) {
			sql += " and c.WorkerCode = ? ";
			list.add(workerProblem.getWorkerCode());
		}
		if (StringUtils.isNotBlank(workerProblem.getPrjRegionCode())) {
			sql += " and e.PrjRegionCode = ? ";
			list.add(workerProblem.getPrjRegionCode());
		}
		if(StringUtils.isNotBlank(workerProblem.getDepartment2())){
			sql += " and e3.department2 in (" + SqlUtil.resetStatus(workerProblem.getDepartment2()) + ")" ;
		}
		if (StringUtils.isNotBlank(workerProblem.getProjectMan())) {
			sql += " and d.ProjectMan = ? ";
			list.add(workerProblem.getProjectMan());
		}
		if (StringUtils.isBlank(workerProblem.getExpired())||"F".equals(workerProblem.getExpired())) {
			sql += " and a.expired = 'F' ";
		}
		if (StringUtils.isNotBlank(workerProblem.getStatus())) {
			sql += " and a.status in " + "('"
					+ workerProblem.getStatus().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " ) a order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 问题反馈图片分页信息
	 * @param page
	 * @param workerProblem
	 * @return
	 */
	public Page<Map<String, Object>> findPicPageBySql(Page<Map<String, Object>> page,
			WorkerProblem workerProblem) {
		List<Object> list = new ArrayList<Object>();
		String sql = 
				"select a.no, a.PK, a.PhotoName, a.LastUpdate, a.Expired " +
				"from tWorkerProblemPic a " +
				"where  1 = 1 ";
		
		if (StringUtils.isNotBlank(workerProblem.getNo())) {
			sql += " and a.No=? ";
			list.add(workerProblem.getNo());
		}
		if (StringUtils.isBlank(workerProblem.getExpired())||"F".equals(workerProblem.getExpired())) {
			sql += " and a.expired = 'F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " order by a.LastUpdate desc ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String , Object>> getWorkerPicList(String no) {
		String sql = " select PhotoName from tWorkerProblemPic  where no = ? ";
		List<Map<String , Object>> list = this.findBySql(sql, new Object[]{no});
		if (list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	public List<Map<String , Object>> getConfirmPicList(String no) {
		String sql = " select * from tPrjProgPhoto where RefNo= ? ";
		List<Map<String , Object>> list = this.findBySql(sql, new Object[]{no});
		if (list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	public void deletePic(String no) {
		String sql = " delete from tWorkerProblemPic where no = ? ";
		this.executeUpdateBySql(sql, new Object[]{no});
	}
	
	public WorkerProblemPic getByPhotoName(String id) {
		String hql = "from WorkerProblemPic where photoName=? ";
		List<WorkerProblemPic> list = this.find(hql, new Object[]{id});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> findNoSendYun(){
		String sql = " select top 100 PK, PhotoName "
				   + " from tWorkerProblemPic "
				   + " where (IsSendYun is null or IsSendYun = '0') and LastUpdate >= dateadd(day,-30,getdate()) "
				   + " order by PK desc"; 
		return this.findBySql(sql, new Object[]{});
	}
	/**
	 * 确认
	 * @param workerProblem
	 */
	public void doConfirm(WorkerProblem workerProblem) {
		String sql = " update tWorkerProblem set confirmCZY=?,confirmDate=getdate(),confirmRemark=?," 
				+"lastupdate=getdate(),lastupdatedby=?,actionlog='Edit',status='2'  where no = ? ";
		String sql2="update tCustWorker set status=? where PK=? ";
		this.executeUpdateBySql(sql, new Object[]{workerProblem.getConfirmCzy(),
				workerProblem.getConfirmRemark(),workerProblem.getLastUpdatedBy(),workerProblem.getNo()});
		this.executeUpdateBySql(sql2, new Object[]{workerProblem.getCwStatus(),workerProblem.getCustWkPk()});
	}
	/**
	 * 处理
	 * @param workerProblem
	 */
	public void doDeal(WorkerProblem workerProblem) {
		String sql = " update tWorkerProblem set dealCZY=?,dealDate=getdate(),dealRemark=?," 
				+"lastupdate=getdate(),lastupdatedby=?,actionlog='Edit',status='4'  where no = ? ";
		String sql2="update tCustWorker set status=? where PK=? ";
		this.executeUpdateBySql(sql, new Object[]{workerProblem.getDealCzy(),
				workerProblem.getDealRemark(),workerProblem.getLastUpdatedBy(),workerProblem.getNo()});
		this.executeUpdateBySql(sql2, new Object[]{workerProblem.getCwStatus(),workerProblem.getCustWkPk()});
	}
	/**
	 * 根据No查工人问题明细
	 * @author cjg
	 * @date 2019-11-15
	 * @param workerProblem
	 * @return
	 */
	public Map<String, Object> getWorkerProblemDetail(String no){
		String sql = "  select c.CustCode,a.Status,a.No,f.NOTE typeDescr,d.Address,a.Remark,b.PhotoName "
			 +"from tWorkerProblem a  "
			 +"left join tWorkerProblemPic b on a.No=b.No "
			 +"left join tCustWorker c on a.CustWkPk=c.PK "
			 +"left join tCustomer d on c.CustCode=d.Code "
			 +"left join tXTDM f on a.Type=f.CBM and f.ID='WKPBLTYPE'"
			 +"where a.No=? "; 
		return this.findBySql(sql, new Object[]{no}).get(0);
	}
}
