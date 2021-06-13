package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.LongFeeRule;

@SuppressWarnings("serial")
@Repository
public class LongFeeRuleDao extends BaseDao {

	/**
	 * LongFeeRule分页信息
	 * 
	 * @param page
	 * @param longFeeRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, LongFeeRule longFeeRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select b.descr itemType1Descr,c.descr itemType2Descr,a.no,a.lastUpdate,a.lastUpdatedBy," 
				+" a.expired,a.actionLog,a.itemType1,a.itemType2,a.remarks,a.SupplCode,sp.descr Suppldescr, "
		        +" d.NOTE SendTypeDescr, e.NOTE CalTypeDescr, f.Desc1 WarehouseDescr "
				+" from tLongFeeRule a "
				+" left join tItemType1 b on a.itemType1=b.code "
				+" left join tItemType2 c on a.itemType2=c.code "
				+" left join tSupplier sp on sp.Code=a.SupplCode "
				+" left join tXTDM d on d.ID = 'ITEMAPPSENDTYPE' and a.SendType = d.CBM "
				+" left join tXTDM e on e.ID = 'LongFeeCalType' and a.CalType = e.CBM "
				+" left join tWareHouse f on a.WHCode = f.Code "
				+" where 1=1 ";

    	if (StringUtils.isNotBlank(longFeeRule.getNo())) {
			sql += " and a.No=? ";
			list.add(longFeeRule.getNo());
		}
    	if (StringUtils.isNotBlank(longFeeRule.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(longFeeRule.getItemType1());
		}
    	if (StringUtils.isNotBlank(longFeeRule.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(longFeeRule.getItemType2());
		}
    	if (StringUtils.isNotBlank(longFeeRule.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(longFeeRule.getRemarks());
		}
    	if (longFeeRule.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(longFeeRule.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(longFeeRule.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(longFeeRule.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(longFeeRule.getExpired()) || "F".equals(longFeeRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(longFeeRule.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(longFeeRule.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.No";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * LongFeeRuleDetail分页信息
	 * 
	 * @param page
	 * @param longFeeRule
	 * @return
	 */
	public Page<Map<String,Object>> goDetailJqGrid(Page<Map<String,Object>> page, LongFeeRule longFeeRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.No,a.PK,a.SendRegion,a.LastUpdate,a.LastUpdatedBy,a.LongFee,a.SmallSendType," 
				+"a.Expired,a.ActionLog,b.Descr sendRegionDescr,c.NOTE SmallSendTypeDescr,a.SmallSendMaxValue "
	            +"from tLongFeeRuleDetail a " 
	            +"left join tSendRegion b on a.SendRegion=b.No "
	            +"left join tXTDM c on a.SmallSendType=c.CBM and c.ID='SmallSendType' "
				+" where a.No=?";
		list.add(longFeeRule.getNo());

		return this.findPageBySql(page, sql, list.toArray());
	}
	
    public Page<Map<String, Object>> goItemDetailJqGrid(Page<Map<String, Object>> page,
            LongFeeRule longFeeRule) {
        
        List<Object> params = new ArrayList<Object>();

        String sql = "select a.No,a.ItemType3,a.Expired,a.LastUpdate,a.LastUpdatedBy,"
                + "a.ActionLog,b.Descr ItemType3Descr "
                + "from tLongFeeRuleItem a "
                + "left join tItemType3 b on a.ItemType3=b.Code "
                + "where a.No=? ";
        
        params.add(longFeeRule.getNo());

        return this.findPageBySql(page, sql, params.toArray());
    }
	
	/**
	 * 保存
	 * 
	 * @param longFeeRule
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(LongFeeRule longFeeRule) {
		Assert.notNull(longFeeRule);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pLongFeeRule(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, longFeeRule.getNo());
			call.setString(2, longFeeRule.getM_umState());	
			call.setString(3, longFeeRule.getItemType1());
			call.setString(4, longFeeRule.getItemType2());
			call.setString(5, longFeeRule.getRemarks());
			call.setString(6, longFeeRule.getLastUpdatedBy());
			call.setString(7, longFeeRule.getLongFeeRuleDetailJson());
			
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			
			call.setString(10, longFeeRule.getSupplCode());
			call.setString(11, longFeeRule.getSendType());
			call.setString(12, longFeeRule.getwHCode());
			call.setString(13, longFeeRule.getCalType());
			call.setString(14, longFeeRule.getItemDetailXml());
			call.execute();
			result.setCode(String.valueOf(call.getInt(8)));
			result.setInfo(call.getString(9));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}

}

