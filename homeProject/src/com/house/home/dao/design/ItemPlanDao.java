package com.house.home.dao.design;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.basic.CzybmDao;
import com.house.home.entity.basic.CustGift;
import com.house.home.entity.basic.CustGiftItem;
import com.house.home.entity.basic.CustType;
import com.house.home.entity.design.BaseItemPlan;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ItemPlan;

@SuppressWarnings("serial")
@Repository
public class ItemPlanDao extends BaseDao {
	
	@Autowired
	private CzybmDao czybmDao;

	/**
	 * ItemPlan分页信息
	 * 
	 * @param page
	 * @param itemPlan
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlan itemPlan,UserContext uc) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from(select a.Code,a.CustType,g.desc1 CustTypeDescr,a.Descr,a.Address,a.Layout,f.NOTE LayoutDescr,a.Area,a.Status,d.NOTE StatusDescr,"
                + "a.ContractFee,a.DesignFee,a.ManageFee,a.BaseFee,a.BaseDisc,a.BaseFee_Dirct,a.BaseFee_Comp,"
                + "a.MainFee,a.MainFee_Per,a.MainDisc,a.MainServFee,"
                + "a.SoftFee,a.SoftOther,a.SoftDisc,a.IntegrateFee,a.IntegrateDisc,a.CupboardFee,a.CupBoardDisc,"
                + "a.DesignMan,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr,"
                + "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, "
                + "MainSetFee,SetMinus,SetAdd,a.LongFee,ManageFee_Base,"
                + "ManageFee_Main,ManageFee_Int,ManageFee_Serv,ManageFee_Soft,ManageFee_Cup,a.SoftPlanRemark,g.type isoutset,b.MainManDate, cast(dbo.fGetEmpNameChi(a.Code,'32') as nvarchar(100)) MainDesignMan, "
                + "cast(dbo.fGetEmpNameChi(a.Code, '14') as nvarchar(100)) IntegrateMan,cast(dbo.fGetEmpNameChi(a.Code, '03') as nvarchar(100)) MappingDescr,"
                + "cast(dbo.fGetEmpNameChi(a.Code, '04') as nvarchar(100)) sketchDescr,cast(dbo.fGetEmpNameChi(a.Code, '64') as nvarchar(100)) DesignDescr,cast(dbo.fGetEmpNameChi(a.Code, '34') as nvarchar(100)) MainBusinessMan, "
                + "cast(dbo.fGetEmpNameChi(a.Code, '11') as nvarchar(100)) JCDesignMan, "
                + "cast(dbo.fGetEmpNameChi(a.Code, '61') as nvarchar(100)) CGDesignMan ,"
                + " j.ContractTempName,x3.NOTE ConStatusDescr,case when x2.note is null then x.note else x2.note end discapprovestatus, " +
                "  x.note ProcStatusDescr,l.no wfProcInstNo "
                + " from tCustomer a "
                + " left outer join tEmployee e1 on a.DesignMan=e1.Number "
                + " left outer join tEmployee e2 on a.BusinessMan=e2.Number "
                + " left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
                + " left outer join tXTDM e on a.CustType = e.CBM and e.ID = 'CUSTTYPE' "
                + " left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
                + " left outer join tCusttype g on g.code=a.custType "
                + " left join ( "
                + "  select a.CustCode,min(a.LastUpdate) MainManDate from tCustStakeholderHis a"
                + "  where a.Role='34' and a.OperType='1'"
                + "  group by a.CustCode"
                + " ) b on a.Code=b.CustCode "
                + " left join tBuilder h on h.Code = a.BuilderCode "
                + " left join tRegion i on i.Code = h.RegionCode "
                + " left join tCmpCustType j on j.CmpCode = i.CmpCode and j.CustType = a.CustType "
                + " left join (select max(WfProcInstNo) wfProcInstNo,a.CustCode from tWfCust_CustGiftApp a group by a.CustCode ) k on k.CustCode = a.code "
                + " left join tWfProcInst l on l.No = k.WfProcInstNo " 
                + " left join ("
                + "  select max(pk) pk,WfProcInstNo from tWfProcTrack where actionType in ('4','5','6') group by  WfProcInstNo "
                + ")m on m.WfProcInstNo = l.no"
                + " left join tWfProcTrack n on m.pk = n.pk "
                + " left join tXtdm x2 on x2.id = 'PROCACTTYPE' and x2.cbm = n.ActionType "
                + " left join tXtdm x on x.id = 'WFPROCINSTSTAT' and x.cbm = l.status "
                + " left join (select max(pk)conPk,in_a.CustCode from tCustContract in_a where in_a.ConType = '1'  group by in_a.CustCode) o on o.CustCode = a.Code "
                + " left join tCustContract p on o.conPk = p.PK "
                + " left join tXTDM x3 on p.Status = x3.CBM and x3.ID = 'CONTRACTSTAT' "
                + " where 1=1 ";
		
		// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//		String hql="select CZYBH from tCZYGNQX where MKDM='0208' and  GNMC='所有客户预算' and CZYBH=?";
//		List ls=this.findBySql(hql, new Object[]{uc.getCzybh()});
//    	if(ls==null||ls.size()<1){
//    		sql+=" and "+SqlUtil.getCustRight(uc, "a", 0);
//    	}
		if (!czybmDao.hasGNQXByCzybh(uc.getCzybh(), "0208", "所有客户预算")) {
			sql+=" and "+SqlUtil.getCustRight(uc, "a", 0);
		}
		
    	if (StringUtils.isNotBlank(itemPlan.getCustCode())) {
			sql += " and a.Code=? ";
			list.add(itemPlan.getCustCode());
		}
    	
    	if (StringUtils.isNotBlank(itemPlan.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+itemPlan.getDescr().trim()+"%");
		}
    	
    	
    	if (StringUtils.isNotBlank(itemPlan.getDesignMan())) {
			sql += " and a.designMan=? ";
			list.add(itemPlan.getDesignMan());
		}

    	if (StringUtils.isNotBlank(itemPlan.getBusinessMan())) {
			sql += " and a.businessMan=? ";
			list.add(itemPlan.getBusinessMan());
		}
    	if (StringUtils.isNotBlank(itemPlan.getAddress())) {
			sql += " and a.address like ? ";
			list.add("%"+itemPlan.getAddress().trim()+"%");
		}
    	if(StringUtils.isNotBlank(itemPlan.getStatus())){
    		sql+=" and a.status in ("+itemPlan.getStatus()+") ";
    		
    	}

		if (StringUtils.isBlank(itemPlan.getExpired()) || "F".equals(itemPlan.getExpired())) {
			sql += " and a.Expired='F' ";
		}

    	if (itemPlan.getDispSeq() != null) {
			sql += " and a.DispSeq=? ";
			list.add(itemPlan.getDispSeq());
		}
    	if(StringUtils.isNotBlank(itemPlan.getCustType())){
			sql += " and a.custtype in (" + itemPlan.getCustType() + ")";
		}
    	if (StringUtils.isNotBlank(itemPlan.getEmpCode())) {
    		sql +=" and exists (select 1 from tCustStakeholder where CustCode=a.Code and Role='32' and EmpCode=?)";
			list.add(itemPlan.getEmpCode());
		}
    	if (StringUtils.isNotBlank(itemPlan.getMainBusinessMan())) {
    		sql +=" and exists (select 1 from tCustStakeholder where CustCode=a.Code and Role='34' and EmpCode=?)";
			list.add(itemPlan.getMainBusinessMan());
		}
		if (itemPlan.getMainManDateFrom()!= null) {
			sql += " and b.MainManDate>=CONVERT(VARCHAR(10),?,120) ";
			list.add(itemPlan.getMainManDateFrom());
		}
		if (itemPlan.getMainManDateTo() != null) {
			sql += " and b.MainManDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
			list.add(itemPlan.getMainManDateTo());
		}
		if (StringUtils.isNotBlank(itemPlan.getSoftPlanRemark())) {
			sql += " and a.softPlanRemark like ? ";
			list.add("%"+itemPlan.getSoftPlanRemark().trim()+"%");
		}
		if(StringUtils.isNotBlank(itemPlan.getContractStatus())){
			sql += " and p.status = ? ";
			list.add(itemPlan.getContractStatus());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate DESC";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_zcys(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		List<Object> list = new ArrayList<Object>();
		if ((StringUtils.isBlank(itemPlan.getCustCode()) || StringUtils.isBlank(itemPlan.getItemType1())) && itemPlan.getGiftPk() == null) {
			return null;
		}
		String sql = "select * from (select a.*,c.Descr FixAreaDescr,b.Descr ItemDescr,b.isfixprice,d.Descr IntProdDescr,f.Descr Uom,rtrim(b.ItemType2) ItemType2, "
           +"e.Descr ItemType1Descr,g.Descr ItemType2Descr,sql.Descr SqlCodeDescr,st.Descr ItemSetDescr,xt.Note isoutsetdescr,"
           +"case when a.LineAmount <> 0 then round(round(a.LineAmount-a.Qty*a.Cost-a.ProcessCost,0)/ a.LineAmount,4)*100 else 0 end ProfitPer "
           +",c.preplanareapk,pre.Descr PrePlanAreaDescr,isnull(dbo.fGetItemPlanAutoQty(a.custcode,a.Algorithm,a.ItemCode,c.preplanareapk,isnull(a.DoorPK,0),isnull(a.tempdtpk,0),a.AlgorithmPer,a.AlgorithmDeduct),0) AutoQty "
           +",at.descr AlgorithmDescr,x1.note cuttypeDescr,x2.NOTE CommiTypeDescr,gf.Descr GiftDescr,i.Descr SupplPromDescr,x3.note CanModiQtyDescr "
           +"from (select a.isservice,a.giftPK,a.supplpromitempk,a.pk itemPlanPK,a.PK,a.CustCode,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.iscommi,a.itemSetNo,a.CustTypeItemPk,"
           +"a.ItemType1,a.ProjectQty,a.Qty,a.UnitPrice,a.ProcessCost,"
           +"a.Cost,a.BefLineAmount,a.MarkUp,(a.LineAmount-isnull(a.ProcessCost,0)) TmpLineAmount,a.LineAmount,a.DispSeq,"
           +"a.Remark,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired,a.Cost*a.Qty CostAll,a.isoutset,a.Algorithm,a.DoorPK,a.CutType,a.TempDtPk,a.IsGift," 
		   +"a.OldProjectCost,a.ProjectCost,AlgorithmPer,AlgorithmDeduct,a.CanModiQty, ";
        if (!"1".equals(itemPlan.getCostRight())) { //主材预算增加提成类型、提成比例，服务性产品、软装、集成预算不用加，删掉b.CommiType modify by zb
        	sql += " '没有权限查看' CommiType,'没有权限查看' CommiPerc " ;
		} else {
			sql += "a.CommiType,a.CommiPerc " ; 
		}
	    sql += "from tItemPlan a ";
		if ("JC".equals(itemPlan.getItemType1())){
			sql += "inner join tIntProd b on a.IntProdPK=b.Pk ";
		}
        sql += "where a.CustCode=?  ";
        list.add(itemPlan.getCustCode());
        if(StringUtils.isNotBlank(itemPlan.getItemType1())){
        	sql+=" and a.itemTYpe1 = ? ";
        	list.add(itemPlan.getItemType1());
        }
		if ("JC".equals(itemPlan.getItemType1()) 
				&& StringUtils.isNotBlank(itemPlan.getIsIntPerfDetail()) 
				&& StringUtils.isNotBlank(itemPlan.getIsCupboard())){//集成业绩计算明细用于判断是否橱柜 add by cjg
			sql += " and b.iscupboard=? ";
			list.add(itemPlan.getIsCupboard());
		}
		if ("ZC".equals(itemPlan.getItemType1()) && StringUtils.isBlank(itemPlan.getIsCustGift())){
			if (itemPlan.getIsService()==1){
				if(!itemPlan.isContainMainServ()){
					sql += "and a.isService=? and exists(select 1 from tCustomer where a.Custcode=Code and ContainMainServ=1) ";
				}else{
					sql += "and a.isService=? and exists(select 1 from tCustomer where a.Custcode=Code ) ";
				}
			
			}else{
				if(itemPlan.isExceptMain()){
					sql += "and a.isService=? and exists(select 1 from tCustomer where a.Custcode=Code) ";
				}else{
					//默认总价包含主材
					sql += "and a.isService=? and exists(select 1 from tCustomer where a.Custcode=Code and ContainMain=1) ";
				}
				
			}
			list.add(itemPlan.getIsService());
		}else if ("RZ".equals(itemPlan.getItemType1())){
			sql += "and exists(select 1 from tCustomer where a.Custcode=Code and ContainSoft=1) ";
		}else if ("JC".equals(itemPlan.getItemType1())){
			sql += "and exists(select 1 from tCustomer aa "
				+ "where a.Custcode=aa.Code and (aa.ContainInt=1 and b.IsCupboard='0' or aa.ContainCup=1 and b.IsCupboard='1')) ";
		}
        sql+=") a "
           +"left join tItem b on b.Code=a.ItemCode "
           +"left join tBrand sql on sql.code=b.sqlcode "
           +"left join tItemType1 e on e.Code=a.ItemType1 "
           +"left join tItemType2 g on g.Code=b.ItemType2 "
           +"left join tFixArea c on c.Pk=a.FixAreaPK "
           +"left join tIntProd d on d.PK=a.IntProdPK "
           +"left join tUom f on b.Uom=f.Code "
           +"left join tItemSet st on a.itemSetNo=st.no "
           +"left join txtdm xt on a.IsOutSet=xt.Cbm and xt.id='YESNO' "
           +"left join tPrePlanArea pre ON pre.pk=c.PrePlanAreaPK "
           +"left join tAlgorithm at  on at.Code=a. Algorithm "
           +"left join txtdm x1 on  x1.id='CUTTYPE' and  x1.cbm=a.cuttype "
           +"left join tXTDM x2 on x2.CBM=a.CommiType and x2.ID='COMMITYPE' " //提成类型 add by zb
           +"left outer join tgift gf on gf.PK=a.GiftPK " +
           " left join tSupplPromItem h on h.pk = a.supplpromitempk " +
           " left join tSupplProm i on i.no = h.no "
           +"left join tXTDM x3 on x3.CBM=a.CanModiQty and x3.ID='YESNO' " //提成类型 add by zb
           +"where a.CustCode=? "
           +" ";
		list.add(itemPlan.getCustCode());
		if(StringUtils.isNotBlank(itemPlan.getItemType1())){
        	sql+=" and a.itemTYpe1 = ? ";
        	list.add(itemPlan.getItemType1().trim());
        }
		if (StringUtils.isNotBlank(itemPlan.getFixareadescr())) {
			sql += "and c.Descr like ? ";
			list.add("%"+itemPlan.getFixareadescr()+"%");
		}
		if (StringUtils.isNotBlank(itemPlan.getItemdescr())) {
			sql += "and b.Descr like ? ";
			list.add("%"+itemPlan.getItemdescr()+"%");
		}
		if (StringUtils.isNotBlank(itemPlan.getItemtype2descr())) {
			sql += "and g.Descr like ? ";
			list.add("%"+itemPlan.getItemtype2descr()+"%");
		}
		if (StringUtils.isNotBlank(itemPlan.getItemType2())) {
			sql += "and b.itemType2 = ? ";
			list.add(itemPlan.getItemType2());
		}
		if(itemPlan.getGiftPk() != null){
			sql+=" and (a.giftPK = ? or a.giftPK is null or a.giftPK = '') " +
				" and (exists( select 1 from tGiftItem in_a where in_a.giftpk = ? and in_a.itemCode = a.itemCode " +
				" ) or not exists( select 1 from tGiftItem in_b where in_b.giftpk = ? )" +
				") " +
				" and exists (select 1 from tgift in_c where in_c.pk = ? and in_c.discAmtType = a.itemType1 and in_c.isService = a.IsService ) " +
				" and not exists( select 1 from tCustGiftItem in_d " +
				"              left join tCustGift in_f on in_f.pk = in_d.custGiftPk " +
				"				where  in_f.custCode = a.CustCode and in_d.itemCOde = a.ItemCode and in_d.FixAreaPK=a.FixAreaPK)" +
				"  ";
			list.add(itemPlan.getGiftPk());
			list.add(itemPlan.getGiftPk());
			list.add(itemPlan.getGiftPk());
			list.add(itemPlan.getGiftPk());
		}
        if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.DispSeq,a.FixAreaPk";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public boolean hasItemPlan(String custCode) {
		String sql = "select top 1 Custcode from tItemPlan where Custcode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}

	public Page<Map<String, Object>> findPageBySql_rzys(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		String sql="select a.PK,a.CustCode,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.ItemType1,a.ProjectQty,a.Qty,f.Descr Uom,a.Cost," +
				"a.UnitPrice,a.BefLineAmount,a.MarkUp,round(a.BefLineAmount*a.MarkUp/100,0) TmpLineAmount,isnull(a.ProcessCost,0) ProcessCost," +
				"a.LineAmount,a.DispSeq,a.Remark,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired, c.Descr FixAreaDescr,b.Descr ItemDescr," +
				"d.Descr IntProdDescr,d.IsCupboard,case when d.isCupboard=1 then '是' else '否' end isCupboardDescr,b.commitype, e.Descr ItemType1Descr,g.Code Item2Code,g.Descr ItemType2Descr, "+ 
				"b.IsFixPrice, rtrim(b.ItemType2) ItemType2,a.IsOutSet,x1.NOTE IsOutSetDescr,b.ItemType3,it3.Descr ItemType3Descr,a.itemsetno,a.CustTypeItemPk,h.descr itemsetDescr,  " +
				"IsGift,GiftPK,gf.Descr GiftDescr, "+
				"a.OldProjectCost,a.ProjectCost, b.IsClearInv  "+
				"from tItemPlan a  left outer join tItem b on b.Code=a.ItemCode  " +
				"left outer join tItemType1 e on e.Code=a.ItemType1  " +
				"left outer join tItemType2 g on g.Code=b.ItemType2  " +
				"left outer join tFixArea c on c.Pk=a.FixAreaPK  " +
				"left outer join tIntProd d on d.PK=a.IntProdPK  " +
				"left outer join tUom f on b.Uom=f.Code  " +
				"left outer join tXTDM x1 on x1.ID='YESNO' and x1.CBM=a.IsOutSet " +
				"left outer join tItemType3 it3 on it3.Code=b.ItemType3  " +
				"left join titemset h on h.no=a.itemsetno "+
				"left outer join tgift gf on gf.PK=a.GiftPK "+
				"where rtrim(a.CustCode)=? and rtrim(a.ItemType1)=? order by a.DispSeq ";
			if (StringUtils.isBlank(itemPlan.getCustCode()) || StringUtils.isBlank(itemPlan.getItemType1())) {
					return null;
		}
		return this.findPageBySql(page, sql,new Object[]{itemPlan.getCustCode(),itemPlan.getItemType1()});
	}

	public Result doItemForProc(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClys_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			String FromStatus="";
			call.setString(1, itemPlan.getItemType1().substring(0, 1));
			call.setString(2,itemPlan.getCustCode());
			call.setString(3,itemPlan.getItemType1());
			if("JC".equals(itemPlan.getItemType1())){
				call.setDouble(4, itemPlan.getBefLineAmount());
			}else{
				call.setDouble(4, itemPlan.getFee());
			}
			
			call.setDouble(5, itemPlan.getDiscAmount());
			if("RZ".equals(itemPlan.getItemType1())){
				call.setDouble(6, itemPlan.getFee());
				call.setString(16, itemPlan.getSoftPlanRemark());
			}else{
				call.setDouble(6, 0);
				call.setString(16, null);
			}
			call.setDouble(7, itemPlan.getCupboardFee()==null?0:itemPlan.getCupboardFee());
			call.setDouble(8, itemPlan.getCupboardDisc()==null?0:itemPlan.getCupboardDisc());
			call.setString(9,itemPlan.getLastUpdatedBy());
			call.setDouble(10,itemPlan.getSoftFee_WallPaper()==null?0:itemPlan.getSoftFee_WallPaper());
			call.setDouble(11,itemPlan.getSoftFee_Curtain()==null?0:itemPlan.getSoftFee_Curtain());
			call.setDouble(12,itemPlan.getSoftFee_Light()==null?0:itemPlan.getSoftFee_Light());
			call.setDouble(13,itemPlan.getSoftFee_Furniture()==null?0:itemPlan.getSoftFee_Furniture());
			call.setDouble(14,itemPlan.getSoftFee_Adornment()==null?0:itemPlan.getSoftFee_Adornment());
			call.setString(15,itemPlan.getCustType());
			call.setDouble(17,itemPlan.getManageFee_Main()==null?0:itemPlan.getManageFee_Main());
			call.setDouble(18,itemPlan.getManageFee_Serv()==null?0:itemPlan.getManageFee_Serv());
			call.setDouble(19,itemPlan.getManageFee_Soft()==null?0:itemPlan.getManageFee_Soft());
			call.setDouble(20,itemPlan.getManageFee_Int()==null?0:itemPlan.getManageFee_Int());
			call.setDouble(21,itemPlan.getManageFee_Cup()==null?0:itemPlan.getManageFee_Cup());
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.NVARCHAR);
			call.setString(24,itemPlan.getItemPlanDetailXml());
			call.setString(25,itemPlan.getMainTempNo());
			call.setTimestamp(26, itemPlan.getPlanSendDate()== null ? null
					: new Timestamp(itemPlan.getPlanSendDate().getTime()));
			call.setString(27,itemPlan.getDiscTokenNo());
			call.execute();
			result.setCode(String.valueOf(call.getInt(22)));
			result.setInfo(call.getString(23));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public void importDetail(Map<String, Object> data) {
		// TODO Auto-generated method stub
		Assert.notNull(data);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClys_Import_BS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1,data.get("custCode").toString());
			call.setString(2,data.get("itemType1").toString());
			call.setString(3,data.get("isservice").toString());
			call.setInt(4, 1);
			call.setString(5,data.get("fixareadescr").toString());
			call.setString(6,data.get("intproddescr").toString());
			call.setString(7,data.get("iscupboard").toString());
			call.setString(8,data.get("itemcode").toString());
			call.setDouble(9, Double.parseDouble(data.get("projectqty").toString()));
			call.setDouble(10, Double.parseDouble(data.get("qty").toString()));
			call.setDouble(11, Double.parseDouble(data.get("unitprice").toString()));
			call.setDouble(12, Double.parseDouble(data.get("markup").toString()));
			call.setDouble(13, Double.parseDouble(data.get("processcost").toString()));
			call.setString(14,data.get("remark").toString());
			call.setString(15,data.get("isoutset").toString());
			call.setString(16,data.get("lastupdatedby").toString());
			call.setInt(17, Integer.parseInt(data.get("dispseq").toString()));
			call.setString(18,data.get("itemsetdescr").toString());
			call.setString(19,data.get("algorithmdescr")==null?"":data.get("algorithmdescr").toString());
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.NVARCHAR);
			call.registerOutParameter(23, Types.NVARCHAR);
			call.registerOutParameter(24, Types.INTEGER);
			call.setString(25,data.get("preplanareadescr")==null?"":data.get("preplanareadescr").toString());
			call.registerOutParameter(26, Types.INTEGER);
			call.registerOutParameter(27, Types.NVARCHAR);
			call.execute();
			if(call.getInt(26)==1){
				data.put("fixareapk", call.getInt(20));
				data.put("intprodpk", call.getInt(21));
				data.put("itemsetno", call.getString(22));
				data.put("algorithm", call.getString(23));
				data.put("preplanareapk", call.getString(24));
				
			}else{
				data.put("isinvalid", 1);
				data.put("isinvaliddescr", call.getString(27));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
	}

	public Page<Map<String, Object>> findItemPlanList(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		List<Object> list = new ArrayList<Object>();
		String sql="select a.IsService,a.PK,a.CustCode,b.Descr CustomerDescr,b.Address,a.FixAreaPK,c.Descr FixAreaDescr,a.IntProdPK,d.Descr IntProdDescr,a.ItemCode," +
				"e.Descr ItemDescr,a.ItemType1,f.Descr ItemType1Descr,rtrim(e.ItemType2) ItemType2,i.descr itemtype2Descr,j.descr itemtype3Descr,a.Qty,e.uom,g.Descr UomDescr,a.Cost,a.UnitPrice,a.Markup, a.Qty*a.UnitPrice beflineamount,(a.LineAmount-isnull(a.ProcessCost,0)) TmpLineAmount,isnull(a.ProcessCost,0) ProcessCost," +
				"a.LineAmount,a.Remark,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog, a.ItemSetNo,h.Descr ItemSetDescr  " +
				"from tItemPlan a left outer join tCustomer b on b.Code=a.CustCode  " +
				"left outer join tFixArea c on c.PK=a.FixAreaPK  " +
				"left outer join tIntProd d on d.PK=a.IntProdPK  " +
				"left outer join tItem e on e.Code=a.ItemCode  " +
				"left outer join tItemType1 f on f.Code=a.ItemType1  " +
				"left outer join tUom g on e.Uom=g.Code  " +
				"left outer join tItemSet h on h.No=a.ItemSetNo  " +
				"left outer join tItemType2 i on i.Code=e.ItemType2  " +
				"left outer join tItemType3 j on j.Code=e.ItemType3  " +
				"where a.Expired='F'  ";
				if(StringUtils.isNotBlank(itemPlan.getCustCode())){
					sql+=" and a.CustCode=?";
					list.add(itemPlan.getCustCode());
				}
				if(StringUtils.isNotBlank(itemPlan.getItemType1())){
					sql+=" and a.ItemType1=?";
					list.add(itemPlan.getItemType1().trim());
				}
				if(itemPlan.getIsService()!=null){
					sql+=" and a.IsService=?";
					list.add(itemPlan.getIsService());
				}
				sql+="order by a.DispSeq ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	public Result doBaseBatchTempProc(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBaseItemPlanFromTemp_forXml(?,?,?,?,?,?)}");
			call.setString(1, itemPlan.getTempNo());
			call.setString(2, itemPlan.getCustCode());
			call.setString(3, itemPlan.getLastUpdatedBy());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.setString(6,itemPlan.getItemPlanDetailXml());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				for (String k : map.keySet()) {
					if (map.get(k)==null){
						map.put(k, "");
					}
	            }
				String date= map.get("lastupdate").toString();
				JSONObject json = JSONObject.fromObject(map); 
				json.put("lastupdate", date);
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	public Result doBaseItemTempProc(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBasePlanFromPrePlanTemp(?,?,?,?,?)}");
			call.setString(1, itemPlan.getCustCode());
			call.setString(2, itemPlan.getTempNo());
			call.setString(3, itemPlan.getLastUpdatedBy());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				for (String k : map.keySet()) {
					if (map.get(k)==null){
						map.put(k, "");
					}
	            }
				String date= map.get("lastupdate").toString();
				JSONObject json = JSONObject.fromObject(map); 
				json.put("lastupdate", date);
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Result doCopyPlan(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCopyPlan_forXml(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemPlan.getsCustCode());
			call.setString(2, itemPlan.getCustCode());
			call.setString(3, itemPlan.getItemType1());
			call.setInt(4, itemPlan.getIsService());
			call.setInt(5, 1);
			call.setString(6, itemPlan.getLastUpdatedBy());
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.NVARCHAR);
			call.setString(9, itemPlan.getIsOutSet());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				for (String k : map.keySet()) {
					if (map.get(k)==null){
						map.put(k, "");
					}
	            }
				String date= map.get("lastupdate").toString();
				JSONObject json = JSONObject.fromObject(map); 
				json.put("lastupdate", date);
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(7)));
			result.setInfo(call.getString(8));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Result doCopyPlanPre(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCopyPlanFromPrePlan_forXml(?,?,?,?)}");
			call.setString(1, itemPlan.getCustCode());
			call.setString(2, itemPlan.getLastUpdatedBy());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				JSONObject json = JSONObject.fromObject(map); 
				if(map.get("lastupdate")!=null){
					String date= map.get("lastupdate").toString();
					json.put("lastupdate", date);
				}
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Result doPrePlanTemp(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pPlanFromPrePlanTemp_ZC(?,?,?,?,?)}");
			call.setString(1, itemPlan.getCustCode());
			call.setString(2, itemPlan.getTempNo());
			call.setString(3, itemPlan.getLastUpdatedBy());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				JSONObject json = JSONObject.fromObject(map); 
				if(map.get("lastupdate")!=null){
					String date= map.get("lastupdate").toString();
					json.put("lastupdate", date);
				}
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public Result doItemTCForProc(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClys_TC_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			String FromStatus="";
			call.setString(1, itemPlan.getItemType1().substring(0, 1));
			call.setString(2,itemPlan.getCustCode());
			call.setString(3,itemPlan.getItemType1());
			if("JC".equals(itemPlan.getItemType1())){
				call.setDouble(4, itemPlan.getBefLineAmount());
			}else{
				call.setDouble(4, itemPlan.getFee());
			}
			
			call.setDouble(5, itemPlan.getDiscAmount());
			call.setDouble(6, 0);
			call.setDouble(7, itemPlan.getCupboardFee()==null?0:itemPlan.getCupboardFee());
			call.setDouble(8, itemPlan.getCupboardDisc()==null?0:itemPlan.getCupboardDisc());
			call.setString(9,itemPlan.getLastUpdatedBy());
			call.setDouble(10,itemPlan.getSoftFee_WallPaper()==null?0:itemPlan.getSoftFee_WallPaper());
			call.setDouble(11,itemPlan.getSoftFee_Curtain()==null?0:itemPlan.getSoftFee_Curtain());
			call.setDouble(12,itemPlan.getSoftFee_Light()==null?0:itemPlan.getSoftFee_Light());
			call.setDouble(13,itemPlan.getSoftFee_Furniture()==null?0:itemPlan.getSoftFee_Furniture());
			call.setDouble(14,itemPlan.getSoftFee_Adornment()==null?0:itemPlan.getSoftFee_Adornment());
			call.setDouble(15,itemPlan.getManageFee_Main()==null?0:itemPlan.getManageFee_Main());
			call.setDouble(16,itemPlan.getManageFee_Serv()==null?0:itemPlan.getManageFee_Serv());
			call.setDouble(17,itemPlan.getManageFee_Soft()==null?0:itemPlan.getManageFee_Soft());
			call.setDouble(18,itemPlan.getManageFee_Int()==null?0:itemPlan.getManageFee_Int());
			call.setDouble(19,itemPlan.getManageFee_Cup()==null?0:itemPlan.getManageFee_Cup());
			call.setString(20,itemPlan.getCustType());
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.NVARCHAR);
			call.setString(23,itemPlan.getItemPlanDetailXml());
			call.setString(24,itemPlan.getMainTempNo());
			call.execute();
			result.setCode(String.valueOf(call.getInt(21)));
			result.setInfo(call.getString(22));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}

	public Page<Map<String, Object>> findPageBySql_ckyj(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		Assert.notNull(itemPlan);

		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pYsgl_ckyj(?)}");
			call.setString(1, itemPlan.getCustCode());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			if (!list.isEmpty()) {
				page.setTotalCount(list.size());
			} else {
				page.setTotalCount(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}

	public long getItemPlanCount(ItemPlan itemPlan) {
		List<Object> list = new ArrayList<Object>();
		String sql=" select *  from tItemPlan a where 1=1 ";
		if(StringUtils.isNotBlank(itemPlan.getCustCode())){
			sql+=" and  a.custCode=? ";
			list.add(itemPlan.getCustCode());
		}
		return this.countSqlResult(sql, "", list.toArray());
	}

	public Page<Map<String, Object>> findPageBySql_zcmlfx(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		String sql="select 1 from tCustomer where Code=? and (ContainMain='0' or MainFee=0) ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemPlan.getCustCode()});
		if (list!=null && list.size()>0){
			return null;
		}
		String hql="select a.IsService,a.IsCommi,a.PK,a.CustCode,a.FixAreaPK,a.IntProdPK,a.ItemCode,"
	               + "a.ItemType1,a.ProjectQty,a.Qty,f.Descr Uom,a.Cost,a.UnitPrice,"
	               + "a.BefLineAmount,a.MarkUp,round(a.BefLineAmount*a.MarkUp/100,0) TmpLineAmount,"
	               + "a.ProcessCost,a.LineAmount,a.DispSeq,"
	               + "a.Remark,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired, "
	               + "c.Descr FixAreaDescr,b.Descr ItemDescr,d.Descr IntProdDescr, "
	               + "e.Descr ItemType1Descr,g.Code Item2Code,right('00000'+cast(g.DispSeq as varchar(20)),4)+' '+g.Descr ItemType2Descr, "
	               + "b.IsFixPrice, " 
	               + " case when a.LineAmount <> 0 then  a.LineAmount-a.Qty*case when a.Cost = 0 then a.UnitPrice else a.Cost end-a.ProcessCost "
	               + " else 0 end Profit,"
	               + "case when a.LineAmount <> 0 then round((a.LineAmount-a.Qty * "
	               + "case when a.Cost = 0 then a.UnitPrice else a.Cost end-a.ProcessCost)*100/ a.LineAmount,2) else 0 end ProfitPer"
	               //正确的毛利是下面这段，因涉及项目经理结算价暂时先不用
//	               + " case when case when a.isoutset = '1' then a.lineamount else round(round(a.qty * b.projectcost * a.markup / 100, 0) + a.processcost, 0) end <> 0 "
//	               + "      then  case when a.isoutset = '1' then  a.lineamount - a.qty * case when a.cost = 0 then a.unitprice else a.cost end - a.processcost " 
//				   + "			  else round(round(a.qty * b.projectcost * a.markup / 100, 0) + a.processcost, 0) - a.qty* case when b.cost = 0 then a.unitprice else a.cost end - a.processcost end "          
//                   + "       else 0 end profit, "  
//                   + " case when case when a.isoutset = '1' then a.lineamount else round(round(a.qty * b.projectcost * a.markup / 100, 0) + a.processcost, 0) end <> 0 "
//                   + "      then round(( case when a.isoutset = '1' then  a.lineamount - a.qty * case when a.cost = 0 then a.unitprice else a.cost end - a.processcost " 
//				   + "						  else round(round(a.qty * b.projectcost * a.markup / 100, 0) + a.processcost, 0) - a.qty* case when b.cost = 0 then a.unitprice else a.cost end - a.processcost end) " 
//                   + "                  /(case when a.isoutset = '1' then a.lineamount else round(round(a.qty * b.projectcost * a.markup / 100, 0) + a.processcost, 0) end )* 100, 2) "
//                   + "      else 0 end profitper "
                   + " from tItemPlan a "
	               + " left outer join tItem b on b.Code=a.ItemCode "
	               + " left outer join tItemType1 e on e.Code=a.ItemType1 "
	               + " left outer join tItemType2 g on g.Code=b.ItemType2 "
	               + " left outer join tFixArea c on c.Pk=a.FixAreaPK "
	               + " left outer join tIntProd d on d.PK=a.IntProdPK "
	               + " left outer join tUom f on b.Uom=f.Code "
	               + " where a.ItemType1='ZC' and a.IsService='0' and a.CustCode=? "
	               + " order by a.DispSeq";
		return this.findPageBySql(page, hql, new Object[]{itemPlan.getCustCode()});
		
	}
	
	public Map<String, Object> findBySql_mlfx(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pCallProfit(?)}");
			call.setString(1, itemPlan.getCustCode());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return null;
	}

	public Result doClearPlanForProc(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClearPlan(?,?,?,?,?)}");
			call.setString(1,itemPlan.getCustCode());
			call.setString(2, itemPlan.getItemType1().trim());
			call.setInt(3, itemPlan.getIsService());
			call.setInt(4,itemPlan.getIsClearFixArea());
			call.setString(5, itemPlan.getLastUpdatedBy());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			if(list!=null&&list.size()>0){
				Map<String, Object> map=list.get(0);
				result.setCode(map.get("ret").toString());
				result.setInfo(map.get("errmsg").toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	/*
	 * 获取四期款
	 * @param  
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Map<String, Object> findBySql_GetFourCustPay(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGetFourCustPay(?,?,?,?,?,?,?)}");
			call.setString(1, itemPlan.getCustCode());
			call.setInt(2, 1);	
			call.setString(3, itemPlan.getPayType());
			call.registerOutParameter(4, Types.NUMERIC);
			call.registerOutParameter(5, Types.NUMERIC);
			call.registerOutParameter(6, Types.NUMERIC);
			call.registerOutParameter(7, Types.NUMERIC);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
//			ResultSet rs = call.getResultSet();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return  null;
		
	}
	
	/**
	 * 获取付款说明
	 * @param  
	 * @return
	 * 
	 */
	public Map<String, Object> getPayRemark(ItemPlan itemPlan){
		String sql=" select  rtrim(prd1.PayRemark) PayRemark1 ,rtrim(prd2.PayRemark)  PayRemark2,rtrim(prd3.PayRemark)  PayRemark3, "
        + " rtrim(prd4.PayRemark)  PayRemark4,case when isnull(prd1.IsRcvDesignFee,'0')='1' or isnull(prd2.IsRcvDesignFee,'0')='1' or isnull(prd3.IsRcvDesignFee,'0')='1' or isnull(prd4.IsRcvDesignFee,'0')='1' then pr.DesignFeeType else '3' end DesignFeeType," 
        + " isnull(prd1.IsRcvDesignFee,0) IsRcvDesignFee1, "
        + " isnull(prd2.IsRcvDesignFee,0) IsRcvDesignFee2,isnull(prd3.IsRcvDesignFee,0) IsRcvDesignFee3,isnull(prd4.IsRcvDesignFee,0) IsRcvDesignFee4 "
        + " from tPayRule pr  "
        + " left outer join tPayRuleDetail prd1 on prd1.no=pr.No and prd1.paynum='1' "
        + " left outer join tPayRuleDetail prd2 on prd2.no=pr.No and prd2.paynum='2' "
        + " left outer join tPayRuleDetail prd3 on prd3.no=pr.No and prd3.paynum='3' "
        + " left outer join tPayRuleDetail prd4 on prd4.no=pr.No and prd4.paynum='4' "
		+ " where pr.CustType=? and pr.PayType=? ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{itemPlan.getCustType(),itemPlan.getPayType()});
		if (list!=null && list.size()>0){
			 return list.get(0);
		}
		return null;
	}
	
