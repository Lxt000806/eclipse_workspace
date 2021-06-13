package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.CustMessage;

@SuppressWarnings("serial")
@Repository
public class CustMessageDao extends BaseDao {

	public Page<Map<String,Object>> getCustMessageList(Page<Map<String,Object>> page, CustMessage custMessage){
		List<Object> params = new ArrayList<Object>();
		String sql = " select tcm.PK,tc.Address,tcm.MsgText,tcm.SendDate,tcm.RcvStatus "
				   + " from tCustMessage tcm "
				   + " left join tCustMapped tcm2 on tcm.MsgRelCustCode = tcm2.CustCode "
				   + " left join tCustomer tc on tc.Code = tcm2.CustCode "
				   + " left join tCustAccount tca on tcm.RcvCZY = tca.PK "
				   + " where tcm.PushStatus='1' and tca.Mobile1=? ";
		params.add(custMessage.getRcvCZY());
		if(StringUtils.isNotBlank(custMessage.getRcvStatus())){
			sql += " and tcm.RcvStatus=? ";
			params.add(custMessage.getRcvStatus());
		}
		sql += " order by tcm.SendDate desc ";
		return this.findPageBySql(page, sql, params.toArray());
	}

	public List<Map<String, Object>> getCustomerMessagePushList(){
		String sql = " select count(*) msgnum,tca.Mobile1 Phone "
				   + " from tCustMessage tcm "
				   + " left join tCustMapped tc on tc.CustCode = tcm.MsgRelCustCode "
				   + " left join tCustAccount tca on tc.CustAccountPk = tca.PK "
				   + " where PushStatus='0' "
				   + " group by tca.Mobile1 ";
		return this.findBySql(sql, new Object[]{});
	}
	
	public void updateCustomerMessagePushStatus(String phone) {
		String sql = " update tCustMessage set PushStatus='1' from tCustMessage a" +
				" left join tCustAccount b on b.PK=a.RcvCZY where b.Mobile1= ?  ";
		this.executeUpdateBySql(sql, new Object[]{phone});
	}
}

