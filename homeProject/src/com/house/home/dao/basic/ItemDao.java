package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.house.framework.bean.Result;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.SqlUtil;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.ItemBatchHeaderQueryEvt;
import com.house.home.client.service.evt.WareHouseItemEvt;
import com.house.home.entity.basic.Item;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.service.basic.CzybmService;

@SuppressWarnings("serial")
@Repository
public class ItemDao extends BaseDao {
	@Autowired
	private HttpServletRequest request;

	/**
	 * Item分页信息
	 * 
	 * @param page
	 * @param item
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();
		UserContext uc = (UserContext) request.getSession().getAttribute(
				CommonConstant.USER_CONTEXT_KEY);
		String sql = "select ";

		if (StringUtils.isNotBlank(item.getPuno())) {
			sql += " pd.UnitPrice  Price,pd.markup pumarkup,pd.beflineprice pubeflineprice ,";
		} else {
			sql += " a.Price,";
		}
		sql += " a.expired,a.Code,a.Descr,a.ItemSize,a.SizeDesc SizeDescr,a.BarCode,a.RemCode,a.ItemType1,i1.Descr ItemType1Descr,"
				+ "a.ItemType2,i2.Descr ItemType2Descr,a.ItemType3,i3.Descr ItemType3Descr,a.oldcost,a.oldprice,"
				+ "a.SqlCode,b.Descr SqlDescr,a.Uom,u.Descr UomDescr,a.SupplCode,s.Descr SplCodeDescr,"
				+ "a.Model,a.Color,a.Remark,a.CommiType,d.NOTE CommiTypeDescr,a.MarketPrice,a.IsFixPrice,"
				+ "p.Note IsFixPriceDescr,a.Cost,a.AvgCost,a.AllQty,a.ProjectCost,a.ActionLog,dbo.fGetPurQty(a.Code,'') PurQty,dbo.fGetUseQty(a.Code,'','') UseQty"
				+ " from tItem a "
				+ " left outer join tItemType1 i1 on a.ItemType1=i1.Code "
				+ " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
				+ " left outer join tItemtype3 i3 on a.ItemType3=i3.Code "
				+ " left outer join tBrand b on a.SqlCode=b.Code "
				+ " left outer join tXTDM d on a.CommiType=d.CBM and d.ID='COMMITYPE' "
				+ " left outer join tXTDM p on a.IsFixPrice=p.CBM and p.ID='ISFIXPRICE' "
				+ " left outer join tSupplier s on a.SupplCode=s.Code "
				+ " left outer join tUom u on a.Uom = u.Code "// +
				+ " left join tcusttype ct on a.CustType=ct.Code ";
																// " where 1=1 ";
		if (StringUtils.isNotBlank(item.getPuno())) {
			sql += "LEFT JOIN (select puno,itcode,unitPrice,markup,beflineprice from tPurchaseDetail group by unitPrice,itcode,puno,markup,beflineprice ) pd on pd.ITCode=a.Code where 1=1 "
					+ " and pd.PUNo=? ";
			list.add(item.getPuno());
		} else {
			sql += "where 1=1";
		}

		if (StringUtils.isNotBlank(item.getCode())) {
			sql += " and (a.Code like ? or a.Descr like ?) ";
			list.add("%" + item.getCode() + "%");
			list.add("%" + item.getCode() + "%");
		}
		if (StringUtils.isNotBlank(item.getDescr())) {
			sql += " and (a.Descr like ? ";
			list.add("%" + item.getDescr() + "%");
			if ("1".equals(item.getContainCode())) {
				sql += " or a.Code like ? ";
				list.add("%" + item.getDescr() + "%");
			}
			sql += " ) ";
		}
		if (StringUtils.isNotBlank(item.getRemCode())) {
			sql += " and a.RemCode=? ";
			list.add(item.getRemCode());
		}
		if (StringUtils.isNotBlank(item.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(item.getItemType1());
		} else {
			
			String itemRight = "";
			for (String str : uc.getItemRight().trim().split(",")) {
				itemRight += "'" + str + "',";
			}
			sql += " and a.ItemType1 in("
					+ itemRight.substring(0, itemRight.length() - 1) + ") ";
		}
		if (StringUtils.isNotBlank(item.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(item.getItemType2());
		}
		if (StringUtils.isNotBlank(item.getItemType3())) {
			sql += " and a.ItemType3=? ";
			list.add(item.getItemType3());
		}
		if (StringUtils.isNotBlank(item.getBarCode())) {
			sql += " and a.BarCode=? ";
			list.add(item.getBarCode());
		}
		if (StringUtils.isNotBlank(item.getSqlCode())) {
			sql += " and a.SqlCode=? ";
			list.add(item.getSqlCode());
		}
		if (StringUtils.isNotBlank(item.getSupplCode())) {
			sql += " and a.SupplCode=? ";
			list.add(item.getSupplCode());
		}
		if (StringUtils.isNotBlank(item.getModel())) {
			sql += " and a.Model like ? ";
			list.add("%" + item.getModel() + "%");
		}
		if (StringUtils.isNotBlank(item.getIsCmpActGift())) {
			sql += " and a.code in ("
					+ " select  a.ItemCode from dbo.tCmpActivityGift a "
					+ " left join tcmpactivity b on b.no=a.No "
					+ " left join tactivity c on c.CmpActNo=b.No "
					+ " where  c.No= ? " + ")";
			list.add(item.getActNo());
		}
		if (item.getItemSize() != null) {
			sql += " and a.ItemSize=? ";
			list.add(item.getItemSize());
		}
		if (StringUtils.isNotBlank(item.getSizeDesc())) {
			sql += " and a.SizeDesc like ? ";
			list.add("%" + item.getSizeDesc() + "%");
		}
		if (StringUtils.isNotBlank(item.getIsCustTypeItem())) {
			if ("1".equals(item.getIsCustTypeItem())) {
				sql += " and a.code in (select itemCode from tCustTypeItem where 1=1 and expired='F') ";
			} else if ("2".equals(item.getIsCustTypeItem())) {
				sql += " and a.code not in (select itemCode from tCustTypeItem where 1=1 and expired='F') ";
			}
		}
		if (StringUtils.isNotBlank(item.getActNo())
				&& !"1".equals(item.getIsCmpActGift())) { // 活动礼品
			sql += " and a.code in(select itemcode from  tCmpActivityGift  where no=?   ";
			list.add(item.getActNo());
			if ("1".equals(item.getGiftAppType())) {
				sql += " and type='1' ) ";
			}
			if ("2".equals(item.getGiftAppType())) {
				sql += " and type in('2','3') ) ";
			}
			if (StringUtils.isBlank(item.getGiftAppType())) {
				sql += " ) ";
			}
		}
		
		if (StringUtils.isBlank(item.getExpired())
				|| "F".equals(item.getExpired())) {
			sql += " and a.Expired='F' ";
		}

		if(StringUtils.isNotBlank(item.getCustCode())){
			sql+=" and (s.isGroup = '1' or exists (select 1 from tCustomer in_a " +
					"			left join tBuilder in_b on in_b.code = in_a.BuilderCode " +
					" 			left Join tRegion in_c on in_c.code= in_b.regionCode " +
					"			where exists ( select 1 from tSupplCmp in_d where in_d.SupplCode = s.Code and in_d.CmpCode = in_c.CmpCode ) " +
					" and in_a.Code = ?  " +
					"  ))";
			list.add(item.getCustCode());
		}
		
		// 判断材料是否可以使用公用材料
//		if(StringUtils.isNotBlank(item.getCustType())){
//			sql+=" and ( (exists(select 1 from tCusttype in_ct where in_ct.Code=? and in_ct.CanUseComItem<>'0') and (a.CustType=? or isnull(a.CustType,'')='' ) )  "
//		        + "   or (exists(select 1 from tCusttype in_ct where in_ct.Code=? and in_ct.CanUseComItem='0' ) and a.CustType=?)  ) ";
//			list.add(item.getCustType());
//			list.add(item.getCustType());
//			list.add(item.getCustType());
//			list.add(item.getCustType());
//		}
		if(StringUtils.isNotBlank(item.getCustType())){	
		    if(StringUtils.isNotBlank(item.getCanUseComItem())){
                if("0".equals(item.getCanUseComItem())){
                    sql+=" and ( a.custType = ? or a.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?) ) ";
                }else{
                    sql+=" and ( (a.custType = ? or a.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?))" +
                            " or ((a.custType = '' or a.custType is null) and (a.CustTypeGroupNo is null or a.CustTypeGroupNo = '')) ) ";
                }
                list.add(item.getCustType());
                list.add(item.getCustType());
            }
		}
		
		if (StringUtils.isNotBlank(item.getIsItemProcess())) { // 加工材料
			sql += " and exists (select 1 from tItemTransform in_a where in_a.itemcode=a.code )   ";
			
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.LastUpdate Desc";
		}
		page.setAutoCount(false);
		return this.findPageBySql(page, sql, list.toArray());
	}
	
    public Page<Map<String, Object>> findSupplCostPageBySql(Page<Map<String, Object>> page,
            Item item) {
        
        List<Object> params = new ArrayList<Object>();
        
        String sql = "select * from ("
                + "select a.PK, a.ItemCode, b.Descr ItemDescr, "
                + "    a.SupplCode, c.Descr SupplDescr, a.Cost, "
                + "    a.LastUpdate, a.LastUpdatedBy, a.Expired, "
                + "    a.ActionLog "
                + "from tSupplCost a "
                + "left join tItem b on a.ItemCode = b.Code "
                + "left join tSupplier c on a.SupplCode = c.Code "
                + "where 1 = 1 ";

        if (StringUtils.isNotBlank(item.getCode())) {
            sql += "and a.ItemCode = ? ";
            params.add(item.getCode());
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.Cost";
        }
        
        return this.findPageBySql(page, sql, params.toArray());
    }

	public Page<Map<String, Object>> findPurchPageBySql(
			Page<Map<String, Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select ";

		if (StringUtils.isNotBlank(item.getPuno())) {
			sql += " pd.UnitPrice  Price,pd.markup pumarkup,pd.beflineprice pubeflineprice ,";
		} else {
			sql += " a.Price,";
		}
		if (StringUtils.isNotBlank(item.getWhCode())) {
			sql += " iwhb.qtyCal whBalQty, ";
		}
		sql += " a.expired,a.Code,a.Descr,a.ItemSize,a.SizeDesc SizeDescr,a.BarCode,a.RemCode,a.ItemType1,i1.Descr ItemType1Descr,"
				+ "a.ItemType2,i2.Descr ItemType2Descr,a.ItemType3,i3.Descr ItemType3Descr,"
				+ "a.SqlCode,b.Descr SqlDescr,a.Uom,u.Descr UomDescr,a.SupplCode,s.Descr SplCodeDescr,"
				+ "a.Model,a.Color,a.Remark,a.CommiType,d.NOTE CommiTypeDescr,a.MarketPrice,a.IsFixPrice,"
				+ "p.Note IsFixPriceDescr,a.Cost,a.AvgCost,a.AllQty,a.ProjectCost,a.ActionLog,dbo.fGetPurQty(a.Code,'') PurQty,dbo.fGetUseQty(a.Code,'','') UseQty"
				+ " from tItem a "
				+ " left outer join tItemType1 i1 on a.ItemType1=i1.Code "
				+ " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
				+ " left outer join tItemtype3 i3 on a.ItemType3=i3.Code "
				+ " left outer join tBrand b on a.SqlCode=b.Code "

				+ " left outer join tXTDM d on a.CommiType=d.CBM and d.ID='COMMITYPE' "
				+ " left outer join tXTDM p on a.IsFixPrice=p.CBM and p.ID='ISFIXPRICE' "
				+ " left outer join tSupplier s on a.SupplCode=s.Code "
				+ " left outer join tUom u on a.Uom = u.Code ";

		if (StringUtils.isNotBlank(item.getWhCode())) {
			sql += " inner join tItemWHBal iwhb on iwhb.itCode = a.code and iwhb.WHCode = ? ";
			list.add(item.getWhCode());
		}
		// + " where 1=1 ";
		if (StringUtils.isNotBlank(item.getPuno())) {
			sql += "LEFT JOIN (select puno,itcode,unitPrice,markup,beflineprice from tPurchaseDetail group by unitPrice,itcode,puno,markup,beflineprice ) pd on pd.ITCode=a.Code where 1=1 "
					+ " and pd.PUNo=? ";
			list.add(item.getPuno());
		} else {
			sql += "where 1=1";
		}
		if (StringUtils.isBlank(item.getExpired())
				|| "F".equals(item.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(item.getCode())) {
			sql += " and a.Code=? ";
			list.add(item.getCode());
		}
		if (StringUtils.isNotBlank(item.getSupplDescr())) {
			sql += " and s.Descr like ? ";
			list.add("%" + item.getSupplDescr() + "%");
		}
		if (StringUtils.isNotBlank(item.getDescr())) {
			sql += " and (a.Descr like ? ";
			list.add("%" + item.getDescr() + "%");
			if ("1".equals(item.getContainCode())) {
				sql += " or a.Code like ? ";
				list.add("%" + item.getDescr() + "%");
			}
			sql += " ) ";
		}
		if (StringUtils.isNotBlank(item.getRemCode())) {
			sql += " and a.RemCode=? ";
			list.add(item.getRemCode());
		}
		if (StringUtils.isNotBlank(item.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(item.getItemType1());
		} else {
			UserContext uc = (UserContext) request.getSession().getAttribute(
					CommonConstant.USER_CONTEXT_KEY);
			String itemRight = "";
			for (String str : uc.getItemRight().trim().split(",")) {
				itemRight += "'" + str + "',";
			}
			sql += " and a.ItemType1 in("
					+ itemRight.substring(0, itemRight.length() - 1) + ") ";
		}
		if (StringUtils.isNotBlank(item.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(item.getItemType2());
		}
		if (StringUtils.isNotBlank(item.getItemType3())) {
			sql += " and a.ItemType3=? ";
			list.add(item.getItemType3());
		}
		if (StringUtils.isNotBlank(item.getBarCode())) {
			sql += " and a.BarCode=? ";
			list.add(item.getBarCode());
		}
		if (StringUtils.isNotBlank(item.getSqlCode())) {
			sql += " and a.SqlCode=? ";
			list.add(item.getSqlCode());
		}
		if (StringUtils.isNotBlank(item.getSupplCode())) {
			sql += " and a.SupplCode=? ";
			list.add(item.getSupplCode());
		}
		if (StringUtils.isNotBlank(item.getModel())) {
			sql += " and a.Model like ? ";
			list.add("%" + item.getModel() + "%");
		}
		if (StringUtils.isNotBlank(item.getIsCmpActGift())) {
			sql += " and a.code in ("
					+ " select  a.ItemCode from dbo.tCmpActivityGift a "
					+ " left join tcmpactivity b on b.no=a.No "
					+ " left join tactivity c on c.CmpActNo=b.No "
					+ " where  c.No= ? " + ")";
			list.add(item.getActNo());
		}
		if (item.getItemSize() != null) {
			sql += " and a.ItemSize=? ";
			list.add(item.getItemSize());
		}
		if (StringUtils.isNotBlank(item.getSizeDesc())) {
			sql += " and a.SizeDesc like ? ";
			list.add("%" + item.getSizeDesc() + "%");
		}
		if (StringUtils.isNotBlank(item.getIsCustTypeItem())) {
			if ("1".equals(item.getIsCustTypeItem())) {
				sql += " and a.code in (select itemCode from tCustTypeItem where 1=1 and expired='F') ";
			} else if ("2".equals(item.getIsCustTypeItem())) {
				sql += " and a.code not in (select itemCode from tCustTypeItem where 1=1 and expired='F') ";
			}
		}
		if (StringUtils.isNotBlank(item.getActNo())
				&& !"1".equals(item.getIsCmpActGift())) { // 活动礼品
			sql += " and a.code in(select itemcode from  tCmpActivityGift  where no=?   ";
			list.add(item.getActNo());
			if ("1".equals(item.getGiftAppType())) {
				sql += " and type='1' ) ";
			}
			if ("2".equals(item.getGiftAppType())) {
				sql += " and type in('2','3') ) ";
			}
			if (StringUtils.isBlank(item.getGiftAppType())) {
				sql += " ) ";
			}
		}

		/*
		 * if (StringUtils.isBlank(item.getExpired()) ||
		 * "F".equals(item.getExpired())) { sql += " and a.Expired='F' "; }
		 */
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += " order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}


	
	/**
	 * 材料信息模块列表
	 * @param page
	 * @param item
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_itemMessage(Page<Map<String,Object>> page, Item item) {
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		List<Object> list = new ArrayList<Object>();
		String sql = " select * from (select  0 as IsSel,a.Code,a.Descr,a.AvgCost,a.AllQty,"
                 + "a.ItemSize,a.SizeDesc,a.IsSetItem,"
                 + " a.BarCode,a.RemCode,a.ItemType1,i1.Descr ItemType1Descr,"
                 + " a.ItemType2,i2.Descr ItemType2Descr,a.ItemType3,i3.Descr ItemType3Descr,"
                 + "a.SqlCode,b.Descr SqlDescr,a.Uom,u.Descr UomDescr,a.SupplCode,s.Descr SplCodeDescr,"
                 + "a.Model,a.Color,a.Remark,"
                 + "a.CommiType,d.NOTE CommiTypeDescr,"
                 + "a.Price,a.MarketPrice,a.ProjectCost,a.IsFixPrice,p.Note IsFixPriceDescr,"
                 + "a.Cost,a.CommiPerc,"
                 + "a.LastUpdate,a.LastUpdatedBy,a.Expired,a.DispSeq,"   
                 + "a.SendType,a.WHCode,a.MinQty,x1.note SendTypeDescr,W.Desc1 WHDescr ,"
                 + "a.ActionLog,a.PerWeight,a.PerNum,a.IsProm,x2.Note IsPromDescr,a.OldPrice,a.OldCost, "
                 + "a.PackageNum,a.Buyer1,e1.NameChi Buyer1Descr,a.Buyer2,e2.NameChi Buyer2Descr,a.CrtDate,a.IsClearInv, "
                 + "x3.NOTE IsClearInvDescr, " 
                 + "a.Volume,a.Size,a.isFee,x4.Note isFeedescr,a.isInv,x5.Note isInvDescr,a.hasSample,x6.NOTE hasSampleDescr,a.PerfPer, "
                 + "case when a.price=0 then 0 else Round((a.price-a.cost-a.price*i2.OtherCostPer_Sale-a.cost*i2.OtherCostPer_Cost)/a.price,4) end ProfitPer "
                 + " ,LampNum,x7.note IsContainTaxDescr,a.InstallFee,x8.note InstallFeeTypedescr,a.InstallFeeType ";
		 //	软装星级 
		 if ("1".equals(item.getSoftLevel())){
				sql += ",case when a.ItemType1 = 'RZ' then c.Level else '' end Level ";
		 }
		 sql += " from tItem a "
                 + "left outer join tItemType1 i1 on a.ItemType1=i1.Code "
                 + "left outer join tItemType2 i2 on a.ItemType2=i2.Code "
                 + "left outer join tItemtype3 i3 on a.ItemType3=i3.Code "
                 + "left outer join tBrand b on a.SqlCode=b.Code "
                 + "left outer join tXTDM d on a.CommiType=d.CBM and d.ID='COMMITYPE' "
                 + "left outer join tXTDM p on a.IsFixPrice=p.CBM and p.ID='ISFIXPRICE' "
                 + "left outer join tSupplier s on a.SupplCode=s.Code "
                 + "left outer join tUom u on a.Uom = u.Code "
                 + "left outer join tXTDM x1 on x1.IBM = a.SendType and x1.id = 'ITEMAPPSENDTYPE' "
                 + "left outer join tXTDM x2 on x2.CBM = a.IsProm and x2.id = 'YESNO' "
                 + "left outer join tWareHouse w on w.Code=a.WHCode  "
                 + "left outer join tEmployee e1 on e1.Number=a.Buyer1 "
                 + "left outer join tEmployee e2 on e2.Number=a.Buyer2 "
                 + "left outer join tXTDM x3 on x3.CBM = a.IsClearInv and x3.id = 'YESNO' "
                 + "left outer join tXTDM x4 on x4.CBM = a.IsFee and x4.id = 'YESNO' "
                 + "left outer join tXTDM x5 on x5.CBM = a.IsInv and x5.id = 'YESNO' "
                 + "left outer join tXTDM x6 on x6.CBM = a.hasSample and x6.id = 'YESNO' "
                 + "left outer join tXTDM x7 on x7.CBM = s.IsContainTax and x7.id = 'YESNO' "
                 + "left outer join tXTDM x8 on x8.CBM = a.InstallFeeType and x8.id = 'INSTALLFEETYPE' ";
         //	软装星级 
		if ("1".equals(item.getSoftLevel())){
			sql+= "left outer join tProfitLevel c on ( case when a.price = 0 then 0 "
				+" else round(( a.price - a.cost - a.price * i2.OtherCostPer_Sale - a.cost * i2.OtherCostPer_Cost ) / a.price, "
				+" 4) end ) >= c.FromProfit and ( case when a.price = 0 then 0 "
				+" else round(( a.price - a.cost - a.price * i2.OtherCostPer_Sale - a.cost * i2.OtherCostPer_Cost ) / a.price,4) "
				+" end ) < c.ToProfit ";
		}             
		sql += " where 1 = 1 ";	
		if (StringUtils.isNotBlank(item.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+item.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(item.getSqlCode())) {
			sql += " and a.SqlCode=? ";
			list.add(item.getSqlCode());
		}
    	if (StringUtils.isNotBlank(item.getCode())) {
			sql += " and a.Code=? ";
			list.add(item.getCode());
		}

    	if (StringUtils.isNotBlank(item.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(item.getItemType1());
		}else{
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";
			}
			sql += " and a.ItemType1 in("+itemRight.substring(0, itemRight.length()-1)+") ";
		}
    	if (StringUtils.isNotBlank(item.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(item.getItemType2());
		}
    	if (StringUtils.isNotBlank(item.getItemType3())) {
			sql += " and a.ItemType3=? ";
			list.add(item.getItemType3());
		}
    	if (StringUtils.isNotBlank(item.getBarCode())) {
			sql += " and a.BarCode=? ";
			list.add(item.getBarCode());
		}
    	if (StringUtils.isNotBlank(item.getModel())) {
			sql += " and a.Model like ? ";
			list.add("%"+item.getModel()+"%");
		}
    	if (StringUtils.isNotBlank(item.getSizeDesc())) {
    		sql += " and a.SizeDesc like  ? ";
			list.add("%"+item.getSizeDesc()+"%");
		}
    
    	if (StringUtils.isNotBlank(item.getSupplCode())) {
			sql += " and a.SupplCode=? ";
			list.add(item.getSupplCode());
		}
    	if (StringUtils.isNotBlank(item.getIsSetItem())) {
			sql += "  and a.IsSetItem=? ";
			list.add(item.getIsSetItem());
		}
    	if (StringUtils.isNotBlank(item.getWhCode())) {
			sql += " and a.WHCode=? ";
			list.add(item.getWhCode());
		}
    	if (StringUtils.isNotBlank(item.getSendType())) {
			sql += " and a.SendType=? ";
			list.add(item.getSendType());
		}
    	if (StringUtils.isNotBlank(item.getHasSample())) {
			sql += " and a.hasSample=? ";
			list.add(item.getHasSample());
		}
    	//只看买手
    	if("1".equals(item.getHasBuyerRight())){
    		sql += " and (a.Buyer1=? or a.Buyer2=?)";
    		list.add(uc.getEmnum());
    		list.add(uc.getEmnum());
    	}else if("1".equals(item.getHasBuyerDeptRight())){
    		sql += " and exists (select 1 from tEmployee e1"
    		    + " inner join tEmployee e2 on e2.Department1=e1.Department1 and (e2.Department2=e1.Department2 or e1.Department2 is null or e1.Department2='')"
    		    + " where e1.Number=? and (a.Buyer1=e2.Number or a.Buyer2=e2.Number))";
    		list.add(uc.getEmnum());
    	}
    	if (StringUtils.isNotBlank(item.getIsPrefPre())) {
    		if("1".equals(item.getIsPrefPre())){
				sql += " and a.PerfPer<1  ";	
			}else{
				sql += " and a.PerfPer>=1  ";	
			}
		}
		if (StringUtils.isBlank(item.getExpired()) || "F".equals(item.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " ) a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " ) a order by a.LastUpdate Desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}


	public Page<Map<String, Object>> findPageBySqlForClient(
			Page<Map<String, Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();

		String sql = "";

		if (StringUtils.isNotBlank(item.getIbdno())) {
			if (("ZC").equals(item.getItemType1())) {
				sql = "SELECT a.*,u.descr uomDescr,c.pk bdpk,c.remarks FROM tItem a left join tuom u on a.uom=u.code "
						+ "inner join tItemBatchDetail c on a.code=c.itCode inner join tItemBatchHeader b on c.ibdno=b.no "
						+ "WHERE b.No=? and b.batchType='3' ";
			} else {
				sql = "SELECT a.*,u.descr uomDescr,0 bdpk,a.remark remarks FROM tItem a left join tuom u on a.uom=u.code "
						+ "inner join tItemBatchDetail c on a.code=c.itCode inner join tItemBatchHeader b on c.ibdno=b.no "
						+ "WHERE b.No=? and b.batchType='3' ";
			}
			list.add(item.getIbdno());
		} else {
			if (("ZC").equals(item.getItemType1())) {
				sql = "SELECT a.*,u.descr uomDescr,c.pk bdpk,c.remarks FROM tItem a left join tuom u on a.uom=u.code "
						+ "inner join tItemBatchDetail c on a.code=c.itCode inner join tItemBatchHeader b on c.ibdno=b.no "
						+ "WHERE b.batchType='3' ";
			} else {
				sql = "SELECT a.*,u.descr uomDescr,0 bdpk,a.remark remarks FROM tItem a left join tuom u on a.uom=u.code WHERE 1=1 ";
			}
		}

		if (StringUtils.isNotBlank(item.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(item.getItemType1());
		}
		if (StringUtils.isNotBlank(item.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%" + item.getDescr() + "%");
		}
		if (StringUtils.isBlank(item.getExpired())
				|| "F".equals(item.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(item.getIsSetItem())
				&& !("JZ").equals(item.getItemType1())) {
			sql += " and a.IsSetItem=? ";
			list.add(item.getIsSetItem());
		}
		if (StringUtils.isNotBlank(item.getIbdno())
				&& !StringUtils.isNotBlank(item.getDescr())) {
			sql += " order by c.dispSeq,a.Descr";
		} else {
			if (StringUtils.isNotBlank(page.getPageOrderBy())) {
				sql += " order by a." + page.getPageOrderBy() + " "
						+ page.getPageOrder();
			} else {
				sql += " order by a.SaleQtySeq desc,a.Descr";
			}
		}

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> findItemById(String id, String custType) {
		String sql = "SELECT (a.AllQty +ISNULL(d.allQty,0) - ISNULL(e.allQty,0)-ISNULL(g.allQty,0)-ISNULL (w.allQty,0))availableNum ,a.code, a.Descr,a.Price,c.Descr uom," 
				+ "a.AllQty,b.Descr brand,a.Model,a.ItemSize,a.SizeDesc,a.Color,a.remark,a.itemType1,h.ItemType12 " ;
		if(StringUtils.isNotBlank(custType)){
			sql +=  " ,isnull(j.CommonPrice,-1) CommonPrice,isnull(j.UpgradedPrice,-1) UpgradedPrice " ;
		}
				
		sql += "from tItem a "
				+ "left join tBrand b on a.SqlCode=b.Code "
				+ "left join tUOM c on a.Uom=c.Code "
				+ "LEFT JOIN (SELECT itCode, SUM(qtyCal) allQty FROM (SELECT b.itCode,  case when a.Type='S' then b.QtyCal-b.ArrivQty else b.QtyCal*-1 end QtyCal "
				+ "from tPurchaseDetail b inner join tPurchase a on a.no=b.puno left outer join tCustomer c on c.code=a.CustCode  "
				+ "where a.status='OPEN' and a.Expired='F'  and a.date>=dateadd(day,-180, getdate()) )c  GROUP BY itCode)d ON d.itCode=a.code "
				+ "LEFT JOIN (SELECT itemcode, SUM(qty) allQty FROM(SELECT b.itemcode, case when a.Type='S' then b.Qty else b.Qty*-1 end Qty "
				+ "from tItemAppDetail b inner join tItemApp a on a.no=b.no "
				+ "left outer join tCustomer c on c.code=a.CustCode "
				+ "left outer join tFixArea f on f.PK=b.FixAreaPK "
				+ "where a.status='OPEN' and a.Expired='F'  and a.date>=dateadd(day,-180, getdate()) )b   GROUP BY itemcode)e ON e.itemcode=a.code "
				+ "LEFT JOIN  (SELECT itCode, SUM(qty) allQty FROM(SELECT b.itcode, case when a.Type='S' then b.Qty else b.Qty*-1 end Qty "
				+ "from tSalesInvoiceDetail b inner join tSalesInvoice a on a.no=b.sino where a.status='OPEN' and a.Expired='F'  and  a.date>=dateadd(day,-180, getdate()) )a GROUP BY itCode)w ON w.itcode=a.code "
				+ "LEFT JOIN (SELECT itemcode,SUM(qty) allQty FROM (SELECT b.itemcode ,case when a.Type='S' then b.Qty else b.Qty*-1 end Qty "
				+ "from tItemAppDetail b inner join tItemApp a on a.no=b.no left outer join tCustomer c on c.code=a.CustCode left outer join tFixArea f on f.PK=b.FixAreaPK  "
				+ "where a.status='CONFIRMED' and a.Expired='F' and  a.date>=dateadd(day,-180, getdate()))d    GROUP BY itemcode )g ON g.itemcode=a.code "
				+ "left join tItemType2 h on h.Code=a.ItemType2 "
				+ "left join tItemType12 i on i.Code=h.ItemType12 ";
		if(StringUtils.isNotBlank(custType)){
			sql += "left join (select top 1 ItemCode,Price UpgradedPrice,case when DiscAmtCalcType='2' then ProjectCost else Price end CommonPrice from tCustTypeItem where ItemCode =? and CustType = ? ) j on a.Code = j.ItemCode ";
		}
		sql += "where a.Code=?";
		List<Map<String, Object>> list = null;
		if(StringUtils.isNotBlank(custType)){
			list = this.findBySql(sql,
					new Object[] { id, custType, id });
		}else{
			list = this.findBySql(sql,
					new Object[] { id });
		}
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<Map<String, Object>> getItemTypeById(String id) {
		String sql = "select b.Code,b.Descr from tItemType1 b where "
				+ "( select a.ItemRight from tCZYBM a  where a.CZYBH=?)  like '%'+rtrim(Code)+'%'";
		return this.findBySql(sql, new Object[] { id });
	}

	public Page<Map<String, Object>> findPageByCzy(
			Page<Map<String, Object>> page, String czy, String batchType,String itemBatchHeaderRemarks) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select  t1.No,t1.BatchType,t1.date,t1.CrtCZY,t1.ItemType1,it1.Descr ItemType1Descr,t1.Remarks IDHRemakrs,"
				+ "isnull(t2.SumPrice,0) SumPrice,isnull(t2.SumQty,0) SumQty,t1.Status,xt1.NOTE StatusDescr,t1.CustCode "
				+ " from tItemBatchHeader t1 "
				+ " left outer join "
				+ "     (select a.IBDNo,sum(b.Price*a.Qty) SumPrice,sum(a.Qty) SumQty from tItemBatchDetail a "
				+ "     left outer join tItem b on a.ITCode=b.Code group by a.IBDNo) t2 on t1.no=t2.ibdno"
				+ " left outer join tItemType1 it1 on it1.code=t1.ItemType1"
				+ " left outer join tXTDM xt1 on xt1.CBM = t1.Status and xt1.ID='ITEMBATCHSTATUS' "
				+ " where t1.CrtCZY=? and t1.BatchType=?";
		list.add(czy);
		list.add(batchType);
		if(StringUtils.isNotBlank(itemBatchHeaderRemarks)){
			sql +=" and t1.Remarks like ? ";
			list.add("%"+itemBatchHeaderRemarks+"%");
		}
		sql	+= " order by t1.date desc";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> findWareHouseItemDetail(WareHouseItemEvt evt) {
		String sql = "SELECT a.Code,a.Descr,(b.QtyCal-ISNULL(c.num,0.00)) availablePutNum FROM   tItem a "
				+ "LEFT JOIN tItemWHBal b ON a.Code=b.ITCode "
				+ "LEFT JOIN  (SELECT  SUM(a.QtyCal) num,b.WHCode,a.ITCode FROM  tWHPosiBal a "
				+ "LEFT JOIN tWareHousePosi b ON b.PK=a.WHPPk "
				+ "WHERE a.whppk IN (SELECT pk FROM dbo.tWareHousePosi WHERE  WHCode=b.WHCode)  GROUP BY b.WHCode,a.ITCode) c ON c.ITCode=a.Code AND c.WHCode=b.WHCode "
				+ "WHERE a.Code=? AND b.WHCode=?";
		List<Map<String, Object>> list = this.findBySql(sql,
				new Object[] { evt.getItCode(), evt.getWhCode() });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	public Page<Map<String, Object>> getItemBySqlCode(
			Page<Map<String, Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.Expired,ltrim(rtrim(a.ItemType2)) ItemType2,ltrim(rtrim(a.SqlCode)) ItemType3, "
				+ " a.Code,a.Descr,b.Descr SqlCodeDescr,f.Descr ItemType3Descr,c.Descr SupplCodeDescr,a.SizeDesc, "
				+ "  a.Color,d.Descr Uomdescr,a.Cost,a.MarketPrice,a.Price,a.IsFixPrice,e.Note IsFixPricedescr,a.Remark," 
				+ " g.Descr ItemType2Descr,a.CommiType,a.model "
				+ " ,a.ProjectCost "
				+ " from tItem a "
				+ " left outer join tBrand b on b.Code=a.SqlCode "
				+ " left outer join tSupplier c on c.Code=a.SupplCode "
				+ "left outer join tUom d on a.Uom=d.Code "
				+ " left outer join tXTDM e on a.IsFixPrice=e.CBM and e.ID='YESNO' "
				+ " left outer join tItemType3 f on a.ItemType3=f.Code "
				+ " left outer join tItemType2 g on a.ItemType2=g.Code "
				+ " left join tcusttype ct on a.CustType=ct.Code "
				+ "where a.Expired='F'  ";
		
		if (StringUtils.isNotBlank(item.getItemType1())) {
			sql += " and ltrim(rtrim(a.ItemType1))=? ";
			list.add(item.getItemType1());
		}
		if (StringUtils.isNotBlank(item.getItemType2())) {
			sql += " and ltrim(rtrim(a.ItemType2))=? ";
			list.add(item.getItemType2());
		}
		if (StringUtils.isNotBlank(item.getSqlCode())) {
			sql += " and a.SqlCode=? ";
			list.add(item.getSqlCode().trim());
			// sql+=" order by a.DispSeq,a.Descr DESC ";
			// return this.findPageBySql(page, sql, list.toArray());
		}
		if (StringUtils.isNotBlank(item.getCode())) {
			sql += "and a.Code=? ";
			list.add(item.getCode().trim());
		}
		if (StringUtils.isNotBlank(item.getDescr())) {
			sql += "and a.Descr like ? ";
			list.add("%" + item.getDescr() + "%");
		}
		if(StringUtils.isNotBlank(item.getCustCode())){
			sql+=" and (C.isGroup = '1' or exists (select 1 from tCustomer in_a " +
					"			left join tBuilder in_b on in_b.code = in_a.BuilderCode " +
					" 			left Join tRegion in_c on in_c.code= in_b.regionCode " +
					"			where exists ( select 1 from tSupplCmp in_d where in_d.SupplCode = c.Code and in_d.CmpCode = in_c.CmpCode ) " +
					" and in_a.Code = ?  " +
					"  ))";
			list.add(item.getCustCode());
		}
		// 判断材料是否对应客户类型
//		if(StringUtils.isNotBlank(item.getCustType())){
//			sql+=" and ( (exists(select 1 from tCusttype in_ct where in_ct.Code=? and in_ct.CanUseComItem<>'0') and (a.CustType=? or isnull(a.CustType,'')='' ) )  "
//		        + "   or (exists(select 1 from tCusttype in_ct where in_ct.Code=? and in_ct.CanUseComItem='0' ) and a.CustType=?)  ) ";
//			list.add(item.getCustType());
//			list.add(item.getCustType());
//			list.add(item.getCustType());
//			list.add(item.getCustType());
//		}
		if(StringUtils.isNotBlank(item.getCustType())){
			if(StringUtils.isNotBlank(item.getCanUseComItem())){
                if("0".equals(item.getCanUseComItem())){
                    sql+=" and ( a.custType = ? or a.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?) ) ";
                }else{
                    sql+=" and ( (a.custType = ? or a.CustTypeGroupNo in (select No from tCustTypeGroupDt where CustType = ?))" +
                            " or ((a.custType = '' or a.custType is null) and (a.CustTypeGroupNo is null or a.CustTypeGroupNo = '')) ) ";
                }
                list.add(item.getCustType());
                list.add(item.getCustType());
            }
		}
		sql += " order by a.Descr  ";
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Page<Map<String, Object>> getItemBySqlCode2(
			Page<Map<String, Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.Expired,ltrim(rtrim(a.ItemType2)) ItemType2,ltrim(rtrim(a.SqlCode)) ItemType3, "
				+ " a.Code itcode,a.Descr itcodedescr,b.Descr SqlCodeDescr,f.Descr ItemType3Descr,c.Descr SupplCodeDescr,a.SizeDesc, "
				+ "  a.Color,d.Descr Uomdescr,a.Cost,a.MarketPrice,a.Price,a.IsFixPrice,e.Note IsFixPricedescr,a.Remark remarks,"
				+ " g.Descr ItemType2Descr,a.CommiType,a.model,h.desc1 whdescr "
				+ " from tItem a "
				+ " left outer join tBrand b on b.Code=a.SqlCode "
				+ " left outer join tSupplier c on c.Code=a.SupplCode "
				+ " left outer join tUom d on a.Uom=d.Code "
				+ " left outer join tXTDM e on a.IsFixPrice=e.CBM and e.ID='YESNO' "
				+ " left outer join tItemType3 f on a.ItemType3=f.Code "
				+ " left outer join tItemType2 g on a.ItemType2=g.Code "
				+ " left outer join tWareHouse h on a.WHCode=h.Code "
				+ " left join tcusttype ct on a.CustType=ct.Code "
				+ " where a.Expired='F'  ";
		if ("false".equals(item.getIsAdded()) && item.getItcodes() != null
				&& StringUtils.isNotBlank(item.getItcodes())) {
			sql += " and a.Code not in ("
					+ item.getItcodes().replaceAll("              ", "") + ")";
		}
		if (StringUtils.isNotBlank(item.getItemType1())) {
			sql += " and ltrim(rtrim(a.ItemType1))=? ";
			list.add(item.getItemType1());
		}
		if (StringUtils.isNotBlank(item.getItemType2())) {
			sql += " and ltrim(rtrim(a.ItemType2))=? ";
			list.add(item.getItemType2());
		}
		if (StringUtils.isNotBlank(item.getSqlCode())) {
			sql += " and a.SqlCode=? ";
			list.add(item.getSqlCode().trim());
			// sql+=" order by a.DispSeq,a.Descr DESC ";
			// return this.findPageBySql(page, sql, list.toArray());
		}
		if (StringUtils.isNotBlank(item.getCode())) {
			sql += "and a.Code=? ";
			list.add(item.getCode().trim());
		}
		if (StringUtils.isNotBlank(item.getDescr())) {
			sql += "and a.Descr like ? ";
			list.add("%" + item.getDescr() + "%");
		}
		// 判断材料是否对应客户类型
//		if(StringUtils.isNotBlank(item.getCustType())){
//			sql+=" and ( (exists(select 1 from tCusttype in_ct where in_ct.Code=? and in_ct.CanUseComItem<>'0') and (a.CustType=? or isnull(a.CustType,'')='' ) )  "
//		        + "   or (exists(select 1 from tCusttype in_ct where in_ct.Code=? and in_ct.CanUseComItem='0' ) and a.CustType=?)  ) ";
//			list.add(item.getCustType());
//			list.add(item.getCustType());
//			list.add(item.getCustType());
//			list.add(item.getCustType());
//		}
		if(StringUtils.isNotBlank(item.getCustType())){	
			if(StringUtils.isNotBlank(item.getCanUseComItem()) && "0".equals(item.getCanUseComItem())){
					sql+=" and a.CustType = ? ";
					list.add(item.getCustType());
			}else{
				sql+=" and (a.CustType = ? or a.CustType = '' or a.CustType is null) ";
				list.add(item.getCustType());	
			}
		}
		sql += " order by a.Descr  ";

		return this.findPageBySql(page, sql, list.toArray());
	}

	public Item getItemByCondition(Item item) {
		List<Object> list = new ArrayList<Object>();
		list.add(item.getCode());
		String hql = " from Item where code=? ";
		if (StringUtils.isNotBlank(item.getItemType1())) {
			hql += " and itemType1=? ";
			list.add(item.getItemType1());
		}
		if (StringUtils.isBlank(item.getExpired())
				|| "F".equals(item.getExpired())) {
			hql += " and expired='F' ";
		}
		List<Map<String, Object>> list1 = this.find(hql, list.toArray());
		if (list1 != null && list1.size() > 0) {
			return (Item) list1.get(0);
		}
		return null;
	}
	
	/**
	 * 添加材料存储过程
	 * @param item
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result saveForProc(Item item) {
		Assert.notNull(item);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn
					.prepareCall("{Call pClxx(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, item.getM_umState());
			call.setString(2, item.getCode());
			call.setString(3, item.getDescr());
			call.setString(4, item.getRemCode());
			call.setString(5, item.getIsSetItem());
			call.setString(6, item.getItemType1());
			call.setString(7, item.getItemType2());
			call.setString(8, item.getItemType3());
			call.setString(9, item.getBarCode());
			call.setString(10, item.getSqlCode());
			call.setString(11, item.getSupplCode());
			call.setString(12, item.getSizeDesc());
			call.setString(13, item.getColor());
			call.setString(14, item.getUom());
			call.setString(15, item.getModel());
			call.setDouble(16, item.getCost() == null ? 0 : item.getCost()); 
			call.setDouble(17, item.getMarketPrice() == null ? 0 : item.getMarketPrice()); 
			call.setDouble(18, item.getPrice() == null ? 0 : item.getPrice()); 
			call.setDouble(19, item.getProjectCost() == null ? 0 : item.getProjectCost()); 
			call.setString(20, item.getIsFixPrice());
			call.setString(21, item.getCommiType());
			call.setDouble(22, item.getCommiPerc() == null ? 0 : item.getCommiPerc()); 
			call.setString(23, item.getRemark());
			call.setString(24, item.getLastUpdatedBy());
			call.setString(25, item.getExpired());
			call.setDouble(26, item.getAvgCost() == null ? 0 : item.getAvgCost()); 
			call.setDouble(27, item.getOldCost() == null ? 0 : item.getOldCost());
			/*@OldAvgCost money,--原移动平均成本，避免主界面没有刷新而造成修改时数量不是真正的数据。*/
			call.setInt(28, item.getDispSeq());
			call.setString(29, item.getSendType());
			call.setString(30, item.getWhCode());
			call.setDouble(31, item.getMinQty() == null ? 0 : item.getMinQty());
			call.setDouble(32, item.getPerWeight() == null ? 0 : item.getPerWeight());
			call.setDouble(33, item.getPerNum() == null ? 0 : item.getPerNum());
			call.setInt(34, item.getPackageNum() == null ? 1 : item.getPackageNum());
			call.setString(35, item.getBuyer1());
			call.setString(36, item.getBuyer2());
			call.setTimestamp(37, item.getCrtDate() == null ? null : new Timestamp(
					 item.getCrtDate().getTime()));
			call.setString(38,item.getIsClearInv());
			call.setDouble(39, item.getSize() == null ? 0 : item.getSize());
			call.setString(40,item.getIsFee());
			call.setString(41,item.getIsInv());
			call.setString(42,item.getHasSample());
			call.setDouble(43, item.getwHFee() == null ? 0 : item.getwHFee()); 
			call.setDouble(44, item.getLampNum() == null ? 0 : item.getLampNum());
			call.setString(45, item.getInstallFeeType()); 
			call.setDouble(46, item.getInstallFee() == null ? 0 : item.getInstallFee());
			call.registerOutParameter(47, Types.INTEGER);
			call.registerOutParameter(48, Types.NVARCHAR);
			call.setString(49,item.getWhFeeType());
			call.setString(50,item.getCustType());
			call.setString(51, item.getSupplCostXml());
			call.setString(52, item.getCustTypeGroupNo());
			call.setString(53, item.getIsActualItem());
			call.setString(54, "1".equals(item.getIsActualItem()) ? "" : item.getWareHouseItemCode());
			call.setDouble(55, item.getAdditionalCost() == null ? 0.0 : item.getAdditionalCost());
			call.execute();
			result.setCode(String.valueOf(call.getInt(47)));
			result.setInfo(call.getString(48));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public boolean hasQzbyXTCS(String sItemtype2) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select 1 from  tXTCS where id='Furniture' and qz like ?";
		list.add("%"+sItemtype2+"%");
		List<Map<String,Object>> list1= this.findBySql(sql, list.toArray());
		if (list1!=null && list1.size()>0){
			return true;
		}else{
			return false;
		}
	}
	public boolean isExistDescr(Item item) {
		List<Object> list = new ArrayList<Object>();
		String sql="";
		if("A".equals(item.getM_umState())||"C".equals(item.getM_umState())){
			sql = "select 1 from tItem where Expired='F' and Descr =? and SupplCode=?";
			list.add(item.getDescr());
			list.add(item.getSupplCode());
		}else if("M".equals(item.getM_umState())){
			sql = "select 1 from tItem where Expired='F' and Descr =? and SupplCode=? and Code<>?";
			list.add(item.getDescr());
			list.add(item.getSupplCode());
			list.add(item.getCode());
		}else if("I".equals(item.getM_umState())){ //导入
			sql = "select 1 from tItem where Expired='F' and  ((Descr =? and SupplCode=?) "
				+ " or (ItemType2=? and ItemType3=? and SqlCode=? and  Model=? and SizeDesc=?))";
			list.add(item.getDescr());
			list.add(item.getSupplCode());
			list.add(item.getItemType2());
			list.add(item.getItemType3());
			list.add(item.getSqlCode());
			list.add(item.getModel());
			list.add(item.getSizeDesc());	
		}else{
			return false;
		}
		List<Map<String,Object>> list1= this.findBySql(sql, list.toArray());
		if (list1!=null && list1.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 材料信息--修改预算价格数据列表
	 * 
	 * @param page
	 * @param item
	 * @return
	 */
	public Page<Map<String,Object>>getPageBySql_updatePrePrice(Page<Map<String,Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select b.pk,a.Address,b.Qty, b.UnitPrice, b.BefLineAmount, b.Markup, b.ProcessCost, b.LineAmount,c.Descr FixAreaDescr, (b.LineAmount-isnull(b.ProcessCost,0)) TmpLineAmount "
                 + " from tItemPlan b"
                 + " inner join tCustomer a "
                 + " on a.Code=b.CustCode and b.ItemCode=? "
                 + " left outer join tFixArea c on c.pk=b.FixAreaPK "
                 + " where a.Status in ('1','2','3') and b.IsOutSet='1' " ;

		if (StringUtils.isNotBlank(item.getCode())) {
			list.add(item.getCode());
		}
		sql += " order by  b.CustCode,b.DispSeq";
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 材料信息--批量修改数据列表
	 * 
	 * @param page
	 * @param item
	 * @return
	 */
	public Page<Map<String,Object>>getPageBySql_updateBatch(Page<Map<String,Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();
		UserContext uc=(UserContext) request.getSession().getAttribute(CommonConstant.USER_CONTEXT_KEY);
		String sql = "select a.Code,a.Descr,a.AvgCost,a.AllQty,a.ItemSize,a.SizeDesc, "
                 + " a.BarCode,a.RemCode,a.ItemType1,i1.Descr ItemType1Descr,"
				 + " a.ItemType2,i2.Descr ItemType2Descr,a.ItemType3,i3.Descr ItemType3Descr,"
                 + " a.SqlCode,b.Descr SqlDescr,a.Uom,u.Descr UomDescr,a.SupplCode,s.Descr SplCodeDescr,"
                 + " a.Model,a.Color,a.Remark,a.CommiType,d.NOTE CommiTypeDescr,"
                 + " a.Price,a.MarketPrice,a.IsFixPrice,p.Note IsFixPriceDescr,a.Cost,a.CommiPerc,"
                 + " a.LastUpdate,a.LastUpdatedBy,a.Expired,(case when a.Expired='F' then '否' else '是' end) ExpiredDescr,"
                 + " a.ActionLog from tItem a left outer join tItemType1 i1 on a.ItemType1=i1.Code "
                 + " left outer join tItemType2 i2 on a.ItemType2=i2.Code "
                 + " left outer join tItemtype3 i3 on a.ItemType3=i3.Code "
                 + " left outer join tBrand b on a.SqlCode=b.Code "
                 + " left outer join tXTDM d on a.CommiType=d.CBM and d.ID='COMMITYPE' "
                 + " left outer join tXTDM p on a.IsFixPrice=p.CBM and p.ID='ISFIXPRICE' "
                 + " left outer join tSupplier s on a.SupplCode=s.Code "
                 + " left outer join tUom u on a.Uom = u.Code  where 1 = 1 ";
		if (StringUtils.isNotBlank(item.getDescr())) {
			sql += " and a.Descr like ? ";
			list.add("%"+item.getDescr()+"%");
		}
		if (StringUtils.isNotBlank(item.getSqlCode())) {
			sql += " and a.SqlCode=? ";
			list.add(item.getSqlCode());
		}
    	if (StringUtils.isNotBlank(item.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(item.getItemType1());
		}else{
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";
			}
			sql += " and a.ItemType1 in("+itemRight.substring(0, itemRight.length()-1)+") ";
		}
    	if (StringUtils.isNotBlank(item.getItemType2())) {
			sql += " and a.ItemType2=? ";
			list.add(item.getItemType2());
		}
    	if (StringUtils.isNotBlank(item.getItemType3())) {
			sql += " and a.ItemType3=? ";
			list.add(item.getItemType3());
		}
    	if (StringUtils.isNotBlank(item.getBarCode())) {
			sql += " and a.BarCode=? ";
			list.add(item.getBarCode());
		}
    
    	if (StringUtils.isNotBlank(item.getSupplCode())) {
			sql += " and a.SupplCode=? ";
			list.add(item.getSupplCode());
		}
    	
		if (StringUtils.isBlank(item.getExpired()) || "F".equals(item.getExpired())) {
			sql += " and a.Expired='F' ";
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate Desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	@SuppressWarnings("deprecation")
	public Result doUpdatePrePrice (Item item) {
		Assert.notNull(item);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pUpItemPrice(?,?,?,?,?)}");
			call.setString(1, item.getCode());
			call.setDouble(2, item.getPrice());
			call.setString(3, item.getLastUpdatedBy());
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(4)));
			result.setInfo(call.getString(5));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public double getPerfPer(String itemType2, double cost, double price) {
		Double dOtherCostPer_Sale=0.00,dOtherCostPer_Cost=0.00;
		String sItemType12="";
		Double  dProfitPer=0.00; //毛利率
		String sql = " select OtherCostPer_Sale, OtherCostPer_Cost, ItemType12 from tItemType2 where Code=?";
		List<Map<String, Object>> list =  this.findBySql(sql, new Object[]{itemType2});
		if (list!=null && list.size()>0){
			dOtherCostPer_Sale=(Double) list.get(0).get("OtherCostPer_Sale");
			dOtherCostPer_Cost=(Double) list.get(0).get("OtherCostPer_Cost");
			sItemType12=(String) list.get(0).get("ItemType12");
			if(price==0.00){
				 dProfitPer=0.00;
			}else{
				
				dProfitPer= (price-cost-price*dOtherCostPer_Sale-cost*dOtherCostPer_Cost)/price;
				
				DecimalFormat df = new DecimalFormat("#.####");    
				dProfitPer = Double.parseDouble(df.format(dProfitPer));  
			}
			/*String sql2 = " select a.PerfPer from tProfitPerf a where a.ItemType12=? "
					      +" and (a.FromProfit is null or a.FromProfit<=? ) "
						  + " and (a.ToProfit is null or a.ToProfit>?) ";
			List<Map<String, Object>> list2 = this.findBySql(sql2, new Object[]{sItemType12,dProfitPer,dProfitPer});
			if  (list2!=null && list2.size()>0){
				return list2.get(0);
			}
			else{
				return null;
			}	*/
			return dProfitPer;
		}else{
			return 0.0;
		}
	}
	
	public boolean hasItemPlan(String itemCode) {
		String sql = "select top 1 ItemCode from tItemPlan where ItemCode=?";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemCode});
		if (list!=null && list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 材料批量导入调过程
	 * @param custTypeItem
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveBatch (Item item) {
		Assert.notNull(item);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pClxx_import(?,?,?,?)}");
			call.setString(1, item.getLastUpdatedBy());
			call.setString(2, item.getDetailXml());
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
	public void doUpdateBatch(Item item){
		StringBuilder sbCode = new StringBuilder();
		for(String str:item.getCode().trim().split(",")){
			sbCode.append( "'"+str+"',");
		}
		String sCode=sbCode.toString();
		sCode=sCode.substring(0, sCode.length()-1);
		String sql="update tItem set  lastUpdatedBy =?, LastUpdate=?, ActionLog='EDIT' ";
		if (StringUtils.isNotBlank(item.getExpired())) {
			sql +=" ,Expired='"+item.getExpired()+"'" ;	
		}
		if (StringUtils.isNotBlank(item.getCommiType())) {
			sql +=" ,CommiType='"+item.getCommiType()+"'" ;
			
		}
		if (item.getCommiPerc() != null) {
			sql +=" ,CommiPerc="+item.getCommiPerc() ;	
		}
		sql+=" where Code in("+sCode+") ";
		
		this.executeUpdateBySql(sql, new Object[]{item.getLastUpdatedBy(),item.getLastUpdate()});				
	}	
	/**
	 * 删除图片
	 * @param custTypeItem
	 * @return
	 */
	public void doDelPicture(String photoName ){	
			String sql=" delete from tItemPic where PhotoName= ? " ;
			
			this.executeUpdateBySql(sql, new Object[]{photoName});
	
	}
	public List<Map<String, Object>> getAlgorithmByCode(Item item) {
		String sql = " select rtrim(a.Code) + ' ' + a.Descr fd, a.Code,a.isCalCutFee,a.Descr  from tAlgorithm a "
				+ " where exists ( "
				+ " select * from tAlgorithmItem ai "
				+ " inner join tItem i on i.Code= ?  "
				+ " where ai.Code=a.Code  and (ai.ItemType2=i.ItemType2 or ai.ItemType2 is null or ai.ItemType2='') "
				+ " and (ai.ItemType3=i.ItemType3 or ai.ItemType3 is null or ai.ItemType3='') "
				+ " and (ai.Uom=i.Uom or ai.Uom is null or ai.Uom='') )";
		List<Map<String, Object>> list = this.findListBySql(sql,
				new Object[] { item.getCode()});

		return list;
	}
	public Map<String, Object> getLabelContent(String code) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String sql = " select a.Code, a.Descr, isnull(b.Descr,'') BrandDescr, round(a.Price,2) Price,convert(varchar(100), getdate(), 23) Date, " 
				+" round(a.MarketPrice,2) MarketPrice, a.Code + case when a.ItemType1 = 'RZ' then c.Level else '' end Level,isnull(u.Descr,0) UomDescr, "
				+" isnull(a.SizeDesc,'') SizeDesc, isnull(a.Model,'')Model"
				+" from  tItem a "
				+" left outer join tBrand b on a.SqlCode = b.Code "
				+" left outer join tItemType2 i2 on a.ItemType2 = i2.Code "
				+" left outer join tProfitLevel c on ( case when a.price = 0 then 0 "
				+" else round(( a.price - a.cost - a.price * i2.OtherCostPer_Sale - a.cost * i2.OtherCostPer_Cost ) / a.price, "
				+" 4) end ) >= c.FromProfit and ( case when a.price = 0 then 0 "
				+" else round(( a.price - a.cost - a.price * i2.OtherCostPer_Sale - a.cost * i2.OtherCostPer_Cost ) / a.price,4) "
				+" end ) < c.ToProfit "
				+" left outer join tUom u on a.Uom = u.Code "
				+" where a.code=? " ;  
		list = this.findListBySql(sql, new Object[] {code});
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	/**
	 * 判断是否集团供应商或者供应公司为客户所在公司的材料
	 * @param code 材料编号 
	 * @param custCode 客户编号
	 * @return
	 */
	public boolean isSplCompanyItem(String itemCode,String custCode) {
		String sql = " select 1 from tItem a " 
				 + " inner join tSupplier b on a.SupplCode=b.Code " 
				 + " left join tSupplCmp c on c.SupplCode=b.Code "
				 + " where a.code=? and (b.IsGroup='1' or c.CmpCode=(select CmpCode from  tCustomer in_a "
				 + " left join tBuilder in_b on in_b.code = in_a.BuilderCode "
				 + " left join tRegion in_c on in_c.code = in_b.regionCode "
				 + " where in_a.code=? ) ) ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{itemCode ,custCode});
		if (list!=null && list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	public Page<Map<String, Object>> getCustCodeList(Page<Map<String, Object>> page, UserContext uc, String searchAddress) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select Code CustCode,Address from tCustomer a "
				+ " where a.Status='4' and a.Expired = 'F' ";
		sql += " and " + SqlUtil.getCustRight(uc, "a", 0);
		if(StringUtils.isNotBlank(searchAddress)){
			sql +=" and a.Address like ? ";
			list.add("%"+searchAddress+"%");
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getPrePlanAreaList(Page<Map<String, Object>> page,String custCode) {
		String sql = "select a.PK,a.FixAreaType,a.Descr from tPrePlanArea a " 
				+" where a.CustCode=? and FixAreaType not in ('9','10','11','12') and a.Expired='F' ";
		return this.findPageBySql(page, sql, new Object[] {custCode});
	}
	
	public Page<Map<String, Object>> getFixAreaTypeItemType12AttrList(Page<Map<String, Object>> page,String fixAreaType,String itemType12) {
		String sql = "select a.PK,a.Attr,a.Attr AttrString from tFixAreaTypeItemType12Attr a " 
				+" where a.FixAreaType=? and a.ItemType12=? and a.Expired='F'";
		return this.findPageBySql(page, sql, new Object[] {fixAreaType,itemType12});
	}
	
	public Page<Map<String, Object>> getItemType12List(Page<Map<String, Object>> page,String custCode,String itemType1) {
		String sql = "select d.Code,d.Descr from tItemReq a "
				 +" left join tItem b on a.ItemCode = b.Code "
				 +" left join tItemType2 c on b.ItemType2 = c.Code "
				 +" left join tItemType12 d on c.ItemType12 = d.Code "
				 +" where a.CustCode=? and a.ItemType1= ? "
				 +" group by d.Code,d.Descr";
		return this.findPageBySql(page, sql, new Object[] {custCode,itemType1});
	}
	
	public Page<Map<String, Object>> getMaterialOldItemList(Page<Map<String, Object>> page,ItemBatchHeaderQueryEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.PK OldReqPk,a.ItemCode OldItemCode,c.Descr OldItemDescr,b.Descr FixAreaDescr,b.PrePlanAreaPK, "
				+" e.Descr PrePlanAreaDescr,c.Cost Price,f.Descr UOM,e.FixAreaType,d.ItemType12"
				+" from tItemReq a "
				+" left join tFixArea b on a.FixAreaPK = b.PK "
				+" left join tItem c on a.ItemCode = c.Code "
				+" left join tItemType2 d on c.ItemType2 = d.Code "
				+" left join tPrePlanArea e on e.PK = b.PrePlanAreaPK "
				+" left join tUOM f on c.UOM = f.Code "
				+" where a.Qty>0 and a.SendQty<=0 and a.CustCode=? and a.itemType1 = ? ";
		list.add(evt.getCustCode());
		list.add(evt.getItemType1());
		if(StringUtils.isNotBlank(evt.getItemType12())){
			sql+=" and d.ItemType12 = ? ";
			list.add(evt.getItemType12());
		}
		if(evt.getPrePlanAreaPk()!=null){
			sql+=" and b.PrePlanAreaPK = ? ";
			list.add(evt.getPrePlanAreaPk());
		}
		if(!evt.isShowReplaced()&&StringUtils.isNotBlank(evt.getAlreadyReplaceStr())){
			String str = SqlUtil.resetIntStatus(evt.getAlreadyReplaceStr());
			sql += " and a.PK not in (" + str + ")";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public Map<String, Object> getSameItemNum(String custCode,String id, String alreadyReplaceStr) {
		String sql = "select isnull(count(*),0) SameItemNum from tItemReq where CustCode=? and ItemCode=? and Qty>0";
		if(StringUtils.isNotBlank(alreadyReplaceStr)) {
			sql += " and PK not in (" + alreadyReplaceStr + ")";
		}
		List<Map<String, Object>> list = this.findBySql(sql,new Object[] { custCode,id });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public Result saveCustSelection(ItemBatchHeader itemBatchHeader, String xml) {
		Assert.notNull(itemBatchHeader);
		Result result = new Result();
		Connection conn = null; 
		CallableStatement call = null;
		try{
			HibernateTemplate hibernateTemplate = SpringContextHolder.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pSaveCustSelection_forXml(?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, itemBatchHeader.getM_umState());
			call.setString(2, itemBatchHeader.getNo());
			call.setString(3, itemBatchHeader.getCustCode());
			call.setString(4, itemBatchHeader.getRemarks());
			call.setString(5, itemBatchHeader.getItemType1());
			call.setString(6, itemBatchHeader.getCrtCzy());
			call.setString(7, itemBatchHeader.getOtherRemarks());
			call.setString(8, itemBatchHeader.getStatus());
			call.setString(9, xml);
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.NVARCHAR);
			call.execute();
			result.setCode(String.valueOf(call.getInt(10)));
			result.setInfo(call.getString(11));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(null, call, conn);
		}
		return result;
	}
	
	public Map<String, Object> getItemMainPicPhotoUrl(String itemCode) {
		String sql = "select PicFile PhotoUrl from tItemPic where PicType ='1' and ItemCode=? ";
		List<Map<String, Object>> list = this.findBySql(sql,new Object[] { itemCode });
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public Page<Map<String, Object>> getItemType2List(Page<Map<String, Object>> page,String itemType1) {
		String sql = " select a.Code,a.Descr from tItemType2 a " 
				+" left join tItemType1 b on a.ItemType1=b.Code "
				+" where b.Code = ? ";
		return this.findPageBySql(page, sql, new Object[] {itemType1});
	}
	
	public Page<Map<String, Object>> getMaterialNewItemList(Page<Map<String, Object>> page,ItemBatchHeaderQueryEvt evt,String custType, String canUseComItem) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code ItemCode,a.Descr ItemDescr from tItem a "
				+" where a.Expired='F' and a.ItemType1 = ?  ";
		list.add(evt.getItemType1());
		if(StringUtils.isNotBlank(evt.getItemType2())){
			sql+=" and a.ItemType2 = ? ";
			list.add(evt.getItemType2());
		}
		if(StringUtils.isNotBlank(evt.getItemDescr())){
			sql+=" and (a.Descr like ? or a.Code = ? )";
			list.add("%"+evt.getItemDescr()+"%");
			list.add(evt.getItemDescr());
		}
		if(StringUtils.isNotBlank(custType)){
			if("0".equals(canUseComItem)){
				sql += " and (a.CustType = ? or a.CustTypeGroupNo in (select in_a.No from tCustTypeGroupDt in_a where in_a.CustType = ? ))";
			}else{
				sql += " and (a.CustType = ?  or a.CustTypeGroupNo in (select in_a.No from tCustTypeGroupDt in_a where in_a.CustType = ? ) "
					+"or ((a.CustType is null or a.CustType = '') and (a.CustTypeGroupNo is null or a.CustTypeGroupNo = '')) ) ";
			}
			list.add(custType);
			list.add(custType);
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getSameItemList(Page<Map<String, Object>> page,ItemBatchHeaderQueryEvt evt) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.PK OldReqPk,a.ItemCode OldItemCode,c.Descr OldItemDescr,b.Descr FixAreaDescr,b.PrePlanAreaPK, "
				+" e.Descr PrePlanAreaDescr,c.Cost Price,f.Descr UOM,e.FixAreaType,d.ItemType12"
				+" from tItemReq a "
				+" left join tFixArea b on a.FixAreaPK = b.PK "
				+" left join tItem c on a.ItemCode = c.Code "
				+" left join tItemType2 d on c.ItemType2 = d.Code "
				+" left join tPrePlanArea e on e.PK = b.PrePlanAreaPK "
				+" left join tUOM f on c.UOM = f.Code "
				+" where a.Qty>0 and a.CustCode=? and a.ItemCode = ? ";
		list.add(evt.getCustCode());
		list.add(evt.getItemCode());
		if(StringUtils.isNotBlank(evt.getAlreadyReplaceStr())) {
			sql += " and a.PK not in (" + evt.getAlreadyReplaceStr() + ")";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	public List<Map<String, Object>> getAuthItemType2ByItemType1(Item item) {
		List<Object> params = new ArrayList<Object>();
		String sql = " select rtrim(code) id, rtrim(code) + ' ' + descr name from tItemType2 where itemType1 = ? ";
		params.add(item.getItemType1());
		if(StringUtils.isNotBlank(item.getExpired())){
			sql += " and Expired=? ";
			params.add(item.getExpired());
		}
		List<Map<String, Object>> list = this.findBySql(sql, params.toArray());
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	
	public Page<Map<String, Object>> getItemType12List(Page<Map<String, Object>> page, String itemType1) {
		String sql = " select a.Code,a.Descr from tItemType12 a "
				 +" where a.ItemType1 = ? ";
		return this.findPageBySql(page, sql, new Object[] {itemType1});
	}
	
	public List<Map<String, Object>> getCustTypeItemList(String itemCode) {
		String sql = " select b.Desc1 CustTypeDescr,a.Price UpgradedPrice from tCustTypeItem a "
				+" left join tCusttype b on a.CustType = b.Code "
				+" where a.Expired = 'F' and b.Expired = 'F' and a.ItemCode = ? ";
		return this.findBySql(sql, new Object[] { itemCode });
	}
	
	public Page<Map<String, Object>> getMaterialList(Page<Map<String, Object>> page, String searchText, String itemType1) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select a.Code ItemCode, a.Descr ItemDescr "
				+" from  tItem a "
				+" where a.Expired = 'F' ";
		
		if(StringUtils.isNotBlank(searchText)){
			sql +=" and a.Code like ? or a.Descr like ? ";
			list.add("%"+searchText+"%");
			list.add("%"+searchText+"%");
		}
		if(StringUtils.isNotBlank(itemType1)){
			sql +=" and a.ItemType1 = ? ";
			list.add(itemType1);
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public boolean canScanItem(String itemCode, String custType, String canUseComItem) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code ItemCode,a.Descr ItemDescr from tItem a "
				+" where a.Expired='F' and a.Code = ?  ";
			if("0".equals(canUseComItem)){
				sql += " and (a.CustType = ? or a.CustTypeGroupNo in (select in_a.No from tCustTypeGroupDt in_a where in_a.CustType = ? ))";
			}else{
				sql += " and (a.CustType = ?  or a.CustTypeGroupNo in (select in_a.No from tCustTypeGroupDt in_a where in_a.CustType = ? ) "
					+"or ((a.CustType is null or a.CustType = '') and (a.CustTypeGroupNo is null or a.CustTypeGroupNo = '')) ) ";
			}
			list.add(itemCode);
			list.add(custType);
			list.add(custType);
			
		List<Map<String,Object>> list1 = this.findBySql(sql, list.toArray());
		if (list1!=null && list1.size()>0){
			return true;
		}else{
			return false;
		}
	}

	public Page<Map<String, Object>> findSuggestPageBySql(Page<Map<String, Object>> page, Item item) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Price,a.expired,a.Code,a.Descr,a.ItemSize,a.SizeDesc SizeDescr,a.BarCode,a.RemCode,a.ItemType1,i1.Descr ItemType1Descr,"+
				 " a.ItemType2,i2.Descr ItemType2Descr,a.ItemType3,i3.Descr ItemType3Descr,a.oldcost,a.oldprice,"+
				 " a.SqlCode,b.Descr SqlDescr,a.Uom,u.Descr UomDescr,a.SupplCode,s.Descr SplCodeDescr,"+
				 " a.Model,a.Color,a.Remark,a.CommiType,d.NOTE CommiTypeDescr,a.MarketPrice,a.IsFixPrice,"+
				 " p.Note IsFixPriceDescr,a.Cost,a.AvgCost,a.AllQty,a.ProjectCost "+
				 " from tItem a "+
				 " left outer join tItemType1 i1 on a.ItemType1=i1.Code "+
				 " left outer join tItemType2 i2 on a.ItemType2=i2.Code "+
				 " left outer join tItemtype3 i3 on a.ItemType3=i3.Code "+
				 " left outer join tBrand b on a.SqlCode=b.Code "+
				 " left outer join tXTDM d on a.CommiType=d.CBM and d.ID='COMMITYPE' "+
				 " left outer join tXTDM p on a.IsFixPrice=p.CBM and p.ID='ISFIXPRICE' "+
				 " left outer join tSupplier s on a.SupplCode=s.Code "+
				 " left outer join tUom u on a.Uom = u.Code "+// +
				 " left join tcusttype ct on a.CustType=ct.Code "+
				 " where 1 = 1 ";
		
		if (StringUtils.isNotBlank(item.getCode())) {
			sql += " and (a.Code like ? or a.Descr like ?) ";
			list.add("%" + item.getCode() + "%");
			list.add("%" + item.getCode() + "%");
		}
		if(StringUtils.isNotBlank(item.getItemType1())){
			sql+=" and a.ItemType1 = ? ";
			list.add(item.getItemType1());
		}
		
		if (StringUtils.isNotBlank(item.getDescr())) {
			sql += " and (a.Descr like ? ";
			list.add("%" + item.getDescr() + "%");
			if ("1".equals(item.getContainCode())) {
				sql += " or a.Code like ? ";
				list.add("%" + item.getDescr() + "%");
			}
			sql += " ) ";
		}
		
		return this.findPageBySql(page, sql, list.toArray());
	}
}
