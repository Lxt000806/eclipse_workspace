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
import com.house.home.entity.project.IntReplenish;
import com.house.home.entity.project.IntReplenishDT;

@SuppressWarnings("serial")
@Repository
public class IntReplenishDTDao extends BaseDao {

	/**
	 * IntReplenishDT分页信息
	 * 
	 * @param page
	 * @param intReplenishDT
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntReplenishDT intReplenishDT) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from tIntReplenishDT a where 1=1 ";

    	if (intReplenishDT.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(intReplenishDT.getPk());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getNo())) {
			sql += " and a.No=? ";
			list.add(intReplenishDT.getNo());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getIntSpl())) {
			sql += " and a.IntSpl=? ";
			list.add(intReplenishDT.getIntSpl());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getType())) {
			sql += " and a.Type=? ";
			list.add(intReplenishDT.getType());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getRemarks())) {
			sql += " and a.Remarks=? ";
			list.add(intReplenishDT.getRemarks());
		}
    	if (intReplenishDT.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(intReplenishDT.getDateFrom());
		}
		if (intReplenishDT.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(intReplenishDT.getDateTo());
		}
    	if (intReplenishDT.getArriveDate() != null) {
			sql += " and a.ArriveDate=? ";
			list.add(intReplenishDT.getArriveDate());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getDocDescr())) {
			sql += " and a.DocDescr=? ";
			list.add(intReplenishDT.getDocDescr());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getDocName())) {
			sql += " and a.DocName=? ";
			list.add(intReplenishDT.getDocName());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(intReplenishDT.getActionLog());
		}
		if (StringUtils.isBlank(intReplenishDT.getExpired()) || "F".equals(intReplenishDT.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (intReplenishDT.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(intReplenishDT.getLastUpdate());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(intReplenishDT.getLastUpdatedBy());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * IntReplenishDT分页信息
	 * 
	 * @param page
	 * @param intReplenishDT
	 * @return
	 */
	public Page<Map<String,Object>> goCodeJqGrid(Page<Map<String,Object>> page, IntReplenishDT intReplenishDT) {
		List<Object> list = new ArrayList<Object>();

		String sql = "select * from (select a.Pk,b.Descr SupplDescr,e.NOTE IsCupboardDescr,f.Address,a.Date,c.NOTE TypeDescr,a.Remarks "
			+"from tIntReplenishDT a  "
			+"left join tSupplier b on a.IntSpl=b.Code "
			+"left join tXTDM c on a.Type=c.CBM and c.ID='IntRepType' "
			+"inner join tIntReplenish d on a.No=d.No "
			+"left join tXTDM e on d.IsCupboard=e.CBM and e.ID='YESNO' "
			+"left join tCustomer f on d.CustCode=f.Code "
			+"where not exists(select 1 from tItemApp in_a where in_a.ItemType1='JC' and in_a.IntRepPK=a.PK ) ";

    	if (StringUtils.isNotBlank(intReplenishDT.getCustCode())) {
			sql += " and f.Code=? ";
			list.add(intReplenishDT.getCustCode());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getIntSpl())) {
			sql += " and a.IntSpl=? ";
			list.add(intReplenishDT.getIntSpl());
		}

