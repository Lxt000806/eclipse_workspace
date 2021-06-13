package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.WorkType12;

@SuppressWarnings("serial")
@Repository
public class WorkType12Dao extends BaseDao {

	/**
	 * WorkType12分页信息
	 * @param page
	 * @param workType12
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkType12 workType12) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code as WorkType12Code, a.Descr as WorkType12Name, b.descr as WorkType1Name,"
		        + " c.Descr as PrjItemName, d.Descr as OfferWorkType2Name, a.PerQualityFee as PerQualityFee,"
		        + " a.SignQualityFee as SignQualityFee, a.PayNum as PayNum, a.MinDay as MinDay, a.MaxDay as MaxDay,"
		        + " a.QualityFeeBegin as QualityFeeBegin, a.BeginCheck, i.NOTE BeginCheckDescr, "
		        + " case(a.MustPay)"
		        + " when '1' then '是'"
		        + " when '0' then '否' end as MustPay,"
		        + " a.AppMinDay as AppMinDay, a.AppMaxDay as AppMaxDay,"
		        + " a.DispSeq as DispSeq, e.Descr as NeedWorkType2ReqName, f.Descr as ConfPrjItemName,"
		        + " a.LastUpdate as LastUpdate, a.LastUpdatedBy as LastUpdatedBy, a.Expired as Expired, a.ActionLog as ActionLog,"
		        + " g.Descr as BefSameWorkType12Name, h.Descr beginPrjItemName, a.MinSalaryProvideAmount, j.NOTE confTypeDescr, "
		        + " a.IsRegisterMall, k.NOTE IsRegisterMallDescr "
				+ " from tWorkType12 a"
				+ " left join tWorkType1 b on a.WorkType1 = b.Code"
		        + " left join tPrjItem1 c on a.PrjItem = c.Code"
				+ " left join tWorkType2 d on a.OfferWorkType2 = d.Code"
		        + " left join tWorkType2 e on a.NeedWorkType2Req = e.Code"
				+ " left join tPrjItem1 f on a.ConfPrjItem = f.Code"
		        + " left join tWorkType12 g on a.BefSameWorkType12 = g.Code"
		        + " left join tPrjItem1 h on a.BeginPrjItem = h.Code"
		        + " left join tXTDM i on a.BeginCheck = i.CBM and i.ID = 'YESNO'"
		        + " left join tXTDM j on a.ConfType=j.CBM and j.id = 'WT12CONFTYPE'"
		        + " left join tXTDM k on a.IsRegisterMall = k.CBM and k.ID = 'YESNO'"
		        + " where 1=1 ";

    	if (StringUtils.isNotBlank(workType12.getCode())) {//根据工种分类12编号查询
			sql += " and a.Code=? ";
			list.add(workType12.getCode());
		}
    	if (StringUtils.isNotBlank(workType12.getDescr())) {//根据工种分类12名称查询
			sql += " and a.Descr like ?";
			list.add("%" + workType12.getDescr() + "%");
		}
    	if (StringUtils.isNotBlank(workType12.getWorkType1())) {
			sql += " and a.WorkType1=? ";
			list.add(workType12.getWorkType1());
		}
    	if(StringUtils.isNotBlank(workType12.getWorkType1Name())){//根据工种分类1名称查询
    	    sql += " and b.descr like ?";
    	    list.add("%" + workType12.getWorkType1Name() + "%");
    	}
    	if (StringUtils.isNotBlank(workType12.getLastUpdatedBy())) {
			sql += " and a.LastUpdatedBy=? ";
			list.add(workType12.getLastUpdatedBy());
		}
		if (StringUtils.isBlank(workType12.getExpired()) || "F".equals(workType12.getExpired())) {
			sql += " and a.Expired='F' ";
		}
    	if (StringUtils.isNotBlank(workType12.getActionLog())) {
			sql += " and a.ActionLog=? ";
			list.add(workType12.getActionLog());
		}
    	if (workType12.getLastUpdate() != null) {
			sql += " and a.LastUpdate=? ";
			list.add(workType12.getLastUpdate());
		}
    	if(StringUtils.isNotBlank(workType12.getIsRegisterMall())){
    		sql += "and a.IsRegisterMall = ? ";
    		list.add(workType12.getIsRegisterMall());
    	}
		if (StringUtils.isNotBlank(page.getPageOrderBy())){
			sql += " order by a."+page.getPageOrderBy()+" "+page.getPageOrder();
		}else{
			sql += " order by a.LastUpdate desc";
		}

		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<WorkType12> findByNoExpired(){
		String hql = "from WorkType12 a where a.expired='F' order by a.dispSeq ";
		return this.find(hql);
	}
	
	public Map<String,Object> getWorkType12InfoByCode(String code){
		String sql = " SELECT WorkType1,OfferWorkType2 FROM dbo.tWorkType12 WHERE Code=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{code});
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String,Object>> findWorkType12(Map<String,Object> param,UserContext uc){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tWorkType12 a where  (" +
				" (select PrjRole from tCZYBM where CZYBH= ? ) is null" +
				" or( select PrjRole from tCZYBM where CZYBH= ? ) ='' ) or  Code in(" +
				" select WorkType12 From tprjroleworktype12 pr where pr.prjrole = " +
				" (select PrjRole from tCZYBM where CZYBH= ? ) or pr.prjrole = '' )";
		list.add(uc.getCzybh());
		list.add(uc.getCzybh());
		list.add(uc.getCzybh());
		sql += " order by a.Code ";
		return this.findBySql(sql, list.toArray());
	}
	
	public List<Map<String,Object>> findDeptByWorkType12(Map<String,Object> param){
		List<Object> list = new ArrayList<Object>();
		String sql = "select code id,descr name from tworkType12Dept a where a.expired='F'";
		if(StringUtils.isNotBlank((String)param.get("pCode"))){
			sql += " and a.workType12 in ("+param.get("pCode")+") ";
		}
		sql += " order by a.Code ";
		return this.findBySql(sql, list.toArray());
	}
	
	public void edit(WorkType12 workType12){
	    String sql = "update workType12"
	            + " set Descr = ?, WorkType1 = ?, PrjItem = ?, OfferWorkType2 = ?, PerQualityFee = ?,"
	            + " SignQualityFee = ?, PayNum = ?, MinDay = ?, MaxDay = ?, QualityFeeBegin = ?,"
	            + " MustPay = ?, AppMinDay = ?, AppMaxDay = ?, DispSeq = ?, NeedWorkType2Req = ?,"
	            + " ConfPrjItem = ?, Expired = ?, LastUpdate = ?, LastUpdatedBy = ?, ActionLog = ?"
	            + " where code = ?";
	    this.executeUpdateBySql(
	            sql,
                new Object[] {workType12.getDescr(), workType12.getWorkType1(), workType12.getPrjItem(),
                        workType12.getOfferWorkType2(), workType12.getPerQualityFee(),
                        workType12.getSignQualityFee(), workType12.getPayNum(), workType12.getMinDay(),
                        workType12.getMaxDay(), workType12.getQualityFeeBegin(), workType12.getMustPay(),
                        workType12.getAppMinDay(), workType12.getAppMaxDay(), workType12.getDispSeq(),
                        workType12.getNeedWorktype2Req(), workType12.getConfPrjItem(), workType12.getExpired(),
                        workType12.getLastUpdate(), workType12.getLastUpdatedBy(), workType12.getActionLog(),
                        workType12.getCode()});
	}
	
	public Map<String, Object> view(String id){
	    String sql = "select a.Code as WorkType12Code, a.Descr as WorkType12Name, b.descr as WorkType1Name,"
                + " c.Descr as PrjItemName, d.Descr as OfferWorkType2Name, a.PerQualityFee as PerQualityFee,"
                + " a.SignQualityFee as SignQualityFee, a.PayNum as PayNum, a.MinDay as MinDay, a.MaxDay as MaxDay,"
                + " a.QualityFeeBegin as QualityFeeBegin, a.MustPay as MustPay, a.AppMinDay as AppMinDay, a.AppMaxDay as AppMaxDay,"
                + " a.DispSeq as DispSeq, e.Descr as NeedWorkType2ReqName, g.Descr as ConfPrjItemName,"
                + " a.LastUpdate as LastUpdate, a.LastUpdatedBy as LastUpdatedBy, a.Expired as Expired, a.ActionLog as ActionLog,"
                + " h.Descr as BefSameWorkType12Name ,a.maxPhotoNum,a.minPhotoNum ,a.salaryCtrl, a.beginPrjItem, i.Descr beginPrjItemName, "
                + " a.BeginCheck, a.MinSalaryProvideAmount, j.NOTE confType, a.IsRegisterMall, a.IsPrjApp "
                + " from tWorkType12 as a left join tWorkType1 as b on a.WorkType1 = b.Code"
                + " left join tPrjItem1 as c on a.PrjItem = c.Code"
                + " left join tWorkType2 as d on a.OfferWorkType2 = d.Code"
                + " left join tWorkType2 as e on a.NeedWorkType2Req = e.Code"
                + " left join tPrjItem1 as g on a.ConfPrjItem = g.Code"
                + " left join tWorkType12 as h on a.BefSameWorkType12 = h.Code"
                + " left join tPrjItem1 i on a.BeginPrjItem = i.Code"
                + " left join tXTDM j on a.ConfType=j.CBM and j.id = 'WT12CONFTYPE'"
                + " where a.code = ?";
	    
	    return this.findBySql(sql, id).get(0);
	}
	
	public List<Map<String, Object>> findBefWorkType(String id){
	    String sql = "select c.Descr, b.WorkType12, b.BefWorkType12,"
	    		+ " case(b.IsEval) when '1' then '是' when '0' then '否' end as isEval, "
	    		+ " case(b.IsNext) when '1' then '是' when '0' then '否' end isNext,x.NOTE WorkerArrCtrlDescr "
	            + " from tWorkType12 as a inner join tBefWorkType12 as b on a.Code = b.WorkType12"
	            + " inner join tWorkType12 as c on b.BefWorkType12 = c.Code"
	            + " left join tXTDM x on x.ID='WORKERARRCTRL' and x.CBM=b.WorkerArrCtrl "
	            + " where a.Code = ?";
	    return this.findBySql(sql, id);
	}
	
	public List<Map<String, Object>> findWorkType12Item(String id){
	    String sql = "select a.PK pk, b.Descr itemType1Name, c.Descr itemType2Name, d.Descr itemType3Name,"
	            + " a.LastAppDay lastAppDay, a.LastUpdate lastUpdate, a.LastUpdatedBy lastUpdatedBy,"
	            + " a.Expired expired,"
	            + " a.ActionLog actionLog, e.NOTE appType"
	            + " from tWorkType12Item a"
	            + " left join tItemType1 b on a.ItemType1 = b.Code"
	            + " left join tItemType2 c on a.ItemType2 = c.Code"
	            + " left join tItemType3 d on a.ItemType3 = d.Code"
	            + " inner join tXTDM e on e.ID = 'APPTYPE' and a.AppType = e.CBM"
	            + " where a.WorkType12 = ?"
	            + " order by a.LastUpdate desc";
	    return this.findBySql(sql, id);
	}
	/**
	 * 工艺列表（BS和APP用）
	 * @author	created by zb
	 * @date	2019-4-30--上午11:29:19
	 * @param page
	 * @param workType12
	 * @return
	 */
	public Page<Map<String, Object>> getTechBySql(
			Page<Map<String, Object>> page, WorkType12 workType12) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.Code,a.Descr,a.WorkType12,a.DisSeq,a.SourceType,tx.NOTE SourceTypeDescr, "
					+"a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog,b.gotNum gotPicNum "
					+"from tTechnology a "
					+"left join tXTDM tx on tx.CBM=a.SourceType and tx.ID='SOURCETYPE' "
					+"left join ( "
					+"	select in_b.TechCode,count(*) gotNum from tSignIn in_a "
					+"	left join tSignInPic in_b on in_b.No=in_a.no "
					+"	where Expired='F' and TechCode is not null " ;
		if (StringUtils.isNotBlank(workType12.getCustCode())) {
			sql +=	"	and in_a.CustCode=? " ;
			list.add(workType12.getCustCode());
		}
			sql +=	"	group by in_b.TechCode " +
					")b on b.TechCode=a.Code " +
					"where a.WorkType12=? and a.Expired='F' ";
		list.add(workType12.getCode());
		if (StringUtils.isNotBlank(workType12.getSourceType())) {
			sql += "and a.SourceType = ? ";
			list.add(workType12.getSourceType());
		}
			sql +=	"order by a.SourceType,a.DisSeq ";
		return this.findPageBySql(page, sql, list.toArray());
	}
	/**
	 * 获取是否上传图片模板为是的工种
	 * @author	created by zb
	 * @date	2019-5-1--下午3:13:08
	 * @return
	 */
	public List<Map<String, Object>> getTechWorkType12() {
		String sql = "select *,1 MinPhotoNum from tWorkType12 a " +
					"left join ( " +
					"	select WorkType12,count(*) MaxPhotoNum from tTechnology " +
					"	where Expired='F' and SourceType='1' group by WorkType12 " +
					") b on b.WorkType12=a.Code " +
					"where a.IsTechnology='1'";
		return this.findListBySql(sql, new Object[]{});
	}

	public List<Map<String, Object>> getWorkType12List() {
		String sql = "select Code,Descr from  tWorkType12 "
				+" where Expired='F'";
		return this.findListBySql(sql, new Object[]{});
	}
	
	public Page<Map<String, Object>> goQualityJqGrid(
			Page<Map<String, Object>> page, WorkType12 workType12) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select a.WorkerClassify,a.WorkType12,b.NOTE WorkerClassifyDescr,c.Descr WorkType12Descr, "
				+"a.SignQualityFee,a.QualityFeeBegin,a.PerQualityFee,a.LastUpdate,a.LastUpdatedBy,a.Expired,a.ActionLog " 
				+"from tWorkCsfQuality a  "
				+"left join tXTDM b on a.WorkerClassify=b.CBM and b.ID='WORKERCLASSIFY' " 
				+"left join tWorkType12 c on a.WorkType12=c.Code where a.WorkType12=? " ;
		list.add(workType12.getCode());
		return this.findPageBySql(page, sql, list.toArray());
	}
	public void doAddQlt(WorkType12 workType12){
	    String sql = "insert into tWorkCsfQuality values(?,?,?,?,?,?,?,?,?)";
	    this.executeUpdateBySql(
	            sql,
                new Object[] {workType12.getWorkerClassify(), workType12.getCode(), workType12.getSignQualityFee(),
                        workType12.getQualityFeeBegin(), workType12.getPerQualityFee(), workType12.getLastUpdate(),
                        workType12.getLastUpdatedBy(),"F","ADD"});
	}
	public void doUpdateQlt(WorkType12 workType12){
	    String sql = "update tWorkCsfQuality set WorkerClassify=?,SignQualityFee=?,QualityFeeBegin=?,PerQualityFee=?," +
	    		"LastUpdate=getdate(),LastUpdatedBy=?,ActionLog='EDIT' where WorkerClassify=? and WorkType12=? ";
	    this.executeUpdateBySql(
	            sql,
                new Object[] {workType12.getWorkerClassify(), workType12.getSignQualityFee(),
                        workType12.getQualityFeeBegin(), workType12.getPerQualityFee(), 
                        workType12.getLastUpdatedBy(),workType12.getOldWorkerClassify(),workType12.getCode()});
	}
	public void doDeleteQlt(WorkType12 workType12){
	    String sql = "delete tWorkCsfQuality where WorkerClassify=? and WorkType12=?  ";
	    this.executeUpdateBySql(
	            sql,
                new Object[] {workType12.getWorkerClassify(),workType12.getCode()});
	}
}

