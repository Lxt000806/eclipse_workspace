package com.house.home.dao.basic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.CutFeeSet;

import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class CutFeeSetDao extends BaseDao{

	/**
	 * @Description: TODO 切割费设置分页查询
	 * @author	created by zb
	 * @date	2018-10-22--上午10:21:59
	 * @param page
	 * @param cutFeeSet
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CutFeeSet cutFeeSet) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from(select a.CutType,tx.NOTE CutTypeDescr,a.Size,a.CutFee,a.SupplCutFee,a.FactCutFee," +
				" a.AllowModify,tx1.NOTE AllowModifyDescr,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired " +
				" from tCutFeeSet a " +
				" left join tXTDM tx on tx.CBM = a.CutType and tx.ID = 'CUTTYPE' " +
				" left join tXTDM tx1 on tx1.CBM = a.AllowModify and tx1.ID = 'YESNO' " +
				" where 1=1 ";
		
		if (StringUtils.isBlank(cutFeeSet.getExpired()) || "F".equals(cutFeeSet.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(cutFeeSet.getCutType())) {
			sql += " and a.CutType = ? ";
			list.add(cutFeeSet.getCutType());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " )a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * @Description: TODO 查询切割类型和瓷砖尺寸是否重复
	 * @author	created by zb
	 * @date	2018-10-22--上午11:36:33
	 * @param cutType
	 * @param size
	 * @return
	 */
	public boolean checkCode(String cutType, String size) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select 1 from tCutFeeSet where cutType=? and size=?";
		list.add(cutType);
		list.add(size);
		List<Map<String, Object>> page = this.findBySql(sql, list.toArray());
		return !page.isEmpty();
	}

	/**
	 * @Description: TODO 删除操作
	 * @author	created by zb
	 * @date	2018-10-22--下午3:02:56
	 * @param cutFeeSet
	 * @return
	 */
	public Boolean doDelete(CutFeeSet cutFeeSet) {
		Long result = (long) 0;
		try {
			List<Object> list = new ArrayList<Object>();
			String sql = " delete from tCutFeeSet where CutType = ? and Size = ? ";
			list.add(cutFeeSet.getCutType());
			list.add(cutFeeSet.getSize());
			result = this.executeUpdateBySql(sql, list.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Description: TODO 编辑操作
	 * @author	created by zb
	 * @date	2018-10-22--下午4:15:52
	 * @param cutFeeSet
	 * @return
	 */
	public Boolean doUpdate(CutFeeSet cutFeeSet) {
		Long result = (long) 0;
		try {
			List<Object> list = new ArrayList<Object>();
			String sql = " update tCutFeeSet set CutFee=?,SupplCutFee=?,FactCutFee=?,AllowModify=?," +
					" LastUpdate=?,LastUpdatedBy=?,ActionLog=?,Expired=? " +
					" where CutType = ? and Size = ? ";
			list.add(cutFeeSet.getCutFee());
			list.add(cutFeeSet.getSupplCutFee());
			list.add(cutFeeSet.getFactCutFee());
			list.add(cutFeeSet.getAllowModify());
			list.add(cutFeeSet.getLastUpdate());
			list.add(cutFeeSet.getLastUpdatedBy());
			list.add(cutFeeSet.getActionLog());
			list.add(cutFeeSet.getExpired());
			list.add(cutFeeSet.getCutType());
			list.add(cutFeeSet.getSize());
			result = this.executeUpdateBySql(sql, list.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result > 0) {
			return true;
		}
		return false;
	}

}
