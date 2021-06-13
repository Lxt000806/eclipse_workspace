package com.house.home.dao.basic;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.Doc;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository
public class DocDao extends BaseDao{

	/**
	 * 主页面查询
	 * @param page
	 * @param asset
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Doc doc) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = " select * from (select a.FolderPK,a.pk,a.DocName, a.DocCode, a.DocVersion, b.FolderName, x1.note IsForRegulardescr," +
				" a.EnableDate, a.ExpiredDate,dbo.fGetDocFileNames(a.pk) attpks,x3.note statusDescr, a.status, " +
				" e1.NameChi drawUpCzyDescr, a.DrawUpDate, e2.NameChi confirmCzyDescr, a.ConfirmDate, e3.namechi approveCZYDescr," +
				" a.ApproveDate, a.LastUpdate, x2.NOTE isNeedNoticeDescr, a.DownloadCnt, e4.nameChi filedCzyDescr, a.filedDate " +
				" from tDoc a " +
				" left join tEmployee e1 on e1.Number = a.DrawUpCZY" +
				" left join tEmployee e2 on e2.Number = a.ConfirmCZY" +
				" left join tEmployee e3 on e3.Number = a.ApproveCZY" +
				" left join tEmployee e4 on e4.Number = a.FiledCZY " +
				" left join tDocFolder b on  b.PK = a.FolderPK" +
				" left join tXTDM x1 on x1.ID='YESNO' and x1.cbm = a.IsForRegular " +
				" left join tXTDM x2 on x2.ID='YESNO' and x2.cbm = a.IsNeedNotice " +
				" left join tXTDM x3 on x3.id = 'DOCSTATUS' and x3.cbm = a.Status " +
				" where 1=1 and a.expired = 'F' " ;
		if(doc.isHasAuthByCzybh()){ // 有查看所有目录的权限 不限制目录条件
			sql+=" and 1=1 ";
		} else {
			sql += " and exists (select  1 from tDocFolder in_a " +
				" 			inner join tDocFolder in_d on (in_d.Path like in_a.Path + '/%') or in_d.Path = in_a.Path" +
				" 			where ','+rtrim(in_a.AdminCZY)+',' like ? and in_d.pk = a.FolderPK	" +
				" )";
			list.add("%,"+doc.getCzybh().trim()+",%");
		}
		
		if(doc.getFolderPK() != null){ // 选择了目录才查询数据
			if(StringUtils.isNotBlank(doc.getSubdirectory())){ //包含子目录文件
				sql+=" and (a.FolderPK = ? or exists (select 1 from tDocFolder in_a " +
						"		left join tDocFolder in_b on in_b.Path like in_a.Path+'/%'" +
						" where  in_a.PK = ? and in_b.pk = a.FolderPK ))";
				list.add(doc.getFolderPK());
				list.add(doc.getFolderPK());
			} else {
				sql+=" and a.folderPk = ?";
				list.add(doc.getFolderPK());
			}
		} else {
			sql+=" and 1<>1 ";
		}
		
		if(StringUtils.isNotBlank(doc.getAuthNode())){
			if("false".equals(doc.getAuthNode())){
				sql += " and a.folderPk <> ? ";
				list.add(doc.getFolderPK());
			}
		}
		
		if(StringUtils.isNotBlank(doc.getStatus())){
			sql += " and a.status = ? ";
			list.add(doc.getStatus());
		}
		
		if(StringUtils.isNotBlank(doc.getQueryCondition())){
			sql += " and (a.DocCode like ? or a.DocName like ? or a.KeyWords like ? )";
			list.add("%"+doc.getQueryCondition()+"%");
			list.add("%"+doc.getQueryCondition()+"%");
			list.add("%"+doc.getQueryCondition()+"%");
		}
		
		if(StringUtils.isNotBlank(doc.getDocCode())){
			sql+=" and a.docCode = ? ";
			list.add(doc.getDocCode());
		}
		
		if(StringUtils.isNotBlank(doc.getDateType())){
			
			String dateType = doc.getDateType().trim();;
			
			if(doc.getDateFrom() != null){
				
				sql+=" and a."+dateType+"Date >= ? ";
				list.add(new Timestamp(DateUtil.startOfTheDay(doc.getDateFrom()).getTime()));
			}
			
			if(doc.getDateTo() != null){
				
				sql+=" and a."+dateType+"Date <= ? ";
				list.add(new Timestamp(DateUtil.endOfTheDay(doc.getDateTo()).getTime()));
			}
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by a.lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>> getAuthNodeList(String czybm, boolean hasAuthByCzybh){
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "";
		if(hasAuthByCzybh){
			sql = " select a.PK id ,a.ParentPK pId,a.FolderName name, a.FolderCode ,'true' isParent,'true' nodeType " +
				" from tDocFolder a " +
				" where expired = 'F' ";
			list = this.findBySql(sql, new Object[]{});
		} else {
			sql = " select  d.PK id ,d.ParentPK pId,d.FolderName name, d.FolderCode ,'true' isParent,'true' nodeType" +
				" from tDocFolder a" +
				" inner join tDocFolder d on (d.Path like a.Path + '/%') or d.Path = a.Path" +
				" where ','+rtrim(a.AdminCZY)+',' like ? " +
				" group by d.PK ,d.ParentPK ,d.FolderName , d.FolderCode, d.Path    " +
				" union " +
				" select  d.PK id ,d.ParentPK pid,d.FolderName name, d.FolderCode ,'true' isParent,'false' nodeType" +
				" from tDocFolder a" +
				" inner join tDocFolder d on (a.Path like d.Path + '/%')" +
				" where ','+rtrim(a.AdminCZY)+',' like ? and not exists ( " +
				"	select	in_d.pk " +
				"	from	tDocFolder in_a" +
				"	inner join tDocFolder in_d on (in_d.Path like in_a.Path + '/%') or in_d.Path = in_a.Path" +
				"	where	','+rtrim(in_a.AdminCZY)+',' like ? and in_d.pk = d.pk" +
				" )" +
				" group by d.PK ,d.ParentPK ,d.FolderName , d.FolderCode, d.Path   " ;
			
			list = this.findBySql(sql, new Object[]{"%,"+czybm.trim()+",%", "%,"+czybm.trim()+",%", "%,"+czybm.trim()+",%",});
		}
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	/**
	 * 验证文档名称是否唯一
	 * @param docName
	 * @return
	 */
	public boolean checkDocNameUnique(String docName, Integer pk){
		
		String sql = "select 1 from tDoc where docName = ? and (? = -1 or pk <> ?) and status = '1' and expired = 'F'";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{docName, pk, pk});
		if(list != null && list.size() > 0){
			return false;
		}
		
