package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.project.CustCheck;

@SuppressWarnings("serial")
@Repository
public class CustCheckDao extends BaseDao {
	/**
	 * 工地结算分页查询
	 * @param page
	 * @param custCheck
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustCheck custCheck) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select * from (select a.CustCode,c.Address,a.AppDate,a.MainStatus,x1.NOTE MainStatusDescr,a.MainRcvDate,e1.NameChi MainCZY,a.MainCplDate,a.MainRemark, "
					+" a.SoftSatus,x2.NOTE SoftStatusDescr,a.SoftRcvDate,e2.NameChi SoftCZY,a.SoftCplDate,a.SoftRemark, " 
					+" a.IntStatus,x3.NOTE IntStatusDescr,a.IntRcvDate,e3.NameChi IntCZY,a.IntCplDate,a.IntRemark, "
					+" e5.NameChi ConfirmCZY,b.ConfirmDate, g.NameChi Housekeeper, "
					+" a.FinStatus,x4.NOTE FinStatusDescr,e4.NameChi FinCZY,a.FinRcvDate,a.FinCplDate,a.FinRemark, "
					+" x5.NOTE CustTypeDescr,x6.NOTE ConstructTypeDescr,e6.nameChi projectManDescr,c.DocumentNo "
					+" from tCustCheck a "
					+" left join tPrjProg b on a.CustCode=b.CustCode and b.PrjItem='16' "
					+" left join tCustomer c on c.Code=a.CustCode "
					+" left join tXTDM x1 on x1.CBM=a.MainStatus and x1.ID='CUSTCHECKSTS' "
					+" left join tXTDM x2 on x2.CBM=a.SoftSatus and x2.ID='CUSTCHECKSTS' "
					+" left join tXTDM x3 on x3.CBM=a.IntStatus and x3.ID='CUSTCHECKSTS' "
					+" left join tXTDM x4 on x4.CBM=a.FinStatus and x4.ID='CUSTCHECKSTS' "
					+" left join tEmployee e1 on e1.Number=a.MainRcvCzy "
					+" left join tEmployee e2 on e2.Number=a.SoftRcvCzy "
					+" left join tEmployee e3 on e3.Number=a.IntRcvCzy "
					+" left join tEmployee e4 on e4.Number=a.FinRcvCzy "
					+" left join tEmployee e5 on e5.Number=b.ConfirmCZY "
					+" LEFT JOIN dbo.tXTDM x5 ON x5.ID = 'CUSTTYPE' AND x5.CBM = c.CustType "
                    +" LEFT JOIN dbo.tXTDM x6 ON x6.ID = 'CONSTRUCTTYPE' AND x6.CBM = c.ConstructType "
					+" LEFT JOIN dbo.tEmployee e6 ON e6.Number = c.projectMan "
                    +" left join tCustStakeholder f on a.CustCode = f.CustCode and f.Role = '34' "
					+" left join tEmployee g on f.EmpCode = g.Number "
					+" where 1=1 ";
		if(StringUtils.isNotBlank(custCheck.getAddress())){
			sql += " and c.Address like ? ";
			list.add("%"+custCheck.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(custCheck.getMainStatus())){
			sql += " and a.MainStatus in ('"+custCheck.getMainStatus().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(custCheck.getSoftSatus())){
			sql += " and a.SoftSatus in ('"+custCheck.getSoftSatus().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(custCheck.getIntStatus())){
			sql += " and a.IntStatus in ('"+custCheck.getIntStatus().replace(",", "','")+"')";
		}
		if(StringUtils.isNotBlank(custCheck.getFinStatus())){
			sql += " and a.FinStatus in ('"+custCheck.getFinStatus().replace(",", "','")+"')";
		}
		if(custCheck.getDateFrom() != null ){
			sql += " and cast(a.AppDate as date) >= ? ";
			list.add(custCheck.getDateFrom());
		}
		if(custCheck.getDateTo() != null ){
			sql += " and cast(a.AppDate as date) <= ? ";
			list.add(custCheck.getDateTo());
		}
		if(StringUtils.isNotBlank(custCheck.getIsConfirm())){
			if("1".equals(custCheck.getIsConfirm())){
				sql += " and b.ConfirmCZY is not null ";
			}else if("0".equals(custCheck.getIsConfirm())){
				sql += " and b.ConfirmCZY is null ";
			}
		}
		if(StringUtils.isNotBlank(custCheck.getCustType())){
			String str = SqlUtil.resetStatus(custCheck.getCustType());
			sql += " and c.CustType in (" + str + ")";
		}
		if(StringUtils.isNotBlank(custCheck.getConstructType())){
			sql += " and c.constructType=? ";
			list.add(custCheck.getConstructType());
		}
		
		// 添加主材管家过虑条件
		// 张海洋 20200608
		if (StringUtils.isNotBlank(custCheck.getHousekeeper())) {
            sql += " and f.EmpCode = ? ";
            list.add(custCheck.getHousekeeper());
        }
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.appDate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
}