    	if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += ")a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += ")a order by a.Pk";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 根据No获取DT信息
	 * @author	created by zb
	 * @date	2019-11-20--上午11:57:26
	 * @param page
	 * @param intReplenishDT
	 * @return
	 */
	public Page<Map<String, Object>> findNoPageBySql(
			Page<Map<String, Object>> page, IntReplenishDT intReplenishDT) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from (select a.PK,a.No,d.Address,c.IsCupboard,tx2.NOTE IsCupboardDescr, "
					+"c.Status,tx3.NOTE StatusDescr,c.Source,tx4.NOTE SourceDescr, "
					+"c.ReadyDate,c.ServiceDate,c.FinishDate,f.WorkerCode,g.NameChi WorkerDescr,f.EndDate, "
					+"a.IntSpl,b.Descr IntSplDescr,a.Type,tx.NOTE TypeDescr,a.Remarks, "
					+"a.Date,a.ArriveDate,a.DocDescr,a.DocName,a.ActionLog,a.Expired, "
					+"a.LastUpdate,a.LastUpdatedBy,a.ArriveRemarks,a.ResPart,tx5.NOTE ResPartDescr, "
					+"a.OKDate,a.OKDate OKDateDescr, h.No IANo, "
					+"case a.Type when '1' then dateadd(day,3,a.Date) "
					+"when '2' then dateadd(day,7,a.Date) "
					+"when '3' then dateadd(day,15,a.Date) "
					+"when '4' then dateadd(day,3,a.Date) "
					+"end preArrivDate,a.Undertaker,tx6.NOTE UndertakerDescr, "
					+"h.Date ItemAppDate, h.ConfirmDate ItemAppConfirmDate "
					+"from tIntReplenishDT a "
					+"left join tSupplier b on b.Code=a.IntSpl "
					+"inner join tIntReplenish c on c.No=a.No "
					+"inner join tCustomer d on d.Code=c.CustCode "
					+"left join tWorkerProblem e on e.No=c.WKPBNo "
            		+"left join tCustWorker f on f.PK=e.CustWkPk "
            		+"left join tWorker g on g.Code=f.WorkerCode "
            		+"left join tItemApp h on a.Pk = h.IntRepPk "
					+"left join tXTDM tx on tx.ID='IntRepType' and tx.CBM=a.Type "
					+"left join tXTDM tx2 on tx2.ID='YESNO' and tx2.CBM=c.IsCupboard "
					+"left join tXTDM tx3 on tx3.ID='IntRepStatus' and tx3.CBM=c.Status "
					+"left join txtdm tx4 on tx4.ID='IntRepSource' and tx4.CBM=c.Source "
					+"left join txtdm tx5 on tx5.ID='IntRepResPart' and tx5.CBM=a.ResPart "
					+"left join txtdm tx6 on tx6.ID='IntRepResPart' and tx6.CBM=a.Undertaker "
					+"where 1=1 ";
    	if (intReplenishDT.getPk() != null) {
			sql += " and a.PK=? ";
			list.add(intReplenishDT.getPk());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getNo())) {
			sql += " and a.No=? ";
			list.add(intReplenishDT.getNo());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getAddress())) {
			sql += " and d.address like ? ";
			list.add("%"+intReplenishDT.getAddress()+"%");
		}
    	
    	if (StringUtils.isNotBlank(intReplenishDT.getCustCode())) {
            sql += " and d.Code = ? ";
            list.add(intReplenishDT.getCustCode());
        }
    	
    	if (StringUtils.isNotBlank(intReplenishDT.getStatus())) {
			sql += " and c.Status in ('" + intReplenishDT.getStatus().replace(",", "', '") + "') ";
		}
    	
    	if (StringUtils.isNotBlank(intReplenishDT.getIsCupboard())) {
			sql += " and c.isCupboard = ? ";
			list.add(intReplenishDT.getIsCupboard());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getSource())) {
			sql += " and c.source = ? ";
			list.add(intReplenishDT.getSource());
		}
    	if (intReplenishDT.getFinishDateFrom() != null){
			sql += " and c.FinishDate>= ? ";
			list.add(intReplenishDT.getFinishDateFrom());
		}
		if (intReplenishDT.getFinishDateTo() != null){
			sql += " and c.FinishDate<= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(intReplenishDT.getFinishDateTo()).getTime()));
		}
		if (intReplenishDT.getArriveDateFrom() != null){
			sql += " and a.ArriveDate>= ? ";
			list.add(intReplenishDT.getArriveDateFrom());
		}
		if (intReplenishDT.getArriveDateTo() != null){
			sql += " and a.ArriveDate<= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(intReplenishDT.getArriveDateTo()).getTime()));
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getIntSpl())) {
			sql += " and a.IntSpl=? ";
			list.add(intReplenishDT.getIntSpl());
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getType())) {
			sql += " and a.Type=? ";
			list.add(intReplenishDT.getType());
		}
    	if (intReplenishDT.getDateFrom() != null){
			sql += " and a.Date>= ? ";
			list.add(intReplenishDT.getDateFrom());
		}
		if (intReplenishDT.getDateTo() != null){
			sql += " and a.Date<= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(intReplenishDT.getDateTo()).getTime()));
		}
    	if (StringUtils.isNotBlank(intReplenishDT.getDocDescr())) {
			sql += " and a.DocDescr=? ";
			list.add(intReplenishDT.getDocDescr());
		}
		/*if (StringUtils.isBlank(intReplenishDT.getExpired()) || "F".equals(intReplenishDT.getExpired())) {
			sql += " and a.Expired='F' ";
		}*/
    	//当查询位到货信息查询时，添加条件
    	if (StringUtils.isNotBlank(intReplenishDT.getM_umState())) {
			if ("arrive".equals(intReplenishDT.getM_umState())) {
				sql += " and c.status = '2' and a.arrivedate is null ";
			}
		}
    	if (intReplenishDT.getOkDateFrom() != null){
			sql += " and a.OkDate>= ? ";
			list.add(intReplenishDT.getOkDateFrom());
		}
		if (intReplenishDT.getOkDateTo() != null){
			sql += " and a.OkDate<= ? ";
			list.add(new Timestamp(DateUtil.endOfTheDay(intReplenishDT.getOkDateTo()).getTime()));
		}
		if (StringUtils.isNotBlank(intReplenishDT.getResPart())) {
			sql += " and a.ResPart = ? ";
			list.add(intReplenishDT.getResPart());
		}
		if (StringUtils.isNotBlank(intReplenishDT.getUndertaker())) {
			sql += " and a.Undertaker = ? ";
			list.add(intReplenishDT.getUndertaker());
		}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " )a order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " )a order by a.no desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 查询存在同楼盘状态是1、2、3的补货单
	 * @author	created by zb
	 * @date	2019-12-9--下午2:51:07
	 * @param page
	 * @param intReplenish
	 */
	public Page<Map<String, Object>> getHadDetail(Page<Map<String, Object>> page,
			IntReplenish intReplenish) {
		String sql = "select * from (select a.PK,a.No,d.Address,c.IsCupboard,tx2.NOTE IsCupboardDescr, "
					+"c.Status,tx3.NOTE StatusDescr,c.Source,tx4.NOTE SourceDescr, "
					+"c.ReadyDate,c.ServiceDate,c.FinishDate,f.WorkerCode,g.NameChi WorkerDescr,f.EndDate, "
					+"a.IntSpl,b.Descr IntSplDescr,a.Type,tx.NOTE TypeDescr,a.Remarks, "
					+"a.Date,a.ArriveDate,a.DocDescr,a.DocName,a.ActionLog,a.Expired, "
					+"a.LastUpdate,a.LastUpdatedBy,a.ArriveRemarks,a.ResPart,tx5.NOTE ResPartDescr, "
					+"case a.Type when '1' then dateadd(day,3,a.Date) "
					+"when '2' then dateadd(day,7,a.Date) "
					+"when '3' then dateadd(day,15,a.Date) "
					+"when '4' then dateadd(day,3,a.Date) "
					+"end preArrivDate "
					+"from tIntReplenishDT a "
					+"left join tSupplier b on b.Code=a.IntSpl "
					+"inner join tIntReplenish c on c.No=a.No "
					+"inner join tCustomer d on d.Code=c.CustCode "
					+"left join tWorkerProblem e on e.No=c.WKPBNo "
            		+"left join tCustWorker f on f.PK=e.CustWkPk "
            		+"left join tWorker g on g.Code=f.WorkerCode "
					+"left join tXTDM tx on tx.ID='IntRepType' and tx.CBM=a.Type "
					+"left join tXTDM tx2 on tx2.ID='YESNO' and tx2.CBM=c.IsCupboard "
					+"left join tXTDM tx3 on tx3.ID='IntRepStatus' and tx3.CBM=c.Status "
					+"left join txtdm tx4 on tx4.ID='IntRepSource' and tx4.CBM=c.Source "
					+"left join txtdm tx5 on tx5.ID='IntRepResPart' and tx5.CBM=a.ResPart "
					+"where c.CustCode=? and c.Status in ('1','2','3') and c.No<>? "
					+")a ";
		return this.findPageBySql(page, sql, new Object[]{intReplenish.getCustCode(),intReplenish.getNo()});
	}
}

