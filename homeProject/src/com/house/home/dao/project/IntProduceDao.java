package com.house.home.dao.project;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.project.IntProduce;

@SuppressWarnings("serial")
@Repository
public class IntProduceDao extends BaseDao{

	/**
	 * 获取生产进度登记_显示列表
	 * @author	created by zb
	 * @date	2020-2-28--下午2:59:14
	 * @param page
	 * @param intProduce
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, IntProduce intProduce) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from ( select a.PK,a.CustCode,b.Address,a.SupplCode,c.Descr SupplDescr,a.IsCupboard,tx.NOTE IsCupboardDescr, "
					+"case when a.IsCupboard='1' then d.CupAppDate else d.IntAppDate end appDate, "
					+"a.SetBoardDate,a.ArrBoardDate,a.OpenMaterialDate,a.SealingSideDate,a.ExHoleDate,a.PackDate, "
					+"a.InWHDate,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog "
					+"from tIntProduce a "
					+"left join tCustomer b on b.Code=a.CustCode "
					+"left join tSupplier c on c.Code=a.SupplCode "
					+"left join tXTDM tx on tx.ID='YESNO' and tx.CBM=a.IsCupboard "
					+"left join tCustIntProg d on d.CustCode=a.CustCode "
					+"where 1=1 ";
		
		if (StringUtils.isNotBlank(intProduce.getSupplCode())) {
			sql += " and a.SupplCode=? ";
			list.add(intProduce.getSupplCode());
		}
    	if (StringUtils.isNotBlank(intProduce.getAddress())) {
			sql += " and b.Address like ? ";
			list.add("%"+intProduce.getAddress().trim()+"%");
		}
    	if (StringUtils.isNotBlank(intProduce.getIsCupboard())) {
			sql += " and a.IsCupboard=? ";
			list.add(intProduce.getIsCupboard());
		}
    	//增加【下单日期】查询条件。同时增加下单日期列。 add by zb on 20200326
    	if (null != intProduce.getDateFrom()) {
    		if (StringUtils.isNotBlank(intProduce.getIsCupboard())) {
    			if ("1".equals(intProduce.getIsCupboard())) {
    				sql += " and d.CupAppDate >= ? ";
    			} else {
    				sql += " and d.IntAppDate >= ? ";
    			}
			} else {
				sql += "and ((d.IntAppDate >= ? and a.IsCupboard='0') or (d.CupAppDate >= ? and a.IsCupboard='1'))";
				list.add(intProduce.getDateFrom());
			}
			list.add(intProduce.getDateFrom());
		}
    	if (null != intProduce.getDateTo()) {
    		if (StringUtils.isNotBlank(intProduce.getIsCupboard())) {
    			if ("1".equals(intProduce.getIsCupboard())) {
    				sql += " and d.CupAppDate <= ? ";
    			} else {
    				sql += " and d.IntAppDate <= ? ";
    			}
    		} else {
    			sql += "and ((d.IntAppDate <= ? and a.IsCupboard='0') or (d.CupAppDate <= ? and a.IsCupboard='1'))";
    			list.add(new Timestamp(DateUtil.endOfTheDay(intProduce.getDateTo()).getTime()));
    		}
			list.add(new Timestamp(DateUtil.endOfTheDay(intProduce.getDateTo()).getTime()));
		}
    	
    	if (StringUtils.isBlank(intProduce.getIncludeShipped()) || "0".equals(intProduce.getIncludeShipped())) {
            if (StringUtils.isNotBlank(intProduce.getIsCupboard())) {
                if ("1".equals(intProduce.getIsCupboard())) {
                    sql += " and d.CupSendDate is null ";
                } else {
                    sql += " and d.IntSendDate is null ";
                }
            } else {
                sql += " and ((a.IsCupboard = '1' and d.CupSendDate is null) or (a.IsCupboard = '0' and d.IntSendDate is null)) ";
            }
        }
    	
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ") a order by "+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ") a order by a.LastUpdate desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 根据楼盘、供应商、是否橱柜查询客户集成进度表
	 * @author	created by zb
	 * @date	2020-3-4--上午10:20:55
	 * @param intProduce
	 * @return
	 */
	public List<Map<String, Object>> getCustIntProg(IntProduce intProduce) {
		String sql = "select 1 from tCustIntProg where CustCode=? ";
		if ("0".equals(intProduce.getIsCupboard())) {
			sql += " and IntSpl=? ";
		} else {
			sql += " and CupSpl=? ";
		}
		return this.findBySql(sql, new Object[]{intProduce.getCustCode(), intProduce.getSupplCode()});
	}
	/**
	 * 根据楼盘、供应商、是否橱柜三个信息查询集成生产信息
	 * @author	created by zb
	 * @date	2020-3-4--上午10:35:05
	 * @param intProduce
	 * @return
	 */
	public List<Map<String, Object>> getIntProduce(IntProduce intProduce) {
		String sql = "select 1 from tIntProduce where CustCode=? and SupplCode=? and IsCupboard=? ";
		return this.findBySql(sql, new Object[]{intProduce.getCustCode(), intProduce.getSupplCode(), 
				intProduce.getIsCupboard()});
	}

}
