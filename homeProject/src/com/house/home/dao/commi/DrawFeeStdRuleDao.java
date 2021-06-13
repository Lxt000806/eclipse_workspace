package com.house.home.dao.commi;

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
import com.house.home.entity.commi.DrawFeeStdRule;

@SuppressWarnings("serial")
@Repository
public class DrawFeeStdRuleDao extends BaseDao {

	/**
	 * DrawFeeStdRule分页信息
	 * 
	 * @param page
	 * @param drawFeeStdRule
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, DrawFeeStdRule drawFeeStdRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
				+"select a.PK,a.DrawPrice,a.DrawFeeMin,c.NOTE MustDesignPicCfm,b.Descr PayeeDescr,a.Remarks,a.Prior,"
				+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,d.Descr CommiStdDesignRuleDescr "
				+"from tDrawFeeStdRule a "
				+"left join tTaxPayee b on a.PayeeCode = b.Code "
				+"left join tXTDM c on a.MustDesignPicCfm = c.CBM and c.ID = 'YESNO' "
				+"left join tCommiStdDesignRule d on a.CommiStdDesignRulePK = d.pk  "
				+"where 1=1 ";

    	if (drawFeeStdRule.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(drawFeeStdRule.getPk());
		}
    	if (StringUtils.isNotBlank(drawFeeStdRule.getPayeeCode())) {
			sql += " and a.PayeeCode=? ";
			list.add(drawFeeStdRule.getPayeeCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Prior Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * goDetailJqGrid分页信息
	 * 
	 * @param page
	 * @param drawFeeStdRule
	 * @return
	 */
	public Page<Map<String,Object>> goDetailJqGrid(Page<Map<String,Object>> page, DrawFeeStdRule drawFeeStdRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (" 
				+"select DrawFeeStdRulePK,BeginArea,EndArea,ColorDrawFee,ColorDrawFee3D,"
				+"LastUpdate,LastUpdatedBy,Expired,ActionLog "
				+"from tDrawFeeStdRuleDetail_ColorDraw  "
				+"where DrawFeeStdRulePK = ? ";
		list.add(drawFeeStdRule.getPk());
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.DrawFeeStdRulePK";
		}
		
		System.out.println(sql);
		System.out.println(list);
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 保存
	 * 
	 * @param drawFeeStdRule
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(DrawFeeStdRule drawFeeStdRule) {
		Assert.notNull(drawFeeStdRule);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDrawFeeStdRule(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setInt(1, drawFeeStdRule.getPk() == null ? -1 : drawFeeStdRule.getPk());
			call.setString(2, drawFeeStdRule.getM_umState());	
			call.setString(3, drawFeeStdRule.getPayeeCode());
			call.setDouble(4, drawFeeStdRule.getDrawPrice());
			call.setDouble(5, drawFeeStdRule.getDrawFeeMin());
			call.setString(6, drawFeeStdRule.getMustDesignPicCfm());
			call.setInt(7, drawFeeStdRule.getPrior());
			call.setString(8, drawFeeStdRule.getRemarks());
			call.setString(9, drawFeeStdRule.getLastUpdatedBy());
			call.setString(10, drawFeeStdRule.getDrawFeeStdRuleDetailJson());
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.NVARCHAR);
			call.setInt(13, drawFeeStdRule.getCommiStdDesignRulePK());
			call.execute();
			result.setCode(String.valueOf(call.getInt(11)));
			result.setInfo(call.getString(12));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
}

