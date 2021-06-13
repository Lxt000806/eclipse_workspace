package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.project.ItemPreMeasure;

@SuppressWarnings("serial")
@Repository
public class ItemPreMeasureDao extends BaseDao {

	/**
	 * ItemPreMeasure分页信息
	 * 
	 * @param page
	 * @param itemPreMeasure
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.PK,a.SupplCode,a.Status,xt.Note statusDescr,a.Remarks,a.PreAppNo,"
				+"a.appCzy,c1.zwxm appCzyDescr,a.date,a.confirmCzy,c2.zwxm confirmCzyDescr,a.confirmDate,"
				+"a.MeasureRemark,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,c.address,a.recvDate,"
				+"ee.NameChi,ee.Phone,ct.Desc1 CustTypeDescr "
				+"from tItemPreMeasure a "
				+"inner join tItemPreApp b on a.PreAppNo=b.No "
				+"left join tCustomer c on b.CustCode=c.Code "
				+"left join tCzybm c1 on a.appczy=c1.czybh "
				+"left join tCzybm c2 on a.confirmCzy=c2.czybh "
				+"left join tEmployee ee on c.ProjectMan=ee.Number "
				+"left join tCustType ct on c.CustType=ct.Code "
				+"left join tXTDM xt on a.Status=xt.CBM and xt.ID='MEASURESTATUS' where 1=1 ";

    	if (itemPreMeasure.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(itemPreMeasure.getPk());
		}
    	if (StringUtils.isNotBlank(itemPreMeasure.getAddress())) {
			sql += " and c.address like ? ";
			list.add("%"+itemPreMeasure.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(itemPreMeasure.getSupplCode())) {
			sql += " and a.SupplCode=? ";
			list.add(itemPreMeasure.getSupplCode());
		}else{
			return null;
		}
    	if (StringUtils.isNotBlank(itemPreMeasure.getStatus())) {
			sql += " and a.Status in ("+SqlUtil.resetStatus(itemPreMeasure.getStatus())+") ";
		}
    	if (StringUtils.isNotBlank(itemPreMeasure.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(itemPreMeasure.getRemarks());
		}
    	if (StringUtils.isNotBlank(itemPreMeasure.getPreAppNo())) {
			sql += " and a.PreAppNo=? ";
			list.add(itemPreMeasure.getPreAppNo());
		}
    	if (StringUtils.isNotBlank(itemPreMeasure.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemPreMeasure.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemPreMeasure.getExpired()) || "F".equals(itemPreMeasure.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(itemPreMeasure.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemPreMeasure.getActionLog());
		}
    	if (itemPreMeasure.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemPreMeasure.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(itemPreMeasure.getMeasureRemark())) {
			sql += " and a.MeasureRemark=? ";
			list.add(itemPreMeasure.getMeasureRemark());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public int getMessageCount(String supplCode) {
		String sql = "select count(*) num from tItemPreMeasure where SupplCode=? and Status='1' and Expired='F'";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{supplCode});
		if (list!=null && list.size()>0){
			return Integer.parseInt(String.valueOf(list.get(0).get("num")));
		}
		return 0;
	}
	
	public Map<String, Object> getByPk(Integer pk) {
		String sql = "select a.*,c.Address,ee.NameChi projectManDescr,ee.Phone "
				+"from tItemPreMeasure a "
				+"inner join tItemPreApp b on a.PreAppNo=b.No "
				+"left join tCustomer c on b.CustCode=c.Code "
				+"left join tEmployee ee on c.ProjectMan=ee.Number where pk=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public int getPrjJobMessageCount(String supplCode) {
		String sql = "select count(*) num from tSupplJob where SupplCode=? and Status='0' and Expired='F'";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{supplCode});
		if (list!=null && list.size()>0){
			return Integer.parseInt(String.valueOf(list.get(0).get("num")));
		}
		return 0;
	}
}

