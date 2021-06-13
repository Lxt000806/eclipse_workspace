package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.PrjWithHold;

@SuppressWarnings("serial")
@Repository
public class PrjWithHoldDao extends BaseDao {

	/**
	 * PrjWithHold分页信息
	 * 
	 * @param page
	 * @param prjWithHold
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjWithHold prjWithHold) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tPrjWithHold a where 1=1 ";

    	if (prjWithHold.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(prjWithHold.getPk());
		}
    	if (StringUtils.isNotBlank(prjWithHold.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(prjWithHold.getCustCode());
		}
    	if (StringUtils.isNotBlank(prjWithHold.getWorkType2())) {
			sql += " and a.WorkType2=? ";
			list.add(prjWithHold.getWorkType2());
		}
    	if (StringUtils.isNotBlank(prjWithHold.getType())) {
			sql += " and a.Type=? ";
			list.add(prjWithHold.getType());
		}
    	if (prjWithHold.getAmount() != null) {
			sql += " and a.Amount=? ";
			list.add(prjWithHold.getAmount());
		}
    	if (StringUtils.isNotBlank(prjWithHold.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(prjWithHold.getRemarks());
		}
    	if (prjWithHold.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(prjWithHold.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(prjWithHold.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(prjWithHold.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(prjWithHold.getExpired()) || "F".equals(prjWithHold.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(prjWithHold.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(prjWithHold.getActionLog());
		}
    	if (StringUtils.isNotBlank(prjWithHold.getItemAppNo())) {
			sql += " and a.ItemAppNo=? ";
			list.add(prjWithHold.getItemAppNo());
		}
    	if (StringUtils.isNotBlank(prjWithHold.getIsCreate())) {
			sql += " and a.IsCreate=? ";
			list.add(prjWithHold.getIsCreate());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_ykd(
			Page<Map<String, Object>> page, PrjWithHold prjWithHold) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.pk,a.CustCode,a.WorkType2,a.Amount,w1.Descr workType1Descr,w2.Descr workType2Descr,"
		+"isnull(b.ConfirmAmount,0)+isnull(c.AppAmount,0) ysqje,a.Amount-isnull(b.ConfirmAmount,0)-isnull(c.AppAmount,0) bcsqje "
		+"from tPrjWithHold a "
		+"left join tWorkType2 w2 on a.WorkType2=w2.Code "
		+"left join tWorkType1 w1 on w2.WorkType1=w1.Code "
		+"left join ( "
		+"select b.WithHoldNo,sum(b.ConfirmAmount) ConfirmAmount from tWorkCostDetail b where b.Status<>'3' "
		+"group by b.WithHoldNo) b on a.PK=b.WithHoldNo "
		+"left join ( "
		+"select c.WithHoldNo,sum(c.AppAmount) AppAmount from tPreWorkCostDetail c where c.Status in ('1','2','3','4') "
		+"group by c.WithHoldNo) c on a.pk=c.WithHoldNo "
		+"where a.CustCode=? and a.WorkType2=? ";

    	if (StringUtils.isBlank(prjWithHold.getCustCode()) || StringUtils.isBlank(prjWithHold.getWorkType2())) {
			return null;
		}
		list.add(prjWithHold.getCustCode());
		list.add(prjWithHold.getWorkType2());
		if (StringUtils.isNotBlank(prjWithHold.getWorkType2Descr())){
			sql += " and w2.Descr like ? ";
			list.add("%"+prjWithHold.getWorkType2Descr()+"%");
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> findByPk(int pk) {
		String sql = "select a.pk,a.CustCode,a.WorkType2,a.Amount,w1.Descr workType1Descr,w2.Descr workType2Descr,"
		+"isnull(b.ConfirmAmount,0)+isnull(c.AppAmount,0) ysqje,a.Amount-isnull(b.ConfirmAmount,0)-isnull(c.AppAmount,0) bcsqje "
		+"from tPrjWithHold a "
		+"left join tWorkType2 w2 on a.WorkType2=w2.Code "
		+"left join tWorkType1 w1 on w2.WorkType1=w1.Code "
		+"left join ( "
		+"select b.WithHoldNo,sum(b.ConfirmAmount) ConfirmAmount from tWorkCostDetail b where b.Status<>'3' "
		+"group by b.WithHoldNo) b on a.PK=b.WithHoldNo "
		+"left join ( "
		+"select c.WithHoldNo,sum(c.AppAmount) AppAmount from tPreWorkCostDetail c where c.Status in ('1','2','3','4') "
		+"group by c.WithHoldNo) c on a.pk=c.WithHoldNo "
		+"where a.pk=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * PrjWithHold分页信息
	 * 
	 * @param page
	 * @param prjWithHold
	 * @return
	 */
	public Page<Map<String,Object>> findCodePageBySql(Page<Map<String,Object>> page, PrjWithHold prjWithHold) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select convert(varchar(20),a.PK) PK,a.CustCode,b.Descr CustDescr,b.Address,d.Code WorkType1,d.Descr WorkType1Descr,"
                      +"a.WorkType2,c.Descr WorkType2Descr,a.Type,x1.NOTE TypeDescr,a.Amount,a.Remarks,"
                      +"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
                      +"from tPrjWithHold a "
                      +"left outer join tCustomer b on b.Code = a.CustCode "
                      +"left outer join tWorkType2 c on c.Code = a.WorkType2 "
                      +"left outer join tWorkType1 d on d.Code = c.WorkType1 "
                      +"left outer join tXTDM  x1 on x1.CBM=a.Type and x1.ID='PRJWITHHOLDTYPE' " 
                      +"where 1=1 ";

    	if (StringUtils.isNotBlank(prjWithHold.getAddress())) {
			sql += " and b.address like ? ";
			list.add("%"+prjWithHold.getAddress()+"%");
		}
    	if (StringUtils.isNotBlank(prjWithHold.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(prjWithHold.getCustCode());
		}
    	if (StringUtils.isNotBlank(prjWithHold.getWorkType2())) {
			sql += " and a.WorkType2=? ";
			list.add(prjWithHold.getWorkType2());
		}
    	if (StringUtils.isNotBlank(prjWithHold.getWorkType1())) {
			sql += " and c.WorkType1=? ";
			list.add(prjWithHold.getWorkType1());
		}
    	if (StringUtils.isNotBlank(prjWithHold.getType())) {
			sql += " and a.Type=? ";
			list.add(prjWithHold.getType());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")s order by s."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")s order by s.Pk";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
}

