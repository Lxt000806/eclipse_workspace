package com.house.home.dao.basic;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Czrz;
import com.house.home.entity.basic.PayRule;
import com.house.home.entity.insales.Purchase;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
@SuppressWarnings("serial")
@Repository
public class PayRuleDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PayRule payRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select  a.*,x1.note payTypeDescr, x2.note designFeeDescr," +
				"x3.note ContractFeeRepTypeDescr, b.desc1 custTypeDescr from tPayRule a  " +
				" left join tXtdm x1 on x1.cbm= a.PayType and x1.id='TIMEPAYTYPE' " +
				" left join tCustType b on b.code=a.CustType " +
				" left join tXtdm x2 on x2.cbm=a.DesignFeeType and x2.id='DESIGNFEETYPE' " +
				" left join tXtdm x3 on x3.cbm=a.ContractFeeRepType and x3.id='FEEREPTYPE' " +
				"where 1=1 ";
		if(StringUtils.isNotBlank(payRule.getNo())){
			sql+=" and a. no = ?";
			list.add(payRule.getNo());
		}
		if(StringUtils.isNotBlank(payRule.getCustType())){
			sql+=" and a.CustType = ? ";
			list.add(payRule.getCustType());
		}
		if (StringUtils.isBlank(payRule.getExpired())
				|| "F".equals(payRule.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	
	}
	
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, PayRule payRule) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.confirmPrjitem,a.isrcvdesignfee,x1.note isrcvdesignFeedescr,b.descr confirmPrjitemDescr,a.PayExpr,a.PayNum," +
				" a.No,a.ChgPer,a.PayRemark,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, a.PayTip, " +
		        " a.TipType, x2.NOTE TipTypeDescr, a.WorkType12, c.Descr WorkType12Descr, a.TipAddDays, " +
		        " a.IsCalcCommi, a.BusinessCommiProvidePer, a.BusinessSubsidyProvidePer," +
		        " a.BusinessMultipleProvidePer, a.DesignCommiProvidePer, a.DesignSubsidyProvidePer, a.DesignMultipleProvidePer," +
		        " a.IsCalcSceneDesignCommi, a.SceneDesignProvidePer, a.IsCalcDesignFeeCommi, " +
		        " a.IsCalcDrawFeeCommi, a.IsCalcMainCommi, a.BusinessMainCommiProvidePer, a.IsCalcIntCommi, a.BusinessIntCommiProvidePer," +
		        " x3.note IsCalcCommiDescr,x4.note IsCalcSceneDesignCommiDescr, x5.note IsCalcDesignFeeCommiDescr, "+  
		        " x6.note IsCalcDrawFeeCommiDescr,x7.note IsCalcMainCommiDescr, x8.note IsCalcIntCommiDescr,a.DesignFeeCommiProvidePer,	a.DrawFeeCommiProvidePer," +
		        " IsCalcBasePersonalCommi,x9.note IsCalcBasePersonalCommiDescr, BasePersonalCommiProvidePer,NewDesignCommiProvidePer, "  + 
		        " a.IsCalcSoftCommi, a.BusinessSoftCommiProvidePer,x10.NOTE IsCalcSoftCommiDescr"+
				" from tPayRuledetail a " +
				" left join txtdm x1 on x1.cbm=a.IsRcvDesignFee and x1.id='YESNO'" +
				" left join tPrjItem1 b on b.Code=a.ConfirmPrjItem " +
				" left join tXTDM x2 on x2.CBM = a.TipType and x2.ID = 'PAYRULETIPTYPE' " +
				" left join tWorkType12 c on a.WorkType12 = c.Code " +
				" left join txtdm x3 on x3.cbm=a.IsCalcCommi and x3.id='YESNO'" +
				" left join txtdm x4 on x4.cbm=a.IsCalcSceneDesignCommi and x4.id='YESNO'" +
				" left join txtdm x5 on x5.cbm=a.IsCalcDesignFeeCommi and x5.id='YESNO'" +
				" left join txtdm x6 on x6.cbm=a.IsCalcDrawFeeCommi and x6.id='YESNO'" +
				" left join txtdm x7 on x7.cbm=a.IsCalcMainCommi and x7.id='YESNO'" +
				" left join txtdm x8 on x8.cbm=a.IsCalcIntCommi and x8.id='YESNO'" +
				" left join txtdm x9 on x9.cbm=a.IsCalcBasePersonalCommi and x9.id='YESNO'" +
				" left join txtdm x10 on x10.cbm=a.IsCalcSoftCommi and x10.id='YESNO'" +
				" where 1=1 ";
		if(StringUtils.isNotBlank(payRule.getNo())){
			sql+=" and a.No= ? ";
			list.add(payRule.getNo());
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	
	}
	
	@SuppressWarnings("deprecation")
	public Result doSave(PayRule payRule) {
		Assert.notNull(payRule);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPayRule(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, payRule.getNo());
			call.setString(2, payRule.getM_umState());
			call.setString(3, payRule.getCustType());
			call.setString(4, payRule.getPayType());
			call.setString(5, payRule.getDesignFeeType());
			call.setString(6, payRule.getLastUpdatedBy());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
			call.setString(9, payRule.getDetailXml());
			call.setString(10, payRule.getContractFeeRepType());
			call.setString(11, payRule.getExpired());
			call.execute();
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}

}
