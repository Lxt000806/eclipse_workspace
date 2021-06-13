package com.house.home.dao.query;
import java.sql.Timestamp;
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
public class IntSetAnalyDao extends BaseDao{

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( ";
			sql += "select o.Descr regionDescr,b.Address,a.CustCode,a.LastUpdate,'橱柜' itemtype2, "
				  +"case when a.CupDeliverDate is not null then '完成' else '安装中' end Status, "
				  +"a.CupInstallDate InstallDate,a.CupDeliverDate DeliverDate, "
				  +"case when a.CupDeliverDate is not null then datediff(day,a.CupSendDate,a.CupDeliverDate) "
				  +"else datediff(day,a.CupSendDate,getdate()) end InstallDays,c.LastIASend," 
				  +"g.ConfirmDate MqConfirmDate,null CjConfirmDate,a.CupAppDate IntAppDate,h.date,a.CupSendDate SendDate,case when i.replenishNum>0 then '是' else '否' end isReplenish, "
				  +"e.EmpCode CupDesignerCode,'' IntDesignerCode,f.NameChi CupDesigner,'' IntDesigner,null FaceConfirmDate, "
				  +"b.CupboardFee*b.ContainCup+isnull(k.CupChgFee,0)+l.CupSaleAmount_Set SaleFee, p.Remarks CanNotInsRemark, "
				  +"g4.ConfirmDate gdConfirmDate,g5.ConfirmDate jgConfirmDate,a.CupDesignDate designdate, r.EndDate workEndDate,s.ConfirmDate zpConfirmDate "
				  +"from tCustIntProg a  "
				  +"left join tCustomer b on b.Code=a.CustCode "
				  +"left join tEmployee m on b.DesignMan = m.Number "
				  +"left join ( "
				  +"  select in_a.CustCode, "
				  +"  max(case when in_a.SendDate is not null then in_a.SendDate else in_a.Date end) LastIASend "
				  +"  from tItemApp in_a  "
				  +"  where in_a.IsCupboard='1' and in_a.ItemType1='JC' "
				  +"  group by in_a.CustCode "
				  +") c on c.CustCode=a.CustCode "
				  +"left join (" //增加【集成部】查询条件
				  +"  select a.CustCode,max(a.PK) PK "
				  +"  from tCustStakeholder a "
				  +"  where a.Role='61' and a.Expired='F' "	//role由11修改为61 by zb
				  +"  group by a.CustCode "
				  +")d on d.CustCode=a.CustCode "
				  +"left join tCustStakeholder e on e.PK=d.PK "
				  +"left join tEmployee f on f.number=e.EmpCode "
				  +"left join tPrjProg g on g.custCode=b.Code and g.PrjItem='10' "
				  +"left join tPrjProg g4 on g4.custCode=b.Code and g4.PrjItem='19' "
				  +"left join tPrjProg g5 on g5.custCode=b.Code and g5.PrjItem='16' "
				  +"left join(select max(date) date,CustCode from tprjjob where ItemType1='jc' and JobType='08' and Status not in ('1','5') "
				  +" group by CustCode )h on h.CustCode=b.Code "
				  +"left join ( "
				  +"  select count(1) replenishNum,CustCode from tIntReplenish where IsCupboard='1' group by CustCode "
				  +")i on a.CustCode=i.CustCode "
				  /*+"left join ( "
				  +"  select in_a.CustCode, "
				  +"  max(in_a.SendDate) LastWhSend "
				  +"  from tItemApp in_a  "
				  +"  where in_a.IsCupboard='1' and in_a.ItemType1='JC' and WHReceiveDate is not null and isnull(in_a.IntRepPK,-1)=-1 "
				  +"  group by in_a.CustCode "
				  +") j on j.CustCode=a.CustCode "*/
				  +"left join (select CustCode, isnull(sum(BefAmount),0)CupChgFee, "
				  +"  isnull(sum(DiscAmount),0)CupChgDisc "
				  +"  from tItemChg  "
				  +"  where Status='2' and ItemType1='JC' and IsCupboard='1' "
				  +"  group by CustCode" 
				  +")k on a.CustCode=k.CustCode  "
				  +"left join tCustType l on b.CustType=l.Code "
				  +" left join tBuilder n on n.Code = b.BuilderCode "
				  +" left join tRegion o on o.Code = n.RegionCode "
                + " outer apply ( "
                + "    select in_a.Remarks, in_a.LastUpdatedBy "
                + "    from tIntProgDetail in_a "
                + "    where in_a.PK = ( "
                + "        select max(in_in_a.PK) "
                + "        from tIntProgDetail in_in_a "
                + "        left join tCZYBM in_in_b on in_in_a.LastUpdatedBy = in_in_b.CZYBH "
                + "        where in_in_a.IsCupboard = '1' and in_in_a.Type = '2' "
                + "            and in_in_b.CZYLB <> '2' "
                + "            and in_in_a.CustCode = a.CustCode "
                + "    ) "
                + " ) p " 
                + " left join (select max(a.PK) pk ,a.CustCode from  tCustWorker a where a.WorkType12 = '18' group by a.CustCode) q on q.CustCode = a.CustCode "
                + " left join tCustWorker r on r.Pk = q.Pk "
                + " left join tPrjProg s on s.CustCode = a.CustCode and s.PrjItem = '7' "
				+ " where 1=1 ";
			if (null != customer.getDateFrom()) {
				sql += "and a.CupSendDate>=? ";
				list.add(customer.getDateFrom());
			}
			if (null != customer.getDateTo()) {
				sql += "and a.CupSendDate<=? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
			}
			if (null != customer.getDeliverDateFrom()) {
				sql += "and a.CupDeliverDate>=? ";
				list.add(customer.getDeliverDateFrom());
			}
			if (null != customer.getDeliverDateTo()) {
				sql += "and a.CupDeliverDate<=? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(customer.getDeliverDateTo()).getTime()));
			}
			if (StringUtils.isNotBlank(customer.getDepartment2())) {
				sql += " and (f.Department2 in ('"
				        + customer.getDepartment2().replace(",", "', '")
				        + "') or m.Department2 in ('"
				        + customer.getDepartment2().replace(",", "', '")
				        + "'))";
			}
			if(StringUtils.isNotBlank(customer.getAddress())){
				sql+="and b.Address like ? ";
				list.add("%"+customer.getAddress()+"%");
			}
			if(StringUtils.isNotBlank(customer.getDesignMan())){
				sql+="and e.EmpCode = ? ";
				list.add(customer.getDesignMan());
			}
			if(customer.getItemType2().indexOf("CG")==-1){
				sql+="and 1<>1 ";
			}
			
			sql+=" union all ";//材料类型2改成多选，改用union，没有选中的用1<>1剔除掉 by cjg 
			
			sql += "select o.Descr regionDescr,b.Address,a.CustCode,a.LastUpdate,'衣柜' itemtype2, "
			    +"case when a.IntDeliverDate is not null then '完成' else '安装中' end Status, "
			    +"a.IntInstallDate InstallDate,a.IntDeliverDate DeliverDate, "
			    +"case when a.IntDeliverDate is not null then datediff(day,a.IntSendDate,a.IntDeliverDate) "
			    +"else datediff(day,a.IntSendDate,getdate()) end InstallDays,c.LastIASend,g3.ConfirmDate MqConfirmDate,g.ConfirmDate CjConfirmDate, "
			    +"a.IntAppDate,h.date,a.IntSendDate SendDate,case when i.replenishNum>0 then '是' else '否' end isReplenish, "
			    +"'' CupDesignerCode,e.EmpCode IntDesignerCode,'' CupDesigner,f.NameChi IntDesigner,g2.ConfirmDate FaceConfirmDate," 
			    +" b.IntegrateFee*b.ContainInt+isnull(k.IntChgFee,0)+case when l.IntSaleAmountCalcType_Set='1' "
			    +"	then l.IntSaleAmount_Set else b.Area*l.IntSaleAmount_Set end SaleFee, p.Remarks CanNotInsRemark, "
			    +"g4.ConfirmDate gdConfirmDate,g5.ConfirmDate jgConfirmDate,a.IntDesignDate designdate,r.EndDate workEndDate, s.ConfirmDate zpConfirmDate "
			    +"from tCustIntProg a  "
			    +"left join tCustomer b on b.Code=a.CustCode "
			    +"left join tEmployee m on b.DesignMan = m.Number "
			    +"left join ( "
			    +"  select in_a.CustCode, "
			    +"  max(case when in_a.SendDate is not null then in_a.SendDate else in_a.Date end) LastIASend "
			    +"  from tItemApp in_a  "
			    +"  where in_a.IsCupboard='0' and in_a.ItemType1='JC' "
			    +"  group by in_a.CustCode "
			    +") c on c.CustCode=a.CustCode "
			    +"left join (" //增加【集成部】查询条件
			    +"  select a.CustCode,max(a.PK) PK "
			    +"  from tCustStakeholder a "
			    +"  where a.Role='11' and a.Expired='F' "
			    +"  group by a.CustCode "
			    +")d on d.CustCode=a.CustCode "
			    +"left join tCustStakeholder e on e.PK=d.PK "
			    +"left join tEmployee f on f.number=e.EmpCode "
			    +"left join tPrjProg g on g.custCode=b.Code and g.PrjItem='20' "
			    +"left join tPrjProg g2 on g2.custCode=b.Code and g2.PrjItem='13' "	//增加饰面验收日期 add by zb
			    +"left join tPrjProg g3 on g3.custCode=b.Code and g3.PrjItem='10' "
			    +"left join tPrjProg g4 on g4.custCode=b.Code and g4.PrjItem='19' "
				+"left join tPrjProg g5 on g5.custCode=b.Code and g5.PrjItem='16' "
			    +"left join(select max(date) date,CustCode from tprjjob where ItemType1='jc' and JobType='09' and Status not in ('1','5') "
			    +" group by CustCode )h on h.CustCode=b.Code "
			    +"left join ( "
			    +"  select count(1) replenishNum,CustCode from tIntReplenish where IsCupboard='0' group by CustCode "
			    +")i on a.CustCode=i.CustCode "
			   /* +"left join ( "
		 	    +"  select in_a.CustCode, "
			    +"  max(in_a.SendDate) LastWhSend "
			    +"  from tItemApp in_a  "
			    +"  where in_a.IsCupboard='0' and in_a.ItemType1='JC' and WHReceiveDate is not null and isnull(in_a.IntRepPK,-1)=-1 "
			    +"  group by in_a.CustCode "
			    +") j on j.CustCode=a.CustCode "*/
			    +"left join (select CustCode,  "
			    +"  isnull(sum(BefAmount),0)IntChgFee,  "
			    +"  isnull(sum(DiscAmount),0)IntChgDisc  "
			    +"  from tItemChg  "
			    +"  where Status='2' and ItemType1='JC' and IsCupboard='0'"
			    +"  group by CustCode" 
			    +")k on a.CustCode=k.CustCode  "
			    +"left join tCustType l on b.CustType=l.Code "
				  +" left join tBuilder n on n.Code = b.BuilderCode "
				  +" left join tRegion o on o.Code = n.RegionCode "
	              + " outer apply ( "
				  + "    select in_a.Remarks, in_a.LastUpdatedBy "
	                + "    from tIntProgDetail in_a "
	                + "    where in_a.PK = ( "
	                + "        select max(in_in_a.PK) "
	                + "        from tIntProgDetail in_in_a "
	                + "        left join tCZYBM in_in_b on in_in_a.LastUpdatedBy = in_in_b.CZYBH "
	                + "        where in_in_a.IsCupboard = '0' and in_in_a.Type = '2' "
	                + "            and in_in_b.CZYLB <> '2' "
	                + "            and in_in_a.CustCode = a.CustCode "
	                + "    ) "
	                + " ) p " 
	                + " left join (select max(a.PK) pk ,a.CustCode from  tCustWorker a where a.WorkType12 = '17' group by a.CustCode) q on q.CustCode = a.CustCode "
	                + " left join tCustWorker r on r.Pk = q.Pk "
	                + " left join tPrjProg s on s.CustCode = a.CustCode and s.PrjItem = '7' "
			    +"where 1=1 ";
			if (null != customer.getDateFrom()) {
				sql += "and a.IntSendDate>=? ";
				list.add(customer.getDateFrom());
			}
			if (null != customer.getDateTo()) {
				sql += "and a.IntSendDate<=? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(customer.getDateTo()).getTime()));
			}
			if (null != customer.getDeliverDateFrom()) {
				sql += "and a.IntDeliverDate>=? ";
				list.add(customer.getDeliverDateFrom());
			}
			if (null != customer.getDeliverDateTo()) {
				sql += "and a.IntDeliverDate<=? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(customer.getDeliverDateTo()).getTime()));
			}
			if (StringUtils.isNotBlank(customer.getDepartment2())) {
			    sql += " and (f.Department2 in ('"
                        + customer.getDepartment2().replace(",", "', '")
                        + "') or m.Department2 in ('"
                        + customer.getDepartment2().replace(",", "', '")
                        + "'))";
			}
			if(StringUtils.isNotBlank(customer.getAddress())){
				sql+="and b.Address like ? ";
				list.add("%"+customer.getAddress()+"%");
			}
			if(StringUtils.isNotBlank(customer.getDesignMan())){
				sql+="and e.EmpCode = ? ";
				list.add(customer.getDesignMan());
			}
			if(customer.getItemType2().indexOf("YG")==-1){
				sql+="and 1<>1 ";
			}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 领料明细查看
	 * @author	created by zb
	 * @date	2019-10-30--上午11:47:25
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findIaDetailBySql(
			Page<Map<String, Object>> page, Customer customer) {
		String sql = "select a.No,a.IsCupboard,tx.NOTE IsCupboardDescr,a.Status,tx2.NOTE StatusDescr, "
					+"a.Date,a.ConfirmDate,a.SendDate,a.SendType,tx3.NOTE SendTypeDescr,a.SupplCode, "
					+"b.Descr SupplDescr,a.Remarks "
					+"from tItemApp a   "
					+"left join tXTDM tx on tx.ID='YESNO' and tx.CBM=a.IsCupboard "
					+"left join tXTDM tx2 on tx2.ID='ITEMAPPSTATUS' and tx2.CBM=a.Status "
					+"left join tXTDM tx3 on tx3.ID='ITEMAPPSENDTYPE' and tx3.CBM=a.SendType "
					+"left join tSupplier b on b.Code=a.SupplCode "
					+"where a.ItemType1='JC' and a.CustCode=? and a.IsCupboard=? ";
		
		return this.findPageBySql(page, sql, new Object[]{customer.getCode(),customer.getIsCupboard()});
	}


}
