package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.CupMeasure;
import com.house.home.entity.project.FixDuty;

@SuppressWarnings("serial")
@Repository
public class CupMeasureDao extends BaseDao {

	/**
	 * CupMeasure分页信息
	 * 
	 * @param page
	 * @param cupMeasure
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CupMeasure cupMeasure) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from(select cast(dbo.fGetEmpNameChi(a.Code,'61') as nvarchar(1000)) CupDesignDescr,"
				+ "cast(dbo.fGetEmpNameChi(a.Code,'11') as nvarchar(1000)) IntDesignDescr,d.note statusDescr, "
				+ "c.CustCode,a.Address,a.Area,a.CustType,a.Descr custDescr,c.CupDownHigh,c.CupUpHigh,"
				+ "c.Status,c.LastUpdate,c.LastUpdatedBy,c.Expired,c.ActionLog,b.Desc1 custTypeDescr,c.remarks,tw.NameChi workerDescr,c.AppDate,  "
				+ "case when c.IsCupboard='1' then tsir.CupDownHigh else tsir.BathDownHigh end sirCupDownHigh, "
				+ "case when c.IsCupboard='1' then isnull(c.CupDownHigh, 0) - isnull(tsir.CupDownHigh, 0) else isnull(c.CupDownHigh, 0) - isnull(tsir.BathDownHigh, 0) end diffCupDownHigh, "
				+ "case when c.IsCupboard='1' then tsir.CupUpHigh else tsir.BathUpHigh end sirCupUpHigh, "
				+ "case when c.IsCupboard='1' then isnull(c.CupUpHigh, 0) - isnull(tsir.CupUpHigh, 0) else isnull(c.CupUpHigh, 0) - isnull(tsir.BathUpHigh, 0)end diffCupUpHigh, "
				+ "te.NameChi confirmCzyDescr,c.ConfirmDate, "
				+ "case when c.IsCupboard='1' then '是' else '否' end IsCupboardDescr,c.IsCupboard "
				+ "from tCustomer a  "
				+ "left join tCusttype b on a.CustType=b.Code "
				+ "inner join tCupMeasure c on a.Code=c.CustCode " 
				+ "left join tXTDM d on c.status=d.CBM and d.ID='CUPMEASURESTAT'"
				+ "left join tWorker tw on tw.Code = c.WorkerCode "
				+ "left join tSpecItemReq tsir on tsir.CustCode = a.Code "
				+ "left join tEmployee te on te.Number = c.ConfirmCZY "
				+"where 1=1 ";

		if (StringUtils.isNotBlank(cupMeasure.getAddress())) {
			sql += " and a.address like ? ";
			list.add("%"+cupMeasure.getAddress()+"%");
		}

		if (StringUtils.isNotBlank(cupMeasure.getStatus())) {
			sql += " and c.Status=? ";
			list.add(cupMeasure.getStatus());
		}
		if (StringUtils.isNotBlank(cupMeasure.getIsCupboard())) {
			sql += " and c.IsCupboard=? ";
			list.add(cupMeasure.getIsCupboard());
		}
		if (StringUtils.isBlank(cupMeasure.getExpired())
				|| "F".equals(cupMeasure.getExpired())) {
			sql += " and c.Expired='F' ";
		}

		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ")a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.CustCode";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 确认
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public String confirm(CupMeasure cupMeasure) {
		List<Object> list = new ArrayList<Object>();

		String sql = "update tCupMeasure set status='2',lastupdate=getdate(),lastupdatedby=?,confirmCZY=?," 
					+"confirmDate=getdate(),actionLog='EDIT',cupDownHigh=?,cupUpHigh=?,remarks=? where CustCode=? and IsCupboard=? ";
		list.add(cupMeasure.getLastUpdatedBy());
		list.add(cupMeasure.getConfirmCzy());
		list.add(cupMeasure.getCupDownHigh());
		list.add(cupMeasure.getCupUpHigh());
		list.add(cupMeasure.getRemarks());
		list.add(cupMeasure.getCustCode());
		list.add(cupMeasure.getIsCupboard());
		
		return executeUpdateBySql(sql, list.toArray()).toString();
	}
	
	public Double getCupDownHigh(String custCode){
		String sql = "select CupDownHigh from tSpecItemReq where CustCode = ?";
		return Double.parseDouble(this.findBySql(sql, new Object[]{custCode}).get(0).get("CupDownHigh").toString());
	}
	
	public Double getCupUpHigh(String custCode){
		String sql = "select CupUpHigh from tSpecItemReq where CustCode = ?";
		return Double.parseDouble(this.findBySql(sql, new Object[]{custCode}).get(0).get("CupUpHigh").toString());
	}
	
	public Double getBathDownHigh(String custCode){
		String sql = "select BathDownHigh from tSpecItemReq where CustCode = ?";
		return Double.parseDouble(this.findBySql(sql, new Object[]{custCode}).get(0).get("BathDownHigh").toString());
	}
	
	public Double getBathUpHigh(String custCode){
		String sql = "select BathUpHigh from tSpecItemReq where CustCode = ?";
		return Double.parseDouble(this.findBySql(sql, new Object[]{custCode}).get(0).get("BathUpHigh").toString());
	}
}

