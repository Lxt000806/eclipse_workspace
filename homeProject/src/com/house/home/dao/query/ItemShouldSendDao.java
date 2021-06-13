package com.house.home.dao.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.SqlUtil;
import com.house.home.entity.design.Customer;

@Repository
@SuppressWarnings("serial")
public class ItemShouldSendDao extends BaseDao {

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String beginOrEnd = "";
		if ("1".equals(customer.getStatistcsMethod())) {
			beginOrEnd = "Begin";
		} else {
			beginOrEnd = "End";
		}
		String sql = "select * from (select a.Area,a.Address,a.Code CustCode,d.ConfItemType,g.No,f.Descr ConfItemTypeDescr,g.ConfirmDate,g.SplRemark,x2.NOTE DelivTypeDescr, "
				+ "dateadd(DAY,d."
				+ beginOrEnd
				+ "AddDays,case when d."
				+ beginOrEnd
				+ "DateType='1' then e.BeginDate when d."
				+ beginOrEnd
				+ "DateType='2' then e.EndDate when d."
				+ beginOrEnd
				+ "DateType='3' then e.ConfirmDate end)shouldSendDate, "
				+ "h.Descr shouldSendNode,x1.NOTE nodeDateType,case when d."
				+ beginOrEnd
				+ "DateType='1' then e.BeginDate when d."
				+ beginOrEnd
				+ "DateType='2' then e.EndDate when d."
				+ beginOrEnd
				+ "DateType='3' then e.ConfirmDate end nodeTriggerDate,case when d.PayNum>0 and m.shouldBanlance>0 then m.shouldBanlance else 0 end shouldBanlance, "
				+ "l.Descr SupplDescr,b.desc1 CustTypeDescr,g.ArriveDate,cast(dbo.fGetEmpNameChi(a.Code,'34') as nvarchar(20)) MainDescr "
				+ "from tCustomer a "
				+ "left join tCustType b on a.CustType = b.Code "
				+ "left join tItemSendNode c on b.WorkerClassify = c.WorkerClassify "
				+ "left join tItemSendNodeDetail d on c.Code = d.Code "
				+ "inner join tPrjProg e on dateadd(DAY,d."
				+ beginOrEnd
				+ "AddDays,case when d."
				+ beginOrEnd
				+ "DateType='1' then e.BeginDate when d."
				+ beginOrEnd
				+ "DateType='2' then e.EndDate when d."
				+ beginOrEnd
				+ "DateType='3' then e.ConfirmDate end)" + " <dateadd(day,1,";
		if (customer.getEndDate() != null) {
			sql += "?";
			list.add(customer.getEndDate());
		} else {
			sql += "getdate()";
		}
		sql += ") and e.PrjItem=d.BeginNode and a.Code=e.CustCode "
				+ "left join tConfItemType f on f.Code=d.ConfItemType "
				+ "inner join tItemApp g on a.Code=g.CustCode and g.Status in('OPEN','CONFIRMED','CONRETURN')"
				+ "left join tPrjItem1 h on e.PrjItem=h.Code "
				+ "left join tBuilder i on a.BuilderCode=i.Code "
				+ "left join tRegion j on i.RegionCode=j.Code "
				+ "left join tXTDM x1 on d."
				+ beginOrEnd
				+ "DateType=x1.CBM and x1.ID='ALARMDAYTYPE' "
				+ "left join tXTDM x2 on g.DelivType=x2.CBM and x2.ID='DELIVTYPE' "
				+ "left join tEmployee k on k.Number=a.ProjectMan "
				+ "left join tSupplier l on g.SupplCode=l.Code "
				+ "outer apply (select dbo.fGetShouldBanlanceByPayNum(a.Code,d.PayNum,default) shouldBanlance ) m "
				+ "where a.Status='4' and c.Type='2' and exists(select 1 from titemAppDetail in_a "
				+ "left join tItem in_b on in_a.ItemCode = in_b.Code "
				+ "left join tItemType2 in_c on in_b.ItemType2=in_c.Code "
				+ "left join tItemType3 in_d on in_b.ItemType3=in_d.Code  "
				+ "where in_a.No=g.No and exists(select 1 from tConfItemTypeDt in_e where ConfItemType=f.Code "
				+ "and ((in_b.ItemType2=in_e.ItemType2 and isnull(in_e.ItemType3,'')<>'' and in_b.ItemType3=in_e.ItemType3) "
				+ "or(in_b.ItemType2=in_e.ItemType2 and isnull(in_e.ItemType3,'')='')))  )";
		if (StringUtils.isNotBlank(customer.getAddress())) {
			sql += " and a.Address like ? ";
			list.add("%" + customer.getAddress() + "%");
		}
		
