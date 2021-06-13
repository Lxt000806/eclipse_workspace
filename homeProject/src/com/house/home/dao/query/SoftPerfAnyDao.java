package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;
@Repository
@SuppressWarnings("serial")
public class SoftPerfAnyDao extends BaseDao {
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.EmpCode,c.NameChi,d.Desc1 department1descr,e.Desc1 department2descr,f.Desc1 department3descr, "
				+ "sum(case when b.IsPartDecorate <> '2' and b.IsAddAllInfo = '1' then chgAmount - ChgDiscCost "
				+ "        else planFee - planDisc + chgAmount - ChgDiscAmount + isnull(g.BaseChgAmount, 0) - isnull(g.BaseChgDiscAmount, 0) "
				+ "            + isnull(h.BaseFee, 0) - isnull(h.BaseDisc, 0) "
				+ "    end) amount from ( "
				+ "	select a.Code,a.address,a.CustType,a.EmpCode,sum(a.chgAmount) chgAmount, "
				+ "	sum(case when b.BefAmount < 0 then -b.DiscAmount else b.DiscAmount end) ChgDiscAmount, "
				+ "	sum(case when b.BefAmount < 0 then -b.DiscCost else b.DiscCost end) ChgDiscCost," 
				+ " sum(planFee) planFee,sum(planDisc) planDisc "
				+ "	from (  "
				+ "		select a.no,e.Code,e.Address,f.EmpCode,e.CustType, "
				+ "		sum(case when b.IsOutSet = '1' then b.LineAmount else round(round(b.Qty * c.Price * b.Markup / 100,0)+ b.ProcessCost,0) end) chgAmount,0 planFee,0 planDisc "
				+ "		from tItemChg a "
				+ "		inner join tItemChgDetail b on a.No = b.No "
				+ "		inner join tItem c on b.ItemCode = c.Code "
				+ "		inner join tItemType2 d on c.ItemType2 = d.Code "
				+ "		left join tCustomer e on a.CustCode = e.Code "
				+ "		inner join tCustStakeholder f on e.Code = f.CustCode "
				+ "		left join tCusttype g on e.CustType = g.Code "
				+ "		where a.Status = '2' "
				+ "		and a.ConfirmDate >= ? "
				+ "		and a.ConfirmDate <dateadd(day,1,?) "
				+ "		and a.expired = 'F' ";
				if("50".equals(customer.getRole())){
					//sql+=" and ((g.IsAddAllInfo = '1' and f.Role='50')or(g.IsAddAllInfo = '0' and f.Role='00'))"; 改成跟软装业绩一致
					sql+=" and (((g.Code='2' or g.Code='20') and f.Role='00') or (g.Code<>'2' and g.Code<>'20' and f.Role='50')) ";
				}else {
					sql+=" and f.Role=? ";
				}
				sql+= "		and ( ( g.IsPartDecorate <> '2' and g.IsAddAllInfo = '1' and c.SqlCode not in ( '1052','1943' )) "
				+ "		or ( g.IsPartDecorate = '2' )  or(g.IsPartDecorate <> '2' and g.IsAddAllInfo = '0')) "// 家装套餐客户不包含预估
				+ "		and ( ( g.IsPartDecorate = '2' and a.ItemType1 in ( 'RZ','ZC','JC' ))or ( g.IsPartDecorate <> '2'and a.ItemType1 = 'RZ')) "// 精装客户包含集成主材
				+ "		group by e.Code,e.Address,e.CustType,a.No,f.EmpCode "
				+ "	) a "
				+ "	left join tItemChg b on a.No = b.No "
				+ "	group by a.Code,a.address,a.CustType,a.EmpCode "
				+ "	union all "
				+ " select c.Code,c.Address,c.CustType,f.EmpCode,0 chgAmount,0 ChgDiscAmount,0 ChgDiscCost, "
				+ " sum(case when ip.IsOutSet = '1' then ip.LineAmount else round(round(ip.Qty * i.Price * ip.Markup / 100,0)+ ip.ProcessCost,0)end) planAmount, "
				+ " case when g.IsPartDecorate = '2' then c.SoftDisc*c.ContainSoft+IntegrateDisc*ContainInt+MainDisc*ContainMain "
				+ " else c.SoftDisc*c.ContainSoft end  PlanDisc "
				+ " from tItemPlan ip "
				+ " inner join tCustomer c on ip.CustCode = c.Code "
				+ " inner join tItem i on ip.ItemCode = i.Code "
				+ " inner join tItemType2 it2 on i.ItemType2 = it2.Code "
				+ " left join tIntProd ipd on ip.IntProdPK = ipd.Pk "
				+ " inner join tCustStakeholder f on c.Code=f.CustCode "
				+ " left join tCusttype g on c.CustType=g.Code "
				+ " where  c.SignDate is not null  "
				+ " and c.SignDate >=? "
				+ " and c.SignDate < dateadd(day,1,?) "
				+ " and ip.expired = 'F' ";
				if("50".equals(customer.getRole())){
					//sql+=" and ((g.IsAddAllInfo = '1' and f.Role='50')or(g.IsAddAllInfo = '0' and f.Role='00'))";
					sql+=" and (((g.Code='2' or g.Code='20') and f.Role='00') or (g.Code<>'2' and g.Code<>'20' and f.Role='50')) ";
				}else {
					sql+=" and f.Role=? ";
				}
				sql+= " and ( ( g.IsPartDecorate <> '2' and g.IsAddAllInfo = '1' and i.SqlCode not in ( '1052','1943' )) "// 家装套餐客户不包含预估
				+ " or ( g.IsPartDecorate = '2' ) or(g.IsPartDecorate <> '2' and g.IsAddAllInfo = '0')) "
				+ " and ( ( g.IsPartDecorate = '2' and i.ItemType1 in ( 'RZ','ZC','JC' ))or ( g.IsPartDecorate <> '2'and i.ItemType1 = 'RZ')) "// 精装客户包含集成主材软装
				+ " group by c.Code,c.Address,c.CustType,ContainMain,ContainSoft,ContainInt, "
				+ " ContainCup,MainDisc,SoftDisc,IntegrateDisc,CupBoardDisc,g.IsPartDecorate,f.EmpCode "
				+ ") a "
				+ "left join tCusttype b on a.CustType = b.Code "
				+ "left join tEmployee c on a.EmpCode = c.Number "
				+ "left join tDepartment1 d on c.Department1 = d.Code "
				+ "left join tDepartment2 e on c.Department2 = e.Code "
				+ "left join tDepartment3 f on c.Department3 = f.Code "
                + "outer apply ( "
                + "    select sum(in1_a.BefAmount) BaseChgAmount, "
                + "        sum(case when in1_a.BefAmount < 0 then -in1_a.DiscAmount else in1_a.DiscAmount end) BaseChgDiscAmount "
                + "    from tBaseItemChg in1_a "
                + "    inner join tCustomer in1_b on in1_b.Code = in1_a.CustCode "
                + "    inner join tCustType in1_c on in1_c.Code = in1_b.CustType "
                + "    where in1_a.Expired = 'F' and in1_a.Status = '2' "
                + "        and in1_a.ConfirmDate >= ? "
                + "        and in1_a.ConfirmDate < dateadd(day, 1, ?) "
                + "        and in1_a.CustCode = a.Code "
                + "        and in1_c.IsPartDecorate = '2' "
                + ") g "
                + "outer apply ( "
                + "    select in1_a.BaseFee, in1_a.BaseDisc "
                + "    from tCustomer in1_a "
                + "    inner join tCustType in1_b on in1_b.Code = in1_a.CustType "
                + "    where in1_a.SignDate >= ? "
                + "        and in1_a.SignDate < dateadd(day, 1, ?) "
                + "        and in1_a.Code = a.Code "
                + "        and in1_b.IsPartDecorate = '2' "
                + ") h "
				+ "where 1=1 ";
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		if(!"50".equals(customer.getRole())){
			list.add(customer.getRole());
		}
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		if(!"50".equals(customer.getRole())){
			list.add(customer.getRole());
		}
		