	/*
	 * 获取预报价数量、预估施工量、其他费用
	 * @param  
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Map<String, Object> findBySql_GetItemPlanQty(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGetItemPlanQty_forXml(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemPlan.getCustCode());
			call.setString(2, itemPlan.getAlgorithm());
			call.setString(3, itemPlan.getItemCode());
			call.setInt(4, itemPlan.getPrePlanAreaPK()==null?0:itemPlan.getPrePlanAreaPK());
			call.setInt(5, itemPlan.getDoorPK()==null?0:itemPlan.getDoorPK());
			call.setString(6, itemPlan.getCutType());
			call.setString(7,itemPlan.getItemPlanDetailXml());
			call.registerOutParameter(8, Types.NUMERIC);
			call.registerOutParameter(9, Types.NUMERIC);
			call.registerOutParameter(10, Types.NUMERIC);
			call.setString(11, itemPlan.getType());
			call.setDouble(12, itemPlan.getQty()==null?0:itemPlan.getQty());
			call.setString(13, itemPlan.getIsOutSet());
			call.setDouble(14, itemPlan.getAlgorithmPer()==null? 1.0 :itemPlan.getAlgorithmPer());
			call.setDouble(15, itemPlan.getAlgorithmDeduct()==null? 0.0 :itemPlan.getAlgorithmDeduct());
			call.setInt(16, itemPlan.getTempDtPk()==null? 0:itemPlan.getTempDtPk());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
//			ResultSet rs = call.getResultSet();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return  null;
		
	}
	public Result doRegenFromPrePlanTemp(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRegenFromPrePlanTemp_ZC(?,?,?,?,?,?)}");
			call.setString(1, itemPlan.getCustCode());
			call.setString(2, itemPlan.getTempNo());
			call.setString(3, itemPlan.getLastUpdatedBy());
			call.setString(4,itemPlan.getItemPlanDetailXml());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				for (String k : map.keySet()) {
					if (map.get(k)==null){
						map.put(k, "");
					}
	            }
				JSONObject json = JSONObject.fromObject(map); 
				if(map.get("lastupdate")!=null){
					String date= map.get("lastupdate").toString();
					json.put("lastupdate", date);
				}
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	public Result doRegenBasePlanFromPrePlanTemp(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRegenBasePlanFromPrePlanTemp(?,?,?,?,?,?,?,?)}");
			call.setString(1, itemPlan.getCustCode());
			call.setString(2, itemPlan.getTempNo());
			call.setString(3, itemPlan.getLastUpdatedBy());
			call.setString(4,itemPlan.getItemPlanDetailXml());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.setString(7, itemPlan.getIsRegenCanreplace());
			call.setString(8, itemPlan.getIsRegenCanmodiQty());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				for (String k : map.keySet()) {
					if (map.get(k)==null){
						map.put(k, "");
					}
	            }
				JSONObject json = JSONObject.fromObject(map); 
				if(map.get("lastupdate")!=null){
					String date= map.get("lastupdate").toString();
					json.put("lastupdate", date);
				}
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	
	/*
	 * 获取预报价数量、预估施工量、其他费用
	 * @param  
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Map<String, Object> findBySql_GetBaseItemPlanQty(BaseItemPlan baseItemPlan) {
		Assert.notNull(baseItemPlan);
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGetBaseItemPlanQty_forProc(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, baseItemPlan.getCustCode());
			call.setString(2, baseItemPlan.getBaseAlgorithm());
			call.setString(3, baseItemPlan.getBaseItemCode());
			call.setInt(4, baseItemPlan.getPrePlanAreaPK()==null?0:baseItemPlan.getPrePlanAreaPK());
			call.setString(5, "1");
			call.registerOutParameter(6, Types.NUMERIC);
			call.setString(7, "0");
			call.registerOutParameter(8, Types.NUMERIC);
			call.registerOutParameter(9, Types.NUMERIC);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return  null;
		
	}
	

	public Page<Map<String, Object>> findPrePlanAreaList(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		List<Object> list = new ArrayList<Object>();
		String sql=" select d.descr custDescr,b.descr fixAreaTypeDescr,a.Area 面积,Perimeter 周长,Height 高度," +
				" BeamLength 包梁长度,ShowerLength 淋浴房长,ShowerWidth 淋浴房宽,DispSeq,x1.NOTE 铺贴类型 ," +
				" a.descr preplanAreaDescr from tPrePlanArea a" +
				" left join tFixAreaType b on b.Code=a.FixAreaType" +
				" left join tPrePlan c on c.no =a.No  " +
				" left join tcustomer d on c.CustCode=d.code " +
				" left join txtdm x1 on x1.CBM=a.PaveType and x1.id='PAVETYPE'" +
				" where 1=1 ";
				//" order by a.DispSeq ";
		if(StringUtils.isNotBlank(itemPlan.getCustCode())){
			sql+=" and d.code = ? ";
			list.add(itemPlan.getCustCode());
		}
		sql+=" order by a.DispSeq ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object> >getFixAreaTypes(){
		String sql="select code,descr from tFixAreaType where 1=1 and IsDefaultArea='0' and expired = 'F' order by dispSeq ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{});
		if (list!=null && list.size()>0){
			 return list;
		}
		return null;
	}
	
	public Result doBaseAndItemPlanBak(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBaseAndItemPlanBak(?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemPlan.getM_umState());
			call.setString(2, itemPlan.getCustCode());
			call.setString(3, itemPlan.getItemType1());
			call.setInt(4, itemPlan.getIsService()==null? 0:itemPlan.getIsService());
			call.setString(5, itemPlan.getLastUpdatedBy());
			call.setString(6, itemPlan.getRemark());
			call.setString(7,itemPlan.getItemPlanDetailXml());
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(8)));
			result.setInfo(call.getString(9));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
		
	}
	public Page<Map<String,Object>> findPlanBakPageBySql(Page<Map<String,Object>> page, ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGetPlanBakDetail(?,?,?,?,?,?,?,?)}");
			call.setString(1, itemPlan.getPlanBakNo());
			call.setString(2, itemPlan.getCustCode());
			call.setString(3, itemPlan.getItemType1());
			call.setInt(4, itemPlan.getIsService());
			call.setString(5, itemPlan.getLastUpdatedBy());
			call.registerOutParameter(6, Types.INTEGER);
			call.registerOutParameter(7, Types.NVARCHAR);
			call.setString(8, itemPlan.getIsOutSet());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 	
			page.setTotalCount(page.getResult().size());
			/*if (!list.isEmpty()) {
				page.setTotalCount((Integer)list.get(0).get("totalcount"));
			} else {
				page.setTotalCount(0);
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	} 
	
	/**
	 * 获取空间名称列表
	 * @param  
	 * @return
	 */
	public List<Map<String,Object>> getPrePlanAreaDescr(String custCode){
		String sql="select a.Descr from tPrePlanArea a inner join tFixAreaType b on " 
				  + " a.FixAreaType=b.Code where b.IsDefaultArea='0'and  a.CustCode=?  ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			 return list;
		}
		return null;
	}
	
	public boolean getDelNotify(Integer pk,String custCode){
		String sql=" select 1 from tFixArea where PrePlanAreaPK = ? and CustCode=?  ";
		List<Map<String,Object>> list=this.findBySql(sql, new Object[]{pk,custCode});
		if (list!=null && list.size()>0){
			 return false;
		}
		return true;
	}
	
	/**
	 * 获取税金
	 * @param custoemr
	 * @return
	 */
	public String getTax(Customer customer) {
		String sql = " select dbo.fGetTax(?,?,?,?,?,?,?,?,?) tax  ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {customer.getCode(),customer.getContractFee(),customer.getDesignFee(),
						customer.getTaxCallType(),customer.getDiscAmount(),customer.getSetAdd(),
						customer.getManageFeeBase(), customer.getBaseFeeComp(), customer.getBaseFeeDirct()});
		if (list != null && list.size() > 0) {
			return list.get(0).get("tax").toString();
		} else {
			return "0.00";
		}
	}
	/**
	 * 获取合同造价表类型
	 * @param custoemr
	 * @return
	 */
	public List<Map<String, Object>> getContractFeeRepType(Customer customer) {
		String sql = " select contractFeeRepType from tPayRule where custtype=? and payType=?  ";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {customer.getCustType(),customer.getPayType()});
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}
	
	public Page<Map<String, Object>> findPageBySql_giftByDescr(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		List<Object> list = new ArrayList<Object>();
		String sql="select * from ( " 
			+" select a.pk,a.Descr,a.IsService,a.Type ,a.IsLimitItem,x1.NOTE TypeDescr,x2.NOTE IsServiceDescr,x3.NOTE IsLimitItemDescr,a.discper, "
			+" a.disctype,a.DispSeq,a.IsOutSet,x4.note IsOutSetDescr "
			+" from tGift a "
			+" left join tXTDM x1 on x1.ID='GIFTTYPE'  and x1.CBM=a.type "
			+" left join tXTDM x2 on x2.ID='YESNO'  and x2.CBM=a.IsService "
			+" left join tXTDM x3 on x3.ID='YESNO'  and x3.CBM=a.IsLimitItem "
			+" left join tXTDM x4 on x4.id='YESNO' and a.IsOutSet=x4.cbm "
			+" where a.Expired='F' and a.Type='3'  " ;
			if(StringUtils.isNotBlank(itemPlan.getGiftDescr())){
				sql+="and a.Descr like ? ";
				list.add("%"+itemPlan.getGiftDescr().trim()+"%");
			}
			if(StringUtils.isNotBlank(itemPlan.getCustCode())){
				sql+=" and (exists (select 1 from tAgainSign in_a " +
					"		 where in_a.CustCode = ? and convert(nvarchar(10),in_a.NewSignDate,120) between a.BeginDate and dateadd(d,1,a.EndDate)" +
					"		) " +
					"		or cast(getdate() as date) between a.BeginDate and a.EndDate " +
					"	   )";
				list.add(itemPlan.getCustCode());
			}else {
				sql+=" and cast(getdate() as date) between BeginDate and EndDate ";
			}
			
			if(StringUtils.isNotBlank(itemPlan.getItemType1())){
				sql+=" and a.QuoteModule=? ";
				list.add(itemPlan.getItemType1().trim());
			}
			if(itemPlan.getIsService()!=null){
				sql+=" and a.IsService=? ";
				list.add(itemPlan.getIsService());
			}
			if(StringUtils.isNotBlank(itemPlan.getCustType())){
				sql+=" and (exists(select 1 from tGiftCustType gct where gct.GiftPK=a.PK and gct.CustType=?) " 
					+" or not exists(select 1 from tGiftCustType gct where gct.GiftPK=a.PK ) ) ";
				list.add(itemPlan.getCustType());
			}
			if(StringUtils.isNotBlank(itemPlan.getIsCupboard())){
				sql+=" and a.IsCupboard=? ";
				list.add(itemPlan.getIsCupboard());
			}
			if (StringUtils.isNotBlank(page.getPageOrderBy())){
				sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
			}else{
				sql += " ) a order by a.DispSeq ";
			}
			return this.findPageBySql(page, sql, list.toArray());
	}
	public Page<Map<String, Object>> findPageBySql_giftItem(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		List<Object> list = new ArrayList<Object>();
		String sql="select * from ( select a.pk,a.GiftPK, " ;
		if("JZ".equals(itemPlan.getItemType1())){
			sql+=" a.BaseItemCode,c.Descr BaseItemDescr, g.descr giftdescr, "
				+" c.Cost,c.MarketPrice, c.material, c.OfferPri unitprice,c.Remark,g.IsOutSet,x1.NOTE IsOutSetDescr, "
				+" c.Uom,d.Descr UOMDescr,c.Category,x2.note CategoryDescr,c.BaseItemType1,c.BaseItemType2,c.iscalmangefee,c.allowpricerise,c.IsFixPrice "
				+" from tGiftItem a "
				+" inner join tBaseItem c on c.Code=a.BaseItemCode "
				+" left join txtdm x2 on x2.cbm=c.Category and x2.id='BASEITEMCAT' "
			    +" left join tUOM d on d.code=c.UOM "	
			    +" left join tGift g on g.pk=a.GiftPK "
				+" left join tXTDM x1 on x1.id='YESNO' and g.IsOutSet=x1.cbm ";
				
		}else{
			sql+=" a.ItemCode,c.Descr ItemDescr,g.descr giftdescr," 
				+" isfixprice,c.cost,g.IsOutSet,x1.NOTE IsOutSetDescr, "
				+" c.Uom,d.Descr UOMDescr,c.ItemType2,c.ItemType3,it2.Descr ItemType2Descr,it3.Descr ItemType3Descr,c.ProjectCost oldProjectCost, ";
			if("0".equals(itemPlan.getIsOutSet())){
				sql+=" cti.remark,cti.price,cti.PK custtypeitempk,cti.ProjectCost ";
			}else{
				sql+=" c.remark,c.price,c.ProjectCost ";
			}	
			sql+=" from tGiftItem a "
			    +" inner join titem c on c.code=a.ItemCode "
				+" left join tItemType2 it2 on it2.Code=c.ItemType2 "
				+" left join tItemType3 it3 on it3.Code=c.ItemType3 "
				+" left join tUOM d on d.code=c.UOM "	
				+" left join tGift g on g.pk=a.GiftPK "
				+" left join tXTDM x1 on x1.id='YESNO' and g.IsOutSet=x1.cbm ";
			if("0".equals(itemPlan.getIsOutSet())){
				sql+=" left join tCusttypeitem cti on cti.ItemCode=a.ItemCode   ";	
			}
					
		}	
		sql=sql+" where 1=1 and c.Expired='F' ";	
		if(itemPlan.getGiftPK()!=null){
			sql+=" and a.GiftPK=? ";
			list.add(itemPlan.getGiftPK());
		}
		if("0".equals(itemPlan.getIsOutSet())){
			sql+=" and cti.CustType=? and cti.Expired='F' ";
			list.add(itemPlan.getCustType());	
		}
		
		// 判断材料是否对应客户类型
		if(StringUtils.isNotBlank(itemPlan.getCustType()) && !"JZ".equals(itemPlan.getItemType1())){
			sql+=" and ( (exists(select 1 from tCusttype in_ct where in_ct.Code=? and in_ct.CanUseComItem<>'0') and (c.CustType=? or isnull(c.CustType,'')='' ) )  "
		        + "   or (exists(select 1 from tCusttype in_ct where in_ct.Code=? and in_ct.CanUseComItem='0' ) and c.CustType=?)  ) ";
			list.add(itemPlan.getCustType());
			list.add(itemPlan.getCustType());
			list.add(itemPlan.getCustType());
			list.add(itemPlan.getCustType());
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.pk ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public boolean hasGiftItem(String custCode) {
		String sql = "select 1 from tCustGift where Custcode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public Page<Map<String, Object>> getCustGiftJqGrid(
			Page<Map<String, Object>> page, CustGift custGift) {
		List<Object> list = new ArrayList<Object>();
		String sql="select * from ( select e.remarks giftRemarks,b.BaseFee_Dirct baseFeeDirct,b.custtype,b.area,f.maxDiscAmount,a.pk,a.CustCode, a.GiftPK, a.Type, a.QuoteModule, a.MaxDiscAmtExpr,a.DiscAmount, " +
				" a.SaleAmount, a.TotalCost, a.DiscAmtType, a.PerfDiscType, a.PerfDiscPer, round(a.PerfDiscAmount,0) perfDiscAmount," +
				" a.CalcDiscCtrlPer, a.IsSoftToken, a.DispSeq, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog, " +
				" b.descr CustDescr,c.descr QuoteModuleDescr ,d.descr DiscAmtTypeDescr,x.note TypeDescr,x2.Note perfDiscTypeDescr " +
				" ,x4.note isSoftTokenDescr," +
				" (select sum((1-Markup/100)*BefLineAmount+case when ProcessCost<0 then -ProcessCost else 0 end )  " +
				"    from titemplan where GiftPK = a.GiftPK and custCode = a.custCode ) bjyhje, " +
				" e.discType,e.descr,x5.note discTypeDescr,a.DiscConfirmRemarks, " +
				" a.DiscAmount*e.CalcDiscCtrlPer CoinAmount, isnull(g.WallArea, 0.00) WallArea "+
				" from tCustGift a " +
				" left join tCustomer b on b.code = a.CustCOde" +
				" left join tItemType1 c on c.code = a.QuoteModule " +
				" left join tItemType1 d on d.code = a.DiscAmtType " +
				" left join tXtdm x on x.id='GIFTTYPE' and x.cbm = a.type " +
				" left join tXtdm x2 on x2.cbm = a.PerfDiscType and x2.id='GIFTPERFDSTYPE' " +
				" left join tXtdm x4 on x4.cbm = a.IsSoftToken and x4.id='YESNO' " +
				" left  join tGift e on e.pk = a.GiftPk " +
				" left join tXtdm x5 on x5.cbm = e.discType and x5.id='GIFTDISCTYPE' " +
				" left join tSegDiscRule f on f.custType = b.custType and f.discAmtType = a.DiscAmtType and b.Area between f.MinArea and f.MaxArea " +
				" left join ( " +
                "     select in_a.CustCode, sum(in_a.Qty) WallArea " +
                "     from tBaseItemPlan in_a " +
                "     left join tBaseItem in_b on in_a.BaseItemCode = in_b.Code " +
                "     where in_b.PrjType = '1' " +
                "     group by in_a.CustCode " +
                " ) g on a.CustCode = g.CustCode" +
				" where 1=1 ";
		if(StringUtils.isNotBlank(custGift.getCustCode())){
			sql+=" and a.custCode = ? ";
			list.add(custGift.getCustCode());
		}
		if(StringUtils.isNotBlank(custGift.getDiscAmtType())){
			sql+= " and a.DiscAmtType = ? " ;
			list.add(custGift.getDiscAmtType());
		} 
		if(StringUtils.isNotBlank(custGift.getHasGiftAppDetail()) && "1".equals(custGift.getHasGiftAppDetail())){
			if(StringUtils.isBlank(custGift.getDiscAmtType())||"LP".equalsIgnoreCase(custGift.getDiscAmtType())){
				sql+= " union all select c.descr giftremarks,0 baseFeeDirct,'' custtype, 0 area,0 maxDiscAmount, 0 pk, b.CustCode,0 GiftPK,'' Type, '' QuoteModule, '' MaxDiscAmtExpr ,isnull(sum(case when b.outType='2' then 1*a.qty else a.qty end * a.Cost),0)DiscAmount,"+
					     " 0 SaleAmount,0 TotalCost,'' DiscAmtType,'' PerfDiscType,0 PerfDiscPer,0 perfdiscamount, " +
					     " 1.0 calcdiscctrlper, '0' IsSoftToken, 1000000 DispSeq, '' LastUpdate, '' LastUpdatedBy, '' Expired, '' ActionLog, " +
						 " '' CustDescr,'' QuoteModuleDescr ,'礼品' DiscAmtTypeDescr,'实物赠送' TypeDescr,'' perfDiscTypeDescr, " +
						 " '否' isSoftTokenDescr," +
						 " 0 bjyhje,'' discType,c.descr,'单品' discTypeDescr,'' DiscConfirmRemarks,isnull(sum(case when b.outType='2' then 1*a.qty else a.qty end * a.Cost),0) CoinAmount, " +	
						 " 0.00 WallArea " +
						 " from tGiftAppDetail a " +
						 " left join tGiftApp b on a.No=b.no " +
						 " left join tItem c on c.Code=a.ItemCode " +
						 " where CustCode=? and b.status in ('send','return') and type='2' " +
						 " group by c.descr,b.CustCode " +
						 " having isnull(sum(case when b.outtype = '2' then 1 * a.qty else a.qty end * a.cost), 0)<>0 ";
				list.add(custGift.getCustCode());
			} 
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.DispSeq";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getCustGiftAllJqGrid(
			Page<Map<String, Object>> page, CustGift custGift) {
		Assert.notNull(custGift);
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pZsxmhz(?)}");
			call.setString(1, custGift.getCustCode());
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			page.setResult(list); 			
			page.setTotalCount(list.size());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return page;
	}
	
	public Page<Map<String, Object>> getItemGiftJqgrid(
			Page<Map<String, Object>> page, ItemPlan itemPlan) {
		List<Object> list = new ArrayList<Object>();
		String sql=" select a.CustCode, a.GiftPK, a.Type, a.QuoteModule, a.MaxDiscAmtExpr,a.DiscAmount, " +
				" a.SaleAmount, a.TotalCost, a.DiscAmtType, a.PerfDiscType, a.PerfDiscPer, a.PerfDiscAmount," +
				" a.IsCalcDiscCtrl, a.IsSoftToken, a.DispSeq, a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog " +
				" from tCustGift a " +
				" where 1=1 ";
		if(StringUtils.isNotBlank(itemPlan.getCustCode())){
			sql+=" and a.custCode = ? ";
			list.add(itemPlan.getCustCode());
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getCustGiftItemJqgrid(
			Page<Map<String, Object>> page, CustGiftItem custGiftItem) {
		List<Object> list = new ArrayList<Object>();
		String sql=" select a.pk,a.CustGiftPK, a.FixAreaPK,a.ItemCode,  a.Qty, a.Markup, " +
				"a.SaleAmount beflineamount, a.TotalCost , a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog," +
				" b.descr fixAreaDescr,c.descr itemDescr, case when a.qty = 0 then 0 else a.TotalCost/a.Qty end cost," +
				" a.baseItemCode,d.descr baseItemDescr,a.ProjectAmount " +
				" from tCustGiftItem a " +
				" left join tFixArea b on b.pk = a.fixAreaPK " +
				" left join tItem c on c.code = a.itemCode " +
				" left join tBaseitem d on d.code = a.BaseItemCode " +
				" where 1=1 ";
		if(custGiftItem.getCustGiftPK() != null){
			sql+=" and a.custGiftpk = ? ";
			list.add(custGiftItem.getCustGiftPK());
		}
		if(StringUtils.isNotBlank(custGiftItem.getCustCode())){
			sql+=" and exists ( select 1 from tCustGift in_a where in_a.custCode = ? and in_a.pk = a.custGiftPk )";
			list.add(custGiftItem.getCustCode());
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public double getMaxDiscExpr(Integer giftPk,double area,String custType) {
		String sql = " select isnull(MaxDiscAmount,0)MaxDiscAmount from tGift a " +
				" left join tSegDiscRule b on a.discAmtType = b.discAmtType and ? >=b.minArea and ? <= b.maxArea and b.CustType = ? " +
				" where a.pk = ? ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {area,area,custType,giftPk});
		if (list != null && list.size() > 0) {
			if(StringUtils.isNotBlank(list.get(0).get("MaxDiscAmount").toString())){
				return Double.parseDouble(list.get(0).get("MaxDiscAmount").toString());
			}else{
				return 0.0;
			}
		} else {
			return 0.0;
		}
	}
	public Result getBaseItemTempDiff(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDiffPlan_JZ(?,?,?,?,?)}");
			call.setString(1, itemPlan.getCustCode());
			call.setString(2, itemPlan.getTempNo());
			call.setString(3, itemPlan.getLastUpdatedBy());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				for (String k : map.keySet()) {
					if (map.get(k)==null){
						map.put(k, "");
					}
	            }
				JSONObject json = JSONObject.fromObject(map); 
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	public Result getItemTempDiff_ZC(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pDiffPlan_ZC(?,?,?,?,?)}");
			call.setString(1, itemPlan.getCustCode());
			call.setString(2, itemPlan.getTempNo());
			call.setString(3, itemPlan.getLastUpdatedBy());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				for (String k : map.keySet()) {
					if (map.get(k)==null){
						map.put(k, "");
					}
	            }
				
				JSONObject json = JSONObject.fromObject(map); 
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	public Result doBaseItemSetAddProc(ItemPlan itemPlan) {
		Assert.notNull(itemPlan);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pBaseItemSetAdd(?,?,?,?,?,?)}");
			call.setString(1, itemPlan.getCustCode());
			call.setString(2, itemPlan.getBaseItemCode());
			call.setInt(3, itemPlan.getFixAreaPk());
			call.setString(4, itemPlan.getLastUpdatedBy());
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.NVARCHAR);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			List<Map<String, Object>> list=BeanConvertUtil.resultSetToList(rs);
			List<JSONObject> jsonLists=new ArrayList<JSONObject>();
			for(Map<String, Object> map:list){
				for (String k : map.keySet()) {
					if (map.get(k)==null){
						map.put(k, "");
					}
	            }
				String date= map.get("lastupdate").toString();
				JSONObject json = JSONObject.fromObject(map); 
				json.put("lastupdate", date);
				jsonLists.add(json);
			}
			result.setCode(String.valueOf(call.getInt(5)));
			result.setInfo(call.getString(6));
			result.setJson(jsonLists.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Page<Map<String,Object>> goSoftSendJqGrid(Page<Map<String,Object>> page, String custCode){
		String sql = " select  a.pk ReqPK, a.CustCode, a.FixAreaPK, a.IntProdPK, a.ItemCode, "
					+" a.Qty ReqQty, a.Remark Remarks, a.Cost, a.SendQty, a.ProjectCost, "
					+" a.Qty - a.SendQty TheSendQty,b.Descr FixAreaDescr, c.Descr IntProdDescr, "
					+" d.Descr ItemDescr, sp.Code, sp.Descr SupplierDescr, "
					+" d.sqlcode, 0 PreAppDtPK, 0 ShortQty, 0 aftqty, 0 aftcost,0 declareqty,d.ItemType2,it2.Descr itemtype2descr,a.Qty-a.SendQty Qty " 
					+" from tItemReq a "
					+" left join tFixArea b on a.FixAreaPK = b.PK "
					+" left join tIntProd c on a.IntProdPK = c.PK "
					+" left join tItem d on a.ItemCode = d.Code "
					+" left join tSupplier sp on d.SupplCode = sp.Code "
					+" left join tItemType2 it2 on it2.code= d.ItemType2 "       
					+" where a.CustCode=? and a.itemType1='RZ'"
					+" and a.qty <> SendQty  "
					+" order by it2.Descr ";
		return this.findPageBySql(page, sql, new Object[]{custCode});
	}
	
	public boolean isSendComplete(String custCode) {
		String sql = "select 1 from tItemReq where CustCode=? and Qty<>SendQty ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return false ;
		}
		return true;
	}

	public Page<Map<String, Object>> findProcListJqGrid(
            Page<Map<String, Object>> page, Customer customer) {
        
        List<Object> parameters = new ArrayList<Object>();

        String sql = "select * from (" +
        		" select WfProcInstNo ,EmpCode ,	EmpName ,CustCode ,	CustDescr ,Address ,ApproveLine ,ContractFee ," +
        		"	DiscountRemarks ,ManagerItems ,PresidentItems ,Remarks, StartTime, ActProcDefId " +
        		" from tWfCust_CustGiftApp a " +
        		" left join tWfProcInst b on b.No = a.WfProcInstNo "
                + "where 1 = 1 ";

        if (StringUtils.isNotBlank(customer.getCode())) {
            sql += " and a.CustCode = ? ";
            parameters.add(customer.getCode());
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.WfProcInstNo desc";
        }
        
        return this.findPageBySql(page, sql, parameters.toArray());
    }
	
	public boolean checkProcStatus(ItemPlan itemPlan) {
		String sql = "select 1 from tWfProcInst a " +
				" inner join tWfCust_CustGiftApp b on b.WfProcInstNo = a.no " +
				" where a.status ='1' and a.WfProcNo = '028' and b.CustCode = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemPlan.getCustCode()});
		if (list!=null && list.size()>0){
			return false ;
		}
		return true;
	}
	
	public List<Map<String, Object>> getCustAgreement(Customer customer) {
		String sql = "select case when a.Type='1' then gf.Remarks+'，优惠金额：'+cast(a.DiscAmount as nvarchar(30))+'元'" +
				"	when a.Type='2' then gf.Remarks" +
				"	when a.Type='3' and gf.IsLimitItem='1' then  gf.Remarks" +
				" else  gf.Remarks+'，项目名称：'+isnull(bi.Descr,i.Descr)+'，数量：'+cast(gft.Qty as nvarchar(20))+isnull(u1.Descr,u2.Descr)" +
				" end detail" +
				" from tCustGift a" +
				" left join tCustomer c on c.Code=a.CustCode" +
				" left join tGift gf on gf.pk=a.GiftPK" +
				" left join tCustGiftItem gft on gft.CustGiftPK=a.PK and gf.IsLimitItem='0'" +
				" left join tBaseItem bi on bi.Code=gft.BaseItemCode" +
				" left join tItem i on i.Code=gft.ItemCode" +
				" left join tuom u1 on u1.Code=bi.Uom" +
				" left join tuom u2 on u2.Code=i.Uom" +
				" where (a.Type='3' and ((gf.IsLimitItem='0' and gft.qty<>0)" +
				"          or (gf.IsLimitItem='1' and (select isnull(sum(qty), 0) " +
				"	from tCustGiftItem w_gft where w_gft.CustGiftPK=a.PK) <> 0))" +
				"	  or a.Type<>'3') and a.CustCode = ? " +
				" order by a.DispSeq, gft.pk";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{customer.getCode()});
		if (list!=null && list.size()>0){
			return list ;
		}
		return null;
	}
	
	public boolean hasItemPlanQtyUnequal(String custCode) {
		String sql =" select 1 "+
					" from tItemPlan a "+
					" left join tCustomer b on a.CustCode=b.Code "+
					" left join tFixArea c on c.Pk=a.FixAreaPK "+
					" where a.CustCode=? and a.ItemType1='ZC' and a.CanModiQty='0' and a.isoutset='0' "+
					" and a.qty<>isnull(dbo.fGetItemPlanAutoQty(a.custcode,a.Algorithm,a.ItemCode,c.preplanareapk,isnull(a.DoorPK,0),isnull(a.tempdtpk,0),a.AlgorithmPer,a.AlgorithmDeduct),0) ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}	
	
	public boolean hasBasePlanQtyUnequal(String custCode) {
		String sql =" select 1 "+
				" from tBaseItemPlan a "+
				" left join tCustomer b on a.CustCode=b.Code "+
				" left join tFixArea c on c.Pk=a.FixAreaPK "+
				" where a.CustCode=? and a.CanModiQty='0' and a.isoutset='0' "+
				" and a.qty<> isnull(dbo.fGetBaseItemPlanAutoQty(a.custcode,a.BaseAlgorithm,a.BaseItemCode,c.preplanareapk,isnull(a.tempdtpk,0)),0)  ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	public List<Map<String, Object>> checkCommiConstruct(ItemPlan itemPlan) {
		String sql = " select 1 from tItemPlan a" +
				" left join tItem b on b.Code = a.ItemCode " +
				" left join tItemType1 c on c.Code = b.ItemType1 " +
				" where c.Code = ? and a.CustCode = ? " +
				" and (b.IsClearInv = '1' or " +
				"	((a.Markup <> 100 or a.ProcessCost < 0 ) " +
				"	 and (a.ItemSetNo is null or a.ItemSetNo = ''))" +
				")";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemPlan.getItemType1(), itemPlan.getCustCode()});
		if (list!=null && list.size()>0){
			return list ;
		}
		return null;
	}
	
	public Map<String, Object> getBefAmountByCustCodeItemType(ItemPlan itemPlan) {
		String sql = "select BefLineAmount, DiscAmount ItemPlanDiscAmount,isnull(b.SoftDisc, 0) DiscAmount " +
				" from ( select isnull(sum(isnull(a.BefLineAmount, 0)), 0)BefLineAmount, " +
				" isnull(sum(isnull(a.BefLineAmount, 0)- isnull(a.LineAmount, 0)),0) DiscAmount,CustCode" +
				" from tItemPlan a " +
				" left join tItem b on b.Code = a.ItemCode " +
				" left join tItemType1 c on c.Code = b.ItemType1 " +
				" where 1=1 and a.custCode = ? " +
				" and (a.itemType1  = ?  or c.Descr = ? )and a.expired = 'F'" +
				" Group by CustCode" +
				") a " +
				" left join tCustomer b on b.Code = a.CustCode " +
				" ";
		
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemPlan.getCustCode(), itemPlan.getItemType1(), itemPlan.getItemType1()});
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String, Object>> getClearInvList(ItemPlan itemPlan, String isClearInv) {
		String sql = " select 1 from tItemPlan a " +
				" left join tItem b on b.Code = a.ItemCode " +
				" where 1=1 and a.custCode = ? and a.itemType1  = ? and b.IsClearInv = ? " +
				" and (a.ItemSetNo is null or a.ItemSetNo = '' or isClearInv = ? )";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemPlan.getCustCode(), itemPlan.getItemType1(), isClearInv, isClearInv});
		if (list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	public List<Map<String, Object>> getSaleDiscApproveDetail(ItemPlan itemPlan) {
		String sql = " select a.ItemCode, b.Descr ItemDescr,a.FixAreaPk,c.Descr FixAreaDescr,a.Qty, a.BefLineAmount, " +
				" a.Markup,case when b.IsClearInv = '1' then '是' else '否' end IsClearInv,a.ProcessCost " +
				" from tItemPlan a " +
				" left join tItem b on b.Code = a.ItemCode " +
				" left join tFixArea c on c.pk = a.FixAreaPk " +
				" left join tItemType1 d on d.Code = b.ItemType1" +
				" where 1=1 and (a.custCode = ? and d.Descr  = ? )" +
				" and(b.IsClearInv = '1' or " +
				"	((a.Markup <> 100 or a.ProcessCost < 0 ) " +
				"	 and (a.ItemSetNo is null or a.ItemSetNo = ''))" +
				")";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemPlan.getCustCode(), itemPlan.getItemType1()});
		if (list!=null && list.size()>0){
			return list;
		}
		return list;
	}	
	
	public List<Map<String, Object>> getWfProcDetail(ItemPlan itemPlan) {
		String sql = "select * from tWfCust_SaleDiscApproveDtl a " +
				" left join tWfCust_SaleDiscApprove b on b.WfProcINstNo = a.WfProcInstNo" +
				" where b.CustCode = ? and a.WfProcInstNo = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemPlan.getCustCode(), itemPlan.getWfProcInstNo()});
		if (list!=null && list.size()>0){
			return list;
		}
		return list;
	}	
	
	public String getSaleDiscApproveStatus(Customer customer) {
		String sql = "select b.Status from (" +
				"	 select max(a.WfProcInstNo) WfProcInstNo from tWfCust_SaleDiscApprove a" +
				"	 where a.CustCode = ? " +
				" ) a " +
				" left join tWfProcInst b on b.No = a.WfProcInstNo " +
				" where 1=1 and b.status is not null";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{customer.getCode()});
		if (list!=null && list.size()>0){
			return list.get(0).get("Status").toString();
		}
		return "";
	}
}

