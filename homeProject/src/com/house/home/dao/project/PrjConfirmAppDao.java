package com.house.home.dao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.CustWorkerAppEvt;
import com.house.home.client.service.evt.PrjConfirmAppEvt;

@SuppressWarnings("serial")
@Repository
public class PrjConfirmAppDao extends BaseDao {
	
	public Page<Map<String,Object>> getPrjConfirmAppList(Page<Map<String,Object>> page,PrjConfirmAppEvt evt){
		List<Object> list = new ArrayList<Object>();
		String sql = " SELECT pca.PK,c.Address,pca.Status,(case when pca.Status='2' then x1.NOTE else '未验收' end)+( "
				    +" 		case when pca.Status='2' and pca.RefConfirmNo is not null and pca.RefConfirmNo<>'' "
				    +" 		then "
				    +" 			'('+case when tppc.prjLevel <> '' and tppc.prjLevel is not null then tx2.NOTE "
				    +" 			else '不合格' end+')' "
				    +" 		else '' end	"
				    +" ) StatusDescr,tpi.Descr PrjItemDescr,pca.AppDate "
					+" FROM dbo.tPrjConfirmApp pca "
					+" LEFT JOIN dbo.tCustomer c ON pca.CustCode = c.Code "
					+" LEFT JOIN dbo.tXTDM x1 ON x1.CBM = pca.Status AND x1.ID='CONFMAPPSTS' "
					+" LEFT JOIN dbo.tPrjItem1 tpi ON tpi.Code = pca.PrjItem "
					+" left join tPrjProgConfirm tppc on tppc.No = pca.RefConfirmNo "
					+" left join tXTDM tx2 on tx2.ID='PRJLEVEL' and tx2.CBM = tppc.prjLevel "
					+" WHERE 1=1 ";
		if(StringUtils.isNotBlank(evt.getAddress())){
			sql += " AND c.Address like ? ";
			list.add("%"+evt.getAddress()+"%");
		}
		if(StringUtils.isNotBlank(evt.getStatus())){
			sql += " AND pca.Status=? ";
			list.add(evt.getStatus());
		}
		if(StringUtils.isNotBlank(evt.getProjectMan())){
			sql += " AND c.projectMan=? ";
			list.add(evt.getProjectMan());
		}
		sql += " ORDER BY pca.AppDate DESC  ";
		return this.findPageBySql(page, sql , list.toArray());
	}
	
