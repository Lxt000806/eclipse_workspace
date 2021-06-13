package com.house.home.dao.query;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.Customer;

import java.util.List;
@Repository
@SuppressWarnings("serial")
public class ItemSzQueryDao extends BaseDao {

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer, UserContext uc) {
		List<Object> list=new ArrayList<Object>();
		String sql= "select distinct  a.Code,a.Source,h.NOTE as EndCodeDescr,a.EndRemark,b.NOTE as SourceDescr,a.Descr,a.Gender,c.NOTE as GenderDescr,a.Address,a.Layout,f.NOTE as LayoutDescr,a.Area,a.Mobile1,a.Mobile2, "
                +"a.QQ,a.Email2,a.Remarks,a.CrtDate,a.Status,d.NOTE as StatusDescr,a.DesignMan,e1.NameChi as DesignManDescr,a.BusinessMan,e2.NameChi as BusinessManDescr, "
                +"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.DesignStyle,e.NOTE as DesignStyleDescr,a.EndDate,a.CustCheckDate,a.SetDate,a.SignDate, "
               +" a.ContractFee,a.DesignFee,a.MeasureFee,a.DrawFee,a.ColorDrawFee, "
	           +" a.ManageFee*ContainBase as ManageFee, "
               +" a.BaseFee*ContainBase as BaseFee,a.BaseDisc*ContainBase as BaseDisc, "
               +" a.BaseFee_Dirct*ContainBase as BaseFee_Dirct,a.BaseFee_Comp*ContainBase as BaseFee_Comp, "
               +" a.MainFee*a.ContainMain as MainFee,a.MainDisc*a.ContainMain as MainDisc, "
               +" a.SoftFee*a.ContainSoft as SoftFee,a.SoftDisc*a.ContainSoft as SoftDisc,a.SoftOther*a.ContainSoft as SoftOther, "
               +" a.IntegrateFee*a.ContainInt as IntegrateFee,a.IntegrateDisc*a.ContainInt as IntegrateDisc,a.MainServFee*a.ContainMainServ as MainServFee, "
               +" a.CupboardFee*a.ContainCup as CupboardFee,a.CupBoardDisc*a.ContainCup as CupBoardDisc,a.Gift,a.DocumentNo, "
               +" dpt1.Desc1 as DesiDpt1,dpt2.Desc1 as DesiDpt2,dpt3.Desc1  as DesiDpt3, "
               +" convert(nvarchar(1000),dbo.fGetEmpNameChi(a.Code,'50')) as SoftDesignDescr,g.Desc1 as CustTypeDescr "
               +" from tCustomer a "
               +" left outer join tEmployee e1 on a.DesignMan=e1.Number "
               +" left outer join tEmployee e2 on a.BusinessMan=e2.Number "
               +" left outer join tDepartment1 dpt1 on dpt1.Code=e1.department1 "
               +" left outer join tDepartment2 dpt2 on dpt2.Code=e1.department2 "
               +" left outer join tDepartment3 dpt3 on dpt3.Code=e1.Department3 "
               +" left outer join tCustType g on g.Code = a.CustType "
               +" left outer join tXTDM b on a.Source=b.CBM and b.ID='CUSTOMERSOURCE' "
               +" left outer join tXTDM c on a.Gender=c.CBM and c.ID='GENDER' "
               +" left outer join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
               +" left outer join tXTDM e on a.DesignStyle=e.CBM and e.ID='DESIGNSTYLE' "
               +" left outer join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
               +" left outer join tXTDM h on a.EndCode=h.CBM and h.ID='CUSTOMERENDCODE'  " ;
				sql+=" where  "+SqlUtil.getCustRight(uc, "a", 1);
			  if(StringUtils.isNotBlank(customer.getDepartment1())||StringUtils.isNotBlank(customer.getRole())||StringUtils.isNotBlank(customer.getEmpCode())){
				  sql+=" and exists(select 1 from tCustStakeholder m inner join tEmployee te1 on m.EmpCode=te1.Number "
				        + "where m.CustCode=a.Code ";
				  if (StringUtils.isNotBlank(customer.getDepartment1())){
						sql+=" and te1.Department1= ? ";
						list.add(customer.getDepartment1());
					}
				  if (StringUtils.isNotBlank(customer.getDepartment2())){
						sql+=" and te1.Department2= ? ";
						list.add(customer.getDepartment2());
					}
				  if (StringUtils.isNotBlank(customer.getDepartment3())){
						sql+=" and te1.Department3= ? ";
						list.add(customer.getDepartment3());
					}
				  if (StringUtils.isNotBlank(customer.getRole())){
						sql+=" and m.Role= ? ";
						list.add(customer.getRole());
					}
				  if (StringUtils.isNotBlank(customer.getEmpCode())){
						sql+=" and m.EmpCode= ? ";
						list.add(customer.getEmpCode());
					}
				  sql+=" ) ";
			  }
			     if (StringUtils.isBlank(customer.getExpired())
						|| "F".equals(customer.getExpired())) {
					sql += " and a.Expired='F' ";
				}
				if (StringUtils.isNotBlank(customer.getStatus())) {
					sql += " and a.status in  ('"+customer.getStatus().trim().replace(",", "','")+"')";
				}
				if (StringUtils.isNotBlank(customer.getAddress())) {
					sql += " and a.Address like ? ";
					list.add("%" + customer.getAddress() + "%");
				}
				if (StringUtils.isNotBlank(customer.getDescr())) {
					sql += " and a.Descr like ? ";
					list.add("%" + customer.getDescr() + "%");
				}
				if (StringUtils.isNotBlank(customer.getSource())) {
					sql += " and RTrim(a.Source)= ? ";
					list.add(customer.getSource().trim());
				}
				if (customer.getSignDateFrom() != null) {
					sql += " and a.signDate>=CONVERT(VARCHAR(10),?,120) ";
					list.add(customer.getSignDateFrom());
				}
				if (customer.getSignDateTo() != null) {
					sql += " and a.signDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
					list.add(customer.getSignDateTo());
				}
				if (customer.getSetDateFrom() != null) {
					sql += " and a.SetDate>=CONVERT(VARCHAR(10),?,120) ";
					list.add(customer.getSetDateFrom());
				}
				if (customer.getSetDateTo() != null) {
					sql += " and a.SetDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
					list.add(customer.getSetDateTo());
				}
				if (customer.getCrtDateFrom() != null) {
					sql += " and a.CrtDate>=CONVERT(VARCHAR(10),?,120) ";
					list.add(customer.getCrtDateFrom());
				}
				if (customer.getCrtDateTo() != null) {
					sql += " and a.CrtDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
					list.add(customer.getCrtDateTo());
				}
				if (customer.getEndDateFrom() != null) {
					sql += " and a.EndDate>=CONVERT(VARCHAR(10),?,120) ";
					list.add(customer.getEndDateFrom());
				}
				if (customer.getEndDateTo() != null) {
					sql += " and a.EndDate<CONVERT(VARCHAR(10),dateadd(dd,1,?),120) ";
					list.add(customer.getEndDateTo());
				}
				if (StringUtils.isNotBlank(customer.getCode())) {
					sql += " and a.Code=? ";
					list.add(customer.getCode());
				}
				if (StringUtils.isNotBlank(customer.getCheckStatus())) {
					sql += " and a.CheckStatus in  ("+customer.getCheckStatus()+")";
				}
				if (StringUtils.isNotBlank(customer.getEndCode())) {
					String str = SqlUtil.resetStatus(customer.getEndCode());
					sql += " and a.endCode in (" + str + ")";
				}
				if (StringUtils.isNotBlank(page.getPageOrderBy())) {
					sql += " order by a." + page.getPageOrderBy() + " "
							+ page.getPageOrder();
				} else {
					sql += " order by a.Code";
				}
				return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String,Object> getXmjljsxx(String code){
		String sql = " SELECT c.code,c.descr,c.address,x1.NOTE custStatDescr,ct.type, "
				   + " x2.Note checkStatusName,pc.baseCtrlAmt,pc.cost,pc.withHold,pc.mainCoopFee, "
				   + " pc.recvFee,pc.qualityFee,pc.accidentFee,pc.mustAmount,pc.realAmount,x3.Note isProvide,pc.provideNo,pc.remarks prjRemarks, "
				   + " c.projectCtrlAdj,c.ctrlAdjRemark,ct.prjCtrlType,pc.RecvFee_FixDuty recvFeeFixDuty "
				   + " FROM dbo.tCustomer c "
				   + " LEFT JOIN dbo.tXTDM x1 ON x1.ID='CUSTOMERSTATUS' AND x1.cbm = c.Status "
				   + " LEFT JOIN dbo.tCusttype ct ON ct.Code = c.CustType "
				   + " LEFT JOIN dbo.tPrjCheck pc ON c.Code = pc.CustCode "
				   + " LEFT JOIN dbo.tXTDM x2 ON c.CheckStatus = x2.CBM AND x2.Id='CheckStatus' "
				   + " LEFT JOIN dbo.tXTDM x3 ON pc.IsProvide = x3.CBM AND x3.Id='YESNO' "
				   + " WHERE c.Code=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public List<Map<String,Object>> goJcszxxJqGrid(String custCode){
		Connection conn = null;
		CallableStatement call = null;
		List<Map<String, Object>> list = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pJcszcx(?)}");
			call.setString(1, custCode);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			list = BeanConvertUtil.resultSetToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return list;
	}
	
	public List<Map<String,Object>> goYshfbdemxJqGrid_material(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		String sql = " select * from ( select b.code,b.descr,a.qty,a.Material cost, "
				   + " round(a.Material*a.Qty,0) preamount, "
				   // update by zzr 2017/12/21 修改发包公式 begin
				   //+ " round(round(a.Material*a.Qty,0)*isnull(e.BaseCtrlPer,d.BaseCtrlPer),2) prefbamount, "
				   + " CASE WHEN e.BaseCtrlPer IS NOT NULL THEN round(round(a.Material*a.Qty,0)*e.BaseCtrlPer,2) "
				   + " ELSE ROUND(( CASE WHEN a.PrjCtrlType='1' THEN a.Material*a.MaterialCtrl ELSE a.MaterialCtrl END )*a.Qty,2) END prefbamount, " 
				   // update by zzr 2017/12/21 修改发包公式 end
				   + " c.MaterWorkType2 worktype2,f.descr worktype2name,t.Descr fixareaname "
				   + " ,? worktype1name,a.DispSeq "
				   + " from tBaseItemPlan a "
				   + " inner join tBaseItem b on a.BaseItemCode=b.Code "
				   + " inner join tBaseItemType2 c on b.BaseItemType2=c.Code "
				   + " inner join tCustomer d on a.CustCode=d.Code "
				   + " left join tCustBaseCtrl e on a.CustCode=e.CustCode and a.BaseItemCode=e.BaseItemCode "
				   + " inner join tWorkType2 f on c.MaterWorkType2=f.Code "
				   + " inner join tFixArea t on a.FixAreaPK=t.PK "
				   + " where a.CustCode=? and c.MaterWorkType2=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.DispSeq ";
		}
		return this.findBySql(sql, new Object[]{workType1Name,custCode,workType2});
	}
	
	public List<Map<String,Object>> goYshfbdemxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		String sql = " select * from ( select b.code,b.descr,a.qty,a.UnitPrice cost, "
				   + " round(a.UnitPrice*a.Qty,0) preamount, "
				   // update by zzr 2017/12/21 修改发包公式 begin
				   //+ " round(round(a.UnitPrice*a.Qty,0)*isnull(e.BaseCtrlPer,d.BaseCtrlPer),2) prefbamount, "
				   + " CASE WHEN e.BaseCtrlPer IS NOT NULL THEN round(round(a.UnitPrice*a.Qty,0)*e.BaseCtrlPer,2) "
				   + " ELSE ROUND( ( CASE WHEN a.PrjCtrlType='1' THEN a.UnitPrice*a.OfferCtrl ELSE a.OfferCtrl END )*a.Qty ,2) END prefbamount, " 
				   // update by zzr 2017/12/21 修改发包公式 end
				   + " c.OfferWorkType2 worktype2,f.descr worktype2name,t.Descr fixareaname "
				   + " ,? worktype1name,a.DispSeq "
				   + " from tBaseItemPlan a "
				   + " inner join tBaseItem b on a.BaseItemCode=b.Code "
				   + " inner join tBaseItemType2 c on b.BaseItemType2=c.Code "
				   + " inner join tCustomer d on a.CustCode=d.Code "
				   + " left join tCustBaseCtrl e on a.CustCode=e.CustCode and a.BaseItemCode=e.BaseItemCode "
				   + " inner join tWorkType2 f on c.OfferWorkType2=f.Code "
				   + " inner join tFixArea t on a.FixAreaPK=t.PK "
				   + " where a.CustCode=? and c.OfferWorkType2=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.DispSeq ";
		}
		return this.findBySql(sql, new Object[]{workType1Name,custCode,workType2});
	}
	
	public List<Map<String,Object>> goZjhfbdemxJqGrid_material(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		String sql = " select * from ( select a.no,a.status,h.note,a.date,c.code,c.descr,b.qty,b.Material cost,round(b.Material*b.Qty,0) chgamount, "
				   // update by zzr 2017/12/21 修改发包公式 begin
				   //+ " round(round(b.Material*b.Qty,0)*isnull(f.BaseCtrlPer,e.BaseCtrlPer),2) chgfbamount, "
				   + " CASE WHEN f.BaseCtrlPer IS NOT NULL THEN round(round(b.Material*b.Qty,0)*f.BaseCtrlPer,2) "
				   + " ELSE ROUND(( CASE WHEN b.PrjCtrlType='1' THEN b.Material*b.MaterialCtrl ELSE b.MaterialCtrl END )*b.Qty,2) END chgfbamount, " 
				   // update by zzr 2017/12/21 修改发包公式 end
				   + " d.MaterWorkType2 worktype2,g.descr worktype2name,t.Descr fixareaname "
				   + " ,? worktype1name "
				   + " from tBaseItemChg a "
				   + " inner join tBaseItemChgDetail b on a.No=b.No "
				   + " inner join tBaseItem c on b.BaseItemCode=c.Code "
				   + " inner join tBaseItemType2 d on c.BaseItemType2=d.Code "
				   + " inner join tCustomer e on a.CustCode=e.Code "
				   + " left join tCustBaseCtrl f on a.CustCode=f.CustCode and b.BaseItemCode=f.BaseItemCode "
				   + " inner join tWorkType2 g on d.MaterWorkType2=g.Code "
				   + " left join tXTDM h on a.Status=h.CBM and h.ID='ITEMCHGSTATUS' "
				   + " inner join tFixArea t on b.FixAreaPK=t.PK "
				   + " where a.CustCode=? and d.MaterWorkType2=? and a.Status = '2' ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{workType1Name,custCode,workType2});
	}
	
	public List<Map<String,Object>> goZjhfbdemxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		String sql = " select * from ( select a.no,a.status,h.note,a.date,c.code,c.descr,b.qty,b.UnitPrice cost,round(b.UnitPrice*b.Qty,0) chgamount, "
				   // update by zzr 2017/12/21 修改发包公式 begin
				   //+ "round(round(b.UnitPrice*b.Qty,0)*isnull(f.BaseCtrlPer,e.BaseCtrlPer),2) chgfbamount, "
				   + " CASE WHEN f.BaseCtrlPer IS NOT NULL THEN round(round(b.UnitPrice*b.Qty,0)*f.BaseCtrlPer,2) "
				   + " ELSE ROUND(( CASE WHEN b.PrjCtrlType='1' THEN b.UnitPrice*b.OfferCtrl ELSE b.OfferCtrl END )*b.Qty,2) END chgfbamount, " 
				   // update by zzr 2017/12/21 修改发包公式 end
				   + " d.OfferWorkType2 worktype2,g.Descr worktype2name,t.Descr fixareaname "
				   + " ,? worktype1name "
				   + " from tBaseItemChg a "
				   + " inner join tBaseItemChgDetail b on a.No=b.No "
				   + " inner join tBaseItem c on b.BaseItemCode=c.Code "
				   + " inner join tBaseItemType2 d on c.BaseItemType2=d.Code "
				   + " inner join tCustomer e on a.CustCode=e.Code "
				   + " left join tCustBaseCtrl f on a.CustCode=f.CustCode and b.BaseItemCode=f.BaseItemCode "
				   + " inner join tWorkType2 g on d.OfferWorkType2=g.Code "
				   + " left join tXTDM h on a.Status=h.CBM and h.ID='ITEMCHGSTATUS' "
				   + " inner join tFixArea t on b.FixAreaPK=t.PK "
				   + " where a.CustCode=? and d.OfferWorkType2=? and a.Status = '2' ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{workType1Name,custCode,workType2});
	}

	public List<Map<String,Object>> goZfbdemxJqGrid_material(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		String sql = " select * from ( select b.code,b.descr,a.qty,a.Material cost, "
				   // update by zzr 2017/12/21 修改发包公式 begin
				   //+ " round(round(a.Material*a.Qty,0)*isnull(e.BaseCtrlPer,d.BaseCtrlPer),2) allfbamount, "
				   + " CASE WHEN e.BaseCtrlPer IS NOT NULL THEN round(round(a.Material*a.Qty,0)*e.BaseCtrlPer,2) "
				   + " ELSE ROUND(( CASE WHEN a.PrjCtrlType='1' THEN a.Material*a.MaterialCtrl ELSE a.MaterialCtrl END )*a.Qty,2) END allfbamount, " 
				   // update by zzr 2017/12/21 修改发包公式 end
				   + " c.MaterWorkType2 worktype2,f.descr worktype2name,t.Descr fixareaname "
				   + " ,? worktype1name,a.DispSeq,x.note isoutsetdescr "
				   + " from tBaseItemReq a "
				   + " inner join tBaseItem b on a.BaseItemCode=b.Code "
				   + " inner join tBaseItemType2 c on b.BaseItemType2=c.Code "
				   + " inner join tCustomer d on a.CustCode=d.Code "
				   + " left join tCustBaseCtrl e on a.CustCode=e.CustCode and a.BaseItemCode=e.BaseItemCode "
				   + " inner join tWorkType2 f on c.MaterWorkType2=f.Code "
				   + " inner join tFixArea t on a.FixAreaPK=t.PK "
				   + " left join tXTDM x on x.ID='YESNO' and a.isOutSet=x.IBM "
				   + " where a.CustCode=? and c.MaterWorkType2=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.DispSeq ";
		}
		System.out.println(sql);
		System.out.println("workType1Name:"+workType1Name);
		System.out.println("custCode:"+custCode);
		System.out.println("workType2:"+workType2);
		return this.findBySql(sql, new Object[]{workType1Name,custCode,workType2});
	}
	
	public List<Map<String,Object>> goZfbdemxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		String sql = " select * from ( select b.code,b.descr,a.qty,a.UnitPrice cost, "
				   // update by zzr 2017/12/21 修改发包公式 begin
				   //+ " round(round(a.UnitPrice*a.Qty,0)*isnull(e.BaseCtrlPer,d.BaseCtrlPer),2) allfbamount, "
				   + " CASE WHEN e.BaseCtrlPer IS NOT NULL THEN round(round(a.UnitPrice*a.Qty,0)*e.BaseCtrlPer,2) "
				   + " ELSE ROUND(( CASE WHEN a.PrjCtrlType='1' THEN a.UnitPrice*a.OfferCtrl ELSE a.OfferCtrl END )*a.Qty,2) END allfbamount, " 
				   // update by zzr 2017/12/21 修改发包公式 end
				   + " c.OfferWorkType2 worktype2,f.descr worktype2name,t.Descr fixareaname "
				   + " ,? worktype1name,a.DispSeq,x.note isoutsetdescr "
				   + " from tBaseItemReq a "
				   + " inner join tBaseItem b on a.BaseItemCode=b.Code "
				   + " inner join tBaseItemType2 c on b.BaseItemType2=c.Code "
				   + " inner join tCustomer d on a.CustCode=d.Code "
				   + " left join tCustBaseCtrl e on a.CustCode=e.CustCode and a.BaseItemCode=e.BaseItemCode "
				   + " inner join tWorkType2 f on c.OfferWorkType2=f.Code "
				   + " inner join tFixArea t on a.FixAreaPK=t.PK "
				   + " left join tXTDM x on x.ID='YESNO' and a.isOutSet=x.IBM "
				   + " where a.CustCode=? and c.OfferWorkType2=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.DispSeq ";
		}
		return this.findBySql(sql, new Object[]{workType1Name,custCode,workType2});
	}
	
    public List<Map<String, Object>> goRgcbmxJqGrid(Page<Map<String, Object>> page,
            String costType, String workType1Name, String custCode, String workType2) {
		String sql = " select * from ( select a.no,a.date,a.documentno,a.payczy,a.paydate,b.pk,g.NameChi applyman,b.worktype2,d.descr worktype2name, "
				   + " b.confirmamount,b.salarytype,c.Descr salarytypename,b.status,e.Note statusname,b.appamount,b.remarks,b.confirmremark, "
				   + " b.actname,b.iswithhold,f.Note iswithholdname,b.withholdno "
				   + " ,? worktype1name, h.NOTE iscalprojectcostdescr "
				   + " from tWorkCost a "
				   + " inner join tWorkCostDetail b on a.No=b.No "
				   + " inner join tSalaryType c on b.SalaryType=c.Code "
				   + " inner join tWorkType2 d on b.WorkType2=d.Code "
				   + " left join tXTDM e on b.Status=e.CBM and e.ID='WorkCostStatus' "
				   + " left join tXTDM f on b.IsWithHold=f.CBM and f.ID='YESNO' "
				   + " left join tEmployee g on b.ApplyMan=g.Number "
				   + " left join tXTDM h on h.ID = 'YESNO' and h.CBM = b.IsCalProjectCost "
				   + " where b.CustCode=? and b.WorkType2=? "
				   + " and a.Status <> '3' ";  //非取消
				   //+ " and c.IsCalCost='1' ";  //只统计计算成本
		
		if ("CAL_COST".equals(costType)) {
            sql += " and b.IsCalCost = '1' ";
        } else if ("CAL_PROJECT_COST".equals(costType)) {
            sql += " and b.IsCalProjectCost = '1' ";
        } else {
            sql += " and 1 = 2 ";
        }
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		
		return this.findBySql(sql, new Object[]{workType1Name,custCode,workType2});
	}
	
	public List<Map<String,Object>> goClcbmxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		String sql = " select * from ( select a.no,a.itemtype2,c.Descr itemtype2name,c.materworktype2,n.Descr materworktype2name,a.othercost,a.othercostadj, "
				   + " a.projectothercost,a.type,g.Note typename,a.status,h.note statusname,a.date,a.sendtype,m.Note sendtypename, "
				   + " case when a.sendType='1' then e.PayDate else f.CheckDate end paydate, "
				   + " case when a.sendType='1' then e.DocumentNo else f.DocumentNo end documentno, "
				   + " case when a.sendType='1' then e.Remark else f.Remarks end remarks, "
				   + " round(a.OtherCost+a.OtherCostAdj+a.ProjectOtherCost+case when a.Type='S' then sum(b.ProjectCost*b.Qty) else sum(b.ProjectCost*b.Qty)*-1 end,0) materialcostjs, "
				   + " round(a.OtherCost+a.OtherCostAdj+case when a.Type='S' then sum(b.Cost*b.Qty) else sum(b.Cost*b.Qty)*-1 end,0) materialcost "
				   + " ,? worktype1name,sp.descr supplcodedescr,a.remarks itemappremarks,a.preappno,wk.nameChi workername "
				   + " from tItemApp a "
				   + " inner join tItemAppDetail b on a.No=b.No "
				   + " inner join tItemType2 c on a.ItemType2=c.Code "
				   + " inner join tWorkType2 n on c.MaterWorkType2=n.Code "
				   + " left join tPurchase d on a.PUNo=d.No "
				   + " left join tSplCheckout e on d.CheckOutNo=e.No "
				   + " left join tWHCheckout f on a.WHCheckOutNo=f.No "
				   + " left join tXTDM g on a.Type=g.CBM and g.ID='ITEMAPPTYPE' "
				   + " left join tXTDM h on a.Status=h.CBM and h.ID='ITEMAPPSTATUS' "
				   + " left join tXTDM m on a.SendType=m.CBM and m.ID='ITEMAPPSENDTYPE' "
				   + " left join tSupplier sp on d.Supplier=sp.Code "
				   + " left join tItemPreApp ipa on a.preAppNo=ipa.No "
				   + " left join tWorker wk on ipa.workerCode=wk.Code "
				   + " where a.CustCode=? and c.MaterWorkType2=? "
				   + " and a.Status <> 'CANCEL' and a.ItemType1='JZ'  "
				   + " group by a.No,a.ItemType2,c.Descr,c.MaterWorkType2,n.Descr,a.OtherCost,a.OtherCostAdj,a.ProjectOtherCost,a.Type,a.remarks,wk.nameChi, "
				   + " g.Note,a.Status,h.Note,a.Date,a.SendType,m.Note,e.PayDate,f.CheckDate,e.DocumentNo,f.DocumentNo,e.Remark,f.Remarks,sp.Descr,a.preappno ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{workType1Name,custCode,workType2});
	}
	
	public List<Map<String,Object>> goLlmxJqGrid(Page<Map<String,Object>> page,String no,String custCode){
		String sql = " select * from ( Select a.pk,a.no,a.reqpk,a.fixareapk,b.descr fixareadescr,a.intprodpk,c.Descr intproddescr, "
				   + " a.itemcode,d.Descr itemdescr,a.qty,a.sendqty,a.reqqty,a.sendedqty,confirmedqty=ISNULL((SELECT SUM(Qty) FROM tItemAppDetail i2, "
				   + " tItemApp i1 WHERE i2.No=i1.No AND i2.ItemCode = a.ItemCode AND i1.Status='CONFIRMED' and i1.CustCode = ? ),0), "
				   + " a.remarks, "
				   + " (case when t.Type='S' then a.Cost*a.Qty else a.Cost*a.Qty*-1 end) bCost,a.projectcost, "
				   + " isnull(a.ProjectCost,0)*isnull(a.Qty,0) xmjljszj"
				   + " from (" 
				   + " 		select a.PK,a.No,a.ReqPK,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.Qty,a.SendQty,b.Qty as ReqQty,b.SendQty as SendedQty, "
				   + " 		a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Cost,a.ProjectCost "
				   + " 		FROM tItemAppDetail a "
				   + " 		left outer join tItemReq b on a.ReqPK = b.PK "
				   + " 		where a.No=? "
				   + " ) a  "
				   + " inner join tItemApp t on a.No=t.No "
				   + " left outer join tFixArea b on a.FixAreaPK = b.PK "
				   + " left outer join tIntProd c on a.IntProdPK = c.PK "
				   + " left outer join tItem d on a.ItemCode = d.Code "
				   + " left outer join tPurchase p on t.PUNo=p.no "
				   + " where 1=1 "
				   + " and t.Status <> 'CANCEL' ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode,no});
	}
	
	public List<Map<String,Object>> goRgfymxJqGrid(Page<Map<String,Object>> page,String custCode){
		String sql = " select * from ( select a.no,a.itemtype1,x1.Descr itemtype1descr,a.status,x2.NOTE statusdescr, "
				   + " a.actname,b.pk,b.feetype,c.Descr feetypedescr,b.amount,b.remarks,a.documentno "
				   + " from tLaborFee a inner join tLaborFeeDetail b on a.No=b.No "
				   + " left join tLaborFeeType c on b.FeeType=c.Code "
				   + " left join tItemAppSend d on b.AppSendNo=d.No "
				   + " left join titemapp e on d.IANo=e.No "
				   + " left join titemtype1 x1 on a.ItemType1=x1.Code "
				   + " left join txtdm x2 on a.Status=x2.CBM and x2.id='LABORFEESTATUS' "
				   + " where a.Status<>'5' and c.IsCalCost='1' and e.IsSetItem='0' "
				   + " and a.ItemType1='JZ' "
				   + " and b.CustCode=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode});
	}
	
	public Map<String,Object> getCustPayPlan(String custCode){
		String sql = " select a.SignDate,ISNULL(a.ContractFee,0) ContractFee,isnull(case when tpr.DesignFeeType='2' then a.StdDesignFee else a.DesignFee end,0) DesignFee,a.Area,a.FirstPay,a.SecondPay,a.ThirdPay,a.FourPay, "
				   + " b.NOTE StatusDescr,c.NOTE LayOutDescr,a.Tax, "
		           + " CupBoardDisc*a.ContainCup + IntegrateDisc*a.ContainInt + SoftDisc*a.ContainSoft + MainDisc*a.ContainMain + BaseDisc*a.ContainBase SumDisc, "
		           + " DesignFee+FirstPay+SecondPay+ThirdPay+FourPay SumPay, "
		           + " ContractFee+DesignFee ContractDesign, "
		           + " a.BaseFee_Dirct*a.ContainBase BaseFee_DirctContainBase, "
		           + " a.BaseFee_Comp*a.ContainBase BaseFee_CompContainBase, "
		           + " a.ManageFee*a.ContainBase ManageFeeContainBase, "
		           + " a.MainFee*a.ContainMain MainFeeContainMain, "
		           + " a.SoftFee*a.ContainSoft SoftFeeContainSoft, "
		           + " a.IntegrateFee*a.ContainInt IntegrateFeeContainInt, "
		           + " a.CupboardFee*a.ContainCup CupboardFeeContainCup, "
		           + " a.MainServFee*a.ContainMainServ MainServFeeContainMainServ, "
		           + " isnull(convert(char(10),signdate,120),'') sSignDate, "
		           + " cast(dbo.fGetEmpNameChi(a.Code,'00') as nvarchar(1000)) DesignManDescr, "
		           + " cast(dbo.fGetEmpNameChi(a.Code,'01') as nvarchar(1000)) BusinessManDescr, "
		           + " isnull((select sum(LineAmount) BaseFee0240 from tBaseItemPlan b where b.custcode=a.code and BaseItemCode='0240'),0) BaseFee0240  "
		           + " ,d.NameChi ProjectManName,e.NameChi DesignManName,f.NameChi BusinessManName,(case when exists(select 1 from tPayRuleDetail where No=tpr.No and IsRcvDesignFee='1' and Expired='F') then 1 else 0 end) isRcvDesignFee "
		           + " from tCustomer a  "
		           + " left outer join tXTDM b on a.Status=b.CBM and b.ID='CUSTOMERSTATUS'  "
		           + " left outer join tXTDM c on a.LayOut=c.CBM and c.ID='LAYOUT' "
		           + " left join tEmployee d on a.ProjectMan= d.Number "
		           + " left join tEmployee e on a.DesignMan= e.Number "
		           + " left join tEmployee f on a.BusinessMan= f.Number "
				   + " left join tPayRule tpr on tpr.CustType=a.Custtype and tpr.PayType=a.PayType "
		           + " where code=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public List<Map<String,Object>> goKhfkJqGrid(Page<Map<String,Object>> page,String custCode){
		String sql = " select * from ( select  "
				   + " b.custcode,a.descr,a.address,a.firstpay,a.secondpay,a.thirdpay,a.fourpay, "
				   + " a.contractfee,b.amount,b.date,a.code,c.documentno,c.checkdate,c.remarks remarkscheck, "
				   + " b.lastupdate,b.lastupdatedby, "
				   //+ " b.remarks,isnull(PK,0) as PK from tCustomer a left outer join tCustPay b on "
				   + " b.remarks,isnull(PK,0) as PK from tCustomer a inner join tCustPay b on "
				   + " b.CustCode = a.Code "
				   + " left join tPayCheckOut c on b.PayCheckOutNo=c.No "
				   + " where a.Code = ? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode});
	}

	public List<Map<String,Object>> goYxxmjljlJqGrid(Page<Map<String,Object>> page,String custCode){
		String sql = " select * from ( select a.no,a.date,a.documentno,a.payczy,a.paydate,b.pk,g.NameChi applyman,b.worktype2,d.Descr worktype2name, "
				   + " b.confirmamount,b.salarytype,c.Descr salarytypename,b.status,e.Note statusname,b.appamount, "
				   + " b.actname,b.iswithhold,f.Note iswithholdname,b.withholdno,h.Descr worktype1name, "
				   + " Round(dbo.fGetCustBaseCtrl(b.CustCode,1,b.WorkType2)*0.1,0) kzx, "
				   + " Round(dbo.fGetCustBaseCost(b.CustCode,5,b.WorkType2,default),0) ljjlje, "
				   + " Round(dbo.fGetCustBaseCtrl(b.CustCode,1,b.WorkType2)*0.1,0)-Round(dbo.fGetCustBaseCost(b.CustCode,5,b.WorkType2,default),0) yue  "
				   + " from tWorkCost a  "
				   + " inner join tWorkCostDetail b on a.No=b.No "
				   + " inner join tSalaryType c on b.SalaryType=c.Code "
				   + " inner join tWorkType2 d on b.WorkType2=d.Code "
				   + " inner join tWorkType1 h on d.WorkType1=h.Code "
				   + " left join tXTDM e on b.Status=e.CBM and e.ID='WorkCostStatus' "
				   + " left join tXTDM f on b.IsWithHold=f.CBM and f.ID='YESNO' "
				   + " left join tEmployee g on b.ApplyMan=g.Number "
				   + " where b.CustCode=? "
				   + " and b.Status <> '3' "
				   + " and b.SalaryType='05'";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.WorkType2,a.Date ";
		}
		return this.findBySql(sql, new Object[]{custCode});
	}

	public List<Map<String,Object>> goYkmxJqGrid(Page<Map<String,Object>> page,String custCode){
		String sql = " select * from ( select t.pk,t.custcode,t.worktype2,t1.Descr worktype2descr,t1.worktype1,t2.Descr worktype1descr,t.type,t3.NOTE typedescr, "
				   + " t.amount,t.remarks,t.lastupdate,t.lastupdatedby,t.expired,t.actionlog,t.iscreate,t4.NOTE iscreatedescr "
				   + " from tPrjWithHold t  "
				   + " left outer join tWorkType2 t1 on t1.Code=t.WorkType2 "
				   + " left outer join tWorkType1 t2 on t2.Code=t1.WorkType1 "
				   + " left outer join tXTDM t3 on t3.IBM=t.Type and t3.ID = 'PrjWithHoldType' "
				   + " left outer join tXTDM t4 on t4.IBM=t.isCreate and t4.ID = 'YESNO' "
				   + " where  t.CustCode=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate desc ";
		}
		return this.findBySql(sql, new Object[]{custCode});
	}

	public Map<String,Object> getZjzje(String custCode){
		String sql = " select sum(zjzje) zjzje from ( "
				   + " select isnull(sum(a.Amount),0) zjzje from tItemChg a "
				   + " where custcode=? and status='2' "
				   + " union all "
				   + " select isnull(sum(b.Amount),0) from tBaseItemChg b "
				   + " where custcode=? and status='2' "
				   + " UNION ALL "
				   + " select isnull(sum(c.ChgAmount),0) from tConFeeChg c "
				   + " where CustCode=? and status='CONFIRMED' ) d ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,custCode,custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public Map<String,Object> getHasPay(String custCode){
		String sql = " select isnull(sum(Amount),0) Haspay from tCustPay where CustCode=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});	
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Map<String,Object> getJccbzcxx(String custCode){
		String sql = " select a.code,a.DocumentNo,a.Descr,a.Address,a.Area,a.Status,s1.NOTE StatusDescr, "
                   + " a.ProjectMan,e3.NameChi ProjectManDescr,a.CheckStatus,s2.NOTE CheckStatusDescr, " 
                   + " a.DesignMan,e1.NameChi DesignManDescr,a.BusinessMan,e2.NameChi BusinessManDescr, "
                   + " p.AppCZY,e4.NameChi AppCZYDescr,p.Date,p.ConfirmCZY,e5.NameChi ConfirmCZYDescr,p.ConfirmDate, "
                   + " a.MainSetFee,a.LongFee,isnull(p.AllSetAdd, 0) AllSetAdd,isnull(p.AllSetMinus, 0) AllSetMinus, "
                   + " isnull(p.AllItemAmount, 0) AllItemAmount, "
                   + " isnull(p.AllManageFee_Base, 0) AllManageFee_Base,isnull(p.UpgWithHold, 0) UpgWithHold, "
                   + " isnull(p.BaseCtrlAmt, 0) BaseCtrlAmt,isnull(p.Cost, 0) Cost,isnull(p.BaseCost, 0) BaseCost,isnull(p.MainCost, 0) MainCost, "
                   + " isnull(p.WithHold,0) WithHold,isnull(p.RecvFee, 0) RecvFee,isnull(p.QualityFee, 0) QualityFee,isnull(p.MustAmount, 0) MustAmount, "
                   + " isnull(p.RealAmount, 0) RealAmount,isnull(p.MainCoopFee, 0) MainCoopFee,p.Remarks, p.IsProvide, p.ProvideNo, "
                   + " p.LastUpdate,p.LastUpdatedBy, p.Expired, p.ActionLog,ct.type,a.ctrlAdjRemark,a.projectCtrlAdj,  "
                   + " a.Descr descr,a.Address address,s1.NOTE custStatDescr,ct.prjCtrlType "//解决基础人工成本收支明细中表头信息大小写冲突导致套餐客户信息不显示
                   + " ,isnull(p.RecvFee_FixDuty, 0) recvFeeFixDuty"
                   + " from tCustomer a "
                   + " inner join tCustType ct on a.CustType=ct.Code "
                   + " left join tPrjCheck p on a.Code=p.CustCode "
                   + " left join tEmployee e1 on a.DesignMan=e1.Number "
                   + " left join tEmployee e2 on a.BusinessMan=e2.Number "
                   + " left join tEmployee e3 on a.ProjectMan=e3.Number "
                   + " left join tEmployee e4 on p.AppCZY=e4.Number "
                   + " left join tEmployee e5 on p.ConfirmCZY=e5.Number "
                   + " left join tXTDM s1 on a.Status=s1.CBM and s1.ID='CUSTOMERSTATUS' "
                   + " left join tXTDM s2 on a.CheckStatus=s2.CBM and s2.ID='CheckStatus' "
                   + " left join ( "
                   + "   select CustCode,isnull(sum(Amount-SetMinus),0) SetAdd_Chg,isnull(sum(-SetMinus),0) SetMinus_Chg "
                   + "   from tBaseItemChg where Status='2' group by CustCode "
                   + " ) chg_b on a.Code=chg_b.CustCode "
                   + " left join ( "
                   + "   select CustCode,isnull(sum(Amount),0) Material_Chg "
                   + "   from tItemChg where Status='2' and (ItemType1='ZC' or ItemType1='RZ') group by CustCode "
                   + " ) chg_m on a.Code=chg_m.CustCode "
                   + " left join ( "
                   + "   select CustCode,isnull(sum(ChgAmount),0) ContractFee_Chg "
                   + "   from tConFeeChg where Status='CONFIRMED' and ChgType='2' group by CustCode "
                   + " ) con on a.Code=con.CustCode "
                   + " where a.Code = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public List<Map<String,Object>> getSzhz(String custCode){
		Connection conn = null;
		CallableStatement call = null;
		List<Map<String, Object>> list = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSzhz(?)}");
			call.setString(1, custCode);
			call.execute();
			ResultSet rs = call.getResultSet();
			list = BeanConvertUtil.resultSetToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return list;
	}

	public List<Map<String,Object>> goJcbgJqGrid(Page<Map<String,Object>> page,String custCode){
		String sql = " select * from ( select no,date,Amount-SetMinus setadd,setminus,managefee,befamount,discamount,amount "
				   + " from tBaseItemChg where Status='2' and CustCode=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode});
	}

	public List<Map<String,Object>> goJcclcbmxJqGrid(Page<Map<String,Object>> page,String custCode,String workType2){
		String sql = " select * from ( select a.no,a.type,x1.note typedescr,a.itemtype1,x4.Descr itemtype1descr,a.status,x2.NOTE statusdescr, "
				   + " a.sendtype,x3.NOTE sendtypedescr,a.date, "
				   + " Case When a.Type='S' then a.ProjectAmount else a.ProjectAmount*-1 end cost, "
				   + " Case When a.Type='S' then a.Amount else a.Amount*-1 end+a.OtherCost+a.OtherCostAdj amount, "
				   + " Case When a.Type='S' then a.ProjectAmount else a.ProjectAmount*-1 end projectamount, "
				   + " case when a.sendType='1' then e.PayDate else f.CheckDate end paydate, "
				   + " case when a.sendType='1' then e.DocumentNo else f.DocumentNo end documentno, "
				   + " case when a.sendType='1' then e.Remark else f.Remarks end remarks,a.Remarks itemappremarks, "
				   + " a.othercost,a.othercostadj,a.projectothercost,sp.Descr supplcodedescr,x5.Descr itemtype2descr "
				   + " from tItemApp a "
				   + " left join tXTDM x1 on a.Type=x1.CBM and x1.ID='ITEMAPPTYPE' "
				   + " left join tXTDM x2 on a.Status=x2.CBM and x2.ID='ITEMAPPSTATUS' "
				   + " left join tXTDM x3 on a.SendType=x3.CBM and x3.ID='ITEMAPPSENDTYPE' "
				   + " left join tItemType1 x4 on a.ItemType1=x4.code "
				   + " left join tItemType2 x5 on a.ItemType2=x5.Code "
				   + " left join tPurchase d on a.PUNo=d.No "
				   + " left join tSplCheckout e on d.CheckOutNo=e.No "
				   + " left join tWHCheckout f on a.WHCheckOutNo=f.No "
				   + " left join tSupplier sp on d.Supplier=sp.Code "
				   + " where a.Status<>'CANCEL' and a.ItemType1='JZ' "
				   + " and a.CustCode=? and x5.MaterWorkType2=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode,workType2});
	}

	public List<Map<String,Object>> goTcnclcbmxJqGrid(Page<Map<String,Object>> page,String custCode,String itemType1){
		String sql = " select * from ( select a.no,a.type,x1.note typedescr,a.itemtype1,x4.Descr itemtype1descr,a.status,x2.NOTE statusdescr, "
			       + " a.sendtype,x3.NOTE sendtypedescr,a.date, "
			       + " Case When a.Type='S' then a.ProjectAmount+a.OtherCost+a.OtherCostAdj+a.ProjectOtherCost "
			       + " else a.ProjectAmount*-1+a.OtherCost+a.OtherCostAdj+a.ProjectOtherCost end cost, "
			       + " Case When a.Type='S' then a.Amount else a.Amount*-1 end+a.OtherCost+a.OtherCostAdj amount, "
			       + " Case When a.Type='S' then a.ProjectAmount else a.ProjectAmount*-1 end projectamount, "
			       + " case when a.sendType='1' then e.PayDate else f.CheckDate end paydate, "
			       + " case when a.sendType='1' then e.DocumentNo else f.DocumentNo end documentno, "
			       + " case when a.sendType='1' then e.Remark else f.Remarks end remarks, "
			       + " a.othercost,a.othercostadj,a.projectothercost,sp.Descr supplcodedescr,x5.Descr itemtype2descr,a.Remarks itemappremarks "
			       + " from tItemApp a "
			       + " left join tXTDM x1 on a.Type=x1.CBM and x1.ID='ITEMAPPTYPE' "
			       + " left join tXTDM x2 on a.Status=x2.CBM and x2.ID='ITEMAPPSTATUS' "
			       + " left join tXTDM x3 on a.SendType=x3.CBM and x3.ID='ITEMAPPSENDTYPE' "
			       + " left join tItemType1 x4 on a.ItemType1=x4.code "
			       + " left join tItemType2 x5 on a.ItemType2=x5.Code "
			       + " left join tPurchase d on a.PUNo=d.No "
			       + " left join tSplCheckout e on d.CheckOutNo=e.No "
			       + " left join tWHCheckout f on a.WHCheckOutNo=f.No "
			       + " left join tSupplier sp on d.Supplier=sp.Code "
			       + " where a.Status<>'CANCEL' and a.ItemType1=? and a.IsSetItem='1' "
			       + " and a.CustCode=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{(StringUtils.isNotBlank(itemType1)?itemType1:""),custCode});
	}
	

	public List<Map<String,Object>> goJcxqJqGrid(Page<Map<String,Object>> page,String workType1,String baseItem,String custCode,String isOutSet){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.qty,a.unitprice,a.lineamount,a.remark,a.material,a.FixAreaPk,a.lastupdate,a.lastupdatedby,a.actionlog,a.expired, "
				   + " c.Descr fixareadescr,b.Descr baseitemdescr,d.Descr uom,a.PrjCtrlType,tx.NOTE prjctrltypedescr, "
				   + " a.offerctrl,x.note isoutsetdescr,"
				   + " a.materialctrl,"
				   + " CASE WHEN e.BaseCtrlPer IS NOT NULL "
				   + " THEN round(round(a.UnitPrice*a.Qty,0)*e.BaseCtrlPer,2) "
				   + " ELSE ROUND( ( CASE WHEN a.PrjCtrlType='1' "
				   + " 			  THEN a.UnitPrice*a.OfferCtrl "
				   + " 			  ELSE a.OfferCtrl "
				   + " 			  END )*a.Qty ,2) "
				   + " end +"
				   + " CASE WHEN e.BaseCtrlPer IS NOT NULL "
				   + " THEN round(round(a.Material*a.Qty,0)*e.BaseCtrlPer,2) "
				   + " ELSE ROUND( ( CASE WHEN a.PrjCtrlType='1' "
				   + " 			  THEN a.Material*a.MaterialCtrl "
				   + " 			  ELSE a.MaterialCtrl "
				   + " 			  END )*a.Qty ,2) "
				   + " end totalctrl" // 基础需求增加发包方式、人工发包、材料发包、发包额。发包额的计算方法参考非套餐客户的基础收支信息；此修改只涉及套餐客户。add by zb
				   + " ,x2.note isbaseitemsetdescr "
				   + " from tBaseItemReq a "
				   + " left outer join tBaseItem b on b.Code=a.BaseItemCode "
				   + " left join tBaseItemType2 cc on b.BaseItemType2=cc.Code "
				   + " left join tWorkType2 dd on cc.MaterWorkType2=dd.Code "
				   + " left outer join tFixArea c on c.Pk=a.FixAreaPK "
				   + " left outer join tUom d on b.Uom=d.Code "
				   + " left join tCustBaseCtrl e on e.CustCode = a.CustCode and e.BaseItemCode = a.BaseItemCode "
				   + " left join tXTDM tx on tx.CBM = a.PrjCtrlType and tx.ID = 'PRJCTRLTYPE' "
				   + " left join tXTDM x on x.ID='YESNO' and a.isOutSet=x.IBM "
				   + " left join tXTDM x2 on x2.ID='YESNO' and b.IsBaseItemSet=x2.cbm "
				   + " where a.Qty<> 0 and a.CustCode=? ";
		params.add(custCode);
		if(StringUtils.isNotBlank(workType1)){
			sql += " and RTrim(dd.WorkType1) = ? ";
			params.add(workType1.trim());
		}
		if(StringUtils.isNotBlank(baseItem)){
			sql += " and b.Descr like ? ";
			params.add("%"+baseItem+"%");
		}
		if(StringUtils.isNotBlank(isOutSet)){
			sql += " and a.isOutSet = ? ";
			params.add(isOutSet.trim());
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.FixAreaPk ";
		}
		return this.findBySql(sql, params.toArray());
	}
	
    public List<Map<String, Object>> goRgcbmxTcJqGrid(Page<Map<String, Object>> page,
            String costType, String custCode, String workType2) {
		String sql = " select * from ( select a.no,a.date,a.documentno,a.payczy,a.paydate,b.pk,g.NameChi applyman,b.worktype2,d.Descr worktype2name, "
				   + " b.confirmamount,b.salarytype,c.Descr salarytypename,b.status,e.Note statusname,b.appamount,b.remarks,b.confirmremark, "
				   + " b.actname,b.iswithhold,f.Note iswithholdname,b.withholdno "
				   + " ,x.Descr worktype1name, h.NOTE iscalprojectcostdescr "
				   + " from tWorkCost a "
				   + " inner join tWorkCostDetail b on a.No=b.No "
				   + " left join tSalaryType c on b.SalaryType=c.Code "
				   + " left join tWorkType2 d on b.WorkType2=d.Code "
				   + " left join tWorkType1 x on d.WorkType1=x.Code "
				   + " left join tXTDM e on b.Status=e.CBM and e.ID='WorkCostStatus' "
				   + " left join tXTDM f on b.IsWithHold=f.CBM and f.ID='YESNO' "
				   + " left join tEmployee g on b.ApplyMan=g.Number "
				   + " left join tXTDM h on h.ID = 'YESNO' and h.CBM = b.IsCalProjectCost "
				   + " where b.CustCode=? "
				   + " and a.Status <> '3' ";  //非取消
				   //+ " and b.IsCalCost='1' ';  //只统计计算成本
		
        if ("CAL_COST".equals(costType)) {
            sql += " and b.IsCalCost = '1' ";
        } else if ("CAL_PROJECT_COST".equals(costType)) {
            sql += " and b.IsCalProjectCost = '1' ";
        } else {
            sql += " and 1 = 2 ";
        }
		
		sql += " and b.WorkType2=? ";
		
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		
		return this.findBySql(sql, new Object[]{custCode,workType2});
	}

	public List<Map<String,Object>> goRgfymxTcJqGrid(Page<Map<String,Object>> page,String custCode){
		String sql = " select * from ( select a.no,a.itemtype1,x1.Descr itemtype1descr,a.status,x2.NOTE statusdescr, "
				   + " a.actname,b.pk,b.feetype,c.Descr feetypedescr,b.amount,b.remarks,a.documentno "
				   + " from tLaborFee a inner join tLaborFeeDetail b on a.No=b.No "
				   + " left join tLaborFeeType c on b.FeeType=c.Code "
				   + " left join tItemAppSend d on b.AppSendNo=d.No "
				   + " left join titemapp e on d.IANo=e.No "
				   + " left join tCustomer ct on e.CustCode=ct.Code "
				   + " left join tCustType cty on ct.CustType=cty.Code "
				   + " left join titemtype1 x1 on a.ItemType1=x1.Code "
				   + " left join txtdm x2 on a.Status=x2.CBM and x2.id='LABORFEESTATUS' "
				   + " where a.Status<>'5' and c.IsCalCost='1' and e.IsSetItem='1' "
				   + " and (a.ItemType1='JZ' or a.ItemType1=case when cty.IsSetMainCtrl='1' then 'ZC' else '' end) "
				   + " and b.CustCode=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode});
	}
	
	@SuppressWarnings("deprecation")
	public List<Map<String,Object>> goZcrzszxxJqGrid(String custCode){
		Connection conn = null;
		CallableStatement call = null;
		List<Map<String, Object>> list = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pZcrzszcx(?)}");
			call.setString(1, custCode);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			list = BeanConvertUtil.resultSetToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return list;
	}
	
	public List<Map<String,Object>> goLldJqGrid(Page<Map<String,Object>> page,String custCode,String type,String itemType2,String itemAppStatus){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select distinct a.no,a.itemtype1,i.Descr itemtype1descr,a.custcode,c.Descr custdescr,b.address,a.status,s1.Note statusdescr, "
				   + " a.appczy,e1.NameChi appname,a.date,a.confirmczy,e2.NameChi confirmname,a.confirmdate,isnull(sco.DocumentNo,whc.DocumentNo) documentno, "
				   + " a.sendczy,e3.NameChi sendname,a.senddate,a.sendtype,s2.Note sendtypedescr,a.supplcode,sp.Descr suppldescr, "
				   + " a.puno,a.whcode,w.Desc1 warehouse,a.remarks,a.lastupdate,a.lastupdatedby,a.expired,a.actionlog, "
				   + " p.checkoutno,sco.date checkoutdate,a.othercost,a.othercostadj,a.projectothercost, "
				   + " Round(case when a.Type='S' then round(a.Amount,0) else round(a.Amount*-1,0) end +a.OtherCost+a.OtherCostAdj,0)  bcost, "
				   + " case when a.Type='S' then a.Amount else a.Amount*-1 end clcost,s3.Note issetitemdescr, "
				   + " Round(case when a.Type='S' then round(a.ProjectAmount,0) else Round(a.ProjectAmount,0)*-1 end +a.OtherCost+a.OtherCostAdj+a.ProjectOtherCost,0) clcostjs, "
				   + " p.remainamount,p.secondamount ,s4.NOTE isCupboardDescr ";
		if("RZ".equals(type)){
			sql += " ,i2.Descr itemtype2descr ";
		}
		sql += " from tItemApp a "
			+ " inner join tItemAppDetail bb on a.no=bb.no "
			+ " left join tItem cc on bb.ItemCode=cc.Code "
			+ " left outer join tItemType1 i on a.ItemType1=i.Code "
			+ " left outer join tItemType2 i2 on cc.ItemType2=i2.Code "
			+ " left outer join tCustomer c on a.CustCode=c.Code "
			+ " left outer join tCustomer b on b.Code=a.CustCode "
			+ " left outer join tXTDM s1 on a.Status=s1.CBM and s1.ID='ITEMAPPSTATUS' "
			+ " left outer join tEmployee e1 on a.AppCZY=e1.Number "
			+ " left outer join tEmployee e2 on a.ConfirmCZY=e2.Number "
			+ " left outer join tEmployee e3 on a.SendCZY=e3.Number "
			+ " left outer join tWareHouse w on a.WHCode=w.Code "
			+ " left outer join tXTDM  s2 on a.SendType=s2.CBM and s2.ID='ITEMAPPSENDTYPE' "
			+ " left outer join tSupplier sp on a.SupplCode=sp.Code "
			+ " left outer join tPurchase p on a.PUNo=p.no "
			+ " left outer join tSplCheckOut sco on sco.no=p.CheckOutNo "
			+ " left outer join (select IANo,max(WHCheckOutNo) WHCheckOutNo from tItemAppSend group by IANo) ta on ta.iano=a.no "
			+ " left outer join tWHCheckOut whc on whc.no=ta.WHCheckOutNo "
			+ " left outer join tXTDM  s3 on a.IsSetItem=s3.CBM and s3.ID='YESNO' " +
			" left join tXTDM s4 on s4.id='YESNO' and s4.cbm = a.IsCupboard"
			+ " where a.Status<>'CANCEL' and a.CustCode=? ";
		params.add(custCode);
		if(StringUtils.isNotBlank(type)){
			if("ZC".equals(type)){
				sql += " and a.IsSetItem='0' and a.ItemType1 =  'ZC' and a.IsService=0 ";
			}else if("RZ".equals(type)){
				sql += " and a.IsSetItem='0' and a.ItemType1 =  'RZ' ";
				if(StringUtils.isNotBlank(itemAppStatus)){
					sql += " and a.Status=? ";
					params.add(itemAppStatus);
				}
				if(StringUtils.isNotBlank(itemType2)){
					sql += " and cc.ItemType2=? ";
					params.add(itemType2);
				}
			}else if("JC".equals(type)){
				sql += " and a.ItemType1 = 'JC' ";
			}else if("FW".equals(type)){
				sql += " and a.IsSetItem='0' and a.ItemType1 = 'ZC' and a.IsService=1 ";
			}
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, params.toArray());
	}
	
	public List<Map<String,Object>> goLldLlmxJqGrid(Page<Map<String,Object>> page,String no,String custCode){
		String sql = " select * from ( Select a.pk,a.no,a.reqpk,a.fixareapk,b.Descr fixareadescr,a.intprodpk,c.Descr intproddescr, "
            + " a.itemcode,d.Descr itemdescr,a.qty,a.sendqty,a.reqqty,a.sendedqty,confirmedqty=ISNULL((SELECT SUM(Qty) FROM tItemAppDetail i2, "
            + " tItemApp i1 WHERE i2.No=i1.No AND i2.ItemCode = a.ItemCode AND i1.Status='CONFIRMED' and i1.CustCode=? ),0), "
            + " a.remarks, "
            + " (case when t.Type='S' then a.Cost*a.Qty else a.Cost*a.Qty*-1 end) bcost "
            + " from ( "
            + " 	select a.PK,a.No,a.ReqPK,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.Qty,a.SendQty,b.Qty as ReqQty,b.SendQty as SendedQty, "
            + " 	a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,a.Cost "
            + "  	FROM tItemAppDetail a "
            + "  	left outer join tItemReq b on a.ReqPK = b.PK  "
            + " 	where a.No = ? "
            + " ) a  "
            + " inner join tItemApp t on a.No=t.No "
            + " left outer join tFixArea b on a.FixAreaPK = b.PK "
            + " left outer join tIntProd c on a.IntProdPK = c.PK "
            + " left outer join tItem d on a.ItemCode = d.Code "
            + "  left outer join tPurchase p on t.PUNo=p.no "
            //+ " left outer join tSplCheckOut sco on sco.no=p.CheckOutNo "
            + " where 1=1 "
            //+ " and ((t.SendType='1' and t.Status in ('SEND','RETURN')) or (sco.Status='2')) ";
            + " and t.Status <> 'CANCEL' ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode,no});
	}
	
	public List<Map<String,Object>> goClzjJqGrid(Page<Map<String,Object>> page,String custCode,String type){
		String sql = " select * from ( select a.no,a.itemtype1,b.Descr itemtype1descr,a.custcode,c.Descr customerdescr,c.address,a.status, "
                   + " d.NOTE statusdescr,a.date,a.befamount,a.discamount,a.amount,a.remarks, "
                   + " a.lastupdate,a.lastupdatedby,a.expired,a.actionlog "
                   + " from tItemChg a left outer join tItemType1 b on ltrim(rtrim(a.ItemType1))=ltrim(rtrim(b.Code)) "
                   + " left outer join tCustomer c on a.CustCode=c.Code "
                   + " left outer join tXTDM d on a.Status=d.CBM and d.ID='ITEMCHGSTATUS' "
                   + " where a.CustCode = ? ";
		if(StringUtils.isNotBlank(type)){
			if("ZC".equals(type)){
				sql += " and b.Descr = '主材' and a.IsService=0 and a.status<>'3' ";
			}else if("RZ".equals(type)){
				sql += " and b.Descr = '软装' and a.status<>'3' ";
			}else if("JC".equals(type)){
				sql += " and b.Descr = '集成' and a.status<>'3' ";
			}else if("FWXCP".equals(type)){
				sql += " and b.Descr = '主材' and a.IsService=1 and a.status<>'3' ";
			}
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode});
	}
	
	public List<Map<String,Object>> goClzjZjmxJqGrid(Page<Map<String,Object>> page,String no){
		String sql = " select * from ( select a.iscommi,a.pk,a.no,a.reqpk,a.fixareapk,b.Descr fixareadescr,a.intprodpk,c.Descr intproddescr,a.itemcode,d.Descr itemdescr,f.Descr itemtype2descr, "
				   + " a.qty,e.descr uom,a.cost,a.unitprice,a.beflineamount,a.markup,a.lineamount, "
				   + " a.processcost,a.tmplineamount,sql.Descr sqlcodedescr, "
				   + " a.remarks,a.lastupdate,a.lastupdatedby,a.expired,a.actionlog "
				   + " from ("
				   + " 		select a.IsCommi,a.PK,a.No,a.ReqPK,a.FixAreaPK,a.IntProdPK,a.ItemCode,a.Qty, "
				   + " 		a.Cost,a.UnitPrice,a.BefLineAmount,a.Markup,a.LineAmount, "
				   + " 		a.ProcessCost,(a.Qty*a.UnitPrice*a.Markup/100) TmpLineAmount, "
				   + "		a.Remarks,a.LastUpdate,a.LastUpdatedBy,a.ActionLog,a.Expired "
				   + " 		from tItemChgDetail a "
				   + " 		where a.No=? "
				   + " ) a "
				   + " left outer join tFixArea b on b.PK=a.FixAreaPK "
				   + " left outer join tIntProd c on c.Pk=a.IntProdPK "
				   + " left outer join tItem d on d.Code=a.ItemCode "
				   + " left outer join tBrand sql on sql.code=d.SqlCode "
				   + " left outer join tUom e on d.Uom=e.Code "
				   + " left outer join tItemType2 f on f.Code=d.ItemType2";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{no});
	}

	public List<Map<String,Object>> goRgfymxZcrzszxxJqGrid(Page<Map<String,Object>> page,String custCode,String itemType1,String laborFeeStatus){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select a.no,a.itemtype1,x1.Descr itemtype1descr,a.status,x2.NOTE statusdescr, "
				   + " a.actname,b.pk,b.feetype,c.Descr feetypedescr,b.amount,b.remarks,a.documentno "
				   + " from tLaborFee a inner join tLaborFeeDetail b on a.No=b.No "
				   + " left join tLaborFeeType c on b.FeeType=c.Code "
				   + " left join tItemAppSend d on b.AppSendNo=d.No "
				   + " left join titemapp e on d.IANo=e.No "
				   + " left join titemtype1 x1 on a.ItemType1=x1.Code "
				   + " left join txtdm x2 on a.Status=x2.CBM and x2.id='LABORFEESTATUS' "
				   + " where a.Status<>'5' and c.IsCalCost='1'  "//and (e.IsSetItem='0' or e.IsSetItem is null) 统计所有费用 无论套餐内还是套餐外 modify by xzy 20190523
				   + " and b.CustCode=? ";
		params.add(custCode);
		if(StringUtils.isNotBlank(itemType1)){
			sql += " and a.itemType1=? ";
			params.add(itemType1);
		}
		if(StringUtils.isNotBlank(laborFeeStatus)){
			sql += " and a.Status=? ";
			params.add(laborFeeStatus);
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, params.toArray());
	}
	
	public List<Map<String,Object>> goRzmxJqGrid(String custCode){
		Connection conn = null;
		CallableStatement call = null;
		List<Map<String, Object>> list = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pRzszcx(?)}");
			call.setString(1, custCode);
			call.execute();
			ResultSet rs = call.getResultSet(); 
			list = BeanConvertUtil.resultSetToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return list;
	}
	
	public List<Map<String,Object>> goCgdJqGrid(Page<Map<String,Object>> page,String custCode,String itemType2,String purchaseStatus,String isCheckOut,String checkOutStatus){
		List<Object> params = new ArrayList<Object>();
		String sql = " select * from ( select distinct a.no,a.status,xt1.NOTE statusdescr,a.date,a.type,xt2.NOTE typedescr,a.whcode, "
				   + " wh.Desc1 whcodedescr,a.supplier,sp.Descr supplierdescr,a.amount,a.othercost,a.othercostadj,a.firstamount, "
				   + " a.secondamount,a.remainamount,b.documentno,a.ischeckout,xt3.NOTE ischeckoutdescr, "
				   + " xt4.NOTE checkoutstatusdescr,a.checkoutno,a.remarks "
				   + " from tPurchase a "
				   + " inner join tPurchaseDetail aa on a.no=aa.Puno "
				   + " left join tItem bb on aa.ITCode=bb.Code "
				   + " left join tSplCheckOut b on a.CheckOutNo=b.No "
				   + " left join tXTDM xt1 on a.Status=xt1.CBM and xt1.ID='PURCHSTATUS' "
				   + " left join tXTDM xt2 on a.Type=xt2.CBM and xt2.ID='PURCHTYPE' "
				   + " left join tXTDM xt3 on a.IsCheckOut=xt3.CBM and xt3.ID='YESNO' "
				   + " left join tXTDM xt4 on b.status=xt4.CBM and xt4.ID='SPLCKOTSTATUS' "
				   + " left join tWareHouse wh on a.WHCode=wh.Code "
				   + " left join tsupplier sp on a.Supplier=sp.Code "
				   + " where a.CustCode=? and a.ItemType1='RZ' ";
		params.add(custCode);
		if(StringUtils.isNotBlank(itemType2)){
			sql += " and bb.itemType2=? ";
			params.add(itemType2);
		}
		if(StringUtils.isNotBlank(purchaseStatus)){
			sql += " and a.Status=? ";
			params.add(purchaseStatus);
		}
		if(StringUtils.isNotBlank(isCheckOut)){
			sql += " and a.IsCheckOut=? ";
			params.add(isCheckOut);
		}
		if(StringUtils.isNotBlank(checkOutStatus)){
			sql += " and b.Status=? ";
			params.add(checkOutStatus);
		}
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, params.toArray());
	}

	public List<Map<String,Object>> goCgdmxJqGrid(Page<Map<String,Object>> page,String no){
		String sql = " select * from ( select a.itcode,b.Descr itcodedescr,c.Descr sqlcodedescr,a.qtycal,d.Descr uomdescr, "
				   + " a.unitprice,a.amount,a.remarks "
				   + " from tPurchaseDetail a "
				   + " left join tItem b on a.ITCode=b.Code "
				   + " left join tBrand c on b.SqlCode=c.Code "
				   + " left join tUOM d on b.Uom=d.Code "
				   + " where a.PUNo=? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{no});
	}

	/**
	 * 水电发包定额
	 * @author	created by zb
	 * @date	2018-11-26--下午4:36:51
	 * @param page
	 * @param custCode
	 * @return
	 */
	public List<Map<String, Object>> findWaterContractQuotaJqGrid(
			Page<Map<String, Object>> page, String custCode,String workType2) {
		String sql="select * from ( select e.code workType2Code,e.descr workType2Descr,a.Qty,a.Material amount,g.descr FixAreaDescr," +
				" round(a.qty*a.Material,0) totalamount,a.Remark,c.code BaseCheckItemCode,c.descr BaseCheckItemDescr,f.descr UomDescr " +
				" from tBaseCheckItemPlan a  " +
				" inner join tCustomer b on a.CustCode=b.code " +
				" inner join tBaseCheckItem c on a.BaseCheckItemCode=c.Code " +
				" inner join tBaseItemType2 d on c.BaseItemType2=d.Code " +
				" inner join tWorkType2 e on e.Code=d.MaterWorkType2" +
				" left join tUom f on f.Code=c.Uom" +
				" left join tFixArea g on a.FixAreaPK=g.PK "+ 
				" where a.custCode = ? and e.code= ? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode,workType2});
	}
	
	public List<Map<String, Object>> findWaterContractQuotaJqGridOffer(
			Page<Map<String, Object>> page, String custCode,String workType2) {
		String sql="select * from ( select e.code workType2Code,e.descr workType2Descr,a.Qty,a.OfferPri amount,g.descr FixAreaDescr," +
				" round(a.qty*a.OfferPri,0) totalamount,a.Remark,c.code BaseCheckItemCode,c.descr BaseCheckItemDescr,f.descr UomDescr " +
				" from tBaseCheckItemPlan a  " +
				" inner join tCustomer b on a.CustCode=b.code " +
				" inner join tBaseCheckItem c on a.BaseCheckItemCode=c.Code " +
				" inner join tBaseItemType2 d on c.BaseItemType2=d.Code " +
				" inner join tWorkType2 e on e.Code=d.OfferWorkType2" +
				" left join tUom f on f.Code=c.Uom" +
				" left join tFixArea g on a.FixAreaPK=g.PK "+ 
				" where a.custCode = ? and e.code= ? ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode,workType2});
	}
	/**
	 * 统计卧室和卫生间数量
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getFixAreaTypeCount( String custCode) {
		String sql="select count(ws)ws,count(wsj)wsj from( "
				+"select case when FixAreaType = '2' then FixAreaType  end ws , "
				+" 		case when FixAreaType = '3' then FixAreaType end wsj "
				+"from tPrePlanArea "
				+"where CustCode =?  and Area > 0 and FixAreaType in ( '2','3' ) )a";
		return this.findBySql(sql, new Object[]{custCode}).get(0);
	}
	/**
	 * 套餐内主材奖惩
	 * @author cjg
	 * @date 2019-4-22
	 * @param page
	 * @param custCode
	 * @return
	 */
	public List<Map<String, Object>> goTcnzcjcJqGrid(Page<Map<String, Object>> page, String custCode) {
		String sql="select * from ( select c.Descr fixareadescr,b.Descr itemdescr,d.Descr itemtype2descr,"
				+"a.qty,a.sendqty,f.projectcost,(a.qty-a.sendqty)*f.ProjectCost jcje "
				+"from tItemReq a  "
				+"left join tItem b on a.ItemCode=b.Code "
				+"left join tFixArea c on a.FixAreaPK=c.PK "
				+"left join tItemType2 d on b.ItemType2=d.Code "
				+"inner join ( "
				+"		select iad.ReqPK, max(PK) PK from tItemApp ia  "
				+"		inner join tItemAppDetail iad on ia.No =iad.No  "
				+"		where ia.CustCode=? and ia.ItemType1='ZC' and ia.Status='SEND' "
				+"		group by iad.ReqPK "
				+") e on a.PK=e.ReqPK "
				+"inner join tItemAppDetail f on e.PK=f.PK "
				+"left join tItemType12 it12 on it12.code=d.ItemType12 "
				+"where a.ItemType1='ZC' and a.IsOutSet='0' "
				+"and a.CustCode=? and a.Qty<>a.SendQty and a.SendQty>0 and a.IsService='0' and it12.Code<>'14'  ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a "; 
		}
		return this.findBySql(sql, new Object[]{custCode,custCode});
	}
	
	/**
	 * 施工补贴
	 * @author cjg
	 * @date 2020-7-13
	 * @param page
	 * @param custCode
	 * @return
	 */
	public List<Map<String, Object>> goSgbtJqGrid(Page<Map<String, Object>> page, String custCode) {
		String sql="select * from (select a.PrjMaterial,a.PrjOfferPri,a.Qty, " 
				+"(a.PrjMaterial+a.PrjOfferPri)*a.Qty Total,b.Descr BaseCheckItemDescr "
				+"from tBaseCheckItemPlan a " 
				+"left join tBaseCheckItem b on a.BaseCheckItemCode=b.Code "
				+"where a.CustCode=? and a.IsSubsidyItem='1' ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a "; 
		}
		return this.findBySql(sql, new Object[]{custCode});
	}
	
	/**
	 * 查询公式和结果
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getExprByCust( String custCode, String type) {
		if(StringUtils.isBlank(type)){
			type="1";
		}
		String sql="select CtrlExprRemarks,CtrlExprWithNum from  dbo.fGetCustBaseCtrl_Sdj(?,?)";
		List<Map<String, Object>> list=this.findBySql(sql, new Object[]{custCode, type});
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 售后材料明细
	 * @author cjg
	 * @date 2019-11-1
	 * @param page
	 * @param workType1Name
	 * @param custCode
	 * @param workType2
	 * @return
	 */
	public List<Map<String,Object>> goShclmxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		String sql = " select * from ( select a.no,a.itemtype2,c.Descr itemtype2name,c.materworktype2,n.Descr materworktype2name,a.othercost,a.othercostadj, "
				   + " a.projectothercost,a.type,g.Note typename,a.status,h.note statusname,a.date,a.sendtype,m.Note sendtypename, "
				   + " case when a.sendType='1' then e.PayDate else f.CheckDate end paydate, "
				   + " case when a.sendType='1' then e.DocumentNo else f.DocumentNo end documentno, "
				   + " case when a.sendType='1' then e.Remark else f.Remarks end remarks, "
				   + " round(a.OtherCost+a.OtherCostAdj+a.ProjectOtherCost+case when a.Type='S' then sum(b.ProjectCost*b.Qty) else sum(b.ProjectCost*b.Qty)*-1 end,0) materialcostjs, "
				   + " round(a.OtherCost+a.OtherCostAdj+case when a.Type='S' then sum(b.Cost*b.Qty) else sum(b.Cost*b.Qty)*-1 end,0) materialcost "
				   + " ,wt1.Descr worktype1name,sp.descr supplcodedescr,a.remarks itemappremarks,a.preappno,wk.nameChi workername,ct.address "
				   + " from tItemApp a "
				   + " inner join tItemAppDetail b on a.No=b.No "
				   + " inner join tItemType2 c on a.ItemType2=c.Code "
				   + " inner join tWorkType2 n on c.MaterWorkType2=n.Code "
				   + " left join tWorkType1 wt1 on n.WorkType1=wt1.Code "
				   + " left join tPurchase d on a.PUNo=d.No "
				   + " left join tSplCheckout e on d.CheckOutNo=e.No "
				   + " left join tWHCheckout f on a.WHCheckOutNo=f.No "
				   + " left join tXTDM g on a.Type=g.CBM and g.ID='ITEMAPPTYPE' "
				   + " left join tXTDM h on a.Status=h.CBM and h.ID='ITEMAPPSTATUS' "
				   + " left join tXTDM m on a.SendType=m.CBM and m.ID='ITEMAPPSENDTYPE' "
				   + " left join tSupplier sp on d.Supplier=sp.Code "
				   + " left join tItemPreApp ipa on a.preAppNo=ipa.No "
				   + " left join tWorker wk on ipa.workerCode=wk.Code "
				   + " left join tCustomer ct on a.CustCode=ct.Code "
				   + " where a.RefCustCode=?  "
				   + " and a.Status <> 'CANCEL' and a.ItemType1='JZ'  "
				   + " group by a.No,a.ItemType2,c.Descr,c.MaterWorkType2,n.Descr,a.OtherCost,a.OtherCostAdj,a.ProjectOtherCost,a.Type,a.remarks,wk.nameChi, "
				   + " g.Note,a.Status,h.Note,a.Date,a.SendType,m.Note,e.PayDate,f.CheckDate,e.DocumentNo,f.DocumentNo,e.Remark,f.Remarks,sp.Descr,a.preappno,ct.address,wt1.Descr ";
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode});
	}
	/**
	 * 售后人工明细
	 * @author cjg
	 * @date 2019-11-1
	 * @param page
	 * @param workType1Name
	 * @param custCode
	 * @param workType2
	 * @return
	 */
	public List<Map<String,Object>> goShrgmxJqGrid(Page<Map<String,Object>> page,String workType1Name,String custCode,String workType2){
		String sql = " select * from ( select a.no,a.date,a.documentno,a.payczy,a.paydate,b.pk,g.NameChi applyman,b.worktype2,d.descr worktype2name, "
				   + " b.confirmamount,b.salarytype,c.Descr salarytypename,b.status,e.Note statusname,b.appamount,b.remarks,b.confirmremark, "
				   + " b.actname,b.iswithhold,f.Note iswithholdname,b.withholdno,h.address "
				   + " ,wt1.Descr worktype1name "
				   + " from tWorkCost a "
				   + " inner join tWorkCostDetail b on a.No=b.No "
				   + " inner join tSalaryType c on b.SalaryType=c.Code "
				   + " inner join tWorkType2 d on b.WorkType2=d.Code "
				   + " left join tWorkType1 wt1 on d.WorkType1=wt1.Code "
				   + " left join tXTDM e on b.Status=e.CBM and e.ID='WorkCostStatus' "
				   + " left join tXTDM f on b.IsWithHold=f.CBM and f.ID='YESNO' "
				   + " left join tEmployee g on b.ApplyMan=g.Number "
				   + " left join tCustomer h on b.CustCode=h.Code "
				   + " where b.RefCustCode=? "
				   + " and a.Status <> '3' "  //非取消
				   + " and c.IsCalCost='1' ";  //只统计计算成本
		if(StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a ";
		}
		return this.findBySql(sql, new Object[]{custCode});
	}
	/**
	 * 是否关联楼盘
	 * @author cjg
	 * @date 2019-11-6
	 * @param custCode
	 * @return
	 */
	public List<Map<String,Object>> isRefCustCode(String custCode){
		String sql = " select 1 from tItemApp where RefCustCode=? "; 
		return this.findBySql(sql, new Object[]{custCode});
	}
	/**
	 * 人工是否关联楼盘
	 * @author cjg
	 * @date 2019-12-20
	 * @param custCode
	 * @return
	 */
	public List<Map<String,Object>> isRefCustCode_wc(String custCode){
		String sql = " select 1 from tWorkCostDetail where RefCustCode=? "; 
		return this.findBySql(sql, new Object[]{custCode});
	}
}
