package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
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
import com.house.home.entity.basic.SpcBuilder;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.insales.Purchase;

@SuppressWarnings("serial")
@Repository
public class SpcBuilderDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SpcBuilder spcBuilder) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.*,e2.namechi leaderdescr,x1.note TypeDescr,x2.note builderTypeDescr from tSpcBuilder a  " +
				" left join temployee e on e.number=a.lastUpdatedBy " +
				" left join temployee e2 on e2.number=a.leadercode " +
				" left join txtdm x1 on x1.cbm=a.Type and x1.id='SPCBUILDERTYPE' " +
				" left join txtdm x2 on x2.cbm=a.builderType and x2.id='BUILDERTYPE' " +
				" where 1=1 and a.expired='F' ";
		if(StringUtils.isNotBlank(spcBuilder.getCode())){
			sql+=" and a.code=?";
			list.add(spcBuilder.getCode());
		}
		if(StringUtils.isNotBlank(spcBuilder.getDescr())){
			sql+=" and a.descr like ? ";
			list.add("%"+spcBuilder.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.lastUpdate desc";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

	}
	
	public Page<Map<String, Object>> findDelivPageBySql(
			Page<Map<String, Object>> page, String code,String builderNums) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select *,dbo.fGetBuilderNum(code) builderNum from tBuilderDeliv a " +
				" where 1=1  ";
		if(StringUtils.isNotBlank(code)){
			sql+=" and a.builderCode= ? ";
			list.add(code);
		}
		if(StringUtils.isNotBlank(builderNums)){
			sql += " and a.code not  in " + "('"+builderNums.replaceAll(",", "','")+"')";
		}
		
		return this.findPageBySql(page, sql, list.toArray());

	}
	
	@SuppressWarnings("deprecation")
	public Result doSave(SpcBuilder spcBuilder) {
		Assert.notNull(spcBuilder);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSpcBuilder_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, spcBuilder.getCode());
			call.setString(2, spcBuilder.getDescr());
			call.setString(3, spcBuilder.getLastUpdatedBy());
			call.setString(4, spcBuilder.getRemarks());
			call.setString(5, spcBuilder.getLeaderCode());
			call.setString(6, spcBuilder.getType());
			call.setString(7, spcBuilder.getBuilderType());
			call.setString(8, spcBuilder.getDelivQty());
			call.setString(9, spcBuilder.getTotalQty());
			call.setTimestamp(10, spcBuilder.getDelivDate()==null?null : new Timestamp(spcBuilder.getDelivDate().getTime()));
			call.setString(11, spcBuilder.getTotalBeginQty());
			call.setString(12, spcBuilder.getSelfDecorQty());
			call.setString(13, spcBuilder.getDecorCmpBeginQty());
			call.setString(14, spcBuilder.getDecorCmp1());
			call.setString(15, spcBuilder.getDecorCmp1Qty());
			call.setString(16, spcBuilder.getDecorCmp2());
			call.setString(17, spcBuilder.getDecorCmp2Qty());
			call.setString(18, spcBuilder.getDecorCmp3());
			call.setString(19, spcBuilder.getDecorCmp3Qty());
			call.setString(20, spcBuilder.getDecorCmp4());
			call.setString(21, spcBuilder.getDecorCmp4Qty());
			call.setString(22, spcBuilder.getDecorCmp5());
			call.setString(23, spcBuilder.getDecorCmp5Qty());
			call.setString(24, spcBuilder.getTemp());
			call.setString(25, spcBuilder.getExpired());
			call.registerOutParameter(26, Types.INTEGER);
			call.registerOutParameter(27, Types.NVARCHAR);
			call.setString(28, spcBuilder.getDetailXml());
			String xml= spcBuilder.getDetailXml();
			call.execute();
			result.setCode(String.valueOf(call.getInt(26)));
			result.setInfo(call.getString(27));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public SpcBuilder getByDescr(String descr) {
		String hql = "from SpcBuilder where descr=?";
		List<SpcBuilder> list = this.find(hql, new Object[] {descr});
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
