package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.JobType;

@SuppressWarnings("serial")
@Repository
public class JobTypeDao extends BaseDao {

	/**
	 * JobType分页信息
	 * 
	 * @param page
	 * @param jobType
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, JobType jobType) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tJobType a where 1=1 ";

    	if (StringUtils.isNotBlank(jobType.getCode())) {
			sql += " and a.Code=? ";
			list.add(jobType.getCode());
		}
    	if (StringUtils.isNotBlank(jobType.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(jobType.getItemType1());
		}
    	if (StringUtils.isNotBlank(jobType.getDescr())) {
			sql += " and a.Descr=? ";
			list.add(jobType.getDescr());
		}
    	if (jobType.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(jobType.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(jobType.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(jobType.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(jobType.getExpired()) || "F".equals(jobType.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(jobType.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(jobType.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	public List<Map<String, Object>> getPrjJobTypeList(String itemRight) {
		String sql = "select * from tJobType where ItemType1 in(";
		String[] taskType = itemRight.split(",");
		String itemRightNew = "";
		for(int i = 0;i<taskType.length;i++){
			itemRightNew += "'"+taskType[i]+"',";
		}
		sql += itemRightNew.substring(0, itemRightNew.length()-1)+")";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{});
		if (list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	/**
	 * ERP主页查询
	 * @author	created by zb
	 * @date	2019-9-4--上午10:14:27
	 * @param page
	 * @param jobType
	 * @return
	 */
	public Page<Map<String, Object>> findERPPageBySql(
			Page<Map<String, Object>> page, JobType jobType) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.Code,a.ItemType1,b.Descr ItemType1Descr,a.Descr,a.ChooseMan,c.NOTE ChooseManDescr, "
					+"a.Department1,d.Desc1 Department1Descr,a.Department2,d2.Desc1 Department2Descr,dbo.fGetJobTypePosition(a.Code) Position, "
					+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.remarks,a.IsJobDepart,c2.NOTE IsJobDepartDescr, "
					+"a.role,e.Descr RoleDescr,a.IsNeedSuppl,c3.NOTE IsNeedSupplDescr,a.IsDispCustPhn,c4.NOTE IsDispCustPhnDescr, "
					+"a.CanEndCust,c5.NOTE CanEndCustDescr,a.IsNeedPic, c6.NOTE IsNeedPicDescr, "
					+"case when a.IsNeedReq='0' then '不控制' else '无需求不允许申请' end IsNeedReqDescr,a.Prjitem, "
					+"c7.NOTE PrjItemDescr,c8.NOTE IsCupboardDescr,c9.NOTE IsMaterialSendJobDescr "
					+"from tJobType a "
					+"left join tItemType1 b on b.Code=a.ItemType1 "
					+"left join tDepartment1 d on d.Code=a.Department1 "
					+"left join tDepartment2 d2 on d2.Code=a.Department2 "
					+"left join tRoll e on e.Code=a.role "
					+"left join tXTDM c on c.ID='YESNO' and a.ChooseMan=c.CBM "
					+"left join tXTDM c2 on c2.ID='YESNO' and c2.CBM=a.IsJobDepart "
					+"left join tXTDM c3 on c3.ID='YESNO' and c3.CBM=a.IsNeedSuppl "
					+"left join tXTDM c4 on c4.ID='YESNO' and c4.CBM=a.IsDispCustPhn "
					+"left join tXTDM c5 on c5.ID='YESNO' and c5.CBM=a.CanEndCust "
					+"left join tXTDM c6 on c6.ID='YESNO' and c6.CBM=a.IsNeedPic "
					+"left join tXTDM c7 on c7.ID='PRJITEM' and c7.CBM=a.Prjitem "
					+"left join tXTDM c8 on c8.ID='YESNO' and c8.CBM=a.IsCupboard "
					+"left join tXTDM c9 on c9.ID='YESNO' and c9.CBM=a.IsMaterialSendJob " 
					+"where 1=1 ";

    	if (StringUtils.isNotBlank(jobType.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+jobType.getCode()+"%");
		}
    	if (StringUtils.isNotBlank(jobType.getItemType1())) {
			sql += " and a.ItemType1 in ('"+jobType.getItemType1().replace(",", "','")+"')";
		}
    	if (StringUtils.isNotBlank(jobType.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+jobType.getDescr()+"%");
		}
    	if (jobType.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(jobType.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(jobType.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(jobType.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(jobType.getExpired()) || "F".equals(jobType.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(jobType.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(jobType.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Code";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
}

