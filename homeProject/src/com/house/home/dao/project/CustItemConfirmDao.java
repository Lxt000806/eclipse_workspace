package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.driver.ItemSendBatch;
import com.house.home.entity.project.CustItemConfirm;

@SuppressWarnings("serial")
@Repository
public class CustItemConfirmDao extends BaseDao {

	/**
	 * CustItemConfirm分页信息
	 * 
	 * @param page
	 * @param custItemConfirm
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfirm custItemConfirm) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tCustItemConfirm a where 1=1 ";

    	if (custItemConfirm.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(custItemConfirm.getPk());
		}
    	if (StringUtils.isNotBlank(custItemConfirm.getCustCode())) {
			sql += " and a.CustCode=? ";
			list.add(custItemConfirm.getCustCode());
		}
    	if (StringUtils.isNotBlank(custItemConfirm.getConfItemType())) {
			sql += " and a.ConfItemType=? ";
			list.add(custItemConfirm.getConfItemType());
		}
    	if (StringUtils.isNotBlank(custItemConfirm.getItemConfStatus())) {
			sql += " and a.ItemConfStatus=? ";
			list.add(custItemConfirm.getItemConfStatus());
		}
    	if (custItemConfirm.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(custItemConfirm.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(custItemConfirm.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(custItemConfirm.getLastUpdatedBy());
		}
    	if (StringUtils.isNotBlank(custItemConfirm.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(custItemConfirm.getActionLog());
		}
		if (StringUtils.isBlank(custItemConfirm.getExpired()) || "F".equals(custItemConfirm.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	public String getSeqNo(String tableName){
		return super.getSeqNo(tableName);
	}
	public CustItemConfirm getCustItemConfirm(String custCode,
			String confItemType) {
		String hql=" from CustItemConfirm WHERE custCode=? AND confItemType=?";
		List<CustItemConfirm> list=this.find(hql, new Object[]{custCode,confItemType});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public Page<Map<String, Object>> findConfirmResultPageBySql(
			Page<Map<String, Object>> page, String custCode) {
		String sql=	"SELECT b.Descr confItemdescr, CASE WHEN c.ItemConfStatusDescr IS NULL THEN '未确认' ELSE c.ItemConfStatusDescr END ItemConfStatusDescr,a.Code,a.descr ConfItemTypeDescr,b.code ItemTimeCode,b.descr ItemTimeDescr,c.ConfirmDate FROM  tConfItemType a "
				+"LEFT  JOIN tConfItemTime b ON a.ItemTimeCode=b.Code "
				+"LEFT  JOIN (SELECT  d.NOTE ItemConfStatusDescr,b.Code,b.Descr ConfItemTypeDescr,b.ItemTimeCode,c.ConfirmDate FROM  tCustItemConfirm a "
				+" LEFT JOIN tConfItemType b ON a.ConfItemType=b.Code "
				+" LEFT JOIN tCustItemConfDate c ON c.CustCode=a.CustCode AND c.ItemTimeCode=b.ItemTimeCode "
			    +"  LEFT  JOIN tXTDM d ON a.ItemConfStatus=d.CBM AND  d.ID='ITEMCONFSTS' "
			    +" WHERE a.CustCode=?) c ON a.Code=c.Code where a.ItemType1='ZC' order by b.code ";
		return this.findPageBySql(page, sql, new Object[]{custCode});
	}
	public Page<Map<String, Object>> findConfirmStatusPageBySql(
			Page<Map<String, Object>> page, String custCode) {
		String sql=" SELECT b.ConfirmDate,c.NOTE ItemConfStatusDescr,e.Descr ConfItemTypeDescr FROM  tCustItemConfProgDt a "
			    +" LEFT JOIN dbo.tCustItemConfProg b ON  a.ConfProgNo=b.No "
			    +" LEFT  JOIN tXTDM c ON b.ItemConfStatus=c.CBM AND  c.ID='ITEMCONFSTS' "
			    +" LEFT JOIN tConfItemType e ON a.ConfItemType=e.Code "
			   +"  WHERE EXISTS (SELECT 1 FROM  tCustItemConfProg d WHERE d.No=a.ConfProgNo AND d.CustCode=?) ";
		return this.findPageBySql(page, sql, new Object[]{custCode});
	}
	/**
	 * 查询客户材料选定信息（工地进度拖延分析查看用）
	 * @author	created by zb
	 * @date	2019-11-8--下午2:45:14
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Page<Map<String, Object>> findCustConfirmResultPageBySql(
			Page<Map<String, Object>> page, String custCode) {
		String sql=	"SELECT b.Descr confItemdescr, CASE WHEN c.ItemConfStatusDescr IS NULL THEN '未确认' "
				   +"ELSE c.ItemConfStatusDescr END ItemConfStatusDescr,a.Code,a.descr ConfItemTypeDescr, "
				   +"b.code ItemTimeCode,b.descr ItemTimeDescr,c.ConfirmDate,c.CustConfirmDate "
				   +"FROM  tConfItemType a "
				   +"LEFT  JOIN tConfItemTime b ON a.ItemTimeCode=b.Code "
				   +"LEFT  JOIN ( "
				   +"  SELECT  d.NOTE ItemConfStatusDescr,b.Code,b.Descr ConfItemTypeDescr,b.ItemTimeCode,c.ConfirmDate, "
				   +"  e.ConfirmDate CustConfirmDate "
				   +"  FROM  tCustItemConfirm a "
				   +"  LEFT JOIN tConfItemType b ON a.ConfItemType=b.Code "
				   +"  LEFT JOIN tCustItemConfDate c ON c.CustCode=a.CustCode AND c.ItemTimeCode=b.ItemTimeCode "
		    	   +"  LEFT  JOIN tXTDM d ON a.ItemConfStatus=d.CBM AND  d.ID='ITEMCONFSTS' "
				   +"  left join ( "
				   +" 	 select in_a.CustCode,in_a.ConfItemType,max(in_a.LastUpdate) ConfirmDate "
				   +" 	 from tCustItemConfirm in_a "
				   +" 	 where in_a.ItemConfStatus='2' "
				   +" 	 group by in_a.CustCode,in_a.ConfItemType "
				   +"  )e on e.CustCode=a.CustCode and e.ConfItemType=a.ConfItemType "
		    	   +"  WHERE a.CustCode=? "
		    	   +") c ON a.Code=c.Code order by b.code ";
		return this.findPageBySql(page, sql, new Object[]{custCode});
	}
}

