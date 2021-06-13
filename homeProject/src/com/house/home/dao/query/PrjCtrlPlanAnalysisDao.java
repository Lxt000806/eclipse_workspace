package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.design.Customer;


@SuppressWarnings("serial")
@Repository
public class PrjCtrlPlanAnalysisDao extends BaseDao {

	/**
	 * 工地发包预算分析列表信息
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select* from ( select a.SignDate,dp2.Desc1 Department2Descr, x1.note Layoutdescr, a.Code, a.Address, e3.NameChi ProjectManDescr, "
                	+ " isnull(case when a.InnerArea > 0 then a.InnerArea else a.Area * ct.InnerAreaPer "
                    + "  end, 0) InnerArea, isnull(cb.amount, 0) CostFix ,"
                    + " round(dbo.fGetCustBaseCost_Com(a.code, 3, ''),0) Cost, "
                    + " dbo.fGetCustBaseCtrl_Com(a.code, 3, '') BaseCtrlAmt, "
                    + "   case when isnull(cb.amount, 0)=0  then 0 else  round(dbo.fGetCustBaseCtrl_Com(a.code, 3, '')*100/cb.amount ,0) end ctrlcostper, "
                    + " case when isnull(case  when a.innerarea > 0 then a.innerarea  else a.area * ct.innerareaper  end, 0)=0 then 0 else  "
                    + " round((dbo.fGetCustBaseCtrl_Com(a.code, 3, '') -isnull(cb.amount, 0) ) / isnull(case "
                    + " when a.innerarea > 0 then a.innerarea else a.area * ct.innerareaper end, 0),0)  end plancommiper, "
                    + " round(dbo.fGetCustBaseCtrl_Com(a.code, 3, '') -isnull(cb.amount, 0),0)  plancommi,"
                    + "  round(isnull(SetAddctrl,0),0) SetAddctrl, "
                    + "  isnull(pp.BaseQuotaPrice,0 ) BaseQuotaPrice, "
                    + "  case when isnull(case when a.innerarea > 0 then a.innerarea else a.area * ct.innerareaper end, 0) = 0 then 0 "
              		+ "	 	else round((isnull(cb.amount, 0)-isnull(SetAddctrl,0)*1.0/1.1)/ isnull(case when a.innerarea > 0 then a.innerarea else a.area * ct.innerareaper end, 0),0) end  setInCostPer, "
              		+ "  case when isnull(case when a.innerarea > 0 then a.innerarea else a.area * ct.innerareaper end, 0) = 0 then 0 "
                    + "    else round(isnull(cb.amount, 0)/ isnull(case when a.innerarea > 0 then a.innerarea else a.area * ct.innerareaper end, 0),0) end  costfixPer "
              		+ " ,ct.desc1 custtypedescr "
                    + " from  tCustomer a "
                    + " inner join tCustType ct on a.CustType = ct.Code "
                    + " left outer join tPrjCheck p on a.Code = p.CustCode "
                    + " left outer join tEmployee e3 on a.ProjectMan = e3.Number "
                    + " left outer join tDepartment2 dp2 on e3.Department2=dp2.Code "
                    + " left outer join txtdm x1 on x1.id='Layout'and x1.cbm=a.Layout "
                    + " left outer join ( select    CustCode, "
                    + " 	sum(round(Qty * Material, 0)) + sum(round(Qty * OfferPri, 0)) amount "
                    + " 	from      tBaseCheckItemPlan "
                    + "     group by  custCode "
                    + " ) cb on cb.CustCode = a.Code" 
                    + " left join(  "   //计算基础发包额
                    + " 	select a.Custcode,  "
                    + "		isnull(sum(case when f.BaseCtrlPer is not null then round(round(a.Material * a.Qty, 0) * f.BaseCtrlPer,2) "
					+ " 	else round((case when a.PrjCtrlType = '1' then a.Material * a.MaterialCtrl else a.MaterialCtrl end ) * a.Qty, 2) end) ,0) "
					+ " 	+isnull(sum(case when f.BaseCtrlPer is not null then round(round(a.UnitPrice * a.Qty, 0) * f.BaseCtrlPer,2) "
					+ "	 	else round((case when a.PrjCtrlType = '1' then a.UnitPrice * a.OfferCtrl else a.OfferCtrl end ) * a.Qty, 2) end) ,0) SetAddctrl "
					+ " 	from tBaseItemReq  a "
					+ " 	left join tCustBaseCtrl f on a.CustCode = f.CustCode and a.BaseItemCode = f.BaseItemCode "
					+ " 	where  IsOutSet='1' and a.LineAmount<>0 GROUP BY a.CustCode "
					+ " ) sc ON sc.CustCode=a.Code "	
					+ " left outer join tProjectCtrlPrice pp on  pp.CustType=a.CustType  "
					+ " 	and isnull(case when a.InnerArea>0 then a.InnerArea else a.Area*ct.InnerAreaPer end ,0)>=pp.FromArea  "
					+ "		and  isnull(case when a.InnerArea>0 then a.InnerArea else a.Area*ct.InnerAreaPer end ,0)<=pp.ToArea and pp.Expired='F' " 
                    + " where a.Expired='F' and  a.status='4' and IsAddAllInfo='1' and a.CheckStatus not in ('3', '4' ) ";
        
					
		if (StringUtils.isNotBlank(customer.getProjectMan())) {
			sql += " and a.ProjectMan=? ";
			list.add(customer.getProjectMan());
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and a.CustType in " + "('"+customer.getCustType().replaceAll(",", "','")+"')";
		}
		if (customer.getSignDateFrom() != null){
			sql += " and a.SignDate>= ? ";
			list.add(customer.getSignDateFrom());
		}
    	if (customer.getSignDateTo() != null){
			sql += " and a.SignDate< ? ";
			list.add(DateUtil.addDateOneDay(customer.getSignDateTo()));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.SignDate ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
		
}

