package com.house.home.dao.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.driver.ItemReturnArrive;

@SuppressWarnings("serial")
@Repository
public class ItemReturnArriveDao extends BaseDao {

	/**
	 * ItemReturnArrive分页信息
	 * 
	 * @param page
	 * @param itemReturnArrive
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemReturnArrive itemReturnArrive) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tItemReturnArrive a where 1=1 ";

		if (StringUtils.isNotBlank(itemReturnArrive.getNo())) {
			sql += " and a.No=? ";
			list.add(itemReturnArrive.getNo());
		}
		if (StringUtils.isNotBlank(itemReturnArrive.getReturnNo())) {
			sql += " and a.ReturnNo=? ";
			list.add(itemReturnArrive.getReturnNo());
		}
		if (StringUtils.isNotBlank(itemReturnArrive.getDriverCode())) {
			sql += " and a.DriverCode=? ";
			list.add(itemReturnArrive.getDriverCode());
		}
		if (StringUtils.isNotBlank(itemReturnArrive.getAddress())) {
			sql += " and a.Address=? ";
			list.add(itemReturnArrive.getAddress());
		}
		if (StringUtils.isNotBlank(itemReturnArrive.getDriverRemark())) {
			sql += " and a.DriverRemark=? ";
			list.add(itemReturnArrive.getDriverRemark());
		}
		if (itemReturnArrive.getArriveDate() != null) {
			sql += " and a.ArriveDate=? ";
			list.add(itemReturnArrive.getArriveDate());
		}
		if (StringUtils.isNotBlank(itemReturnArrive.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(itemReturnArrive.getActionLog());
		}
		if (itemReturnArrive.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(itemReturnArrive.getLastUpdate());
		}
		if (StringUtils.isNotBlank(itemReturnArrive.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(itemReturnArrive.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(itemReturnArrive.getExpired()) || "F".equals(itemReturnArrive.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> findByReturnNo(String returnNo) {
		// TODO Auto-generated method stub
		String sql="SELECT No FROM tItemReturnArrive WHERE ReturnNo=? ";
		return this.findBySql(sql, new Object[]{returnNo});
	}

	public Page<Map<String,Object>> findArriveByNo(Page<Map<String,Object>> page, ItemReturnArrive itemReturnArrive) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select  h.No,h.ArriveDate,h.address ,h.DriverRemark "   
           +" from  tItemReturn a "  
           +" left outer join tItemReturnArrive h on h.ReturnNo= a.No " 
           +" where a.no=? "
           +" order by  a.No   ";
		list.add(itemReturnArrive.getNo());
		return this.findPageBySql(page, sql, list.toArray());
	}
}

