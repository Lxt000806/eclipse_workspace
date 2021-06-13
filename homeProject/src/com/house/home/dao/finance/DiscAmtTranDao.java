package com.house.home.dao.finance;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.DiscAmtTran;
@SuppressWarnings("serial")
@Repository
public class DiscAmtTranDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, DiscAmtTran discAmtTran) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from( "
					+"select a.PK,a.CustCode,e.Address,a.Type,b.NOTE TypeDescr,a.Amount,a.IsRiskFund,c.NOTE IsRiskFundDescr,a.CustGiftPK, "
					+"a.FixDutyManPK,a.IsExtra,d.NOTE IsExtraDescr,a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
					+"from tDiscAmtTran a "
					+"left join tXTDM b on b.ID='DISCAMTTRANTYPE' and b.CBM=a.Type "
					+"left join tXTDM c on c.ID='YESNO' and c.CBM=a.IsRiskFund "
					+"left join tXTDM d on d.ID='YESNO' and d.CBM=a.IsExtra "
					+"left join tCustomer e on e.Code=a.CustCode "
					+"where 1=1 ";
		
		if (StringUtils.isBlank(discAmtTran.getExpired()) || "F".equals(discAmtTran.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(discAmtTran.getAddress())) {
			sql += " and e.address like ? ";
			list.add("%"+discAmtTran.getAddress().trim()+"%");
		}
		if (StringUtils.isNotBlank(discAmtTran.getCustCode())) {
			sql += " and a.CustCode = ? ";
			list.add(discAmtTran.getCustCode());
		}
		if (null != discAmtTran.getCustGiftPK()) {
			sql += " and a.CustGiftPK = ? ";
			list.add(discAmtTran.getCustGiftPK());
		}
		if (null != discAmtTran.getFixDutyManPK()) {
			sql += " and a.FixDutyManP = ? ";
			list.add(discAmtTran.getFixDutyManPK());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		} else {
			sql += " )a order by a.LastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String,Object>> findDiscAmtTranPageBySql(Page<Map<String,Object>> page, DiscAmtTran discAmtTran){
		List<Object> list = new ArrayList<Object>();

        String sql = "select * from (" +
        		" select a.PK,a.CustCode, a.amount,a.remarks,a.lastUpdate,a.lastUpdatedBy,a.expired ,a.actionlog," +
        		" b.Address,x1.NOTE typeDescr,x2.note isRiskFundDescr ,x3.note isExtraDescr from tDiscAmtTran a" +
        		" left join tCustomer b on b.Code= a.custCode" +
        		" left join tXTDM x1 on x1.ID='DISCAMTTRANTYPE' and x1.CBM = a.Type" +
        		" left join tXTDM x2 on x2.id='YESNO' and x2.cbm = a.isriskFund" +
        		" left join tXTDM x3 on x3.ID='YESNO' and x3.cbm = a.isextra " +
        		" where 1=1 and a.isRiskFund = '0' ";
        if(StringUtils.isNotBlank(discAmtTran.getCustCode())){
        	sql+=" and a.custCode = ?";
        	list.add(discAmtTran.getCustCode());
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())){
            sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
        }else{
            sql += ")a order by a.LastUpdate desc";
        }
        return this.findPageBySql(page, sql, list.toArray());
    }

	/**
	 * 获取礼品金额
	 * @param custCode
	 * @return
	 */
	public double getLpExpense(String custCode) {
		String sql = " select isnull(sum(case when b.outType='2' then a.qty else -1*a.qty end * a.Cost),0) lpExpense "+
                  	 " from tGiftAppDetail a " + 
                  	 " left join tGiftApp b on a.No=b.no " + 
                  	 " where b.status in ('send','return') and b.Type='2' and b.CustCode=? " +
                  	 " and exists(select 1 from tXTCS where ID='GiftInDisc' and QZ='1' ) "+
                	 //" and isnull(a.UseDiscAmount,'0')='0' "; // 使用优惠额度的礼品会生成优惠变动,防止重复算
		             " and isnull(a.UseDiscAmount,0)=0 "; // 使用优惠额度的礼品会生成优惠变动,防止重复算
		return (Double) this.findBySql(sql, custCode).get(0).get("lpExpense");
	}

}
