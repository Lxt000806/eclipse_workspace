package com.house.home.dao.commi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.commi.CommiCustomer;

@SuppressWarnings("serial")
@Repository
public class CommiCustomerDao extends BaseDao {

	/**
	 * CommiCustomer分页信息
	 * 
	 * @param page
	 * @param commiCustomer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CommiCustomer commiCustomer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from ("
					+"select a.PK,a.SignCommiNo,a.CustCode,a.Status,b.NOTE StatusDescr, "
					+"a.InvalidNo,a.SecondPayNo,a.ThirdPayNo,a.CheckNo,a.LastUpdateNo, "
					+"a.SignPerfPK,a.InvalidType,c.NOTE InvalidTypeDescr,a.InvalidPerfPK, "
					+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog ,d.Address, "
					+"cc1.Mon SignCommiMon,cc2.Mon InvalidMon,cc3.Mon SecondPayMon,"
					+"cc4.Mon ThirdPayMon,cc5.Mon CheckMon,cc6.Mon LastUpdateMon "
					+"from tCommiCustomer a  "
					+"left join tCustomer d on a.CustCode = d.Code "
					+"left join tXTDM b on a.Status = b.CBM and b.ID = 'COMMICUSTOMSTAT' "
					+"left join tXTDM c on a.InvalidType = c.CBM and c.ID = 'COMMIINVALIDTYP' "
					+"left join tCommiCycle cc1 on a.SignCommiNo=cc1.No "
					+"left join tCommiCycle cc2 on a.InvalidNo=cc2.No "
					+"left join tCommiCycle cc3 on a.SecondPayNo=cc3.No "
					+"left join tCommiCycle cc4 on a.ThirdPayNo=cc4.No "
					+"left join tCommiCycle cc5 on a.CheckNo=cc5.No "
					+"left join tCommiCycle cc6 on a.LastUpdateNo=cc6.No "
					+"where 1=1 ";
		
		if (commiCustomer.getMon() != null){
			sql += " and exists(" 
					+"	select 1 from tCommiCustStakeholder in_a " 
					+"  left join tCommiCycle in_b on in_a.CommiNo = in_b.No "
					+"	where in_b.Mon = ? and in_a.CustCode = a.CustCode and in_a.SignCommiNo = a.SignCommiNo " 
					+") ";
			list.add(commiCustomer.getMon());
		}
    	if (StringUtils.isNotBlank(commiCustomer.getSignCommiNo())) {
    		sql += " and exists(" 
					+"	select 1 from tCommiCustStakeholder in_a " 
					+"	where in_a.CommiNo = ? and in_a.CustCode = a.CustCode and in_a.SignCommiNo = a.SignCommiNo" 
					+") ";
			list.add(commiCustomer.getSignCommiNo());
		}
    	if (StringUtils.isNotBlank(commiCustomer.getAddress())) {
			sql += " and d.Address like ? ";
			list.add("%"+commiCustomer.getAddress()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

}

