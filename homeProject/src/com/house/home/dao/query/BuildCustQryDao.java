package com.house.home.dao.query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
@SuppressWarnings("serial")
@Repository
public class BuildCustQryDao extends BaseDao{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer ,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
	
		String sql =" select * from ( select  a.code ,a.address,a.area,x2.NOTE typeDescr,b.Desc1 custtypeDescr,a.contractFee ," +
				" case when a.status = '5' then '竣工验收' else f.descr end  prjDescr,e.NameChi designDescr ,d.Desc1 designDesp2 " +
				"		,e2.NameChi projectManDescr,d2.Desc1 projectManDesp2,x1.note statusDescr" +
				" from    tcustomer a  " +
				"        left join tCusttype b on b.Code = a.CustType" +
				" left join " +
				" (  select max(pk) pk,custcode from tprjprog a " +// --查询实际施工节点
				" 		where a.begindate=(select max(begindate) from tprjprog  where custcode=a.custcode )  group by a.custcode" +
				"        ) dd on dd.custcode = a.code " +
				" left join tprjprog sj on sj.pk=dd.pk   " +
				"        left join tEmployee e on e.Number = a.designMan" +
				"        left join tEmployee e2 on e2.Number = a.projectMan" +
				"        left join tDepartment2 d on d.Code= e.Department2" +
				"        left join tDepartment2 d2 on e2.Department2 = d2.Code" +
				"        left join txtdm x1 on x1.id = 'CUSTOMERSTATUS' and x1.cbm = a.Status" +
				"        left join tXTDM x2 on x2.id  = 'CONSTRUCTTYPE' and x2.cbm = a.constructType " +
				"		 left join tPrjItem1 f on f.Code=sj.prjItem " +
				" where (a.status= '4' or (a.status='5' and a.endCode = '3' )) and b.IsAddAllInfo = '1'  " ;

		if(StringUtils.isNotBlank(customer.getBuilderCode())){
			sql+=" and a.builderCode = ? ";
			list.add(customer.getBuilderCode());
		} else {
			sql+=" and 1<>1 ";
		}
		if(StringUtils.isNotBlank(customer.getIncludEnd())){
			if("0".equals(customer.getIncludEnd())){
				sql+=" and a.status = 4 ";
			}else{
				sql+=" and 1=1 ";
			}
		}
		if(StringUtils.isNotBlank(customer.getBuilderNum())){
			sql+=" and a.BuilderNum like ? ";
			list.add("%"+customer.getBuilderNum()+"%");
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a  ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
	

}