		if (StringUtils.isNotBlank(customer.getItemType1())) {
			sql += " and c.ItemType1=? ";
			list.add(customer.getItemType1());
		}
		if (StringUtils.isNotBlank(customer.getBuilderCode())) {
			sql += " and a.BuilderCode=? ";
			list.add(customer.getBuilderCode());
		}
		if (StringUtils.isNotBlank(customer.getRegion())) {
			String str = SqlUtil.resetStatus(customer.getRegion());
			sql += " and i.RegionCode in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getSendType())) {
			sql += " and g.SendType =? ";
			list.add(customer.getSendType());
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			String str = SqlUtil.resetStatus(customer.getCustType());
			sql += " and a.CustType in (" + str + ")";
		}
		if (StringUtils.isNotBlank(customer.getWhCode())) {
			sql += " and g.whCode = ? ";
			list.add(customer.getWhCode());
		}
		if (StringUtils.isNotBlank(customer.getPrjItem())) {
			sql += " and e.PrjItem = ? ";
			list.add(customer.getPrjItem());
		}
		if (StringUtils.isNotBlank(customer.getDepartment2())) {
			sql += " and k.Department2 in ('"
					+ customer.getDepartment2().replaceAll(",", "','") + "')";
		}
		if (StringUtils.isNotBlank(customer.getSupplierCode())) {
			sql += " and g.SupplCode=? ";
			list.add(customer.getSupplierCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.CustCode asc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select b.Descr ItemDescr,a.Qty,e.Descr FixAreaDescr "
				+ "from tItemAppDetail a "
				+ "left join tItem b on a.ItemCode = b.Code "
				+ "left join tFixArea e on a.FixAreaPK=e.PK "
				+ "where a.No=? "
				+ "and exists(select 1 from tConfItemTypeDt in_a where ConfItemType=? "
				+ " and ((b.ItemType2=in_a.ItemType2 and isnull(in_a.ItemType3,'')<>'' and b.ItemType3=in_a.ItemType3) "
				+ " or(b.ItemType2=in_a.ItemType2 and isnull(in_a.ItemType3,'')=''))) ";
		list.add(customer.getNo());
		list.add(customer.getConfItemType());
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.ItemDescr asc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getItemAppInfo(String iaNo) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select a.Date,b.Descr SupplDescr,c.Desc1 WHDescr,a.ConfirmDate, "
				+ "d.Address,a.No,x1.NOTE StatusDescr,x2.NOTE SendTypeDescr "
				+ "from tItemApp a "
				+ "left join tSupplier b on a.SupplCode=b.Code  "
				+ "left join tWareHouse c on a.WHCode=c.Code  "
				+ "left join tCustomer d on a.CustCode=d.Code "
				+ "left join tXTDM x1 on a.Status=x1.CBM and x1.ID='ITEMAPPSTATUS' "
				+ "left join tXTDM x2 on a.SendType=x2.CBM and x2.ID='ITEMAPPSENDTYPE' "
				+ "where a.No=? ";
		list.add(iaNo);
		return this.findBySql(sql, list.toArray()).get(0);
	}
}
