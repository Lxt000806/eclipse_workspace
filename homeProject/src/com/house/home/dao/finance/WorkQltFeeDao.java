package com.house.home.dao.finance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.finance.WorkQltFeeTran;
import com.house.home.entity.project.Worker;

@SuppressWarnings("serial")
@Repository
public class WorkQltFeeDao extends BaseDao {

	/**
	 * WorkQltFee分页信息
	 * 
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Worker worker) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select  a.Code,a.WorkType12,a.QualityFee,a.NameChi,a.Descr,a.AccidentFee  from("
				+ "SELECT distinct a.Code,a.WorkType12,a.QualityFee,a.NameChi,c.Descr,a.AccidentFee FROM tWorker a "
				+ "LEFT JOIN tWorkQltFeeTran b on a.code=b.WorkerCode "
				+ "LEFT JOIN tWorkType12 c on a.WorkType12=c.code "
				+ "where 1=1 ";

		if (StringUtils.isNotBlank(worker.getCode())) {
			sql += " and a.Code = ? ";
			list.add(worker.getCode());
		}
		if (StringUtils.isNotBlank(worker.getNameChi())) {
			sql += " and a.NameChi like ? ";
			list.add("%"+worker.getNameChi()+"%");
		}
		if (StringUtils.isNotBlank(worker.getWorkType12())) {
			sql += " and a.WorkType12 in ('"+worker.getWorkType12().replace(",", "','")+"') ";
		}
		if (StringUtils.isBlank(worker.getExpired())
				|| "F".equals(worker.getExpired())) {
			sql += " and a.Expired='F' and a.IsLeave='0' ";
		}
		if (StringUtils.isNotBlank(worker.getEmpCode())){
			sql += " and a.EmpCode = ? ";
			list.add(worker.getEmpCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.code";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * WorkQltFee明细信息
	 * 
	 * @param page
	 * @param workQltFeeTran
	 * @return
	 */
	public Page<Map<String, Object>> findDetailBySql(
			Page<Map<String, Object>> page, WorkQltFeeTran workQltFeeTran) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.code,a.worktype12,a.qualityfee,a.namechi,c.descr,b.date,"
				+ "b.TryFee,b.AftFee,b.pk,b.LastUpdatedBy,case when PrefixCode='LFD' then e3.Address when PrefixCode='IA' then e5.Address when PrefixCode='CL' then e4.Address else isnull(e.Address, e2.Address) end Address,x1.note typedescr, " 
				+ "case when PrefixCode='IA' then '领料生成,单号：'+b.RefPK+'。' when PrefixCode='CL' then '材料增减生成,单号：'+b.RefPK+'。' when PrefixCode='WCD' then '基础人工成本生成,单号：'+d.NO+'。' "
				+ "when PrefixCode='LFD' then '人工费用生成,单号：'+d2.NO+'。' else '' end +b.Remarks Remarks, "
				+ "case when PrefixCode='WCD' then ref1.Address when PrefixCode='LFD' then ref2.Address when PrefixCode='CL' then ref3.Address when PrefixCode='IA' then ref4.Address else '' end RefAddress "
				+ "from tworker a "
				+ "left join tworkqltfeetran b on a.code=b.workercode "
				+ "left join tworktype12 c on a.worktype12=c.code "
				+ "left join tWorkCostDetail d on cast(d.PK as nvarchar(20)) = b.RefPk "
				+ "left join tLaborFeeDetail d2 on cast(d2.PK as nvarchar(20)) = b.RefPk "
				+ "left join tCustomer e on e.Code = d.CustCode "
				+ "left join tCustomer e2 on e2.Code = b.RefPk "//添加楼盘，用RefPK关联tWorkCostDetail表跟tCustomer表 (存在RefPk直接关联customer表)--add by zb
				+ "left join tItemChg ic on b.RefPk=ic.No "
				+ "left join tItemApp ia on b.RefPk=ia.No "
				+ "left join tCustomer e3 on d2.CustCode=e3.Code "
				+ "left join tCustomer e4 on ic.CustCode=e4.Code "
				+ "left join tCustomer e5 on ia.CustCode=e5.Code "
				+ "left join txtdm x1 on x1.cbm=b.type and x1.id='WKQLTFEETYPE' "
				+ "left join tCustomer ref1 on d.RefCustCode = ref1.Code "
				+ "left join tCustomer ref2 on d2.RefCustCode = ref2.Code "
				+ "left join tCustomer ref3 on ic.RefCustCode = ref3.Code "
				+ "left join tCustomer ref4 on ia.RefCustCode = ref4.Code "
				+ "where 1=1 and b.TryFee <> 0 ";
		if (StringUtils.isNotBlank(workQltFeeTran.getCode())) {
			sql += " and a.code=? ";
			list.add(workQltFeeTran.getCode());
		}
		if (workQltFeeTran.getDateFrom() != null) {
			sql += " and b.Date>= ? ";
			list.add(workQltFeeTran.getDateFrom());
		}
		if (workQltFeeTran.getDateTo() != null) {
			sql += " and b.Date< ? ";
			list.add(DateUtil.addInteger(workQltFeeTran.getDateTo(),
					Calendar.DATE, 1));
		}
		if (StringUtils.isNotBlank(workQltFeeTran.getType())) {
			sql += " and b.type=? ";
			list.add(workQltFeeTran.getType());
		}
		if(StringUtils.isNotBlank(workQltFeeTran.getEmpCode())){
			sql += " and a.EmpCode = ? ";
			list.add(workQltFeeTran.getEmpCode());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += " )a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ")a order by a.date desc";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}

	/**
	 * 存取
	 * 
	 * @param workQltFeeTran
	 */
	public void doUpdate(WorkQltFeeTran workQltFeeTran) {
		if ("1".equals(workQltFeeTran.getType())){
			String sql = "update  tWorker set QualityFee=?  WHERE Code=?";
			this.executeUpdateBySql(
					sql,
					new Object[] {
							workQltFeeTran.getQualityFee()+ workQltFeeTran.getTryFee(),
							workQltFeeTran.getWorkerCode() });
		}else{
			String sql = "update  tWorker set AccidentFee=? WHERE Code=?";
			this.executeUpdateBySql(
					sql,
					new Object[] {
							workQltFeeTran.getAccidentFee()+ workQltFeeTran.getTryFee(),
							workQltFeeTran.getWorkerCode() });	
		}
		
	}
}
