package com.house.home.dao.basic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.GuideTopic;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@SuppressWarnings("serial")
@Repository
public class GuideTopicDao extends BaseDao{
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, GuideTopic guideTopic, boolean hasAuthByCzybh) {	
		List<Object> list = new ArrayList<Object>();
 
		String sql = " select * from (select a.pk,a.Topic,a.Content,a.CreateDate,b.NameChi createCzyDescr,a.VisitCnt, a.lastUpdate,c.FolderName " +
				" from tGuideTopic a" +
				" left join tEmployee b on b.Number = a.CreateCZY " +
				" left join tGuideTopicFolder c on c.pk = a.folderPk " +
				" where 1=1 " ;
		if(hasAuthByCzybh){
			sql += " and 1=1" ;
		} else {
			sql += "and exists (select  1 from tGuideTopicFolder in_a " +
				" 			inner join tGuideTopicFolder in_d on (in_d.Path like in_a.Path + '/%') or in_d.Path = in_a.Path" +
				" 			where ','+rtrim(in_a.AdminCZY)+',' like ? and in_d.pk = a.FolderPK" +
				" ) and a.expired = 'F' ";
			list.add("%,"+guideTopic.getCzybh().trim()+",%");
		}

		if(guideTopic.getFolderPK() != null){ // 选择了目录才查询数据
			if(StringUtils.isNotBlank(guideTopic.getSubdirectory())){ //包含子目录文件
				sql+=" and (a.FolderPK = ? or exists (select 1 from tGuideTopicFolder in_a " +
						"		left join tGuideTopicFolder in_b on in_b.Path like in_a.Path+'/%'" +
						" where  in_a.PK = ? and in_b.pk = a.FolderPK ))";
				list.add(guideTopic.getFolderPK());
				list.add(guideTopic.getFolderPK());
			} else {
				sql+=" and a.folderPk = ?";
				list.add(guideTopic.getFolderPK());
			}
		} else {
			sql+=" and 1<>1 ";
		}
		
		if(StringUtils.isNotBlank(guideTopic.getAuthNode())){
			if("false".equals(guideTopic.getAuthNode())){
				sql += " and a.folderPk <> ? ";
				list.add(guideTopic.getFolderPK());
			}
		}
		
		if (StringUtils.isNotBlank(guideTopic.getQueryCondition())) {
			sql += " and content like ? or KeyWords like ? or topic like ? ";
			list.add("%"+guideTopic.getQueryCondition()+"%");
			list.add("%"+guideTopic.getQueryCondition()+"%");
			list.add("%"+guideTopic.getQueryCondition()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += ") a order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += ") a order by lastUpdate desc ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	public List<Map<String, Object>> getAuthNodeList(String czybm, boolean hasAuthByCzybh){
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "";
		if(hasAuthByCzybh){
			sql = " select a.PK id ,a.ParentPK pId,a.FolderName name, a.FolderCode ,'true' isParent,'true' nodeType " +
					" from tGuideTopicFolder a" +
					" where 1=1";
			list = this.findBySql(sql, new Object[]{});
		} else {
			sql = " select  d.PK id ,d.ParentPK pId,d.FolderName name, d.FolderCode ,'true' isParent,'true' nodeType" +
					" from tGuideTopicFolder a" +
					" inner join tGuideTopicFolder d on (d.Path like a.Path + '/%') or d.Path = a.Path" +
					" where ','+rtrim(a.AdminCZY)+',' like ? " +
					" group by d.PK ,d.ParentPK ,d.FolderName , d.FolderCode, d.Path    " +
					" union " +
					" select  d.PK id ,d.ParentPK pid,d.FolderName name, d.FolderCode ,'true' isParent,'false' nodeType" +
					" from tGuideTopicFolder a" +
					" inner join tGuideTopicFolder d on (a.Path like d.Path + '/%')" +
					" where ','+rtrim(a.AdminCZY)+',%' like ?  and not exists ( " +
					"	select	in_d.pk " +
					"	from	tGuideTopicFolder in_a" +
					"	inner join tGuideTopicFolder in_d on (in_d.Path like in_a.Path + '/%') or in_d.Path = in_a.Path" +
					"	where	','+rtrim(in_a.AdminCZY)+',' like ? and in_d.pk = d.pk" +
					" )" +
					" group by d.PK ,d.ParentPK ,d.FolderName , d.FolderCode, d.Path   " ;
			list = this.findBySql(sql, new Object[]{"%,"+czybm.trim()+",%", "%,"+czybm.trim()+",%", "%,"+czybm.trim()+",%"});
		}
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	public boolean getCheckTopic(String topic, Integer pk){
		
		String sql = " select 1 from tGuideTopic where topic = ? and pk <> ? ";
		List<Map<String,Object>> list = this.findBySql(sql, new Object[]{topic, pk});
		if(list != null && list.size() > 0){
			return false;
		}
		return true;
	}



}
