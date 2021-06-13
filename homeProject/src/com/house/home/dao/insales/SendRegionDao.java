package com.house.home.dao.insales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.insales.SendRegion;

@SuppressWarnings("serial")
@Repository
public class SendRegionDao extends BaseDao {

	/**
	 * SendRegion分页信息
	 * 
	 * @param page
	 * @param sendRegion
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SendRegion sendRegion) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.No, a.Descr, a.TransFee, a.DistanceType, a.Remarks,x1.NOTE  DistanceTypeDescr "
				+ "from tSendRegion  a "
				+ "left outer join tXTDM x1 on x1.CBM=a.DistanceType and x1.id='DistanceType' where 1=1";

		if (StringUtils.isNotBlank(sendRegion.getNo())) {
			sql += " and a.No=? ";
			list.add(sendRegion.getNo());
		}
		if (StringUtils.isNotBlank(sendRegion.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+sendRegion.getDescr()+"%");
		}
		if (sendRegion.getTransFee() != null) {
			sql += " and a.TransFee=? ";
			list.add(sendRegion.getTransFee());
		}
		if (StringUtils.isNotBlank(sendRegion.getDistanceType())) {
			sql += " and a.DistanceType=? ";
			list.add(sendRegion.getDistanceType());
		}
		if (StringUtils.isNotBlank(sendRegion.getRemarks())) {
			sql += " and a.Remarks like ? ";
			list.add("%"+sendRegion.getRemarks()+"%");
		}
		if (sendRegion.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(sendRegion.getLastUpdate());
		}
		if (StringUtils.isNotBlank(sendRegion.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(sendRegion.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(sendRegion.getExpired())
				|| "F".equals(sendRegion.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(sendRegion.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(sendRegion.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: 配送区域分页查询
	 * @author	created by zb
	 * @date	2018-9-6--下午12:16:59
	 * @param page
	 * @param sendRegion
	 * @return
	 */
	public Page<Map<String, Object>> findSendRegionPageBySql(
			Page<Map<String, Object>> page, SendRegion sendRegion) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.No, a.Descr, a.TransFee, a.DistanceType, a.SendType, " +
				" x2.NOTE SendTypeDescr, a.Remarks,x1.NOTE DistanceTypeDescr, " +
				" a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog,a.SoftTransFee "+ 
				" from tSendRegion  a "+ 
				" left outer join tXTDM x1 on x1.CBM=a.DistanceType and x1.id='DistanceType' " +
				" left outer join tXTDM x2 on x2.CBM=a.SendType and x2.id='ITEMAPPSENDTYPE' " +
				" where 1=1";

		if (StringUtils.isNotBlank(sendRegion.getNo())) {
			sql += " and a.No like ? ";
			list.add("%"+sendRegion.getNo()+"%");
		}
		if (StringUtils.isNotBlank(sendRegion.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+sendRegion.getDescr()+"%");
		}
		if (StringUtils.isBlank(sendRegion.getExpired())
				|| "F".equals(sendRegion.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.No desc ";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * @Description: TODO 根据传入的表名，和java类判断是否存在descr
	 * @author	created by zb
	 * @date	2018-9-6--下午12:19:10
	 * @param tableName 表名
	 * @param sendRegion java类
	 * @return true：存在
	 */
	public boolean hasDescr(String tableName, SendRegion sendRegion) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select 1 from "+ tableName +" where Descr = ? ";
		list.add(sendRegion.getDescr());
		List<Map<String, Object>> page = this.findBySql(sql, list.toArray());
		return !page.isEmpty();
	}
}
