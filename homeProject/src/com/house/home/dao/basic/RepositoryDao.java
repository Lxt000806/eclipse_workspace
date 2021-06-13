package com.house.home.dao.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.client.service.evt.RepositoryEvt;


@SuppressWarnings("serial")
@Repository
public class RepositoryDao extends BaseDao {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(RepositoryDao.class);
	

	public Page<Map<String, Object>> getGuideTopicList(
			Page<Map<String, Object>> page, RepositoryEvt evt) {	
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.Pk, a.Topic,a.content,a.KeyWords,a.VisitCnt "
					+" from tGuideTopic a "
					+" where 1=1 and a.expired = 'F' "  
					+" and a.FolderPk in ( " 
					+" 	select in_a.pk from tGuideTopicFolder in_a "
					+" 	where in_a.AuthType = '1' or IsAuthPM = '1' or exists (select 1 from tTopicFolderViewRole in_c "
					+"		left join TSYS_ROLE_USER in_d on in_d.ROLE_ID = in_c.RolePK " 
					+"		where in_c.FolderPK = in_a.pk and in_d.CZYBH = ? "
					+"	) "
					+" 	union all  "
					+"	select out_a.PK from tGuideTopicFolder out_a "
					+"	inner join ( "
					+"		select * from tGuideTopicFolder in_a "
					+"		where in_a.AuthType = '1' or IsAuthPM = '1' or exists (select 1 from tTopicFolderViewRole in_c "
					+"			left join TSYS_ROLE_USER in_d on in_d.ROLE_ID = in_c.RolePK "
					+"			where in_c.FolderPK = in_a.pk and in_d.CZYBH = ? " 
					+"			) "
					+"	) out_b on out_a.Path like out_b.Path+'/%' " 
					+")";

		list.add(evt.getCzybh());
		list.add(evt.getCzybh());
		
		if(StringUtils.isNotBlank(evt.getSearchText())){
			sql+=" and (a.Topic like ? or a.KeyWords like ? )";
			list.add("%"+evt.getSearchText()+"%");
			list.add("%"+evt.getSearchText()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "order by a.LastUpdate Desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getDocList(
			Page<Map<String, Object>> page, RepositoryEvt evt) {	
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.pk,a.DocName, a.DocCode "
				+" from tDoc a "
				+" where 1=1 and a.expired = 'F'  and a.Status = '1' " 
				+" and ( a.IsForRegular = '1' "
				+" 		or ( a.IsForRegular ='0' "  
				+" 			and exists ( "
				+" 				select 1 from tCZYBM in_a "
				+" 				inner join tEmployee in_b on in_a.EMNum = in_b.Number "
				+" 				where in_b.IsLead = '1' and in_a.CZYBH = ? "
				+" 				) "
				+" 			) "
				+" ) "
				+" and a.FolderPk in ( "
				+" 	select in_a.PK from tDocFolder in_a "
				+" 	where in_a.AuthType = '1' or IsAuthPM = '1' or exists (select 1 from tDocFolderViewRole in_b "
				+" 		left join TSYS_ROLE_USER in_c on in_c.ROLE_ID = in_b.RolePK "
				+" 		where in_b.FolderPK = in_a.pk and in_c.CZYBH = ? "
				+" 	) "
				+" 	union all "
				+" 	select out_a.PK from tDocFolder out_a "
				+" 	inner join ( "
				+" 		select * from tDocFolder in_a "
				+" 		where in_a.AuthType = '1' or IsAuthPM = '1' or exists (select 1 from tDocFolderViewRole in_b "
				+" 			left join TSYS_ROLE_USER in_c on in_c.ROLE_ID = in_b.RolePK "
				+" 			where in_b.FolderPK = in_a.pk and in_c.CZYBH = ? "
				+" 		) "
				+" 	) out_b on out_a.Path like out_b.Path+'/%' "
				+" ) ";

		list.add(evt.getCzybh());
		list.add(evt.getCzybh());
		list.add(evt.getCzybh());
		
		if(StringUtils.isNotBlank(evt.getSearchText())){
			sql+=" and (a.DocName like ? or a.DocCode like ? or a.KeyWords like ? )";
			list.add("%"+evt.getSearchText()+"%");
			list.add("%"+evt.getSearchText()+"%");
			list.add("%"+evt.getSearchText()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "order by a.LastUpdate Desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public List<Map<String, Object>> getDocAttachmentList(Integer pk){
		String sql = " select a.pk, a.docPk, a.attachment, a.attName from  tDocAttachment a "
				+" where a.DocPK = ? ";
		
		return this.findBySql(sql, new Object[]{pk});
	}
	
	public Page<Map<String, Object>> getGuideTopicListForWorker(
			Page<Map<String, Object>> page, RepositoryEvt evt) {	
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.Pk, a.Topic,a.content,a.KeyWords,a.VisitCnt "
					+" from tGuideTopic a "
					+" where 1=1 and a.expired = 'F' "  
					+" and exists (select 1 from tGuideTopicFolder in_a " 
					+"  			where in_a.IsAuthWorker = '1' and in_a.AuthWorkerTypes like ? "
					+"  			and a.FolderPK = in_a.PK "
					+" ) ";

		list.add("%"+evt.getWorkType12().trim()+"%");
		
		if(StringUtils.isNotBlank(evt.getSearchText())){
			sql+=" and (a.Topic like ? or a.KeyWords like ? )";
			list.add("%"+evt.getSearchText()+"%");
			list.add("%"+evt.getSearchText()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "order by a.LastUpdate Desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	
	public Page<Map<String, Object>> getDocListForWorker(
			Page<Map<String, Object>> page, RepositoryEvt evt) {	
		List<Object> list = new ArrayList<Object>();

		String sql = " select a.pk,a.DocName, a.DocCode "
					+" from tDoc a "  
					+" where 1=1 and a.expired = 'F' " 
					+" and exists (select 1 from tDocFolder in_a "
					+"  			where in_a.IsAuthWorker = '1' and in_a.AuthWorkerTypes like ? " 
					+" 				and a.FolderPK = in_a.PK "
					+"  ) ";

		list.add("%"+evt.getWorkType12().trim()+"%");
		
		if(StringUtils.isNotBlank(evt.getSearchText())){
			sql+=" and (a.DocName like ? or a.DocCode like ? or a.KeyWords like ? )";
			list.add("%"+evt.getSearchText()+"%");
			list.add("%"+evt.getSearchText()+"%");
			list.add("%"+evt.getSearchText()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "order by a.LastUpdate Desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
}
