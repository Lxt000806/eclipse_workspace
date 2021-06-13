package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.BankPos;

@SuppressWarnings("serial")
@Repository
public class BankPosDao extends BaseDao {

	/**
	 * BankPos分页信息
	 * 
	 * @param page
	 * @param bankPos
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BankPos bankPos) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from( select a.Code,a.Descr,a.RcvAct,b.Descr RcvActDescr, a.PosID,a.CompName,a.MinFee,a.MaxFee,AcquireFeePerc,"
                   +" a.FeePerc,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired,a.CompCode,a.CardAttr,tx.NOTE CardAttrDescr,tx2.NOTE CardTypeDescr  "
                   +" from tBankPos a left outer join tRcvAct b on  a.RcvAct=b.Code "
                   +"left join tXTDM tx on tx.ID='CARDATTR' and tx.CBM=a.CardAttr "//维护卡性质 add by zb on 20190531
                   +"left join tXTDM tx2 on tx2.ID='POSCARDTYPE' and tx2.CBM=a.CardType "
                   +" where 1=1  ";
		
    	if (StringUtils.isNotBlank(bankPos.getCode())) {
			sql += " and a.Code=? ";
			list.add(bankPos.getCode());
		}
    	if (StringUtils.isNotBlank(bankPos.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+bankPos.getDescr()+"%");
		}
    	if (StringUtils.isNotBlank(bankPos.getRcvAct())) {
			sql += " and a.RcvAct=? ";
			list.add(bankPos.getRcvAct());
		}
		if (StringUtils.isBlank(bankPos.getExpired()) || "F".equals(bankPos.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.LastUpdate desc" ;
		}
		System.out.println(sql);
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 更新bankPos
	 * @param bankPos
	 */
	public void doUpdate(BankPos bankPos) {
		String sql = " update tBankPos set lastupdate=?,lastupdatedby=?,actionlog=?,expired=?, " +
				"descr=?,rcvAct=?,posId=?,compName=?,minFee=?,maxFee=?,feePerc=?,acquirefeeperc=?,compcode=?,CardAttr=?,CardType=? where code=?";
		this.executeUpdateBySql(
				sql,
				new Object[] { bankPos.getLastUpdate(),bankPos.getLastUpdatedBy(),bankPos.getActionLog(),bankPos.getExpired(),
						bankPos .getDescr(),bankPos.getRcvAct(),bankPos.getPosId(),bankPos.getCompName(),bankPos.getMinFee(),
						bankPos.getMaxFee(),bankPos.getFeePerc(),bankPos.getAcquireFeePerc(),bankPos.getCompCode(),bankPos.getCardAttr(),
						bankPos.getCardType(),bankPos.getCode()});
	}
	/**
	 * 检查code是否已存在
	 * @param bankPos
	 * @return
	 */
	public boolean checkExsist(BankPos bankPos) {
		boolean flag=false;
		String sql="select 1 from tBankPos where Code=?";
		List<Map<String, Object>> list=this.findBySql(sql,new Object[]{bankPos.getCode()});
		if(list.size()>0){
			flag=true;
		}
		return flag;
	}
	/**
	 * 根据code查询BankPos
	 * @param code
	 * @return
	 */
	public Map<String , Object>  getBankPosDetail(String code) {
		String sql = " select * from tBankPos where Code=?";
		code=code.replace(" ", "+");
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
		
	}
}

