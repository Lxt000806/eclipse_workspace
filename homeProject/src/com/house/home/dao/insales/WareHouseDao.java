package com.house.home.dao.insales;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
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
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.home.entity.basic.Czybm;
import com.house.home.entity.basic.Item;
import com.house.home.entity.insales.WareHouse;

@SuppressWarnings("serial")
@Repository
public class WareHouseDao extends BaseDao {

	/**
	 * WareHouse分页信息
	 * 
	 * @param page
	 * @param wareHouse
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WareHouse wareHouse) {
		List<Object> list = new ArrayList<Object>();
		String sql ="SElECT * FROM ( select i.descr itemType1Descr,a.Code,a.Desc1,b.ZWXM,a.WareType,c.Note WareTypeDescr,a.Address,a.IsWHFee,f.Note IsWHFeeDescr,a.WHFeePer,a.IsManagePosi,d.Note IsManagePosiDescr,"
		+"a.LastUpdate,a.Expired,a.ActionLog,a.LastUpdatedBy,g.note DelivTypeDescr,j.Descr taxPayeeDescr "
		+"from tWareHouse a "
		+"inner join tCZYBM b on a.LastUpdatedBy=b.CZYBH "
		+"left join tXTDM c on c.ID='WARETYPE' and a.WareType=c.CBM "
		+"left join tXTDM d on d.ID='YESNO' and a.IsManagePosi=d.CBM "
		+"left join tXTDM f on f.ID='YESNO' and a.IsWHFee=f.CBM "
		+"left join tXTDM g on g.ID='DELIVTYPE' and a.DelivType=g.CBM " +
		" left join tItemType1 i on i.code = a.itemType1 " +
		" left join tTaxPayee j on j.Code = a.TaxPayeeCode "
		+"where 1=1 ";
		
    	if (StringUtils.isNotBlank(wareHouse.getCode())) {
			sql += " and a.Code like ? ";
			list.add("%"+wareHouse.getCode()+"%");
		}
    	if (StringUtils.isNotBlank(wareHouse.getDesc1())) {
			sql += " and a.Desc1 like ? ";
			list.add("%"+wareHouse.getDesc1()+"%");
		}
    	if (wareHouse.getDateFrom() != null){
			sql += " and a.LastUpdate>= ? ";
			list.add(wareHouse.getDateFrom());
		}
		if (wareHouse.getDateTo() != null){
			sql += " and a.LastUpdate<= ? ";
			list.add(wareHouse.getDateTo());
		}
    	if (StringUtils.isNotBlank(wareHouse.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(wareHouse.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(wareHouse.getExpired()) || "F".equals(wareHouse.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(wareHouse.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(wareHouse.getActionLog());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
    public Page<Map<String, Object>> findLimitItemType2PageBySql(Page<Map<String, Object>> page,
            WareHouse wareHouse) {

        List<Object> params = new ArrayList<Object>();
        
        String sql = "select * from ("
                + "select c.ItemType1, a.ItemType2, c.Descr ItemType2Descr, "
                + "    a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog "
                + "from tWareHouseItemType2 a "
                + "left join tWareHouse b on a.WHCode = b.Code "
                + "left join tItemType2 c on a.ItemType2 = c.Code "
                + "where 1 = 1 ";

        if (StringUtils.isNotBlank(wareHouse.getCode())) {
            sql += "and a.WHCode = ? ";
            params.add(wareHouse.getCode());
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.Expired";
        }
        
        return this.findPageBySql(page, sql, params.toArray());
    }

    public Page<Map<String, Object>> findLimitRegionPageBySql(Page<Map<String, Object>> page,
            WareHouse wareHouse) {

        List<Object> params = new ArrayList<Object>();
        
        String sql = "select * from ("
                + "select a.RegionCode, c.Descr RegionDescr, "
                + "    a.LastUpdate, a.LastUpdatedBy, a.Expired, a.ActionLog "
                + "from tWareHouseRegion a "
                + "left join tWareHouse b on a.WHCode = b.Code "
                + "left join tRegion c on a.RegionCode = c.Code "
                + "where 1 = 1 ";

        if (StringUtils.isNotBlank(wareHouse.getCode())) {
            sql += "and a.WHCode = ? ";
            params.add(wareHouse.getCode());
        }
        
        if (StringUtils.isNotBlank(page.getPageOrderBy())) {
            sql += ") a order by a." + page.getPageOrderBy() + " " + page.getPageOrder();
        } else {
            sql += ") a order by a.Expired";
        }
        
        return this.findPageBySql(page, sql, params.toArray());
    }
    
    @SuppressWarnings("deprecation")
    public Result saveForProc(WareHouse wareHouse) {
        Assert.notNull(wareHouse);
        Result result = new Result();
        Connection conn = null;
        CallableStatement call = null;
        try {
            HibernateTemplate hibernateTemplate =
                    SpringContextHolder.getBean("hibernateTemplate");
            Session session = SessionFactoryUtils.getSession(
                    hibernateTemplate.getSessionFactory(), true);
            conn = session.connection();
            
            call = conn.prepareCall("{Call pWareHouse(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            call.setString(1, wareHouse.getM_umState());
            call.setString(2, wareHouse.getCode());
            call.setString(3, wareHouse.getDesc1());
            call.setString(4, wareHouse.getWareType());
            call.setString(5, wareHouse.getAddress());
            call.setString(6, wareHouse.getIsManagePosi());
            call.setString(7, wareHouse.getIsWHFee());
            call.setDouble(8, wareHouse.getwHFeePer() == null ? 0 : wareHouse.getwHFeePer());
            call.setString(9, wareHouse.getWhFeeType());
            call.setString(10, wareHouse.getWhFeeCostType());
            call.setString(11, wareHouse.getDelivType());
            call.setString(12, wareHouse.getItemType1());
            call.setString(13, wareHouse.getSupplCode());
            call.setString(14, wareHouse.getLimitItemType2());
            call.setString(15, wareHouse.getLimitRegion());
            call.setString(16, wareHouse.getIsSupplOrder()); 
            call.setTimestamp(17, new Timestamp(wareHouse.getLastUpdate().getTime())); 
            call.setString(18, wareHouse.getLastUpdatedBy()); 
            call.setString(19, wareHouse.getExpired()); 
            call.setString(20, wareHouse.getActionLog());
            call.setString(21, wareHouse.getLimitItemType2DetailXml());
            call.setString(22, wareHouse.getLimitRegionDetailXml());
            call.registerOutParameter(23, Types.INTEGER);
            call.registerOutParameter(24, Types.NVARCHAR);
            call.setString(25, wareHouse.getSendFeeCostType());
            call.setString(26, wareHouse.getTaxPayeeCode());
            call.execute();
            result.setCode(String.valueOf(call.getInt(23)));
            result.setInfo(call.getString(24));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(null, call, conn);
        }
        return result;
    }

	@SuppressWarnings("unchecked")
	public List<WareHouse> findByNoExpired() {
		String hql = "from WareHouse a where a.expired='F'";
		return this.find(hql);
	}

	public Page<Map<String, Object>> findPageBySqlCode(
			Page<Map<String, Object>> page, WareHouse wareHouse) {
		
		List<Object> list = new ArrayList<Object>();

		String sql ="select a.Code,a.Desc1,a.LastUpdate,a.Expired,a.ActionLog,a.LastUpdatedBy "
				+"from tWareHouse a " +
				" ";
		
		if (StringUtils.isNotBlank(wareHouse.getCzybh())) {
			sql += " left join tWareHouseOperater b on a.code=b.whCode and b.czybh=? where 1=1 and b.whCode is not null ";
			list.add(wareHouse.getCzybh());
		}else{
			sql += " where 1=1 ";
		}
		if(StringUtils.isNotBlank(wareHouse.getItemRight())){
			sql+=" and (a.itemType1 in "+"('"+wareHouse.getItemRight().trim().replace(",", "','")+"') or a.itemType1 is null or a.itemType1 = '' )";
		} 
		
		if (StringUtils.isNotBlank(wareHouse.getDelXNCK())) {
			sql += " and a.Code<>'1000' ";
			//list.add(wareHouse.getCode());
		}
		// 增加IsManagePosi查询条件 --add by zb --20180809
		if (StringUtils.isNotBlank(wareHouse.getIsManagePosi())) {
			sql += " and IsManagePosi = ? ";
			list.add(wareHouse.getIsManagePosi());
		}
    	if (StringUtils.isNotBlank(wareHouse.getCode())) {
			sql += " and a.Code=? ";
			list.add(wareHouse.getCode());
		}
    	if (StringUtils.isNotBlank(wareHouse.getDesc1())) {
			sql += " and a.Desc1 like ? ";
			list.add("%"+wareHouse.getDesc1()+"%");
		}
    	
    	if (StringUtils.isNotBlank(wareHouse.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(wareHouse.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(wareHouse.getExpired()) || "F".equals(wareHouse.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(wareHouse.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(wareHouse.getActionLog());
		}
    	if (StringUtils.isNotBlank(wareHouse.getItemType1())) {
			sql += " and a.ItemType1=? ";
			list.add(wareHouse.getItemType1());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Code";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	/**
	 * 操作员[czybm]是否有仓库[wareHouse]的权限
	 * @param czybm
	 * @param wareHouse
	 * @return
	 */
	public boolean hasWhRight(Czybm czybm, WareHouse wareHouse) {
		List<Object> list = new ArrayList<Object>();
		String sql = " select 1 from tWareHouseOperater where CZYBH=? and WHCode=?";
		list.add(czybm.getCzybh());
		list.add(wareHouse.getCode());
		List<Map<String, Object>> page = this.findBySql(sql, list.toArray());
		return !page.isEmpty();
	}
	/**
	 * 根据领料单号获取仓库材料数量
	 * @author	created by zb
	 * @date	2019-11-1--上午10:10:59
	 * @param page2
	 * @param iaNo
	 * @return
	 */
	public Page<Map<String, Object>> getWHItemDetail(
			Page<Map<String, Object>> page2, String iaNo) {
		String sql = "select a.WHCode,a.ITCode,a.QtyCal,a.AvgCost from tItemWHBal a "
					+"inner join tItemApp b on b.WHCode=a.WHCode "
					+"inner join ( "
					+"	select in_a.No,in_a.ItemCode from tItemAppDetail in_a "
					+"  group by in_a.No,in_a.ItemCode "
					+")c on c.No=b.No and c.ItemCode=a.ITCode "
					+"where b.No=?";
		return this.findPageBySql(page2, sql, new Object[]{iaNo});
	}
	
}

