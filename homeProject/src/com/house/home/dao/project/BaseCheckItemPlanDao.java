package com.house.home.dao.project;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.BaseCheckItemPlan;
import com.house.home.entity.project.WorkCost;
import com.house.home.entity.project.WorkCostDetail;

@SuppressWarnings("serial")
@Repository
public class BaseCheckItemPlanDao extends BaseDao {

	/**
	 * BaseCheckItemPlan分页信息
	 * 
	 * @param page
	 * @param baseCheckItemPlan
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, BaseCheckItemPlan baseCheckItemPlan) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select * from(select a.Address,a.code,a.Descr,a.custtype,a.status,a.DesignMan,a.BusinessMan,"
				+ "isnull(b.OfferPri,0)OfferPri,isnull(b.Material,0)Material,isnull(b.PrjOfferPri,0)PrjOfferPri,isnull(b.PrjMaterial,0)PrjMaterial, "
				+ "g.Desc1 custTypeDescr,d.note custStatusDescr,e1.NameChi DesignManDescr,e2.NameChi BusinessManDescr, "
				+ "f.note layoutdescr,h.confirmdate,h.status dppStatus,case when h.status='4' then '已审核' else '未审核' end  dppStatusDescr," 
				+ "e3.NameChi ProjectManDescr,i.Desc1 projectDept,j.note iswateritemctrldescr,k.note iswaterctrldescr, "
				+ "b.checkPlanDate,b.lastUpdate,a.ConfirmBegin,a.area "
				+ "from tCustomer a "
				+ "left join (select CustCode,sum(round(qty*offerPri,0))offerPri,sum(round(qty*material,0))material,sum(round(qty*prjOfferPri,0))prjOfferPri,"
				+ "sum(round(qty*prjMaterial,0))prjMaterial,max(lastupdate)lastUpdate,min(lastupdate)checkPlanDate from tBaseCheckItemPlan group by CustCode) b on a.Code=b.CustCode "
				+ "left join tEmployee e1 on a.DesignMan=e1.Number "
				+ "left join tEmployee e2 on a.BusinessMan=e2.Number "
				+ "left join tEmployee e3 on a.ProjectMan=e3.Number "
				+ "left join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+ "left join tCusttype g on g.code=a.custType  "
				+ "left join tXTDM f on a.layout=f.CBM and f.ID='LAYOUT' "
				+ "left join tDesignPicPrg h on a.code=h.custcode "
				+ "left join tDepartMent2 i on i.code=e3.DepartMent2 "
				+ "left join tXTDM j on a.isWaterItemCtrl=j.CBM and j.ID='YESNO' "
				+ "left join tXTDM k on a.isWaterCtrl=k.CBM and k.ID='YESNO' "
				+ "where 1=1 ";
		if (StringUtils.isNotBlank(baseCheckItemPlan.getCustCode())) {
			sql += " and a.Code=? ";
			list.add(baseCheckItemPlan.getCustCode());
		}
		if (StringUtils.isNotBlank(baseCheckItemPlan.getAddress())) {
			sql += " and a.address like ? ";
			list.add("%" + baseCheckItemPlan.getAddress() + "%");
		}
		if (StringUtils.isNotBlank(baseCheckItemPlan.getCustType())) {
			sql += " and a.custtype in " + "('"
					+ baseCheckItemPlan.getCustType().replace(",", "','")
					+ "')";
		}
		if (StringUtils.isNotBlank(baseCheckItemPlan.getCustStatus())) {
			sql += " and a.status in " + "('"
					+ baseCheckItemPlan.getCustStatus().replace(",", "','")
					+ "')";
		}
		if (StringUtils.isBlank(baseCheckItemPlan.getExpired())
				|| "F".equals(baseCheckItemPlan.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (baseCheckItemPlan.getSignDateFrom() != null) {
			sql += " and a.SignDate>=?";
			list.add(baseCheckItemPlan.getSignDateFrom());
		}
		if (baseCheckItemPlan.getSignDateTo() != null) {
			sql += " and a.SignDate<?";
			list.add(DateUtil.addInteger(baseCheckItemPlan.getSignDateTo(),
					Calendar.DATE, 1));
		}
		if (baseCheckItemPlan.getConfirmBeginFrom() != null) {
			sql += " and a.ConfirmBegin>=?";
			list.add(baseCheckItemPlan.getConfirmBeginFrom());
		}
		if (baseCheckItemPlan.getConfirmBeginTo() != null) {
			sql += " and a.ConfirmBegin<?";
			list.add(DateUtil.addInteger(baseCheckItemPlan.getConfirmBeginTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(baseCheckItemPlan.getDppStatus())) {
			if("1".equals(baseCheckItemPlan.getDppStatus())){
				sql += " and h.status='4' ";
			}else if("0".equals(baseCheckItemPlan.getDppStatus())){
				sql += " and (h.status<>'4' or h.status is null) ";
			}
		}
		sql+=")a where 1=1 ";
		if (baseCheckItemPlan.getCheckPlanDateFrom() != null) {
			sql += " and a.checkPlanDate>=?";
			list.add(baseCheckItemPlan.getCheckPlanDateFrom());
		}
		if (baseCheckItemPlan.getCheckPlanDateTo() != null) {
			sql += " and a.checkPlanDate<?";
			list.add(DateUtil.addInteger(baseCheckItemPlan.getCheckPlanDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.Lastupdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 项目结算界面表头信息
	 * 
	 * @param code
	 * @return
	 */
	public List<Map<String, Object>> findHeadInfoBySql(String code) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.area,a.layout,a.address,a.code,a.descr,a.custType,a.status,a.designMan,a.businessMan,"
				+ "isnull(b.OfferPri,0)offerPri,isnull(b.Material,0)material,isnull(b.PrjOfferPri,0)prjOfferPri,isnull(b.PrjMaterial,0)prjMaterial, "
				+ "g.Desc1 custTypeDescr,f.note layoutDescr,d.note custStatusDescr,e1.NameChi designManDescr,e2.NameChi businessManDescr "
				+ "from tCustomer a "
				+ "left join (select CustCode,sum(qty*offerPri)offerPri,sum(qty*material)material,sum(qty*prjOfferPri)prjOfferPri,"
				+ "sum(qty*prjMaterial)prjMaterial from tBaseCheckItemPlan group by CustCode) b on a.Code=b.CustCode "
				+ "left join tEmployee e1 on a.DesignMan=e1.Number "
				+ "left join tEmployee e2 on a.BusinessMan=e2.Number "
				+ "left join tXTDM d on a.Status=d.CBM and d.ID='CUSTOMERSTATUS' "
				+ "left join tXTDM f on a.LayOut=f.CBM and f.ID='LAYOUT' "
				+ "left join tCusttype g on g.code=a.custType where a.code=? ";
		list.add(code);
		return this.findBySql(sql, list.toArray());
	}

	/**
	 * 表身数据
	 * 
	 * @param page
	 * @param baseCheckItemPlan
	 * @return
	 */
	public Page<Map<String, Object>> findBodyInfoBySql(
			Page<Map<String, Object>> page, BaseCheckItemPlan baseCheckItemPlan) {
		List<Object> list = new ArrayList<Object>();

		String sql = "SELECT * from  (SELECT a.basereqpk,a.lastupdate,a.lastupdatedby,a.actionlog,a.expired,g.descr+' - '+d.Descr ordername,g.descr FAPK ,d.Descr BIDescr,"
				+ "a.FixAreaPK,c.Descr fixareadescr,b.WorkType12,d.Descr workType12Descr,d.WorkType1,e.Descr workType1Descr, "
				+ "b.Code ,b.Descr ,a.Qty,a.OfferPri,a.Material,a.PrjOfferPri,a.PrjMaterial,a.dispseq,a.type,tx.note typedescr,"
				+ "round(a.Qty*a.OfferPri,0) totalOfferPri,round(a.Qty*a.Material,0) totalMaterial,round(a.Qty*a.PrjOfferPri,0) totalPrjOfferPri,round(a.Qty*a.PrjMaterial,0) totalPrjMaterial, "
				+ "case when a.type='1' then round(a.Qty*a.OfferPri,0) else 0 end totalSetOfferPri,case when a.type='2' then round(a.Qty*a.OfferPri,0) else 0 end totalIndiOfferPri,"
				+ "case when a.type='1' then round(a.Qty*a.Material,0) else 0 end totalSetMaterial,case when a.type='2' then round(a.Qty*a.Material,0) else 0 end totalIndiMaterial,"
				+ "round(a.Qty*a.OfferPri,0)+round(a.Qty*a.Material,0) total,round(a.Qty*a.PrjOfferPri,0)+round(a.Qty*a.PrjMaterial,0) prjTotal,a.Remark,f.Descr uom,b.baseitemtype1, "
				+ "b.OfferPri TempOfferPri,b.Material TempMaterial,b.PrjOfferPri TempPrjOfferPri,b.PrjMaterial TempPrjMaterial, "
				+ "a.IsSubsidyItem,x1.note IsSubsidyItemDescr "
				+ "FROM tBaseCheckItemPlan a "
				+ "LEFT JOIN tCustomer tc on a.CustCode=tc.Code "
				+ "LEFT JOIN tBaseCheckItem b on a.BaseCheckItemCode=b.Code "
				+ "LEFT JOIN tFixArea c on a.FixAreaPK=c.PK "
				+ "LEFT JOIN tWorkType12 d ON b.WorkType12=d.Code "
				+ "LEFT JOIN tWorkType1 e ON e.Code=d.WorkType1 "
				+ "LEFT JOIN tBaseItemType1 g ON g.Code=b.baseitemtype1 "
				+ "LEFT JOIN tXTDM tx on tx.CBM = a.Type and tx.ID = 'BASECKITEMTYPE' "
				+ "LEFT JOIN tUOM f ON f.Code=b.Uom " 
				+ "LEFT JOIN tXTDM X1 on X1.CBM = a.IsSubsidyItem and X1.ID='YESNO' "
				+ "WHERE tc.Code=? ";
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.FixAreaPK";
		}
		System.out.println(sql);
		list.add(baseCheckItemPlan.getCustCode());
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 从客户报价导入
	 * 
	 * @param baseCheckItemPlan
	 * @return
	 */
	public List<Map<String, Object>> importFromCust(
			BaseCheckItemPlan baseCheckItemPlan) {
		String sql = "select k.descr+' - '+g.Descr ordername,'F' expired,'ADD' actionlog,getdate() lastupdate,c.code,c.descr,c.baseitemtype1,d.fixareapk,"
				+ baseCheckItemPlan.getLastUpdatedBy()
				+ " lastupdatedby,isnull(c.worktype12,'')worktype12,d.pk basereqpk,"
				+ "isnull(g.worktype1,'')worktype1,isnull(g.Descr,'') worktype12descr,isnull(h.Descr,'') worktype1descr,isnull(i.Descr,'') fixareadescr,"
				+ "isnull(j.descr,'') uom,c.material,c.offerpri,c.prjmaterial,c.prjofferpri,c.type,tx.note typedescr,"
				+ "case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end qty,"
				+ "round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.Material,0) totalmaterial,"
				+ "round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.OfferPri,0) totalofferpri,"
				+ "case when c.type='1' then round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.OfferPri,0) else 0 end totalsetofferpri,"
				+ "case when c.type='2' then round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.OfferPri,0) else 0 end totalindiofferpri,"
				+ "case when c.type='1' then round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.Material,0) else 0 end totalsetmaterial,"
				+ "case when c.type='2' then round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.Material,0) else 0 end totalindimaterial,"
				+ "round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.PrjMaterial,0) totalprjmaterial,"
				+ "round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.PrjOfferPri,0) totalprjofferpri, "
				+ "round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.Material,0)+"
				+ "round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.OfferPri,0) total, "
				+ "round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.PrjMaterial,0)+ "
				+ "round((case when a.CalType='1' then d.Qty when a.CalType='2' then case when e.InnerArea <>0 then e.InnerArea*a.qty else e.Area*f.InnerAreaPer*a.qty end when a.CalType='3' then a.Qty end)*c.PrjOfferPri,0)prjtotal,  "
				+ "c.OfferPri tempofferpri,c.Material tempmaterial,c.PrjOfferPri tempprjofferpri,c.prjmaterial tempprjmaterial, "
				+ "c.issubsidyitem,x1.note issubsidyitemdescr "
				+ "from tBaseItemToCheckItem a "
				+ "left join tBaseItem b on a.BaseItemCode=b.Code "
				+ "left join tBaseCheckItem c on a.BaseCheckItemCode=c.Code "
				+ "left join tBaseItemReq d on b.Code=d.BaseItemCode "
				+ "left join tCustomer e on e.Code=d.CustCode "
				+ "left join tCusttype f on e.CustType=f.Code "
				+ "left join tWorkType12 g on c.WorkType12=g.Code "
				+ "left join tWorkType1 h on g.WorkType1=h.Code "
				+ "left join tFixArea i on d.FixAreaPK=i.PK "
				+ "left join tBaseItemType1 k ON k.Code=c.baseitemtype1 "
				+ "left join tUom j on j.code=c.uom "
				+ "LEFT JOIN tXTDM tx on tx.CBM = c.Type and tx.ID = 'BASECKITEMTYPE' "
				+ "LEFT JOIN tXTDM X1 on X1.CBM = c.IsSubsidyItem and X1.ID='YESNO' "
				+ "where e.Code=? and d.qty<>0 order by i.pk asc";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { baseCheckItemPlan.getCustCode() });
		return list;
	}

	/**
	 * 保存
	 * 
	 * @param baseCheckItemPlan
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(BaseCheckItemPlan baseCheckItemPlan) {
		Assert.notNull(baseCheckItemPlan);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pJcjsxmys(?,?,?,?)}");
			call.setString(1, baseCheckItemPlan.getCustCode());
			call.setString(2, baseCheckItemPlan.getBaseCheckItemPlanJson());
			call.registerOutParameter(3, Types.INTEGER);
			call.registerOutParameter(4, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(3)));
			result.setInfo(call.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;

	}
	/**
	 * 独立销售客户类型
	 * 
	 * @return
	 */
	public List<Map<String, Object>> addAllInfoCustType() {
		String sql = "select code from tcusttype where IsAddAllInfo='1'";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] {});
		return list;
	}
}
