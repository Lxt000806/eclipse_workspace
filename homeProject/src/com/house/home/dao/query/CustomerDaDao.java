package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.framework.web.login.UserContextHolder;
import com.house.home.entity.design.Customer;

@SuppressWarnings("serial")
@Repository
public class CustomerDaDao extends BaseDao {

	/**
	 * customeDa分页信息
	 * 
	 * @param page
	 * @param custome
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();                                                                                                      
		String sql = "select * from (select a.SignDate,a.DocumentNo,a.Code,a.Descr,a.Gender,c.NOTE GenderDescr,a.Address,a.BusinessMan,dbo.fGetEmpNameChi(a.Code,'01') BusinessManDescr,e2.Desc1 BusinessManDesc1,e2.Desc2 BusinessManDesc2,e2.Desc3 BusinessManDesc3,"
              + "a.DesignMan,dbo.fGetEmpNameChi(a.Code,'00') DesignManDescr, e1.Desc1 DesignManDesc1,e1.Desc2 DesignManDesc2,e1.Desc3 DesignManDesc3,"
              + "a.ProjectMan,dbo.fGetEmpNameChi(a.Code,'20') ProjectManDescr,e3.Desc1 ProjectManDesc1,e3.Desc2 ProjectManDesc2,e3.Desc3 ProjectManDesc3,a.Layout,f.NOTE LayoutDescr,a.Area,"
              + " a.Status,d.NOTE StatusDescr,a.EndCode, h.NOTE EndCodeDescr,a.DesignStyle,e.NOTE DesignStyleDescr,a.ConstructType,g.NOTE ConstructTypeDescr,"
              + " a.ContractFee,a.DesignFee,a.SetDate,a.ConfirmBegin,a.LastUpdate "
              + " from tCustomer a "
              + " left outer join ( "
              + " select c.Number,d1.Desc1 as Desc1,d2.Desc1 as Desc2,d3.Desc1 as Desc3  from tEmployee c "
              + " left outer join tDepartment1 d1 on c.Department1 = d1.Code "
              + " left outer join tDepartment2 d2 on c.Department2 = d2.Code "
              + " left outer join tDepartment3 d3 on c.Department3 = d3.Code "
              + " ) e1 on a.DesignMan=e1.Number "
              + " left outer join ( "
              + " select c.Number,d1.Desc1 as Desc1,d2.Desc1 as Desc2,d3.Desc1 as Desc3  from tEmployee c "
              + " left outer join tDepartment1 d1 on c.Department1 = d1.Code "
              + " left outer join tDepartment2 d2 on c.Department2 = d2.Code "
              + " left outer join tDepartment3 d3 on c.Department3 = d3.Code "
              + " ) e2 on a.BusinessMan=e2.Number "
              + " left outer join ( "
              + " select c.Number,d1.Desc1 as Desc1,d2.Desc1 as Desc2,d3.Desc1 as Desc3  from tEmployee c "
              + " left outer join tDepartment1 d1 on c.Department1 = d1.Code "
              + " left outer join tDepartment2 d2 on c.Department2 = d2.Code "
              + " left outer join tDepartment3 d3 on c.Department3 = d3.Code "
              + " ) e3 on a.ProjectMan=e3.Number "
              + " left outer join tXTDM c on a.Gender=c.CBM and c.ID='GENDER' "
              + " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
              + " left outer join tXTDM e on a.DesignStyle=e.CBM and e.ID='DESIGNSTYLE' "
              + " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
              + " left outer join tXTDM g on a.ConstructType=g.CBM and g.ID='CONSTRUCTTYPE' "
              + " left outer join tXTDM h on a.EndCode=h.CBM and h.ID='CUSTOMERENDCODE' "
              + " left outer join tBuilder i on a.BuilderCode=i.Code "
              + " where 1=1 and (a.Status='4' or (a.Status='5' and a.EndCode='3')) "; 
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + customer.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(customer.getDesignMan())) {
			sql += " and a.DesignMan=? ";
			list.add(customer.getDesignMan());
		}
		if (StringUtils.isNotBlank(customer.getProjectMan())) {
			sql += " and a.ProjectMan=? ";
			list.add(customer.getProjectMan());
		}
		if (StringUtils.isNotBlank(customer.getBusinessMan())) {
			sql += " and a.BusinessMan=? ";
			list.add(customer.getBusinessMan());	
		}
		if (customer.getSetDateFrom() != null){
			sql += " and a.SetDate>= ? ";
			list.add(customer.getSetDateFrom());
		}
		if (customer.getSetDateTo() != null){
			sql += " and a.SetDate<= ? ";
			list.add(customer.getSetDateTo());
		}
		
		if (customer.getSignDateFrom() != null){
			sql += " and a.SignDate>= ? ";
			list.add(customer.getSignDateFrom());
		}
		if (customer.getSignDateTo() != null){
			sql += " and a.SignDate<= ? ";
			list.add(customer.getSignDateTo());
		}
		if (customer.getConfirmBeginFrom() != null){
			sql += " and a.ConfirmBegin>= ? ";
			list.add(customer.getConfirmBeginFrom());
		}
		if (customer.getConfirmBeginTo() != null){
			sql += " and a.ConfirmBegin<= ? ";
			list.add(customer.getConfirmBeginTo());
		}
		if (StringUtils.isNotBlank(customer.getBuilderCode())) {
			sql += " and a.BuilderCode=? ";
			list.add(customer.getBuilderCode());
		}
		if (StringUtils.isNotBlank(customer.getStatus())) {
			sql += " and a.Status in " + "('"+customer.getStatus().replaceAll(",", "','")+"')";
		}
		if (StringUtils.isBlank(customer.getExpired())
				|| "F".equals(customer.getExpired())) {
			sql += " and a.Expired='F' ";
		}	
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.LastUpdate desc,a.Code desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}


}