        list.add(customer.getDateFrom());
        list.add(customer.getDateTo());
        list.add(customer.getDateFrom());
        list.add(customer.getDateTo());
		
		if(StringUtils.isNotBlank(customer.getDepartment1())){
			sql+=" and d.code=?";
			list.add(customer.getDepartment1());
		}
		if(StringUtils.isNotBlank(customer.getDepartment2())){
			sql+=" and e.code=?";
			list.add(customer.getDepartment2());
		}
		if(StringUtils.isNotBlank(customer.getDepartment3())){
			sql+=" and f.code=?";
			list.add(customer.getDepartment3());
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and  a.CustType in " + "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		sql+= " group by a.EmpCode,c.NameChi,d.Desc1,e.Desc1,f.Desc1";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.empcode asc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	} 

	public Page<Map<String, Object>> findPageBySql_detail(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select a.EmpCode,a.Address,c.NameChi, "
				+ "sum(chgAmount + isnull(g.BaseChgAmount, 0)) chgAmount, sum(ChgDiscCost)ChgDiscCost,"
				+ "sum(ChgDiscAmount + isnull(g.BaseChgDiscAmount, 0)) ChgDiscAmount,"
				+ "sum(planFee + isnull(h.BaseFee, 0)) planFee, sum(planDisc + isnull(h.BaseDisc, 0))planDisc, b.Desc1 custTypeDescr,"
				+ "sum(case when b.IsPartDecorate <> '2' and b.IsAddAllInfo = '1' then chgAmount - ChgDiscCost "
				+ "        else planFee - planDisc + chgAmount - ChgDiscAmount + isnull(g.BaseChgAmount, 0) - isnull(g.BaseChgDiscAmount, 0) "
				+ "            + isnull(h.BaseFee, 0) - isnull(h.BaseDisc, 0) "
				+ "    end) amount from ( "
				+ "	select a.Code,a.address,a.CustType,a.EmpCode,sum(a.chgAmount) chgAmount, "
				+ "	sum(case when b.BefAmount < 0 then -b.DiscAmount else b.DiscAmount end) ChgDiscAmount, "
				+ "	sum(case when b.BefAmount < 0 then -b.DiscCost else b.DiscCost end) ChgDiscCost,"
				+ " sum(planFee) planFee,sum(planDisc) planDisc "
				+ "	from (  "
				+ "		select a.no,e.Code,e.Address,f.EmpCode,e.CustType, "
				+ "		sum(case when b.IsOutSet = '1' then b.LineAmount else round(round(b.Qty * c.Price * b.Markup / 100,0)+ b.ProcessCost,0) end) chgAmount,0 planFee,0 planDisc "
				+ "		from tItemChg a "
				+ "		inner join tItemChgDetail b on a.No = b.No "
				+ "		inner join tItem c on b.ItemCode = c.Code "
				+ "		inner join tItemType2 d on c.ItemType2 = d.Code "
				+ "		left join tCustomer e on a.CustCode = e.Code "
				+ "		inner join tCustStakeholder f on e.Code = f.CustCode "
				+ "		left join tCusttype g on e.CustType = g.Code "
				+ "		where a.Status = '2' "
				+ "		and a.ConfirmDate >= ? "
				+ "		and a.ConfirmDate <dateadd(day,1,?) "
				+ "		and a.expired = 'F' ";
				if("50".equals(customer.getRole())){
					//sql+=" and ((g.IsAddAllInfo = '1' and f.Role='50')or(g.IsAddAllInfo = '0' and f.Role='00'))"; 改成跟软装业绩计算一致
					sql+=" and (((g.Code='2' or g.Code='20') and f.Role='00') or (g.Code<>'2' and g.Code<>'20' and f.Role='50')) ";
				}else {
					sql+=" and f.Role=? ";
				}
				sql+= "		and ( ( g.IsPartDecorate <> '2' and g.IsAddAllInfo = '1' and c.SqlCode not in ( '1052','1943' )) "
				+ "		or ( g.IsPartDecorate = '2' )  or(g.IsPartDecorate <> '2' and g.IsAddAllInfo = '0') ) "// 家装套餐客户不包含预估
				+ "		and ( ( g.IsPartDecorate = '2' and a.ItemType1 in ( 'RZ','ZC','JC' ))or ( g.IsPartDecorate <> '2'and a.ItemType1 = 'RZ')) "// 精装客户包含集成主材
				+ "		group by e.Code,e.Address,e.CustType,a.No,f.EmpCode "
				+ "	) a "
				+ "	left join tItemChg b on a.No = b.No "
				+ "	group by a.Code,a.address,a.CustType,a.EmpCode "
				+ "	union all "
				+ " select c.Code,c.Address,c.CustType,f.EmpCode,0 chgAmount,0 ChgDiscAmount,0 ChgDiscCost, "
				+ " sum(case when ip.IsOutSet = '1' then ip.LineAmount else round(round(ip.Qty * i.Price * ip.Markup / 100,0)+ ip.ProcessCost,0)end) planAmount, "
				+ " case when g.IsPartDecorate = '2' then c.SoftDisc*c.ContainSoft+IntegrateDisc*ContainInt+MainDisc*ContainMain "
				+ " else c.SoftDisc*c.ContainSoft end  PlanDisc "
				+ " from tItemPlan ip "
				+ " inner join tCustomer c on ip.CustCode = c.Code "
				+ " inner join tItem i on ip.ItemCode = i.Code "
				+ " inner join tItemType2 it2 on i.ItemType2 = it2.Code "
				+ " left join tIntProd ipd on ip.IntProdPK = ipd.Pk "
				+ " inner join tCustStakeholder f on c.Code=f.CustCode "
				+ " left join tCusttype g on c.CustType=g.Code "
				+ " where  c.SignDate is not null  "
				+ " and c.SignDate >=? "
				+ " and c.SignDate < dateadd(day,1,?) "
				+ " and ip.expired = 'F' ";
				if("50".equals(customer.getRole())){
					//sql+=" and ((g.IsAddAllInfo = '1' and f.Role='50')or(g.IsAddAllInfo = '0' and f.Role='00'))";
					sql+=" and (((g.Code='2' or g.Code='20') and f.Role='00') or (g.Code<>'2' and g.Code<>'20' and f.Role='50')) ";
				}else {
					sql+=" and f.Role=? ";
				}
				sql+= " and ( ( g.IsPartDecorate <> '2' and g.IsAddAllInfo = '1' and i.SqlCode not in ( '1052','1943' )) "// 家装套餐客户不包含预估
				+ " or ( g.IsPartDecorate = '2' ) or(g.IsPartDecorate <> '2' and g.IsAddAllInfo = '0')) "
				+ " and ( ( g.IsPartDecorate = '2' and i.ItemType1 in ( 'RZ','ZC','JC' ))or ( g.IsPartDecorate <> '2'and i.ItemType1 = 'RZ')) "// 精装客户包含集成主材软装
				+ " group by c.Code,c.Address,c.CustType,ContainMain,ContainSoft,ContainInt, "
				+ " ContainCup,MainDisc,SoftDisc,IntegrateDisc,CupBoardDisc,g.IsPartDecorate,f.EmpCode "
				+ ") a "
				+ "left join tCusttype b on a.CustType = b.Code "
				+ "left join tEmployee c on a.EmpCode = c.Number "
				+ "left join tDepartment1 d on c.Department1 = d.Code "
				+ "left join tDepartment2 e on c.Department2 = e.Code "
				+ "left join tDepartment3 f on c.Department3 = f.Code "
				+ "outer apply ( "
				+ "    select sum(in1_a.BefAmount) BaseChgAmount, "
				+ "        sum(case when in1_a.BefAmount < 0 then -in1_a.DiscAmount else in1_a.DiscAmount end) BaseChgDiscAmount "
				+ "    from tBaseItemChg in1_a "
				+ "    inner join tCustomer in1_b on in1_b.Code = in1_a.CustCode "
				+ "    inner join tCustType in1_c on in1_c.Code = in1_b.CustType "
				+ "    where in1_a.Expired = 'F' and in1_a.Status = '2' "
				+ "        and in1_a.ConfirmDate >= ? "
				+ "        and in1_a.ConfirmDate < dateadd(day, 1, ?) "
				+ "        and in1_a.CustCode = a.Code "
				+ "        and in1_c.IsPartDecorate = '2' "
				+ ") g "
				+ "outer apply ( "
				+ "    select in1_a.BaseFee, in1_a.BaseDisc "
				+ "    from tCustomer in1_a "
				+ "    inner join tCustType in1_b on in1_b.Code = in1_a.CustType "
				+ "    where in1_a.SignDate >= ? "
				+ "        and in1_a.SignDate < dateadd(day, 1, ?) "
				+ "        and in1_a.Code = a.Code "
				+ "        and in1_b.IsPartDecorate = '2' "
				+ ") h "
				+ "where 1=1 and c.number=?  ";

		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		if(!"50".equals(customer.getRole())){
			list.add(customer.getRole());
		}
		list.add(customer.getDateFrom());
		list.add(customer.getDateTo());
		if(!"50".equals(customer.getRole())){
			list.add(customer.getRole());
		}
		
        list.add(customer.getDateFrom());
        list.add(customer.getDateTo());
        list.add(customer.getDateFrom());
        list.add(customer.getDateTo());
		
        list.add(customer.getEmpCode());
		if (StringUtils.isNotBlank(customer.getDepartment1())) {
			sql += " and d.code=?";
			list.add(customer.getDepartment1());
		}
		if (StringUtils.isNotBlank(customer.getDepartment2())) {
			sql += " and e.code=?";
			list.add(customer.getDepartment2());
		}
		if (StringUtils.isNotBlank(customer.getDepartment3())) {
			sql += " and f.code=?";
			list.add(customer.getDepartment3());
		}
		if (StringUtils.isNotBlank(customer.getCustType())) {
			sql += " and  a.CustType in " + "('"+customer.getCustType().replace(",", "','" )+ "')";
		}
		sql += " group by a.EmpCode,c.NameChi,a.Address,b.Desc1";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.Address asc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

    @SuppressWarnings("deprecation")
    public Page<Map<String, Object>> findCustomerPageBySql(Page<Map<String, Object>> page, Customer customer) {

        Assert.notNull(customer);
        Connection conn = null;
        CallableStatement call = null;
        
        try {
            HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
            Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
            
            conn = session.connection();
            call = conn.prepareCall("{Call pSoftPerfAnyCustomer(?,?,?)}");
            call.setString(1, customer.getCustType());
            call.setDate(2, new Date(customer.getDateFrom().getTime()));
            call.setDate(3, new Date(customer.getDateTo().getTime()));
            call.execute();
            
            List<Map<String, Object>> list = BeanConvertUtil.resultSetToList(call.getResultSet());
            page.setResult(list);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(null, call, conn);
        }
        
        return page;
    }
}