		return true;
	}

	/**
	 * 检查文档编号唯一性
	 * @param docName
	 * @param pk
	 * @return
	 */
	public boolean checkDocCodeUnique(String docCode, Integer pk){
	
		String sql = "select 1 from tDoc where DocCode = ? and (? = -1 or pk <> ?) and status = '1' and expired = 'F' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{docCode, pk, pk});
		if(list != null && list.size() > 0){
			return false;
		}
		
		return true;
	}
	
	public void doSaveFile(Doc doc, Map<String, Object> map){
		String sql = "insert into tDocAttachment (DocPK, Attachment, AttName, AttType, UploadDate, DownloadCnt, " +
				"	LastUpdate, LastUpdatedBy, Expired, ActionLog)" +
				"	values( ?, ?, ?, '1', getdate(), 0, getdate(), ?, 'F', 'ADD')";
		this.executeUpdateBySql(sql, new Object[]{
					doc.getPk(),map.get("fullName"),map.get("originalName"), doc.getLastUpdatedBy()});
	}
	
	public void doCopyDocAttr(Integer oldPk, Doc doc){
		String sql = "insert into tDocAttachment (DocPK, Attachment, AttName, AttType, UploadDate, DownloadCnt, LastUpdate, LastUpdatedBy, " +
				" Expired, ActionLog)" +
				" select ?, Attachment, AttName, AttType, UploadDate, DownloadCnt, getdate(), ?," +
				" Expired, ActionLog from tDocAttachment a where a.DocPK = ? ";
		this.executeUpdateBySql(sql, new Object[]{doc.getPk(),doc.getLastUpdatedBy(), oldPk});
	}
	
	public void doSaveNotice(Doc doc){
		String sql = " select * from (" +
				" select c.PK,d.RolePK from tDoc a " +
				" left join tDocFolder b on b.PK = a.FolderPK" +
				" left join tDocFolder c on b.path like c.Path+'/%'" +
				" inner join tDocFolderViewRole d on d.FolderPK = c.PK" +
				" where a.PK = ? " +
				" union " +
				" select b.PK,d.RolePK from tDoc a " +
				" left join tDocFolder b on b.PK = a.FolderPK" +
				" inner join tDocFolderViewRole d on d.FolderPK = b.PK" +
				" where a.pk = ? ) a" +
				" inner join TSYS_ROLE_USER b on b.ROLE_ID = a.RolePK";
		this.executeUpdateBySql(sql, new Object[]{doc.getPk(),doc.getPk()});
	}
	
	public boolean getHasFiledDoc(String docCode){
		
		String sql = "select 1 from tDoc where docCode = ? and Status = '2' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{ docCode});
		if(list != null && list.size() > 0){
			return true;
		}
		
		return false;
	}

	public List<Map<String, Object>> getDocAtt(Integer pk){
		
		String sql = " select AttName fileName,pk from tDocAttachment where DocPK = ? and expired ='F' ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{ pk});
		
		if(list != null && list.size() > 0){
			
			return list;
		}
		
		return null;
	}
	
	public Integer getDocAttachmentPk(String attName, Integer docPk){
		
		String sql = " select pk from tDocAttachment where AttName = ? and DocPk = ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{ attName, docPk});
		
		if(list != null && list.size() > 0){
			
			return Integer.parseInt(list.get(0).get("pk").toString());
		}
		
		return 0;
	}
	
}