	public boolean existPrjConfirmApp(String custCode,String prjItem,String status){
		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT * "
					+" FROM dbo.tPrjConfirmApp "
					+" WHERE 1=1 ";
		if(StringUtils.isNotBlank(custCode)){ 
			sql += " AND CustCode=? ";
			params.add(custCode);
		}
		if(StringUtils.isNotBlank(prjItem)){
			sql += " AND PrjItem=? ";
			params.add(prjItem);
		}
		if(StringUtils.isNotBlank(status)){
			sql += " AND Status in ('"+status.replace(",", "','")+"')";
		}
		List<Map<String,Object>> list = this.findBySql(sql, params.toArray());
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	public boolean checkPrjProg(String custCode,String prjItem){
		String sql = " SELECT * "
					+" FROM dbo.tPrjProg "
					+" WHERE CustCode=? AND PrjItem=? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,prjItem});
		if(list != null && list.size() > 0){
			if(list.get(0).get("EndDate") != null){
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	

	public Map<String,Object> getPrjConfirmAppByPk(Integer pk){
		String sql = " SELECT c.Address,pca.CustCode,pca.PrjItem,tpi.Descr PrjItem1Descr,pca.Remarks,pca.Status,x1.NOTE StatusDescr,pca.AppDate,e.NameChi ConfirmCzyDescr,x2.NOTE PrjLevelDescr,ppc.Date ConfirmDate, "
					+" case when pca.RefConfirmNo is not null and pca.RefConfirmNo<>'' and ppc.IsPass='1' then '是' else '否' end isPassDescr "
					+" FROM dbo.tPrjConfirmApp pca "
					+" LEFT JOIN dbo.tCustomer c ON pca.CustCode = c.Code "
					+" LEFT JOIN dbo.tPrjItem1 tpi ON tpi.Code = pca.PrjItem "
					+" LEFT JOIN dbo.tXTDM x1 ON x1.CBM = pca.Status AND x1.ID = 'CONFMAPPSTS' "
					+" LEFT JOIN dbo.tPrjProgConfirm ppc ON pca.RefConfirmNo = ppc.No "
					+" LEFT JOIN dbo.tEmployee e ON ppc.LastUpdatedBy = e.Number "
					+" LEFT JOIN dbo.tXTDM x2 ON x2.CBM = ppc.prjLevel AND x2.ID = 'PRJLEVEL' "
					+" WHERE pca.PK=? "; 
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{pk});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public boolean judgeProgTemp(PrjConfirmAppEvt evt){
		String sql = " SELECT DISTINCT c.Code,c.Descr,c.Address,ppt.No,ppt.Type "
					+" FROM dbo.tCustomer c "
					+" LEFT JOIN dbo.tPrjProgTemp ppt ON c.PrjProgTempNo = ppt.No "
					+" WHERE c.Code=? AND ppt.Type='2' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{evt.getCustCode()});
		if(list != null && list.size()>0){
			return true;
		}
		return false;
	}
	
	public Map<String,Object> getMaxPkCustWorker(String custCode,String prjItem){
		String sql=" select w.NameChi,cw.WorkerCode,we.remark,we.Score,we.healthScore,we.toolScore,cw.PK maxPK,we.PK workerEvalId "
				  +" from tCustWorker cw "
				  +" left join tworker w on cw.WorkerCode = w.Code "
				  +" left join tWorkerEval we on we.CustWkPk = cw.PK "
				  +" where cw.CustCode = ? and cw.WorkType12 in ( "
				  +" 	select WorkType12 from tPrjItem1 in_a where in_a.Code=? "
				  +" ) and cw.PK = ( "
				  +" 	select max(PK) from tCustWorker in_a where in_a.CustCode = cw.CustCode and in_a.WorkType12 = cw.WorkType12 "
				  +" ) ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{custCode,prjItem});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public List<Map<String,Object>> getConfirmStatus(String custCode,String workType12){
		System.out.println(custCode+","+workType12);
		String sql=/*"select a.CustCode,a.PrjItem,b.Descr PrjItemDescr,c.Status,c.AppDate,e.Date ConfirmDate,d.Address,d.ProjectMan,e.isPass from tPrjProg a "
				+" inner join tPrjItem1 b on a.PrjItem = b.Code "
				+" left join tPrjConfirmApp c on a.CustCode=c.CustCode and c.PrjItem=a.PrjItem "
				+" left join tCustomer d on a.CustCode=d.Code"
				//+" left join tPrjProgConfirm e on b.Code=e.PrjItem and e.CustCode=a.CustCode"
				+" left join tPrjProgConfirm e on e.No = c.RefConfirmNo "
				+" where b.IsConfirm='1' and a.CustCode=? and b.worktype12=? "
				+" order by b.Seq";*/
				 "select e.confPK PK,a.CustCode,a.PrjItem,b.Descr PrjItemDescr,d.Address,d.ProjectMan,e.ConfirmDate,e.IsPass,e.AppDate,e.Status,d.EndDate "
				+"from tPrjProg a "
				+"inner join tPrjItem1 b on a.PrjItem = b.Code "
				+"left join tCustomer d on a.CustCode = d.Code "
				+"left join ( "
				+" 	select a.PK,c.PK confPk,a.ConfirmDate,c.AppDate, "
				+" 	case when a.ConfirmCZY <> '' and a.ConfirmCZY is not null then '1' else '0' end isPass, "
				+" 	case when a.ConfirmCZY <> '' and a.ConfirmCZY is not null then '2' else c.Status end Status "
				+"	from tPrjProg a "
				+" 	inner join tPrjItem1 b on a.PrjItem = b.Code "
				+" 	left join tPrjConfirmApp c on a.CustCode = c.CustCode and c.PrjItem = a.PrjItem and c.Status = '1' "
				+" 	where b.IsConfirm='1' and a.CustCode=? and b.worktype12=? " 
				+") e on e.PK = a.PK "
				+"where b.IsConfirm='1' and a.CustCode=? and b.worktype12=? "
				+"order by b.Seq ";
		return this.findBySql(sql, new Object[]{custCode,workType12,custCode,workType12});
	}
	
	public List<Map<String,Object>> getBefWorkerList(String custCode,String workerCode,String workType12){
		String sql="select a.PK CustWkPk,a.CustCode,a.WorkerCode,a.WorkType12,b.NameChi,c.Descr WorkType12Descr,d.Score,d.WorkerEvalId,d.remark,d.LastUpdate from tCustWorker a "
				+" left join tWorker b on a.WorkerCode=b.Code "
				+" left join tWorkType12 c on a.WorkType12=c.Code "
				+" left join (select PK WorkerEvalId,CustWkPk,Remark,Score,LastUpdate from tWorkerEval where Type='2' and EvalWorker=? ) d on a.PK=d.custWkPk " 
				+" where a.CustCode=? and a.WorkerCode<>?  and a.WorkType12 in " 
				+" 	(select BefWorktype12 from tBefWorkType12 where WorkType12=? and IsEval='1' )";
		return this.findBySql(sql, new Object[]{workerCode,custCode,workerCode,workType12});
	}
	
	
}	
