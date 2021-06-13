package com.house.home.dao.query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.home.entity.basic.Doc;
import com.sun.org.apache.bcel.internal.generic.Select;

@SuppressWarnings("serial")
@Repository
public class DocQueryDao extends BaseDao{
	
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
		if(doc.isHasAuthByCzybh()){
			sql += " and 1=1 ";
		} else {
			
			sql +=	" " +
				" and (exists (" +
				"	select * from (select in_b.pk from tDocFolder in_a " +
				"		inner join tDocFolder in_b on (in_b.Path like in_a.Path + '/%' and in_b.AuthType <> '1') " +
				"		where in_a.AuthType = '1' " +
				"		and exists (select 1 from tDocFolderViewRole in_c " +
				"				left join TSYS_ROLE_USER in_d on in_d.ROLE_ID = in_c.RolePK" +
				"				where in_c.FolderPK = in_b.pk and (in_d.CZYBH = ? or in_c.AuthCZY = ?)" +
				"		)" +
				"		union" +
				"		select in_b.pk from tDocFolder in_a " +
				"		inner join tDocFolder in_b on (in_b.Path like in_a.Path + '/%') " +
				"		where in_a.AuthType = '1'  and (in_b.AuthType = '1' or exists (select 1 from tDocFolderViewRole in_c " +
				"			left join TSYS_ROLE_USER in_d on in_d.ROLE_ID = in_c.RolePK" +
				"			where in_c.FolderPK = in_b.pk and (in_d.CZYBH = ? or in_c.AuthCZY = ?)" +
				"		))" +
				"	)in_a  where in_a.PK = a.FolderPK" +
				" )" +
				" and (a.IsForRegular = '1' " +
				"		or ( a.IsForRegular ='0' and " +
				"			exists (" +
				"				select 1 from tCZYBM in_a" +
				"				left join tEmployee in_b on in_a.EMNum = in_b.Number " +
				"				where in_b.IsLead = '1' and in_a.CZYBH = ? " +
				"			)" +
				"		)" +
				" ) or exists (" +
				"		select 1 from tDocFolder in_a where in_a.AuthType = '1' and in_a.pk = a.FolderPK" +
				"	) or exists (select 1 from tDocFolderViewRole in_c " +
				"		left join TSYS_ROLE_USER in_d on in_d.ROLE_ID = in_c.RolePK" +
				"		where in_c.FolderPK = a.FolderPK and (in_d.CZYBH = ? or in_c.AuthCZY = ? )" +
				"	)" +
				" )" ;
			list.add(doc.getCzybh());
			list.add(doc.getCzybh());
			list.add(doc.getCzybh());
			list.add(doc.getCzybh());
			list.add(doc.getCzybh());
			list.add(doc.getCzybh());
			list.add(doc.getCzybh());
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
			if(doc.getDateFrom() != null){
				String dateType = "enable";
				if(StringUtils.isNotBlank(doc.getDateType())){
					dateType = doc.getDateType().trim();
				}
				sql+=" and a."+dateType+"Date >= ? ";
				list.add(new Timestamp(DateUtil.startOfTheDay(doc.getDateFrom()).getTime()));
			}
			
			if(doc.getDateTo() != null){
				String dateType = "enable";
				if(StringUtils.isNotBlank(doc.getDateType())){
					dateType = doc.getDateType().trim();
				}
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
	
	public List<Map<String, Object>> getAuthNodeList(String czybm, boolean hasAuth){
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "";
		if(hasAuth){
			sql = " select a.PK id ,a.ParentPK pId,a.FolderName name, a.FolderCode ,'true' isParent,'true' nodeType from tDocFolder a " +
					" where 1=1  and expired ='F' ";
			list = this.findBySql(sql, new Object[]{});

		} else {
			sql= " select b.PK id ,b.ParentPK pId,b.FolderName name, b.FolderCode ,'true' isParent,'true' nodeType from tDocFolder a " +
				" inner join tDocFolder b on (b.Path like a.Path + '/%' and b.AuthType <> '1') " +
				" where a.AuthType = '1' or " +
				" exists (select 1 from tDocFolderViewRole in_a " +
				"		left join TSYS_ROLE_USER b on b.ROLE_ID = in_a.RolePK" +
				"		where in_a.FolderPK = a.pk and b.CZYBH = ?" +
				" 	)" +
				" union " +
				" select b.PK id ,b.ParentPK pId,b.FolderName name, b.FolderCode ,'true' isParent,'true' nodeType from tDocFolder a " +
				" inner join tDocFolder b on (a.Path like b.Path + '/%') or b.Path = a.Path" +
				" where a.AuthType = '1' or " +
				" exists (select 1 from tDocFolderViewRole in_a " +
				"		left join TSYS_ROLE_USER b on b.ROLE_ID = in_a.RolePK" +
				"		where in_a.FolderPK = a.pk and b.CZYBH = ? " +
				" ) " ;
			list = this.findBySql(sql, new Object[]{czybm, czybm});
		}
		
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
}
