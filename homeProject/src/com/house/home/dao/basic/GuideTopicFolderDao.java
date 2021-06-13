package com.house.home.dao.basic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseDao;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DbUtil;
import com.house.framework.commons.utils.SpringContextHolder;
import com.house.framework.commons.utils.StringUtils;
import com.house.home.entity.basic.GuideTopicFolder;

@SuppressWarnings("serial")
@Repository
public class GuideTopicFolderDao extends BaseDao {

	@SuppressWarnings(value="unchecked")
	public List<GuideTopicFolder> getAll(){
		String hql = "from GuideTopicFolder ";
		return this.find(hql);
	}
	
	@SuppressWarnings(value="unchecked")
	public List<GuideTopicFolder> getSubGuideTopicFolder(Integer parentPk){
		if(null == parentPk)
			parentPk = 0;
		String hql = "from GuideTopicFolder where parentPk = ? ";
		return this.find(hql, new Object[]{parentPk});
	}
	
	/**
	 * 保存
	 * @param guideTopicFolder
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Result doSaveProc(GuideTopicFolder guideTopicFolder) {
		Assert.notNull(guideTopicFolder);
		Result result = new Result();
		Connection conn = null;
		CallableStatement call = null;
		try {
			HibernateTemplate hibernateTemplate = SpringContextHolder
					.getBean("hibernateTemplate");
			Session session = SessionFactoryUtils.getSession(
					hibernateTemplate.getSessionFactory(), true);
			conn = session.connection();
			call = conn.prepareCall("{Call pGuideTopicFolder(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			call.setString(1, guideTopicFolder.getM_umState());
			call.setInt(2, guideTopicFolder.getPk()==null?0:guideTopicFolder.getPk());
			call.setInt(3, guideTopicFolder.getParentPk()==null?0:guideTopicFolder.getParentPk());
			call.setString(4, guideTopicFolder.getFolderName());
			call.setString(5, guideTopicFolder.getFolderCode());
			call.setString(6, guideTopicFolder.getAdminCzy());
			call.setString(7, guideTopicFolder.getAuthType());
			call.setString(8, guideTopicFolder.getIsAuthWorker());
			call.setString(9, guideTopicFolder.getAuthWorkerTypes());
			call.setString(10, guideTopicFolder.getTopicFolderViewRole());
			call.setString(11, guideTopicFolder.getLastUpdatedBy());
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.NVARCHAR);
			call.setString(14, guideTopicFolder.getIsAuthPM());
			call.execute();
			result.setCode(String.valueOf(call.getInt(12)));
			result.setInfo(call.getString(13));
		} catch (Exception e) {
			e.printStackTrace();
			result.setInfo(e.getMessage());
		}finally {
			DbUtil.close(null, call, conn);
		}
		return result;	
	}
	
	public boolean hasGuideTopicByFolderPK(Integer folderPK) {
		if (folderPK == null){
			return false;
		}
		String sql =" select 1 from tGuideTopic where folderPK= ?  ";
		List<Map<String, Object>> list = this.findBySql(sql, new Object[] {folderPK});
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, GuideTopicFolder guideTopicFolder) {	
		List<Object> list = new ArrayList<Object>();
		String sql = "";
		if(guideTopicFolder.isHasAuthByCzybh()){
			sql = "select * from (" +
					" select dbo.fgetguidetopicfoldername(a.Path) folderNames, a.PK, a.ParentPK, a.FolderName, a.FolderCode, a.Path" +
					" from tGuideTopicFolder a " +
					") a where 1=1";
		} else {
			sql = "select * from (" +
				"	select  dbo.fgetguidetopicfoldername(d.Path) folderNames,d.PK ,d.ParentPK ,d.FolderName , d.FolderCode, d.Path " +
				"	from tGuideTopicFolder a" +
				" 	inner join tGuideTopicFolder d on (d.Path like a.Path + '/%' and a.AuthType <> '1' ) or d.Path = a.Path" +
				" 	where ','+rtrim(a.AdminCZY)+',' like ? " +
				" 	group by d.PK ,d.ParentPK ,d.FolderName , d.FolderCode, d.Path    " +
				" ) a where 1=1 ";

				list.add("%,"+guideTopicFolder.getCzybm().trim()+",%");
		}
		
		if(StringUtils.isNotBlank(guideTopicFolder.getFolderName())){
			sql+=" and (a.folderNames like ? or a.FolderCode like ?)";
			list.add("%"+guideTopicFolder.getFolderName()+"%");
			list.add("%"+guideTopicFolder.getFolderName()+"%");
		}
		
		if (StringUtils.isNotBlank(page.getPageOrderBy())) {
			sql += "order by a." + page.getPageOrderBy() + " "
					+ page.getPageOrder();
		} else {
			sql += "order by a.PK ";
		}
		return this.findPageBySql(page, sql, list.toArray());
	}
	

}

